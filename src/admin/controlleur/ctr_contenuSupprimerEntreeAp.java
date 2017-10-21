/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuSupprimerEntreeAp;
import admin.vue.contenuSupprimerEntreeAp;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuSupprimerEntreeAp {
    
    private contenuSupprimerEntreeAp vue;
    private md_contenuSupprimerEntreeAp model;

    public ctr_contenuSupprimerEntreeAp(contenuSupprimerEntreeAp vue, md_contenuSupprimerEntreeAp model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuSupprimerEntreeAp(contenuSupprimerEntreeAp vue) {
        this.vue = vue;
    }

    public ctr_contenuSupprimerEntreeAp() {
   
    }
}