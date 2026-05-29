package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.dto.FaturaMatriculaResponse;
import dev.backanderson.projetomastersys.dto.MatriculaFiltroRequest;
import dev.backanderson.projetomastersys.dto.MatriculaRequest;
import dev.backanderson.projetomastersys.dto.MatriculaResponse;
import dev.backanderson.projetomastersys.service.FaturaMatriculaService;
import dev.backanderson.projetomastersys.service.MatriculaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas")
@AllArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final FaturaMatriculaService faturaMatriculaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaResponse cadastrar(@RequestBody @Valid MatriculaRequest matriculaRequest) {
        return matriculaService.cadastrar(matriculaRequest);

    }

    @PostMapping("/{id}/gerar-fatura")
    @ResponseStatus(HttpStatus.CREATED)
    public FaturaMatriculaResponse gerarFatura(@PathVariable Long id) {
        return faturaMatriculaService.gerarFatura(id);
    }

    @GetMapping
    public Page<MatriculaResponse> listar(MatriculaFiltroRequest filtroRequest, Pageable pageable) {
        return matriculaService.listar(filtroRequest, pageable);
    }

    @GetMapping("/{id}")
    public MatriculaResponse buscarPorId(@PathVariable("id") Long id) {
        return matriculaService.buscarPorId(id);
    }

    @PatchMapping("/{id}/encerrar")
    public MatriculaResponse encerrarMatricula(@PathVariable("id") Long id) {
        return matriculaService.encerrarMatricula(id);

    }

    @PatchMapping("/{id}/cancelar")
    public MatriculaResponse cancelarMatricula(@PathVariable("id") Long id) {
        return matriculaService.cancelarMatricula(id);
    }
}
