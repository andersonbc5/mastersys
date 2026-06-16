package dev.backanderson.projetomastersys.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record MatriculaRequest(

        @NotBlank(message = "CPF do aluno é obrigatório")
        @CPF(message = "CPF do aluno deve ser válido")
        String cpfAluno,

        @NotNull(message = "Dia de vencimento é obrigátorio")
        @Min(value = 1, message = "Dia de vencimento deve ser no mínimo 1")
        @Max(value = 31, message = "Dia de vencimento deve ser no máximo 31")
        Integer diaVencimento
) {
}
