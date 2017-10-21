/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Mr_JYPY
 */
@Embeddable
public class SignataireApproberPK implements Serializable {
    @Basic(optional = false)
    @Column(nullable = false)
    private int id;
    @Basic(optional = false)
    @Column(nullable = false)
    private int signataire;

    public SignataireApproberPK() {
    }

    public SignataireApproberPK(int id, int signataire) {
        this.id = id;
        this.signataire = signataire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSignataire() {
        return signataire;
    }

    public void setSignataire(int signataire) {
        this.signataire = signataire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) signataire;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SignataireApproberPK)) {
            return false;
        }
        SignataireApproberPK other = (SignataireApproberPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.signataire != other.signataire) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.SignataireApproberPK[ id=" + id + ", signataire=" + signataire + " ]";
    }
    
}
