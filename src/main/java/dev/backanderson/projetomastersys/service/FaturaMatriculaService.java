package dev.backanderson.projetomastersys.service;

import dev.backanderson.projetomastersys.domain.FaturaMatricula;
import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.domain.MatriculaModalidade;
import dev.backanderson.projetomastersys.domain.enums.StatusFatura;
import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;
import dev.backanderson.projetomastersys.dto.FaturaMatriculaResponse;
import dev.backanderson.projetomastersys.exception.RecursoNaoEncontradoException;
import dev.backanderson.projetomastersys.exception.RegraDeNegocioException;
import dev.backanderson.projetomastersys.repository.FaturaMatriculaRepository;
import dev.backanderson.projetomastersys.repository.MatriculaModalidadeRepository;
import dev.backanderson.projetomastersys.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaturaMatriculaService {

    private final FaturaMatriculaRepository faturaMatriculaRepository;
    private final MatriculaRepository matriculaRepository;
    private final MatriculaModalidadeRepository matriculaModalidadeRepository;

    private FaturaMatriculaResponse toResponse(FaturaMatricula fatura) {
        List<String> modalidades = matriculaModalidadeRepository
                .findByMatriculaId(fatura.getMatricula().getId())
                .stream()
                .map(mm -> mm.getModalidade().getNome())
                .toList();

        return FaturaMatriculaResponse.fromEntity(fatura, modalidades);
    }

    @Transactional
    public FaturaMatriculaResponse gerarFatura(Long matriculaId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula não encontrada"));

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException("Só é possível gerar faturas para matrículas ativas");
        }

        List<MatriculaModalidade> modalidades = matriculaModalidadeRepository
                .findByMatriculaId(matriculaId);

        if (modalidades.isEmpty()) {
            throw new RegraDeNegocioException("Matrícula não possui modalidades vinculadas");
        }

        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        boolean jaExisteFatura = faturaMatriculaRepository
                .existsByMatriculaIdAndStatusAndDataVencimentoBetween(
                        matriculaId,
                        StatusFatura.ABERTA,
                        inicioMes,
                        fimMes
                );

        if (jaExisteFatura) {
            throw new RegraDeNegocioException("Já existe uma fatura aberta para este mês");
        }

        BigDecimal valorTotal = modalidades.stream()
                .map(mm -> mm.getPlano().getValorMensal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate dataVencimento = hoje.withDayOfMonth(
                Math.min(matricula.getDiaVencimento(), hoje.lengthOfMonth())
        );

        FaturaMatricula fatura = new FaturaMatricula();
        fatura.setMatricula(matricula);
        fatura.setDataVencimento(dataVencimento);
        fatura.setValor(valorTotal);
        fatura.setStatus(StatusFatura.ABERTA);

        return toResponse(faturaMatriculaRepository.save(fatura));
    }

    @Transactional
    public FaturaMatriculaResponse registrarPagamento(Long id) {
        FaturaMatricula fatura = buscarEntidade(id);

        if (fatura.getStatus() != StatusFatura.ABERTA &&
                fatura.getStatus() != StatusFatura.VENCIDA) {
            throw new RegraDeNegocioException("Apenas faturas abertas ou vencidas podem ser pagas");
        }

        fatura.setStatus(StatusFatura.PAGA);
        fatura.setDataPagamento(LocalDateTime.now());

        return toResponse(faturaMatriculaRepository.save(fatura));
    }

    @Transactional
    public FaturaMatriculaResponse cancelar(Long id) {
        FaturaMatricula fatura = buscarEntidade(id);

        if (fatura.getStatus() == StatusFatura.CANCELADA) {
            throw new RegraDeNegocioException("Fatura já está cancelada");
        }

        if (fatura.getStatus() == StatusFatura.PAGA) {
            throw new RegraDeNegocioException("Fatura já paga não pode ser cancelada");
        }

        fatura.setStatus(StatusFatura.CANCELADA);
        fatura.setDataCancelamento(LocalDate.now());

        return toResponse(faturaMatriculaRepository.save(fatura));
    }


    @Transactional(readOnly = true)
    public FaturaMatriculaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }


    @Transactional(readOnly = true)
    public Page<FaturaMatriculaResponse> listarPorMatricula(Long matriculaId, Pageable pageable) {
        if (!matriculaRepository.existsById(matriculaId)) {
            throw new RecursoNaoEncontradoException("Matrícula não encontrada");
        }
        return faturaMatriculaRepository.findByMatriculaId(matriculaId, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<FaturaMatriculaResponse> listarPorStatus(String status, Pageable pageable) {
        if (status == null || status.isBlank()) {
            return faturaMatriculaRepository.findAll(pageable)
                    .map(this::toResponse);
        }
        try {
            StatusFatura statusFatura = StatusFatura.valueOf(status.toUpperCase());
            return faturaMatriculaRepository.findByStatus(statusFatura, pageable)
                    .map(this::toResponse);
        } catch (IllegalArgumentException e) {
            throw new RegraDeNegocioException("Status inválido: " + status);
        }
    }


    private FaturaMatricula buscarEntidade(Long id) {
        return faturaMatriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fatura não encontrada"));
    }
}
