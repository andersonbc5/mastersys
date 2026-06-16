package dev.backanderson.projetomastersys.dto.response;

import dev.backanderson.projetomastersys.domain.Graduacao;

public record GraduacaoResponse(
        Long id,
        String nome,
        Long modalidadeId,
        String nomeModalidade

) {

    public static GraduacaoResponse fromEntity(Graduacao graduacao) {
        return new GraduacaoResponse(
                graduacao.getId(),
                graduacao.getNome(),
                graduacao.getModalidade().getId(),
                graduacao.getModalidade().getNome()
        );
    }
}
