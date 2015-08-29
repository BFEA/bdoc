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
public class TeacherDAO {
    
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public TeacherDAO(){
        conn = dbconnector.getConnection();
    }
    
    public Integer getTeacherId(String teacher_lastname){
        Integer id = 0;
        
        final String sql = "SELECT teacher_code FROM teacher WHERE teacher_lastname = ?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            pst.setString(1, teacher_lastname);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("teacher_code");
                
                if ( dc == null) {
                    dc = "0";
                }
                id = Integer.parseInt(dc);
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return id;
    }
    
    public String getTeacherLastNameById(String teacherId){
        String teacherlastName = "";
        
        final String sql = "SELECT teacher_lastname FROM teacher WHERE teacher_code = ?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            pst.setString(1, teacherId);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("teacher_lastname");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                teacherlastName = dc;
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }        
        
        return teacherlastName;
    }
    
    public List<String> getTeacherLastNames(){
                                
        List<String> teacherLastNames = new ArrayList<String>();
        
        String sql= "SELECT teacher_lastname FROM teacher ORDER BY teacher_lastname ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("teacher_lastname");
                
                if ( dc== null) {
                    teacherLastNames.add("");
                }
                teacherLastNames.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return teacherLastNames;
    }
    
    
    public static void main(String[] args) {
        TeacherDAO t = new TeacherDAO();
        List<String> a = t.getTeacherLastNames();
        
        for (int a1=0; a1 < a.size(); a1++) {
            System.out.println(a.get(a1));
        }
        
    }
}
