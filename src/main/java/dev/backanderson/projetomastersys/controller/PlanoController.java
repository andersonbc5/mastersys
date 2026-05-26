package dev.backanderson.projetomastersys.controller;


import dev.backanderson.projetomastersys.dto.PlanoRequest;
import dev.backanderson.projetomastersys.dto.PlanoResponse;
import dev.backanderson.projetomastersys.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanoResponse cadastrar(@RequestBody @Valid PlanoRequest request) {
        return planoService.cadastrar(request);
    }

    @GetMapping("/{id}")
    public PlanoResponse buscarPorId(@PathVariable("id") Long id) {
        return planoService.buscarPorId(id);

    }

    @GetMapping
    public Page<PlanoResponse> listar(
            @RequestParam(required = false) Long modalidadeId,
            @RequestParam(required = false) Boolean ativo,
            Pageable pageable) {
        return planoService.listar(modalidadeId, ativo, pageable);

    }

    @PutMapping("/{id}")
    public PlanoResponse atualizar(@PathVariable Long id, @RequestBody @Valid PlanoRequest request) {
        return planoService.atualizar(id, request);
    }

    @PatchMapping("/{id}/ativar")
    public PlanoResponse ativar(@PathVariable Long id) {
        return planoService.ativar(id);
    }

    @PatchMapping("/{id}/desativar")
    public PlanoResponse desativar(@PathVariable Long id) {
        return planoService.desativar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        planoService.excluir(id);
    }



}
