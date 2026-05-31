package payroll.model.productionEmployee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Regra de Negócio - ProductionEmployee")
class ProductionEmployeeTest {

    @Test
    @DisplayName("Deve calcular o salário final somando o salário base e o bônus de produção")
    void deveCalcularSalarioCorretamente() {
        ProductionEmployee empregado = new ProductionEmployee(
                "Lucas", "Silva", "PRD-001", 100, 5.50
        );

        double salarioFinal = empregado.getSalary();

        assertEquals(2550.00, salarioFinal, 0.001, "O cálculo do salário final está incorreto.");
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se a quantidade de peças for negativa")
    void deveLancarExceptionParaQuantidadeNegativa() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductionEmployee("Lucas", "Silva", "PRD-001", -1, 5.50);
        });

        assertEquals("O número de peças não pode ser negativo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException se o valor das peças for negativo")
    void deveLancarExceptionParaValorDePecaNegativo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new ProductionEmployee("Lucas", "Silva", "PRD-001", 10, -2.50);
        });

        assertEquals("O valor das peças não pode ser negativo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve formatar o toString com os dados da superclasse e os valores financeiros formatados")
    void deveFormatarToStringCorretamente() {
        ProductionEmployee empregado = new ProductionEmployee(
                "Ana", "Melo", "PRD-002", 10, 10.00
        );

        String resultadoEsperado = "\n| Matrícula: PRD-002" +
                "\n| Nome: Ana Melo" +
                "\n| Salário Fixo: 2000,00" +
                "\n| Produtividade: 100,00" +
                "\n| Salário Final: R$ 2100,00";

        String resultadoReal = empregado.toString();

        assertEquals(resultadoEsperado.replace(".", ","), resultadoReal.replace(".", ","),
                "O formato do relatório toString não corresponde ao padrão esperado.");
    }
}