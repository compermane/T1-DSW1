<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Locações da Locadora</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Locações da Locadora</h1>
    <table>
        <thead>
            <tr>
                <th>Cliente</th>
                <th>Data e Hora</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="locacao" items="${locacoes}">
                <tr>
                    <td>${locacao.clienteNome}</td>
                    <td>${locacao.dataHora}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
