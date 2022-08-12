package com.near.ProjetoNear.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.near.ProjetoNear.exceptions.CriptoExistException;
import com.near.ProjetoNear.exceptions.EmailExistsException;
import com.near.ProjetoNear.exceptions.ServiceExc;
import com.near.ProjetoNear.models.Loja;
import com.near.ProjetoNear.repository.LojaRepositorio;
import com.near.ProjetoNear.util.Util;

@Service
public class ServiceLoja {
	
	@Autowired
	private LojaRepositorio lojaRepositorio;

	public void salvarLoja(Loja loja) throws Exception {
		try {
			
			if(lojaRepositorio.buscarEmail(loja.getEmail()) != null) {
				throw new EmailExistsException("Esse Email já está cadastrado");
			}
			
			
			loja.setSenha(Util.md5(loja.getSenha()));
			
			
		} catch (NoSuchAlgorithmException e) {
			
			throw new CriptoExistException("Erro na criptografia da senha");
			
		}
		
		lojaRepositorio.saveAndFlush(loja);
	}
	
	public Loja loginLoja (Loja loja) throws ServiceExc, NoSuchAlgorithmException {
		Loja loginLoja = lojaRepositorio.buscarLogin(loja.getEmail(), Util.md5(loja.getSenha()));
		return loginLoja;
		
	}
	
	
	
	
}
