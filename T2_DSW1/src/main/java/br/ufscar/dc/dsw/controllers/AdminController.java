package br.ufscar.dc.dsw.controllers;


import org.springframework.security.core.Authentication;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.command.ClienteCommand;
import br.ufscar.dc.dsw.command.LocadoraCommand;
import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.ILocadoraDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.services.spec.IClienteService;
import br.ufscar.dc.dsw.services.spec.ILocadoraService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private ILocadoraService locadoraService;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IClienteDAO clienteDAO;

    @Autowired
    private ILocadoraDAO locadoraDAO;

    @GetMapping("/crud-cliente")
    public String getCrudCliente(Authentication authentication, Model model, HttpSession session) {
        model.addAttribute("clienteCommand", new ClienteCommand());
        getClientes(model);
        return "/admin/crudCliente";
    }

    @GetMapping("/crud-locadora")
    public String getCrudLocadora(Authentication authentication, Model model, HttpSession session) {
        model.addAttribute("locadoraCommand", new LocadoraCommand());
        getLocadoras(model);
        return "/admin/crudLocadora";
    }

    @PostMapping("/handle-crud-cliente")
    public String handleCrudCliente(@ModelAttribute("clienteCommand") ClienteCommand clienteCommand, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("[+] Método handleCrudCliente de AdminController executado");
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
                    if(!validateCpf(cliente.getCpf())) {
                        redirectAttributes.addFlashAttribute("errorMessage", "ja.cadastrado");
                        throw new Exception("CPF já cadastrado");
                    }
                    if(!validateEmail(cliente.getUsername())) {
                        redirectAttributes.addFlashAttribute("errorMessage", "ja.cadastrado");
                        throw new Exception("Email já cadastrado");
                    }
                    clienteService.salvar(cliente);
                }
                catch(Exception e) {
                    System.out.println("[-] Erro ao inserir cliente: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "cliente.error.create");

                    return "redirect:/admin/crud-cliente";
                }
                redirectAttributes.addFlashAttribute("successMessage", "cliente.success.create");
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
                    redirectAttributes.addFlashAttribute("errorMessage", "cliente.error.update");

                    return "redirect:/admin/crud-cliente";
                }
                redirectAttributes.addFlashAttribute("successMessage", "cliente.success.update");
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
                    redirectAttributes.addFlashAttribute("errorMessage", "cliente.error.delete");

                    return "redirect:/admin/crud-cliente";
                }
                redirectAttributes.addFlashAttribute("successMessage", "cliente.success.delete");
                break;

            default:
                System.out.println("[-] Não sei por que passou aqui");
                break;
        }

        return "redirect:/admin/crud-cliente";
    }

    @PostMapping("/handle-crud-locadora")
    public String handleCrudLocadora(@ModelAttribute("locadoraCommand") LocadoraCommand locadoraCommand, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("[+] Método handleCrudLocadora de AdminController executado");
        String action = locadoraCommand.getCrudAction();
        System.out.println("action: " + action);

        switch (action) {
            case "create":
                System.out.println("[+] Passou por 'create'");
                Locadora locadora = new Locadora();

                locadora.setCnpj(locadoraCommand.getCnpj());
                locadora.setNome(locadoraCommand.getNome());
                locadora.setCidade(locadoraCommand.getCidade());
                locadora.setUsername(locadoraCommand.getEmail());
                locadora.setPassword(locadoraCommand.getSenha());
                locadora.setRole("ROLE_CLIENTE");

                try {
                    if(!validateCnpj(locadora.getCnpj())) {
                        redirectAttributes.addFlashAttribute("errorMessage", "ja.cadastrado");
                        throw new Exception("CNPJ já cadastrado");
                    }
                    if(!validateEmail(locadora.getUsername())) {
                        redirectAttributes.addFlashAttribute("errorMessage", "ja.cadastrado");
                        throw new Exception("Email já cadastrado");
                    }
                    locadoraService.salvar(locadora);
                }
                catch(Exception e) {
                    System.out.println("[-] Erro ao inserir locadora: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "locadora.create.error");

                    return "redirect:/admin/crud-locadora";
                }
                redirectAttributes.addFlashAttribute("successMessage", "locadora.create.sucess");
                break;
            
            case "update":
                Long id = Long.parseLong(locadoraCommand.getId());
                Locadora locadoraUpdate = locadoraService.buscarPorID(id);

                String cpf = (locadoraCommand.getCnpj().equals("")) ? locadoraUpdate.getCnpj() : locadoraCommand.getCnpj();
                String nome = (locadoraCommand.getNome().equals("")) ? locadoraUpdate.getNome() : locadoraCommand.getNome();
                String cidade = (locadoraCommand.getCidade().equals("")) ? locadoraUpdate.getCidade() : locadoraCommand.getCidade();
                String username = (locadoraCommand.getEmail().equals("")) ? locadoraUpdate.getUsername() : locadoraCommand.getEmail();
                String password = (locadoraCommand.getSenha().equals("")) ? locadoraUpdate.getPassword() : locadoraCommand.getSenha();

                locadoraUpdate.setCnpj(cpf);
                locadoraUpdate.setNome(nome);
                locadoraUpdate.setCidade(cidade);
                locadoraUpdate.setUsername(username);
                locadoraUpdate.setPassword(password);

                try {
                    locadoraService.salvar(locadoraUpdate);
                } 
                catch (Exception e) {
                    System.out.println("[-] Erro ao atualizar locadora: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "locadora.update.error");

                    return "redirect:/admin/crud-locadora";
                }
                redirectAttributes.addFlashAttribute("successMessage", "locadora.update.success");
                break;
            
            case "delete":
                System.out.println("[+] Passou por 'delete'");
                System.out.println("id: " + locadoraCommand.getId());
                Long idDelete = Long.parseLong(locadoraCommand.getId());

                try {
                    clienteService.excluir(idDelete);
                } catch (Exception e) {
                    System.out.println("[-] Erro ao deletar cliente: " + e.getMessage());
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "locadora.delete.error");

                    return "redirect:/admin/crud-locadora";
                }
                redirectAttributes.addFlashAttribute("successMessage", "locadora.delete.success");
                break;

            default:
                System.out.println("[-] Não sei por que passou aqui");
                break;
        }

        return "redirect:/admin/crud-locadora";
    }

    public Boolean validateCpf(String cpf) {
        Cliente cliente = clienteDAO.findByCPF(cpf);

        if (cliente == null) {
            return true;
        }

        return false;
    }

    public Boolean validateCnpj(String cnpj) {
        Locadora locadora = locadoraDAO.findByCnpj(cnpj);

        if (locadora == null) {
            return true;
        }

        return false;
    }

    public Boolean validateEmail(String email) {
        Usuario usuario = usuarioDAO.getUserByUsername(email);

        if (usuario == null) {
            return true;
        }

        return false;
    }

    public void getClientes(Model model) {
        model.addAttribute("listaClientes", clienteService.buscarTodos());
    }

    public void getLocadoras(Model model) {
        model.addAttribute("listaLocadoras", locadoraService.buscarTodos());
    }
}