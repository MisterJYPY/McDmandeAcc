/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mr_JYPY
 */
@Entity
@Table(catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cotation.findAll", query = "SELECT c FROM Cotation c")})
public class Cotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code_cotation", nullable = false, length = 255)
    private String codeCotation;
    @Basic(optional = false)
    @Column(name = "date_envoi", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;
    @Basic(optional = false)
    @Column(name = "delai_reponse", nullable = false)
    private int delaiReponse;
    @Basic(optional = false)
    @Column(nullable = false, length = 3)
    private String statut;
    @JoinColumn(name = "demandeAchat", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Demandeachat demandeAchat;
    @JoinColumn(name = "expediteur", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Personnel expediteur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cotation")
    private Collection<Fournisseursurcotation> fournisseursurcotationCollection;

    public Cotation() {
    }

    public Cotation(Integer id) {
        this.id = id;
    }

    public Cotation(String codeCotation, Date dateEnvoi, int delaiReponse, String statut) {
        this.codeCotation = codeCotation;
        this.dateEnvoi = dateEnvoi;
        this.delaiReponse = delaiReponse;
        this.statut = statut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeCotation() {
        return codeCotation;
    }

    public void setCodeCotation(String codeCotation) {
        this.codeCotation = codeCotation;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public int getDelaiReponse() {
        return delaiReponse;
    }

    public void setDelaiReponse(int delaiReponse) {
        this.delaiReponse = delaiReponse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Demandeachat getDemandeAchat() {
        return demandeAchat;
    }

    public void setDemandeAchat(Demandeachat demandeAchat) {
        this.demandeAchat = demandeAchat;
    }

    public Personnel getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(Personnel expediteur) {
        this.expediteur = expediteur;
    }

    @XmlTransient
    public Collection<Fournisseursurcotation> getFournisseursurcotationCollection() {
        return fournisseursurcotationCollection;
    }

    public void setFournisseursurcotationCollection(Collection<Fournisseursurcotation> fournisseursurcotationCollection) {
        this.fournisseursurcotationCollection = fournisseursurcotationCollection;
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
        if (!(object instanceof Cotation)) {
            return false;
        }
        Cotation other = (Cotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Cotation[ id=" + id + " ]";
    }
    
}
