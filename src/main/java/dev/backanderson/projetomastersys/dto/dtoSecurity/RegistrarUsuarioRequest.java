package dev.backanderson.projetomastersys.dto.dtoSecurity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegistrarUsuarioRequest(
        @NotBlank
        String nome,
        @Email
        @NotBlank(message = "O email é obrigatório ")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String senha,

        String role
) {
}
