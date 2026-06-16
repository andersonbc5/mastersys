package dev.backanderson.projetomastersys.dto.request;

import dev.backanderson.projetomastersys.domain.Modalidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModalidadeRequest(

        @NotBlank(message = "Nome da modalidade é obrigatório")
        @Size(max = 100, message = "Nome da modalidade deve ter no máximo 100 caracteres")
        String nome,

        Boolean ativa
) {

        public void preencher(Modalidade modalidade){
                modalidade.setNome(nome);
                modalidade.setAtiva(ativa);
        }
}
