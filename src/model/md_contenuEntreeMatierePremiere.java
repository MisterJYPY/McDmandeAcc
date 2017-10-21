/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Articles;
import classe.Demandeachat;
import classe.Famille;
import classe.Magasin;
import classe.EntreeProduit;
import classe.Fournisseur;
import classe.Personnel;
import classe.Services;
import classe.Stocks;
import classe.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr_JYPY
 */
public class md_contenuEntreeMatierePremiere {
    
     // public 
       //  private ctr_contenuEntreeMatierePremiere ctr_ceMp;
  //ctr_contenuEntreeMatierePremiere ctr_ceMp
      static  database dtb;
    public md_contenuEntreeMatierePremiere() {
       // this.ctr_ceMp = ctr_ceMp;
    }
    /**
     * Cette methode Assez speciale permet de creer une instance de
     * EntreeProduit et le retourner ensuite
     * les paramatere cités doivent imperativement etre passés
     * linstance cree est en fait une concatenation de plusieurs instances superpose
     * @param famille la famille de larticle
     * @param designation le nom de l'article
     * @param magasinDesignation le nom du magasin
     * @param dateActuelle  la date actuelle au format String
     * @param demande     le bordereaux de la demande
     * @param libelle      le bordereaux de livraison
     * @param DateString   la date ay format strinfg
     * @param quantiter   la quantite
     * @param numVoiture  le numero de vehicule
     * @param fournisseur le fournisseur
     * @param dates       la date au format sql
     * @return une classe de type Operation Produit
     */
   public EntreeProduit GenererUntype(String famille,String designation,String magasinDesignation,String dateActuelle,String libelle,String demande,String DateString,int quantiter,String numVoiture,String fournisseur,java.sql.Date dates)
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
                    + "  INNER JOIN famille on articles.famille=famille.id AND famille.id=(SELECT id from famille WHERE designation='"+famille+"')"
                    + "  INNER JOIN stocks ON stocks.articles=articles.id"
                    + " WHERE articles.designation ='"+designation+"' AND articles.famille=(SELECT id from famille WHERE designation='"+famille+"') ";
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
            res=null;               
            int idMagasin=0;
            String codeMagasin="";
             String sql2="SELECT * FROM magasin WHERE designation='"+magasinDesignation+"'";
          try {
              res=dtb.selectInTab(sql2);
              while(res.next()){
                 idMagasin=res.getInt("id");
                // codeMagasin=res.getString("designation");
              }
              res=null;
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
             magasin=new Magasin(idMagasin);
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
      /**
       * en parametre le bordereaux choisi ,l'article
       * @param bordereaux le bordereaux
       * @param article     l'article
       * @return un entier qui est la quantiter entree dun produit qui est le total des quantites deja entree
       * pour un article precis lie a une demande,Elle servira de suivre les entree en stocks
       * 
       */
         public static int AvoirQuantiterEntree(String bordereaux,String article)
         {
           int somme=0;
           ResultSet res=null;
     String sql="SELECT SUM(quantite) as somme FROM entree_produit "
             + " WHERE demandeAchat=(SELECT id FROM demandeachat WHERE bordereaux='"+bordereaux+"') "
             + " AND article=(SELECT id FROM articles WHERE designation='"+article+"')";
          try {
             // dtb=database.getInstance();
              res=database.getInstance().selectInTab(sql);
              while(res.next()){
                 somme=res.getInt("somme"); 
               
              }
              res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
           return somme;
         }
         
         /**
          * surcharge de la methode AvoirQuantiterEntree
          * la particuliarite c'est qu'il s'adapte a toutes les tables
          * se referer a la doc de la methode mere pour avoir plus de details
          * @param bordereaux la demande acaht
          * @param article  le Nom unique de l'article
          * @param table     le Nom de la table
          * @return  un entier qui est la quantite totale entree pour un article precis
          */
          public static int AvoirQuantiterEntree(String bordereaux,String article,String table)
         {
           int somme=0;
           ResultSet res=null;
     String sql="SELECT SUM(quantite) as somme FROM "+table+" "
             + " WHERE demandeAchat=(SELECT id FROM demandeachat WHERE bordereaux='"+bordereaux+"') "
             + " AND article=(SELECT id FROM articles WHERE designation='"+article+"')";
          try {
             // dtb=database.getInstance();
              res=database.getInstance().selectInTab(sql);
              while(res.next()){
                 somme=res.getInt("somme"); 
               
              }
              res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
            // System.out.println("la SOMME DEJA ENTRER: "+somme);
           return somme;
         }
         /**
          * 
          * @param bordereaux
          * @param article
          * @return  un entier qui est le total des quantites
          * ou de la quantite demander pour un article precis
          */
        public static int AvoirQuantiterDemander(String bordereaux,String article)
         {
           int somme=0;
           ResultSet res=null;
     String sql="SELECT SUM(quantiter) as somme FROM articlesdemandeachat "
             + " WHERE demandeAchat=(SELECT id FROM demandeachat WHERE bordereaux='"+bordereaux+"') "
             + " AND article=(SELECT id FROM articles WHERE designation='"+article+"')";
          
          try {
              dtb=database.getInstance();
              res=dtb.selectInTab(sql);
              while(res.next()){
                 somme=res.getInt("somme"); 
               
              }
              res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
           return somme;
         }
         /**
          * cette methode permet de nous renvoyer la liste de Produit dont leur entree en stocks n'est pas complete
          * elle prends en parametre la requete ,elle specifique slmt au entree produit
          *
          * @param sql
          * @param demande
          * @return une liste
          */
         public static ArrayList ArticlesNonComplete(String sql,String demande){
            ArrayList<String>lesArticles=new ArrayList<>();
            ResultSet res=null;
            int quantiter=0;
          try {
               //dtb=database.getInstance();
              res=database.getInstance().selectInTab(sql);
              while(res.next())
              {
                quantiter=(int)res.getInt("quantiter");
               String artcle=res.getString("designation");
               int qteDejaEnregister=AvoirQuantiterEntree(demande, artcle);
                 System.out.println("la quantiter commander de "+artcle+" est: "+quantiter);
               System.out.println("la somme de "+artcle+" est: "+qteDejaEnregister);
               if(qteDejaEnregister<quantiter){
               lesArticles.add(artcle);
               }
              }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
              return lesArticles;
         }
          public static ArrayList ArticlesNonComplete(String sql,String demande,String table){
            ArrayList<String>lesArticles=new ArrayList<>();
            ResultSet res=null;
            int quantiter=0;
          try {
               //dtb=database.getInstance();
              res=database.getInstance().selectInTab(sql);
              while(res.next())
              {
                quantiter=(int)res.getInt("quantiter");
               String artcle=res.getString("designation");
               int qteDejaEnregister=AvoirQuantiterEntree(demande, artcle,table);
                 System.out.println("la quantiter commander de "+artcle+" est: "+quantiter);
               System.out.println("la somme de "+artcle+" est: "+qteDejaEnregister);
               if(qteDejaEnregister<quantiter){
               lesArticles.add(artcle);
               }
              }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
              return lesArticles;
         }
         /**
          * @return un entier qui est la quantiter pouvant etre inserer 
 pour un produit se trouvant sur une demande achat donnee 
 le param sql est la requete lien la table demdeAchat et se trouve dans le controlleur
 qui ctr_controlleurEntreeMatierPremiere precisement dans la methode quiest appeler lorsque 
 on choisi un article  By Mister_Jypy
 return un entier qui est la quantiter qui s'afiche sur le jtecfiel de quaniter sur la vue
          * @param sql
          * @param demande
          * @param artcle
          * 
          *
          */
         public static int quantiterMaximalProduit(String sql,String demande,String artcle){
            ResultSet res=null;
            int quantiter=0;
          try {
               dtb=database.getInstance();
              res=dtb.selectInTab(sql);
              while(res.next())
              {
                int quantiterDemander=(int)res.getInt("quantiter");
               int qteDejaEnregister=AvoirQuantiterEntree(demande, artcle);
              //  System.out.println("la quantiter commander de "+artcle+" est: "+quantiter);
              // System.out.println("la somme de "+artcle+" est: "+qteDejaEnregister);
                quantiter=quantiterDemander-qteDejaEnregister;
              }
              res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
          return quantiter;
         }
         /**<p>methode surcharger provenant de la methode </p><strong>quantiterMaximalProduit(String sql,String demande)</strong>
          * cette methode permet d'avoir la quantite maximal d'un article se trouvant sur une demande d'achat
          * si il y a deja eu des Entrees pour le Produit en question la methode se charge l difference
          * entre celle deja entree et celle demander lors de la demande d'achat
          * @param sql
          * @param demande le bordereaux de la demande
          * @param artcle
          * @param table le nom de la table .elle a ete ajouter car chaque entree a sa table de stockage qui lui est propre en fonction de sa famille
          * @return la quantiter maximal pouvant etre inserer
          */
         public static int quantiterMaximalProduit(String sql,String demande,String artcle,String table){
            ResultSet res=null;
            int quantiter=0;
          try {
               dtb=database.getInstance();
              res=dtb.selectInTab(sql);
              while(res.next())
              {
                int quantiterDemander=(int)res.getInt("quantiter");
               int qteDejaEnregister=AvoirQuantiterEntree(demande, artcle,table);
              //  System.out.println("la quantiter commander de "+artcle+" est: "+quantiter);
              // System.out.println("la somme de "+artcle+" est: "+qteDejaEnregister);
                quantiter=quantiterDemander-qteDejaEnregister;
              }
              res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
          return quantiter;
         }
         /**
          * verifier si une demande A completement ete Livree dans sa totalite 
          * cad es ts les matieres commandes sont ok
          * @param Bordereaux
          * @return 
          */
         public boolean etatDeLademande(String Bordereaux)
          {
           ResultSet res=null;
          int NbreArticles=0;  //le nombre darticles demander
          int NbreArticleEntierementEntre=0; //nombre Articles Complet
          boolean estOk=false;
           //recuperation de Tous les Produits de la demande qui sont des Matieres Premieres
           String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Bordereaux+"')";
              dtb=database.getInstance();
       try {
            NbreArticles=database.getInstance().nbreLigneRequete(sqlMat);
            System.out.println("NBRE ARTICLE: "+NbreArticles);
            res=dtb.selectInTab(sqlMat);
         while(res.next())
                {
                     System.out.println("NBRE ARTICLE: "+NbreArticles);
           //for each artcle we recupering (- -) all quantiter and whe compare and quantiter enter deja ooooh
            //                               -
            String artcle=res.getString("designation");
           int quantiterDemander=res.getInt("quantiter");
           int quantiterDejaEntrer=this.AvoirQuantiterEntree(Bordereaux, artcle);
              // System.out.println("POUR CE ARTICLE NOMBRE DE PRODUIT ENTREE: "+quantiterDejaEntrer);
                if(quantiterDemander==quantiterDejaEntrer)
                {
                 NbreArticleEntierementEntre++;
                }
             }
         res.close();
         if( NbreArticleEntierementEntre==NbreArticles){
              estOk=true;
               }
           } catch (SQLException ex) {
             Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
           return estOk;
           }
           public static boolean etatDeLademande(String Bordereaux,String table)
          {
           ResultSet res=null;
          int NbreArticles=-1;  //le nombre darticles demander
          int NbreArticleEntierementEntre=0; //nombre Articles Complet
          boolean estOk=false;
           //recuperation de Tous les Produits de la demande qui sont des Matieres Premieres
       String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille IN (SELECT id FROM famille WHERE designation!='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Bordereaux+"')";
              System.out.println(sqlMat); 
            dtb=database.getInstance();
       try {
            NbreArticles=dtb.nbreLigneRequete(sqlMat);
            System.out.println("NBRE ARTICLES: "+NbreArticles);
            res=dtb.selectInTab(sqlMat);
         while(res.next())
                {
           //for each artcle we recupering (- -) all quantiter and whe compare and quantiter enter deja ooooh
            //                               -
            String artcle=res.getString("designation");
           int quantiterDemander=res.getInt("quantiter");
                    System.out.println("ARTICLE: "+artcle+" qte: "+quantiterDemander);
       int quantiterDejaEntrer=md_contenuEntreeMatierePremiere.AvoirQuantiterEntree(Bordereaux, artcle,table);
             System.out.println("DEMANDE DEJA ENTRER: "+ quantiterDejaEntrer);
// System.out.println("POUR CE ARTICLE NOMBRE DE PRODUIT ENTREE: "+quantiterDejaEntrer);
                if(quantiterDemander==quantiterDejaEntrer)
                {
                 NbreArticleEntierementEntre++;
                }
             }
         res.close();
         if( NbreArticleEntierementEntre==NbreArticles){
              estOk=true;
               }
           } catch (SQLException ex) {
             Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
              System.out.println("condition: "+estOk);
           return estOk;
           }
        /**
         * insere un element en base de Donnee qui est une instance de EntreeProduit
         * @param entreeProd
         * @return un boolean
         */
         public boolean CreerEntreeProduit(EntreeProduit entreeProd)
         {
           boolean estOk=false;
String sql="INSERT INTO `entree_produit`( `article`, `libelle`, `quantite`, `date`, `magasin`,`dateString`, `stocks_avant`, `stocks_apres`, `demandeAchat`, `numeroVehicule`, `fournisseur`) "
        + " VALUES ("+entreeProd.getArticle().getId()+",'"+entreeProd.getLibelle()+"',"+entreeProd.getQuantite()+",'"+(java.sql.Date)entreeProd.getDate()+"',"+entreeProd.getMagasin().getId()+",'"+entreeProd.getDateString()+"',"+entreeProd.getArticle().getStocks().getQuantite()+","+entreeProd.getStocks_apres()+","
        + " "+entreeProd.getDemandeachat().getId()+",'"+entreeProd.getNumeroVehicule()+"',"+entreeProd.getFournisseur().getId()+")";

             System.out.println("ECRITURE DE LA DATE:"+entreeProd.getDate());
             System.out.println(sql);
dtb=database.getInstance();
            estOk=dtb.InserData(sql);
            return estOk;
         }
         /**
          * cette metode a servi pour l'actualisation du stocks d'un produit en Magasin
          * lors de la creation d'une entree prooduit(matire Premiere) uniquement
          * @param magasin un Objet de Type Magasin
          * @param art     Un objet de type article(instance)
          * @return un entier qui est le Stocks du produit dans un magasin
          * 
          */
         public static int StocksArticlesEnMagasin(Magasin magasin,Articles art)
             {
           int nbre=0;
         String sql="SELECT quantite FROM stocks_magasin WHERE produit="+art.getId()+" AND magasin="+magasin.getId()+" ";
         dtb=database.getInstance();
          try {
              ResultSet res=dtb.selectInTab(sql);
               while(res.next())
               {
             nbre=res.getInt("quantite");
               }
               res.close();
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
             
           return nbre;
             }
         public ArrayList ListeEntreeP()
         {
               ArrayList<EntreeProduit> entreeP;
               Fournisseur fournissr=null;
               Articles artcle=null;
               Magasin mag=null;
               Stocks stk=null;
               Demandeachat dmde=null;
               EntreeProduit entrp=null;
               entreeP = new ArrayList<>();
               ResultSet res=null;
        String sql="SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,\n" +
            "entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_produit.article\n" +
            "WHERE entree_produit.date=CURRENT_DATE()"    ;
               dtb=database.getInstance();
          try {
              res=dtb.selectInTab(sql);
               while(res.next())
               {
               /**
                * nous allons Procceder dabord par la creation des elements un par un en comenacant par la demande Achat
                */
                   int idDemande=res.getInt("idDemande");
                   String bor=res.getString("bordereaux");
                   dmde=new Demandeachat(idDemande);
                      dmde.setBordereaux(bor);
                /**
                 * construction de l'article
                */
                       int idA=res.getInt("idArticle");
                       String Adesignation=res.getString("designationArticle");
                       artcle=new Articles(idA);
                       artcle.setDesignation(Adesignation);
                 /**
                  * creation du fournisseur
                  */
                      int idF=res.getInt("idFournisseur");
                      String NomF=res.getString("nom");
                      fournissr=new Fournisseur(idF);
                       fournissr.setNom(NomF);
                     /**
                      * creation du magasin
                      */
                    int idM =res.getInt("idM");
                    String nomMag=res.getString("nomMag");
                    mag=new Magasin(idM);
                    mag.setDesignation(nomMag);
                  /**
                   * creation de entree_produit
                   */
                     int idEnt=res.getInt("id");
                    int quantite=res.getInt("quantite");
                  String libelle=res.getString("libelle");
                  int stokApr=res.getInt("stocks_apres");
                  int stokAvt=res.getInt("stocks_avant");
                     String DateEntree=res.getString("dateEntree");
                     entrp =new EntreeProduit(idEnt);
                     entrp.setLibelle(libelle);
                     entrp.setDateString(DateEntree);
                     entrp.setQuantite(quantite);
                     entrp.setStocks_avant(stokAvt);
                     entrp.setStocks_apres(stokApr);
                     /**
                      * insertion des objets lies
                      */
                     entrp.setArticle(artcle);
                     entrp.setMagasin(mag);
                     entrp.setFournisseur(fournissr);
                     entrp.setDemandeachat(dmde);
                   /**
                    * enregistrement dans le ArrayList
                    */
                     entreeP.add(entrp);
                     /**
                      * reinitialistion des objets
                      */
                     artcle=null;
                     mag=null;
                     fournissr=null;
                     dmde=null;
                     
               }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
                
               return entreeP;
         }
         /**
          * 
          * @param sql
          * @return la lise de toutes les entree en 
          * fonction de la requete specier.c'est des instances de EntreeProduit qui sont generer
          */
          public static ArrayList ListeEntreeP(String sql)
         {
               ArrayList<EntreeProduit> entreeP;
               Fournisseur fournissr=null;
               Articles artcle=null;
               Magasin mag=null;
               Stocks stk=null;
               Demandeachat dmde=null;
               EntreeProduit entrp=null;
               entreeP = new ArrayList<>();
               ResultSet res=null;
               dtb=database.getInstance();
          try {
              res=dtb.selectInTab(sql);
               while(res.next())
               {
               /**
                * nous allons Procceder dabord par la creation des elements un par un en comenacant par la demande Achat
                */
                   int idDemande=res.getInt("idDemande");
                   String bor=res.getString("bordereaux");
                   dmde=new Demandeachat(idDemande);
                      dmde.setBordereaux(bor);
                /**
                 * construction de l'article
                */
                       int idA=res.getInt("idArticle");
                       String Adesignation=res.getString("designationArticle");
                       artcle=new Articles(idA);
                       artcle.setDesignation(Adesignation);
                 /**
                  * creation du fournisseur
                  */
                      int idF=res.getInt("idFournisseur");
                      String NomF=res.getString("nom");
                      fournissr=new Fournisseur(idF);
                       fournissr.setNom(NomF);
                     /**
                      * creation du magasin
                      */
                    int idM =res.getInt("idM");
                    String nomMag=res.getString("nomMag");
                    mag=new Magasin(idM);
                    mag.setDesignation(nomMag);
                  /**
                   * creation de entree_produit
                   */
                     int idEnt=res.getInt("id");
                    int quantite=res.getInt("quantite");
                  String libelle=res.getString("libelle");
                  String NumeroVehicule=res.getString("numeroVehicule");
                  java.sql.Timestamp derniere_modif=res.getTimestamp("derniere_modif");
                  int stokApr=res.getInt("stocks_apres");
                  int stokAvt=res.getInt("stocks_avant");
                     String DateEntree=res.getString("dateEntree");
                     entrp =new EntreeProduit(idEnt);
                     entrp.setLibelle(libelle);
                     entrp.setDateString(DateEntree);
                     entrp.setQuantite(quantite);
                     entrp.setStocks_avant(stokAvt);
                     entrp.setStocks_apres(stokApr);
                     entrp.setDerniereModif(derniere_modif);
                     entrp.setNumeroVehicule(NumeroVehicule);
                     /**
                      * insertion des objets lies
                      */
                     entrp.setArticle(artcle);
                     entrp.setMagasin(mag);
                     entrp.setFournisseur(fournissr);
                     entrp.setDemandeachat(dmde);
                   /**
                    * enregistrement dans le ArrayList
                    */
                     entreeP.add(entrp);
                     /**
                      * reinitialistion des objets
                      */
                     artcle=null;
                     mag=null;
                     fournissr=null;
                     dmde=null;
                     
               }
          } catch (SQLException ex) {
              Logger.getLogger(md_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
          }
                
               return entreeP;
         }
          
  }
