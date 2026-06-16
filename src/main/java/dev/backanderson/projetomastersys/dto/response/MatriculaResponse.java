package dev.backanderson.projetomastersys.dto.response;

import dev.backanderson.projetomastersys.domain.Matricula;

import java.time.LocalDate;

public record MatriculaResponse(
        Long id,
        Long alunoId,
        String cpfAluno,
        String alunoNome,
        LocalDate dataMatricula,
        Integer diaVencimento,
        LocalDate dataEncerramento,
        String status
) {

    public static MatriculaResponse fromEntity(Matricula matricula){
        return new MatriculaResponse(
                matricula.getId(),
                matricula.getAluno().getId(),
                matricula.getAluno().getCpf(),
                matricula.getAluno().getNome(),
                matricula.getDataMatricula(),
                matricula.getDiaVencimento(),
                matricula.getDataEncerramento(),
                matricula.getStatus().name()
        );
    }
}
