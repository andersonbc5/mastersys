package dev.backanderson.projetomastersys.dto.response;

import dev.backanderson.projetomastersys.domain.MatriculaModalidade;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MatriculaModalidadeResponse(
        Long id,
        Long matriculaId,
        String nomeAluno,
        String cpfAluno,
        String nomeModalidade,
        String nomeGraduacao,
        String nomePlano,
        BigDecimal valorPlano,
        LocalDate dataInicio,
        LocalDate dataFim

) {

    public static MatriculaModalidadeResponse fromEntity(MatriculaModalidade matriculaModalidade) {
        return new MatriculaModalidadeResponse(
                matriculaModalidade.getId(),
                matriculaModalidade.getMatricula().getId(),
                matriculaModalidade.getMatricula().getAluno().getNome(),
                matriculaModalidade.getMatricula().getAluno().getCpf(),
                matriculaModalidade.getModalidade().getNome(),
                matriculaModalidade.getGraduacao() != null ? matriculaModalidade.getGraduacao().getNome() : null,
                matriculaModalidade.getPlano().getNome(),
                matriculaModalidade.getPlano().getValorMensal(),
                matriculaModalidade.getDataInicio(),
                matriculaModalidade.getDataFim()
        );

    }
}
