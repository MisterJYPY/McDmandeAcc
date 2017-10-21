/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEtatGlobal;
import static model.md_contenuEtatGlobal.avoirQuantite;
import vue.contenuEtatGlobal;

/**
 *
 * @author Mr_JYPY
 */
public final class ctr_contenuEtatGlobal {
    
    private final contenuEtatGlobal vue;
    private final md_contenuEtatGlobal model;

    public ctr_contenuEtatGlobal(contenuEtatGlobal vue) {
        this.vue = vue;
        model=new md_contenuEtatGlobal();
        initialisationDeLavue("entree_produit","sortie_produit");
        
        //initialisation des actions sur les Boutons d'impressions
         //pour les mouse
            this.vue.getImprimerEntree().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                ImpreesionTableEntree(evt);
               
            }
        });
            
            //initialisation des actions sur les Boutons d'impressions
         //pour les mouse
            this.vue.getImprimerSortie().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
                 ImpreesionTableSortie(evt);
            }
        });
    }
     /**
       * methode permettant de creer la page d'impression lorsque on clique
       * sur le Bouton format pdf de la vue contenuEtatGloabl se trouvant dans 
       * la partie sortie
       * @param evt 
       */
      public void ImpreesionTableSortie(java.awt.event.MouseEvent evt)
      {
       String entete="BILAN PERIODIQUE DES SORTIES DE MATIERES-P";
       String Pied="Edité Par Mc croft Application G_Stocks le : "+ctr_contenuEntreeMatierePremiere.donnerDateJourFormatChaineFr();
       ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableSortie(), entete, Pied);
      }
      /**
       * methode permettant de creer la page d'impression lorsque on clique
       * sur le Bouton format pdf de la vue contenuEtatGloabl se trouvant dans 
       * la partie entree
       * @param evt 
       */
       public void ImpreesionTableEntree(java.awt.event.MouseEvent evt)
      {
      String entete="BILAN PERIODIQUE DES ENTREES DE MATIERES-P";
        String Pied="Edité Par Mc croft Application G_Stocks le : "+ctr_contenuEntreeMatierePremiere.donnerDateJourFormatChaineFr();
         ctr_contenuEntreeMatierePremiere.impressionJtable(this.vue.getTableEntree(), entete, Pied);
      }
    /**
     * methode pour linitialisation
     * @param tableEntree
     * @param tableSortie
     */
    public void initialisationDeLavue(String tableEntree,String tableSortie)
    {
  String sqlArticles="select stocks.quantite,articles.designation as des,articles.id,articles.famille\n" +
       "FROM stocks\n" +
       "INNER JOIN articles ON articles.id=stocks.articles AND articles.famille=(SELECT id FROM famille WHERE designation='Matiere premiere')";
     DefaultTableModel models=( DefaultTableModel) this.vue.getTableEntree().getModel();
    DefaultTableModel modelAll=( DefaultTableModel) this.vue.getTableSortie().getModel();
     DefaultTableModel modelDemandeV=( DefaultTableModel) this.vue.getDemandeSatisfaite().getModel();
    DefaultTableModel modelDemandeEC=( DefaultTableModel) this.vue.getDemandeNonSatisfaite().getModel();
   //selection de tous les demande Achat coter et satisfaite
     String sql4="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id  IN (SELECT demandesatisfaite.demandeAchat FROM demandesatisfaite)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille=(SELECT famille.id FROM famille WHERE famille.designation='Matiere premiere')))";
     //System.out.println(sql4);
     //selection de tous les demande Achat coter et non satisfaite
     String sqlNonS="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id NOT IN (SELECT demandesatisfaite.demandeAchat FROM demandesatisfaite)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille=(SELECT famille.id FROM famille WHERE famille.designation='Matiere premiere')))";
     //System.out.println(sql4);
     try
      {
            //pour chaque articles nous allons calculer la quantite entree
  ResultSet res=database.getInstance().selectInTab(sqlArticles);
  ResultSet resDemande=database.getInstance().selectInTab(sql4);
  ResultSet resDemandeNs=database.getInstance().selectInTab(sqlNonS);
        while(res.next())
        {
           int idArticle=res.getInt("id");
           Date dateHeb=new Date();
           
           java.sql.Date dateJourA=new java.sql.Date(dateHeb.getTime());
           int jour=dateHeb.getDay();
               
       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
        System.out.println("la date reculee de jour pour la requete: "+dateHeb);
      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
     
          Date date=new Date();
           date=ctr_contenuProduction.avoirPremierJourMois(date);
           
          Date  dates=new java.sql.Date(date.getTime());
          
    System.out.println("LA DATE EN PULSION "+datesHeb);
    String sqlHebdomadaire="SELECT SUM(quantite) as hebdo FROM "+tableEntree+" WHERE date>'"+datesHeb+"' ";
    String sqlMensuel="SELECT SUM(quantite) as mois FROM "+tableEntree+" WHERE date>='"+dates+"'";
    //les variables pour les entrees
     String article=res.getString("des");
     int qj=md_contenuEtatGlobal.avoirQuantite(idArticle,tableEntree,dateJourA);
     int qs=md_contenuEtatGlobal.avoirQuantite(idArticle,tableEntree,datesHeb);
     int qm=md_contenuEtatGlobal.avoirQuantite(idArticle,tableEntree,  dates);
     int qn=md_contenuEtatGlobal.avoirQuantite(idArticle,tableEntree,  avoirDateJanvierEncour());
     int stocks=res.getInt("quantite");
     //les objects
         Object [] row =new Object[6];
         row[0]=article;
         row[1]=qj;
         row[2]=qs;
         row[3]=qm;
         row[4]=qn;
         row[5]=stocks;
         models.addRow(row);
       
        //pour les sortie
   
     int qjs=md_contenuEtatGlobal.avoirQuantite(idArticle,tableSortie,dateJourA);
     int qss=md_contenuEtatGlobal.avoirQuantite(idArticle,tableSortie,datesHeb);
     int qms=md_contenuEtatGlobal.avoirQuantite(idArticle,tableSortie,  dates);
     int qns=md_contenuEtatGlobal.avoirQuantite(idArticle,tableSortie,  avoirDateJanvierEncour());
        row[0]=article;
         row[1]=qjs;
         row[2]=qss;
         row[3]=qms;
         row[4]=qns;
         row[5]=stocks;
         modelAll.addRow(row);
        }
        int cpt=1;
        while(resDemande.next())
        {
        Object [] row =new Object[5];
         row[0]=cpt;
         row[1]=resDemande.getString("code");
         row[2]=resDemande.getString("nom");
         row[3]=resDemande.getString("des");
         row[4]=resDemande.getDate("date");
        
          modelDemandeV.addRow(row);
        cpt++;
        
        }
          cpt=1;
        while(resDemandeNs.next())
        {
        Object [] row =new Object[5];
         row[0]=cpt;
         row[1]=resDemandeNs.getString("code");
         row[2]=resDemandeNs.getString("nom");
         row[3]=resDemandeNs.getString("des");
         row[4]=resDemandeNs.getDate("date");
        
          modelDemandeEC.addRow(row);
        cpt++;
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(ctr_contenuEtatGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   
    }
    public static Date avoirDateJanvierEncour()
    {
     Calendar calendar = Calendar.getInstance();
        Date date=new Date();
         // date.get
        Calendar myCalendar = GregorianCalendar.getInstance();
        //   myCalendar.setTime(myDate);
 calendar.set(calendar.get((Calendar.YEAR)),Calendar.JANUARY,01);
      //  Date dateJanvier;
      //  dateJanvier=calendar.getTimeInMillis();
          Date  dateArenoyer=new java.sql.Date(calendar.getTimeInMillis());
        return dateArenoyer;
    }
    /**
     * methode static permettant de recuperer un bilan 
     * par article Non matiere premiere et l'afficher dans le Jtable Model correspondant
     *  la table en base de donnee qui prends les entree,et celle qui prends les sorties
     * un tableau de Model de Jtable qui sont donné de la maniere suivante:
     * <p><strong>Element 0 </strong>:le model du jtable des Entrees</p>
     * <p><strong>Element 1</strong>:le model du jtable des Sorties</p>
     * <p><strong>Element 2 </strong>:le model de la table des demandes Achat satisfaite</p>
     * <p><strong>Element 3 </strong>:le model de la table des demandes Achat Non satisfaite</p>
     * @param tableEntree --le nom de la table des entree
     * @param tableSortie --le nom de la table des sorties
     * @param tableModel  --le tableau des models
     */
     public static void initialisationDeLavue(String tableEntree,String tableSortie,DefaultTableModel[] tableModel)
    {
  String sqlArticles="select stocks.quantite,articles.designation as des,articles.id,articles.famille\n" +
       "FROM stocks\n" +
       "INNER JOIN articles ON articles.id=stocks.articles AND articles.famille IN (SELECT id FROM famille WHERE designation!='Matiere premiere')"
     + "  WHERE 1 ORDER BY articles.designation ";
     DefaultTableModel models=tableModel[0];
    DefaultTableModel modelAll=tableModel[1];
     DefaultTableModel modelDemandeV=tableModel[2];
    DefaultTableModel modelDemandeEC=tableModel[3];
   //selection de tous les demande Achat coter et satisfaite
     String sql4="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id  IN (SELECT demandesatisfaite_autreproduit.demandeAchat FROM demandesatisfaite_autreproduit)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille IN (SELECT famille.id FROM famille WHERE famille.designation!='Matiere premiere')))";
     //System.out.println(sql4);
     //selection de tous les demande Achat coter et non satisfaite
     String sqlNonS="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id NOT IN (SELECT demandesatisfaite_autreproduit.demandeAchat FROM demandesatisfaite_autreproduit)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille IN (SELECT famille.id FROM famille WHERE famille.designation!='Matiere premiere')))";
     //System.out.println(sql4);
     try
      {
            //pour chaque articles nous allons calculer la quantite entree
  ResultSet res=database.getInstance().selectInTab(sqlArticles);
  ResultSet resDemande=database.getInstance().selectInTab(sql4);
  ResultSet resDemandeNs=database.getInstance().selectInTab(sqlNonS);
        while(res.next())
        {
           int idArticle=res.getInt("id");
           Date dateHeb=new Date();
           
           java.sql.Date dateJourA=new java.sql.Date(dateHeb.getTime());
           int jour=dateHeb.getDay();
               
       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
        System.out.println("la date reculee de jour pour la requete: "+dateHeb);
      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
     
          Date date=new Date();
           date=ctr_contenuProduction.avoirPremierJourMois(date);
           
          Date  dates=new java.sql.Date(date.getTime());
          
    System.out.println("LA DATE EN PULSION "+datesHeb);
    String sqlHebdomadaire="SELECT SUM(quantite) as hebdo FROM "+tableEntree+" WHERE date>'"+datesHeb+"' ";
    String sqlMensuel="SELECT SUM(quantite) as mois FROM "+tableEntree+" WHERE date>='"+dates+"'";
    //les variables pour les entrees
     String article=res.getString("des");
     int qj=avoirQuantite(idArticle,tableEntree,dateJourA);
     int qs=avoirQuantite(idArticle,tableEntree,datesHeb);
     int qm=avoirQuantite(idArticle,tableEntree,  dates);
     int qn=avoirQuantite(idArticle,tableEntree,  avoirDateJanvierEncour());
     int stocks=res.getInt("quantite");
     //les objects
         Object [] row =new Object[6];
         row[0]=article;
         row[1]=qj;
         row[2]=qs;
         row[3]=qm;
         row[4]=qn;
         row[5]=stocks;
         models.addRow(row);
       
        //pour les sortie
   
     int qjs=avoirQuantite(idArticle,tableSortie,dateJourA);
     int qss=avoirQuantite(idArticle,tableSortie,datesHeb);
     int qms=avoirQuantite(idArticle,tableSortie,  dates);
     int qns=avoirQuantite(idArticle,tableSortie,  avoirDateJanvierEncour());
        row[0]=article;
         row[1]=qjs;
         row[2]=qss;
         row[3]=qms;
         row[4]=qns;
         row[5]=stocks;
         modelAll.addRow(row);
        }
        int cpt=1;
        while(resDemande.next())
        {
        Object [] row =new Object[5];
         row[0]=cpt;
         row[1]=resDemande.getString("code");
         row[2]=resDemande.getString("nom");
         row[3]=resDemande.getString("des");
         row[4]=resDemande.getDate("date");
        
          modelDemandeV.addRow(row);
        cpt++;
        
        }
          cpt=1;
        while(resDemandeNs.next())
        {
        Object [] row =new Object[5];
         row[0]=cpt;
         row[1]=resDemandeNs.getString("code");
         row[2]=resDemandeNs.getString("nom");
         row[3]=resDemandeNs.getString("des");
         row[4]=resDemandeNs.getDate("date");
        
          modelDemandeEC.addRow(row);
        cpt++;
        
        }
        } catch (SQLException ex) {
            Logger.getLogger(ctr_contenuEtatGlobal.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   
    }
}
