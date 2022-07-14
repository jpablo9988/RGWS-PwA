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
public class EnriqPK implements Serializable {

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String params;
    @Basic(optional = false)
    @Column(name = "frases_orden", nullable = false)
    private int frasesOrden;

    public EnriqPK() {
    }

    public EnriqPK(String params, int frasesOrden) {
        this.params = params;
        this.frasesOrden = frasesOrden;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public int getFrasesOrden() {
        return frasesOrden;
    }

    public void setFrasesOrden(int frasesOrden) {
        this.frasesOrden = frasesOrden;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (params != null ? params.hashCode() : 0);
        hash += (int) frasesOrden;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnriqPK)) {
            return false;
        }
        EnriqPK other = (EnriqPK) object;
        if ((this.params == null && other.params != null) || (this.params != null && !this.params.equals(other.params))) {
            return false;
        }
        if (this.frasesOrden != other.frasesOrden) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EnriqPK[ params=" + params + ", frasesOrden=" + frasesOrden + " ]";
    }
    
}
