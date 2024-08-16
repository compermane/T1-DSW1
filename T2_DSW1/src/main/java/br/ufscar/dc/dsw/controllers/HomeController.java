package br.ufscar.dc.dsw.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.dao.ILocadoraDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.services.spec.ILocacaoService;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ILocadoraService locadoraService;

    @Autowired
    private ILocacaoService locacaoService;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IClienteDAO clienteDAO;

    @Autowired
    private ILocadoraDAO locadoraDAO;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("")
    public String getHome(Authentication authentication, Model model, HttpSession session) {
        UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().toString();
        
        Usuario usuario = usuarioDAO.getUserByUsername(username);
        String nome = usuario.getNome();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        session.setAttribute("user_role", role);
        session.setAttribute("user_email", username);
        model.addAttribute("nome", nome);
        model.addAttribute("id_logado", usuario.getId());

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_CLIENTE")) {
                Optional<Cliente> optionalCliente = clienteDAO.findById(usuario.getId());

                if (optionalCliente.isPresent()) {
                    Cliente cliente = optionalCliente.get();
                    listarLocacoes(model, cliente);
                }

                return "/home/homeCliente"; 
            } 
            else if (authority.getAuthority().equals("ROLE_LOCADORA")) {
                Optional<Locadora> optionalLocadora = locadoraDAO.findById(usuario.getId());

                if (optionalLocadora.isPresent()) {
                    Locadora locadora = optionalLocadora.get();
                    listarLocacoesLocadora(model, locadora);
                }
                return "/home/homeLocadora";
            }
            else {
                return "/home/homeAdmin";
            }
        }
        
        return "home";
    }

    public void listarLocadoras(String cidade, ModelMap model) {
        if(cidade == null) {
            model.addAttribute("locadoras", locadoraService.buscarTodos());
        }
    }

    public void listarLocacoes(Model model, Cliente cliente) {
        model.addAttribute("listaLocacoes", locacaoService.buscarTodosPorCliente(cliente));
    }

    public void listarLocacoesLocadora(Model model, Locadora locadora) {
        model.addAttribute("listaLocacoesLocadora", locacaoService.buscarTodosPorLocadora(locadora));
    }
}