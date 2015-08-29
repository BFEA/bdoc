package DAO;

import bdoc.dbconnector;
import bdoc.mainBdoc;
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

public class BupDAO {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public BupDAO(){
        conn = dbconnector.getConnection();
    }
    
    private Integer getBupIdByDisc(String discCode){
  
        Integer bupid = 0;
        
        final String sql = "SELECT bup_year_id_auto FROM bup_year WHERE bup_dis_code=?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, discCode);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("bup_year_id_auto");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                bupid =Integer.parseInt(dc);
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return bupid;
    }
    
    /**
     *      * 
     * @param columnName SELECT (columnName) FROM table where
     * @param bupId      SELECT * FROM table where id=bupId
     * @return String columnName
     * 
     * 
     */
    private String getBupInfoById(String columnName, String bupId){
        String info = "";
       
        final String sql = "SELECT "+columnName+" FROM bup_year WHERE bup_year_id_auto=?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, bupId);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString(columnName);
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                info = dc;
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
              
        return info;
    }
    
    public static void main(String[] args) {
//        String a = "bup_dis_code";
//        String b = "4";
//        
//        BupDAO bd = new BupDAO();
//        String f = bd.getBupInfoById(a,b );
//        
////        Integer f = bd.getBupIdByDisc("ACCO 230");
//        System.out.println(f);
    }
}
