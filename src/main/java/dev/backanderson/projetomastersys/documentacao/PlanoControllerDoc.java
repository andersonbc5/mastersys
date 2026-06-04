package dev.backanderson.projetomastersys.documentacao;

import dev.backanderson.projetomastersys.dto.PlanoRequest;
import dev.backanderson.projetomastersys.dto.PlanoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.ErrorResponse;


@Tag(
        name = "Planos",
        description = "API para gerenciamento de planos"
)
public interface PlanoControllerDoc {

    @Operation(
            summary = "Cadastrar um novo plano",
            description = "Permite cadastrar um novo plano no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Plano cadastrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados do plano estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }
    )
    PlanoResponse cadastrar(
            @Valid
            @RequestBody
                    (
                            description = "Dados do plano a ser cadastrado",
                            required = true,
                            content = @Content(schema = @Schema(implementation = PlanoRequest.class),
                                    examples = @ExampleObject(
                                            name = "Plano valido",
                                            value = """
                                                    {
                                                        "nome": "Plano Mensal",
                                                        "valor": 99.90,
                                                        "descricao": "Plano de assinatura mensal com acesso ilimitado."
                                                    }
                                                    """
                                    ))
                    )
            PlanoRequest planoRequest);

    @Operation(
            summary = "Listar planos com paginação",
            description = "Permite listar os planos cadastrados no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de planos retornada com sucesso")
            }
    )
    Page<PlanoResponse> listar(
            @Parameter(
                    description = "ID da modalidade para filtrar os planos (opcional)", example = "1")
            Long modalidadeId,
            @Parameter(
                    description = "Status de ativo para filtrar os planos (opcional)", example = "true")
            Boolean ativo,
            Pageable pageable
    );

    @Operation(
            summary = "Buscar plano por ID",
            description = "Permite buscar um plano específico pelo seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plano encontrado e retornado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Plano não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    PlanoResponse buscarPorId(
            @Parameter(
                    description = "ID do plano a ser buscado", example = "1", required = true)
            Long id);


    @Operation(
            summary = "Atualizar um plano existente",
            description = "Permite atualizar os dados de um plano existente no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados do plano estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Plano não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    PlanoResponse atualizar(
            @Parameter(description = "ID do plano a ser atualizado", example = "1", required = true) Long id,
            @RequestBody @Valid PlanoRequest request);


    @Operation(
            summary = "Excluir um plano",
            description = "Permite excluir um plano do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Plano excluído com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Plano não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void excluir(@Parameter(description = "ID do plano a ser excluído", example = "1", required = true) Long id);
}
