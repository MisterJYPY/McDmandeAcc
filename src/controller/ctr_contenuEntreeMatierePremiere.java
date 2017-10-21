/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.EntreeProduit;
import classe.SaisieVerificator;
import classe.database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEntreeMatierePremiere;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import vue.contenuEntreeMatierePremiere;

/**
 *cette classe est le controlleur comme son l'indique du contenu qui s'affiche lorsque on clik
 * sur le bouton entree produit de la gestion des Matieres Premieres.
 * cette classe regorge plusieurs methodes Surcharger pour reinitialiser ou remplir un JCOMBO BOX 
 * ou Jtexfiels en fonction du type du contenu a inserer
 * @author Mr_JYPY
 */
public class ctr_contenuEntreeMatierePremiere {
        private contenuEntreeMatierePremiere contenuEntreeMatierePremiere;
        static  private database dtb;
        private md_contenuEntreeMatierePremiere mdce;
         /**
          * 
          * @param contenuMatierePremiere 
          */
    public ctr_contenuEntreeMatierePremiere(contenuEntreeMatierePremiere contenuMatierePremiere) {
        this.contenuEntreeMatierePremiere = contenuMatierePremiere;
            /**
               * l'action de bouton de demandeAchat Premier choix
             */
           this.contenuEntreeMatierePremiere.getDemandeAchat().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererLesMatieresPremieres(evt);
            }
        }); 
           /**
            * Action sur le bouton demandeAchat second Choix
            */
            this.contenuEntreeMatierePremiere.getDemandeAchat1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererLesMatieresPremieresDansLeModel(evt);
            }
        });
           /**
            * action sur le choix des matieres Premieres
            */
            this.contenuEntreeMatierePremiere.getLesArticles().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mettreQuantiter(evt);
            }
        }); 
           this.contenuEntreeMatierePremiere.getQuantite().addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                VerificateurSaisie(evt);
            }
        });
             /**
              * Action sur le Validateur de l'autre Bouton
              */
             this.contenuEntreeMatierePremiere.getValiderAutreChoix().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TraitementBoutonAutrechoix(evt);
            }
        }); 
            /**
             * Fin validateur Autre Bouton
             */
            /**
             * les differentes requets pour l'initialisation des elements du panels qui s'afficheront
             */
       String sql="SELECT designation FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere')";
       String sql2="SELECT designation FROM magasin WHERE type!='cartonMc'";
    //   String sql3="SELECT nom FROM fournisseur";
     /**
      * Permet de Selectionner Tous les codes de Demande Acaht issu du service Achat et Production Dont 
      * les articles demander contiennenet au moin un article de la famille des Matiere Premieres
      */
   String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation) AND service=(SELECT id FROM services WHERE designation='ACHAT ET PRODUCTION') "
           + " AND demandeachat.id NOT IN(SELECT demandeAchat FROM demandesatisfaite)"
           + " AND id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT id FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
       //  remplirItem(this.contenuEntreeMatierePremiere.getLesArticles(),sql);
         remplirItem(this.contenuEntreeMatierePremiere.getMagasin(),sql2);
            remplirItem(this.contenuEntreeMatierePremiere.getMagasin1(),sql2);
        //  remplirItem(this.contenuEntreeMatierePremiere.getFournisseur(),sql3,"nom");
          /**
           * ces bout de code permettent de Remplir les elements les combo box des demande achat 1 et 2
           */
           remplirItem(this.contenuEntreeMatierePremiere.getDemandeAchat(),sql4,"code");
            remplirItem(this.contenuEntreeMatierePremiere.getDemandeAchat1(),sql4,"code");
            /**
             * mettre la date ds les labels
             */
        MettreDate(this.contenuEntreeMatierePremiere.getDateActuelle());
        MettreDate(this.contenuEntreeMatierePremiere.getDateActuelle1());
        /**
         * les  actions sur le bouton entrre
         */
        //MettreDate(this.contenuEntreeMatierePremiere.);
        contenuEntreeMatierePremiere.getEntrer().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnregistrerEntreeMatierePremiere(evt);
                  
            }
        });
        mdce=new md_contenuEntreeMatierePremiere();
    } 

    public contenuEntreeMatierePremiere getContenuEntreeMatierePremiere() {
        return contenuEntreeMatierePremiere;
    }

    public md_contenuEntreeMatierePremiere getMdce() {
        return mdce;
    }
        
    /**
         * 
         * @param cmbo notre combobox
         * @param sql  la requete sql referant le champ a inserer qui doit etre nomme "designation"
         * exemple de requete: "select designation from articles"
         */
          public static void remplirItem(JComboBox cmbo,String sql){
                 ResultSet res=null;
               
                 dtb=database.getInstance();
                 
           try {
               res=dtb.selectInTab(sql);
               while(res.next()){
                  //System.out.println(res.getString("designation"));
            //  String nom=res.getString("designation").replace("1", "'");
                     String nom=res.getString("designation");
               cmbo.addItem(nom);
               }
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
            }
          
          /**
           * cette methode est la redefinition de la premiere methode au cas ou le Champ doit etre specifier
           * @param cmbo une instance de comboBox
           * @param sql  notre requete de selection
           * @param champ le champ de la requete ou l'index
           */
          public static void remplirItem(JComboBox cmbo,String sql,String champ){
                 ResultSet res;
               
                 dtb=database.getInstance();
           try {
               res=dtb.selectInTab(sql);
               while(res.next()){
               cmbo.addItem(res.getString(champ));
               }
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
           
            }
          /**
           * c'est cette methode qui nous permettra de remplir un comboBox avec une liste de Arealiste
           * @param cmbo l'instance de combo box
           * @param liste le ArrayList contenant les elements a afficher dans le combobox
           */
            public static void remplirItem(JComboBox cmbo,ArrayList liste){
               int nbreElement=liste.size();
               if(nbreElement>0)
               {
               for(int i=0;i<nbreElement;i++)
               {
                cmbo.addItem((String)liste.get(i));
               }
               }
            }
           /**
             * permet de remplir un textfiels en lui passant une requete
             * @param txtF l'objet de type TextFiel
             * @param sql  la requete sql ou le champ a inserer a le nom "quntiter" c'es lindex
             */
            public void remplirItem(JTextField txtF,String sql){
                 ResultSet res;
                 dtb=database.getInstance();
           try {
               res=dtb.selectInTab(sql);
               while(res.next()){
               txtF.setText(res.getString("quantiter"));
               }
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
           
            }
    /**
     * surchage de la methode remplir item qui prends en parametre un JtexFields
     * et un Nombre Entier qu'il caste en caractere(chaine)
     * 
     * @param txtF l'instance de type JTextfield
     * @param nbre  le Nombre a inserer
     *
     */ 
         
         public static void remplirItem(JTextField txtF,int nbre){
               
               txtF.setText(Integer.toString(nbre));
            }
         /**
          * surchage de la methode remplir Item
          * qui sert de remplir un JtexTfiel avec une chaine de Caractere passer en paramettre
          * Pour l'instant le parametre cond sert a rien il faut juste passer une valeur aleatoire
          * 
          * @param txtF Le TextFields 
          * @param Text  Le text 
          * @param Cond   la variable a mettre,Mettez 0 
          */
   public static void remplirItem(JTextField txtF,String Text,int Cond){
               
               txtF.setText(Text);
            }
            /**
             * une surcharge de la methode remplir Item pour prendre en parametre le champ
             * @param cmbo l'instance de Combobox
             * @param sql  la requete sql 
             * @param champ le champ ou l'index a inserer present dans la requete
             */
            public static void remplirItem(JTextField cmbo,String sql,String champ){
                 ResultSet res;
                 
                 dtb=database.getInstance();
            
           try {
               res=dtb.selectInTab(sql);
               while(res.next()){
               cmbo.setText(res.getString(champ));
               }
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
           
            }
           /**
            * met une date à un Jlabel
            * @param label 
            */
          public static void MettreDate(JLabel label){ 
          DateTime dateDuJour = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
            String dateDuJourEnString = formatter.print(dateDuJour);
            label.setText(dateDuJourEnString);
          }
          public static String donnerDateJourFormatChaineFr()
          {
              String dateDuJourEnString="";
           DateTime dateDuJour = new DateTime();
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd MMMM yyyy");
            dateDuJourEnString = formatter.print(dateDuJour);
             return dateDuJourEnString;
          }
          /**
           * 
           * @param evt 
           */
          public void EnregistrerEntreeMatierePremiere( java.awt.event.ActionEvent evt){
               String dateAinserer="";
               // System.out.println(this.mdce.ListeEntreeP());
            
           String articles= this.contenuEntreeMatierePremiere.getLesArticles().getSelectedItem().toString();
           String Demande= this.contenuEntreeMatierePremiere.getDemandeAchat().getSelectedItem().toString(); 
           String Fournisseur= this.contenuEntreeMatierePremiere.getFournisseur().getSelectedItem().toString(); 
           String dateActuelle=this.contenuEntreeMatierePremiere.getDateActuelle().getText();
           String libelle=this.contenuEntreeMatierePremiere.getbonCommande().getText();
           String vehiculeNumber=this.contenuEntreeMatierePremiere.getVehiculeNumber().getText();
           int quantite=0;
              if(!"".equals(this.contenuEntreeMatierePremiere.getQuantite().getText()))
                 {
                quantite=Integer.parseInt(this.contenuEntreeMatierePremiere.getQuantite().getText());
                 }
       String dateChosser=((JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent())).getText();
       String magasin=this.contenuEntreeMatierePremiere.getMagasin().getSelectedItem().toString();
          /**
           * controle de saisie
           */
               JTextField text[]=new JTextField[3];
               JComboBox cmbo[]=new JComboBox[4];
               JComboBox cmborei[]=new JComboBox[2];
                   /**
                    * remlissage des jtextfiels pour le controle
                    */
                   text[0]=this.contenuEntreeMatierePremiere.getVehiculeNumber();
                   text[1]=this.contenuEntreeMatierePremiere.getbonCommande();
                   text[2]=this.contenuEntreeMatierePremiere.getQuantite();
                 // text[2]=(JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent());
               /**
                * remplissage des Jcombox pour le controle
                */
                cmbo[0]=this.contenuEntreeMatierePremiere.getLesArticles();
                cmbo[1]= this.contenuEntreeMatierePremiere.getDemandeAchat();
                cmbo[2]=this.contenuEntreeMatierePremiere.getFournisseur();
                cmbo[3]=this.contenuEntreeMatierePremiere.getMagasin();
 //  text[3]=((JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent()));
                /**
                 * variable dinitialisation du tableau pour creer la reinitialisation
                 */
                cmborei[0]= this.contenuEntreeMatierePremiere.getLesArticles();
                cmborei[1]=this.contenuEntreeMatierePremiere.getFournisseur();
               // cmborei[3]=this.contenuEntreeMatierePremiere.getMagasin();
               // cmborei[2]=this.contenuEntreeMatierePremiere.getDemandeAchat();
                boolean PresenceVdeSaisie=verificateurDrChampText(text);
                boolean PresenceVdeSelection=verificateurSelection(cmbo);
                boolean quantiterSup=verifierQuantiterSaisie(Demande, articles, quantite);
                   Date date=new Date();
              if(!PresenceVdeSaisie && !PresenceVdeSelection && quantiterSup){
      this.contenuEntreeMatierePremiere.getStatut().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/traitement.gif"))); // NOI18N
         //  this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().setDateFormatString("Y-m-d");
                  if(!dateChosser.isEmpty())
                  {
                date=this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getDate();
                if(dateActuelle.equals(dateChosser)){
                  dateAinserer=dateActuelle;
                }
                else{
                   dateAinserer=dateChosser;   
                }
              
                  }
                  else
                  {
                  dateAinserer=dateActuelle;
                  }
                SimpleDateFormat formater = null;
               java.sql.Date dates= new java.sql.Date(date.getTime());
           String type="ENT";
               /**
                * texte de la date
                */
          //  System.out.println("LA DATE PRISE EST :"+date);
    
                    //  {
           try {
               // }
               /**
                * insertion ou creation de l'entree et tous ceci doit etre mis dans un commit
                */
               dtb.getCon().setAutoCommit(false);
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
           
    EntreeProduit entreeP=mdce.GenererUntype("Matiere premiere",articles,magasin,dateActuelle,libelle,Demande,dateAinserer,quantite,vehiculeNumber,Fournisseur,dates);
     if(this.mdce.CreerEntreeProduit(entreeP))
              {
              /**
               * explication de la portion de code:
               * dabord on si linsertion de lentree a ete un succes
               * on 
               */ 
                  
                   /**
              * la requete qui permetttra d'ajouter les matieres premieres
              */
          String sqlactualisationJour="SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.numeroVehicule,entree_produit.derniere_modif,"
                + "entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,\n" +
            "entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_produit.article "
                + "WHERE entree_produit.date=CURRENT_DATE() AND entree_produit.article="+entreeP.getArticle().getId()+" AND stocks_avant="+entreeP.getArticle().getStocks().getQuantite()+" "
                  + " AND entree_produit.stocks_apres="+entreeP.getStocks_apres()+" AND entree_produit.demandeAchat="+entreeP.getDemandeachat().getId()+""
                  + " AND entree_produit.fournisseur="+entreeP.getFournisseur().getId()+" AND entree_produit.numeroVehicule='"+entreeP.getNumeroVehicule()+"'"
                  + " ORDER BY derniere_modif DESC";
            /**
             * end of sql actualisation
             */
             /**
              * refractor de table Toutes les entrees
             */
             String sqlactualisation="SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.numeroVehicule,entree_produit.derniere_modif,"
                + "entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,\n" +
            "entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_produit.article "
                + "WHERE entree_produit.article="+entreeP.getArticle().getId()+" AND stocks_avant="+entreeP.getArticle().getStocks().getQuantite()+" "
                  + " AND entree_produit.stocks_apres="+entreeP.getStocks_apres()+" AND entree_produit.demandeAchat="+entreeP.getDemandeachat().getId()+""
                  + " AND entree_produit.fournisseur="+entreeP.getFournisseur().getId()+" AND entree_produit.numeroVehicule='"+entreeP.getNumeroVehicule()+"'"
                  + " ORDER BY derniere_modif DESC";
           /**
           * end of
           */
       String sqlParfait="INSERT INTO `demandesatisfaite`( `demandeAchat`) VALUES ("+entreeP.getDemandeachat().getId()+")";
         boolean etatDesEntree=this.mdce.etatDeLademande(Demande);
         boolean insertionFaite=false;
    String sqlActualisation="UPDATE `stocks` SET `quantite`="+entreeP.getStocks_apres()+" WHERE articles="+entreeP.getArticle().getId()+"";
            /**
             * recuperation du magasin et du Produit
             */
            //Magasin mag=    
         /**
           * fin recuperation magasin et du produit
         */
       int stocksMagasin=0;
       stocksMagasin=this.mdce.StocksArticlesEnMagasin(entreeP.getMagasin(),entreeP.getArticle());
                            // System.out.println("SOCKTS DY MAG "+stocksMagasin);
     int NvoStocksMag=0;
     NvoStocksMag=stocksMagasin+entreeP.getQuantite();
   String sqlActualisationMagasin="UPDATE `stocks_magasin` SET `quantite`="+NvoStocksMag+" WHERE produit="+entreeP.getArticle().getId()+" AND magasin="+entreeP.getMagasin().getId()+" ";
           if(dtb.InserData(sqlActualisationMagasin)){
               System.out.println("MAGASIN ACTUALISER AVEC SUCCES ");
       if(dtb.InserData(sqlActualisation))
                  {
                  if(etatDesEntree){
            if(dtb.InserData(sqlParfait))
                {
           insertionFaite=true;   
           System.out.println("!!!!!!!!TOUT C BIEN DEROULER!!!!!!!!!!!!!!!!!!!!!!!!");
                }
             // }
                 }
                  
                  }
         try {
                      AfficherEntreeDuJour(sqlactualisationJour,"");
                      AfficherEntree(sqlactualisationJour,"");
     // NOI18N   
                     //    dtb.getCon().commit();
  // 
                        reinitialiserTout(cmborei);
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getDemandeAchat());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getDemandeAchat());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getMagasin());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getMagasin());
                     reinitialiserToutEnBoucle(cmbo);
                        reinitialiserTout(text);
                    /**
                     * remplissage demande Article
                     */
          String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
    + " SELECT demandeAchat FROM cotation) AND service=(SELECT id FROM services WHERE designation='ACHAT ET PRODUCTION') "
      + " AND demandeAchat.id NOT IN(SELECT demandeAchat FROM demandesatisfaite)"
         + " AND id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT id FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
              remplirItem(this.contenuEntreeMatierePremiere.getDemandeAchat(), sql4,"code");
        /**
         * reinitialisation du magasin
         */
             String sqlM="SELECT designation FROM magasin WHERE type!='cartonMc'";
            remplirItem(this.contenuEntreeMatierePremiere.getMagasin(), sqlM,"designation");
                 dtb.getCon().commit();
  this.contenuEntreeMatierePremiere.getStatut().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.jpg")));
                dtb.getCon().setAutoCommit(true);
          // String
                       //  System.out.println ("EST entree : "+.getDemandeachat());
                   } catch (SQLException ex) {
                       Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
        // ArrayList <EntreeProduit> Derniere=this.mdce.ListeEntreeP(sqlactualisationJour); 
                     
                   }
          
           }
     else{
           System.out.println("ECHEC DANS LINSERTION");
     }
              }
                else{
                System.out.println("REVOYEZ VOS CHMAPS SVP");
                }
            
          }
              else{
                 
                   String message="Vous Avez Mal Renseigner Vos Champs Revoyer Les SVP";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
             JOptionPane.showMessageDialog(null, message,"Erreur Champs",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }  
          }
         public void  genererLesMatieresPremieres( java.awt.event.ActionEvent evt){
              dtb=database.getInstance();
               reinitialiserTout(this.contenuEntreeMatierePremiere.getFournisseur());
           String DemandeAchat=evt.getActionCommand();
              if(DemandeAchat=="comboBoxChanged"){
              String Demande= this.contenuEntreeMatierePremiere.getDemandeAchat().getSelectedItem().toString();
            if(Demande!="Aucun choix"){
           /**
            * requete permettant de selectionner tous les articles
            * qui viennent de la famille des Matieres Premieres ,en Parametre id de la demande de Bordereaux
            */
        String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
           /**
            * requete d'essai pour la selection de Produit
            */
              String sqlMats="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')"
                     + " AND articlesdemandeachat.quantiter<(SELECT SUM(quantite) FROM entree_produit WHERE demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"' "
                     + " AND article=articlesdemandeachat.article)";
             // System.out.println(mdce.ArticlesNonComplete(sqlMat, Demande));
             /**
              * end of requete d'essai ,nous allons faire un parcours pour voir ceux qui sont succeptible d'etre afficher
              */
                // reinitialiserTout(this.contenuEntreeMatierePremiere.getLesArticles());
                 reinitialiserTout(this.contenuEntreeMatierePremiere.getQuantite());
                 //reinitialiserTout(this.contenuEntreeMatierePremiere.getFournisseur());
                 reinitialiserToutEnBoucle(this.contenuEntreeMatierePremiere.getLesArticles());
                 reinitialiserToutEnBoucle(this.contenuEntreeMatierePremiere.getFournisseur());
             //  remplirItem(this.contenuEntreeMatierePremiere.getLesArticles(),sqlMat);
           remplirItem(this.contenuEntreeMatierePremiere.getLesArticles(),md_contenuEntreeMatierePremiere.ArticlesNonComplete(sqlMat, Demande));
                  System.out.println("EST CE QUE TOUS LES PRODUITS SONT ENTREE CORRECTEMENT ?:"+mdce.etatDeLademande(Demande));
                 Demande="MC"+Demande;
             String sqlFournisseur="SELECT fournisseursurcotation.id,fournisseur.nom as designation FROM fournisseursurcotation "
                     + " INNER JOIN fournisseur ON fournisseur.id=fournisseursurcotation.fournisseur "
                     + "  WHERE fournisseursurcotation.cotation=(SELECT id FROM cotation WHERE code_cotation='"+Demande+"')";
                  //this.contenuEntreeMatierePremiere.getFournisseur().removeAllItems();
             remplirItem(this.contenuEntreeMatierePremiere.getFournisseur(),sqlFournisseur);
            }
            else{
            //  System.out.println("echec: "+Demande);
              }
              }
             //System.out.println(DemandeAchat);
         }
         /**
          * code action pour mettre les articles dans le tableModel
          * @param evt 
          */
         public void genererLesMatieresPremieresDansLeModel( java.awt.event.ActionEvent evt)
             {
          String Demande= this.contenuEntreeMatierePremiere.getDemandeAchat1().getSelectedItem().toString(); 
          DefaultTableModel models=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableChoix().getModel();
                reinitialiserTout(models);
            if(!Demande.equals("Aucun choix")){
          String sql="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
         
                 dtb=database.getInstance();
                  ResultSet res=null;
                  System.out.println(sql);
            try {
               res=dtb.selectInTab(sql);
               /**
                * partie pour inserer dans le Model
                */
       
         Object [] row =new Object[4];
           int nbreLignePresents=0;
           int i=1;
           while(res.next())
              {
          /**
           * Nous allons recuperer les quantiter normal a afficher
           */
            int quantiterDemander=res.getInt("quantiter");
            int quantiterDeJaEntrer=this.mdce.AvoirQuantiterEntree(Demande,res.getString("designation"));
            int quantiterRestant=quantiterDemander-quantiterDeJaEntrer;
                 if(quantiterRestant>0){
               row[0]=i;
               row[1]=res.getString("designation");
               row[2]=quantiterRestant;
               row[3]=Demande;
               models.addRow(row);
               i++;
                 }
              }
           
            } catch (SQLException ex) {
                Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
            }
             /**
           * partie Fournisseur
           */
                Demande="MC".concat(Demande);
           String sqlFournisseurs="SELECT fournisseursurcotation.id,fournisseur.nom as designation FROM fournisseursurcotation "
                     + " INNER JOIN fournisseur ON fournisseur.id=fournisseursurcotation.fournisseur "
                     + "  WHERE fournisseursurcotation.cotation=(SELECT id FROM cotation WHERE code_cotation='"+Demande+"')";
                  //this.contenuEntreeMatierePremiere.getFournisseur().removeAllItems();
            //String sqlF="SELECT nom as designation FROM fournisseur";
          //    System.out.println(this.contenuEntreeMatierePremiere.getFournisseurAutreChoice());
                reinitialiserToutEnBoucle(this.contenuEntreeMatierePremiere.getFournisseurAutreChoice());
            remplirItem((JComboBox)this.contenuEntreeMatierePremiere.getFournisseurAutreChoice(),sqlFournisseurs);
             /**
              * partie Fournisseur
              */
            } 
             }
          public void mettreQuantiter( java.awt.event.ActionEvent evt){
              dtb=database.getInstance();
           String DemandeAchat=evt.getActionCommand();
              if("comboBoxChanged".equals(DemandeAchat)){
           String ArticlesDesignation= this.contenuEntreeMatierePremiere.getLesArticles().getSelectedItem().toString();  
           String Demande= this.contenuEntreeMatierePremiere.getDemandeAchat().getSelectedItem().toString();  
                 if(!"Aucun choix".equals(Demande)){
                     
         String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articlesdemandeachat.quantiter,articles.designation "
                 + " FROM articlesdemandeachat"
                 + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                 + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                 + " WHERE articlesdemandeachat.article=(SELECT id from articles WHERE designation='"+ArticlesDesignation+"')"
                 + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
//          this.contenuEntreeMatierePremiere.getQuantite().setText
          reinitialiserTout(this.contenuEntreeMatierePremiere.getQuantite());
       // System.out.println("LA QUANITER QUE VOUS DEVRIER ENTRER EST: "+mdce.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation));
      //remplirItem(this.contenuEntreeMatierePremiere.getQuantite(), sqlMat);
      remplirItem(this.contenuEntreeMatierePremiere.getQuantite(), +mdce.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation));
                 }
              }
               }
       /**
        * permet de reinitialiser nimporte quelle Jcombobox mis en parametre
        * @param jcb 
        */
          public static void reinitialiserTout(JComboBox jcb)
          {
//            DefaultComboBoxModel cmbModel=(DefaultComboBoxModel)jcb.getModel();
//				// Vider le JComboBox
//	            cmbModel.removeAllElements();
          int nbre=jcb.getItemCount();
          if(nbre>0)
          {
           for(int i=1;i<=nbre;i++){
               if(jcb.getItemAt(i)!=null){
           jcb.removeItemAt(i);
           }
          }
          }}
          public static void reinitialiserToutEnBoucle(JComboBox [] jcb)
          {
         
      for(int i=0;i<jcb.length;i++)
      {
           boolean finish=false;
           do{
      int nbreLigne=jcb[i].getItemCount();    
      if(nbreLigne==1)
      {
          finish=true;
      }
        ctr_contenuEntreeMatierePremiere.reinitialiserTout(jcb[i]);
    }
    while(!finish);
          
          }
          }
           public static void reinitialiserToutEnBoucle(JComboBox  jcb)
          {
         
     
           boolean finish=false;
           do{
      int nbreLigne=jcb.getItemCount();    
      if(nbreLigne==1)
      {
          finish=true;
      }
        ctr_contenuEntreeMatierePremiere.reinitialiserTout(jcb);
    }
    while(!finish);
          }
         /**
           * methode permettant de reinitialiser un Tableau de combobox
           * @param jcb  le comBobox
           */
            public static void reinitialiserTout(JComboBox []jcb)
          {
//            DefaultComboBoxModel cmbModel=(DefaultComboBoxModel)jcb.getModel();
//				// Vider le JComboBox
//	            cmbModel.removeAllElements();
          int nbres=jcb.length;
          System.out.println("LE NOMBRE EST :"+nbres);
           for(int j=0;j<nbres;j++){
               int nbre=jcb[j].getItemCount();
          if(nbre>0)
          {  
           
            ///  jcb[j].removeAllItems();
           for(int i=1;i<=nbre;i++){
               if(jcb[j].getItemAt(i)!=null){
              jcb[j].removeItemAt(i);
           }
        }
         }
          }}
             public void reinitialiserTout(JTextField []jtxf)
          {
              int nbre=jtxf.length;
              for(int i=0;i<nbre;i++){
            jtxf[i].setText("");
              }
          }
          /**
           * reinitialisation du jtexfield(surchage de la methode
           * @param jtxf 
           */
          public void reinitialiserTout(JTextField jtxf)
          {
          jtxf.setText("");
          }
          /**
           * 
            * @param models
           */
          public static void reinitialiserTout(DefaultTableModel models)
          {
          int nbre=models.getRowCount();
             models.getDataVector().clear();
             models.fireTableRowsDeleted(0, nbre);
          }
          private void VerificateurSaisie(java.awt.event.KeyEvent evt) { 
	      SaisieVerificator.veritierLabel(this.contenuEntreeMatierePremiere.getQuantite(),evt.getKeyChar());
		}
          /**
           * 
           * @param txt
           * @return un boolean qui stipule 
           * si les parametre passer ont un champ vide
           */
          public static boolean verificateurDrChampText(JTextField []txt)
          {
             boolean presenceVide=false;
                    for(int i=0;i<txt.length;i++)
                    {
                        if(txt[i].getText().isEmpty())
                        {
                        presenceVide=true;
                        }
                    }
             return presenceVide;
          }
            public static boolean verificateurSelection(JComboBox []txt)
          {
             boolean presenceVide=false;
           for (JComboBox txt1 : txt) {
               if ("Aucun choix".equals(txt1.getSelectedItem().toString())) {
                   presenceVide=true;
               }
           }
             return presenceVide;
          }
      public static void AfficherEntreeDuJour(ArrayList entrp,DefaultTableModel model)
            {
         Object [] row =new Object[8];
           for(int i=0;i<entrp.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((EntreeProduit)(entrp.get(i))).getArticle().getDesignation();
               row[2]=((EntreeProduit)(entrp.get(i))).getLibelle();
               row[3]=((EntreeProduit)(entrp.get(i))).getDemandeachat().getBordereaux();
               row[4]=((EntreeProduit)(entrp.get(i))).getQuantite();
               row[5]=((EntreeProduit)(entrp.get(i))).getStocks_avant();
               row[6]=((EntreeProduit)(entrp.get(i))).getStocks_apres();
               row[7]=((EntreeProduit)(entrp.get(i))).getFournisseur().getNom();
               
               model.addRow(row);
              }
           }
      //
      /**
       * methode pour remplir des JtableDefault Model si 
       * in y avait deja des elements
       * cette methode est <strong>uniquement pour un jtable a 8 colonnes</strong>
       * @param entrp  la liste dentree produit
       * @param model le table Model
       * @param Default variable par defaut,mettez 0
       */
        public static void AfficherEntreeDuJour( ArrayList entrp ,DefaultTableModel model,int Default)
            {
          int nbreLignePresents=0;
         Object [] row =new Object[8];
              
            for (Object entrp1 : entrp) {
                nbreLignePresents=model.getRowCount()+1;
                row[0]=nbreLignePresents;
                row[1] = ((EntreeProduit) (entrp1)).getArticle().getDesignation();
                row[2] = ((EntreeProduit) (entrp1)).getLibelle();
                row[3] = ((EntreeProduit) (entrp1)).getDemandeachat().getBordereaux();
                row[4] = ((EntreeProduit) (entrp1)).getQuantite();
                row[5] = ((EntreeProduit) (entrp1)).getStocks_avant();
                row[6] = ((EntreeProduit) (entrp1)).getStocks_apres();
                row[7] = ((EntreeProduit) (entrp1)).getFournisseur().getNom();
                model.addRow(row);
            }
           }
      //initialisation A 11 colonee
        /**
         * concu specialement pour 11 colonnes ayant 
         * deja des valeurs
         * @param entrp la liste d'instance EntreeProduit (dans un ArrayList)
         * @param model l'instance de DefaultTableModel
         * @param Default  n'est pas utiliser en tant que tel un parametre come le vide
         */
         public static void AfficherEntreeDuJour( ArrayList entrp ,DefaultTableModel model,String Default)
            {
          int nbreLignePresents=0;
        Object [] row =new Object[11];
              
            for (Object entrp1 : entrp) {
                nbreLignePresents=model.getRowCount()+1;
                row[0]=nbreLignePresents;
                row[1] = ((EntreeProduit) (entrp1)).getArticle().getDesignation();
                row[2] = ((EntreeProduit) (entrp1)).getNumeroVehicule();
                row[3] = ((EntreeProduit) (entrp1)).getLibelle();
                row[4] = ((EntreeProduit) (entrp1)).getQuantite();
                row[5] = ((EntreeProduit) (entrp1)).getStocks_avant();
                row[6] = ((EntreeProduit) (entrp1)).getStocks_apres();
                row[7] = ((EntreeProduit) (entrp1)).getMagasin().getDesignation();
                row[8] = ((EntreeProduit) (entrp1)).getFournisseur().getNom();
                row[9] = ((EntreeProduit) (entrp1)).getDemandeachat().getBordereaux();
                row[10] = ((EntreeProduit) (entrp1)).getDerniereModif();
                model.addRow(row);
            }
           }
       public static void AfficherEntree(ArrayList entrp,DefaultTableModel model)
            {
       
         Object [] row =new Object[11];
           for(int i=0;i<entrp.size();i++)
              {
             int nbre=i+1;
               row[0]=nbre;
               row[1]=((EntreeProduit)(entrp.get(i))).getArticle().getDesignation();
               row[2]=((EntreeProduit)(entrp.get(i))).getNumeroVehicule();
               row[3]=((EntreeProduit)(entrp.get(i))).getLibelle();
               row[4]=((EntreeProduit)(entrp.get(i))).getQuantite();
               row[5]=((EntreeProduit)(entrp.get(i))).getStocks_avant();
               row[6]=((EntreeProduit)(entrp.get(i))).getStocks_apres();
               row[7]=((EntreeProduit)(entrp.get(i))).getMagasin().getDesignation();
               row[8]=((EntreeProduit)(entrp.get(i))).getFournisseur().getNom();
               row[9]=((EntreeProduit)(entrp.get(i))).getDemandeachat().getBordereaux();
              row[10]=((EntreeProduit)(entrp.get(i))).getDerniereModif();
               model.addRow(row);
              }
           }
       //
      
        public void AfficherEntreeDuJour(String sql)
            {
        ArrayList<EntreeProduit> entrp=this.mdce.ListeEntreeP(sql);
         // System.out.println("Nombre de Element est :"+ entrp.size());
       DefaultTableModel model=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableOption1().getModel();
         Object [] row =new Object[8];
           for(int i=0;i<entrp.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=entrp.get(i).getArticle().getDesignation();
               row[2]=entrp.get(i).getLibelle();
               row[3]=entrp.get(i).getDemandeachat().getBordereaux();
               row[4]=entrp.get(i).getQuantite();
               row[5]=entrp.get(i).getStocks_avant();
               row[6]=entrp.get(i).getStocks_apres();
               row[7]=entrp.get(i).getFournisseur().getNom();
               
               model.addRow(row);
              }
           }
      /**
       * cette Methode depends fortement du parametre sql
       * permet de afficher la liste des Entree dans JTAble
       * le Jtable est construite dans la methode mais cette methode devra etre surcharge pour qu'elle
       * soit un peu independante
       * @param sql la requete 
       * @param part 
       */
       public void AfficherEntreeDuJour(String sql,String part)
            {
        ArrayList<EntreeProduit> entrp=this.mdce.ListeEntreeP(sql);
       DefaultTableModel model=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableOption1().getModel();
          int nbreLignePresents=0;
         Object [] row =new Object[8];
              
           for(int i=0;i<entrp.size();i++)
              {
               nbreLignePresents=model.getRowCount()+1;
               int nbre=i+1;
               row[0]=nbreLignePresents;
               row[1]=entrp.get(i).getArticle().getDesignation();
               row[2]=entrp.get(i).getLibelle();
               row[3]=entrp.get(i).getDemandeachat().getBordereaux();
               row[4]=entrp.get(i).getQuantite();
               row[5]=entrp.get(i).getStocks_avant();
               row[6]=entrp.get(i).getStocks_apres();
               row[7]=entrp.get(i).getFournisseur().getNom();
               
               model.addRow(row);
              }
           }
         
           public void AfficherEntree(String sql)
            {
        ArrayList<EntreeProduit> entrp=this.mdce.ListeEntreeP(sql);
       DefaultTableModel model=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableOption2().getModel();
         Object [] row =new Object[11];
           for(int i=0;i<entrp.size();i++)
              {
             int nbre=i+1;
               row[0]=nbre;
               row[1]=entrp.get(i).getArticle().getDesignation();
               row[2]=entrp.get(i).getNumeroVehicule();
               row[3]=entrp.get(i).getLibelle();
               row[4]=entrp.get(i).getQuantite();
               row[5]=entrp.get(i).getStocks_avant();
               row[6]=entrp.get(i).getStocks_apres();
               row[7]=entrp.get(i).getMagasin().getDesignation();
               row[8]=entrp.get(i).getFournisseur().getNom();
               row[9]=entrp.get(i).getDemandeachat().getBordereaux();
              row[10]=entrp.get(i).getDerniereModif();
               model.addRow(row);
              }
           }
           /**
            * Permet d'avoir la liste des entrees dans un jtable
            * en fonction de la requete et la date sous format String 
            * Methode Non complete qui sera surcharger pour qu'elle s'adapte aux autres models
            * @param sql
            * @param part 
            */
             public void AfficherEntree(String sql,String part)
            {
        ArrayList<EntreeProduit> entrp=this.mdce.ListeEntreeP(sql);
       DefaultTableModel model=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableOption2().getModel();
         Object [] row =new Object[11];
           int nbreLignePresents=0;
           for(int i=0;i<entrp.size();i++)
              {
             int nbre=i+1;
               nbreLignePresents=model.getRowCount()+1;
               row[0]=nbreLignePresents;
               row[1]=entrp.get(i).getArticle().getDesignation();
               row[2]=entrp.get(i).getNumeroVehicule();
               row[3]=entrp.get(i).getLibelle();
               row[4]=entrp.get(i).getQuantite();
               row[5]=entrp.get(i).getStocks_avant();
               row[6]=entrp.get(i).getStocks_apres();
               row[7]=entrp.get(i).getMagasin().getDesignation();
               row[8]=entrp.get(i).getFournisseur().getNom();
               row[9]=entrp.get(i).getDemandeachat().getBordereaux();
              row[10]=entrp.get(i).getDerniereModif();
               model.addRow(row);
              }
           }
             /**
              * c'est la methode qui est appeler lorsque on clique sur le bouton d'action
              * de la seconde methode de creer une entree de matiere premiere(en groupe)
              * elle est identique à la premiere sauf que il y a une boucle qui est lancer pour
              * tous les articles
              * @param evt 
              */
             public void TraitementBoutonAutrechoix( java.awt.event.ActionEvent evt)
             {
                 String dateAinserer="";
                 Date date;
          /**
           * Nous allons verifier si tous les elements sont Presents 
           * et Bien renseigner
           */
           String Demande= this.contenuEntreeMatierePremiere.getDemandeAchat1().getSelectedItem().toString(); 
           String Fournisseur= this.contenuEntreeMatierePremiere.getFournisseurAutreChoice().getSelectedItem().toString(); 
           String dateActuelle=this.contenuEntreeMatierePremiere.getDateActuelle1().getText();
           String libelle=this.contenuEntreeMatierePremiere.getBonComande1().getText();
           String vehiculeNumber=this.contenuEntreeMatierePremiere.getVehiculeNumber1().getText();
                
       String dateChosser=((JTextField)(this.contenuEntreeMatierePremiere.getAutreDatePourAutreOption().getDateEditor().getUiComponent())).getText();
       String magasin=this.contenuEntreeMatierePremiere.getMagasin1().getSelectedItem().toString();
          /**
           * controle de saisie
           */
               JTextField text[]=new JTextField[2];
               JComboBox cmbo[]=new JComboBox[3];
               JComboBox cmborei[]=new JComboBox[1];
                   /**
                    * remlissage des jtextfiels pour le controle
                    */
                   text[0]=this.contenuEntreeMatierePremiere.getVehiculeNumber1();
                   text[1]=this.contenuEntreeMatierePremiere.getBonComande1();
                 // text[2]=(JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent());
               /**
                * remplissage des Jcombox pour le controle
                */
                cmbo[0]= this.contenuEntreeMatierePremiere.getDemandeAchat1();
                cmbo[1]=this.contenuEntreeMatierePremiere.getFournisseurAutreChoice();
                cmbo[2]=this.contenuEntreeMatierePremiere.getMagasin1();
           // cmbo[2]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
    //  text[3]=((JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent()));
                /**
                 * variable dinitialisation du tableau pour creer la reinitialisation
                 */
                cmborei[0]= this.contenuEntreeMatierePremiere.getFournisseurAutreChoice();
               // cmborei[1]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
               // cmborei[3]=this.contenuEntreeMatierePremiere.getMagasin1();
               // cmborei[2]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
           /**
            * 
            */
                boolean PresenceVdeSaisie=verificateurDrChampText(text);
                boolean PresenceVdeSelection=verificateurSelection(cmbo);   
                  date=new Date();
            /**
             * 
             * fin de la verification ET debut de l'etat
             */
                  if(!PresenceVdeSaisie && !PresenceVdeSelection){
         //  this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().setDateFormatString("Y-m-d");
        this.contenuEntreeMatierePremiere.getLabelIndicationAutreChoix().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/traitement.gif"))); // NOI18N
                  if(!dateChosser.isEmpty())
                  {
                date=this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getDate();
                if(dateActuelle.equals(dateChosser)){
                  dateAinserer=dateActuelle;
                }
                else{
                   dateAinserer=dateChosser;   
                }
              
                  }
                  else
                  {
                  dateAinserer=dateActuelle;
                  }
                SimpleDateFormat formater = null;
               java.sql.Date dates= new java.sql.Date(date.getTime());
              /**
               * Debut du code Pour Inserer
               */
       DefaultTableModel models=( DefaultTableModel) this.contenuEntreeMatierePremiere.getTableChoix().getModel();
          int nbreLigne=models.getRowCount();
       boolean estOk=verifierQuantiterEntrer(models);
         if(estOk){
             try {
               dtb.getCon().setAutoCommit(false);
               /**
                * deroulement
                */
                String demande="";
                String articles="";
                int quantite=0;
                  for(int i=0;i<nbreLigne;i++)
             {
                System.out.println("ELEMENT "+i);
                
             articles = (String) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,1);
            if(this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2).getClass().equals(articles.getClass())){
                quantite =Integer.parseInt(this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2).toString());
            }
            else{
             quantite = (int) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2);
            }
            demande = (String) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,3);
        ExecueterUneEntree(articles,magasin,dateActuelle,libelle,Demande,dateAinserer,quantite,vehiculeNumber,Fournisseur,dates);
              }
               /**
                * fin du deroulement
                */
                 // reinitialiserTout(cmbo);
                   reinitialiserTout(models);
                 /**
                  * code de reinitialisation
                  */
                     reinitialiserTout(cmborei);
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getDemandeAchat1());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getDemandeAchat1());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getMagasin1());
//                     reinitialiserTout(this.contenuEntreeMatierePremiere.getMagasin1());
                      reinitialiserToutEnBoucle(cmbo);
                        reinitialiserTout(text);
                    /**
                     * remplissage demande Article
                     */
          String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
    + " SELECT demandeAchat FROM cotation) AND service=(SELECT id FROM services WHERE designation='ACHAT ET PRODUCTION') "
      + " AND demandeAchat.id NOT IN(SELECT demandeAchat FROM demandesatisfaite)"
         + " AND id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT id FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
              remplirItem(this.contenuEntreeMatierePremiere.getDemandeAchat1(), sql4,"code");
        /**
         * reinitialisation du magasin
         */
             String sqlM="SELECT designation FROM magasin WHERE type!='cartonMc'";
            remplirItem(this.contenuEntreeMatierePremiere.getMagasin1(), sqlM,"designation");
                 /**
                  * fin du code de reinitialisation
                  */
                  dtb.getCon().commit();
  this.contenuEntreeMatierePremiere.getLabelIndicationAutreChoix().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.jpg"))); // NOI18N
                  dtb.getCon().setAutoCommit(true);
                     
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
           }
                 }
          else{
           String message="Revoyez les Quantiter Entrees";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
        JOptionPane.showMessageDialog(null, message,"Erreur Quantiter",JOptionPane.INFORMATION_MESSAGE, img2);      
               }
                  
                  }
                  else{
        String message="Revoyez Vos Champs Saisie ou Selectionner";
        ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
        JOptionPane.showMessageDialog(null, message,"!!!Error Insert!!!",JOptionPane.INFORMATION_MESSAGE, img2);         
                  }
             }
             /**
              * permet de verifier sur une liste de Jtable les quantiter saisies sont bonnes ou pas
              * @param model
              * @return un boolean si toutes les saisies sont bonnes ou pas
              */
        public boolean verifierQuantiterEntrer(DefaultTableModel model){
               boolean estOk=true;
               int nbreLigne=this.contenuEntreeMatierePremiere.getTableChoix().getRowCount();
               int nbreColonne=this.contenuEntreeMatierePremiere.getTableChoix().getColumnCount();
                // this.mdce.
                   for(int i=0;i<nbreLigne;i++)
             {
                System.out.println("ELEMENT "+i);
                
                String demande="";
                String Article="";
                int quantite=0;
             Article = (String) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,1);
            if(this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2).getClass().equals(Article.getClass())){
                quantite =Integer.parseInt(this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2).toString());
            }
            else{
             quantite = (int) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,2);
            }
             demande = (String) this.contenuEntreeMatierePremiere.getTableChoix().getValueAt(i,3);
         int quantiterDemander=+md_contenuEntreeMatierePremiere.AvoirQuantiterDemander(demande, Article);
        int quantiterEntrer=+md_contenuEntreeMatierePremiere.AvoirQuantiterEntree(demande, Article);
        int quantiterRestant=quantiterDemander-quantiterEntrer;
           if(quantiterRestant<quantite)
           {
             estOk=false;
           }
              }
             
                return estOk;
            }
      /**
       * permet de generer une instance de Entree Produit et 
       * de creer en base cad linserer avec toutes les actualisations possible
       * comme le stocks que ce soit en Magasin ou en entrepot
       * @param articles
       * @param magasin
       * @param dateActuelle
       * @param libelle
       * @param Demande
       * @param dateAinserer
       * @param quantite
       * @param vehiculeNumber
       * @param Fournisseur
       * @param dates 
       */
   public void ExecueterUneEntree(String articles,String magasin,String dateActuelle,String libelle,String Demande,String dateAinserer,int quantite,String vehiculeNumber,String Fournisseur,java.sql.Date dates){
           
            EntreeProduit entreeP=mdce.GenererUntype("Matiere premiere",articles,magasin,dateActuelle,libelle,Demande,dateAinserer,quantite,vehiculeNumber,Fournisseur,dates);
     if(this.mdce.CreerEntreeProduit(entreeP))
              {
              /**
               * explication de la portion de code:
               * dabord on si linsertion de lentree a ete un succes
               * on 
               */ 
                  
                   /**
              * la requete qui permetttra d'ajouter les matieres premieres
              */
          String sqlactualisationJour="SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.numeroVehicule,entree_produit.derniere_modif,"
                + "entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,\n" +
            "entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_produit.article "
                + "WHERE entree_produit.date=CURRENT_DATE() AND entree_produit.article="+entreeP.getArticle().getId()+" AND stocks_avant="+entreeP.getArticle().getStocks().getQuantite()+" "
                  + " AND entree_produit.stocks_apres="+entreeP.getStocks_apres()+" AND entree_produit.demandeAchat="+entreeP.getDemandeachat().getId()+""
                  + " AND entree_produit.fournisseur="+entreeP.getFournisseur().getId()+" AND entree_produit.numeroVehicule='"+entreeP.getNumeroVehicule()+"'"
                  + " ORDER BY derniere_modif DESC";
            /**
             * end of sql actualisation
             */
             /**
              * refractor de table Toutes les entrees
             */
             String sqlactualisation="SELECT entree_produit.id,entree_produit.dateString as dateEntree,entree_produit.numeroVehicule,entree_produit.derniere_modif,"
                + "entree_produit.libelle,entree_produit.quantite,entree_produit.stocks_avant,\n" +
            "entree_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_produit.article "
                + "WHERE entree_produit.article="+entreeP.getArticle().getId()+" AND stocks_avant="+entreeP.getArticle().getStocks().getQuantite()+" "
                  + " AND entree_produit.stocks_apres="+entreeP.getStocks_apres()+" AND entree_produit.demandeAchat="+entreeP.getDemandeachat().getId()+""
                  + " AND entree_produit.fournisseur="+entreeP.getFournisseur().getId()+" AND entree_produit.numeroVehicule='"+entreeP.getNumeroVehicule()+"'"
                  + " ORDER BY derniere_modif DESC";
           /**
           * end of
           */
    String sqlParfait="INSERT INTO `demandesatisfaite`( `demandeAchat`) VALUES ("+entreeP.getDemandeachat().getId()+")";
         boolean etatDesEntree=this.mdce.etatDeLademande(Demande);
         boolean insertionFaite=false;
    String sqlActualisation="UPDATE `stocks` SET `quantite`="+entreeP.getStocks_apres()+" WHERE articles="+entreeP.getArticle().getId()+"";
            /**
             * recuperation du magasin et du Produit
             */
            //Magasin mag=    
         /**
           * fin recuperation magasin et du produit
         */
       int stocksMagasin=0;
       stocksMagasin=this.mdce.StocksArticlesEnMagasin(entreeP.getMagasin(),entreeP.getArticle());
                            // System.out.println("SOCKTS DY MAG "+stocksMagasin);
     int NvoStocksMag=0;
     NvoStocksMag=stocksMagasin+entreeP.getQuantite();
   String sqlActualisationMagasin="UPDATE `stocks_magasin` SET `quantite`="+NvoStocksMag+" WHERE produit="+entreeP.getArticle().getId()+" AND magasin="+entreeP.getMagasin().getId()+" ";
           if(dtb.InserData(sqlActualisationMagasin)){
               System.out.println("MAGASIN ACTUALISER AVEC SUCCES ");
       if(dtb.InserData(sqlActualisation))
                  {
                  if(etatDesEntree){
            if(dtb.InserData(sqlParfait))
                {
           insertionFaite=true;   
           System.out.println("!!!!!!!!TOUT C BIEN DEROULER!!!!!!!!!!!!!!!!!!!!!!!!");
                }
             // }
                 }
                  
                  }
            //  try {
                      AfficherEntreeDuJour(sqlactualisationJour,"");
                       AfficherEntree(sqlactualisationJour,"");
          
                      
              //  dtb.getCon().commit();
              // dtb.getCon().setAutoCommit(true);
              
                       //  System.out.println ("EST entree : "+.getDemandeachat());
                  // } catch (SQLException ex) {
                     //  Logger.getLogger(ctr_contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
        // ArrayList <EntreeProduit> Derniere=this.mdce.ListeEntreeP(sqlactualisationJour); 
                     
                  // }
          
           }
     else{
           System.out.println("ECHEC DANS LINSERTION");
     }
             
              }
     else{
     
           System.out.println("ECHEC DE LA CREATION DE LENTREE");
     }
   }
   /**
    * 
    * @param demande
    * @param Articles
    * @param quantiterSaisie
    * @return une valeur TRUE pour specifuer si 
    * Le nombre entrer(saisie) dans le TextFields respecte les limites de la quanitite à entrer
    * pour un produit specifique sur une demande d'achat
    */
     public boolean verifierQuantiterSaisie(String demande,String Articles,int quantiterSaisie)
     {
      boolean estOk=true;
        String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articlesdemandeachat.quantiter,articles.designation "
                 + " FROM articlesdemandeachat"
                 + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                 + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                 + " WHERE articlesdemandeachat.article=(SELECT id from articles WHERE designation='"+Articles+"')"
                 + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+demande+"')";
       int nbreMaximal=md_contenuEntreeMatierePremiere.quantiterMaximalProduit(sqlMat, demande, Articles);
          //int nbreEntrer=this.mdce.
       if(quantiterSaisie>nbreMaximal)
       {
           estOk=false;
       }
         return estOk;
     }
     /**
   * permet d'imprimer un JTABLE 
   * @param table le Jtable a imprimer
   * @param entetes l'entete ou le titre du tableau qui s'affichera en haut
   * @param pied    le pieds du message
   */
    
    public static void impressionJtable(JTable table,String entetes, String pied){
    
    
        //System.out.println("\n"+texte);
            MessageFormat entete = new MessageFormat(entetes);
            MessageFormat piedPage = new MessageFormat(pied);

        try {    
           
            table.print(JTable.PrintMode.FIT_WIDTH, entete, piedPage);
             
        } catch (java.awt.print.PrinterException e) {
            System.err.format("Impossible d'imprimer %s%n", e.getMessage());
        }   
    }   
    
}
