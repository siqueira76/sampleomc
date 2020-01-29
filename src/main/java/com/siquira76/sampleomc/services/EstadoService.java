package com.siquira76.sampleomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Estado;
import com.siquira76.sampleomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	EstadoRepository repo;
	
	public List<Estado> findAll(){
		return repo.findAllByOrderByNome();
	}

}
