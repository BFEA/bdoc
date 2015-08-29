package DAO;

import bdoc.dbconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Altynbek
 * 
 */
public class DepartmentDAO {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public DepartmentDAO(){
        conn = dbconnector.getConnection();
    }
    
    private String getDepartmentName(String departmentId){
    
        String depName = "";
        
        return depName;
        
    }
    
    private String getDepartmentId(String departmentName){
        
        String departmentId = "";
        
        return departmentId;
    }
          
    public List<String> getDepartmentNames(){
        
        List<String> depNamelist = new ArrayList<String>();
        
        String sql= "SELECT depart_name FROM departments ORDER BY depart_name ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("depart_name");
                
                if ( dc== null) {
                    depNamelist.add("");
                }
                depNamelist.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }

        return depNamelist;        
    }
    
    private List<String> getDepartmenId(){
        
        List<String> depIdlist = new ArrayList<String>();
        
        String sql= "SELECT depart_code FROM departments ORDER BY depart_code ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("depart_name");
                
                if ( dc== null) {
                    depIdlist.add("");
                }
                depIdlist.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return depIdlist;        
    }
    
}
