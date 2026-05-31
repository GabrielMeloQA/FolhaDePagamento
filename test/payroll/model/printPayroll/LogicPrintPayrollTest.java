package payroll.model.printPayroll;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.data.EmployeeRepository;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.employee.Employee;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Regra de Relatório - LogicPrintPayroll")
class LogicPrintPayrollTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private LogicPrintPayroll logicPrint;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        logicPrint = new LogicPrintPayroll();
        EmployeeRepository.getEmployeeList().clear();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("printAll - Deve avisar se o repositório estiver completamente vazio")
    void printAllDeveAvisarSeListaVazia() {
        logicPrint.printAll();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Nenhum colaborador cadastrado."));
    }

    @Test
    @DisplayName("printAll - Deve listar todos os funcionários cadastrados com sucesso")
    void printAllDeveListarFuncionarios() {
        Employee emp1 = new StandardEmployee("Thiago", "Silva", "TC-01", 0.0);
        Employee emp2 = new StandardEmployee("Ana", "Melo", "TC-02", 0.0);
        EmployeeRepository.registerEmployee(emp1);
        EmployeeRepository.registerEmployee(emp2);

        logicPrint.printAll();

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Matrícula: TC-01"));
        assertTrue(printDoConsole.contains("Nome: Thiago Silva"));
        assertTrue(printDoConsole.contains("Matrícula: TC-02"));
        assertTrue(printDoConsole.contains("Nome: Ana Melo"));
    }

    @Test
    @DisplayName("printOne - Deve encontrar e imprimir o funcionário exato por matrícula (Case Insensitive)")
    void printOneDeveEncontrarPorMatricula() {
        Employee emp = new StandardEmployee("Carlos", "Souza", "tc-99", 0.0);
        EmployeeRepository.registerEmployee(emp);

        logicPrint.printOne("TC-99");

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Matrícula: tc-99"));
        assertTrue(printDoConsole.contains("Nome: Carlos Souza"));
        assertFalse(printDoConsole.contains("Colaborador não encontrado."));
    }

    @Test
    @DisplayName("printOne - Deve exibir mensagem de erro se a matrícula não existir")
    void printOneDeveAvisarSeNaoEncontrar() {
        Employee emp = new StandardEmployee("Carlos", "Souza", "TC-99", 0.0);
        EmployeeRepository.registerEmployee(emp);

        logicPrint.printOne("MATRICULA-FANTASMA");

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Colaborador não encontrado."));
    }

    @Test
    @DisplayName("printByFilter - Deve listar apenas os funcionários do tipo filtrado")
    void printByFilterDeveFiltrarCorretamente() {
        Employee padrao = new StandardEmployee("Lucas", "Ferreira", "TC-10", 0.0);
        Employee comissionado = new Employee("Mariana", "Costa", "TC-20") {
            @Override
            public double getSalary() { return 3000.0; }
        };

        EmployeeRepository.registerEmployee(padrao);
        EmployeeRepository.registerEmployee(comissionado);

        logicPrint.printByFilter(StandardEmployee.class);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Matrícula: TC-10"), "Deveria conter o funcionário Padrão.");
        assertFalse(printDoConsole.contains("Matrícula: TC-20"), "NÃO deveria conter o funcionário Comissionado.");
    }

    @Test
    @DisplayName("printByFilter - Deve exibir mensagem específica se nenhum funcionário do tipo for encontrado")
    void printByFilterDeveAvisarSeTipoNaoExistir() {
        Employee padrao = new StandardEmployee("Lucas", "Ferreira", "TC-10", 0.0);
        EmployeeRepository.registerEmployee(padrao);

        logicPrint.printByFilter(CommissionedEmployee.class);

        String printDoConsole = outContent.toString();
        assertTrue(printDoConsole.contains("Nenhum colaborador desse tipo encontrado."));
    }

    @Test
    @DisplayName("emptyArray - Deve retornar true para lista vazia e false para lista povoada")
    void emptyArrayDeveValidarEstadosDaLista() {
        assertTrue(logicPrint.emptyArray(), "Deveria retornar true com repositório recém-limpo.");

        EmployeeRepository.registerEmployee(new StandardEmployee("A", "B", "C", 0.0));

        assertFalse(logicPrint.emptyArray(), "Deveria retornar false após inserção.");
    }
}