package dev.backanderson.projetomastersys.documentacao;

import dev.backanderson.projetomastersys.dto.response.FaturaMatriculaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.ErrorResponse;

@Tag(
        name = "Faturas",
        description = "API para gerenciamento de faturas das matrículas"
)
public interface FaturaMatriculaControllerDoc {

    @Operation(
            summary = "Gerar fatura para matrícula",
            description = "Gera uma nova fatura para a matrícula informada, caso não exista uma fatura pendente para o período.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Fatura gerada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Não foi possível gerar a fatura",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    FaturaMatriculaResponse gerar(
            @Parameter(
                    description = "ID da matrícula para geração da fatura",
                    example = "1",
                    required = true
            )
            Long matriculaId
    );

    @Operation(
            summary = "Buscar fatura por ID",
            description = "Retorna os dados de uma fatura específica.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fatura encontrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fatura não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    FaturaMatriculaResponse buscarPorId(
            @Parameter(
                    description = "ID da fatura",
                    example = "1",
                    required = true
            )
            Long id
    );

    @Operation(
            summary = "Listar faturas por matrícula",
            description = "Retorna uma lista paginada de faturas associadas a uma matrícula.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de faturas retornada com sucesso"
                    )
            }
    )
    Page<FaturaMatriculaResponse> listarPorMatricula(

            @Parameter(
                    description = "ID da matrícula",
                    example = "1",
                    required = true
            )
            Long matriculaId,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Listar faturas por status",
            description = "Retorna uma lista paginada de faturas filtrando pelo status informado.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de faturas retornada com sucesso"
                    )
            }
    )
    Page<FaturaMatriculaResponse> listarPorStatus(

            @Parameter(
                    description = "Status da fatura para filtro (opcional). Ex: PENDENTE, PAGA, CANCELADA",
                    example = "PENDENTE"
            )
            String status,

            @ParameterObject
            Pageable pageable
    );

    @Operation(
            summary = "Registrar pagamento de fatura",
            description = "Marca uma fatura como paga.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pagamento registrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fatura não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "A fatura não pode ser paga",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    FaturaMatriculaResponse pagar(
            @Parameter(
                    description = "ID da fatura",
                    example = "1",
                    required = true
            )
            Long id
    );

    @Operation(
            summary = "Cancelar fatura",
            description = "Cancela uma fatura pendente.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fatura cancelada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fatura não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "A fatura não pode ser cancelada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    FaturaMatriculaResponse cancelar(
            @Parameter(
                    description = "ID da fatura",
                    example = "1",
                    required = true
            )
            Long id
    );
}