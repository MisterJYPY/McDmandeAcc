/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import classe.Magasin;
import classe.Personnel;
import classe.clients;
import classe.database;
import classe.venteProduitFini;
import controller.ctr_contenuSortieMatierePremiere;
import controller.ctr_contenuVente;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.md_contenuProduction.stocksProduitFinis;
import static model.md_contenuProduction.stocksProduitFinisEnMagasin;

/**
 *cette classe contient des methodes imbriquer pour un soucils de requete 
 * general avec la base de donnee pour avoir certaines informations essentielles
 * Ces methodes subiront dans les prochaines versions des modifications en vu de leur Optimisation
 * Notons par ailleur que cette classe est le model gerer par le controlleur de Vente ctr_contenuVente situe dans le Package Controller
 * @author Mr_JYPY
 * 
 */
public class md_contenuVente {
    
      // private database dtb;
      
       /**
        * methode permettant de generer une requete complete pour avoir la liste des cliens d'une ou 
        * un ensemble de localite donné.le parametre (tableau entiers ) contient uniquement les ID des secteurs_zone_localites
        * Cette methode pouvait effectuer la requete et ramener Sous forme de ArrayList le nom des clients mais
        * la methode static utiliser pour remplir les listes deroulantes prends en parametre un cmbox et une requete 
        * Ainsi pour d'enventuels utulisation differente l'on devra la surcharger
        * ---------------cette methode est appeler de la maniere suivante avec ces methods:--------------
        * 1-RenvoyerIdPersonnelEtIdZone(String nomRDI,String Prenom) ramener un Tableau entier,se referer a sa doc
        * 2-donnerIdDesLocalitesGouverenerRDI(int [] tab) ,se referer a sa Doc
        * 3-donnerClientsDeRdi(int [] tab),  qui est la methode actuelle
        * @param tab
        * @return 
        */
        public static String donnerClientsDeRdi(int [] tab)
        {
         
        String concat="";
            //creation de la concatenation
          for(int i=0;i<tab.length;i++)
          {
              
             if(tab[i]>-1)
             {
             // System.out.println("ID ZONE LOCALITE PRIS EST: "+tab[i]);
            concat=concat.concat(Integer.toString(tab[i]));
                if(tab[i+1]>-1)
                {
                    concat=concat.concat(",");
                }
             }
          }
          String sql="null";
          if(!"".equals(concat)){
        sql=" SELECT nom as designation  FROM clients WHERE secteur_zone_localite IN("+concat+")";
          }
           // System.out.println(sql);
          return sql;
        }
     /**
      * cette methode presente dans le model de vente permet de renvoyer l'id ded'un RDI et l'id de 
      * la zone kil gouverne elle prends en parametre le nom et le prenom du RDI
      * e
      * @param nomRDI
      * @param Prenom
      * @return un tableau de 2 entiers dont la premier element est l'id du RDI et a patir du secon l'id ou les id en fonction
      * du Nombre de zone gouverner
      */
       public static int[] RenvoyerIdPersonnelEtIdZone(String nomRDI,String Prenom)
       {
                  int[] tab=null;
               
                nomRDI=nomRDI.replace("'", "\\'");
//                 // nomRDI=Remplacer(nomRDI);
//                //  Prenom=Remplacer(Prenom);
               Prenom=Prenom.replace("'", "\\'");
//               System.out.println(nomRDI);
        int id=0;
        String sql="SELECT id"
                + " FROM personnel"
                + " WHERE nom='"+nomRDI+"' AND prenom='"+Prenom+"'"
                + " AND fonction='RESPONSABLE DISTRIBUTEUR INTERNE' AND service=(SELECT id FROM services WHERE abreviation='COM') ";
      System.out.println(sql);
        try {
                ResultSet res=database.getInstance().selectInTab(sql);
                 while(res.next())
                 {
                     id=res.getInt("id");
                 }
                 res=null;
                 tab=new int[10];
                   //initialisation
                    for(int i=0;i<10;i++)
                    {
                         tab[i]=-1;
                    }
                     tab[0]=id;
                System.out.println("ID DU PERSONNEL: "+id);
                  String sqlSZ="SELECT secteur_zone"
                + " FROM zone_secteur_rdi WHERE personnel="+id+" ";
                  res=database.getInstance().selectInTab(sqlSZ);
                  int idsz=0;
                  int cpt=1;
                  while(res.next()){
                  tab[cpt]=res.getInt("secteur_zone");
              //    System.out.println("ID ZONE : "+res.getInt("secteur_zone"));  
                   cpt++;
                  }
                  res.close();
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         return tab;
       }
       /**
        * methode permettant de genrer un tableau dentier conenant l'ID des localites liee au secteur_zone
        * pour un RDI donne.Le tableau en parametre a son premier Element qui est l'ID du personnel
        * et le reste les Id des secteur_zone gouverner par le personnel qui a l'ID dans lelement 1 du tableau
        * elle servira pour la methode qui genere la requete pour avoir les Clients de la Zone nommé donnerClientsDeRdi(int [] tab)
        * @param tab le Tableau des entier,Premier element Id du RDI imperativement et le reste les Id de ses zones
        * @return un Tableau d'entier contenant l'ID des localites ,cad des villes ou l'ensemble des villes
        */
       public static int[] donnerIdDesLocalitesGouverenerRDI(int [] tab)
       {
        int[] tabi=null;
       tabi=new int[10];
       //initialisation
                    for(int i=0;i<10;i++)
                    {
                         tabi[i]=-1;
                    }
       //le premier element contient l'id de la personne
           int idRDI=tab[0];
        String concat="";
            //creation de la concatenation
          for(int i=1;i<tab.length;i++)
          {
             if(tab[i]>-1)
             {
            concat=concat.concat(Integer.toString(tab[i]));
                if(tab[i+1]>-1)
                {
                    concat=concat.concat(",");
                }
             }
          }
           System.out.println("concat est ( "+concat+" )");
         String sql=" SELECT id FROM secteur_zone_localite WHERE secteur_zone IN("+concat+")";
            try {
                //System.out.println(sql);
                int cpt=0;
                ResultSet res=database.getInstance().selectInTab(sql);
                while(res.next())
                {
                tabi[cpt]=res.getInt("id");
               //  System.out.println("ID DES SECTEUR LOCALITES :"+res.getInt("id"));
                cpt++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
            }
        return tabi;
       }
       public static String Remplacer(String s) 
{ 
String in = s; 
String out=""; 
String tmp = null; 

for (int i = 0; i < in.length() ; i++) 
{ 
tmp = in.substring(i,i+1); 
if (tmp.equals("'")) 
{ 
out = out + tmp +"'"; 
} else 
{ 
out = out + tmp; 
} 
} 
return out; 
}
       public static int donnerStocksEnMagasinProduitFini(Magasin mag)
       {
        int stk=0;
      String sqlStocks="SELECT SUM(quantite) as stock FROM stocks_magasin_produit_fini WHERE magasin="+mag.getId()+"";
            try {
                ResultSet res=database.getInstance().selectInTab(sqlStocks);
                while(res.next())
                {
                stk=res.getInt("stock");
                }
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
            }
       
      return stk;
       }
        public venteProduitFini genererEntrerPruitFini(int quantite,String Magasin,java.sql.Date dates,String libelle,String dateString,String type,String NomRDI,String PrenomRDI,String NomClients)
     {
         int[] tab;
       Personnel personnel=genererRDIViaNom(NomRDI, PrenomRDI);
           
       clients  client=genererClientsViaNom(NomClients);
       
       venteProduitFini entrep=new venteProduitFini();
        Magasin mgasin=ctr_contenuSortieMatierePremiere.genererMagasinViaDesignation(Magasin);
         int stocks_avant=stocksProduitFinis();
         int stocks_apres=stocks_avant-quantite;
         
         entrep.setDate(dates);
         entrep.setMagasin(mgasin);
         entrep.setQuantite(quantite);
         entrep.setStocks_avant(stocks_avant);
         entrep.setStocks_apres(stocks_apres);
         entrep.setLibelle(libelle);
         entrep.setDateString(dateString);
         entrep.setType(type);
         entrep.setClient(client);
         entrep.setRdi(personnel);
         
         return entrep;
     }
        /**
         * methode permettant de Creer ou d'inserer une Vente de Produit Fini
         * dans la base de donnee precisement dans la Table vente_produit_fini
         * @param ventePfini qui est Une instance de 'venteProduitFini'
         * @return un boolean true si tout a ete bien fait
         */
     public boolean creerVenteProduit(venteProduitFini ventePfini)
     {
        boolean estOk=false;
       //debut
         Connection con=null;
   int  NvoStocksMag=stocksProduitFinisEnMagasin(ventePfini.getMagasin())-ventePfini.getQuantite();
        String sql="INSERT INTO `vente_produit_fini`( `bordereaux`, `quantite`, `stocks_avant`, `stocks_apres`, `date`, `dateString`,`personnel`,`client`,`magasin`) "
        + " VALUES ('"+ventePfini.getLibelle()+"',"+ventePfini.getQuantite()+",'"+ventePfini.getStocks_avant()+"',"+ventePfini.getStocks_apres()+",'"+ventePfini.getDate()+"','"+ventePfini.getDateString()+"',"+ventePfini.getRdi().getId()+","+ventePfini.getClient().getId()+","+ventePfini.getMagasin().getId()+")"
        + "";
     String sqlActualisationStoks="UPDATE `stocks_produit_fini` SET `quantite`="+ventePfini.getStocks_apres()+" WHERE quantite="+ventePfini.getStocks_avant()+"";
     String sqlActualisationMagasin="UPDATE `stocks_magasin_produit_fini` SET `quantite`="+NvoStocksMag+" WHERE magasin="+ventePfini.getMagasin().getId()+" ";    
     //System.out.println("ECRITURE DE LA DATE:"+sortie.getDate());
       boolean a=false,b= false,c=false;
               try {
                database.getInstance().getCon().setAutoCommit(false);
               a= database.getInstance().InserData(sql);
               if(a){
                b= database.getInstance().InserData(sqlActualisationStoks);
               }
               if(b)
               {
               c= database.getInstance().InserData(sqlActualisationMagasin);
                  if(c)
                  {
                  estOk=true;
                  }
               }
                   database.getInstance().getCon().commit();
                   database.getInstance().getCon().setAutoCommit(true);   
               } catch (SQLException ex) {
           Logger.getLogger(md_contenuSortieMatierePremiere.class.getName()).log(Level.SEVERE, null, ex);
                
            }
         
        
        
        //fin
        return estOk;
     }
         public clients genererClientsViaNom(String NomClient)
    {
      clients client=new clients();
         client.setNom(NomClient);
      String sql="SELECT * FROM clients WHERE nom='"+NomClient+"' ";
         try {
             ResultSet res=database.getInstance().selectInTab(sql);
               while(res.next())
               {
              client.setId(res.getInt("id"));
              client.setMailString("mail");
              client.setSecteur(res.getInt("secteur_zone_localite"));
               }
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
         }
       return client;
    }
         /**
          * methode permettant de generer une instance de Personnel 
          * precisement un personnel qui a la fonction de RDI en fonction de son Nom et Prenom
          * @param NomRDI le Nom du RDI
          * @param Prenom le Prenom du RDI
          * @return une Instance de Personnel ou un RDI
          */
     public static Personnel genererRDIViaNom(String NomRDI,String Prenom)
    {
      Personnel personnel=new Personnel();
         personnel.setNom(NomRDI);
         personnel.setPrenom(Prenom);
          NomRDI=NomRDI.replace("'", "\\'");
         Prenom=Prenom.replace("'", "\\'");
      String sql="SELECT * "
                + " FROM personnel"
                + " WHERE nom='"+NomRDI+"' AND prenom='"+Prenom+"'"
                + " AND fonction='RESPONSABLE DISTRIBUTEUR INTERNE' AND service=(SELECT id FROM services WHERE abreviation='COM') ";
         try {
             ResultSet res=database.getInstance().selectInTab(sql);
               while(res.next())
               {
              personnel.setId(res.getInt("id"));
                
               }
         } catch (SQLException ex) {
             Logger.getLogger(ctr_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
         }
       return personnel;
    }
     public static ArrayList listeDesVentes(String requete)
     {
        ArrayList<venteProduitFini> liste=new ArrayList();
            venteProduitFini vpf;
            Personnel per;
            clients clt;
            try {
           ResultSet res=database.getInstance().selectInTab(requete);
             while(res.next())
             {
             //recuperation des information
           int id=res.getInt("id");
           String dateString=res.getString("dateString");
           String bordereaux=res.getString("bordereaux");
           int quantite=res.getInt("quantite");
           int stocks_avant=res.getInt("stocks_avant");
           int stocks_apres=res.getInt("stocks_apres");
               //partie client
           String client=res.getString("nomC");
           int  idClient=res.getInt("idC");
              clt=new clients();
              clt.setId(idClient);
              clt.setNom(client);
              //partie personel
              int idPersonne=res.getInt("idP");
              String nomPersonne=res.getString("nomP");
              String prenomPersonne=res.getString("prenomP");
             per=new Personnel(idPersonne);
             per.setNom(nomPersonne);
             per.setPrenom(prenomPersonne);
             //assignement des valeurs
             vpf=new venteProduitFini(per, clt);
             vpf.setId(id);
             vpf.setDateString(dateString);
             vpf.setQuantite(quantite);
             vpf.setStocks_avant(stocks_avant);
             vpf.setStocks_apres(stocks_apres);
             vpf.setLibelle(bordereaux);
             
              liste.add(vpf);
              //reinitialisation des Objets
             vpf=null;
             per=null;
             clt=null;
             }
            } catch (SQLException ex) {
                Logger.getLogger(md_contenuVente.class.getName()).log(Level.SEVERE, null, ex);
            }
          return liste;
     }
}
