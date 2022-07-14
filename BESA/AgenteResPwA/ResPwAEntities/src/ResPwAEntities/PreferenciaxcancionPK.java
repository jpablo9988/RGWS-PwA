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
public class PreferenciaxcancionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cancion_nombre", nullable = false, length = 2147483647)
    private String cancionNombre;
    @Basic(optional = false)
    @Column(name = "perfil_preferencia_nombrepreferido", nullable = false, length = 2147483647)
    private String perfilPreferenciaNombrepreferido;

    public PreferenciaxcancionPK() {
    }

    public PreferenciaxcancionPK(String cancionNombre, String perfilPreferenciaNombrepreferido) {
        this.cancionNombre = cancionNombre;
        this.perfilPreferenciaNombrepreferido = perfilPreferenciaNombrepreferido;
    }

    public String getCancionNombre() {
        return cancionNombre;
    }

    public void setCancionNombre(String cancionNombre) {
        this.cancionNombre = cancionNombre;
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
        hash += (cancionNombre != null ? cancionNombre.hashCode() : 0);
        hash += (perfilPreferenciaNombrepreferido != null ? perfilPreferenciaNombrepreferido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaxcancionPK)) {
            return false;
        }
        PreferenciaxcancionPK other = (PreferenciaxcancionPK) object;
        if ((this.cancionNombre == null && other.cancionNombre != null) || (this.cancionNombre != null && !this.cancionNombre.equals(other.cancionNombre))) {
            return false;
        }
        if ((this.perfilPreferenciaNombrepreferido == null && other.perfilPreferenciaNombrepreferido != null) || (this.perfilPreferenciaNombrepreferido != null && !this.perfilPreferenciaNombrepreferido.equals(other.perfilPreferenciaNombrepreferido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaxcancionPK[ cancionNombre=" + cancionNombre + ", perfilPreferenciaNombrepreferido=" + perfilPreferenciaNombrepreferido + " ]";
    }
    
}
