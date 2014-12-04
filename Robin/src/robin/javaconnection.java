package robin;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Robbin Williams (Team 2)
 */
public class javaconnection {
    
    Connection conn = null;
    
    public static Connection ConnecrDB(){
       
        try {
            //if sql lite "com.mysql.jbdc.Diver" "org.sqlite.JDBC"
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:Robbie.sqlite");
            return conn;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
