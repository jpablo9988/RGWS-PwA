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
public class DiaXProgramaEjercicioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "dia_nombre")
    private String diaNombre;
    @Basic(optional = false)
    @Column(name = "programa_ejercicio_nombre")
    private String programaEjercicioNombre;

    public DiaXProgramaEjercicioPK() {
    }

    public DiaXProgramaEjercicioPK(String diaNombre, String programaEjercicioNombre) {
        this.diaNombre = diaNombre;
        this.programaEjercicioNombre = programaEjercicioNombre;
    }

    public String getDiaNombre() {
        return diaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        this.diaNombre = diaNombre;
    }

    public String getProgramaEjercicioNombre() {
        return programaEjercicioNombre;
    }

    public void setProgramaEjercicioNombre(String programaEjercicioNombre) {
        this.programaEjercicioNombre = programaEjercicioNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diaNombre != null ? diaNombre.hashCode() : 0);
        hash += (programaEjercicioNombre != null ? programaEjercicioNombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiaXProgramaEjercicioPK)) {
            return false;
        }
        DiaXProgramaEjercicioPK other = (DiaXProgramaEjercicioPK) object;
        if ((this.diaNombre == null && other.diaNombre != null) || (this.diaNombre != null && !this.diaNombre.equals(other.diaNombre))) {
            return false;
        }
        if ((this.programaEjercicioNombre == null && other.programaEjercicioNombre != null) || (this.programaEjercicioNombre != null && !this.programaEjercicioNombre.equals(other.programaEjercicioNombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.DiaXProgramaEjercicioPK[ diaNombre=" + diaNombre + ", programaEjercicioNombre=" + programaEjercicioNombre + " ]";
    }
    
}
