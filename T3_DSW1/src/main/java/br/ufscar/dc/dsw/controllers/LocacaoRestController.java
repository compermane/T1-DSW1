package br.ufscar.dc.dsw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.services.spec.ILocacaoService;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;

@RestController
public class LocacaoRestController {

	@Autowired
	private ILocacaoService locacaoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ILocadoraService locadoraService;

    // Lista todas as locações
    @GetMapping(path = "/locacoes")
	public ResponseEntity<List<Locacao>> lista() {
		List<Locacao> lista = locacaoService.buscarTodos();
		if (lista.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(lista);
	}
	
    //Lista as locações por ID
    @GetMapping(path = "/locacoes/{id}")
	public ResponseEntity<Locacao> listaPorId(@PathVariable("id") long id) {
    	Locacao locacao = locacaoService.buscarPorID(id);
		if (locacao == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(locacao);
	}

    // Lista as locações de um cliente por seu ID
	@GetMapping(path = "/locacoes/clientes/{id}")
	public ResponseEntity<List<Locacao>> listaPorCliente(@PathVariable("id") long id) {
        Cliente cliente = clienteService.buscarPorID(id);
		List<Locacao> listaClientes = locacaoService.buscarTodosPorCliente(cliente);

		if (listaClientes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(listaClientes);
	}

    // Lista as locações de uma locadora por seu ID
    @GetMapping(path = "/locacoes/locadoras/{id}")
	public ResponseEntity<List<Locacao>> listaPorLocadora(@PathVariable("id") long id) {
        Locadora locadora = locadoraService.buscarPorID(id);
		List<Locacao> listaLocadoras = locacaoService.buscarTodosPorLocadora(locadora);

		if (listaLocadoras.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(listaLocadoras);
	}
}
