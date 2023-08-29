package com.wamk.picpay.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamk.picpay.dtos.UsuarioDTO;
import com.wamk.picpay.dtos.UsuarioMinDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.exceptions.CpfJaCadastradoException;
import com.wamk.picpay.services.exceptions.EmailJaCadastradoException;
import com.wamk.picpay.services.exceptions.EntidadeNaoEncontradaException;
import com.wamk.picpay.services.exceptions.SenhaJaCadastradoException;

import jakarta.validation.Valid;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public List<UsuarioMinDTO> findAll() {
		List<Usuario> list = usuarioRepository.findAll();
		return list.stream().map(x -> new UsuarioMinDTO(x)).toList();
	}

	public Usuario findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado!"));
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
	
	public void validaCadastro(@Valid UsuarioDTO usuarioDTO) {
		if(existByCpf(usuarioDTO.getCpf()))
			throw new CpfJaCadastradoException("Este CPF já estar cadastrado!");
		else if(existByEmail(usuarioDTO.getEmail()))
			throw new EmailJaCadastradoException("Este email já estar cadastrado!");
		else if(existBySenha(usuarioDTO.getSenha()))
			throw new SenhaJaCadastradoException("Esta senha já estar cadastrado!");
	}
}
