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
public class DiaXCategoriaEntrenamientoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "dia_nombre")
    private String diaNombre;
    @Basic(optional = false)
    @Column(name = "categoria_tipo")
    private String categoriaTipo;
    @Basic(optional = false)
    @Column(name = "indice_orden")
    private int indiceOrden;

    public DiaXCategoriaEntrenamientoPK() {
    }

    public DiaXCategoriaEntrenamientoPK(String diaNombre, String categoriaTipo, int indiceOrden) {
        this.diaNombre = diaNombre;
        this.categoriaTipo = categoriaTipo;
        this.indiceOrden = indiceOrden;
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }

    public String getCategoriaTipo() {
        return categoriaTipo;
    }

    public void setCategoriaTipo(String categoriaTipo) {
        this.categoriaTipo = categoriaTipo;
    }

    public int getIndiceOrden() {
        return indiceOrden;
    }

    public void setIndiceOrden(int indiceOrden) {
        this.indiceOrden = indiceOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diaNombre != null ? diaNombre.hashCode() : 0);
        hash += (categoriaTipo != null ? categoriaTipo.hashCode() : 0);
        hash += (int) indiceOrden;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiaXCategoriaEntrenamientoPK)) {
            return false;
        }
        DiaXCategoriaEntrenamientoPK other = (DiaXCategoriaEntrenamientoPK) object;
        if ((this.diaNombre == null && other.diaNombre != null) || (this.diaNombre != null && !this.diaNombre.equals(other.diaNombre))) {
            return false;
        }
        if ((this.categoriaTipo == null && other.categoriaTipo != null) || (this.categoriaTipo != null && !this.categoriaTipo.equals(other.categoriaTipo))) {
            return false;
        }
        if (this.indiceOrden != other.indiceOrden) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.DiaXCategoriaEntrenamientoPK[ diaNombre=" + diaNombre + ", categoriaTipo=" + categoriaTipo + ", indiceOrden=" + indiceOrden + " ]";
    }
    
}
