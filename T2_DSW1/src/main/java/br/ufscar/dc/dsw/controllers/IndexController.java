package br.ufscar.dc.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.services.spec.ILocadoraService;

@Controller
public class IndexController {
    @Autowired
    private ILocadoraService locadoraService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/")
    public String index(@RequestParam(value = "cidade", required = false) String cidade, ModelMap model) {
        listarTodasLocadoras(model);
        System.out.println("CIDADE: " + cidade);
		if (cidade == null || cidade.equals("Todas")) {
			model.addAttribute("listaLocadoras", locadoraService.buscarTodos());
		} else {
            System.out.println("BRUH:" + locadoraService.buscarPorCidade(cidade));
			model.addAttribute("listaLocadoras", locadoraService.buscarPorCidade(cidade));
		}

        return "index";
    }

    public void listarTodasLocadoras(ModelMap model) {
        model.addAttribute("listaTodasLocadoras", locadoraService.buscarTodos());
    }
}