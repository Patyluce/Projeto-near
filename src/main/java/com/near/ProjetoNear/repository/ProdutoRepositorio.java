package com.near.ProjetoNear.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.near.ProjetoNear.models.Produto;

@Transactional
@Repository
public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
	
	@Query (value="select u from Produto u where u.idloja = :idloja")
	Iterable<Produto> listarProduto(Long idloja);
	
	@Query (value="select u from Produto u where u.idproduto = :idproduto")
	Produto editar(Long idproduto);
	
	@Modifying
	@Query(value = "update Produto set nome = :nome, valor = :valor, descricao = :descricao where idproduto = :idproduto ")
	public void editarProduto(String nome, double valor, String descricao, Long idproduto);
	
	@Modifying
	@Query(value = "delete from Produto where idproduto = :idproduto")
	public void deletarProduto(Long idproduto);
	
}
