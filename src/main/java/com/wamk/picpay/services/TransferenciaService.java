package com.wamk.picpay.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.repositories.UsuarioRepository;
import com.wamk.picpay.services.exceptions.LojistaException;
import com.wamk.picpay.services.exceptions.MesmoClienteException;
import com.wamk.picpay.services.exceptions.SaldoInsuficienteException;

import jakarta.transaction.Transactional;

@Service
public class TransferenciaService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public void transferir(TransferenciaDTO transferencia) {
		var usuarioPagador = usuarioService.findById(transferencia.getPagador());
		var usuarioReceptor = usuarioService.findById(transferencia.getReceptor());
		
		BigDecimal dinheiroDoPagador = usuarioPagador.getSaldo().subtract(transferencia.getValor());
		BigDecimal dinheiroDoReceptor = usuarioReceptor.getSaldo().add(transferencia.getValor());
		
		usuarioPagador.setSaldo(dinheiroDoPagador);
		usuarioReceptor.setSaldo(dinheiroDoReceptor);
		usuarioPagador.setId(transferencia.getPagador());
		usuarioReceptor.setId(transferencia.getReceptor());
		
		usuarioRepository.save(usuarioPagador);
		usuarioRepository.save(usuarioReceptor);
	}
	
	public void validarTransferencia(TransferenciaDTO transferencia) {
		Long pagador = transferencia.getPagador();
		Long receptor = transferencia.getReceptor();
		
		var usuarioPagador = usuarioService.findById(pagador);
		int comparacao = transferencia.getValor().compareTo(usuarioPagador.getSaldo());
		int saldoZero = usuarioPagador.getSaldo().compareTo(BigDecimal.ZERO);
		
		if(pagador == receptor) 
			throw new MesmoClienteException("Você não pode transferir dinheiro para você mesmo!");
		
		else if(comparacao > 0 || saldoZero == 0)
			throw new SaldoInsuficienteException("Você não possui saldo suficiente para realizar essa transação!");
		
		else if(usuarioPagador.getTipoUsuario().equals(TipoUsuario.LOJISTA))
			throw new LojistaException("Lojista não pode realizar transações!");
	}
}
