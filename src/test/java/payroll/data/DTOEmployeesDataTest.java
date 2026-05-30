package payroll.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payroll.enums.EmployeeType;
import payroll.model.StandardEmployee.StandardEmployee;
import payroll.model.commissionedEmployee.CommissionedEmployee;
import payroll.model.employee.Employee;
import payroll.model.productionEmployee.ProductionEmployee;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes Unitários - DTOEmployeeData")
class DTOEmployeesDataTest {

    @Test
    @DisplayName("Deve converter DTO para entidade StandardEmployee quando o tipo for STANDARD")
    void deveConverterParaStandardEmployee() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "João", "Silva", "ST-1",
                EmployeeType.STANDARD,
                0, 0, 0.0, 0.0, 150.00
        );

        Employee resultado = dto.toEntity();

        assertNotNull(resultado, "A entidade gerada não deverá ser nula.");

        assertInstanceOf(StandardEmployee.class, resultado, "A entidade deve ser uma instância de StardartEmployee");

        assertEquals("João", resultado.getName());
        assertEquals("Silva", resultado.getSurname());
        assertEquals("ST-1", resultado.getRegistration());
    }

    @Test
    @DisplayName("Deve converter DTO para entidade CommissionedEmployee quando o tipo for COMMISSIONED")
    void deveConverterParaComissionedEmployee() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "Henrique", "Pereira", "ST-2",
                EmployeeType.COMMISSIONED,
                0, 5, 1000.00, 0.0, 0
        );

        Employee resultado = dto.toEntity();

        assertNotNull(resultado, "A entidade gerada não deverá ser nula.");

        assertInstanceOf(CommissionedEmployee.class, resultado, "A entidade deve ser uma instância de CommissionedEmployee");

        assertEquals("Henrique", resultado.getName());
        assertEquals("Pereira", resultado.getSurname());
        assertEquals("ST-2", resultado.getRegistration());
    }

    @Test
    @DisplayName("Deve converter DTO para entidade ProductionEmployee quando o tipo for PRODUCTION")
    void deveConverterParaProductionEmployee() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "Maria", "Eduarda", "ST-3",
                EmployeeType.PRODUCTION,
                10, 0, 0, 10.0, 0
        );

        Employee resultado = dto.toEntity();

        assertNotNull(resultado, "A entidade gerada não deverá ser nula.");

        assertInstanceOf(ProductionEmployee.class, resultado, "A entidade deve ser uma instância de ProductionEmployee");

        assertEquals("Maria", resultado.getName());
        assertEquals("Eduarda", resultado.getSurname());
        assertEquals("ST-3", resultado.getRegistration());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso valor extra seja negativo na criação de um Colaborador Padrão")
    void naoDeveCriarColaboradorPadraoComExtraNegativo() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "Jorge", "Gomes", "ST-4",
                EmployeeType.STANDARD,
                0,0,0,0,-1
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O valor extra não pode ser negativo.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso valores sejam negativos na criação de um Colaborador Comissionado " +
            "com valor comissão negativa.")
    void naoDeveCriarColaboradorComissionadoComComissaoNegativo() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "Hilo", "Matos", "ST-5",
                EmployeeType.COMMISSIONED,
                0,-1,0,0,0
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O valor da comissão das vendas não pode ser negativo.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso valores sejam negativos na criação de um Colaborador Comissionado " +
            "com valor das vendas negativa.")
    void naoDeveCriarColaboradorComissionadoComVendasNegativo() {
        DTOEmployeesData dto = new DTOEmployeesData(
                "Matheus", "Felipe", "ST-6",
                EmployeeType.COMMISSIONED,
                0,0,-1,0,0
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O valor das vendas não pode ser negativo.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso valores sejam negativos na criação de um Colaborador De Produção" +
            " com número de peças negativas.")
    void naoDeveCriarColaboradorProducaoComPecasNegativo(){
        DTOEmployeesData dto = new DTOEmployeesData(
                "Humberto", "Liro", "ST-7",
                EmployeeType.PRODUCTION,
                -1,0,0,0,0
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O número de peças não pode ser negativo.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso valores sejam negativos na criação de um Colaborador De Produção" +
            " com número de valor de peças negativas.")
    void naoDeveCriarColaboradorProducaoComValorPecasNegativo(){
        DTOEmployeesData dto = new DTOEmployeesData(
                "Lorena", "Escorona", "ST-8",
                EmployeeType.PRODUCTION,
                0,0,0,-1,0
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O valor das peças não pode ser negativo.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Não deve converter DTO para entidade caso o tipo de funcionário seja nulo")
    void deveLancarExcecaoQuandoTipoForNulo(){
        DTOEmployeesData dto = new DTOEmployeesData(
                "Yan", "Pinto", "ST-9",
                null,
                0,0,0,0,0
        );

        IllegalArgumentException excecaoLancada = assertThrows(IllegalArgumentException.class, () -> {
            dto.toEntity();
        });

        assertEquals("O tipo de funcionário é obrigatório e deve ser preenchido.", excecaoLancada.getMessage());
    }

    @Test
    @DisplayName("Deve garantir a exata integridade e mapeamento dos dados comuns na conversão")
    void deveGarantirIntegridadeDosDadosComuns() {
        String nomeOriginal = "Nome@Test";
        String sobrenomeOriginal = "Sobrenome#Test";
        String matriculaOriginal = "REG-XYZ-999";

        DTOEmployeesData dto = new DTOEmployeesData(
                nomeOriginal, sobrenomeOriginal, matriculaOriginal,
                EmployeeType.STANDARD,
                0, 0, 0.0, 0.0, 0.0
        );

        Employee resultado = dto.toEntity();

        assertAll("Mapeamento de propriedades básicas",
                () -> assertEquals(nomeOriginal, resultado.getName(), "O nome foi alterado ou invertido no mapeamento. "),
                () -> assertEquals(sobrenomeOriginal, resultado.getSurname(), "O sobrenome foi alterado ou invertido no mapeamento"),
                () -> assertEquals(matriculaOriginal, resultado.getRegistration(), "A matrícula foi alterada ou invertida no mapemanto")
        );
    }

}