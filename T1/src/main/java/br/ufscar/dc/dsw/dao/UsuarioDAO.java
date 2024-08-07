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
                int id = resultSet.getInt("id");
                String documento = resultSet.getString("documento");
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora");

                Usuario user = new Usuario(id, documento, email, senha, nome, admin, locadora);
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
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora"); 

                user = new Usuario(id, document, email, senha, nome, admin, locadora);
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

    public Usuario getUserByID(int id) {
        Usuario user = null;
        String sqlQuery = "SELECT * FROM Usuario WHERE id = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                String documento = resultSet.getString("documento");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                String email = resultSet.getString("email");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora"); 

                user = new Usuario(id, documento, email, senha, nome, admin, locadora);
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

    public Usuario getUserByEmail(String email) {
        Usuario user = null;
        String sqlQuery = "SELECT * FROM Usuario WHERE email = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()) {
                int id = resultSet.getInt("id");
                String documento = resultSet.getString("documento");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean locadora = resultSet.getBoolean("isLocadora"); 

                user = new Usuario(id, documento, email, senha, nome, admin, locadora);
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
            stmt.setBoolean(6, user.getIsLocadora());
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
        String sqlQuery = "UPDATE Usuario SET email = ?, senha = ?, nome = ?, isAdmin = ?, isLocadora = ?, documento = ? WHERE id = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getNome());
            stmt.setBoolean(4, user.getAdmin());
            stmt.setBoolean(5, user.getIsLocadora());
            stmt.setString(6, user.getDocumento());
            stmt.setInt(7, user.getId());

            stmt.executeUpdate();
            stmt.close();

            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}