package payroll.model.standardEmployee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import payroll.data.EmployeeRepository;
import payroll.model.employee.Employee;
import payroll.model.employee.EmployeeConsole;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - InitStandardEmployee")
class InitStandardEmployeeTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        EmployeeRepository.getEmployeeList().clear();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Deve cadastrar colaborador padrão com sucesso se os dados forem válidos")
    void deveCadastrarStandardEmployeeComSucesso() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicStandardEmployee> logicMock = Mockito.mockStatic(LogicStandardEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("STD-001");
            consoleMock.when(EmployeeConsole::name).thenReturn("Bruno");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Gagliasso");

            logicMock.when(LogicStandardEmployee::extras).thenReturn(10);

            InitStandardEmployee.registerStandEmployee();

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Colaborador(a) cadastrado."));

            List<Employee> lista = EmployeeRepository.getEmployeeList();
            assertEquals(1, lista.size(), "O funcionário deveria ter sido adicionado à lista.");

            Employee cadastrado = lista.get(0);
            assertEquals("Bruno", cadastrado.getName());
            assertEquals("STD-001", cadastrado.getRegistration());

            assertInstanceOf(StandardEmployee.class, cadastrado,
                    "A entidade deve ser uma instância de StandardEmployee.");
        }
    }

    @Test
    @DisplayName("Deve barrar o cadastro e exibir erro se a matrícula já estiver em uso")
    void deveBarrarCadastroSeMatriculaJaExistir() {
        StandardEmployee funcionarioExistente = new StandardEmployee("Claudio", "Azevedo", "STD-001", 0.0);
        EmployeeRepository.registerEmployee(funcionarioExistente);

        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class)) {
            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("STD-001");

            InitStandardEmployee.registerStandEmployee();

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("[ERRO] Esta matrícula já está cadastrada!"),
                    "Deveria ter exibido o alerta de erro.");

            assertEquals(1, EmployeeRepository.getEmployeeList().size());
        }
    }

    @Test
    @DisplayName("Deve abortar a transação e não salvar se a captura de horas extras falhar")
    void deveAbortarFluxoSeExtrasLancarExcecao() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicStandardEmployee> logicMock = Mockito.mockStatic(LogicStandardEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("STD-999");
            consoleMock.when(EmployeeConsole::name).thenReturn("Letícia");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Spiller");

            logicMock.when(LogicStandardEmployee::extras)
                    .thenThrow(new IllegalArgumentException("Valor de horas extras inválido."));

            assertThrows(IllegalArgumentException.class, () -> {
                InitStandardEmployee.registerStandEmployee();
            });

            assertTrue(EmployeeRepository.getEmployeeList().isEmpty(),
                    "O repositório deveria continuar vazio após a falha.");
        }
    }
}