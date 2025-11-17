package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entity.Beneficio;

@Repository
public interface BeneficioRepository extends JpaRepository<Beneficio, Long> {

}