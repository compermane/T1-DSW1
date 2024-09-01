<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<fmt:bundle basename="messages">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="admin.crud.locadora" /></title>
    <link rel="stylesheet" type="text/css" href="teste.css">
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
                <li><a href="${pageContext.request.contextPath}/admin/clientes"><fmt:message key="admin.crud.cliente" /></a></li>
                <li><a href="${pageContext.request.contextPath}/admin/locadoras"><fmt:message key="admin.crud.locadora" /></a></li>
            </ul>
            <div id = "clienteTableContainer">
                <table id="clienteTable">
                    <tr>
                        <th>ID</th>
                        <th><fmt:message key="admin.crud.email" /></th>
                        <th><fmt:message key="admin.crud.nome" /></th>
                        <th>CNPJ</th>
                        <th><fmt:message key="admin.crud.cidade" /></th>
                    </tr>

                    <c:forEach var="locadora" items="${sessionScope.listaLocadoras}">
                        <tr>
                            <td>${locadora.id}</td>
                            <td>${locadora.email}</td>
                            <td>${locadora.nome}</td>
                            <td>${locadora.documento}</td>
                            <td>${locadora.cidade}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <form class="crud" action="${pageContext.request.contextPath}/crud-locadora/handle-crud" method="post">
                <select name="crudSelectAction" required>
                    <option value=""><fmt:message key="admin.crud.selectAction" /></option>
                    <option value="create"><fmt:message key="admin.crud.create" /></option>
                    <option value="update"><fmt:message key="admin.crud.update" /></option>
                    <option value="delete"><fmt:message key="admin.crud.delete" /></option>
                </select>
                <select name="crudSelectId">
                    <option value=""><fmt:message key="admin.crud.selectID" /></option>
                    <c:forEach items="${listaLocadoras}" var="locadora">
                        <option value="${locadora.id}">${locadora.id}</option>
                    </c:forEach>
                </select>
                <label for="emailCrud">Email</label>
                <input type="email" name="emailCrud">
                <label for="senhaCrud"><fmt:message key="admin.crud.senha" /></label>
                <input type="password" name="senhaCrud">
                <label for="nomeCrud"><fmt:message key="admin.crud.nome" /></label>
                <input type="text" name="nomeCrud">
                <label for="documentoCrud">CNPJ</label>
                <input type="text" name="documentoCrud">
                <label for="cidadeCrud"><fmt:message key="admin.crud.cidade" /></label>
                <input type="text" name="cidadeCrud">

                <button type="submit" name="action" value="Executar"><fmt:message key="admin.crud.execute"></fmt:message>
            </form>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
