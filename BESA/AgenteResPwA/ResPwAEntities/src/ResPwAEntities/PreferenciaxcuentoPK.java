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
public class PreferenciaXCuentoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cuento_nombre")
    private String cuentoNombre;
    @Basic(optional = false)
    @Column(name = "preferencia_pwa_cedula")
    private String preferenciaPwaCedula;

    public PreferenciaXCuentoPK() {
    }

    public PreferenciaXCuentoPK(String cuentoNombre, String preferenciaPwaCedula) {
        this.cuentoNombre = cuentoNombre;
        this.preferenciaPwaCedula = preferenciaPwaCedula;
    }

    public String getCuentoNombre() {
        return cuentoNombre;
    }

    public void setCuentoNombre(String cuentoNombre) {
        this.cuentoNombre = cuentoNombre;
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
        hash += (cuentoNombre != null ? cuentoNombre.hashCode() : 0);
        hash += (preferenciaPwaCedula != null ? preferenciaPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXCuentoPK)) {
            return false;
        }
        PreferenciaXCuentoPK other = (PreferenciaXCuentoPK) object;
        if ((this.cuentoNombre == null && other.cuentoNombre != null) || (this.cuentoNombre != null && !this.cuentoNombre.equals(other.cuentoNombre))) {
            return false;
        }
        if ((this.preferenciaPwaCedula == null && other.preferenciaPwaCedula != null) || (this.preferenciaPwaCedula != null && !this.preferenciaPwaCedula.equals(other.preferenciaPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXCuentoPK[ cuentoNombre=" + cuentoNombre + ", preferenciaPwaCedula=" + preferenciaPwaCedula + " ]";
    }
    
}
