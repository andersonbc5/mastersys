package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.documentacao.MatriculaControllerDoc;
import dev.backanderson.projetomastersys.dto.response.FaturaMatriculaResponse;
import dev.backanderson.projetomastersys.dto.request.MatriculaFiltroRequest;
import dev.backanderson.projetomastersys.dto.request.MatriculaRequest;
import dev.backanderson.projetomastersys.dto.response.MatriculaResponse;
import dev.backanderson.projetomastersys.service.FaturaMatriculaService;
import dev.backanderson.projetomastersys.service.MatriculaService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController implements MatriculaControllerDoc {

    private final MatriculaService matriculaService;
    private final FaturaMatriculaService faturaMatriculaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public MatriculaResponse cadastrar(@RequestBody @Valid MatriculaRequest matriculaRequest) {
        return matriculaService.cadastrar(matriculaRequest);

    }


    @PostMapping("/{id}/gerar-fatura")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public FaturaMatriculaResponse gerarFatura(@PathVariable Long id) {
        return faturaMatriculaService.gerarFatura(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<MatriculaResponse> listar(MatriculaFiltroRequest filtroRequest, Pageable pageable) {
        return matriculaService.listar(filtroRequest, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public MatriculaResponse buscarPorId(@PathVariable("id") Long id) {
        return matriculaService.buscarPorId(id);
    }

    @PatchMapping("/{id}/encerrar")
    @PreAuthorize("hasRole('ADMIN')")
    public MatriculaResponse encerrarMatricula(@PathVariable("id") Long id) {
        return matriculaService.encerrarMatricula(id);

    }

    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasRole('ADMIN')")
    public MatriculaResponse cancelarMatricula(@PathVariable("id") Long id) {
        return matriculaService.cancelarMatricula(id);
    }
}
