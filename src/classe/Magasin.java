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
    @NamedQuery(name = "Magasin.findAll", query = "SELECT m FROM Magasin m")})
public class Magasin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String code;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String designation;
    @Basic(optional = false)
    @Column(name = "derniere_modif", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date derniereModif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "magasin")
    private Collection<StocksMagasin> stocksMagasinCollection;
    private Collection<Articles> Articles;
    public Magasin() {
    }

    public Magasin(Integer id) {
        this.id = id;
    }

    public Magasin(Integer id, String code, String designation, Date derniereModif) {
        this.id = id;
        this.code = code;
        this.designation = designation;
        this.derniereModif = derniereModif;
    }
 public Magasin(Integer id, String designation,String code) {
        this.id = id;
        this.designation = designation;
        this.code = code;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    @XmlTransient
    public Collection<StocksMagasin> getStocksMagasinCollection() {
        return stocksMagasinCollection;
    }

    public void setStocksMagasinCollection(Collection<StocksMagasin> stocksMagasinCollection) {
        this.stocksMagasinCollection = stocksMagasinCollection;
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
        if (!(object instanceof Magasin)) {
            return false;
        }
        Magasin other = (Magasin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Magasin[ id=" + id + " ]";
    }
    
}
