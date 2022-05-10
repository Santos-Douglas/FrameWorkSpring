package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	/*FUNÇÃO PARA CADASTRAR USUARIO */
	public Optional<Usuario>cadastraUsuario(Usuario usuario) {
		
		/*PRIMEIRO VALIDA SE O USUARIO JA EXISTE NO BANCO */
		if(repository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
			
		/*CRIPTOGRAFO A SENHA DO USUARIO CASO NÃO EXISTA */	
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
		/* E POR ULTIMO, SALVO O USUARIO COM A SENHA JÁ CRIPTOGRAFADA NO BANCO DE DADOS */
			return Optional.of(repository.save(usuario));
		
	};
	
	/*FUNÇÃO PARA CRIPTOGRAFAR A SENHA DIGITADA PELO USUARIO */
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);

	};
	
	public Optional<UsuarioLogin>autenticaUsuario(Optional<UsuarioLogin> usuarioLogin) {
		   Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());

		   if(usuario.isPresent()) {
			   if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
				   	usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setFoto(usuario.get().getFoto());
					usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
					usuarioLogin.get().setSenha(usuario.get().getSenha());
			   
					return usuarioLogin;
			   }
		   }
		   
		   			return Optional.empty();
	}
	
	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
		
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
					return encoder.matches(senhaDigitada, senhaBanco);
	}
	
	private String gerarBasicToken(String usuario, String senha) {

		String token = usuario + ":" + senha;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

}
