package com.wamk.picpay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamk.picpay.dtos.UsuarioDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.exceptions.CpfJaCadastradoException;
import com.wamk.picpay.services.exceptions.EmailJaCadastradoException;
import com.wamk.picpay.services.exceptions.EntidadeNaoEncontradaException;
import com.wamk.picpay.services.exceptions.SenhaJaCadastradoException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public Usuario save(Usuario usuario) {
		validaCadastro(new UsuarioDTO(usuario));
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Usuario findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId).orElseThrow(
				() -> new EntidadeNaoEncontradaException("Usuário não encontrado!"));
	}
	
	public boolean existByCpf(String cpf) {
		return usuarioRepository.existsByCpf(cpf);
	}
	
	public boolean existByEmail(String email) {
		return usuarioRepository.existsByEmail(email);
	}
	
	public boolean existBySenha(String senha) {
		return usuarioRepository.existsBySenha(senha);
	}
	
	public void validaCadastro(UsuarioDTO usuarioDTO) {
		if(existByCpf(usuarioDTO.getCpf()))
			throw new CpfJaCadastradoException("Este CPF já estar cadastrado!");
		
		else if(existByEmail(usuarioDTO.getEmail()))
			throw new EmailJaCadastradoException("Este email já estar cadastrado!");
		
		else if(existBySenha(usuarioDTO.getSenha()))
			throw new SenhaJaCadastradoException("Esta senha já estar cadastrado!");
	}

	public void delete(Long usuarioId) {
		usuarioRepository.delete(findById(usuarioId));
	}
}
