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

@WebServlet(urlPatterns = { "/locadoras/*" })
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
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

        if(usuario == null || !usuario.getAdmin()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin");
            dispatcher.forward(request, response);
            return;
        }

        String action = request.getPathInfo();

        if(action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "/cadastro":
                    apresentaFormCadastro(request, response);
                    break;
                case "/insercao":
                    insere(request, response);
                    break;
                case "/remocao":
                    remove(request, response);
                    break;
                case "/edicao":
                    apresentaFormEdicao(request, response);
                    break;
                case "/atualizacao":
                    atualize(request, response);
                    break;
                default:
                    lista(request, response);
                    break;
            }
        } 
        catch (RuntimeException | IOException | ServletException e) {

            throw new ServletException(e);
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("listaLocadoras", daoLocadora.getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/locadora/lista.jsp");
        dispatcher.forward(request, response);
    }


    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("listaLocadoras", new LocadoraDAO().getAll());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/locadora/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void apresentaFormEdicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Locadora locadora = daoLocadora.getLocadoraByID(id);

        request.getSession().setAttribute("locadoraSelecionadaCRUD", locadora);
        request.setAttribute("locadora", locadora);
        request.getSession().setAttribute("listaLocadoras",  new LocadoraDAO().getAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/locadora/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String email = request.getParameter("email");

            // Verificar se o email já existe
            if (daoUsuario.getUserByEmail(email) != null) {
                String mensagemErro = "O email já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/locadora/formulario.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String cnpj = request.getParameter("cnpj");
            // Verificar se o CPF já existe
            if (daoLocadora.getLocadoraByCNPJ(cnpj) != null) {
                String mensagemErro = "O CNPJ já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/locadora/formulario.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String senha = request.getParameter("senha");
            String nome = request.getParameter("nome");

            boolean admin;
            String administrador = request.getParameter("administrador");

            if (administrador == null || administrador == "0") {
                admin = false;
            } 
            else {
                admin = true;
            }

            Usuario usuario = new Usuario(email, cnpj, senha, nome, admin, true);
            daoUsuario.insertUser(usuario);

            usuario = daoUsuario.getUserByEmail(email);
            String cidade = request.getParameter("cidade");
            Locadora locadora = new Locadora(usuario.getId(), cnpj, email, senha, nome, admin, true, cidade);
            daoLocadora.insertLocadora(locadora);
            response.sendRedirect("lista");
        } catch (RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }

    private void atualize(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            String cnpj = request.getParameter("cnpj");
            String email = request.getParameter("email");
            Locadora locadoraSelecionada = (Locadora) request.getSession().getAttribute("locadoraSelecionadaCRUD");

            if(locadoraSelecionada != null) {
                Usuario usuarioSelecionado = daoUsuario.getUserByID(locadoraSelecionada.getId());

                if(usuarioSelecionado != null) {
                    // Verificar se o email já existe
                    if(daoUsuario.getUserByEmail(email) != null && !daoUsuario.getUserByEmail(email).getEmail().equals(usuarioSelecionado.getEmail())) {
                        String mensagemErro = "O email já está em uso.";
                        request.setAttribute("mensagemErro", mensagemErro);
                        apresentaFormEdicao(request, response);
                    }
                    // Verificar se o CNPJ já existe
                    if(daoLocadora.getLocadoraByCNPJ(cnpj) != null && !daoLocadora.getLocadoraByCNPJ(cnpj).getDocumento().equals(locadoraSelecionada.getDocumento())) {
                        String mensagemErro = "O CNPJ já está em uso.";
                        request.setAttribute("mensagemErro", mensagemErro);
                        apresentaFormEdicao(request, response);
                    }
                }
                
            }
            

            String senha = request.getParameter("senha");
            String nome = request.getParameter("nome");

            String administrador = request.getParameter("administrador");
            boolean admin = false;

            if (administrador == null || administrador == "0") {
                admin = false;
            }
            else if(administrador == "1") {
                admin = true;
            }

            Usuario usuario = daoUsuario.getUserByID(Integer.parseInt(request.getParameter("id")));

            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setNome(nome);
            usuario.setAdmin(admin);
            daoUsuario.updateUser(usuario);

            String cidade = request.getParameter("cidade");
            Locadora locadora = daoLocadora.getLocadoraByID(usuario.getId());

            locadora.setDocumento(cnpj);
            locadora.setCidade(cidade);

            daoLocadora.updateLocadora(locadora);
            response.sendRedirect("lista");
        } catch (RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }

    private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Usuario usuario = daoUsuario.getUserByID(Integer.parseInt(request.getParameter("id")));
            daoUsuario.deleteUser(usuario);
            response.sendRedirect("lista");
        } catch (RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }
}