package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.Modalidade;
import dev.backanderson.projetomastersys.domain.Plano;
import dev.backanderson.projetomastersys.dto.PlanoRequest;
import dev.backanderson.projetomastersys.dto.PlanoResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import dev.backanderson.projetomastersys.repository.ModalidadeRepository;
import dev.backanderson.projetomastersys.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;
    private final ModalidadeRepository modalidadeRepository;
    private final MatriculaRepository matriculaRepository;

    public PlanoResponse cadastrar(PlanoRequest planoRequest) {
        Modalidade modalidade = modalidadeRepository.findById(planoRequest.modalidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada com id: " + planoRequest.modalidadeId()));

        if (planoRepository.existsByModalidadeIdAndNome(planoRequest.modalidadeId(), planoRequest.nome())) {
            throw new RegraDeNegocioException("Já existe um plano com esse nome para a modalidade informada");
        }

        Plano plano = new Plano();
        plano.setModalidade(modalidade);
        plano.setNome(planoRequest.nome());
        plano.setValorMensal(planoRequest.valorMensal());
        plano.setAtivo(planoRequest.ativo() != null ? planoRequest.ativo() : true);

        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public PlanoResponse buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + id));
        return PlanoResponse.fromEntity(plano);
    }

    public Page<PlanoResponse> listar(Long modalidadeId, Boolean ativo, Pageable pageable) {
        if (modalidadeId != null) {
            return planoRepository.findByModalidadeId(modalidadeId, pageable)
                    .map(PlanoResponse::fromEntity);
        }
        if (ativo != null) {
            return planoRepository.findByAtivo(ativo, pageable)
                    .map(PlanoResponse::fromEntity);
        }
        return planoRepository.findAll(pageable)
                .map(PlanoResponse::fromEntity);
    }

    public PlanoResponse atualizar(Long id, PlanoRequest request) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + id));
        Modalidade modalidade = modalidadeRepository.findById(request.modalidadeId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Modalidade não encontrada com id: " + request.modalidadeId()));

        plano.setModalidade(modalidade);
        plano.setNome(request.nome());
        plano.setValorMensal(request.valorMensal());

        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public PlanoResponse ativar(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + id));
        plano.setAtivo(true);
        if (!plano.getAtivo()) {
            throw new RegraDeNegocioException("Plano já está ativo");
        }
        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public PlanoResponse desativar(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + id));
        plano.setAtivo(false);
        if (plano.getAtivo()) {
            throw new RegraDeNegocioException("Plano já está desativado");
        }
        return PlanoResponse.fromEntity(planoRepository.save(plano));
    }

    public void excluir(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Plano não encontrado com id: " + id));

        if (matriculaRepository.existsByPlanoId(plano.getId())) {
            throw new RegraDeNegocioException("Não é possível excluir um plano que está associado a uma matrícula");
        }
        planoRepository.delete(plano);
    }

}
