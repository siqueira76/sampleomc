package com.siquira76.sampleomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siquira76.sampleomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
