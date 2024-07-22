<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Locação de Bicicleta</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Locação de Bicicleta</h1>
    <h1> POR FAVOR, INSIRA OS SEGUINTES DADOS:</h1>

    <form action="LocacaoBicicleta" method="post">
        <label for="locadora">Locadora:</label>
        <select id="locadora" name="locadora" required>
            <c:forEach var="locadora" items="${locadoras}">
                <option value="${locadora.cnpj}">${locadora.nome}</option>
            </c:forEach>
        </select><br>

        <label for="dataHora">Data e Hora:</label>
        <input type="datetime-local" id="dataHora" name="dataHora" required><br>

        <input type="submit" value="Alugar">
    </form>
</body>
</html>
