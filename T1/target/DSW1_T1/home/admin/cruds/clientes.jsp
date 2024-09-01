<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<fmt:bundle basename="messages">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="admin.crud.cliente" /></title>
    <!-- <link rel="stylesheet" type="text/css" href="teste.css"> -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="center-container" style="margin-top: 10vh; margin-left: 10vw; width: 80%;">
        <div class="box-shadow">
            <c:if test="${not empty mensagemErro}">
                <div class="erro">
                    <c:forEach var="erro" items="${mensagemErro}">
                        <h3><fmt:message key="${erro}" /></h3>
                    </c:forEach>
                </div>
            </c:if>
            <c:if test="${not empty mensagemSucesso}">
                <div class="erro">
                    <c:forEach var="erro" items="${mensagemSucesso}">
                        <h3><fmt:message key="${erro}" /></h3>
                    </c:forEach>
                </div>
            </c:if>
            <ul>
                <li><a href="${pageContext.request.contextPath}/">Logout</a></li>
                <li><a href="${pageContext.request.contextPath}/admin/cliente"><fmt:message key="admin.crud.cliente" /></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/locadora"><fmt:message key="admin.crud.locadora" /></a></li>
            </ul>
            <div id = "clienteTableContainer">
                <table id="clienteTable">
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="admin.crud.email" /></th>
                        <th><fmt:message key="admin.crud.nome" /></th>
                        <th><fmt:message key="admin.crud.cpf" /></th>
                        <th><fmt:message key="admin.crud.telefone" /></th>
                        <th><fmt:message key="admin.crud.sexo" /></th>
                        <th><fmt:message key="admin.crud.dataNascimento" /></th>
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
                    <option value=""><fmt:message key="admin.crud.selectAction" /></option>
                    <option value="create"><fmt:message key="admin.crud.create" /></option>
                    <option value="update"><fmt:message key="admin.crud.update" /></option>
                    <option value="delete"><fmt:message key="admin.crud.delete" /></option>
                </select>
                <select name="crudSelectId">
                    <option value=""><fmt:message key="admin.crud.selectID" /></option>
                    <c:forEach items="${listaClientes}" var="cliente">
                        <option value="${cliente.id}">${cliente.id}</option>
                    </c:forEach>
                </select>
                <label for="emailCrud"><fmt:message key="admin.crud.email" /></label>
                <input type="email" name="emailCrud">
                <label for="senhaCrud"><fmt:message key="admin.crud.senha" /></label>
                <input type="password" name="senhaCrud">
                <label for="nomeCrud"><fmt:message key="admin.crud.nome" /></label>
                <input type="text" name="nomeCrud">
                <label for="documentoCrud"><fmt:message key="admin.crud.cpf" /></label>
                <input type="text" name="documentoCrud">
                <label for="telefoneCrud"><fmt:message key="admin.crud.telefone" /></label>
                <input type="text" name="telefoneCrud">
                <select name="sexoCrud">
                    <option value=""><fmt:message key="admin.crud.selectSexo" /></option>
                    <option value="M"><fmt:message key="cliente.sexo.masc" /></option>
                    <option value="F"><fmt:message key="cliente.sexo.fem" /></option>
                </select>
                <label for="dataNascimentoCrud"><fmt:message key="admin.crud.dataNascimento" /></label>
                <input type="date" name="dataNascimentoCrud" value="0001-01-01">
                <select name="isAdminCrud">
                    <option value=""><fmt:message key="admin.crud.isAdmin" /></option>
                    <option value="true"><fmt:message key="admin.crud.yes" /></option>
                    <option value="false"><fmt:message key="admin.crud.no" /></option>
                </select>
                <button type="submit" name="action" value="Executar"><fmt:message key="admin.crud.execute"></fmt:message>
            </form>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
