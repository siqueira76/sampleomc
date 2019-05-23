package com.siquira76.sampleomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Categoria;
import com.siquira76.sampleomc.repositories.CategoriaRepository;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não emcontrado id: " 
		+ id + ", Tipo: " + Categoria.class.getName()));
	}
	
}
