package br.ufscar.dc.dsw.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.services.spec.IClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cliente cliente) {
		return "cliente/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("clientes",clienteService.buscarTodos());
		return "cliente/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "cliente/cadastro";
		}

		System.out.println("password = " + cliente.getPassword());
		
		cliente.setPassword(encoder.encode(cliente.getPassword()));
		clienteService.salvar(cliente);
		attr.addFlashAttribute("sucess", "cliente.create.sucess");
		return "redirect:/clientes/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", clienteService.buscarPorID(id));
		return "cliente/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "cliente/cadastro";
		}

		System.out.println(cliente.getPassword());
		
		clienteService.salvar(cliente);
		attr.addFlashAttribute("sucess", "cliente.edit.sucess");
		return "redirect:/clientes/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		clienteService.excluir(id);
		model.addAttribute("sucess", "cliente.delete.sucess");
		return listar(model);
	}
}