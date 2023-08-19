package com.wamk.picpay;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;

@SpringBootApplication
public class DesafioPicpayApplication implements CommandLineRunner{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicpayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Usuario u1 = new Usuario(null, "Wilson Pedro", "333.127.410-09", "wilson@gmail.com", 
				"laranja123", new BigDecimal(1000), TipoUsuario.COMUM);
		
		Usuario u2 = new Usuario(null, "Ana Julia", "454.745.510-45", "ana@gmail.com", 
				"morango123", new BigDecimal(1000), TipoUsuario.LOJISTA);
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2));
	}

}
