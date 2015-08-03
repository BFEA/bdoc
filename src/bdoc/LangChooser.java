
package bdoc;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;


public class LangChooser extends javax.swing.JFrame {


    public LangChooser() {
        initComponents();
    }
    
    public void closeWin(){
        WindowEvent winClosingEvent = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chooserPanel = new javax.swing.JPanel();
        kgButton = new javax.swing.JButton();
        ruButton = new javax.swing.JButton();
        enButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Выбрать язык настроек");
        setResizable(false);

        chooserPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        kgButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kg_icon.png"))); // NOI18N
        kgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kgButtonActionPerformed(evt);
            }
        });

        ruButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ru_icon.png"))); // NOI18N
        ruButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ruButtonActionPerformed(evt);
            }
        });

        enButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eng_icon.png"))); // NOI18N
        enButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chooserPanelLayout = new javax.swing.GroupLayout(chooserPanel);
        chooserPanel.setLayout(chooserPanelLayout);
        chooserPanelLayout.setHorizontalGroup(
            chooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chooserPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(kgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ruButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(enButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        chooserPanelLayout.setVerticalGroup(
            chooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chooserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chooserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ruButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kgButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(chooserPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chooserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(267, 108));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kgButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kgButtonActionPerformed
      SettingsKG kg = new SettingsKG();
      kg.setVisible(true);
      closeWin();
    }//GEN-LAST:event_kgButtonActionPerformed

    private void ruButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ruButtonActionPerformed
      Settings ru = new Settings();
      ru.setVisible(true);
      closeWin();
    }//GEN-LAST:event_ruButtonActionPerformed

    private void enButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enButtonActionPerformed
      SettingsEN en = new SettingsEN();
      en.setVisible(true);
      closeWin();
    }//GEN-LAST:event_enButtonActionPerformed

   
    public static void main(String args[]) {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LangChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LangChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LangChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LangChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LangChooser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chooserPanel;
    private javax.swing.JButton enButton;
    private javax.swing.JButton kgButton;
    private javax.swing.JButton ruButton;
    // End of variables declaration//GEN-END:variables
}
