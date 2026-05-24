package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.dto.MatriculaModalidadeRequest;
import dev.backanderson.projetomastersys.dto.MatriculaModalidadeResponse;
import dev.backanderson.projetomastersys.service.MatriculaModalidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matricula-modalidades")
@RequiredArgsConstructor
public class MatriculaModalidadeController {

    private final MatriculaModalidadeService matriculaModalidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatriculaModalidadeResponse adicionar(@RequestBody @Valid MatriculaModalidadeRequest request) {
        return matriculaModalidadeService.adicionar(request);
    }

    @GetMapping
    public Page<MatriculaModalidadeResponse> listar(Pageable pageable) {
        return matriculaModalidadeService.listar(pageable);
    }

    @GetMapping("/matricula/{matriculaId}")
    public List<MatriculaModalidadeResponse> listarPorMatricula(@PathVariable("matriculaId") Long matriculaId) {
        return matriculaModalidadeService.listarPorMatricula(matriculaId);
    }


    @PatchMapping("/{id}/graduacao/{graduacaoId}")
    public MatriculaModalidadeResponse atualizarGraduacao(
            @PathVariable Long id,
            @PathVariable Long graduacaoId
    ) {
        return matriculaModalidadeService.atualizarGraducao(id, graduacaoId);

    }

    @PatchMapping("/{id}/plano/{planoId}")
    public MatriculaModalidadeResponse atualizarPlano(
            @PathVariable Long id,
            @PathVariable Long planoId) {
        return matriculaModalidadeService.atualizarPlano(id, planoId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        matriculaModalidadeService.excluir(id);
    }
}
