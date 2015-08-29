
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddFormobuch extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
   
    public AddFormobuch() {
        initComponents();
        conn = dbconnector.getConnection();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddFormobuchPanel = new javax.swing.JPanel();
        codeOfFormobuchField = new javax.swing.JTextField();
        codeOfFormobuchLabel = new javax.swing.JLabel();
        nameOfFormobuchField = new javax.swing.JTextField();
        nameOfFormobuchLabel = new javax.swing.JLabel();
        addNewFormobuch = new javax.swing.JButton();
        cancelNewFormobuch = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавить элемент Формы обучения");
        setResizable(false);

        AddFormobuchPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfFormobuchField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfFormobuchField.setToolTipText("Введите код страны");

        codeOfFormobuchLabel.setText("Код элемента");

        nameOfFormobuchField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfFormobuchField.setToolTipText("Введите описание элемента");

        nameOfFormobuchLabel.setText("Описание элемента справочника");

        addNewFormobuch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewFormobuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewFormobuchActionPerformed(evt);
            }
        });

        cancelNewFormobuch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewFormobuch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewFormobuchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddFormobuchPanelLayout = new javax.swing.GroupLayout(AddFormobuchPanel);
        AddFormobuchPanel.setLayout(AddFormobuchPanelLayout);
        AddFormobuchPanelLayout.setHorizontalGroup(
            AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddFormobuchPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewFormobuch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewFormobuch)
                .addGap(53, 53, 53))
            .addGroup(AddFormobuchPanelLayout.createSequentialGroup()
                .addGroup(AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddFormobuchPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfFormobuchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddFormobuchPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfFormobuchField)
                                .addComponent(nameOfFormobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddFormobuchPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfFormobuchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddFormobuchPanelLayout.setVerticalGroup(
            AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddFormobuchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfFormobuchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfFormobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfFormobuchLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfFormobuchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddFormobuchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewFormobuch)
                    .addComponent(addNewFormobuch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddFormobuchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddFormobuchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-340)/2, (screenSize.height-266)/2, 340, 266);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewFormobuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewFormobuchActionPerformed

        try {
            String sql = "INSERT INTO spr_formobuch (formobuch_code,formobuch_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfFormobuchField.getText());
            pst.setString(2, nameOfFormobuchField.getText());


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
    }//GEN-LAST:event_addNewFormobuchActionPerformed

    private void cancelNewFormobuchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewFormobuchActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewFormobuchActionPerformed


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
            java.util.logging.Logger.getLogger(AddFormobuch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddFormobuch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddFormobuch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddFormobuch.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddFormobuch().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddFormobuchPanel;
    private javax.swing.JButton addNewFormobuch;
    private javax.swing.JButton cancelNewFormobuch;
    private javax.swing.JTextField codeOfFormobuchField;
    private javax.swing.JLabel codeOfFormobuchLabel;
    private javax.swing.JTextField nameOfFormobuchField;
    private javax.swing.JLabel nameOfFormobuchLabel;
    // End of variables declaration//GEN-END:variables
}
