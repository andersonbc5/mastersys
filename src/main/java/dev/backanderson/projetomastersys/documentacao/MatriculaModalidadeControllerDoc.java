package dev.backanderson.projetomastersys.documentacao;


import dev.backanderson.projetomastersys.dto.request.MatriculaModalidadeRequest;
import dev.backanderson.projetomastersys.dto.response.MatriculaModalidadeResponse;
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

import java.util.List;

@Tag(
        name = "Matrículas Modalidades",
        description = "API para gerenciamento das matrículas em modalidades"
)
public interface MatriculaModalidadeControllerDoc {


    @Operation(
            summary = "Adicionar uma nova matrícula em uma modalidade",
            description = "Permite adicionar uma nova matrícula em uma modalidade",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Matrícula em modalidade adicionada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula ou modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Matrícula já possui a modalidade",
                            content = @Content(schema = @Schema(implementation = String.class))
                    )
            }
    )
    MatriculaModalidadeResponse adicionar(
            @Valid
            @RequestBody(
                    description = "Dados da matrícula em modalidade a ser adicionada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MatriculaModalidadeRequest.class),
                            examples = @ExampleObject(
                                    name = "Matrícula Modalidade válida",
                                    value = """
                                            {
                                                "matriculaId": 1,
                                                "modalidadeId": 2,
                                                "graduacaoId": 3,
                                                "planoId": 4
                                            }
                                            """
                            )
                    )
            ) MatriculaModalidadeRequest request);


    @Operation(
            summary = "Listar todas as matrículas em modalidades",
            description = "Permite listar todas as matrículas em modalidades",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrículas em modalidades listadas com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Não há matrículas em modalidades",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }
    )
    Page<MatriculaModalidadeResponse> listar(
            @Parameter(
                    description = "Permite listar Todas as matriculas em modalidades")
            Pageable pageable

    );

    @Operation(
            summary = "Listar matrículas em modalidades por matrícula",
            description = "Permite listar as matrículas em modalidades associadas a uma matrícula específica",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrículas em modalidades listadas com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    List<MatriculaModalidadeResponse> listarPorMatricula(
            @Parameter(
                    description = "ID da matrícula a ser buscada",
                    example = "1",
                    required = true
            ) Long matriculaId
    );


    @Operation(
            summary = "Atualizar graduação de uma matrícula em modalidade",
            description = "Permite atualizar a graduação associada a uma matrícula em modalidade, desde que a nova graduação pertença à mesma modalidade da matrícula",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Graduação atualizada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula em modalidade ou graduação não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Nova graduação não pertence à mesma modalidade da matrícula",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaModalidadeResponse atualizarGraduacao(
            @Parameter(
                    description = "ID da matrícula em modalidade a ser atualizada",
                    example = "1",
                    required = true
            ) Long id,
            @Parameter(
                    description = "ID da nova graduação a ser associada",
                    example = "1",
                    required = true

            ) Long graduacaoId

    );


    @Operation(
            summary = "Atualizar plano de uma matrícula em modalidade",
            description = "Permite atualizar o plano associado a uma matrícula em modalidade desde que o novo plano pertença à mesma modalidade da matrícula",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plano atualizado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula em modalidade ou plano não encontrado",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaModalidadeResponse atualizarPlano(
            @Parameter(
                    description = "ID da matrícula em modalidade a ser atualizada",
                    example = "1",
                    required = true
            ) Long id,
            @Parameter(
                    description = "ID do novo plano a ser associado",
                    example = "1",
                    required = true
            ) Long planoId
    );


    @Operation(
            summary = "Remover uma matrícula em modalidade",
            description = "Permite remover uma matrícula em modalidade do sistema, desde que ela exista e não possua dependências que impeçam sua exclusão",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Matrícula em modalidade removida com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula em modalidade não encontrada",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Matrícula em modalidade não pode ser removida",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void remover(
            @Parameter(
                    description = "ID da matrícula em modalidade a ser removida",
                    example = "1",
                    required = true
            ) Long id
    );
}
