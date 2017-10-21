/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JButton;
import vue.A_Propos;
import vue.AcceuilleAutreProduit;
import vue.AcceuilleMatierePremiere;
import vue.AcceuilleProduitFinis;
import admin.vue.adminSecure;
import vue.contenuPrincipal;
import vue.principal;

/**
 *c'est la classe du controlleur de l'objet Principal
 * elle pointe sur la vue qui est la classe principale.java
 * permet de gerer les clicks de Boutons ou de fermerture de la connection depuis la classe principal.java
 * @author Mr_JYPY
 * @version v.1
 * @since le 17 janvier 2017 
 */
public class ctr_principal {
      /**
       * une reference de l'objet Principal(le menu principal de lappli)
       * 
       * @since le 17 janvier 2017
       */
        principal pc;

    public ctr_principal(principal pc) {
        this.pc = pc;
        //pour l'action sur deconnection
        this.pc.getDeconnection().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fermerConnection(evt);
            }
        });    
       //pour laction sur matierePriem
         this.pc.getMatierePremiere().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ouvrirMatierePremiere(evt);
                 mettreColorNormal(pc.getMatierePremiere());
            }
        });    
               this.pc.getAcceuille().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreAcceuille(evt);
                mettreColorNormal(pc.getAcceuille());
            }
        });   
               
          this.pc.getProduitFinis().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AfficherProduitFinis(evt);
                mettreColorNormal(pc.getProduitFinis());
            }
        });   
           this.pc.getAutresArticles().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AfficherAutreArticles(evt);
                 mettreColorNormal(pc.getAutresArticles());
            }
        });   
           
            //ici les actions liées aux click de bouton des Menu
        this.pc.getConcepteur().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A_Propos.getInstance().setVisible(true);
                A_Propos.getInstance().setResizable(false);
                A_Propos.getInstance().setLocationRelativeTo(null);
            }
        });   
        
        
        this.pc.getMenuAdmin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
  
                adminSecure.getInstance(pc).setVisible(true);
                adminSecure.getInstance(pc).setResizable(false);
                  adminSecure.getInstance(pc).setLocationRelativeTo(null);
                
            }
        });   
    
    }
       //action pour le bouton admin
         
    /**
     * sert a changer la couleur du bouton en fonction du click
     * @param btn 
     */
     public void mettreColorNormal(JButton btn)
    {
       String action=btn.getActionCommand();
         btn.setBackground(new java.awt.Color(81,100,104));
        if("AUTRES PRODUITS".equals(action))
        {
     this.pc.getMatierePremiere().setBackground(new java.awt.Color(240,240,240));
     this.pc.getProduitFinis().setBackground(new java.awt.Color(255,255,255));
   // this.pc.getAutresArticles().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAcceuille().setBackground(new java.awt.Color(240,240,240));
        }
        if("MATIERES PREMIERES".equals(action))
        {
   //  this.pc.getMatierePremiere().setBackground(new java.awt.Color(240,240,240));
     this.pc.getProduitFinis().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAutresArticles().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAcceuille().setBackground(new java.awt.Color(240,240,240)); 
        }
         if("PRODUITS FINIS".equals(action))
         {
     this.pc.getMatierePremiere().setBackground(new java.awt.Color(240,240,240));
  //   this.pc.getProduitFinis().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAutresArticles().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAcceuille().setBackground(new java.awt.Color(240,240,240));
         } 
         if("ACCEUIL".equals(action))
     {
     this.pc.getMatierePremiere().setBackground(new java.awt.Color(240,240,240));
     this.pc.getProduitFinis().setBackground(new java.awt.Color(255,255,255));
     this.pc.getAutresArticles().setBackground(new java.awt.Color(255,255,255));
 //    this.pc.getAcceuille().setBackground(new java.awt.Color(240,240,240));
     } 
    
    }
      public void AfficherAutreArticles(java.awt.event.ActionEvent evt)
      {
          AcceuilleAutreProduit AutreProduit=AcceuilleAutreProduit.getInstance();
            principal.placerPanel(AutreProduit,this.pc.getScropMilieu());
      }
      public void fermerConnection(java.awt.event.ActionEvent evt){
           System.exit(0);
           System.out.println("gdggdg");
      }
       public void ouvrirMatierePremiere(java.awt.event.ActionEvent evt)
        {
          AcceuilleMatierePremiere Acceuimpremiere=AcceuilleMatierePremiere.getInstance(); 
          
           principal.placerPanel(Acceuimpremiere,this.pc.getScropMilieu());
            
        }
       public void mettreAcceuille(java.awt.event.ActionEvent evt)
       {
             AcceuilleMatierePremiere.SetNull();
             AcceuilleProduitFinis.SetNull();
             AcceuilleAutreProduit.SetNull();
           
             contenuPrincipal ctp=new contenuPrincipal(this.pc);
             principal.placerPanel(ctp, this.pc.getScropMilieu());
       }
         public void AfficherProduitFinis(java.awt.event.ActionEvent evt)
       {
            AcceuilleProduitFinis Acpfini=AcceuilleProduitFinis.getInstance(this.pc);
             principal.placerPanel(Acpfini, this.pc.getScropMilieu());
       }
}
