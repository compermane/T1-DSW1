package br.ufscar.dc.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.ufscar.dc.dsw.services.spec.ILocadoraService;

@Controller
public class IndexController {
    @Autowired
    private ILocadoraService locadoraService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/index")
    public String index(@RequestParam(value = "cidade", required = false) String cidade, ModelMap model) {
        listarLocadoras(cidade, model);
        return "index";
    }

    public void listarLocadoras(String cidade, ModelMap model) {
        if(cidade == null) {
            model.addAttribute("locadoras", locadoraService.buscarTodos());
        }
    }
}