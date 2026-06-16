package dev.backanderson.projetomastersys.security;

import dev.backanderson.projetomastersys.domain.Usuario;
import dev.backanderson.projetomastersys.domain.enums.Role;
import dev.backanderson.projetomastersys.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInicial {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            if (!usuarioRepository.existsByEmail("admin@academia.com")) {
                var admin = Usuario.builder()
                        .nome("admin")
                        .email("admin@academia.com")
                        .senha(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();
                usuarioRepository.save(admin);
            }
        };
    }
}
