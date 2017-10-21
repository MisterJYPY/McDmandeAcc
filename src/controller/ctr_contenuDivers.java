/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.Magasin;
import classe.OperationProductionProduitFini;
import classe.consommationProduitFini;
import classe.retourProduit;
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
import model.md_contenuDivers;
import model.md_contenuSortieMatierePremiere;
import model.md_contenuVente;
import vue.contenuDivers;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuDivers {
    
    private final contenuDivers vue;
    private final md_contenuDivers model;

    public ctr_contenuDivers(contenuDivers vues) {
        this.vue = vues;
        model=new md_contenuDivers();
         this.vue.getMagasin().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererStocks(evt);
                  
            }
        }); 
           this.vue.getValiderDivers().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               validerDivers(evt);
            }
        });
           this.vue.getGenererbordereaux().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                 genererBordereaux(evt);
            }
        });
    }
      public void genererBordereaux(java.awt.event.MouseEvent evt)
      {
       String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("consommation_produit_fini","bordereaux","Cons","Mc");
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelle(), bor,0);
      }
      public void validerDivers(java.awt.event.MouseEvent evt)
      {
      if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
                  //le code se situera Apres
          System.out.println("bien entendu");
          String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        String Libeller=this.vue.getLibelle().getText();
        String Motif=this.vue.getMotif().getText();
        int quantiter=(int) this.vue.getQuantite().getValue();
   
          JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[1];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            text[0]=this.vue.getLibelle();
           
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
                   //fin situation du code
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                if(!Motif.isEmpty())
                {
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
                   int quantiterStocksMagasin=0;
                 Magasin mag=genererMagasinViaDesignation(magasin);
                 //Articles art=genererArticleViaDesignation(Article);
               quantiterStocksMagasin=md_contenuVente.donnerStocksEnMagasinProduitFini(mag);
                     System.out.println("quantiter de PF en MAG :"+magasin+ "est : "+quantiterStocksMagasin);
                  if(quantiter<=quantiterStocksMagasin){
               String message="Confirmer l'operation SVP \n"
               + " Cela Reduira le stocks De Produit Fini tant en Stocks \n"
                       + " que Dans le Magasin <<"+mag.getDesignation()+">> de: ["+quantiter+"]";
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
       {  
        consommationProduitFini conso=this.model.genererConsoPruitFini(quantiter, magasin, (java.sql.Date) dates, Libeller, dateAinserer, Motif);
        boolean estOk=this.model.creerConsoProduit(conso);
           if(estOk)
           {
           //nous allons actualiser les Jtable
                String sql2="SELECT consommation_produit_fini.id,consommation_produit_fini.dateString,consommation_produit_fini.bordereaux,consommation_produit_fini.quantite,consommation_produit_fini.stocks_avant,\n" +
" consommation_produit_fini.motif,consommation_produit_fini.stocks_apres,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `consommation_produit_fini`\n" +
" INNER JOIN magasin ON magasin.id=consommation_produit_fini.magasin "
        + " WHERE consommation_produit_fini.date=CURRENT_DATE() "
        + "AND consommation_produit_fini.bordereaux='"+conso.getLibelle()+"' "
        + " AND consommation_produit_fini.magasin="+conso.getMagasin().getId()+" "
        + " AND consommation_produit_fini.quantite="+conso.getQuantite()+" "
        + " AND consommation_produit_fini.stocks_avant="+conso.getStocks_avant()+" "
        + " AND consommation_produit_fini.stocks_apres="+conso.getStocks_apres()+" "
        + "ORDER BY consommation_produit_fini.date DESC";
           //System.out.println("Liste des ventes actuelles : "+md_contenuVente.listeDesVentes(sql2));
          String sqlAll="SELECT consommation_produit_fini.id,consommation_produit_fini.dateString,consommation_produit_fini.bordereaux,consommation_produit_fini.quantite,consommation_produit_fini.stocks_avant,\n" +
" consommation_produit_fini.motif,consommation_produit_fini.stocks_apres,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `consommation_produit_fini`\n" +
" INNER JOIN magasin ON magasin.id=consommation_produit_fini.magasin "
        + " WHERE consommation_produit_fini.bordereaux='"+conso.getLibelle()+"' "
        + " AND consommation_produit_fini.magasin="+conso.getMagasin().getId()+" "
        + " AND consommation_produit_fini.quantite="+conso.getQuantite()+" "
        + " AND consommation_produit_fini.stocks_avant="+conso.getStocks_avant()+" "
        + " AND consommation_produit_fini.stocks_apres="+conso.getStocks_apres()+" "
        + "ORDER BY consommation_produit_fini.date DESC";  
              // System.out.println(sqlAll);
          //fin actualisation
           ArrayList<retourProduit> liste=md_contenuDivers.listeDesConsoProduits(sql2);
           ArrayList<retourProduit> listeAll=md_contenuDivers.listeDesConsoProduits(sqlAll);
            ArrayList<OperationProductionProduitFini> listeBilan=ctr_contenuProduction.genererBilan("consommation_produit_fini");
           DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableAll().getModel();
           DefaultTableModel modelBillan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
           
           //reinitialisation du JTABLE BILAN
              ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBillan);
           if(liste.size()>0)
           {
      ctr_contenuDivers.AfficherDansTableModel(liste, model, liste.get(0));
           }
           if(listeAll.size()>0)
           {
           ctr_contenuDivers.AfficherDansTableModel(listeAll,modelAll, listeAll.get(0)); 
           }
           if(listeBilan.size()>0)
           {
        ctr_contenuDivers.AfficherDansTableModel(listeBilan,modelBillan, listeBilan.get(0));  
           }
             //pour les Actualisations
             this.vue.getLibelle().setText("");
           this.vue.getQuantite().setValue(0);
          ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
          //on va reinitialiser tous les elements
            this.vue.getMotif().setText("");
            ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
            String sqlMag="SELECT stocks_magasin_produit_fini.id,magasin.designation\n" +
"FROM stocks_magasin_produit_fini\n" +
"INNER JOIN magasin ON magasin.id=stocks_magasin_produit_fini.magasin\n" +
"WHERE stocks_magasin_produit_fini.quantite>0";
              ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
              //fin actualisation et on genere un autre libeller
               String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("consommation_produit_fini","bordereaux","Cons","Mc");
      ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelle(), bor,0);
          //fin generation de l'autre libeller affichage de la notif de reussite
                  String messageSuccess=" L'operation a ete Un vrai succes\n"
                          + " Merci !!!";
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSuccess,"Succes Insert",  JOptionPane.INFORMATION_MESSAGE, img2); 
           }
         else{
            String messageError=" !!!!!Une erreur Inattendue s'est Produite!!!!!!!!!\n"
                          + " Si cela Persiste verifier Votre !!!\n"
                    + "Connection Internet ou intranet";
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageError,"Erreur Insertion",  JOptionPane.INFORMATION_MESSAGE, img2); 
           
           }
       }
                  }
                  //ici c'est Pour signifier la quantiter consommer 
                 else{
                  
                  String message="La quantite Saisie de : PRODUIT FINI \n"
               + " Dans le Magasin <<"+magasin+">> est superieur au Stocks Total<<"+quantiterStocksMagasin+">> De produit \n"
                  + " Dans ce magasin,Veuillez saisir une valeur inferieure ou Egale au total en Magasin  ";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2); 
                  
                  }
                 }
               //message^pour une saisies complete
                 else
                 {
         String message="Revoyez Vos Saisies,Vous devriez tout Selectionner ou Saisir\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);  
                 }     
                }
               else{
          String message="Entrer Le Motif SVP\n"
               + " Merci de Reesayer !!!!!!!!!!!!!";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);        
                
                
                }
                }
              //message pour la date
            else{
                String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);
              
              }
       this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
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
       public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof consommationProduitFini){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=6)
                              {
                             Object [] row =new Object[6];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((consommationProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((consommationProduitFini)(listeDesElements.get(i))).getLibelle();
               row[5]=((consommationProduitFini)(listeDesElements.get(i))).getMotif();
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
               row[1]=((consommationProduitFini)(listeDesElements.get(i))).getQuantite();
               row[2]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((consommationProduitFini)(listeDesElements.get(i))).getLibelle();
               row[5]=((consommationProduitFini)(listeDesElements.get(i))).getMotif();
                     
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
               row[1]=((consommationProduitFini)(listeDesElements.get(i))).getDateString();
               row[2]=((consommationProduitFini)(listeDesElements.get(i))).getQuantite();
               row[3]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((consommationProduitFini)(listeDesElements.get(i))).getLibelle();
               row[6]=((consommationProduitFini)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[7]=((consommationProduitFini)(listeDesElements.get(i))).getMotif();
               
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
               row[1]=((consommationProduitFini)(listeDesElements.get(i))).getDateString();
               row[2]=((consommationProduitFini)(listeDesElements.get(i))).getQuantite();
               row[3]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((consommationProduitFini)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((consommationProduitFini)(listeDesElements.get(i))).getLibelle();
               row[6]=((consommationProduitFini)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[7]=((consommationProduitFini)(listeDesElements.get(i))).getMotif();
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
               if(obj instanceof OperationProductionProduitFini)
               {
                     if(model.getColumnCount()<=6)
                              {
                             Object [] row =new Object[5];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]="Qté";
               row[1]=((OperationProductionProduitFini)(listeDesElements.get(i))).getQuantiteJournalier();
               row[2]=((OperationProductionProduitFini)(listeDesElements.get(i))).getQuantiterHebdomadaire();
               row[3]=((OperationProductionProduitFini)(listeDesElements.get(i))).getQuantiterMensuelle();
               row[4]=((OperationProductionProduitFini)(listeDesElements.get(i))).getQuantiteStoks();;
  
              // row[5]=((EntreeProduit)(listeDesElements.get(i))).getLibelle();
               
               model.addRow(row);
              }}
                 }
                     
               }
           }
}
