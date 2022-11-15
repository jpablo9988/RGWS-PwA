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
@Table(name = "dia_x_programa_ejercicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DiaXProgramaEjercicio.findAll", query = "SELECT d FROM DiaXProgramaEjercicio d"),
    @NamedQuery(name = "DiaXProgramaEjercicio.findByDiaNombre", query = "SELECT d FROM DiaXProgramaEjercicio d WHERE d.diaXProgramaEjercicioPK.diaNombre = :diaNombre"),
    @NamedQuery(name = "DiaXProgramaEjercicio.findByProgramaEjercicioNombre", query = "SELECT d FROM DiaXProgramaEjercicio d WHERE d.diaXProgramaEjercicioPK.programaEjercicioNombre = :programaEjercicioNombre"),
    @NamedQuery(name = "DiaXProgramaEjercicio.findByIndiceOrden", query = "SELECT d FROM DiaXProgramaEjercicio d WHERE d.indiceOrden = :indiceOrden")})
public class DiaXProgramaEjercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DiaXProgramaEjercicioPK diaXProgramaEjercicioPK;
    @Basic(optional = false)
    @Column(name = "indice_orden")
    private int indiceOrden;
    @JoinColumn(name = "dia_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Dia dia;
    @JoinColumn(name = "programa_ejercicio_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProgramaEjercicio programaEjercicio;

    public DiaXProgramaEjercicio() {
    }

    public DiaXProgramaEjercicio(DiaXProgramaEjercicioPK diaXProgramaEjercicioPK) {
        this.diaXProgramaEjercicioPK = diaXProgramaEjercicioPK;
    }

    public DiaXProgramaEjercicio(DiaXProgramaEjercicioPK diaXProgramaEjercicioPK, int indiceOrden) {
        this.diaXProgramaEjercicioPK = diaXProgramaEjercicioPK;
        this.indiceOrden = indiceOrden;
    }

    public DiaXProgramaEjercicio(String diaNombre, String programaEjercicioNombre) {
        this.diaXProgramaEjercicioPK = new DiaXProgramaEjercicioPK(diaNombre, programaEjercicioNombre);
    }

    public DiaXProgramaEjercicioPK getDiaXProgramaEjercicioPK() {
        return diaXProgramaEjercicioPK;
    }

    public void setDiaXProgramaEjercicioPK(DiaXProgramaEjercicioPK diaXProgramaEjercicioPK) {
        this.diaXProgramaEjercicioPK = diaXProgramaEjercicioPK;
    }

    public int getIndiceOrden() {
        return indiceOrden;
    }

    public void setIndiceOrden(int indiceOrden) {
        this.indiceOrden = indiceOrden;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public ProgramaEjercicio getProgramaEjercicio() {
        return programaEjercicio;
    }

    public void setProgramaEjercicio(ProgramaEjercicio programaEjercicio) {
        this.programaEjercicio = programaEjercicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diaXProgramaEjercicioPK != null ? diaXProgramaEjercicioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiaXProgramaEjercicio)) {
            return false;
        }
        DiaXProgramaEjercicio other = (DiaXProgramaEjercicio) object;
        if ((this.diaXProgramaEjercicioPK == null && other.diaXProgramaEjercicioPK != null) || (this.diaXProgramaEjercicioPK != null && !this.diaXProgramaEjercicioPK.equals(other.diaXProgramaEjercicioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.DiaXProgramaEjercicio[ diaXProgramaEjercicioPK=" + diaXProgramaEjercicioPK + " ]";
    }
    
}
