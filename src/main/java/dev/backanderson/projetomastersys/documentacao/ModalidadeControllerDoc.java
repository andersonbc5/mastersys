package dev.backanderson.projetomastersys.documentacao;

import dev.backanderson.projetomastersys.dto.request.ModalidadeRequest;
import dev.backanderson.projetomastersys.dto.response.ModalidadeResponse;
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
        name = "Modalidades",
        description = "API para gerenciamento de modalidades"
)
public interface ModalidadeControllerDoc {

    @Operation(
            summary = "Cadastrar uma nova modalidade",
            description = "Permite cadastrar uma nova modalidade no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Modalidade cadastrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados da modalidade estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }
    )
    ModalidadeResponse cadastrar(
            @Valid
            @RequestBody(
                    description = "Dados da modalidade a ser cadastrada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ModalidadeRequest.class),
                            examples = @ExampleObject(
                                    name = "Modalidade valida",
                                    value = """
                                            {
                                                "nome": "Jiu-Jitsu",
                                                "descricao": "Arte marcial japonesa focada em luta de chão."
                                            }
                                            """
                            ))
            )
            ModalidadeRequest modalidadeRequest);

    @Operation(
            summary = "Listar modalidades com paginação",
            description = "Permite listar as modalidades cadastradas no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de modalidades retornada com sucesso")
            }
    )
    Page<ModalidadeResponse> listar(
            @Parameter(
                    description = "Status de ativa para filtrar as modalidades (opcional)",
                    example = "true")
            Boolean ativa, Pageable pageable
    );

    @Operation(
            summary = "Buscar modalidade por ID",
            description = "Permite buscar uma modalidade específica pelo seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Modalidade encontrada e retornada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Modalidade não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    ModalidadeResponse buscarPorId(
            @Parameter(
                    description = "ID da modalidade a ser buscada", example = "1", required = true)
            Long id);


    @Operation(
            summary = "Atualizar uma modalidade existente",
            description = "Permite atualizar os dados de uma modalidade existente no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Modalidade atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados da modalidade estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Modalidade não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    ModalidadeResponse atualizar(
            @Parameter(description = "ID da modalidade a ser atualizada", example = "1", required = true) Long id,
            @org.springframework.web.bind.annotation.RequestBody @Valid ModalidadeRequest request);


    @Operation(
            summary = "Excluir uma modalidade",
            description = "Permite excluir uma modalidade do sistema",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Modalidade excluída com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Modalidade não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void excluir(@Parameter(description = "ID da modalidade a ser excluída", example = "1", required = true) Long id);
}
