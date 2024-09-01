<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>
    <fmt:bundle basename="messages">    
    <head>
        <title><fmt:message key="login.login" /></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    </head>
<body>
    <div class = "center-container">
        <c:if test="${not empty errorMessage}">
            <div class="erro">
                <c:forEach var="erro" items="${errorMessage}">
                    <h3>${erro}</h3>
                </c:forEach>
            </div>
        </c:if>
    
        <div class = "form-container">
        <form class = "loginForm" method = "post" action = "/DSW1_T1/loginController/login">
            <h1><fmt:message key="login.login" /></h1>
            <div class = "loginInput">
                <label><fmt:message key="login.email" /></label>
                <input type="email" name = "email"/>
            </div>
            <div class = "loginInput">
            <label><fmt:message key="login.senha" /></label>
            <input type="password" name = "senha" />
            </div>
            <div class = "form-footer">
            <button type="submit"><fmt:message key="login.login" /></button>
            <span><fmt:message key="no.account.log" /><a href="${pageContext.request.contextPath}/signUp-locadora"><fmt:message key="as.locadora" /></a>
                <a href="${pageContext.request.contextPath}/signUp-cliente"><fmt:message key="as.cliente" /></a></span>
            </div>
        </form>

        <h2 id = "listaLocadorasTitulo"><fmt:message key="index.locadora.list" /></h2>
        <select name = "selectCidade" id = "selectCidade">
            <option value = "vazio" selected style = "background-color: gray;"><fmt:message key="index.locadora.list" /></option>
            <!-- TODO: Adicionar cidades do banco como seleção -->
        </select>
        <div id = "locadorasTableContainer">
            <table id="locadorasTable">
                <tr>
                    <th><fmt:message key="index.nome.locadora" /></th>
                    <th><fmt:message key="index.cidade.locadora" /></th>
                </tr>
                <c:forEach var="locadora" items="${sessionScope.listaLocadoras}">
                    <tr>
                        <td>${locadora.nome}</td>
                        <td>${locadora.cidade}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
            loadCidades();

            console.log("PASSEI POR BTUH4");
        var selectCidade = document.getElementById('selectCidade');

        selectCidade.addEventListener('change', function () {
            var cidadeSelecionada = selectCidade.value;
            var xhr = new XMLHttpRequest();

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var locadorasTableContainer = document.getElementById('locadorasTableContainer');
                    locadorasTableContainer.innerHTML = xhr.responseText;
                }
            };

            xhr.open('GET', '${pageContext.request.contextPath}/filtrar_locadoras.jsp?cidadeSelecionada=' + cidadeSelecionada , true);
            xhr.send();
        });
    });

    function loadCidades() {
        var cidadeSelect = document.getElementById('selectCidade');
        var xhr = new XMLHttpRequest();

        try {
            xhr.open('GET', '${pageContext.request.contextPath}/cidades/cidades.txt', true);

            xhr.onreadystatechange = () => {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var cidades = xhr.responseText.split('\n');

                    for (var i = 0; i < cidades.length; i++) {
                        var option = document.createElement('option');
                        option.text = cidades[i];
                        cidadeSelect.add(option);
                    }
                }
            };

            xhr.send();
        }
        catch (error) {
            console.log(error)
        }
    }
</script>
</body>
</fmt:bundle>
</html>