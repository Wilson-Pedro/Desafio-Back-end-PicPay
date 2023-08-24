package com.wamk.picpay.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.dtos.UsuarioInputDTO;
import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.exceptions.CpfJaCadastradoException;
import com.wamk.picpay.services.exceptions.EmailJaCadastradoException;
import com.wamk.picpay.services.exceptions.EntidadeNaoEncontradaException;
import com.wamk.picpay.services.exceptions.LojistaException;
import com.wamk.picpay.services.exceptions.MesmoClienteException;
import com.wamk.picpay.services.exceptions.SaldoInsuficienteException;
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

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
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
	
	public void validaCadastro(@Valid UsuarioInputDTO usuarioDTO) {
		if(existByCpf(usuarioDTO.getCpf()))
			throw new CpfJaCadastradoException("Este CPF já estar cadastrado!");
		else if(existByEmail(usuarioDTO.getEmail()))
			throw new EmailJaCadastradoException("Este email já estar cadastrado!");
		else if(existBySenha(usuarioDTO.getSenha()))
			throw new SenhaJaCadastradoException("Esta senha já estar cadastrado!");
	}
	
	public void validarTransferencia(TransferenciaDTO transferencia) {
		Long pagador = transferencia.getPagador();
		Long receptor = transferencia.getReceptor();
		var usuarioPagador = findById(pagador);
		int comparacao = transferencia.getValor().compareTo(usuarioPagador.getDinheiro());
		int saldoZero = usuarioPagador.getDinheiro().compareTo(BigDecimal.ZERO);
		if(pagador == receptor) 
			throw new MesmoClienteException("Você não pode transferir dinheiro para você mesmo!");
		else if(comparacao > 0 || saldoZero == 0) 
			throw new SaldoInsuficienteException("Você não possui saldo suficiente para realizar essa transação!");
		else if(usuarioPagador.getTipoUsuario().equals(TipoUsuario.LOJISTA)) {
			throw new LojistaException("Lojista não pode realizar transações!");
		}
	}

	public void transferir(TransferenciaDTO transferencia) {
		var usuarioPagador = findById(transferencia.getPagador());
		var usuarioReceptor = findById(transferencia.getReceptor());
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
