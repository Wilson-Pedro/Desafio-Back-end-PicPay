package com.wamk.picpay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wamk.picpay.enums.TipoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioInputDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "cpf é obrigatório")
	private String cpf;
	
	@NotBlank(message = "email é obrigatório")
	private String email;
	
	@NotBlank(message = "senha é obrigatório")
	private String senha;
	
	@NotNull(message = "dinheiro não pode ser null")
	private BigDecimal dinheiro;
	
	@NotNull(message = "tipoUsuario é obrigatório")
	private TipoUsuario tipoUsuario;
	
	public UsuarioInputDTO() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public BigDecimal getDinheiro() {
		return dinheiro;
	}

	public void setDinheiro(BigDecimal dinheiro) {
		this.dinheiro = dinheiro;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
