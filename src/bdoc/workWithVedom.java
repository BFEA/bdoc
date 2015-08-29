
package bdoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class workWithVedom extends javax.swing.JFrame {
    
            Connection conn;
            ResultSet rs;
            PreparedStatement pst;
            
      
    public workWithVedom() {
        initComponents();
        conn = dbconnector.getConnection();
        FillComboDiscip();
        isSemectrActive();
    }
    
    private void isSemectrActive(){
         try{       
        String sql = "SELECT * FROM spr_settings WHERE A_ID=1";
             
            pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                 if(rs.next()){
                 String begunok= rs.getString("A_VED");
                 if(begunok.equals("1")){
                     editApplyButton.setEnabled(true);
                 }else{
                     editApplyButton.setEnabled(false);
                 }
                 }
        }catch(SQLException sqlex){
            sqlex.printStackTrace();
            JOptionPane.showMessageDialog(null,"isSemActive "+sqlex);
        }
    }
    
    private void showMarkAndBegunokNumber(String disc_code,String studId){

        String sql = "SELECT " +
                        "uchpls_begunok, uchpls_nuberbegunok, uchpls_numbervedom,uchplans_sem,uchPls_date " +
                     "FROM" +
                        " uchplanstudents " +
                     "WHERE student_id='"+studId+"' AND disc_code='"+disc_code+"'";

        try{
             pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                    if(rs.next()){
                        
                             Integer begunok= rs.getInt("uchpls_begunok");
                             String num_vedom= rs.getString("uchpls_numbervedom");
                             String date = rs.getString("uchPls_date");
                             ((JTextField)dateControlChooser.getDateEditor().getUiComponent()).setText(date);
                             if(num_vedom.equals("null")){
                             
                             }else{
                             numberVedomostiGruppyField.setText(num_vedom);    
                             }
                             if(begunok==1){
                                 begunokCheck.setSelected(true);
                             }else{
                                begunokCheck.setSelected(false);
                             }

                         }
        }catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null,"buttonFormirovatMouseReleased "+sqlex);
        }
    }
    
    private void buttonFormirovat(){
        
        String sql = "SELECT " +
                    "     student_id AS 'ID студента'," +
                    "     uchplans_sem AS 'Семестр'," +
                    "     disc_code AS 'Дисциплина'," +
                    "     uchpls_clock AS 'Часы'," +
                    "     uchpls_credit AS 'Кредиты'," +
                    "     uchpls_ball AS 'Баллы'," +
                    "     uchpls_ocenka AS 'Оценка' ," +
                    "     uchpls_controlb AS 'Контроль'" +
                    "FROM " +
                    "     uchplanstudents " +
                    "WHERE group_code=? AND disc_code=? AND uchplans_sem=?";
        try{
            String semestr = (String) grSemField.getSelectedItem();
            
            pst=conn.prepareStatement(sql);
            pst.setString(1, groupNameField.getText());
            pst.setString(2, codeDiscField.getText());
            pst.setString(3, semestr);
            rs=pst.executeQuery();
           
                disciplinesTable.setModel(DbUtils.resultSetToTableModel(rs));
                disciplinesTable.setSelectionMode(1);
//                disciplinesTableMouseReleased(null);
                studLastName.setText("");
                clearFields();

        }catch(SQLException sqlex){
            sqlex.printStackTrace();
            JOptionPane.showMessageDialog(null,"buttonFormirovatMouseReleased "+sqlex);
        }
        
    }
    
    public void FillComboDiscip(){
        
        dateControlChooser.setDate(new Date());
        try{
            String sql= "SELECT * FROM spr_discipline ORDER BY disc_name asc";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                String disc_name = rs.getString("disc_name");
                comboDiscVed.addItem(disc_name);
            }
            
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }        
    }
    
    public void FillFields(String grName,String grSem){
        groupNameField.setText(grName);
        grSemField.setSelectedItem(grSem);
        
    }
    
    private void ItemChange(String item){

        try{     
           String sql = "SELECT disc_code FROM spr_discipline WHERE disc_name = '"+item+"'";
               pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                 while(rs.next()){
                 String add1 = rs.getString("disc_code");
                        codeDiscField.setText(add1);
                 }
           }catch(SQLException sqlex){
               JOptionPane.showMessageDialog(null, "comboDiscVedItemStateChanged "+sqlex);
       }
    }
    
    private void editMark(String studId, 
                            String discCode,
                                Integer ball,
                                    String vedomNumber ){
        
        int row = disciplinesTable.getSelectedRow();
        String controlType=(disciplinesTable.getValueAt(row, 7).toString());  
        
        try{
            String sql = "UPDATE "
                            + "uchplanstudents "
                        + "SET "
                        +"uchpls_ball='"+ball+"',"
                        +"uchpls_numbervedom='"+vedomNumber+"'"
                        +" WHERE "
                        + "student_id='"+studId
                        +"' AND "
                        + "disc_code='"+discCode+"'";
        
            pst = conn.prepareStatement(sql);
            pst.execute();
            if(controlType.equalsIgnoreCase("Курсовая")){
                ocenkaStringPraktikaIKursovaya(ball);
            }else if(controlType.equalsIgnoreCase("Практика")){
                ocenkaStringPraktikaIKursovaya(ball);
            }else{
                ocenkaStringEkzamenIZachet(ball);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void editMarkWithoutBegunok(String studId,
                                    String discCode,
                                     Integer ball,
                                       String vedomNumber){
        
        int row = disciplinesTable.getSelectedRow();
        String controlType=(disciplinesTable.getValueAt(row, 7).toString());
        String semestr = grSemField.getSelectedItem().toString();
        String dataSdachiPredmeta = ((JTextField)dateControlChooser.getDateEditor().getUiComponent()).getText();
        Date today = new Date();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDate.format(today);
        mainBdoc mb = new mainBdoc();
        String editorLoginName = mb.jMenuUserLogin.getText();
        
        try{
            String sql = "UPDATE uchplanstudents SET "
                    + "uchpls_date='"+dataSdachiPredmeta+"', "
                    + "uchpls_edit='"+editorLoginName+"', "
                    + "uchpls_dateOper='"+currentDate+"', "
                    + "uchpls_numbervedom='"+vedomNumber+"' "
                    + "WHERE "
                    + "student_id='"+studId+"' "
                    + "AND "
                    + "disc_code='"+discCode+"' "
                    + "AND "
                    + "uchplans_sem='"+semestr+"'";
        
            pst = conn.prepareStatement(sql);
            pst.execute();
            if(controlType.equals("Курсовая")){
                ocenkaStringPraktikaIKursovaya(ball);
            }else if(controlType.equals("Практика")){
                ocenkaStringPraktikaIKursovaya(ball);
            }else{
                ocenkaStringEkzamenIZachet(ball);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void clickTable(String studentId, String discCode, int row ){

        String sql = "SELECT * FROM student WHERE student_id=?";
        String ballOfStudent = (disciplinesTable.getValueAt(row, 5).toString());  
        
        try{
             pst = conn.prepareStatement(sql);
               pst.setString(1,studentId);
                 rs = pst.executeQuery();
                 if(rs.next()){
                     String l_name= rs.getString("student_lastname");
                     String name= rs.getString("student_name");
                     String m_name= rs.getString("student_middlename");
                     studLastName.setText(l_name+" "+name+" "+m_name);
                     ballTextField.setText(ballOfStudent);
                     showMarkAndBegunokNumber(discCode,studentId);
                 }
        }catch(SQLException sqlex){
            JOptionPane.showMessageDialog(null,"disciplinesTableMouseReleasedActionPerformed "+sqlex);
        }
    }
    
    private void clearFields(){
        ballTextField.setText("");
//        begunokNumberlTextField.setText("");
        begunokCheck.setSelected(false);
   }

    private void MarkCanEdit(String ocenka){
        String sql = "SELECT * FROM spr_settings WHERE A_ID=1";
             try{
        pst = conn.prepareStatement(sql);
                 rs = pst.executeQuery();
                 if(rs.next()){
                 String begunok= rs.getString("A_ID");
                 if(begunok.equals("1")){
                     editApplyButton.setEnabled(true);
                 }else{
                     editApplyButton.setEnabled(false);
                 }
                 }
             }catch(SQLException sqlex){
                 
             }
    }
    
    private void fillOcenka(String markText, Integer mark){
        
        int row = disciplinesTable.getSelectedRow();
        String studId=(disciplinesTable.getValueAt(row, 0).toString());
        String discCode=(disciplinesTable.getValueAt(row, 2).toString());
        String semestr = (disciplinesTable.getValueAt(row, 1).toString());
        String control_type = (disciplinesTable.getValueAt(row, 7).toString());
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
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ocenkaStringEkzamenIZachet(Integer mark){

        int row = disciplinesTable.getSelectedRow();
        String control=(disciplinesTable.getValueAt(row, 7).toString());
        String discFizk = (String) comboDiscVed.getSelectedItem();
        
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
                     Integer markAfterDivide = 0;
                     
                     if(control.equalsIgnoreCase("Зачет")&& discFizk.equalsIgnoreCase("Физкультура")){
                         if(mark>=soLow && mark<=excelTop){
                             markText = "Зачтено";
                             fillOcenka(markText, mark);
                         }else {
                             markText = "Не зачтено";
                             fillOcenka(markText, mark);
                         }
                     }else{
                         
                         if(control.equalsIgnoreCase("Экзамен")){
                             
                            if(mark>=110 && mark<=200){
                                markAfterDivide = mark/2;
                                if(excelTop>=markAfterDivide && markAfterDivide>=excelLow){
                                    markText = "Отлично";
                                    fillOcenka(markText, mark);
                                }else if(markAfterDivide<=goodTop && goodLow<=markAfterDivide){
                                    markText = "Хорошо";
                                    fillOcenka(markText, mark);
                                }else if (markAfterDivide<=soTop && markAfterDivide>=soLow){
                                    markText = "Удов.";
                                    fillOcenka(markText, mark);
                                }else {
                                    markText = "Не удов.";
                                    fillOcenka(markText, mark);
                                }
                            }else if(mark<=109 && mark > 0){
                               markText = "Не удов.";
                               fillOcenka(markText, mark);
                            }else if (mark>=201){
                                JOptionPane.showMessageDialog(null, "Балл не должен превышать 200!");
                            }else if (markAfterDivide<0){
                                JOptionPane.showMessageDialog(null, "Балл не может быть отрицательным числом!");
                            }else { }   
                     
                     }else if(control.equalsIgnoreCase("Зачет")){
                         
                                if(excelTop>=mark && mark>=excelLow){
                                    markText = "Отлично";
                                    fillOcenka(markText, mark);
                                }else if(mark<=goodTop && goodLow<=mark){
                                    markText = "Хорошо";
                                    fillOcenka(markText, mark);
                                }else if (mark<=soTop && mark>=soLow){
                                    markText = "Удов.";
                                    fillOcenka(markText, mark);
                                }else if (mark>100){
                                    JOptionPane.showMessageDialog(null, "Баллы для этого вида контроля\n не может превышать 100");
                                }else{
                                    markText = "Не удов.";
                                    fillOcenka(markText, mark);
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
    
    public void ocenkaStringPraktikaIKursovaya (Integer mark){
        
                     Integer excellent = 5;
                     Integer good = 4;
                     Integer satisfied = 3;
                     Integer bad = 2;
                     
                     String markText;
                     
                            if(excellent==mark){
                                markText = "Отлично";
                                fillOcenka(markText, mark);
                            }else if(good==mark){
                                markText = "Хорошо";
                                fillOcenka(markText, mark);
                            }else if (satisfied==mark){
                                markText = "Удов.";
                                fillOcenka(markText, mark);
                            }else if (bad == mark) {
                                markText = "Не удов.";
                                fillOcenka(markText, mark);
                            }else if (mark == 1 && mark == 0){
                                markText = "Не удов.";
                                fillOcenka(markText, mark);
                            }else{
                                JOptionPane.showMessageDialog(null, "Введите правильное значение оценки!!!");
                            }                    
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parametrVedomPanel = new javax.swing.JPanel();
        groupNameLabel = new javax.swing.JLabel();
        semestrLabel = new javax.swing.JLabel();
        discLabel = new javax.swing.JLabel();
        groupNameField = new javax.swing.JTextField();
        codeDiscField = new javax.swing.JTextField();
        controlDateLabel = new javax.swing.JLabel();
        comboDiscVed = new javax.swing.JComboBox();
        controlTypeDiscLabel = new javax.swing.JLabel();
        grSemField = new javax.swing.JComboBox();
        dateControlChooser = new com.toedter.calendar.JDateChooser();
        numberVedomostiGruppyLabel = new javax.swing.JLabel();
        numberVedomostiGruppyField = new javax.swing.JTextField();
        begunokCheck = new javax.swing.JCheckBox();
        isBegunChek = new javax.swing.JLabel();
        buttonVedomPanel = new javax.swing.JPanel();
        acceptButtonVedom = new javax.swing.JButton();
        cancelButtonVedom = new javax.swing.JButton();
        editFieldsButtonsPanel = new javax.swing.JPanel();
        studLastName = new javax.swing.JLabel();
        VvediteKolvoBallov = new javax.swing.JLabel();
        ballTextField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        editApplyButton = new javax.swing.JButton();
        printWorkWV = new javax.swing.JButton();
        discScrollPane = new javax.swing.JScrollPane();
        disciplinesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Работа с ведомостью");
        setResizable(false);

        parametrVedomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Параметры ведомости"));

        groupNameLabel.setText("Группа");

        semestrLabel.setText("Семестр");

        discLabel.setText("Дисциплина");

        groupNameField.setEditable(false);

        codeDiscField.setEditable(false);

        controlDateLabel.setText("Дата контроля");

        comboDiscVed.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboDiscVedPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        grSemField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        grSemField.setEnabled(false);

        dateControlChooser.setDateFormatString("yyyy-MM-dd");

        numberVedomostiGruppyLabel.setText("№ ведомости");

        isBegunChek.setText("Бегунок");

        javax.swing.GroupLayout parametrVedomPanelLayout = new javax.swing.GroupLayout(parametrVedomPanel);
        parametrVedomPanel.setLayout(parametrVedomPanelLayout);
        parametrVedomPanelLayout.setHorizontalGroup(
            parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupNameLabel)
                    .addComponent(semestrLabel)
                    .addComponent(discLabel)
                    .addComponent(controlDateLabel))
                .addGap(21, 21, 21)
                .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(codeDiscField)
                            .addComponent(groupNameField)
                            .addComponent(grSemField, 0, 82, Short.MAX_VALUE))
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboDiscVed, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(controlTypeDiscLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(249, Short.MAX_VALUE))))
                    .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                        .addComponent(dateControlChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(numberVedomostiGruppyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberVedomostiGruppyField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(isBegunChek)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(begunokCheck)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        parametrVedomPanelLayout.setVerticalGroup(
            parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(groupNameLabel)
                            .addComponent(groupNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(semestrLabel)
                            .addComponent(controlTypeDiscLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(grSemField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(discLabel)
                            .addComponent(codeDiscField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboDiscVed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(controlDateLabel)
                                    .addComponent(dateControlChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(parametrVedomPanelLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(isBegunChek, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(parametrVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(numberVedomostiGruppyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numberVedomostiGruppyLabel))
                    .addComponent(begunokCheck))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        buttonVedomPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        acceptButtonVedom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        acceptButtonVedom.setText("Формировать");
        acceptButtonVedom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                acceptButtonVedomMouseReleased(evt);
            }
        });

        cancelButtonVedom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        cancelButtonVedom.setText("Выход");
        cancelButtonVedom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonVedomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonVedomPanelLayout = new javax.swing.GroupLayout(buttonVedomPanel);
        buttonVedomPanel.setLayout(buttonVedomPanelLayout);
        buttonVedomPanelLayout.setHorizontalGroup(
            buttonVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonVedomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(acceptButtonVedom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButtonVedom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        buttonVedomPanelLayout.setVerticalGroup(
            buttonVedomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonVedomPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(acceptButtonVedom)
                .addGap(18, 18, 18)
                .addComponent(cancelButtonVedom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        editFieldsButtonsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        studLastName.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N

        VvediteKolvoBallov.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        VvediteKolvoBallov.setText("Введите количество баллов семестрового контроля...........................");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        editApplyButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.png"))); // NOI18N
        editApplyButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editApplyButtonMouseClicked(evt);
            }
        });

        printWorkWV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/printButton.png"))); // NOI18N
        printWorkWV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printWorkWVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editFieldsButtonsPanelLayout = new javax.swing.GroupLayout(editFieldsButtonsPanel);
        editFieldsButtonsPanel.setLayout(editFieldsButtonsPanelLayout);
        editFieldsButtonsPanelLayout.setHorizontalGroup(
            editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFieldsButtonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editFieldsButtonsPanelLayout.createSequentialGroup()
                        .addComponent(VvediteKolvoBallov)
                        .addGap(31, 31, 31)
                        .addComponent(ballTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(studLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(editApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(printWorkWV, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        editFieldsButtonsPanelLayout.setVerticalGroup(
            editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFieldsButtonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(editFieldsButtonsPanelLayout.createSequentialGroup()
                        .addGroup(editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VvediteKolvoBallov, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ballTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(studLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(editFieldsButtonsPanelLayout.createSequentialGroup()
                        .addGroup(editFieldsButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(printWorkWV, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        disciplinesTable.setModel(new javax.swing.table.DefaultTableModel(
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
        disciplinesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                disciplinesTableMouseReleased(evt);
            }
        });
        discScrollPane.setViewportView(disciplinesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(discScrollPane, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(parametrVedomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonVedomPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(editFieldsButtonsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(parametrVedomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonVedomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editFieldsButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(discScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(762, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonVedomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonVedomActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonVedomActionPerformed

    private void comboDiscVedPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboDiscVedPopupMenuWillBecomeInvisible

            String item =(String) comboDiscVed.getSelectedItem();
            String sql = "SELECT disc_code FROM spr_discipline WHERE disc_name =?";
        try{     
          
               pst = conn.prepareStatement(sql);
               pst.setString(1, item);
                 rs = pst.executeQuery();
                 
                 if(rs.next()){
                 String add1 = rs.getString("disc_code");
                        codeDiscField.setText(add1);
                 }
           }catch(SQLException sqlex){
               JOptionPane.showMessageDialog(null, "comboDiscVedItemStateChanged "+sqlex);
       }
    }//GEN-LAST:event_comboDiscVedPopupMenuWillBecomeInvisible

    private void acceptButtonVedomMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acceptButtonVedomMouseReleased
        buttonFormirovat();
    }//GEN-LAST:event_acceptButtonVedomMouseReleased

    private void disciplinesTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_disciplinesTableMouseReleased
        clearFields();
        
        int row = disciplinesTable.getSelectedRow();
        String studIdFromTable = (disciplinesTable.getValueAt(row, 0).toString());
        String disc_code = codeDiscField.getText();
        
        clickTable(studIdFromTable, disc_code, row);
    }//GEN-LAST:event_disciplinesTableMouseReleased

    private void editApplyButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editApplyButtonMouseClicked
        int row = disciplinesTable.getSelectedRow();
        String studId=(disciplinesTable.getValueAt(row, 0).toString());
        String discCode=(disciplinesTable.getValueAt(row, 2).toString());
        Integer ball = Integer.parseInt(ballTextField.getText());
        String vedNumber = numberVedomostiGruppyField.getText();
        editMarkWithoutBegunok(studId, discCode, ball, vedNumber);
        buttonFormirovat();
        
    }//GEN-LAST:event_editApplyButtonMouseClicked

    private void printWorkWVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printWorkWVActionPerformed
        try{
           String grCode = groupNameField.getText();
           String disc_code = codeDiscField.getText();
           String uchpl_term = (String) grSemField.getSelectedItem();
           JasperDesign jd = JRXmlLoader.load(getClass().getResourceAsStream("reports/zachEkzVedom.jrxml"));
           String sql = "SELECT \n" +
                        "	stud.student_lastname AS Familiya,\n" +
                        "	stud.student_name AS Imya,\n" +
                        "	stud.student_middlename AS Otchestvo,\n" +
                        "	uchpl.student_id AS ID,\n" +
                        "	uchpl.group_code AS GroupCode,\n" +
                        "	uchpl.uchpls_ball AS ball,\n" +
                        "	uchpl.uchpls_ocenka AS Ocenka,\n" +
                        "       uchpl.uchplans_sem AS SemUchPlan,\n"+
                        "       uchpl.uchpls_numbervedom AS vedomNum,\n"+
                        "	uchpl.disc_code AS DiscCode,\n" +
                        "	sp_disc.disc_name AS DiscName,\n" +
                        "	gr.group_semestr AS SemestrGroup,\n" +
                        "	spr_formob.formobuch_name AS Formaobuchen,\n" +
                        "	spr_ru.A_NAMEL AS BafeName,\n" +
                        "	spr_ru.A_UOTDEL AS Uotdel,\n" +
                        "	spr_ru.A_1 AS excellLow,\n" +
                        "	spr_ru.A_2 AS ecxellTop,\n" +
                        "	spr_ru.A_3 AS goodLow,\n" +
                        "	spr_ru.A_4 AS goodTop,\n" +
                        "	spr_ru.A_5 AS soLow,\n" +
                        "	spr_ru.A_6 AS soTop,\n" +
                        "	spr_ru.A_YEARS AS Year1,\n" +
                        "	spr_ru.A_YEARE AS Year2\n" +
                        "FROM \n" +
                        "uchplanstudents uchpl\n" +
                        "INNER JOIN student stud ON uchpl.student_id=stud.student_id\n" +
                        "INNER JOIN spr_discipline sp_disc ON uchpl.disc_code=sp_disc.disc_code\n" +
                        "INNER JOIN groups gr ON uchpl.group_code=gr.group_code\n" +
                        "INNER JOIN spr_formobuch spr_formob ON gr.group_formaobuch = spr_formob.formobuch_code,\n" +
                        "spr_settings spr_ru\n" +
                        "WHERE \n" +
                        "uchpl.disc_code='"+disc_code+"'\n" +
                        "AND\n" +
                        "uchpl.uchPlans_sem='"+uchpl_term+"'\n" +
                        "AND\n"+
                        "uchpl.group_code='"+grCode+"'\n" +
                        "ORDER BY Familiya ASC";
           JRDesignQuery newQuery = new JRDesignQuery();
           newQuery.setText(sql);
           jd.setQuery(newQuery);
           JasperReport jr = JasperCompileManager.compileReport(jd);
           JasperPrint jp = JasperFillManager.fillReport(jr, null,conn);
           
           JasperViewer jv = new JasperViewer(jp, false);
           jv.setTitle("ЗЭВ группы "+grCode+" по дисциплине "+disc_code);
           jv.setVisible(true);
          
  
           
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "printSpisokGroupActionPerformed Action Error:\n"+e);
           e.printStackTrace();
       }
    }//GEN-LAST:event_printWorkWVActionPerformed

    public static void main(String args[]) {
      
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(workWithVedom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(workWithVedom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(workWithVedom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(workWithVedom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new workWithVedom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel VvediteKolvoBallov;
    private javax.swing.JButton acceptButtonVedom;
    private javax.swing.JTextField ballTextField;
    private javax.swing.JCheckBox begunokCheck;
    private javax.swing.JPanel buttonVedomPanel;
    private javax.swing.JButton cancelButtonVedom;
    private javax.swing.JTextField codeDiscField;
    private javax.swing.JComboBox comboDiscVed;
    private javax.swing.JLabel controlDateLabel;
    private javax.swing.JLabel controlTypeDiscLabel;
    private com.toedter.calendar.JDateChooser dateControlChooser;
    private javax.swing.JLabel discLabel;
    private javax.swing.JScrollPane discScrollPane;
    private javax.swing.JTable disciplinesTable;
    private javax.swing.JButton editApplyButton;
    private javax.swing.JPanel editFieldsButtonsPanel;
    private javax.swing.JComboBox grSemField;
    private javax.swing.JTextField groupNameField;
    private javax.swing.JLabel groupNameLabel;
    private javax.swing.JLabel isBegunChek;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField numberVedomostiGruppyField;
    private javax.swing.JLabel numberVedomostiGruppyLabel;
    private javax.swing.JPanel parametrVedomPanel;
    private javax.swing.JButton printWorkWV;
    private javax.swing.JLabel semestrLabel;
    private javax.swing.JLabel studLastName;
    // End of variables declaration//GEN-END:variables
}
