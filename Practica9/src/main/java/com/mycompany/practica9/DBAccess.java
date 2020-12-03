package com.mycompany.practica9;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.*;

public class DBAccess {
    
    private final String serverName, dataBase, userName, password;
    private final String[] types = {"TABLE"};
    private Connection con;
    private DatabaseMetaData md;

    public DBAccess(String serverName, String dataBase, String userName, String password) {
        this.serverName = serverName;
        this.dataBase = dataBase;
        this.userName = userName;
        this.password = password;
    }

    public void connectDatabase() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://"+serverName+"/"+dataBase+"?useSSL=true",userName,password);
        md = con.getMetaData();
    }
    
    public ArrayList<String> getTableNames() throws SQLException{
        ArrayList<String> tablesNames = new ArrayList<>(); 
        ResultSet rs = md.getTables(null, null, "%", types);

        while (rs.next()) {
            tablesNames.add(rs.getString("TABLE_NAME"));    
        }       
        return tablesNames;
    }
    
    public ArrayList<String> getColumnsNames(String[] names) throws SQLException{
        ArrayList<String> columnsNames = new ArrayList<>();
        
        for (String name : names) {
            ResultSet rs2 = md.getColumns(null, null, name, null);
            while (rs2.next()) {
                StringBuilder campo = new StringBuilder(name);
                campo.append(".").append(rs2.getString("COLUMN_NAME"));
                columnsNames.add(campo.toString());
            }
        }
        return columnsNames;
    }
    
    public void closeConnection() throws SQLException{
        con.close();
    }
}
