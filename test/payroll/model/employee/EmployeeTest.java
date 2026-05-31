package payroll.model.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - Classe Employee")
class EmployeeTest {

    private Employee criarFuncionarioAnonimo(String name, String surname, String registration) {
        return new Employee(name, surname, registration) {
            @Override
            public double getSalary() {
                return BASE_SALARY;
            }
        };
    }

    @Test
    @DisplayName("Deve inicializar os atributos corretamente através do construtor")
    void deveInicializarAtributosCorretamente() {
        Employee funcionario = criarFuncionarioAnonimo("Thiago", "Silva", "EMP-123");

        assertAll("Validando estado do objeto Employee",
                () -> assertEquals("Thiago", funcionario.getName(), "O nome deveria ser Thiago"),
                () -> assertEquals("Silva", funcionario.getSurname(), "O sobrenome deveria ser Silva"),
                () -> assertEquals("EMP-123", funcionario.getRegistration(), "A matrícula deveria ser EMP-123")
        );
    }

    @Test
    @DisplayName("Deve garantir o valor correto da constante de salário base")
    void deveGarantirValorDoSalarioBase() {
        assertEquals(2000.00, Employee.BASE_SALARY, 0.001, "O salário base da folha deve ser estritamente R$ 2000.00");
    }

    @Test
    @DisplayName("Deve formatar o toString corretamente de acordo com o padrão do sistema")
    void deveFormatarToStringCorretamente() {
        Employee funcionario = criarFuncionarioAnonimo("Mariana", "Souza", "EMP-999");

        String resultadoEsperado = "\n| Matrícula: EMP-999\n| Nome: Mariana Souza";

        String resultadoReal = funcionario.toString();

        assertEquals(resultadoEsperado, resultadoReal, "O formato do toString() está desalinhado com o esperado pelo console.");
    }

    @Test
    @DisplayName("Deve formatar o toString corretamente mesmo se os atributos forem strings vazias")
    void deveFormatarToStringComAtributosVazios() {
        Employee funcionarioVazio = criarFuncionarioAnonimo("", "", "");
        String resultadoEsperado = "\n| Matrícula: \n| Nome:  ";

        assertEquals(resultadoEsperado, funcionarioVazio.toString(),
                "O toString deveria manter o formato padrão mesmo com dados vazios.");
    }

    @Test
    @DisplayName("Deve garantir que o salário base não seja alterado e permaneça em 2000.00")
    void deveGarantirQueOSalarioBaseEImutavel() {
        assertTrue(Employee.BASE_SALARY > 0, "O salário base deve ser um valor positivo.");
        assertEquals(2000.00, Employee.BASE_SALARY, "Alerta: O valor do BASE_SALARY foi modificado no código de produção!");
    }

    @Test
    @DisplayName("Deve retornar null nos getters se o funcionário for instanciado com valores nulos")
    void deveRetornarNullNosGettersSeInstanciadoComNulo() {
        Employee funcionarioNulo = criarFuncionarioAnonimo(null, null, null);

        assertNull(funcionarioNulo.getName(), "O nome deveria ser nulo.");
        assertNull(funcionarioNulo.getSurname(), "O sobrenome deveria ser nulo.");
        assertNull(funcionarioNulo.getRegistration(), "A matrícula deveria ser nula.");
    }
}