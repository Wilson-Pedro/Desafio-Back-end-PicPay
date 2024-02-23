package com.wamk.picpay.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioServiceTest {

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
	@Order(1)
	@DisplayName("Shoud Save The User Successfully")
	void saveCase01() {
		
		assertEquals(0, usuarioRepository.count());
		
		usuarioService.save(usuario);
		
		assertEquals(1, usuarioRepository.count());
	}

	@Test
	@Order(2)
	@DisplayName("Should Fetch a List Of Users Successfully")
	void findAllCase01() {
		usuarioService.save(usuario);
		
		List<Usuario> list = usuarioService.findAll();
		
		assertEquals(list.size(), usuarioRepository.count());
	}
	
	@Test
	@Order(3)
	@DisplayName("Should Find The User From The Id Successfully")
	void findByIdCase01() {
		usuarioService.save(usuario);
		
		Long id = usuarioRepository.findAll().get(0).getId();
		
		Usuario usuario = usuarioService.findById(id);
		
		assertEquals("Wilson", usuario.getNome());
		assertEquals("9816923456", usuario.getCpf());
		assertEquals("wilson@gmail.com", usuario.getEmail());
		assertEquals("1234567", usuario.getSenha());
		assertEquals(new BigDecimal("1000.00"), usuario.getSaldo());
		assertEquals(TipoUsuario.COMUM, usuario.getTipoUsuario());
	}
	
	@Test
	@Order(4)
	@DisplayName("Should Delete The User Successfully")
	void deleteCase01() {
		usuarioService.save(usuario);
		
		Long id = usuarioRepository.findAll().get(0).getId();
		
		assertEquals(1, usuarioRepository.count());
		
		usuarioService.delete(id);
		
		assertEquals(0, usuarioRepository.count());
	}
}
