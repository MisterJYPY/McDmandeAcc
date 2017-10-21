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
    @NamedQuery(name = "Articles.findAll", query = "SELECT a FROM Articles a")})
public class Articles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String code;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String designation;
    @Basic(optional = false)
    @Column(name = "derniere_modif", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date derniereModif;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String unite;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "article")
    private Collection<Articlesdemandeachat> articlesdemandeachatCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "articles")
    private Stocks stocks;
    @JoinColumn(name = "famille", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Famille famille;

    public Articles() {
    }

    public Articles(Integer id) {
        this.id = id;
    }

    public Articles(Integer id,String designation,Famille famille, Stocks st) {
        this.id = id;
        this.famille= famille;
        this.designation = designation;
        this.stocks=st;
        
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

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    @XmlTransient
    public Collection<Articlesdemandeachat> getArticlesdemandeachatCollection() {
        return articlesdemandeachatCollection;
    }

    public void setArticlesdemandeachatCollection(Collection<Articlesdemandeachat> articlesdemandeachatCollection) {
        this.articlesdemandeachatCollection = articlesdemandeachatCollection;
    }

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

  

    public Famille getFamille() {
        return famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
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
        if (!(object instanceof Articles)) {
            return false;
        }
        Articles other = (Articles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Articles[ id=" + id + " ]";
    }
    
}
