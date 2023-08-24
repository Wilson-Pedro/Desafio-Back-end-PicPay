package com.wamk.picpay.services.exceptions;

public class CpfJaCadastradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public CpfJaCadastradoException(String msg) {
		super(msg);
	}
}
