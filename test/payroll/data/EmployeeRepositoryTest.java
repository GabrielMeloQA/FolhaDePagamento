package payroll.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.model.standardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.employee.Employee;
import payroll.model.productionEmployee.ProductionEmployee;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - EmployeeRepository")
class EmployeeRepositoryTest {

    @BeforeEach
    void setUp() {
        EmployeeRepository.getEmployeeList().clear();
    }

    @Test
    @DisplayName("Deve cadastrar um funcionário padrão com sucesso na lista")
    void deveCadastrarUmFuncionarioPadraoComSucessoNalista() {

        Employee funcionario = new StandardEmployee(
                "Will", "Escuela", "TC-1", 1000
        );

        EmployeeRepository.registerEmployee(funcionario);

        List<Employee> listaRepositorio = EmployeeRepository.getEmployeeList();

        assertEquals(1, listaRepositorio.size(), "A lista deve conter apenas um usuário cadastrado");
        assertSame(funcionario, listaRepositorio.get(0), "O funcionário na lista deve ser exatamente a mesma instância enviada.");
    }

    @Test
    @DisplayName("Deve cadastrar um funcionário comissionado com sucesso na lista")
    void deveCadastrarUmFuncionarioComissionadoComSucessoNalista() {

        Employee funcionario = new CommissionedEmployee(
                "Phy", "Gonzaga", "TC-2", 1000, 5
        );

        EmployeeRepository.registerEmployee(funcionario);

        List<Employee> listaRepositorio = EmployeeRepository.getEmployeeList();

        assertEquals(1, listaRepositorio.size(), "A lista deve conter apenas um usuário cadastrado");
        assertSame(funcionario, listaRepositorio.get(0), "O funcionário na lista deve ser exatamente a mesma instância enviada.");
    }

    @Test
    @DisplayName("Deve cadastrar um funcionário produção com sucesso na lista")
    void deveCadastrarUmFuncionarioProducaoComSucessoNalista() {

        Employee funcionario = new ProductionEmployee(
                "Ilha", "Jesus", "TC-3", 1000, 5
        );

        EmployeeRepository.registerEmployee(funcionario);

        List<Employee> listaRepositorio = EmployeeRepository.getEmployeeList();

        assertEquals(1, listaRepositorio.size(), "A lista deve conter apenas um usuário cadastrado");
        assertSame(funcionario, listaRepositorio.get(0), "O funcionário na lista deve ser exatamente a mesma instância enviada.");
    }

    @Test
    @DisplayName("Deve retornar True quando mátricula de funcionário for encontrada na lista")
    void deveRetornarTrueAoEncontrarUmaMatriculaValidaNaLista() {

        Employee funcionario = new StandardEmployee(
                "Erica", "Melo", "TC-4", 1
        );

        EmployeeRepository.registerEmployee(funcionario);

        boolean resultado = EmployeeRepository.isRegistered("TC-4");

        assertTrue(resultado, "Deve retornar True para uma mátricula existente na lista.");

    }

    @Test
    @DisplayName("Deve retornar False quando mátricula de funcionário não for econtrada na lista.")
    void deveRetornarFalseQuandoMatriculaNaoForEncontradaNaLista() {

        Employee funcionario = new StandardEmployee(
                "Erica", "Melo", "TC-5", 1
        );

        EmployeeRepository.registerEmployee(funcionario);

        boolean resultado = EmployeeRepository.isRegistered("TC-1");

        assertFalse(resultado, "Deve retornar false para uma mátricula não existente na lista.");
    }

    @Test
    @DisplayName("Deve retornar False ao buscar mátricula de funcionário quando lista estiver vazia")
    void deveRetornarFalseAoBuscarMatriculaDeFuncionarioQuandoListaEstiverVazia() {

        boolean resultado = EmployeeRepository.isRegistered("TC-6");

        assertEquals(0, EmployeeRepository.getEmployeeList().size());
        assertFalse(resultado, "Deve retornar false ao buscar funcionario em uma lista vazia.");
    }

    @Test
    @DisplayName("Deve expor que o repositório não aceita duplicidade de funcionários com a mesma matrícula")
    void deveExporDuplicidadeDeMatricula() {
        Employee f1 = new StandardEmployee(
                "Maria", "Eduarda", "TC-5", 1
        );
        Employee f2 = new StandardEmployee(
                "Maria", "Eduarda", "TC-5", 1
        );

        EmployeeRepository.registerEmployee(f1);

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            EmployeeRepository.registerEmployee(f2);
        });

        assertEquals("Já existe um funcionário cadastrado com a matrícula: TC-5", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve cadastrar um funcionário nulo")
    void naoDeveCadastrarFuncionarioNulo() {

        IllegalArgumentException excecao = assertThrows(IllegalArgumentException.class, () -> {
            EmployeeRepository.registerEmployee(null);
        });

        assertEquals("Não é possível cadastrar um funcionário nulo.", excecao.getMessage());
    }
}