package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;
import dev.backanderson.projetomastersys.dto.MatriculaFiltroRequest;
import dev.backanderson.projetomastersys.dto.MatriculaRequest;
import dev.backanderson.projetomastersys.dto.MatriculaResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import dev.backanderson.projetomastersys.specification.MatriculaSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;

    public MatriculaResponse cadastrar(MatriculaRequest matriculaRequest) {
        if (matriculaRepository.existsByAlunoIdAndStatus(matriculaRequest.alunoId(), StatusMatricula.ATIVA)) {
            throw new RegraDeNegocioException("Aluno já possui uma matrícula ativa");
        }

        Aluno aluno = alunoRepository.findById(matriculaRequest.alunoId()).
                orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com id: " + matriculaRequest.alunoId()));

        Matricula matricula = new Matricula();
        matricula.setAluno(aluno);
        matricula.setDiaVencimento(matriculaRequest.diaVencimento());

        return MatriculaResponse.fromEntitu(matriculaRepository.save(matricula));

    }

    public Page<MatriculaResponse> listar(MatriculaFiltroRequest filtroRequest, Pageable pageable) {
        return matriculaRepository.findAll(MatriculaSpecification.comFiltros(filtroRequest), pageable)
                .map(MatriculaResponse::fromEntitu);
    }


    public MatriculaResponse buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .map(MatriculaResponse::fromEntitu)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula não encontrada com id: " + id));
    }

    public MatriculaResponse encerrarMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula não encontrada com id: " + id));

        if (!matricula.getStatus().name().equals("ATIVA")) {
            throw new RegraDeNegocioException("Apenas matrículas ativas podem ser encerradas");
        }

        matricula.setStatus(StatusMatricula.ENCERRADA);
        matricula.setDataEncerramento(LocalDate.now());

        matriculaRepository.save(matricula);

        return MatriculaResponse.fromEntitu(matriculaRepository.save(matricula));
    }

    public MatriculaResponse cancelarMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula não encontrada com id: " + id));

        if (matricula.getStatus() == StatusMatricula.CANCELADA) {
            throw new RegraDeNegocioException("A matricula já esta cancelada");
        }

        matricula.setStatus(StatusMatricula.CANCELADA);
        matricula.setDataEncerramento(LocalDate.now());

        return MatriculaResponse.fromEntitu(matriculaRepository.save(matricula));
    }


}
