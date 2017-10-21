/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import vue.contenuPrincipal;

/**
 *la classe du controlleur par le tres Joyeux et Paresseux Mister_JYPY
 * excusez moi je kozais avec ma machine(mon ami(e) fidele)
 * @author Mr_JYPY
 * @since le 17 janvier 2017 dans la salle de Reunion de mccroft Tobacco Zone industrielle
 */
  
public class ctr_contenuPrincipal {
    
     
      private final contenuPrincipal contenuPrincipal;
   
    public ctr_contenuPrincipal(contenuPrincipal ctp) {
         this.contenuPrincipal=ctp;
         //this.contenuPrincipal.getNomConnecter().setText(this.pcp.getNomConnecter().toString());
          this.contenuPrincipal.getNomConnecter().setText(this.contenuPrincipal.getPcipal().getNomMagasinier());
         System.out.println(this.contenuPrincipal.getPcipal().getjPanel1().getWidth());
         System.out.println(this.contenuPrincipal.getPcipal().getjPanel1().getHeight());
    }
      
      
}
