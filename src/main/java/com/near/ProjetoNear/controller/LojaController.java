package com.near.ProjetoNear.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.near.ProjetoNear.exceptions.ServiceExc;
import com.near.ProjetoNear.models.Loja;
import com.near.ProjetoNear.models.Produto;
import com.near.ProjetoNear.repository.LojaRepositorio;
import com.near.ProjetoNear.repository.ProdutoRepositorio;
import com.near.ProjetoNear.service.ServiceLoja;

@Controller
public class LojaController {
	
	@Autowired
	private LojaRepositorio lojaRepositorio;

	@Autowired
	private ProdutoRepositorio pr;

	@Autowired
	private ServiceLoja serviceLoja;

	@RequestMapping("/")
	public ModelAndView home(Loja busca) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("busca", busca);
		mv.setViewName("busca/home");
		return mv;
	}

	// Redirecionamento para a pagina de cadastro
	@GetMapping("/cadastro")
	public ModelAndView cadastroLoja(Loja cadastro) {
		ModelAndView mv = new ModelAndView("cadastro/cadastro");
		mv.addObject("cadastro", cadastro);
		return mv;
	}

	// Envio do dados digitados para o banco de dados
	@PostMapping("/cadastro/confirmar")

	public ModelAndView salvar(@Valid Loja cadastro, BindingResult result )
			throws Exception, IllegalStateException, IOException {
		
		if (result.hasErrors()) {
			return cadastroLoja(cadastro);
		}
		
		ModelAndView mv = new ModelAndView();
		serviceLoja.salvarLoja(cadastro);
		mv.setViewName("redirect:/login");
		return mv;

	}

	// Redirecionamento para Pagina de Edição de Perfil
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("loja/editar");
		Loja loja = lojaRepositorio.getReferenceById(id);
		mv.addObject("loja", loja);
		return mv;
	}

	// Confirmação da Edição
	@PostMapping("/editar/confirmar")
	public ModelAndView salvarEdicao(Loja editar, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		lojaRepositorio.editarLoja(editar.getNome(), editar.getCep(), editar.getCategoria(), editar.getBairro(),
				(Long) session.getAttribute("exibirLoja"));

		mv.setViewName("redirect:/loja");
		return mv;

	}

	// Redirecionamento para Pagina de Login
	@GetMapping("/login")
	public ModelAndView login(Loja login) {
		ModelAndView mv = new ModelAndView("cadastro/login");
		mv.addObject("login", login);
		return mv;
	}

	// Confirmação e Validação do Login e Criação da Sessão
	@PostMapping("/login")
	public ModelAndView confirmarLogin(@Valid Loja login, BindingResult br, HttpSession session)
			throws NoSuchAlgorithmException, ServiceExc {
		ModelAndView mv = new ModelAndView();
		mv.addObject("login", new Loja());
		if (br.hasErrors()) {

			mv.setViewName("cadastro/login");
		} else {

		}
//		System.out.println(login.getSenha());
//		System.out.println(Util.md5(login.getSenha()));
		Loja loginLoja = serviceLoja.loginLoja(login);
		if (loginLoja == null) {
			mv.setViewName("cadastro/login");
			mv.addObject("msg", "Email ou Senha não encontrados. Tente Novamente");
		} else {
			session.setAttribute("lojaLogada", loginLoja);
			session.setAttribute("exibirLoja", loginLoja.getId());
			mv.setViewName("redirect:/");
		}
		return mv;

	}

	// Opção de Deslogar
	@GetMapping("/deslogar")
	public ModelAndView deslogar(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		session.invalidate();
		mv.setViewName("redirect:/");
		return mv;
	}

	// Acesso a loja logada a sua loja
	@GetMapping("/loja")
	public ModelAndView acessarLoja(Loja loja, HttpSession session) {
		ModelAndView mv = new ModelAndView("loja/minhaloja");
		Long exibir = (Long) session.getAttribute("exibirLoja");
		Iterable<Loja> dadosLoja = lojaRepositorio.exibirLoja(exibir);
		Iterable<Produto> dadosProduto = pr.listarProduto(exibir);
	
		mv.addObject("dadosloja", dadosLoja);
		mv.addObject("dadosproduto", dadosProduto);
		mv.addObject("idLoja", exibir);
		return mv;

	}

	// Busca por bairro
	@PostMapping("/buscar")
	public ModelAndView busca(@RequestParam("buscar") String busca, Loja bairro) {
		ModelAndView mv = new ModelAndView("busca/categoria");
		bairro.setBairro(busca);
		mv.addObject("listaLoja", lojaRepositorio.buscarLojaporBairro(busca));
		mv.addObject("Bairro", bairro.getBairro());
		return mv;

	}

	// Busca por bairro e categoria
	@PostMapping("/categoria")
	public ModelAndView buscarCategoria(@RequestParam("categoria") String categoria,
			@RequestParam("bairro") String bairro) {
		ModelAndView mv = new ModelAndView("busca/categoria");
		mv.addObject("listaLoja", lojaRepositorio.buscarLojaporCategoria(bairro, categoria));
		return mv;
	}

	// Acesso a loja
	@GetMapping("/loja/{id}")
	public ModelAndView loja(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("loja/loja");
		Iterable<Loja> dadosLoja = lojaRepositorio.exibirLoja(id);
		Iterable<Produto> dadosProduto = pr.listarProduto(id);
		mv.addObject("dadosloja", dadosLoja);
		mv.addObject("dadosproduto", dadosProduto);
		return mv;
	}

	/* Area dos Produtos */

	// Redirecionamento para a pagina de registro de Produto
	@GetMapping("/produto/{id}")
	public ModelAndView produto(Produto produto, @PathVariable("id") Long idloja) {
		ModelAndView mv = new ModelAndView();
		produto.setIdloja(idloja);
		mv.addObject("produto", produto);
		mv.setViewName("produto/produto");
		return mv;
	}

	// Inserção no banco de dados
	@PostMapping("/produto/cadastrar")
	public ModelAndView salvarProduto(Produto produto, BindingResult br) {
		ModelAndView mv = new ModelAndView();
		if (br.hasErrors()) {
			mv.setViewName("redirect:/produto");
		}
		pr.save(produto);
		mv.setViewName("redirect:/loja");
		return mv;
	}

	// Redirecionamento para a pagina de edição de produto
	@GetMapping("/produto/editar/{id}")
	public ModelAndView editarProduto(@PathVariable("id") Long idproduto) {
		ModelAndView mv = new ModelAndView();
		Produto produto = pr.getReferenceById(idproduto);
		mv.addObject("produto", produto);
		mv.setViewName("produto/editar");
		return mv;
	}

	// Inserção das alterações no banco de dados
	@PostMapping("/produto/editar/confirmar")
	public ModelAndView editarProduto(Produto prod) {
		ModelAndView mv = new ModelAndView();
		pr.editarProduto(prod.getNome(), prod.getValor(), prod.getDescricao(), prod.getIdproduto());
		mv.setViewName("redirect:/loja");
		return mv;

	}

	// Opção de deletar um produto
	@GetMapping("/produto/deletar/{id}")
	public ModelAndView deletar(@PathVariable("id") Long idproduto) {
		ModelAndView mv = new ModelAndView();
		pr.deletarProduto(idproduto);
		mv.setViewName("redirect:/loja");
		return mv;
	}
	

}
