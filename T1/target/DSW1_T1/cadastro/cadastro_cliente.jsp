<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <fmt:bundle basename="messages">
    <meta charset="UTF-8">
    <title><fmt:message key="cliente.cadastrar.label" /></title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="center-container">
    <h1><fmt:message key="cliente.cadastrar.label" /></h1>
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/signUp-cliente/insercao" method="post">
                <label for="email"><fmt:message key="login.email" /></label>
                <input class="registerInput" type="email" id="email" name="email" required><br>

                <label for="senha"><fmt:message key="usuario.password.label" /></label>
                <input class="registerInput" type="password" id="senha" name="senha" required><br>

                <label for="cpf"><fmt:message key="cliente.cpf.label" /></label>
                <input class="registerInput" type="text" id="cpf" name="cpf" required><br>

                <label for="nome"><fmt:message key="cliente.home.nome" /></label>
                <input class="registerInput" type="text" id="nome" name="nome" required><br>

                <label for="telefone"><fmt:message key="cliente.telefone.label" /></label>
                <input class="registerInput" type="text" id="telefone" name="telefone" required><br>

                <label for="sexo"><fmt:message key="cliente.sexo.label" /></label>
                <select id="sexo" name="sexo" required>
                    <option value=""><fmt:message key="cliente.sexo.placeholder" /></option>
                    <option value="M"><fmt:message key="cliente.sexo.masc" /></option>
                    <option value="F"><fmt:message key="cliente.sexo.fem" /></option>
                </select>

                <label for="dataNascimento"><fmt:message key="cliente.dataNascimento.label" /></label>
                <input class="registerInput" type="date" id="dataNascimento" name="dataNascimento" required><br>

                <button type="submit" value="Cadastrar"><fmt:message key="sidebar.link.cadastrar" />
            </form>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
