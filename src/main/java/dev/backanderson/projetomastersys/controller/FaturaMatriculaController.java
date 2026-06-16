package dev.backanderson.projetomastersys.controller;

import dev.backanderson.projetomastersys.documentacao.FaturaMatriculaControllerDoc;
import dev.backanderson.projetomastersys.dto.response.FaturaMatriculaResponse;
import dev.backanderson.projetomastersys.service.FaturaMatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faturas")
@RequiredArgsConstructor
public class FaturaMatriculaController implements FaturaMatriculaControllerDoc {

    private final FaturaMatriculaService service;

    @PostMapping("/matriculas/{matriculaId}/gerar")
    @ResponseStatus(HttpStatus.CREATED)
    public FaturaMatriculaResponse gerar(@PathVariable Long matriculaId) {
        return service.gerarFatura(matriculaId);
    }

    @GetMapping("/{id}")
    public FaturaMatriculaResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/matricula/{matriculaId}")
    public Page<FaturaMatriculaResponse> listarPorMatricula(
            @PathVariable Long matriculaId, Pageable pageable) {
        return service.listarPorMatricula(matriculaId, pageable);
    }

    @GetMapping
    public Page<FaturaMatriculaResponse> listarPorStatus(
            @RequestParam(required = false) String status,
            Pageable pageable) {
        return service.listarPorStatus(status, pageable);
    }

    @PatchMapping("/{id}/pagar")
    public FaturaMatriculaResponse pagar(@PathVariable Long id) {
        return service.registrarPagamento(id);
    }

    @PatchMapping("/{id}/cancelar")
    public FaturaMatriculaResponse cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}
