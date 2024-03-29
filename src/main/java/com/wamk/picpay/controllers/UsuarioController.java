package com.wamk.picpay.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wamk.picpay.dtos.ComprovanteDTO;
import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.dtos.UsuarioDTO;
import com.wamk.picpay.dtos.UsuarioMinDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.services.ComprovanteService;
import com.wamk.picpay.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ComprovanteService comprovanteService;
	
	@GetMapping
	public ResponseEntity<List<UsuarioMinDTO>> listar(){
		List<Usuario> list = usuarioService.findAll();
		List<UsuarioMinDTO> listDto = list.stream().map(x -> new UsuarioMinDTO(x)).toList();
		if(!list.isEmpty()) 
			for(UsuarioMinDTO usuario : listDto) {
				Long id = usuario.getId();
				usuario.add(linkTo(methodOn(UsuarioController.class).findById(id)).withSelfRel());
			}
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<Usuario> findById(@PathVariable Long usuarioId){
		Usuario usuario = usuarioService.findById(usuarioId);
		usuario.add(linkTo(methodOn(UsuarioController.class).listar()).withSelfRel());
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping("/")
	public ResponseEntity<Usuario> criarUsario(@Valid @RequestBody UsuarioDTO usuarioDTO){
		Usuario usuario = usuarioService.save(new Usuario(usuarioDTO));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> delete(@PathVariable Long usuarioId){
		usuarioService.delete(usuarioId);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping("/transferencia")
	public ResponseEntity<Object> transferir(@RequestBody TransferenciaDTO transferencia){
		ComprovanteDTO comprovante = comprovanteService.gerarComprovante(transferencia);
		return ResponseEntity.ok(comprovante);
	}
}