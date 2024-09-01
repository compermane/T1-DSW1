package br.ufscar.dc.dsw.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;

@Controller
public class IndexController {
    
    @Autowired
    private ILocadoraService locadoraService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/")
    public String index(@RequestParam(value = "cidade", required = false) String cidade, ModelMap model) {
        listarTodasLocadoras(model);

		if (cidade == null || cidade.equals("Todas")) {

            model.addAttribute("listaLocadoras", locadoraService.buscarTodos());
		} else {
			model.addAttribute("listaLocadoras", locadoraService.buscarPorCidade(cidade));
		}

        System.out.println("BRUH");
        return "index";
    }

    public void listarTodasLocadoras(ModelMap model) {
        List<Locadora> listaLocadoras = locadoraService.buscarTodos();
        List<String> listaCidades = new ArrayList<>();

        for (Locadora locadora : listaLocadoras) {
            listaCidades.add(locadora.getCidade());
        }

        Set<String> conjuntoSemDuplicatas = new HashSet<>(listaCidades);
        List<String> listaCidadesSemDuplicatas = new ArrayList<>(conjuntoSemDuplicatas);

        model.addAttribute("listaTodasLocadoras", listaCidadesSemDuplicatas);
    }
}