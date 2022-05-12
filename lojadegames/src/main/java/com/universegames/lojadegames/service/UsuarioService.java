package com.universegames.lojadegames.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.universegames.lojadegames.model.Usuario;
import com.universegames.lojadegames.model.UsuarioLogin;
import com.universegames.lojadegames.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	/*FUNÇÃO PARA CADASTRAR UM USUARIO */
	public Optional<Usuario>cadastrarUsuario(Usuario usuario) {
		
		/*PRIMEIRO VALIDA SE O USUARIO É EXISTENTE NO BANCO */
		if(repository.findByUsuario(usuario.getUsuario()).isPresent())
			return Optional.empty();
			
		/*CRIPTOGRAFO A SENHA DO USUARIO CASO NÃO EXISTA */	
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
			
		/* E POR ULTIMO, SALVO O USUARIO COM A SENHA JÁ CRIPTOGRAFADA NO BANCO DE DADOS */
			return Optional.of(repository.save(usuario));
	};
	
	public Optional<Usuario> atualizarUsuario(Usuario usuario) {
		
		/*PROCURAR USARIO POR ID */
		if(repository.findById(usuario.getId()).isPresent()) {
		
		/*CRIPTOGRAFAR A SENHA CRIPT */				
			return Optional.of(repository.save(usuario));
	}
			return Optional.empty();
		
	}
	/*FUNÇÃO PARA CRIPTOGRAFAR A SENHA DIGITADA PELO USUARIO */
	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(senha);
		
	}
	
	public Optional<UsuarioLogin>autenticaUsuario(Optional<UsuarioLogin> usuarioLogin) {
		   Optional<Usuario> usuario = repository.findByUsuario(usuarioLogin.get().getUsuario());
		  /* SE O USUARIO EXISTIR ELE VAI CAIR NO IF E VAI COMPARAR SE A SENHA CADASTRADA NO BANCO DO USUARIO É IGUAL A SENHA INSERIDA */
		   
		   if(usuario.isPresent()) {
		/* COMPARAR SENHA RETORNA UM VERDADEIRO OU FALSO */
			   if(compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {
		/* SE AS SENHAS FOREM IGUAIS, ELE VAI PREENCHER OS DADOS DE ID, NOME, FOTO E GERAR UM TOKEN */
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
