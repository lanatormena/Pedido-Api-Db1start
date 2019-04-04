package br.com.db1.pedidos.pedidosapi.domain;

import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pedido_historico")
public class Historico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "data", nullable = false)
	private LocalDateTime data;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 30, nullable = false)
	private StatusPedido statusPedido;
	
	@ManyToOne
	@JoinColumn(name ="id_pedido", referencedColumnName = "id")
	private Pedido pedido;
	
	public Historico(StatusPedido status, LocalDateTime dataHora) {
		this.statusPedido = status;
		this.data = dataHora;
	}

	public LocalDateTime getData() {
		return this.data;
	}

	public StatusPedido getStatus() {
		return this.statusPedido;
	}
}
