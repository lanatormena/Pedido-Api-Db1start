package br.com.db1.pedidos.pedidosapi.repositorio;

import org.springframework.data.repository.CrudRepository;

import br.com.db1.pedidos.pedidosapi.domain.PedidoItem;

	public interface PedidoItemRepository extends CrudRepository <PedidoItem, Long> {
		
		PedidoItem findByCodigo(String codigo);
}
