package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Modalidade;
import dev.backanderson.projetomastersys.dto.request.ModalidadeRequest;
import dev.backanderson.projetomastersys.dto.response.ModalidadeResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.MatriculaModalidadeRepository;
import dev.backanderson.projetomastersys.repository.ModalidadeRepository;
import dev.backanderson.projetomastersys.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ModalidadeService {

    private final ModalidadeRepository repository;
    private final PlanoRepository planoRepository;
    private final MatriculaModalidadeRepository matriculaModalidadeRepository;


    @Transactional
    public ModalidadeResponse cadastrar(ModalidadeRequest request) {
        if (repository.existsByNome(request.nome())) {
            throw new RegraDeNegocioException("Já existe uma modalidade com esse nome");
        }

        Modalidade modalidade = new Modalidade();
        modalidade.setNome(request.nome());
        modalidade.setAtiva(request.ativa());

        return ModalidadeResponse.fromEntity(repository.save(modalidade));
    }

    @Transactional(readOnly = true)
    public ModalidadeResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(ModalidadeResponse::fromEntity).orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada"));
    }

    @Transactional(readOnly = true)
    public Page<ModalidadeResponse> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ModalidadeResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<ModalidadeResponse> listarAtivas(Boolean ativa, Pageable pageable) {
        if (ativa != null) {
            return repository.findByAtiva(ativa, pageable)
                    .map(ModalidadeResponse::fromEntity);
        }
        return repository.findAll(pageable)
                .map(ModalidadeResponse::fromEntity);
    }

    @Transactional
    public ModalidadeResponse atualizar(Long id, ModalidadeRequest request) {
        Modalidade modalidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada com id: " + id));

        if (!modalidade.getNome().equals(request.nome()) && repository.existsByNome(request.nome())) {
            throw new RegraDeNegocioException("Já existe uma modalidade com esse nome");
        }

        request.preencher(modalidade);
        return ModalidadeResponse.fromEntity(repository.save(modalidade));
    }

    @Transactional
    public ModalidadeResponse ativar(Long id) {
        Modalidade modalidade = repository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada com id: " + id));
        if (modalidade.getAtiva()) {
            throw new RegraDeNegocioException("Modalidade já está ativa");
        }
        modalidade.setAtiva(true);
        return ModalidadeResponse.fromEntity(repository.save(modalidade));
    }

    @Transactional
    public ModalidadeResponse desativar(Long id) {
        Modalidade modalidade = repository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada"));

        if (!modalidade.getAtiva()) {
            throw new RegraDeNegocioException("Modalidade já está desativada");

        }

        modalidade.setAtiva(false);
        return ModalidadeResponse.fromEntity(repository.save(modalidade));
    }

    @Transactional
    public void excluir(Long id) {
        Modalidade modalidade = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada com id: " + id));

        if (planoRepository.existsByModalidadeId(id)) {
            throw new RegraDeNegocioException("Não é possível excluir uma modalidade que possui planos associados");
        }

        if (matriculaModalidadeRepository.existsByModalidadeId(id)) {
            throw new RegraDeNegocioException("Não é possível excluir uma modalidade que possui matrículas associadas");
        }
        repository.delete(modalidade);

    }
}
