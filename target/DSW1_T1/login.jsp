<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>

<html>

<head>
    <title>Locadora de Bicicletas - Login</title>
</head>

<body>
    <c:if test="${not empty errorMessage}">
        <div class="erro">
            <c:forEach var="erro" items="${errorMessage}">
                <h3>${erro}</h3>
            </c:forEach>
        </div>
    </c:if>

    <div class = "center-container">
        <div class = "form-container">
        <form class = "loginForm" method = "post" action = "/DSW1_T1/loginController/login">
            <h1>Login</h1>
            <div class = "loginInput">
                <label>Email</label>
                <input type="email" name = "email"/>
            </div>
            <div class = "loginInput">
            <label>Senha</label>
            <input type="password" name = "senha" />
            </div>
            <div class = "form-footer">
            <button type="submit">Login</button>
            <span>Não tem uma conta? Cadastre-se<a href="${pageContext.request.contextPath}/signUp-locadora"> como uma locadora</a> 
                ou <a href="${pageContext.request.contextPath}/signUp-cliente">como um cliente</a></span>
            </div>
        </form>

        <h2 id = "listaLocadorasTitulo">Lista de Locadoras</h2>
        <select name = "selectCidade" id = "selectCidade">
            <option value = "vazio" selected style = "background-color: gray;">Selecionar cidade</option>
            <!-- TODO: Adicionar cidades do banco como seleção -->
        </select>
        <div id = "locadorasTableContainer">
            <table id="locadorasTable">
                <tr>
                    <th>Nome</th>
                    <th>Cidade</th>
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
</html>