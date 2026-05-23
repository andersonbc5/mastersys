package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MatriculaRepository extends JpaRepository<Matricula, Long>, JpaSpecificationExecutor<Matricula> {

    boolean existsByAlunoIdAndStatus(Long alunoId, StatusMatricula statusMatricula);
}
