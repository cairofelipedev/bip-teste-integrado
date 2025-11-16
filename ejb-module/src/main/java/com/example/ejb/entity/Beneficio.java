package com.example.ejb.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Beneficio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private BigDecimal valor;

    private Boolean ativo;

    @Version
    private Long version;
}
