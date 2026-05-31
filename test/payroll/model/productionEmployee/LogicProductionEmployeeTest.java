package payroll.model.productionEmployee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.model.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes de Validação - LogicProductionEmployee")
class LogicProductionEmployeeTest {

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
    @DisplayName("qtPieces - Deve aceitar uma quantidade positiva de primeira")
    void qtPiecesDeveAceitarQuantidadePositivaDePrimeira() {
        prepararEntradasConsole("50\n");

        int resultado = LogicProductionEmployee.qtPieces();

        assertEquals(50, resultado);
    }

    @Test
    @DisplayName("qtPieces - Deve aceitar zero como quantidade válida de peças")
    void qtPiecesDeveAceitarZero() {
        prepararEntradasConsole("0\n");

        int resultado = LogicProductionEmployee.qtPieces();

        assertEquals(0, resultado, "Zero deve ser considerado um número válido de produção.");
    }

    @Test
    @DisplayName("qtPieces - Deve rejeitar número negativo e insistir no loop")
    void qtPiecesDeveRejeitarNegativoEInsistirNoLoop() {
        prepararEntradasConsole("-5\n12\n");

        int resultado = LogicProductionEmployee.qtPieces();

        assertEquals(12, resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Este número é inválido, por favor, insira um número válido."));
    }

    @Test
    @DisplayName("piecesValue - Deve aceitar valor decimal com ponto de primeira")
    void piecesValueDeveAceitarValorComPontoDePrimeira() {
        prepararEntradasConsole("4.50\n");

        double resultado = LogicProductionEmployee.piecesValue();

        assertEquals(4.50, resultado, 0.001);
    }

    @Test
    @DisplayName("piecesValue - Deve substituir vírgula por ponto e converter com sucesso")
    void piecesValueDeveConverterVirgulaParaPonto() {
        prepararEntradasConsole("10,75\n");

        double resultado = LogicProductionEmployee.piecesValue();

        assertEquals(10.75, resultado, 0.001, "A vírgula deveria ter sido convertida para ponto.");
    }

    @Test
    @DisplayName("piecesValue - Deve rejeitar valor negativo e insistir no loop")
    void piecesValueDeveRejeitarNegativoEInsistirNoLoop() {
        prepararEntradasConsole("-1.50\n3.25\n");

        double resultado = LogicProductionEmployee.piecesValue();

        assertEquals(3.25, resultado, 0.001);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Este número é inválido, por favor, insira um número válido."));
    }

    @Test
    @DisplayName("piecesValue - Deve tratar NumberFormatException quando receber texto e continuar o loop")
    void piecesValueDeveTratarExceptionDeTextoEInsistirNoLoop() {
        prepararEntradasConsole("texto_errado\n8.90\n");

        double resultado = LogicProductionEmployee.piecesValue();

        assertEquals(8.90, resultado, 0.001);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Entrada inválida, por favor insira um número válido."));
    }
}