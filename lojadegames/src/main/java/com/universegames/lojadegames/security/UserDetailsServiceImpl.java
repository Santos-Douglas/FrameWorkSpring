package com.universegames.lojadegames.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.universegames.lojadegames.model.Usuario;
import com.universegames.lojadegames.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

	@Autowired
	private UsuarioRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
		Optional<Usuario> usuario = userRepository.findByUsuario(userName);
		
		usuario.orElseThrow(()-> new UsernameNotFoundException(userName + "Usuario não encontrado"));
		
		return usuario.map(UserDetailsImpl:: new).get();
	
	}
}
