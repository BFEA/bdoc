
package bdoc;

import java.sql.*;
import javax.swing.*;


public class AddDiscipline extends javax.swing.JFrame {

            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
   
    public AddDiscipline() {
        initComponents();
        conn = dbconnector.getConnection();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddDisciplinePanel = new javax.swing.JPanel();
        codeOfDisciplineField = new javax.swing.JTextField();
        codeOfDisciplineLabel = new javax.swing.JLabel();
        nameOfDisciplineField = new javax.swing.JTextField();
        nameOfDisciplineLabel = new javax.swing.JLabel();
        addNewDiscipline = new javax.swing.JButton();
        cancelNewDiscipline = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        AddDisciplinePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfDisciplineField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfDisciplineField.setToolTipText("Введите код страны");

        codeOfDisciplineLabel.setText("Код элемента");

        nameOfDisciplineField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfDisciplineField.setToolTipText("Введите описание элемента");

        nameOfDisciplineLabel.setText("Описание элемента справочника");

        addNewDiscipline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewDiscipline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewDisciplineActionPerformed(evt);
            }
        });

        cancelNewDiscipline.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewDiscipline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewDisciplineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AddDisciplinePanelLayout = new javax.swing.GroupLayout(AddDisciplinePanel);
        AddDisciplinePanel.setLayout(AddDisciplinePanelLayout);
        AddDisciplinePanelLayout.setHorizontalGroup(
            AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddDisciplinePanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewDiscipline)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewDiscipline)
                .addGap(53, 53, 53))
            .addGroup(AddDisciplinePanelLayout.createSequentialGroup()
                .addGroup(AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddDisciplinePanelLayout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfDisciplineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(AddDisciplinePanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfDisciplineField)
                                .addComponent(nameOfDisciplineField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AddDisciplinePanelLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfDisciplineLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        AddDisciplinePanelLayout.setVerticalGroup(
            AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDisciplinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfDisciplineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfDisciplineField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfDisciplineLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfDisciplineField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddDisciplinePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewDiscipline)
                    .addComponent(addNewDiscipline))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddDisciplinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AddDisciplinePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-342)/2, (screenSize.height-260)/2, 342, 260);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewDisciplineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewDisciplineActionPerformed

        try {
            String sql = "INSERT INTO spr_discipline (disc_code,disc_name) VALUES (?,?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, codeOfDisciplineField.getText());
            pst.setString(2, nameOfDisciplineField.getText());


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
    }//GEN-LAST:event_addNewDisciplineActionPerformed

    private void cancelNewDisciplineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewDisciplineActionPerformed
        dispose();
    }//GEN-LAST:event_cancelNewDisciplineActionPerformed

   
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
            java.util.logging.Logger.getLogger(AddDiscipline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddDiscipline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddDiscipline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddDiscipline.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddDiscipline().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddDisciplinePanel;
    private javax.swing.JButton addNewDiscipline;
    private javax.swing.JButton cancelNewDiscipline;
    private javax.swing.JTextField codeOfDisciplineField;
    private javax.swing.JLabel codeOfDisciplineLabel;
    private javax.swing.JTextField nameOfDisciplineField;
    private javax.swing.JLabel nameOfDisciplineLabel;
    // End of variables declaration//GEN-END:variables
}
