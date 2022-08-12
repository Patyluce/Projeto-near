package com.near.ProjetoNear.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.near.ProjetoNear.models.Loja;

@Transactional
@Repository
public interface LojaRepositorio extends JpaRepository<Loja, Long> {

	@Query(value = "select u from Loja u where u.id = :id")
	Iterable<Loja> exibirLoja(Long id);

	@Query(value = "select i from Loja i where i.email = :email")
	public Loja buscarEmail(String email);

	@Query(value = "select j from Loja j where j.email  = :email and j.senha = :senha")
	public Loja buscarLogin(String email, String senha);

	@Modifying
	@Query(value = "update Loja set nome = :nome, cep = :cep, categoria = :categoria, bairro = :bairro where id = :id ")
	public void editarLoja(String nome, String cep, String categoria, String bairro, Long id);
	
	@Query(value = "select b from Loja b where b.bairro like %?1%")
	Iterable<Loja> buscarLojaporBairro(String bairro);
	
	@Query(value = "select b from Loja b where b.bairro like %?1% and b.categoria like %?2%")
	List<Loja> buscarLojaporCategoria(String bairro, String categoria);

}
