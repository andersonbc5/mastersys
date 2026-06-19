package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.documentacao.MatriculaModalidadeControllerDoc;
import dev.backanderson.projetomastersys.dto.request.MatriculaModalidadeRequest;
import dev.backanderson.projetomastersys.dto.response.MatriculaModalidadeResponse;
import dev.backanderson.projetomastersys.service.MatriculaModalidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matricula-modalidades")
@RequiredArgsConstructor
public class MatriculaModalidadeController implements MatriculaModalidadeControllerDoc {

    private final MatriculaModalidadeService matriculaModalidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public MatriculaModalidadeResponse adicionar(@RequestBody @Valid MatriculaModalidadeRequest request) {
        return matriculaModalidadeService.adicionar(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<MatriculaModalidadeResponse> listar(Pageable pageable) {
        return matriculaModalidadeService.listar(pageable);
    }

    @GetMapping("/matricula/{matriculaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<MatriculaModalidadeResponse> listarPorMatricula(@PathVariable("matriculaId") Long matriculaId) {
        return matriculaModalidadeService.listarPorMatricula(matriculaId);
    }


    @PatchMapping("/{id}/graduacao/{graduacaoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public MatriculaModalidadeResponse atualizarGraduacao(
            @PathVariable Long id,
            @PathVariable Long graduacaoId
    ) {
        return matriculaModalidadeService.atualizarGraduacao(id, graduacaoId);

    }

    @PatchMapping("/{id}/plano/{planoId}")
    @PreAuthorize("hasRole('ADMIN')")
    public MatriculaModalidadeResponse atualizarPlano(
            @PathVariable Long id,
            @PathVariable Long planoId) {
        return matriculaModalidadeService.atualizarPlano(id, planoId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void remover(@PathVariable Long id) {
        matriculaModalidadeService.excluir(id);
    }
}
