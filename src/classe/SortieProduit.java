/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *la classe qui gere toutes les sorties de
 * produit
 * @author Mr_JYPY
 */
public class SortieProduit {
       
      /**
       * id de la sortie
       */
    private Integer id; 
        /**
         * objet de type article
         * pointant sur l'article 
         */
    private Articles article;
       /**
        * larticle du produit
        */
    private String libelle;
   
    private int quantite;
   
    private java.sql.Date date;
    
  
    private Magasin magasin;
    private java.sql.Timestamp derniereModif;
    private int stocks_avant ;
    private int stocks_apres ;
    
     private String DateString;

    public SortieProduit() {
    }

    public SortieProduit(Integer id) {
        this.id = id;
    }

    public SortieProduit(Integer id, Articles article, Magasin magasin) {
        this.id = id;
        this.article = article;
        this.magasin = magasin;
    }

    public SortieProduit(Integer id, Articles article, String libelle, int quantite, Date date, Magasin magasin, Timestamp derniereModif, int stocks_avant, int stocks_apres, String DateString) {
        this.id = id;
        this.article = article;
        this.libelle = libelle;
        this.quantite = quantite;
        this.date = date;
        this.magasin = magasin;
        this.derniereModif = derniereModif;
        this.stocks_avant = stocks_avant;
        this.stocks_apres = stocks_apres;
        this.DateString = DateString;
    }
       

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Timestamp getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Timestamp derniereModif) {
        this.derniereModif = derniereModif;
    }

    public int getStocks_avant() {
        return stocks_avant;
    }

    public void setStocks_avant(int stocks_avant) {
        this.stocks_avant = stocks_avant;
    }

    public int getStocks_apres() {
        return stocks_apres;
    }

    public void setStocks_apres(int stocks_apres) {
        this.stocks_apres = stocks_apres;
    }

    public String getDateString() {
        return DateString;
    }

    public void setDateString(String DateString) {
        this.DateString = DateString;
    }
  
    
}
