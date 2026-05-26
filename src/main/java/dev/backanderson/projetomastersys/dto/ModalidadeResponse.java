package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.Modalidade;

public record ModalidadeResponse(
        Long id,
        String nome,
        boolean ativa
) {

    public static ModalidadeResponse fromEntity(Modalidade modalidade) {
        return new ModalidadeResponse(
                modalidade.getId(),
                modalidade.getNome(),
                modalidade.getAtiva());
    }




}
