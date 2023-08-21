package com.wamk.picpay.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Transactional
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> findById(Long usuarioId) {
		return usuarioRepository.findById(usuarioId);
	}

	public void transferir(TransferenciaDTO transferencia) {
		var usuarioPagador = findById(transferencia.getPagador()).get();
		var usuarioReceptor = findById(transferencia.getReceptor()).get();
		BigDecimal dinheiroDoPagador = usuarioPagador.getDinheiro().subtract(transferencia.getValor());
		BigDecimal dinheiroDoReceptor = usuarioReceptor.getDinheiro().add(transferencia.getValor());
		usuarioPagador.setDinheiro(dinheiroDoPagador);
		usuarioReceptor.setDinheiro(dinheiroDoReceptor);
		usuarioPagador.setId(transferencia.getPagador());
		usuarioReceptor.setId(transferencia.getReceptor());
		save(usuarioPagador);
		save(usuarioReceptor);
	}
}
