package dev.backanderson.projetomastersys.controller;

import dev.backanderson.projetomastersys.dto.GraduacaoRequest;
import dev.backanderson.projetomastersys.dto.GraduacaoResponse;
import dev.backanderson.projetomastersys.service.GraduacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graduacoes")
@RequiredArgsConstructor
public class GraduacaoController {

    private final GraduacaoService graduacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GraduacaoResponse cadastrar(@RequestBody @Valid GraduacaoRequest request) {
        return graduacaoService.cadastrar(request);
    }

    @GetMapping("/{id}")
    public GraduacaoResponse buscarPorId(@PathVariable("id") Long id) {
        return graduacaoService.buscarPorId(id);
    }

    @GetMapping
    public Page<GraduacaoResponse> listarTodas(Pageable pageable) {
        return graduacaoService.listarTodas(pageable);
    }

    @GetMapping("/modalidade/{modalidadeId}")
    public List<GraduacaoResponse> listarPorModalidade(@PathVariable("modalidadeId") Long modalidadeId) {
        return graduacaoService.listarPorModalidade(modalidadeId);
    }

    @PutMapping("/{id}")
    public GraduacaoResponse atualizar(@PathVariable Long id, @RequestBody @Valid GraduacaoRequest request) {
        return graduacaoService.atualizar(id, request);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") Long id) {
        graduacaoService.excluir(id);
    }
}
