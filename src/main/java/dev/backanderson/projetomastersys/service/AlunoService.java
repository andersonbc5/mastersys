package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Aluno;
import dev.backanderson.projetomastersys.dto.AlunoRequest;
import dev.backanderson.projetomastersys.dto.AlunoResponse;
import dev.backanderson.projetomastersys.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoResponse cadastrar(AlunoRequest alunoRequest) {
        if (alunoRequest.email() != null && repository.existsByEmail(alunoRequest.email())) {
            throw new RuntimeException("Ja existe um aluno com esse email");
        }

        Aluno aluno = alunoRequest.toEntity();
        Aluno alunoSalvo = repository.save(aluno);
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    public Page<AlunoResponse> listar(Pageable pageable){
        return  repository.findAll(pageable).map(AlunoResponse::fromEntity);
    }

    public AlunoResponse buscarPorId(Long id){
        return repository.findById(id)
                .map(AlunoResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request){
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        request.preencher(aluno);
        Aluno alunoSalvo = repository.save(aluno);
        return AlunoResponse.fromEntity(alunoSalvo);
    }

    public void excluir(Long id){
        repository.deleteById(id);
    }
}
