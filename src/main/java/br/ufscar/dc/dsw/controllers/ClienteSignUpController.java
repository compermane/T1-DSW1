package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Cliente;

@WebServlet(urlPatterns = { "/signUp-cliente/*" })
public class ClienteSignUpController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO daoCliente;
    private UsuarioDAO daoUsuario;

    @Override
    public void init() {
        daoCliente = new ClienteDAO();
        daoUsuario = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if (action == null) {
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

                default:
                    apresentaFormCadastro(request, response);
                    break;
            }
        } 
        catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

    private void apresentaFormCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro/cadastro_cliente.jsp");
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
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro/cadastro_cliente.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String CPF = request.getParameter("cpf");

            // Verificar se o CPF já existe
            if (daoCliente.getClienteByCPF(CPF) != null) {
                String mensagemErro = "O CPF já está em uso.";
                request.setAttribute("mensagemErro", mensagemErro);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro/cadastro_cliente.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String nome = request.getParameter("nome");
            String senha = request.getParameter("senha");

            String administrador = request.getParameter("administrador");
            boolean admin = false;

            if (administrador == null || administrador == "0") {
                admin = false;
            }
            else {
                admin = true;
            }

            Usuario usuario = new Usuario(email, CPF, senha, nome, admin, false);
            daoUsuario.insertUser(usuario);
            
            System.out.println("\nUsuário inserido com sucesso.\n");

            String telefone = request.getParameter("telefone");
            String sexo = request.getParameter("sexo");
            SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            java.util.Date data_sem_formatar = reFormat.parse(request.getParameter("dataNascimento"));
            java.sql.Date dataNascimento = new java.sql.Date(data_sem_formatar.getTime());
            usuario = daoUsuario.getUserByEmail(email);

            Cliente cliente = new Cliente(usuario.getId(), CPF, email, senha, nome, 
                                          admin, false, telefone, sexo, dataNascimento);
            daoCliente.insertCliente(cliente);

            System.out.println("\nCliente inserido com sucesso.\n");
            
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } catch (ParseException | RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }
}