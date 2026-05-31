package payroll.model.employee;

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

@DisplayName("Testes de Console - EmployeeConsole")
class EmployeeConsoleTest {

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
    @DisplayName("Deve capturar a matrícula com sucesso na primeira tentativa")
    void deveCapturarMatriculaComSucessoDePrimeira() {
        prepararEntradasConsole("MAT-123\n");

        String resultado = EmployeeConsole.registrationCode();

        assertEquals("MAT-123", resultado, "Deveria retornar a matrícula exatamente como digitada.");
    }

    @Test
    @DisplayName("Deve insistir no loop se o usuário enviar a matrícula vazia ou apenas com espaços")
    void deveInsistirNoLoopSeMatriculaForVazia() {
        prepararEntradasConsole("\n   \nMAT-999\n");

        String resultado = EmployeeConsole.registrationCode();

        assertEquals("MAT-999", resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Por favor, insira a matrícula do colaborador(a)"),
                "Deveria ter alertado o usuário sobre o campo obrigatório.");
    }

    @Test
    @DisplayName("Deve capturar o nome com sucesso na primeira tentativa")
    void deveCapturarNomeComSucessoDePrimeira() {
        prepararEntradasConsole("Gabriel\n");

        String resultado = EmployeeConsole.name();

        assertEquals("Gabriel", resultado);
    }

    @Test
    @DisplayName("Deve insistir no loop se o usuário enviar o nome em branco")
    void deveInsistirNoLoopSeNomeForVazio() {
        prepararEntradasConsole("\nAna\n");

        String resultado = EmployeeConsole.name();

        assertEquals("Ana", resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Por favor, insira o nome do colaborador(a)"));
    }

    @Test
    @DisplayName("Deve capturar o sobrenome com sucesso na primeira tentativa")
    void deveCapturarSobrenomeComSucessoDePrimeira() {
        prepararEntradasConsole("Melo\n");

        String resultado = EmployeeConsole.surname();

        assertEquals("Melo", resultado);
    }

    @Test
    @DisplayName("Deve insistir no loop se o usuário enviar o sobrenome em branco")
    void deveInsistirNoLoopSeSobrenomeForVazio() {
        prepararEntradasConsole("\nSilva\n");

        String resultado = EmployeeConsole.surname();

        assertEquals("Silva", resultado);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Por favor, insira o sobrenome do colaborador(a)"));
    }
}