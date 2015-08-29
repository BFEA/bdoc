
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddSostob extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            
    public AddSostob() {
        initComponents();
        conn = dbconnector.getConnection();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddSostobuchPanel = new javax.swing.JPanel();
        codeOfSostobuchField = new javax.swing.JTextField();
        codeOfSostobuchLabel = new javax.swing.JLabel();
        nameOfSostobuchField = new javax.swing.JTextField();
        nameOfSostobuchLabel = new javax.swing.JLabel();
        addNewSostobuch = new javax.swing.JButton();
        cancelNewSostobuch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddSostobuchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfSostobuchField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfSostobuchField.setToolTipText("Введите код страны");

        codeOfSostobuchLabel.setText("Код элемента");

        nameOfSostobuchField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfSostobuchField.setToolTipText("Введите описание элемента");

        nameOfSostobuchLabel.setText("Описание элемента справочника");

        addNewSostobuch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewSostobuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewSostobuchActionPerformed(evt);
            }
        });

        cancelNewSostobuch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewSostobuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewSostobuchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddSostobuchPanelLayout = new javax.swing.GroupLayout(AddSostobuchPanel);
        AddSostobuchPanel.setLayout(AddSostobuchPanelLayout);
        AddSostobuchPanelLayout.setHorizontalGroup(
            AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddSostobuchPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewSostobuch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewSostobuch)
                .addGap(53, 53, 53))
            .addGroup(AddSostobuchPanelLayout.createSequentialGroup()
                .addGroup(AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddSostobuchPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfSostobuchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddSostobuchPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfSostobuchField)
                                .addComponent(nameOfSostobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddSostobuchPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfSostobuchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddSostobuchPanelLayout.setVerticalGroup(
            AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddSostobuchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfSostobuchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfSostobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfSostobuchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfSostobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddSostobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewSostobuch)
                    .addComponent(addNewSostobuch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddSostobuchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddSostobuchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-328)/2, (screenSize.height-244)/2, 328, 244);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewSostobuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewSostobuchActionPerformed

        try {
            String sql = "INSERT INTO spr_sostobuchen (sostobuchen_code,sostobuchen_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfSostobuchField.getText());
            pst.setString(2, nameOfSostobuchField.getText());


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
    }//GEN-LAST:event_addNewSostobuchActionPerformed

    private void cancelNewSostobuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewSostobuchActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewSostobuchActionPerformed


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
            java.util.logging.Logger.getLogger(AddSostob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddSostob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddSostob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddSostob.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddSostob().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddSostobuchPanel;
    private javax.swing.JButton addNewSostobuch;
    private javax.swing.JButton cancelNewSostobuch;
    private javax.swing.JTextField codeOfSostobuchField;
    private javax.swing.JLabel codeOfSostobuchLabel;
    private javax.swing.JTextField nameOfSostobuchField;
    private javax.swing.JLabel nameOfSostobuchLabel;
    // End of variables declaration//GEN-END:variables
}
