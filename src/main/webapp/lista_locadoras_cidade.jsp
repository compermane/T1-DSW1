<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Locadoras por Cidade</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Lista de Locadoras por Cidade</h1>
    <form action="ListarLocadorasPorCidade" method="get">
        <label for="cidade">Cidade:</label>
        <input type="text" id="cidade" name="cidade" required>
        <input type="submit" value="Buscar">
    </form>
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
