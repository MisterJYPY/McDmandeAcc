/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.SortieProduit;
import classe.database;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.md_contenuSortieMatierePremiere.dtb;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuSortieAutreProduit {
    
   
      public boolean creerUneSortieProduit(SortieProduit sortie)
       {
         boolean estOk=false;
         Connection con=null;
      int  NvoStocksMag=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(sortie.getMagasin(), sortie.getArticle())-sortie.getQuantite();
         String sql="INSERT INTO `sortie_autre_produit`( `article`, `bordereaux`, `quantite`, `date`, `magasin`,`dateString`, `stocks_avant`, `stocks_apres`) "
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


}
