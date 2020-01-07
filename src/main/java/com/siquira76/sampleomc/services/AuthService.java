package com.siquira76.sampleomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.siquira76.sampleomc.domain.Cliente;
import com.siquira76.sampleomc.repositories.ClienteRepository;
import com.siquira76.sampleomc.services.exceptions.ObjectNotFondException;

@Service
public class AuthService {

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	BCryptPasswordEncoder pe;

	@Autowired
	EmailService emailService;

	Random rand = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFondException("Email não encontrado");
		}
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasword(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		} else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}

	}

}
