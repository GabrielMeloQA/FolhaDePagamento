package payroll.model.comissionedEmployee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import payroll.data.EmployeeRepository;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.commissionedEmployee.InitCommissionedEmployee;
import payroll.model.commissionedEmployee.LogicCommissionedEmployee;
import payroll.model.employee.Employee;
import payroll.model.employee.EmployeeConsole;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - InitCommissionedEmployeeTest")
public class InitCommissionedEmployeeTest {

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
    @DisplayName("Deve barrar o fluxo e exibir erro se a matrícula digitada já estiver cadastrada")
    void deveBarrarCadastroSeMatriculaJaExistir() {
        Employee funcionarioExistente = new StandardEmployee("Jorge", "Silva", "TC-01", 0.0);
        EmployeeRepository.registerEmployee(funcionarioExistente);

        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class)) {
            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("TC-01");
            InitCommissionedEmployee.registerCommissionedEmployee();
            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("[ERRO] Esta matrícula já está cadastrada!"),
                    "Deveria ter exibido a mensagem de erro de matrícula duplicada.");
            assertEquals(1, EmployeeRepository.getEmployeeList().size());
        }
    }

    @Test
    @DisplayName("Deve simular entradas do teclado com sucesso e cadastrar o funcionário comissionado")
    void deveCadastrarComissionadoComSucessoViaMocks() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicCommissionedEmployee> logicMock = Mockito.mockStatic(LogicCommissionedEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("TC-02");
            consoleMock.when(EmployeeConsole::name).thenReturn("Gabriel");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Melo");

            logicMock.when(LogicCommissionedEmployee::validateVlrSales).thenReturn(5000.00);
            logicMock.when(LogicCommissionedEmployee::validatePercentageCommission).thenReturn(10);

            InitCommissionedEmployee.registerCommissionedEmployee();

            String printDoConsole = outContent.toString();
            assertTrue(printDoConsole.contains("Colaborador Comissionado cadastrado com sucesso!"),
                    "Deveria exibir a mensagem de sucesso no console.");

            List<Employee> lista = EmployeeRepository.getEmployeeList();
            assertEquals(1, lista.size(), "O funcionário deveria ter sido adicionado ao repositório.");

            Employee cadastrado = lista.get(0);
            assertEquals("Gabriel", cadastrado.getName());
            assertEquals("TC-02", cadastrado.getRegistration());
            assertInstanceOf(CommissionedEmployee.class, cadastrado);
        }
    }

    @Test
    @DisplayName("Deve interromper o fluxo e não cadastrar nada se a validação do valor de vendas estourar uma exceção")
    void deveInterromperFluxoSeValidacaoDeVendasFalhar() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicCommissionedEmployee> logicMock = Mockito.mockStatic(LogicCommissionedEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("TC-03");
            consoleMock.when(EmployeeConsole::name).thenReturn("Bruno");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Souza");

            logicMock.when(LogicCommissionedEmployee::validateVlrSales)
                    .thenThrow(new IllegalArgumentException("Valor inválido capturado no console."));

            assertThrows(IllegalArgumentException.class, () -> {
                InitCommissionedEmployee.registerCommissionedEmployee();
            });

            assertTrue(EmployeeRepository.getEmployeeList().isEmpty(),
                    "O repositório não deveria ter recebido cadastros após uma falha de validação.");
        }
    }

    @Test
    @DisplayName("Deve cadastrar colaborador com sucesso mesmo com vendas e comissão zeradas")
    void deveCadastrarComissionadoComValoresZerados() {
        try (MockedStatic<EmployeeConsole> consoleMock = Mockito.mockStatic(EmployeeConsole.class);
             MockedStatic<LogicCommissionedEmployee> logicMock = Mockito.mockStatic(LogicCommissionedEmployee.class)) {

            consoleMock.when(EmployeeConsole::registrationCode).thenReturn("TC-04");
            consoleMock.when(EmployeeConsole::name).thenReturn("Aline");
            consoleMock.when(EmployeeConsole::surname).thenReturn("Costa");

            logicMock.when(LogicCommissionedEmployee::validateVlrSales).thenReturn(0.0);
            logicMock.when(LogicCommissionedEmployee::validatePercentageCommission).thenReturn(0);

            InitCommissionedEmployee.registerCommissionedEmployee();

            List<Employee> lista = EmployeeRepository.getEmployeeList();
            assertEquals(1, lista.size());

            CommissionedEmployee cadastrado = (CommissionedEmployee) lista.get(0);
            assertEquals(0.0, cadastrado.getSalary() - new StandardEmployee("B", "S", "X", 0).getSalary(), 0.001,
                    "O bônus de comissão calculado deveria ser exatamente zero.");
        }
    }


}
