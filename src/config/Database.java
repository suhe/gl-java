/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author suhe
 */
public class Database {
    public Connection connection;

    /**
     * Is Connect default is false
     */
    public static boolean isConnect;
    private static String DbName = "gl";
    private static String DbHost = "localhost";
    private static String DbUser = "root";
    private static String DbPassword = "";
    private static Integer DbPort = 3306;
    
    public Connection getConnection() {
        return connection;
    }

    public boolean dbConnection() { //<-- untuk koneksi ke database  
        boolean isConnectDb;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //<-- nama driver untuk koneksi ke MySQL    
            try {
                String url, user, password;
                url = "jdbc:mysql://" + DbHost + ":" + DbPort + "/" + DbName + ""; //alamat DB
                user = DbUser;
                password = DbPassword;
                connection = DriverManager.getConnection(url, user, password);
                isConnectDb = true;
            } catch (SQLException e) {
                isConnectDb = false;
            }
        } catch (ClassNotFoundException cnfe) {
            JOptionPane.showMessageDialog(null, "Driver Jdbc Not Found" + cnfe,"Connect DB",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            isConnectDb = false;
        }
        
        return isConnectDb;
    }
    
    public static void setConnect(boolean connect) {  
        isConnect = connect;  
    }
    
    public static boolean getConenct() {  
        return isConnect;  
    }
    
    public static void SetDbName(String var) {
        DbName = var; 
    }
    
    public static String getDbName() {
        return DbName;
    }
    
    public static void SetDbUser(String var) {
        DbUser = var; 
    }
    
    public static String getDbUser() {
        return DbUser;
    }
    
    public static void SetDbHost(String var) {
        DbHost = var; 
    }
    
    public static String getDbHost() {
        return DbHost;
    }
    
    public static void SetDbPassword(String var) {
        DbPassword = var; 
    }
    
    public static String getDbPassword() {
        return DbPassword;
    }
    
    public static void SetDbPort(Integer var) {
        DbPort = var; 
    }
    
    public static Integer getDbPort() {
        return DbPort;
    }
   
}
