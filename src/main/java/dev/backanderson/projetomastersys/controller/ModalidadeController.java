package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.documentacao.ModalidadeControllerDoc;
import dev.backanderson.projetomastersys.dto.ModalidadeRequest;
import dev.backanderson.projetomastersys.dto.ModalidadeResponse;
import dev.backanderson.projetomastersys.service.ModalidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/modalidades")
@RequiredArgsConstructor
public class ModalidadeController implements ModalidadeControllerDoc {

    private final ModalidadeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModalidadeResponse cadastrar(@RequestBody @Valid ModalidadeRequest request) {
        return service.cadastrar(request);
    }

    @GetMapping
    public Page<ModalidadeResponse> listar(
            @RequestParam(required = false) Boolean ativa,
            Pageable pageable) {
        return service.listarAtivas(ativa, pageable);
    }


    @GetMapping("/{id}")
    public ModalidadeResponse buscarPorId(@PathVariable("id") Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ModalidadeResponse atualizar(@PathVariable Long id, @RequestBody ModalidadeRequest request) {
        return service.atualizar(id, request);
    }

    @PatchMapping("/{id}/ativar")
    public ModalidadeResponse ativar(@PathVariable Long id) {
        return service.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public ModalidadeResponse desativar(@PathVariable Long id) {
        return service.desativar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable("id") Long id) {
        service.excluir(id);
    }


}
