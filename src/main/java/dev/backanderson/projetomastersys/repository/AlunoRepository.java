package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {

    boolean existsByEmail(String email);

    Optional<Aluno> findByCpf(String cpf);

}
