<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Locação de Bicicleta</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Locação de Bicicleta</h1>

    <c:set var="listaLocadoras" value="${sessionScope.listaLocadoras}" />
    <c:if test="${not empty listaLocadoras}">
        <form action="${pageContext.request.contextPath}/cadastrar-locacao/alugar" method="post">
            <label for="labelLocadora">Locadora:</label>
            <c:set var="listaLocadoras" value="${sessionScope.listaLocadoras}" />
            <select id="locadoraSelectID" name="locadoraSelect" required>
                <c:forEach items="${listaLocadoras}" var="locadora">
                    <option value="${locadora.documento}">${locadora.nome}</option>
                </c:forEach>
            </select><br>
    
            <label for="dataLocacao">Data e Hora:</label>
            <input type="date" id="dataLocacao" name="dataLocacao" required value="${locacao.dia}"><br>
            <c:choose>
                <c:when test="${erroLocacao != 'Horário indisponível'}">
                    <td><label for="hora">Horario</label></td>
                    <td><input type="time" id="horario" name="horario" step="3600" required /></td>
                    <c:set var="erroLocacao" value="" scope="request" />
                </c:when>
                <c:otherwise>
                    <td><label for="hora">Horario</label></td>
                    <td><input type="time" id="horario" class="horarioUsado" name="horario" step="3600" required /></td>
                </c:otherwise>
            </c:choose>
            <input type="submit" value="Alugar">
        </form>
    </c:if>
    <c:if test="${empty listaLocadoras}">
        <p>listaLocadoras está vazia ou é nula.</p>
    </c:if>
</body>
</html>
