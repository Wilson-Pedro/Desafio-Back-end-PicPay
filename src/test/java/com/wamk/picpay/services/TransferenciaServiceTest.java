package com.wamk.picpay.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;

@SpringBootTest
class TransferenciaServiceTest {

	@Autowired
	TransferenciaService transferenciaService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		
	}
	
	@Test
	void transferCase01() {
		usuarioRepository.save(new Usuario(null, "Wilson", "9816923456", "wilson@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM));
		usuarioRepository.save(new Usuario(null, "Pedro", "2177723654", "pedro@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM));
		
		Long pagadorId = usuarioRepository.findAll().get(0).getId();
		Long receptorId = usuarioRepository.findAll().get(1).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO();
		transferencia.setPagador(pagadorId);
		transferencia.setReceptor(receptorId);
		transferencia.setValor(new BigDecimal(500.0));
		
		transferenciaService.transferir(transferencia);
		
		var wilson = usuarioRepository.findById(pagadorId).get();
		var pedro = usuarioRepository.findById(receptorId).get();
		
		assertEquals(new BigDecimal("500.00"), wilson.getSaldo());
		assertEquals(new BigDecimal("1500.00"), pedro.getSaldo());
	}

}
