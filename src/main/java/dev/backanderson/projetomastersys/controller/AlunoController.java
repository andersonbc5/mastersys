package dev.backanderson.projetomastersys.controller;

import dev.backanderson.projetomastersys.documentacao.AlunoControllerDoc;
import dev.backanderson.projetomastersys.dto.AlunoFiltroRequest;
import dev.backanderson.projetomastersys.dto.AlunoRequest;
import dev.backanderson.projetomastersys.dto.AlunoResponse;
import dev.backanderson.projetomastersys.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements AlunoControllerDoc {

    private final AlunoService service;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoResponse cadastrar(@RequestBody @Valid AlunoRequest alunoRequest) {
        return service.cadastrar(alunoRequest);
    }

    @GetMapping
    public Page<AlunoResponse> listar(
            @ParameterObject AlunoFiltroRequest filtroRequest,
            @PageableDefault(page = 0, size = 10, sort = "nome") Pageable pageable) {
        return service.listar(filtroRequest, pageable);
    }

    @GetMapping("/{id}")
    public AlunoResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public AlunoResponse atualizar(@PathVariable Long id, @RequestBody @Valid AlunoRequest request) {
        return service.atualizar(id, request);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") Long id) {
        service.excluir(id);
    }

}
