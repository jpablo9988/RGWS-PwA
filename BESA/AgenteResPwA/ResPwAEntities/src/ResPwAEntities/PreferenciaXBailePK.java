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
public class PreferenciaXBailePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "baile_id")
    private BigDecimal baileId;
    @Basic(optional = false)
    @Column(name = "preferencia_pwa_cedula")
    private String preferenciaPwaCedula;

    public PreferenciaXBailePK() {
    }

    public PreferenciaXBailePK(BigDecimal baileId, String preferenciaPwaCedula) {
        this.baileId = baileId;
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    public BigDecimal getBaileId() {
        return baileId;
    }

    public void setBaileId(BigDecimal baileId) {
        this.baileId = baileId;
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
        hash += (baileId != null ? baileId.hashCode() : 0);
        hash += (preferenciaPwaCedula != null ? preferenciaPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXBailePK)) {
            return false;
        }
        PreferenciaXBailePK other = (PreferenciaXBailePK) object;
        if ((this.baileId == null && other.baileId != null) || (this.baileId != null && !this.baileId.equals(other.baileId))) {
            return false;
        }
        if ((this.preferenciaPwaCedula == null && other.preferenciaPwaCedula != null) || (this.preferenciaPwaCedula != null && !this.preferenciaPwaCedula.equals(other.preferenciaPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXBailePK[ baileId=" + baileId + ", preferenciaPwaCedula=" + preferenciaPwaCedula + " ]";
    }
    
}
