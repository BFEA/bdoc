
package bup;

import bdoc.dbconnector;
import bdoc.mainBdoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Altynbek
 */
public class AddBupYears extends mainBdoc{
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public AddBupYears(){
        conn = dbconnector.getConnection();
    }
    
        
    public boolean isBupYearsExist(String yearStart, String yearEnd){
        
        Boolean isBupYearsExist = false;
        
        final String sql = "SELECT EXISTS (SELECT * FROM bup_years WHERE bup_years_start=? and bup_years_end=?)";
        
        try{
            
            pst=conn.prepareStatement(sql);
            pst.setString(1, yearStart);
            pst.setString(2, yearEnd);
            rs=pst.executeQuery();
            
            rs.next();
            if ( rs.getInt(1) != 0) {
                isBupYearsExist = true;
            }
            
            pst.close();
            rs.close();            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return isBupYearsExist; 
    }
    
    public Integer getLastIdBupYears(){
        
        Integer lastIdBupYears = 0;
        
        final String sql = "SELECT MAX(bup_years_id) AS id FROM bup_years";
        
        try{
            pst = conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                lastIdBupYears = rs.getInt("id");
            }            
            pst.close();
            rs.close();            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return lastIdBupYears; 
    }
    
    public Boolean insertBupYears(String bup_year_id, String bup_years_start, String bup_years_end){
        
        Boolean isCreated = false;
        
        try {
            
        final String sql = "INSERT INTO  bup_years (bup_years_id, bup_years_start ,bup_years_end) VALUES (?,?,?)";
                
            pst = conn.prepareStatement(sql);
                 
                pst.setString(1, bup_year_id);
                pst.setString(2, bup_years_start);
                pst.setString(3, bup_years_end);
                
                int check = pst.executeUpdate();
                
                if(check > 0){
                   isCreated = true;
                }else{
                    isCreated = false;
                }
                    
            pst.close();
            rs.close(); 
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return isCreated;
    }
    

    public static void main(String[] args) {
//        
//        String id ="2";
//        String a ="2016";
//        String b ="2017";
//        AddBupYears db = new AddBupYears();
//        Boolean is;
//        if(db.isBupYearsExist(a, b) != true){
//            is = db.insertBupYears(id, a, b);
//            System.out.println(is);            
//        }else{            
//            JOptionPane.showMessageDialog(db, "Запись с таким id уже существует!");
//        }
    }
    
}
