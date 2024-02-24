package com.wamk.picpay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferenciaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal valor;
	private Long pagador;
	private Long receptor;
	
	public TransferenciaDTO() {
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getPagador() {
		return pagador;
	}

	public void setPagador(Long pagador) {
		this.pagador = pagador;
	}

	public Long getReceptor() {
		return receptor;
	}

	public void setReceptor(Long receptor) {
		this.receptor = receptor;
	}
}
