package br.com.db1.pedidosapi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "codigo", length = 50, nullable = false, unique =  true)
	private String codigo;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@Column(name = "valor", precision =16 , nullable = false)
	private Double valor;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", length = 30 , nullable = false)
	private StatusProduto status;

	//Construtor
	public Produto(String codigo, String nome, Double valor) {
		this.codigo = codigo;
		this.nome = nome;
		this.valor = valor;
		this.status = StatusProduto.ATIVO;
	}

	public String getNome() {
		return nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		if (this.status == StatusProduto.ATIVO) {
			return true;
		} else {
			return false;
		}
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setStatus(StatusProduto status) {
		this.status = status;
	}

}
