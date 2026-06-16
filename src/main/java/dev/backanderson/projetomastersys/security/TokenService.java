package dev.backanderson.projetomastersys.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import dev.backanderson.projetomastersys.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("projetomastersys")
                .withSubject(usuario.getEmail())
                .withExpiresAt(calcularExpiracao())
                .sign(algorithm);
    }

    public String validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("projetomastersys")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant calcularExpiracao(){
        return Instant.now().plusSeconds(3600);
    }
}
