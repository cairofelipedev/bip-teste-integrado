package com.example.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.example.backend.dto.TransferRequest;
import com.example.backend.entity.Beneficio;
import com.example.backend.service.BeneficioService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Beneficio> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Beneficio find(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Beneficio create(@RequestBody Beneficio beneficio) {
        return service.save(beneficio);
    }

    @PutMapping("/{id}")
    public Beneficio update(@PathVariable Long id, @RequestBody Beneficio beneficio) {
        beneficio.setId(id);
        return service.save(beneficio);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // 🔥 Endpoint que simula o EJB de transferência
    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequest req) {
        service.transfer(req.fromId(), req.toId(), req.amount());
    }
}
