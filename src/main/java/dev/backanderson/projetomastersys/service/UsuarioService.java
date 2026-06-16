package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Usuario;
import dev.backanderson.projetomastersys.domain.enums.Role;
import dev.backanderson.projetomastersys.dto.dtoSecurity.RegistrarUsuarioRequest;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void registrar(RegistrarUsuarioRequest request){

        if (repository.existsByEmail(request.email())) {
            throw new RegraDeNegocioException("Email já registrado");
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(encoder.encode(request.senha()))
                .role(Role.valueOf(request.role().toUpperCase()))
                .build();

        repository.save(usuario);
    }

}
