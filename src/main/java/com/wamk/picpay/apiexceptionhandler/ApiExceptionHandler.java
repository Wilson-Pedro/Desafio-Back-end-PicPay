package com.wamk.picpay.apiexceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wamk.picpay.services.exceptions.CpfJaCadastradoException;
import com.wamk.picpay.services.exceptions.EmailJaCadastradoException;
import com.wamk.picpay.services.exceptions.EntidadeNaoEncontradaException;
import com.wamk.picpay.services.exceptions.LojistaException;
import com.wamk.picpay.services.exceptions.MesmoClienteException;
import com.wamk.picpay.services.exceptions.SaldoInsuficienteException;
import com.wamk.picpay.services.exceptions.ValidacaoNaoAutorizadaException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<Campo> campos = new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
		
			campos.add(new Campo(nome, mensagem));
		}
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos! Por favor preencha-os corretamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Problema> entidadeNaoEncontradaException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		problema.setStatus(status.value());
		problema.setTitulo("Usuário não encontrado.");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problema);
	}
	
	@ExceptionHandler(MesmoClienteException.class)
	public ResponseEntity<Problema> mesmoClienteException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Você não pode transferir dinheiro para você mesmo!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(SaldoInsuficienteException.class)
	public ResponseEntity<Problema> saldoInsuficienteException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Você não possui saldo suficiente para realizar essa transação!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(LojistaException.class)
	public ResponseEntity<Problema> lojistaException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Lojista não pode realizar transações!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(CpfJaCadastradoException.class)
	public ResponseEntity<Problema> cpfJaCadastradoException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Este cpf já foi cadastrado!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(EmailJaCadastradoException.class)
	public ResponseEntity<Problema> emailJaCadastradoException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Este email já foi cadastrado!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
	
	@ExceptionHandler(ValidacaoNaoAutorizadaException.class)
	public ResponseEntity<Problema> validacaoNaoAutorizadaException(){
		
		Problema problema = new Problema();
		
		HttpStatus status = HttpStatus.BAD_REQUEST;

		problema.setStatus(status.value());
		problema.setTitulo("Transação não autorizada!");
		problema.setDataHora(OffsetDateTime.now());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problema);
	}
}
