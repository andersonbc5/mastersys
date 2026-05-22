package dev.backanderson.projetomastersys.controller;

import dev.backanderson.projetomastersys.projection.AlunosPorCidadeProjection;
import dev.backanderson.projetomastersys.projection.FaturamentoMensalProjection;
import dev.backanderson.projetomastersys.projection.FaturasEmAbertoProjection;
import dev.backanderson.projetomastersys.repository.RelatorioAcademiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
@AllArgsConstructor
public class RelatorioAcademinaController {

    private final RelatorioAcademiaRepository relatorioAcademiaRepository;


    @GetMapping("/faturamento-mensal")
    public List<FaturamentoMensalProjection> faturamentoMensalProjections(){
        return relatorioAcademiaRepository.faturamentoMensal();
    }

    @GetMapping("/faturas-em-aberto")
    public List<FaturasEmAbertoProjection> faturasEmAbertoProjections(){
        return relatorioAcademiaRepository.faturasEmAberto();
    }

    @GetMapping("/alunos-por-cidade")
    public List<AlunosPorCidadeProjection> alunosPorCidadeProjections(){
        return relatorioAcademiaRepository.alunosPorCidade();
    }
}
