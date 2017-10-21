/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import admin.model.md_contenuModifierEntreeMp;
import admin.vue.contenuModifierEntreeMp;

/**
 *
 * @author Mr_JYPY
 */
public class ctr_contenuModifierEntreeMp {
    
      private contenuModifierEntreeMp vue;
      private md_contenuModifierEntreeMp model;

    public ctr_contenuModifierEntreeMp(contenuModifierEntreeMp vue, md_contenuModifierEntreeMp model) {
        this.vue = vue;
        this.model = model;
    }

    public ctr_contenuModifierEntreeMp(contenuModifierEntreeMp vue) {
        this.vue = vue;
    }

    public ctr_contenuModifierEntreeMp() {
    }
      
      
}
