
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddGroup extends mainBdoc {

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;
 
    public AddGroup() {
        initComponents();
        conn = dbconnector.ConnectorDb();
        FillCombo();
        FillDep();
        FillTeacher();
        FillComboGr();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        addGroupDown = new javax.swing.JPanel();
        addAddNewGroup = new javax.swing.JButton();
        cancelAddNewGroup = new javax.swing.JButton();
        AddGroupPanelMain = new javax.swing.JPanel();
        fieldDepIdGroupAdd = new javax.swing.JTextField();
        fieldNameGroupAdd = new javax.swing.JTextField();
        fieldNaprGroupAdd = new javax.swing.JTextField();
        fieldSemestrGroupAdd = new javax.swing.JTextField();
        fieldMaterGroupAdd = new javax.swing.JTextField();
        fieldFromaobuchGroupAdd = new javax.swing.JTextField();
        labelGroupDepIdAdd = new javax.swing.JLabel();
        labelGroupAdd = new javax.swing.JLabel();
        labelNapravlenieAdd = new javax.swing.JLabel();
        labelSemestrGroupAdd = new javax.swing.JLabel();
        labelMaterGroupAdd = new javax.swing.JLabel();
        labelFObuchGroupAdd = new javax.swing.JLabel();
        comboSemestrGroupAdd = new javax.swing.JComboBox();
        comboFromaobuchGroupAdd = new javax.swing.JComboBox();
        depList = new javax.swing.JComboBox();
        getGroupCuratorID = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавление группы");
        setMaximumSize(new java.awt.Dimension(590, 360));
        setResizable(false);

        addGroupDown.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        AddGroupPanelMain.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Информация о группе", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0))));
        AddGroupPanelMain.setMinimumSize(new java.awt.Dimension(373, 320));
        AddGroupPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AddGroupPanelMain.add(fieldDepIdGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 40, -1));
        AddGroupPanelMain.add(fieldNameGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 320, -1));
        AddGroupPanelMain.add(fieldNaprGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 320, -1));

        fieldSemestrGroupAdd.setEnabled(false);
        fieldSemestrGroupAdd.setFocusable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, comboSemestrGroupAdd, org.jdesktop.beansbinding.ELProperty.create("${selectedItem}"), fieldSemestrGroupAdd, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        AddGroupPanelMain.add(fieldSemestrGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 80, -1));
        AddGroupPanelMain.add(fieldMaterGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 80, -1));
        AddGroupPanelMain.add(fieldFromaobuchGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 80, -1));

        labelGroupDepIdAdd.setText("Программа");
        AddGroupPanelMain.add(labelGroupDepIdAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 90, -1));

        labelGroupAdd.setText("Группа");
        AddGroupPanelMain.add(labelGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 90, -1));

        labelNapravlenieAdd.setText("Направление");
        AddGroupPanelMain.add(labelNapravlenieAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 90, -1));

        labelSemestrGroupAdd.setText("Семестр");
        AddGroupPanelMain.add(labelSemestrGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 150, 90, -1));

        labelMaterGroupAdd.setText("Куратор");
        AddGroupPanelMain.add(labelMaterGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 190, 90, -1));

        labelFObuchGroupAdd.setText("Форма обучения");
        AddGroupPanelMain.add(labelFObuchGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 120, -1));

        comboSemestrGroupAdd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        AddGroupPanelMain.add(comboSemestrGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, 70, -1));

        comboFromaobuchGroupAdd.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboFromaobuchGroupAddPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        AddGroupPanelMain.add(comboFromaobuchGroupAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, 230, -1));

        depList.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                depListPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        AddGroupPanelMain.add(depList, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 250, -1));

        getGroupCuratorID.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                getGroupCuratorIDPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        AddGroupPanelMain.add(getGroupCuratorID, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 230, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AddGroupPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                    .addComponent(addGroupDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddGroupPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addGroupDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        setSize(new java.awt.Dimension(598, 387));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void FillTeacher(){
        try{
            String sql= "SELECT * FROM teacher ORDER BY teacher_lastname ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                String name_depart = rs.getString("teacher_lastname");
                getGroupCuratorID.addItem(name_depart);
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
    }
    
    private void FillDep(){
       try{
            String sql= "SELECT * FROM departments ORDER BY depart_code ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            while(rs.next()){
                String name_depart = rs.getString("depart_name");
                depList.addItem(name_depart);
               
               
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
    }
    
     private void FillComboGr(){
        
        try{
            String sql= "SELECT * FROM spr_formobuch ORDER BY formobuch_code ASC";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            
            
            while(rs.next()){
                String name_formob = rs.getString("formobuch_name");
                comboFromaobuchGroupAdd.addItem(name_formob);
            
            }
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e);
        }finally {
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
     }
    
    private void addAddNewGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAddNewGroupActionPerformed
            
        try {

            String sql = "INSERT INTO groups (department_code,group_code,group_name,group_semestr,group_curator,group_formaobuch) values (?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, fieldDepIdGroupAdd.getText());
            pst.setString(2, fieldNameGroupAdd.getText());
            pst.setString(3, fieldNaprGroupAdd.getText());
            pst.setString(4, fieldSemestrGroupAdd.getText());
            pst.setString(5, fieldMaterGroupAdd.getText());
            pst.setString(6, fieldFromaobuchGroupAdd.getText());

            pst.execute();
                
            JOptionPane.showMessageDialog(null, "Группа успешно добавлено в БД");
            
            
            mainBdoc mb = new mainBdoc();
            mb.Update_table_Groups();
            
            fieldDepIdGroupAdd.setText("");
            fieldNameGroupAdd.setText("");
            fieldNaprGroupAdd.setText("");
            fieldSemestrGroupAdd.setText("");
            fieldMaterGroupAdd.setText("");
            fieldFromaobuchGroupAdd.setText("");
            
            mb.Update_table_Groups();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

    }//GEN-LAST:event_addAddNewGroupActionPerformed

    private void cancelAddNewGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddNewGroupActionPerformed
        dispose();
    }//GEN-LAST:event_cancelAddNewGroupActionPerformed

    private void depListPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_depListPopupMenuWillBecomeInvisible

            String item =(String) depList.getSelectedItem();
            String sql = "SELECT * FROM departments WHERE depart_name=? ORDER BY depart_name ASC";
        try{     
          
               pst = conn.prepareStatement(sql);
               pst.setString(1, item);
                 rs = pst.executeQuery();
                 
                 if(rs.next()){
                 String add1 = rs.getString("depart_code");
                        fieldDepIdGroupAdd.setText(add1);
                 }
           }catch(SQLException sqlex){
               JOptionPane.showMessageDialog(null, "depListPopupMenuWillBecomeInvisible "+sqlex);
       }
    }//GEN-LAST:event_depListPopupMenuWillBecomeInvisible

    private void comboFromaobuchGroupAddPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboFromaobuchGroupAddPopupMenuWillBecomeInvisible
 
            String item =(String) comboFromaobuchGroupAdd.getSelectedItem();
            String sql = "SELECT * FROM spr_formobuch WHERE formobuch_name=? ORDER BY formobuch_name ASC";
        try{     
          
               pst = conn.prepareStatement(sql);
               pst.setString(1, item);
                 rs = pst.executeQuery();
                 
                 if(rs.next()){
                 String add1 = rs.getString("formobuch_code");
                        fieldFromaobuchGroupAdd.setText(add1);
                 }
           }catch(SQLException sqlex){
               JOptionPane.showMessageDialog(null, "depListPopupMenuWillBecomeInvisible "+sqlex);
       }
    }//GEN-LAST:event_comboFromaobuchGroupAddPopupMenuWillBecomeInvisible

    private void getGroupCuratorIDPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_getGroupCuratorIDPopupMenuWillBecomeInvisible
            String item =(String) getGroupCuratorID.getSelectedItem();
            String sql = "SELECT * FROM teacher WHERE teacher_lastname=? ORDER BY teacher_lastname ASC";
        try{     
          
               pst = conn.prepareStatement(sql);
               pst.setString(1, item);
                 rs = pst.executeQuery();
                 
                 if(rs.next()){
                 String add1 = rs.getString("teacher_code");
                        fieldMaterGroupAdd.setText(add1);
                 }
           }catch(SQLException sqlex){
               JOptionPane.showMessageDialog(null, "getGroupCuratorIDPopupMenu "+sqlex);
       }
    }//GEN-LAST:event_getGroupCuratorIDPopupMenuWillBecomeInvisible

  
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
            java.util.logging.Logger.getLogger(AddGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddGroup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AddGroup().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddGroupPanelMain;
    private javax.swing.JButton addAddNewGroup;
    private javax.swing.JPanel addGroupDown;
    private javax.swing.JButton cancelAddNewGroup;
    private javax.swing.JComboBox comboFromaobuchGroupAdd;
    private javax.swing.JComboBox comboSemestrGroupAdd;
    private javax.swing.JComboBox depList;
    private javax.swing.JTextField fieldDepIdGroupAdd;
    private javax.swing.JTextField fieldFromaobuchGroupAdd;
    private javax.swing.JTextField fieldMaterGroupAdd;
    private javax.swing.JTextField fieldNameGroupAdd;
    private javax.swing.JTextField fieldNaprGroupAdd;
    private javax.swing.JTextField fieldSemestrGroupAdd;
    private javax.swing.JComboBox getGroupCuratorID;
    private javax.swing.JLabel labelFObuchGroupAdd;
    private javax.swing.JLabel labelGroupAdd;
    private javax.swing.JLabel labelGroupDepIdAdd;
    private javax.swing.JLabel labelMaterGroupAdd;
    private javax.swing.JLabel labelNapravlenieAdd;
    private javax.swing.JLabel labelSemestrGroupAdd;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
