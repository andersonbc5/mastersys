package dev.backanderson.projetomastersys.specification;

import dev.backanderson.projetomastersys.domain.Matricula;
import dev.backanderson.projetomastersys.domain.enums.StatusMatricula;
import dev.backanderson.projetomastersys.dto.request.MatriculaFiltroRequest;
import org.springframework.data.jpa.domain.Specification;

public class MatriculaSpecification {

    public static Specification<Matricula> comFiltros(MatriculaFiltroRequest filtro) {
        return Specification.
                where(statusIgual(filtro.status()))
                .and(diaVencimentoIgual(filtro.diaVencimento()));

    }

    private static Specification<Matricula> statusIgual(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.isBlank()) {
                return null;
            }
            try {
                StatusMatricula statusEnum = StatusMatricula.valueOf(status.toUpperCase());
                return criteriaBuilder.equal(root.get("status"), statusEnum);
            } catch (IllegalArgumentException e) {
                return null;
            }
        };
    }

    private static Specification<Matricula> diaVencimentoIgual(Integer diaVencimento) {
        return (root, query, criteriaBuilder) -> {
            if (diaVencimento == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("diaVencimento"), diaVencimento);
        };
        }
}
