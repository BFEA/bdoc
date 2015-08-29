package bup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bdoc.dbconnector;
import java.util.Iterator;

public class DisplayBUPYears {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public DisplayBUPYears(){
        conn = dbconnector.getConnection();
    }
    
    public List<String> fillComboBupStartYears(){
       
        List<String> _startyearList = new ArrayList<String>();
        final String sql= "SELECT bup_years_start FROM bup_years ORDER BY bup_years_start ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("bup_years_start");
                
                if ( dc== null) {
                    _startyearList.add("");
                }
                _startyearList.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return _startyearList;       
    }
    
    public List<String> fillComboBupEndYears(){
           
        List<String> _startyearList = new ArrayList<String>();
        final String sql= "SELECT bup_years_end FROM bup_years ORDER BY bup_years_end ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("bup_years_end");
                
                if ( dc== null) {
                    _startyearList.add("");
                }
                _startyearList.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return _startyearList;       
    }

    public String getIdYearsUchGod(String _startYear, String _endYear){
        
        String yearsId = "";
        
        final String sql = "SELECT bup_years_id FROM bup_years WHERE bup_years_start =? AND bup_years_end=?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, _startYear);
                pst.setString(2, _endYear);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("bup_years_id");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                yearsId = dc;
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return yearsId;
    }

}