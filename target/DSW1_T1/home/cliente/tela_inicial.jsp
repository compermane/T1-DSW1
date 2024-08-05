<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Página Inicial - Sistema de Locação de Bicicletas</title>
    <link rel="stylesheet" type="text/css" href="teste.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Sistema de Locação de Bicicletas</h1>
            <h2>Bem vindo <%= request.getAttribute("nomeUsuario") %></h2>
        </header>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Logout</a></li>
                <li><a href="${pageContext.request.contextPath}/cadastrar-locacao/">Cadastrar locação</a></li>
            </ul>
        </nav>
        <main>
            <h2>Bem-vindo ao Sistema de Locação de Bicicletas</h2>
            <p>Utilize os links acima para navegar pelas funcionalidades do sistema.</p>
            <div id = "locacoesTableContainer">
                <table id="locacoesTable">
                    <tr>
                        <th>Nome</th>
                        <th>Cidade</th>
                        <th>Dia</th>
                        <th>Horário</th>
                    </tr>
                    <c:forEach var="locacao" items="${sessionScope.listaLocacoes}">
                        <tr>
                            <td>${locacao.locadora.nome}</td>
                            <td>${locacao.locadora.cidade}</td>
                            <td>${locacao.dia}</td>
                            <td>${locacao.horario}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </main>
        <footer>
            <p>&copy; 2024 Sistema de Locação de Bicicletas</p>
        </footer>
    </div>
</body>
</html>
