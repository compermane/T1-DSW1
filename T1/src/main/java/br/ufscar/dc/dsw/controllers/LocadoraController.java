package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.dao.LocadoraDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Locadora;

@WebServlet(urlPatterns = { "/crud-locadora/*" })
public class LocadoraController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private LocadoraDAO daoLocadora;
    private UsuarioDAO daoUsuario;

    @Override
    public void init() {
        daoLocadora = new LocadoraDAO();
        daoUsuario = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método doPost de LocadoraController executado");
        String action = request.getParameter("crudSelectAction");

        switch (action) {
            case "create":
                handleCreateLocadora(request, response);
                break;
            
            case "update":
                handleUpdateLocadora(request, response);
                break;

            case "delete":
                handleDeleteLocadora(request, response);
                break;
            default:
                break;
        }
    }

    private void handleCreateLocadora(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método handleCreateCliente de ClienteController executado");
		
		String nome = request.getParameter("nomeCrud");
		String email = request.getParameter("emailCrud");
		String senha = request.getParameter("senhaCrud");
		String cnpj = request.getParameter("documentoCrud");
		String cidade = request.getParameter("cidadeCrud");

		boolean isAdmin = false;
		String isAdminStr = request.getParameter("isAdminCrud");

		if(isAdminStr.equals("true")) {
			isAdmin = true;
		}

		try {
            if (daoUsuario.getUserByEmail(email) != null) {
                String mensagemErro = "error.email.already.inuse";
                request.setAttribute("mensagemErro", mensagemErro);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
				dispatcher.forward(request, response);
                return;
            }

            // Verificar se o CNPJ já existe
            if (daoLocadora.getLocadoraByCNPJ(cnpj) != null) {
                String mensagemErro = "error.cnpj.already.inuse";
                request.setAttribute("mensagemErro", mensagemErro);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
				dispatcher.forward(request, response);
                return;
            }

			Usuario usuario = new Usuario(email, cnpj, senha, nome, isAdmin, true);
            daoUsuario.insertUser(usuario);
            usuario = daoUsuario.getUserByEmail(email);

            Locadora locadora = new Locadora(usuario.getId(), cnpj, email, senha, nome, isAdmin, true, cidade);
            daoLocadora.insertLocadora(locadora);

            System.out.println("\nLocadora inserido com sucesso.\n");
            
            String mensagemSucesso = "locadora.create.success";
            request.setAttribute("mensagemSucesso", mensagemSucesso);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
            dispatcher.forward(request, response);
		}
		catch(RuntimeException | IOException e) {
            throw new ServletException(e);
		}
    }

    private void handleUpdateLocadora(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método handleUpdateLocadora de LocadoraController executado");
        request.setCharacterEncoding("UTF-8");
		
		int idLocadora = Integer.parseInt(request.getParameter("crudSelectId"));
        try {
            String cnpj = request.getParameter("documentoCrud");
            String email = request.getParameter("emailCrud");

			Locadora locadoraSelecionada = daoLocadora.getLocadoraByID(idLocadora);

            if(locadoraSelecionada != null) {
                // Verificar se o email já existe
                if(daoUsuario.getUserByEmail(email) != null && !daoUsuario.getUserByEmail(email).getEmail().equals(locadoraSelecionada.getEmail())) {
                    String mensagemErro = "error.email.already.inuse";
                    request.setAttribute("mensagemErro", mensagemErro);
                }
                // Verificar se o CNPJ já existe
                if(daoLocadora.getLocadoraByCNPJ(cnpj) != null && !daoLocadora.getLocadoraByCNPJ(cnpj).getDocumento().equals(locadoraSelecionada.getDocumento())) {
                    String mensagemErro = "error.cnpj.already.inuse";
                    request.setAttribute("mensagemErro", mensagemErro);
                }             
            }

            String senha = request.getParameter("senhaCrud");
            String nome = request.getParameter("nomeCrud");
            String cidade = request.getParameter("cidadeCrud");
            String administrador = request.getParameter("isAdminCrud");
            boolean admin = false;

            if (administrador == null || administrador == "false") {
                admin = false;
            }
            else if(administrador == "true") {
                admin = true;
            }

			if(email != "") {
            	locadoraSelecionada.setEmail(email);
			}
			if(senha != "") {
            	locadoraSelecionada.setSenha(senha);
			}
			if(nome != "") {
            	locadoraSelecionada.setNome(nome);
			}
			if(cnpj != "") {
				locadoraSelecionada.setDocumento(cnpj);
			}
			if(cidade != "") {
				locadoraSelecionada.setCidade(cidade);
			}

            locadoraSelecionada.setAdmin(admin);
            daoUsuario.updateUser(locadoraSelecionada);
            daoLocadora.updateLocadora(locadoraSelecionada);

            String mensagemSucesso = "locadora.update.success";
            request.setAttribute("mensagemSucesso", mensagemSucesso);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
            dispatcher.forward(request, response);
        } catch (RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }
    private void handleDeleteLocadora(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método handleDeleteLocadora de LocadoraController executado");

		int idLocadora = Integer.parseInt(request.getParameter("crudSelectId"));
		daoUsuario.deleteUser(daoLocadora.getLocadoraByID(idLocadora));

        request.setAttribute("mensagemSucesso", "locadora.delete.success");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
        dispatcher.forward(request, response);
    }
}