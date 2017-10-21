/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.EntreeProduit;
import classe.Magasin;
import classe.database;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *le model lie a la vue contenuProduction
 * nous permettra d'avoir toutes les donnees
 * @author Mr_JYPY
 */
public class md_contenuProduction {
    
    private static database dtb;

    public md_contenuProduction() {
    }
   /**
    * methode permettant de creer une iinstance de entree produit specifique 
    * au Produit Fini.
    * Cette methode est la reecriture des preccedentes notemment dans la Partie acceuilleMP
    * 
    * au retour aussi
    * @param quantite la quantite prise dans le spiner
    * @param Magasin  le libelle du magasin
    * @param dates    la date travailler avec JdateChooser ou celle du jour
    * @param libelle  le bordereaux generer automatikement
     * @param dateString
     * @param type   le type de l'operation dentree de produit fini,Deux types possibles: RETOUR ou PRODUCTION
    * @return  une instance de EntreeProduit
    */
     public EntreeProduit genererEntrerPruitFini(int quantite,String Magasin,java.sql.Date dates,String libelle,String dateString,String type)
     {
        EntreeProduit entrep=new EntreeProduit();
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
         return entrep;
     }
     /**
      * Methode permettant d'avoir le stocks de Produit fini 
      * @return  le stocks En cours theorique de Produits Finis en Base de Donnee
      */
      public static int stocksProduitFinis()
      {
       int stk=0;
    String sql="SELECT quantite FROM stocks_produit_fini ";
    try {
            ResultSet res=database.getInstance().selectInTab(sql);
            while(res.next()){
                stk=res.getInt("quantite");
            }
        } catch (SQLException ex) {
            Logger.getLogger(md_contenuProduction.class.getName()).log(Level.SEVERE, null, ex);
        }
       return stk;
      }
       public static int stocksProduitFinisEnMagasin(Magasin magasin)
      {
        int stk=0;
      String sql="SELECT SUM(quantite) as quantite FROM stocks_magasin_produit_fini Where magasin="+magasin.getId()+"";
        try {
            ResultSet res=database.getInstance().selectInTab(sql);
            while(res.next()){
                stk=res.getInt("quantite");
            }
        } catch (SQLException ex) {
            Logger.getLogger(md_contenuProduction.class.getName()).log(Level.SEVERE, null, ex);
        }
      return stk;
      }
       public boolean creerUneEntreeProduitFini(EntreeProduit entrp)
       {
          boolean estOk=false;
          
              Connection con=null;
      int  NvoStocksMag=stocksProduitFinisEnMagasin(entrp.getMagasin())+entrp.getQuantite();
         String sql="INSERT INTO `entree_produit_fini`( `bordereaux`, `type`, `quantite`, `stocks_avant`, `stocks_apres`, `date`, `dateString`,`magasin`) "
        + " VALUES ('"+entrp.getLibelle()+"','"+entrp.getType()+"',"+entrp.getQuantite()+",'"+entrp.getStocks_avant()+"',"+entrp.getStocks_apres()+",'"+entrp.getDate()+"','"+entrp.getDateString()+"',"+entrp.getMagasin().getId()+")"
        + "";
     String sqlActualisationStoks="UPDATE `stocks_produit_fini` SET `quantite`="+entrp.getStocks_apres()+" WHERE quantite="+entrp.getStocks_avant()+"";
     String sqlActualisationMagasin="UPDATE `stocks_magasin_produit_fini` SET `quantite`="+NvoStocksMag+" WHERE magasin="+entrp.getMagasin().getId()+" ";    
     //System.out.println("ECRITURE DE LA DATE:"+sortie.getDate());
         //  System.out.println(sql);
         dtb=database.getInstance();
            con=dtb.getCon();
               try {
                   dtb.getCon().setAutoCommit(false);
            boolean a= dtb.InserData(sql);
            boolean b= dtb.InserData(sqlActualisationStoks);
            boolean c= dtb.InserData(sqlActualisationMagasin);
                    if(a && b && c)
                    {
                        estOk=true;
                    }
                   dtb.getCon().commit();
                   dtb.getCon().setAutoCommit(true);   
               } catch (SQLException ex) {
           Logger.getLogger(md_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
                
            }
         
               
          return estOk;
       }
     public static ArrayList donnerListeEntreeProduitFini(String sql2)
     {
       ArrayList<EntreeProduit> entrp=new ArrayList();
           EntreeProduit entree=null;
         
        try {
            ResultSet res=database.getInstance().selectInTab(sql2);
            while(res.next())
            {
              int id=res.getInt("id");
              String dateString=res.getString("dateString");
              //Date date=res.getDate("date");
              int quantite=res.getInt("quantite");
              String Bordereaux=res.getString("bordereaux");
              String MagasinDesignation=res.getString("nomMag");
              int idMagasin=res.getInt("idM");
              //creation du Mag
              Magasin mag=new Magasin(idMagasin);
                mag.setDesignation(MagasinDesignation);
                //on continu
              int stocks_avant=res.getInt("stocks_avant");
               int stocks_apres=res.getInt("stocks_apres");
              entree=new EntreeProduit(id);
                 entree.setDateString(dateString);
                 entree.setQuantite(quantite);
                 entree.setStocks_avant(stocks_avant);
                 entree.setStocks_apres(stocks_apres);
                 entree.setLibelle(Bordereaux);
                 entree.setMagasin(mag);
                 entrp.add(entree);
            }
        } catch (SQLException ex) {
            Logger.getLogger(md_contenuProduction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entrp;
     
     }
}
