package dev.backanderson.projetomastersys.dto.response;

import dev.backanderson.projetomastersys.domain.FaturaMatricula;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FaturaMatriculaResponse(
        Long id,
        Long matriculaId,
        String nomeAluno,
        String cpfAluno,
        LocalDate dataVencimento,
        BigDecimal valor,
        List<String> modalidades,
        LocalDateTime dataPagamento,
        LocalDate dataCancelamento,
        String status
) {

    public static FaturaMatriculaResponse fromEntity(FaturaMatricula fatura, List<String> modalidades) {
        return new FaturaMatriculaResponse(
                fatura.getId(),
                fatura.getMatricula().getId(),
                fatura.getMatricula().getAluno().getNome(),
                fatura.getMatricula().getAluno().getCpf(),
                fatura.getDataVencimento(),
                fatura.getValor(),
                modalidades,
                fatura.getDataPagamento(),
                fatura.getDataCancelamento(),
                fatura.getStatus().name()
        );
    }
}
