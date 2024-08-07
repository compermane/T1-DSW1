package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.sql.Time;
import java.time.LocalTime;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.errors.Erro;

@WebServlet(urlPatterns = { "/usuario/*" })
public class UsuarioController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		java.sql.Date dataAtualSistema = new java.sql.Date(System.currentTimeMillis());

		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		LocalTime horaAtualSistema = LocalTime.of(hour, 0, 0);
		Time sqlTime = Time.valueOf(horaAtualSistema);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dataAtualSistemaString = dateFormat.format(dataAtualSistema);
		
		request.setAttribute("horaAtualSistema", sqlTime);
		request.setAttribute("dataAtualSistemaString", dataAtualSistemaString);
			

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		Erro erros = new Erro();

		if(usuario == null) {
			response.sendRedirect(request.getContextPath());
		} 
		else if (usuario.getAdmin()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logado/usuario/index.jsp");
			dispatcher.forward(request, response);

		} 
		else {
			erros.add("acesso_negado_usuario");
			request.setAttribute("mensagens", erros);
			RequestDispatcher rd = request.getRequestDispatcher("/noAuth.jsp");
			rd.forward(request, response);
		}
	}
}