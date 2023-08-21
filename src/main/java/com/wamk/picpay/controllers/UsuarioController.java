package com.wamk.picpay.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
import com.wamk.picpay.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> list = usuarioService.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> findById(@PathVariable Long usuarioId){
		Optional<Usuario> usuario = usuarioService.findById(usuarioId);
		return ResponseEntity.ok(usuario.get());
	}
	
	@PostMapping
	public ResponseEntity<Usuario> criarUsario(@RequestBody UsuarioInputDTO usuarioDTO){
		var usuario = new Usuario();
		BeanUtils.copyProperties(usuarioDTO, usuario);
		usuarioService.save(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferencia){
		Long pagador = transferencia.getPagador();
		Long receptor = transferencia.getReceptor();
		var usuarioPagador = usuarioService.findById(pagador);
		int comparacao = transferencia.getValor().compareTo(usuarioPagador.get().getDinheiro());
		int saldoZero = usuarioPagador.get().getDinheiro().compareTo(BigDecimal.ZERO);
		if(pagador == receptor) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Você não pode transferir dinheiro para você mesmo!");
		else if(comparacao > 0 || saldoZero == 0) 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Você não pode saldo suficiente para realizar essa transação!");
		usuarioService.transferir(transferencia);
		return ResponseEntity.ok("Transferência realizada com sucesso!");
	}
}
