
package bdoc;

import java.sql.*;
import javax.swing.*;

public class AddRegion extends javax.swing.JFrame {
    
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;

    public AddRegion() {
        initComponents();
         conn = dbconnector.ConnectorDb();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddNatPanel = new javax.swing.JPanel();
        codeOfRegionField = new javax.swing.JTextField();
        codeOfRegionLabel = new javax.swing.JLabel();
        nameOfRegionField = new javax.swing.JTextField();
        nameOfRegionLabel = new javax.swing.JLabel();
        addNewRegion = new javax.swing.JButton();
        cancelNewRegion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddNatPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfRegionField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfRegionField.setToolTipText("Введите код страны");

        codeOfRegionLabel.setText("Код элемента");

        nameOfRegionField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfRegionField.setToolTipText("Введите описание элемента");

        nameOfRegionLabel.setText("Описание элемента справочника");

        addNewRegion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewRegionActionPerformed(evt);
            }
        });

        cancelNewRegion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewRegion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewRegionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddNatPanelLayout = new javax.swing.GroupLayout(AddNatPanel);
        AddNatPanel.setLayout(AddNatPanelLayout);
        AddNatPanelLayout.setHorizontalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewRegion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewRegion)
                .addGap(53, 53, 53))
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfRegionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddNatPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfRegionField)
                                .addComponent(nameOfRegionField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddNatPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfRegionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddNatPanelLayout.setVerticalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfRegionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfRegionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfRegionLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfRegionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewRegion)
                    .addComponent(addNewRegion))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddNatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddNatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-336)/2, (screenSize.height-270)/2, 336, 270);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewRegionActionPerformed

        try {
            String sql = "INSERT INTO spr_region (region_code,region_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfRegionField.getText());
            pst.setString(2, nameOfRegionField.getText());


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

    }//GEN-LAST:event_addNewRegionActionPerformed

    private void cancelNewRegionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewRegionActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewRegionActionPerformed

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
            java.util.logging.Logger.getLogger(AddRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddRegion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddRegion().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddNatPanel;
    private javax.swing.JButton addNewRegion;
    private javax.swing.JButton cancelNewRegion;
    private javax.swing.JTextField codeOfRegionField;
    private javax.swing.JLabel codeOfRegionLabel;
    private javax.swing.JTextField nameOfRegionField;
    private javax.swing.JLabel nameOfRegionLabel;
    // End of variables declaration//GEN-END:variables
}
