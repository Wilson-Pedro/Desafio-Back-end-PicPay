package com.wamk.picpay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class ComprovanteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String pagador;
	private String receptor;
	private BigDecimal valorTransferido;
	private OffsetDateTime dataTransferencia;
	
	public ComprovanteDTO() {
	}

	public String getPagador() {
		return pagador;
	}

	public void setPagador(String pagador) {
		this.pagador = pagador;
	}

	public String getReceptor() {
		return receptor;
	}

	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	public BigDecimal getValorTransferido() {
		return valorTransferido;
	}

	public void setValorTransferido(BigDecimal valorTransferido) {
		this.valorTransferido = valorTransferido;
	}

	public OffsetDateTime getDataTransferencia() {
		return dataTransferencia;
	}

	public void setDataTransferencia(OffsetDateTime dataTransferencia) {
		this.dataTransferencia = dataTransferencia;
	}
}
