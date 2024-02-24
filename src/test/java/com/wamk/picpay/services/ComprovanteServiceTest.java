package com.wamk.picpay.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.picpay.dtos.ComprovanteDTO;
import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;

@SpringBootTest
class ComprovanteServiceTest {
	
	@Autowired
	ComprovanteService comprovanteService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		
	}

	@Test
	@DisplayName("Should Generate Receipt Successfully")
	void generateReceiptCase01() {
		usuarioRepository.save(new Usuario(null, "Wilson", "9816923456", "wilson@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM));
		usuarioRepository.save(new Usuario(null, "Pedro", "2177723654", "pedro@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM));
		
		Long pagadorId = usuarioRepository.findAll().get(0).getId();
		Long receptorId = usuarioRepository.findAll().get(1).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO();
		transferencia.setPagador(pagadorId);
		transferencia.setReceptor(receptorId);
		transferencia.setValor(new BigDecimal(500.0));
		
		ComprovanteDTO comprovante = comprovanteService.gerarComprovante(transferencia);
		
		assertEquals("Wilson", comprovante.getPagador());
		assertEquals("Pedro", comprovante.getReceptor());
		assertEquals(new BigDecimal("500"), comprovante.getValorTransferido());
	}

}
