package br.com.db1.pedidos.pedidosapi.repositorio;

import org.springframework.data.repository.CrudRepository;

import br.com.db1.pedidos.pedidosapi.domain.Historico;

public interface HistoricoRepository extends CrudRepository<Historico, Long>{
	
	Historico findByCodigo(String codigo);
}
