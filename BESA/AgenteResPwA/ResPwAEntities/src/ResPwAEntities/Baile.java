/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author USER
 */
@Entity
@Table(name = "baile", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Baile.findAll", query = "SELECT b FROM Baile b"),
    @NamedQuery(name = "Baile.findById", query = "SELECT b FROM Baile b WHERE b.id = :id"),
    @NamedQuery(name = "Baile.findByNombre", query = "SELECT b FROM Baile b WHERE b.nombre = :nombre")})
public class Baile implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @JoinColumn(name = "genero", referencedColumnName = "genero")
    @ManyToOne(optional = false)
    private Genero genero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "baile")
    private List<PreferenciaXBaile> preferenciaXBaileList;

    public Baile() {
    }

    public Baile(BigDecimal id) {
        this.id = id;
    }

    public Baile(BigDecimal id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @XmlTransient
    public List<PreferenciaXBaile> getPreferenciaXBaileList() {
        return preferenciaXBaileList;
    }

    public void setPreferenciaXBaileList(List<PreferenciaXBaile> preferenciaXBaileList) {
        this.preferenciaXBaileList = preferenciaXBaileList;
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
        if (!(object instanceof Baile)) {
            return false;
        }
        Baile other = (Baile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Baile[ id=" + id + " ]";
    }
    
}
