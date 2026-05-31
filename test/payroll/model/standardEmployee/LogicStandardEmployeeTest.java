package payroll.model.standardEmployee;

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

@DisplayName("Testes de Validação - LogicStandardEmployee")
class LogicStandardEmployeeTest {

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
    @DisplayName("Deve retornar a quantidade de horas extras informada positivamente de primeira")
    void deveAceitarHorasExtrasPositivasDePrimeira() {
        prepararEntradasConsole("15\n");

        int resultado = LogicStandardEmployee.extras();

        assertEquals(15, resultado, "Deveria retornar exatamente o número inserido.");
    }

    @Test
    @DisplayName("Deve aceitar zero como uma quantidade válida de horas extras")
    void deveAceitarZeroComoValorValido() {
        prepararEntradasConsole("0\n");

        int resultado = LogicStandardEmployee.extras();

        assertEquals(0, resultado, "O valor zero deveria ser aceito sem restrições.");
    }

    @Test
    @DisplayName("Deve rejeitar valor inválido negativo, exibir alerta e aceitar o próximo número positivo")
    void deveRejeitarValorNegativoEInsistirNoLoop() {
        prepararEntradasConsole("-3\n8\n");

        int resultado = LogicStandardEmployee.extras();

        assertEquals(8, resultado, "Deveria ter saído do loop capturando o valor correto '8'.");

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Este número é inválido, por favor, insira um número válido."),
                "O console deveria ter exibido a mensagem de alerta sobre o número inválido.");
    }
}