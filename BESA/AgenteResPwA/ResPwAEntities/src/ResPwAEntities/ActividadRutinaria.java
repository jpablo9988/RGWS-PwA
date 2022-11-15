/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @author tesispepper
 */
@Entity
@Table(name = "actividad_rutinaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActividadRutinaria.findAll", query = "SELECT a FROM ActividadRutinaria a"),
    @NamedQuery(name = "ActividadRutinaria.findById", query = "SELECT a FROM ActividadRutinaria a WHERE a.id = :id"),
    @NamedQuery(name = "ActividadRutinaria.findByNombre", query = "SELECT a FROM ActividadRutinaria a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "ActividadRutinaria.findByDuracion", query = "SELECT a FROM ActividadRutinaria a WHERE a.duracion = :duracion"),
    @NamedQuery(name = "ActividadRutinaria.findByHora", query = "SELECT a FROM ActividadRutinaria a WHERE a.hora = :hora")})
public class ActividadRutinaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "duracion")
    private double duracion;
    @Basic(optional = false)
    @Column(name = "hora")
    @Temporal(TemporalType.DATE)
    private Date hora;
    @JoinColumn(name = "medico_pwa_cedula", referencedColumnName = "perfil_pwa_cedula")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilMedico medicoPwaCedula;

    public ActividadRutinaria() {
    }

    public ActividadRutinaria(Integer id) {
        this.id = id;
    }

    public ActividadRutinaria(Integer id, String nombre, double duracion, Date hora) {
        this.id = id;
        this.nombre = nombre;
        this.duracion = duracion;
        this.hora = hora;
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

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public PerfilMedico getMedicoPwaCedula() {
        return medicoPwaCedula;
    }

    public void setMedicoPwaCedula(PerfilMedico medicoPwaCedula) {
        this.medicoPwaCedula = medicoPwaCedula;
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
        if (!(object instanceof ActividadRutinaria)) {
            return false;
        }
        ActividadRutinaria other = (ActividadRutinaria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.ActividadRutinaria[ id=" + id + " ]";
    }
    
}
