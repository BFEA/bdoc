
package bdoc;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class dbconnector extends LoginFrame {
     
    public Connection conn=null;
    
    public static Connection getConnection(){
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.10.3:3306/bdoc", "bdoc", "nmap_00_poi");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdoc", "root", "");
            return conn;
        }catch (ClassNotFoundException | SQLException | HeadlessException e){
            JOptionPane.showMessageDialog(null,"Соединение с БД\n не установлено!");
            return null;
        }
        
    }
    
    public static void close(Connection conn){
        try {
            conn.close();
        } catch (Exception e) {
            
        }
    }
}
