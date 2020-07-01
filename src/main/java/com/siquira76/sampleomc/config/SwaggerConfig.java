package com.siquira76.sampleomc.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				
				.select()
				.apis(RequestHandlerSelectors
				.basePackage("com.siquira76.sampleomc.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API do curso Spring Boot",
				"Esta API é utilizada como treinamento design UML com base em um exemplo de e-commerce",
				"Versão 1.0",
				"https://github.com/siqueira76/sampleomc.git",
				new Contact("José Carlos Siqueira", "https://www.linkedin.com/in/siqueira1/",
						"josecarlos.siqueira76@gmail.com"),
				"Permitido uso para estudantes", 
				"https://github.com/siqueira76/sampleomc.git", 
				Collections.emptyList() // Vendor
				// Extensions
		);
	}


}
