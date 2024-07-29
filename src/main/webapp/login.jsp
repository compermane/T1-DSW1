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

        <!-- Locadoras -->
        <h2 id = "listaLocadorasTitulo">Lista de Locadoras</h2>
        <select name = "selectCidade" id = "selectCidade">
            <option value = "vazio" selected style = "background-color: gray;">Selecionar cidade</option>
        </select>
        <div id = "locadorasTableContainer">
            <table id="locadorasTable">
                <tr>
                    <th>Nome</th>
                    <th>Cidade</th>
                </tr>
                <!-- Popule a tabela com todas as locadoras inicialmente -->
                <c:forEach var="locadora" items="${sessionScope.listaLocadoras}">
                    <tr>
                        <td>${locadora.nome}</td>
                        <td>${locadora.cidade}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
<script src="scripts/listaLocadoras.js">
function loadCidades() {
    console.log("PASSEI POR BRUH2")
    var cidadeSelect = document.getElementById('selectCidade');
    var xhr = new XMLHttpRequest();

    try {
        xhr.open('GET', './cidades.txt', true);

        console.log("PASSEI POR BRUH 3")
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
        console.log("BRUH219")
    }
}
</script>
</body>
</html>