package com.grupa1.teleman.networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import com.mysql.jdbc.Driver;

public class JdbcConnection {
    private Connection connection;

    public JdbcConnection(String IP, String PORT, String database, String username, String password){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties props = new Properties();
            props.setProperty("user", username);
            props.setProperty("password", password);
            connection =  DriverManager.getConnection("jdbc:mysql://"+IP+":"+PORT+"/"+database, props);
            int x=0;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){
        try {
            ResultSet result = connection.createStatement().executeQuery(query);
            if(result.isBeforeFirst()) return result;
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
