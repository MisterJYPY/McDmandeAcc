/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import static com.sun.javafx.tk.Toolkit.getToolkit;
import java.awt.Color;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Mr_JYPY
 */
public class JoptionPane {
       
    
      
        public static void showBlack(String message) throws UnsupportedLookAndFeelException {
            
        try {
            // ImageIcon img=new ImageIcon(, message);
              
              ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
                //.out.println(img2);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("OptionPane.background", Color.BLACK);
            UIManager.put("Panel.background", Color.BLACK);
            UIManager.put("OptionPane.messageForeground", Color.red);
           JOptionPane.showMessageDialog(null, message);
            // JOptionPane.showMessageDialog(null, message,"Erreur Champs",  JOptionPane.INFORMATION_MESSAGE, img2);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JoptionPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(JoptionPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JoptionPane.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(JoptionPane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public static void  ErrorMessage()
        {
        }
    
}
