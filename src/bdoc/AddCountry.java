
package bdoc;

import java.sql.*;
import javax.swing.JOptionPane;

public class AddCountry extends javax.swing.JFrame {

    
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement pst = null;
   
    public AddCountry() {
        initComponents();
        conn = dbconnector.ConnectorDb();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        codeOfCountryField = new javax.swing.JTextField();
        codeOfCountryLabel = new javax.swing.JLabel();
        nameOfCountryField = new javax.swing.JTextField();
        nameOfCountryLabel = new javax.swing.JLabel();
        addNewCountry = new javax.swing.JButton();
        cancelNewCountry = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Добвить элемент справки");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Добавить новый элемент"));

        codeOfCountryField.setBackground(new java.awt.Color(255, 255, 255));
        codeOfCountryField.setToolTipText("Введите код страны");

        codeOfCountryLabel.setText("Код элемента");

        nameOfCountryField.setBackground(new java.awt.Color(255, 255, 255));
        nameOfCountryField.setToolTipText("Введите описание элемента");

        nameOfCountryLabel.setText("Описание элемента справочника");

        addNewCountry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/apply.png"))); // NOI18N
        addNewCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewCountryActionPerformed(evt);
            }
        });

        cancelNewCountry.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel_editing.png"))); // NOI18N
        cancelNewCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelNewCountryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(addNewCountry)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cancelNewCountry)
                .addGap(53, 53, 53))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(codeOfCountryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(85, 85, 85))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(codeOfCountryField)
                                .addComponent(nameOfCountryField, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(nameOfCountryLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(codeOfCountryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(codeOfCountryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nameOfCountryLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameOfCountryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelNewCountry)
                    .addComponent(addNewCountry))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-337)/2, (screenSize.height-270)/2, 337, 270);
    }// </editor-fold>//GEN-END:initComponents

    private void addNewCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewCountryActionPerformed
      
        try{
            String sql = "INSERT INTO spr_country (cou_cod,cou_name) VALUES (?,?)";
                 pst = conn.prepareStatement(sql);
                 
                        pst.setString(1, codeOfCountryField.getText());
                        pst.setString(2, nameOfCountryField.getText());
                       
            
                 pst.execute();
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
            }
        }

        
    }//GEN-LAST:event_addNewCountryActionPerformed

    private void cancelNewCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelNewCountryActionPerformed
       dispose();
    }//GEN-LAST:event_cancelNewCountryActionPerformed

    
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
            java.util.logging.Logger.getLogger(AddCountry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCountry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCountry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCountry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

       
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddCountry().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewCountry;
    private javax.swing.JButton cancelNewCountry;
    private javax.swing.JTextField codeOfCountryField;
    private javax.swing.JLabel codeOfCountryLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField nameOfCountryField;
    private javax.swing.JLabel nameOfCountryLabel;
    // End of variables declaration//GEN-END:variables
}
