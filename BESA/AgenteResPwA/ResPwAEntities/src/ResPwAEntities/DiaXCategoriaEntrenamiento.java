/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "dia_x_categoria_entrenamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiaXCategoriaEntrenamiento.findAll", query = "SELECT d FROM DiaXCategoriaEntrenamiento d"),
    @NamedQuery(name = "DiaXCategoriaEntrenamiento.findByDiaNombre", query = "SELECT d FROM DiaXCategoriaEntrenamiento d WHERE d.diaXCategoriaEntrenamientoPK.diaNombre = :diaNombre"),
    @NamedQuery(name = "DiaXCategoriaEntrenamiento.findByCategoriaTipo", query = "SELECT d FROM DiaXCategoriaEntrenamiento d WHERE d.diaXCategoriaEntrenamientoPK.categoriaTipo = :categoriaTipo"),
    @NamedQuery(name = "DiaXCategoriaEntrenamiento.findByIndiceOrden", query = "SELECT d FROM DiaXCategoriaEntrenamiento d WHERE d.diaXCategoriaEntrenamientoPK.indiceOrden = :indiceOrden"),
    @NamedQuery(name = "DiaXCategoriaEntrenamiento.findByOpcional", query = "SELECT d FROM DiaXCategoriaEntrenamiento d WHERE d.opcional = :opcional")})
public class DiaXCategoriaEntrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DiaXCategoriaEntrenamientoPK diaXCategoriaEntrenamientoPK;
    @Basic(optional = false)
    @Column(name = "opcional")
    private boolean opcional;
    @JoinColumn(name = "categoria_tipo", referencedColumnName = "tipo", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CategoriaEntrenamiento categoriaEntrenamiento;
    @JoinColumn(name = "dia_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dia dia;

    public DiaXCategoriaEntrenamiento() {
    }

    public DiaXCategoriaEntrenamiento(DiaXCategoriaEntrenamientoPK diaXCategoriaEntrenamientoPK) {
        this.diaXCategoriaEntrenamientoPK = diaXCategoriaEntrenamientoPK;
    }

    public DiaXCategoriaEntrenamiento(DiaXCategoriaEntrenamientoPK diaXCategoriaEntrenamientoPK, boolean opcional) {
        this.diaXCategoriaEntrenamientoPK = diaXCategoriaEntrenamientoPK;
        this.opcional = opcional;
    }

    public DiaXCategoriaEntrenamiento(String diaNombre, String categoriaTipo, int indiceOrden) {
        this.diaXCategoriaEntrenamientoPK = new DiaXCategoriaEntrenamientoPK(diaNombre, categoriaTipo, indiceOrden);
    }

    public DiaXCategoriaEntrenamientoPK getDiaXCategoriaEntrenamientoPK() {
        return diaXCategoriaEntrenamientoPK;
    }

    public void setDiaXCategoriaEntrenamientoPK(DiaXCategoriaEntrenamientoPK diaXCategoriaEntrenamientoPK) {
        this.diaXCategoriaEntrenamientoPK = diaXCategoriaEntrenamientoPK;
    }

    public boolean getOpcional() {
        return opcional;
    }

    public void setOpcional(boolean opcional) {
        this.opcional = opcional;
    }

    public CategoriaEntrenamiento getCategoriaEntrenamiento() {
        return categoriaEntrenamiento;
    }

    public void setCategoriaEntrenamiento(CategoriaEntrenamiento categoriaEntrenamiento) {
        this.categoriaEntrenamiento = categoriaEntrenamiento;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diaXCategoriaEntrenamientoPK != null ? diaXCategoriaEntrenamientoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiaXCategoriaEntrenamiento)) {
            return false;
        }
        DiaXCategoriaEntrenamiento other = (DiaXCategoriaEntrenamiento) object;
        if ((this.diaXCategoriaEntrenamientoPK == null && other.diaXCategoriaEntrenamientoPK != null) || (this.diaXCategoriaEntrenamientoPK != null && !this.diaXCategoriaEntrenamientoPK.equals(other.diaXCategoriaEntrenamientoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.DiaXCategoriaEntrenamiento[ diaXCategoriaEntrenamientoPK=" + diaXCategoriaEntrenamientoPK + " ]";
    }
    
}
