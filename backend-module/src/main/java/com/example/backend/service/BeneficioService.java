package com.example.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.entity.Beneficio;
import com.example.backend.repository.BeneficioRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class BeneficioService {

    @PersistenceContext
    private EntityManager em;

    private final BeneficioRepository repo;

    public BeneficioService(BeneficioRepository repo) {
        this.repo = repo;
    }

    public List<Beneficio> findAll() {
        return repo.findAll();
    }

    public Beneficio findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Benefício não encontrado"));
    }

    @Transactional
    public Beneficio save(Beneficio beneficio) {
        return repo.save(beneficio);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    /**
     * 🔥 Aqui está a parte mais importante do teste:
     * A "integração" com o EJB que na prática
     * significa RECRIAR o comportamento dele no Spring.
     */
    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        // 🔒 Lock pessimista — substitui o locking do EJB
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.PESSIMISTIC_WRITE);
        Beneficio to = em.find(Beneficio.class, toId, LockModeType.PESSIMISTIC_WRITE);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício origem/destino não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
