package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import bdoc.dbconnector;

/**
 *
 * @author Altynbek
 * 
 */
public class DisciplineDAO {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public DisciplineDAO(){
        
        conn = dbconnector.getConnection();
        
    }
    
    public String getDisciplineName(String disciplineCode){
        
        String departmentName = "";
        
        return departmentName;
    }
    
    public  List<String> getDisciplineNameList(){
        
        List<String> discNameList = new ArrayList<String>();
        
        String sql= "SELECT disc_name FROM spr_discipline ORDER BY disc_name ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("disc_name");
                
                if ( dc== null) {
                    discNameList.add("");
                }
                discNameList.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return discNameList;
    }
    
    public String getDisciplineCode(String discName){
        
        String disciplineCode = "";
        final String sql = "SELECT disc_code FROM spr_discipline WHERE disc_name = ?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, discName);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("disc_code");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                disciplineCode = dc;
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return disciplineCode;
    }
    
    public List<String> getDisciplineCodeList(){
                        
        List<String> discCodeList = new ArrayList<String>();
        
        String sql= "SELECT disc_code FROM spr_discipline ORDER BY disc_code ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("disc_code");
                
                if ( dc== null) {
                    discCodeList.add("");
                }
                discCodeList.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return discCodeList;
    }
    
//    public static void main(String[] args) {
//        DisciplineDAO de = new DisciplineDAO();
//        String grd = de.getDisciplineCode("Excell обеспечение финансового менеджмента");
//        
//        System.out.println(grd);
        
        
//        for (int i = 0; i < grd.size(); i++) {
//            System.out.println(grd.get(i));
//        }
//    }
}
