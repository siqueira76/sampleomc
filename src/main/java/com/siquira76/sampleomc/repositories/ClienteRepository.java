package com.siquira76.sampleomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siquira76.sampleomc.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
