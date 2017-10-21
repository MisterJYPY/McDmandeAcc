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
import javax.persistence.Lob;
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
@Table(catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fournisseursurcotation.findAll", query = "SELECT f FROM Fournisseursurcotation f")})
public class Fournisseursurcotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(nullable = false, length = 125)
    private String validite;
    @Basic(optional = false)
    @Lob
    @Column(name = "delai_livraison", nullable = false, length = 65535)
    private String delaiLivraison;
    @Basic(optional = false)
    @Column(nullable = false, length = 125)
    private String receptioniste;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String reglement;
    @JoinColumn(name = "fournisseur", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Fournisseur fournisseur;
    @JoinColumn(name = "cotation", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Cotation cotation;

    public Fournisseursurcotation() {
    }

    public Fournisseursurcotation(Integer id) {
        this.id = id;
    }

    public Fournisseursurcotation(Integer id, Date date, String validite, String delaiLivraison, String receptioniste, String reglement) {
        this.id = id;
        this.date = date;
        this.validite = validite;
        this.delaiLivraison = delaiLivraison;
        this.receptioniste = receptioniste;
        this.reglement = reglement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getValidite() {
        return validite;
    }

    public void setValidite(String validite) {
        this.validite = validite;
    }

    public String getDelaiLivraison() {
        return delaiLivraison;
    }

    public void setDelaiLivraison(String delaiLivraison) {
        this.delaiLivraison = delaiLivraison;
    }

    public String getReceptioniste() {
        return receptioniste;
    }

    public void setReceptioniste(String receptioniste) {
        this.receptioniste = receptioniste;
    }

    public String getReglement() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement = reglement;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Cotation getCotation() {
        return cotation;
    }

    public void setCotation(Cotation cotation) {
        this.cotation = cotation;
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
        if (!(object instanceof Fournisseursurcotation)) {
            return false;
        }
        Fournisseursurcotation other = (Fournisseursurcotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Fournisseursurcotation[ id=" + id + " ]";
    }
    
}
