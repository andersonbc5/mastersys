package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Usuario;
import dev.backanderson.projetomastersys.domain.enums.Role;
import dev.backanderson.projetomastersys.dto.dtoSecurity.RegistrarUsuarioRequest;
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
        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(encoder.encode(request.senha()));

        usuario.setRole(Role.valueOf(request.role().toUpperCase()));

        repository.save(usuario);
    }
}
