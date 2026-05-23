package dev.backanderson.projetomastersys.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MatriculaRequest(

        @NotNull(message = "Campo alunoId é obrigatório")
        Long alunoId,

        @NotNull(message = "Dia de vencimento é obrigátorio")
        @Min(value = 1, message = "Dia de vencimento deve ser no mínimo 1")
        @Max(value = 31, message = "Dia de vencimento deve ser no máximo 31")
        Integer diaVencimento
) {
}
