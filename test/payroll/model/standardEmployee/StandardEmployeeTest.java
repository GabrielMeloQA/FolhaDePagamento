package payroll.model.standardEmployee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Regra de Negócio - StandardEmployee")
class StandardEmployeeTest {

    @Test
    @DisplayName("Deve garantir que o getSalary retorne exatamente o valor estável do salário base")
    void deveRetornarSalarioBaseCorretamente() {
        StandardEmployee empregado = new StandardEmployee(
                "Carlos", "Eduardo", "STD-001", 150.00
        );

        double salarioReal = empregado.getSalary();

        assertEquals(2000.00, salarioReal, 0.001, "O getSalary deveria retornar estritamente o BASE_SALARY (2000.00).");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor extra for negativo")
    void deveLancarExceptionParaExtrasNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new StandardEmployee("Carlos", "Eduardo", "STD-001", -50.0);
        });

        assertEquals("O valor extra não pode ser negativo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve formatar o toString com os dados da superclasse e a soma correta do salário final")
    void deveFormatarToStringCorretamente() {
        StandardEmployee empregado = new StandardEmployee(
                "Bruna", "Marquezine", "STD-002", 350.50
        );

        String resultadoEsperado = "\n| Matrícula: STD-002" +
                "\n| Nome: Bruna Marquezine" +
                "\n| Salário Fixo: 2000,00" +
                "\n| Extras: 350,50" +
                "\n| Salário Final: 2350,50";

        String resultadoReal = empregado.toString();

        assertEquals(resultadoEsperado.replace(".", ","), resultadoReal.replace(".", ","),
                "O formato do relatório toString não corresponde ao padrão esperado pela regra de negócio.");
    }
}