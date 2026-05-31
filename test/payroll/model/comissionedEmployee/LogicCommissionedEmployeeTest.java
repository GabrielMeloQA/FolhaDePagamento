package payroll.model.comissionedEmployee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.model.commissionedEmployee.LogicCommissionedEmployee;
import payroll.model.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes de Validação - LogicCommissionedEmployee")
class LogicCommissionedEmployeeTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void prepararEntradasConsole(String dadosSimulados) {
        System.setIn(new ByteArrayInputStream(dadosSimulados.getBytes()));
        Utils.sc = new Scanner(System.in);
    }

    @Test
    @DisplayName("Deve validar valor de vendas com sucesso na primeira tentativa")
    void deveValidarVlrSalesComSucessoDePrimeira() {

        prepararEntradasConsole("1500.50\n");

        double resultado = LogicCommissionedEmployee.validateVlrSales();

        assertEquals(1500.50, resultado, 0.001);
    }

    @Test
    @DisplayName("Deve aceitar vírgula como separador decimal e converter corretamente")
    void deveAceitarVirgulaNoValorDeVendas() {

        prepararEntradasConsole("1250,75\n");

        double resultado = LogicCommissionedEmployee.validateVlrSales();

        assertEquals(1250.75, resultado, 0.001, "O método deveria ter substituído a vírgula por ponto.");
    }

    @Test
    @DisplayName("Deve repetir o loop ao digitar valor negativo e só aceitar quando for positivo")
    void deveInsistirNoLoopSeVendasForNegativo() {

        prepararEntradasConsole("-50.00\n300.00\n");

        double resultado = LogicCommissionedEmployee.validateVlrSales();

        assertEquals(300.00, resultado, 0.001);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("O valor não pode ser negativo."));
    }

    @Test
    @DisplayName("Deve tratar letras ou caracteres inválidos no valor de vendas e continuar o loop")
    void deveTratarExceptionNoValorDeVendas() {

        prepararEntradasConsole("texto_invalido\n100.00\n");

        double resultado = LogicCommissionedEmployee.validateVlrSales();

        assertEquals(100.00, resultado, 0.001);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Entrada inválida. Use apenas números."));
    }

    @Test
    @DisplayName("Deve validar a porcentagem de comissão com sucesso")
    void deveValidarPorcentagemComSucesso() {

        prepararEntradasConsole("15\n");

        int resultado = LogicCommissionedEmployee.validatePercentageCommission();

        assertEquals(15, resultado);
    }

    @Test
    @DisplayName("Deve insistir no loop da comissão se o valor for menor ou igual a zero")
    void deveInsistirNoLoopSeComissaoForInvalida() {

        prepararEntradasConsole("0\n-5\n10\n");

        int resultado = LogicCommissionedEmployee.validatePercentageCommission();

        assertEquals(10, resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("A comissão deve ser maior que 0."));
    }

    @Test
    @DisplayName("Deve tratar entrada de número quebrado ou texto na comissão inteira")
    void deveTratarExceptionNaComissao() {

        prepararEntradasConsole("10.5\nabc\n12\n");

        int resultado = LogicCommissionedEmployee.validatePercentageCommission();

        assertEquals(12, resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Entrada inválida. Use números inteiros."));
    }
}