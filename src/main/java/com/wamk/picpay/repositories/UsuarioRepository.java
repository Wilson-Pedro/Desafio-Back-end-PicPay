package com.wamk.picpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wamk.picpay.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	boolean existsByCpf(String cpf);
	
	boolean existsByEmail(String email);
	
	boolean existsBySenha(String senha);
}
