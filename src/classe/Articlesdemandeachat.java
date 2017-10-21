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
@Table(catalog = "mccroftbd", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articlesdemandeachat.findAll", query = "SELECT a FROM Articlesdemandeachat a")})
public class Articlesdemandeachat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String justificatif;
    @Basic(optional = false)
    @Column(nullable = false, length = 255)
    private String observation;
    @Basic(optional = false)
    @Column(nullable = false)
    private int quantiter;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInsertion;
    @JoinColumn(name = "article", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Articles article;
    @JoinColumn(name = "demandeAchat", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Demandeachat demandeAchat;

    public Articlesdemandeachat() {
    }

    public Articlesdemandeachat(Integer id) {
        this.id = id;
    }

    public Articlesdemandeachat(Integer id, String justificatif, String observation, int quantiter, Date dateInsertion) {
        this.id = id;
        this.justificatif = justificatif;
        this.observation = observation;
        this.quantiter = quantiter;
        this.dateInsertion = dateInsertion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJustificatif() {
        return justificatif;
    }

    public void setJustificatif(String justificatif) {
        this.justificatif = justificatif;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getQuantiter() {
        return quantiter;
    }

    public void setQuantiter(int quantiter) {
        this.quantiter = quantiter;
    }

    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articlesdemandeachat)) {
            return false;
        }
        Articlesdemandeachat other = (Articlesdemandeachat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Articlesdemandeachat[ id=" + id + " ]";
    }
    
}
