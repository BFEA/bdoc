
package bdoc;

import java.sql.*;
import javax.swing.*;

public class AddDoljnost extends javax.swing.JFrame {
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;


    public AddDoljnost() {
        initComponents();
        conn = dbconnector.getConnection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddNatPanel = new javax.swing.JPanel();
        codeOfDoljnostField = new javax.swing.JTextField();
        codeOfDoljnostLabel = new javax.swing.JLabel();
        nameOfDoljnostField = new javax.swing.JTextField();
        nameOfDoljnostLabel = new javax.swing.JLabel();
        addNewDoljnost = new javax.swing.JButton();
        cancelNewDoljnost = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добавить элемент должности");
        setResizable(false);

        AddNatPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfDoljnostField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfDoljnostField.setToolTipText("Введите код страны");

        codeOfDoljnostLabel.setText("Код элемента");

        nameOfDoljnostField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfDoljnostField.setToolTipText("Введите описание элемента");

        nameOfDoljnostLabel.setText("Описание элемента справочника");

        addNewDoljnost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewDoljnost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewDoljnostActionPerformed(evt);
            }
        });

        cancelNewDoljnost.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewDoljnost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewDoljnostActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddNatPanelLayout = new javax.swing.GroupLayout(AddNatPanel);
        AddNatPanel.setLayout(AddNatPanelLayout);
        AddNatPanelLayout.setHorizontalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewDoljnost)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewDoljnost)
                .addGap(53, 53, 53))
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddNatPanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfDoljnostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddNatPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfDoljnostField)
                                .addComponent(nameOfDoljnostField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddNatPanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfDoljnostLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddNatPanelLayout.setVerticalGroup(
            AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddNatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfDoljnostLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfDoljnostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfDoljnostLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfDoljnostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddNatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewDoljnost)
                    .addComponent(addNewDoljnost))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddNatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddNatPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-328)/2, (screenSize.height-265)/2, 328, 265);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewDoljnostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewDoljnostActionPerformed

        try {
            String sql = "INSERT INTO spr_doljnost (dolj_code,dolj_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfDoljnostField.getText());
            pst.setString(2, nameOfDoljnostField.getText());


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
    }//GEN-LAST:event_addNewDoljnostActionPerformed

    private void cancelNewDoljnostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewDoljnostActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewDoljnostActionPerformed

   
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
            java.util.logging.Logger.getLogger(AddDoljnost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddDoljnost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddDoljnost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddDoljnost.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddDoljnost().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddNatPanel;
    private javax.swing.JButton addNewDoljnost;
    private javax.swing.JButton cancelNewDoljnost;
    private javax.swing.JTextField codeOfDoljnostField;
    private javax.swing.JLabel codeOfDoljnostLabel;
    private javax.swing.JTextField nameOfDoljnostField;
    private javax.swing.JLabel nameOfDoljnostLabel;
    // End of variables declaration//GEN-END:variables
}
