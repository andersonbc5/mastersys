package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Graduacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GraduacaoRepository extends JpaRepository<Graduacao, Long> {

    boolean existsByModalidadeIdAndNome(Long modalidadeId, String nome);

    List<Graduacao> findByModalidadeId(Long modalidadeId);
}
