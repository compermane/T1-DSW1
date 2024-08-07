package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufscar.dc.dsw.dao.LocacaoDAO;
import br.ufscar.dc.dsw.domain.Locacao;
import br.ufscar.dc.dsw.domain.Usuario;

@WebServlet(name = "HomeController", urlPatterns = { "/tela_inicial/*" })
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método get de HomeController executado");
        // Recuperar o usuário da sessão
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario != null) {
            // Definir o nome do usuário como atributo da requisição
            request.setAttribute("nomeUsuario", usuario.getNome());
            List<Locacao> listaLocacoes = new LocacaoDAO().getAll(usuario.getId());
            request.getSession().setAttribute("listaLocacoes", listaLocacoes);

            // Encaminhar para a página inicial
            if(usuario.getAdmin()) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/tela_inicial.jsp");
                dispatcher.forward(request, response);  
            }
            else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/home/cliente/tela_inicial.jsp");
                dispatcher.forward(request, response);
            }
        } 
        else {
            // Usuário não autenticado, redirecionar para a página de login
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
