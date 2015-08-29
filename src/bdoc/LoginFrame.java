
package bdoc;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginFrame extends javax.swing.JFrame {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    String roleLev1 = "administrator";
    String roleLev2 = "redactor";
    String roleLev3 = "metodist";
    

    public LoginFrame() {
        initComponents();
        conn = dbconnector.getConnection();
    }

    private void adminPanelGui(String userName, String userFIO){
        JOptionPane.showMessageDialog(null, "Инициализация пользователя прошла успешно");
                closeWin();
                mainBdoc gl = new mainBdoc();
                gl.UserLoginText(userName,userFIO);
                gl.setVisible(true);
                
    }
    
    private void redactorPanelGui(String userName, String userFIO){
                JOptionPane.showMessageDialog(null, "Инициализация пользователя прошла успешно");
                closeWin();
                mainBdoc gl = new mainBdoc();
                gl.tabStudUchPlanButtons.setVisible(false);
                gl.jmenuSettings.setEnabled(false);
                gl.UserLoginText(userName,userFIO);
                gl.setVisible(true);
                
    }
    
    private void metodistPanelGui(String userName, String userFIO){
        JOptionPane.showMessageDialog(null, "Инициализация пользователя прошла успешно");
                closeWin();
                mainBdoc gl = new mainBdoc();
                gl.programmEditButton.setEnabled(false);
                gl.programmAddButton.setEnabled(false);
                gl.programmDeleteButton.setEnabled(false);
                gl.programmApplyButton.setEnabled(false);
                gl.addGroupButton.setEnabled(false);
                gl.editGroupButton.setEnabled(false);
                gl.cancelGroupButton.setEnabled(false);
                gl.deleteGroupButton.setEnabled(false);
                gl.updateTableGroups.setEnabled(false);
                gl.raznoskaButton.setEnabled(false);
                gl.studentAddButton1.setEnabled(false);
                gl.studentEditButton1.setEnabled(false);
                gl.studentCancelButton.setEnabled(false);
                gl.studentDeleteButton.setEnabled(false);
                gl.attachStudentImage.setEnabled(false);
                gl.tabStudUchPlanButtons.setVisible(false);
                gl.editUchPlgPanel.setVisible(false);
                gl.TabPaneSprav.setVisible(false);
                gl.jmenuSettings.setEnabled(false);
                gl.studPerevGroupButton.setEnabled(false);
                gl.UserLoginText(userName,userFIO);
                gl.setVisible(true);
    }
    
   
    public void closeWin(){
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelLogin = new javax.swing.JLabel();
        loginTextField = new javax.swing.JTextField();
        labelPassword = new javax.swing.JLabel();
        passwordTextField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mainFrameSttings = new javax.swing.JMenu();
        updateProgramm = new javax.swing.JMenu();
        aboutProgramm = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Панель входа");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 255, 174));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Панель входа", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Microsoft Sans Serif", 1, 24), new java.awt.Color(0, 0, 204))); // NOI18N

        labelLogin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelLogin.setText("Логин");

        labelPassword.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        labelPassword.setText("Пароль");

        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordTextFieldKeyPressed(evt);
            }
        });

        loginButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        loginButton.setText("Вход");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(labelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginTextField)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        mainFrameSttings.setText("Настройки");
        jMenuBar1.add(mainFrameSttings);

        updateProgramm.setText("Справка");

        aboutProgramm.setText("О программе");
        updateProgramm.add(aboutProgramm);

        jMenuItem2.setText("Обновления");
        updateProgramm.add(jMenuItem2);

        jMenuBar1.add(updateProgramm);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(399, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );

        setSize(new java.awt.Dimension(694, 474));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed

        String sql = "SELECT * FROM users WHERE user_login=? AND user_pass=?";
        
        try{
            pst=conn.prepareStatement(sql);
            pst.setString(1, loginTextField.getText());
            pst.setString(2, passwordTextField.getText());
            rs=pst.executeQuery();
            
            if (rs.next()){
                String userName = rs.getString("user_login");
                String userFIO = rs.getString("user_fio");
                String role = rs.getString("user_role");
                
                if(role.equals(roleLev1)){
                    adminPanelGui(userName, userFIO);
                }
                else if(role.equals(roleLev2)){
                    redactorPanelGui(userName, userFIO);
                }
                else if(role.equals(roleLev3)){
                    metodistPanelGui(userName, userFIO);
                }
                else{
                    JOptionPane.showMessageDialog(null, role+ "  is not correct Role");
                }
               
            }
            
            else{
                JOptionPane.showMessageDialog(null, "Username and Password is not correct");
            }
            
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            
        }
        
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passwordTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTextFieldKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            loginButtonActionPerformed(null);
        }
    }//GEN-LAST:event_passwordTextFieldKeyPressed
    
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
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutProgramm;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelLogin;
    private javax.swing.JLabel labelPassword;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextField loginTextField;
    private javax.swing.JMenu mainFrameSttings;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JMenu updateProgramm;
    // End of variables declaration//GEN-END:variables
}
