package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;

public class ClienteDAO extends GeralDAO {
    public List<Cliente> getAll() {
        List<Cliente> listaClientes = new ArrayList<>();

        // SELECT l.id_locadora, u.nome, u.senha, u.email, l.CNPJ, l.cidade,  u.isAdmin, u.isLocadora FROM Locadora l JOIN Usuario u ON l.id_locadora = u.id;";
        String sqlQuery = "SELECT c.id_cliente, u.nome, u.senha, u.email, c.CPF, c.sexo, c.telefone, c.data_nascimento, u.isAdmin, u.isLocadora FROM Cliente c JOIN Usuario u ON c.id_cliente = u.id;";

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                int id = resultSet.getInt("id_usuario");
                String documento = resultSet.getString("CPF");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                Date dataNascimento = resultSet.getDate("data_nascimento");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");

                Cliente cliente = new Cliente(id, documento, email, senha, nome, admin, isLocadora, telefone, sexo, dataNascimento);
                listaClientes.add(cliente);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return listaClientes;
    }

    public Cliente getClienteByID(int id) {
        Cliente cliente = null;
        String sqlQuery = "SELECT * FROM Cliente WHERE id_cliente = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String cpf = resultSet.getString("CPF");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                Date dataNascimento = resultSet.getDate("data_nascimento");

                cliente = new Cliente(id, cpf, email, senha, nome, admin, isLocadora, telefone, sexo, dataNascimento);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
     return cliente;
    }

    public Cliente getClienteByCPF(String CPF) {
        Cliente cliente = null;
        String sqlQuery = "SELECT * FROM Cliente WHERE CPF = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            
            stmt.setString(1, CPF);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_usuario");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                Date dataNascimento = resultSet.getDate("data_nascimento");

                cliente = new Cliente(id, CPF, email, senha, nome, admin, isLocadora, telefone, sexo, dataNascimento);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
     return cliente;
    }

    public void insertCliente(Cliente cliente){
        try {
            Connection conn = this.getConnection();

            String sqlQuery = "INSERT INTO Cliente (id_usuario, CPF, sexo, telefone, data_nascimento) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getDocumento());
            stmt.setString(3, cliente.getSexo());
            stmt.setString(4, cliente.getTelefone());
            stmt.setDate(5, cliente.getDataNascimento());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            System.out.println("\nCliente cadastrado com sucesso.");
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCliente(Cliente cliente) {
      
        try {
            Connection conn = this.getConnection();
            String sqlQuery = "UPDATE Cliente SET sexo = ?, telefone = ?, data_nascimento = ? WHERE CPF = ?;";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, cliente.getSexo());
            stmt.setString(2, cliente.getTelefone());
            stmt.setDate(3, cliente.getDataNascimento());
            stmt.setString(4, cliente.getDocumento());
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCliente(Cliente cliente) {
        String sqlQuery = "DELETE FROM Cliente WHERE CNPJ = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, cliente.getDocumento());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
