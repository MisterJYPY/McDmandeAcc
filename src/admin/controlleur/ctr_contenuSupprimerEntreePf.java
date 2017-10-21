/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuSupprimerEntreePf;
import admin.vue.contenuSupprimerEntreePf;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuSupprimerEntreePf {
    
    private contenuSupprimerEntreePf vue;
    private md_contenuSupprimerEntreePf model;

    public ctr_contenuSupprimerEntreePf(contenuSupprimerEntreePf vue, md_contenuSupprimerEntreePf model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuSupprimerEntreePf(contenuSupprimerEntreePf vue) {
        this.vue = vue;
    }

    public ctr_contenuSupprimerEntreePf() {
    }
    
    
}
