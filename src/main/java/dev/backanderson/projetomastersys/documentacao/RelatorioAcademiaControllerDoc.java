package dev.backanderson.projetomastersys.documentacao;

import dev.backanderson.projetomastersys.projection.AlunosPorCidadeProjection;
import dev.backanderson.projetomastersys.projection.FaturamentoMensalProjection;
import dev.backanderson.projetomastersys.projection.FaturasEmAbertoProjection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Relatórios",
        description = "API para consulta de relatórios gerenciais da academia"
)
public interface RelatorioAcademiaControllerDoc {

    @Operation(
            summary = "Relatório de faturamento mensal",
            description = "Retorna o faturamento consolidado por mês com base nas faturas pagas.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Relatório de faturamento mensal retornado com sucesso"
                    )
            }
    )
    List<FaturamentoMensalProjection> faturamentoMensalProjections();

    @Operation(
            summary = "Relatório de faturas em aberto",
            description = "Retorna todas as faturas que permanecem pendentes de pagamento.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Relatório de faturas em aberto retornado com sucesso"
                    )
            }
    )
    List<FaturasEmAbertoProjection> faturasEmAbertoProjections();

    @Operation(
            summary = "Relatório de alunos por cidade",
            description = "Retorna a quantidade de alunos agrupados por cidade.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Relatório de alunos por cidade retornado com sucesso"
                    )
            }
    )
    List<AlunosPorCidadeProjection> alunosPorCidadeProjections();
}