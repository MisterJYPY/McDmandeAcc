/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Articles;
import classe.Magasin;
import classe.SortieProduit;
import classe.Stocks;
import classe.database;
import controller.ctr_contenuEntreeMatierePremiere;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *le model lie a la vue 
 * qui est la classe contenuSortieMatierePremiere
 * @author Mr_JYPY
 * 
 */
public class md_contenuSortieMatierePremiere {
    
         static  database dtb;
           /**
            * methode qui permettra de gener
            * un code de bordereaux de sortie automatiquement
            * cette partie concerne la partie de sortie de Produit precisement de la famille Matiere Premiere
            * @return la chaine de Caractere qui est un code Unique
            */
       public String genererUnBordereauxLivraison()
          {
              int ligne=0;
              String bordereux="";
        do{
           ligne=0;
          String bor="Srti";
          int mt;
           mt = (int) (Math.random()*109999);
           String fin="Mc";
          bordereux=bor.concat(String.valueOf(mt)).concat(fin);
        String sql="SELECT * FROM sortie_produit WHERE libelle='"+bordereux+"' ";
            dtb=database.getInstance();
                  try {
                      ligne=dtb.nbreLigneRequete(sql);
                  } catch (SQLException ex) {
                      Logger.getLogger(md_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
                  }
          } while(ligne>0);
        
        return bordereux;
           }
       /**
        * methode Static permettant de Generer un Bordereaux En fonction du Nom
        * de la table du debut voulu de Bordereaux et de la fin
        * @param NomTable le NOm de la table
       * @param colonne le Nom de la colonne a faire le text
        * @param commencement l'ilustration de la chaine de debut ex:BorLiPf
        * @param fin    celle de la fin
        * @return  un string qui est le bordereaux unique generer
        */
        public static String genererUnBordereauxLivraison(String NomTable,String colonne,String commencement,String fin)
          {
              int ligne=0;
              String bordereux="";
        do{
           ligne=0;
          String bor=commencement;
          int mt;
           mt = (int) (Math.random()*109999);
           //String fin="Mc";
          bordereux=bor.concat(String.valueOf(mt)).concat(fin);
        String sql="SELECT * FROM "+NomTable+" WHERE "+colonne+"='"+bordereux+"' ";
            dtb=database.getInstance();
                  try {
                      ligne=dtb.nbreLigneRequete(sql);
                  } catch (SQLException ex) {
                      Logger.getLogger(md_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
                  }
          } while(ligne>0);
        
        return bordereux;
           }
       public boolean creerUneSortieProduit(SortieProduit sortie)
       {
         boolean estOk=false;
         Connection con=null;
      int  NvoStocksMag=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(sortie.getMagasin(), sortie.getArticle())-sortie.getQuantite();
         String sql="INSERT INTO `sortie_produit`( `article`, `libelle`, `quantite`, `date`, `magasin`,`dateString`, `stocks_avant`, `stocks_apres`) "
        + " VALUES ("+sortie.getArticle().getId()+",'"+sortie.getLibelle()+"',"+sortie.getQuantite()+",'"+(java.sql.Date)sortie.getDate()+"',"+sortie.getMagasin().getId()+",'"+sortie.getDateString()+"',"+sortie.getStocks_avant()+","+sortie.getStocks_apres()+")"
        + "";
     String sqlActualisationStoks="UPDATE `stocks` SET `quantite`="+sortie.getStocks_apres()+" WHERE articles="+sortie.getArticle().getId()+"";
     String sqlActualisationMagasin="UPDATE `stocks_magasin` SET `quantite`="+NvoStocksMag+" WHERE produit="+sortie.getArticle().getId()+" AND magasin="+sortie.getMagasin().getId()+" ";    
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
       /**
        * methode staic permettant d'envoyer la liste des sortie de 
        * Produit tant pour les matieres premieres que pour les autres articles
        * la requete doit imperativement contenir comme label les mots suivants:
        * idArticle pour lid de larticle
        * designationArticle pour la designation de larticle
        * idM pour l'id du magasin de sotie,id pour lid general de linstance de la sortie
        * libelle pour le bordereaux de sortie
        * nomMag pour le nom du magasin
        * dateSortie pour la date sous format string de sortie.
        * le reste des elements non citer reste identique au label de la table ciblee
        * @param sql
        * @return une liste de Sortie
        */
      public static ArrayList ListeSortie(String sql)
         {
               ArrayList<SortieProduit> sortieP;
               Articles artcle=null;
               Magasin mag=null;
               sortieP = new ArrayList<>();
               ResultSet res=null;
              // dtb=database.getInstance();
          try {
              res=database.getInstance().selectInTab(sql);
               while(res.next())
               {
             
                       int idA=res.getInt("idArticle");
                       String Adesignation=res.getString("designationArticle");
                Articles  articleCourant= ctr_contenuSortieMatierePremiere.genererArticleViaDesignation(Adesignation);
                     /**
                      * creation du magasin
                      */
                    int idM =res.getInt("idM");
                    String nomMag=res.getString("nomMag");
                  Magasin  magasinCourant=ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation( nomMag);
                  /**
                   * creation de entree_produit
                   */
                     int idEnt=res.getInt("id");
                    int quantite=res.getInt("quantite");
                  String libelle=res.getString("libelle");
                  java.sql.Timestamp derniere_modif=res.getTimestamp("derniere_modif");
                  int stokApr=res.getInt("stocks_apres");
                  int stokAvt=res.getInt("stocks_avant");
                   String DateSortie=res.getString("dateSortie");
                  SortieProduit sortie = new SortieProduit(idEnt);
                     sortie .setLibelle(libelle);
                     sortie .setDateString(DateSortie);
                     sortie .setQuantite(quantite);
                    sortie .setStocks_avant(stokAvt);
                     sortie .setStocks_apres(stokApr);
                     sortie .setDerniereModif(derniere_modif);
                     /**
                      * insertion des objets lies
                      */
                     sortie.setMagasin(magasinCourant);
                     sortie.setArticle(articleCourant);
                   /**
                    * enregistrement dans le ArrayList
                    */
                     sortieP.add(sortie);
                     
               }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
                
               return sortieP;
         }
}
