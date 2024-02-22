package com.wamk.picpay.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "nome é obrigatório")
	private String nome;
	
	@NotBlank(message = "cpf é obrigatório")
	private String cpf;
	
	@NotBlank(message = "email é obrigatório")
	private String email;
	
	@NotBlank(message = "senha é obrigatório")
	private String senha;
	
	@NotNull(message = "saldo não pode ser null")
	private BigDecimal saldo;
	
	@NotNull(message = "tipoUsuario é obrigatório")
	private TipoUsuario tipoUsuario;
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Usuario usuario) {
		nome = usuario.getNome();
		cpf = usuario.getCpf();
		email = usuario.getEmail();
		senha = usuario.getSenha();
		saldo = usuario.getSaldo();
		tipoUsuario = usuario.getTipoUsuario();
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
