package dev.backanderson.projetomastersys.dto;

import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;

public record MatriculaFiltroRequest(

        String status,
        Integer diaVencimento
) {
}
