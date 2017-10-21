/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.vue.contenuAdmin;
import admin.vue.contenuModifierEntreeAp;
import admin.vue.contenuModifierEntreeMp;
import admin.vue.contenuModifierEntreePf;
import java.awt.Cursor;
import vue.principal;

/**
 *
 * @author Mr_JYPY
 */
 


public class ctr_contenuAdmin {
    
    private contenuAdmin vue;
    
    
    public ctr_contenuAdmin(contenuAdmin vue) {
        this.vue = vue;
        
          //mettre les actions sous les boutons de la vue
           //pour la partie matiere Premiere
            this.vue.getModifEntreeMp().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
                traiterBtnModifEntreeMp(evt);
            }
        });
            
             this.vue.getModifSortieMp().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
                traiterBtnModifSortieMp(evt);
            }
        });
             
             //pour les autres produits
             
               this.vue.getModifEntreeAp().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
               traiterBtnModifEntreeAp(evt);
            }
        });
            
             this.vue.getModifSortieAp().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
                traiterBtnModifSortieAp(evt);
            }
        });
             
             //pour les produits finis
             
            this.vue.getModifEntreePf().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
              traiterBtnModifEntreePf(evt);
            }
        });
            
             this.vue.getModifSortiePf().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              //validerProduction(evt);
               traiterBtnModifSortiePf(evt);
            }
        });
    }

    public contenuAdmin getVue() {
        return vue;
    }

    public void setVue(contenuAdmin vue) {
        this.vue = vue;
    }
    /*
     debut des methodes de traitement des boutons de modification
    depuis la vue globale de la page dadministration
    */
    
    public void traiterBtnModifEntreeMp(java.awt.event.MouseEvent evt)
    {
         if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }   
        contenuModifierEntreeMp moMp=new contenuModifierEntreeMp();
        principal.placerPanel(moMp,this.vue.getScrollAdmin());
        
        this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) ;
    }
    public void traiterBtnModifEntreeAp(java.awt.event.MouseEvent evt)
    {
         if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
         
        contenuModifierEntreeAp moAp=new contenuModifierEntreeAp();
        principal.placerPanel(moAp,this.vue.getScrollAdmin());
        
             
        this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) ;
    }
    public void traiterBtnModifEntreePf(java.awt.event.MouseEvent evt)
    {
          if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
        
        contenuModifierEntreePf moPf=new contenuModifierEntreePf();
        principal.placerPanel(moPf,this.vue.getScrollAdmin());
        
         this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
    /*
     Fin des methodes de traitement des boutons de modification
    depuis la vue globale de la page dadministration et debut de ceux des sorties
    */
     public void traiterBtnModifSortieMp(java.awt.event.MouseEvent evt)
    {
          if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
          System.out.println("modif sortie mat premiere");
          
               this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
      public void traiterBtnModifSortieAp(java.awt.event.MouseEvent evt)
    {
        if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
        
          System.out.println("modif sortie mat premiere");
          
     this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
       public void traiterBtnModifSortiePf(java.awt.event.MouseEvent evt)
    {
        if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            } 
         
        
          System.out.println("modif sortie mat premiere");
          
     this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
    }
}
