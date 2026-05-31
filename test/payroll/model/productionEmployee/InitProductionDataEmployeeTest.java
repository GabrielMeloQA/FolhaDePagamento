package payroll.model.productionEmployee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import payroll.data.EmployeeRepository;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.employee.Employee;
import payroll.model.employee.EmployeeConsole;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - InitProductionDataEmployee")
class InitProductionDataEmployeeTest {

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
    @DisplayName("Deve cadastrar colaborador de produção com sucesso quando os dados forem válidos")
    void deveCadastrarProdEmployeeComSucesso() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicProductionEmployee> logicMock = Mockito.mockStatic(LogicProductionEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("PRD-100");
            consoleMock.when(EmployeeConsole::name).thenReturn("Rodrigo");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Faro");

            logicMock.when(LogicProductionEmployee::qtPieces).thenReturn(150);
            logicMock.when(LogicProductionEmployee::piecesValue).thenReturn(5.50);

            // Executa a operação
            InitProductionDataEmployee.registerProdEmployee();

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Colaborador(a) cadastrado."));

            List<Employee> lista = EmployeeRepository.getEmployeeList();
            assertEquals(1, lista.size(), "O funcionário deveria ter sido adicionado à lista.");

            Employee cadastrado = lista.get(0);
            assertEquals("Rodrigo", cadastrado.getName());
            assertEquals("PRD-100", cadastrado.getRegistration());

            assertInstanceOf(ProductionEmployee.class, cadastrado,
                    "A entidade gerada deve ser uma instância de ProductionEmployee.");
        }
    }

    @Test
    @DisplayName("Deve barrar o cadastro e exibir erro se a matrícula já existir no sistema")
    void deveBarrarCadastroSeMatriculaJaExistir() {
        Employee funcionarioExistente = new StandardEmployee("Jorge", "Silva", "PRD-100", 2000.0);
        EmployeeRepository.registerEmployee(funcionarioExistente);

        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class)) {
            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("PRD-100");

            InitProductionDataEmployee.registerProdEmployee();

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("[ERRO] Esta matrícula já está cadastrada!"));

            assertEquals(1, EmployeeRepository.getEmployeeList().size());
        }
    }

    @Test
    @DisplayName("Deve interromper o fluxo e não salvar nada se a validação de quantidade de peças falhar")
    void deveInterromperFluxoSeValidacaoDePecasLancarExcecao() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicProductionEmployee> logicMock = Mockito.mockStatic(LogicProductionEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("PRD-200");
            consoleMock.when(EmployeeConsole::name).thenReturn("Lucas");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Lima");

            logicMock.when(LogicProductionEmployee::qtPieces)
                    .thenThrow(new IllegalArgumentException("Entrada inválida."));

            assertThrows(IllegalArgumentException.class, () -> {
                InitProductionDataEmployee.registerProdEmployee();
            });

            assertTrue(EmployeeRepository.getEmployeeList().isEmpty(),
                    "O repositório não deveria conter registros se o fluxo quebrou no caminho.");
        }
    }

    @Test
    @DisplayName("Deve cadastrar colaborador com produção zerada e valores mínimos com sucesso")
    void deveCadastrarProdEmployeeComValoresMinimos() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicProductionEmployee> logicMock = Mockito.mockStatic(LogicProductionEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("PRD-MIN");
            consoleMock.when(EmployeeConsole::name).thenReturn("Ana");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Melo");

            logicMock.when(LogicProductionEmployee::qtPieces).thenReturn(0);
            logicMock.when(LogicProductionEmployee::piecesValue).thenReturn(0.01);

            InitProductionDataEmployee.registerProdEmployee();

            List<Employee> lista = EmployeeRepository.getEmployeeList();
            assertEquals(1, lista.size());

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Colaborador(a) cadastrado."));
        }
    }

    @Test
    @DisplayName("Deve repassar os nomes com espaços nas extremidades exatamente como vieram do console")
    void deveSalvarNomesComEspacosNasExtremidades() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicProductionEmployee> logicMock = Mockito.mockStatic(LogicProductionEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("PRD-ESP");
            consoleMock.when(EmployeeConsole::name).thenReturn("  Rodrigo  ");
            consoleMock.when(EmployeeConsole::surname).thenReturn("  Faro  ");

            logicMock.when(LogicProductionEmployee::qtPieces).thenReturn(10);
            logicMock.when(LogicProductionEmployee::piecesValue).thenReturn(2.0);

            InitProductionDataEmployee.registerProdEmployee();

            Employee cadastrado = EmployeeRepository.getEmployeeList().get(0);

            assertEquals("  Rodrigo  ", cadastrado.getName(), "O nome deveria manter os espaços originais vindos do console.");
            assertEquals("  Faro  ", cadastrado.getSurname(), "O sobrenome deveria manter os espaços originais vindos do console.");
        }
    }
}