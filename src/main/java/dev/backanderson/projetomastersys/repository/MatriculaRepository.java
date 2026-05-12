package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}
