package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.dto.AlunoFiltroRequest;
import dev.backanderson.projetomastersys.dto.AlunoRequest;
import dev.backanderson.projetomastersys.dto.AlunoResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import dev.backanderson.projetomastersys.specification.AlunoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;
    private final MatriculaRepository matriculaRepository;

    @Transactional
    public AlunoResponse cadastrar(AlunoRequest alunoRequest) {
        if (alunoRequest.email() != null && repository.existsByEmail(alunoRequest.email())) {
            throw new RegraDeNegocioException("Ja existe um aluno com esse email");
        }

        Aluno aluno = alunoRequest.toEntity();
        Aluno alunoSalvo = repository.save(aluno);
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    @Transactional(readOnly = true)
    public Page<AlunoResponse> listar(AlunoFiltroRequest filtroRequest, Pageable pageable){
        return  repository.findAll(AlunoSpecification.comFiltros(filtroRequest), pageable)
                .map(AlunoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public AlunoResponse buscarPorId(Long id){
        return repository.findById(id)
                .map(AlunoResponse::fromEntity)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));
    }

    @Transactional
    public AlunoResponse atualizar(Long id, AlunoRequest request){
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));

        request.preencher(aluno);
        Aluno alunoSalvo = repository.save(aluno);
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com id: " + id));

        if (matriculaRepository.existsByAlunoId(id)) {
            throw new RegraDeNegocioException("Não é possível excluir um aluno que possui matrículas ativas");
        }

        repository.delete(aluno);
    }
}
