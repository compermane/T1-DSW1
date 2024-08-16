package br.ufscar.dc.dsw.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ILocadoraService locadoraService;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("")
    public String getHome(Authentication authentication, Model model) {
         UsuarioDetails userDetails = (UsuarioDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        Usuario usuario = usuarioDAO.getUserByUsername(username);
        String nome = usuario.getNome();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        model.addAttribute("nome", nome);

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_CLIENTE")) {
                return "homeCliente"; 
            } else if (authority.getAuthority().equals("ROLE_LOCADORA")) {
                return "homeLocadora";
            }
        }
        
        return "home";
    }

    public void listarLocadoras(String cidade, ModelMap model) {
        if(cidade == null) {
            model.addAttribute("locadoras", locadoraService.buscarTodos());
        }
    }
}