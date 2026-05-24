package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.*;
import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;
import dev.backanderson.projetomastersys.dto.MatriculaModalidadeRequest;
import dev.backanderson.projetomastersys.dto.MatriculaModalidadeResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaModalidadeService {

    private final MatriculaModalidadeRepository matriculaModalidadeRepository;
    private final MatriculaRepository matriculaRepository;
    private final ModalidadeRepository modalidadeRepository;
    private final GraduacaoRepository graduacaoRepository;
    private final PlanoRepository planoRepository;


    public MatriculaModalidadeResponse adicionar(MatriculaModalidadeRequest request) {
        Matricula matricula = matriculaRepository.findById(request.matriculaId())
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada com id: " + request.matriculaId()));

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException("Matrícula deve estar ativa para adicionar uma modalidade");
        }

        if (matriculaModalidadeRepository.existsByMatriculaIdAndModalidadeId(request.matriculaId(), request.modalidadeId())) {
            throw new RegraDeNegocioException("Aluno já esta matriculado nessa modalidade");

        }

        Modalidade modalidade = modalidadeRepository.findById(request.modalidadeId())
                .orElseThrow(() -> new RuntimeException("Modalidade não encontrada com id: " + request.modalidadeId()));

        if (!modalidade.isAtiva()) {
            throw new RegraDeNegocioException("Modalidade deve estar ativa para ser adicionada a matrícula");
        }

        Graduacao graduacao = null;
        if (request.graduacaoId() != null) {
            graduacao = graduacaoRepository.findById(request.graduacaoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Graduação não encontrada"));

            if (!graduacao.getModalidade().getId().equals(request.modalidadeId())) {
                throw new RegraDeNegocioException("Graduação não pertence à modalidade informada");
            }
        }

        Plano plano = planoRepository.findById(request.planoId()).orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado"));

        if (!plano.getModalidade().getId().equals(request.modalidadeId())) {
            throw new RegraDeNegocioException("Plano não pertence à modalidade informada");
        }

        if (!plano.isAtivo()) {
            throw new RegraDeNegocioException("Plano deve estar ativo para ser adicionado à matrícula");
        }

        MatriculaModalidade matriculaModalidade = new MatriculaModalidade();
        matriculaModalidade.setMatricula(matricula);
        matriculaModalidade.setModalidade(modalidade);
        matriculaModalidade.setGraduacao(graduacao);
        matriculaModalidade.setPlano(plano);

        return MatriculaModalidadeResponse.fromEntity(matriculaModalidadeRepository.save(matriculaModalidade));
    }

    public List<MatriculaModalidadeResponse> listarPorMatricula(Long matriculaId) {
        if (!matriculaRepository.existsById(matriculaId)) {
            throw new RecursoNaoEncontradoException("Matrícula não encontrada com id: " + matriculaId);
        }
        return matriculaModalidadeRepository.findByMatriculaId(matriculaId)
                .stream()
                .map(MatriculaModalidadeResponse::fromEntity)
                .toList();
    }

    public MatriculaModalidadeResponse atualizarGraducao(Long id, Long graduacaoId) {
        MatriculaModalidade matriculaModalidade = matriculaModalidadeRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("MatriculaModalidade não encontrada com id: " + id));

        Graduacao graduacao = graduacaoRepository.findById(graduacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Graduação não encontrada com id: " + graduacaoId));

        if (!graduacao.getModalidade().getId().equals(matriculaModalidade.getModalidade().getId())) {
            throw new RegraDeNegocioException("Graduação não pertence à modalidade informada");

        }

        matriculaModalidade.setGraduacao(graduacao);
        return MatriculaModalidadeResponse.fromEntity(matriculaModalidadeRepository.save(matriculaModalidade));

    }

    public MatriculaModalidadeResponse atualizarPlano(Long id, long planoId) {
        MatriculaModalidade matriculaModalidade = matriculaModalidadeRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("MatriculaModalidade não encontrada com id: " + id));

        Plano plano = planoRepository.findById(planoId).
                orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + planoId));

        if (!plano.getModalidade().getId().equals(matriculaModalidade.getModalidade().getId())) {
            throw new RegraDeNegocioException("Plano não pertence à modalidade informada");

        }

        if (!plano.isAtivo()) {
            throw new RegraDeNegocioException("Plano deve estar ativo para ser adicionado à matrícula");

        }

        matriculaModalidade.setPlano(plano);
        return MatriculaModalidadeResponse.fromEntity(matriculaModalidadeRepository.save(matriculaModalidade));
    }

    public void excluir(Long id) {
        MatriculaModalidade matriculaModalidade = matriculaModalidadeRepository.findById(id).
                orElseThrow(() -> new RecursoNaoEncontradoException("MatriculaModalidade não encontrada com id: " + id));

        if (matriculaModalidade.getMatricula().getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException("Apenas modalidades de matrículas ativas podem ser excluídas");
        }

        matriculaModalidadeRepository.delete(matriculaModalidade);
    }

    public Page<MatriculaModalidadeResponse> listar(Pageable pageable) {
        return matriculaModalidadeRepository.findAll(pageable)
                .map(MatriculaModalidadeResponse::fromEntity);
    }

    public List<MatriculaModalidadeResponse> listarTodos() {
        return matriculaModalidadeRepository.findAll()
                .stream()
                .map(MatriculaModalidadeResponse::fromEntity).toList();
    }
}
