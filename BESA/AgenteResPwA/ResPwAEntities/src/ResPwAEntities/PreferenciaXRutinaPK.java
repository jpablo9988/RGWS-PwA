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
public class PreferenciaXRutinaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "rutina_id")
    private BigDecimal rutinaId;
    @Basic(optional = false)
    @Column(name = "preferencia_pwa_cedula")
    private String preferenciaPwaCedula;

    public PreferenciaXRutinaPK() {
    }

    public PreferenciaXRutinaPK(BigDecimal rutinaId, String preferenciaPwaCedula) {
        this.rutinaId = rutinaId;
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    public BigDecimal getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(BigDecimal rutinaId) {
        this.rutinaId = rutinaId;
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
        hash += (rutinaId != null ? rutinaId.hashCode() : 0);
        hash += (preferenciaPwaCedula != null ? preferenciaPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXRutinaPK)) {
            return false;
        }
        PreferenciaXRutinaPK other = (PreferenciaXRutinaPK) object;
        if ((this.rutinaId == null && other.rutinaId != null) || (this.rutinaId != null && !this.rutinaId.equals(other.rutinaId))) {
            return false;
        }
        if ((this.preferenciaPwaCedula == null && other.preferenciaPwaCedula != null) || (this.preferenciaPwaCedula != null && !this.preferenciaPwaCedula.equals(other.preferenciaPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXRutinaPK[ rutinaId=" + rutinaId + ", preferenciaPwaCedula=" + preferenciaPwaCedula + " ]";
    }
    
}
