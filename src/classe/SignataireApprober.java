/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mr_JYPY
 */
@Entity
@Table(name = "signataire_approber", catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SignataireApprober.findAll", query = "SELECT s FROM SignataireApprober s")})
public class SignataireApprober implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SignataireApproberPK signataireApproberPK;
    @Basic(optional = false)
    @Column(name = "heure_signature", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureSignature;
    @JoinColumn(name = "signataire", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Personnel personnel;
    @JoinColumn(name = "demandeAchat", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Demandeachat demandeAchat;

    public SignataireApprober() {
    }

    public SignataireApprober(SignataireApproberPK signataireApproberPK) {
        this.signataireApproberPK = signataireApproberPK;
    }

    public SignataireApprober(SignataireApproberPK signataireApproberPK, Date heureSignature) {
        this.signataireApproberPK = signataireApproberPK;
        this.heureSignature = heureSignature;
    }

    public SignataireApprober(int id, int signataire) {
        this.signataireApproberPK = new SignataireApproberPK(id, signataire);
    }

    public SignataireApproberPK getSignataireApproberPK() {
        return signataireApproberPK;
    }

    public void setSignataireApproberPK(SignataireApproberPK signataireApproberPK) {
        this.signataireApproberPK = signataireApproberPK;
    }

    public Date getHeureSignature() {
        return heureSignature;
    }

    public void setHeureSignature(Date heureSignature) {
        this.heureSignature = heureSignature;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Demandeachat getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(Demandeachat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (signataireApproberPK != null ? signataireApproberPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SignataireApprober)) {
            return false;
        }
        SignataireApprober other = (SignataireApprober) object;
        if ((this.signataireApproberPK == null && other.signataireApproberPK != null) || (this.signataireApproberPK != null && !this.signataireApproberPK.equals(other.signataireApproberPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.SignataireApprober[ signataireApproberPK=" + signataireApproberPK + " ]";
    }
    
}
