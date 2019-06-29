package br.com.ledson.desafio.resources;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ledson.desafio.domain.Usuario;

@RestController
@CrossOrigin
@RequestMapping(value = "/usuarios")
public class UsuarioResource extends GenericResource<Usuario> {
	
}
