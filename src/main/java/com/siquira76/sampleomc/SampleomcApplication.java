package com.siquira76.sampleomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.siquira76.sampleomc.domain.Categoria;
import com.siquira76.sampleomc.repositories.CategoriaRepository;

@SpringBootApplication
public class SampleomcApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleomcApplication.class, args);
	}
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Override
	public void run(String... args) throws Exception {
		
		categoriaRepository.deleteAll();
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

}
