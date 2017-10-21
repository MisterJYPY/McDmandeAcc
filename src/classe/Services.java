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
    @NamedQuery(name = "Services.findAll", query = "SELECT s FROM Services s")})
public class Services implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 120)
    private String designation;
    @Basic(optional = false)
    @Column(nullable = false, length = 6)
    private String abreviation;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Collection<Personnel> personnelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private Collection<Demandeachat> demandeachatCollection;

    public Services() {
    }

    public Services(Integer id) {
        this.id = id;
    }

    public Services(Integer id, String designation, String abreviation) {
        this.id = id;
        this.designation = designation;
        this.abreviation = abreviation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation = abreviation;
    }

    @XmlTransient
    public Collection<Personnel> getPersonnelCollection() {
        return personnelCollection;
    }

    public void setPersonnelCollection(Collection<Personnel> personnelCollection) {
        this.personnelCollection = personnelCollection;
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
        if (!(object instanceof Services)) {
            return false;
        }
        Services other = (Services) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "classe.Services[ id=" + id + " ]";
    }
    
}
