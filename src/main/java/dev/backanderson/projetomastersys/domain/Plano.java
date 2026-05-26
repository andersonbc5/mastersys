package dev.backanderson.projetomastersys.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "planos")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "valor_mensal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMensal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modalidade_id", nullable = false)
    private Modalidade modalidade;

    @Column(nullable = false)
    private Boolean ativo = true;

}
