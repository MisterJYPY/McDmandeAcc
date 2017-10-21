/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.Articles;
import classe.Magasin;
import classe.SortieProduit;
import classe.database;
import static controller.ctr_contenuSortieMatierePremiere.genererArticleViaDesignation;
import static controller.ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation;
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
import model.md_contenuEntreeMatierePremiere;
import model.md_contenuSortieAutreProduit;
import model.md_contenuSortieMatierePremiere;
import vue.contenuSortieAutreProduit;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuSortieAutreProduit {
     
    private final contenuSortieAutreProduit vue;
    private final md_contenuSortieAutreProduit model;

    public ctr_contenuSortieAutreProduit(contenuSortieAutreProduit vue) {
        this.vue = vue;
        model=new md_contenuSortieAutreProduit();
        //initialisation des elements
           this.vue.getFamille().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererArticles(evt);
                  
            }
        }); 
         
            this.vue.getArtiles().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererMagasin(evt);
                  
            }
        }); 
          this.vue.getMagasin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afficherQuantite(evt);
                  
            }
        }); 
            this.vue.getButtonValider().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               validerSortie(evt);
            }
        });
    }
    public void genererArticles(java.awt.event.ActionEvent evt)
           {
        ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(vue.getArtiles());
     String famille=this.vue.getFamille().getSelectedItem().toString();    
       if(!famille.equals("Aucun choix"))
          {
 String sql="SELECT designation FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='"+famille+"')";
        ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getArtiles(), sql);
          }
          }
    public void genererMagasin(java.awt.event.ActionEvent evt)
           {
       ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(vue.getMagasin());
     String articles=this.vue.getArtiles().getSelectedItem().toString();    
       if(!articles.equals("Aucun choix"))
          {
 //String sql="SELECT designation FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='"+articles+"')";
        String sqlMag="SELECT stocks_magasin.id,magasin.designation FROM stocks_magasin"
                + " INNER JOIN magasin ON magasin.id=stocks_magasin.magasin "
                + " WHERE stocks_magasin.produit=(SELECT id FROM articles WHERE designation='"+articles+"') AND stocks_magasin.quantite>0";
 
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
          }
          }
    public void afficherQuantite(java.awt.event.ActionEvent evt)
    {
     this.vue.getQuantiter().setValue(0);
       String magasin=this.vue.getMagasin().getSelectedItem().toString();    
       String articles=this.vue.getArtiles().getSelectedItem().toString();    
        if(!magasin.equals("Aucun choix"))
          {
              int qte=0;
 //String sql="SELECT designation FROM articles WHERE famille=(SELECT id FROM famille WHERE designation='"+articles+"')";
  
      String sql="SELECT quantite FROM stocks_magasin WHERE produit=(SELECT id FROM articles WHERE designation='"+articles+"')"
              + " AND magasin=(SELECT id FROM magasin WHERE designation='"+magasin+"')";
         try {
             ResultSet res=database.getInstance().selectInTab(sql);
             while(res.next())
             {
             qte=res.getInt("quantite");
             }
              this.vue.getQuantiter().setValue(qte);
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuSortieAutreProduit.class.getName()).log(Level.SEVERE, null, ex);
         }
          }
        
    }
    public void validerSortie(java.awt.event.MouseEvent evt)
        {
         if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
            }    
         //le code here
       String dateAinserer="";
        String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();    
       String articles=this.vue.getArtiles().getSelectedItem().toString();   
        String Libeller=this.vue.getLibelleSortie().getText();
        int quantiter=(int) this.vue.getQuantiter().getValue();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
         JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[3];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getArtiles();
             cmbo[2]=this.vue.getFamille();
            text[0]=this.vue.getLibelleSortie();
          
         boolean PresenceVideCombo=ctr_contenuEntreeMatierePremiere.verificateurSelection(cmbo);
         boolean PresenceVideJText=ctr_contenuEntreeMatierePremiere.verificateurDrChampText(text);
         Date date=new Date();
         Date dates=null;
           //controle de la date
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
                  Magasin mag=genererMagasinViaDesignation(magasin);
                 Articles art=genererArticleViaDesignation(articles);
               int quantiterStocksMagasin=md_contenuEntreeMatierePremiere.StocksArticlesEnMagasin(mag, art);
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + " Cela Reduira le stocks Pour ce Article";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {
        SortieProduit sortie=ctr_contenuSortieMatierePremiere.genererUneSortieProduit(articles, dateAinserer, (java.sql.Date) dates, Libeller, magasin, quantiter);
           if(this.model.creerUneSortieProduit(sortie))
           {
            //nous allons faire les reinitialisations
            ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
           String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("sortie_autre_produit","bordereaux","Sr","Mc");
           ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
            String sqlFamille="SELECT designation FROM famille WHERE designation!='Matiere Premiere'";
            ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getFamille(),sqlFamille);
           //actualisation des models
           String sql2="SELECT sortie_autre_produit.id,sortie_autre_produit.dateString as dateSortie,sortie_autre_produit.derniere_modif,sortie_autre_produit.bordereaux as libelle,sortie_autre_produit.quantite,sortie_autre_produit.stocks_avant,\n" +
"sortie_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
"magasin.id as idM,magasin.designation as nomMag\n" +
"FROM `sortie_autre_produit`\n" +
"INNER JOIN magasin ON magasin.id=sortie_autre_produit.magasin\n" +
"INNER JOIN articles ON articles.id=sortie_autre_produit.article  "
      + "WHERE sortie_autre_produit.date=CURRENT_DATE() "
      + " AND sortie_autre_produit.bordereaux='"+sortie.getLibelle()+"' AND sortie_autre_produit.magasin="+sortie.getMagasin().getId()+" "
      + " AND sortie_autre_produit.quantite="+sortie.getQuantite()+" AND sortie_autre_produit.stocks_avant='"+sortie.getStocks_avant()+"' "
      + " AND sortie_autre_produit.stocks_apres="+sortie.getStocks_apres()+" AND sortie_autre_produit.article="+sortie.getArticle().getId()+" ORDER BY derniere_modif DESC";
           //affichage pour dire tout s'est bien passer
           String sqlAll="SELECT sortie_autre_produit.id,sortie_autre_produit.dateString as dateSortie,sortie_autre_produit.derniere_modif,sortie_autre_produit.bordereaux as libelle,sortie_autre_produit.quantite,sortie_autre_produit.stocks_avant,\n" +
"sortie_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
"magasin.id as idM,magasin.designation as nomMag\n" +
"FROM `sortie_autre_produit`\n" +
"INNER JOIN magasin ON magasin.id=sortie_autre_produit.magasin\n" +
"INNER JOIN articles ON articles.id=sortie_autre_produit.article  "
      + "WHERE  "
      + "sortie_autre_produit.bordereaux='"+sortie.getLibelle()+"' AND sortie_autre_produit.magasin="+sortie.getMagasin().getId()+" "
      + " AND sortie_autre_produit.quantite="+sortie.getQuantite()+" AND sortie_autre_produit.stocks_avant='"+sortie.getStocks_avant()+"' "
      + " AND sortie_autre_produit.stocks_apres="+sortie.getStocks_apres()+" AND sortie_autre_produit.article="+sortie.getArticle().getId()+" ORDER BY derniere_modif DESC";
              // System.out.println(sql2);
           ArrayList <SortieProduit> liste=md_contenuSortieMatierePremiere.ListeSortie(sql2);
     ArrayList <SortieProduit> listeALL=md_contenuSortieMatierePremiere.ListeSortie(sqlAll);
      DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAujourdhui().getModel();
       DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableTout().getModel();
       if(liste.size()>0)
         {
        ctr_contenuSortieAutreProduit.AfficherDansTableModel(liste, model,liste.get(0));
         }
       if(listeALL.size()>0)
       {
      ctr_contenuSortieAutreProduit.AfficherDansTableModel(listeALL, modelAll,listeALL.get(0)); 
       }
           message="Reussite de l'enregistrement de la Sortie: \n"
                 + " Cliquez sur OK svp \n";
              img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Success Operation",  JOptionPane.INFORMATION_MESSAGE, img2);    
           }
          else{
           //ici pour stipuler une erreur
           message="!!!!!!ECHEC enregistrement de la Sortie: \n"
                 + " Une erreur inatendue s'est Produite \n"
                   + "Veuillez Reesayer SVP!!!!";
              img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!!ERREUR INSERT",  JOptionPane.INFORMATION_MESSAGE, img2);   
             }
         
       }
                  }  
               //on affiche que la quantite demander est plus que celui mis
               else{
         String message="La quantite Saisie pour ce Article:<<"+articles+">>\n"
               + " Dans le Magasin <<"+magasin+">> est superieur au Stocks Total Du produit \n"
                  + " Dans ce magasin qui est de : "+quantiterStocksMagasin+ " \n"
                 + " Reesayer SVP!!!!";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);   
                  
                  }
                 }
          //message pour indiquer que l'on devrait tout renseigner avant de cliquer
               else{
               String message="Revoyez Vos Saisies,Vous devriez tout Cocher\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);    
                     
                 }
                }
           //message pour indiquer que la date n pas bonne
         else{
               String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
             }
           //end of controle de la date
     this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
        }
    /**
     * methode provenant du controlleur ctr_contenuSortieMatierePremiere
     * la difference se trouve au niveau du nombre de colonnes du Jtable,ici c'est 7 au lieu de 6 
     * dans le controlleur de Matiere Premiere.Elle a ete modifer pour eviter certains soucils.
     * cette methode renvoi la liste de toutes les sorties sous forme de liste d'objets 
     * @param listeDesElements
     * @param model
     * @param obj 
     */
     public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof SortieProduit){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=7)
                              {
                             Object [] row =new Object[7];
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
               row[6]=((SortieProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
                     
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
