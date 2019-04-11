/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.baruc.backend.crud.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author bromerov
 */
public class Database {

    private Connection conexion;
    private String usuario = "baruc";
    private String password = "123.pass";
    private String url = "jdbc:mysql://localhost:3306/crud";

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(url, usuario, password);
            System.out.println("Conecto a DB");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error " + e);
        }
    }

    public Connection getConexion() { 
       return conexion;
    }

    public Connection cerrarConexion() {
        try {
            conexion.isClosed();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return conexion = null;
    }

}
