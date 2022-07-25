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
import javax.persistence.Embeddable;

/**
 *
 * @author USER
 */
@Embeddable
public class ActXPreferenciaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "actividad_pwa_id")
    private BigDecimal actividadPwaId;
    @Basic(optional = false)
    @Column(name = "preferencia_pwa_cedula")
    private String preferenciaPwaCedula;

    public ActXPreferenciaPK() {
    }

    public ActXPreferenciaPK(BigDecimal actividadPwaId, String preferenciaPwaCedula) {
        this.actividadPwaId = actividadPwaId;
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    public BigDecimal getActividadPwaId() {
        return actividadPwaId;
    }

    public void setActividadPwaId(BigDecimal actividadPwaId) {
        this.actividadPwaId = actividadPwaId;
    }

    public String getPreferenciaPwaCedula() {
        return preferenciaPwaCedula;
    }

    public void setPreferenciaPwaCedula(String preferenciaPwaCedula) {
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actividadPwaId != null ? actividadPwaId.hashCode() : 0);
        hash += (preferenciaPwaCedula != null ? preferenciaPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActXPreferenciaPK)) {
            return false;
        }
        ActXPreferenciaPK other = (ActXPreferenciaPK) object;
        if ((this.actividadPwaId == null && other.actividadPwaId != null) || (this.actividadPwaId != null && !this.actividadPwaId.equals(other.actividadPwaId))) {
            return false;
        }
        if ((this.preferenciaPwaCedula == null && other.preferenciaPwaCedula != null) || (this.preferenciaPwaCedula != null && !this.preferenciaPwaCedula.equals(other.preferenciaPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.ActXPreferenciaPK[ actividadPwaId=" + actividadPwaId + ", preferenciaPwaCedula=" + preferenciaPwaCedula + " ]";
    }
    
}
