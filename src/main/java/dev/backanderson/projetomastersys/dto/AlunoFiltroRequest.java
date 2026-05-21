package dev.backanderson.projetomastersys.dto;

public record AlunoFiltroRequest(
        String nome,
        String email,
        String celular,
        String cidade,
        String estado
) {
}
