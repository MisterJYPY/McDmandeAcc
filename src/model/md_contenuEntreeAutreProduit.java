/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Articles;
import classe.Demandeachat;
import classe.EntreeProduit;
import classe.Famille;
import classe.Fournisseur;
import classe.Magasin;
import classe.Personnel;
import classe.Services;
import classe.Stocks;
import classe.database;
import static controller.ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin;
import static model.md_contenuEntreeMatierePremiere.dtb;
import static model.md_contenuEntreeMatierePremiere.etatDeLademande;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuEntreeAutreProduit {
    
    
    
   public EntreeProduit GenererUntype(String designation,String magasinDesignation,String dateActuelle,String libelle,String demande,String DateString,int quantiter,String numVoiture,String fournisseur,java.sql.Date dates)
         {        
            ResultSet res=null;
            Stocks stk=null;
            Famille fami=null;
            Demandeachat demdeAchat=null;
            Fournisseur frnisseu=null;
          Articles articles = null;
           Magasin magasin=null;
            String sql="SELECT articles.designation as ad,articles.id as idA,articles.code as codea,famille.id as idf,famille.code as codef ,famille.designation as fd"
                    + ",stocks.quantite,stocks.id as idStocks FROM articles"
                    + "  INNER JOIN famille on articles.famille=famille.id "
                    + "  INNER JOIN stocks ON stocks.articles=articles.id"
                    + "  WHERE articles.designation ='"+designation+"' ";
           //un article qu'on cree
           // System.out.println(sql);
                  dtb=database.getInstance();
          try {
              res=dtb.selectInTab(sql);
              //information sur l'article
                int idArticle=0;
                String codeArticle="";
                String ArticleDesignation="";
              //information sur soon stocks
                int IdstocksArticle=0;
                 int quantiteStocks=0;
               //informations sur la famille
                int idFamille=0;
                String codeFamille="";
                String famileDesignation="";
                //
                while(res.next()){
                idArticle=res.getInt("idA");
                ArticleDesignation=res.getString("ad");
                IdstocksArticle=res.getInt("idStocks");
                quantiteStocks=res.getInt("quantite");
                idFamille=res.getInt("idf");
                famileDesignation=res.getString("fd");
               //creation de l'article
                 fami=new Famille(idFamille,famileDesignation);
                 fami.setCode(codeFamille);
                 stk=new Stocks(IdstocksArticle,quantiteStocks);
                articles=new Articles(idArticle,ArticleDesignation,fami,stk);
                 articles.setCode(codeArticle);
               //System.out.println(articles.getStocks().getQuantite());
            String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
                    + " SELECT demandeAchat FROM cotation) AND service=(SELECT id FROM services WHERE designation='ACHAT ET PRODUCTION')";               
                    }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
            //creation du magasin
            magasin=genererMagasinViaDesignation(magasinDesignation);
             //System.out.println( magasin);
          //creation de la demande achat
              String sqlDemande="SELECT * FROM demandeachat WHERE bordereaux='"+demande+"'";
          try {
              res=dtb.selectInTab(sqlDemande);
               int idDemande=0;
               int service=0;
               int demandeur=0;
               Date datedemande=null;
               while(res.next()){
               idDemande=res.getInt("id");
               service=res.getInt("service");
               demandeur=res.getInt("demandeur");
               datedemande=(Date)res.getDate("date");
             //creation du service
               Services servic=new Services(service);
               Personnel personne=new Personnel(demandeur);
             demdeAchat=new Demandeachat(idDemande);
                    demdeAchat.setDate(datedemande);
                    demdeAchat.setDemandeur(personne);
                    demdeAchat.setService(servic);
                    demdeAchat.setBordereaux(demande);
               }
                res=null;
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
            //creation du fournisseur
          String sqlF="SELECT * FROM fournisseur WHERE nom='"+fournisseur+"'";
            int idF=0;
            String nomF="";
          try {
              res=dtb.selectInTab(sqlF);
               while(res.next())
               {
                 idF=res.getInt("id");
                 nomF=res.getString("nom");
                
               }
                frnisseu=new Fournisseur(idF);
                  frnisseu.setNom(nomF);
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
              int quantite=0;
             int stocks_av=0; //il se trouve dans lobjet Article et c'est le stocks general de l'article
             int stocks_apres=articles.getStocks().getQuantite()+quantiter;
          //creation de entree Produit
          EntreeProduit op=new EntreeProduit();
                   op.setArticle(articles);
                   op.setMagasin(magasin);
                   op.setDemandeachat(demdeAchat);
                   op.setLibelle(libelle);
                   op.setDateString(DateString);
                   op.setQuantite(quantiter);
                   op.setNumeroVehicule(numVoiture);
                   op.setFournisseur(frnisseu);
                   op.setStocks_apres(stocks_apres);
                   op.setDate(dates);
           return op;
         }
  public boolean CreerEntreeProduit(EntreeProduit entreeProd)
         {
           boolean estOk=false;   
             try {
               dtb.getCon().setAutoCommit(false);
           
           boolean etatDesEntree=false;
String sql="INSERT INTO `entree_autre_produit`( `article`, `libelle`, `quantite`, `date`, `magasin`,`dateString`, `stocks_avant`, `stocks_apres`, `demandeAchat`, `numeroVehicule`, `fournisseur`) "
        + " VALUES ("+entreeProd.getArticle().getId()+",'"+entreeProd.getLibelle()+"',"+entreeProd.getQuantite()+",'"+(java.sql.Date)entreeProd.getDate()+"',"+entreeProd.getMagasin().getId()+",'"+entreeProd.getDateString()+"',"+entreeProd.getArticle().getStocks().getQuantite()+","+entreeProd.getStocks_apres()+","
        + " "+entreeProd.getDemandeachat().getId()+",'"+entreeProd.getNumeroVehicule()+"',"+entreeProd.getFournisseur().getId()+")";

String sqlParfait="INSERT INTO `demandesatisfaite_autreproduit`( `demandeAchat`) VALUES ("+entreeProd.getDemandeachat().getId()+")";
             System.out.println("demande: " +entreeProd.getDemandeachat().getBordereaux());
 
         boolean insertionFaite=false;
         
String sqlActualisation="UPDATE `stocks` SET `quantite`="+entreeProd.getStocks_apres()+" WHERE articles="+entreeProd.getArticle().getId()+"";
            

   int stocksMagasin=0;
       stocksMagasin=StocksArticlesEnMagasin(entreeProd.getMagasin(),entreeProd.getArticle());
                            // System.out.println("SOCKTS DY MAG "+stocksMagasin);
     int NvoStocksMag=0;
     NvoStocksMag=stocksMagasin+entreeProd.getQuantite();
   String sqlActualisationMagasin="UPDATE `stocks_magasin` SET `quantite`="+NvoStocksMag+" WHERE produit="+entreeProd.getArticle().getId()+" AND magasin="+entreeProd.getMagasin().getId()+" ";
          if(database.getInstance().InserData(sql)){
       if(database.getInstance().InserData(sqlActualisationMagasin)){
              // System.out.println("MAGASIN ACTUALISER AVEC SUCCES ");
       if(database.getInstance().InserData(sqlActualisation))
                  {
   etatDesEntree=etatDeLademande(entreeProd.getDemandeachat().getBordereaux(),"entree_autre_produit");
             if(etatDesEntree){
      if(database.getInstance().InserData(sqlParfait))
                {   
         //  System.out.println("!!!!!!!!TOUT C BIEN DEROULER!!!!!!!!!!!!!!!!!!!!!!!!");
                }
             // }
                 }
                   estOk=true;
                  }
      
       }}
            //return 
          database.getInstance().getCon().commit();
            database.getInstance().getCon().setAutoCommit(true);
         }catch(Exception ex)
         {
   Logger.getLogger(md_contenuEntreeAutreProduit.class.getName()).log(Level.SEVERE, null, ex);      
         }
       return estOk;
              
         }
  
}
