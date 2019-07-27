package com.siquira76.sampleomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siquira76.sampleomc.domain.Cidade;
import com.siquira76.sampleomc.domain.Cliente;
import com.siquira76.sampleomc.domain.Endereco;
import com.siquira76.sampleomc.domain.enums.TipoCliente;
import com.siquira76.sampleomc.dto.ClienteDTO;
import com.siquira76.sampleomc.dto.ClienteNewDTO;
import com.siquira76.sampleomc.repositories.ClienteRepository;
import com.siquira76.sampleomc.repositories.EnderecoRepository;
import com.siquira76.sampleomc.services.exceptions.DataIntegrityExeption;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class ClienteService {
	
	@Autowired
	ClienteRepository repo;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFondException("Objeto não emcontrado id: " 
		+ id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExeption("Não é possivel excluir pois possui entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2()!=null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3()!=null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}
	

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
