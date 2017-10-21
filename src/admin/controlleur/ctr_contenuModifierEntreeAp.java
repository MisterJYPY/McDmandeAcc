/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuModifierEntreeAp;
import admin.vue.contenuModifierEntreeAp;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuModifierEntreeAp {
    
    private contenuModifierEntreeAp vue;
    private md_contenuModifierEntreeAp model;

    public ctr_contenuModifierEntreeAp(contenuModifierEntreeAp vue, md_contenuModifierEntreeAp model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuModifierEntreeAp(contenuModifierEntreeAp vue) {
        this.vue = vue;
    }

    public ctr_contenuModifierEntreeAp() {
    }
   
}
