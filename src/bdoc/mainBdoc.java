package bdoc;

import excelReports.ExecuteCreateJournal;
import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author Altynbek Shaidyldaev
 */
public class mainBdoc extends javax.swing.JFrame {
    
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    ResultSet resu2 = null;
    PreparedStatement pstt2 = null;

    
   
    public mainBdoc() {
        initComponents();
        conn=dbconnector.ConnectorDb();
        Update_table_Departments();
        Update_table_Teachers();
        Update_table_Students();
        CurrentDate();
        FillCombo();
        
//        Intialize handbooks
        Update_spr_Country();
        Update_spr_Nationality();
        Update_spr_Region();
        Update_spr_Doljnost();
        Update_spr_Uchzv();
        Update_spr_Uchst();
        Update_spr_SttPS();
        Update_spr_Disciplina();
        Update_spr_Uslzachisl();
        Update_spr_Formobuchn();
        Update_spr_Sostobuchn();
        
    }
    
    private Integer isExistsUchPlan(String student,int row){
        
        String semestr = (Table_PlaneOfGroup.getValueAt(row, 1).toString());
        String discipline = (Table_PlaneOfGroup.getValueAt(row, 2).toString());
        
        Integer result=0;
            String sql = "SELECT * FROM "
                            + "uchplanstudents "
                            + "WHERE "
                            + "student_id='"+student+"' "
                            + "AND "
                            + "disc_code='"+discipline+"' "
                            + "AND "
                            + "uchplans_sem='"+semestr+"'";
                try{
                    pst=conn.prepareStatement(sql);
                    rs=pst.executeQuery();

                    if(!rs.next()){
                        //Null
                        result = 0;
                    }else{
                        //Not null
                        result = 1;
                    }

                }catch(Exception ex){
                    ex.printStackTrace();
                }
        return result;
    }
    
    private void selectDisciplineNameUchPl(String disc_id){
        
            String sql = "SELECT * FROM spr_discipline WHERE disc_code='"+disc_id+"'";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                while(rs.next()){
                    String name_disc = rs.getString("disc_name");
                    studUchPlDisciplinaComboB1.setSelectedItem(name_disc);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    
    private void selectDisciplineNameUchPlGruppe(String disc_id){
        
            String sql = "SELECT * FROM spr_discipline WHERE disc_code='"+disc_id+"'";
            try{
                pst=conn.prepareStatement(sql);
                rs=pst.executeQuery();
                while(rs.next()){
                    String name_disc = rs.getString("disc_name");
                    planeComboBoxDisciplina.setSelectedItem(name_disc);
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

    public boolean semestrActive(){
        String sql = "SELECT A_VED FROM spr_settings WHERE A_ID=1";
        try{
        pst = conn.prepareStatement(sql);
            rs=pst.executeQuery();
              String semestrAct= rs.getString("A_VED");
              if(semestrAct.equals(1)){
                  return true;
              }else{
                  return false;
              }
        }catch(SQLException sqlex){
            return false;
        }    
      
    }

    private void imageShow(String st_idimage) {
 
     String imagequery = "SELECT student_photo FROM student where student_id='"+st_idimage+"'";
             try  {
                         pst = conn.prepareStatement(imagequery);
                            rs=pst.executeQuery();
                   if(rs.next()){
                                byte[] imagedata = rs.getBytes("student_photo");
                                format = new ImageIcon(imagedata);
                                textPaneStudentPhoto.setIcon(format);
                                }
                    }catch(Exception e){
                    }
             
}
    
    private void clickDepToGroups (String depId){
        try{
            
            String sql = "SELECT"+
                                " department_code as 'Программа',"+
                                "  group_code as 'Группа',"+
                                "  group_name as 'Направление',"+
                                "  group_semestr as 'Семестр',"+
                                "  group_curator as 'Куратор',"+
                                "  group_formaobuch as 'Форма обучения'"+
                                "  FROM groups"+
                                "  WHERE"+
                                "  department_code='"+depId+"'";
            pst = conn.prepareStatement(sql);
            rs=pst.executeQuery();
             Table_Groups.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException sqex){
            JOptionPane.showMessageDialog(null, "ActionPerformed clickDepToGroups "+sqex);
        }
    }
    
    private void clickGroupsToStudents(String groupId){
        try{
            String sql = "SELECT student_id as Код, "+
                                "student_lastname as 'Фамилия', "+
                                "student_name as 'Имя', "+
                                "student_middlename as 'Отчество' "+
                                "FROM student "+
                                "WHERE group_code='"+groupId+"'"+ 
                                "ORDER BY student_lastname ASC ";
        
             pst = conn.prepareStatement(sql);
                rs=pst.executeQuery();
                    Table_Students.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException sqex){
            JOptionPane.showMessageDialog(null,"ActionPerformed clickGroupsToStudents "+sqex );
        }
    }
    
    private void clickGroupsToUchPlane(String groupNameId){
                
        try{
                String sql = "SELECT group_code as 'Группа', "+
                                "uchPlg_semestr as 'Семестр', "+
                                "discip_code as 'Дисциплина', "+
                                "uchPlg_clock as 'Часы', "+
                                "uchPlg_credit as 'Кредиты', "+
                                "uchPlg_control as 'Контроль' "+
                                "FROM uchplangroup "+
                                "WHERE group_code='"+groupNameId+"' "+
                                "ORDER BY uchplg_semestr ASC";
                pst = conn.prepareStatement(sql);
                 rs=pst.executeQuery();
                   
//                    Integer group_semestr =  Table_PlaneOfGroup.getModel().getValueAt(row, 1);
                 
                    Table_PlaneOfGroup.setModel(DbUtils.resultSetToTableModel(rs));

                    
                            
                    
        }catch(SQLException sqex){
            JOptionPane.showMessageDialog(null,"ActionPerformed clickGroupsToUchPlane "+sqex);
        }
    }
    
    private void StudClickTableToUch(String studId){
        try{
            
            String sql = "SELECT student_id as 'ID студента',"+
                                " group_code as Группа,"+
                                " uchPlans_sem as Семестр,"+
                                " disc_code as 'Код дисциплины',"+
                                " uchPls_clock as Часы,"+
                                " uchPls_credit as Кредиты,"+
                                " uchPls_ball as Баллы,"+
                                " uchPls_ocenka as Оценка,"+
                                " uchPls_controlb as Контроль,"+
                                " uchPls_date as 'Дата сдачи'"+
                                " FROM uchplanstudents"+
                                " WHERE student_id='"+studId+"'"+
                                " ORDER BY uchplans_sem ASC";
                    pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                      
                    Table_UchPlaneStudent.setModel(DbUtils.resultSetToTableModel(rs));
            
        
         }catch(SQLException sqlex){
                JOptionPane.showMessageDialog(null,"ActionPerformed StudClickTableToUch "+sqlex);
             }
      
    }
    
    public void Update_spr_Country(){
        try{
                 
        String sql = "SELECT"+
                            " cou_cod as 'Код',"+
                            " cou_name as 'Страны' "+
                            "FROM spr_country";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableCountry.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
        
    }
    public void Update_spr_Nationality(){
        try{
                 
        String sql = "SELECT "+
                            "nationality_code as 'Код', "+
                            "nationality_name as 'Национальность' "+
                            "FROM spr_nationality";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableNationality.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_spr_Region (){
        
        try{
                 
        String sql = "SELECT "+
                            "region_code as 'Код', "+
                            "region_name as 'Области' "+
                            "FROM spr_region";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableRegion.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_spr_Doljnost(){
        
         try{
                 
        String sql = "SELECT "+
                            "dolj_code as 'Код', "+
                            "dolj_name as 'Наименование должности' "+
                            "FROM spr_doljnost";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableDoljnost.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    } 
    public void Update_spr_Uchzv(){
        
         try{
                 
        String sql = "SELECT "+
                            "uzvanie_code as 'Код', "+
                            "uzvanie_name as 'Наименование ученого звания' "+
                            "FROM spr_uzvanie";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableUchenZvan.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    } 
    public void Update_spr_Uchst(){
         try{
                 
        String sql = "SELECT "+
                            "ustep_code as 'Код', "+
                            "ustep_name as 'Наименование ученой степени' "+
                            "FROM spr_uchstepen";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableUchenStep.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_spr_SttPS(){
        
         
         try{
                 
        String sql = "SELECT "+
                            "status_code as 'Код', "+
                            "status_name as 'Наименование статуса ППС' "+
                            "FROM spr_statusteach";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableStatusPPS.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }  
    public void Update_spr_Disciplina(){
         try{
                 
        String sql = "SELECT "+
                            "disc_code as 'Код', "+
                            "disc_name as 'Наименование дисциплины' "+
                            "FROM spr_discipline";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableDisciplines.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
         FillCombo();
    }
    public void Update_spr_Uslzachisl(){
         try{
                 
        String sql = "SELECT "+
                            "zachislenie_code as 'Код', "+
                            "zachislenie_name as 'Условия зачисления' "+
                            "FROM spr_zachislenie";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableUsZachisl.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_spr_Formobuchn(){
          try{
                 
        String sql = "SELECT "+
                            "formobuch_code as 'Код', "+
                            "formobuch_name as 'Формы обучения' "+
                            "FROM spr_formobuch";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableFormob.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_spr_Sostobuchn(){
        try{
                 
        String sql = "SELECT "+
                            "sostobuchen_code as 'Код', "+
                            "sostobuchen_name as 'Наименование состояния судента' "+
                            "FROM spr_sostobuchen";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        tableSostOb.setModel(DbUtils.resultSetToTableModel(rs));
            
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }        
    public void UserLoginText(String userName, String FIO){
        jMenuUserLogin.setText(userName);
        jMenuUserFIO.setText(" ("+FIO+")");
    } 
    public void CurrentDate(){
        Calendar cal = new GregorianCalendar();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        
        date_txt.setText(year+"-"+(month+1)+"-"+day);
        
    }
    public void Update_table_Departments(){
        try {
            
        String sql = "SELECT "+
                            "depart_code as 'Код',"+
                            "depart_name as 'Наименование',"+
                            "depart_header as 'Руководитель',"+
                            "depart_assistant as 'Лаборант',"+
                            "depart_phone as 'Телефон',"+
                            "depart_email as 'E-Mail' "+
                            "FROM departments";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        Table_Departments.setModel(DbUtils.resultSetToTableModel(rs));
        
        
        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_table_Groups(){
        try {
//            int row = Table_Groups.getSelectedRow();
//            String idDepCodGr = (Table_Groups.getValueAt(row, 0).toString());
            String idDepCodGr = fieldCodeIdDep.getText();
        
            String sql = "SELECT "+
                            "department_code as 'Программа', "+
                            "group_code as 'Группа', "+
                            "group_name as 'Направление', "+
                            "group_semestr as 'Семестр', "+
                            "group_curator as 'Куратор',"+
                            "group_formaobuch as 'Форма обучения' "+
                            "FROM groups where department_code='"+idDepCodGr+"' ";
                
            
            pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        Table_Groups.setModel(DbUtils.resultSetToTableModel(rs));
        
        
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }
    public void Update_table_Teachers(){
        try {
            
        String sql = "SELECT "+
                            "teacher_code as 'Код', "+
                            "teacher_lastname as 'Фамилия', "+
                            "teacher_name as 'Имя', "+
                            "teacher_middlename as 'Отчество' "+
                            "FROM  teacher "+
                            "ORDER BY teacher_lastname ASC";
        pst=conn.prepareStatement(sql);
        rs=pst.executeQuery();
        Table_State.setModel(DbUtils.resultSetToTableModel(rs));
        
        }catch(Exception e){
           // JOptionPane.showMessageDialog(null, e);
        }
    }
    public void FillCombo(){
        
        try{
            String sql= "SELECT * FROM spr_nationality ORDER BY nationality_name ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String name_nationality = rs.getString("nationality_name");
                chooserNationalityStudent.addItem(name_nationality);
                ComboBoxNationality.addItem(name_nationality);
               
            }
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
        
        try{
            String sql= "SELECT * FROM spr_discipline ORDER BY disc_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String disc_name = rs.getString("disc_name");
                planeComboBoxDisciplina.addItem(disc_name);
                studUchPlDisciplinaComboB1.addItem(disc_name);
               
            }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
        
         try{
            String sql= "SELECT * FROM spr_country ORDER BY cou_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String countr_name = rs.getString("cou_name");
                chooserCounrtyStudent.addItem(countr_name);
               
               
            }
            
            
        }catch(Exception e){
            // JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_region ORDER BY region_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String regiona_name = rs.getString("region_name");
                chooserRegionStudent.addItem(regiona_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_doljnost ORDER BY dolj_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String doljn_name = rs.getString("dolj_name");
                ComboBoxDoljnost.addItem(doljn_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
        
         try{
            String sql= "SELECT * FROM spr_uzvanie ORDER BY uzvanie_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String uzvan_name = rs.getString("uzvanie_name");
                ComboBoxUchenZvanie.addItem(uzvan_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
        
         try{
            String sql= "SELECT * FROM spr_uchstepen ORDER BY ustep_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String ustep_name = rs.getString("ustep_name");
                ComboBoxUchenStepen.addItem(ustep_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_statusteach ORDER BY status_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String statusn_name = rs.getString("status_name");
                ComboBoxStatus.addItem(statusn_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM departments ORDER BY depart_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String departm_name = rs.getString("depart_name");
                ComboBoxCodeOfPodrazdel.addItem(departm_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_sostobuchen ORDER BY sostobuchen_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String sost_name = rs.getString("sostobuchen_name");
                chooserSostStudent.addItem(sost_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_zachislenie ORDER BY zachislenie_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String zachisl_name = rs.getString("zachislenie_name");
                chooserUsloviyaZachisleniya1.addItem(zachisl_name);
              }
            
            
        }catch(Exception e){
             //JOptionPane.showMessageDialog(null, e);
        }
         
          try{
            String sql= "SELECT * FROM teacher ORDER BY teacher_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String teacher_lastname = rs.getString("teacher_lastname");
                planeComboBoxPrepodavatel.addItem(teacher_lastname);
                comboTeacherEditGroup.addItem(teacher_lastname);
              }
            
            
        }catch(Exception e){
            // JOptionPane.showMessageDialog(null, e);
        }
         
        
    }
    public void Update_table_Students(){
        try{
                        String group_code_text = (TabStudentTableName.getText());
                        
                        if(rs.next()){String sql = "SELECT "
                                + "student_id as Код, "
                                + "student_lastname as Фамилия, "
                                + "student_name as Имя, "
                                + "student_middlename as Отчество "
                                + "FROM "
                                + "student "
                                + "WHERE group_code='"+group_code_text+"'";
                        pst = conn.prepareStatement(sql);
                          rs=pst.executeQuery();
                          Table_Students.setModel(DbUtils.resultSetToTableModel(rs));
                        }
                    }catch(Exception e){
                        //JOptionPane.showMessageDialog(null, e);
                    }
    }
    public void Update_Table_UchPlGr(){
          try{
              
              String add1 = downButtonPanelText.getText();
              String sql = "SELECT group_code as 'Группа', "+
                            "uchPlg_semestr as 'Семестр', "+
                            "discip_code as 'Дисциплина', "+
                            "uchPlg_clock as 'Часы', "+
                            "uchPlg_credit as 'Кредиты', "+
                            "uchPlg_control as 'Контроль' "+
                            "FROM uchplangroup Where group_code='"+add1+"'";
               pst=conn.prepareStatement(sql);
                     rs=pst.executeQuery();
              Table_PlaneOfGroup.setModel(DbUtils.resultSetToTableModel(rs));
          }catch(Exception e){
               JOptionPane.showMessageDialog(null, e);
          }
      }
    private void raznoskaUchPlan(String studIdC, int row){
  
        String groupCode = (Table_PlaneOfGroup.getValueAt(row, 0).toString());
        String uchPlSemestr = (Table_PlaneOfGroup.getValueAt(row, 1).toString());
        String disciplineCode = (Table_PlaneOfGroup.getValueAt(row, 2).toString());
        String uchPlansClock = (Table_PlaneOfGroup.getValueAt(row, 3).toString());
        String uchPlansCredit = (Table_PlaneOfGroup.getValueAt(row, 4).toString());
        String uchPlansControl = (Table_PlaneOfGroup.getValueAt(row, 5).toString());
        String date_oper = date_txt.getText();
        String user_login = jMenuUserLogin.getText();

                try{
                    String raznoskaSql = "INSERT INTO uchplanstudents"+
                                            " (group_code,student_id,uchplans_sem,disc_code,"+
                                            " uchPls_clock,uchpls_credit,uchpls_controlb,"+
                                            " uchpls_date,uchpls_dateoper,user_login,uchpls_add,uchplsadd_date)"+
                                            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                    pst = conn.prepareStatement(raznoskaSql);
                    pst.setString(1, groupCode);
                    pst.setString(2, studIdC);
                    pst.setString(3, uchPlSemestr);
                    pst.setString(4, disciplineCode);
                    pst.setString(5, uchPlansClock);
                    pst.setString(6, uchPlansCredit);
                    pst.setString(7, uchPlansControl);
                    pst.setString(8, date_oper);
                    pst.setString(9, date_oper);
                    pst.setString(10, user_login);
                    pst.setString(11, user_login);
                    pst.setString(12, date_oper);

                    pst.execute();

                }catch(SQLException sqlex){
                    sqlex.printStackTrace();
                }
                
    }
    private void addNewUchPlanAll(String gr_code, 
                               String uchpl_sem,
                               String disc_code,
                               String uchpl_clock,
                               String uchpls_credit,
                               String uchpls_date,
                               String student_id, 
                               String uchpls_add,
                               String uchplsadd_date,
                               String controlTypes){
       String dateOper = date_txt.getText();
        
       int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n добавить новый элемент?","Добавлено успешно!",JOptionPane.YES_NO_OPTION);
            
       if(p==0){
        
        try {
            
            String sql = "INSERT INTO "
                    + "uchplanstudents "
                    + "(group_code, uchplans_sem, disc_code,"
                    + "uchpls_clock,uchpls_credit,uchpls_date,"
                    + "student_id,uchpls_add,uchplsadd_date,uchpls_dateoper,uchpls_controlb) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
           
                pst = conn.prepareStatement(sql);
                
                    pst.setString(1,gr_code);
                    pst.setString(2,uchpl_sem);
                    pst.setString(3,disc_code);
                    pst.setString(4,uchpl_clock);
                    pst.setString(5,uchpls_credit);
                    pst.setString(6,uchpls_date);
                    pst.setString(7,student_id);
                    pst.setString(8,uchpls_add);
                    pst.setString(9,uchplsadd_date);
                    pst.setString(10,dateOper);
                    pst.setString(11,controlTypes);
                
                    pst.execute();
                    
            } catch (SQLException ex) {
              ex.printStackTrace();
            }
       }
        String Table_Click = fieldIdStudent.getText();
        StudClickTableToUch(Table_Click);
    }
    
    private void editUchPlanOfStudent(  String studId,
                                        String disc_code,
                                        String uchPlanChasy,
                                        String uchPlanCredity,
                                        Integer uchPlanBally,
                                        String dataSdachiPredmeta,
                                        String disc_sem){
        
        String currentDate = date_txt.getText();
        String editorLoginName = jMenuUserLogin.getText();
        int row = Table_UchPlaneStudent.getSelectedRow();
        String controlType = studUchPlControTextF.getText();
        
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n изменить?","Updated",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
                try {
                    String sql = "UPDATE uchplanstudents SET "
                            + "uchpls_clock='" +uchPlanChasy 
                            + "',uchpls_credit='" +uchPlanCredity 
                            + "',uchpls_ball='" +uchPlanBally 
                            + "',uchpls_date='" +dataSdachiPredmeta 
                            + "',uchpls_edit='" +editorLoginName
                            + "',uchpls_dateOper='"+currentDate
                            + "' WHERE "
                            + "student_id='"+studId+"'"
                            + " AND "
                            + "disc_code='"+disc_code+"'"
                            + " AND "
                            + "uchPlans_sem='"+disc_sem+"'";
                    
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    
                if(controlType.equalsIgnoreCase("Курсовая")){
                    setMarkForPraktikaIKursovaya(uchPlanBally);
                }else if(controlType.equalsIgnoreCase("Практика")){
                    setMarkForPraktikaIKursovaya(uchPlanBally);
                }else {
                    setMarkForEkzIZachet(uchPlanBally);
                }


            } catch (SQLException sqlex) {
                JOptionPane.showMessageDialog(null,"editUchPlanOfStudent"+sqlex);
            }
                }
            String Table_Click = fieldIdStudent.getText();
            StudClickTableToUch(Table_Click);
    }
    private void editUchPlanOfStudentWithBegunok(String studId,
                                                String disc_code,
                                                String uchPlanChasy,
                                                String uchPlanCredity,
                                                Integer uchPlanBally,
                                                String dataSdachiPredmeta,
                                                String number_begunok,
                                                String disc_sem){
        String currentDate = date_txt.getText();
        String editorLoginName = jMenuUserLogin.getText();
        int row = Table_UchPlaneStudent.getSelectedRow();
        String controlType = studUchPlControTextF.getText();
        Integer begunok = 1;
        
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n изменить?","Updated",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
                try {
                    String sql = "UPDATE uchplanstudents SET uchpls_clock='" +uchPlanChasy 
                            + "',uchpls_credit='" +uchPlanCredity
                            + "',uchpls_date='" +dataSdachiPredmeta 
                            + "',uchpls_edit='" +editorLoginName
                            + "',uchpls_dateOper='"+currentDate
                            + "',uchpls_nuberbegunok='"+number_begunok
                            + "',uchpls_begunok='"+begunok
                            + "' WHERE "
                            + "student_id='"+studId+"'"
                            + " AND "
                            + "disc_code='"+disc_code+"'"
                            + " AND "
                            + "uchPlans_sem='"+disc_sem+"'";
                    
                    pst = conn.prepareStatement(sql);
                    pst.execute();
                    
                if(controlType.equalsIgnoreCase("Курсовая")){
                    setMarkForPraktikaIKursovaya(uchPlanBally);
                }else if(controlType.equalsIgnoreCase("Практика")){
                    setMarkForPraktikaIKursovaya(uchPlanBally);
                }else {
                    setMarkForEkzIZachet(uchPlanBally);
                }


            } catch (SQLException sqlex) {
                JOptionPane.showMessageDialog(null,"editUchPlanOfStudent"+sqlex);
            }
                }
            String Table_Click = fieldIdStudent.getText();
            StudClickTableToUch(Table_Click);
    }
    
    private void setMarkForEkzIZachet(Integer mark){
        String control = studUchPlControTextF.getText();
        String discFizk = (String) studUchPlDisciplinaComboB1.getSelectedItem();
        String sql = "SELECT * FROM spr_settings WHERE A_ID=1";
        try{
            pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                 if(rs.next()){
                     Integer excelTop = rs.getInt("A_2");
                     Integer excelLow = rs.getInt("A_1");
                     Integer goodTop = rs.getInt("A_4");
                     Integer goodLow = rs.getInt("A_3");
                     Integer soTop = rs.getInt("A_6");
                     Integer soLow = rs.getInt("A_5");
                     String markText;
                    if(control.equalsIgnoreCase("Зачет")&& discFizk.equalsIgnoreCase("Физкультура")){
                         if(mark>=soLow && mark<=excelTop){
                             markText = "Зачтено";
                             setMarkTextStudent(markText,mark);
                         }else{
                             markText = "Не зачтено";
                             setMarkTextStudent(markText,mark);
                         }
                    }else{
                            if(control.equalsIgnoreCase("Экзамен")){
                                Integer markAfterDivideWithBegunok=0;
                                   if(mark>=110 && mark<=200){
                                       markAfterDivideWithBegunok = mark/2;
                                       if(excelTop>=markAfterDivideWithBegunok && markAfterDivideWithBegunok>=excelLow){
                                           markText = "Отлично";
                                           setMarkTextStudent(markText,mark);
                                       }else if(markAfterDivideWithBegunok<=goodTop && goodLow<=markAfterDivideWithBegunok){
                                           markText = "Хорошо";
                                           setMarkTextStudent(markText,mark);
                                       }else if (markAfterDivideWithBegunok<=soTop && markAfterDivideWithBegunok>=soLow){
                                           markText = "Удов.";
                                           setMarkTextStudent(markText,mark);
                                       }else {
                                           markText = "Не удов.";
                                           setMarkTextStudent(markText,mark);
                                       }
                                   }else if(mark<=109 && mark > 0){
                                      markText = "Не удов.";
                                      setMarkTextStudent(markText,mark);
                                   }
                                   else if (mark>=201){
                                       JOptionPane.showMessageDialog(null, "Балл не должен превышать 200!");
                                   }else if (mark<0){
                                       JOptionPane.showMessageDialog(null, "Балл не может быть отрицательным числом!");
                                   }else {

                                   }   
                            }else if(control.equalsIgnoreCase("Зачет")){

                                       if(excelTop>=mark && mark>=excelLow){
                                           markText = "Отлично";
                                           setMarkTextStudent(markText,mark);
                                       }else if(mark<=goodTop && goodLow<=mark){
                                           markText = "Хорошо";
                                           setMarkTextStudent(markText,mark);
                                       }else if (mark<=soTop && mark>=soLow){
                                           markText = "Удов.";
                                           setMarkTextStudent(markText,mark);
                                       }else if (mark>100){
                                           JOptionPane.showMessageDialog(null, "Баллы для этого вида контроля\n не может превышать 100");
                                       }else{
                                           markText = "Не удов.";
                                           setMarkTextStudent(markText,mark);
                                       }
                            }
                    }
                 }else{
                     JOptionPane.showMessageDialog(null, "Введите количество баллов!");
                 }
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void setMarkForPraktikaIKursovaya(Integer mark){
        
                     Integer excellent = 5;
                     Integer good = 4;
                     Integer satisfied = 3;
                     Integer bad = 2;
                     
                     String markText;
                            if(excellent==mark){
                                markText = "Отлично";
                                setMarkTextStudent(markText,mark);
                            }else if(good==mark){
                                markText = "Хорошо";
                                setMarkTextStudent(markText,mark);
                            }else if (satisfied==mark){
                                markText = "Удов.";
                                setMarkTextStudent(markText,mark);
                            }else if (bad == mark) {
                                markText = "Не удов.";
                                setMarkTextStudent(markText,mark);
                            }else if (mark == 1 && mark == 0){
                                markText = "Не удов.";
                                setMarkTextStudent(markText,mark);
                            }else{
                                JOptionPane.showMessageDialog(null, "Введите правильное значение оценки!!!");
                            }
                    JOptionPane.showMessageDialog(null, "Updated!");
    }
    
    private void setMarkTextStudent(String markText, Integer mark){
            
        int row = Table_UchPlaneStudent.getSelectedRow();
        String studId=(Table_UchPlaneStudent.getValueAt(row, 0).toString());
        String discCode=(Table_UchPlaneStudent.getValueAt(row, 3).toString());
        String semestr = studUchPlSemestrTextF.getText();
        String control_type=(Table_UchPlaneStudent.getValueAt(row, 8).toString());
        
            try{
                    String sql = "UPDATE "
                            + "uchplanstudents "
                            + "SET "
                            + "uchpls_ocenka='"+markText+"',"
                            + "uchPls_ball='"+mark+"' "
                            + "WHERE "
                            + "student_id='"+studId+"' "
                            + "AND "
                            + "disc_code='"+discCode+"' "
                            + "AND "
                            + "uchPlans_sem='"+semestr+"'"
                            + "AND "
                            + "uchPls_controlb='"+control_type+"'";

                    pst = conn.prepareStatement(sql);
                    pst.execute();
                }catch(SQLException ex){
                     JOptionPane.showMessageDialog(null, ex);
                }
    }
    
    private void groupSemestrToSemestr(Integer semestr, String groupCode){
        
        String sql = "UPDATE groups SET group_semestr='"+semestr+"' WHERE group_code='"+groupCode+"'";
            try{
                pst = conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Группа "+groupCode+" переведена!");
                Update_table_Groups();
            }catch(SQLException sqlex){
                JOptionPane.showMessageDialog(null, sqlex);
            }
        }
    
    private void clearTableUchPlaneStudent(){
        Table_UchPlaneStudent.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null, null},
    },
    new String [] {
        "ID", "Группа", "Семестр", "Код дисциплины", "Часы", "Кредиты", "Баллы", "Оценка", "Контроль", "Дата сдачи"
    }
));
    }
    
    protected void printExaminationPaper(String parameter, String stud_id, String disc_id){
        
        try{
           InputStream in = getClass().getResourceAsStream("reports/EkList.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           HashMap parametersMap = new HashMap();
           parametersMap.put("ExpDate", parameter);
           
           String sql = "SELECT " +
                            "upl.student_id AS st_ID, " +
                            "upl.uchplans_sem AS uchP_semestr, " +
                            "st.student_lastname AS Lastname, " +
                            "st.student_name AS Name, " +
                            "st.student_middlename AS Middlename, " +
                            "upl.group_code AS Gruppa, " +
                            "upl.disc_code AS Disc, " +
                            "sp_disc.disc_name AS disc_Name, " +
                            "sp_form.formobuch_name AS form_Name, " +
                            "sett_RU.A_NAMEL AS bafeName " +
                            "FROM uchplanstudents upl " +
                        "INNER JOIN student st ON upl.student_id=st.student_id " +
                        "INNER JOIN groups gr ON upl.group_code=gr.group_code " +
                        "INNER JOIN spr_formobuch sp_form ON gr.group_formaobuch=sp_form.formobuch_code " +
                        "INNER JOIN spr_discipline sp_disc ON upl.disc_code=sp_disc.disc_code, " +
                            "spr_settings sett_RU " +
                        "WHERE " +
                            "upl.student_id='"+stud_id+"' " +
                        "AND\n" +
                            "upl.disc_code='"+disc_id+"' ";
           
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, parametersMap,conn);
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Экзаменационный лист");
           jv.setVisible(true);
       }catch(Exception e){
           e.printStackTrace();
       }      
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        tabPanel = new javax.swing.JTabbedPane();
        tabStructure = new javax.swing.JPanel();
        downPanelStructure = new javax.swing.JTabbedPane();
        downPanelGroups = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        addGroupButton = new javax.swing.JButton();
        LabelNameOfGroup = new javax.swing.JLabel();
        editGroupButton = new javax.swing.JButton();
        cancelGroupButton = new javax.swing.JButton();
        deleteGroupButton = new javax.swing.JButton();
        workWithVedomButton = new javax.swing.JButton();
        raznoskaButton = new javax.swing.JButton();
        updateTableGroups = new javax.swing.JButton();
        editGroupPanel = new javax.swing.JPanel();
        fieldDepIdGroup = new javax.swing.JTextField();
        fieldNameGroup = new javax.swing.JTextField();
        fieldNaprGroup = new javax.swing.JTextField();
        fieldSemestrGroup = new javax.swing.JTextField();
        fieldMaterGroup = new javax.swing.JTextField();
        fieldFromaobuchGroup = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        comboSemestrGroup = new javax.swing.JComboBox();
        comboTeacherEditGroup = new javax.swing.JComboBox();
        jScrollPane18 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Groups = new javax.swing.JTable();
        downPanelPlane = new javax.swing.JPanel();
        downPlaneButtonPanel = new javax.swing.JPanel();
        downButtonPanelText = new javax.swing.JLabel();
        editUchPlgPanel = new javax.swing.JPanel();
        deleteButtonPlane = new javax.swing.JButton();
        raznoskaButtonPlane = new javax.swing.JButton();
        applyButtonPlane = new javax.swing.JButton();
        editButtonPlane = new javax.swing.JButton();
        addButtonPlane = new javax.swing.JButton();
        ScrollPanelPlane = new javax.swing.JScrollPane();
        Table_PlaneOfGroup = new javax.swing.JTable(){

            public Component prepareRenderer(TableCellRenderer r,
                int rw,
                int col)
            {
                String activeSemGroup = fieldSemestrGroup.getText();
                String activeSemTable = (String)getValueAt(rw,1);
                Component c = super.prepareRenderer(r, rw, col);
                c.setBackground(Color.WHITE);
                if(activeSemGroup.equals(activeSemTable)){
                    c.setBackground(Color.red);
                }else if(isRowSelected(rw)){
                    c.setBackground(Color.BLUE);
                }else{
                    c.setBackground(super.getBackground());
                    c.setForeground(super.getForeground());
                }
                return c;
            }

        };
        editPanelPlane = new javax.swing.JPanel();
        planeTextLabelSemestr = new javax.swing.JLabel();
        planeComboBoxSemestr = new javax.swing.JComboBox();
        planeTextLabelDisciplina = new javax.swing.JLabel();
        planeComboBoxDisciplina = new javax.swing.JComboBox();
        planeTextFieldDisciplina1 = new javax.swing.JTextField();
        planeTextLabelPrepodavatel = new javax.swing.JLabel();
        planeComboBoxPrepodavatel = new javax.swing.JComboBox();
        planeTextLabelChasy = new javax.swing.JLabel();
        planeTextFieldChasy = new javax.swing.JTextField();
        planeTextLabelCredity = new javax.swing.JLabel();
        planeTextFieldControl = new javax.swing.JTextField();
        planeTextLabelControl = new javax.swing.JLabel();
        planeComboBoxControl = new javax.swing.JComboBox();
        planeTextFieldSemestr = new javax.swing.JTextField();
        planeTextFieldCredity1 = new javax.swing.JTextField();
        nameOfTeacher = new javax.swing.JTextField();
        tabStructureButtonPanel = new javax.swing.JPanel();
        TabStructureTableName = new javax.swing.JLabel();
        programmAddButton = new javax.swing.JButton();
        programmEditButton = new javax.swing.JButton();
        programmApplyButton = new javax.swing.JButton();
        programmDeleteButton = new javax.swing.JButton();
        ProgrammScrollPanelField = new javax.swing.JScrollPane();
        Table_Departments = new javax.swing.JTable();
        editDepPanel = new javax.swing.JPanel();
        fieldCodeIdDep = new javax.swing.JTextField();
        fieldNameDepE = new javax.swing.JTextField();
        fieldHeaderDepE = new javax.swing.JTextField();
        fieldLaborantDepE = new javax.swing.JTextField();
        fieldPhoneDepE = new javax.swing.JTextField();
        fieldMaileDepE = new javax.swing.JTextField();
        labelMaileDepE = new javax.swing.JLabel();
        labelPhoneDepE = new javax.swing.JLabel();
        labelLaborantDepE = new javax.swing.JLabel();
        labelHeaderDepE = new javax.swing.JLabel();
        labelNameDepE = new javax.swing.JLabel();
        labelCodeIdDepE = new javax.swing.JLabel();
        tabStudents = new javax.swing.JPanel();
        TabStudentEditTab = new javax.swing.JTabbedPane();
        tabStudentEditUchebKartochka = new javax.swing.JPanel();
        textIdStudent = new javax.swing.JLabel();
        fieldIdStudent = new javax.swing.JTextField();
        textFioStudent = new javax.swing.JLabel();
        fieldFamiliyaStudent = new javax.swing.JTextField();
        textBirthStudent = new javax.swing.JLabel();
        fieldNameStudent = new javax.swing.JTextField();
        fieldOtchestvoStudent = new javax.swing.JTextField();
        calendarBirthStudent = new com.toedter.calendar.JDateChooser();
        textNationalityStudent = new javax.swing.JLabel();
        fieldNationalityStudent = new javax.swing.JTextField();
        chooserNationalityStudent = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        chooserCounrtyStudent = new javax.swing.JComboBox();
        fieldCountryStudent = new javax.swing.JTextField();
        textRegionStudent = new javax.swing.JLabel();
        fieldSexStudent = new javax.swing.JTextField();
        chooserRegionStudent = new javax.swing.JComboBox();
        textSexStudent = new javax.swing.JLabel();
        fieldRegionStudent1 = new javax.swing.JTextField();
        textPropiskaStudent = new javax.swing.JLabel();
        fieldPropiskaStudent = new javax.swing.JTextField();
        textProjivanieStudent = new javax.swing.JLabel();
        fieldProjivanieStudent = new javax.swing.JTextField();
        textPhoneStudent = new javax.swing.JLabel();
        fieldPhoneStudent = new javax.swing.JTextField();
        textEmailStudent = new javax.swing.JLabel();
        fieldPasportSeriyaStudent = new javax.swing.JTextField();
        textPasportStudent = new javax.swing.JLabel();
        fieldEmailStudent = new javax.swing.JTextField();
        textPasportNumberStudent = new javax.swing.JLabel();
        textAttestatStudent = new javax.swing.JLabel();
        fieldPasportNumberStudent1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        fieldAttestatSeriyaStudent = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        fieldAttestatNumberStudent = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        fieldDiplomNumberStudent = new javax.swing.JTextField();
        dchooserVydachiDoc = new com.toedter.calendar.JDateChooser();
        fieldMestoVydachiDoc = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        fieldDiplomSeriyaStudent = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        fieldNumOfPrikazZachisl = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        fieldUsloviyaZachisleniya = new javax.swing.JTextField();
        chooserSemestrStudent = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        fieldSemestrStudent = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        fieldSumOfContract = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        fieldDiplBfaSeiyaStudent = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        dateVidachDiplBfa = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        fieldDiplBfaNumberStudent = new javax.swing.JTextField();
        chooserSostStudent = new javax.swing.JComboBox();
        jLabel22 = new javax.swing.JLabel();
        dateOfSostStudent = new com.toedter.calendar.JDateChooser();
        jLabel23 = new javax.swing.JLabel();
        fieldSostObuchStudent = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        chooserSexStudent = new javax.swing.JComboBox();
        choserDateOfPrikazZachisl = new com.toedter.calendar.JDateChooser();
        chooserUsloviyaZachisleniya1 = new javax.swing.JComboBox();
        fieldPasportNumberStudent2 = new javax.swing.JTextField();
        dchooserVydachiDoc1 = new com.toedter.calendar.JDateChooser();
        descPaneStudentPhoto = new javax.swing.JDesktopPane();
        textPaneStudentPhoto = new javax.swing.JLabel();
        path = new javax.swing.JTextField();
        jScrollPane16 = new javax.swing.JScrollPane();
        fieldAdditionalStudent2 = new javax.swing.JTextArea();
        jScrollPane17 = new javax.swing.JScrollPane();
        fieldAdditionalStudent1 = new javax.swing.JTextArea();
        tabStudentUchebnyiPlan = new javax.swing.JPanel();
        tabStudUchPlanScroll = new javax.swing.JScrollPane();
        Table_UchPlaneStudent = new javax.swing.JTable(){

            public Component prepareRenderer(TableCellRenderer r,
                int rw,
                int col)
            {
                String activeSemGroup = fieldSemestrGroup.getText();
                String activeSemTableUchPl = (String)getValueAt(rw,2);
                Component c = super.prepareRenderer(r, rw, col);
                c.setBackground(Color.WHITE);
                if(activeSemGroup.equals(activeSemTableUchPl)){
                    c.setBackground(Color.red);
                }else if(isRowSelected(rw)){
                    c.setBackground(Color.BLUE);
                }else{
                    c.setBackground(super.getBackground());
                    c.setForeground(super.getForeground());
                }
                return c;
            }

        };
        tabStudUchPlanButtons = new javax.swing.JPanel();
        studUchPlAddButton = new javax.swing.JButton();
        studUchPlEditButton = new javax.swing.JButton();
        studUchPlCancelButton = new javax.swing.JButton();
        studUchPlApplyButton = new javax.swing.JButton();
        studUchPlDeleteButton = new javax.swing.JButton();
        tabStudUchPlanPanelEdit = new javax.swing.JPanel();
        studUchPlSemestrText = new javax.swing.JLabel();
        studUchPlSemestrComboB = new javax.swing.JComboBox();
        studUchPlControlText = new javax.swing.JLabel();
        studUchPlControComboB = new javax.swing.JComboBox();
        studUchPlDisciplinaText = new javax.swing.JLabel();
        studUchPlDisciplinaField = new javax.swing.JTextField();
        studUchPlChasyText = new javax.swing.JLabel();
        studUchPlChasyField = new javax.swing.JTextField();
        studUchPlKreditytrText = new javax.swing.JLabel();
        studUchPlCredityField = new javax.swing.JTextField();
        studUchPlBallyText = new javax.swing.JLabel();
        studUchPlBallyField = new javax.swing.JTextField();
        studUchPlOcenkaText = new javax.swing.JLabel();
        studUchPlOcenkaField = new javax.swing.JTextField();
        studUchPlDataSdachiText = new javax.swing.JLabel();
        chooserStudUchPlDataSdachi = new com.toedter.calendar.JDateChooser();
        studUchPlDisciplinaComboB1 = new javax.swing.JComboBox();
        studUchPlSemestrTextF = new javax.swing.JTextField();
        studUchPlControTextF = new javax.swing.JTextField();
        raznoskaLog = new javax.swing.JLabel();
        logAddLoginLabel = new javax.swing.JTextField();
        logEditDetails = new javax.swing.JLabel();
        logEditUserField = new javax.swing.JTextField();
        logLastEditDate = new com.toedter.calendar.JDateChooser();
        logDateLabel = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JSeparator();
        numberOfBegunok = new javax.swing.JTextField();
        labelNomberOfBegunok = new javax.swing.JLabel();
        numberBegunokChek = new javax.swing.JCheckBox();
        uchplstudID = new javax.swing.JLabel();
        tabStructureButtonPanel1 = new javax.swing.JPanel();
        studentAddButton1 = new javax.swing.JButton();
        studentEditButton1 = new javax.swing.JButton();
        studentCancelButton = new javax.swing.JButton();
        studentDeleteButton = new javax.swing.JButton();
        TabStudentTableName = new javax.swing.JTextField();
        attachStudentImage = new javax.swing.JButton();
        studPerevGroupButton = new javax.swing.JButton();
        tabStudentsScrollPane = new javax.swing.JScrollPane();
        Table_Students = new javax.swing.JTable();
        tabState = new javax.swing.JPanel();
        tabStateMenuPanel = new javax.swing.JPanel();
        tabStateLabelText = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        tabStateScrollPanel = new javax.swing.JScrollPane();
        Table_State = new javax.swing.JTable();
        tabStateEdit = new javax.swing.JTabbedPane();
        tabStateEditPanel = new javax.swing.JPanel();
        textLabelCodeState = new javax.swing.JLabel();
        textFieldCodeState = new javax.swing.JTextField();
        textCodeOfPodrazdel = new javax.swing.JLabel();
        textFieldCodeOfPodrazdel = new javax.swing.JTextField();
        textLabelFamiliya = new javax.swing.JLabel();
        textFieldFamiliya = new javax.swing.JTextField();
        textLabelName = new javax.swing.JLabel();
        textFieldName = new javax.swing.JTextField();
        textLabelOtchestvo = new javax.swing.JLabel();
        textFieldOtchestvo = new javax.swing.JTextField();
        textLabelUchenZvanie = new javax.swing.JLabel();
        textFieldUchenZvanie = new javax.swing.JTextField();
        textLabelUchenStepen = new javax.swing.JLabel();
        textFieldUchenStepen = new javax.swing.JTextField();
        textLabelStatus = new javax.swing.JLabel();
        textFieldStatus = new javax.swing.JTextField();
        textLabelUchebnNagruzka = new javax.swing.JLabel();
        textFieldSexTeacher = new javax.swing.JTextField();
        textLabelNationality = new javax.swing.JLabel();
        textFieldNationality = new javax.swing.JTextField();
        textLabelDateofbirth = new javax.swing.JLabel();
        textLabelSex = new javax.swing.JLabel();
        textLabelAdressState = new javax.swing.JLabel();
        textFieldAdressState = new javax.swing.JTextField();
        textLabelTelefon = new javax.swing.JLabel();
        textFieldTelefon = new javax.swing.JTextField();
        textLabelEmail = new javax.swing.JLabel();
        textFieldEmail = new javax.swing.JTextField();
        textLabelStateQuit = new javax.swing.JLabel();
        textLabelAdditional = new javax.swing.JLabel();
        ComboBoxCodeOfPodrazdel = new javax.swing.JComboBox();
        textFieldDoljnost = new javax.swing.JLabel();
        jTextField27 = new javax.swing.JTextField();
        ComboBoxDoljnost = new javax.swing.JComboBox();
        ComboBoxUchenZvanie = new javax.swing.JComboBox();
        ComboBoxUchenStepen = new javax.swing.JComboBox();
        ComboBoxStatus = new javax.swing.JComboBox();
        ComboBoxNationality = new javax.swing.JComboBox();
        ComboBoxSex = new javax.swing.JComboBox();
        dateFieldDateofbirth = new com.toedter.calendar.JDateChooser();
        textFieldUchebnNagruzka1 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jScrollPane15 = new javax.swing.JScrollPane();
        textFieldAdditional = new javax.swing.JTextArea();
        tabSpravochnik = new javax.swing.JPanel();
        TabPaneSprav = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        CountryPanel = new javax.swing.JPanel();
        EditPanelCount = new javax.swing.JPanel();
        sprAddCoun = new javax.swing.JButton();
        sprEditCoun = new javax.swing.JButton();
        sprDeleteCoun = new javax.swing.JButton();
        updtCountry = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCountry = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        EditPanelNation = new javax.swing.JPanel();
        sprAddNation = new javax.swing.JButton();
        sprEditNation = new javax.swing.JButton();
        sprDeleteNation = new javax.swing.JButton();
        updtNaty = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableNationality = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        EditPanelRegion = new javax.swing.JPanel();
        sprAddRegion = new javax.swing.JButton();
        sprEditRegion = new javax.swing.JButton();
        sprDeleteRegion = new javax.swing.JButton();
        updtRegn = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableRegion = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        EditPanelDoljnost = new javax.swing.JPanel();
        sprAddDoljnost = new javax.swing.JButton();
        sprEditDoljnost = new javax.swing.JButton();
        sprDeleteDoljnost = new javax.swing.JButton();
        updtDolj = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        tableDoljnost = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        EditPanelUchenZvan = new javax.swing.JPanel();
        sprAddUchenZvan = new javax.swing.JButton();
        sprEditUchenZvan = new javax.swing.JButton();
        sprDeleteUchenZvan = new javax.swing.JButton();
        updtUzvan = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableUchenZvan = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        EditPanelUchenStep = new javax.swing.JPanel();
        sprAddUchenStep = new javax.swing.JButton();
        sprEditUchenStep = new javax.swing.JButton();
        sprDeleteUchenStep = new javax.swing.JButton();
        updtUstep = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableUchenStep = new javax.swing.JTable();
        panelSprStatusPPS = new javax.swing.JPanel();
        EditPanelStatusPPS = new javax.swing.JPanel();
        sprAddStatusPPS = new javax.swing.JButton();
        sprEditStatusPPS = new javax.swing.JButton();
        sprDeleteStatusPPS = new javax.swing.JButton();
        updtStPPS = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableStatusPPS = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        EditPanelDisciplines = new javax.swing.JPanel();
        sprAddDisciplines = new javax.swing.JButton();
        sprEditDisciplines = new javax.swing.JButton();
        sprDeleteDisciplines = new javax.swing.JButton();
        updtDisc = new javax.swing.JButton();
        jScrollPane14 = new javax.swing.JScrollPane();
        tableDisciplines = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        EditPanelUsZachisl = new javax.swing.JPanel();
        sprAddUsZachisl = new javax.swing.JButton();
        sprEditUsZachisl = new javax.swing.JButton();
        sprDeleteUsZachisl = new javax.swing.JButton();
        updtUslZach = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        tableUsZachisl = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        EditPanelFormob = new javax.swing.JPanel();
        sprAddFormob = new javax.swing.JButton();
        sprEditFormob = new javax.swing.JButton();
        sprDeleteFormob = new javax.swing.JButton();
        updtFromob = new javax.swing.JButton();
        jScrollPane12 = new javax.swing.JScrollPane();
        tableFormob = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        EditPanelSostOb = new javax.swing.JPanel();
        sprAddSostOb = new javax.swing.JButton();
        sprEditSostOb = new javax.swing.JButton();
        sprDeleteSostOb = new javax.swing.JButton();
        updtSostob = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        tableSostOb = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        edPanIdLabel = new javax.swing.JLabel();
        edPanNameLabel = new javax.swing.JLabel();
        edPanIdField = new javax.swing.JTextField();
        edPanNameField = new javax.swing.JTextField();
        tabSearch = new javax.swing.JPanel();
        jTabSearch = new javax.swing.JTabbedPane();
        panelStudentSearch = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchStudentTable = new javax.swing.JTable();
        panelStateSearch = new javax.swing.JPanel();
        searchStateTextField = new javax.swing.JTextField();
        searchButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        searchStateResult = new javax.swing.JTable();
        mainMenuBar = new javax.swing.JMenuBar();
        menuSettings = new javax.swing.JMenu();
        jmenuSettings = new javax.swing.JMenuItem();
        jmenuExit = new javax.swing.JMenuItem();
        menu3nk = new javax.swing.JMenu();
        menuService = new javax.swing.JMenu();
        print_Service = new javax.swing.JMenu();
        printSpravka = new javax.swing.JMenuItem();
        printSpravkaSep = new javax.swing.JPopupMenu.Separator();
        printEkzamList = new javax.swing.JMenuItem();
        printEkzamListSep = new javax.swing.JPopupMenu.Separator();
        printSpisokGroup = new javax.swing.JMenuItem();
        printSpisokGroupSep = new javax.swing.JPopupMenu.Separator();
        printJournalDvij = new javax.swing.JMenuItem();
        printJournalDvijSep = new javax.swing.JPopupMenu.Separator();
        printUchPlanStud = new javax.swing.JMenuItem();
        printUchPlanStudSep = new javax.swing.JPopupMenu.Separator();
        printUchKartochka = new javax.swing.JMenuItem();
        printUchKartochkaSep = new javax.swing.JPopupMenu.Separator();
        printGosAttest = new javax.swing.JMenuItem();
        printVedTekControlSep = new javax.swing.JPopupMenu.Separator();
        printVedTekControl = new javax.swing.JMenuItem();
        journalBallovPrintSep = new javax.swing.JPopupMenu.Separator();
        journalBallovPrint = new javax.swing.JMenuItem();
        JmenuSpace = new javax.swing.JMenu();
        jMenuUserLogin = new javax.swing.JMenu();
        jMenuUserFIO = new javax.swing.JMenu();
        date_txt = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BDoc");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1120, 720));
        setName("frame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1180, 720));

        tabPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabStructure.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        downPanelGroups.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(1213, 27));

        addGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        addGroupButton.setToolTipText("Добавть запись");
        addGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGroupButtonActionPerformed(evt);
            }
        });

        LabelNameOfGroup.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelNameOfGroup.setText("Группы");

        editGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        editGroupButton.setToolTipText("Редактировать запись");
        editGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGroupButtonActionPerformed(evt);
            }
        });

        cancelGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing2.png"))); // NOI18N
        cancelGroupButton.setToolTipText("Прекратить редактирование");
        cancelGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelGroupButtonActionPerformed(evt);
            }
        });

        deleteGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteGroupButton.setToolTipText("Удалить запись");
        deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupButtonActionPerformed(evt);
            }
        });

        workWithVedomButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/workwithtable.png"))); // NOI18N
        workWithVedomButton.setToolTipText("Работа с ведомостью");
        workWithVedomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workWithVedomButtonActionPerformed(evt);
            }
        });

        raznoskaButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Export.png"))); // NOI18N
        raznoskaButton.setToolTipText("Перевод группы семестр -> семестр");
        raznoskaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raznoskaButtonActionPerformed(evt);
            }
        });

        updateTableGroups.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updateTableGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableGroupsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(LabelNameOfGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addComponent(addGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(editGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cancelGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(updateTableGroups, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addComponent(deleteGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(workWithVedomButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(raznoskaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(621, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editGroupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addGroupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNameOfGroup, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteGroupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workWithVedomButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(raznoskaButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(updateTableGroups, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(cancelGroupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        editGroupPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0))));
        editGroupPanel.setMinimumSize(new java.awt.Dimension(373, 320));
        editGroupPanel.setPreferredSize(new java.awt.Dimension(390, 250));

        fieldSemestrGroup.setEnabled(false);
        fieldSemestrGroup.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, comboSemestrGroup, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSemestrGroup, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        fieldMaterGroup.setEditable(false);

        jLabel1.setText("Программа");

        jLabel2.setText("Группа");

        jLabel3.setText("Направление");

        jLabel4.setText("Семестр");

        jLabel5.setText("Куратор");

        jLabel6.setText("Форма обучения");

        comboSemestrGroup.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        comboTeacherEditGroup.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboTeacherEditGroupPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        javax.swing.GroupLayout editGroupPanelLayout = new javax.swing.GroupLayout(editGroupPanel);
        editGroupPanel.setLayout(editGroupPanelLayout);
        editGroupPanelLayout.setHorizontalGroup(
            editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editGroupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editGroupPanelLayout.createSequentialGroup()
                        .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editGroupPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldDepIdGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNaprGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldFromaobuchGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(editGroupPanelLayout.createSequentialGroup()
                        .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(fieldMaterGroup, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldSemestrGroup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                        .addGap(30, 30, 30)
                        .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboSemestrGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTeacherEditGroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        editGroupPanelLayout.setVerticalGroup(
            editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editGroupPanelLayout.createSequentialGroup()
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(fieldDepIdGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(fieldNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNaprGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldSemestrGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboSemestrGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fieldMaterGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTeacherEditGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editGroupPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldFromaobuchGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Groups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Программа", "Группа", "Направление", "Семестр", "Куратор", "Форма обучения"
            }
        ));
        Table_Groups.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table_GroupsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Table_Groups);

        jScrollPane18.setViewportView(jScrollPane1);

        javax.swing.GroupLayout downPanelGroupsLayout = new javax.swing.GroupLayout(downPanelGroups);
        downPanelGroups.setLayout(downPanelGroupsLayout);
        downPanelGroupsLayout.setHorizontalGroup(
            downPanelGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPanelGroupsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editGroupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 1267, Short.MAX_VALUE)
        );
        downPanelGroupsLayout.setVerticalGroup(
            downPanelGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPanelGroupsLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(downPanelGroupsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editGroupPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        downPanelStructure.addTab("Группы  ", new javax.swing.ImageIcon(getClass().getResource("/images/groups.png")), downPanelGroups); // NOI18N

        downPlaneButtonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        downButtonPanelText.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        downButtonPanelText.setText("Учебные планы групп");

        deleteButtonPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        deleteButtonPlane.setToolTipText("Удалить запись");
        deleteButtonPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonPlaneActionPerformed(evt);
            }
        });

        raznoskaButtonPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Export.png"))); // NOI18N
        raznoskaButtonPlane.setToolTipText("Разноска учебного плана студентам группы");
        raznoskaButtonPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raznoskaButtonPlaneActionPerformed(evt);
            }
        });

        applyButtonPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        applyButtonPlane.setToolTipText("Добавить учебный план группы");
        applyButtonPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyButtonPlaneActionPerformed(evt);
            }
        });

        editButtonPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        editButtonPlane.setToolTipText("Редактировать");
        editButtonPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonPlaneActionPerformed(evt);
            }
        });

        addButtonPlane.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        addButtonPlane.setToolTipText("Новая");
        addButtonPlane.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonPlaneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editUchPlgPanelLayout = new javax.swing.GroupLayout(editUchPlgPanel);
        editUchPlgPanel.setLayout(editUchPlgPanelLayout);
        editUchPlgPanelLayout.setHorizontalGroup(
            editUchPlgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editUchPlgPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(addButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(raznoskaButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(279, 279, 279)
                .addComponent(applyButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deleteButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        editUchPlgPanelLayout.setVerticalGroup(
            editUchPlgPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(editButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(raznoskaButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(applyButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(deleteButtonPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout downPlaneButtonPanelLayout = new javax.swing.GroupLayout(downPlaneButtonPanel);
        downPlaneButtonPanel.setLayout(downPlaneButtonPanelLayout);
        downPlaneButtonPanelLayout.setHorizontalGroup(
            downPlaneButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPlaneButtonPanelLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(downButtonPanelText, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addComponent(editUchPlgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        downPlaneButtonPanelLayout.setVerticalGroup(
            downPlaneButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPlaneButtonPanelLayout.createSequentialGroup()
                .addGroup(downPlaneButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(downButtonPanelText)
                    .addComponent(editUchPlgPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ScrollPanelPlane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_PlaneOfGroup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Группа", "Семестр", "Дисциплина", "Часы", "Кредиты", "Контроль"
            }
        ));
        Table_PlaneOfGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_PlaneOfGroupMouseClicked(evt);
            }
        });
        ScrollPanelPlane.setViewportView(Table_PlaneOfGroup);

        editPanelPlane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        editPanelPlane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        planeTextLabelSemestr.setText("Семестр");
        editPanelPlane.add(planeTextLabelSemestr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        planeComboBoxSemestr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        editPanelPlane.add(planeComboBoxSemestr, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, 270, -1));

        planeTextLabelDisciplina.setText("Дисциплина");
        editPanelPlane.add(planeTextLabelDisciplina, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        planeComboBoxDisciplina.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                planeComboBoxDisciplinaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        editPanelPlane.add(planeComboBoxDisciplina, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 270, -1));
        editPanelPlane.add(planeTextFieldDisciplina1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 80, -1));

        planeTextLabelPrepodavatel.setText("Преподаватель");
        editPanelPlane.add(planeTextLabelPrepodavatel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        planeComboBoxPrepodavatel.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                planeComboBoxPrepodavatelPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        editPanelPlane.add(planeComboBoxPrepodavatel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 300, -1));

        planeTextLabelChasy.setText("Часы");
        editPanelPlane.add(planeTextLabelChasy, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));
        editPanelPlane.add(planeTextFieldChasy, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 130, -1));

        planeTextLabelCredity.setText("Кредиты");
        editPanelPlane.add(planeTextLabelCredity, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        planeTextFieldControl.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, planeComboBoxControl, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), planeTextFieldControl, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        editPanelPlane.add(planeTextFieldControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 80, -1));

        planeTextLabelControl.setText("Контроль");
        editPanelPlane.add(planeTextLabelControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        planeComboBoxControl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Экзамен", "Зачет", "Курсовая", "Практика" }));
        editPanelPlane.add(planeComboBoxControl, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, 230, -1));

        planeTextFieldSemestr.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, planeComboBoxSemestr, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), planeTextFieldSemestr, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        editPanelPlane.add(planeTextFieldSemestr, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 80, -1));
        editPanelPlane.add(planeTextFieldCredity1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 130, -1));

        nameOfTeacher.setEnabled(false);
        editPanelPlane.add(nameOfTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 50, -1));

        javax.swing.GroupLayout downPanelPlaneLayout = new javax.swing.GroupLayout(downPanelPlane);
        downPanelPlane.setLayout(downPanelPlaneLayout);
        downPanelPlaneLayout.setHorizontalGroup(
            downPanelPlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPanelPlaneLayout.createSequentialGroup()
                .addComponent(ScrollPanelPlane, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPanelPlane, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE))
            .addComponent(downPlaneButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        downPanelPlaneLayout.setVerticalGroup(
            downPanelPlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downPanelPlaneLayout.createSequentialGroup()
                .addComponent(downPlaneButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(downPanelPlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(editPanelPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScrollPanelPlane, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        downPanelStructure.addTab("Учебные планы групп  ", new javax.swing.ImageIcon(getClass().getResource("/images/ucplans.png")), downPanelPlane); // NOI18N

        tabStructureButtonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        TabStructureTableName.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        TabStructureTableName.setText("Программы");
        TabStructureTableName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        programmAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        programmAddButton.setToolTipText("Добавить запись");
        programmAddButton.setPreferredSize(new java.awt.Dimension(50, 50));
        programmAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programmAddButtonActionPerformed(evt);
            }
        });

        programmEditButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        programmEditButton.setToolTipText("Редактировать поле");
        programmEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programmEditButtonActionPerformed(evt);
            }
        });

        programmApplyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        programmApplyButton.setToolTipText("Применить изменения");
        programmApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programmApplyButtonActionPerformed(evt);
            }
        });

        programmDeleteButton.setBackground(new java.awt.Color(255, 255, 255));
        programmDeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        programmDeleteButton.setToolTipText("Удалить");
        programmDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                programmDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabStructureButtonPanelLayout = new javax.swing.GroupLayout(tabStructureButtonPanel);
        tabStructureButtonPanel.setLayout(tabStructureButtonPanelLayout);
        tabStructureButtonPanelLayout.setHorizontalGroup(
            tabStructureButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStructureButtonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TabStructureTableName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(programmAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(programmEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(190, 190, 190)
                .addComponent(programmApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(programmDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabStructureButtonPanelLayout.setVerticalGroup(
            tabStructureButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStructureButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(TabStructureTableName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(programmAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(programmEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStructureButtonPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(tabStructureButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(programmDeleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(programmApplyButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        ProgrammScrollPanelField.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Departments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Код", "Наименование", "Руководитель", "Лаборант", "Телефон", "Е-Mail"
            }
        ));
        Table_Departments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table_DepartmentsMouseReleased(evt);
            }
        });
        Table_Departments.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Table_DepartmentsKeyPressed(evt);
            }
        });
        ProgrammScrollPanelField.setViewportView(Table_Departments);

        editDepPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        labelMaileDepE.setText("E-Mail");

        labelPhoneDepE.setText("Телефон");

        labelLaborantDepE.setText("Лаборант");

        labelHeaderDepE.setText("Руководитель");

        labelNameDepE.setText("Наименование");

        labelCodeIdDepE.setText("Код подразделения");

        javax.swing.GroupLayout editDepPanelLayout = new javax.swing.GroupLayout(editDepPanel);
        editDepPanel.setLayout(editDepPanelLayout);
        editDepPanelLayout.setHorizontalGroup(
            editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editDepPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelHeaderDepE)
                    .addComponent(labelLaborantDepE)
                    .addComponent(labelPhoneDepE)
                    .addComponent(labelMaileDepE)
                    .addComponent(labelCodeIdDepE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelNameDepE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldMaileDepE, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                    .addComponent(fieldCodeIdDep)
                    .addComponent(fieldNameDepE)
                    .addComponent(fieldHeaderDepE)
                    .addComponent(fieldLaborantDepE)
                    .addComponent(fieldPhoneDepE))
                .addContainerGap())
        );
        editDepPanelLayout.setVerticalGroup(
            editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editDepPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldCodeIdDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodeIdDepE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldNameDepE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNameDepE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldHeaderDepE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelHeaderDepE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldLaborantDepE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelLaborantDepE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPhoneDepE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelPhoneDepE, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(editDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldMaileDepE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMaileDepE, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabStructureLayout = new javax.swing.GroupLayout(tabStructure);
        tabStructure.setLayout(tabStructureLayout);
        tabStructureLayout.setHorizontalGroup(
            tabStructureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabStructureButtonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabStructureLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabStructureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabStructureLayout.createSequentialGroup()
                        .addComponent(ProgrammScrollPanelField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editDepPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(downPanelStructure, javax.swing.GroupLayout.Alignment.TRAILING)))
        );
        tabStructureLayout.setVerticalGroup(
            tabStructureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStructureLayout.createSequentialGroup()
                .addComponent(tabStructureButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStructureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(editDepPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProgrammScrollPanelField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downPanelStructure, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        tabPanel.addTab("Структура  ", new javax.swing.ImageIcon(getClass().getResource("/images/icon_quicknav.png")), tabStructure); // NOI18N

        tabStudentEditUchebKartochka.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tabStudentEditUchebKartochka.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textIdStudent.setText("ID");
        tabStudentEditUchebKartochka.add(textIdStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 14, -1, -1));
        tabStudentEditUchebKartochka.add(fieldIdStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 91, -1));

        textFioStudent.setText("ФИО");
        tabStudentEditUchebKartochka.add(textFioStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, -1, -1));
        tabStudentEditUchebKartochka.add(fieldFamiliyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 230, -1));

        textBirthStudent.setText("Дата рождения");
        tabStudentEditUchebKartochka.add(textBirthStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 74, -1, -1));
        tabStudentEditUchebKartochka.add(fieldNameStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 40, 230, -1));
        tabStudentEditUchebKartochka.add(fieldOtchestvoStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, 180, -1));

        calendarBirthStudent.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(calendarBirthStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 160, -1));

        textNationalityStudent.setText("Национальность");
        tabStudentEditUchebKartochka.add(textNationalityStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 100, 20));

        fieldNationalityStudent.setDoubleBuffered(true);
        fieldNationalityStudent.setEnabled(false);
        tabStudentEditUchebKartochka.add(fieldNationalityStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 140, -1));

        chooserNationalityStudent.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserNationalityStudentPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStudentEditUchebKartochka.add(chooserNationalityStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 70, 180, -1));

        jLabel8.setText("Страна");
        tabStudentEditUchebKartochka.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, -1, -1));

        chooserCounrtyStudent.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserCounrtyStudentPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStudentEditUchebKartochka.add(chooserCounrtyStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 140, -1));

        fieldCountryStudent.setEnabled(false);
        tabStudentEditUchebKartochka.add(fieldCountryStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 140, -1));

        textRegionStudent.setText("Область");
        tabStudentEditUchebKartochka.add(textRegionStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 133, -1, -1));

        fieldSexStudent.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, chooserSexStudent, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSexStudent, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tabStudentEditUchebKartochka.add(fieldSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, 28, -1));

        chooserRegionStudent.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserRegionStudentPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStudentEditUchebKartochka.add(chooserRegionStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, 140, -1));

        textSexStudent.setText("Пол");
        tabStudentEditUchebKartochka.add(textSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        fieldRegionStudent1.setEnabled(false);
        tabStudentEditUchebKartochka.add(fieldRegionStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 140, -1));

        textPropiskaStudent.setText("Прописка");
        tabStudentEditUchebKartochka.add(textPropiskaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 162, -1, -1));
        tabStudentEditUchebKartochka.add(fieldPropiskaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 490, -1));

        textProjivanieStudent.setText("Проживание");
        tabStudentEditUchebKartochka.add(textProjivanieStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, -1));
        tabStudentEditUchebKartochka.add(fieldProjivanieStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 490, -1));

        textPhoneStudent.setText("Телефон");
        tabStudentEditUchebKartochka.add(textPhoneStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 219, -1, -1));
        tabStudentEditUchebKartochka.add(fieldPhoneStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, 153, -1));

        textEmailStudent.setText("E-Mail");
        tabStudentEditUchebKartochka.add(textEmailStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 220, -1, -1));
        tabStudentEditUchebKartochka.add(fieldPasportSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 63, -1));

        textPasportStudent.setText("Паспорт");
        tabStudentEditUchebKartochka.add(textPasportStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, -1, -1));
        tabStudentEditUchebKartochka.add(fieldEmailStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, 250, -1));

        textPasportNumberStudent.setText("№");
        tabStudentEditUchebKartochka.add(textPasportNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, -1, -1));

        textAttestatStudent.setText("Аттестат");
        tabStudentEditUchebKartochka.add(textAttestatStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));
        tabStudentEditUchebKartochka.add(fieldPasportNumberStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 130, -1));

        jLabel9.setText("№");
        tabStudentEditUchebKartochka.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, -1, -1));
        tabStudentEditUchebKartochka.add(fieldAttestatSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 280, 60, -1));

        jLabel10.setText("Диплом");
        tabStudentEditUchebKartochka.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, -1, -1));
        tabStudentEditUchebKartochka.add(fieldAttestatNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 120, -1));

        jLabel11.setText("№");
        tabStudentEditUchebKartochka.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, -1, -1));

        jLabel12.setText("Дата выдачи");
        tabStudentEditUchebKartochka.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
        tabStudentEditUchebKartochka.add(fieldDiplomNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 280, 130, 20));

        dchooserVydachiDoc.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(dchooserVydachiDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 250, 120, -1));
        tabStudentEditUchebKartochka.add(fieldMestoVydachiDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 360, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("Приказ о ");
        jLabel13.setToolTipText("");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        tabStudentEditUchebKartochka.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));

        jLabel14.setText("зачислении");
        tabStudentEditUchebKartochka.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, -1, -1));
        tabStudentEditUchebKartochka.add(fieldDiplomSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 70, -1));

        jLabel15.setText("от");
        tabStudentEditUchebKartochka.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, -1, -1));
        tabStudentEditUchebKartochka.add(fieldNumOfPrikazZachisl, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, 70, 23));

        jLabel16.setText("Семестр");
        tabStudentEditUchebKartochka.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, -1, 10));

        fieldUsloviyaZachisleniya.setEnabled(false);
        tabStudentEditUchebKartochka.add(fieldUsloviyaZachisleniya, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 70, -1));

        chooserSemestrStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        tabStudentEditUchebKartochka.add(chooserSemestrStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 120, -1));

        jLabel17.setText("Сумма контракта");
        tabStudentEditUchebKartochka.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 370, -1, -1));

        fieldSemestrStudent.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, chooserSemestrStudent, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSemestrStudent, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tabStudentEditUchebKartochka.add(fieldSemestrStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 70, -1));

        jLabel18.setText("Диплом БФЭА");
        tabStudentEditUchebKartochka.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));
        tabStudentEditUchebKartochka.add(fieldSumOfContract, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 370, 180, -1));

        jLabel19.setText("№");
        tabStudentEditUchebKartochka.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, -1, -1));
        tabStudentEditUchebKartochka.add(fieldDiplBfaSeiyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 70, 20));

        jLabel20.setText("Дата выдачи");
        tabStudentEditUchebKartochka.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, 10));

        dateVidachDiplBfa.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(dateVidachDiplBfa, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 400, 180, -1));

        jLabel21.setText("Состояние");
        tabStudentEditUchebKartochka.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, 20));
        tabStudentEditUchebKartochka.add(fieldDiplBfaNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 120, 20));

        chooserSostStudent.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserSostStudentPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStudentEditUchebKartochka.add(chooserSostStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 150, -1));

        jLabel22.setText("Дата окончания");
        tabStudentEditUchebKartochka.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 430, -1, -1));

        dateOfSostStudent.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(dateOfSostStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 430, 180, -1));

        jLabel23.setText("Примечание");
        tabStudentEditUchebKartochka.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        fieldSostObuchStudent.setEnabled(false);
        tabStudentEditUchebKartochka.add(fieldSostObuchStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 70, 20));

        jLabel24.setText("Приказы");
        tabStudentEditUchebKartochka.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, -1, -1));

        chooserSexStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "М", "Ж" }));
        tabStudentEditUchebKartochka.add(chooserSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 50, -1));

        choserDateOfPrikazZachisl.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(choserDateOfPrikazZachisl, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 140, 23));

        chooserUsloviyaZachisleniya1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserUsloviyaZachisleniya1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStudentEditUchebKartochka.add(chooserUsloviyaZachisleniya1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 340, 200, -1));
        tabStudentEditUchebKartochka.add(fieldPasportNumberStudent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 100, -1));

        dchooserVydachiDoc1.setDateFormatString("yyyy-MM-dd");
        tabStudentEditUchebKartochka.add(dchooserVydachiDoc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 150, -1));

        descPaneStudentPhoto.setBackground(new java.awt.Color(255, 255, 255));
        descPaneStudentPhoto.setForeground(new java.awt.Color(255, 255, 255));
        descPaneStudentPhoto.setAutoscrolls(true);

        textPaneStudentPhoto.setForeground(new java.awt.Color(255, 255, 255));
        textPaneStudentPhoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textPaneStudentPhoto.setToolTipText("Фото студента");
        descPaneStudentPhoto.add(textPaneStudentPhoto);
        textPaneStudentPhoto.setBounds(0, 0, 140, 170);
        descPaneStudentPhoto.setLayer(textPaneStudentPhoto, javax.swing.JLayeredPane.PALETTE_LAYER);

        tabStudentEditUchebKartochka.add(descPaneStudentPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, 140, 170));

        path.setSelectedTextColor(new java.awt.Color(244, 244, 244));
        tabStudentEditUchebKartochka.add(path, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, 110, -1));

        fieldAdditionalStudent2.setColumns(20);
        fieldAdditionalStudent2.setRows(5);
        jScrollPane16.setViewportView(fieldAdditionalStudent2);

        tabStudentEditUchebKartochka.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 470, 280, 60));

        fieldAdditionalStudent1.setColumns(20);
        fieldAdditionalStudent1.setRows(5);
        jScrollPane17.setViewportView(fieldAdditionalStudent1);

        tabStudentEditUchebKartochka.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 250, 60));

        TabStudentEditTab.addTab("Учебная карточка  ", new javax.swing.ImageIcon(getClass().getResource("/images/uchebnaya kart.png")), tabStudentEditUchebKartochka, "Учебная карточка студента"); // NOI18N

        tabStudUchPlanScroll.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_UchPlaneStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Группа", "Семестр", "Код дисциплины", "Часы", "Кредиты", "Баллы", "Оценка", "Контроль", "Дата сдачи"
            }
        ));
        Table_UchPlaneStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table_UchPlaneStudentMouseReleased(evt);
            }
        });
        tabStudUchPlanScroll.setViewportView(Table_UchPlaneStudent);

        tabStudUchPlanButtons.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        studUchPlAddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        studUchPlAddButton.setToolTipText("Добавить новую");
        studUchPlAddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studUchPlAddButtonActionPerformed(evt);
            }
        });

        studUchPlEditButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        studUchPlEditButton.setToolTipText("Редактировать");
        studUchPlEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studUchPlEditButtonActionPerformed(evt);
            }
        });

        studUchPlCancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing2.png"))); // NOI18N
        studUchPlCancelButton.setToolTipText("Отменить редактирование");
        studUchPlCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studUchPlCancelButtonActionPerformed(evt);
            }
        });

        studUchPlApplyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        studUchPlApplyButton.setToolTipText("Применить");
        studUchPlApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studUchPlApplyButtonActionPerformed(evt);
            }
        });

        studUchPlDeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        studUchPlDeleteButton.setToolTipText("Удалить");
        studUchPlDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studUchPlDeleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabStudUchPlanButtonsLayout = new javax.swing.GroupLayout(tabStudUchPlanButtons);
        tabStudUchPlanButtons.setLayout(tabStudUchPlanButtonsLayout);
        tabStudUchPlanButtonsLayout.setHorizontalGroup(
            tabStudUchPlanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudUchPlanButtonsLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(studUchPlAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studUchPlEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(studUchPlCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(studUchPlApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studUchPlDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(355, Short.MAX_VALUE))
        );
        tabStudUchPlanButtonsLayout.setVerticalGroup(
            tabStudUchPlanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudUchPlanButtonsLayout.createSequentialGroup()
                .addGroup(tabStudUchPlanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studUchPlCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabStudUchPlanButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(studUchPlAddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlEditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(studUchPlDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studUchPlApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        tabStudUchPlanPanelEdit.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        studUchPlSemestrText.setText("Семестр");

        studUchPlSemestrComboB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));

        studUchPlControlText.setText("Контроль");

        studUchPlControComboB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Экзамен", "Зачет", "Курсовая", "Практика" }));

        studUchPlDisciplinaText.setText("Дисциплина");

        studUchPlDisciplinaField.setEnabled(false);

        studUchPlChasyText.setText("Часы");

        studUchPlKreditytrText.setText("Кредиты");

        studUchPlBallyText.setText("Баллы");

        studUchPlOcenkaText.setText("Оценка");

        studUchPlDataSdachiText.setText("Дата сдачи");

        chooserStudUchPlDataSdachi.setDateFormatString("yyyy-MM-dd");

        studUchPlDisciplinaComboB1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                studUchPlDisciplinaComboB1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        studUchPlSemestrTextF.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, studUchPlSemestrComboB, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), studUchPlSemestrTextF, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        studUchPlControTextF.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, studUchPlControComboB, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), studUchPlControTextF, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        raznoskaLog.setText("Журнал разноски:");

        logAddLoginLabel.setBackground(new java.awt.Color(204, 204, 204));

        logEditDetails.setText("Журнал изменений:");

        logEditUserField.setBackground(new java.awt.Color(204, 204, 204));

        logLastEditDate.setDateFormatString("yyyy-MM-dd");

        logDateLabel.setDateFormatString("yyyy-MM-dd");

        labelNomberOfBegunok.setText("№ Бегунка");

        javax.swing.GroupLayout tabStudUchPlanPanelEditLayout = new javax.swing.GroupLayout(tabStudUchPlanPanelEdit);
        tabStudUchPlanPanelEdit.setLayout(tabStudUchPlanPanelEditLayout);
        tabStudUchPlanPanelEditLayout.setHorizontalGroup(
            tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(raznoskaLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logAddLoginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logEditDetails)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logEditUserField, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logLastEditDate, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studUchPlSemestrText)
                    .addComponent(studUchPlDisciplinaText)
                    .addComponent(studUchPlChasyText)
                    .addComponent(studUchPlBallyText))
                .addGap(26, 26, 26)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                        .addComponent(studUchPlSemestrTextF, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(studUchPlSemestrComboB, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(studUchPlControlText, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(studUchPlControTextF, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(studUchPlControComboB, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studUchPlDisciplinaField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studUchPlChasyField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studUchPlBallyField, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studUchPlKreditytrText)
                            .addComponent(studUchPlOcenkaText))
                        .addGap(35, 35, 35)
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studUchPlDisciplinaComboB1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(studUchPlCredityField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(studUchPlOcenkaField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(studUchPlDataSdachiText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(labelNomberOfBegunok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(numberOfBegunok)
                                    .addComponent(chooserStudUchPlDataSdachi, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(numberBegunokChek)))))
                .addContainerGap(32, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        tabStudUchPlanPanelEditLayout.setVerticalGroup(
            tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(studUchPlSemestrText))
                    .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(studUchPlSemestrComboB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlSemestrTextF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlControlText)
                        .addComponent(studUchPlControTextF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlControComboB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(3, 3, 3)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studUchPlDisciplinaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studUchPlDisciplinaText))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(studUchPlChasyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studUchPlChasyText)
                            .addComponent(studUchPlKreditytrText)))
                    .addGroup(tabStudUchPlanPanelEditLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(studUchPlDisciplinaComboB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNomberOfBegunok, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(studUchPlCredityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberOfBegunok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(numberBegunokChek)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(studUchPlBallyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlBallyText))
                    .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(studUchPlOcenkaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlDataSdachiText, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studUchPlOcenkaText))
                    .addComponent(chooserStudUchPlDataSdachi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(logDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(raznoskaLog)
                        .addComponent(logAddLoginLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(tabStudUchPlanPanelEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(logEditUserField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(logEditDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logLastEditDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        uchplstudID.setForeground(new java.awt.Color(212, 208, 200));
        uchplstudID.setText("qrqq");

        javax.swing.GroupLayout tabStudentUchebnyiPlanLayout = new javax.swing.GroupLayout(tabStudentUchebnyiPlan);
        tabStudentUchebnyiPlan.setLayout(tabStudentUchebnyiPlanLayout);
        tabStudentUchebnyiPlanLayout.setHorizontalGroup(
            tabStudentUchebnyiPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabStudUchPlanScroll)
            .addComponent(tabStudUchPlanButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tabStudUchPlanPanelEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabStudentUchebnyiPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tabStudentUchebnyiPlanLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(uchplstudID)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        tabStudentUchebnyiPlanLayout.setVerticalGroup(
            tabStudentUchebnyiPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudentUchebnyiPlanLayout.createSequentialGroup()
                .addComponent(tabStudUchPlanScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabStudUchPlanButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabStudUchPlanPanelEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
            .addGroup(tabStudentUchebnyiPlanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(tabStudentUchebnyiPlanLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(uchplstudID)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        uchplstudID.getAccessibleContext().setAccessibleName("uchplstudId");

        TabStudentEditTab.addTab("Учебный план  ", new javax.swing.ImageIcon(getClass().getResource("/images/ucplans.png")), tabStudentUchebnyiPlan, "Учебный план студента"); // NOI18N

        tabStructureButtonPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        studentAddButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        studentAddButton1.setToolTipText("Очистить поля и добавить запись");
        studentAddButton1.setPreferredSize(new java.awt.Dimension(50, 50));
        studentAddButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentAddButton1ActionPerformed(evt);
            }
        });

        studentEditButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        studentEditButton1.setToolTipText("Применить изменения");
        studentEditButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentEditButton1ActionPerformed(evt);
            }
        });

        studentCancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing2.png"))); // NOI18N
        studentCancelButton.setToolTipText("Прекратить редактирование");
        studentCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentCancelButtonActionPerformed(evt);
            }
        });

        studentDeleteButton.setBackground(new java.awt.Color(255, 255, 255));
        studentDeleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        studentDeleteButton.setToolTipText("Удалить студента из группы");
        studentDeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentDeleteButtonActionPerformed(evt);
            }
        });

        TabStudentTableName.setEnabled(false);
        TabStudentTableName.setFocusable(false);

        attachStudentImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/attach_image.gif"))); // NOI18N
        attachStudentImage.setToolTipText("Добавить фото студента");
        attachStudentImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachStudentImageActionPerformed(evt);
            }
        });

        studPerevGroupButton.setBackground(new java.awt.Color(255, 255, 255));
        studPerevGroupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/perevodsgruppy.png"))); // NOI18N
        studPerevGroupButton.setToolTipText("Перевод студента Группа -> Группа");
        studPerevGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studPerevGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabStructureButtonPanel1Layout = new javax.swing.GroupLayout(tabStructureButtonPanel1);
        tabStructureButtonPanel1.setLayout(tabStructureButtonPanel1Layout);
        tabStructureButtonPanel1Layout.setHorizontalGroup(
            tabStructureButtonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStructureButtonPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(TabStudentTableName, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(studentAddButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(studentEditButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(studentCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(studentDeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(studPerevGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(attachStudentImage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabStructureButtonPanel1Layout.setVerticalGroup(
            tabStructureButtonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStructureButtonPanel1Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(tabStructureButtonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStructureButtonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStructureButtonPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentEditButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentCancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(studentAddButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(studentDeleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TabStudentTableName)
                        .addComponent(attachStudentImage, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(studPerevGroupButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        tabStudentsScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_Students.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID студента", "Фамилия", "Имя", "Отчество"
            }
        ));
        Table_Students.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_StudentsMouseClicked(evt);
            }
        });
        tabStudentsScrollPane.setViewportView(Table_Students);

        javax.swing.GroupLayout tabStudentsLayout = new javax.swing.GroupLayout(tabStudents);
        tabStudents.setLayout(tabStudentsLayout);
        tabStudentsLayout.setHorizontalGroup(
            tabStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudentsLayout.createSequentialGroup()
                .addComponent(tabStudentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabStudentEditTab, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(tabStructureButtonPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabStudentsLayout.setVerticalGroup(
            tabStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabStudentsLayout.createSequentialGroup()
                .addComponent(tabStructureButtonPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TabStudentEditTab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tabStudentsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 122, Short.MAX_VALUE))
        );

        tabPanel.addTab("Студенты  ", new javax.swing.ImageIcon(getClass().getResource("/images/students.png")), tabStudents); // NOI18N

        tabStateMenuPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabStateLabelText.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        tabStateLabelText.setText("П П Состав");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        jButton1.setToolTipText("Очистить поля и добавить запись");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        jButton2.setToolTipText("Применить изменения");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        jButton3.setToolTipText("Добавить сотрудника в БД");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing2.png"))); // NOI18N
        jButton4.setToolTipText("Прекратить редактирование");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        jButton11.setToolTipText("Удалить сотрудника из БД");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabStateMenuPanelLayout = new javax.swing.GroupLayout(tabStateMenuPanel);
        tabStateMenuPanel.setLayout(tabStateMenuPanelLayout);
        tabStateMenuPanelLayout.setHorizontalGroup(
            tabStateMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStateMenuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabStateLabelText, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        tabStateMenuPanelLayout.setVerticalGroup(
            tabStateMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStateMenuPanelLayout.createSequentialGroup()
                .addGroup(tabStateMenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabStateLabelText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabStateScrollPanel.setBackground(new java.awt.Color(255, 255, 255));
        tabStateScrollPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        Table_State.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Код", "Фамилия", "Имя", "Отчество"
            }
        ));
        Table_State.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Table_StateMouseReleased(evt);
            }
        });
        tabStateScrollPanel.setViewportView(Table_State);

        tabStateEdit.setAlignmentX(0.0F);
        tabStateEdit.setAlignmentY(0.0F);

        tabStateEditPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tabStateEditPanel.setMinimumSize(new java.awt.Dimension(520, 494));
        tabStateEditPanel.setPreferredSize(new java.awt.Dimension(520, 494));
        tabStateEditPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textLabelCodeState.setText("Код");
        textLabelCodeState.setMinimumSize(new java.awt.Dimension(130, 14));
        textLabelCodeState.setPreferredSize(new java.awt.Dimension(130, 14));
        tabStateEditPanel.add(textLabelCodeState, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 17, -1, -1));
        tabStateEditPanel.add(textFieldCodeState, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 14, 170, -1));

        textCodeOfPodrazdel.setText("Код подразделения");
        textCodeOfPodrazdel.setMaximumSize(new java.awt.Dimension(130, 14));
        textCodeOfPodrazdel.setMinimumSize(new java.awt.Dimension(130, 14));
        tabStateEditPanel.add(textCodeOfPodrazdel, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 43, 130, -1));

        textFieldCodeOfPodrazdel.setEnabled(false);
        tabStateEditPanel.add(textFieldCodeOfPodrazdel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 90, -1));

        textLabelFamiliya.setText("Фамилия");
        tabStateEditPanel.add(textLabelFamiliya, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 69, 129, -1));
        tabStateEditPanel.add(textFieldFamiliya, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 66, 500, -1));

        textLabelName.setText("Имя");
        tabStateEditPanel.add(textLabelName, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 95, 132, -1));
        tabStateEditPanel.add(textFieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 92, 500, -1));

        textLabelOtchestvo.setText("Отчество");
        tabStateEditPanel.add(textLabelOtchestvo, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 121, 132, -1));
        tabStateEditPanel.add(textFieldOtchestvo, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 118, 500, -1));

        textLabelUchenZvanie.setText("Ученое звание");
        tabStateEditPanel.add(textLabelUchenZvanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 173, 117, -1));

        textFieldUchenZvanie.setEnabled(false);
        tabStateEditPanel.add(textFieldUchenZvanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 170, 90, -1));

        textLabelUchenStepen.setText("Ученая степень");
        tabStateEditPanel.add(textLabelUchenStepen, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 199, 117, -1));

        textFieldUchenStepen.setEnabled(false);
        tabStateEditPanel.add(textFieldUchenStepen, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 196, 90, -1));

        textLabelStatus.setText("Статус");
        tabStateEditPanel.add(textLabelStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 225, 117, -1));

        textFieldStatus.setEnabled(false);
        textFieldStatus.setMinimumSize(new java.awt.Dimension(65, 20));
        tabStateEditPanel.add(textFieldStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 222, 90, -1));

        textLabelUchebnNagruzka.setText("Учебная нагрузка");
        tabStateEditPanel.add(textLabelUchebnNagruzka, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 254, 117, -1));

        textFieldSexTeacher.setEnabled(false);
        textFieldSexTeacher.setMinimumSize(new java.awt.Dimension(65, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, ComboBoxSex, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), textFieldSexTeacher, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        tabStateEditPanel.add(textFieldSexTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 40, -1));

        textLabelNationality.setText("Национальность");
        tabStateEditPanel.add(textLabelNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 277, 138, -1));

        textFieldNationality.setEnabled(false);
        tabStateEditPanel.add(textFieldNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 274, 90, -1));

        textLabelDateofbirth.setText("Дата рождения");
        tabStateEditPanel.add(textLabelDateofbirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 303, 138, -1));

        textLabelSex.setText("пол");
        tabStateEditPanel.add(textLabelSex, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, -1, -1));

        textLabelAdressState.setText("Адрес");
        tabStateEditPanel.add(textLabelAdressState, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 329, 138, -1));
        tabStateEditPanel.add(textFieldAdressState, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 326, 500, -1));

        textLabelTelefon.setText("Телефон");
        tabStateEditPanel.add(textLabelTelefon, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 355, 138, -1));
        tabStateEditPanel.add(textFieldTelefon, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 352, 240, -1));

        textLabelEmail.setText("E-Mail");
        tabStateEditPanel.add(textLabelEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 381, 138, -1));
        tabStateEditPanel.add(textFieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 378, 180, -1));

        textLabelStateQuit.setText("Дата увольнения");
        tabStateEditPanel.add(textLabelStateQuit, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, -1, -1));

        textLabelAdditional.setText("Примечание");
        tabStateEditPanel.add(textLabelAdditional, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 132, -1));

        ComboBoxCodeOfPodrazdel.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxCodeOfPodrazdelPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxCodeOfPodrazdel, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 400, -1));

        textFieldDoljnost.setText("Должность");
        tabStateEditPanel.add(textFieldDoljnost, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 147, 117, -1));

        jTextField27.setEnabled(false);
        tabStateEditPanel.add(jTextField27, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 144, 90, -1));

        ComboBoxDoljnost.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxDoljnostPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxDoljnost, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 144, 400, -1));

        ComboBoxUchenZvanie.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxUchenZvaniePopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxUchenZvanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 170, 400, -1));

        ComboBoxUchenStepen.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxUchenStepenPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxUchenStepen, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 196, 400, -1));

        ComboBoxStatus.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxStatusPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(259, 222, 400, -1));

        ComboBoxNationality.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboBoxNationalityMouseClicked(evt);
            }
        });
        ComboBoxNationality.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                ComboBoxNationalityPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        tabStateEditPanel.add(ComboBoxNationality, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 274, 400, -1));

        ComboBoxSex.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "М", "Ж" }));
        tabStateEditPanel.add(ComboBoxSex, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 300, 100, -1));

        dateFieldDateofbirth.setDateFormatString("yyyy-MM-dd");
        tabStateEditPanel.add(dateFieldDateofbirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 130, -1));

        textFieldUchebnNagruzka1.setMinimumSize(new java.awt.Dimension(65, 20));
        tabStateEditPanel.add(textFieldUchebnNagruzka1, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 248, 130, -1));

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser1.setMinimumSize(new java.awt.Dimension(27, 27));
        jDateChooser1.setPreferredSize(new java.awt.Dimension(87, 25));
        tabStateEditPanel.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 380, 170, 25));

        textFieldAdditional.setColumns(20);
        textFieldAdditional.setRows(5);
        jScrollPane15.setViewportView(textFieldAdditional);

        tabStateEditPanel.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 420, 490, -1));

        tabStateEdit.addTab("Редактирование", tabStateEditPanel);

        javax.swing.GroupLayout tabStateLayout = new javax.swing.GroupLayout(tabState);
        tabState.setLayout(tabStateLayout);
        tabStateLayout.setHorizontalGroup(
            tabStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabStateMenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(tabStateLayout.createSequentialGroup()
                .addComponent(tabStateScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabStateEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
        );
        tabStateLayout.setVerticalGroup(
            tabStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStateLayout.createSequentialGroup()
                .addComponent(tabStateMenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabStateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabStateScrollPanel)
                    .addComponent(tabStateEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
                .addGap(0, 102, Short.MAX_VALUE))
        );

        tabStateEdit.getAccessibleContext().setAccessibleName("Редактирование");

        tabPanel.addTab("ПП Состав  ", new javax.swing.ImageIcon(getClass().getResource("/images/teachers.png")), tabState); // NOI18N

        TabPaneSprav.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        EditPanelCount.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddCoun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddCoun.setToolTipText("Добавить элемент");
        sprAddCoun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddCounActionPerformed(evt);
            }
        });

        sprEditCoun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditCoun.setToolTipText("Редактировать элемент");
        sprEditCoun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditCounActionPerformed(evt);
            }
        });

        sprDeleteCoun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteCoun.setToolTipText("Удалить элемент");
        sprDeleteCoun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteCounActionPerformed(evt);
            }
        });

        updtCountry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtCountry.setToolTipText("Применить изменения");
        updtCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtCountryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelCountLayout = new javax.swing.GroupLayout(EditPanelCount);
        EditPanelCount.setLayout(EditPanelCountLayout);
        EditPanelCountLayout.setHorizontalGroup(
            EditPanelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelCountLayout.createSequentialGroup()
                .addComponent(updtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddCoun, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditCoun, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteCoun, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        EditPanelCountLayout.setVerticalGroup(
            EditPanelCountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditCoun)
            .addComponent(sprAddCoun)
            .addComponent(sprDeleteCoun)
            .addComponent(updtCountry)
        );

        tableCountry.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableCountry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableCountryMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tableCountry);

        javax.swing.GroupLayout CountryPanelLayout = new javax.swing.GroupLayout(CountryPanel);
        CountryPanel.setLayout(CountryPanelLayout);
        CountryPanelLayout.setHorizontalGroup(
            CountryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelCount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        CountryPanelLayout.setVerticalGroup(
            CountryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CountryPanelLayout.createSequentialGroup()
                .addComponent(EditPanelCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Страны", CountryPanel);

        EditPanelNation.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddNation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddNation.setToolTipText("Добавить элемент");
        sprAddNation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddNationActionPerformed(evt);
            }
        });

        sprEditNation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditNation.setToolTipText("Редактировать элемент");
        sprEditNation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditNationActionPerformed(evt);
            }
        });

        sprDeleteNation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteNation.setToolTipText("Удалить элемент");
        sprDeleteNation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteNationActionPerformed(evt);
            }
        });

        updtNaty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtNaty.setToolTipText("Применить изменения");
        updtNaty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtNatyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelNationLayout = new javax.swing.GroupLayout(EditPanelNation);
        EditPanelNation.setLayout(EditPanelNationLayout);
        EditPanelNationLayout.setHorizontalGroup(
            EditPanelNationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelNationLayout.createSequentialGroup()
                .addComponent(updtNaty, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddNation, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditNation, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteNation, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelNationLayout.setVerticalGroup(
            EditPanelNationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditNation)
            .addComponent(sprAddNation)
            .addComponent(sprDeleteNation)
            .addComponent(updtNaty)
        );

        tableNationality.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableNationality.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableNationalityMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tableNationality);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelNation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(EditPanelNation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Национальности", jPanel7);

        EditPanelRegion.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddRegion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddRegion.setToolTipText("Добавить элемент");
        sprAddRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddRegionActionPerformed(evt);
            }
        });

        sprEditRegion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditRegion.setToolTipText("Редактировать элемент");
        sprEditRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditRegionActionPerformed(evt);
            }
        });

        sprDeleteRegion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteRegion.setToolTipText("Удалить элемент");
        sprDeleteRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteRegionActionPerformed(evt);
            }
        });

        updtRegn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtRegn.setToolTipText("Применить изменения");
        updtRegn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtRegnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelRegionLayout = new javax.swing.GroupLayout(EditPanelRegion);
        EditPanelRegion.setLayout(EditPanelRegionLayout);
        EditPanelRegionLayout.setHorizontalGroup(
            EditPanelRegionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelRegionLayout.createSequentialGroup()
                .addComponent(updtRegn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteRegion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelRegionLayout.setVerticalGroup(
            EditPanelRegionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditRegion)
            .addComponent(sprAddRegion)
            .addComponent(sprDeleteRegion)
            .addComponent(updtRegn)
        );

        tableRegion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableRegion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableRegionMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tableRegion);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelRegion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(EditPanelRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Области", jPanel8);

        EditPanelDoljnost.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddDoljnost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddDoljnost.setToolTipText("Добавить элемент");
        sprAddDoljnost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddDoljnostActionPerformed(evt);
            }
        });

        sprEditDoljnost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditDoljnost.setToolTipText("Редактировать элемент");
        sprEditDoljnost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditDoljnostActionPerformed(evt);
            }
        });

        sprDeleteDoljnost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteDoljnost.setToolTipText("Удалить элемент");
        sprDeleteDoljnost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteDoljnostActionPerformed(evt);
            }
        });

        updtDolj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtDolj.setToolTipText("Применить изменения");
        updtDolj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtDoljActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelDoljnostLayout = new javax.swing.GroupLayout(EditPanelDoljnost);
        EditPanelDoljnost.setLayout(EditPanelDoljnostLayout);
        EditPanelDoljnostLayout.setHorizontalGroup(
            EditPanelDoljnostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelDoljnostLayout.createSequentialGroup()
                .addComponent(updtDolj, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddDoljnost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditDoljnost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteDoljnost, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelDoljnostLayout.setVerticalGroup(
            EditPanelDoljnostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditDoljnost)
            .addComponent(sprAddDoljnost)
            .addComponent(sprDeleteDoljnost)
            .addComponent(updtDolj)
        );

        tableDoljnost.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableDoljnost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableDoljnostMouseReleased(evt);
            }
        });
        jScrollPane10.setViewportView(tableDoljnost);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelDoljnost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(EditPanelDoljnost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Должности", jPanel9);

        EditPanelUchenZvan.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddUchenZvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddUchenZvan.setToolTipText("Добавить элемент");
        sprAddUchenZvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddUchenZvanActionPerformed(evt);
            }
        });

        sprEditUchenZvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditUchenZvan.setToolTipText("Редактировать элемент");
        sprEditUchenZvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditUchenZvanActionPerformed(evt);
            }
        });

        sprDeleteUchenZvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteUchenZvan.setToolTipText("Удалить элемент");
        sprDeleteUchenZvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteUchenZvanActionPerformed(evt);
            }
        });

        updtUzvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtUzvan.setToolTipText("Применить изменения");
        updtUzvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtUzvanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelUchenZvanLayout = new javax.swing.GroupLayout(EditPanelUchenZvan);
        EditPanelUchenZvan.setLayout(EditPanelUchenZvanLayout);
        EditPanelUchenZvanLayout.setHorizontalGroup(
            EditPanelUchenZvanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelUchenZvanLayout.createSequentialGroup()
                .addComponent(updtUzvan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddUchenZvan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditUchenZvan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteUchenZvan, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelUchenZvanLayout.setVerticalGroup(
            EditPanelUchenZvanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditUchenZvan)
            .addComponent(sprAddUchenZvan)
            .addComponent(sprDeleteUchenZvan)
            .addComponent(updtUzvan)
        );

        tableUchenZvan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableUchenZvan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableUchenZvanMouseReleased(evt);
            }
        });
        jScrollPane9.setViewportView(tableUchenZvan);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelUchenZvan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(EditPanelUchenZvan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ученые звания", jPanel10);

        EditPanelUchenStep.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddUchenStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddUchenStep.setToolTipText("Добавить элемент");
        sprAddUchenStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddUchenStepActionPerformed(evt);
            }
        });

        sprEditUchenStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditUchenStep.setToolTipText("Редактировать элемент");
        sprEditUchenStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditUchenStepActionPerformed(evt);
            }
        });

        sprDeleteUchenStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteUchenStep.setToolTipText("Удалить элемент");
        sprDeleteUchenStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteUchenStepActionPerformed(evt);
            }
        });

        updtUstep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtUstep.setToolTipText("Применить изменения");
        updtUstep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtUstepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelUchenStepLayout = new javax.swing.GroupLayout(EditPanelUchenStep);
        EditPanelUchenStep.setLayout(EditPanelUchenStepLayout);
        EditPanelUchenStepLayout.setHorizontalGroup(
            EditPanelUchenStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelUchenStepLayout.createSequentialGroup()
                .addComponent(updtUstep, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddUchenStep, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditUchenStep, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteUchenStep, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelUchenStepLayout.setVerticalGroup(
            EditPanelUchenStepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditUchenStep)
            .addComponent(sprAddUchenStep)
            .addComponent(sprDeleteUchenStep)
            .addComponent(updtUstep)
        );

        tableUchenStep.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableUchenStep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableUchenStepMouseReleased(evt);
            }
        });
        jScrollPane8.setViewportView(tableUchenStep);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelUchenStep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(EditPanelUchenStep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ученые степени", jPanel11);

        EditPanelStatusPPS.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddStatusPPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddStatusPPS.setToolTipText("Добавить элемент");
        sprAddStatusPPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddStatusPPSActionPerformed(evt);
            }
        });

        sprEditStatusPPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditStatusPPS.setToolTipText("Редактировать элемент");
        sprEditStatusPPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditStatusPPSActionPerformed(evt);
            }
        });

        sprDeleteStatusPPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteStatusPPS.setToolTipText("Удалить элемент");
        sprDeleteStatusPPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteStatusPPSActionPerformed(evt);
            }
        });

        updtStPPS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtStPPS.setToolTipText("Применить изменения");
        updtStPPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtStPPSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelStatusPPSLayout = new javax.swing.GroupLayout(EditPanelStatusPPS);
        EditPanelStatusPPS.setLayout(EditPanelStatusPPSLayout);
        EditPanelStatusPPSLayout.setHorizontalGroup(
            EditPanelStatusPPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelStatusPPSLayout.createSequentialGroup()
                .addComponent(updtStPPS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123)
                .addComponent(sprAddStatusPPS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditStatusPPS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteStatusPPS, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelStatusPPSLayout.setVerticalGroup(
            EditPanelStatusPPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditStatusPPS)
            .addComponent(sprAddStatusPPS)
            .addComponent(sprDeleteStatusPPS)
            .addComponent(updtStPPS)
        );

        tableStatusPPS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableStatusPPS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableStatusPPSMouseReleased(evt);
            }
        });
        jScrollPane7.setViewportView(tableStatusPPS);

        javax.swing.GroupLayout panelSprStatusPPSLayout = new javax.swing.GroupLayout(panelSprStatusPPS);
        panelSprStatusPPS.setLayout(panelSprStatusPPSLayout);
        panelSprStatusPPSLayout.setHorizontalGroup(
            panelSprStatusPPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelStatusPPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        panelSprStatusPPSLayout.setVerticalGroup(
            panelSprStatusPPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSprStatusPPSLayout.createSequentialGroup()
                .addComponent(EditPanelStatusPPS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Статус ППС", panelSprStatusPPS);

        EditPanelDisciplines.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddDisciplines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddDisciplines.setToolTipText("Добавить элемент");
        sprAddDisciplines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddDisciplinesActionPerformed(evt);
            }
        });

        sprEditDisciplines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditDisciplines.setToolTipText("Редактировать элемент");
        sprEditDisciplines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditDisciplinesActionPerformed(evt);
            }
        });

        sprDeleteDisciplines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteDisciplines.setToolTipText("Удалить элемент");
        sprDeleteDisciplines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteDisciplinesActionPerformed(evt);
            }
        });

        updtDisc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtDisc.setToolTipText("Применить изменения");
        updtDisc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtDiscActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelDisciplinesLayout = new javax.swing.GroupLayout(EditPanelDisciplines);
        EditPanelDisciplines.setLayout(EditPanelDisciplinesLayout);
        EditPanelDisciplinesLayout.setHorizontalGroup(
            EditPanelDisciplinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelDisciplinesLayout.createSequentialGroup()
                .addComponent(updtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(sprAddDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelDisciplinesLayout.setVerticalGroup(
            EditPanelDisciplinesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditDisciplines)
            .addComponent(sprAddDisciplines)
            .addComponent(sprDeleteDisciplines)
            .addComponent(updtDisc)
        );

        tableDisciplines.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableDisciplines.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableDisciplinesMouseReleased(evt);
            }
        });
        jScrollPane14.setViewportView(tableDisciplines);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelDisciplines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(EditPanelDisciplines, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Дисциплины", jPanel13);

        EditPanelUsZachisl.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddUsZachisl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddUsZachisl.setToolTipText("Добавить элемент");
        sprAddUsZachisl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddUsZachislActionPerformed(evt);
            }
        });

        sprEditUsZachisl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditUsZachisl.setToolTipText("Редактировать элемент");
        sprEditUsZachisl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditUsZachislActionPerformed(evt);
            }
        });

        sprDeleteUsZachisl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteUsZachisl.setToolTipText("Удалить элемент");
        sprDeleteUsZachisl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteUsZachislActionPerformed(evt);
            }
        });

        updtUslZach.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtUslZach.setToolTipText("Применить изменения");
        updtUslZach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtUslZachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelUsZachislLayout = new javax.swing.GroupLayout(EditPanelUsZachisl);
        EditPanelUsZachisl.setLayout(EditPanelUsZachislLayout);
        EditPanelUsZachislLayout.setHorizontalGroup(
            EditPanelUsZachislLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelUsZachislLayout.createSequentialGroup()
                .addComponent(updtUslZach, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(sprAddUsZachisl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditUsZachisl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteUsZachisl, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelUsZachislLayout.setVerticalGroup(
            EditPanelUsZachislLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditUsZachisl)
            .addComponent(sprAddUsZachisl)
            .addComponent(sprDeleteUsZachisl)
            .addComponent(updtUslZach)
        );

        tableUsZachisl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableUsZachisl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableUsZachislMouseReleased(evt);
            }
        });
        jScrollPane13.setViewportView(tableUsZachisl);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelUsZachisl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(EditPanelUsZachisl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Условия зачисления", jPanel14);

        EditPanelFormob.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddFormob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddFormob.setToolTipText("Добавить элемент");
        sprAddFormob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddFormobActionPerformed(evt);
            }
        });

        sprEditFormob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditFormob.setToolTipText("Редактировать элемент");
        sprEditFormob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditFormobActionPerformed(evt);
            }
        });

        sprDeleteFormob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteFormob.setToolTipText("Удалить элемент");
        sprDeleteFormob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteFormobActionPerformed(evt);
            }
        });

        updtFromob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtFromob.setToolTipText("Применить изменения");
        updtFromob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtFromobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelFormobLayout = new javax.swing.GroupLayout(EditPanelFormob);
        EditPanelFormob.setLayout(EditPanelFormobLayout);
        EditPanelFormobLayout.setHorizontalGroup(
            EditPanelFormobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelFormobLayout.createSequentialGroup()
                .addComponent(updtFromob, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(sprAddFormob, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditFormob, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteFormob, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelFormobLayout.setVerticalGroup(
            EditPanelFormobLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditFormob)
            .addComponent(sprAddFormob)
            .addComponent(sprDeleteFormob)
            .addComponent(updtFromob)
        );

        tableFormob.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableFormob.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableFormobMouseReleased(evt);
            }
        });
        jScrollPane12.setViewportView(tableFormob);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelFormob, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(EditPanelFormob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Формы обучения", jPanel15);

        EditPanelSostOb.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sprAddSostOb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adds.png"))); // NOI18N
        sprAddSostOb.setToolTipText("Добавить элемент");
        sprAddSostOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprAddSostObActionPerformed(evt);
            }
        });

        sprEditSostOb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        sprEditSostOb.setToolTipText("Редактировать элемент");
        sprEditSostOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprEditSostObActionPerformed(evt);
            }
        });

        sprDeleteSostOb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete_table.png"))); // NOI18N
        sprDeleteSostOb.setToolTipText("Удалить элемент");
        sprDeleteSostOb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sprDeleteSostObActionPerformed(evt);
            }
        });

        updtSostob.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh.png"))); // NOI18N
        updtSostob.setToolTipText("Применить изменения");
        updtSostob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updtSostobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EditPanelSostObLayout = new javax.swing.GroupLayout(EditPanelSostOb);
        EditPanelSostOb.setLayout(EditPanelSostObLayout);
        EditPanelSostObLayout.setHorizontalGroup(
            EditPanelSostObLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelSostObLayout.createSequentialGroup()
                .addComponent(updtSostob, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119)
                .addComponent(sprAddSostOb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(sprEditSostOb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(sprDeleteSostOb, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(158, Short.MAX_VALUE))
        );
        EditPanelSostObLayout.setVerticalGroup(
            EditPanelSostObLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sprEditSostOb)
            .addComponent(sprAddSostOb)
            .addComponent(sprDeleteSostOb)
            .addComponent(updtSostob)
        );

        tableSostOb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableSostOb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableSostObMouseReleased(evt);
            }
        });
        jScrollPane11.setViewportView(tableSostOb);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditPanelSostOb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(EditPanelSostOb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 229, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Состояние обучения", jPanel16);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Информация об элементах справки"));

        edPanIdLabel.setText("Код элемента справки");

        edPanNameLabel.setText("Наименование элемента справки");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edPanIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edPanNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edPanIdField)
                    .addComponent(edPanNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(edPanIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edPanIdLabel))
                .addGap(46, 46, 46)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(edPanNameLabel)
                    .addComponent(edPanNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(727, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 621, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jTabbedPane1))
        );

        TabPaneSprav.addTab("Справочники", new javax.swing.ImageIcon(getClass().getResource("/images/spr1.jpg")), jPanel2); // NOI18N

        javax.swing.GroupLayout tabSpravochnikLayout = new javax.swing.GroupLayout(tabSpravochnik);
        tabSpravochnik.setLayout(tabSpravochnikLayout);
        tabSpravochnikLayout.setHorizontalGroup(
            tabSpravochnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1286, Short.MAX_VALUE)
            .addGroup(tabSpravochnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TabPaneSprav))
        );
        tabSpravochnikLayout.setVerticalGroup(
            tabSpravochnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(tabSpravochnikLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(TabPaneSprav, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE))
        );

        tabPanel.addTab("Справочники  ", new javax.swing.ImageIcon(getClass().getResource("/images/spravochnik.png")), tabSpravochnik); // NOI18N

        tabSearch.setToolTipText("Поиск студентов и сотрудников");

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        searchButton.setText("Найти");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 502, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        searchStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Код", "Фамилия", "Имя", "Отчество", "Группа", "Специальность"
            }
        ));
        jScrollPane3.setViewportView(searchStudentTable);

        javax.swing.GroupLayout panelStudentSearchLayout = new javax.swing.GroupLayout(panelStudentSearch);
        panelStudentSearch.setLayout(panelStudentSearchLayout);
        panelStudentSearchLayout.setHorizontalGroup(
            panelStudentSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentSearchLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelStudentSearchLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panelStudentSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelStudentSearchLayout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap(502, Short.MAX_VALUE))
        );
        panelStudentSearchLayout.setVerticalGroup(
            panelStudentSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStudentSearchLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(panelStudentSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(searchTextField))
                .addGap(55, 55, 55)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabSearch.addTab("Поиск студента", panelStudentSearch);

        searchButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        searchButton1.setText("Найти");
        searchButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButton1ActionPerformed(evt);
            }
        });

        searchStateResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Код", "Фамилия", "Имя", "Отчество"
            }
        ));
        jScrollPane4.setViewportView(searchStateResult);

        javax.swing.GroupLayout panelStateSearchLayout = new javax.swing.GroupLayout(panelStateSearch);
        panelStateSearch.setLayout(panelStateSearchLayout);
        panelStateSearchLayout.setHorizontalGroup(
            panelStateSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStateSearchLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelStateSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelStateSearchLayout.createSequentialGroup()
                        .addComponent(searchStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(searchButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addContainerGap(500, Short.MAX_VALUE))
        );
        panelStateSearchLayout.setVerticalGroup(
            panelStateSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelStateSearchLayout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addGroup(panelStateSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchStateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jTabSearch.addTab("Поиск сотрудника", panelStateSearch);

        javax.swing.GroupLayout tabSearchLayout = new javax.swing.GroupLayout(tabSearch);
        tabSearch.setLayout(tabSearchLayout);
        tabSearchLayout.setHorizontalGroup(
            tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabSearch)
        );
        tabSearchLayout.setVerticalGroup(
            tabSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabSearch)
        );

        jTabSearch.getAccessibleContext().setAccessibleName("");

        tabPanel.addTab("Поиск  ", new javax.swing.ImageIcon(getClass().getResource("/images/search.png")), tabSearch, "Поиск студентов и сотрудников"); // NOI18N

        mainMenuBar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mainMenuBar.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        menuSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Settings-icon.png"))); // NOI18N
        menuSettings.setText("Настройки");
        menuSettings.setIconTextGap(14);

        jmenuSettings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmenuSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/options.png"))); // NOI18N
        jmenuSettings.setText("Настройки");
        jmenuSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuSettingsActionPerformed(evt);
            }
        });
        menuSettings.add(jmenuSettings);

        jmenuExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jmenuExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N
        jmenuExit.setText("Выход");
        jmenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmenuExitActionPerformed(evt);
            }
        });
        menuSettings.add(jmenuExit);

        mainMenuBar.add(menuSettings);

        menu3nk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/otchetnost.png"))); // NOI18N
        menu3nk.setText("Отчетность");
        menu3nk.setIconTextGap(14);
        mainMenuBar.add(menu3nk);

        menuService.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/service.png"))); // NOI18N
        menuService.setText("Сервис");
        menuService.setIconTextGap(14);
        mainMenuBar.add(menuService);

        print_Service.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printButton.png"))); // NOI18N
        print_Service.setText(" Печать");

        printSpravka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/docinfo-16.png"))); // NOI18N
        printSpravka.setText(" Справка");
        printSpravka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printSpravkaActionPerformed(evt);
            }
        });
        print_Service.add(printSpravka);
        print_Service.add(printSpravkaSep);

        printEkzamList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ekzList.png"))); // NOI18N
        printEkzamList.setText("Экзаменационный лист");
        printEkzamList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printEkzamListActionPerformed(evt);
            }
        });
        print_Service.add(printEkzamList);
        print_Service.add(printEkzamListSep);

        printSpisokGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/listG.png"))); // NOI18N
        printSpisokGroup.setText("Список группы");
        printSpisokGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printSpisokGroupActionPerformed(evt);
            }
        });
        print_Service.add(printSpisokGroup);
        print_Service.add(printSpisokGroupSep);

        printJournalDvij.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/journal.png"))); // NOI18N
        printJournalDvij.setText("Журнал учета движения студентов");
        printJournalDvij.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printJournalDvijActionPerformed(evt);
            }
        });
        print_Service.add(printJournalDvij);
        print_Service.add(printJournalDvijSep);

        printUchPlanStud.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plan.png"))); // NOI18N
        printUchPlanStud.setText("Учебный план студента");
        printUchPlanStud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printUchPlanStudActionPerformed(evt);
            }
        });
        print_Service.add(printUchPlanStud);
        print_Service.add(printUchPlanStudSep);

        printUchKartochka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kartochka.png"))); // NOI18N
        printUchKartochka.setText("Учебная карточка студента");
        printUchKartochka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printUchKartochkaActionPerformed(evt);
            }
        });
        print_Service.add(printUchKartochka);
        print_Service.add(printUchKartochkaSep);

        printGosAttest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gos.png"))); // NOI18N
        printGosAttest.setText("Государственная аттестация");
        printGosAttest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printGosAttestActionPerformed(evt);
            }
        });
        print_Service.add(printGosAttest);
        print_Service.add(printVedTekControlSep);

        printVedTekControl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/VedomTekControl.png"))); // NOI18N
        printVedTekControl.setText("Ведомость текущего контроля");
        printVedTekControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printVedTekControlActionPerformed(evt);
            }
        });
        print_Service.add(printVedTekControl);
        print_Service.add(journalBallovPrintSep);

        journalBallovPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ucplans.png"))); // NOI18N
        journalBallovPrint.setText(" Журнал баллов");
        journalBallovPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                journalBallovPrintActionPerformed(evt);
            }
        });
        print_Service.add(journalBallovPrint);

        mainMenuBar.add(print_Service);

        JmenuSpace.setText("                                    Пользователь:");
        JmenuSpace.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        JmenuSpace.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        JmenuSpace.setPreferredSize(new java.awt.Dimension(200, 19));
        mainMenuBar.add(JmenuSpace);

        jMenuUserLogin.setText("userName");
        jMenuUserLogin.setPreferredSize(new java.awt.Dimension(100, 19));
        mainMenuBar.add(jMenuUserLogin);

        jMenuUserFIO.setText("FIO");
        jMenuUserFIO.setPreferredSize(new java.awt.Dimension(300, 19));
        mainMenuBar.add(jMenuUserFIO);

        date_txt.setText("Date");
        date_txt.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mainMenuBar.add(date_txt);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPanel)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("Программа электронного документооборота");

        bindingGroup.bind();

        setSize(new java.awt.Dimension(1297, 794));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jmenuExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuExitActionPerformed
       System.exit(0);
    }//GEN-LAST:event_jmenuExitActionPerformed

    private void studentAddButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentAddButton1ActionPerformed

            String grNm = TabStudentTableName.getText();
            AddStudent addStudents = new AddStudent();
            addStudents.addGrText(grNm);
            addStudents.setVisible(true);
        
    }//GEN-LAST:event_studentAddButton1ActionPerformed

    private void jmenuSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmenuSettingsActionPerformed
        
        LangChooser lchoose = new LangChooser();
        lchoose.setVisible(true);
        
    }//GEN-LAST:event_jmenuSettingsActionPerformed

    private void Table_GroupsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_GroupsMouseReleased

            fieldIdStudent.setText("");
            fieldFamiliyaStudent.setText("");
            fieldNameStudent.setText("");
            fieldOtchestvoStudent.setText("");
            ((JTextField)calendarBirthStudent.getDateEditor().getUiComponent()).setText("");
            fieldPropiskaStudent.setText("");
            fieldProjivanieStudent.setText("");
            fieldPhoneStudent.setText("");
            fieldEmailStudent.setText("");
            fieldPasportSeriyaStudent.setText("");
            fieldPasportNumberStudent2.setText("");            
            fieldPasportNumberStudent1.setText("");            
            ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).setText("");
            fieldAttestatSeriyaStudent.setText("");
            fieldAttestatNumberStudent.setText("");
            fieldDiplomSeriyaStudent.setText("");
            fieldDiplomNumberStudent.setText("");
            ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).setText("");
            fieldMestoVydachiDoc.setText("");
            fieldNumOfPrikazZachisl.setText("");
            ((JTextField)choserDateOfPrikazZachisl.getDateEditor().getUiComponent()).setText("");
            fieldSumOfContract.setText("");
            fieldDiplBfaSeiyaStudent.setText("");
            fieldDiplBfaNumberStudent.setText("");
            ((JTextField)dateVidachDiplBfa.getDateEditor().getUiComponent()).setText("");
            ((JTextField)dateOfSostStudent.getDateEditor().getUiComponent()).setText("");
            fieldAdditionalStudent1.setText("");
            fieldAdditionalStudent2.setText("");
            textPaneStudentPhoto.setIcon(null);
        
        try{
             int row = Table_Groups.getSelectedRow();
             String Table_Click_Groups = (Table_Groups.getModel().getValueAt(row,1).toString());
                
                planeTextFieldChasy.setText("");
                planeTextFieldCredity1.setText("");
                
             String sql = "SELECT * FROM groups WHERE group_code='"+Table_Click_Groups+"'";
                
            pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                    if(rs.next()){
                        String add1 = rs.getString("group_code");
                            LabelNameOfGroup.setText(add1);
                            TabStudentTableName.setText(add1);
                            downButtonPanelText.setText(add1);
                        String add2 = rs.getString("department_code");
                            fieldDepIdGroup.setText(add2);
                            fieldNameGroup.setText(add1);
                        String add3 = rs.getString("group_name");
                            fieldNaprGroup.setText(add3);
                        String add4 = rs.getString("group_semestr");
                            fieldSemestrGroup.setText(add4);
                        String add5 = rs.getString("group_curator");
                            fieldMaterGroup.setText(add5);
                        String add6 = rs.getString("group_formaobuch");
                            fieldFromaobuchGroup.setText(add6);
                    
                    clickGroupsToStudents(add1);
                    clickGroupsToUchPlane(add1);
                    clearTableUchPlaneStudent();
                    }
                  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Exception of Action Performed Table_GroupsMouseReleased \n"+e);
            
        }
        
    }//GEN-LAST:event_Table_GroupsMouseReleased

    private void Table_PlaneOfGroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_PlaneOfGroupMouseClicked
        
                int row = Table_PlaneOfGroup.getSelectedRow();
                String Table_ClickGroupCode = (Table_PlaneOfGroup.getValueAt(row, 0).toString());
                String Table_ClickDiscCode = (Table_PlaneOfGroup.getValueAt(row, 2).toString());
                
                String sql = "SELECT *  FROM uchplangroup WHERE group_code='"+Table_ClickGroupCode
                                                        +"' AND discip_code = '"+Table_ClickDiscCode+"' ";
                
            try{
                pst = conn.prepareStatement(sql);
                    rs = pst.executeQuery();
                          if(rs.next()){          
                        String add1=rs.getString("uchPlg_semestr");
                        planeTextFieldSemestr.setText(add1);
                        String add2=rs.getString("discip_code");
                        planeTextFieldDisciplina1.setText(add2);
                        String add3=rs.getString("teacher_code");
                        nameOfTeacher.setText(add3);
                        String add5=rs.getString("uchPlg_clock");
                        planeTextFieldChasy.setText(add5);
                        String add6=rs.getString("uchPlg_credit");
                        planeTextFieldCredity1.setText(add6);
                        String add7=rs.getString("uchPlg_control");
                        planeTextFieldControl.setText(add7);
                              selectDisciplineNameUchPlGruppe(add2);
                          }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error Action Performed of Table_PlaneOfGroupMouseClicked "+e );
            }
       
    }//GEN-LAST:event_Table_PlaneOfGroupMouseClicked

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        
            
        try{
            String searchText = (searchTextField.getText().toString());
            String sql = "SELECT s1.student_id as 'Код',"+
                            " s1.student_lastname as 'Фамилия',"+
                            " s1.student_name as 'Имя',"+
                            " s1.student_middlename as 'Отчество',"+
                            " s1.group_code as 'Группа',"+
                            " g2.group_name as 'Специальность' "+
                            "FROM student s1 "+
                            "INNER JOIN groups g2 on s1.group_code=g2.group_code "+
                            "WHERE s1.student_lastname='"+searchText+"'";
        
            pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                  searchStudentTable.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{
//                rs.close();
//                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButton1ActionPerformed
               
        try{
            String searchTextState = (searchStateTextField.getText().toString());
            String sql = "SELECT teacher_code as 'Код', "+
                            "teacher_lastname as 'Фамилия', "+
                            "teacher_name as 'Имя', "+
                            "teacher_middlename as 'Отчество' "+
                            "FROM  teacher "+
                            "WHERE teacher_lastname='"+searchTextState+"'";
             pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                  searchStateResult.setModel(DbUtils.resultSetToTableModel(rs));
                
              }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{
//                rs.close();
//                pst.close();
            }catch(Exception e){
                
            }
        }
        
        
    }//GEN-LAST:event_searchButton1ActionPerformed

    private void Table_StateMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_StateMouseReleased
        
        int row = Table_State.getSelectedRow();
                String Table_ClickTeacher = (Table_State.getValueAt(row, 0).toString());
                
                String sql = "SELECT * FROM  teacher WHERE teacher_code='"+Table_ClickTeacher+"'";
                
                try{
                    pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        
                        if(rs.next()){
                    String add1 = rs.getString("teacher_code");
                        textFieldCodeState.setText(add1);
                    String add2 = rs.getString("depart_code");
                        textFieldCodeOfPodrazdel.setText(add2);
                    String add3 = rs.getString("teacher_lastname");
                        textFieldFamiliya.setText(add3);
                    String add4 = rs.getString("teacher_name");
                        textFieldName.setText(add4);
                    String add5 = rs.getString("teacher_middlename");
                        textFieldOtchestvo.setText(add5);
                    String add6 = rs.getString("dolj_code");
                        jTextField27.setText(add6);
                    String add7 = rs.getString("uzvanie_code");
                        textFieldUchenZvanie.setText(add7);
                    String add8 = rs.getString("ustep_code");
                        textFieldUchenStepen.setText(add8);
                    String add9 = rs.getString("status_code");
                        textFieldStatus.setText(add9);
                    String add10 = rs.getString("teacher_uchebnagr");
                        textFieldUchebnNagruzka1.setText(add10);
                    String add11 = rs.getString("nationality_code");
                        textFieldNationality.setText(add11);
                    String add12 = rs.getString("teacher_sex");
                        textFieldSexTeacher.setText(add12);
                    String add13 = rs.getString("teacher_birthdate");
                        ((JTextField)dateFieldDateofbirth.getDateEditor().getUiComponent()).setText(add13);
                    String add14 = rs.getString("teacher_adress");
                        textFieldAdressState.setText(add14);
                    String add15 = rs.getString("teacher_phone");
                        textFieldTelefon.setText(add15);
                    String add16 = rs.getString("teacher_email");
                        textFieldEmail.setText(add16);
                    String add17 = rs.getString("teacher_additional1");
                        textFieldAdditional.setText(add17);
                    String add18 = rs.getString("teacher_quit");
                        ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).setText(add18);
                                     
                    
                        }
                   
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }finally {
            try{
//                rs.close();
//                pst.close();
            }catch(Exception e){
                
            }
        }
        
        
    }//GEN-LAST:event_Table_StateMouseReleased

    private void Table_UchPlaneStudentMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_UchPlaneStudentMouseReleased

            int row = Table_UchPlaneStudent.getSelectedRow();
            String Table_UchPlaneStudentID = (Table_UchPlaneStudent.getValueAt(row, 0).toString());
            String Table_UchPlaneDiscCode = (Table_UchPlaneStudent.getValueAt(row, 3).toString());
            String semestr = (Table_UchPlaneStudent.getValueAt(row, 2).toString());
        
            String sql = "SELECT"
                        + " * "
                        + "FROM "
                        + "uchplanstudents "
                        + "WHERE "
                        + "student_id='"+Table_UchPlaneStudentID+"' "
                        + "AND "
                        + "disc_code='"+Table_UchPlaneDiscCode+"' "
                        + "AND "
                        + "uchPlans_sem='"+semestr+"'";
            
            try{
                    pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
            
            if(rs.next()){
                    String add1 = rs.getString("uchplans_sem");
                       studUchPlSemestrTextF.setText(add1);
                    String add2 = rs.getString("uchpls_controlb");
                        studUchPlControTextF.setText(add2);
                    String add3 = rs.getString("disc_code");
                        studUchPlDisciplinaField.setText(add3);
                    String add4 = rs.getString("uchpls_clock");
                        studUchPlChasyField.setText(add4);
                    String add5 = rs.getString("uchpls_credit");
                        studUchPlCredityField.setText(add5);
                    String add6 = rs.getString("uchpls_ball");
                        studUchPlBallyField.setText(add6);
                    String add7 = rs.getString("uchpls_ocenka");
                        studUchPlOcenkaField.setText(add7);
                    String add8 = rs.getString("uchpls_date");
                        ((JTextField)chooserStudUchPlDataSdachi.getDateEditor().getUiComponent()).setText(add8);
                    
                    String dateOpEdit = rs.getString("uchpls_dateOper");
                        ((JTextField)logDateLabel.getDateEditor().getUiComponent()).setText(dateOpEdit);
                    String loginOpEdit = rs.getString("user_login");
                        logAddLoginLabel.setText(loginOpEdit);
                    String loginOpUchAdd = rs.getString("uchpls_add");
                        logEditUserField.setText(loginOpUchAdd);
                    String dateOpUchAdd = rs.getString("uchplsadd_date");
                        ((JTextField)logLastEditDate.getDateEditor().getUiComponent()).setText(dateOpUchAdd);
                    String uchPlstudId = rs.getString("uchplstud_id");
                        uchplstudID.setText(uchPlstudId);
                    String begunokNumber = rs.getString("uchpls_nuberbegunok");
                        numberOfBegunok.setText(begunokNumber);
                    selectDisciplineNameUchPl(Table_UchPlaneDiscCode);
                                       
                    studUchPlApplyButton.setEnabled(false);
                    numberBegunokChek.setSelected(false);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }//GEN-LAST:event_Table_UchPlaneStudentMouseReleased

    private void attachStudentImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachStudentImageActionPerformed
        
        String stid = fieldIdStudent.getText();
        
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();
            // String filename = f.get();
        path.setText(filename);
      
        
        try{
            FileInputStream fis = new FileInputStream(f);
            int len = (int) f.length();
            
            pst = conn.prepareStatement("UPDATE student SET student_photo=? WHERE student_id = '"+stid+"'");
            pst.setBinaryStream(1, fis, len);
            int status = pst.executeUpdate();
            if (status>0){
                JOptionPane.showMessageDialog(null, "Фотография успешно загружено!");
                
            }else{
                JOptionPane.showMessageDialog(null, "Изображение не загружено!");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_attachStudentImageActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                          
                        textFieldCodeState.setText("");
                        textFieldFamiliya.setText("");
                        textFieldName.setText("");
                        textFieldOtchestvo.setText("");
                        jTextField27.setText("");
                        textFieldUchebnNagruzka1.setText("");
                        ((JTextField)dateFieldDateofbirth.getDateEditor().getUiComponent()).setText("");
                        textFieldAdressState.setText("");
                        textFieldTelefon.setText("");
                        textFieldEmail.setText("");
                        textFieldAdditional.setText("");
                        ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).setText("");
                                 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    
          int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите добавить \n нового сотрудника в БД?","Добавлено",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
        
        try{
         String sql = "INSERT INTO  teacher ("
                 + "teacher_code,"
                 + "depart_code,"
                 + "teacher_lastname,"
                 + "teacher_name,"
                 + "teacher_middlename,"
                 + "dolj_code,"
                 + "uzvanie_code,"
                 + "ustep_code,"
                 + "status_code,"
                 + "teacher_uchebnagr,"
                 + "nationality_code,"
                 + "teacher_sex,"
                 + "teacher_birthdate,"
                 + "teacher_adress,"
                 + "teacher_phone,"
                 + "teacher_email,"
                 + "teacher_additional1,"
                 + "teacher_quit) "
                 + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
         
         pst = conn.prepareStatement(sql);
                     pst.setString(1,textFieldCodeState.getText());
                     pst.setString(2,textFieldCodeOfPodrazdel.getText());
                     pst.setString(3,textFieldFamiliya.getText());
                     pst.setString(4,textFieldName.getText());
                     pst.setString(5,textFieldOtchestvo.getText());
                     pst.setString(6,jTextField27.getText());
                     pst.setString(7,textFieldUchenZvanie.getText());
                     pst.setString(8,textFieldUchenStepen.getText());
                     pst.setString(9,textFieldStatus.getText());
                     pst.setString(10,textFieldUchebnNagruzka1.getText());
                     pst.setString(11,textFieldNationality.getText());
                     pst.setString(12,textFieldSexTeacher.getText());
                     pst.setString(13,((JTextField)dateFieldDateofbirth.getDateEditor().getUiComponent()).getText());
                     pst.setString(14,textFieldAdressState.getText());
                     pst.setString(15,textFieldTelefon.getText());
                     pst.setString(16,textFieldEmail.getText());
                     pst.setString(17,textFieldAdditional.getText());
                     pst.setString(18,((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText());
            
         
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Сотрудник успешно добавлен в БД");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
            Update_table_Teachers();
            }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void studentEditButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentEditButton1ActionPerformed
     
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n изменить личные\n данные студента?","Выполнение операции",JOptionPane.YES_NO_OPTION);
            
                if(p==0){  
                    try{

                        String value1 = fieldIdStudent.getText();
                        String value2 = fieldFamiliyaStudent.getText();
                        String value3 = fieldNameStudent.getText();
                        String value4 = fieldOtchestvoStudent.getText();
                        String value5 = ((JTextField)calendarBirthStudent.getDateEditor().getUiComponent()).getText();
                        String value6 = fieldNationalityStudent.getText();
                        String value7 = fieldCountryStudent.getText();              
                        String value8 = fieldRegionStudent1.getText();            
                        String value9 = fieldSexStudent.getText();
                        String value10 = fieldPropiskaStudent.getText();
                        String value11 = fieldProjivanieStudent.getText();
                        String value12 = fieldPhoneStudent.getText();
                        String value13 = fieldEmailStudent.getText();
                        String value14 = fieldPasportSeriyaStudent.getText();
                        String value15 = fieldPasportNumberStudent2.getText();         
                        String value16 = fieldPasportNumberStudent1.getText();      
                        String value17 = ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).getText();
                        String value18 = fieldAttestatSeriyaStudent.getText();
                        String value19 = fieldAttestatNumberStudent.getText();
                        String value20 = fieldDiplomSeriyaStudent.getText();
                        String value21 = fieldDiplomNumberStudent.getText();
                        String value22 = ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).getText();
                        String value23 = fieldMestoVydachiDoc.getText();
                        String value24 = fieldNumOfPrikazZachisl.getText();
                        String value25 = ((JTextField)choserDateOfPrikazZachisl.getDateEditor().getUiComponent()).getText();
                        String value26 = fieldUsloviyaZachisleniya.getText();
                        String value27 = fieldSemestrStudent.getText();
                        String value28 = fieldSumOfContract.getText();
                        String value29 = fieldDiplBfaSeiyaStudent.getText();
                        String value30 = fieldDiplBfaNumberStudent.getText();
                        String value31 = fieldSostObuchStudent.getText();
                        String value32 = fieldAdditionalStudent1.getText();
                        String value33 = fieldAdditionalStudent2.getText(); 

                        String sql = "UPDATE student SET "
                                + "student_id='"+value1+"', "
                                + "student_lastname='"+value2+"', "
                                + "student_name='"+value3+"',"
                                + "student_middlename='"+value4+"',"
                                + "student_birthday='"+value5+"',"
                                + "nationality_code='"+value6+"',"
                                + "cou_cod='"+value7+"',"
                                + "region_code='"+value8+"',"
                                + "student_sex='"+value9+"',"
                                + "student_propiska='"+value10+"',"
                                + "student_addr='"+value11+"',"
                                + "student_phone='"+value12+"',"
                                + "student_email='"+value13+"',"
                                + "student_passeries='"+value14+"',"
                                + "student_passnumber='"+value15+"',"
                                + "student_passvid='"+value16+"',"
                                + "student_passissue='"+value17+"',"
                                + "student_attseries='"+value18+"',"
                                + "student_attnumber='"+value19+"',"
                                + "student_dipseries='"+value20+"',"
                                + "student_dipnumber='"+value21+"',"
                                + "student_docdate='"+value22+"',"
                                + "student_docfrom='"+value23+"',"
                                + "student_prikaz='"+value24+"',"
                                + "student_prikazdate='"+value25+"',"
                                + "zachislenie_code='"+value26+"',"
                                + "student_semestr='"+value27+"',"
                                + "student_sumk='"+value28+"',"
                                + "student_dsera='"+value29+"',"
                                + "student_duma='"+value30+"',"
                                + "sostobuchen_code='"+value31+"',"
                                + "student_additional1='"+value32+"',"
                                + "student_additional2='"+value33+"'"
                                + " WHERE "
                                + "student_id='"+value1+"'";
                        pst = conn.prepareStatement(sql);
                                pst.execute();

                               JOptionPane.showMessageDialog(null, "Updated");

                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
    }//GEN-LAST:event_studentEditButton1ActionPerformed

    private void studentDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentDeleteButtonActionPerformed
   
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){            
            String sql = "DELETE FROM student WHERE student_id=?";
                 try{

                    pst = conn.prepareStatement(sql);
                    pst.setString(1, fieldIdStudent.getText());
                    pst.execute();


                        JOptionPane.showMessageDialog(null, "Удалено");

                    }catch(SQLException | HeadlessException e){
                        JOptionPane.showMessageDialog(null, e);
                    }
            }
             Update_table_Students();
        
    }//GEN-LAST:event_studentDeleteButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
  int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите \n изменить ранее внесенные данные?","Изменено",JOptionPane.YES_NO_OPTION);
            
            if(p==0){     
        try{
        
                     String val1 =   textFieldCodeState.getText();
                     String val2 =   textFieldCodeOfPodrazdel.getText();
                     String val3 =   textFieldFamiliya.getText();
                     String val4 =   textFieldName.getText();
                     String val5 =   textFieldOtchestvo.getText();
                     String val6 =   jTextField27.getText();
                     String val7 =   textFieldUchenZvanie.getText();
                     String val8 =   textFieldUchenStepen.getText();
                     String val9 =   textFieldStatus.getText();
                     String val10 =  textFieldUchebnNagruzka1.getText();
                     String val11 =  textFieldNationality.getText();
                     String val12 =  textFieldSexTeacher.getText();
                     String val13 =  ((JTextField)dateFieldDateofbirth.getDateEditor().getUiComponent()).getText();
                     String val14 =   textFieldAdressState.getText();
                     String val15 =   textFieldTelefon.getText();
                     String val16 =   textFieldEmail.getText();
                     String val17 =   textFieldAdditional.getText();
                     String val18 =   ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).getText();
                     
                     String sql = "UPDATE  teacher SET "
                             + "teacher_code='"+val1+"',"
                             + "depart_code='"+val2+"',"
                             + "teacher_lastname='"+val3+"',"
                             + "teacher_name='"+val4+"',"
                             + "teacher_middlename='"+val5+"',"
                             + "dolj_code='"+val6+"',"
                             + "uzvanie_code='"+val7+"',"
                             + "ustep_code='"+val8+"',"
                             + "status_code='"+val9+"',"
                             + "teacher_uchebnagr='"+val10+"',"
                             + "nationality_code='"+val11+"',"
                             + "teacher_sex='"+val12+"',"
                             + "teacher_birthdate='"+val13+"',"
                             + "teacher_adress='"+val14+"',"
                             + "teacher_phone='"+val15+"',"
                             + "teacher_email='"+val16+"',"
                             + "teacher_additional1='"+val17+"'"
                             + " WHERE teacher_code='"+val1+"' ";
                     pst = conn.prepareStatement(sql);
                    pst.execute();
                    
                   JOptionPane.showMessageDialog(null, "Updated");
            
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table_Teachers();
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

            int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
        
         String sql = "DELETE FROM  teacher WHERE teacher_code=?";
             try{
                 
                pst = conn.prepareStatement(sql);
                pst.setString(1, textFieldCodeState.getText());
                pst.execute();
                    
                    
                    JOptionPane.showMessageDialog(null, "Deleted");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{

                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
         Update_table_Teachers();
            }
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        int row = Table_State.getSelectedRow();
                String Table_ClickTeacher = (Table_State.getValueAt(row, 0).toString());
                
                String sql = "SELECT * FROM  teacher WHERE teacher_code='"+Table_ClickTeacher+"'";
                
                try{
                    pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
                        
                        if(rs.next()){
                    String add1 = rs.getString("teacher_code");
                        textFieldCodeState.setText(add1);
                    String add2 = rs.getString("depart_code");
                        textFieldCodeOfPodrazdel.setText(add2);
                    String add3 = rs.getString("teacher_lastname");
                        textFieldFamiliya.setText(add3);
                    String add4 = rs.getString("teacher_name");
                        textFieldName.setText(add4);
                    String add5 = rs.getString("teacher_middlename");
                        textFieldOtchestvo.setText(add5);
                    String add6 = rs.getString("dolj_code");
                        jTextField27.setText(add6);
                    String add7 = rs.getString("uzvanie_code");
                        textFieldUchenZvanie.setText(add7);
                    String add8 = rs.getString("ustep_code");
                        textFieldUchenStepen.setText(add8);
                    String add9 = rs.getString("status_code");
                        textFieldStatus.setText(add9);
                    String add10 = rs.getString("teacher_uchebnagr");
                        textFieldUchebnNagruzka1.setText(add10);
                    String add11 = rs.getString("nationality_code");
                        textFieldNationality.setText(add11);
                    String add12 = rs.getString("teacher_sex");
                        textFieldSexTeacher.setText(add12);
                    String add13 = rs.getString("teacher_birthdate");
                        ((JTextField)dateFieldDateofbirth.getDateEditor().getUiComponent()).setText(add13);
                    String add14 = rs.getString("teacher_adress");
                        textFieldAdressState.setText(add14);
                    String add15 = rs.getString("teacher_phone");
                        textFieldTelefon.setText(add15);
                    String add16 = rs.getString("teacher_email");
                        textFieldEmail.setText(add16);
                    String add17 = rs.getString("teacher_additional1");
                        textFieldAdditional.setText(add17);
                    String add18 = rs.getString("teacher_quit");
                        ((JTextField)jDateChooser1.getDateEditor().getUiComponent()).setText(add18);
                                     
                    
                        }
                   
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }finally {
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void addButtonPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonPlaneActionPerformed
                planeTextFieldSemestr.setText(null);
                planeTextFieldDisciplina1.setText(null);
                nameOfTeacher.setText(null);
                planeTextFieldControl.setText(null);
                planeTextFieldChasy.setText(null);
                planeTextFieldCredity1.setText(null);
        
    }//GEN-LAST:event_addButtonPlaneActionPerformed

    private void deleteButtonPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonPlaneActionPerformed
       
           int row = Table_PlaneOfGroup.getSelectedRow();
           String groupCodeUchpl = (Table_PlaneOfGroup.getValueAt(row, 0).toString());
           String discCodeUchpl = (Table_PlaneOfGroup.getValueAt(row, 2).toString());
           
                 String sql = "DELETE FROM uchplangroup WHERE group_code='"+groupCodeUchpl
                                    +"' AND discip_code = '"+discCodeUchpl+"' ";
                 
                 int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
        
             try{
                 
                pst = conn.prepareStatement(sql);
                pst.execute();
                    
                    
                    JOptionPane.showMessageDialog(null, "Deleted");
                    Update_Table_UchPlGr();
           
       }catch(Exception e){
           JOptionPane.showMessageDialog(null,e);
       }
        }
             
              Update_Table_UchPlGr();
             
    }//GEN-LAST:event_deleteButtonPlaneActionPerformed

    private void editButtonPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonPlaneActionPerformed
        
            int row = Table_PlaneOfGroup.getSelectedRow();
            String groupCodeUchpl = (Table_PlaneOfGroup.getValueAt(row, 0).toString());
            String discCodeUchpl = (Table_PlaneOfGroup.getValueAt(row, 2).toString());
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n изменить?","Updated",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
            try{
                        String val1= planeTextFieldSemestr.getText();
                        String val2= planeTextFieldDisciplina1.getText();
                        String val3= nameOfTeacher.getText();
                        String val4= planeTextFieldChasy.getText();
                        String val5= planeTextFieldCredity1.getText();
                        String val6= planeTextFieldControl.getText();
                        
                    String sql = "UPDATE uchplangroup SET uchPlg_semestr='"+val1
                                    +"', discip_code='"+val2
                                    +"', teacher_code='"+val3
                                    +"', uchPlg_clock='"+val4
                                    +"', uchPlg_credit='"+val5
                                    +"', uchPlg_control='"+val6
                                +"' WHERE group_code='"+groupCodeUchpl
                                +"' AND discip_code = '"+discCodeUchpl+"' ";
                     pst = conn.prepareStatement(sql);
                    pst.execute();
                
                   JOptionPane.showMessageDialog(null, "Updated");
                        
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }
            }
        
        Update_Table_UchPlGr();
    }//GEN-LAST:event_editButtonPlaneActionPerformed

    private void applyButtonPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyButtonPlaneActionPerformed

            int row = Table_PlaneOfGroup.getSelectedRow();
//            String uchplg = (Table_PlaneOfGroup.getValueAt(row, 0).toString());
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n добавить новый элемент?","Добавлено успешно!",JOptionPane.YES_NO_OPTION);
            
       if(p==0){
            try {

                String sql = "INSERT INTO uchplangroup"
                                + " (group_code,"
                                + "uchPlg_semestr,"
                                + "discip_code,"
                                + "teacher_code,"
                                + "uchPlg_clock,"
                                + "uchPlg_credit,"
                                + "uchPlg_control)"+
                                " VALUES (?,?,?,?,?,?,?)";
                     pst = conn.prepareStatement(sql);
                            pst.setString(1, downButtonPanelText.getText());
                            pst.setString(2, planeTextFieldSemestr.getText());
                            pst.setString(3, planeTextFieldDisciplina1.getText());
                            pst.setString(4, nameOfTeacher.getText());
                            pst.setString(5, planeTextFieldChasy.getText());
                            pst.setString(6, planeTextFieldCredity1.getText());
                            pst.setString(7, planeTextFieldControl.getText());

                     pst.execute();

                JOptionPane.showMessageDialog(null, "Добавлено успешно!");

            }catch(Exception e){

                JOptionPane.showMessageDialog(null, e);
            }
        }
             Update_Table_UchPlGr();
    }//GEN-LAST:event_applyButtonPlaneActionPerformed

    private void studUchPlDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studUchPlDeleteButtonActionPerformed
        
        int row = Table_UchPlaneStudent.getSelectedRow();
        String studId = (Table_UchPlaneStudent.getValueAt(row, 0).toString());
        String semUchpl = (Table_UchPlaneStudent.getValueAt(row, 2).toString());
        String discCode = (Table_UchPlaneStudent.getValueAt(row, 3).toString());
            
        
          int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
                    String sql = "DELETE FROM "
                            + "uchplanstudents "
                            + "WHERE "
                            + "student_id='"+studId+"' "
                            + "AND "
                            + "disc_code='"+discCode+"'"
                            + "AND "
                            + "uchplans_sem='"+semUchpl+"'";
             try{
                 
                pst = conn.prepareStatement(sql);
                pst.execute();
                    
                    
                    JOptionPane.showMessageDialog(null, "Удаление успешно\nзавершено!");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        
       }
     String Table_Click = fieldIdStudent.getText();
     StudClickTableToUch(Table_Click);
    }//GEN-LAST:event_studUchPlDeleteButtonActionPerformed

    private void deleteGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupButtonActionPerformed
          int row = Table_Groups.getSelectedRow();
            String idUchpls = (Table_Groups.getValueAt(row, 1).toString());
        
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
        
         String sql = "DELETE FROM groups WHERE group_code='"+idUchpls+"'";
             try{
                 
                pst = conn.prepareStatement(sql);
                pst.execute();
                    
                    
                    JOptionPane.showMessageDialog(null, "Deleted");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{

                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
         Update_table_Groups();
     }
    }//GEN-LAST:event_deleteGroupButtonActionPerformed

    private void programmDeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programmDeleteButtonActionPerformed
         
             int row = Table_Departments.getSelectedRow();
            String idUchpls = (Table_Departments.getValueAt(row, 0).toString());
        
        int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите удалить?","Удаление",JOptionPane.YES_NO_OPTION);
            
            if(p==0){
        
         String sql = "DELETE FROM departments WHERE depart_code='"+idUchpls+"'";
             try{
                 
                pst = conn.prepareStatement(sql);
                pst.execute();
                    
                    
                    JOptionPane.showMessageDialog(null, "Deleted");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{

                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
         Update_table_Departments();
            }
    }//GEN-LAST:event_programmDeleteButtonActionPerformed

    private void studUchPlAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studUchPlAddButtonActionPerformed
        studUchPlDisciplinaField.setText(null);
        studUchPlControTextF.setText(null);
        studUchPlSemestrTextF.setText(null);
        studUchPlChasyField.setText(null);
        studUchPlCredityField.setText(null);
        studUchPlBallyField.setText(null);
        studUchPlOcenkaField.setText(null);
        studUchPlApplyButton.setEnabled(true);

    }//GEN-LAST:event_studUchPlAddButtonActionPerformed

    private void studUchPlEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studUchPlEditButtonActionPerformed
            int row = Table_UchPlaneStudent.getSelectedRow();
            String studId = (Table_UchPlaneStudent.getValueAt(row, 0).toString());
            String disc_code = (Table_UchPlaneStudent.getValueAt(row, 3).toString());
            String uchPlanChasy = studUchPlChasyField.getText();
            String uchPlanCredity = studUchPlCredityField.getText();
            Integer uchPlanBally = Integer.parseInt(studUchPlBallyField.getText());
            String dataSdachiPredmeta = ((JTextField)chooserStudUchPlDataSdachi.getDateEditor().getUiComponent()).getText();
            String semestr = studUchPlSemestrTextF.getText();
            String numberOfBegunokString = numberOfBegunok.getText();
            
            if(numberBegunokChek.isSelected()){
                if(numberOfBegunok.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Введите номер бегунка!");
                }else if(!numberOfBegunok.getText().isEmpty()){
                    editUchPlanOfStudentWithBegunok(studId, 
                            disc_code, 
                            uchPlanChasy, 
                            uchPlanCredity, 
                            uchPlanBally, 
                            dataSdachiPredmeta, 
                            numberOfBegunokString, 
                            semestr);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Введите номер бегунка!");
//                editUchPlanOfStudent(studId, 
//                        disc_code, 
//                        uchPlanChasy, 
//                        uchPlanCredity, 
//                        uchPlanBally, 
//                        dataSdachiPredmeta,
//                        semestr);
            }
            
        
        
    }//GEN-LAST:event_studUchPlEditButtonActionPerformed

    private void studUchPlCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studUchPlCancelButtonActionPerformed
       
            int row = Table_UchPlaneStudent.getSelectedRow();
            String Table_UchPlaneStudentID = (Table_UchPlaneStudent.getValueAt(row, 0).toString());
            String Table_UchPlaneDiscCode = (Table_UchPlaneStudent.getValueAt(row, 3).toString());
        
            String sql = "SELECT * FROM uchplanstudents WHERE student_id='"+Table_UchPlaneStudentID+"' AND disc_code='"+Table_UchPlaneDiscCode+"' ";
            
            try{
                    pst = conn.prepareStatement(sql);
                        rs = pst.executeQuery();
            
            if(rs.next()){
                    String add1 = rs.getString("uchPlans_sem");
                       studUchPlSemestrTextF.setText(add1);
                    String add2 = rs.getString("uchPls_controlb");
                        studUchPlControTextF.setText(add2);
                    String add3 = rs.getString("disc_code");
                        studUchPlDisciplinaField.setText(add3);
                    String add4 = rs.getString("uchPls_clock");
                        studUchPlChasyField.setText(add4);
                    String add5 = rs.getString("uchPls_credit");
                        studUchPlCredityField.setText(add5);
                    String add6 = rs.getString("uchPls_ball");
                        studUchPlBallyField.setText(add6);
                    String add7 = rs.getString("uchPls_ocenka");
                        studUchPlOcenkaField.setText(add7);
                    String add8 = rs.getString("uchPls_date");
                        ((JTextField)chooserStudUchPlDataSdachi.getDateEditor().getUiComponent()).setText(add8); 
                
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        
    }//GEN-LAST:event_studUchPlCancelButtonActionPerformed

    private void studUchPlApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studUchPlApplyButtonActionPerformed

            String gr_code = TabStudentTableName.getText();
            String uchpl_sem = studUchPlSemestrTextF.getText();
            String disc_code = studUchPlDisciplinaField.getText();
            String uchpl_clock = studUchPlChasyField.getText();
            String uchpls_credit = studUchPlCredityField.getText();
            String uchpls_date = ((JTextField)chooserStudUchPlDataSdachi.getDateEditor().getUiComponent()).getText();
            String student_id = fieldIdStudent.getText();
            String uchpls_add = jMenuUserLogin.getText();
            String uchplsadd_date = date_txt.getText();
            String uchpl_control = studUchPlControTextF.getText();
                    
            addNewUchPlanAll(gr_code, uchpl_sem, disc_code, 
                    uchpl_clock, uchpls_credit, uchpls_date,
                    student_id, uchpls_add, uchplsadd_date,uchpl_control);
            
            studUchPlApplyButton.setEnabled(false);
            
    }//GEN-LAST:event_studUchPlApplyButtonActionPerformed

    private void programmAddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programmAddButtonActionPerformed
                AddDep addDepNew = new AddDep();
                addDepNew.setVisible(true);
                Update_table_Departments();
    }//GEN-LAST:event_programmAddButtonActionPerformed

    private void programmApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programmApplyButtonActionPerformed
        Update_table_Departments();
    }//GEN-LAST:event_programmApplyButtonActionPerformed

    private void programmEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_programmEditButtonActionPerformed
  
            try{   
                
                
                     String val1 =   fieldCodeIdDep.getText();
                     String val2 =   fieldNameDepE.getText();
                     String val3 =   fieldHeaderDepE.getText();
                     String val4 =   fieldLaborantDepE.getText();
                     String val5 =   fieldPhoneDepE.getText();
                     String val6 =   fieldMaileDepE.getText();
                    
                     
             String sql = "UPDATE departments SET depart_code='"+val1
                             +"', depart_name='"+val2
                             +"', depart_header='"+val3
                             +"', depart_assistant='"+val4
                             +"', depart_phone='"+val5
                             +"', depart_email='"+val6
                             +"' WHERE depart_code='"+val1+"'";

               pst = conn.prepareStatement(sql);
                   pst.execute();
                    

            JOptionPane.showMessageDialog(null, "Updated");

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        Update_table_Departments();

    }//GEN-LAST:event_programmEditButtonActionPerformed

    private void addGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGroupButtonActionPerformed
        AddGroup addGroupNew = new AddGroup();
        addGroupNew.setVisible(true);
    }//GEN-LAST:event_addGroupButtonActionPerformed

    private void editGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGroupButtonActionPerformed
       int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n изменить?","Updated",JOptionPane.YES_NO_OPTION);
            
        if(p==0){

            try{
                 String val1 =   fieldDepIdGroup.getText();
                 String val2 =   fieldNameGroup.getText();
                 String val3 =   fieldNaprGroup.getText();
                 String val4 =   fieldSemestrGroup.getText();
                 String val5 =   fieldMaterGroup.getText();
                 String val6 =   fieldFromaobuchGroup.getText();

                 String sql = "UPDATE groups SET department_code='"+val1
                                 +"', group_code='"+val2
                                 +"', group_name='"+val3
                                 +"', group_semestr='"+val4
                                 +"', group_curator='"+val5
                                 +"', group_formaobuch='"+val6
                                 +"' WHERE group_code='"+val2+"'";

                         pst = conn.prepareStatement(sql);
                        pst.execute();

                       JOptionPane.showMessageDialog(null, "Updated");

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
        Update_table_Groups();
        
    }//GEN-LAST:event_editGroupButtonActionPerformed

    private void cancelGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelGroupButtonActionPerformed
       
        
         try{
                String grupCodeName =  LabelNameOfGroup.getText().toString();
                
                String sql = "SELECT * FROM groups WHERE group_code='"+grupCodeName+"'";
                
            pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                    
                    String add11 = rs.getString("department_code");
                    fieldDepIdGroup.setText(add11);
                    fieldNameGroup.setText(grupCodeName);
                    String add12 = rs.getString("group_name");
                    fieldNaprGroup.setText(add12);
                    String add13 = rs.getString("group_semestr");
                    fieldSemestrGroup.setText(add13);
                    String add14 = rs.getString("group_curator");
                    fieldMaterGroup.setText(add14);
                    String add15 = rs.getString("group_formaobuch");
                    fieldFromaobuchGroup.setText(add15);
                
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }//GEN-LAST:event_cancelGroupButtonActionPerformed

    private void updateTableGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateTableGroupsActionPerformed
        
        Update_table_Groups();
        
    }//GEN-LAST:event_updateTableGroupsActionPerformed

    private void updtSostobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtSostobActionPerformed
        Update_spr_Sostobuchn();
    }//GEN-LAST:event_updtSostobActionPerformed

    private void updtFromobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtFromobActionPerformed
        Update_spr_Formobuchn();
    }//GEN-LAST:event_updtFromobActionPerformed

    private void updtUslZachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtUslZachActionPerformed
        Update_spr_Uslzachisl();
    }//GEN-LAST:event_updtUslZachActionPerformed

    private void updtDiscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtDiscActionPerformed
        Update_spr_Disciplina();
    }//GEN-LAST:event_updtDiscActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        edPanIdField.setText("");
        edPanNameField.setText("");
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void updtStPPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtStPPSActionPerformed
        Update_spr_SttPS();
    }//GEN-LAST:event_updtStPPSActionPerformed

    private void updtUstepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtUstepActionPerformed
        Update_spr_Uchst();
    }//GEN-LAST:event_updtUstepActionPerformed

    private void updtUzvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtUzvanActionPerformed
        Update_spr_Uchzv();
    }//GEN-LAST:event_updtUzvanActionPerformed

    private void updtDoljActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtDoljActionPerformed
        Update_spr_Doljnost();
    }//GEN-LAST:event_updtDoljActionPerformed

    private void tableRegionMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableRegionMouseReleased
        int row = tableRegion.getSelectedRow();
        String idRegion = (tableRegion.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_region WHERE region_code='" + idRegion + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("region_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("region_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableRegionMouseReleased

    private void updtRegnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtRegnActionPerformed
        Update_spr_Region();
    }//GEN-LAST:event_updtRegnActionPerformed

    private void sprDeleteRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteRegionActionPerformed
        int row = tableRegion.getSelectedRow();
        String idRegion = (tableRegion.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_region WHERE region_code='" + idRegion + "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Region();
    }//GEN-LAST:event_sprDeleteRegionActionPerformed

    private void sprEditRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditRegionActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_region SET region_code='" + val1
                    + "', region_name='" + val2 + "' WHERE region_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Region();
    }//GEN-LAST:event_sprEditRegionActionPerformed

    private void tableNationalityMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableNationalityMouseReleased
        int row = tableNationality.getSelectedRow();
        String idNation = (tableNationality.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_nationality WHERE nationality_code='" + idNation + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("nationality_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("nationality_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableNationalityMouseReleased

    private void updtNatyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtNatyActionPerformed
        Update_spr_Nationality();
    }//GEN-LAST:event_updtNatyActionPerformed

    private void sprDeleteNationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteNationActionPerformed
        int row = tableNationality.getSelectedRow();
        String idNation = (tableNationality.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_nationality WHERE nationality_code='" + idNation + "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Nationality();
    }//GEN-LAST:event_sprDeleteNationActionPerformed

    private void sprEditNationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditNationActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_nationality SET nationality_code='" + val1
                    + "', nationality_name='" + val2 + "' WHERE nationality_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Nationality();
    }//GEN-LAST:event_sprEditNationActionPerformed

    private void tableCountryMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCountryMouseReleased
        int row = tableCountry.getSelectedRow();
        String idCoun = (tableCountry.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_country WHERE cou_cod='" + idCoun + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("cou_cod");
                edPanIdField.setText(add1);
                String add2 = rs.getString("cou_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableCountryMouseReleased

    private void updtCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updtCountryActionPerformed
        Update_spr_Country();
    }//GEN-LAST:event_updtCountryActionPerformed

    private void sprDeleteCounActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteCounActionPerformed
        int row = tableCountry.getSelectedRow();
        String idCounttr = (tableCountry.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_country WHERE cou_cod='" + idCounttr + "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Country();
    }//GEN-LAST:event_sprDeleteCounActionPerformed

    private void sprEditCounActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditCounActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_country SET cou_cod='" + val1
                    + "', cou_name='" + val2 + "' WHERE cou_cod='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Country();
    }//GEN-LAST:event_sprEditCounActionPerformed

    private void sprAddCounActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddCounActionPerformed
        AddCountry addContryNew = new AddCountry();
        addContryNew.setVisible(true);
        Update_spr_Country();
    }//GEN-LAST:event_sprAddCounActionPerformed

    private void workWithVedomButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workWithVedomButtonActionPerformed
            String grName = fieldNameGroup.getText();
            String grSem = fieldSemestrGroup.getText();
            workWithVedom wwV = new workWithVedom();
            wwV.FillFields(grName, grSem);
            wwV.setVisible(true);
    }//GEN-LAST:event_workWithVedomButtonActionPerformed

    private void tableDoljnostMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDoljnostMouseReleased
        int row = tableDoljnost.getSelectedRow();
        String idDoljnost = (tableDoljnost.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_doljnost WHERE dolj_code='" + idDoljnost + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("dolj_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("dolj_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableDoljnostMouseReleased

    private void sprDeleteDoljnostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteDoljnostActionPerformed
         int row = tableDoljnost.getSelectedRow();
        String idDolj = (tableDoljnost.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_doljnost WHERE dolj_code='" +idDolj+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Doljnost();
    }//GEN-LAST:event_sprDeleteDoljnostActionPerformed

    private void sprEditDoljnostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditDoljnostActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_doljnost SET dolj_code='" + val1
                    + "', dolj_name='" + val2 + "' WHERE dolj_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Doljnost();
    }//GEN-LAST:event_sprEditDoljnostActionPerformed

    private void sprDeleteUchenZvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteUchenZvanActionPerformed
                int row = tableUchenZvan.getSelectedRow();
        String idUzvan = (tableUchenZvan.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_uzvanie WHERE uzvanie_code='" +idUzvan+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Uchzv();
    }//GEN-LAST:event_sprDeleteUchenZvanActionPerformed

    private void sprDeleteUchenStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteUchenStepActionPerformed
               int row = tableUchenStep.getSelectedRow();
        String idUchstep = (tableUchenStep.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_uchstepen WHERE ustep_code='" +idUchstep+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Uchst();
    }//GEN-LAST:event_sprDeleteUchenStepActionPerformed

    private void sprDeleteStatusPPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteStatusPPSActionPerformed
         int row = tableStatusPPS.getSelectedRow();
        String idStatus = (tableStatusPPS.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_statusteach WHERE status_code='" +idStatus+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_SttPS();
    }//GEN-LAST:event_sprDeleteStatusPPSActionPerformed

    private void sprDeleteDisciplinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteDisciplinesActionPerformed
         int row = tableDisciplines.getSelectedRow();
        String idDisc = (tableDisciplines.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_discipline WHERE disc_code='" +idDisc+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Disciplina();
    }//GEN-LAST:event_sprDeleteDisciplinesActionPerformed

    private void sprDeleteUsZachislActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteUsZachislActionPerformed
         int row = tableUsZachisl.getSelectedRow();
        String idZachisl = (tableUsZachisl.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_zachislenie WHERE zachislenie_code='" +idZachisl+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Uslzachisl();
    }//GEN-LAST:event_sprDeleteUsZachislActionPerformed

    private void sprDeleteFormobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteFormobActionPerformed
         int row = tableFormob.getSelectedRow();
        String idFormob = (tableFormob.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_formobuch WHERE formobuch_code='" +idFormob+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Formobuchn();
    }//GEN-LAST:event_sprDeleteFormobActionPerformed

    private void sprDeleteSostObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprDeleteSostObActionPerformed
         int row = tableSostOb.getSelectedRow();
        String idSostobuch = (tableSostOb.getValueAt(row, 0).toString());

        int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить?", "Удаление", JOptionPane.YES_NO_OPTION);

        if (p == 0) {

            String sql = "DELETE FROM spr_sostobuchen WHERE sostobuchen_code='" +idSostobuch+ "'";
            try {

                pst = conn.prepareStatement(sql);
                pst.execute();


                JOptionPane.showMessageDialog(null, "Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {

                    rs.close();
                    pst.close();
                } catch (Exception e) {
                }
            }
        }
        Update_spr_Sostobuchn();
    }//GEN-LAST:event_sprDeleteSostObActionPerformed

    private void sprEditUchenZvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditUchenZvanActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_uzvanie SET uzvanie_code='" + val1
                    + "', uzvanie_name='" + val2 + "' WHERE uzvanie_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Uchzv();
    }//GEN-LAST:event_sprEditUchenZvanActionPerformed

    private void sprEditUchenStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditUchenStepActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_uchstepen SET ustep_code='" + val1
                    + "', ustep_name='" + val2 + "' WHERE ustep_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Uchst();
    }//GEN-LAST:event_sprEditUchenStepActionPerformed

    private void sprEditStatusPPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditStatusPPSActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_statusteach SET status_code='" + val1
                    + "', status_name='" + val2 + "' WHERE status_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_SttPS();
    }//GEN-LAST:event_sprEditStatusPPSActionPerformed

    private void sprEditDisciplinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditDisciplinesActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_discipline SET disc_code='" + val1
                    + "', disc_name='" + val2 + "' WHERE disc_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Disciplina();
    }//GEN-LAST:event_sprEditDisciplinesActionPerformed

    private void sprEditUsZachislActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditUsZachislActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_zachislenie SET zachislenie_code='" + val1
                    + "', zachislenie_name='" + val2 + "' WHERE zachislenie_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Uslzachisl();
    }//GEN-LAST:event_sprEditUsZachislActionPerformed

    private void sprEditFormobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditFormobActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_formobuch SET formobuch_code='" + val1
                    + "', formobuch_name='" + val2 + "' WHERE formobuch_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Formobuchn();
    }//GEN-LAST:event_sprEditFormobActionPerformed

    private void sprEditSostObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprEditSostObActionPerformed
        try {
            String val1 = edPanIdField.getText();
            String val2 = edPanNameField.getText();

            String sql = "UPDATE spr_sostobuchen SET sostobuchen_code='" + val1
                    + "', sostobuchen_name='" + val2 + "' WHERE sostobuchen_code='" + val1 + "'";

            pst = conn.prepareStatement(sql);
            pst.execute();

            JOptionPane.showMessageDialog(null, "Updated");


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
        Update_spr_Sostobuchn();
    }//GEN-LAST:event_sprEditSostObActionPerformed

    private void tableUchenZvanMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUchenZvanMouseReleased
        int row = tableUchenZvan.getSelectedRow();
        String idUzvanie = (tableUchenZvan.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_uzvanie WHERE uzvanie_code='" + idUzvanie + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("uzvanie_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("uzvanie_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableUchenZvanMouseReleased

    private void tableUchenStepMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUchenStepMouseReleased
        int row = tableUchenStep.getSelectedRow();
        String idUchst = (tableUchenStep.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_uchstepen WHERE ustep_code='" + idUchst + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("ustep_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("ustep_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableUchenStepMouseReleased

    private void tableStatusPPSMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStatusPPSMouseReleased
         int row = tableStatusPPS.getSelectedRow();
        String idStatus = (tableStatusPPS.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_statusteach WHERE status_code='" + idStatus + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("status_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("status_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableStatusPPSMouseReleased

    private void tableDisciplinesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableDisciplinesMouseReleased
       int row = tableDisciplines.getSelectedRow();
        String idDisc = (tableDisciplines.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_discipline WHERE disc_code='" + idDisc + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("disc_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("disc_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableDisciplinesMouseReleased

    private void tableUsZachislMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsZachislMouseReleased
       int row = tableUsZachisl.getSelectedRow();
        String idZach = (tableUsZachisl.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_zachislenie WHERE zachislenie_code='" + idZach + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("zachislenie_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("zachislenie_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableUsZachislMouseReleased

    private void tableFormobMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFormobMouseReleased
       int row = tableFormob.getSelectedRow();
        String idFormob = (tableFormob.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_formobuch WHERE formobuch_code='" + idFormob + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("formobuch_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("formobuch_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableFormobMouseReleased

    private void tableSostObMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSostObMouseReleased
        int row = tableSostOb.getSelectedRow();
        String idSost = (tableSostOb.getValueAt(row, 0).toString());
        String sql = "SELECT * FROM spr_sostobuchen WHERE sostobuchen_code='" + idSost + "'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                String add1 = rs.getString("sostobuchen_code");
                edPanIdField.setText(add1);
                String add2 = rs.getString("sostobuchen_name");
                edPanNameField.setText(add2);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {

                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tableSostObMouseReleased

    private void sprAddNationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddNationActionPerformed
     AddNation addNat = new AddNation();
     addNat.setVisible(true);
    }//GEN-LAST:event_sprAddNationActionPerformed

    private void sprAddRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddRegionActionPerformed
      AddRegion addReg = new AddRegion();
      addReg.setVisible(true);
    }//GEN-LAST:event_sprAddRegionActionPerformed

    private void sprAddDoljnostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddDoljnostActionPerformed
    AddDoljnost addDolj = new AddDoljnost();
    addDolj.setVisible(true);
    }//GEN-LAST:event_sprAddDoljnostActionPerformed

    private void sprAddUchenZvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddUchenZvanActionPerformed
    AddUzvan addUzv = new AddUzvan();
    addUzv.setVisible(true);
    }//GEN-LAST:event_sprAddUchenZvanActionPerformed

    private void sprAddUchenStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddUchenStepActionPerformed
     AddUstep addUst = new AddUstep();
     addUst.setVisible(true);
    }//GEN-LAST:event_sprAddUchenStepActionPerformed

    private void sprAddStatusPPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddStatusPPSActionPerformed
     AddStatus addStt = new AddStatus();
     addStt.setVisible(true);
    }//GEN-LAST:event_sprAddStatusPPSActionPerformed

    private void sprAddDisciplinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddDisciplinesActionPerformed
      AddDiscipline addDiscip = new AddDiscipline();
      addDiscip.setVisible(true);
    }//GEN-LAST:event_sprAddDisciplinesActionPerformed

    private void sprAddUsZachislActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddUsZachislActionPerformed
      AddZachislenie addZach = new AddZachislenie();
      addZach.setVisible(true);
    }//GEN-LAST:event_sprAddUsZachislActionPerformed

    private void sprAddFormobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddFormobActionPerformed
      AddFormobuch addFormch = new AddFormobuch();
      addFormch.setVisible(true);
    }//GEN-LAST:event_sprAddFormobActionPerformed

    private void sprAddSostObActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sprAddSostObActionPerformed
      AddSostob addSost = new AddSostob();
      addSost.setVisible(true);
    }//GEN-LAST:event_sprAddSostObActionPerformed

    private void studentCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentCancelButtonActionPerformed
        Table_StudentsMouseClicked(null);
    }//GEN-LAST:event_studentCancelButtonActionPerformed

    private void Table_DepartmentsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_DepartmentsMouseReleased
            
       try{
           int row = Table_Departments.getSelectedRow();
           String Table_Click = (Table_Departments.getValueAt(row, 0).toString());    
           String sql = "SELECT * FROM departments WHERE depart_code = '"+Table_Click+"'";
            pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                String add1 = rs.getString("depart_code");
                    fieldCodeIdDep.setText(add1);
                String add2 = rs.getString("depart_name");
                    fieldNameDepE.setText(add2);
                String add3 = rs.getString("depart_header");
                    fieldHeaderDepE.setText(add3);
                String add4 = rs.getString("depart_assistant");
                    fieldLaborantDepE.setText(add4);
                String add5 = rs.getString("depart_phone");
                    fieldPhoneDepE.setText(add5);
                String add6 = rs.getString("depart_email");
                    fieldMaileDepE.setText(add6);
                 clickDepToGroups(add1);
             }
             rs.close();
             pst.close();
             
          
       }catch(SQLException sqex){
           JOptionPane.showMessageDialog(null, sqex);
       }
          
    }//GEN-LAST:event_Table_DepartmentsMouseReleased

    private void ComboBoxNationalityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboBoxNationalityMouseClicked
 
    }//GEN-LAST:event_ComboBoxNationalityMouseClicked

    private void printSpisokGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printSpisokGroupActionPerformed
       try{
           String grName =(String) TabStudentTableName.getText();
           JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream("reports/ListOfGroup.jrxml"));
           String sql = "SELECT\n" +
                            "     student.`student_id` AS 'ID студента',\n" +
                            "     student.`student_lastname` AS Фамилия,\n" +
                            "     student.`student_name` AS Имя,\n" +
                            "     student.`student_middlename` AS Отчество,\n" +
                            "     student.`student_birthday` AS 'Дата рождения',\n" +
                            "     student.`student_sex` AS Пол,\n" +
                            "     groups.`group_name` AS Направление,\n" +
                            "     groups.`group_additional2` AS Выпуск,\n" +
                            "     departments.`depart_name` AS Программа,\n" +
                            "     groups.`group_code` AS Группа,\n" +
                            "     departments.`depart_code` AS departments_depart_code\n" +
                        "FROM\n" +
                            "     `groups` groups INNER JOIN `student` student ON groups.`group_code` = student.`group_code`\n" +
                        "INNER JOIN `departments` departments ON groups.`department_code` = departments.`depart_code`\n" +
                        "WHERE\n" +
                            "    student.group_code = '"+grName+"'"+
                        "ORDER BY student_lastname";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Список группы "+grName);
           jv.setVisible(true);
          
  
           
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printSpisokGroupActionPerformed Action Error:\n"+e);
       }
    }//GEN-LAST:event_printSpisokGroupActionPerformed

    private void printSpravkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printSpravkaActionPerformed
        try{
           String studID =(String) fieldIdStudent.getText();
           InputStream in = getClass().getResourceAsStream("reports/Spavka.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           String sql = "SELECT\n" +
                "     spr_settings.`A_NAMEL` AS NAMEL_ru,\n" +
                "     spr_settingsen.`A_NAMEL` AS NAMEL_en,\n" +
                "     spr_settingskg.`A_NAMEL` AS NAMEL_kg,\n" +
                "     spr_settingskg.`A_ADRES` AS ADRES_kg,\n" +
                "     spr_settingsen.`A_ADRES` AS ADRES_en,\n" +
                "     spr_settings.`A_ADRES` AS ADRES_ru,\n" +
                "      spr_settings.`A_NAMES` AS NAMES_ru,\n" +
                "     spr_settingskg.`A_PHONE` AS PHONE_kg,\n" +
                "     spr_settingskg.`A_FAX` AS FAX_kg,\n" +
                "     spr_settingskg.`A_EMAIL` AS EMAIL_kg,\n" +
                "     spr_settingskg.`A_WWW` AS WWW_kg,\n" +
                "     student.`student_lastname` AS lastname,\n" +
                "     student.`student_name` AS name,\n" +
                "     student.`student_middlename` AS  middlename,\n" +
                "     student.`group_code` AS  group_code,\n" +
                "     student.`student_sex` AS  sex,\n" +
                "     groups.`group_semestr` AS  group_semestr,\n" +
                "     groups.`group_name` AS  group_name,\n" +
                "     spr_formobuch.`formobuch_name` AS  formobuch_name,\n" +
                "     spr_settingskg.`A_RECTOR` AS  RECTOR_kg,\n" +
                "     spr_settingskg.`A_PRIM` AS PRIM_kg,\n" +
                "     spr_settingskg.`PHOTO_INF` AS PHOTO_INF_kg\n" +
                "FROM\n" +
                "     `spr_settingskg` spr_settingskg,\n" +
                "     `spr_settingsen` spr_settingsen,\n" +
                "     `spr_settings` spr_settings,\n" +
                "     `student` student \n" +
                "     INNER JOIN \n" +
                "`groups` groups ON student.`group_code` = groups.`group_code`\n" +
                "     INNER JOIN \n" +
                "`spr_formobuch` spr_formobuch ON groups.`group_formaobuch` = spr_formobuch.`formobuch_code`\n" +
                "WHERE\n" +
                "student_id = '"+studID+"'";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Справка");
           jv.setVisible(true);
          
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printSpravka Action Error:\n"+e);
            e.printStackTrace();
       }        
    }//GEN-LAST:event_printSpravkaActionPerformed

    private void printUchPlanStudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printUchPlanStudActionPerformed

          String studID = fieldIdStudent.getText();
          String uchPlSem = studUchPlSemestrTextF.getText();
        
        try{
           
           InputStream in = getClass().getResourceAsStream("reports/UchPlan.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           String sql = "SELECT "+
                      "st.student_lastname AS student_student_lastname, "+
                      "st.student_name AS student_student_name, "+
                      "st.student_middlename AS student_student_middlename, "+
                      "departments.depart_name AS departments_depart_name, "+
                      "uchplanstudents.group_code AS uchplanstudents_group_code, "+
                      "uchplanstudents.student_id AS uchplanstudents_student_id, "+
                      "uchplanstudents.uchPlans_sem AS uchplanstudents_uchPlans_sem, "+
                      "uchplanstudents.disc_code AS uchplanstudents_disc_code, "+
                      "uchplanstudents.uchpls_clock AS uchplanstudents_uchPls_clock, "+
                      "uchplanstudents.uchpls_credit AS uchplanstudents_uchPls_credit, "+
                      "uchplanstudents.uchpls_ball AS uchplanstudents_uchPls_ball, "+
                      "uchplanstudents.uchpls_ocenka AS uchplanstudents_uchPls_ocenka, "+
                      "uchplanstudents.uchpls_controlb AS uchplanstudents_uchPls_controlb, "+
                      "uchplanstudents.uchpls_date AS uchplanstudents_uchPls_date, "+
                      "uchplanstudents.uchpls_numbervedom AS VedomNumber, "+
                      "groups.group_name AS groups_group_name, "+
                      "st.student_id AS student_student_id, "+
                      "spr_discipline.disc_name AS spr_discipline_disc_name, "+
                      "groups.group_semestr AS groups_group_semestr, "+
                      "spr_settings.A_NAMES AS spr_settings_A_NAMES "+
                    "FROM "+
                    "uchplanstudents uchplanstudents "+
                    "INNER JOIN student st ON uchplanstudents.student_id = st.student_id "+
                    "INNER JOIN groups groups ON st.group_code = groups.group_code AND groups.group_code = uchplanstudents.group_code "+
                    "INNER JOIN departments departments ON groups.department_code = departments.depart_code "+
                    "INNER JOIN spr_discipline spr_discipline ON uchplanstudents.disc_code = spr_discipline.disc_code, "+
                    "spr_settings spr_settings"+
                    " WHERE "+
                    "uchplanstudents.student_id='"+studID+"'"+
                    " AND "+
                    " uchplanstudents.uchPlans_sem='"+uchPlSem+"'";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Учебный план студента за "+uchPlSem+" семестр");
           jv.setVisible(true);
          
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"printUchPlanStudActionPerformed "+e);
        }
    }//GEN-LAST:event_printUchPlanStudActionPerformed

    private void printGosAttestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printGosAttestActionPerformed
        try{
           InputStream in = getClass().getResourceAsStream("reports/GosAttestat.jrxml");
           JasperDesign report = JRXmlLoader.load(in);
           JasperReport jr = JasperCompileManager.compileReport(report);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Форма государственной аттестации");
           jv.setVisible(true);
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printSpisokGroupActionPerformed Action Error:\n"+e);
       }
    }//GEN-LAST:event_printGosAttestActionPerformed

    private void printEkzamListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printEkzamListActionPerformed
        String stud_id = fieldIdStudent.getText();
        String disc_id = studUchPlDisciplinaField.getText();
        if(disc_id.isEmpty()){
            JOptionPane.showMessageDialog(null,"Не определен предмет!");
        }else if(stud_id.isEmpty()){
            JOptionPane.showConfirmDialog(null,"Студент не выбран!");
        }else{        
            ExpDateExamList exc = new ExpDateExamList();
            exc.setParams(stud_id, disc_id);
            exc.setVisible(true);
        }
    }//GEN-LAST:event_printEkzamListActionPerformed

    private void printVedTekControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printVedTekControlActionPerformed
       try{

           String grName =(String) TabStudentTableName.getText();
           
           InputStream in = getClass().getResourceAsStream("reports/VedomTekControl.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           String sql ="SELECT " +
                            "student.`student_lastname` AS student_student_lastname, " +
                            "student.`student_name` AS student_student_name, " +
                            "student.`student_middlename` AS student_student_middlename, " +
                            "student.`student_semestr` AS student_student_semestr, " +
                            "student.`group_code` AS student_group_code, " +
                            "spr_formobuch.`formobuch_name` AS spr_formobuch_formobuch_name, " +
                            "spr_settings.`A_NAMEL` AS spr_settings_A_NAMEL, " +
                            "spr_settings.`A_YEARS` AS spr_settings_A_YEARS, " +
                            "spr_settings.`A_YEARE` AS spr_settings_A_YEARE, " +
                            "spr_settings.`A_UOTDEL` AS spr_settings_A_UOTDEL " +
                        "FROM " +
                            "student `student` " +
                        "INNER JOIN groups `gr` ON student.group_code=gr.group_code " +
                        "INNER JOIN spr_formobuch spr_formobuch ON gr.group_formaobuch=spr_formobuch.formobuch_code, " +
                            "spr_settings spr_settings " +
                        "WHERE " +
                            "student.group_code = '"+grName+"' " +
                        "ORDER BY student_lastname ASC";

           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Ведомость текущего конроля");
           jv.setVisible(true);
          
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printVedTekControlActionPerformed Action Error:\n"+e);
       }                                             

    }//GEN-LAST:event_printVedTekControlActionPerformed

    private void printJournalDvijActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printJournalDvijActionPerformed
        try{

           String grName =(String) TabStudentTableName.getText();
                      
           InputStream in = getClass().getResourceAsStream("reports/JournalReport.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           String sql ="SELECT\n" +
                        "     student.`student_lastname` AS student_student_lastname,\n" +
                        "     student.`student_name` AS student_student_name,\n" +
                        "     student.`student_middlename` AS student_student_middlename,\n" +
                        "     student.`student_birthday` AS student_student_birthday,\n" +
                        "     student.`nationality_code` AS student_nationality_code,\n" +
                        "     student.`student_prikaz` AS student_student_prikaz,\n" +
                        "     student.`student_prikazdate` AS student_student_prikazdate,\n" +
                        "     groups.`group_name` AS groups_group_name,\n" +
                        "     student.`group_code` AS student_group_code,\n" +
                        "     spr_nationality.`nationality_name` AS spr_nationality_nationality_name,\n" +
                        "     spr_settings.`A_NAMES` AS spr_settings_A_NAMES\n" +
                        "FROM\n" +
                        "     `groups` groups INNER JOIN `student` student ON groups.`group_code` = student.`group_code`\n" +
                        "     INNER JOIN `spr_nationality` spr_nationality ON student.`nationality_code` = spr_nationality.`nationality_code`,\n" +
                        "     `spr_settings` spr_settings\n" +
                        "WHERE\n" +
                        "      student.group_code = '"+grName+"'"+
                        "ORDER BY student_lastname ASC";

           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Журнал учета движения студентов группы "+grName);
           jv.setVisible(true);
          
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printVedTekControlActionPerformed Action Error:\n"+e);
       }                              
    }//GEN-LAST:event_printJournalDvijActionPerformed

    private void studPerevGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studPerevGroupButtonActionPerformed

            String stud_id =(String) fieldIdStudent.getText();
            String stud_FirN =(String)fieldFamiliyaStudent.getText();
            String stud_Name =(String)fieldNameStudent.getText();
            String stud_MidN =(String)fieldOtchestvoStudent.getText();
            String stud_Group =(String)TabStudentTableName.getText();
            groupRenameStudent gRenStud = new groupRenameStudent();
            gRenStud.addStudId(stud_id,stud_FirN, stud_Name, stud_MidN, stud_Group);
            gRenStud.setVisible(true);
        
            Update_table_Students();
    }//GEN-LAST:event_studPerevGroupButtonActionPerformed

    private void studUchPlDisciplinaComboB1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_studUchPlDisciplinaComboB1PopupMenuWillBecomeInvisible
           
           String item = (String) studUchPlDisciplinaComboB1.getSelectedItem();
           String sql = "SELECT disc_code FROM spr_discipline WHERE disc_name = ?";
         try{  
             pst = conn.prepareStatement(sql);
             pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("disc_code");
                    studUchPlDisciplinaField.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "studUchPlDisciplinaComboB1MouseClicked "+sqlex);
       }
    }//GEN-LAST:event_studUchPlDisciplinaComboB1PopupMenuWillBecomeInvisible

    private void ComboBoxCodeOfPodrazdelPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxCodeOfPodrazdelPopupMenuWillBecomeInvisible
            
           String item = (String) ComboBoxCodeOfPodrazdel.getSelectedItem();
           String sql = "SELECT depart_code FROM departments WHERE depart_name = ?";
          try{ 
             pst = conn.prepareStatement(sql);
             pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("depart_code");
                    textFieldCodeOfPodrazdel.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxCodeOfPodrazdelMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_ComboBoxCodeOfPodrazdelPopupMenuWillBecomeInvisible

    private void ComboBoxDoljnostPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxDoljnostPopupMenuWillBecomeInvisible
        
           String item = (String) ComboBoxDoljnost.getSelectedItem();
           String sql = "SELECT dolj_code FROM spr_doljnost WHERE dolj_name = ?";
       try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("dolj_code");
                    jTextField27.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxDoljnostMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_ComboBoxDoljnostPopupMenuWillBecomeInvisible

    private void ComboBoxUchenZvaniePopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxUchenZvaniePopupMenuWillBecomeInvisible
            
           String item = (String) ComboBoxUchenZvanie.getSelectedItem();
           String sql = "SELECT uzvanie_code FROM spr_uzvanie WHERE uzvanie_name = ?";
       try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("uzvanie_code");
                    textFieldUchenZvanie.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxUchenZvanieMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_ComboBoxUchenZvaniePopupMenuWillBecomeInvisible

    private void ComboBoxUchenStepenPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxUchenStepenPopupMenuWillBecomeInvisible
            
           String item = (String) ComboBoxUchenStepen.getSelectedItem();
           String sql = "SELECT ustep_code FROM spr_uchstepen WHERE ustep_name = ?";
         try{
             pst = conn.prepareStatement(sql);
             pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("ustep_code");
                    textFieldUchenStepen.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxUchenStepenMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_ComboBoxUchenStepenPopupMenuWillBecomeInvisible

    private void ComboBoxStatusPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxStatusPopupMenuWillBecomeInvisible
           
           String item = (String) ComboBoxStatus.getSelectedItem();
           String sql = "SELECT status_code FROM spr_statusteach WHERE status_name = ?";
       try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("status_code");
                    textFieldStatus.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxUchenStepenMouseClicked "+sqlex);
       }
        
    }//GEN-LAST:event_ComboBoxStatusPopupMenuWillBecomeInvisible

    private void ComboBoxNationalityPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_ComboBoxNationalityPopupMenuWillBecomeInvisible
          
           String item = (String) ComboBoxNationality.getSelectedItem();
           String sql = "SELECT nationality_code FROM spr_nationality WHERE nationality_name = ?";
        try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("nationality_code");
                    textFieldNationality.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "ComboBoxNationalityMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_ComboBoxNationalityPopupMenuWillBecomeInvisible

    private void chooserNationalityStudentPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserNationalityStudentPopupMenuWillBecomeInvisible
         
           String item = (String) chooserNationalityStudent.getSelectedItem();
           String sql = "SELECT nationality_code FROM spr_nationality WHERE nationality_name = ?";
        try{ 
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("nationality_code");
                    fieldNationalityStudent.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "chooserNationalityStudentMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_chooserNationalityStudentPopupMenuWillBecomeInvisible

    private void chooserCounrtyStudentPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserCounrtyStudentPopupMenuWillBecomeInvisible
           
           String item = (String) chooserCounrtyStudent.getSelectedItem();
           String sql = "SELECT cou_cod FROM spr_country WHERE cou_name = ?";
         try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("cou_cod");
                    fieldCountryStudent.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "chooserCounrtyStudentMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_chooserCounrtyStudentPopupMenuWillBecomeInvisible

    private void chooserRegionStudentPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserRegionStudentPopupMenuWillBecomeInvisible
     
           String item = (String) chooserRegionStudent.getSelectedItem();
           String sql = "SELECT region_code FROM spr_region WHERE region_name = ?";
       try{ 
           pst = conn.prepareStatement(sql);
           pst.setString(1, item); 
           rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("region_code");
                    fieldRegionStudent1.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "chooserRegionStudentMouseClicked "+sqlex);
       }
    }//GEN-LAST:event_chooserRegionStudentPopupMenuWillBecomeInvisible

    private void chooserUsloviyaZachisleniya1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserUsloviyaZachisleniya1PopupMenuWillBecomeInvisible
   
           String item = (String) chooserUsloviyaZachisleniya1.getSelectedItem();
           String sql = "SELECT zachislenie_code FROM spr_zachislenie WHERE zachislenie_name =?";
      try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("zachislenie_code");
                    fieldUsloviyaZachisleniya.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "chooserUsloviyaZachisleniya1MouseClicked "+sqlex);
       }
    }//GEN-LAST:event_chooserUsloviyaZachisleniya1PopupMenuWillBecomeInvisible

    private void chooserSostStudentPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserSostStudentPopupMenuWillBecomeInvisible
     
           String item = (String) chooserSostStudent.getSelectedItem();
           String sql = "SELECT sostobuchen_code FROM spr_sostobuchen WHERE sostobuchen_name = ?";
      try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
           rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("sostobuchen_code");
                    fieldSostObuchStudent.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "chooserSostStudentMouseClicked "+sqlex);
       }
               
    }//GEN-LAST:event_chooserSostStudentPopupMenuWillBecomeInvisible

    private void planeComboBoxDisciplinaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_planeComboBoxDisciplinaPopupMenuWillBecomeInvisible
           
           String item = (String) planeComboBoxDisciplina.getSelectedItem();
           String sql = "SELECT disc_code FROM spr_discipline WHERE disc_name = ?";
      try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("disc_code");
                    planeTextFieldDisciplina1.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "planeComboBoxDisciplinaActionPerformed "+sqlex);
       }
    }//GEN-LAST:event_planeComboBoxDisciplinaPopupMenuWillBecomeInvisible

    private void raznoskaButtonPlaneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raznoskaButtonPlaneActionPerformed
        int row = Table_PlaneOfGroup.getSelectedRow();
        int rowIndexStart = Table_PlaneOfGroup.getSelectionModel().getMinSelectionIndex();
        int rowIndexEnd = Table_PlaneOfGroup.getSelectionModel().getMaxSelectionIndex();

        int student_table_col_count= Table_Students.getRowCount()-1;
            
            if(rowIndexStart==rowIndexEnd){
                for(int i=0; i<=student_table_col_count; i++){    
                   String stId = (Table_Students.getValueAt(i, 0).toString()); 
                        if(isExistsUchPlan(stId, row)==0){
                            raznoskaUchPlan(stId,row);
                        }
                 }
                JOptionPane.showMessageDialog(null, "Разноска учебного плана\n успешно выполнено!");
            }else{
                for(int i=row; i<=rowIndexEnd; i++){
                    for(int a=0; a<=student_table_col_count; a++){
                        String stId = (Table_Students.getValueAt(a, 0).toString());
                            if(isExistsUchPlan(stId, i)==0){
                                 raznoskaUchPlan(stId,i);
                             }
                    }
                 }
                JOptionPane.showMessageDialog(null, "Разноска выполнено успешно!");
            }
        
    }//GEN-LAST:event_raznoskaButtonPlaneActionPerformed

    private void planeComboBoxPrepodavatelPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_planeComboBoxPrepodavatelPopupMenuWillBecomeInvisible
        
           String item = (String) planeComboBoxPrepodavatel.getSelectedItem();
           String sql = "SELECT * FROM teacher WHERE teacher_lastname = ?";
      try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("teacher_code");
                    nameOfTeacher.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "planeComboBoxDisciplinaActionPerformed "+sqlex);
       }

    }//GEN-LAST:event_planeComboBoxPrepodavatelPopupMenuWillBecomeInvisible

    private void printUchKartochkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printUchKartochkaActionPerformed
    try{
           String studID =(String) fieldIdStudent.getText();
           InputStream in = getClass().getResourceAsStream("reports/UchKartochka.jrxml");
           JasperDesign jd = JRXmlLoader.load(in);
           String sql = "SELECT \n" +
                        "       departments.depart_name AS departments_depart_name,\n" +
                        "	groups.department_code AS groups_department_code,\n" +
                        "	groups.group_semestr AS groups_group_semestr,\n" +
                        "	groups.group_formaobuch AS groups_group_formaobuch,\n" +
                        "	groups.group_code AS groups_group_code,\n" +
                        "	groups.group_name AS groups_group_name,\n" +
                        "	student.student_id AS student_student_id,\n" +
                        "	student.student_lastname AS student_student_lastname,\n" +
                        "	student.student_name AS student_student_name,\n" +
                        "	student.student_middlename AS student_student_middlename,\n" +
                        "	student.student_birthday AS student_student_birthday,\n" +
                        "	student.nationality_code AS student_nationality_code,\n" +
                        "	student.student_addr AS student_student_addr,\n" +
                        "	student.student_docfrom AS student_student_docfrom,\n" +
                        "	student.student_prikaz AS student_student_prikaz,\n" +
                        "	student.student_prikazdate AS student_student_prikazdate,\n" +
                        "	student.student_photo AS student_student_photo,\n" +
                        "	student.student_propiska AS student_student_propiska,\n" +
                        "	student.student_phone AS student_student_phone,\n" +
                        "	student.student_social AS social_student,\n" +
                        "	student.student_docdate AS studl_docdate,\n" +
                        "	spr_zachislenie.zachislenie_name AS spr_zachislenie_zachislenie_name,\n" +
                        "	spr_settingsen.`A_NAMEL` AS spr_settingsen_A_NAMEL,\n" +
                        "	spr_settingsen.`A_ADRES` AS spr_settingsen_A_ADRES,\n" +
                        "	spr_settingsen.`A_PHONE` AS spr_settingsen_A_PHONE,\n" +
                        "	spr_settingsen.`A_FAX` AS spr_settingsen_A_FAX,\n" +
                        "	spr_settingsen.`A_RECTOR` AS spr_settingsen_A_RECTOR,\n" +
                        "	spr_settingsen.`A_UOTDEL` AS spr_settingsen_A_UOTDEL,\n" +
                        "	spr_settingsen.`A_WWW` AS spr_settingsen_A_WWW,\n" +
                        "	spr_settingsen.`A_EMAIL` AS spr_settingsen_A_EMAIL,\n" +
                        "	spr_settingskg.`A_NAMEL` AS spr_settingskg_A_NAMEL,\n" +
                        "	spr_settingskg.`A_ADRES` AS spr_settingskg_A_ADRES,\n" +
                        "	spr_settingskg.`A_PHONE` AS spr_settingskg_A_PHONE,\n" +
                        "	spr_settingskg.`A_FAX` AS spr_settingskg_A_FAX,\n" +
                        "	spr_settingskg.`A_RECTOR` AS spr_settingskg_A_RECTOR,\n" +
                        "	spr_settingskg.`A_WWW` AS spr_settingskg_A_WWW,\n" +
                        "	spr_settingskg.`A_EMAIL` AS spr_settingskg_A_EMAIL,\n" +
                        "	spr_settingskg.`PHOTO_INF` AS spr_settingskg_PHOTO_INF,\n" +
                        "	spr_settings.`A_NAMEL` AS spr_settings_A_NAMEL,\n" +
                        "	spr_settings.`A_ADRES` AS spr_settings_A_ADRES,\n" +
                        "	spr_settings.`A_PHONE` AS spr_settings_A_PHONE,\n" +
                        "	spr_settings.`A_FAX` AS spr_settings_A_FAX,\n" +
                        "	spr_settings.`A_RECTOR` AS spr_settings_A_RECTOR,\n" +
                        "	spr_settings.`A_WWW` AS spr_settings_A_WWW,\n" +
                        "	spr_settings.`A_EMAIL` AS spr_settings_A_EMAIL,\n" +
                        "	spr_settingskg.`A_UOTDEL` AS spr_settingskg_A_UOTDEL,\n" +
                        "	spr_settings.`A_UOTDEL` AS spr_settings_A_UOTDEL,\n" +
                        "	spr_settingskg.`A_NAMES` AS spr_settingskg_A_NAMES,\n" +
                        "	spr_settings.`A_NAMES` AS spr_settings_A_NAMES,\n" +
                        "	spr_settingsen.`A_NAMES` AS spr_settingsen_A_NAMES,\n" +
                        "	spForm.formobuch_name AS formobuchName,\n" +
                        "	sp_nat.nationality_name AS Nationality\n" +
                        "FROM departments departments\n" +
                        "	INNER JOIN groups groups ON departments.depart_code = groups.department_code \n" +
                        "	INNER JOIN student student ON groups.group_code = student.group_code\n" +
                        "	INNER JOIN spr_formobuch spForm ON groups.group_formaobuch=spForm.formobuch_code\n" +
                        "	INNER JOIN spr_zachislenie spr_zachislenie ON student.zachislenie_code = spr_zachislenie.zachislenie_code\n" +
                        "	INNER JOIN spr_nationality sp_nat ON student.nationality_code=sp_nat.nationality_code,\n" +
                        "	spr_settingsen spr_settingsen,\n" +
                        "	spr_settingskg spr_settingskg,\n" +
                        "	spr_settings spr_settings\n" +
                        "WHERE \n" +
                        "	 student.student_id = '"+studID+"'";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("Учебная карточка студента");
           jv.setVisible(true);
          
        }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printUchKartochkaActionPerformed Action Error:\n"+e);
        } 
    }//GEN-LAST:event_printUchKartochkaActionPerformed

    private void Table_StudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_StudentsMouseClicked

        int row = Table_Students.getSelectedRow();
        String Table_Click = (Table_Students.getValueAt(row, 0).toString());

        try{
            String sql = "SELECT * FROM student WHERE student_id='"+Table_Click+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if(rs.next()){
                textPaneStudentPhoto.setIcon(null);
                String add1= rs.getString("student_id");

                String add2= rs.getString("student_lastname");
                String add3= rs.getString("student_name");
                String add4= rs.getString("student_middlename");
                String add5= rs.getString("student_birthday");
                String add6= rs.getString("nationality_code");
                String add7= rs.getString("cou_cod");
                String add8= rs.getString("region_code");
                String add9= rs.getString("student_sex");
                String add10= rs.getString("student_propiska");
                String add11= rs.getString("student_addr");
                String add12= rs.getString("student_phone");
                String add13= rs.getString("student_email");
                String add14= rs.getString("student_passeries");
                String add15= rs.getString("student_passnumber");
                String add16= rs.getString("student_passvid");
                String add17= rs.getString("student_passissue");
                String add18= rs.getString("student_attseries");
                String add19= rs.getString("student_attnumber");
                String add20= rs.getString("student_dipseries");
                String add21= rs.getString("student_dipnumber");
                String add22= rs.getString("student_docdate");
                String add23= rs.getString("student_docfrom");
                String add24= rs.getString("student_prikaz");
                String add25= rs.getString("student_prikazdate");
                String add26= rs.getString("zachislenie_code");
                String add27= rs.getString("student_semestr");
                String add28= rs.getString("student_sumk");
                String add29= rs.getString("student_dsera");
                String add30= rs.getString("student_duma");
                String add31= rs.getString("student_diplvidacha");
                String add32= rs.getString("sostobuchen_code");
                String add33= rs.getString("student_dateokonchanie");
                String add34= rs.getString("student_additional1");
                String add35= rs.getString("student_additional2");

                imageShow(add1);

                fieldIdStudent.setText(add1);
                fieldFamiliyaStudent.setText(add2);
                fieldNameStudent.setText(add3);
                fieldOtchestvoStudent.setText(add4);
                ((JTextField)calendarBirthStudent.getDateEditor().getUiComponent()).setText(add5);
                fieldNationalityStudent.setText(add6);
                fieldCountryStudent.setText(add7);
                fieldRegionStudent1.setText(add8);
                fieldSexStudent.setText(add9);
                fieldPropiskaStudent.setText(add10);
                fieldProjivanieStudent.setText(add11);
                fieldPhoneStudent.setText(add12);
                fieldEmailStudent.setText(add13);
                fieldPasportSeriyaStudent.setText(add14);
                fieldPasportNumberStudent2.setText(add15);
                fieldPasportNumberStudent1.setText(add16);
                ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).setText(add17);
                fieldAttestatSeriyaStudent.setText(add18);
                fieldAttestatNumberStudent.setText(add19);
                fieldDiplomSeriyaStudent.setText(add20);
                fieldDiplomNumberStudent.setText(add21);
                ((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).setText(add22);
                fieldMestoVydachiDoc.setText(add23);
                fieldNumOfPrikazZachisl.setText(add24);
                ((JTextField)choserDateOfPrikazZachisl.getDateEditor().getUiComponent()).setText(add25);
                fieldUsloviyaZachisleniya.setText(add26);
                fieldSemestrStudent.setText(add27);
                fieldSumOfContract.setText(add28);
                fieldDiplBfaSeiyaStudent.setText(add29);
                fieldDiplBfaNumberStudent.setText(add30);
                ((JTextField)dateVidachDiplBfa.getDateEditor().getUiComponent()).setText(add31);
                fieldSostObuchStudent.setText(add32);
                ((JTextField)dateOfSostStudent.getDateEditor().getUiComponent()).setText(add33);
                fieldAdditionalStudent1.setText(add34);
                fieldAdditionalStudent2.setText(add35);
                //            textPaneStudentPhoto.setIcon(format);

                StudClickTableToUch(Table_Click);
        String user = jMenuUserLogin.getText();
        studUchPlApplyButton.setEnabled(false);
        logAddLoginLabel.setText(user);
        logEditUserField.setText(user);

        logDateLabel.setDate(new java.util.Date());
        logLastEditDate.setDate(new java.util.Date());
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Exception of Action Performed Table_StudentsMouseClicked \n"+e);

        }
    }//GEN-LAST:event_Table_StudentsMouseClicked

    private void Table_DepartmentsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Table_DepartmentsKeyPressed
//               if(evt.getKeyCode()==KeyEvent.VK_DOWN){
//                   Table_DepartmentsMouseReleased(null);
//        }
    }//GEN-LAST:event_Table_DepartmentsKeyPressed

    private void raznoskaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raznoskaButtonActionPerformed
        String grCode = fieldNameGroup.getText();
        Integer semTo = Integer.parseInt(fieldSemestrGroup.getText())+1;

        groupSemestrToSemestr(semTo, grCode);
    }//GEN-LAST:event_raznoskaButtonActionPerformed

    private void comboTeacherEditGroupPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboTeacherEditGroupPopupMenuWillBecomeInvisible
        
           String item = (String) comboTeacherEditGroup.getSelectedItem();
           String sql = "SELECT * FROM teacher WHERE teacher_lastname = ?";
      try{
           pst = conn.prepareStatement(sql);
           pst.setString(1, item);
             rs = pst.executeQuery();
             if(rs.next()){
             String add1 = rs.getString("teacher_code");
                  fieldMaterGroup.setText(add1);
             }
       }catch(SQLException sqlex){
           JOptionPane.showMessageDialog(null, "planeComboBoxDisciplinaActionPerformed "+sqlex);
       }

    }//GEN-LAST:event_comboTeacherEditGroupPopupMenuWillBecomeInvisible

    private void journalBallovPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_journalBallovPrintActionPerformed
        String grCode = TabStudentTableName.getText();
        String semestr = planeTextFieldSemestr.getText();
        ExecuteCreateJournal exc = new ExecuteCreateJournal();
        try {
            exc.createJournalExcel(grCode, semestr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_journalBallovPrintActionPerformed


    
    public static void main(String args[]) {
       
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(mainBdoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(mainBdoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(mainBdoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(mainBdoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

         
        
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new mainBdoc().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox ComboBoxCodeOfPodrazdel;
    private javax.swing.JComboBox ComboBoxDoljnost;
    private javax.swing.JComboBox ComboBoxNationality;
    private javax.swing.JComboBox ComboBoxSex;
    private javax.swing.JComboBox ComboBoxStatus;
    private javax.swing.JComboBox ComboBoxUchenStepen;
    private javax.swing.JComboBox ComboBoxUchenZvanie;
    private javax.swing.JPanel CountryPanel;
    private javax.swing.JPanel EditPanelCount;
    private javax.swing.JPanel EditPanelDisciplines;
    private javax.swing.JPanel EditPanelDoljnost;
    private javax.swing.JPanel EditPanelFormob;
    private javax.swing.JPanel EditPanelNation;
    private javax.swing.JPanel EditPanelRegion;
    private javax.swing.JPanel EditPanelSostOb;
    private javax.swing.JPanel EditPanelStatusPPS;
    private javax.swing.JPanel EditPanelUchenStep;
    private javax.swing.JPanel EditPanelUchenZvan;
    private javax.swing.JPanel EditPanelUsZachisl;
    private javax.swing.JMenu JmenuSpace;
    private javax.swing.JLabel LabelNameOfGroup;
    private javax.swing.JScrollPane ProgrammScrollPanelField;
    private javax.swing.JScrollPane ScrollPanelPlane;
    protected javax.swing.JTabbedPane TabPaneSprav;
    public javax.swing.JLabel TabStructureTableName;
    private javax.swing.JTabbedPane TabStudentEditTab;
    public javax.swing.JTextField TabStudentTableName;
    private javax.swing.JTable Table_Departments;
    private javax.swing.JTable Table_Groups;
    private javax.swing.JTable Table_PlaneOfGroup;
    private javax.swing.JTable Table_State;
    private javax.swing.JTable Table_Students;
    private javax.swing.JTable Table_UchPlaneStudent;
    private javax.swing.JButton addButtonPlane;
    protected javax.swing.JButton addGroupButton;
    private javax.swing.JButton applyButtonPlane;
    protected javax.swing.JButton attachStudentImage;
    private com.toedter.calendar.JDateChooser calendarBirthStudent;
    protected javax.swing.JButton cancelGroupButton;
    private javax.swing.JComboBox chooserCounrtyStudent;
    private javax.swing.JComboBox chooserNationalityStudent;
    private javax.swing.JComboBox chooserRegionStudent;
    private javax.swing.JComboBox chooserSemestrStudent;
    private javax.swing.JComboBox chooserSexStudent;
    private javax.swing.JComboBox chooserSostStudent;
    private com.toedter.calendar.JDateChooser chooserStudUchPlDataSdachi;
    private javax.swing.JComboBox chooserUsloviyaZachisleniya1;
    private com.toedter.calendar.JDateChooser choserDateOfPrikazZachisl;
    private javax.swing.JComboBox comboSemestrGroup;
    private javax.swing.JComboBox comboTeacherEditGroup;
    private com.toedter.calendar.JDateChooser dateFieldDateofbirth;
    private com.toedter.calendar.JDateChooser dateOfSostStudent;
    private com.toedter.calendar.JDateChooser dateVidachDiplBfa;
    private javax.swing.JMenu date_txt;
    private com.toedter.calendar.JDateChooser dchooserVydachiDoc;
    private com.toedter.calendar.JDateChooser dchooserVydachiDoc1;
    private javax.swing.JButton deleteButtonPlane;
    protected javax.swing.JButton deleteGroupButton;
    private javax.swing.JDesktopPane descPaneStudentPhoto;
    private javax.swing.JLabel downButtonPanelText;
    private javax.swing.JPanel downPanelGroups;
    private javax.swing.JPanel downPanelPlane;
    private javax.swing.JTabbedPane downPanelStructure;
    private javax.swing.JPanel downPlaneButtonPanel;
    private javax.swing.JTextField edPanIdField;
    private javax.swing.JLabel edPanIdLabel;
    private javax.swing.JTextField edPanNameField;
    private javax.swing.JLabel edPanNameLabel;
    private javax.swing.JButton editButtonPlane;
    private javax.swing.JPanel editDepPanel;
    protected javax.swing.JButton editGroupButton;
    private javax.swing.JPanel editGroupPanel;
    private javax.swing.JPanel editPanelPlane;
    protected javax.swing.JPanel editUchPlgPanel;
    private javax.swing.JTextArea fieldAdditionalStudent1;
    private javax.swing.JTextArea fieldAdditionalStudent2;
    private javax.swing.JTextField fieldAttestatNumberStudent;
    private javax.swing.JTextField fieldAttestatSeriyaStudent;
    private javax.swing.JTextField fieldCodeIdDep;
    private javax.swing.JTextField fieldCountryStudent;
    private javax.swing.JTextField fieldDepIdGroup;
    private javax.swing.JTextField fieldDiplBfaNumberStudent;
    private javax.swing.JTextField fieldDiplBfaSeiyaStudent;
    private javax.swing.JTextField fieldDiplomNumberStudent;
    private javax.swing.JTextField fieldDiplomSeriyaStudent;
    private javax.swing.JTextField fieldEmailStudent;
    private javax.swing.JTextField fieldFamiliyaStudent;
    private javax.swing.JTextField fieldFromaobuchGroup;
    private javax.swing.JTextField fieldHeaderDepE;
    public javax.swing.JTextField fieldIdStudent;
    private javax.swing.JTextField fieldLaborantDepE;
    private javax.swing.JTextField fieldMaileDepE;
    private javax.swing.JTextField fieldMaterGroup;
    private javax.swing.JTextField fieldMestoVydachiDoc;
    private javax.swing.JTextField fieldNameDepE;
    private javax.swing.JTextField fieldNameGroup;
    private javax.swing.JTextField fieldNameStudent;
    private javax.swing.JTextField fieldNaprGroup;
    private javax.swing.JTextField fieldNationalityStudent;
    private javax.swing.JTextField fieldNumOfPrikazZachisl;
    private javax.swing.JTextField fieldOtchestvoStudent;
    private javax.swing.JTextField fieldPasportNumberStudent1;
    private javax.swing.JTextField fieldPasportNumberStudent2;
    private javax.swing.JTextField fieldPasportSeriyaStudent;
    private javax.swing.JTextField fieldPhoneDepE;
    private javax.swing.JTextField fieldPhoneStudent;
    private javax.swing.JTextField fieldProjivanieStudent;
    private javax.swing.JTextField fieldPropiskaStudent;
    private javax.swing.JTextField fieldRegionStudent1;
    private javax.swing.JTextField fieldSemestrGroup;
    private javax.swing.JTextField fieldSemestrStudent;
    private javax.swing.JTextField fieldSexStudent;
    private javax.swing.JTextField fieldSostObuchStudent;
    private javax.swing.JTextField fieldSumOfContract;
    private javax.swing.JTextField fieldUsloviyaZachisleniya;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenuUserFIO;
    public javax.swing.JMenu jMenuUserLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    protected javax.swing.JTabbedPane jTabSearch;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JMenuItem jmenuExit;
    protected javax.swing.JMenuItem jmenuSettings;
    private javax.swing.JMenuItem journalBallovPrint;
    private javax.swing.JPopupMenu.Separator journalBallovPrintSep;
    private javax.swing.JLabel labelCodeIdDepE;
    private javax.swing.JLabel labelHeaderDepE;
    private javax.swing.JLabel labelLaborantDepE;
    private javax.swing.JLabel labelMaileDepE;
    private javax.swing.JLabel labelNameDepE;
    private javax.swing.JLabel labelNomberOfBegunok;
    private javax.swing.JLabel labelPhoneDepE;
    private javax.swing.JTextField logAddLoginLabel;
    private com.toedter.calendar.JDateChooser logDateLabel;
    private javax.swing.JLabel logEditDetails;
    private javax.swing.JTextField logEditUserField;
    private com.toedter.calendar.JDateChooser logLastEditDate;
    protected javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JMenu menu3nk;
    private javax.swing.JMenu menuService;
    private javax.swing.JMenu menuSettings;
    private javax.swing.JTextField nameOfTeacher;
    private javax.swing.JCheckBox numberBegunokChek;
    private javax.swing.JTextField numberOfBegunok;
    private javax.swing.JPanel panelSprStatusPPS;
    private javax.swing.JPanel panelStateSearch;
    private javax.swing.JPanel panelStudentSearch;
    private javax.swing.JTextField path;
    private javax.swing.JComboBox planeComboBoxControl;
    private javax.swing.JComboBox planeComboBoxDisciplina;
    private javax.swing.JComboBox planeComboBoxPrepodavatel;
    private javax.swing.JComboBox planeComboBoxSemestr;
    private javax.swing.JTextField planeTextFieldChasy;
    private javax.swing.JTextField planeTextFieldControl;
    private javax.swing.JTextField planeTextFieldCredity1;
    private javax.swing.JTextField planeTextFieldDisciplina1;
    private javax.swing.JTextField planeTextFieldSemestr;
    private javax.swing.JLabel planeTextLabelChasy;
    private javax.swing.JLabel planeTextLabelControl;
    private javax.swing.JLabel planeTextLabelCredity;
    private javax.swing.JLabel planeTextLabelDisciplina;
    private javax.swing.JLabel planeTextLabelPrepodavatel;
    private javax.swing.JLabel planeTextLabelSemestr;
    private javax.swing.JMenuItem printEkzamList;
    private javax.swing.JPopupMenu.Separator printEkzamListSep;
    private javax.swing.JMenuItem printGosAttest;
    private javax.swing.JMenuItem printJournalDvij;
    private javax.swing.JPopupMenu.Separator printJournalDvijSep;
    private javax.swing.JMenuItem printSpisokGroup;
    private javax.swing.JPopupMenu.Separator printSpisokGroupSep;
    private javax.swing.JMenuItem printSpravka;
    private javax.swing.JPopupMenu.Separator printSpravkaSep;
    private javax.swing.JMenuItem printUchKartochka;
    private javax.swing.JPopupMenu.Separator printUchKartochkaSep;
    private javax.swing.JMenuItem printUchPlanStud;
    private javax.swing.JPopupMenu.Separator printUchPlanStudSep;
    private javax.swing.JMenuItem printVedTekControl;
    private javax.swing.JPopupMenu.Separator printVedTekControlSep;
    private javax.swing.JMenu print_Service;
    protected javax.swing.JButton programmAddButton;
    protected javax.swing.JButton programmApplyButton;
    protected javax.swing.JButton programmDeleteButton;
    protected javax.swing.JButton programmEditButton;
    protected javax.swing.JButton raznoskaButton;
    private javax.swing.JButton raznoskaButtonPlane;
    private javax.swing.JLabel raznoskaLog;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton searchButton1;
    private javax.swing.JTable searchStateResult;
    private javax.swing.JTextField searchStateTextField;
    private javax.swing.JTable searchStudentTable;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JButton sprAddCoun;
    private javax.swing.JButton sprAddDisciplines;
    private javax.swing.JButton sprAddDoljnost;
    private javax.swing.JButton sprAddFormob;
    private javax.swing.JButton sprAddNation;
    private javax.swing.JButton sprAddRegion;
    private javax.swing.JButton sprAddSostOb;
    private javax.swing.JButton sprAddStatusPPS;
    private javax.swing.JButton sprAddUchenStep;
    private javax.swing.JButton sprAddUchenZvan;
    private javax.swing.JButton sprAddUsZachisl;
    private javax.swing.JButton sprDeleteCoun;
    private javax.swing.JButton sprDeleteDisciplines;
    private javax.swing.JButton sprDeleteDoljnost;
    private javax.swing.JButton sprDeleteFormob;
    private javax.swing.JButton sprDeleteNation;
    private javax.swing.JButton sprDeleteRegion;
    private javax.swing.JButton sprDeleteSostOb;
    private javax.swing.JButton sprDeleteStatusPPS;
    private javax.swing.JButton sprDeleteUchenStep;
    private javax.swing.JButton sprDeleteUchenZvan;
    private javax.swing.JButton sprDeleteUsZachisl;
    private javax.swing.JButton sprEditCoun;
    private javax.swing.JButton sprEditDisciplines;
    private javax.swing.JButton sprEditDoljnost;
    private javax.swing.JButton sprEditFormob;
    private javax.swing.JButton sprEditNation;
    private javax.swing.JButton sprEditRegion;
    private javax.swing.JButton sprEditSostOb;
    private javax.swing.JButton sprEditStatusPPS;
    private javax.swing.JButton sprEditUchenStep;
    private javax.swing.JButton sprEditUchenZvan;
    private javax.swing.JButton sprEditUsZachisl;
    protected javax.swing.JButton studPerevGroupButton;
    private javax.swing.JButton studUchPlAddButton;
    private javax.swing.JButton studUchPlApplyButton;
    private javax.swing.JTextField studUchPlBallyField;
    private javax.swing.JLabel studUchPlBallyText;
    private javax.swing.JButton studUchPlCancelButton;
    private javax.swing.JTextField studUchPlChasyField;
    private javax.swing.JLabel studUchPlChasyText;
    private javax.swing.JComboBox studUchPlControComboB;
    private javax.swing.JTextField studUchPlControTextF;
    private javax.swing.JLabel studUchPlControlText;
    private javax.swing.JTextField studUchPlCredityField;
    private javax.swing.JLabel studUchPlDataSdachiText;
    private javax.swing.JButton studUchPlDeleteButton;
    private javax.swing.JComboBox studUchPlDisciplinaComboB1;
    private javax.swing.JTextField studUchPlDisciplinaField;
    private javax.swing.JLabel studUchPlDisciplinaText;
    private javax.swing.JButton studUchPlEditButton;
    private javax.swing.JLabel studUchPlKreditytrText;
    private javax.swing.JTextField studUchPlOcenkaField;
    private javax.swing.JLabel studUchPlOcenkaText;
    private javax.swing.JComboBox studUchPlSemestrComboB;
    private javax.swing.JLabel studUchPlSemestrText;
    private javax.swing.JTextField studUchPlSemestrTextF;
    protected javax.swing.JButton studentAddButton1;
    protected javax.swing.JButton studentCancelButton;
    protected javax.swing.JButton studentDeleteButton;
    protected javax.swing.JButton studentEditButton1;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel tabSearch;
    private javax.swing.JPanel tabSpravochnik;
    private javax.swing.JPanel tabState;
    private javax.swing.JTabbedPane tabStateEdit;
    private javax.swing.JPanel tabStateEditPanel;
    private javax.swing.JLabel tabStateLabelText;
    private javax.swing.JPanel tabStateMenuPanel;
    private javax.swing.JScrollPane tabStateScrollPanel;
    private javax.swing.JPanel tabStructure;
    private javax.swing.JPanel tabStructureButtonPanel;
    protected javax.swing.JPanel tabStructureButtonPanel1;
    protected javax.swing.JPanel tabStudUchPlanButtons;
    private javax.swing.JPanel tabStudUchPlanPanelEdit;
    private javax.swing.JScrollPane tabStudUchPlanScroll;
    private javax.swing.JPanel tabStudentEditUchebKartochka;
    private javax.swing.JPanel tabStudentUchebnyiPlan;
    private javax.swing.JPanel tabStudents;
    private javax.swing.JScrollPane tabStudentsScrollPane;
    private javax.swing.JTable tableCountry;
    private javax.swing.JTable tableDisciplines;
    private javax.swing.JTable tableDoljnost;
    private javax.swing.JTable tableFormob;
    private javax.swing.JTable tableNationality;
    private javax.swing.JTable tableRegion;
    private javax.swing.JTable tableSostOb;
    private javax.swing.JTable tableStatusPPS;
    private javax.swing.JTable tableUchenStep;
    private javax.swing.JTable tableUchenZvan;
    private javax.swing.JTable tableUsZachisl;
    private javax.swing.JLabel textAttestatStudent;
    private javax.swing.JLabel textBirthStudent;
    private javax.swing.JLabel textCodeOfPodrazdel;
    private javax.swing.JLabel textEmailStudent;
    private javax.swing.JTextArea textFieldAdditional;
    private javax.swing.JTextField textFieldAdressState;
    private javax.swing.JTextField textFieldCodeOfPodrazdel;
    private javax.swing.JTextField textFieldCodeState;
    private javax.swing.JLabel textFieldDoljnost;
    private javax.swing.JTextField textFieldEmail;
    private javax.swing.JTextField textFieldFamiliya;
    private javax.swing.JTextField textFieldName;
    private javax.swing.JTextField textFieldNationality;
    private javax.swing.JTextField textFieldOtchestvo;
    private javax.swing.JTextField textFieldSexTeacher;
    private javax.swing.JTextField textFieldStatus;
    private javax.swing.JTextField textFieldTelefon;
    private javax.swing.JTextField textFieldUchebnNagruzka1;
    private javax.swing.JTextField textFieldUchenStepen;
    private javax.swing.JTextField textFieldUchenZvanie;
    private javax.swing.JLabel textFioStudent;
    private javax.swing.JLabel textIdStudent;
    private javax.swing.JLabel textLabelAdditional;
    private javax.swing.JLabel textLabelAdressState;
    private javax.swing.JLabel textLabelCodeState;
    private javax.swing.JLabel textLabelDateofbirth;
    private javax.swing.JLabel textLabelEmail;
    private javax.swing.JLabel textLabelFamiliya;
    private javax.swing.JLabel textLabelName;
    private javax.swing.JLabel textLabelNationality;
    private javax.swing.JLabel textLabelOtchestvo;
    private javax.swing.JLabel textLabelSex;
    private javax.swing.JLabel textLabelStateQuit;
    private javax.swing.JLabel textLabelStatus;
    private javax.swing.JLabel textLabelTelefon;
    private javax.swing.JLabel textLabelUchebnNagruzka;
    private javax.swing.JLabel textLabelUchenStepen;
    private javax.swing.JLabel textLabelUchenZvanie;
    private javax.swing.JLabel textNationalityStudent;
    private javax.swing.JLabel textPaneStudentPhoto;
    private javax.swing.JLabel textPasportNumberStudent;
    private javax.swing.JLabel textPasportStudent;
    private javax.swing.JLabel textPhoneStudent;
    private javax.swing.JLabel textProjivanieStudent;
    private javax.swing.JLabel textPropiskaStudent;
    private javax.swing.JLabel textRegionStudent;
    private javax.swing.JLabel textSexStudent;
    private javax.swing.JLabel uchplstudID;
    protected javax.swing.JButton updateTableGroups;
    private javax.swing.JButton updtCountry;
    private javax.swing.JButton updtDisc;
    private javax.swing.JButton updtDolj;
    private javax.swing.JButton updtFromob;
    private javax.swing.JButton updtNaty;
    private javax.swing.JButton updtRegn;
    private javax.swing.JButton updtSostob;
    private javax.swing.JButton updtStPPS;
    private javax.swing.JButton updtUslZach;
    private javax.swing.JButton updtUstep;
    private javax.swing.JButton updtUzvan;
    private javax.swing.JButton workWithVedomButton;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

        // FileInputStream fis;    
    private ImageIcon format = null;
    
        // String filename;   
    
    String filename = null;
    int s=0;
    byte[] student_image=null;
    

}
