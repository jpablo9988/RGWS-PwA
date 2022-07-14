/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semanas.findAll", query = "SELECT s FROM Semanas s")
    , @NamedQuery(name = "Semanas.findByIdSemana", query = "SELECT s FROM Semanas s WHERE s.semanasPK.idSemana = :idSemana")
    , @NamedQuery(name = "Semanas.findByCantidadRepeticiones", query = "SELECT s FROM Semanas s WHERE s.cantidadRepeticiones = :cantidadRepeticiones")
    , @NamedQuery(name = "Semanas.findByFechainicio", query = "SELECT s FROM Semanas s WHERE s.fechainicio = :fechainicio")
    , @NamedQuery(name = "Semanas.findByProxintensidadRutinasId1", query = "SELECT s FROM Semanas s WHERE s.semanasPK.proxintensidadRutinasId1 = :proxintensidadRutinasId1")})
public class Semanas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SemanasPK semanasPK;
    @Column(name = "cantidad_repeticiones")
    private Integer cantidadRepeticiones;
    @Temporal(TemporalType.DATE)
    private Date fechainicio;

    public Semanas() {
    }

    public Semanas(SemanasPK semanasPK) {
        this.semanasPK = semanasPK;
    }

    public Semanas(int idSemana, int proxintensidadRutinasId1) {
        this.semanasPK = new SemanasPK(idSemana, proxintensidadRutinasId1);
    }

    public SemanasPK getSemanasPK() {
        return semanasPK;
    }

    public void setSemanasPK(SemanasPK semanasPK) {
        this.semanasPK = semanasPK;
    }

    public Integer getCantidadRepeticiones() {
        return cantidadRepeticiones;
    }

    public void setCantidadRepeticiones(Integer cantidadRepeticiones) {
        this.cantidadRepeticiones = cantidadRepeticiones;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semanasPK != null ? semanasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Semanas)) {
            return false;
        }
        Semanas other = (Semanas) object;
        if ((this.semanasPK == null && other.semanasPK != null) || (this.semanasPK != null && !this.semanasPK.equals(other.semanasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Semanas[ semanasPK=" + semanasPK + " ]";
    }
    
}
