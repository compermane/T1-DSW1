package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Cliente;

public class ClienteDAO extends GeralDAO {
    public List<Cliente> getAll() {
        List<Cliente> listaClientes = new ArrayList<>();

        String sqlQuery = "SELECT * FROM Cliente;";

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                String documento = resultSet.getString("documento");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                String dataNascimento = resultSet.getString("data_nascimento");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");

                Cliente cliente = new Cliente(documento, email, senha, nome, admin, isLocadora, telefone, sexo, dataNascimento);
                listaClientes.add(cliente);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return listaClientes;
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
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                String dataNascimento = resultSet.getString("data_nascimento");

                cliente = new Cliente(CPF, email, senha, nome, admin, isLocadora, telefone, sexo, dataNascimento);
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
            String sqlQuery = "INSERT INTO Cliente (CPF, sexo, telefone, data_nascimento) VALUES (?, ?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, cliente.getDocumento());
            stmt.setString(2, cliente.getSexo());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getDataNascimento());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
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
            stmt.setString(3, cliente.getDataNascimento());
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
