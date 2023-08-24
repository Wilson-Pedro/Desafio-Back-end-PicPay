package com.wamk.picpay.services.exceptions;

public class SenhaJaCadastradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public SenhaJaCadastradoException(String msg) {
		super(msg);
	}
}
