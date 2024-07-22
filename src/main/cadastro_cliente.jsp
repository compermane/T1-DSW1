<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Cliente</title>
    <link rel="stylesheet" href="teste.css">
</head>
<body>
    <h1>Cadastro de Cliente</h1>
    <form action="CadastroCliente" method="post">
        <h1> POR FAVOR, INSIRA OS SEGUINTES DADOS:</h1>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="senha">Senha:</label>
        <input type="password" id="senha" name="senha" required><br>

        <label for="cpf">CPF:</label>
        <input type="text" id="cpf" name="cpf" required><br>

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required><br>

        <label for="telefone">Telefone:</label>
        <input type="text" id="telefone" name="telefone" required><br>

        <label for="sexo">Sexo:</label>
        <input type="text" id="sexo" name="sexo" required><br>

        <label for="dataNascimento">Data de Nascimento:</label>
        <input type="date" id="dataNascimento" name="dataNascimento" required><br>

        <input type="submit" value="Cadastrar">
    </form>
</body>
</html>
