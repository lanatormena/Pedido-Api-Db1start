package br.com.db1.pedidos.pedidosapi.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//rever nome
	@Column(name = "codigo", nullable = false, length = 50)
	private String numero;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 50)
	private StatusPedido status;
	
	@Column(name = "data_status", nullable = false)
	private LocalDateTime data;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
	private Cliente cliente;
	
	// diferente do maiko
	private Double valorTotal;
	
	@OneToMany(mappedBy="pedido", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Historico> historico = new ArrayList<>();
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PedidoItem> itens = new ArrayList<>();

	public Pedido(String numero, Cliente cliente, List<PedidoItem> itens) {
		this.numero = numero;
		validaCliente(cliente);
		aberturaPedido();
		validaQtdeItens(itens);
		calculaValorTotal(itens);
	}

	private void calculaValorTotal(List<PedidoItem> listItens) {
		valorTotal = 0.0;
		for (int i = 0; i < listItens.size(); i++) {
			valorTotal = valorTotal + (listItens.get(i).getQtde() * listItens.get(i).getValor());
		}
	}

	public double getValorTotal() {
		return this.valorTotal;
	}

	public String getNumero() {
		return numero;
	}

	public LocalDateTime getData() {
		Historico h1 = this.historico.get(0);
		LocalDateTime data = h1.getData();
		return data;
	}

	public List<PedidoItem> getItens() {
		return itens;
	}

	private void validaCliente(Cliente cliente) {
		if (cliente.getStatus() == StatusCliente.ATIVO) {
			this.cliente = cliente;
		} else {
			throw new RuntimeException("Status do cliente: " + cliente.getStatus());
		}
	}

	private void validaQtdeItens(List<PedidoItem> listItens) {
		if (listItens.size() > 10 || listItens.size() < 0) {
			throw new RuntimeException("Quantidade de itens diferente de 1 a 10, quantidade: " + listItens.size());
		} else {
			this.itens = listItens;
		}
	}

	private void aberturaPedido() {
		Historico abertura = new Historico(StatusPedido.ABERTO, LocalDateTime.now());
		this.historico.add(abertura);
	}

	public StatusPedido getStatus() {
		Historico h = this.historico.get(historico.size() - 1);
		return h.getStatus();
	}
	
	public LocalDateTime getDataUltimaAlteracao() {
		Historico h = this.historico.get(historico.size() - 1);
		LocalDateTime data = h.getData();
		return data;
	}

	public void setStatus(StatusPedido status) {
		if (this.getStatus() != StatusPedido.ABERTO) {
			throw new RuntimeException("Pedido econtra-se: " + this.getStatus());
		} else {
			if (status == StatusPedido.FATURADO) {
				this.status = status;
				Historico faturamento = new Historico(StatusPedido.FATURADO, LocalDateTime.now());
				this.historico.add(faturamento);
			}
			if (status == StatusPedido.CANCELADO) {
				Historico cancelamento = new Historico(StatusPedido.CANCELADO, LocalDateTime.now());
				this.historico.add(cancelamento);
			}
		}
	}

	public void relatorio1() {
		System.out.println("N�mero do pedido: " + this.getNumero());
		System.out.println("Data do pedido: " + this.getData());
		System.out.println("Status: " + this.getStatus());
		System.out.println("Data �ltima altera��o no status: " + this.getDataUltimaAlteracao());
		System.out.println("Total de itens do pedido: " + this.getItens().size());
		System.out.println("Valor total do pedido: " + this.getValorTotal());
	}
	
	public void relatorio2() {
		this.relatorio1();
		List<PedidoItem> listItens = this.itens;
		System.out.println("-------------------------");
		System.out.println("ITENS DO PEDIDO: ");
		Double totalPorItem = 0.0;
		for (int i = 0; i < listItens.size(); i++) {
			totalPorItem = (listItens.get(i).getValor()*listItens.get(i).getQtde());
			System.out.println("C�digo: " + listItens.get(i).getProduto().getCodigo());
			System.out.println("Descri��o: " + listItens.get(i).getProduto().getNome());
			System.out.println("Valor: " + listItens.get(i).getValor());
			System.out.println("Quantidade: " + listItens.get(i).getQtde());
			System.out.println("Total: " + totalPorItem);	
			System.out.println("------------");
		}
		
	}
}