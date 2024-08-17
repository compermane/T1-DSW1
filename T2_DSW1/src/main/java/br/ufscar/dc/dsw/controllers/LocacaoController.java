package br.ufscar.dc.dsw.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.services.spec.ILocacaoService;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private ILocacaoService locacaoService;

    @Autowired
    private ILocadoraService locadoraService;
    
    @Autowired
    private IClienteService clienteService;
    
    @Autowired
    private IUsuarioDAO usuarioDAO;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/cadastrar-locacao")
    public String cadastrarLocacaoPage(ModelMap model) {
        model.addAttribute("locacao", new Locacao());
        listarLocadoras(model);
        horariosDisponiveis(model);
        return "locacoes/cadastrar";
    }

    public void horariosDisponiveis(ModelMap model) {
        List<String> horariosDisponiveis = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            String horaString = String.format("%02d:00:00", i);
            horariosDisponiveis.add(horaString);
        }

        model.addAttribute("horariosDisponiveis", horariosDisponiveis);
    }
    public void listarLocadoras(ModelMap model) {
        System.out.println(locadoraService.buscarTodos());
        model.addAttribute("locadoras", locadoraService.buscarTodos()); 
    }

    @PostMapping("/salvar")
    public String cadastrarLocacao(@Valid Locacao locacao, BindingResult result, RedirectAttributes attr) {
        System.out.println("[+] Método salvar de LocacaoController executado");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            Usuario usuario = usuarioDAO.getUserByUsername(email);
            Cliente cliente = clienteService.buscarPorID(usuario.getId());

            if (!validateLocacao(cliente, locacao)) {
                attr.addFlashAttribute("errorMessage", "dia.horario.alugados");
                throw new Exception("Dia e horário já alugados");
            }
            locacao.setCliente(cliente);
            locacaoService.salvar(locacao);
        }
        catch(Exception e) {
            System.out.println("[-] Erro ao cadastrar locadora: " + e.getMessage());
            e.printStackTrace();

            return "redirect:/locacoes/cadastrar-locacao";
        }
        
        attr.addFlashAttribute("sucessMessage", "locacao.create.sucess");
        return "redirect:/home";
    }

    public Boolean validateLocacao(Cliente cliente, Locacao locacao) {
        List<Locacao> listaLocacoes = locacaoService.buscarTodosPorCliente(cliente);

        for (Locacao Ilocacao : listaLocacoes) {
            if (Ilocacao.getHorario().equals(locacao.getHorario()) && Ilocacao.getDia().equals(locacao.getDia())) {
                return false;
            }
        }
        return true;
    }
}