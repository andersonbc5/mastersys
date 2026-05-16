package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByEmail(String email);

}
