package dev.backanderson.projetomastersys.documentacao;


import dev.backanderson.projetomastersys.dto.response.FaturaMatriculaResponse;
import dev.backanderson.projetomastersys.dto.request.MatriculaFiltroRequest;
import dev.backanderson.projetomastersys.dto.request.MatriculaRequest;
import dev.backanderson.projetomastersys.dto.response.MatriculaResponse;
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
        name = "Matrículas",
        description = "API para gerenciamento de matrículas"
)
public interface MatriculaControllerDoc {

    @Operation(
            summary = "Cadastrar uma nova matrícula",
            description = "Permite cadastrar uma nova matrícula no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Matrícula cadastrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Aluno não encontrado com o CPF fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))

                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Aluno já possui uma matrícula ativa",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados da matrícula estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaResponse cadastrar(
            @Valid
            @RequestBody(
                    description = "Dados da matrícula a ser cadastrada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MatriculaRequest.class),
                            examples = @ExampleObject(
                                    name = "Matrícula válida",
                                    value = """
                                            {
                                                "cpfAluno": "11144477735",
                                                "diaVencimento": 5
                                            }
                                            """
                            )

                    ))
            MatriculaRequest request);


    @Operation(
            summary = "Gerar fatura de matrícula",
            description = "Permite gerar uma fatura de matrícula",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Fatura gerada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Matrícula não possui modalidades vinculadas",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }

    )
    FaturaMatriculaResponse gerarFatura(
            @Parameter(
                    description = "ID da matrícula a ser gerada a fatura",
                    example = "1",
                    required = true
            ) Long id
    );


    @Operation(
            summary = "Listar matrículas",
            description = "Permite listar as matrículas cadastradas no sistema, com suporte a filtros e paginação",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrículas listadas com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, parâmetros de filtro ou paginação estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    Page<MatriculaResponse> listar(
            @Parameter(
                    description = """
                            Filtros opcionais:
                            status = Ativa, Encerrada, Cancelada
                            diaVencimento = Dia do vencimento da matrícula
                            """) MatriculaFiltroRequest filtroRequest,
            @Parameter(
                    description = "Parâmetros de paginação e ordenação") Pageable pageable
    );


    @Operation(
            summary = "Buscar matrícula por ID",
            description = "Permite buscar uma matrícula específica pelo seu ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrícula encontrada e retornada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada com o ID fornecido"
                            , content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaResponse buscarPorId(
            @Parameter(
                    description = "ID da matrícula a ser buscada",
                    example = "1",
                    required = true
            ) Long id
    );


    @Operation(
            summary = "Encerrar uma matrícula",
            description = "Permite encerrar uma matrícula ativa, alterando seu status para Encerrada e registrando a data de encerramento",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrícula encerrada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Somente matrículas ativas podem ser encerradas",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaResponse encerrarMatricula(
            @Parameter(
                    description = "ID da matrícula a ser encerrada",
                    example = "1",
                    required = true

            ) Long id
    );

    @Operation(
            summary = "Cancelar uma matrícula",
            description = "Permite cancelar uma matrícula, alterando seu status para Cancelada e registrando a data de encerramento. " +
                    "Matrículas já canceladas não podem ser canceladas novamente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Matrícula cancelada com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Matrícula não encontrada com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Somente matrículas ativas podem ser canceladas",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    MatriculaResponse cancelarMatricula(
            @Parameter(
                    description = "ID da matrícula a ser cancelada",
                    example = "1",
                    required = true

            ) Long id
    );
}
