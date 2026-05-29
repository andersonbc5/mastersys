package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.Graduacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GraduacaoRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
        String nome,

        @NotNull(message = "modalidade é obrigatório")
        Long modalidadeId
) {


}


