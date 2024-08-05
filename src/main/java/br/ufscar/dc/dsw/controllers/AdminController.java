package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.errors.Erro;

@WebServlet(urlPatterns = {"/admin/*", "/admin/locadoras/*", "/admin/clientes/*"})
public class AdminController extends HttpServlet {

    private static final long serialVersionUID = 1L;    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
    	Erro erros = new Erro();

    	if(usuario == null) {
    		response.sendRedirect(request.getContextPath());
    	} 
        else if(usuario.getAdmin()) {
			String path = request.getServletPath();

			if(path.contains("locadora")) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/locadoras.jsp");
            	dispatcher.forward(request, response);
			}
			else if(path.contains("cliente")) {
				getClientes(request, response);
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/cruds/clientes.jsp");
            	dispatcher.forward(request, response);
			}
			else {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("/home/admin/tela_inicial.jsp");	
            	dispatcher.forward(request, response);
			}
    		
    	} 
		else {
    		erros.add("acesso_negado_adm");
    		request.setAttribute("mensagens", erros);
    		RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
    		rd.forward(request, response);
    	}
    }

	public void getClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método getClientes de AdminController executado");
		Erro erros = new Erro();

		List<Cliente> listaClientes = new ClienteDAO().getAll();
    	request.getSession().setAttribute("listaClientes", listaClientes);

		request.setAttribute("mensagens", erros);
	}
}