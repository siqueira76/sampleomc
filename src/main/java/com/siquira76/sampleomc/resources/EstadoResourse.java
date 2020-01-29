package com.siquira76.sampleomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siquira76.sampleomc.domain.Cidade;
import com.siquira76.sampleomc.domain.Estado;
import com.siquira76.sampleomc.dto.CidadeDTO;
import com.siquira76.sampleomc.dto.EstadoDTO;
import com.siquira76.sampleomc.services.CidadeService;
import com.siquira76.sampleomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResourse {
	
	@Autowired
	EstadoService service;
	
	@Autowired
	CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> list = service.findAll();
		List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDTO = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	

}
