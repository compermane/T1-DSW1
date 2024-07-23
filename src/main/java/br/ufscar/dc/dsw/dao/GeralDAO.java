package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

abstract public class GeralDAO {
    public GeralDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() throws SQLException {
        String db_url = "jdbc:mysql://localhost:8080/T1_DSW1";
        try {
            return DriverManager.getConnection(db_url, "eugenio", "aA@12345");
        }
        catch(SQLException e) {
            System.out.println("Não foi possível conectar ao BD " + e);
            throw e;
        }
    }
}