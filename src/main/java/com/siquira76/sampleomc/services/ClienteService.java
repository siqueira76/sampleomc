package com.siquira76.sampleomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Categoria;
import com.siquira76.sampleomc.domain.Cliente;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class ClienteService {
	
//	@Autowired
//	ClienteRepository repo;
//	
//	public Cliente buscar(Integer id) {
//		Optional<Cliente> obj = repo.findById(id);
//		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não emcontrado id: " 
//		+ id + ", Tipo: " + Cliente.class.getName()));
//	}
	
}
