/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import classe.OperationProductionProduitFini;
import classe.retourProduit;
import static controller.ctr_contenuVente.DecouperNom;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuRetour;
import model.md_contenuSortieMatierePremiere;
import vue.ContenuRetour;
/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuRetour {
    
     private final md_contenuRetour model;
     private final ContenuRetour   vue;

    public ctr_contenuRetour(ContenuRetour vue) {
        this.vue = vue;
        model=new md_contenuRetour();
        
//         this.vue.getValiderRetour().addActionListener(new java.awt.event.ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                validerRetour(evt);
//                  
//            }
//        }); 
        
         this.vue.getGenererbordereaux().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genererBordereaux(evt);
                  
            }
        }); 
            this.vue.getValiderRetour().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
               validerRetour(evt);
            }
        });
    }
       /**
        * mouse listener method
        * @param evt 
        */
    
        public void validerRetour(java.awt.event.MouseEvent evt)
      {
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
               JComboBox cmbo[]=new JComboBox[2];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getListeRDI();
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
                  //ok on peu y aller
                     
                   String message="Confirmer l'operation SVP \n"
               + "Cela Augmentera le stocks De Produit Fini tant en Stocks\n"
                       + "que Dans le Magasin <<"+magasin+">> de: ["+quantiter+"]";
     
               //   JoptionPane.showBlack(message);
       if(JOptionPane.showConfirmDialog(null, message)==  JOptionPane.OK_OPTION)
              {  
          String NomRDI=this.vue.getListeRDI().getSelectedItem().toString();
         String nomPersonnel=DecouperNom(NomRDI,0);
         String prenomPersonnel=DecouperNom(NomRDI,1);
    retourProduit retour=this.model.genererRetourPruitFini(quantiter, magasin, (java.sql.Date) dates,Libeller, dateAinserer,"", nomPersonnel,prenomPersonnel);
              if(this.model.creerRetourProduit(retour))
                  {
          //si tout a ete bien ete fait
           //nous allons maintenant faire les reinitialisation
           String sql2="SELECT retour_produit_fini.id,retour_produit_fini.dateString,retour_produit_fini.bordereaux,retour_produit_fini.quantite,retour_produit_fini.stocks_avant,\n" +
" retour_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `retour_produit_fini`\n" +
 " INNER JOIN personnel ON personnel.id=retour_produit_fini.personnel \n"
 + " INNER JOIN magasin ON magasin.id=retour_produit_fini.magasin" +
" WHERE retour_produit_fini.date=CURRENT_DATE() AND retour_produit_fini.bordereaux='"+retour.getLibelle()+"'  "
      + "AND retour_produit_fini.personnel="+retour.getRdi().getId()+" AND retour_produit_fini.magasin="+retour.getMagasin().getId()+" "
      + " AND retour_produit_fini.quantite="+retour.getQuantite()+" AND  retour_produit_fini.stocks_apres="+retour.getStocks_apres()+" "
       + " ORDER BY retour_produit_fini.date DESC";
           //System.out.println("Liste des ventes actuelles : "+md_contenuVente.listeDesVentes(sql2));
          String sqlAll="SELECT retour_produit_fini.id,retour_produit_fini.dateString,retour_produit_fini.bordereaux,retour_produit_fini.quantite,retour_produit_fini.stocks_avant,\n" +
" retour_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `retour_produit_fini`\n" +
 " INNER JOIN personnel ON personnel.id=retour_produit_fini.personnel \n"
 + " INNER JOIN magasin ON magasin.id=retour_produit_fini.magasin" +
" WHERE retour_produit_fini.bordereaux='"+retour.getLibelle()+"'  "
      + "AND retour_produit_fini.personnel="+retour.getRdi().getId()+" AND retour_produit_fini.magasin="+retour.getMagasin().getId()+" "
      + " AND retour_produit_fini.quantite="+retour.getQuantite()+" AND  retour_produit_fini.stocks_apres="+retour.getStocks_apres()+" "
       + " ORDER BY retour_produit_fini.date DESC";
          //System.out.println(sql2);
           ArrayList<retourProduit> liste=md_contenuRetour.listeDesReTourProduits(sql2);
           ArrayList<retourProduit> listeAll=md_contenuRetour.listeDesReTourProduits(sqlAll);
           DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableAll().getModel();
            ArrayList<OperationProductionProduitFini> listeBilan=ctr_contenuProduction.genererBilan("retour_produit_fini");
             DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
                    //reinitialisation du JTABLE BILAN
              ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
           if(liste.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(liste, model,liste.get(0));
            }
           if(listeAll.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(listeAll, modelAll,listeAll.get(0));
            }   
          if(listeBilan.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(listeBilan,modelBilan,listeBilan.get(0));
            }
           //on va reinitialiser tout les elements maintenant   
           this.vue.getLibelle().setText("");
           this.vue.getQuantite().setValue(0);
          ((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).setText("");
          //on va reinitialiser tous les elements
               ctr_contenuEntreeMatierePremiere.reinitialiserToutEnBoucle(cmbo);
               String sqlMag="SELECT designation FROM magasin";
               ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getMagasin(), sqlMag);
           //fin reinitialisation
          String messageSucess="Succes!!!! Retour Enregistrer avec succes"
                  + "   \n";
            ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
                      //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"Succes Insert",  JOptionPane.INFORMATION_MESSAGE, img2); 
                  }
               //si non on affiche que c pas bon
              else{
             //si tout a ete bien ete fait
          String messageSucess="Echec!!!! Une erreur innatendue s'est Produite quelque part"
                  + "   \n";
            ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
                      //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);     
              
              }
                 }
                 }
         //on informe pour lui dire qu'il y a une saisie incorrecte ou insatisfaite
                 else{
           String message="Revoyez Vos Saisies,Vous devriez tout Cocher\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);    
                 
                 } 
                }
          //on informe par rapport a la date
            else{
             String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
                }
            this.vue.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
      }
    
      public void validerRetour(java.awt.event.ActionEvent evt)
      {
         String dateAinserer="";
         String dateActuelle=this.vue.getDateActuelle().getText();
        String magasin=this.vue.getMagasin().getSelectedItem().toString();
        String dateChosser=((JTextField)(this.vue.getAutreDate().getDateEditor().getUiComponent())).getText();
        String Libeller=this.vue.getLibelle().getText();
        int quantiter=(int) this.vue.getQuantite().getValue();
   
          JTextField text[]=new JTextField[1];
               JComboBox cmbo[]=new JComboBox[2];
          /*
               les variables
               */
            cmbo[0]=this.vue.getMagasin();
            cmbo[1]=this.vue.getListeRDI();
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
                  //ok on peu y aller
                      {   
          String NomRDI=this.vue.getListeRDI().getSelectedItem().toString();
         String nomPersonnel=DecouperNom(NomRDI,0);
         String prenomPersonnel=DecouperNom(NomRDI,1);
    retourProduit retour=this.model.genererRetourPruitFini(quantiter, magasin, (java.sql.Date) dates,Libeller, dateAinserer,"", nomPersonnel,prenomPersonnel);
              if(this.model.creerRetourProduit(retour))
                  {
          //si tout a ete bien ete fait
           //nous allons maintenant faire les reinitialisation
           String sql2="SELECT retour_produit_fini.id,retour_produit_fini.dateString,retour_produit_fini.bordereaux,retour_produit_fini.quantite,retour_produit_fini.stocks_avant,\n" +
" retour_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `retour_produit_fini`\n" +
 " INNER JOIN personnel ON personnel.id=retour_produit_fini.personnel \n"
 + " INNER JOIN magasin ON magasin.id=retour_produit_fini.magasin" +
" WHERE retour_produit_fini.date=CURRENT_DATE() AND retour_produit_fini.bordereaux='"+retour.getLibelle()+"'  "
      + "AND retour_produit_fini.personnel="+retour.getRdi().getId()+" AND retour_produit_fini.magasin="+retour.getMagasin().getId()+" "
      + " AND retour_produit_fini.quantite="+retour.getQuantite()+" AND  retour_produit_fini.stocks_apres="+retour.getStocks_apres()+" "
       + " ORDER BY retour_produit_fini.date DESC";
           //System.out.println("Liste des ventes actuelles : "+md_contenuVente.listeDesVentes(sql2));
          String sqlAll="SELECT retour_produit_fini.id,retour_produit_fini.dateString,retour_produit_fini.bordereaux,retour_produit_fini.quantite,retour_produit_fini.stocks_avant,\n" +
" retour_produit_fini.stocks_apres,personnel.id as idP,personnel.nom as nomP ,personnel.prenom as prenomP,\n" +
" magasin.designation as designationM,magasin.id as idM FROM `retour_produit_fini`\n" +
 " INNER JOIN personnel ON personnel.id=retour_produit_fini.personnel \n"
 + " INNER JOIN magasin ON magasin.id=retour_produit_fini.magasin" +
" WHERE retour_produit_fini.bordereaux='"+retour.getLibelle()+"'  "
      + "AND retour_produit_fini.personnel="+retour.getRdi().getId()+" AND retour_produit_fini.magasin="+retour.getMagasin().getId()+" "
      + " AND retour_produit_fini.quantite="+retour.getQuantite()+" AND  retour_produit_fini.stocks_apres="+retour.getStocks_apres()+" "
       + " ORDER BY retour_produit_fini.date DESC";
          //System.out.println(sql2);
           ArrayList<retourProduit> liste=md_contenuRetour.listeDesReTourProduits(sql2);
           ArrayList<retourProduit> listeAll=md_contenuRetour.listeDesReTourProduits(sqlAll);
           DefaultTableModel model=( DefaultTableModel) this.vue.getJtableAuj().getModel();
           DefaultTableModel modelAll=( DefaultTableModel) this.vue.getJtableAll().getModel();
            ArrayList<OperationProductionProduitFini> listeBilan=ctr_contenuProduction.genererBilan("retour_produit_fini");
             DefaultTableModel modelBilan=( DefaultTableModel) this.vue.getJtableBilan().getModel();
                    //reinitialisation du JTABLE BILAN
              ctr_contenuEntreeMatierePremiere.reinitialiserTout(modelBilan);
           if(liste.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(liste, model,liste.get(0));
            }
           if(listeAll.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(listeAll, modelAll,listeAll.get(0));
            }   
          if(listeBilan.size()>0){
         ctr_contenuRetour.AfficherDansTableModel(listeBilan,modelBilan,listeBilan.get(0));
            }
           //on va reinitialiser tout les elements maintenant           
           //fin reinitialisation
          String messageSucess="Succes!!!! Retour Enregistrer avec succes"
                  + "   \n";
            ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
                      //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"Succes Insert",  JOptionPane.INFORMATION_MESSAGE, img2); 
                  }
               //si non on affiche que c pas bon
              else{
             //si tout a ete bien ete fait
          String messageSucess="Echec!!!! Une erreur innatendue s'est Produite quelque part"
                  + "   \n";
            ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
                      //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null,messageSucess,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);     
              
              }
                 }
                 }
         //on informe pour lui dire qu'il y a une saisie incorrecte ou insatisfaite
                 else{
           String message="Revoyez Vos Saisies,Vous devriez tout Cocher\n"
               + " Merci de Reesayer";
       ImageIcon img2=new ImageIcon("img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"Erreur Saisies",  JOptionPane.INFORMATION_MESSAGE, img2);    
                 
                 } 
                }
          //on informe par rapport a la date
            else{
             String message="La Date Selectionnée est Interdite\n"
               + "Vous devriez Saisir Une Date <= a celle du "+dateActuelle;
       ImageIcon img2=new ImageIcon("./img/LogoMc2.jpg");
               //   JoptionPane.showBlack(message);
       JOptionPane.showMessageDialog(null, message,"!!!Erreur Insert Date!!!",  JOptionPane.INFORMATION_MESSAGE, img2);  
                }
      }
    public void genererBordereaux(java.awt.event.ActionEvent evt)
    {
    String bor=md_contenuSortieMatierePremiere.genererUnBordereauxLivraison("retour_produit_fini","bordereaux","Rtr","Mc");
       ctr_contenuEntreeMatierePremiere.remplirItem(this.vue.getLibelle(), bor, 0);
    }
    /**
     * 
     * @param listeDesElements
     * @param model
     * @param obj 
     */
     @SuppressWarnings("empty-statement")
      public static void AfficherDansTableModel( ArrayList listeDesElements,DefaultTableModel model,Object obj)
            {
                 if(obj instanceof retourProduit){
         // System.out.println("Nombre de Element est :"+ entrp.size())
        
                              if(model.getColumnCount()<=6)
                              {
                             Object [] row =new Object[6];
                              if(model.getRowCount()==0 && listeDesElements.size()>=1){
           for(int i=0;i<listeDesElements.size();i++)
              {
               int nbre=i+1;
               row[0]=nbre;
               row[1]=((retourProduit)(listeDesElements.get(i))).getQuantite();;
               row[2]=((retourProduit)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((retourProduit)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((retourProduit)(listeDesElements.get(i))).getLibelle();
               row[5]=((retourProduit)(listeDesElements.get(i))).getRdi().getPrenom();
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
               row[1]=((retourProduit)(listeDesElements.get(i))).getQuantite();;
               row[2]=((retourProduit)(listeDesElements.get(i))).getStocks_avant();
               row[3]=((retourProduit)(listeDesElements.get(i))).getStocks_apres();
               row[4]=((retourProduit)(listeDesElements.get(i))).getLibelle();
               row[5]=((retourProduit)(listeDesElements.get(i))).getRdi().getPrenom();
                     
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
               row[1]=((retourProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((retourProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((retourProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((retourProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((retourProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[6]=((retourProduit)(listeDesElements.get(i))).getLibelle();
               row[7]=((retourProduit)(listeDesElements.get(i))).getRdi().getPrenom();
               
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
              row[1]=((retourProduit)(listeDesElements.get(i))).getDateString();
               row[2]=((retourProduit)(listeDesElements.get(i))).getQuantite();
               row[3]=((retourProduit)(listeDesElements.get(i))).getStocks_avant();
               row[4]=((retourProduit)(listeDesElements.get(i))).getStocks_apres();
               row[5]=((retourProduit)(listeDesElements.get(i))).getMagasin().getDesignation();
               row[6]=((retourProduit)(listeDesElements.get(i))).getLibelle();
               row[7]=((retourProduit)(listeDesElements.get(i))).getRdi().getPrenom();
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
