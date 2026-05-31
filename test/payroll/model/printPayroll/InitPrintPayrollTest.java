package payroll.model.printPayroll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.productionEmployee.ProductionEmployee;
import payroll.model.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de Orquestração - InitPrintPayroll")
class InitPrintPayrollTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @Mock
    private LogicPrintPayroll logicPrintMock;

    @InjectMocks
    private InitPrintPayroll initPrintPayroll;

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
    @DisplayName("Deve retornar ao menu principal imediatamente se a opção escolhida for 6")
    void deveSairDoMenuSeOpcaoForSeis() {
        prepararEntradasConsole("6\n");

        initPrintPayroll.printOption();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Retornando ao menu principal..."));
    }

    @Test
    @DisplayName("Deve chamar o método de imprimir um colaborador específico e depois sair")
    void deveChamarPrintOneEEncerrar() {

        prepararEntradasConsole("1\nTC-01\nN\n");

        initPrintPayroll.printOption();

        verify(logicPrintMock).printOne("TC-01");

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Digite a Mátricula:"));
    }

    @Test
    @DisplayName("Deve chamar a impressão de todos os colaboradores")
    void deveChamarPrintAll() {
        // SEQUÊNCIA: Opção 2 -> Pergunta de nova consulta: "N"
        prepararEntradasConsole("2\nN\n");

        initPrintPayroll.printOption();

        verify(logicPrintMock).printAll();
    }

    @Test
    @DisplayName("Deve chamar o filtro correto para colaboradores de Produção")
    void deveFiltrarPorProducao() {
        prepararEntradasConsole("3\nN\n");

        initPrintPayroll.printOption();

        verify(logicPrintMock).printByFilter(ProductionEmployee.class);
    }

    @Test
    @DisplayName("Deve chamar o filtro correto para colaboradores Comissionados")
    void deveFiltrarPorComissionados() {
        prepararEntradasConsole("4\nN\n");

        initPrintPayroll.printOption();

        verify(logicPrintMock).printByFilter(CommissionedEmployee.class);
    }

    @Test
    @DisplayName("Deve chamar o filtro correto para colaboradores Padrão")
    void deveFiltrarPorPadrao() {
        prepararEntradasConsole("5\nN\n");

        initPrintPayroll.printOption();

        verify(logicPrintMock).printByFilter(StandardEmployee.class);
    }

    @Test
    @DisplayName("Deve exibir mensagem de opção inválida no switch e depois aceitar o encerramento")
    void deveTratarOpcaoInvalidaNoSwitch() {
        prepararEntradasConsole("9\nN\n");

        initPrintPayroll.printOption();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Opção inválida. Tente novamente."));
    }

    @Test
    @DisplayName("Deve insistir no loop de nova consulta se digitar algo diferente de Y ou N")
    void deveInsistirNoLoopDeNovaConsultaSeEntradaInvalida() {
        prepararEntradasConsole("2\nTALVEZ\nN\n");

        initPrintPayroll.printOption();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Opção Inválida! Por favor, digite apenas 'Y' para SIM ou 'N' para NÃO."));
    }
}