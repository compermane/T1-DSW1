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
            <h2><fmt:message key="usuario.home.welcome" /> <%= request.getAttribute("nomeUsuario") %>!</h2>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Logout</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/clientes"><fmt:message key="crud.cliente.title" /></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/locadoras"><fmt:message key="crud.locadora.title" /></a></li>
            </ul>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
