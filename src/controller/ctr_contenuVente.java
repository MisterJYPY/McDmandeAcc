/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.Magasin;
import classe.OperationProductionProduitFini;
import classe.database;
import classe.venteProduitFini;
import static controller.ctr_contenuSortieMatierePremiere.addDaysToDate;
import static controller.ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuVente;
import vue.ContenuVente;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuVente {
     private ContenuVente vue;
     private md_contenuVente model;
     private static database dtb;

    public ctr_contenuVente(ContenuVente vue) {
        this.vue = vue;
        model=new md_contenuVente();
//         this.vue.getValiderVente().addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                validerVente(evt);
//                  
//            }
//        }); 
       this.vue.getListeRDI().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionlisteRDI(evt);
                  
            }
        }); 
        this.vue.getMagasin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererStocks(evt);
                  
            }
        }); 
       //code pour l'action sur le Mouse
        this.vue.getValiderVente().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                validerVente(evt);
            }
        });
       //fin action sur le Mouse
      
    }
          public void genererStocks(java.awt.event.ActionEvent evt)
          {
          String Magasin=this.vue.getMagasin().getSelectedItem().toString();
            if(!Magasin.equals("Aucun choix"))
            {
             Magasin mag=genererMagasinViaDesignation(Magasin);
            int quantiterStocksMagasin=0;
           quantiterStocksMagasin = md_contenuVente.donnerStocksEnMagasinProduitFini(mag);
            this.vue.getQuantite().setValue(quantiterStocksMagasin);
            }
            else{
             this.vue.getQuantite().setValue(0);
            }
          }
           public void validerVente(java.awt.event.MouseEvent evt)
      {
          if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
          }
          String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
         String Magasin=this.vue.getMagasin().getSelectedItem().toString();
         String NomRDI=this.vue.getListeRDI().getSelectedItem().toString();
         String Clients=this.vue.getListeClient().getSelectedItem().toString();
         /*
         
         */
         String libelle=this.vue.getLibelle().getText();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        int quantiter=(int) this.vue.getQuantite().getValue();
        /*
         recuperation de tous les combo
        */
          
              JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[3];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getListeClient();
            cmbo[2]=this.vue.getListeRDI();
            text[0]=this.vue.getLibelle();
          
         boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
         
         /*
         traitement de la date
         */
             Date date=new Date();
            java.sql.Date dates=null;
            Date dateDemain=addDaysToDate(date, 1);
            boolean partieDate=false;
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
             //on verifie si la date est bonne:
             if(!dateAinserer.isEmpty()) 
                {
                //on verifie si tout est 
                     if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                //verification de la quantite Entreer
                  int quantiterStocksMagasin=0;
                 Magasin mag=genererMagasinViaDesignation(Magasin);
                 //Articles art=genererArticleViaDesignation(Article);
               quantiterStocksMagasin=md_contenuVente.donnerStocksEnMagasinProduitFini(mag);
                     System.out.println("quantiter de PF en MAG :"+Magasin+ "est : "+quantiterStocksMagasin);
                    
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + "Cela Reduira le stocks De Produit Fini tant en Stocks\n"
                       + "que Dans le Magasin <<"+mag.getDesignation()+">> de: ["+quantiter+"]";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {   
         String nomPersonnel=DecouperNom(NomRDI,0);
         String prenomPersonnel=DecouperNom(NomRDI,1);
         venteProduitFini vpfini=this.model.genererEntrerPruitFini(quantiter, Magasin, dates, libelle, dateAinserer,"", nomPersonnel,prenomPersonnel, Clients);
                if(this.model.creerVenteProduit(vpfini))
                   {
          //si tout a ete bien ete fait
          String messageSucess="Succes!!!! Vente Effectuer avec succes\n"
                  + "   \n";
      // ImageIcon img2s=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
    
             /*
          cette partie concerne la partie pour l'actualisation des elements dans le tableModel 1 et 2
          */
             String sql2="SELECT vente_produit_fini.id,vente_produit_fini.dateString,vente_produit_fini.bordereaux,vente_produit_fini.quantite,vente_produit_fini.stocks_avant,\n" +
" vente_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" clients.nom as nomC ,clients.id as idC FROM`vente_produit_fini`\n" +
" INNER JOIN clients ON clients.id=vente_produit_fini.client "
+ " INNER JOIN personnel ON personnel.id=vente_produit_fini.personnel \n" +
" WHERE vente_produit_fini.date=CURRENT_DATE() AND vente_produit_fini.bordereaux='"+vpfini.getLibelle()+"'  "
      + "AND vente_produit_fini.personnel="+vpfini.getRdi().getId()+" AND vente_produit_fini.client="+vpfini.getClient().getId()+" "
      + " AND vente_produit_fini.quantite="+vpfini.getQuantite()+" AND  vente_produit_fini.stocks_apres="+vpfini.getStocks_apres()+" "
                     + "ORDER BY vente_produit_fini.date DESC";
                      //System.out.println(sql2);
          String sqlAll="SELECT vente_produit_fini.id,vente_produit_fini.dateString,vente_produit_fini.bordereaux,vente_produit_fini.quantite,vente_produit_fini.stocks_avant,\n" +
" vente_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" clients.nom as nomC ,clients.id as idC FROM`vente_produit_fini`\n" +
" INNER JOIN clients ON clients.id=vente_produit_fini.client "
+ " INNER JOIN personnel ON personnel.id=vente_produit_fini.personnel \n" +
" WHERE  vente_produit_fini.bordereaux='"+vpfini.getLibelle()+"'  "
      + "AND vente_produit_fini.personnel="+vpfini.getRdi().getId()+" AND vente_produit_fini.client="+vpfini.getClient().getId()+" "
      + " AND vente_produit_fini.quantite="+vpfini.getQuantite()+" AND  vente_produit_fini.stocks_apres="+vpfini.getStocks_apres()+" "
                     + "ORDER BY vente_produit_fini.date DESC";     
                 
           /*
         fin de l'actualisation
       */
           ArrayList<venteProduitFini> liste=md_contenuVente.listeDesVentes(sql2);
           ArrayList<venteProduitFini> listeAll=md_contenuVente.listeDesVentes(sqlAll);
           DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableAll().getModel();
            ArrayList<OperationProductionProduitFini> listeBilan=ctr_contenuProduction.genererBilan("vente_produit_fini");
            DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
            //reinitialisation
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
           if(liste.size()>0)
           {
         ctr_contenuVente.AfficherDansTableModel(liste, model,liste.get(0));
           }
           if(listeAll.size()>0)
           {
           ctr_contenuVente.AfficherDansTableModel(listeAll, modelAll,listeAll.get(0));
           }
          
              if(listeBilan.size()>0){
         ctr_contenuProduction.AfficherDansTableModel(listeBilan, modelBilan,listeBilan.get(0));
            }
           /**
            * reinitialisation des elements
            */
           this.vue.getLibelle().setText("");
           this.vue.getQuantite().setValue(0);
          ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
          //on va reinitialiser tous les elements
               ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
             //remplissage des combobox
                String sql="SELECT CONCAT(nom,CONCAT(' ',prenom)) as designation "
                + "FROM `personnel` WHERE service=(SELECT id FROM services WHERE abreviation='COM') AND "
                + "fonction='RESPONSABLE DISTRIBUTEUR INTERNE'";
         String sqlcLIENTS="SELECT nom as designation FROM clients";
            String sqlMagasin="SELECT designation FROM magasin";  
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMagasin);
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getListeRDI(), sql);
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getListeClient(), sqlcLIENTS);
               
               //fin remplissage des comboBox
          JOptionPane.showMessageDialog(null,messageSucess,"Succes Insert",  JOptionPane.INFORMATION_MESSAGE, img2); 
                   }
                else{
          String messageSucess="Error!!!! Une Erreur Innatendue "
                  + "  est Survenue Lors de L'insertion \n";
      // ImageIcon img2s=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"!!Echec Insert",  JOptionPane.INFORMATION_MESSAGE, img2);  
                   }
       }
       }
      //on affiche un message si le stock en magasin est inferieur a la quantiter voulant Inserer
            else{
            String message="La quantite Saisie de : PRODUIT FINI \n"
               + " Dans le Magasin <<"+Magasin+">> est superieur au Stocks Total<<"+quantiterStocksMagasin+">> De produit \n"
                  + " Dans ce magasin,Veuillez saisir une valeur inferieure ou Egale au total en Magasin  ";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);     
                  
                }    
                  }
           //le message pour indiquer une eventuelle erreur de Saisie
                   else{
           String message="Revoyez Vos Saisies,Vous devriez tout Selectionner ou Saisir\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);  
                     }
                  }
             //si non on affiche le message par rapport a la date
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
      public void validerVente(java.awt.event.ActionEvent evt)
      {
          String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
         String Magasin=this.vue.getMagasin().getSelectedItem().toString();
         String NomRDI=this.vue.getListeRDI().getSelectedItem().toString();
         String Clients=this.vue.getListeClient().getSelectedItem().toString();
         /*
         
         */
         String libelle=this.vue.getLibelle().getText();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        int quantiter=(int) this.vue.getQuantite().getValue();
        /*
         recuperation de tous les combo
        */
          
              JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[3];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getListeClient();
            cmbo[2]=this.vue.getListeRDI();
            text[0]=this.vue.getLibelle();
          
         boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
         
         /*
         traitement de la date
         */
             Date date=new Date();
            java.sql.Date dates=null;
            Date dateDemain=addDaysToDate(date, 1);
            boolean partieDate=false;
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
             //on verifie si la date est bonne:
             if(!dateAinserer.isEmpty()) 
                {
                //on verifie si tout est 
                     if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                //verification de la quantite Entreer
                  int quantiterStocksMagasin=0;
                 Magasin mag=genererMagasinViaDesignation(Magasin);
                 //Articles art=genererArticleViaDesignation(Article);
               quantiterStocksMagasin=md_contenuVente.donnerStocksEnMagasinProduitFini(mag);
                     System.out.println("quantiter de PF en MAG :"+Magasin+ "est : "+quantiterStocksMagasin);
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + " Cela Reduira le stocks Pour ce Article";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {   
         String nomPersonnel=DecouperNom(NomRDI,0);
         String prenomPersonnel=DecouperNom(NomRDI,1);
         venteProduitFini vpfini=this.model.genererEntrerPruitFini(quantiter, Magasin, dates, libelle, dateAinserer,"", nomPersonnel,prenomPersonnel, Clients);
                if(this.model.creerVenteProduit(vpfini))
                   {
          //si tout a ete bien ete fait
          String messageSucess="Succes!!!! Vente Effectuer avec succes"
                  + "   \n";
      // ImageIcon img2s=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
    
             /*
          cette partie concerne la partie pour l'actualisation des elements dans le tableModel 1 et 2
          */
             String sql2="SELECT vente_produit_fini.id,vente_produit_fini.dateString,vente_produit_fini.bordereaux,vente_produit_fini.quantite,vente_produit_fini.stocks_avant,\n" +
" vente_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" clients.nom as nomC ,clients.id as idC FROM`vente_produit_fini`\n" +
" INNER JOIN clients ON clients.id=vente_produit_fini.client "
+ " INNER JOIN personnel ON personnel.id=vente_produit_fini.personnel \n" +
" WHERE vente_produit_fini.date=CURRENT_DATE() AND vente_produit_fini.bordereaux='"+vpfini.getLibelle()+"'  "
      + "AND vente_produit_fini.personnel="+vpfini.getRdi().getId()+" AND vente_produit_fini.client="+vpfini.getClient().getId()+" "
      + " AND vente_produit_fini.quantite="+vpfini.getQuantite()+" AND  vente_produit_fini.stocks_apres="+vpfini.getStocks_apres()+" "
                     + "ORDER BY vente_produit_fini.date DESC";
                      //System.out.println(sql2);
          String sqlAll="SELECT vente_produit_fini.id,vente_produit_fini.dateString,vente_produit_fini.bordereaux,vente_produit_fini.quantite,vente_produit_fini.stocks_avant,\n" +
" vente_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" clients.nom as nomC ,clients.id as idC FROM`vente_produit_fini`\n" +
" INNER JOIN clients ON clients.id=vente_produit_fini.client "
+ " INNER JOIN personnel ON personnel.id=vente_produit_fini.personnel \n" +
" WHERE  vente_produit_fini.bordereaux='"+vpfini.getLibelle()+"'  "
      + "AND vente_produit_fini.personnel="+vpfini.getRdi().getId()+" AND vente_produit_fini.client="+vpfini.getClient().getId()+" "
      + " AND vente_produit_fini.quantite="+vpfini.getQuantite()+" AND  vente_produit_fini.stocks_apres="+vpfini.getStocks_apres()+" "
                     + "ORDER BY vente_produit_fini.date DESC";     
                 
           /*
         fin de l'actualisation
       */
           ArrayList<venteProduitFini> liste=md_contenuVente.listeDesVentes(sql2);
           ArrayList<venteProduitFini> listeAll=md_contenuVente.listeDesVentes(sqlAll);
           DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableAll().getModel();
            ArrayList<OperationProductionProduitFini> listeBilan=ctr_contenuProduction.genererBilan("vente_produit_fini");
            DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
            //reinitialisation
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
           if(liste.size()>0)
           {
         ctr_contenuVente.AfficherDansTableModel(liste, model,liste.get(0));
           }
           if(listeAll.size()>0)
           {
           ctr_contenuVente.AfficherDansTableModel(listeAll, modelAll,listeAll.get(0));
           }
          
              if(listeBilan.size()>0){
         ctr_contenuProduction.AfficherDansTableModel(listeBilan, modelBilan,listeBilan.get(0));
            }
           /**
            * reinitialisation des elements
            */
           this.vue.getLibelle().setText("");
           this.vue.getQuantite().setValue(0);
          ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
          //on va reinitialiser tous les elements
               ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
             //remplissage des combobox
                String sql="SELECT CONCAT(nom,CONCAT(' ',prenom)) as designation "
                + "FROM `personnel` WHERE service=(SELECT id FROM services WHERE abreviation='COM') AND "
                + "fonction='RESPONSABLE DISTRIBUTEUR INTERNE'";
         String sqlcLIENTS="SELECT nom as designation FROM clients";
            String sqlMagasin="SELECT designation FROM magasin";  
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMagasin);
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getListeRDI(), sql);
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getListeClient(), sqlcLIENTS);
               
               //fin remplissage des comboBox
          JOptionPane.showMessageDialog(null,messageSucess,"Succes Insert",  JOptionPane.INFORMATION_MESSAGE, img2); 
                   }
                else{
          String messageSucess="Error!!!! Une Erreur Innatendue "
                  + "  est Survenue Lors de L'insertion \n";
      // ImageIcon img2s=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"!!Echec Insert",  JOptionPane.INFORMATION_MESSAGE, img2);  
                   }
       }
       }
      //on affiche un message si le stock en magasin est inferieur a la quantiter voulant Inserer
            else{
            String message="La quantite Saisie de : PRODUIT FINI \n"
               + " Dans le Magasin <<"+Magasin+">> est superieur au Stocks Total<<"+quantiterStocksMagasin+">> De produit \n"
                  + " Dans ce magasin,Veuillez saisir une valeur inferieure ou Egale au total en Magasin  ";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);     
                  
                }    
                  }
           //le message pour indiquer une eventuelle erreur de Saisie
                   else{
           String message="Revoyez Vos Saisies,Vous devriez tout Selectionner ou Saisir\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);  
                     }
                  }
             //si non on affiche le message par rapport a la date
              else
               {
       String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }
      }
      public void actionlisteRDI(java.awt.event.ActionEvent evt)
      {
          //recuperation du nom
   String Nom=this.vue.getListeRDI().getSelectedItem().toString();
   String[] splitArray = null; 
      boolean finish=false;
      do{
      int nbreLigne=this.vue.getListeClient().getItemCount();    
      if(nbreLigne==1)
      {
          finish=true;
      }
     ctr_contenuEntreeMatierePremiere.reinitialiserTout(this.vue.getListeClient());
    }
    while(!finish);
      if(Nom!="Aucun choix"){
            splitArray = Nom.split(" ");
    
    String NomRDI=splitArray[0];
    String Prenom="";
      String concat="";
     for(int i = 1; i< splitArray.length;i++){
                // On affiche chaque élément du tableau
           Prenom=Prenom.concat(splitArray[i]);
                if(i<splitArray.length-1)
                {
                Prenom=Prenom.concat(" ");
                }
  }
     //ici c'est le code qui permet davoir la liste des Clients de la zone
       int[] tab;
       tab = md_contenuVente.donnerIdDesLocalitesGouverenerRDI(md_contenuVente.RenvoyerIdPersonnelEtIdZone(NomRDI,Prenom));
        //  System.out.println("element 1 de la table: "+tab[0]);
       String requeteClient=md_contenuVente.donnerClientsDeRdi(tab);
           //System.out.println(requeteClient);
           if(!"null".equals(requeteClient)){
       ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getListeClient(),requeteClient);
           }
      
     //tableau de chaînes
  //la chaîne à traiter
  // On découpe la chaîne "str" à traiter et on récupère le résultat dans le tableau "splitArray"
 
 }}
   
    public ContenuVente getVue() {
        return vue;
    }

    public void setVue(ContenuVente vue) {
        this.vue = vue;
    }

    public static database getDtb() {
        return dtb;
    }

    public static void setDtb(database dtb) {
        ctr_contenuVente.dtb = dtb;
    }
      /**
       * methode qui retourne le Premier mot d'une chaine de caractere
       * la chaine doit avoir tous ces mots separes par le vide(" ")
       * Cette methode est utiliser pour recuperer le Nom et le Prenom d'un Nom compose
       * de Nom+prenom. Si c'est le Nom que nous voulons avoir l'indication a entrer doit etre zero(0)
       * si c'est le Prenom un autre entier different de Zero
       * @param chaine  
       * @param indication un entier 
       * @return soit le Nom ou le prenom en fonction de l'indication
       */
   public static String  DecouperNom(String chaine,int indication)
   {
      String ElementAenvoyer="";
      String[] splitArray = chaine.split(" ");
        if(indication==0)
        {
    ElementAenvoyer=splitArray[0];
        }
      if(indication!=0){
    String Prenom="";
      String concat="";
     for(int i = 1; i< splitArray.length;i++){
                // On affiche chaque élément du tableau
           Prenom=Prenom.concat(splitArray[i]);
                if(i<splitArray.length-1)
                {
                Prenom=Prenom.concat(" ");
                }
  }
       ElementAenvoyer=Prenom;
      }
     
     return ElementAenvoyer;
   }
     public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof venteProduitFini){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=7)
                              {
                             Object [] row =new Object[7];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((venteProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((venteProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((venteProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((venteProduitFini)(listeDesElements.get(i))).getLibelle();
               row[5]=((venteProduitFini)(listeDesElements.get(i))).getRdi().getPrenom();
               row[6]=((venteProduitFini)(listeDesElements.get(i))).getClient().getNom();
               model.addRow(row);
              }
                              }
                              else{
                                  if(model.getRowCount()>1 && listeDesElements.size()>=1){
                        //RECuperation du nbre de Ligne
                  int Position=model.getRowCount()+1;
                 for(int i=0;i<listeDesElements.size();i++)
              {            
               row[0]=Position;
              row[1]=((venteProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((venteProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((venteProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((venteProduitFini)(listeDesElements.get(i))).getLibelle();
               row[5]=((venteProduitFini)(listeDesElements.get(i))).getRdi().getPrenom();
               row[6]=((venteProduitFini)(listeDesElements.get(i))).getClient().getNom();
               model.addRow(row);
                     Position++;
              }
                              }
                              }
                 }
                            else{
                       Object [] row =new Object[8];
                      if(model.getRowCount()==0 && listeDesElements.size()>=1){
                       for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((venteProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((venteProduitFini)(listeDesElements.get(i))).getDateString();
               row[3]=((venteProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((venteProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((venteProduitFini)(listeDesElements.get(i))).getLibelle();
               row[6]=((venteProduitFini)(listeDesElements.get(i))).getRdi().getPrenom();
               row[7]=((venteProduitFini)(listeDesElements.get(i))).getClient().getNom();
               
               model.addRow(row);
              }       
                       
                              }
                      else{
                            if(model.getRowCount()>1 && listeDesElements.size()>=1){
                        //RECuperation du nbre de Ligne
                  int Position=model.getRowCount()+1;
                         for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=Position;
               row[1]=((venteProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((venteProduitFini)(listeDesElements.get(i))).getDateString();
               row[3]=((venteProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((venteProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((venteProduitFini)(listeDesElements.get(i))).getLibelle();
               row[6]=((venteProduitFini)(listeDesElements.get(i))).getRdi().getPrenom();
               row[7]=((venteProduitFini)(listeDesElements.get(i))).getClient().getNom();
               Position++;
               model.addRow(row);
              }       
                    
                            }
                      
                          }
                              }
                 }
                 /**
                  * si c'est une Instance de EntreeProduit
                  */
              
           }
}
