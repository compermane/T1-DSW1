<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CRUD de Clientes</title>
    <link rel="stylesheet" type="text/css" href="teste.css">
</head>
<body>
    <div class="container">
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Logout</a></li>
                <li><a href="${pageContext.request.contextPath}/cadastrarLocacao">Cadastrar locação</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/cliente">CRUD de Clientes</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/locadora">CRUD de Locadoras</a></li>
            </ul>
        </nav>
        <main>
            <h2>Bem-vindo ao Sistema de Locação de Bicicletas</h2>
            <p>Utilize os links acima para navegar pelas funcionalidades do sistema.</p>
            <div id = "clienteTableContainer">
                <table id="clienteTable">
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Nome</th>
                        <th>CPF</th>
                        <th>Telefone</th>
                        <th>Sexo</th>
                        <th>Data de Nascimento</th>
                    </tr>

                    <c:forEach var="locacao" items="${sessionScope.listaClientes}">
                        <tr>
                            <td>${locacao.id}</td>
                            <td>${locacao.email}</td>
                            <td>${locacao.nome}</td>
                            <td>${locacao.documento}</td>
                            <td>${locacao.telefone}</td>
                            <td>${locacao.sexo}</td>
                            <td>${locacao.dataNascimento}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <form class="crud" action="${pageContext.request.contextPath}/crud-cliente/handle-crud" method="post">
                <select name="crudSelectAction" required>
                    <option value="">Selecione a ação</option>
                    <option value="create">Criar</option>
                    <option value="update">Atualizar</option>
                    <option value="delete">Deletar</option>
                </select>
                <select name="crudSelectId">
                    <option value="">Selecione o ID da locadora</option>
                    <c:forEach items="${listaLocadoras}" var="locadora">
                        <option value="${locadora.id}">${locadora.id}</option>
                    </c:forEach>
                </select>
                <label for="emailCrud">Email</label>
                <input type="email" name="emailCrud">
                <label for="senhaCrud">Senha</label>
                <input type="password" name="senhaCrud">
                <label for="nomeCrud">Nome</label>
                <input type="text" name="nomeCrud">
                <label for="documentoCrud">CNPJ</label>
                <input type="text" name="documentoCrud">
                <label for="cidadeCrud">Cidade</label>
                <input type="text" name="telefoneCrud">
                <select name="isAdminCrud">
                    <option value="">É admin?</option>
                    <option value="true">Sim</option>
                    <option value="false">Não</option>
                </select>
                <input type="submit" name="action" value="Executar">
            </form>
        </main>
        <footer>
            <p>&copy; 2024 Sistema de Locação de Bicicletas</p>
        </footer>
    </div>
</body>
</html>
