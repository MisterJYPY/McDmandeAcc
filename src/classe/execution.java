/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import controller.ctr_contenuEntreeMatierePremiere;
import javax.swing.JTable;

/**
 *
 * @author Mr_JYPY
 */
public class execution extends Thread{
    
    JTable table;
    String entete;
    String pieds;
    public static int nbreInstance=0;

    public execution(JTable table, String entete, String pieds) {
        this.table = table;
        this.entete = entete;
        this.pieds = pieds;
        nbreInstance++;
    }
    
    @Override
    public void run()
     {
        ctr_contenuEntreeMatierePremiere.impressionJtable(table, entete, pieds);
        //si l'impression a ete faite on decremente le compteur
        if(nbreInstance>=1)
        {
        nbreInstance=nbreInstance-1;
        }
    }
    
}

