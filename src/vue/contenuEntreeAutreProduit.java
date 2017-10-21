/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import classe.EntreeProduit;
import classe.database;
import com.toedter.calendar.JDateChooser;
import controller.ctr_contenuEntreeAutreProduit;
import controller.ctr_contenuEntreeMatierePremiere;
import static controller.ctr_contenuEntreeMatierePremiere.remplirItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.md_contenuEntreeMatierePremiere;

/**
 *
 * @author Mr_JYPY
 */
public class contenuEntreeAutreProduit extends javax.swing.JPanel {

    /**
     * Creates new form contenuEntreeAutreProduit
     */
    private final ctr_contenuEntreeAutreProduit ctr;
    public contenuEntreeAutreProduit() {
      //  initComponents();
        initialisation();
        String sqlMag="SELECT designation FROM magasin WHERE type!='cartonMc'";
        ctr_contenuEntreeMatierePremiere.remplirItem(magasin, sqlMag);
        ctr_contenuEntreeMatierePremiere.remplirItem(magasin1, sqlMag);
        ctr_contenuEntreeMatierePremiere.MettreDate(dateActuelle);
        ctr_contenuEntreeMatierePremiere.MettreDate(dateActuelle1);
        //afficahge des demandes
           
   String sql4="SELECT bordereaux as code FROM demandeachat WHERE id IN("
           + " SELECT demandeAchat FROM cotation WHERE id IN(SELECT cotation FROM fournisseursurcotation )) "
           + " AND id NOT IN (SELECT demandeAchat FROM demandesatisfaite_autreproduit) "
           + " AND id IN (SELECT demandeAchat FROM articlesdemandeachat WHERE article IN(SELECT id FROM articles WHERE famille!=(SELECT id FROM famille WHERE designation='Matiere premiere')))";
         
             remplirItem(DemandeAchat,sql4,"code");
             remplirItem(DemandeAchat1,sql4,"code");
           ctr=new ctr_contenuEntreeAutreProduit(this);
             
       //initialisation pour remplir les tables
          String sql="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
                + "WHERE entree_autre_produit.date=CURRENT_DATE() ORDER BY derniere_modif DESC";
        /**
         * requet pour la date de Aujourd'hui  entree_produit.date=CURRENT_DATE()
         */
          String sql2="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
                + "WHERE 1 ORDER BY derniere_modif DESC";
          System.out.println(sql2);
//        ctrContenuMp=new ctr_contenuEntreeMatierePremiere(this);
//        ctrContenuMp.AfficherEntreeDuJour(sql2);
//        ctrContenuMp.AfficherEntree(sql);
        //  System.out.println(sql);
          DefaultTableModel model=( DefaultTableModel) tableOption1.getModel();
          ArrayList<EntreeProduit> liste=md_contenuEntreeMatierePremiere.ListeEntreeP(sql);
           DefaultTableModel modelAll=( DefaultTableModel) tableOption2.getModel();
          ArrayList<EntreeProduit> listeAll=md_contenuEntreeMatierePremiere.ListeEntreeP(sql2);
         // System.out.println("taille: "+liste.size());
          if(liste.size()>0)
          {
          ctr_contenuEntreeMatierePremiere.AfficherEntreeDuJour(liste, model);
          }
          if(listeAll.size()>0)
          {
        ctr_contenuEntreeMatierePremiere.AfficherEntree(listeAll, modelAll);
          }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateActuelle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bonComande = new javax.swing.JTextField();
        autreDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lesArticles = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        quantite = new javax.swing.JTextField();
        entrer = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        magasin = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        vehiculeNumber = new javax.swing.JTextField();
        fournisseur = new javax.swing.JComboBox();
        DemandeAchat = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableOption1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        dateActuelle1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        bonComande1 = new javax.swing.JTextField();
        autreDatePourAutreOption = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        entrer1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        magasin1 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        VehiculeNumber1 = new javax.swing.JTextField();
        FournisseurAutreChoix = new javax.swing.JComboBox();
        DemandeAchat1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableChoix = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOption2 = new javax.swing.JTable();
        validerAutreChoix = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fournisseur Different Pour une Meme DA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(204, 0, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText(" Date Actuelle:");
        jLabel2.setToolTipText("");

        dateActuelle.setBackground(new java.awt.Color(102, 102, 0));
        dateActuelle.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateActuelle.setForeground(new java.awt.Color(102, 51, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Bon LiVraison");

        bonComande.setToolTipText("Le Bordereaux de Livraison");
        bonComande.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        autreDate.setDateFormatString("d MMMM yyyy");
        autreDate.setDoubleBuffered(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("   Autre Date:");
        jLabel5.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("    Dme Achat");

        lesArticles.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lesArticles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));
        lesArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lesArticlesActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Qtité");

        quantite.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        quantite.setForeground(new java.awt.Color(204, 0, 0));
        quantite.setToolTipText("la Quantité Sortie");
        quantite.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 0, 51), null, null));

        entrer.setBackground(new java.awt.Color(51, 51, 0));
        entrer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entrer.setText("ENREGISTRER");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText(" Mag.");

        magasin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        magasin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));
        magasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magasinActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("N° Vehicule");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Fournisseur");

        vehiculeNumber.setToolTipText("Le Bordereaux de Livraison");
        vehiculeNumber.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fournisseur.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        fournisseur.setForeground(new java.awt.Color(204, 0, 0));
        fournisseur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        DemandeAchat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DemandeAchat.setForeground(new java.awt.Color(102, 102, 0));
        DemandeAchat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Matiere");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(entrer, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fournisseur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(autreDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateActuelle, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vehiculeNumber)
                            .addComponent(DemandeAchat, 0, 148, Short.MAX_VALUE)
                            .addComponent(bonComande))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(quantite, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lesArticles, javax.swing.GroupLayout.Alignment.LEADING, 0, 129, Short.MAX_VALUE)
                    .addComponent(magasin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateActuelle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DemandeAchat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lesArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(autreDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bonComande, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(vehiculeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fournisseur)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(entrer, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(magasin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        tableOption1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tableOption1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableOption1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Designation", "Bon Livraison", "bordereaux DA", "qte entree", "Stocks avt", "Stocks apres", "Fournisseur"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableOption1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fournisseur Identique Pour une Meme DA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(51, 51, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText(" Date Actuelle:");
        jLabel12.setToolTipText("");

        dateActuelle1.setBackground(new java.awt.Color(102, 102, 0));
        dateActuelle1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateActuelle1.setForeground(new java.awt.Color(102, 51, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Bon Livrson");

        bonComande1.setToolTipText("Le Bordereaux de Livraison");
        bonComande1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        autreDatePourAutreOption.setDateFormatString("d MMMM yyyy");
        autreDatePourAutreOption.setDoubleBuffered(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("   Autre Date:");
        jLabel14.setToolTipText("");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("    Dme Achat");

        entrer1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entrer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/suivant.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 51));
        jLabel17.setText(" Mag.");

        magasin1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        magasin1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("N° Vehicule");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Fournisseur");

        VehiculeNumber1.setToolTipText("Le Bordereaux de Livraison");
        VehiculeNumber1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        FournisseurAutreChoix.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FournisseurAutreChoix.setForeground(new java.awt.Color(153, 0, 0));
        FournisseurAutreChoix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        DemandeAchat1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        DemandeAchat1.setForeground(new java.awt.Color(102, 102, 0));
        DemandeAchat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(autreDatePourAutreOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FournisseurAutreChoix, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(dateActuelle1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bonComande1)
                    .addComponent(DemandeAchat1, 0, 166, Short.MAX_VALUE)
                    .addComponent(VehiculeNumber1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(magasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(entrer1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateActuelle1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DemandeAchat1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(autreDatePourAutreOption, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bonComande1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(magasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VehiculeNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FournisseurAutreChoix, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addComponent(entrer1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 51));
        jLabel1.setText("                 ENTREE DES AUTRES PARTICLES");

        tableChoix.setBackground(new java.awt.Color(255, 255, 204));
        tableChoix.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tableChoix.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tableChoix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Designation", "quaniter", "Bor Demande"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableChoix);
        if (tableChoix.getColumnModel().getColumnCount() > 0) {
            tableChoix.getColumnModel().getColumn(0).setMinWidth(30);
            tableChoix.getColumnModel().getColumn(0).setMaxWidth(35);
            tableChoix.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel3.setBackground(new java.awt.Color(204, 51, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("         LISTE DE TOUTES LES ENTREES");

        tableOption2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "articles", "vehicule", "bon Livraison", "quantite", "stocks_avant", "stocks_apres", "magasin", "fournisseur", "demande Achat", "dateInsertion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableOption2);
        if (tableOption2.getColumnModel().getColumnCount() > 0) {
            tableOption2.getColumnModel().getColumn(0).setMinWidth(30);
            tableOption2.getColumnModel().getColumn(0).setMaxWidth(35);
            tableOption2.getColumnModel().getColumn(4).setMinWidth(50);
            tableOption2.getColumnModel().getColumn(4).setMaxWidth(55);
        }

        validerAutreChoix.setBackground(new java.awt.Color(0, 51, 51));
        validerAutreChoix.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        validerAutreChoix.setText("VALIDER");
        validerAutreChoix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAutreChoixActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 0, 0));
        jLabel20.setText("              Toutes Les Entrées Par Matieres Premieres");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable1);

        jTabbedPane1.addTab("tab1", jScrollPane2);

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
        jScrollPane5.setViewportView(jTable2);

        jTabbedPane1.addTab("tab2", jScrollPane5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(validerAutreChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(341, 341, 341))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(validerAutreChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JComboBox getDemandeAchat() {
        return DemandeAchat;
    }

    public void setDemandeAchat(JComboBox DemandeAchat) {
        this.DemandeAchat = DemandeAchat;
    }

    public JComboBox getDemandeAchat1() {
        return DemandeAchat1;
    }

    public void setDemandeAchat1(JComboBox DemandeAchat1) {
        this.DemandeAchat1 = DemandeAchat1;
    }

    public JComboBox getFournisseurAutreChoix() {
        return FournisseurAutreChoix;
    }

    public void setFournisseurAutreChoix(JComboBox FournisseurAutreChoix) {
        this.FournisseurAutreChoix = FournisseurAutreChoix;
    }

    public JTextField getVehiculeNumber1() {
        return VehiculeNumber1;
    }

    public void setVehiculeNumber1(JTextField VehiculeNumber1) {
        this.VehiculeNumber1 = VehiculeNumber1;
    }

    public JDateChooser getAutreDate() {
        return autreDate;
    }

    public void setAutreDate(JDateChooser autreDate) {
        this.autreDate = autreDate;
    }

    public JDateChooser getAutreDatePourAutreOption() {
        return autreDatePourAutreOption;
    }

    public void setAutreDatePourAutreOption(JDateChooser autreDatePourAutreOption) {
        this.autreDatePourAutreOption = autreDatePourAutreOption;
    }

    public JTextField getBonComande() {
        return bonComande;
    }

    public void setBonComande(JTextField bonComande) {
        this.bonComande = bonComande;
    }

    public JTextField getBonComande1() {
        return bonComande1;
    }

    public void setBonComande1(JTextField bonComande1) {
        this.bonComande1 = bonComande1;
    }

    public JLabel getDateActuelle() {
        return dateActuelle;
    }

    public void setDateActuelle(JLabel dateActuelle) {
        this.dateActuelle = dateActuelle;
    }

    public JLabel getDateActuelle1() {
        return dateActuelle1;
    }

    public void setDateActuelle1(JLabel dateActuelle1) {
        this.dateActuelle1 = dateActuelle1;
    }

    public JButton getEntrer() {
        return entrer;
    }

    public void setEntrer(JButton entrer) {
        this.entrer = entrer;
    }

    public JButton getEntrer1() {
        return entrer1;
    }

    public void setEntrer1(JButton entrer1) {
        this.entrer1 = entrer1;
    }

    public JComboBox getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(JComboBox fournisseur) {
        this.fournisseur = fournisseur;
    }

    public JComboBox getLesArticles() {
        return lesArticles;
    }

    public void setLesArticles(JComboBox lesArticles) {
        this.lesArticles = lesArticles;
    }

    public JComboBox getMagasin() {
        return magasin;
    }

    public void setMagasin(JComboBox magasin) {
        this.magasin = magasin;
    }

    public JComboBox getMagasin1() {
        return magasin1;
    }

    public void setMagasin1(JComboBox magasin1) {
        this.magasin1 = magasin1;
    }

    public JTextField getQuantite() {
        return quantite;
    }

    public void setQuantite(JTextField quantite) {
        this.quantite = quantite;
    }

    public JTable getTableChoix() {
        return tableChoix;
    }

    public void setTableChoix(JTable tableChoix) {
        this.tableChoix = tableChoix;
    }

    public JTable getTableOption1() {
        return tableOption1;
    }

    public void setTableOption1(JTable tableOption1) {
        this.tableOption1 = tableOption1;
    }

    public JTable getTableOption2() {
        return tableOption2;
    }

    public void setTableOption2(JTable tableOption2) {
        this.tableOption2 = tableOption2;
    }

    public JButton getValiderAutreChoix() {
        return validerAutreChoix;
    }

    public void setValiderAutreChoix(JButton validerAutreChoix) {
        this.validerAutreChoix = validerAutreChoix;
    }

    public JTextField getVehiculeNumber() {
        return vehiculeNumber;
    }

    public void setVehiculeNumber(JTextField vehiculeNumber) {
        this.vehiculeNumber = vehiculeNumber;
    }
     public void initialisation()
     {
     
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        dateActuelle = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        bonComande = new javax.swing.JTextField();
        autreDate = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lesArticles = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        quantite = new javax.swing.JTextField();
        entrer = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        magasin = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        vehiculeNumber = new javax.swing.JTextField();
        fournisseur = new javax.swing.JComboBox();
        DemandeAchat = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableOption1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        dateActuelle1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        bonComande1 = new javax.swing.JTextField();
        autreDatePourAutreOption = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        entrer1 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        magasin1 = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        VehiculeNumber1 = new javax.swing.JTextField();
        FournisseurAutreChoix = new javax.swing.JComboBox();
        DemandeAchat1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableChoix = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOption2 = new javax.swing.JTable();
        validerAutreChoix = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fournisseur Different Pour une Meme DA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(204, 0, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText(" Date Actuelle:");
        jLabel2.setToolTipText("");

        dateActuelle.setBackground(new java.awt.Color(102, 102, 0));
        dateActuelle.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateActuelle.setForeground(new java.awt.Color(102, 51, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Bon LiVraison");

        bonComande.setToolTipText("Le Bordereaux de Livraison");
        bonComande.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        autreDate.setDateFormatString("d MMMM yyyy");
        autreDate.setDoubleBuffered(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("   Autre Date:");
        jLabel5.setToolTipText("");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("    Dme Achat");

        lesArticles.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lesArticles.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));
        lesArticles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lesArticlesActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("Qtité");

        quantite.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        quantite.setForeground(new java.awt.Color(204, 0, 0));
        quantite.setToolTipText("la Quantité Sortie");
        quantite.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 0, 51), null, null));

        entrer.setBackground(new java.awt.Color(51, 51, 0));
        entrer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entrer.setText("ENREGISTRER");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText(" Mag.");

        magasin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        magasin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));
        magasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magasinActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("N° Vehicule");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Fournisseur");

        vehiculeNumber.setToolTipText("Le Bordereaux de Livraison");
        vehiculeNumber.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        fournisseur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        fournisseur.setForeground(new java.awt.Color(204, 0, 0));
        fournisseur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        DemandeAchat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DemandeAchat.setForeground(new java.awt.Color(102, 102, 0));
        DemandeAchat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 0, 0));
        jLabel11.setText("Matiere");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(entrer, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fournisseur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(autreDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(dateActuelle, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vehiculeNumber)
                            .addComponent(DemandeAchat, 0, 148, Short.MAX_VALUE)
                            .addComponent(bonComande))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(quantite, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lesArticles, javax.swing.GroupLayout.Alignment.LEADING, 0, 129, Short.MAX_VALUE)
                    .addComponent(magasin, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateActuelle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DemandeAchat, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lesArticles, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(autreDate, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bonComande, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(quantite, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(vehiculeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fournisseur)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(entrer, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(magasin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        tableOption1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tableOption1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableOption1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Designation", "Bon Livraison", "bordereaux DA", "qte entree", "Stocks avt", "Stocks apres", "Fournisseur"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tableOption1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fournisseur Identique Pour une Meme DA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(51, 51, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText(" Date Actuelle:");
        jLabel12.setToolTipText("");

        dateActuelle1.setBackground(new java.awt.Color(102, 102, 0));
        dateActuelle1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        dateActuelle1.setForeground(new java.awt.Color(102, 51, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Bon Livrson");

        bonComande1.setToolTipText("Le Bordereaux de Livraison");
        bonComande1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        autreDatePourAutreOption.setDateFormatString("d MMMM yyyy");
        autreDatePourAutreOption.setDoubleBuffered(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("   Autre Date:");
        jLabel14.setToolTipText("");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("    Dme Achat");

        entrer1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        entrer1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/suivant.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 0, 51));
        jLabel17.setText(" Mag.");

        magasin1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        magasin1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("N° Vehicule");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Fournisseur");

        VehiculeNumber1.setToolTipText("Le Bordereaux de Livraison");
        VehiculeNumber1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        FournisseurAutreChoix.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        FournisseurAutreChoix.setForeground(new java.awt.Color(153, 0, 0));
        FournisseurAutreChoix.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        DemandeAchat1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        DemandeAchat1.setForeground(new java.awt.Color(102, 102, 0));
        DemandeAchat1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Aucun choix" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(autreDatePourAutreOption, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FournisseurAutreChoix, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(dateActuelle1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bonComande1)
                    .addComponent(DemandeAchat1, 0, 166, Short.MAX_VALUE)
                    .addComponent(VehiculeNumber1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(magasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(238, 238, 238))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(309, 309, 309)
                .addComponent(entrer1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateActuelle1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DemandeAchat1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(autreDatePourAutreOption, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(bonComande1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(magasin1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(VehiculeNumber1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FournisseurAutreChoix, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addComponent(entrer1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 51));
        jLabel1.setText("                 ENTREE DES AUTRES PARTICLES");

        // tableChoix.setBackground(new java.awt.Color(255, 255, 204));
        tableChoix.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        tableChoix.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tableChoix.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Designation", "quaniter", "Bor Demande"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableChoix);
        if (tableChoix.getColumnModel().getColumnCount() > 0) {
            tableChoix.getColumnModel().getColumn(0).setMinWidth(30);
            tableChoix.getColumnModel().getColumn(0).setMaxWidth(35);
            tableChoix.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel3.setBackground(new java.awt.Color(204, 51, 0));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("         LISTE DE TOUTES LES ENTREES");

        tableOption2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "articles", "vehicule", "bon Livraison", "quantite", "stocks_avant", "stocks_apres", "magasin", "fournisseur", "demande Achat", "dateInsertion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableOption2);
        if (tableOption2.getColumnModel().getColumnCount() > 0) {
            tableOption2.getColumnModel().getColumn(0).setMinWidth(30);
            tableOption2.getColumnModel().getColumn(0).setMaxWidth(35);
            tableOption2.getColumnModel().getColumn(4).setMinWidth(50);
            tableOption2.getColumnModel().getColumn(4).setMaxWidth(55);
        }

        validerAutreChoix.setBackground(new java.awt.Color(0, 51, 51));
        validerAutreChoix.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        validerAutreChoix.setText("VALIDER");
        validerAutreChoix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validerAutreChoixActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 0, 0));
        jLabel20.setText("              Toutes Les Entrées Par Matieres Premieres");

       //debut
        
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
                             "N°", "Article", "Date", "quantiter", "stocks_avant","stocks_apres","Num Livr."
                         });
            
            String sqlC="SELECT * FROM articles WHERE famille="+familles+"";
              res=database.getInstance().selectInTab(sqlC);
             while(res.next())
             {  
              /**
               * mettre les entree la requete sql
               */
                 String sqlListe="SELECT entree_autre_produit.id as id,entree_autre_produit.dateString as dateEntree,entree_autre_produit.numeroVehicule,entree_autre_produit.derniere_modif,"
                + "entree_autre_produit.libelle,entree_autre_produit.quantite,entree_autre_produit.stocks_avant,\n" +
            "entree_autre_produit.stocks_apres,articles.id as idArticle,articles.designation as designationArticle,\n" +
            "fournisseur.id as idFournisseur,fournisseur.nom,magasin.id as idM,magasin.designation as nomMag,\n" +
            "demandeachat.id as idDemande,demandeachat.bordereaux\n" +
            "FROM `entree_autre_produit`\n" +
            "INNER JOIN fournisseur ON fournisseur.id=entree_autre_produit.fournisseur\n" +
            "INNER JOIN demandeachat ON demandeachat.id=entree_autre_produit.demandeAchat\n" +
            "INNER JOIN magasin ON magasin.id=entree_autre_produit.magasin\n" +
            "INNER JOIN articles ON articles.id=entree_autre_produit.article "
            + "WHERE entree_autre_produit.article="+res.getInt("id")+" ORDER BY derniere_modif DESC";
            //  System.out.println(sql);
             ArrayList<EntreeProduit> liste=md_contenuEntreeMatierePremiere.ListeEntreeP(sqlListe);
              
       
         Object [] row =new Object[7];
              int cpt=1;
           for(int j=0;j<liste.size();j++)
              
           {
             //  model.addRow(row);
            row[0]=  cpt; 
            row[1]=  liste.get(j).getArticle().getDesignation();
            row[2]=  liste.get(j).getDateString();
            row[3]=  liste.get(j).getQuantite(); 
            row[4]=   liste.get(j).getStocks_avant();
            row[5]=  liste.get(j).getStocks_apres();
            row[6]= liste.get(j).getLibelle();
             cpt++;
                model.addRow(row);

              }

                 
                 i++;
             }
             scroll[k].setViewportView(table[k]);
                 jTabbedPane1.addTab(resF.getString("designation"), scroll[k]);
                 k++;
             }
         } catch (SQLException ex) {
             Logger.getLogger(contenuEntreeMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
         }
        //fin

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(validerAutreChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(341, 341, 341))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 30, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(validerAutreChoix, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
     }
    private void lesArticlesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lesArticlesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lesArticlesActionPerformed

    private void validerAutreChoixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validerAutreChoixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_validerAutreChoixActionPerformed

    private void magasinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magasinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_magasinActionPerformed
  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox DemandeAchat;
    private javax.swing.JComboBox DemandeAchat1;
    private javax.swing.JComboBox FournisseurAutreChoix;
    private javax.swing.JTextField VehiculeNumber1;
    private com.toedter.calendar.JDateChooser autreDate;
    private com.toedter.calendar.JDateChooser autreDatePourAutreOption;
    private javax.swing.JTextField bonComande;
    private javax.swing.JTextField bonComande1;
    private javax.swing.JLabel dateActuelle;
    private javax.swing.JLabel dateActuelle1;
    private javax.swing.JButton entrer;
    private javax.swing.JButton entrer1;
    private javax.swing.JComboBox fournisseur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox lesArticles;
    private javax.swing.JComboBox magasin;
    private javax.swing.JComboBox magasin1;
    private javax.swing.JTextField quantite;
    private javax.swing.JTable tableChoix;
    private javax.swing.JTable tableOption1;
    private javax.swing.JTable tableOption2;
    private javax.swing.JButton validerAutreChoix;
    private javax.swing.JTextField vehiculeNumber;
    // End of variables declaration//GEN-END:variables
}
