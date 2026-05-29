package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.FaturaMatricula;
import dev.backanderson.projetomastersys.domain.enums.StatusFatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface FaturaMatriculaRepository extends JpaRepository<FaturaMatricula, Long> {

    Page<FaturaMatricula> findByMatriculaId(Long matriculaId, Pageable pageable);

    Page<FaturaMatricula> findByStatus(StatusFatura status, Pageable pageable);

    boolean existsByMatriculaIdAndStatusAndDataVencimentoBetween(
            Long matriculaId,
            StatusFatura status,
            LocalDate inicio,
            LocalDate fim
    );
}
