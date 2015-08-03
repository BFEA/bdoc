
package bdoc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import javax.swing.*;


public class AddStudent extends javax.swing.JFrame {
    
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            
            // FileInputStream fis;    
            private ImageIcon format = null;

            // String filename; 
            String filename = null;
            int s=0;
            byte[] student_image=null;
            
    public AddStudent() {
        initComponents();
        conn = dbconnector.ConnectorDb();
        FillCombo();
    
    }
    
    public void addGrText(String grNa){
        fieldGroupName.setText(grNa);
    }
    
    private void FillCombo(){
        
        
        try{
            String sql= "SELECT * FROM spr_nationality ORDER BY nationality_code ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String name_nationality = rs.getString("nationality_name");
                chooserNationalityStudent1.addItem(name_nationality);
               
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
        
        
         try{
            String sql= "SELECT * FROM spr_country ORDER BY cou_cod asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String countr_name = rs.getString("cou_name");
                chooserCounrtyStudent1.addItem(countr_name);
            }
            
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_region ORDER BY region_code asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String regiona_name = rs.getString("region_name");
                chooserRegionStudent1.addItem(regiona_name);
              }
           
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
                 
         try{
            String sql= "SELECT * FROM spr_sostobuchen ORDER BY sostobuchen_code asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String sost_name = rs.getString("sostobuchen_name");
                chooserSostStudent1.addItem(sost_name);
              }
            
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
         
         try{
            String sql= "SELECT * FROM spr_zachislenie ORDER BY zachislenie_code asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String zachisl_name = rs.getString("zachislenie_name");
                chooserUsloviyaZachisleniya.addItem(zachisl_name);
              }
            
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
         
        finally {
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
    
    private void addStudent(){
         
        try {
            
            String sql = "INSERT INTO student ("
                    + "student_id,"             //1  fieldIdStudent
                    + "student_lastname, "      //2  fieldFamiliyaStudent
                    + "student_name, "          //3  fieldNameStudent
                    + "student_middlename, "    //4  fieldOtchestvoStudent
                    + "student_birthday,"       //5  calendarBirthStudent
                    + "nationality_code, "      //6  fieldNationalityStudent
                    + "cou_cod,"                //7  fieldCountryStudent
                    + "region_code,"            //8  fieldRegionStudent1
                    + "student_sex, "           //9  fieldSexStudent
                    + "student_propiska, "      //10  fieldPropiskaStudent
                    + "student_addr, "          //11 fieldProjivanieStudent
                    + "student_phone, "         //12 fieldPhoneStudent
                    + "student_email, "         //13 fieldEmailStudent
                    + "student_passeries,"      //14 fieldPasportSeriyaStudent
                    + "student_passnumber, "    //15 fieldPasportNumberStudent1
                    + "student_attseries, "     //16 fieldAttestatSeriyaStudent
                    + "student_attnumber, "     //17 fieldAttestatNumberStudent
                    + "student_dipseries, "     //18 fieldDiplomSeriyaStudent
                    + "student_dipnumber,"      //19 fieldDiplomNumberStudent
                    + "student_docdate, "       //20 dchooserVydachiDoc
                    + "student_docfrom,"        //21 fieldMestoVydachiDoc
                    + "student_prikaz, "        //22 fieldNumOfPrikazZachisl
                    + "student_prikazdate,"     //23 choserDateOfPrikazZachisl
                    + "zachislenie_code, "      //24 fieldUsloviyaZachisleniya
                    + "student_semestr,"        //25 fieldSemestrStudent
                    + "student_sumk, "          //26 fieldSumOfContract.getText
                    + "student_dsera,"          //27 fieldDiplBfaSeiyaStudent
                    + "student_duma, "          //28 fieldDiplBfaNumberStudent
                    + "sostobuchen_code, "      //29 fieldSostObuchStudent
                    + "group_code) "            //30 fieldGroupName
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
            pst = conn.prepareStatement(sql);
            
            pst.setString(1,fieldIdStudent.getText());
            pst.setString(2,fieldFamiliyaStudent.getText());
            pst.setString(3,fieldNameStudent.getText());
            pst.setString(4,fieldOtchestvoStudent.getText());
            pst.setString(5,((JTextField)calendarBirthStudent.getDateEditor().getUiComponent()).getText());
            pst.setString(6,fieldNationalityStudent.getText());
            pst.setString(7,fieldCountryStudent.getText());
            pst.setString(8,fieldRegionStudent1.getText());
            pst.setString(9,fieldSexStudent.getText());
            pst.setString(10,fieldPropiskaStudent.getText());
            pst.setString(11,fieldProjivanieStudent.getText());
            pst.setString(12,fieldPhoneStudent.getText());
            pst.setString(13,fieldEmailStudent.getText());
            pst.setString(14,fieldPasportSeriyaStudent.getText());
            pst.setString(15,fieldPasportNumberStudent1.getText());
            pst.setString(16,fieldAttestatSeriyaStudent.getText());
            pst.setString(17,fieldAttestatNumberStudent.getText());
            pst.setString(18,fieldDiplomSeriyaStudent.getText());
            pst.setString(19,fieldDiplomNumberStudent.getText());
            pst.setString(20,((JTextField)dchooserVydachiDoc.getDateEditor().getUiComponent()).getText());
            pst.setString(21,fieldMestoVydachiDoc.getText());
            pst.setString(22,fieldNumOfPrikazZachisl.getText());
            pst.setString(23,((JTextField)choserDateOfPrikazZachisl.getDateEditor().getUiComponent()).getText());
            pst.setString(24,fieldUsloviyaZachisleniya.getText());
            pst.setString(25,fieldSemestrStudent.getText());
            pst.setString(26,fieldSumOfContract.getText());
            pst.setString(27,fieldDiplBfaSeiyaStudent.getText());
            pst.setString(28,fieldDiplBfaNumberStudent.getText());
            pst.setString(29,fieldSostObuchStudent.getText());
            pst.setString(30, fieldGroupName.getText());
            
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Студент успешно добавлен в БД");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void clearAllFields(){
        
            fieldIdStudent.setText("");
            fieldFamiliyaStudent.setText("");
            fieldNameStudent.setText("");
            fieldOtchestvoStudent.setText("");
            calendarBirthStudent.setDate(null);
            fieldNationalityStudent.setText("");
            fieldCountryStudent.setText("");
            fieldRegionStudent1.setText("");
            fieldSexStudent.setText("");
            fieldPropiskaStudent.setText("");
            fieldProjivanieStudent.setText("");
            fieldPhoneStudent.setText("");
            fieldEmailStudent.setText("");
            fieldPasportSeriyaStudent.setText("");
            fieldPasportNumberStudent1.setText("");
            fieldAttestatSeriyaStudent.setText("");
            fieldAttestatNumberStudent.setText("");
            fieldDiplomSeriyaStudent.setText("");
            fieldDiplomNumberStudent.setText("");
            dchooserVydachiDoc.setDate(null);
            fieldMestoVydachiDoc.setText("");
            fieldNumOfPrikazZachisl.setText("");
            choserDateOfPrikazZachisl.setDate(null);
            fieldUsloviyaZachisleniya.setText("");
            fieldSemestrStudent.setText("");
            fieldSumOfContract.setText("");
            fieldDiplBfaSeiyaStudent.setText("");
            fieldDiplBfaNumberStudent.setText("");
            fieldSostObuchStudent.setText("");
            fieldGroupName.setText("");
            
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        tabStudentEditUchebKartochka = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        textFioStudent = new javax.swing.JLabel();
        textRegionStudent = new javax.swing.JLabel();
        fieldRegionStudent1 = new javax.swing.JTextField();
        textIdStudent = new javax.swing.JLabel();
        chooserCounrtyStudent1 = new javax.swing.JComboBox();
        calendarBirthStudent = new com.toedter.calendar.JDateChooser();
        fieldFamiliyaStudent = new javax.swing.JTextField();
        chooserNationalityStudent1 = new javax.swing.JComboBox();
        textNationalityStudent = new javax.swing.JLabel();
        fieldCountryStudent = new javax.swing.JTextField();
        fieldSexStudent = new javax.swing.JTextField();
        fieldIdStudent = new javax.swing.JTextField();
        labelStudentCountry = new javax.swing.JLabel();
        textSexStudent = new javax.swing.JLabel();
        fieldNameStudent = new javax.swing.JTextField();
        chooserRegionStudent1 = new javax.swing.JComboBox();
        fieldOtchestvoStudent = new javax.swing.JTextField();
        chooserSexStudent = new javax.swing.JComboBox();
        fieldNationalityStudent = new javax.swing.JTextField();
        textBirthStudent = new javax.swing.JLabel();
        textGroupName = new javax.swing.JLabel();
        fieldGroupName = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        textAttestatStudent = new javax.swing.JLabel();
        fieldProjivanieStudent = new javax.swing.JTextField();
        textPasportStudent = new javax.swing.JLabel();
        fieldPhoneStudent = new javax.swing.JTextField();
        dchooserVydachiDoc1 = new com.toedter.calendar.JDateChooser();
        labelOrganVydDocument = new javax.swing.JLabel();
        labelDiplom = new javax.swing.JLabel();
        fieldMestoVydachiDoc = new javax.swing.JTextField();
        textPhoneStudent = new javax.swing.JLabel();
        fieldEmailStudent = new javax.swing.JTextField();
        fieldPropiskaStudent = new javax.swing.JTextField();
        textDataVydDoc = new javax.swing.JLabel();
        textPasportNumberStudent = new javax.swing.JLabel();
        fieldAttestatSeriyaStudent = new javax.swing.JTextField();
        textNameAttestat = new javax.swing.JLabel();
        fieldPasportNumberStudent2 = new javax.swing.JTextField();
        fieldPasportNumberStudent1 = new javax.swing.JTextField();
        textMestoVydachiDoc = new javax.swing.JLabel();
        fieldPasportSeriyaStudent = new javax.swing.JTextField();
        textDateVydachi = new javax.swing.JLabel();
        textNomerDiplom = new javax.swing.JLabel();
        textProjivanieStudent = new javax.swing.JLabel();
        textEmailStudent = new javax.swing.JLabel();
        dchooserVydachiDoc = new com.toedter.calendar.JDateChooser();
        fieldAttestatNumberStudent = new javax.swing.JTextField();
        fieldDiplomNumberStudent = new javax.swing.JTextField();
        fieldDiplomSeriyaStudent = new javax.swing.JTextField();
        textPropiskaStudent = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        labelZachislenie = new javax.swing.JLabel();
        dateVidachDiplBfa = new com.toedter.calendar.JDateChooser();
        textFrom = new javax.swing.JLabel();
        chooserUsloviyaZachisleniya = new javax.swing.JComboBox();
        fieldDiplBfaSeiyaStudent = new javax.swing.JTextField();
        chooserSemestrStudent = new javax.swing.JComboBox();
        fieldSostObuchStudent = new javax.swing.JTextField();
        labelSostoyanie = new javax.swing.JLabel();
        labelPrikaz = new javax.swing.JLabel();
        labelSumOfContract = new javax.swing.JLabel();
        labelNomerOfDiplomBFEA = new javax.swing.JLabel();
        fieldSemestrStudent = new javax.swing.JTextField();
        labelUslovZachislenie = new javax.swing.JLabel();
        fieldSumOfContract = new javax.swing.JTextField();
        fieldUsloviyaZachisleniya = new javax.swing.JTextField();
        labelDateOfVydachi = new javax.swing.JLabel();
        fieldDiplBfaNumberStudent = new javax.swing.JTextField();
        labelSemestrOfStudent = new javax.swing.JLabel();
        choserDateOfPrikazZachisl = new com.toedter.calendar.JDateChooser();
        fieldNumOfPrikazZachisl = new javax.swing.JTextField();
        dateOfSostStudent = new com.toedter.calendar.JDateChooser();
        labelDateOfGraduate = new javax.swing.JLabel();
        labelDiplomOfBFEA = new javax.swing.JLabel();
        chooserSostStudent1 = new javax.swing.JComboBox();
        addGroupDown = new javax.swing.JPanel();
        addAddNewGroup = new javax.swing.JButton();
        cancelAddNewGroup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Личная информация о студенте"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textFioStudent.setText("ФИО");
        jPanel1.add(textFioStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        textRegionStudent.setText("Область");
        jPanel1.add(textRegionStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 117, -1, -1));

        fieldRegionStudent1.setEnabled(false);
        jPanel1.add(fieldRegionStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 117, 40, -1));

        textIdStudent.setText("ID");
        jPanel1.add(textIdStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        chooserCounrtyStudent1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserCounrtyStudent1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanel1.add(chooserCounrtyStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 117, 130, -1));

        calendarBirthStudent.setDateFormatString("yyyy-MM-dd");
        jPanel1.add(calendarBirthStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 87, 160, -1));
        jPanel1.add(fieldFamiliyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 230, -1));

        chooserNationalityStudent1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserNationalityStudent1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanel1.add(chooserNationalityStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 87, 160, -1));

        textNationalityStudent.setText("Национальность");
        jPanel1.add(textNationalityStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 87, 100, 20));

        fieldCountryStudent.setEnabled(false);
        jPanel1.add(fieldCountryStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 117, 50, -1));

        fieldSexStudent.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, chooserSexStudent, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSexStudent, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel1.add(fieldSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(738, 117, 28, -1));
        jPanel1.add(fieldIdStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 91, -1));

        labelStudentCountry.setText("Страна");
        jPanel1.add(labelStudentCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 119, -1, -1));

        textSexStudent.setText("Пол");
        jPanel1.add(textSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(698, 111, -1, 30));
        jPanel1.add(fieldNameStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 50, 230, -1));

        chooserRegionStudent1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserRegionStudent1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanel1.add(chooserRegionStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 117, 160, -1));
        jPanel1.add(fieldOtchestvoStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, 200, -1));

        chooserSexStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "М", "Ж" }));
        jPanel1.add(chooserSexStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(778, 117, 50, -1));

        fieldNationalityStudent.setDoubleBuffered(true);
        fieldNationalityStudent.setEnabled(false);
        jPanel1.add(fieldNationalityStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(428, 87, 40, -1));

        textBirthStudent.setText("Дата рождения");
        jPanel1.add(textBirthStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 91, -1, -1));

        textGroupName.setText("Группа студента");
        jPanel1.add(textGroupName, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 100, -1));

        fieldGroupName.setEditable(false);
        jPanel1.add(fieldGroupName, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, 170, -1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Информация о документах студента"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        textAttestatStudent.setText("Аттестат");
        jPanel2.add(textAttestatStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 147, -1, -1));
        jPanel2.add(fieldProjivanieStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 57, 490, -1));

        textPasportStudent.setText("Паспорт");
        jPanel2.add(textPasportStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 117, -1, -1));
        jPanel2.add(fieldPhoneStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 87, 153, -1));

        dchooserVydachiDoc1.setDateFormatString("yyyy-MM-dd");
        jPanel2.add(dchooserVydachiDoc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 177, 150, -1));

        labelOrganVydDocument.setText("Орган выдавший документ");
        jPanel2.add(labelOrganVydDocument, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 117, 160, -1));

        labelDiplom.setText("Диплом");
        jPanel2.add(labelDiplom, new org.netbeans.lib.awtextra.AbsoluteConstraints(418, 147, -1, -1));
        jPanel2.add(fieldMestoVydachiDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 177, 350, -1));

        textPhoneStudent.setText("Телефон");
        jPanel2.add(textPhoneStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 87, -1, -1));
        jPanel2.add(fieldEmailStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(358, 87, 250, -1));
        jPanel2.add(fieldPropiskaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 27, 490, -1));

        textDataVydDoc.setText("Дата выдачи");
        jPanel2.add(textDataVydDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 177, -1, -1));

        textPasportNumberStudent.setText("№");
        jPanel2.add(textPasportNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 117, -1, -1));
        jPanel2.add(fieldAttestatSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 147, 50, -1));

        textNameAttestat.setText("№");
        jPanel2.add(textNameAttestat, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 147, -1, -1));
        jPanel2.add(fieldPasportNumberStudent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 117, 90, -1));
        jPanel2.add(fieldPasportNumberStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 117, 120, -1));

        textMestoVydachiDoc.setText("Место выдачи документа");
        jPanel2.add(textMestoVydachiDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 177, 150, -1));
        jPanel2.add(fieldPasportSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(118, 117, 50, -1));

        textDateVydachi.setText("Дата выдачи");
        jPanel2.add(textDateVydachi, new org.netbeans.lib.awtextra.AbsoluteConstraints(618, 117, 80, -1));

        textNomerDiplom.setText("№");
        jPanel2.add(textNomerDiplom, new org.netbeans.lib.awtextra.AbsoluteConstraints(668, 147, -1, -1));

        textProjivanieStudent.setText("Проживание");
        jPanel2.add(textProjivanieStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 57, -1, -1));

        textEmailStudent.setText("E-Mail");
        jPanel2.add(textEmailStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(318, 87, -1, -1));

        dchooserVydachiDoc.setDateFormatString("yyyy-MM-dd");
        jPanel2.add(dchooserVydachiDoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 117, 120, -1));
        jPanel2.add(fieldAttestatNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(208, 147, 90, -1));
        jPanel2.add(fieldDiplomNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(718, 147, 120, -1));
        jPanel2.add(fieldDiplomSeriyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 147, 120, -1));

        textPropiskaStudent.setText("Прописка");
        jPanel2.add(textPropiskaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 27, -1, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelZachislenie.setText("зачислении");
        jPanel3.add(labelZachislenie, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 25, -1, -1));

        dateVidachDiplBfa.setDateFormatString("yyyy-MM-dd");
        jPanel3.add(dateVidachDiplBfa, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 75, 180, -1));

        textFrom.setText("от");
        jPanel3.add(textFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 15, -1, -1));

        chooserUsloviyaZachisleniya.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserUsloviyaZachisleniyaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanel3.add(chooserUsloviyaZachisleniya, new org.netbeans.lib.awtextra.AbsoluteConstraints(694, 15, 150, -1));
        jPanel3.add(fieldDiplBfaSeiyaStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 75, 70, -1));

        chooserSemestrStudent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        jPanel3.add(chooserSemestrStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 45, 180, -1));
        jPanel3.add(fieldSostObuchStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 105, 70, -1));

        labelSostoyanie.setText("Состояние");
        jPanel3.add(labelSostoyanie, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 105, -1, 20));

        labelPrikaz.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        labelPrikaz.setText("Приказ о ");
        labelPrikaz.setToolTipText("");
        labelPrikaz.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jPanel3.add(labelPrikaz, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 15, -1, -1));

        labelSumOfContract.setText("Сумма контракта");
        jPanel3.add(labelSumOfContract, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, -1, -1));

        labelNomerOfDiplomBFEA.setText("№");
        jPanel3.add(labelNomerOfDiplomBFEA, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 75, -1, -1));

        fieldSemestrStudent.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, chooserSemestrStudent, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSemestrStudent, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jPanel3.add(fieldSemestrStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 45, 70, -1));

        labelUslovZachislenie.setText("Условия зачисления");
        jPanel3.add(labelUslovZachislenie, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 120, -1));
        jPanel3.add(fieldSumOfContract, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 45, 180, -1));
        jPanel3.add(fieldUsloviyaZachisleniya, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 15, 60, -1));

        labelDateOfVydachi.setText("Дата выдачи");
        jPanel3.add(labelDateOfVydachi, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, -1, 10));
        jPanel3.add(fieldDiplBfaNumberStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 75, 180, -1));

        labelSemestrOfStudent.setText("Семестр");
        jPanel3.add(labelSemestrOfStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 55, -1, 10));

        choserDateOfPrikazZachisl.setDateFormatString("yyyy-MM-dd");
        jPanel3.add(choserDateOfPrikazZachisl, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 15, 180, -1));
        jPanel3.add(fieldNumOfPrikazZachisl, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 15, 70, -1));

        dateOfSostStudent.setDateFormatString("yyyy-MM-dd");
        jPanel3.add(dateOfSostStudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(614, 105, 180, -1));

        labelDateOfGraduate.setText("Дата окончания");
        jPanel3.add(labelDateOfGraduate, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 100, -1));

        labelDiplomOfBFEA.setText("Диплом БФЭА");
        jPanel3.add(labelDiplomOfBFEA, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 75, -1, -1));

        chooserSostStudent1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                chooserSostStudent1PopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        jPanel3.add(chooserSostStudent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(224, 105, 180, -1));

        addGroupDown.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addAddNewGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addAddNewGroup.setText("Применить");
        addAddNewGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAddNewGroupActionPerformed(evt);
            }
        });

        cancelAddNewGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelAddNewGroup.setText("    Отмена");
        cancelAddNewGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddNewGroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addGroupDownLayout = new javax.swing.GroupLayout(addGroupDown);
        addGroupDown.setLayout(addGroupDownLayout);
        addGroupDownLayout.setHorizontalGroup(
            addGroupDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addGroupDownLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(addAddNewGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(cancelAddNewGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        addGroupDownLayout.setVerticalGroup(
            addGroupDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addGroupDownLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addGroupDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAddNewGroup)
                    .addComponent(cancelAddNewGroup))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabStudentEditUchebKartochkaLayout = new javax.swing.GroupLayout(tabStudentEditUchebKartochka);
        tabStudentEditUchebKartochka.setLayout(tabStudentEditUchebKartochkaLayout);
        tabStudentEditUchebKartochkaLayout.setHorizontalGroup(
            tabStudentEditUchebKartochkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudentEditUchebKartochkaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabStudentEditUchebKartochkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(tabStudentEditUchebKartochkaLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(addGroupDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        tabStudentEditUchebKartochkaLayout.setVerticalGroup(
            tabStudentEditUchebKartochkaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabStudentEditUchebKartochkaLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addGroupDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 896, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabStudentEditUchebKartochka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tabStudentEditUchebKartochka, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(904, 649));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addAddNewGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAddNewGroupActionPerformed
        addStudent();
        clearAllFields();
    }//GEN-LAST:event_addAddNewGroupActionPerformed

    private void cancelAddNewGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddNewGroupActionPerformed
        dispose();
    }//GEN-LAST:event_cancelAddNewGroupActionPerformed

    private void chooserNationalityStudent1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserNationalityStudent1PopupMenuWillBecomeInvisible
         
           String item = (String) chooserNationalityStudent1.getSelectedItem();
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
    }//GEN-LAST:event_chooserNationalityStudent1PopupMenuWillBecomeInvisible

    private void chooserCounrtyStudent1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserCounrtyStudent1PopupMenuWillBecomeInvisible
           
           String item = (String) chooserCounrtyStudent1.getSelectedItem();
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
    }//GEN-LAST:event_chooserCounrtyStudent1PopupMenuWillBecomeInvisible

    private void chooserRegionStudent1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserRegionStudent1PopupMenuWillBecomeInvisible
     
           String item = (String) chooserRegionStudent1.getSelectedItem();
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
    }//GEN-LAST:event_chooserRegionStudent1PopupMenuWillBecomeInvisible

    private void chooserUsloviyaZachisleniyaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserUsloviyaZachisleniyaPopupMenuWillBecomeInvisible
   
           String item = (String) chooserUsloviyaZachisleniya.getSelectedItem();
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
    }//GEN-LAST:event_chooserUsloviyaZachisleniyaPopupMenuWillBecomeInvisible

    private void chooserSostStudent1PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_chooserSostStudent1PopupMenuWillBecomeInvisible
     
           String item = (String) chooserSostStudent1.getSelectedItem();
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
    }//GEN-LAST:event_chooserSostStudent1PopupMenuWillBecomeInvisible

   
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
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddStudent().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAddNewGroup;
    private javax.swing.JPanel addGroupDown;
    private com.toedter.calendar.JDateChooser calendarBirthStudent;
    private javax.swing.JButton cancelAddNewGroup;
    private javax.swing.JComboBox chooserCounrtyStudent1;
    private javax.swing.JComboBox chooserNationalityStudent1;
    private javax.swing.JComboBox chooserRegionStudent1;
    private javax.swing.JComboBox chooserSemestrStudent;
    private javax.swing.JComboBox chooserSexStudent;
    private javax.swing.JComboBox chooserSostStudent1;
    private javax.swing.JComboBox chooserUsloviyaZachisleniya;
    private com.toedter.calendar.JDateChooser choserDateOfPrikazZachisl;
    private com.toedter.calendar.JDateChooser dateOfSostStudent;
    private com.toedter.calendar.JDateChooser dateVidachDiplBfa;
    private com.toedter.calendar.JDateChooser dchooserVydachiDoc;
    private com.toedter.calendar.JDateChooser dchooserVydachiDoc1;
    private javax.swing.JTextField fieldAttestatNumberStudent;
    private javax.swing.JTextField fieldAttestatSeriyaStudent;
    private javax.swing.JTextField fieldCountryStudent;
    private javax.swing.JTextField fieldDiplBfaNumberStudent;
    private javax.swing.JTextField fieldDiplBfaSeiyaStudent;
    private javax.swing.JTextField fieldDiplomNumberStudent;
    private javax.swing.JTextField fieldDiplomSeriyaStudent;
    private javax.swing.JTextField fieldEmailStudent;
    private javax.swing.JTextField fieldFamiliyaStudent;
    private javax.swing.JTextField fieldGroupName;
    private javax.swing.JTextField fieldIdStudent;
    private javax.swing.JTextField fieldMestoVydachiDoc;
    private javax.swing.JTextField fieldNameStudent;
    private javax.swing.JTextField fieldNationalityStudent;
    private javax.swing.JTextField fieldNumOfPrikazZachisl;
    private javax.swing.JTextField fieldOtchestvoStudent;
    private javax.swing.JTextField fieldPasportNumberStudent1;
    private javax.swing.JTextField fieldPasportNumberStudent2;
    private javax.swing.JTextField fieldPasportSeriyaStudent;
    private javax.swing.JTextField fieldPhoneStudent;
    private javax.swing.JTextField fieldProjivanieStudent;
    private javax.swing.JTextField fieldPropiskaStudent;
    private javax.swing.JTextField fieldRegionStudent1;
    private javax.swing.JTextField fieldSemestrStudent;
    private javax.swing.JTextField fieldSexStudent;
    private javax.swing.JTextField fieldSostObuchStudent;
    private javax.swing.JTextField fieldSumOfContract;
    private javax.swing.JTextField fieldUsloviyaZachisleniya;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelDateOfGraduate;
    private javax.swing.JLabel labelDateOfVydachi;
    private javax.swing.JLabel labelDiplom;
    private javax.swing.JLabel labelDiplomOfBFEA;
    private javax.swing.JLabel labelNomerOfDiplomBFEA;
    private javax.swing.JLabel labelOrganVydDocument;
    private javax.swing.JLabel labelPrikaz;
    private javax.swing.JLabel labelSemestrOfStudent;
    private javax.swing.JLabel labelSostoyanie;
    private javax.swing.JLabel labelStudentCountry;
    private javax.swing.JLabel labelSumOfContract;
    private javax.swing.JLabel labelUslovZachislenie;
    private javax.swing.JLabel labelZachislenie;
    private javax.swing.JPanel tabStudentEditUchebKartochka;
    private javax.swing.JLabel textAttestatStudent;
    private javax.swing.JLabel textBirthStudent;
    private javax.swing.JLabel textDataVydDoc;
    private javax.swing.JLabel textDateVydachi;
    private javax.swing.JLabel textEmailStudent;
    private javax.swing.JLabel textFioStudent;
    private javax.swing.JLabel textFrom;
    private javax.swing.JLabel textGroupName;
    private javax.swing.JLabel textIdStudent;
    private javax.swing.JLabel textMestoVydachiDoc;
    private javax.swing.JLabel textNameAttestat;
    private javax.swing.JLabel textNationalityStudent;
    private javax.swing.JLabel textNomerDiplom;
    private javax.swing.JLabel textPasportNumberStudent;
    private javax.swing.JLabel textPasportStudent;
    private javax.swing.JLabel textPhoneStudent;
    private javax.swing.JLabel textProjivanieStudent;
    private javax.swing.JLabel textPropiskaStudent;
    private javax.swing.JLabel textRegionStudent;
    private javax.swing.JLabel textSexStudent;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
