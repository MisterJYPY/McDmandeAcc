/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mr_JYPY
 */
public final class database {
       /**
        * 
        */
//     private String adresse="db4free.net:3306";
//     private String bd="mccroftbd";
//     private String url="jdbc:mysql://"+adresse+"/"+bd;
//     private String login="misterjypy";
//     private String pass="paul1990";
      private String adresse="localhost";
     private String bd="mccroftbd";
     private String url="jdbc:mysql://"+adresse+"/"+bd;
     private String login="root";
     private String pass="";
     /**
      * login pour le server local
      */
     private String adresseLocale="localhost";
     private String urlLocale="jdbc:mysql://"+adresseLocale+"/"+bd;
     private String loginLocale="root";
     private String passLocale="";
     /**
      * definition des variables dinstance
      */
    private static Connection con=null;
    private  Statement state=null;
    private static database dbInstancce=null;
    private static int quelleInstance=0;
   /**
    * 
    * @return un Objet type Database
    */
   public static database getInstance(){
          if(dbInstancce==null){
           //  if(con!=null){
          dbInstancce=new database();
           //   }
          }
          return dbInstancce;
        }
   private database() {
         try {
             database.con=CreateConnection();
            
         } catch (SQLException ex) {
            Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
       
      
        /**
         * cette methode permet de creer la connection et donner 
         * un objet de type Connection
         * @return Un objet de type Connection
         * @throws SQLException 
         * 
         */
        public Connection CreateConnection() throws SQLException{
         try {
             Class.forName("com.mysql.jdbc.Driver");      
             con=DriverManager.getConnection(url,login,pass);
             System.out.println("ggdgd");
            } catch (ClassNotFoundException ex) {
           //  Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
              System.out.println("connection  ");
                // con=DriverManager.getConnection(urlLocale,loginLocale,passLocale);
         }
         finally
     {
           if(con==null)
             {
                 System.out.println("PROBLEME DE CONNECTION AU SERVEUR  VIA INTERNET");
        con=DriverManager.getConnection(urlLocale,loginLocale,passLocale);
        quelleInstance=1;
             }
     }
        return con;
}
          /**
           * Cette Methode Permet de faire les selections
           * dans n'importe quel table en lui donnant en parametre la requete
           * @param sql 
           * @return Un Un objet de type ResultSet
           * @throws SQLException 
           */
         public  ResultSet selectInTab(String sql) throws SQLException
         {
             ResultSet res=null;
           state=con.createStatement();
             res=state.executeQuery(sql);
             return res;
         }
         /**
          * methode permettant d'avoir le nombre de ligne d'une requete
          * @param sql
          * @return un Entier qui est le nombre de ligne d'une requete
          * @throws SQLException 
          */
      public int nbreLigneRequete(String sql) throws SQLException{
           ResultSet res=null;
          state=con.createStatement();
             res=state.executeQuery(sql);
           int nbre=0;
          
       if (res!=null) { 
        res.last(); 
       nbre = res.getRow(); 
         res.beforeFirst(); 
  } 
        return nbre;
      }
        public int nbreLigneRequete(String sql,int k) {
           ResultSet res=null;
         int nbres=-1;
         try {
        res=database.getInstance().selectInTab(sql);
               nbres=0;
      while(res.next())
      {
          System.out.println("fffss: "+res.getString("designation"));
          nbres=nbres+1;
          System.out.println("nbre est : "+nbres);
      }
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
          System.out.println("nbre est fin : "+nbres);
        return nbres;
      }
      
          public boolean fermerConnection(){
               boolean t=false;
         try {
             this.con.close();
             this.state.close();
             t=true;
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
            return t;
          }
          public  boolean InserData(String sql)
          {
                boolean estOk=false;
                int nbreExution=0;
         try {
              state=con.createStatement();
             nbreExution=state.executeUpdate(sql);
             if(nbreExution>0){
                estOk=true;
             }
         } catch (SQLException ex) {
             Logger.getLogger(database.class.getName()).log(Level.SEVERE, null, ex);
         }
           
                return estOk;
          }

    public Connection getCon() {
        return con;
    }

    public static int getQuelleInstance() {
        return quelleInstance;
    }
             
          
}
