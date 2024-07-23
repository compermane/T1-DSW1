package br.ufscar.dc.dsw.controllers;

// import java.util.List;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
// import br.ufscar.dc.dsw.domain.Locadora;
// import br.ufscar.dc.dsw.domain.Locacao;
// import br.ufscar.dc.dsw.dao.LocadoraDAO;
// import br.ufscar.dc.dsw.dao.LocacaoDAO;
import br.ufscar.dc.dsw.errors.Erro;

// Login e afins
@WebServlet(name = "Index", urlPatterns = { "/indexController/*" })
public class IndexController extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getPathInfo();
		System.out.println("BRUH 5000");

		switch (action) {
			case "/login":
				handleLogin(request, response);
				break;
		
			default:
				doGet(request, response);
				break;
		}
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Erro erros = new Erro();

		System.out.println("BRUH1");
		System.out.println("BRUH2");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		System.out.println("EMAIL: " + email);
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario = dao.getUserByEmail(email);

		if(usuario != null) {
			System.out.println("aaaijdwao");
			if(usuario.getPassword().equals(senha)) {
				request.getSession().setAttribute("usuarioLogado", usuario);
				System.out.println("BRUH: " + request.getSession().getAttribute("usuarioLogado"));

				if(usuario.getAdmin()) {
					response.sendRedirect("/tela_inicial/");
				} 
				else {
					response.sendRedirect("/DSW1_T1/tela_inicial/");
				}

				return;
			} 
			else {
				erros.add("senha_invalida");
			}
		} 
		else {
			System.out.println("BRUH29810");
			erros.add("usuario_nao_encontrado");
		}

		// List<Locadora> listaLocadoras = new LocadoraDAO().getAll();
    	// request.getSession().setAttribute("listaLocadoras", listaLocadoras);
		// List<Locacao> listaLocacoes = new LocacaoDAO().getAll();

        // request.getSession().setAttribute("listaLocacoes", listaLocacoes);
		// request.setAttribute("mensagens", erros);
	}
}