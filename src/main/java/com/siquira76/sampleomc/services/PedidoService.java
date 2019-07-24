package com.siquira76.sampleomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Pedido;
import com.siquira76.sampleomc.repositories.PedidoRepository;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não emcontrado id: " 
				+ id + ", Tipo: " + Pedido.class.getName()));
	}

}
