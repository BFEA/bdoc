/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddNation extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
            
    public AddNation() {
        initComponents();
        conn = dbconnector.ConnectorDb();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddNatPanel = new javax.swing.JPanel();
        codeOfNationField = new javax.swing.JTextField();
        codeOfCountryLabel = new javax.swing.JLabel();
        nameOfNationField = new javax.swing.JTextField();
        nameOfCountryLabel = new javax.swing.JLabel();
        addNewNation = new javax.swing.JButton();
        cancelNewNation = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавить национальность");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        AddNatPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfNationField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfNationField.setToolTipText("Введите код страны");

        codeOfCountryLabel.setText("Код элемента");

        nameOfNationField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfNationField.setToolTipText("Введите описание элемента");

        nameOfCountryLabel.setText("Описание элемента справочника");

        addNewNation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewNation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewNationActionPerformed(evt);
            }
        });

        cancelNewNation.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewNation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewNationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddNatPanelLayout = new javax.swing.GroupLayout(AddNatPanel);
        AddNatPanel.setLayout(AddNatPanelLayout);
        AddNatPanelLayout.setHorizontalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewNation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewNation)
                .addGap(53, 53, 53))
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfCountryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddNatPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfNationField)
                                .addComponent(nameOfNationField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddNatPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfCountryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddNatPanelLayout.setVerticalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfCountryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfNationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfCountryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfNationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewNation)
                    .addComponent(addNewNation))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(AddNatPanel, new java.awt.GridBagConstraints());

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-334)/2, (screenSize.height-276)/2, 334, 276);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewNationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewNationActionPerformed

        try {
            String sql = "INSERT INTO spr_nationality (nationality_code,nationality_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfNationField.getText());
            pst.setString(2, nameOfNationField.getText());


            pst.execute();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }


    }//GEN-LAST:event_addNewNationActionPerformed

    private void cancelNewNationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewNationActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewNationActionPerformed

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
            java.util.logging.Logger.getLogger(AddNation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNation.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddNation().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddNatPanel;
    private javax.swing.JButton addNewNation;
    private javax.swing.JButton cancelNewNation;
    private javax.swing.JLabel codeOfCountryLabel;
    private javax.swing.JTextField codeOfNationField;
    private javax.swing.JLabel nameOfCountryLabel;
    private javax.swing.JTextField nameOfNationField;
    // End of variables declaration//GEN-END:variables
}
