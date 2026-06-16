package dev.backanderson.projetomastersys.dto.dtoSecurity;

public record RegistrarUsuarioResponse(

        Long id,
        String nome,
        String email,
        String role
) {
}
