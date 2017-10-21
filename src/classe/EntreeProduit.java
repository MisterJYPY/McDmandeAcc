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
@Table(name = "entree_produit", catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntreeProduit.findAll", query = "SELECT o FROM OperationProduit o")})
public class EntreeProduit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false)
    private Articles article;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String libelle;
    @Basic(optional = false)
    @Column(nullable = false)
    private int quantite;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private java.sql.Date date;
    @Basic(optional = false)
    @Column(nullable = false, length = 3)
    private String type;
    @Basic(optional = false)
    @Column(nullable = false)
    private Magasin magasin;
    @Basic(optional = false)
    @Column(name = "derniere_modif", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private java.sql.Timestamp derniereModif;
    private int stocks_avant ;
    private int stocks_apres ;
    private String numeroVehicule;
     private String DateString;
    private Demandeachat demandeachat;
     private Fournisseur fournisseur;
    public EntreeProduit() {
    }

    public EntreeProduit(Integer id) {
        this.id = id;
    }

    public EntreeProduit(Integer id, Articles article, String libelle, int quantite,java.sql.Date date, String type, Magasin magasin,int stocks_avant, int stocks_apres, String numeroVehicule, Demandeachat demandeachat) {
        this.id = id;
        this.article = article;
        this.libelle = libelle;
        this.quantite = quantite;
        this.date = date;
        this.type = type;
        this.magasin = magasin;
       // this.derniereModif = derniereModif;
        this.stocks_avant = stocks_avant;
        this.stocks_apres = stocks_apres;
        this.numeroVehicule = numeroVehicule;
        this.demandeachat = demandeachat;
    }

    public EntreeProduit( String libelle, int quantite,java.sql.Date date, String type,Magasin magasin,Articles article,int stocsav,int stocksApres) {
        this.article = article;
        this.libelle = libelle;
        this.quantite = quantite;
        this.date = date;
        this.type = type;
        this.magasin = magasin;
        this.stocks_avant =  stocsav;
        this.stocks_apres =  stocksApres;
       // this.derniereModif = derniereModif;
    }
public EntreeProduit( String libelle, int quantite,String type,Magasin magasin,Articles article,int stocsav,int stocksApres) {
        this.article = article;
        this.libelle = libelle;
        this.quantite = quantite;
     //   this.date = date;
        this.type = type;
        this.magasin = magasin;
        this.stocks_avant =  stocsav;
        this.stocks_apres =  stocksApres;
       // this.derniereModif = derniereModif;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
   
    public String getNumeroVehicule() {
        return numeroVehicule;
    }

    public String getDateString() {
        return DateString;
    }

    public void setDateString(String DateString) {
        this.DateString = DateString;
    }

    public void setNumeroVehicule(String numeroVehicule) {
        this.numeroVehicule = numeroVehicule;
    }

    public Demandeachat getDemandeachat() {
        return demandeachat;
    }

    public void setDemandeachat(Demandeachat demandeachat) {
        this.demandeachat = demandeachat;
    }
  
    public int getStocks_avant() {
        return stocks_avant;
    }

    public void setStocks_avant(int stocks_avant) {
        this.stocks_avant = stocks_avant;
    }

    public int getStocks_apres() {
        return stocks_apres;
    }

    public void setStocks_apres(int stocks_apres) {
        this.stocks_apres = stocks_apres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin= magasin;
    }

    public java.sql.Timestamp getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(java.sql.Timestamp derniereModif) {
        this.derniereModif = derniereModif;
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
        if (!(object instanceof EntreeProduit)) {
            return false;
        }
        EntreeProduit other = (EntreeProduit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.OperationProduit[ id=" + id + " ]";
    }
    
}
