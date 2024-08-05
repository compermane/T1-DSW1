package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.errors.Erro;

@WebServlet(urlPatterns = "/crud-cliente/*")
public class ClienteController extends HttpServlet {
    private static final long serialVersionUID = 1L;    
	private ClienteDAO daoCliente;
    private UsuarioDAO daoUsuario;

 @Override
    public void init() {
        daoCliente = new ClienteDAO();
        daoUsuario = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[+] Método doPost de ClienteController executado");
        String action = request.getParameter("crudSelectAction");
		Erro erros = new Erro();

		switch(action) {
			case "create":
				handleCreateCliente(request, response);
				break;
			case "update":
				handleUpdateCliente(request, response);
				break;
			case "delete":
				handleDeleteCliente(request, response);
				break;
			default:
				erros.add("acao_nao_reconhecida");
		}
    }

	public void handleCreateCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método handleCreateCliente de ClienteController executado");

		String nome = request.getParameter("nomeCrud");
		String email = request.getParameter("emailCrud");
		String senha = request.getParameter("senhaCrud");
		String cpf = request.getParameter("documentoCrud");
		String telefone = request.getParameter("telefoneCrud");
		String sexo = request.getParameter("sexoCrud");

		java.sql.Date dataNascimento;
		SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date dataNascimento_sem_formatar = reFormat.parse(request.getParameter("dataNascimentoCrud"));
			dataNascimento = new java.sql.Date(dataNascimento_sem_formatar.getTime());

		}
		catch(ParseException e) {
			throw new ServletException(e);
		}

		boolean isAdmin = false;
		String isAdminStr = request.getParameter("isAdminCrud");

		if(isAdminStr.equals("true")) {
			isAdmin = true;
		}

		try {
            if (daoUsuario.getUserByEmail(email) != null) {
                String mensagemErro = "O email já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/admin/clientes");
                dispatcher.forward(request, response);
                return;
            }

            if (daoCliente.getClienteByCPF(cpf) != null) {
                String mensagemErro = "O CPF já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);

                RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/admin/clientes");
                dispatcher.forward(request, response);
                return;
            }
			Usuario usuario = new Usuario(email, cpf, senha, nome, isAdmin, false);
            daoUsuario.insertUser(usuario);
            usuario = daoUsuario.getUserByEmail(email);

            Cliente cliente = new Cliente(usuario.getId(), cpf, email, senha, nome, 
                                          isAdmin, false, telefone, sexo, dataNascimento);
            daoCliente.insertCliente(cliente);

            System.out.println("\nCliente inserido com sucesso.\n");
            
            response.sendRedirect(request.getContextPath() + "/admin/clientes");
		}
		catch(RuntimeException | IOException e) {
            throw new ServletException(e);
		}
	}

	public void handleUpdateCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método handleUpdateCliente de ClienteController executado");
        request.setCharacterEncoding("UTF-8");
		
		if(request.getParameter("crudSelectId") == "") {
			request.setAttribute("mensagemErro", "Selecione um ID de cliente");
			return;
		}
		
		int idCliente = Integer.parseInt(request.getParameter("crudSelectId"));

        try {
            String cpf = request.getParameter("documentoCrud");
            String email = request.getParameter("emailCrud");

			System.out.println("cpf: " + cpf);
			Cliente clienteSelecionado = daoCliente.getClienteByID(idCliente);
			Usuario usuarioSelecionado = daoUsuario.getUserByID(idCliente);

            if(clienteSelecionado != null) {
                if(daoUsuario.getUserByEmail(email) != null && !daoUsuario.getUserByEmail(email).getEmail().equals(clienteSelecionado.getEmail())) {
                    String mensagemErro = "O email já está em uso.";
                    request.setAttribute("mensagemErro", mensagemErro);
                }
                if(daoCliente.getClienteByCPF(cpf) != null && !daoCliente.getClienteByCPF(cpf).getDocumento().equals(clienteSelecionado.getDocumento())) {
                    String mensagemErro = "O CPF já está em uso.";
                    request.setAttribute("mensagemErro", mensagemErro);
                }             
            }

            String senha = request.getParameter("senhaCrud");
            String nome = request.getParameter("nomeCrud");
			String sexo = request.getParameter("sexoCrud");

			java.sql.Date dataNascimento;
			SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date dataNascimento_sem_formatar = reFormat.parse(request.getParameter("dataNascimentoCrud"));
			dataNascimento = new java.sql.Date(dataNascimento_sem_formatar.getTime());

			LocalDate stdDate = LocalDate.of(0001, 01, 01);
			LocalDate dataNascimentoLocalDate = dataNascimento.toLocalDate();

			System.out.println("stdDate: " + stdDate + " dataNascimento: " + dataNascimentoLocalDate);
			if(dataNascimentoLocalDate.isBefore(stdDate) || dataNascimentoLocalDate.isAfter(stdDate)) {
				clienteSelecionado.setDataNascimento(dataNascimento);
			}
	
			String telefone = request.getParameter("telefoneCrud");
            String administrador = request.getParameter("isAdminCrud");
			System.out.println("admin: " +  administrador);
            boolean admin = false;

            if (administrador == null || administrador.equals("false")) {
				System.out.println("PASSOU A1UI1");
                admin = false;
            }
            else {
				System.out.println("PASSOU AQUI 2");
                admin = true;
            }
			System.out.println("sdmin2: " + admin);
			usuarioSelecionado.setAdmin(admin);
			if(email != "") {
            	usuarioSelecionado.setEmail(email);
			}
			if(senha != "") {
            	usuarioSelecionado.setSenha(senha);
			}
			if(nome != "") {
            	usuarioSelecionado.setNome(nome);
			}
			if(cpf != "") {
				usuarioSelecionado.setDocumento(cpf);
				clienteSelecionado.setDocumento(cpf);
			}
			if(sexo != "") {
				clienteSelecionado.setSexo(sexo);
			}
			if(telefone != "") {
				clienteSelecionado.setTelefone(telefone);
			}
			

            usuarioSelecionado.setAdmin(admin);
            daoUsuario.updateUser(usuarioSelecionado);
            daoCliente.updateCliente(clienteSelecionado);

            response.sendRedirect(request.getContextPath() + "/admin/clientes");
        } 
		catch (RuntimeException | IOException | ParseException e) {
            throw new ServletException(e);
        }
    }

	public void handleDeleteCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("crudSelectId") == "") {
			String mensagemErro = "Selecione um id de cliente.";
			request.setAttribute("mensagemErro", mensagemErro);
		}
		int idCliente = Integer.parseInt(request.getParameter("crudSelectId"));
		daoUsuario.deleteUser(daoCliente.getClienteByID(idCliente));

		response.sendRedirect("/clientes.jsp");
	}
}
