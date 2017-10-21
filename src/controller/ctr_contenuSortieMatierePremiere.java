/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.Articles;
import classe.Magasin;
import classe.SortieProduit;
import classe.Stocks;
import classe.database;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEntreeMatierePremiere;
import model.md_contenuSortieMatierePremiere;
import vue.contenuSortieMatierePremiere;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuSortieMatierePremiere {
     
     private final contenuSortieMatierePremiere vue;
     private md_contenuSortieMatierePremiere model;
     private static database dtb;
    public ctr_contenuSortieMatierePremiere(contenuSortieMatierePremiere vue) {
        model=new md_contenuSortieMatierePremiere();
        this.vue = vue;
        String sql="SELECT * FROM magasin where type!='cartonMc'";
        String sqlArticle="SELECT * FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere' )";
        String codeValable=model.genererUnBordereauxLivraison();
        /**
         * mise a jour des elements 
         */
      ctr_contenuEntreeMatierePremiere.MettreDate(this.vue.getDateActuelle());
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sql, "designation");
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getArtiles(), sqlArticle,"designation");
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelleSortie(),codeValable, 0);
          /**
           * mise des actions sur les boutons depuis la vue
           */
             
        vue.getGenerererBordereaux().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererBordereaux(evt);
            }
        });   
        /**
         * action sur le combobox magasin
         */
         vue.getMagasin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionSurLeClickMagasin(evt);
                  
            }
        });   
         /**
          * action sur le bouton de Produit
          */
         vue.getArtiles().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionSurLeClickArticle(evt);
                  
            }
        });   
         //mouse action
         this.vue.getButtonValider().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                  validerSortie(evt);
            }
        });
    }
      
       public void validerSortie(java.awt.event.MouseEvent evt)
       {
          if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
          }
           /**
            * la date format string a inserer
            */
            String dateAinserer="";
          /*
           recuperation de toutes les selections
           et saisies
           */
         
        String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String Article=this.vue.getArtiles().getSelectedItem().toString();
        String libelle=this.vue.getLibelleSortie().getText();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        int quantiter=(int) this.vue.getQuantiter().getValue();
        /*
         recuperation de tous les combo
        */
          
              JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[2];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getArtiles();
            text[0]=this.vue.getLibelleSortie();
          
         boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
            
        /**
         * traitement de la date
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
             /*
                controlle si la Partie Date a correctement ete Inserer
             */
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                 //verification de la quantite Entreer
                 Magasin mag=genererMagasinViaDesignation(magasin);
                 Articles art=genererArticleViaDesignation(Article);
               int quantiterStocksMagasin=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(mag, art);
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + " Cela Reduira le stocks Pour ce Article";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {
        //nous allons construire l'objet
          SortieProduit sortie=genererUneSortieProduit(Article, dateAinserer, dates, libelle, magasin, quantiter);
           //  sortie=this.
          boolean estCreer=this.model.creerUneSortieProduit(sortie);
           if(estCreer)
           {
       //  ctr_contenuEntreeMatierePremiere.reinitialiserTout(cmbo);
               this.vue.getLibelleSortie().setText("");
                ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
                this.vue.getQuantiter().setValue((int)0);
                /**
                 * sql Actualisation
                 */
                 String sqlactualisationJour="SELECT sortie_produit.id,sortie_produit.dateString as dateSortie,sortie_produit.derniere_modif,"
                + "sortie_produit.libelle,sortie_produit.quantite,sortie_produit.stocks_avant,\n" +
            "sortie_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "magasin.id as idM,magasin.designation as nomMag\n" +
            "FROM `sortie_produit`\n" +
            "INNER JOIN magasin ON magasin.id=sortie_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=sortie_produit.article "
          + " WHERE sortie_produit.date=CURRENT_DATE() AND sortie_produit.article="+sortie.getArticle().getId()+" AND stocks_avant="+sortie.getStocks_avant()+" "
          + " AND sortie_produit.stocks_apres="+sortie.getStocks_apres()+""
          + " ORDER BY derniere_modif DESC";
                     /**
                      * reinitialisation de tous les elements
                      */
         ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
         this.vue.getLibelleSortie().setText("");
        String sql="SELECT * FROM magasin where type!='cartonMc'";
        String sqlArticle="SELECT * FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='Matiere premiere' )";
        String codeValable=model.genererUnBordereauxLivraison();
        /**
         * mise a jour des elements 
         */
        ctr_contenuEntreeMatierePremiere.MettreDate(this.vue.getDateActuelle());
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sql, "designation");
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getArtiles(), sqlArticle,"designation");
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelleSortie(),codeValable,0);
                   /*
                 end of element
                 */
                   /**
                    * sql pour tout
                    */
        ArrayList<SortieProduit> liste=this.model.ListeSortie(sqlactualisationJour);
        DefaultTableModel modelToday=( DefaultTableModel) this.vue.getJtableAujourdhui().getModel();
        DefaultTableModel modelTjr=( DefaultTableModel) this.vue.getJtableTout().getModel();
        ctr_contenuSortieMatierePremiere.AfficherDansTableModel(liste, modelToday,liste.get(0));
        ctr_contenuSortieMatierePremiere.AfficherDansTableModel(liste, modelTjr,liste.get(0));
           //System.out.println(sqlactualisationJour);
                }
              }
                     } 
                 else{
        String message="La quantite Saisie pour ce Article: <<"+Article+">>  \n"
               + " Dans le Magasin <<"+magasin+">> est superieur au Stocks Total Du produit \n"
                  + " Dans ce magasin ";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);          
                
                  }
                 }
                 else{
       String message="Revoyez Vos Saisies,Vous devriez tout Cocher\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);  
                 }
               }
             else
               {
       String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }
              
            this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );  
        /*
        fin traitement de la date
        */
      //  System.out.println("POURQUOI SA : "+quantiter);
       }
    /**
     * methode appeler sur l'action sur le bouton valider de la 
     * vue de sortieMatierePremiere
     * @param evt l'evenement sur le click du bouton
     */
       public void validerSortie(java.awt.event.ActionEvent evt)
       {
           /**
            * la date format string a inserer
            */
            String dateAinserer="";
          /*
           recuperation de toutes les selections
           et saisies
           */
         
        String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String Article=this.vue.getArtiles().getSelectedItem().toString();
        String libelle=this.vue.getLibelleSortie().getText();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        int quantiter=(int) this.vue.getQuantiter().getValue();
        /*
         recuperation de tous les combo
        */
          
              JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[2];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getArtiles();
            text[0]=this.vue.getLibelleSortie();
          
         boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
            
        /**
         * traitement de la date
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
             /*
                controlle si la Partie Date a correctement ete Inserer
             */
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                 //verification de la quantite Entreer
                 Magasin mag=genererMagasinViaDesignation(magasin);
                 Articles art=genererArticleViaDesignation(Article);
               int quantiterStocksMagasin=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(mag, art);
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + " Cela Reduira le stocks Pour ce Article";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {
        //nous allons construire l'objet
          SortieProduit sortie=genererUneSortieProduit(Article, dateAinserer, dates, libelle, magasin, quantiter);
           //  sortie=this.
          boolean estCreer=this.model.creerUneSortieProduit(sortie);
           if(estCreer)
           {
       //  ctr_contenuEntreeMatierePremiere.reinitialiserTout(cmbo);
               this.vue.getLibelleSortie().setText("");
                ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
                this.vue.getQuantiter().setValue((int)0);
                /**
                 * sql Actualisation
                 */
                 String sqlactualisationJour="SELECT sortie_produit.id,sortie_produit.dateString as dateSortie,sortie_produit.derniere_modif,"
                + "sortie_produit.libelle,sortie_produit.quantite,sortie_produit.stocks_avant,\n" +
            "sortie_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "magasin.id as idM,magasin.designation as nomMag\n" +
            "FROM `sortie_produit`\n" +
            "INNER JOIN magasin ON magasin.id=sortie_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=sortie_produit.article "
          + " WHERE sortie_produit.date=CURRENT_DATE() AND sortie_produit.article="+sortie.getArticle().getId()+" AND stocks_avant="+sortie.getStocks_avant()+" "
          + " AND sortie_produit.stocks_apres="+sortie.getStocks_apres()+""
          + " ORDER BY derniere_modif DESC";
                 /**
                  * reinitialisation et remplissage des elements du magasin
                  */
               ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
               
                   /**
                    * sql pour tout
                    */
              
             ArrayList<SortieProduit> liste=this.model.ListeSortie(sqlactualisationJour);
             DefaultTableModel modelToday=( DefaultTableModel) this.vue.getJtableAujourdhui().getModel();
             DefaultTableModel modelTjr=( DefaultTableModel) this.vue.getJtableTout().getModel();
             ctr_contenuSortieMatierePremiere.AfficherDansTableModel(liste, modelToday,liste.get(0));
             ctr_contenuSortieMatierePremiere.AfficherDansTableModel(liste, modelTjr,liste.get(0));
           //System.out.println(sqlactualisationJour);
                }
              }
                     } 
                 else{
          String message="La quantite Saisie pour ce Article: <<"+Article+">>  \n"
               + " Dans le Magasin <<"+magasin+">> est superieur au Stocks Total Du produit \n"
                  + " Dans ce magasin ";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);          
                
                  }
                 }
                 else{
       String message="Revoyez Vos Saisies,Vous devriez tout Cocher\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);  
                 }
               }
             else
               {
       String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }
              
              
        /*
        fin traitement de la date
        */
      //  System.out.println("POURQUOI SA : "+quantiter);
       }
       /**
        * methode permettant d'avoir la date du nbre
        * de jour Mis en prametre
        * @param date la date sur laquelle on effectue nos differentes operations
        * @param nbDays le nbre de jour a decaler 
        * @return une Instance de date ki est la date voule
        * ex:addDaysToDate(new Date(),1) retournera la date de demain,si on met -1 c la date d'hier
        */
         public static Date addDaysToDate(Date date, int nbDays){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, nbDays);
        return cal.getTime();
    }
       /**
        * methode appeler quand on click sur le bouton
        * de La partie Du magasin
        * @param evt  le parametre de l'evenement sur le bouton
        */
       public void actionSurLeClickMagasin(java.awt.event.ActionEvent evt)
              {
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String Article=this.vue.getArtiles().getSelectedItem().toString();
        
             /**
              * verifier si les articles ont ete pris
              */
             if(!"Aucun choix".equals(Article)){
          Magasin mag=genererMagasinViaDesignation(magasin);
          Articles  article=genererArticleViaDesignation(Article);
                 /**
                  * on affiche le stocks en magasin du produit
                  */
                 if(!"Aucun choix".equals(magasin))
                 {
              int stocksEnMag=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(mag, article);
                 vue.getQuantiter().setValue((Integer)stocksEnMag);
            vue.getQuantiter().setToolTipText("Stocks Dans le Magasin <<"+mag.getDesignation()+" >> \n"
                    + "du Produit: "+article.getDesignation());
                 }
               /**
                * on affiche le stocks general du Produit
                */
                 else{

              int stocksTotal=quantiterStocksArticle(article);
              vue.getQuantiter().setValue((Integer)stocksTotal);
           vue.getQuantiter().setToolTipText("Stocks general(total) du Produit:<< "+article.getDesignation()+" >>");
                    }
                }     
            else{
         vue.getQuantiter().setValue((Integer)0);
             
              }
              
             }
         public void genererBordereaux(java.awt.event.ActionEvent evt)
         {
         String code=model.genererUnBordereauxLivraison();
         ctr_contenuEntreeMatierePremiere.remplirItem(vue.getLibelleSortie(),code, 0);
         }
       /**
        * methodse invoquer qd on click sur le comboBox
        * des Articles
        * @param evt 
        */
        public void actionSurLeClickArticle(java.awt.event.ActionEvent evt)
              {
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String Article=this.vue.getArtiles().getSelectedItem().toString();
          //System.out.println("dhdhd");
                        /**
              * verifier si les articles ont ete pris
              */
             if(Article!="Aucun choix"){
          Magasin mag=genererMagasinViaDesignation(magasin);
          Articles  article=genererArticleViaDesignation(Article);
                 /**
                  * on affiche le stocks en magasin du produit
                  */
                 if(magasin!="Aucun choix")
                 {
          int stocksEnMag=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(mag, article);
          vue.getQuantiter().setValue((Integer)stocksEnMag);
          vue.getQuantiter().setToolTipText("Stocks Dans le Magasin <<"+mag.getDesignation()+" >> \n"
                    + "du Produit: "+article.getDesignation());
                 }
               /**
                * on affiche le stocks general du Produit
                */
                 else{

          int stocksTotal=quantiterStocksArticle(article);
           vue.getQuantiter().setValue((Integer)stocksTotal);
           vue.getQuantiter().setToolTipText("Stocks general(total) du Produit:<< "+article.getDesignation()+" >>");
                    }
                }     
            else{
         vue.getQuantiter().setValue((Integer)0);
             
              }
              }
        /**
         * Methode static qui permet de retorner la quantite d'une instance 
         * d'article Mis en parametre
         * @param article Instance d'article
         * @return la quantite en entier de stocks theorique pour un article specifier
         */
      public static int  quantiterStocksArticle(Articles article)
      {
        int quantiter=0;
           ResultSet res=null;
           dtb=database.getInstance();
           String sql="SELECT quantite FROM stocks WHERE articles="+article.getId()+"";
         try {
             res=dtb.selectInTab(sql);
              while(res.next())
           {
              quantiter=res.getInt("quantite");
           }
              res.close();
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
          
         return quantiter;
      
      }
      /**
       * Methode static permettant de Generer une Instance de Magasin a patir de
       * la designation du magasin
       * @param designation    le Nom du Magasin,il est unique a chaque magasin
       * @return un Objet ou une instance de Magasin
       */
      public static Magasin genererMagasinViaDesignation(String designation)
      {
        Magasin mag=new Magasin();
        String sql="SELECT * FROM magasin WHERE designation='"+designation+"' ";
        ResultSet res=null;
        int id = 0;
        
        dtb=database.getInstance();
         try {
             res=dtb.selectInTab(sql);
             while(res.next())
             {
                 id=res.getInt("id");
             }
             mag.setDesignation(designation);
             mag.setId(id);
             res.close();
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
        return mag;
      }
       public static Articles genererArticleViaDesignation(String designation)
      {
        Articles article=new Articles();
        String sql="SELECT * FROM articles WHERE designation='"+designation+"' ";
        ResultSet res=null;
        int id = 0;
        
        dtb=database.getInstance();
         try {
             res=dtb.selectInTab(sql);
             while(res.next())
             {
                 id=res.getInt("id");
             }
             article.setDesignation(designation);
             article.setId(id);
             res.close();
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
        return article;
      }
         public static Stocks genererStoksViaArticles(Articles article)
      {
          Stocks stk= new Stocks();
        String sql="SELECT * FROM stocks WHERE articles='"+article.getId()+"' ";
        ResultSet res=null;
        int id = 0;
        int quantiter=0;
        
        dtb=database.getInstance();
         try {
             res=dtb.selectInTab(sql);
             while(res.next())
             {
                 id=res.getInt("id");
                 quantiter=res.getInt("quantite");
             }
             stk.setId(id);
            stk.setQuantite(quantiter);
             res.close();
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
        return stk;
      }
   public static SortieProduit genererUneSortieProduit(String ArticleDesi,String dateString,java.sql.Date date,String libelle,String magasinDesi,int quanititer)
     {
       SortieProduit sortie=null;
           //creation du magasin et de l'article
       Magasin magasin=genererMagasinViaDesignation(magasinDesi);
       Articles article=genererArticleViaDesignation(ArticleDesi);
             
        /**
         * creation des stocks avant et apres dans le magasin et dans l'entrepot enGeneral
         */
       int stocks_avant_general=quantiterStocksArticle(article);
       int stocks_apres_general=stocks_avant_general-quanititer;
       int stocks_avant_magasin=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(magasin, article);
       int stocks_apres_magasin=stocks_avant_magasin-quanititer; 
       
       sortie=new SortieProduit();
          /**
            * assignation des differentes valeurs a l'instance de EntreeProduit
          */
         sortie.setArticle(article);
         sortie.setMagasin(magasin);
         sortie.setDate(date);
         sortie.setDateString(dateString);
         sortie.setLibelle(libelle);
         sortie.setQuantite(quanititer);
         sortie.setStocks_avant(stocks_avant_general);
         sortie.setStocks_apres(stocks_apres_general);
       return sortie;
     }
   /*
     getter and Setter
    */

    public md_contenuSortieMatierePremiere getModel() {
        return model;
    }

    public void setModel(md_contenuSortieMatierePremiere model) {
        this.model = model;
    }
    /**
     * methode Static permettant de remplir un Jtable a partir des Parametres detailles
     * elle fait une verification sur le type d'objet et du nombre de Colonne du Jtable 
     * pour savoir le Jtable a remplir(en fonction de la classe de Provenance Biensur).
     * Pour un objet de Tpe sortieProduit le Jtable Jour a 6 colonne et celui du total a 8 colonness par exemple
     * methode tres adaptative qui pourra etre reecrite ou subir des ajouts de code pour l'adapter a n'importe kel type d'objet
     * @param listeDesElements un ArrayList qui est la liste des Objects a enregistrer,pour cette specification elle etait de type SortieProduit
     * @param model        C'est linstance du model du Jtable a manipuler
     * @param obj           un elements de Arraylist (listeElements) pour verifier la classe des Objets qui le contiennent
     *
     */
   public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof SortieProduit){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=6)
                              {
                             Object [] row =new Object[6];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((SortieProduit)(listeDesElements.get(i))).getArticle().getDesignation();
               row[2]=((SortieProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((SortieProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((SortieProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((SortieProduit)(listeDesElements.get(i))).getLibelle();
               
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
               row[1]=((SortieProduit)(listeDesElements.get(i))).getArticle().getDesignation();
               row[2]=((SortieProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((SortieProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((SortieProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((SortieProduit)(listeDesElements.get(i))).getLibelle();
                     
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
               row[1]=((SortieProduit)(listeDesElements.get(i))).getArticle().getDesignation();
               row[2]=((SortieProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((SortieProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((SortieProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((SortieProduit)(listeDesElements.get(i))).getLibelle();
               row[6]=((SortieProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[7]=((SortieProduit)(listeDesElements.get(i))).getDateString();
               
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
               row[1]=((SortieProduit)(listeDesElements.get(i))).getArticle().getDesignation();
               row[2]=((SortieProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((SortieProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((SortieProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((SortieProduit)(listeDesElements.get(i))).getLibelle();
               row[6]=((SortieProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[7]=((SortieProduit)(listeDesElements.get(i))).getDateString();
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
