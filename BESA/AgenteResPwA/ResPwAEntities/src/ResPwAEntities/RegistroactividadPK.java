/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author USER
 */
@Embeddable
public class RegistroActividadPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "perfil_pwa_cedula")
    private String perfilPwaCedula;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "actividad_pwa_id")
    private BigDecimal actividadPwaId;

    public RegistroActividadPK() {
    }

    public RegistroActividadPK(Date fecha, String perfilPwaCedula, String tipo, BigDecimal actividadPwaId) {
        this.fecha = fecha;
        this.perfilPwaCedula = perfilPwaCedula;
        this.tipo = tipo;
        this.actividadPwaId = actividadPwaId;
    }
    public RegistroActividadPK(Date fecha, String tipo) {
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPerfilPwaCedula() {
        return perfilPwaCedula;
    }

    public void setPerfilPwaCedula(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getActividadPwaId() {
        return actividadPwaId;
    }

    public void setActividadPwaId(BigDecimal actividadPwaId) {
        this.actividadPwaId = actividadPwaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (perfilPwaCedula != null ? perfilPwaCedula.hashCode() : 0);
        hash += (tipo != null ? tipo.hashCode() : 0);
        hash += (actividadPwaId != null ? actividadPwaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroActividadPK)) {
            return false;
        }
        RegistroActividadPK other = (RegistroActividadPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.perfilPwaCedula == null && other.perfilPwaCedula != null) || (this.perfilPwaCedula != null && !this.perfilPwaCedula.equals(other.perfilPwaCedula))) {
            return false;
        }
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        if ((this.actividadPwaId == null && other.actividadPwaId != null) || (this.actividadPwaId != null && !this.actividadPwaId.equals(other.actividadPwaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.RegistroActividadPK[ fecha=" + fecha + ", perfilPwaCedula=" + perfilPwaCedula + ", tipo=" + tipo + ", actividadPwaId=" + actividadPwaId + " ]";
    }
    
}
