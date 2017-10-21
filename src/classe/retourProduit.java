/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

/**
 *
 * @author Mr_JYPY
 */
public class retourProduit extends EntreeProduit{
    
    private Personnel rdi;

    public retourProduit(Personnel rdi) {
        this.rdi = rdi;
    }

    public retourProduit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Personnel getRdi() {
        return rdi;
    }

    public void setRdi(Personnel rdi) {
        this.rdi = rdi;
    }
    
     
}
