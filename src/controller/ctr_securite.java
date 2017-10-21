/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.database;
import model.md_securite;
import vue.principal;
import vue.vue_securite;

/**
 *classe du controlleur de Securité
 * il permet de Verifier la saisies et de Faire le Controle 
 * authenticité de l'utilisateur
 * @author Mr_JYPY
 */
public class ctr_securite {
    
       vue_securite secure;
       principal principale;
       database dt;
       md_securite md;
       /**
        * controller
        * @param secure 
        */
    public ctr_securite(vue_securite secure) {
         dt=database.getInstance();
        this.secure = secure;
        this.md=new md_securite();
        
         
         this.secure.getEnter() .addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterAction(evt);
            }
        });
    }

    public database getDt() {
        return dt;
    }
       
      public boolean verifierDonnee(String login,String pass){
         boolean b=false;
            if(login.isEmpty() || pass.isEmpty()){
      secure.getMessageError().setText("!!!!!!!!!!!!!!!!!!!Remplissez Tous Vos Champs SVP!!!!!!!!!");
                }
            else{
                 if(md.PresenceDeCouple(login, pass))
                 {
                      afficherMenuPrincipal();
                 }
                 else{
              secure.getMessageError().setText("!!!!!!!!!!!!Identifiants Invalide!!!!!!!!!");
                 }
              b=true;
               }
          return b;
      }    
     public void enterAction(java.awt.event.ActionEvent evt){
        verifierDonnee(secure.getLogin().getText(),secure.getPass().getText());
          
     }
     /**
      * methode permettant de 
      * creer une instance de la classe Principal et de l'afficher,
      * utiliser dans dans le controler de securite
      */
       public void afficherMenuPrincipal(){
              String Nom=this.md.RenvoyerLeNom();
             this.secure.setVisible(false);
             this.principale=new principal(Nom);
             principale.setVisible(true);
       }
   
}