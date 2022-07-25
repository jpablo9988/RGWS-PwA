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
public class EjercicioPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_ejercicio")
    private BigDecimal idEjercicio;
    @Basic(optional = false)
    @Column(name = "rutina_id")
    private BigDecimal rutinaId;

    public EjercicioPK() {
    }

    public EjercicioPK(BigDecimal idEjercicio, BigDecimal rutinaId) {
        this.idEjercicio = idEjercicio;
        this.rutinaId = rutinaId;
    }

    public BigDecimal getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(BigDecimal idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public BigDecimal getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(BigDecimal rutinaId) {
        this.rutinaId = rutinaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEjercicio != null ? idEjercicio.hashCode() : 0);
        hash += (rutinaId != null ? rutinaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EjercicioPK)) {
            return false;
        }
        EjercicioPK other = (EjercicioPK) object;
        if (this.idEjercicio != other.idEjercicio) {
            return false;
        }
        if (this.rutinaId != other.rutinaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EjercicioPK[ idEjercicio=" + idEjercicio + ", rutinaId=" + rutinaId + " ]";
    }
    
}
