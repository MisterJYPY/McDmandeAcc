/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import vue.AcceuilleProduitFinis;
import vue.ContenuProduction;
import vue.ContenuRetour;
import vue.ContenuVente;
import vue.contenuDivers;
import vue.principal;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_AcceuilleProduitFinis {
    
      private AcceuilleProduitFinis vue;

    public ctr_AcceuilleProduitFinis(AcceuilleProduitFinis vue) {
        this.vue = vue;
        
    //pour les mouse
            this.vue.getBtnVente().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                 mettreColorNormal(vue.getBtnVente());
                placerVenteEnClick(evt);
               
            }
        });
         
            this.vue.getBtnProduction().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                effectuerProduction(evt);
                mettreColorNormal(vue.getBtnProduction());
            }
        });
              this.vue.getBtnRetours().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                effectuerRetour(evt);
                mettreColorNormal(vue.getBtnRetours());
            }
        });
              this.vue.getBtnDivers().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enregisterConsommation(evt);
                mettreColorNormal(vue.getBtnDivers());
            }
        });
                    }
    public void placerVenteEnClick(java.awt.event.MouseEvent evt)
    {
      //  System.out.println("pour v:"+this.vue.findComponentAt(evt.getPoint()));
      if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
       
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
         System.out.println("curser kkks");
    }
          ContenuVente vente=new ContenuVente();
        principal.placerPanel(vente,this.vue.getScrollPaneProduitFini());
        this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
    public void enregisterConsommation(java.awt.event.ActionEvent evt)
    {
                contenuDivers divers=new contenuDivers();
                 principal.placerPanel(divers,this.vue.getScrollPaneProduitFini());
    
    }
     public void enregisterConsommation(java.awt.event.MouseEvent evt)
    {
       if (this.vue.findComponentAt(evt.getPoint()) == null) {
        this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
                contenuDivers divers=new contenuDivers();
                 principal.placerPanel(divers,this.vue.getScrollPaneProduitFini());
          this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
      public void effectuerProduction(java.awt.event.ActionEvent evt)
      {
                ContenuProduction production=new ContenuProduction();
          
                 principal.placerPanel(production,this.vue.getScrollPaneProduitFini());
      }
       public void effectuerProduction(java.awt.event.MouseEvent evt)
               
      {
           if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
         }
                ContenuProduction production=new ContenuProduction();
                 principal.placerPanel(production,this.vue.getScrollPaneProduitFini());
                 this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
       public void effectuerRetour(java.awt.event.ActionEvent evt)
      {
       ContenuRetour retour=new ContenuRetour();
        principal.placerPanel(retour,this.vue.getScrollPaneProduitFini());
      }
       public void effectuerRetour(java.awt.event.MouseEvent evt)
      {
          if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
         }
       ContenuRetour retour=new ContenuRetour();
        principal.placerPanel(retour,this.vue.getScrollPaneProduitFini());
       this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
        public void effectuerVente(java.awt.event.ActionEvent evt)
      {
          
       ContenuVente vente=new ContenuVente();
        principal.placerPanel(vente,this.vue.getScrollPaneProduitFini());
      }
        /**
         * methode permettant de Changer la couleur des Boutons en fonction des
         * Clicks.Elle recupere le click verifie le nom de l'action et change sa couleur tout en
         * reinitialisant tous les autres boutons
         * @param btn L'objet Jbutton a mettre en parametre
         */
           public void mettreColorNormal(JButton btn)
    {
       String action=btn.getActionCommand();
        System.out.println(action);
         btn.setBackground(Color.blue);
        if("Production".equals(action))
        {
     
  //   this.vue.getBtnProduction().setBackground(new java.awt.Color(153,153,255));
     this.vue.getBtnRetours().setBackground(new java.awt.Color(137,26,43));
     this.vue.getBtnVente().setBackground(new java.awt.Color(51,102,0));
     this.vue.getBtnDivers().setBackground(new java.awt.Color(204,204,0));
        }
        if("Retours".equals(action))
        {
   
     this.vue.getBtnProduction().setBackground(new java.awt.Color(153,153,255));
   //  this.vue.getBtnRetours().setBackground(new java.awt.Color(137,26,43));
     this.vue.getBtnVente().setBackground(new java.awt.Color(51,102,0));
     this.vue.getBtnDivers().setBackground(new java.awt.Color(204,204,0));
        }
         if("Vente".equals(action))
         {
      
     this.vue.getBtnProduction().setBackground(new java.awt.Color(153,153,255));
     this.vue.getBtnRetours().setBackground(new java.awt.Color(137,26,43));
  //   this.vue.getBtnVente().setBackground(new java.awt.Color(51,102,0));
     this.vue.getBtnDivers().setBackground(new java.awt.Color(204,204,0));
         } 
           if("Divers".equals(action))
         {
      
     this.vue.getBtnProduction().setBackground(new java.awt.Color(153,153,255));
     this.vue.getBtnRetours().setBackground(new java.awt.Color(137,26,43));
     this.vue.getBtnVente().setBackground(new java.awt.Color(51,102,0));
 //    this.vue.getBtnDivers().setBackground(new java.awt.Color(204,204,0));
         } 
    
    }
}
