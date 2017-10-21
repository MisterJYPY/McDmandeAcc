/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.database;
import controller.ctr_contenuEtatGlobal;
import controller.ctr_contenuProduction;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuEtatGlobal {
    
    
    public void initialisationDeLavue(String tableEntree,String tableSortie)
    {
    String sqlArticles="select stocks.quantite,articles.designation,articles.id,articles.famille\n" +
        "FROM stocks\n" +
        "INNER JOIN articles ON articles.id=stocks.articles AND articles.famille=(SELECT id FROM famille WHERE designation='Matiere premiere')";
        try {
            //pour chaque articles nous allons calculer la quantite entree
            ResultSet res=database.getInstance().selectInTab(sqlArticles);
        while(res.next())
        {
            int idArticle=res.getInt("id");
           String sqlQteToday="SELECT SUM(quantite) as today FROM "+tableEntree+" WHERE article="+idArticle+" "
              + " AND date=CURRENT_DATE()";
               Date dateHeb=new Date();
            int jour=dateHeb.getDay();
           Calendar cal=Calendar.getInstance();
         
             int nbreJourMois=cal.get(Calendar.DAY_OF_MONTH);
             int kelJour=cal.get(Calendar.DAY_OF_WEEK);
             System.out.println("jour de la semaine: "+kelJour);
       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
        //System.out.println("la date reculee de jour pour la requete: "+dateHeb);
      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
     System.out.println("la date reculee de jour pour la requete gggg: "+dateHeb);
          Date date=new Date();
           date=ctr_contenuProduction.avoirPremierJourMois(date);
           
          Date  dates=new java.sql.Date(date.getTime());
            System.out.println("LA DATE EN PULSION "+datesHeb);
  String sqlHebdomadaire="SELECT SUM(quantite) as hebdo FROM "+tableEntree+" WHERE date>'"+datesHeb+"' ";
   String sqlMensuel="SELECT SUM(quantite) as mois FROM "+tableEntree+" WHERE date>='"+dates+"'";
        }
        } catch (SQLException ex) {
            Logger.getLogger(ctr_contenuEtatGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   
    }
    public static int avoirQuantite(int idArticles,String tableName,Date date)
    {
        int quantite=0;
           Date dateA=new Date();
           Date  dates=new java.sql.Date(dateA.getTime());
           String sql="";
           System.out.println("la dates ggg: "+dates+" date: "+date);
       if(dates.toString().equals(date.toString()))
       {
           sql="SELECT SUM(quantite) as quantite FROM "+tableName+" WHERE article="+idArticles+" "
                   + " AND date=CURRENT_DATE() ";
       }
       else{
           sql="SELECT SUM(quantite) as quantite FROM "+tableName+" WHERE article="+idArticles+" "
                   + " AND date>='"+date+"'";
       }
        try {
            System.out.println("voila la requete : "+sql);
            ResultSet res=database.getInstance().selectInTab(sql);
            while(res.next())
            {
            quantite=res.getInt("quantite");
            }
        } catch (SQLException ex) {
            Logger.getLogger(md_contenuEtatGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quantite;
    }
}
