package dev.backanderson.projetomastersys.dto.response;

import dev.backanderson.projetomastersys.domain.Plano;

import java.math.BigDecimal;

public record PlanoResponse(
        Long id,
        Long modalidadeId,
        String nomeModalidade,
        String nome,
        BigDecimal valorMensal,
        Boolean ativo
) {

    public static PlanoResponse fromEntity(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getModalidade().getId(),
                plano.getModalidade().getNome(),
                plano.getNome(),
                plano.getValorMensal(),
                plano.getAtivo()
        );
        }
}
