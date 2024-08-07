<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Locadora</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Cadastro de Locadora</h1>
    <form action="${pageContext.request.contextPath}/signUp-locadora/insercao" method="post">
        <h1> POR FAVOR, INSIRA OS SEGUINTES DADOS:</h1>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required><br>

        <label for="cnpj">CNPJ:</label>
        <input type="text" id="cnpj" name="cnpj" required><br>

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required><br>

        <label for="cidade">Cidade:</label>
        <input type="text" id="cidade" name="cidade" required><br>

        <input type="submit" value="Cadastrar">
    </form>
</body>
</html>
