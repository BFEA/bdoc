
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddDep extends javax.swing.JFrame {
        
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
    
   
    public AddDep() {
        initComponents();
        conn = dbconnector.getConnection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addDepPanel = new javax.swing.JPanel();
        panelAddDep = new javax.swing.JPanel();
        fieldLaborantDep = new javax.swing.JTextField();
        labelPhoneDep = new javax.swing.JLabel();
        fieldHeaderDep = new javax.swing.JTextField();
        fieldCodeIdDep = new javax.swing.JTextField();
        fieldPhoneDep = new javax.swing.JTextField();
        fieldNameDep = new javax.swing.JTextField();
        labelLaborantDep = new javax.swing.JLabel();
        fieldMaileDep = new javax.swing.JTextField();
        labelMaileDep = new javax.swing.JLabel();
        labelCodeIdDep = new javax.swing.JLabel();
        labelHeaderDep = new javax.swing.JLabel();
        labelNameDep = new javax.swing.JLabel();
        addDepDown = new javax.swing.JPanel();
        addAddNewDep = new javax.swing.JButton();
        cancelAddNewDep = new javax.swing.JButton();

        setTitle("Добавить");

        panelAddDep.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        fieldLaborantDep.setBackground(new java.awt.Color(255, 255, 255));

        labelPhoneDep.setText("Телефон");

        fieldHeaderDep.setBackground(new java.awt.Color(255, 255, 255));

        fieldCodeIdDep.setBackground(new java.awt.Color(255, 255, 255));

        fieldPhoneDep.setBackground(new java.awt.Color(255, 255, 255));

        fieldNameDep.setBackground(new java.awt.Color(255, 255, 255));

        labelLaborantDep.setText("Лаборант");

        fieldMaileDep.setBackground(new java.awt.Color(255, 255, 255));

        labelMaileDep.setText("E-Mail");

        labelCodeIdDep.setText("Код подразделения");

        labelHeaderDep.setText("Руководитель");

        labelNameDep.setText("Наименование");

        javax.swing.GroupLayout panelAddDepLayout = new javax.swing.GroupLayout(panelAddDep);
        panelAddDep.setLayout(panelAddDepLayout);
        panelAddDepLayout.setHorizontalGroup(
            panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddDepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelHeaderDep)
                    .addComponent(labelLaborantDep)
                    .addComponent(labelPhoneDep)
                    .addComponent(labelMaileDep)
                    .addComponent(labelNameDep, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodeIdDep))
                .addGap(27, 27, 27)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fieldMaileDep)
                    .addComponent(fieldCodeIdDep)
                    .addComponent(fieldNameDep)
                    .addComponent(fieldHeaderDep)
                    .addComponent(fieldLaborantDep)
                    .addComponent(fieldPhoneDep))
                .addContainerGap())
        );
        panelAddDepLayout.setVerticalGroup(
            panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAddDepLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldCodeIdDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCodeIdDep))
                .addGap(18, 18, 18)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fieldNameDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNameDep))
                .addGap(18, 18, 18)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelHeaderDep)
                    .addComponent(fieldHeaderDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelLaborantDep)
                    .addComponent(fieldLaborantDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fieldPhoneDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAddDepLayout.createSequentialGroup()
                        .addComponent(labelPhoneDep, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(18, 18, 18)
                .addGroup(panelAddDepLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fieldMaileDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMaileDep, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        addDepDown.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        addAddNewDep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addAddNewDep.setText("Применить");
        addAddNewDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAddNewDepActionPerformed(evt);
            }
        });

        cancelAddNewDep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelAddNewDep.setText("    Отмена");
        cancelAddNewDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAddNewDepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addDepDownLayout = new javax.swing.GroupLayout(addDepDown);
        addDepDown.setLayout(addDepDownLayout);
        addDepDownLayout.setHorizontalGroup(
            addDepDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addDepDownLayout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(addAddNewDep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(cancelAddNewDep, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
        addDepDownLayout.setVerticalGroup(
            addDepDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addDepDownLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addDepDownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAddNewDep)
                    .addComponent(cancelAddNewDep))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout addDepPanelLayout = new javax.swing.GroupLayout(addDepPanel);
        addDepPanel.setLayout(addDepPanelLayout);
        addDepPanelLayout.setHorizontalGroup(
            addDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addDepPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAddDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addDepDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        addDepPanelLayout.setVerticalGroup(
            addDepPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addDepPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelAddDep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addDepDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(addDepPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 341, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(addDepPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-373)/2, 500, 373);
    }// </editor-fold>//GEN-END:initComponents

    private void addAddNewDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAddNewDepActionPerformed
       
        try {
            
            String sql = "INSERT INTO departments (depart_code,depart_name,depart_header,depart_assistant,depart_phone,depart_email) values (?,?,?,?,?,?)";
                 pst = conn.prepareStatement(sql);
                 
                  pst.setString(1, fieldCodeIdDep.getText());
                        pst.setString(2, fieldNameDep.getText());
                        pst.setString(3, fieldHeaderDep.getText());
                        pst.setString(4, fieldLaborantDep.getText());
                        pst.setString(5, fieldPhoneDep.getText());
                        pst.setString(6, fieldMaileDep.getText());
            
                 pst.execute();
                 
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
            rs.close();
                pst.close();
        }catch(Exception e){
    
                }       }
        dispose();
        
    }//GEN-LAST:event_addAddNewDepActionPerformed

    private void cancelAddNewDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAddNewDepActionPerformed
       dispose();
    }//GEN-LAST:event_cancelAddNewDepActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
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
            java.util.logging.Logger.getLogger(AddDep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddDep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddDep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddDep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new AddDep().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAddNewDep;
    private javax.swing.JPanel addDepDown;
    private javax.swing.JPanel addDepPanel;
    private javax.swing.JButton cancelAddNewDep;
    private javax.swing.JTextField fieldCodeIdDep;
    private javax.swing.JTextField fieldHeaderDep;
    private javax.swing.JTextField fieldLaborantDep;
    private javax.swing.JTextField fieldMaileDep;
    private javax.swing.JTextField fieldNameDep;
    private javax.swing.JTextField fieldPhoneDep;
    private javax.swing.JLabel labelCodeIdDep;
    private javax.swing.JLabel labelHeaderDep;
    private javax.swing.JLabel labelLaborantDep;
    private javax.swing.JLabel labelMaileDep;
    private javax.swing.JLabel labelNameDep;
    private javax.swing.JLabel labelPhoneDep;
    private javax.swing.JPanel panelAddDep;
    // End of variables declaration//GEN-END:variables
}
