package br.ufscar.dc.dsw.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Date;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ufscar.dc.dsw.command.ClienteCommand;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.services.spec.IClienteService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IClienteService clienteService;

    // Instancia uma rota GET para o endereço "/"
    @GetMapping("/crud-cliente")
    public String getHome(Authentication authentication, Model model, HttpSession session) {
        model.addAttribute("clienteCommand", new ClienteCommand());
        getClientes(model);
        return "/admin/crudCliente";
    }

    @PostMapping("/handle-crud")
    public String handleCrud(@ModelAttribute("clienteCommand") ClienteCommand clienteCommand, Model model) {
        System.out.println("[+] Método handleCrud de AdminController executado");
        String action = clienteCommand.getCrudAction();
        System.out.println("action: " + action);

        switch (action) {
            case "create":
                System.out.println("[+] Passou por 'create'");
                Cliente cliente = new Cliente();

                cliente.setCpf(clienteCommand.getCpf());
                cliente.setNome(clienteCommand.getNome());
                cliente.setTelefone(clienteCommand.getTelefone());
                cliente.setUsername(clienteCommand.getEmail());
                cliente.setPassword(clienteCommand.getSenha());
                cliente.setDataNascimento(clienteCommand.getDataNascimento());
                cliente.setIsAdmin((clienteCommand.getIsAdmin().equals("true")) ? true : false);
                cliente.setSexo(clienteCommand.getSexo());
                cliente.setRole("ROLE_CLIENTE");

                try {
                    clienteService.salvar(cliente);
                }
                catch(Exception e) {
                    System.out.println("[-] Erro ao inserir cliente: " + e.getMessage());
                    e.printStackTrace();
                    
                    return "redirect:/admin/crud-cliente";
                }
                break;
            
            case "update":
                Long id = Long.parseLong(clienteCommand.getId());
                Cliente clienteUpdate = clienteService.buscarPorID(id);

                String cpf = (clienteCommand.getCpf().equals("")) ? clienteUpdate.getCpf() : clienteCommand.getCpf();
                String nome = (clienteCommand.getNome().equals("")) ? clienteUpdate.getNome() : clienteCommand.getNome();
                String telefone = (clienteCommand.getTelefone().equals("")) ? clienteUpdate.getTelefone() : clienteCommand.getTelefone();
                String username = (clienteCommand.getEmail().equals("")) ? clienteUpdate.getUsername() : clienteCommand.getEmail();
                String password = (clienteCommand.getSenha().equals("")) ? clienteUpdate.getPassword() : clienteCommand.getSenha();
                Date dataNascimento = (clienteCommand.getDataNascimento() == null) ? clienteUpdate.getDataNascimento() : clienteCommand.getDataNascimento();
                Boolean isAdmin = (clienteCommand.getIsAdmin().equals("")) ? clienteUpdate.getIsAdmin() : clienteCommand.getIsAdmin().equals("true");
                String sexo = (clienteCommand.getSexo().equals("")) ? clienteUpdate.getSexo() : clienteCommand.getSexo();

                clienteUpdate.setCpf(cpf);
                clienteUpdate.setNome(nome);
                clienteUpdate.setTelefone(telefone);
                clienteUpdate.setUsername(username);
                clienteUpdate.setPassword(password);
                clienteUpdate.setDataNascimento(dataNascimento);
                clienteUpdate.setIsAdmin(isAdmin);
                clienteUpdate.setSexo(sexo);

                try {
                    clienteService.salvar(clienteUpdate);
                } 
                catch (Exception e) {
                    System.out.println("[-] Erro ao atualizar cliente: " + e.getMessage());
                    e.printStackTrace();
                    
                    return "redirect:/admin/crud-cliente";
                }
                break;
            
            case "delete":
                System.out.println("[+] Passou por 'delete'");
                System.out.println("id: " + clienteCommand.getId());
                Long idDelete = Long.parseLong(clienteCommand.getId());

                try {
                    clienteService.excluir(idDelete);
                } catch (Exception e) {
                    System.out.println("[-] Erro ao deletar cliente: " + e.getMessage());
                    e.printStackTrace();
                    
                    return "redirect:/admin/crud-cliente";
                }
                break;

            default:
                System.out.println("[-] Não sei por que passou aqui");
                break;
        }

        return "redirect:/admin/crud-cliente";
    }

    public void getClientes(Model model) {
        model.addAttribute("listaClientes", clienteService.buscarTodos());
    }
}