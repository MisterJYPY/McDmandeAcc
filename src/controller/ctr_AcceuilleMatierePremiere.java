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
import vue.AcceuilleMatierePremiere;
import vue.contenuEntreeAutreProduit;
import vue.contenuEntreeMatierePremiere;
import vue.contenuEtatGlobal;
import vue.contenuSortieMatierePremiere;
import vue.principal;

/**
 *<strong>Description</strong>
 *   <p>cette classe sert de controleur a l'acceuille des Matieres Premieres
 ,Elle ecoute Tous les boutons dans le menu de AcceuilleMatierePremieres et execute le code correspondant ou
 place le panel cooorespondant</p>
 * @author Mr_JYPY
 * 
 */
public class ctr_AcceuilleMatierePremiere {
     
     private  AcceuilleMatierePremiere vue;
    public ctr_AcceuilleMatierePremiere(AcceuilleMatierePremiere vue) {
        this.vue=vue;
        /**
         * cette Partie Sert d'ecoute au bouton effectuer entree
         */

              /**
         * cette Partie Sert d'ecoute au bouton VUE Globale
         */
//            AcceuilleMatierePremiere.etatGlobal.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                etatGlobale(evt);
//            }
//        });   
            
                /**
         * cette Partie Sert d'ecoute au bouton DIVERS
         */
            AcceuilleMatierePremiere.divers.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreDivers(evt);
            }
        });   
            //pour les Mouse
          AcceuilleMatierePremiere.effectuerEntree.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                  mettreColorNormal(AcceuilleMatierePremiere.getEffectuerEntree());
                effectuerEntree(evt);
            }
        });
           AcceuilleMatierePremiere.effectuerSortie.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                effectuerSortie(evt);
                mettreColorNormal(AcceuilleMatierePremiere.getEffectuerSortie());
            }
        });
            AcceuilleMatierePremiere.etatGlobal.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etatGlobale(evt);
                mettreColorNormal(AcceuilleMatierePremiere.getEtatGlobal());
            }
        });
    }
    //
    /**
     * 
     * @param evt 
     */
      public void effectuerEntree(java.awt.event.ActionEvent evt){
          /**
           * mettre une image dattente
           */
    
     contenuEntreeMatierePremiere contenuMp=new contenuEntreeMatierePremiere();
               principal.placerPanel(contenuMp,AcceuilleMatierePremiere.SrolpanelMatierePremiere);
              AcceuilleMatierePremiere.effectuerEntree.setText("Effectuer une Entrée");
              
      }
       public void effectuerEntree(java.awt.event.MouseEvent evt){
          /**
           * mettre une image dattente
           */
                if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
     contenuEntreeMatierePremiere contenuMp=new contenuEntreeMatierePremiere();
               principal.placerPanel(contenuMp,AcceuilleMatierePremiere.SrolpanelMatierePremiere);
            this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
            
             String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation) AND service=(SELECT id FROM services WHERE designation='ACHAT ET PRODUCTION') "
           + " AND demandeachat.id NOT IN(SELECT demandeAchat FROM demandesatisfaite)"
           + " AND id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT id FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
   
         try {
            if(database.getInstance().nbreLigneRequete(sql4)==0)
            {
        String   message=" Aucune Entree Ne peut etre faite Actuellement\n"
                + " Car aucune demande d'achat Pouvant faire  \n"
                + " L'objet de Livraison Non encore effectué et concernant les articles \n"
                + " De la Famille des Matieres Premieres N'est Presente Dans la Base de Donnée.\n"
                + " Il est Possible que :\n"
                + " toutes les demandes d'achat ont entièrement été Livré pour qui concerne\n"
                + " les Articles Matieres Premieres matières Premieres. Cliquez sur Ok.\n"
                + "Merci";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
     JOptionPane.showMessageDialog(null, message,"Notification",JOptionPane.INFORMATION_MESSAGE, img2); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(contenuEntreeAutreProduit.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
       public void effectuerSortie(java.awt.event.ActionEvent evt){
              contenuSortieMatierePremiere Sortie=new contenuSortieMatierePremiere();
              principal.placerPanel(Sortie,AcceuilleMatierePremiere.SrolpanelMatierePremiere);
      }
        public void effectuerSortie(java.awt.event.MouseEvent evt){
            if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
              contenuSortieMatierePremiere Sortie=new contenuSortieMatierePremiere();
              principal.placerPanel(Sortie,AcceuilleMatierePremiere.SrolpanelMatierePremiere);
              this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
       public void etatGlobale(java.awt.event.MouseEvent evt){
            if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
          contenuEtatGlobal etat=new contenuEtatGlobal();
          principal.placerPanel(etat,AcceuilleMatierePremiere.SrolpanelMatierePremiere);
         this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); 
      }
          public void mettreDivers(java.awt.event.ActionEvent evt){
           System.out.print("DIVERS");
      }
           public void mettreColorNormal(JButton btn)
    {
       String action=btn.getActionCommand();
        System.out.println(action);
         btn.setBackground(Color.blue);
        if("Enregister une Entrée".equals(action))
        {
     //AcceuilleAutreProduit.effectuerEntree.setBackground(new java.awt.Color(163, 163, 11));
     AcceuilleMatierePremiere.effectuerSortie.setBackground(new java.awt.Color(53, 78, 29));
     AcceuilleMatierePremiere.etatGlobal.setBackground(new java.awt.Color(204, 204, 204));
        }
        if("Enregister une Sortie".equals(action))
        {
   AcceuilleMatierePremiere.effectuerEntree.setBackground(new java.awt.Color(163, 163, 11));
   //  AcceuilleMatierePremiere.effectuerSortie.setBackground(new java.awt.Color(53, 78, 29));
     AcceuilleMatierePremiere.etatGlobal.setBackground(new java.awt.Color(204, 204, 204));
        }
         if("Faire un Etat Global".equals(action))
         {
     AcceuilleMatierePremiere.effectuerEntree.setBackground(new java.awt.Color(163, 163, 11));
     AcceuilleMatierePremiere.effectuerSortie.setBackground(new java.awt.Color(53, 78, 29));
   //  AcceuilleMatierePremiere.etatGlobal.setBackground(new java.awt.Color(204, 204, 204));
         } 
    
    }
}
