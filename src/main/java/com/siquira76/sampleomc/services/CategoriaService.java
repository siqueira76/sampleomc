package com.siquira76.sampleomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Categoria;
import com.siquira76.sampleomc.repositories.CategoriaRepository;
import com.siquira76.sampleomc.services.exceptions.DataIntegrityExeption;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não encontrado id: " 
		+ id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		buscar(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExeption("Não é possivel excluir uma categoria que poossui Produto");
		}
	}
	
}
