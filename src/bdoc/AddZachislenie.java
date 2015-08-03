
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddZachislenie extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
    
    public AddZachislenie() {
        initComponents();
         conn = dbconnector.ConnectorDb();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddZachisleniePanel = new javax.swing.JPanel();
        codeOfZachislenieField = new javax.swing.JTextField();
        codeOfZachislenieLabel = new javax.swing.JLabel();
        nameOfZachislenieField = new javax.swing.JTextField();
        nameOfZachislenieLabel = new javax.swing.JLabel();
        addNewZachislenie = new javax.swing.JButton();
        cancelNewZachislenie = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddZachisleniePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfZachislenieField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfZachislenieField.setToolTipText("Введите код страны");

        codeOfZachislenieLabel.setText("Код элемента");

        nameOfZachislenieField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfZachislenieField.setToolTipText("Введите описание элемента");

        nameOfZachislenieLabel.setText("Описание элемента справочника");

        addNewZachislenie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewZachislenie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewZachislenieActionPerformed(evt);
            }
        });

        cancelNewZachislenie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewZachislenie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewZachislenieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddZachisleniePanelLayout = new javax.swing.GroupLayout(AddZachisleniePanel);
        AddZachisleniePanel.setLayout(AddZachisleniePanelLayout);
        AddZachisleniePanelLayout.setHorizontalGroup(
            AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddZachisleniePanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewZachislenie)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewZachislenie)
                .addGap(53, 53, 53))
            .addGroup(AddZachisleniePanelLayout.createSequentialGroup()
                .addGroup(AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddZachisleniePanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfZachislenieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddZachisleniePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfZachislenieField)
                                .addComponent(nameOfZachislenieField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddZachisleniePanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfZachislenieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddZachisleniePanelLayout.setVerticalGroup(
            AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddZachisleniePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfZachislenieLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfZachislenieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfZachislenieLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfZachislenieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddZachisleniePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewZachislenie)
                    .addComponent(addNewZachislenie))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddZachisleniePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddZachisleniePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-328)/2, (screenSize.height-259)/2, 328, 259);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewZachislenieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewZachislenieActionPerformed

        try {
            String sql = "INSERT INTO spr_zachislenie (zachislenie_code,zachislenie_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfZachislenieField.getText());
            pst.setString(2, nameOfZachislenieField.getText());


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
    }//GEN-LAST:event_addNewZachislenieActionPerformed

    private void cancelNewZachislenieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewZachislenieActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewZachislenieActionPerformed

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
            java.util.logging.Logger.getLogger(AddZachislenie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddZachislenie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddZachislenie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddZachislenie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddZachislenie().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddZachisleniePanel;
    private javax.swing.JButton addNewZachislenie;
    private javax.swing.JButton cancelNewZachislenie;
    private javax.swing.JTextField codeOfZachislenieField;
    private javax.swing.JLabel codeOfZachislenieLabel;
    private javax.swing.JTextField nameOfZachislenieField;
    private javax.swing.JLabel nameOfZachislenieLabel;
    // End of variables declaration//GEN-END:variables
}
