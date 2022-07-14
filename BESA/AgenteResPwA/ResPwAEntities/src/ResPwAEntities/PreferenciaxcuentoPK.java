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
 * @author 57305
 */
@Embeddable
public class PreferenciaxcuentoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CUENTO_NOMBRE")
    private String cuentoNombre;
    @Basic(optional = false)
    @Column(name = "perfil_preferencia_nombrepreferido", nullable = false, length = 2147483647)
    private String perfilPreferenciaNombrepreferido;

    public PreferenciaxcuentoPK() {
    }

    public PreferenciaxcuentoPK(String perfilPreferenciaNombrepreferido, String cuentoNombre) {
        this.cuentoNombre = cuentoNombre;
        this.perfilPreferenciaNombrepreferido = perfilPreferenciaNombrepreferido;
    }

    public String getCuentoNombre() {
        return cuentoNombre;
    }

    public void setCuentoNombre(String cuentoNombre) {
        this.cuentoNombre = cuentoNombre;
    }

    public String getPerfilPreferenciaNombrepreferido() {
        return perfilPreferenciaNombrepreferido;
    }

    public void setPerfilPreferenciaNombrepreferido(String perfilPreferenciaNombrepreferido) {
        this.perfilPreferenciaNombrepreferido = perfilPreferenciaNombrepreferido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilPreferenciaNombrepreferido != null ? perfilPreferenciaNombrepreferido.hashCode() : 0);
        hash += (cuentoNombre != null ? cuentoNombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaxcuentoPK)) {
            return false;
        }
        PreferenciaxcuentoPK other = (PreferenciaxcuentoPK) object;
        if ((this.perfilPreferenciaNombrepreferido == null && other.perfilPreferenciaNombrepreferido != null) || (this.perfilPreferenciaNombrepreferido != null && !this.perfilPreferenciaNombrepreferido.equals(other.perfilPreferenciaNombrepreferido))) {
            return false;
        }
        if ((this.cuentoNombre == null && other.cuentoNombre != null) || (this.cuentoNombre != null && !this.cuentoNombre.equals(other.cuentoNombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaxcuentoPK[ perfilPreferenciaNombrepreferido=" + perfilPreferenciaNombrepreferido + ", cuentoNombre=" + cuentoNombre + " ]";
    }
    
}
