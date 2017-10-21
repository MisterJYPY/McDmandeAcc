/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import controller.ctr_contenuProduction;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr_JYPY
 */
public class OperationProductionProduitFini {
    
    private int quantiterHebdomadaire=0;
    private int quantiterMensuelle=0;
    private int quantiteStoks=0;
    private int quantiteJournalier=0;
   // private int quantiterProduiteFonctionMois=0;

    public int getQuantiterHebdomadaire() {
        return quantiterHebdomadaire;
    }

    public void setQuantiterHebdomadaire(int quantiterHebdomadaire) {
        this.quantiterHebdomadaire = quantiterHebdomadaire;
    }

    public int getQuantiterMensuelle() {
        return quantiterMensuelle;
    }

    public void setQuantiterMensuelle(int quantiterMensuelle) {
        this.quantiterMensuelle = quantiterMensuelle;
    }

    public int getQuantiteStoks() {
        return quantiteStoks;
    }

    public void setQuantiteStoks(int quantiteStoks) {
        this.quantiteStoks = quantiteStoks;
    }

    public int getQuantiteJournalier() {
        return quantiteJournalier;
    }

    public void setQuantiteJournalier(int quantiteJournalier) {
        this.quantiteJournalier = quantiteJournalier;
    }
 /**
  * constructeur parametrer qui prends en parametre le nom 
  * d'une table en base de donnee qui gere une entree,une sortie de produit fini
  * et donner le bilan
  * @param table 
  */
    public OperationProductionProduitFini(String table) {
        //vente_produit_fini
     String sqlJournalier="SELECT SUM(quantite) as quantite FROM "+table+" WHERE date=CURRENT_DATE()";
       //pour l'ebdomadaire
         Date dateHeb=new Date();
         int jour=dateHeb.getDay();
         System.out.println("La date du Jour: "+jour);
         //utilisation de l'objet calendar
           Calendar cal=Calendar.getInstance();
         //  System.out.println("le nombre de jour du Mois: "+cal.get(Calendar.DAY_OF_MONTH));
             /**
              * un petit traitement pour avoir la date pour la semaine en cour
              * on recupere le jour actuel du mois,si le jour est inferieur ou egale a 7 on 
              * jour recevra le negatif du quel ieme jour du mois
              * dans le cas contraire on prends le jour de la semaine et on fait -jour de la semaine
              */
             int nbreJourMois=cal.get(Calendar.DAY_OF_MONTH);
             int kelJour=cal.get(Calendar.DAY_OF_WEEK);
                
             System.out.println("jour de la semaine: "+kelJour);
//                           if(nbreJourMois<=7)
//                           {
//                           jour=nbreJourMois;
//                           }
               if(jour==0)
               {
               jour=7;
               }
       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
        System.out.println("la date reculee de jour pour la requete: "+dateHeb);
      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
      //String sqlHebdomadaire="SELECT SUM(quantite) as quantite FROM entree_produit_fini WHERE date>='"+datesHeb+"' ";
       //pour le Mensuel
          // date=null;
          Date date=new Date();
           date=ctr_contenuProduction.avoirPremierJourMois(date);
           
          Date  dates=new java.sql.Date(date.getTime());
            System.out.println("LA DATE hebdomadaire "+datesHeb);
  String sqlHebdomadaire="SELECT SUM(quantite) as quantite FROM "+table+" WHERE date>'"+datesHeb+"' ";
  String sqlMensuel="SELECT SUM(quantite) as quantite FROM "+table+" WHERE date>='"+dates+"'";
            //pour Le general
        System.out.println(sqlMensuel);
   String sqlGeneral="SELECT SUM(quantite) as quantite FROM stocks_produit_fini WHERE libelle='CartonMc'";
        int quantiter=0;
        try {
            ResultSet res=database.getInstance().selectInTab(sqlJournalier);
            while(res.next())
            {
                 quantiter=res.getInt("quantite");
                 this.quantiteJournalier=quantiter;
            }
            quantiter=0;
            res=null;
            res=database.getInstance().selectInTab(sqlHebdomadaire);
             while(res.next())
            {
                 quantiter=res.getInt("quantite");
                 this.quantiterHebdomadaire=quantiter;
            }
            quantiter=0;
            res=null;
            res=database.getInstance().selectInTab(sqlMensuel);
             while(res.next())
            {
                 quantiter=res.getInt("quantite");
                 this.quantiterMensuelle=quantiter;
            }
              quantiter=0;
            res=null;
            res=database.getInstance().selectInTab(sqlGeneral);
              while(res.next())
            {
                 quantiter=res.getInt("quantite");
                 this.quantiteStoks=quantiter;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationProductionProduitFini.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public OperationProductionProduitFini() 
    {
//       String sqlJournalier="SELECT SUM(quantite) as quantite FROM entree_produit_fini WHERE date=CURRENT_DATE()";
//       //pour l'ebdomadaire
//         Date dateHeb=new Date();
//         int jour=dateHeb.getDay();
//       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
//      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
//      //String sqlHebdomadaire="SELECT SUM(quantite) as quantite FROM entree_produit_fini WHERE date>='"+datesHeb+"' ";
//       //pour le Mensuel
//          // date=null;
//          Date date=new Date();
//           date=ctr_contenuProduction.avoirPremierJourMois(date);
//           
//         Date  dates=new java.sql.Date(date.getTime());
//            System.out.println("LE JOUR "+dates.getDay());
//  String sqlHebdomadaire="SELECT SUM(quantite) as quantite FROM entree_produit_fini WHERE date>='"+datesHeb+"' AND date>='"+dates+"' ";
//  String sqlMensuel="SELECT SUM(quantite) as quantite FROM entree_produit_fini WHERE date>='"+dates+"'";
//            //pour Le general
//        System.out.println(sqlMensuel);
//   String sqlGeneral="SELECT SUM(quantite) as quantite FROM stocks_produit_fini WHERE libelle='CartonMc'";
//        int quantiter=0;
//        try {
//            ResultSet res=database.getInstance().selectInTab(sqlJournalier);
//            while(res.next())
//            {
//                 quantiter=res.getInt("quantite");
//                 this.quantiteJournalier=quantiter;
//            }
//            quantiter=0;
//            res=null;
//            res=database.getInstance().selectInTab(sqlHebdomadaire);
//             while(res.next())
//            {
//                 quantiter=res.getInt("quantite");
//                 this.quantiterHebdomadaire=quantiter;
//            }
//            quantiter=0;
//            res=null;
//            res=database.getInstance().selectInTab(sqlMensuel);
//             while(res.next())
//            {
//                 quantiter=res.getInt("quantite");
//                 this.quantiterMensuelle=quantiter;
//            }
//              quantiter=0;
//            res=null;
//            res=database.getInstance().selectInTab(sqlGeneral);
//              while(res.next())
//            {
//                 quantiter=res.getInt("quantite");
//                 this.quantiteStoks=quantiter;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(OperationProductionProduitFini.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        
    }
    
    
    
    
}
