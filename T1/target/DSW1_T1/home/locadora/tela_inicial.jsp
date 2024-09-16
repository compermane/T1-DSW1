<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<fmt:bundle basename="messages">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="sistema.locacao.bicicletas" /></title>
    <link rel="stylesheet" type="text/css" href="teste.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="center-container">
        <div class="box-shadow">
            <h1><fmt:message key="sistema.locacao.bicicletas" /></h1>
            <h2><fmt:message key="usuario.home.welcome"/>!</h2>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Logout</a></li>
            </ul>
            <div id = "locacoesTableContainer">
                <table id="locacoesTable">
                    <tr>
                        <th><fmt:message key="cliente.home.nome" /></th>
                        <th><fmt:message key="cliente.telefone.label" /></th>
                        <th><fmt:message key="cliente.home.dia" /></th>
                        <th><fmt:message key="cliente.home.horario" /></th>
                    </tr>
                    <c:if test="${empty sessionScope.listaLocacoesLocadora}">
                        <p>Nenhuma locação encontrada.</p>
                    </c:if>
                    <c:forEach var="locacao" items="${sessionScope.listaLocacoesLocadora}">
                        <tr>
                            <td>${locacao.cliente.nome}</td>
                            <td>${locacao.cliente.telefone}</td>
                            <td>${locacao.dia}</td>
                            <td>${locacao.horario}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
