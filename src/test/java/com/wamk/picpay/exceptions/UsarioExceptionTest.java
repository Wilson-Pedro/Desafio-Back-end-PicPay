package com.wamk.picpay.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.UsuarioService;
import com.wamk.picpay.services.exceptions.CpfJaCadastradoException;
import com.wamk.picpay.services.exceptions.EmailJaCadastradoException;
import com.wamk.picpay.services.exceptions.EntidadeNaoEncontradaException;

@SpringBootTest
class UsarioExceptionTest {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	Usuario usuario = new Usuario();
	
	@BeforeEach
	void setUp() {
		usuarioRepository.deleteAll();
		
		usuario = new Usuario(null, "Wilson", "9816923456", "wilson@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM);
	}

	@Test
	void EntidadeNaoEncontradaExceptionCase1() {
		
		assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.findById(70L));
	}
	
	@Test
	void CpfJaCadastradoExceptionCase1() {
		usuarioRepository.save(this.usuario);
		
		Usuario user = new Usuario(null, "Wilson", "9816923456", "wilson90@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM);
		
		assertThrows(CpfJaCadastradoException.class, () -> usuarioService.save(user));
	}
	
	@Test
	void EmailJaCadastradoExceptionCase1() {
		usuarioRepository.save(this.usuario);
		
		Usuario user = new Usuario(null, "Wilson", "8800021156", "wilson@gmail.com", "1234567", new BigDecimal(1000.0), TipoUsuario.COMUM);
		
		assertThrows(EmailJaCadastradoException.class, () -> usuarioService.save(user));
	}
}
