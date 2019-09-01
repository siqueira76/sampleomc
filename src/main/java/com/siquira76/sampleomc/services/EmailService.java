package com.siquira76.sampleomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.siquira76.sampleomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
