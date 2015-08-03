
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddUzvan extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
    
    public AddUzvan() {
        initComponents();
        conn = dbconnector.ConnectorDb();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddUzvanPanel = new javax.swing.JPanel();
        codeOfUzvanField = new javax.swing.JTextField();
        codeOfDoljnostLabel = new javax.swing.JLabel();
        nameOfUzvanField = new javax.swing.JTextField();
        nameOfDoljnostLabel = new javax.swing.JLabel();
        addNewUzvan = new javax.swing.JButton();
        cancelNewUzvan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddUzvanPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfUzvanField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfUzvanField.setToolTipText("Введите код страны");

        codeOfDoljnostLabel.setText("Код элемента");

        nameOfUzvanField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfUzvanField.setToolTipText("Введите описание элемента");

        nameOfDoljnostLabel.setText("Описание элемента справочника");

        addNewUzvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewUzvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewUzvanActionPerformed(evt);
            }
        });

        cancelNewUzvan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewUzvan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewUzvanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddUzvanPanelLayout = new javax.swing.GroupLayout(AddUzvanPanel);
        AddUzvanPanel.setLayout(AddUzvanPanelLayout);
        AddUzvanPanelLayout.setHorizontalGroup(
            AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUzvanPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewUzvan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewUzvan)
                .addGap(53, 53, 53))
            .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddUzvanPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfDoljnostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfUzvanField)
                                .addComponent(nameOfUzvanField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfDoljnostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddUzvanPanelLayout.setVerticalGroup(
            AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddUzvanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfDoljnostLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfUzvanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfDoljnostLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfUzvanField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddUzvanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewUzvan)
                    .addComponent(addNewUzvan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddUzvanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddUzvanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-337)/2, (screenSize.height-269)/2, 337, 269);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewUzvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewUzvanActionPerformed

        try {
            String sql = "INSERT INTO spr_uzvanie (uzvanie_code,uzvanie_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfUzvanField.getText());
            pst.setString(2, nameOfUzvanField.getText());


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
    }//GEN-LAST:event_addNewUzvanActionPerformed

    private void cancelNewUzvanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewUzvanActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewUzvanActionPerformed

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
            java.util.logging.Logger.getLogger(AddUzvan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddUzvan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddUzvan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddUzvan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddUzvan().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddUzvanPanel;
    private javax.swing.JButton addNewUzvan;
    private javax.swing.JButton cancelNewUzvan;
    private javax.swing.JLabel codeOfDoljnostLabel;
    private javax.swing.JTextField codeOfUzvanField;
    private javax.swing.JLabel nameOfDoljnostLabel;
    private javax.swing.JTextField nameOfUzvanField;
    // End of variables declaration//GEN-END:variables
}
