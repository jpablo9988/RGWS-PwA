/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author tesispepper
 */
@Embeddable
public class ActXPreferenciaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "actividad_pwa_id")
    private int actividadPwaId;
    @Basic(optional = false)
    @Column(name = "preferencia_pwa_cedula")
    private String preferenciaPwaCedula;

    public ActXPreferenciaPK() {
    }

    public ActXPreferenciaPK(int actividadPwaId, String preferenciaPwaCedula) {
        this.actividadPwaId = actividadPwaId;
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    public int getActividadPwaId() {
        return actividadPwaId;
    }

    public void setActividadPwaId(int actividadPwaId) {
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
        hash += (int) actividadPwaId;
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
        if (this.actividadPwaId != other.actividadPwaId) {
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
