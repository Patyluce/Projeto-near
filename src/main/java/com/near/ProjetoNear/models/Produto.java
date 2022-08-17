package com.near.ProjetoNear.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="produto")
public class Produto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Id
	private Long idproduto;
	private String nome;
	private Double valor;
	private String descricao;
	private Long idloja;
	

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getIdloja() {
		return idloja;
	}
	public void setIdloja(Long idloja) {
		this.idloja = idloja;
	}
	public Long getIdproduto() {
		return idproduto;
	}
	public void setIdproduto(Long idproduto) {
		this.idproduto = idproduto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
	
	
	
	
	
}
