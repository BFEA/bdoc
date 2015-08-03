
package bdoc;

import java.sql.*;
import javax.swing.*;

public class AddStatus extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
    
    public AddStatus() {
        initComponents();
        conn = dbconnector.ConnectorDb();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddStatusPanel = new javax.swing.JPanel();
        codeOfStatusField = new javax.swing.JTextField();
        codeOfStatusLabel = new javax.swing.JLabel();
        nameOfStatusField = new javax.swing.JTextField();
        nameOfStatusLabel = new javax.swing.JLabel();
        addNewStatus = new javax.swing.JButton();
        cancelNewStatus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddStatusPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfStatusField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfStatusField.setToolTipText("Введите код страны");

        codeOfStatusLabel.setText("Код элемента");

        nameOfStatusField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfStatusField.setToolTipText("Введите описание элемента");

        nameOfStatusLabel.setText("Описание элемента справочника");

        addNewStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewStatusActionPerformed(evt);
            }
        });

        cancelNewStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddStatusPanelLayout = new javax.swing.GroupLayout(AddStatusPanel);
        AddStatusPanel.setLayout(AddStatusPanelLayout);
        AddStatusPanelLayout.setHorizontalGroup(
            AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddStatusPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewStatus)
                .addGap(53, 53, 53))
            .addGroup(AddStatusPanelLayout.createSequentialGroup()
                .addGroup(AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddStatusPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddStatusPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfStatusField)
                                .addComponent(nameOfStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddStatusPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddStatusPanelLayout.setVerticalGroup(
            AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddStatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfStatusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfStatusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfStatusField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddStatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewStatus)
                    .addComponent(addNewStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddStatusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-328)/2, (screenSize.height-263)/2, 328, 263);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewStatusActionPerformed

        try {
            String sql = "INSERT INTO spr_statusteach (status_code,status_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfStatusField.getText());
            pst.setString(2, nameOfStatusField.getText());


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
    }//GEN-LAST:event_addNewStatusActionPerformed

    private void cancelNewStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewStatusActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewStatusActionPerformed


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
            java.util.logging.Logger.getLogger(AddStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

     
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddStatus().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddStatusPanel;
    private javax.swing.JButton addNewStatus;
    private javax.swing.JButton cancelNewStatus;
    private javax.swing.JTextField codeOfStatusField;
    private javax.swing.JLabel codeOfStatusLabel;
    private javax.swing.JTextField nameOfStatusField;
    private javax.swing.JLabel nameOfStatusLabel;
    // End of variables declaration//GEN-END:variables
}
