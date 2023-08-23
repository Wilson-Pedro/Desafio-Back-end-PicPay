package com.wamk.picpay.apiexceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Problema implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String titulo;
	private OffsetDateTime dataHora;
	private List<Campo> campos = new ArrayList<>();
	
	public Problema() {
	}
	
	public Problema(Integer status, String titulo, OffsetDateTime dataHora) {
		this.status = status;
		this.titulo = titulo;
		this.dataHora = dataHora;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(OffsetDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public void setCampos(List<Campo> campos) {
		this.campos = campos;
	}
}
