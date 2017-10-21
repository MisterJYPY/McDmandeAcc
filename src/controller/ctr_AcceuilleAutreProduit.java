/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.database;
import java.awt.Color;
import java.awt.Cursor;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.md_AcceuilleAutreProduit;
import vue.AcceuilleAutreProduit;
import vue.contenuBilanParFamilleAutreProduit;
import vue.contenuEntreeAutreProduit;
import vue.contenuEtatGlobalAutreProduit;
import vue.contenuSortieAutreProduit;
import vue.principal;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_AcceuilleAutreProduit {
    
    private final AcceuilleAutreProduit vue;
    private final md_AcceuilleAutreProduit model;

    public ctr_AcceuilleAutreProduit(AcceuilleAutreProduit vue) {
        this.vue = vue;
        model=new md_AcceuilleAutreProduit();
        
         AcceuilleAutreProduit.getDivers().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("DIVERS ENTENDU");
            }
        });
          AcceuilleAutreProduit.getEtatGlobal().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FaireEtatGlobal(evt);
                mettreColorNormal(AcceuilleAutreProduit.getEtatGlobal());
            }
        });
         AcceuilleAutreProduit.getEffectuerEntree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               mettreColorNormal(AcceuilleAutreProduit.getEffectuerEntree());
                effectuerEntree(evt);
             
            }
        });
           AcceuilleAutreProduit.getEffectuerSortie().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                effectuerSortie(evt);
                mettreColorNormal(AcceuilleAutreProduit.getEffectuerSortie());
                //System.out.println("ok sori");
            }
        });
             this.vue.getBtnFamille().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                affichageBilanParFamille(evt);
                mettreColorNormal(vue.getBtnFamille());
                //System.out.println("ok sori");
            }
        });
           
        
    }
    public void mettreColorNormal(JButton btn)
    {
       String action=btn.getActionCommand();
         btn.setBackground(Color.blue);
        if("Enregister une Entrée".equals(action))
        {
     //AcceuilleAutreProduit.effectuerEntree.setBackground(new java.awt.Color(255,255,204));
     AcceuilleAutreProduit.effectuerSortie.setBackground(new java.awt.Color(53,78,29));
     AcceuilleAutreProduit.etatGlobal.setBackground(new java.awt.Color(204, 255, 204));
     this.vue.getBtnFamille().setBackground(new java.awt.Color(255,204,204));
        }
        if("Enregister une Sortie".equals(action))
        {
     AcceuilleAutreProduit.effectuerEntree.setBackground(new java.awt.Color(255,255,204));
  //   AcceuilleAutreProduit.effectuerSortie.setBackground(new java.awt.Color(53,78,29));
     AcceuilleAutreProduit.etatGlobal.setBackground(new java.awt.Color(204,255,204));  
      this.vue.getBtnFamille().setBackground(new java.awt.Color(255,204,204));
        }
    if("Etat Global".equals(action))
         {
             AcceuilleAutreProduit.effectuerEntree.setBackground(new java.awt.Color(255,255,204));
             AcceuilleAutreProduit.effectuerSortie.setBackground(new java.awt.Color(53,78,29));
             //   AcceuilleAutreProduit.etatGlobal.setBackground(new java.awt.Color(204, 205, 204));
              this.vue.getBtnFamille().setBackground(new java.awt.Color(255,204,204));
         } 
     if("Par Famille".equals(action))
         {
             AcceuilleAutreProduit.effectuerEntree.setBackground(new java.awt.Color(255,255,204));
             AcceuilleAutreProduit.effectuerSortie.setBackground(new java.awt.Color(53,78,29));
              AcceuilleAutreProduit.etatGlobal.setBackground(new java.awt.Color(204, 205, 204));
            //  this.vue.getBtnFamille().setBackground(new java.awt.Color(255,204,204));
         } 
    
    }
    public void FaireEtatGlobal(java.awt.event.MouseEvent evt)
    {
       if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }   
       contenuEtatGlobalAutreProduit etatG=new contenuEtatGlobalAutreProduit();
        principal.placerPanel(etatG,AcceuilleAutreProduit.getSrolpanelAutreProduit());
       this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
       
    }
    public void effectuerEntree(java.awt.event.MouseEvent evt)
    {
       if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
        contenuEntreeAutreProduit entreeAutreP=new contenuEntreeAutreProduit();
           principal.placerPanel(entreeAutreP,AcceuilleAutreProduit.getSrolpanelAutreProduit());
         this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
         String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation WHERE id IN(SELECT cotation FROM fournisseursurcotation )) "
           + " AND id NOT IN (SELECT demandeAchat FROM demandesatisfaite_autreproduit) "
           + " AND id IN (SELECT demandeAchat FROM articlesdemandeachat WHERE article IN(SELECT id FROM articles WHERE famille!=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
         try {
            if(database.getInstance().nbreLigneRequete(sql4)==0)
            {
        String   message=" Aucune Entree Ne peut etre faite Actuellement\n"
                + " Car aucune demande d'achat Pouvant faire  \n"
                + " L'objet de Livraison Non encore effectué et concernant les articles \n"
                + " Non Matieres Premieres N'est Presente Dans la Base de Donnée.\n"
                + " Il est Possible que :\n"
                + " toutes les demandes d'achat ont entièrement été Livré pour qui concerne\n"
                + " les Produits Non matières Premieres. Cliquez sur Ok.\n"
                + "Merci";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
//               JOptionPane pane = new JOptionPane( message,
//
//JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
             //  JOptionPane pane =new JOptionPane(null, message,"Notification",JOptionPane.INFORMATION_MESSAGE, img2);
     JOptionPane.showMessageDialog(null, message,"Notification",JOptionPane.INFORMATION_MESSAGE, img2); 
//          JOptionPane pane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE,JOptionPane.OK_OPTION, img2);
//               pane.setBackground(Color.red);
//               pane.add(this.vue.getParent());
            }
        } catch (SQLException ex) {
            Logger.getLogger(contenuEntreeAutreProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
      public void effectuerSortie(java.awt.event.MouseEvent evt)
    {
       if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
         contenuSortieAutreProduit sortie=new contenuSortieAutreProduit();
          principal.placerPanel(sortie,AcceuilleAutreProduit.getSrolpanelAutreProduit());
         this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
       public void affichageBilanParFamille(java.awt.event.MouseEvent evt)
    {
       if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
         contenuBilanParFamilleAutreProduit bialn=new contenuBilanParFamilleAutreProduit();
          principal.placerPanel(bialn,AcceuilleAutreProduit.getSrolpanelAutreProduit());
         this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
     
}
