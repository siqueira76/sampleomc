package com.siquira76.sampleomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siquira76.sampleomc.domain.Categoria;
import com.siquira76.sampleomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	CategoriaService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> Buscar(@PathVariable Integer id){
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}

}
