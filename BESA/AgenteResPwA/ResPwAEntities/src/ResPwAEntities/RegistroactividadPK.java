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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author 57305
 */
@Embeddable
public class RegistroactividadPK implements Serializable {

    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String perfilpwacedula;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String tipo;
    @Basic(optional = false)
    @Column(name = "actividadpwa_id", nullable = false)
    private int actividadpwaId;

    public RegistroactividadPK() {
    }

    public RegistroactividadPK(Date fecha, String perfilpwacedula, String tipo, int actividadpwaId) {
        this.fecha = fecha;
        this.perfilpwacedula = perfilpwacedula;
        this.tipo = tipo;
        this.actividadpwaId = actividadpwaId;
    }
    public RegistroactividadPK(Date fecha, String tipo) {
        this.fecha = fecha;
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getPerfilpwacedula() {
        return perfilpwacedula;
    }

    public void setPerfilpwacedula(String perfilpwacedula) {
        this.perfilpwacedula = perfilpwacedula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getActividadpwaId() {
        return actividadpwaId;
    }

    public void setActividadpwaId(int actividadpwaId) {
        this.actividadpwaId = actividadpwaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (perfilpwacedula != null ? perfilpwacedula.hashCode() : 0);
        hash += (tipo != null ? tipo.hashCode() : 0);
        hash += (int) actividadpwaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroactividadPK)) {
            return false;
        }
        RegistroactividadPK other = (RegistroactividadPK) object;
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.perfilpwacedula == null && other.perfilpwacedula != null) || (this.perfilpwacedula != null && !this.perfilpwacedula.equals(other.perfilpwacedula))) {
            return false;
        }
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        if (this.actividadpwaId != other.actividadpwaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.RegistroactividadPK[ fecha=" + fecha + ", perfilpwacedula=" + perfilpwacedula + ", tipo=" + tipo + ", actividadpwaId=" + actividadpwaId + " ]";
    }

    public void setPerfilpwaCedula(Object perfilpwa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
