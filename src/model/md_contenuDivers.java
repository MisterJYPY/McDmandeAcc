/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Magasin;
import classe.consommationProduitFini;
import classe.database;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.md_contenuProduction.stocksProduitFinis;
import static model.md_contenuProduction.stocksProduitFinisEnMagasin;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuDivers {
    
    /**
     * methode Permettant de Retourner Une Instance de consommationProduitFini qui servira
     * a le stocker en base de donnee via la methode de Creation
     * @param quantite la quantite consommer
     * @param Magasin  le Magasin d'ou est sorti le Produit
     * @param dates    la dates a laquelle loperation c'est effectuée
     * @param libelle   le bordereaux
     * @param dateString la date sous format String
     * @param motif      le Motif
     * @return     une instance de consommationProduitFini
     */
     public consommationProduitFini genererConsoPruitFini(int quantite,String Magasin,java.sql.Date dates,String libelle,String dateString,String motif)
     {
         
       consommationProduitFini conso=new consommationProduitFini();
      Magasin mgasin=ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation(Magasin);
         int stocks_avant=stocksProduitFinis();
         int stocks_apres=stocks_avant-quantite;
         
         conso.setDate(dates);
         conso.setMagasin(mgasin);
         conso.setQuantite(quantite);
         conso.setStocks_avant(stocks_avant);
         conso.setStocks_apres(stocks_apres);
         conso.setLibelle(libelle);
         conso.setDateString(dateString);
         conso.setMotif(motif);
        // conso.setRdi(personnel);
         
         return conso;
     }
     /**
      * methode static permettant de generer une liste <strong>d'instance de consommationProduitFini</strong>
      * une classe derivée de retourProdui
      * @param requete la requete sql pour avoir les donnees
      * @return 
      */
       public static ArrayList listeDesConsoProduits(String requete)
     {
        ArrayList<consommationProduitFini> liste=new ArrayList();
            consommationProduitFini conso;
            Magasin mag;
            try {
           ResultSet res=database.getInstance().selectInTab(requete);
             while(res.next())
             {
             //recuperation des information
                 conso=new consommationProduitFini();
           int id=res.getInt("id");
           String dateString=res.getString("dateString");
           String bordereaux=res.getString("bordereaux");
           String motif=res.getString("motif");
           int quantite=res.getInt("quantite");
           int stocks_avant=res.getInt("stocks_avant");
           int stocks_apres=res.getInt("stocks_apres");
               //partie magasin
             int idMagasin=res.getInt("idM");
            String magasin=res.getString("designationM");
             mag=new Magasin(idMagasin);
             mag.setDesignation(magasin);
             
             //assignement des valeurs
             conso.setId(id);
             conso.setDateString(dateString);
             conso.setQuantite(quantite);
             conso.setStocks_avant(stocks_avant);
             conso.setStocks_apres(stocks_apres);
             conso.setLibelle(bordereaux);
             conso.setMagasin(mag);
             conso.setMotif(motif);
              //
              liste.add(conso);
              //reinitialisation des Objets
             conso=null;
             mag=null;
             }
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuRetour.class.getName()).log(Level.SEVERE, null, ex);
            }
          return liste;
     }
       /**
        *<p> methode permettant d'inserer un divers dans la partie <strong>divers</strong> du module produit fini en base de Donnee</p>
        * le parametre donnee est une instance de <strong>consommationProduitFini</strong> qui lui
        * meme est une classe derivée de <strong>entreeProduit</strong>
        * lors de l'insertion en base de donnée il actualise le stocks en Magasin et dans l'entrepot en general
        * @param conso
        * @return 
        */
  public boolean creerConsoProduit(consommationProduitFini conso)
     {
         boolean estOk=false;
       //debut
         Connection con=null;
   int  NvoStocksMag=stocksProduitFinisEnMagasin(conso.getMagasin())-conso.getQuantite();
        String sql="INSERT INTO `consommation_produit_fini`( `bordereaux`, `quantite`, `stocks_avant`, `stocks_apres`, `date`, `dateString`,`magasin`,`motif`) "
        + " VALUES ('"+conso.getLibelle()+"',"+conso.getQuantite()+",'"+conso.getStocks_avant()+"',"+conso.getStocks_apres()+",'"+conso.getDate()+"','"+conso.getDateString()+"',"+conso.getMagasin().getId()+",'"+conso.getMotif()+"')"
        + "";
     String sqlActualisationStoks="UPDATE `stocks_produit_fini` SET `quantite`="+conso.getStocks_apres()+" WHERE quantite="+conso.getStocks_avant()+"";
     String sqlActualisationMagasin="UPDATE `stocks_magasin_produit_fini` SET `quantite`="+NvoStocksMag+" WHERE magasin="+conso.getMagasin().getId()+" ";    
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

}
