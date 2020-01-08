package com.siquira76.sampleomc.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.siquira76.sampleomc.domain.Cidade;
import com.siquira76.sampleomc.domain.Cliente;
import com.siquira76.sampleomc.domain.Endereco;
import com.siquira76.sampleomc.domain.enums.Perfil;
import com.siquira76.sampleomc.domain.enums.TipoCliente;
import com.siquira76.sampleomc.dto.ClienteDTO;
import com.siquira76.sampleomc.dto.ClienteNewDTO;
import com.siquira76.sampleomc.repositories.ClienteRepository;
import com.siquira76.sampleomc.repositories.EnderecoRepository;
import com.siquira76.sampleomc.security.UserSS;
import com.siquira76.sampleomc.services.exceptions.AuthorizationExeption;
import com.siquira76.sampleomc.services.exceptions.DataIntegrityExeption;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class ClienteService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	S3Service s3Service;
	
//	@Autowired
//	ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	public Cliente buscar(Integer id) {
		
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationExeption("Acesso negado");
		}
		
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
			throw new DataIntegrityExeption("Não é possivel excluir pois possui Pedidos relacionadas");
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
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
	
	public URI uploadProfilePicture(MultipartFile multipartfile) {
		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationExeption("Acesso negado");
		}

		URI uri = s3Service.uploadFile(multipartfile);
		Cliente cli = repo.getOne(user.getId());
		cli.setImageUrl(uri.toString());
		repo.save(cli);

		return uri;
		
	}
	
}
