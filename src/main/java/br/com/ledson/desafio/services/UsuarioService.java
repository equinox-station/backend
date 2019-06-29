package br.com.ledson.desafio.services;

import org.springframework.stereotype.Service;

import br.com.ledson.desafio.domain.Usuario;

@Service
public class UsuarioService extends AbstractService<Usuario> {

	@Override
	protected void atualizarDados(Usuario objetoOrigem, Usuario objetoDestino, String login) {
		if (objetoOrigem.getRole() != null) objetoDestino.setRole(objetoOrigem.getRole());
		if (objetoOrigem.getNome() != null) objetoDestino.setNome(objetoOrigem.getNome());
		if (objetoOrigem.getCargo() != null) objetoDestino.setCargo(objetoOrigem.getCargo());
		if (objetoOrigem.getTelefone() != null) objetoDestino.setTelefone(objetoOrigem.getTelefone());
		if (objetoOrigem.getFoto() != null) objetoDestino.setFoto(objetoOrigem.getFoto());
	}

}
