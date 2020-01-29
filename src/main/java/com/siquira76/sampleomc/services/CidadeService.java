package com.siquira76.sampleomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Cidade;
import com.siquira76.sampleomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estado_id){
		return repo.findCidades(estado_id);
	}
	

}
