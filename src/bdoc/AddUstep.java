
package bdoc;

import java.sql.*;
import javax.swing.*;

public class AddUstep extends javax.swing.JFrame {

    
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
    
    public AddUstep() {
        initComponents();
        conn = dbconnector.ConnectorDb();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddUzvanPanel = new javax.swing.JPanel();
        codeOfUstepField = new javax.swing.JTextField();
        codeOfUstepLabel = new javax.swing.JLabel();
        nameOfUstepField = new javax.swing.JTextField();
        nameOfUstepLabel = new javax.swing.JLabel();
        addNewUstep = new javax.swing.JButton();
        cancelNewUstep = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddUzvanPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfUstepField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfUstepField.setToolTipText("Введите код страны");

        codeOfUstepLabel.setText("Код элемента");

        nameOfUstepField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfUstepField.setToolTipText("Введите описание элемента");

        nameOfUstepLabel.setText("Описание элемента справочника");

        addNewUstep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewUstep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewUstepActionPerformed(evt);
            }
        });

        cancelNewUstep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewUstep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewUstepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddUzvanPanelLayout = new javax.swing.GroupLayout(AddUzvanPanel);
        AddUzvanPanel.setLayout(AddUzvanPanelLayout);
        AddUzvanPanelLayout.setHorizontalGroup(
            AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUzvanPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewUstep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewUstep)
                .addGap(53, 53, 53))
            .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUzvanPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfUstepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfUstepField)
                                .addComponent(nameOfUstepField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfUstepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddUzvanPanelLayout.setVerticalGroup(
            AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfUstepLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfUstepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfUstepLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfUstepField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewUstep)
                    .addComponent(addNewUstep))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddUzvanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddUzvanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-328)/2, (screenSize.height-270)/2, 328, 270);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewUstepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewUstepActionPerformed

        try {
            String sql = "INSERT INTO spr_uchstepen (ustep_code,ustep_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfUstepField.getText());
            pst.setString(2, nameOfUstepField.getText());


            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_addNewUstepActionPerformed

    private void cancelNewUstepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewUstepActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewUstepActionPerformed


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
            java.util.logging.Logger.getLogger(AddUstep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddUstep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddUstep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddUstep.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

  
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddUstep().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddUzvanPanel;
    private javax.swing.JButton addNewUstep;
    private javax.swing.JButton cancelNewUstep;
    private javax.swing.JTextField codeOfUstepField;
    private javax.swing.JLabel codeOfUstepLabel;
    private javax.swing.JTextField nameOfUstepField;
    private javax.swing.JLabel nameOfUstepLabel;
    // End of variables declaration//GEN-END:variables
}
