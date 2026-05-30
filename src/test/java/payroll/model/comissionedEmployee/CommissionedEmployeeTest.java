package payroll.model.comissionedEmployee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.model.commissionedEmployee.CommissionedEmployee;

import static org.junit.jupiter.api.Assertions.*;

class CommissionedEmployeeTest {

    @Test
    @DisplayName("Validação do calculo de comissão com dados válidos.")
    void validacaoDoCalculoDeComissaoComDadosValidos(){

        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-01", 5000.00, 10
        );

        double salarioBaseSistema = new CommissionedEmployee(
                "Base", "Salary", "BS-1", 0.0, 0
        ).getSalary();

        double valorEsperado = salarioBaseSistema + 500.00;
        double salarioCalculado = emp.getSalary();

        assertEquals(valorEsperado, salarioCalculado, 0.001, "O cálculo do salário com 10% de comissão está incorreto.");
    }

    @Test
    @DisplayName("Deve retornar apenas o salário base se o valor da venda for zero.")
    void deveRetornarApenasOSalarioBaseSeOValorDaVendaForZero(){
        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-02", 0.00, 10
        );

        double salarioBaseSistema = new CommissionedEmployee(
                "Base", "Salary", "BS-1", 0.0, 0
        ).getSalary();

        assertEquals(salarioBaseSistema, emp.getSalary(), 0.001, "Vendas zeradas deveriam resultar apenas no salário base.");
    }

    @Test
    @DisplayName("Deve retornar apenas o salário base se o valor da comissão for zero.")
    void deveRetornarApenasOSalarioBaseSeOValorDaComissaoForZero(){
        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-03", 1000.00, 0
        );

        double salarioBaseSistema = new CommissionedEmployee(
                "Base", "Salary", "BS-1", 0.0, 0
        ).getSalary();

        assertEquals(salarioBaseSistema, emp.getSalary(), 0.001, "Comissão zerada deveria resultar apenas no salário base.");
    }

    @Test
    @DisplayName("Deve calcular corretamente comissões que geram valores quebrados/centavos")
    void deveCalcularComissaoComValoresQuebrados() {
        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-04", 1234.56, 7
        );

        double salarioBaseDoSistema = new CommissionedEmployee("Base", "Teste", "B-1", 0, 0).getSalary();
        double valorEsperado = salarioBaseDoSistema + 86.4192;

        double salarioCalculado = emp.getSalary();

        assertEquals(valorEsperado, salarioCalculado, 0.001, "O cálculo de precisão de centavos falhou.");
    }

    @Test
    @DisplayName("Deve formatar as informações do holerite corretamente no método toString")
    void deveFormatarToStringCorretamente() {
        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-05", 1000.00, 5
        );

        String holerite = emp.toString();

        assertTrue(holerite.contains("Comissão: 50,00") || holerite.contains("Comissão: 50.00"),
                "O formato da comissão no toString deveria exibir 50.00 / 50,00.");
        assertTrue(holerite.contains("Salário Final:"),
                "O toString deve conter a linha descritiva do Salário Final.");
    }

    @Test
    @DisplayName("Deve aceitar o valor da comissão quando for 20%")
    void naoDeveAceitarOValorDaComissaoQuandoForVintePorcento(){
        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-06", 1000.00, 20
        );

        double salarioBaseDoSistema = new CommissionedEmployee("Base", "Teste", "B-1", 0, 0).getSalary();
        double valorEsperado = salarioBaseDoSistema + 200.00;

        assertEquals(valorEsperado, emp.getSalary());
    }

    @Test
    @DisplayName("Não deve aceitar o valor da comissão acima dos 20%.")
    void naoDeveAceitarOValorDaComissaoAcimaDosVintePorcento(){

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            new CommissionedEmployee("João", "Silva", "TC-07", 1000.00, 21);
        });

        assertEquals("O valor da comissão das vendas não pode ultrapassar 20%", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Deve calcular o salário com precisão matemática mesmo para grandes volumes de vendas")
    void deveCalcularGrandesVolumesDeVendas() {

        CommissionedEmployee emp = new CommissionedEmployee(
                "João", "Silva", "TC-MEGA", 1500000.00, 12
        );

        double salarioBaseDoSistema = new CommissionedEmployee("Base", "Teste", "B-1", 0, 0).getSalary();
        double valorEsperado = salarioBaseDoSistema + 180000.00;

        assertEquals(valorEsperado, emp.getSalary(), 0.001, "O cálculo para grandes volumes de vendas falhou.");
    }


}