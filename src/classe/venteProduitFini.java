/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.sql.Date;

/**
 *
 * @author Mr_JYPY
 */
public class venteProduitFini extends EntreeProduit{
    
      private Personnel rdi;
       private clients  client;

    public venteProduitFini(Personnel rdi, clients client) {
        this.rdi = rdi;
        this.client = client;
    }

    public venteProduitFini(Personnel rdi, clients client, String libelle, int quantite, Date date, String type, Magasin magasin, Articles article, int stocsav, int stocksApres) {
        super(libelle, quantite, date, type, magasin, article, stocsav, stocksApres);
        this.rdi = rdi;
        this.client = client;
    }

    public venteProduitFini() {
    }
          
         

    public Personnel getRdi() {
        return rdi;
    }

    public void setRdi(Personnel rdi) {
        this.rdi = rdi;
    }

    public clients getClient() {
        return client;
    }

    public void setClient(clients client) {
        this.client = client;
    }
       
       
       
       
}
