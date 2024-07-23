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
        <form class = "loginForm" method = "post" action = "/DSW1_T1/indexController/login">
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
        </div>
    </div>
</body>
</html>