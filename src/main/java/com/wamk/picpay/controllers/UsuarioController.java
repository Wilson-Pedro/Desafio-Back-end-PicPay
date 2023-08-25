package com.wamk.picpay.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.dtos.UsuarioInputDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.services.AutorizacaoService;
import com.wamk.picpay.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> list = usuarioService.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> findById(@PathVariable Long usuarioId){
		Usuario usuario = usuarioService.findById(usuarioId);
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criarUsario(@Valid @RequestBody UsuarioInputDTO usuarioDTO){
		usuarioService.validaCadastro(usuarioDTO);
		var usuario = new Usuario();
		BeanUtils.copyProperties(usuarioDTO, usuario);
		usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferencia){
		autorizacaoService.validarTransferencia(transferencia);
		autorizacaoService.transferir(transferencia);
		return ResponseEntity.ok("Transferência realizada com sucesso!");
	}
}
