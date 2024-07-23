<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Locadoras</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Lista de Locadoras</h1>
    <table>
        <thead>
            <tr>
                <th>Nome</th>
                <th>Cidade</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="locadora" items="${locadoras}">
                <tr>
                    <td>${locadora.nome}</td>
                    <td>${locadora.cidade}</td>
                    <td>${locadora.email}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
