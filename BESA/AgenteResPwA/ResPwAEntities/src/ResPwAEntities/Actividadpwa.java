/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "actividad_pwa", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadPwa.findAll", query = "SELECT a FROM ActividadPwa a"),
    @NamedQuery(name = "ActividadPwa.findById", query = "SELECT a FROM ActividadPwa a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadPwa.findByNombre", query = "SELECT a FROM ActividadPwa a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "ActividadPwa.findByTipo", query = "SELECT a FROM ActividadPwa a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "ActividadPwa.findByDuracion", query = "SELECT a FROM ActividadPwa a WHERE a.duracion = :duracion")})
public class ActividadPwa implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "duracion")
    private BigDecimal duracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividadPwa")
    private List<RegistroActividad> registroActividadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actividadPwa")
    private List<ActXPreferencia> actXPreferenciaList;

    public ActividadPwa() {
    }

    public ActividadPwa(BigDecimal id) {
        this.id = id;
    }

    public ActividadPwa(BigDecimal id, String nombre, String tipo, BigDecimal duracion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.duracion = duracion;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getDuracion() {
        return duracion;
    }

    public void setDuracion(BigDecimal duracion) {
        this.duracion = duracion;
    }

    @XmlTransient
    public List<RegistroActividad> getRegistroActividadList() {
        return registroActividadList;
    }

    public void setRegistroActividadList(List<RegistroActividad> registroActividadList) {
        this.registroActividadList = registroActividadList;
    }

    @XmlTransient
    public List<ActXPreferencia> getActXPreferenciaList() {
        return actXPreferenciaList;
    }

    public void setActXPreferenciaList(List<ActXPreferencia> actXPreferenciaList) {
        this.actXPreferenciaList = actXPreferenciaList;
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
        if (!(object instanceof ActividadPwa)) {
            return false;
        }
        ActividadPwa other = (ActividadPwa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.ActividadPwa[ id=" + id + " ]";
    }
    
}
