package br.ufscar.dc.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.domain.Cliente;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/signUp-cliente")
    public String index(Cliente cliente) {
        return "clientes/signUp-cliente";
    }

    @PostMapping("/salvar")
    public String cadastrarCliente(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
        System.out.println("[+] Método cadastrarCliente executado");
        try {
            cliente.setRole("ROLE_CLIENTE");
            clienteService.salvar(cliente);
        }
        catch(Exception e) {
            System.out.println("[-] Erro ao cadastrar cliente: " + e.getMessage());
            attr.addFlashAttribute("errorMessage", "cliente.error.create");
            e.printStackTrace();

            return "redirect:/clientes/signUp-cliente";
        }
        
        attr.addFlashAttribute("sucessMessage", "cliente.success.create");
        return "redirect:/";
    }

    @PostMapping("/editar")
    public String editarLocadora(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
        return "index";
    }

}