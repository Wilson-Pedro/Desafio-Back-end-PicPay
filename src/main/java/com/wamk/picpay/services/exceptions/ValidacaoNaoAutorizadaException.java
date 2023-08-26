package com.wamk.picpay.services.exceptions;

public class ValidacaoNaoAutorizadaException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ValidacaoNaoAutorizadaException(String msg) {
		super(msg);
	}
}
