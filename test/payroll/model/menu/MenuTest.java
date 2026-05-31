package payroll.model.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import payroll.model.standardEmployee.InitStandardEmployee;
import payroll.model.commissionedEmployee.InitCommissionedEmployee;
import payroll.model.productionEmployee.InitProductionDataEmployee;
import payroll.model.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes do Fluxo de Navegação - Menu")
class MenuTest {

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
    @DisplayName("Deve exibir erro se o usuário digitar letras no menu de retorno")
    void deveExibirErroSeMenuReceberLetras() {
        prepararEntradasConsole("abc\nY\n");

        Menu.returnToMenu();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Essa opção não é válida. Por favor, digite uma nova opção entre Y / N."));
    }

    @Test
    @DisplayName("Deve acionar cadastro padrão e encerrar o submenu ao digitar 'N'")
    void deveAcionarCadastroPadraoESairDoSubmenuComN() {
        try (MockedStatic<InitStandardEmployee> standardMock = Mockito.mockStatic(InitStandardEmployee.class)) {
            prepararEntradasConsole("N\n");

            Menu.callMenuByType(1);

            standardMock.verify(InitStandardEmployee::registerStandEmployee, Mockito.times(1));

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Deseja realizar nova operação de cadastramento"));
        }
    }

    @Test
    @DisplayName("Deve insistir na pergunta se o usuário não digitar Y ou N no submenu")
    void deveInsistirNaPerguntaSeEscolhaForInvalidaNoSubmenu() {
        try (MockedStatic<InitCommissionedEmployee> commissionedMock = Mockito.mockStatic(InitCommissionedEmployee.class)) {
            prepararEntradasConsole("X\nN\n");

            Menu.callMenuByType(2);

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Não entendi, por favor, insira Y ou N."));
        }
    }

    @Test
    @DisplayName("Deve retornar ao menu principal se o usuário digitar letra ao tentar mudar tipo de colaborador")
    void deveAbortarSubmenuSeDigitarLetraNaNovaEscolha() {
        try (MockedStatic<InitProductionDataEmployee> prodMock = Mockito.mockStatic(InitProductionDataEmployee.class)) {
            prepararEntradasConsole("Y\nabc\n");

            Menu.callMenuByType(3);

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Entrada inválida! Retornando ao menu principal."));
        }
    }

    @Test
    @DisplayName("Deve insistir no loop de retorno ao menu se digitar opção errada e aceitar quando for Y")
    void deveValidarLoopDeRetornoAoMenu() {
        prepararEntradasConsole("Invalido\nY\n");

        Menu.returnToMenu();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Essa opção não é válida. Por favor, digite uma nova opção entre Y / N."));
    }
}