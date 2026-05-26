package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.Plano;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlanoRequest(

        @NotNull(message = "modalidade é obrigatório")
        Long modalidadeId,

        @NotNull(message = "nome é obrigatório")
        String nome,

        @NotNull(message = "valor mensal é obrigatório")
        @DecimalMin(value = "0.00", message = "Valor mensal deve ser maior que zero")
        BigDecimal valorMensal,

        Boolean ativo

) {

    public void preencher(Plano plano) {
        plano.setNome(nome);
        plano.setValorMensal(valorMensal);

    }
}
