package dev.backanderson.projetomastersys.dto;

import jakarta.validation.constraints.NotNull;

public record MatriculaModalidadeRequest(

        @NotNull(message = "matricula é obrigatório")
        Long matriculaId,

        @NotNull(message = "modalidade é obrigatório")
        Long modalidadeId,

        Long graduacaoId,

        @NotNull(message = "plano é obrigatório")
        Long planoId
) {
}
