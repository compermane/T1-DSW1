<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Minhas Locações</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Minhas Locações</h1>
    <table>
        <thead>
            <tr>
                <th>Locadora</th>
                <th>Data e Hora</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="locacao" items="${locacoes}">
                <tr>
                    <td>${locacao.locadoraNome}</td>
                    <td>${locacao.dataHora}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
