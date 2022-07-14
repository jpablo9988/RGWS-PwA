/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author 57305
 */
@Embeddable
public class EjerciciosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_ejercicio", nullable = false)
    private BigInteger idEjercicio;
    @Basic(optional = false)
    @Column(name = "rutinas_id_1", nullable = false)
    private int rutinasId1;

    public EjerciciosPK() {
    }

    public EjerciciosPK(BigInteger idEjercicio, int rutinasId1) {
        this.idEjercicio = idEjercicio;
        this.rutinasId1 = rutinasId1;
    }

    public BigInteger getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(BigInteger idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getRutinasId1() {
        return rutinasId1;
    }

    public void setRutinasId1(int rutinasId1) {
        this.rutinasId1 = rutinasId1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEjercicio != null ? idEjercicio.hashCode() : 0);
        hash += (int) rutinasId1;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjerciciosPK)) {
            return false;
        }
        EjerciciosPK other = (EjerciciosPK) object;
        if ((this.idEjercicio == null && other.idEjercicio != null) || (this.idEjercicio != null && !this.idEjercicio.equals(other.idEjercicio))) {
            return false;
        }
        if (this.rutinasId1 != other.rutinasId1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EjerciciosPK[ idEjercicio=" + idEjercicio + ", rutinasId1=" + rutinasId1 + " ]";
    }
    
}
