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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "suivi_validation", catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuiviValidation.findAll", query = "SELECT s FROM SuiviValidation s")})
public class SuiviValidation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date_envoi", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateEnvoi;
    @Basic(optional = false)
    @Column(name = "derniere_modif", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date derniereModif;
    @Basic(optional = false)
    @Column(nullable = false, length = 3)
    private String estSigne;
    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private String type;
    @JoinColumn(name = "demandeAchat", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Demandeachat demandeAchat;
    @JoinColumn(name = "signataire", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Personnel signataire;

    public SuiviValidation() {
    }

    public SuiviValidation(Integer id) {
        this.id = id;
    }

    public SuiviValidation(Integer id, Date dateEnvoi, Date derniereModif, String estSigne, String type) {
        this.id = id;
        this.dateEnvoi = dateEnvoi;
        this.derniereModif = derniereModif;
        this.estSigne = estSigne;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    public String getEstSigne() {
        return estSigne;
    }

    public void setEstSigne(String estSigne) {
        this.estSigne = estSigne;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Demandeachat getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(Demandeachat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Personnel getSignataire() {
        return signataire;
    }

    public void setSignataire(Personnel signataire) {
        this.signataire = signataire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuiviValidation)) {
            return false;
        }
        SuiviValidation other = (SuiviValidation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.SuiviValidation[ id=" + id + " ]";
    }
    
}
