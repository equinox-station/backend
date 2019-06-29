package br.com.ledson.desafio.resources;

import java.net.URI;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ledson.desafio.services.AbstractService;

public abstract class GenericResource<T> {
	@Autowired
	protected AbstractService<T> service;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<T> buscar(@PathVariable Long id) {
		T objeto = service.buscar(id);
		return ResponseEntity.ok().body(objeto);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<T>> buscarTodos() {
		List<T> lista = service.buscarTodos();
		return ResponseEntity.ok().body(lista);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<T> inserir(@Valid @RequestBody T objeto, @RequestHeader("authorization") String auth) {
		Long idObjeto = null;
		
		try {
			/*
			 * Chama o setId(0) do objeto. 
			 * Leva em consideração que o objeto tem uma estratégia de geração de valor de chave primária para o id.
			 * Dessa forma, o objeto não via ser inserido com 0 no banco.
			 */
			objeto.getClass().getMethod("setId", Long.class).invoke(objeto, Long.valueOf(0L));
			objeto = service.inserir(objeto);
			idObjeto = (Long) objeto.getClass().getMethod("getId", (Class<?>[]) null).invoke(objeto, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(null);
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(idObjeto).toUri();
		return ResponseEntity.created(uri).body(objeto);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<T> atualizar(@Valid @RequestBody T objeto, @RequestHeader("authorization") String auth) {
		Long idObjeto = null;
		
		try {
			idObjeto = (Long) objeto.getClass().getMethod("getId", (Class<?>[]) null).invoke(objeto, (Object[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String base64 = auth.split(" ")[1];
		String login = new String(Base64.getDecoder().decode(base64)).split(":")[0];
		
		objeto = service.atualizar(objeto, idObjeto, login);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(idObjeto).toUri();
		return ResponseEntity.created(uri).body(objeto);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();
	}

}
