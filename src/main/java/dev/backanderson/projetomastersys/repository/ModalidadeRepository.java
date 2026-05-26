package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.Modalidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {

    boolean existsByNome(String nome);

    Page<Modalidade> findByAtiva(Boolean ativa, Pageable pageable);


}
