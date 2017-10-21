/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import classe.database;
import static controller.ctr_contenuEtatGlobal.avoirDateJanvierEncour;
import controller.ctr_contenuProduction;
import controller.ctr_contenuSortieMatierePremiere;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEtatGlobal;

/**
 *
 * @author Mr_JYPY
 */
public class contenuBilanParFamilleAutreProduit extends javax.swing.JPanel {

    /**
     * Creates new form contenuBilanParFamilleAutreProduit
     */
    public contenuBilanParFamilleAutreProduit() {
        init("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        entreeAutre = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        entree = new javax.swing.JTable();
        sortie = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        demandeS = new javax.swing.JTabbedPane();
        demandeNS = new javax.swing.JTabbedPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("BILAN DES ARTICLES PAR FAMILLE DES AUTRES PRODUITS");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        entree.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(entree);

        entreeAutre.addTab("tab1", jScrollPane1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        sortie.addTab("tab1", jScrollPane2);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 0));
        jLabel2.setText("        Entree par famille");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 0));
        jLabel3.setText("    Sortie par famille");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(32, 4, 4));
        jLabel4.setText("                    Demande Achats Satisfaite");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(32, 4, 4));
        jLabel5.setText("          Demande Achats Non Encore Satisfaite");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entreeAutre)
                    .addComponent(sortie))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(demandeNS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(demandeS, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(447, 447, 447))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(939, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(64, 64, 64)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(entreeAutre, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(demandeS, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(demandeNS, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(sortie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(486, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(380, 380, 380)))
        );
    }// </editor-fold>//GEN-END:initComponents
     private void init(String m)
     {
      jLabel1 = new javax.swing.JLabel();
        entreeAutre = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        entree = new javax.swing.JTable();
        sortie = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        demandeS = new javax.swing.JTabbedPane();
        demandeNS = new javax.swing.JTabbedPane();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("BILAN DES ARTICLES PAR FAMILLE DES AUTRES PRODUITS");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

          //le code
        
         remplissage(entreeAutre,"entree_autre_produit");
         remplissage(sortie,"sortie_autre_produit");
         remplissage(demandeS,1);
         remplissage(demandeNS,0);
        //fin

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 0));
        jLabel2.setText("        Entree par famille");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 0));
        jLabel3.setText("    Sortie par famille");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(32, 4, 4));
        jLabel4.setText("                    Demande Achats Satisfaite");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(32, 4, 4));
        jLabel5.setText("          Demande Achats Non Encore Satisfaite");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entreeAutre)
                    .addComponent(sortie))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(demandeNS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(demandeS, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(260, 260, 260)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
            .addGroup(layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(447, 447, 447))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(939, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(64, 64, 64)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(entreeAutre, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(demandeS, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(demandeNS, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(sortie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(486, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(380, 380, 380)))
        );
     
     }
    
    
    private void init()
    {
    
        jLabel1 = new javax.swing.JLabel();
        entreeAutre = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        entree = new javax.swing.JTable();
        sortie = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("BILAN DES ARTICLES PAR FAMILLE DES AUTRES PRODUITS");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        //le premier
         
        remplissage(entreeAutre,"entree_autre_produit");
         remplissage(sortie,"sortie_autre_produit");
        //le second
//        jTable2.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null},
//                {null, null, null, null}
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        jScrollPane2.setViewportView(jTable2);
//
//        sortie.addTab("tab1", jScrollPane2);

        
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 0));
        jLabel2.setText("ENTREE");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 0));
        jLabel3.setText("SORTIE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(325, 325, 325)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(383, 383, 383)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(entreeAutre, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(sortie)))
                .addContainerGap(92, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(421, 421, 421)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(721, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(entreeAutre, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(sortie, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(477, 477, 477)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addGap(390, 390, 390)))
        );
    
    }
    private void remplissage(JTabbedPane pane,String tables)
    {
    
     String sqlfamille="SELECT * FROM famille WHERE designation!='Matiere premiere'";
         ResultSet res=null;
         ResultSet resF=null;
       
           JScrollPane [] scroll=new JScrollPane[50];
         javax.swing.JTable [] table=new javax.swing.JTable[50];
         int i=1;
         int k=1;
         try {
               resF=database.getInstance().selectInTab(sqlfamille);
             while(resF.next()){
                 int familles=0;
                   familles=resF.getInt("id");
                   scroll[k]=new javax.swing.JScrollPane();
                 table[k]=new javax.swing.JTable();
                 DefaultTableModel model=( DefaultTableModel) table[k].getModel();
        model.setColumnIdentifiers( new String [] {
                             "Article", "Jour", "Semaine", "Mois","Annee","Stocks actuelle"
                         });
            
            String sqlC="select stocks.quantite,articles.designation as des,articles.id,articles.famille\n" +
       "FROM stocks\n" +
       "INNER JOIN articles ON articles.id=stocks.articles AND articles.famille="+familles+"";
              res=database.getInstance().selectInTab(sqlC);
             while(res.next())
             {  
              /**
               * mettre les entree la requete sql
               */
              int idArticle=res.getInt("id");
           Date dateHeb=new Date();
           
           java.sql.Date dateJourA=new java.sql.Date(dateHeb.getTime());
           int jour=dateHeb.getDay();
               
       dateHeb=ctr_contenuSortieMatierePremiere.addDaysToDate(dateHeb,-jour);
        System.out.println("la date reculee de jour pour la requete: "+dateHeb);
      java.sql.Date datesHeb=new java.sql.Date(dateHeb.getTime());
     
          Date date=new Date();
           date=ctr_contenuProduction.avoirPremierJourMois(date);
           
          Date  dates=new java.sql.Date(date.getTime());
          
    System.out.println("LA DATE EN PULSION "+datesHeb);
  //  String sqlHebdomadaire="SELECT SUM(quantite) as hebdo FROM "+tableEntree+" WHERE date>'"+datesHeb+"' ";
   // String sqlMensuel="SELECT SUM(quantite) as mois FROM "+tableEntree+" WHERE date>='"+dates+"'";
    //les variables pour les entrees
     String article=res.getString("des");
     int qj=md_contenuEtatGlobal.avoirQuantite(idArticle,tables,dateJourA);
     int qs=md_contenuEtatGlobal.avoirQuantite(idArticle,tables,datesHeb);
     int qm=md_contenuEtatGlobal.avoirQuantite(idArticle,tables,dates);
     int qn=md_contenuEtatGlobal.avoirQuantite(idArticle,tables,  avoirDateJanvierEncour());
     int stocks=res.getInt("quantite");
                  Object [] row =new Object[6];
         row[0]=article;
         row[1]=qj;
         row[2]=qs;
         row[3]=qm;
         row[4]=qn;
         row[5]=stocks;
         model.addRow(row);
                 i++;
             }
             scroll[k].setViewportView(table[k]);
                pane.addTab(resF.getString("designation"), scroll[k]);
                 k++;
             }
         } catch (SQLException ex) {
             Logger.getLogger(contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    /**
     * methode redefinie pour afficher par type de service les demandes achats Non satisfaite
     * ou Satisfaite selon le parametre entier passé.
     * le premier parametre le le tabbledPane qui devrai contenir tt les Jtable a afficher
     * le second est l'entier qui permettra de savoir si c'est le cas de la demande complete ou incomplete
     * EX:<strong>pour avoir DA satifaite il faut passer 1 en parametre et un autre Entier au cas contraire comme:</strong>
     * remplissage(notreTablledPane,0) pour les demandes non satisfaite jusqu'as ce jour
     * @param pane
     * @param type 
     */
      private void remplissage(JTabbedPane pane,int type)
    {
      
        int service=0;
     
      
        
        String sqlService="SELECT * FROM services WHERE designation!='ACHAT ET PRODUCTION'";

     String sqlfamille="SELECT * FROM famille WHERE designation!='Matiere premiere'";
         ResultSet res=null;
         ResultSet resF=null;
          String sqlDemande="";
           JScrollPane [] scroll=new JScrollPane[50];
         javax.swing.JTable [] table=new javax.swing.JTable[50];
         int i=1;
         int k=1;
         try {
               resF=database.getInstance().selectInTab(sqlService);
             while(resF.next()){
             
                 service=resF.getInt("id");
                 System.out.println("SERVICE: "+service);
                 //requete pour la demande
                 if(type==1)
                 {
              sqlDemande="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id  IN (SELECT demandesatisfaite_autreproduit.demandeAchat FROM demandesatisfaite_autreproduit)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille IN (SELECT famille.id FROM famille WHERE famille.designation!='Matiere premiere')))"
                + " AND demandeachat.service="+service+"";
                 }
                 else{
           sqlDemande="SELECT demandeachat.bordereaux as code,demandeachat.`date`,CONCAT(personnel.nom,CONCAT(' ',personnel.prenom)) as nom,services.designation as des\n" +
                 "FROM demandeachat INNER JOIN services ON services.id=demandeachat.service  \n" +
                 "INNER JOIN personnel ON personnel.id=demandeachat.demandeur \n" +
                 "WHERE demandeachat.id IN( SELECT cotation.demandeAchat FROM cotation WHERE cotation.id IN(SELECT fournisseursurcotation.cotation FROM fournisseursurcotation )) \n" +
                 "AND demandeachat.id NOT IN (SELECT demandesatisfaite_autreproduit.demandeAchat FROM demandesatisfaite_autreproduit)\n" +
                 "AND demandeachat.id IN (SELECT articlesdemandeachat.demandeAchat FROM articlesdemandeachat WHERE articlesdemandeachat.article IN(SELECT articles.id FROM articles WHERE articles.famille IN (SELECT famille.id FROM famille WHERE famille.designation!='Matiere premiere')))"
                 + " AND demandeachat.service="+service+" ";   
                 
                 }
                 //fin requete pour la demande
                   scroll[k]=new javax.swing.JScrollPane();
                 table[k]=new javax.swing.JTable();
                 DefaultTableModel model=( DefaultTableModel) table[k].getModel();
        model.setColumnIdentifiers( new String [] {
                             "N°", "Bordereaux", "Demandeur", "Date Demande"
                         });
            
              res=database.getInstance().selectInTab(sqlDemande);
                int cpt=1;
             while(res.next())
             {  
             
                  Object [] row =new Object[4];
         row[0]=cpt;
         row[1]=res.getString("code");
         row[2]=res.getString("nom");
         row[3]=res.getDate("date");
         model.addRow(row);
                 cpt++;
             }
             scroll[k].setViewportView(table[k]);
                pane.addTab(resF.getString("designation"), scroll[k]);
               
                 k++;
             }
         } catch (SQLException ex) {
             Logger.getLogger(contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane demandeNS;
    private javax.swing.JTabbedPane demandeS;
    private javax.swing.JTable entree;
    private javax.swing.JTabbedPane entreeAutre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTabbedPane sortie;
    // End of variables declaration//GEN-END:variables
}
