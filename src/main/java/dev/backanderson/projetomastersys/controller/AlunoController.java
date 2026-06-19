package dev.backanderson.projetomastersys.controller;

import dev.backanderson.projetomastersys.documentacao.AlunoControllerDoc;
import dev.backanderson.projetomastersys.dto.request.AlunoFiltroRequest;
import dev.backanderson.projetomastersys.dto.request.AlunoRequest;
import dev.backanderson.projetomastersys.dto.response.AlunoResponse;
import dev.backanderson.projetomastersys.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements AlunoControllerDoc {

    private final AlunoService service;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public AlunoResponse cadastrar(@RequestBody @Valid AlunoRequest alunoRequest) {
        return service.cadastrar(alunoRequest);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<AlunoResponse> listar(AlunoFiltroRequest filtroRequest, Pageable pageable) {
        return service.listar(filtroRequest, pageable);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public AlunoResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public AlunoResponse atualizar(@PathVariable Long id, @RequestBody @Valid AlunoRequest request) {
        return service.atualizar(id, request);

    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void excluir(@PathVariable("id") Long id) {
        service.excluir(id);
    }

}
