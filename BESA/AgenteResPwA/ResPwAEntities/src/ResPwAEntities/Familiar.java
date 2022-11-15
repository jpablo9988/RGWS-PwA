/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "familiar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiar.findAll", query = "SELECT f FROM Familiar f"),
    @NamedQuery(name = "Familiar.findById", query = "SELECT f FROM Familiar f WHERE f.id = :id"),
    @NamedQuery(name = "Familiar.findByNombre", query = "SELECT f FROM Familiar f WHERE f.nombre = :nombre"),
    @NamedQuery(name = "Familiar.findByParentesco", query = "SELECT f FROM Familiar f WHERE f.parentesco = :parentesco"),
    @NamedQuery(name = "Familiar.findByInteres", query = "SELECT f FROM Familiar f WHERE f.interes = :interes"),
    @NamedQuery(name = "Familiar.findByEstaVivo", query = "SELECT f FROM Familiar f WHERE f.estaVivo = :estaVivo"),
    @NamedQuery(name = "Familiar.findByNacimiento", query = "SELECT f FROM Familiar f WHERE f.nacimiento = :nacimiento")})
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "parentesco")
    private String parentesco;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "interes")
    private Double interes;
    @Column(name = "esta_vivo")
    private Boolean estaVivo;
    @Column(name = "nacimiento")
    @Temporal(TemporalType.DATE)
    private Date nacimiento;
    @JoinTable(name = "familiares", joinColumns = {
        @JoinColumn(name = "familiar_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "perfil_pwa_cedula", referencedColumnName = "cedula")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilPwa> perfilPwaList;

    public Familiar() {
    }

    public Familiar(Integer id) {
        this.id = id;
    }

    public Familiar(Integer id, String nombre, String parentesco) {
        this.id = id;
        this.nombre = nombre;
        this.parentesco = parentesco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public Boolean getEstaVivo() {
        return estaVivo;
    }

    public void setEstaVivo(Boolean estaVivo) {
        this.estaVivo = estaVivo;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    @XmlTransient
    public List<PerfilPwa> getPerfilPwaList() {
        return perfilPwaList;
    }

    public void setPerfilPwaList(List<PerfilPwa> perfilPwaList) {
        this.perfilPwaList = perfilPwaList;
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
