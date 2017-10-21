/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import classe.Articles;
import classe.Fournisseursurcotation;
import classe.Magasin;
import classe.EntreeProduit;
import java.util.Collection;

/**
 *
 * @author Mr_JYPY
 */
public class md_article {
    
    
    //avoir le stocks general de l'article dans l'entrepot
    public int GetStocks(Articles article){
       int st=0;
       return st;
    }
    //
    public int GetStocksMagasin(Magasin mag,Articles art){
        int st=0;
       return st;
    }
   
   public Collection<EntreeProduit> listeEntreeAricle(Articles article) {
      Collection<EntreeProduit > articles = null;
       return articles;
    }
  public Collection<EntreeProduit> listeEntreeMagasin(Articles article,Magasin magasin) {
      Collection<EntreeProduit> articles = null;
       return articles;
    }
   public Collection<EntreeProduit> listeSortieMagasin(Articles article,Magasin magasin) {
      Collection<EntreeProduit> articles = null;
       return articles;
    }
    
}
