package br.ufscar.dc.dsw.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.ufscar.dc.dsw.dao.LocadoraDAO;
// import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.LocacaoDAO;
import br.ufscar.dc.dsw.domain.Locadora;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Locacao;


@WebServlet(name = "LocacaoController", urlPatterns = { "/cadastrar-locacao/*" })
public class LocacaoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO daoCliente;
    private LocadoraDAO daoLocadora;
    private LocacaoDAO daoLocacao;

    @Override
    public void init() {
        daoCliente = new ClienteDAO();
        daoLocadora = new LocadoraDAO();
        daoLocacao = new LocacaoDAO();
    }

    @Override 
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if(action == null) {
            action = "";
        }

        switch (action) {
            case "/alugar":
                alugar(request, response);
                break;
        
            default:
                doGet(request, response);
                break;
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método get de LocacaoController executado");

        getLocadoras(request, response);
        getLocacoes(request, response);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/locacao_bicicleta.jsp");
        dispatcher.forward(request, response);
    }

    public void getLocacoes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método getLocadoras de LocacaoController executado");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
		List<Locacao> listaLocacoes = new LocacaoDAO().getAll(usuario.getId());
    	request.getSession().setAttribute("listaLocacoes", listaLocacoes);
    }

    public void getLocadoras(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[+] Método getLocadoras de LocacaoController executado");

		List<Locadora> listaLocadoras = new LocadoraDAO().getAll();
    	request.getSession().setAttribute("listaLocadoras", listaLocadoras);
    }

    public void alugar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            System.out.println(request.getAttribute("dataLocacao"));
            SimpleDateFormat reFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date data_sem_formatar = reFormat.parse(request.getParameter("dataLocacao"));
            java.sql.Date dataLocacao = new java.sql.Date(data_sem_formatar.getTime());

            String horarioString = request.getParameter("horario");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            java.util.Date horario_sem_formatar = timeFormat.parse(horarioString);
            java.sql.Time horario = new java.sql.Time(horario_sem_formatar.getTime());
            
            Locadora locadora = daoLocadora.getLocadoraByCNPJ(request.getParameter("locadoraSelect"));
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
            Locacao locacao = new Locacao(daoCliente.getClienteByID(usuario.getId()), locadora, dataLocacao, horario);
            
            if (!daoLocacao.existeLocacao(locadora.getCidade(), dataLocacao, horario)) {
                daoLocacao.insertLocacao(locacao);

                System.out.println("locadora email: " + locadora.getEmail());
                request.setAttribute("erroLocacao", "");
                request.setAttribute("locadoraParaEmail", locadora);
                request.setAttribute("locadoraEmail", locadora.getEmail());
                request.setAttribute("dataLocacao", request.getParameter("dataLocacao"));
                request.setAttribute("horarioLocacao", request.getParameter("horario"));

                RequestDispatcher dispatcher = request.getRequestDispatcher("/SendEmail");
                dispatcher.forward(request, response);
            }
            else {
                request.setAttribute("erroLocacao", "error.unavailable.time");
                getLocacoes(request, response);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/locacao_bicicleta.jsp");
                dispatcher.forward(request, response);
            }
            
        } 
        catch (ParseException | RuntimeException | IOException e) {
            throw new ServletException(e);
        }
    }
    }