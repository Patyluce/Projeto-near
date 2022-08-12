package com.near.ProjetoNear.models;


	import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

	@Entity
	@Table(name="loja")
	public class Loja implements Serializable{
		
		public Loja() {
				super();
		}
		
		private static final long serialVersionUID = 1L;
		
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Id
		private Long id;
		
		private String cnpj;
		
		private String nome;
		
		@Column(unique=true)
		@NotEmpty(message = "User's email cannot be empty.")
		private String email;
		
		@NotEmpty(message = "User's password cannot be empty.")
		private String senha;
		
		private String cep;
		
		private String categoria;
		
		private String bairro;
		
		private byte[] imagem;
		
		
		public byte[] getImagem() {
			return imagem;
		}
		public void setImagem(byte[] imagem) {
			this.imagem = imagem;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		public String getCnpj() {
			return cnpj;
		}
		public void setCnpj(String cnpj) {
			this.cnpj = cnpj;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getSenha() {
			return senha;
		}
		public void setSenha(String senha) {
			this.senha = senha;
		}
		public String getCep() {
			return cep;
		}
		public void setCep(String cep) {
			this.cep = cep;
		}
		public String getCategoria() {
			return categoria;
		}
		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}
		public String getBairro() {
			return bairro;
		}
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		
		
		
		
}

