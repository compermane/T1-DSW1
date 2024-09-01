<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<fmt:bundle basename="messages">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="cadastro.locadora" /></title>
    <link rel="stylesheet" href="teste.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="center-container">
        <h1><fmt:message key="cadastro.locadora" /></h1>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/signUp-locadora/insercao" method="post">
                <label for="email"><fmt:message key="login.email" /></label>
                <input class="registerInput" type="email" id="email" name="email" required><br>

                <label for="senha"><fmt:message key="login.senha" /></label>
                <input class="registerInput" type="password" id="senha" name="senha" required><br>

                <label for="cnpj"><fmt:message key="locadora.cnpj.label" /></label>
                <input class="registerInput" type="text" id="cnpj" name="cnpj" required><br>

                <label for="nome"><fmt:message key="locadora.nome.label" /></label>
                <input class="registerInput" type="text" id="nome" name="nome" required><br>

                <label for="cidade"><fmt:message key="locadora.cidade.label" /></label>
                <input class="registerInput" type="text" id="cidade" name="cidade" required><br>

                <button type="submit" value="Cadastrar"><fmt:message key="button.cadastrar"></fmt:message>
            </form>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
