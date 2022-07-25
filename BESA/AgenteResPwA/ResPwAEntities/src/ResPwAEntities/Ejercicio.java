/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "ejercicio", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ejercicio.findAll", query = "SELECT e FROM Ejercicio e"),
    @NamedQuery(name = "Ejercicio.findByIdEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.ejercicioPK.idEjercicio = :idEjercicio"),
    @NamedQuery(name = "Ejercicio.findByDescripcion", query = "SELECT e FROM Ejercicio e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Ejercicio.findByTipoEjercicio", query = "SELECT e FROM Ejercicio e WHERE e.tipoEjercicio = :tipoEjercicio"),
    @NamedQuery(name = "Ejercicio.findByIntensidad", query = "SELECT e FROM Ejercicio e WHERE e.intensidad = :intensidad"),
    @NamedQuery(name = "Ejercicio.findByRutinaId", query = "SELECT e FROM Ejercicio e WHERE e.ejercicioPK.rutinaId = :rutinaId"),
    @NamedQuery(name = "Ejercicio.findByUrlvideo", query = "SELECT e FROM Ejercicio e WHERE e.urlvideo = :urlvideo")})
public class Ejercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EjercicioPK ejercicioPK;
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "tipo_ejercicio")
    private String tipoEjercicio;
    @Basic(optional = false)
    @Column(name = "intensidad")
    private int intensidad;
    @Column(name = "urlvideo")
    private String urlvideo;
    @JoinColumn(name = "rutina_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rutina rutina;

    public Ejercicio() {
    }

    public Ejercicio(EjercicioPK ejercicioPK) {
        this.ejercicioPK = ejercicioPK;
    }

    public Ejercicio(EjercicioPK ejercicioPK, String tipoEjercicio, int intensidad) {
        this.ejercicioPK = ejercicioPK;
        this.tipoEjercicio = tipoEjercicio;
        this.intensidad = intensidad;
    }

    public Ejercicio(BigDecimal idEjercicio, BigDecimal rutinaId) {
        this.ejercicioPK = new EjercicioPK(idEjercicio, rutinaId);
    }

    public EjercicioPK getEjercicioPK() {
        return ejercicioPK;
    }

    public void setEjercicioPK(EjercicioPK ejercicioPK) {
        this.ejercicioPK = ejercicioPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public String getUrlvideo() {
        return urlvideo;
    }

    public void setUrlvideo(String urlvideo) {
        this.urlvideo = urlvideo;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ejercicioPK != null ? ejercicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ejercicio)) {
            return false;
        }
        Ejercicio other = (Ejercicio) object;
        if ((this.ejercicioPK == null && other.ejercicioPK != null) || (this.ejercicioPK != null && !this.ejercicioPK.equals(other.ejercicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Ejercicio[ ejercicioPK=" + ejercicioPK + " ]";
    }
    
}
