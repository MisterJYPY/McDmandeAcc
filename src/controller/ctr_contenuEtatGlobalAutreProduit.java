/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.execution;
import java.awt.Cursor;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import vue.contenuEtatGlobalAutreProduit;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuEtatGlobalAutreProduit {
    
    contenuEtatGlobalAutreProduit vue;

    public ctr_contenuEtatGlobalAutreProduit(contenuEtatGlobalAutreProduit vues) {
        this.vue = vues;
       
         this.vue.getImprimerEntree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
//                 ImpreesionTableEntree(evt);
//                System.out.println("bien entendu entree");
                if (vue.findComponentAt(evt.getPoint()) == null) {
         vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
                vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
                  String entete="BILAN PERIODIQUE DES SORTIES Autres-P";
       String Pied="Edité Par Mc croft Application G_Stocks le : "+new Date().toString();
      // ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableSortie(), entete, Pied);
        if(execution.nbreInstance<2){
                        execution exe =new execution(vue.getTableSortie(),entete,Pied);
                        exe.start();
                    }
                //on signifie cela par un JOPTION PANE
                  else{
                String message="Il y a deux Proccesus D'impressions en cours \n  "
                 + "Veuillez attendre leur achevement avant de Lancer une autre SVP \n"
                  + " Merci";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Proccessus en cours...",  JOptionPane.INFORMATION_MESSAGE, img2); 
                    }
        vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
            }
        });
            
            //initialisation des actions sur les Boutons d'impressions
         //pour les mouse
            this.vue.getImprimerSortie().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
//                 ImpreesionTableSortie(evt);
//                System.out.println("bien entendu sortie");
                  if (vue.findComponentAt(evt.getPoint()) == null) {
         vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
                vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
                    String entete="BILAN PERIODIQUE DES SORTIES Autres-P";
       String Pied="Edité Par Mc croft Application G_Stocks le : "+new Date().toString();
      // ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableSortie(), entete, Pied);
              //on lance l'execution du proccesus pour deux ecutions en cours maxi
                    if(execution.nbreInstance<2){
                        execution exes =new execution(vue.getTableSortie(),entete,Pied);
                        exes.start();
                    }
                //on signifie cela par un JOPTION PANE
                  else{
                String message="Il y a deux Proccesus D'impressions en cours \n  "
                 + "Veuillez attendre leur achevement avant de Lancer une autre SVP \n"
                  + " Merci";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Proccessus en cours...",  JOptionPane.INFORMATION_MESSAGE, img2); 
                    }
                      vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
            }
        });
    }
     /**
       * methode permettant de creer la page d'impression lorsque on clique
       * sur le Bouton format pdf de la vue contenuEtatGloablAutreProduit se trouvant dans 
       * la partie sortie
       * @param evt 
       */
      public void ImpreesionTableSortie(java.awt.event.MouseEvent evt)
      {
       String entete="BILAN PERIODIQUE DES SORTIES Autres-P";
       String Pied="Edité Par Mc croft Application G_Stocks le : "+new Date().toString();
       ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableSortie(), entete, Pied);
      }
      /**
       * methode permettant de creer la page d'impression lorsque on clique
       * sur le Bouton format pdf de la vue contenuEtatGloablAutreProduit  se trouvant dans 
       * la partie entree
       * @param evt 
       */
       public void ImpreesionTableEntree(java.awt.event.MouseEvent evt)
      {
       String entete="BILAN PERIODIQUE DES SORTIES Autres-P";
       String Pied="Edité Par Mc croft Application G_Stocks le : "+new Date().toString();
         ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableEntree(), entete, Pied);
      }
}
