/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.EntreeProduit;
import classe.OperationProductionProduitFini;
import java.awt.Cursor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuProduction;
import model.md_contenuSortieMatierePremiere;
import vue.ContenuProduction;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuProduction {
    
       private ContenuProduction vue=null;
       private md_contenuProduction model=null;
    public ctr_contenuProduction(ContenuProduction vue) {
        this.vue = vue;
        model=new md_contenuProduction();
      
                Date date=new Date();
                 Calendar calendar = Calendar.getInstance();
            int DernierJour = calendar.getActualMaximum(Calendar.DATE);
               date=ctr_contenuSortieMatierePremiere.addDaysToDate(date,-DernierJour );
               java.sql.Date dates=new java.sql.Date(date.getTime());
               System.out.println("LA DATE DE LA SEMAINE : "+dates);
               //**pour <le Mois
              Date dateActuelle=new Date();
               //fin pr le Mois
          //mettre la Date Actuelle
          ctr_contenuEntreeMatierePremiere.MettreDate(this.vue.getDateActuelle());
          //chargement des Magasin
          String sql="SELECT designation FROM magasin WHERE type='cartonMc'";
          ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sql,"designation");
          /**
           * appel d'une methode depuis le model de la classe contenuSortieMatPremiere
           * pour generer un bordereaux
           */
         String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("entree_produit_fini","bordereaux","BoS","Mpf");
         ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelle(), bor,0);
         
//      this.vue.getValiderProduction().addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                validerProduction(evt);
//                  
//            }
//        }); 
       this.vue.getGenererbordereaux().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererBordereaux(evt);
                  
            }
        }); 
         // pour les Mouse Listener
        this.vue.getValiderProduction().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              validerProduction(evt);
            }
        });
     }
      public void genererBordereaux(java.awt.event.ActionEvent evt)
                    {
     String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("entree_produit_fini","bordereaux","BoS","Mpf");
            this.vue.getLibelle().setText(bor);
                    }
      //debut pour l'action du Mouse listener
       public void validerProduction(java.awt.event.MouseEvent evt)
      {
           //recuperation des Elements
        // String magasin=this.vue.getMagasin().get
           if (this.vue.findComponentAt(evt.getPoint()) == null) {
      this.vue.setCursor(Cursor.getDefaultCursor());
      }
      else{
      this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
    }
          String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        String Libeller=this.vue.getLibelle().getText();
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
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
               String message="Confirmer l'operation SVP \n"
               + "Cela Augmentera le stocks De Produit Fini tant en Stocks\n"
                      + "que Dans le Magasin <<"+magasin+">> de: ["+quantiter+"]";
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
              {  
       EntreeProduit entrp=this.model.genererEntrerPruitFini(quantiter, magasin,(java.sql.Date)dates, Libeller,dateAinserer,"PRODUCTION");
           if(this.model.creerUneEntreeProduitFini(entrp))
           {
            ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
            this.vue.getLibelle().setText("");
             this.vue.getQuantite().setValue(0);
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(this.vue.getMagasin());
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(this.vue.getMagasin());
             ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(),"SELECT designation FROM magasin");
    String sql2="SELECT entree_produit_fini.id,entree_produit_fini.dateString,entree_produit_fini.bordereaux,entree_produit_fini.quantite,entree_produit_fini.stocks_avant,\n" +
                " entree_produit_fini.stocks_apres,\n" +
                " magasin.id as idM,magasin.designation as nomMag\n" +
                " FROM `entree_produit_fini`\n" +
                " INNER JOIN magasin ON magasin.id=entree_produit_fini.magasin\n" +
                " WHERE entree_produit_fini.date=CURRENT_DATE() AND entree_produit_fini.magasin="+entrp.getMagasin().getId()+" AND entree_produit_fini.stocks_avant="+entrp.getStocks_avant()+" AND entree_produit_fini.stocks_apres="+entrp.getStocks_apres()+" "
              + "AND entree_produit_fini.bordereaux='"+entrp.getLibelle()+"' AND entree_produit_fini.type='PRODUCTION' "
              + " ORDER BY entree_produit_fini.derniere_modif DESC";
    String sqlAll="SELECT entree_produit_fini.id,entree_produit_fini.dateString,entree_produit_fini.bordereaux,entree_produit_fini.quantite,entree_produit_fini.stocks_avant,\n" +
                " entree_produit_fini.stocks_apres,\n" +
                " magasin.id as idM,magasin.designation as nomMag\n" +
                " FROM `entree_produit_fini`\n" +
                " INNER JOIN magasin ON magasin.id=entree_produit_fini.magasin\n" +
                " WHERE entree_produit_fini.date='"+entrp.getDate()+"' AND entree_produit_fini.magasin="+entrp.getMagasin().getId()+" AND entree_produit_fini.stocks_avant="+entrp.getStocks_avant()+" AND entree_produit_fini.stocks_apres="+entrp.getStocks_apres()+" "
              + "AND entree_produit_fini.bordereaux='"+entrp.getLibelle()+"' AND entree_produit_fini.type='PRODUCTION' "
              + " ORDER BY entree_produit_fini.derniere_modif DESC";
                ArrayList<EntreeProduit> liste=md_contenuProduction.donnerListeEntreeProduitFini(sql2);
                ArrayList<EntreeProduit> listeAll=md_contenuProduction.donnerListeEntreeProduitFini(sqlAll);
                
        // ArrayList<EntreeProduit> listeAll=md_contenuProduction.donnerListeEntreeProduitFini(sql2);
            //System.out.println("LE NBRE : "+listeAll.size());
          /**
           * creation des Models pour la manipulation des composantes 
           */
          DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
          DefaultTableModel modelTout=( DefaultTableModel) this.vue.getJtableAll().getModel();
          //pour le Bilan
           ArrayList<OperationProductionProduitFini> listeBilan=this.genererBilan("entree_produit_fini");
           DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
           //pour l'affichage
            ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
         // System.out.println("TAILLE DU NOMBRE DE PRODUIT PRIS : "+liste.size());
           if(liste.size()>0){
         ctr_contenuProduction.AfficherDansTableModel(liste, model,liste.get(0));
            }
            if(listeAll.size()>0)
            {
                    ctr_contenuProduction.AfficherDansTableModel(listeAll, modelTout,listeAll.get(0));
            }
              if(listeBilan.size()>0)
            {
            ctr_contenuProduction.AfficherDansTableModel(listeBilan, modelBilan,listeBilan.get(0));
            }
           }    
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
              else{
               String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }
        /**
         * traitement de la date
         */
            this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
//  this.vue.getQuanitite().setEnabled(false);
          
           }

      //fin
      public void validerProduction(java.awt.event.ActionEvent evt)
      {
           //recuperation des Elements
        // String magasin=this.vue.getMagasin().get
          String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        String Libeller=this.vue.getLibelle().getText();
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
              if(!dateAinserer.isEmpty()) 
                {
               /**
                    *verifier si les COmboBox on bien ete inserer
                    *Verifier si les JTextFiels on bien ete rentre
                    */
                 if(!PresenceVideCombo && !PresenceVideJText && quantiter>0)
                 {
       EntreeProduit entrp=this.model.genererEntrerPruitFini(quantiter, magasin,(java.sql.Date)dates, Libeller,dateAinserer,"PRODUCTION");
           if(this.model.creerUneEntreeProduitFini(entrp))
           {
            ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
            this.vue.getLibelle().setText("");
             this.vue.getQuantite().setValue(0);
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(this.vue.getMagasin());
             ctr_contenuEntreeMatierePremiere.reinitialiserTout(this.vue.getMagasin());
             ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(),"SELECT designation FROM magasin");
    String sql2="SELECT entree_produit_fini.id,entree_produit_fini.dateString,entree_produit_fini.bordereaux,entree_produit_fini.quantite,entree_produit_fini.stocks_avant,\n" +
                " entree_produit_fini.stocks_apres,\n" +
                " magasin.id as idM,magasin.designation as nomMag\n" +
                " FROM `entree_produit_fini`\n" +
                " INNER JOIN magasin ON magasin.id=entree_produit_fini.magasin\n" +
                " WHERE entree_produit_fini.date=CURRENT_DATE() AND entree_produit_fini.magasin="+entrp.getMagasin().getId()+" AND entree_produit_fini.stocks_avant="+entrp.getStocks_avant()+" AND entree_produit_fini.stocks_apres="+entrp.getStocks_apres()+" "
              + "AND entree_produit_fini.bordereaux='"+entrp.getLibelle()+"' AND entree_produit_fini.type='PRODUCTION' "
              + " ORDER BY entree_produit_fini.derniere_modif DESC";
    String sqlAll="SELECT entree_produit_fini.id,entree_produit_fini.dateString,entree_produit_fini.bordereaux,entree_produit_fini.quantite,entree_produit_fini.stocks_avant,\n" +
                " entree_produit_fini.stocks_apres,\n" +
                " magasin.id as idM,magasin.designation as nomMag\n" +
                " FROM `entree_produit_fini`\n" +
                " INNER JOIN magasin ON magasin.id=entree_produit_fini.magasin\n" +
                " WHERE entree_produit_fini.date='"+entrp.getDate()+"' AND entree_produit_fini.magasin="+entrp.getMagasin().getId()+" AND entree_produit_fini.stocks_avant="+entrp.getStocks_avant()+" AND entree_produit_fini.stocks_apres="+entrp.getStocks_apres()+" "
              + "AND entree_produit_fini.bordereaux='"+entrp.getLibelle()+"' AND entree_produit_fini.type='PRODUCTION' "
              + " ORDER BY entree_produit_fini.derniere_modif DESC";
                ArrayList<EntreeProduit> liste=md_contenuProduction.donnerListeEntreeProduitFini(sql2);
                ArrayList<EntreeProduit> listeAll=md_contenuProduction.donnerListeEntreeProduitFini(sqlAll);
                
        // ArrayList<EntreeProduit> listeAll=md_contenuProduction.donnerListeEntreeProduitFini(sql2);
            //System.out.println("LE NBRE : "+listeAll.size());
          /**
           * creation des Models pour la manipulation des composantes 
           */
          DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
          DefaultTableModel modelTout=( DefaultTableModel) this.vue.getJtableAll().getModel();
          //pour le Bilan
           ArrayList<OperationProductionProduitFini> listeBilan=this.genererBilan("entree_produit_fini");
           DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
           //pour l'affichage
            ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
         // System.out.println("TAILLE DU NOMBRE DE PRODUIT PRIS : "+liste.size());
           if(liste.size()>0){
         ctr_contenuProduction.AfficherDansTableModel(liste, model,liste.get(0));
            }
            if(listeAll.size()>0)
            {
                    ctr_contenuProduction.AfficherDansTableModel(listeAll, modelTout,listeAll.get(0));
            }
              if(listeBilan.size()>0)
            {
            ctr_contenuProduction.AfficherDansTableModel(listeBilan, modelBilan,listeBilan.get(0));
            }
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
              else{
               String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
              }
        /**
         * traitement de la date
         */
          
//  this.vue.getQuanitite().setEnabled(false);
          
           }

    public md_contenuProduction getModel() {
        return model;
    }

    public void setModel(md_contenuProduction model) {
        this.model = model;
    }
      
      public String get()
      {
        try {
       this.vue.getQuantite().commitEdit();
   }
   catch (ParseException pe) {{
       // Edited value is invalid, spinner.getValue() will return
       // the last valid value, you could revert the spinner to show that:
       JComponent editor = this.vue.getQuantite().getEditor();
       if (editor instanceof DefaultEditor) {
           this.vue.getQuantite().setEnabled(true);
           ((DefaultEditor)editor).getTextField().setValue(this.vue.getQuantite().getValue());
       }
       // reset the value to some known value:
       this.vue.getQuantite().setValue(0);
       // or treat the last valid value as the current, in which
       // case you don't need to do anything.
   }
   
      }
        return this.vue.getQuantite().getValue().toString();
      }
      
        public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof EntreeProduit){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=6)
                              {
                             Object [] row =new Object[6];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((EntreeProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((EntreeProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((EntreeProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((EntreeProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((EntreeProduit)(listeDesElements.get(i))).getLibelle();
               
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
              row[1]=((EntreeProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((EntreeProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((EntreeProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((EntreeProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((EntreeProduit)(listeDesElements.get(i))).getLibelle();
                     
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
               row[1]=((EntreeProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((EntreeProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((EntreeProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((EntreeProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((EntreeProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[6]=((EntreeProduit)(listeDesElements.get(i))).getLibelle();
               
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
              row[1]=((EntreeProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((EntreeProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((EntreeProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((EntreeProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((EntreeProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[6]=((EntreeProduit)(listeDesElements.get(i))).getLibelle();
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
         /**
          * Methode permettant d'avoir le debut d'un Mois en fonction Du Mois qui le suit
          * si c'est le Mois preccedent le mois actuelle ,il faut creer un instance de Date ex:Date date=new Date() pour lle
          * passer en parametre.
          * @param dateReference le Mois sur lequel on s'appuit pour avoir la Findu Mois Preccedent
          * @return La date du premier jour du Mois preccedent
          */
       public static Date avoirPremierJourMois(Date dateReference)
       {
        Calendar c = Calendar.getInstance();
               // Date dateActuelle=new Date();
               c.setTime(dateReference);
               c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
                      Date debutMois = c.getTime();
                 c.add(Calendar.DAY_OF_MONTH, -1);
             Date finMoisPrecedent = c.getTime();
               finMoisPrecedent=ctr_contenuSortieMatierePremiere.addDaysToDate(finMoisPrecedent,1);
               System.out.println("FIN DU MOIS PRECCEDENT/ "+finMoisPrecedent);
         return finMoisPrecedent ;
       }
       /**
        * methode permettant de generer une Arralist
        * contenant la la quantite du Jour,celle de la semaine,du mois,du stocks de produit fini
        * methode pouvant etre utiliser dans n'importe quel module pourvu que le Jtable qui devrait recuperer
        * ces valeurs ait 5 colonnes: qte,jour,semaine,mois,stocks
        * @return un ArrayList contenant les etats par periode:journalier,mensuel...
        */
       public static ArrayList genererBilan(String table)
         {
        ArrayList<OperationProductionProduitFini> liste=new ArrayList<>();
        OperationProductionProduitFini op=new OperationProductionProduitFini(table);
                  liste.add(op);
          return liste;
         }
//       /**
//        * methode surcharger de la methode portant le meme nom mais sans parametre
//        * se referer a sa doc pour information
//        * cette methode à la particuliarite de creer une instance de OperationProductionProduitFini qui est la classe
//        * permettant davoir le bilan par periode,cette instance pointera sur la table vente_produit_fini pour avoir le bilan
//        * par contre celle sans parametre pointe sur la table entree_produit_fini
//        * @param table
//        * @return 
//        */
//        public static ArrayList genererBilann(String table)
//         {
//        ArrayList<OperationProductionProduitFini> liste=new ArrayList<>();
//        OperationProductionProduitFini op=new OperationProductionProduitFini(table);
//                  liste.add(op);
//          return liste;
//         }
}

