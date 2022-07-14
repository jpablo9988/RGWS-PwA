/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiar.findAll", query = "SELECT f FROM Familiar f")
    , @NamedQuery(name = "Familiar.findById", query = "SELECT f FROM Familiar f WHERE f.id = :id")
    , @NamedQuery(name = "Familiar.findByNombre", query = "SELECT f FROM Familiar f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "Familiar.findByParentesco", query = "SELECT f FROM Familiar f WHERE f.parentesco = :parentesco")
    , @NamedQuery(name = "Familiar.findByNteres", query = "SELECT f FROM Familiar f WHERE f.nteres = :nteres")
    , @NamedQuery(name = "Familiar.findByNacimiento", query = "SELECT f FROM Familiar f WHERE f.nacimiento = :nacimiento")
    , @NamedQuery(name = "Familiar.findByEstavivo", query = "SELECT f FROM Familiar f WHERE f.estavivo = :estavivo")})
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(nullable = false, precision = 131089)
    private BigDecimal id;
    @Column(length = 2147483647)
    private String nombre;
    @Column(length = 2147483647)
    private String parentesco;
    @Column(precision = 17, scale = 17)
    private Double nteres;
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
    private Character estavivo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "familiarId2")
    private List<Familiares> familiaresList;

    public Familiar() {
    }

    public Familiar(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public Double getNteres() {
        return nteres;
    }

    public void setNteres(Double nteres) {
        this.nteres = nteres;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Character getEstavivo() {
        return estavivo;
    }

    public void setEstavivo(Character estavivo) {
        this.estavivo = estavivo;
    }

    @XmlTransient
    public List<Familiares> getFamiliaresList() {
        return familiaresList;
    }

    public void setFamiliaresList(List<Familiares> familiaresList) {
        this.familiaresList = familiaresList;
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
        if (!(object instanceof Familiar)) {
            return false;
        }
        Familiar other = (Familiar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Familiar[ id=" + id + " ]";
    }
    
}
