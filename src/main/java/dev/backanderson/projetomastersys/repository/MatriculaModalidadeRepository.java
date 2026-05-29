package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.MatriculaModalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaModalidadeRepository extends JpaRepository<MatriculaModalidade, Long> {

    boolean existsByMatriculaIdAndModalidadeId(Long matriculaId, Long modalidadeId);

    boolean existsByModalidadeId(Long modalidadeId);

    boolean existsByGraduacaoId(Long graduacaoId);

    boolean existsByPlanoId(Long planoId);

    List<MatriculaModalidade> findByMatriculaId(Long matriculaId);


}
