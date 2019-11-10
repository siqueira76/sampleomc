package com.siquira76.sampleomc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.siquira76.sampleomc.services.S3Service;

@SpringBootApplication
public class SampleomcApplication implements CommandLineRunner {
	
	@Autowired
	private S3Service s3Service;

	public static void main(String[] args) {
		SpringApplication.run(SampleomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\T3575774\\Documents\\Geral\\images\\lampada.jpg");
	}

}
