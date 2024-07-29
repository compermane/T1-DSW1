<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html>
    <fmt:bundle basename="messages">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="menu_sistema" /></title>
    </head>
    <body>
        <% System.out.println("PASSEI POR: WEB-INF/logado/usuario/index.jsp"); %>
        <h1><fmt:message key="pagina_usuario" /></h1>
        <p><fmt:message key="ola" /> ${sessionScope.usuarioLogado.nome}</p>
        <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'C'}">
            <h1><fmt:message key="logado_cliente" /></h1>
            <div class="links">
                <a href="${pageContext.request.contextPath}/locacoes/cadastro"><fmt:message key="cadastrar_locacao" /></a>
                <a href="${pageContext.request.contextPath}/logout.jsp"><fmt:message key="sair" /></a>
            </div>
            <h2><fmt:message key="lista_locacoes" /></h2>
            <div id="locacoesTableContainer" align="center">
                <table id="locacoesTable" border="1">
                    <tr>
                        <th><fmt:message key="nome" /></th>
                        <th><fmt:message key="cidade" /></th>
                        <th><fmt:message key="data" /></th>
                        <th><fmt:message key="horario" /></th>
                        
                    </tr>
                    
                    <!-- Tabela com as locações do cliente logado -->
                    <c:forEach var="locacao" items="${sessionScope.listaLocacoes}">
                        <c:if test="${locacao.cliente.email == sessionScope.usuarioLogado.email}">
                            <c:choose>
                                <c:when test="${requestScope.dataAtualSistemaString > locacao.dia || requestScope.dataAtualSistemaString == locacao.dia && requestScope.horaAtualSistema.compareTo(locacao.horario) >= 0}">
                                    <tr class="locacaoExpirada">
                                        <td>${locacao.locadora.nome}</td>
                                        <td>${locacao.locadora.cidade}</td>
                                        <td>${locacao.dia}</td>
                                        <td>${locacao.horario}</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${locacao.locadora.nome}</td>
                                        <td>${locacao.locadora.cidade}</td>
                                        <td>${locacao.dia}</td>
                                        <td>${locacao.horario}</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                            
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </c:if>  
        <c:if test="${sessionScope.usuarioLogado.tipoUsuario == 'L'}">
            <h1><fmt:message key="logado_locadora" /></h1>
            <a href="${pageContext.request.contextPath}/logout.jsp"><fmt:message key="sair" /></a>
            <h2><fmt:message key="lista_locacoes" /></h2>
            <div id="locacoesTableContainer" align="center">
              
                <table id="locacoesTable" border="1">
                    <tr>
                        <th><fmt:message key="nome_cliente" /></th>
                        <th><fmt:message key="CPF_cliente" /></th>
                        <th><fmt:message key="data" /></th>
                        <th><fmt:message key="horario" /></th>
                    </tr>
                    <!-- Tabela com as locações da locadora logada -->
                    <c:forEach var="locacao" items="${sessionScope.listaLocacoes}">
                        <c:if test="${locacao.locadora.email == sessionScope.usuarioLogado.email}">
                            
                            
                            <!-- Se for verdadeiro, a locação já expirou -->
                            <c:choose>
                                <c:when test="${requestScope.dataAtualSistemaString > locacao.dia || requestScope.dataAtualSistemaString == locacao.dia && requestScope.horaAtualSistema.compareTo(locacao.horario) >= 0}">
                                    <tr class="locacaoExpirada">
                                        <td>${locacao.cliente.nome}</td>
                                        <td>${locacao.cliente.CPF}</td>
                                        <td>${locacao.dia}</td>
                                        <td>${locacao.horario}</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${locacao.cliente.nome}</td>
                                        <td>${locacao.cliente.CPF}</td>
                                        <td>${locacao.dia}</td>
                                        <td>${locacao.horario}</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </table>
            </div> 
        </c:if>
    </body>
</fmt:bundle>
</html>