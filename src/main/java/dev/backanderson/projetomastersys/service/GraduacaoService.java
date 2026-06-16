package dev.backanderson.projetomastersys.service;


import dev.backanderson.projetomastersys.domain.Graduacao;
import dev.backanderson.projetomastersys.domain.Modalidade;
import dev.backanderson.projetomastersys.dto.request.GraduacaoRequest;
import dev.backanderson.projetomastersys.dto.response.GraduacaoResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.GraduacaoRepository;
import dev.backanderson.projetomastersys.repository.MatriculaModalidadeRepository;
import dev.backanderson.projetomastersys.repository.ModalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraduacaoService {

    private final GraduacaoRepository graduacaoRepository;
    private final ModalidadeRepository modalidadeRepository;
    private final MatriculaModalidadeRepository matriculaModalidadeRepository;

    @Transactional
    public GraduacaoResponse cadastrar(GraduacaoRequest request) {
        Modalidade modalidade = modalidadeRepository.findById(request.modalidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada"));

        if (graduacaoRepository.existsByModalidadeIdAndNome(request.modalidadeId(), request.nome())) {
            throw new RegraDeNegocioException("Já existe uma graduação com esse nome para a modalidade");
        }

        Graduacao graduacao = new Graduacao();
        graduacao.setNome(request.nome());
        graduacao.setModalidade(modalidade);
        return GraduacaoResponse.fromEntity(graduacaoRepository.save(graduacao));
    }

    @Transactional(readOnly = true)
    public List<GraduacaoResponse> listarPorModalidade(Long modalidadeId) {
        if (!modalidadeRepository.existsById(modalidadeId)) {
            throw new RecursoNaoEncontradoException("Modalidade não encontrada");
        }
        return graduacaoRepository.findByModalidadeId(modalidadeId).stream()
                .map(GraduacaoResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<GraduacaoResponse> listarTodas(Pageable pageable){
        return graduacaoRepository.findAll(pageable).map(GraduacaoResponse::fromEntity);
    }


    @Transactional(readOnly = true)
    public GraduacaoResponse buscarPorId(Long id) {
        return graduacaoRepository.findById(id).map(GraduacaoResponse::fromEntity)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Graduação não encontrada"));
    }


    @Transactional
    public GraduacaoResponse atualizar(Long id, GraduacaoRequest request) {
        Graduacao graduacao = graduacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Graduação não encontrada"));

        Modalidade modalidade = modalidadeRepository.findById(request.modalidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada"));

        if (graduacaoRepository.existsByModalidadeIdAndNome(request.modalidadeId(), request.nome())
                && !graduacao.getId().equals(id)) {
            throw new RegraDeNegocioException("Já existe uma graduação com esse nome para a modalidade");
        }

        graduacao.setNome(request.nome());
        graduacao.setModalidade(modalidade);
        return GraduacaoResponse.fromEntity(graduacaoRepository.save(graduacao));
    }

    @Transactional
    public void excluir(Long id) {
        Graduacao graduacao = graduacaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Graduação não encontrada com id: " + id));

        if (matriculaModalidadeRepository.existsByGraduacaoId(id)) {
            throw new RegraDeNegocioException("Não é possível excluir a graduação pois existem matrículas associadas a ela");
        }

        graduacaoRepository.delete(graduacao);
    }
}
