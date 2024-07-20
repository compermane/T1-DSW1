package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Usuario;

public class UsuarioDAO extends GeralDAO {
    public List<Usuario> getAll() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Usuario;";

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                String documento = resultSet.getString("documento");
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora");

                Usuario user = new Usuario(documento, email, senha, nome, admin, locadora);
                listaUsuarios.add(user);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return listaUsuarios;
    }

    public Usuario getUserByDocument(String document) {
        Usuario user = null;
        String sqlQuery = "SELECT * FROM Usuario WHERE documento = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            
            stmt.setString(1, document);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora"); 

                user = new Usuario(document, email, senha, nome, admin, locadora);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void insertUser(Usuario user) {
        String sqlQuery = "INSERT INTO Usuario (documento, email, senha, nome, isAdmin, isLocadora) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, user.getDocumento());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getNome());
            stmt.setBoolean(5, user.getAdmin());
            stmt.setBoolean(6, user.getLocadora());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    public void deleteUser(Usuario user) {
        String sqlQuery = "DELETE FROM Usuario WHERE documento = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, user.getDocumento());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
 
    public void updateUser(Usuario user) {
        String sqlQuery = "UPDATE Usuario SET email = ?, senha = ?, nome = ?, isAdmin = ?, isLocadora = ? WHERE documento = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getNome());
            statement.setBoolean(4, user.getAdmin());
            statement.setBoolean(5, user.getLocadora());
            statement.setString(6, user.getDocumento());

            statement.executeUpdate();
            statement.close();

            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}