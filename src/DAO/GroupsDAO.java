package DAO;

import bdoc.dbconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Altynbek
 * 
 */
public class GroupsDAO {
   
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public GroupsDAO(){
        conn = dbconnector.getConnection();
    }
    
    public List<String> getGroupsList(){
    
        List<String> groups = new ArrayList<>();
        
                
        final String sql= "SELECT group_code FROM groups ORDER BY group_code ASC";
        
        try{
                        
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("group_code");
                
                if ( dc== null) {
                    groups.add("");
                }
                groups.add(dc);
               
            }
            
            rs.close();
            pst.close();
//            conn.close();
                        
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return groups;
    }
    
    public Integer getStudentCountInGroup(String groupCode){
        Integer count = 0;
    
        final String sql = "SELECT COUNT(*) FROM student WHERE group_code=?";
        
        try {
                        
            pst=conn.prepareStatement(sql);
            pst.setString(1, groupCode);
            rs = pst.executeQuery();
         
            while(rs.next()){     
                String dc;
                dc = rs.getString("COUNT(*)");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                count =Integer.parseInt(dc);
               
        }
               
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return count;
    }
    
    public Integer getGroupSemestr(String group_code){
        Integer semestr = 0;
          
        final String sql = "SELECT group_semestr FROM groups WHERE group_code = ?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, group_code);
            rs = pst.executeQuery();
                        
            while(rs.next()){     
                String dc;
                dc = rs.getString("group_semestr");
                
                if ( dc == null) {
                    dc = "Не определен";
                }
                semestr =Integer.parseInt(dc);
               
        }
            
            rs.close();
            pst.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return semestr;
    }
    
    public String getGroupInfo(String columnName, String groupCode){
        String info = "";
        
        final String sql = "SELECT "+columnName+" FROM groups WHERE group_code = ?";
        
        try{
                        
            pst=conn.prepareStatement(sql);
                pst.setString(1, groupCode);
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
    
    public Integer getGroupCourse(Integer semestr){
        Integer course = 0;
        
        if(0<semestr && semestr<3){
            course=1;
        }
        else if (2<semestr && semestr<5){
            course=2;
        }
        else if (4<semestr && semestr<7){
            course=3;
        }
        else if (6<semestr && semestr<9){
            course=4;
        }
        else if (8<semestr && semestr<11){
            course=5;
        }
        else if (10<semestr && semestr<13){
            course = 6;
        }
        else if(12<semestr && semestr < 15){
            course = 7;
        }
        else if(semestr<0 || semestr > 14){
            JOptionPane.showMessageDialog(null, "Введите правильное значение семестра!");
            course = 1;
        }
        
        return course;
    }
}
