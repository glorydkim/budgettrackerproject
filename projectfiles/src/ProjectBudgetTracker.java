/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectbudgettracker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author glorykim
 */
public class ProjectBudgetTracker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //Network server control to automatically start server
        NetworkServerControl s = new NetworkServerControl();
        //Added shutdown to close server nicely
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            public void run(){
                System.out.println("shutdown");
                try {
                    s.shutdown();
                } catch (Exception ex) {
                    Logger.getLogger(ProjectBudgetTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, "Shutdown-thread"));
        //Start Server
        s.start(new PrintWriter(System.out,true));
      
       
  /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeScreenFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            
            public void run() {
                try {
                    new HomeScreenFrame("Manager").setVisible(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProjectBudgetTracker.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ProjectBudgetTracker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
