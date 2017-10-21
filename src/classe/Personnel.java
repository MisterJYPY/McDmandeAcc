/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.io.Serializable;
import java.util.Collection;
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
    @NamedQuery(name = "Personnel.findAll", query = "SELECT p FROM Personnel p")})
public class Personnel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 70)
    private String nom;
    @Basic(optional = false)
    @Column(nullable = false, length = 120)
    private String prenom;
    @Basic(optional = false)
    @Column(nullable = false, length = 120)
    private String adresse;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String fonction;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String contacts;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String mail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personnel")
    private Collection<SignataireApprober> signataireApproberCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personnel")
    private Collection<ComptesPersonnel> comptesPersonnelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expediteur")
    private Collection<Cotation> cotationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "signataire")
    private Collection<SuiviValidation> suiviValidationCollection;
    @JoinColumn(name = "service", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Services service;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeur")
    private Collection<Demandeachat> demandeachatCollection;

    public Personnel() {
    }

    public Personnel(Integer id) {
        this.id = id;
    }

    public Personnel(Integer id, String nom, String prenom, String adresse, String fonction, String contacts, String mail) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.fonction = fonction;
        this.contacts = contacts;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @XmlTransient
    public Collection<SignataireApprober> getSignataireApproberCollection() {
        return signataireApproberCollection;
    }

    public void setSignataireApproberCollection(Collection<SignataireApprober> signataireApproberCollection) {
        this.signataireApproberCollection = signataireApproberCollection;
    }

    @XmlTransient
    public Collection<ComptesPersonnel> getComptesPersonnelCollection() {
        return comptesPersonnelCollection;
    }

    public void setComptesPersonnelCollection(Collection<ComptesPersonnel> comptesPersonnelCollection) {
        this.comptesPersonnelCollection = comptesPersonnelCollection;
    }

    @XmlTransient
    public Collection<Cotation> getCotationCollection() {
        return cotationCollection;
    }

    public void setCotationCollection(Collection<Cotation> cotationCollection) {
        this.cotationCollection = cotationCollection;
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

    @XmlTransient
    public Collection<Demandeachat> getDemandeachatCollection() {
        return demandeachatCollection;
    }

    public void setDemandeachatCollection(Collection<Demandeachat> demandeachatCollection) {
        this.demandeachatCollection = demandeachatCollection;
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
        if (!(object instanceof Personnel)) {
            return false;
        }
        Personnel other = (Personnel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Personnel[ id=" + id + " ]";
    }
    
}
