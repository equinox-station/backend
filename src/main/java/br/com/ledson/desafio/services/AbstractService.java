package br.com.ledson.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class AbstractService<T> {
	@Autowired
	protected JpaRepository<T, Long> repositorio;

	public T buscar(Long id) {
		Optional<T> objeto = this.repositorio.findById(id);
		return objeto.get();
	}

	public List<T> buscarTodos() {
		return this.repositorio.findAll();
	}

	public List<T> buscarTodos(Sort ordenacao) {
		return this.repositorio.findAll(ordenacao);
	}

	public T inserir(T objeto) {
		T objetoInserido = this.repositorio.save(objeto);
		return objetoInserido;
	}

	public T atualizar(T objetoNovo, Long id, String login) {
		T objeto = buscar(id); // Cria uma versão com os dados desatualizados do banco.
		atualizarDados(objetoNovo, objeto, login); // Atualiza a versão criada com os novos dados

		T objetoAtualizado = inserirSemRegistrarLog(objeto);

		return objetoAtualizado; // Salva no banco a versão com os novos dados.
	}

	private T inserirSemRegistrarLog(T objeto) {
		return this.repositorio.save(objeto);
	}

	protected abstract void atualizarDados(T objetoOrigem, T objetoDestino, String login);

	public void remover(Long id) {
		T objetoParaRemover = buscar(id);

		try {
			this.repositorio.delete(objetoParaRemover);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não foi possível remover a informação. Contate o administrador.");
		}

	}
}
