package com.wamk.picpay.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.ComprovanteService;
import com.wamk.picpay.services.exceptions.LojistaException;
import com.wamk.picpay.services.exceptions.MesmoClienteException;
import com.wamk.picpay.services.exceptions.SaldoInsuficienteException;

@SpringBootTest
class TransferenciaExceptionTest {
	
	@Autowired
	ComprovanteService comprovanteService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	Usuario usuario = new Usuario();
	
	@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		
		usuario = new Usuario(null, "Wilson", "4556923111", "wilson@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM);
	}

	@Test
	void MesmoClienteExceptionCase01() {
		usuarioRepository.save(usuario);
		
		Long pagadorId = usuarioRepository.findAll().get(0).getId();
		Long receptorId = usuarioRepository.findAll().get(0).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO();
		transferencia.setPagador(pagadorId);
		transferencia.setReceptor(receptorId);
		transferencia.setValor(new BigDecimal(1000.0));
		
		assertThrows(MesmoClienteException.class, () -> comprovanteService.gerarComprovante(transferencia));
	}
	
	@Test
	void SaldoInsuficienteExceptionCase01() {
		usuarioRepository.save(usuario);
		usuarioRepository.save(new Usuario(null, "Pedro", "2616923098", "pedro@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM));
		
		Long pagadorId = usuarioRepository.findAll().get(0).getId();
		Long receptorId = usuarioRepository.findAll().get(1).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO();
		transferencia.setPagador(pagadorId);
		transferencia.setReceptor(receptorId);
		transferencia.setValor(new BigDecimal(2000.0));
		
		assertThrows(SaldoInsuficienteException.class, () -> comprovanteService.gerarComprovante(transferencia));
	}
	
	@Test
	void LojistaExceptionCase01() {
		usuarioRepository.save(new Usuario(null, "Pedro", "2616923098", "pedro@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.LOJISTA));
		usuarioRepository.save(usuario);
		
		Long pagadorId = usuarioRepository.findAll().get(0).getId();
		Long receptorId = usuarioRepository.findAll().get(1).getId();
		
		TransferenciaDTO transferencia = new TransferenciaDTO();
		transferencia.setPagador(pagadorId);
		transferencia.setReceptor(receptorId);
		transferencia.setValor(new BigDecimal(1000.0));
		
		assertThrows(LojistaException.class, () -> comprovanteService.gerarComprovante(transferencia));
	}

}
