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
    @NamedQuery(name = "Demandeachat.findAll", query = "SELECT d FROM Demandeachat d")})
public class Demandeachat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String bordereaux;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInsertion;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeAchat")
    private Collection<SignataireApprober> signataireApproberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeAchat")
    private Collection<Cotation> cotationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeAchat")
    private Collection<Articlesdemandeachat> articlesdemandeachatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeAchat")
    private Collection<SuiviValidation> suiviValidationCollection;
    @JoinColumn(name = "service", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Services service;
    @JoinColumn(name = "demandeur", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Personnel demandeur;

    public Demandeachat() {
    }

    public Demandeachat(Integer id) {
        this.id = id;
    }

    public Demandeachat(Integer id, String bordereaux, Date dateInsertion, Date date) {
        this.id = id;
        this.bordereaux = bordereaux;
        this.dateInsertion = dateInsertion;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBordereaux() {
        return bordereaux;
    }

    public void setBordereaux(String bordereaux) {
        this.bordereaux = bordereaux;
    }

    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlTransient
    public Collection<SignataireApprober> getSignataireApproberCollection() {
        return signataireApproberCollection;
    }

    public void setSignataireApproberCollection(Collection<SignataireApprober> signataireApproberCollection) {
        this.signataireApproberCollection = signataireApproberCollection;
    }

    @XmlTransient
    public Collection<Cotation> getCotationCollection() {
        return cotationCollection;
    }

    public void setCotationCollection(Collection<Cotation> cotationCollection) {
        this.cotationCollection = cotationCollection;
    }

    @XmlTransient
    public Collection<Articlesdemandeachat> getArticlesdemandeachatCollection() {
        return articlesdemandeachatCollection;
    }

    public void setArticlesdemandeachatCollection(Collection<Articlesdemandeachat> articlesdemandeachatCollection) {
        this.articlesdemandeachatCollection = articlesdemandeachatCollection;
    }

    @XmlTransient
    public Collection<SuiviValidation> getSuiviValidationCollection() {
        return suiviValidationCollection;
    }

    public void setSuiviValidationCollection(Collection<SuiviValidation> suiviValidationCollection) {
        this.suiviValidationCollection = suiviValidationCollection;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Personnel getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Personnel demandeur) {
        this.demandeur = demandeur;
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
        if (!(object instanceof Demandeachat)) {
            return false;
        }
        Demandeachat other = (Demandeachat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Demandeachat[ id=" + id + " ]";
    }
    
}
