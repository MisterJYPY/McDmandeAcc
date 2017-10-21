/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.MD5Password;
import classe.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *c'est la classe qui propose les methodes 
 * permettant de verifier l'existance d'un couple login passwprd
 * @author Mr_JYPY
 */
public class md_securite {
     
        database dt;
        ResultSet rs=null;
        /**
         * Verifier l'existence d'un couple(log,pass)
         * @param login le login du client
         * @param password sont mot de passe
         * @return un boolean permettant de verifier si il ya un resultat ou pas
         */
     public boolean PresenceDeCouple(String login,String password)
           {
           boolean t=false;
            int nbre=0;
            password=MD5Password.getEncodedPassword(password);
               String sql="SELECT comptes_personnel.login,comptes_personnel.password,personnel.id,personnel.nom,personnel.prenom FROM comptes_personnel "
                       + " INNER JOIN personnel on comptes_personnel.personnel=personnel.id"
                       + " WHERE login='"+login+"' AND Password='"+password+"' AND "
                       + " personnel IN (SELECT id FROM personnel WHERE fonction='MAGASINIER')"
                       ;
            //System.out.println(sql);
                 dt=database.getInstance();
            try {
                rs=dt.selectInTab(sql);
                 nbre=dt.nbreLigneRequete(sql);
                 if(nbre>0){
                     t=true;
                     /**
                      * permet de fermer la connection
                      */
                   //  System.out.println(RenvoyerLeNom());
                  //dt.fermerConnection();
                 }
                 
            } catch (SQLException ex) {
                Logger.getLogger(md_securite.class.getName()).log(Level.SEVERE, null, ex);
            }
            return t;
           }
       /**
        * 
        * @return  Le nom de Tout le Nom concatener Au Prenom de La Personne Connecter
        */
       public String RenvoyerLeNom(){
             String nom="";
             String Prenom="";
            try {
                while(rs.next()){
                  nom=rs.getString("nom");
                  Prenom=rs.getString("prenom");
                }
            } catch (SQLException ex) {
                Logger.getLogger(md_securite.class.getName()).log(Level.SEVERE, null, ex);
            }
                String st="";
                  st=nom+" "+Prenom;
                 return st;
          }
       /**
        * @since Le 17 janvier 2017 a 13h28
        * @return l'id de la personne connecter
        */
       public int RenvoyerID(){
                int st=0;
                 return st;
          }
       /**
        * 
        * @param id l'identifiant de la personne connecter
        * @return un boolean pour verifier si la session a bien ete creer ou pas
        */
       public  boolean CreerSession(int id){
                boolean st=false;
                return st;
          }
    
}
