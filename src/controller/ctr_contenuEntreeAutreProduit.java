/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.EntreeProduit;
import classe.SaisieVerificator;
import classe.database;
import static controller.ctr_contenuEntreeMatierePremiere.reinitialiserTout;
import static controller.ctr_contenuEntreeMatierePremiere.remplirItem;
import static controller.ctr_contenuEntreeMatierePremiere.verificateurDrChampText;
import static controller.ctr_contenuEntreeMatierePremiere.verificateurSelection;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEntreeAutreProduit;
import model.md_contenuEntreeMatierePremiere;
import static model.md_contenuEntreeMatierePremiere.quantiterMaximalProduit;
import vue.contenuEntreeAutreProduit;


/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuEntreeAutreProduit {
  
    private final contenuEntreeAutreProduit vue;
    private final md_contenuEntreeAutreProduit model;

    public ctr_contenuEntreeAutreProduit(contenuEntreeAutreProduit vue) {
        this.vue = vue;
        model=new md_contenuEntreeAutreProduit();
        
        this.vue.getDemandeAchat().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
             genererArticlesDemandeAchat(evt);
                  
            }
        }); 
        
          this.vue.getLesArticles().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              genererQuantite(evt);
                  
            }
        }); 
          
           this.vue.getDemandeAchat1().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              //genererQuantite(evt);
                genererLesMatieresPremieresDansLeModel(evt);
                  
            }
        }); 
          
         this.vue.getQuantite().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                VerificateurSaisie(evt);
            }
        });
//        this.vue.getEntrer().addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//              validerEntreeAutreProduit(evt);
//                  
//            }
//        }); 
         this.vue.getEntrer().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                validerEntreeAutreProduit(evt);
            }
        });
           this.vue.getValiderAutreChoix().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               TraitementBoutonAutrechoix(evt);
            }
        });
    }
    
    public void genererArticlesDemandeAchat(java.awt.event.ActionEvent evt)
    {
               reinitialiserTout(this.vue.getFournisseur());
               String DemandeAchat=evt.getActionCommand();
              if("comboBoxChanged".equals(DemandeAchat)){
              String Demande= this.vue.getDemandeAchat().getSelectedItem().toString();
            if(!"Aucun choix".equals(Demande)){
           /**
            * requete permettant de selectionner tous les articles
            * qui viennent de la famille des Matieres Premieres ,en Parametre id de la demande de Bordereaux
            */
        String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille IN(SELECT id FROM famille WHERE designation!='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
           /**
            * requete d'essai pour la selection de Produit
            */
              String sqlMats="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille IN (SELECT id FROM famille WHERE designation='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')"
                     + " AND articlesdemandeachat.quantiter<(SELECT SUM(quantite) FROM entree_produit WHERE demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"' "
                     + " AND article=articlesdemandeachat.article)";
            // remplirItem(this.vue.getLesArticles(), sqlMat,"designation");
               // ctr_contenuEntreeMatierePremiere.
         ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getLesArticles());
         ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getFournisseur());
        remplirItem(this.vue.getLesArticles(),md_contenuEntreeMatierePremiere.ArticlesNonComplete(sqlMat, Demande,"entree_autre_produit"));
         //  remplirItem(this.vue.getLesArticles(),mdce.ArticlesNonComplete(sqlMat, Demande));
         //  System.out.println("EST CE QUE TOUS LES PRODUITS SONT ENTREE CORRECTEMENT ?:"+mdce.etatDeLademande(Demande));
                 Demande="MC"+Demande;
          String sqlFournisseur="SELECT fournisseursurcotation.id,fournisseur.nom as designation FROM fournisseursurcotation "
                     + " INNER JOIN fournisseur ON fournisseur.id=fournisseursurcotation.fournisseur "
                     + "  WHERE fournisseursurcotation.cotation=(SELECT id FROM cotation WHERE code_cotation='"+Demande+"')";
                  //this.contenuEntreeMatierePremiere.getFournisseur().removeAllItems();
             remplirItem(this.vue.getFournisseur(),sqlFournisseur);
            }
            else{
            //  System.out.println("echec: "+Demande);
              }
              }
    }
      public void genererQuantite(java.awt.event.ActionEvent evt)
      {
           String DemandeAchat=evt.getActionCommand();
              if("comboBoxChanged".equals(DemandeAchat)){
           String ArticlesDesignation= this.vue.getLesArticles().getSelectedItem().toString();  
           String Demande= this.vue.getDemandeAchat().getSelectedItem().toString();  
                 if(!"Aucun choix".equals(Demande)){
                     
         String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articlesdemandeachat.quantiter,articles.designation "
                 + " FROM articlesdemandeachat"
                 + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                 + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                 + " WHERE articlesdemandeachat.article=(SELECT id from articles WHERE designation='"+ArticlesDesignation+"')"
                 + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
//          this.contenuEntreeMatierePremiere.getQuantite().setText
            this.vue.getQuantite().setText("");
       // System.out.println("LA QUANITER QUE VOUS DEVRIER ENTRER EST: "+mdce.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation));
      //remplirItem(this.contenuEntreeMatierePremiere.getQuantite(), sqlMat);
      remplirItem(this.vue.getQuantite(),md_contenuEntreeMatierePremiere.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation,"entree_autre_produit"));
                 }
              }
      }
       private void VerificateurSaisie(java.awt.event.KeyEvent evt) { 
	      SaisieVerificator.veritierLabel(this.vue.getQuantite(),evt.getKeyChar());
		}
       private void validerEntreeAutreProduit(java.awt.event.ActionEvent evt)
       {
           String dateAinserer="";
           //System.out.println(this.mdce.ListeEntreeP());
           String articles= this.vue.getLesArticles().getSelectedItem().toString();
           String Demande= this.vue.getDemandeAchat().getSelectedItem().toString(); 
           String Fournisseur= this.vue.getFournisseur().getSelectedItem().toString(); 
           String dateActuelle=this.vue.getDateActuelle().getText();
            String libelle=this.vue.getBonComande().getText();
            int quantiter=0;
           String vehiculeNumber=this.vue.getVehiculeNumber().getText();
           if(!this.vue.getQuantite().getText().isEmpty()){
           quantiter=Integer.parseInt(this.vue.getQuantite().getText());  
           }
       String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
           String magasin=this.vue.getMagasin().getSelectedItem().toString();
          /**
           * controle de saisie
           */
               JTextField text[]=new JTextField[2];
               JComboBox cmbo[]=new JComboBox[4];
                   /**
                    * remlissage des jtextfiels pour le controle
                    */
                 text[0]=this.vue.getVehiculeNumber();
                 text[1]=this.vue.getBonComande();
            //     text[2]=(JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent());
               /**
                * remplissage des Jcombox pour le controle
                */
                cmbo[0]=this.vue.getLesArticles();
                cmbo[1]= this.vue.getDemandeAchat();
                cmbo[2]=this.vue.getFournisseur();
                cmbo[3]=this.vue.getMagasin();
       
        boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
         Date date=new Date();
         Date dates=null;
             if(!dateChosser.isEmpty())
                  {
                      /**
                       * date aujourdhui format sql
                       */
                  java.sql.Date dateAujdhui= new java.sql.Date(date.getTime());
                  /*
                  fin de la date Aujourdhui
                  */
              Date datee=this.vue.getAutreDate().getDateEditor().getDate();
                 SimpleDateFormat formater = null;
               dates= new java.sql.Date(datee.getTime());
              // java.sql.Date dateDemains= new java.sql.Date(dateDemain.getTime());
          
         if(dates.before(dateAujdhui) || dates.equals(dateAujdhui))
                  {
         //   System.out.println("LA DATE DE DEMAIN EST :"+date.compareTo(dateDemain));    
                if(dateActuelle.equals(dateChosser)){
                   dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                }
                else{
                   date=this.vue.getAutreDate().getDateEditor().getDate();
                   dates= new java.sql.Date(date.getTime());
                   dateAinserer=dateChosser;   
                }
                  }
                  }
              else
                  {
                 dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                  }
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                    
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                   //nous allons gerer les quantites maximales
                 String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articlesdemandeachat.quantiter,articles.designation "
                 + " FROM articlesdemandeachat"
                 + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                 + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                 + " WHERE articlesdemandeachat.article=(SELECT id from articles WHERE designation='"+articles+"')"
                 + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
//          this.contenuEntreeMatierePremiere.getQuantite().setText
       // System.out.println("LA QUANITER QUE VOUS DEVRIER ENTRER EST: "+mdce.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation));
      //remplirItem(this.contenuEntreeMatierePremiere.getQuantite(), sqlMat);
                  int qtM=quantiterMaximalProduit(sqlMat, Demande,articles,"entree_autre_produit");
                    // System.out.println("la quantiet Maximal: "+qtM);
                     if(quantiter<=qtM)
                     {
                 //c'est que tout est OK nous pouvons Inserer dans la base de donnee
                   String message="Confirmer l'operation SVP \n"
               + " Cela Augmentera le stocks De Produit Fini tant en Stocks \n"
                       + " que Dans le Magasin <<"+magasin+">> de: ["+quantiter+"]";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       { 
    EntreeProduit entrP=this.model.GenererUntype(articles, magasin, dateAinserer,libelle, Demande,dateAinserer, quantiter,vehiculeNumber, Fournisseur, (java.sql.Date) dates);

            if(this.model.CreerEntreeProduit(entrP))
            {
           String sqlJour="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date=CURRENT_DATE() "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
           String sqlTout="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date='"+entrP.getDate()+"' "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
        // System.out.println(sql);
          DefaultTableModel modelJour=( DefaultTableModel) this.vue.getTableOption1().getModel();
          ArrayList<EntreeProduit> liste=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlJour);
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getTableOption2().getModel();
         ArrayList<EntreeProduit> listeAll=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlTout);
         // System.out.println("taille: "+liste.size());
          if(liste.size()>0)
          {
          ctr_contenuEntreeMatierePremiere.AfficherEntreeDuJour(liste, modelJour,0);
          }
          if(liste.size()>0)
          {
        ctr_contenuEntreeMatierePremiere.AfficherEntree(listeAll, modelAll);
          }
        //reinitialisation
          ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
          this.vue.getBonComande().setText("");
          this.vue.getVehiculeNumber().setText("");
          this.vue.getQuantite().setText("");
        //reinitialisation de la demande Achat
           String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation WHERE id IN(SELECT cotation FROM fournisseursurcotation )) "
           + " AND id NOT IN (SELECT demandeAchat FROM demandesatisfaite_autreproduit) "
           + " AND id IN (SELECT demandeAchat FROM articlesdemandeAchat WHERE article IN(SELECT id FROM articles WHERE famille!=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
            remplirItem(this.vue.getDemandeAchat(),sql4,"code");
            ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getDemandeAchat1());
            remplirItem(this.vue.getDemandeAchat1(),sql4,"code");
            String sqlMag="SELECT designation FROM magasin WHERE type!='cartonMc'";
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin1(), sqlMag);
  JOptionPane.showMessageDialog(null,"Succes inser","Reussite",  JOptionPane.INFORMATION_MESSAGE, img2); 
            }
            else{
            JOptionPane.showMessageDialog(null,"Echec Insertion ","ECHEC LORS DE L'ENREGISTREMENT",  JOptionPane.INFORMATION_MESSAGE, img2);    
            }
       }
                     }
               //si non Affiche que la quantite depasse celle entrer
                   else{
            String message="La quantite Saisie de :<< "+articles+" >> \n  "
                 + "est superieur a la quantite Maximal <<"+qtM+">> restante \n"
                  + " et figurante sur la demande <<"+Demande+">>, Veuillez enter Une quantie valide";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);    
                     
                     }
                 }
                 //ici pour revoir la saisie
                 else{
        String message="Revoyez Vos Saisies,Vous devriez tout Selectionner\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);      
                 
                 }
                }
            //ici pour stipuler de la date
            else
              {
         String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);        
              
              }
       }
       /**
        * methode permettant d'enregister une entree de produit
        * dans la base de donnee
        * @param evt 
        */
           private void validerEntreeAutreProduit(java.awt.event.MouseEvent evt)
       {
           if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
        String dateAinserer="";
               // System.out.println(this.mdce.ListeEntreeP());
            
           String articles= this.vue.getLesArticles().getSelectedItem().toString();
           String Demande= this.vue.getDemandeAchat().getSelectedItem().toString(); 
           String Fournisseur= this.vue.getFournisseur().getSelectedItem().toString(); 
           String dateActuelle=this.vue.getDateActuelle().getText();
            String libelle=this.vue.getBonComande().getText();
            int quantiter=0;
           String vehiculeNumber=this.vue.getVehiculeNumber().getText();
           if(!this.vue.getQuantite().getText().isEmpty()){
           quantiter=Integer.parseInt(this.vue.getQuantite().getText());  
           }
       String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
           String magasin=this.vue.getMagasin().getSelectedItem().toString();
          /**
           * controle de saisie
           */
               JTextField text[]=new JTextField[2];
               JComboBox cmbo[]=new JComboBox[4];
                   /**
                    * remlissage des jtextfiels pour le controle
                    */
                 text[0]=this.vue.getVehiculeNumber();
                 text[1]=this.vue.getBonComande();
            //     text[2]=(JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent());
               /**
                * remplissage des Jcombox pour le controle
                */
                cmbo[0]=this.vue.getLesArticles();
                cmbo[1]= this.vue.getDemandeAchat();
                cmbo[2]=this.vue.getFournisseur();
                cmbo[3]=this.vue.getMagasin();
       
        boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
         Date date=new Date();
         Date dates=null;
             if(!dateChosser.isEmpty())
                  {
                      /**
                       * date aujourdhui format sql
                       */
                  java.sql.Date dateAujdhui= new java.sql.Date(date.getTime());
                  /*
                  fin de la date Aujourdhui
                  */
              Date datee=this.vue.getAutreDate().getDateEditor().getDate();
                 SimpleDateFormat formater = null;
               dates= new java.sql.Date(datee.getTime());
              // java.sql.Date dateDemains= new java.sql.Date(dateDemain.getTime());
          
         if(dates.before(dateAujdhui) || dates.equals(dateAujdhui))
                  {
         //   System.out.println("LA DATE DE DEMAIN EST :"+date.compareTo(dateDemain));    
                if(dateActuelle.equals(dateChosser)){
                   dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                }
                else{
                   date=this.vue.getAutreDate().getDateEditor().getDate();
                   dates= new java.sql.Date(date.getTime());
                   dateAinserer=dateChosser;   
                }
                  }
                  }
              else
                  {
                 dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                  }
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                    
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                   //nous allons gerer les quantites maximales
                 String sqlMat="SELECT articlesdemandeachat.article as Idarticle,articlesdemandeachat.quantiter,articles.designation "
                 + " FROM articlesdemandeachat"
                 + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                 + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                 + " WHERE articlesdemandeachat.article=(SELECT id from articles WHERE designation='"+articles+"')"
                 + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
//          this.contenuEntreeMatierePremiere.getQuantite().setText
       // System.out.println("LA QUANITER QUE VOUS DEVRIER ENTRER EST: "+mdce.quantiterMaximalProduit(sqlMat, Demande,ArticlesDesignation));
      //remplirItem(this.contenuEntreeMatierePremiere.getQuantite(), sqlMat);
                  int qtM=quantiterMaximalProduit(sqlMat, Demande,articles,"entree_autre_produit");
                    // System.out.println("la quantiet Maximal: "+qtM);
                     if(quantiter<=qtM)
                     {
                 //c'est que tout est OK nous pouvons Inserer dans la base de donnee
                   String message="Confirmer l'operation SVP \n"
               + " Cela Augmentera le stocks De Produit Fini tant en Stocks \n"
                       + " que Dans le Magasin <<"+magasin+">> de: ["+quantiter+"]";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       { 
    EntreeProduit entrP=this.model.GenererUntype(articles, magasin, dateAinserer,libelle, Demande,dateAinserer, quantiter,vehiculeNumber, Fournisseur, (java.sql.Date) dates);

            if(this.model.CreerEntreeProduit(entrP))
            {
           String sqlJour="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date=CURRENT_DATE() "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
           String sqlTout="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date='"+entrP.getDate()+"' "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
        // System.out.println(sql);
          DefaultTableModel modelJour=( DefaultTableModel) this.vue.getTableOption1().getModel();
          ArrayList<EntreeProduit> liste=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlJour);
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getTableOption2().getModel();
         ArrayList<EntreeProduit> listeAll=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlTout);
         // System.out.println("taille: "+liste.size());
          if(liste.size()>0)
          {
          ctr_contenuEntreeMatierePremiere.AfficherEntreeDuJour(liste, modelJour,0);
          }
          if(liste.size()>0)
          {
        ctr_contenuEntreeMatierePremiere.AfficherEntree(listeAll, modelAll);
          }
        //reinitialisation
          ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
          this.vue.getBonComande().setText("");
          this.vue.getVehiculeNumber().setText("");
          this.vue.getQuantite().setText("");
        //reinitialisation de la demande Achat
           String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation WHERE id IN(SELECT cotation FROM fournisseursurcotation )) "
           + " AND id NOT IN (SELECT demandeAchat FROM demandesatisfaite_autreproduit) "
           + " AND id IN (SELECT demandeAchat FROM articlesdemandeAchat WHERE article IN(SELECT id FROM articles WHERE famille!=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
            remplirItem(this.vue.getDemandeAchat(),sql4,"code");
            ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getDemandeAchat1());
            remplirItem(this.vue.getDemandeAchat1(),sql4,"code");
            ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getMagasin1());
            String sqlMag="SELECT designation FROM magasin ";
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin1(), sqlMag);
           ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
  JOptionPane.showMessageDialog(null,"Succes inser","Reussite",  JOptionPane.INFORMATION_MESSAGE, img2); 
            }
            else{
            JOptionPane.showMessageDialog(null,"Echec Insertion ","Reussite",  JOptionPane.INFORMATION_MESSAGE, img2);    
            }
       }
                     }
               //si non Affiche que la quantite depasse celle entrer
                   else{
            String message="La quantite Saisie de :<< "+articles+" >> \n  "
                 + "est superieur a la quantite Maximal <<"+qtM+">> restante \n"
                  + " et figurante sur la demande <<"+Demande+">>, Veuillez enter Une quantie valide";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);    
                     
                     }
                 }
                 //ici pour revoir la saisie
                 else{
        String message="Revoyez Vos Saisies,Vous devriez tout Selectionner\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);      
                 
                 }
                }
            //ici pour stipuler de la date
            else
              {
         String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);        
              
              }
             this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
       }
              public void genererLesMatieresPremieresDansLeModel( java.awt.event.ActionEvent evt)
             {
          String Demande= this.vue.getDemandeAchat1().getSelectedItem().toString(); 
          DefaultTableModel models=( DefaultTableModel) this.vue.getTableChoix().getModel();
                reinitialiserTout(models);
            if(!Demande.equals("Aucun choix")){
          String sql="SELECT articlesdemandeachat.article as Idarticle,articles.designation,articlesdemandeachat.quantiter "
                     + " FROM articlesdemandeachat"
                     + " INNER JOIN demandeachat ON demandeachat.id=articlesdemandeachat.demandeAchat"
                     + " INNER JOIN articles ON articles.id=articlesdemandeachat.article "
                     + " WHERE articlesdemandeachat.article IN (SELECT id from articles WHERE famille IN (SELECT id FROM famille WHERE designation !='Matiere premiere'))"
                     + " AND articlesdemandeachat.demandeAchat=(SELECT id from demandeachat WHERE bordereaux='"+Demande+"')";
         
                // dtb=database.getInstance();
                  ResultSet res=null;
                  System.out.println(sql);
            try {
               res=database.getInstance().selectInTab(sql);
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
            int quantiterDeJaEntrer=md_contenuEntreeMatierePremiere.AvoirQuantiterEntree(Demande,res.getString("designation"),"entree_autre_produit");
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
            remplirItem((JComboBox)this.vue.getFournisseurAutreChoix(),sqlFournisseurs);
             /**
              * partie Fournisseur
              */
            } 
             }
             public void TraitementBoutonAutrechoix( java.awt.event.MouseEvent evt)
             {
              if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
                 String dateAinserer="";
                 Date date;
          /**
           * Nous allons verifier si tous les elements sont Presents 
           * et Bien renseigner
           */
           String Demande= this.vue.getDemandeAchat1().getSelectedItem().toString(); 
           String Fournisseur= this.vue.getFournisseurAutreChoix().getSelectedItem().toString(); 
           String dateActuelle=this.vue.getDateActuelle1().getText();
           String libelle=this.vue.getBonComande1().getText();
           String vehiculeNumber=this.vue.getVehiculeNumber1().getText();
                
       String dateChosser=((JTextField)(this.vue.getAutreDatePourAutreOption().getDateEditor().getUiComponent())).getText();
       String magasin=this.vue.getMagasin1().getSelectedItem().toString();
          /**
           * controle de saisie
           */
               JTextField text[]=new JTextField[2];
               JComboBox cmbo[]=new JComboBox[3];
               JComboBox cmborei[]=new JComboBox[1];
                   /**
                    * remlissage des jtextfiels pour le controle
                    */
                   text[0]=this.vue.getVehiculeNumber1();
                   text[1]=this.vue.getBonComande1();
                 // text[2]=(JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent());
               /**
                * remplissage des Jcombox pour le controle
                */
                cmbo[0]= this.vue.getDemandeAchat1();
                cmbo[1]=this.vue.getFournisseurAutreChoix();
                cmbo[2]=this.vue.getMagasin1();
           // cmbo[2]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
    //  text[3]=((JTextField)(this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().getUiComponent()));
                /**
                 * variable dinitialisation du tableau pour creer la reinitialisation
                 */
                cmborei[0]= this.vue.getFournisseurAutreChoix();
               // cmborei[1]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
               // cmborei[3]=this.contenuEntreeMatierePremiere.getMagasin1();
               // cmborei[2]=this.contenuEntreeMatierePremiere.getDemandeAchat1();
           /**
            * 
            */
                boolean PresenceVdeSaisie=verificateurDrChampText(text);
                boolean PresenceVdeSelection=verificateurSelection(cmbo);   
                  date=new Date();
                  Date dates=null;
             if(!dateChosser.isEmpty())
                  {
                      /**
                       * date aujourdhui format sql
                       */
                  java.sql.Date dateAujdhui= new java.sql.Date(date.getTime());
                  /*
                  fin de la date Aujourdhui
                  */
              Date datee=this.vue.getAutreDate().getDateEditor().getDate();
                 SimpleDateFormat formater = null;
               dates= new java.sql.Date(datee.getTime());
              // java.sql.Date dateDemains= new java.sql.Date(dateDemain.getTime());
          
         if(dates.before(dateAujdhui) || dates.equals(dateAujdhui))
                  {
         //   System.out.println("LA DATE DE DEMAIN EST :"+date.compareTo(dateDemain));    
                if(dateActuelle.equals(dateChosser)){
                   dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                }
                else{
                   date=this.vue.getAutreDate().getDateEditor().getDate();
                   dates= new java.sql.Date(date.getTime());
                   dateAinserer=dateChosser;   
                }
                  }
                  }
              else
                  {
                 dates= new java.sql.Date(date.getTime());
                  dateAinserer=dateActuelle;
                  }
              if(!dateAinserer.isEmpty()) 
                {
                  if(!PresenceVdeSaisie && !PresenceVdeSelection){
         //  this.contenuEntreeMatierePremiere.getAutreDate().getDateEditor().setDateFormatString("Y-m-d");
        //this.vue.getLabelIndicationAu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/traitement.gif"))); // NOI18N
                 
              /**
               * Debut du code Pour Inserer
               */
       DefaultTableModel models=( DefaultTableModel) this.vue.getTableChoix().getModel();
          int nbreLigne=models.getRowCount();
          boolean estOk=verifierQuantiterEntrer(models);
         if(estOk){
             try {
            
               /**
                * deroulement
                */
                String demande="";
                String articles="";
                int quantite=0;
                 int compteur=0;
                 String message=" Confirmer Svp L'operation\n"
                         + " Ceci Augmentera le stock en entrepot et en Magasin. \n"
                         + " Operation irreversible.Voulez-Vous Continuez?";
                 if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {
                  for(int i=0;i<nbreLigne;i++)
             {
                System.out.println("ELEMENT "+i);
                
             articles = (String) this.vue.getTableChoix().getValueAt(i,1);
            if(this.vue.getTableChoix().getValueAt(i,2).getClass().equals(articles.getClass())){
                quantite =Integer.parseInt(this.vue.getTableChoix().getValueAt(i,2).toString());
            }
            else{
             quantite = (int) this.vue.getTableChoix().getValueAt(i,2);
            }
            demande = (String) this.vue.getTableChoix().getValueAt(i,3);
  EntreeProduit entrP =this.model.GenererUntype(articles, magasin, dateAinserer, libelle, demande,dateAinserer, quantite,vehiculeNumber, Fournisseur, (java.sql.Date) dates);
               if(this.model.CreerEntreeProduit(entrP))
            {
                  database.getInstance().getCon().setAutoCommit(false);
           String sqlJour="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date=CURRENT_DATE() "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
           String sqlTout="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
             + "WHERE entree_autre_produit.date='"+entrP.getDate()+"' "
             + " AND entree_autre_produit.libelle='"+entrP.getLibelle()+"' AND entree_autre_produit.demandeAchat="+entrP.getDemandeachat().getId()+" "
             + " AND entree_autre_produit.fournisseur="+entrP.getFournisseur().getId()+" "
             + " AND entree_autre_produit.magasin="+entrP.getMagasin().getId()+" "
             + " AND entree_autre_produit.numeroVehicule='"+entrP.getNumeroVehicule()+"' "
             + " AND entree_autre_produit.stocks_avant="+entrP.getArticle().getStocks().getQuantite()+" "
             + " AND entree_autre_produit.stocks_apres="+entrP.getStocks_apres()+" "
             + " AND entree_autre_produit.quantite="+entrP.getQuantite()+" ORDER BY derniere_modif DESC";
        // System.out.println(sql);
          DefaultTableModel modelJour=( DefaultTableModel) this.vue.getTableOption1().getModel();
          ArrayList<EntreeProduit> liste=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlJour);
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getTableOption2().getModel();
         ArrayList<EntreeProduit> listeAll=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlTout);
         // System.out.println("taille: "+liste.size());
          if(liste.size()>0)
          {
          ctr_contenuEntreeMatierePremiere.AfficherEntreeDuJour(liste, modelJour,0);
          }
          if(liste.size()>0)
          {
        ctr_contenuEntreeMatierePremiere.AfficherEntree(listeAll, modelAll);
          }
        //reinitialisation
         
                   
              compteur++;  
            }             
             }}
                   ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
          this.vue.getBonComande1().setText("");
          this.vue.getVehiculeNumber1().setText("");
          ((JTextField)(this.vue.getAutreDatePourAutreOption().getDateEditor().getUiComponent())).setText("");
        //reinitialisation de la demande Achat
           String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation WHERE id IN(SELECT cotation FROM fournisseursurcotation )) "
           + " AND id NOT IN (SELECT demandeAchat FROM demandesatisfaite_autreproduit) "
           + " AND id IN (SELECT demandeAchat FROM articlesdemandeAchat WHERE article IN(SELECT id FROM articles WHERE famille!=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
            remplirItem(this.vue.getDemandeAchat(),sql4,"code");
            ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(this.vue.getDemandeAchat1());
            remplirItem(this.vue.getDemandeAchat1(),sql4,"code");
            String sqlMag="SELECT designation FROM magasin ";
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin1(), sqlMag);
                  if(compteur==nbreLigne)
                  {
                message="L'Enregistrement a ete un succes\n"
                        + " Cliquez sur Ok pour Terminer";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
        JOptionPane.showMessageDialog(null, message,"Success Insert",JOptionPane.INFORMATION_MESSAGE, img2); 
                  }
                  else{
                message="!!!!ERREUR LORS DE L'INSERTION\n"
                        + " UNE ERREUR INNATENDUE EST SURVENUE QUELQUE PART\n"
                           + " Merci de Verifier et Reesayer";
               ImageIcon img2=new ImageIcon("src/img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
        JOptionPane.showMessageDialog(null, message,"!!!!!!!!!erreur Insert",JOptionPane.INFORMATION_MESSAGE, img2); 
                  }
                  //reinitialisation
               
                 /**
                  * fin du code de reinitialisation
                  */
                 database.getInstance().getCon().commit();
  
 // this.contenuEntreeMatierePremiere.getLabelIndicationAutreChoix().setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/ok.jpg"))); // NOI18N
             database.getInstance().getCon().setAutoCommit(true);
                     
           } catch (SQLException ex) {
               Logger.getLogger(ctr_contenuEntreeAutreProduit.class.getName()).log(Level.SEVERE, null, ex);
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
              
                  else{
            String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);        
                 
                  }
               this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
             }
/**<p>cette methode est reecrite dans le controlleur de entreeMatPremiere portant le meme nom</p>
 * methode permettant de verifier si les quantiter 
 * afficher dans Un Jdefaultablemodel lors de l'insertion
 * ne sont pas superieur(strictement) aux quantités demandés ou restantes(si il ya deja eu des Insertions)
 * @param model une instance de DefaultTableModel
 * @return un boolean qui ramene true ou false suivant l'etat de la saisie
 */
      public boolean verifierQuantiterEntrer(DefaultTableModel model){
               boolean estOk=true;
               int nbreLigne=model.getRowCount();
               int nbreColonne=model.getColumnCount();
                // this.mdce.
                   for(int i=0;i<nbreLigne;i++)
             {
                System.out.println("ELEMENT "+i);
                
                String demande="";
                String Article="";
                int quantite=0;
             Article = (String) this.vue.getTableChoix().getValueAt(i,1);
            if(this.vue.getTableChoix().getValueAt(i,2).getClass().equals(Article.getClass())){
                quantite =Integer.parseInt(this.vue.getTableChoix().getValueAt(i,2).toString());
            }
            else{
             quantite = (int) this.vue.getTableChoix().getValueAt(i,2);
            }
             demande = (String) this.vue.getTableChoix().getValueAt(i,3);
       int quantiterDemander=+md_contenuEntreeMatierePremiere.AvoirQuantiterDemander(demande, Article);
        int quantiterEntrer=+md_contenuEntreeMatierePremiere.AvoirQuantiterEntree(demande, Article,"entree_autre_produit");
        int quantiterRestant=quantiterDemander-quantiterEntrer;
           if(quantiterRestant<quantite)
           {
             estOk=false;
           }
              }
             
                return estOk;
            }
}
