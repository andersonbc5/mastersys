package dev.backanderson.projetomastersys.repository;

import dev.backanderson.projetomastersys.domain.FaturaMatricula;
import dev.backanderson.projetomastersys.projection.AlunosPorCidadeProjection;
import dev.backanderson.projetomastersys.projection.FaturamentoMensalProjection;
import dev.backanderson.projetomastersys.projection.FaturasEmAbertoProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatorioAcademiaRepository extends Repository<FaturaMatricula, Long> {

    @Query(
            value = """
                    SELECT 
                            TO_CHAR(data_vencimento, 'YYYY-MM') AS mes,
                            SUM(valor) AS valor
                    FROM faturas_matriculas
                    WHERE status = 'PAGA'
                    GROUP BY TO_CHAR(data_vencimento, 'YYYY-MM')
                    ORDER BY mes
                    """,
            nativeQuery = true
    )
    List<FaturamentoMensalProjection> faturamentoMensal();


    @Query(
            value = """
                    SELECT 
                            cidade,
                            COUNT(*) AS quantidade
                    FROM alunos
                    GROUP BY cidade
                    ORDER BY quantidade DESC
                    """,
            nativeQuery = true
    )
    List<AlunosPorCidadeProjection> alunosPorCidade();


    @Query(
            value = """
                    SELECT 
                            m.id as matriculaId,
                            a.nome as alunoNome,
                            f.data_vencimento as dataVencimento,
                            f.valor as valor
                    FROM faturas_matriculas f
                    JOIN matriculas m ON m.id = f.matricula_id
                    JOIN alunos a ON a.id = m.aluno_id
                    WHERE f.status = 'ABERTA'
                    ORDER BY f.data_vencimento DESC
                    
                   
                    """,
            nativeQuery = true
    )
    List<FaturasEmAbertoProjection> faturasEmAberto();
}
