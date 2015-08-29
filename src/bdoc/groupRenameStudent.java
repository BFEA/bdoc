
package bdoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class groupRenameStudent extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
           
                
    public groupRenameStudent() {
        initComponents();
        conn = dbconnector.getConnection();
        FillCombo();
    }
    
    public void addStudId(String stud_id,String stud_FirN,String stud_Name,String stud_MidN,String stud_Group){
        fieldIdStudent.setText(stud_id);
        fieldFamiliyaStudent.setText(stud_FirN);
        fieldNameStudent.setText(stud_Name);
        fieldOtchestvoStudent.setText(stud_MidN);
        fieldGroupName.setText(stud_Group);
    }
    
    private void FillCombo(){
          try{
            String sql= "SELECT * FROM groups ORDER BY group_code ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String group_code = rs.getString("group_code");
                comboBoxGroupTo.addItem(group_code);
               
            }
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e);
        }
          
         
    }
    
    private void editUchplans(String studId, String gr_code){
        
                try{ 
                        String sql= "UPDATE uchplanstudents SET group_code = '"+gr_code+"' WHERE student_id = '"+studId+"'";
                        pst=conn.prepareStatement(sql);
                        pst.execute();
                        
                       }catch(SQLException sqlex){
                                JOptionPane.showMessageDialog(null, sqlex);
                                                }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aboutStudent = new javax.swing.JPanel();
        fieldIdStudent = new javax.swing.JTextField();
        fieldFamiliyaStudent = new javax.swing.JTextField();
        fieldNameStudent = new javax.swing.JTextField();
        fieldOtchestvoStudent = new javax.swing.JTextField();
        fieldGroupName = new javax.swing.JTextField();
        labelFamiliyaStudent = new javax.swing.JLabel();
        labelNameStudent = new javax.swing.JLabel();
        labeMiddlenameStudent = new javax.swing.JLabel();
        labelIdStudent = new javax.swing.JLabel();
        groupFrom = new javax.swing.JLabel();
        groupTo = new javax.swing.JLabel();
        comboBoxGroupTo = new javax.swing.JComboBox();
        buttonDownPanel = new javax.swing.JPanel();
        acceptGroupRename = new javax.swing.JButton();
        cancelGroupRename = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Перевод студента Группа -> Группа");

        aboutStudent.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        fieldFamiliyaStudent.setToolTipText("Фамилия");

        fieldNameStudent.setToolTipText("Имя");

        fieldOtchestvoStudent.setToolTipText("Отчество");

        labelFamiliyaStudent.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelFamiliyaStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFamiliyaStudent.setText("Фамилия");

        labelNameStudent.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelNameStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelNameStudent.setText("Имя");

        labeMiddlenameStudent.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labeMiddlenameStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labeMiddlenameStudent.setText("Отчество");

        labelIdStudent.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelIdStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelIdStudent.setText("ID студента");

        groupFrom.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        groupFrom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        groupFrom.setText("Группа студента");

        groupTo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        groupTo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        groupTo.setText("Перевод в группу:");

        comboBoxGroupTo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout aboutStudentLayout = new javax.swing.GroupLayout(aboutStudent);
        aboutStudent.setLayout(aboutStudentLayout);
        aboutStudentLayout.setHorizontalGroup(
            aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutStudentLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(labelFamiliyaStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(126, 126, 126)
                .addComponent(labelNameStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labeMiddlenameStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
            .addGroup(aboutStudentLayout.createSequentialGroup()
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(aboutStudentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(aboutStudentLayout.createSequentialGroup()
                                .addComponent(fieldFamiliyaStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 20, Short.MAX_VALUE))
                            .addGroup(aboutStudentLayout.createSequentialGroup()
                                .addComponent(fieldIdStudent)
                                .addGap(77, 77, 77))))
                    .addGroup(aboutStudentLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(labelIdStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87)))
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldNameStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(groupFrom, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addComponent(fieldGroupName, javax.swing.GroupLayout.Alignment.LEADING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(aboutStudentLayout.createSequentialGroup()
                        .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldOtchestvoStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboBoxGroupTo, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addGap(24, 24, 24))
        );
        aboutStudentLayout.setVerticalGroup(
            aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutStudentLayout.createSequentialGroup()
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(aboutStudentLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(labelFamiliyaStudent))
                    .addGroup(aboutStudentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelNameStudent)
                            .addComponent(labeMiddlenameStudent))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldFamiliyaStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldNameStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldOtchestvoStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelIdStudent)
                    .addComponent(groupFrom)
                    .addComponent(groupTo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(aboutStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldIdStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldGroupName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxGroupTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        buttonDownPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        acceptGroupRename.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        acceptGroupRename.setText("Применить");
        acceptGroupRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptGroupRenameActionPerformed(evt);
            }
        });

        cancelGroupRename.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing2.png"))); // NOI18N
        cancelGroupRename.setText("Отменить");
        cancelGroupRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelGroupRenameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonDownPanelLayout = new javax.swing.GroupLayout(buttonDownPanel);
        buttonDownPanel.setLayout(buttonDownPanelLayout);
        buttonDownPanelLayout.setHorizontalGroup(
            buttonDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonDownPanelLayout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(acceptGroupRename)
                .addGap(159, 159, 159)
                .addComponent(cancelGroupRename)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonDownPanelLayout.setVerticalGroup(
            buttonDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(acceptGroupRename)
                .addComponent(cancelGroupRename))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(aboutStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonDownPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(aboutStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonDownPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        setSize(new java.awt.Dimension(742, 237));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void acceptGroupRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptGroupRenameActionPerformed
                 int p = JOptionPane.showConfirmDialog(null,"Вы действительно хотите\n перевести студента?","Выполнение операции",JOptionPane.YES_NO_OPTION);
            
                if(p==0){    
                       try{ 
                      
                        String stud_id = fieldIdStudent.getText();
                        String studNewGroup =(String) comboBoxGroupTo.getSelectedItem();
                           
                        String sql= "UPDATE student SET group_code='"+studNewGroup+"' WHERE student_id = '"+stud_id+"'";
                        pst=conn.prepareStatement(sql);
                        pst.execute();
                           editUchplans(stud_id, studNewGroup);
                        JOptionPane.showMessageDialog(null, "Updated");
                           
                       }catch(SQLException sqlex){
                                JOptionPane.showMessageDialog(null, sqlex);
                            }
                   }
                dispose();
    }//GEN-LAST:event_acceptGroupRenameActionPerformed

    private void cancelGroupRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelGroupRenameActionPerformed
                dispose();
    }//GEN-LAST:event_cancelGroupRenameActionPerformed


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
            java.util.logging.Logger.getLogger(groupRenameStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(groupRenameStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(groupRenameStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(groupRenameStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new groupRenameStudent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutStudent;
    private javax.swing.JButton acceptGroupRename;
    private javax.swing.JPanel buttonDownPanel;
    private javax.swing.JButton cancelGroupRename;
    private javax.swing.JComboBox comboBoxGroupTo;
    private javax.swing.JTextField fieldFamiliyaStudent;
    private javax.swing.JTextField fieldGroupName;
    private javax.swing.JTextField fieldIdStudent;
    private javax.swing.JTextField fieldNameStudent;
    private javax.swing.JTextField fieldOtchestvoStudent;
    private javax.swing.JLabel groupFrom;
    private javax.swing.JLabel groupTo;
    private javax.swing.JLabel labeMiddlenameStudent;
    private javax.swing.JLabel labelFamiliyaStudent;
    private javax.swing.JLabel labelIdStudent;
    private javax.swing.JLabel labelNameStudent;
    // End of variables declaration//GEN-END:variables
}
