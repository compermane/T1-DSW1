package br.ufscar.dc.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import br.ufscar.dc.dsw.domain.Locadora;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/signUp-cliente")
    public String index(Locadora locadora) {
        return "clientes/signUp-cliente";
    }

    @PostMapping("/salvar")
    public String cadastrarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
        try {
            locadora.setRole("ROLE_LOCADORA");
            locadoraService.salvar(locadora);
        }
        catch(Exception e) {
            System.out.println("[-] Erro ao cadastrar locadora: " + e.getMessage());
            e.printStackTrace();
        }
        
        attr.addFlashAttribute("sucess", "locadora.create.sucess");
        return "redirect:/locadoras/listar";
    }

    @PostMapping("/editar")
    public String editarLocadora(@Valid Locadora locadora, BindingResult result, RedirectAttributes attr) {
        return "index";
    }

}