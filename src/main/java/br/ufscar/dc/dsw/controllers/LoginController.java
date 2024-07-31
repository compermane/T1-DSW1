package br.ufscar.dc.dsw.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.errors.Erro;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginController", urlPatterns = { "/loginController/*" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

		switch (action) {
			case "/login":
				handleLogin(request, response);
				break;
			
			default:
				doGet(request, response);
				break;
		}
    }

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erro erros = new Erro();

		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.getUserByEmail(email);

		if(usuario != null) {
			if(usuario.getPassword().equals(senha)) {
				request.getSession().setAttribute("usuarioLogado", usuario);

				response.sendRedirect("/DSW1_T1/tela_inicial/");

				return;
			} 
			else {
				erros.add("senha_invalida");
			}
		} 
		else {
			erros.add("usuario_nao_encontrado");
		}
	}
}