package com.wamk.picpay.dtos;

import java.io.Serializable;

import com.wamk.picpay.entities.Usuario;
import com.wamk.picpay.enums.TipoUsuario;

public class UsuarioMinDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private String email;
	
	private TipoUsuario tipoUsuario;
	
	public UsuarioMinDTO() {
	}
	
	public UsuarioMinDTO(Usuario usuario) {
		id = usuario.getId();
		nome = usuario.getNome();
		email = usuario.getEmail();
		tipoUsuario = usuario.getTipoUsuario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
}
