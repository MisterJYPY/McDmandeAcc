/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.controlleur;

import classe.MD5Password;
import admin.vue.adminSecure;
import admin.vue.contenuAdmin;
import vue.principal;
/**
 *
 * @author Mr_JYPY
 */
public class ctr_adminSecure {
    private final adminSecure vue;
    private final String loginAdmin="admin";
    private final String passAdmin="09978a9b4011843a2243818c839a0887";
    /**
     * @param vues 
     */
    public ctr_adminSecure(adminSecure vues) {
          this.vue = vues;
          this.vue.getLoginAdmin().setText("");
          this.vue.getPassAdmin().setText("");
          vue.getError().setText("");
        //ecoute du click sur le bouton valider de adminSecure
          this.vue.getValider().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               //le code ici pour gerer le click et comparer avec le md5()
               TraiterBtnValider();
            }
        });
    }
    
   private void TraiterBtnValider()
   {
               
                char[] pass=vue.getPassAdmin().getPassword();
                  String motDePassNonCrypter=vue.getPassAdmin().getText();
                  String motDePassCrypter=MD5Password.getEncodedPassword(motDePassNonCrypter);
                  String login=vue.getLoginAdmin().getText();
                 // System.out.println(Arrays.toString(pass));
                         if(login.isEmpty() || pass.length==0){                       
                 vue.getError().setText(" !!!!!!!!!!!!!Error ,Aucun champ ne doit etre Vide !! ");
                         }
                         else{
                    if(passAdmin.equals(motDePassCrypter) && loginAdmin.equals(login))
                     {
                 adminSecure.getInstance().setVisible(false);
            contenuAdmin vueAdmin=contenuAdmin.getInstance();
             principal.placerPanel(vueAdmin,adminSecure.getInstance().getPcp().getScropMilieu());
                     }
                    else{
                 vue.getError().setText(" !!!!!!!!!!!!!Error ,Identifiants Incorrects  !!!! ");   
                    }
                         
                         }
   
   }
   
   public static String renvoyerEnChaine(char [] txt)
   {
        String mot = null;
        
       for(int i=0 ; i<txt.length;i++)
       {    
       }
        
        return mot;
   
   }
}
