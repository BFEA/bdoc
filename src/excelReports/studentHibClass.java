package excelReports;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import bdoc.dbconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Altynbek Shaidyldaev
 */
public class studentHibClass extends dbconnector {
    
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    
    public studentHibClass(){
        conn = getConnection();
    }
    
    public List<Integer> studIdArr (String grCode){
        List<Integer> studList = new ArrayList<>();
        try{
            String query = "SELECT * FROM student WHERE group_code='"+grCode+"'";
            
            pst=conn.prepareStatement(query);
            rs=pst.executeQuery();
            
            while(rs.next()){
                studList.add(rs.getInt("student_id"));
            }
            
        }catch(SQLException ex){
             JOptionPane.showMessageDialog(null, " studIdArr "+ex); 
        }
        return studList;
    }
    
    public List<String> getgrUchPlan(String grCode, String uchPlSem){
        List<String> uchplanZaSemestr=new ArrayList<>();
        String query = "SELECT * FROM uchplangroup WHERE group_code='"+grCode+"' AND uchplg_semestr='"+uchPlSem+"'";
        try{
            rs = pst.executeQuery(query);
            while(rs.next()){
                uchplanZaSemestr.add(rs.getString("discip_code"));
            }
        }catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null, " getGruchpl "+sqlex); 
        }
        return uchplanZaSemestr;
    }
    
    public List<String> getMarksForDiscipline(String semUchpl,String stud_id, String discCode){
        List<String> markOfStudent = new ArrayList<>();
        
        List<String> marks = new ArrayList<>();

        String query="SELECT * FROM uchplanstudents WHERE student_id='"+stud_id+"' AND disc_code='"+discCode+"' AND uchplans_sem='"+semUchpl+"'";
        try{
            rs=pst.executeQuery(query);
            while(rs.next()){
                marks.add(rs.getString("uchPls_ocenka"));
            }
        }catch(SQLException sqex){
//            JOptionPane.showMessageDialog(null, " getMarksForDiscipline "+sqex);
                        sqex.printStackTrace();
        }
              
        
        return markOfStudent;
    }
    
    public String getStudMarks(String semUchpl,String stud_id, String discCode){
        String marks=null;
        
        String query="SELECT * FROM uchplanstudents WHERE student_id='"+stud_id+"' AND disc_code='"+discCode+"' AND uchplans_sem='"+semUchpl+"'";
        
        try{
            rs=pst.executeQuery(query);
            while(rs.next()){
                String markString = rs.getString("uchPls_ocenka");
                if(markString!=null){
                    marks = markString;
                }else{
                    marks="-1";
                }

            }
        }catch(SQLException sqex){
//            JOptionPane.showMessageDialog(null, " getStudMarks "+sqex); 
                        sqex.printStackTrace();
        }
        return marks;
    }
    
    public String getStudentNamesById(String stud_id){
        String studentName = null;
            try{
                String query = "SELECT * FROM student WHERE student_id='"+stud_id+"'";
                rs = pst.executeQuery(query);
                    while(rs.next()){
                        String lastname = rs.getString("student_lastname");
                        String name = rs.getString("student_name");
                        String middlename = rs.getString("student_middlename");
                        studentName = lastname+" "+name+" "+middlename;
                    }
            }catch(SQLException ex){
//                JOptionPane.showMessageDialog(null, " getStudentNamesById "+ex); 
                            ex.printStackTrace();
            }
        return studentName;
    }
    
    public String getDisciplineNamesByCode(String discCode){
        String disciplineName = null;
            try{
                String query = "SELECT disc_name FROM spr_discipline WHERE disc_code='"+discCode+"'";
                rs = pst.executeQuery(query);
                    while(rs.next()){
                        disciplineName = rs.getString("disc_name");
                    }
            }catch(SQLException ex){
//                JOptionPane.showMessageDialog(null, " getDisciplineNamesByCode "+ex); 
                            ex.printStackTrace();
            }
        return disciplineName;
    }
    
}
