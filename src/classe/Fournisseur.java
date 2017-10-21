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
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f")})
public class Fournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String nom;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String mail;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String contacts;
    @Basic(optional = false)
    @Column(nullable = false, length = 125)
    private String telecopie;
    @Basic(optional = false)
    @Column(name = "derniere_modif", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date derniereModif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fournisseur")
    private Collection<Fournisseursurcotation> fournisseursurcotationCollection;

    public Fournisseur() {
    }

    public Fournisseur(Integer id) {
        this.id = id;
    }

    public Fournisseur(Integer id, String nom, String mail, String contacts, String telecopie, Date derniereModif) {
        this.id = id;
        this.nom = nom;
        this.mail = mail;
        this.contacts = contacts;
        this.telecopie = telecopie;
        this.derniereModif = derniereModif;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTelecopie() {
        return telecopie;
    }

    public void setTelecopie(String telecopie) {
        this.telecopie = telecopie;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
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
        if (!(object instanceof Fournisseur)) {
            return false;
        }
        Fournisseur other = (Fournisseur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Fournisseur[ id=" + id + " ]";
    }
    
}
