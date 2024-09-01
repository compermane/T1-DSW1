<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<fmt:bundle basename="messages">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="locacao.cadastrar" /></title>
    <link rel="stylesheet" href="teste.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
</head>
<body>
    <div class="center-container">
        <div class="box-shadow">
            <h1><fmt:message key="locacao.cadastrar" /></h1>

            <c:if test="${not empty erroLocacao}">
                <c:forEach var="erro" items="${erroLocacao}">
                    <h3><fmt:message key="${erro}" /></h3>
                </c:forEach>
            </c:if>
            <c:set var="listaLocadoras" value="${sessionScope.listaLocadoras}" />
            <c:if test="${not empty listaLocadoras}">
                <form action="${pageContext.request.contextPath}/cadastrar-locacao/alugar" method="post">
                    <label for="labelLocadora"><fmt:message key="locacao.cadastrar.locadora" /></label>
                    <c:set var="listaLocadoras" value="${sessionScope.listaLocadoras}" />
                    <select id="locadoraSelectID" name="locadoraSelect" required>
                        <c:forEach items="${listaLocadoras}" var="locadora">
                            <option value="${locadora.documento}">${locadora.nome}</option>
                        </c:forEach>
                    </select><br>
            
                    <label for="dataLocacao"><fmt:message key="locacao.cadastrar.date" /></label>
                    <input type="date" id="dataLocacao" name="dataLocacao" required value="${locacao.dia}"><br>
                    <c:choose>
                        <c:when test="${erroLocacao != 'Horário indisponível'}">
                            <td><label for="hora"><fmt:message key="locacao.cadastrar.time" /></label></td>
                            <td><input type="time" id="horario" name="horario" step="3600" required /></td>
                            <c:set var="erroLocacao" value="" scope="request" />
                        </c:when>
                        <c:otherwise>
                            <td><label for="hora"><fmt:message key="locacao.cadastrar.time" /></label></td>
                            <td><input type="time" id="horario" class="horarioUsado" name="horario" step="3600" required /></td>
                        </c:otherwise>
                    </c:choose>
                    <button type="submit" value="Alugar"><fmt:message key="button.cadastrar"></fmt:message>
                </form>
            </c:if>
            <c:if test="${empty listaLocadoras}">
                <p>listaLocadoras está vazia ou é nula.</p>
            </c:if>
        </div>
    </div>
</body>
</fmt:bundle>
</html>
