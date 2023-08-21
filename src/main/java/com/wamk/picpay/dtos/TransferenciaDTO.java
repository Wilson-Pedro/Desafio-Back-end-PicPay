package com.wamk.picpay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransferenciaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal valor;
	private Long pagdor;
	private Long receptor;
	
	public TransferenciaDTO() {
	}

	public BigDecimal getValor() {
		return valor;
	}

	public Long getPagdor() {
		return pagdor;
	}

	public Long getReceptor() {
		return receptor;
	}
}
