package dev.backanderson.projetomastersys.documentacao;


import dev.backanderson.projetomastersys.dto.AlunoFiltroRequest;
import dev.backanderson.projetomastersys.dto.AlunoRequest;
import dev.backanderson.projetomastersys.dto.AlunoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(
        name = "Alunos",
        description = "API para gerenciamento de alunos"
)
public interface AlunoControllerDoc {

    @Operation(
            summary = "Cadastrar um novo aluno",
            description = "Permite cadastrar um novo aluno no sistema",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Aluno cadastrado com sucesso"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados do aluno estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )

            }
    )
    AlunoResponse cadastrar(
            @RequestBody
            @Valid
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do aluno a ser cadastrado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AlunoRequest.class),
                            examples = @ExampleObject(
                                    name = "Aluno valido",
                                    value = """
                                            {
                                                "nome": "Felipe Martins Rocha",
                                                              "cpf": "11144477735",
                                                              "dataNascimento": "1997-11-05",
                                                              "sexo": "M",
                                                              "telefone": "1932789451",
                                                              "celular": "19996587412",
                                                              "email": "felipe.rocha@yahoo.com",
                                                              "observacao": "Aluno Intermediário",
                                                              "endereco": "Avenida Brasil",
                                                              "numero": "450",
                                                              "complemento": "Fundos",
                                                              "bairro": "Jardim Amanda",
                                                              "cidade": "Hortolândia",
                                                              "estado": "SP",
                                                              "cep": "13188020"
                                                              }
                                            """
                            ))
            )
            AlunoRequest alunoRequest);

    @Operation(
            summary = "Listar alunos com filtros e paginação",
            description = "Permite listar os alunos cadastrados no sistema aplicando " +
                    "filtros de busca por nome, e-mail,celular, cidade e estado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso")
            }
    )
    Page<AlunoResponse> listar(
            @Parameter(description = """
                    Filtros opcionais:
                    
                    nome - busca por nome parcial
                    email - busca por e-mail parcial
                    celular - busca por celular
                    cidade - busca por cidade
                    estado - busca por estado
                    """)
            AlunoFiltroRequest filtroRequest,

            @Parameter(description = "Parâmetros de paginação e ordenação")
            Pageable pageable
    );

    @Operation(
            summary = "Buscar aluno por ID",
            description = "Permite buscar um aluno específico pelo seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluno encontrado e retornado com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Aluno não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    AlunoResponse buscarPorId(
            @Parameter(description = "ID do aluno a ser buscado", example = "1", required = true) Long id);


    @Operation(
            summary = "Atualizar um aluno existente",
            description = "Permite atualizar os dados de um aluno existente no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Requisição inválida, dados do aluno estão incorretos",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Aluno não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    AlunoResponse atualizar(
            @Parameter(description = "ID do aluno a ser atualizado", example = "1", required = true) Long id,
            @RequestBody @Valid AlunoRequest request);


    @Operation(
            summary = "Excluir um aluno",
            description = "Permite excluir um aluno do sistema, desde que ele não possua matrículas ativas associadas",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Aluno excluído com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Não é possível excluir um aluno que possui matrículas ativas",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Aluno não encontrado com o ID fornecido",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    void excluir(@Parameter(description = "ID do aluno a ser excluído", example = "1", required = true) Long id);
}
