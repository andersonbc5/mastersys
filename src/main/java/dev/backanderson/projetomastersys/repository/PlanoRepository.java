package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Plano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository<Plano, Long> {

    boolean existsByModalidadeIdAndNome(Long modalidadeId, String nome);

    boolean existyByModalidade(Long modalidadeId);

    Page<Plano> findByModalidadeId(Long modalidadeId, Pageable pageable);

    Page<Plano> findByAtivo(Boolean ativo, Pageable pageable);


}
