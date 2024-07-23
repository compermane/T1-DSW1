package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Locadora;

public class LocadoraDAO extends GeralDAO {
    public List<Locadora> getAll() {
        List<Locadora> listaLocadoras = new ArrayList<>();

        String sqlQuery = "SELECT l.id_locadora, u.nome, u.senha, u.email, l.CNPJ, l.cidade,  u.isAdmin, u.isLocadora FROM Locadora l JOIN Usuario u ON l.id_locadora = u.id;";

        try {
            Connection conn = this.getConnection();
            Statement stmt = conn.createStatement();

            ResultSet resultSet = stmt.executeQuery(sqlQuery);

            while(resultSet.next()) {
                int id = resultSet.getInt("id_locadora");
                String documento = resultSet.getString("CNPJ");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");

                Locadora locadora = new Locadora(id, documento, email, senha, nome, admin, isLocadora, cidade);
                listaLocadoras.add(locadora);
            }
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return listaLocadoras;
    }

    public Locadora getLocadoraByCNPJ(String CNPJ) {
        Locadora locadora = null;
        String sql = "SELECT * FROM Locadora WHERE CNPJ = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, CNPJ);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_locadora");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, CNPJ, email, senha, nome, admin, isLocadora, cidade);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
     return locadora;
    }

    public Locadora getLocadoraByID(int id) {
        Locadora locadora = null;
        String sql = "SELECT * FROM Locadora WHERE id_locadora = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String documento = resultSet.getString("CNPJ");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                boolean admin = resultSet.getBoolean("isAdmin");
                boolean isLocadora = resultSet.getBoolean("isLocadora");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, documento, email, senha, nome, admin, isLocadora, cidade);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } 
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
     return locadora;
    }

    public void insertLocadora(Locadora locadora){
        try {
            Connection conn = this.getConnection();
            String sqlQuery = "INSERT INTO Locadora (id_locadora, CNPJ, cidade) VALUES (?, ?, ?);";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setInt(1, locadora.getId());
            stmt.setString(2, locadora.getDocumento());
            stmt.setString(3, locadora.getCidade());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLocadora(Locadora locadora) {
        try {
            Connection conn = this.getConnection();
            String sqlQuery = "UPDATE Locadora SET cidade = ? WHERE CNPJ = ?;";
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);
            stmt.setString(1, locadora.getCidade());
            stmt.setString(2, locadora.getDocumento());
            stmt.executeUpdate();
            
            stmt.close();
            conn.close();
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteLocadora(Locadora locadora) {
        String sqlQuery = "DELETE FROM Locadora WHERE CNPJ = ?;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlQuery);

            stmt.setString(1, locadora.getDocumento());
            stmt.executeUpdate();

            stmt.close();
            conn.close();
        }
        catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
