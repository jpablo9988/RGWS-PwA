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
public class SemanasPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_semana", nullable = false)
    private int idSemana;
    @Basic(optional = false)
    @Column(name = "proxintensidad_rutinas_id_1", nullable = false)
    private int proxintensidadRutinasId1;

    public SemanasPK() {
    }

    public SemanasPK(int idSemana, int proxintensidadRutinasId1) {
        this.idSemana = idSemana;
        this.proxintensidadRutinasId1 = proxintensidadRutinasId1;
    }

    public int getIdSemana() {
        return idSemana;
    }

    public void setIdSemana(int idSemana) {
        this.idSemana = idSemana;
    }

    public int getProxintensidadRutinasId1() {
        return proxintensidadRutinasId1;
    }

    public void setProxintensidadRutinasId1(int proxintensidadRutinasId1) {
        this.proxintensidadRutinasId1 = proxintensidadRutinasId1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idSemana;
        hash += (int) proxintensidadRutinasId1;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemanasPK)) {
            return false;
        }
        SemanasPK other = (SemanasPK) object;
        if (this.idSemana != other.idSemana) {
            return false;
        }
        if (this.proxintensidadRutinasId1 != other.proxintensidadRutinasId1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.SemanasPK[ idSemana=" + idSemana + ", proxintensidadRutinasId1=" + proxintensidadRutinasId1 + " ]";
    }
    
}
