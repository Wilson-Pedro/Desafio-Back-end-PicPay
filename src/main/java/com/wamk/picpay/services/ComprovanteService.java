package com.wamk.picpay.services;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wamk.picpay.dtos.ComprovanteDTO;
import com.wamk.picpay.dtos.TransferenciaDTO;

@Service
public class ComprovanteService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	public ComprovanteDTO gerarComprovante(TransferenciaDTO transferencia) {
		autorizacaoService.validarTransferencia(transferencia);
		autorizacaoService.transferir(transferencia);
		
		Long pagadorId = transferencia.getPagador();
		Long receptorId = transferencia.getReceptor();
		
		var clientePagador = usuarioService.findById(pagadorId);
		var clienteReceptor = usuarioService.findById(receptorId);
		
		ComprovanteDTO comprovanteDTO = new ComprovanteDTO();
		
		comprovanteDTO.setPagador(clientePagador.getNome());
		comprovanteDTO.setReceptor(clienteReceptor.getNome());
		comprovanteDTO.setValorTransferido(transferencia.getValor());
		comprovanteDTO.setDataTransferencia(OffsetDateTime.now());
		
		return comprovanteDTO;
	}
}
