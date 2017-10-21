/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuModifierEntreePf;
import admin.vue.contenuModifierEntreePf;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuModifierEntreePf {
    
    
    private contenuModifierEntreePf vue;
    private md_contenuModifierEntreePf model;

    public ctr_contenuModifierEntreePf(contenuModifierEntreePf vue, md_contenuModifierEntreePf model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuModifierEntreePf(contenuModifierEntreePf vue) {
        this.vue = vue;
    }

    public ctr_contenuModifierEntreePf() {
    }
    
    
}
