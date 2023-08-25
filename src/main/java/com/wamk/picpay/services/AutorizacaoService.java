package com.wamk.picpay.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.picpay.dtos.TransferenciaDTO;
import com.wamk.picpay.enums.TipoUsuario;
import com.wamk.picpay.services.exceptions.LojistaException;
import com.wamk.picpay.services.exceptions.MesmoClienteException;
import com.wamk.picpay.services.exceptions.SaldoInsuficienteException;

@Service
public class AutorizacaoService {
	
	@Autowired
	private UsuarioService usuarioService;

	public void validarTransferencia(TransferenciaDTO transferencia) {
		Long pagador = transferencia.getPagador();
		Long receptor = transferencia.getReceptor();
		var usuarioPagador = usuarioService.findById(pagador);
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
		var usuarioPagador = usuarioService.findById(transferencia.getPagador());
		var usuarioReceptor = usuarioService.findById(transferencia.getReceptor());
		BigDecimal dinheiroDoPagador = usuarioPagador.getDinheiro().subtract(transferencia.getValor());
		BigDecimal dinheiroDoReceptor = usuarioReceptor.getDinheiro().add(transferencia.getValor());
		usuarioPagador.setDinheiro(dinheiroDoPagador);
		usuarioReceptor.setDinheiro(dinheiroDoReceptor);
		usuarioPagador.setId(transferencia.getPagador());
		usuarioReceptor.setId(transferencia.getReceptor());
		usuarioService.save(usuarioPagador);
		usuarioService.save(usuarioReceptor);
	}
}
