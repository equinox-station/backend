package br.com.ledson.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.ledson.desafio.domain.Usuario;
import br.com.ledson.desafio.repository.UsuarioRepository;

@Component(value = "userController")
@Scope(value = "session")
public class UserController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<Usuario> getAllUsers() {
		return usuarioRepository.findAllByOrderByNomeAsc();
	}

	/*
	 * Insere e atualiza usuários.
	 * Se o ID = 0, insere.
	 * Caso contrário, atualize.
	 */
	public Usuario modifyUser(Long id, String login, String nome, String cargo, String telefone) {
		Usuario usuario = new Usuario(id, login, "USER", nome, cargo, telefone, null);
		return usuarioRepository.save(usuario);
	}
	
	public void deleteUser(long id) {
		try{
			usuarioRepository.deleteById(id);
		}
		catch(IllegalArgumentException iae) {
			System.out.println(iae.getMessage());
		}
	}
}
