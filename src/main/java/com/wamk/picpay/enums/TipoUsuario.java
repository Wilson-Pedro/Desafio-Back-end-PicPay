package com.wamk.picpay.enums;

public enum TipoUsuario {

	COMUM(1, "Comum"),
	LOJISTA(2, "Lojista");
	
	private Integer cod;
	private String descricao;
	
	private TipoUsuario(Integer cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
