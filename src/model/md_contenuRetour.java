/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Magasin;
import classe.Personnel;
import classe.database;
import classe.retourProduit;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.md_contenuProduction.stocksProduitFinis;
import static model.md_contenuProduction.stocksProduitFinisEnMagasin;
import static model.md_contenuVente.genererRDIViaNom;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuRetour {
    
      /**
       * methode permetant de concretiser une vente donc de 
       * le mettre dans la base de donnee,elle prends en parametre une instance de 
       * RetourProduit et l'insere ensuite.le boolean retourner indique si tout c'est passer comme prevue
       * @param retourP une instance de retourProduit qui est une classe derivee de entreeProduit
       * @return une valeur True ou False selon le deroulement du programme
       */
      public boolean creerRetourProduit(retourProduit retourP)
     {
         boolean estOk=false;
       //debut
         Connection con=null;
   int  NvoStocksMag=stocksProduitFinisEnMagasin(retourP.getMagasin())+retourP.getQuantite();
        String sql="INSERT INTO `retour_produit_fini`( `bordereaux`, `quantite`, `stocks_avant`, `stocks_apres`, `date`, `dateString`,`personnel`,`magasin`) "
        + " VALUES ('"+retourP.getLibelle()+"',"+retourP.getQuantite()+",'"+retourP.getStocks_avant()+"',"+retourP.getStocks_apres()+",'"+retourP.getDate()+"','"+retourP.getDateString()+"',"+retourP.getRdi().getId()+","+retourP.getMagasin().getId()+")"
        + "";
     String sqlActualisationStoks="UPDATE `stocks_produit_fini` SET `quantite`="+retourP.getStocks_apres()+" WHERE quantite="+retourP.getStocks_avant()+"";
     String sqlActualisationMagasin="UPDATE `stocks_magasin_produit_fini` SET `quantite`="+NvoStocksMag+" WHERE magasin="+retourP.getMagasin().getId()+" ";    
     //System.out.println("ECRITURE DE LA DATE:"+sortie.getDate());
       boolean a=false,b= false,c=false;
               try {
                database.getInstance().getCon().setAutoCommit(false);
               a= database.getInstance().InserData(sql);
               if(a){
                b= database.getInstance().InserData(sqlActualisationStoks);
               }
               if(b)
               {
               c= database.getInstance().InserData(sqlActualisationMagasin);
                  if(c)
                  {
                  estOk=true;
                  }
               }
                   database.getInstance().getCon().commit();
                   database.getInstance().getCon().setAutoCommit(true);   
               } catch (SQLException ex) {
           Logger.getLogger(md_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
                
            }
         
        
        
        //fin
        return estOk;
     }
      /**
       * methode permetant de renvoyer la Liste des Retours en Fonction de la requete
       * la requete est specifier pour preciser la periode que on veut faire afficher
       * @param requete la requete
       * @return  une Liste de Retour qu'on pourra se servir pour remplir le Jtable souhaiter
       */
       public static ArrayList listeDesReTourProduits(String requete)
     {
        ArrayList<retourProduit> liste=new ArrayList();
            retourProduit retourProduit;
            Personnel per;
            Magasin mag;
            try {
           ResultSet res=database.getInstance().selectInTab(requete);
             while(res.next())
             {
             //recuperation des information
           int id=res.getInt("id");
           String dateString=res.getString("dateString");
           String bordereaux=res.getString("bordereaux");
           int quantite=res.getInt("quantite");
           int stocks_avant=res.getInt("stocks_avant");
           int stocks_apres=res.getInt("stocks_apres");
               //partie magasin
             int idMagasin=res.getInt("idM");
            String magasin=res.getString("designationM");
             mag=new Magasin(idMagasin);
             mag.setDesignation(magasin);
              //partie personel
              int idPersonne=res.getInt("idP");
              String nomPersonne=res.getString("nomP");
              String prenomPersonne=res.getString("prenomP");
             per=new Personnel(idPersonne);
             per.setNom(nomPersonne);
             per.setPrenom(prenomPersonne);
             //assignement des valeurs
             retourProduit=new retourProduit(per);
             retourProduit.setId(id);
             retourProduit.setDateString(dateString);
             retourProduit.setQuantite(quantite);
             retourProduit.setStocks_avant(stocks_avant);
             retourProduit.setStocks_apres(stocks_apres);
             retourProduit.setLibelle(bordereaux);
             retourProduit.setMagasin(mag);
              //
              liste.add(retourProduit);
              //reinitialisation des Objets
             retourProduit=null;
             per=null;
             }
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuRetour.class.getName()).log(Level.SEVERE, null, ex);
            }
          return liste;
     }
      public retourProduit genererRetourPruitFini(int quantite,String Magasin,java.sql.Date dates,String libelle,String dateString,String type,String NomRDI,String PrenomRDI)
     {
         int[] tab;
       Personnel personnel=genererRDIViaNom(NomRDI, PrenomRDI);  
       retourProduit entrep=new retourProduit(personnel);
      Magasin mgasin=ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation(Magasin);
         int stocks_avant=stocksProduitFinis();
         int stocks_apres=stocks_avant+quantite;
         
         entrep.setDate(dates);
         entrep.setMagasin(mgasin);
         entrep.setQuantite(quantite);
         entrep.setStocks_avant(stocks_avant);
         entrep.setStocks_apres(stocks_apres);
         entrep.setLibelle(libelle);
         entrep.setDateString(dateString);
         entrep.setType(type);
        // entrep.setRdi(personnel);
         
         return entrep;
     }
}
