package dev.backanderson.projetomastersys.documentacao;

import dev.backanderson.projetomastersys.dto.request.GraduacaoRequest;
import dev.backanderson.projetomastersys.dto.response.GraduacaoResponse;
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
        name = "Graduações",
        description = "API para gerenciamento de graduações"
)
public interface GraduacaoControllerDoc {

    @Operation(
            summary = "Cadastrar uma nova graduação",
            description = "Permite cadastrar uma nova graduação no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Graduação cadastrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados da graduação estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }
    )
    GraduacaoResponse cadastrar(
            @Valid
            @RequestBody(
                    description = "Dados da graduação a ser cadastrada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = GraduacaoRequest.class),
                            examples = @ExampleObject(
                                    name = "Graduacao valida",
                                    value = """
                                            {
                                                "nome": "Faixa Branca",
                                                "nivel": 1
                                            }
                                            """
                            ))
            )
            GraduacaoRequest graduacaoRequest);

    @Operation(
            summary = "Listar graduações com paginação",
            description = "Permite listar as graduações cadastradas no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de graduações retornada com sucesso")
            }
    )
    Page<GraduacaoResponse> listarTodas(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            Pageable pageable
    );

    @Operation(
            summary = "Buscar graduação por ID",
            description = "Permite buscar uma graduação específica pelo seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Graduação encontrada e retornada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Graduação não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    GraduacaoResponse buscarPorId(
            @Parameter(
                    description = "ID da graduação a ser buscada", example = "1", required = true)
            Long id);


    @Operation(
            summary = "Atualizar uma graduação existente",
            description = "Permite atualizar os dados de uma graduação existente no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Graduação atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados da graduação estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Graduação não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    GraduacaoResponse atualizar(
            @Parameter(description = "ID da graduação a ser atualizada", example = "1", required = true) Long id,
            @org.springframework.web.bind.annotation.RequestBody @Valid GraduacaoRequest request);


    @Operation(
            summary = "Excluir uma graduação",
            description = "Permite excluir uma graduação do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Graduação excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Graduação não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void excluir(@Parameter(description = "ID da graduação a ser excluída", example = "1", required = true) Long id);
}
