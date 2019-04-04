package br.com.db1.pedidos.pedidosapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "pedido_item")
public class PedidoItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_produto", referencedColumnName = "id")
	private Produto produto;
	
	@Column(name = "quantidade", nullable = false, precision = 16, scale = 3)
	private Integer quantidade;
	
	@Column(name = "valor_unitario", nullable = false, precision = 16, scale = 3)
	private Double valor;

	@ManyToOne
	@JoinColumn(name = "id_pedido", referencedColumnName = "id")
	private Pedido pedido;

	//construtor
	public PedidoItem(Produto produto, int quantidade, Double valor) {
		validaProduto(produto);
		verificaADDQuantidade(quantidade);
		this.valor = valor;

	}

	private void verificaADDQuantidade(int q) {
		if (q > 0) {
			this.quantidade = q;
		} else {
			throw new RuntimeException("A quantidade deve ser maior que zero, quantidade atual: " + q);
		}

	}

	private void validaProduto(Produto produto) {
		if (produto.isAtivo()) {
			this.produto = produto;
		} else {
			throw new RuntimeException("Produto intativo: " + produto.getNome());
		}
	}

	public Double getValor() {
		return this.valor;
	}

	public int getQtde() {
		return this.quantidade;
	}
	
	public Produto getProduto() {
		return this.produto;
	}

}
