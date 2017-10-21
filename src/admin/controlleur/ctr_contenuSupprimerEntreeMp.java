/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuSupprimerEntreeMp;
import admin.vue.contenuSupprimerEntreeMp;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuSupprimerEntreeMp {
    
    private contenuSupprimerEntreeMp vue;
    private md_contenuSupprimerEntreeMp model;

    public ctr_contenuSupprimerEntreeMp(contenuSupprimerEntreeMp vue, md_contenuSupprimerEntreeMp model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuSupprimerEntreeMp(contenuSupprimerEntreeMp vue) {
        this.vue = vue;
    }

    public ctr_contenuSupprimerEntreeMp() {
    }
    
    
}
