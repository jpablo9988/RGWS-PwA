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
@Table(name = "preferencia_x_cuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreferenciaXCuento.findAll", query = "SELECT p FROM PreferenciaXCuento p"),
    @NamedQuery(name = "PreferenciaXCuento.findByCuentoNombre", query = "SELECT p FROM PreferenciaXCuento p WHERE p.preferenciaXCuentoPK.cuentoNombre = :cuentoNombre"),
    @NamedQuery(name = "PreferenciaXCuento.findByPreferenciaPwaCedula", query = "SELECT p FROM PreferenciaXCuento p WHERE p.preferenciaXCuentoPK.preferenciaPwaCedula = :preferenciaPwaCedula"),
    @NamedQuery(name = "PreferenciaXCuento.findByGusto", query = "SELECT p FROM PreferenciaXCuento p WHERE p.gusto = :gusto")})
public class PreferenciaXCuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaXCuentoPK preferenciaXCuentoPK;
    @Basic(optional = false)
    @Column(name = "gusto")
    private double gusto;
    @JoinColumn(name = "cuento_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cuento cuento;
    @JoinColumn(name = "preferencia_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilPreferencia perfilPreferencia;

    public PreferenciaXCuento() {
    }

    public PreferenciaXCuento(PreferenciaXCuentoPK preferenciaXCuentoPK) {
        this.preferenciaXCuentoPK = preferenciaXCuentoPK;
    }

    public PreferenciaXCuento(PreferenciaXCuentoPK preferenciaXCuentoPK, double gusto) {
        this.preferenciaXCuentoPK = preferenciaXCuentoPK;
        this.gusto = gusto;
    }

    public PreferenciaXCuento(String cuentoNombre, String preferenciaPwaCedula) {
        this.preferenciaXCuentoPK = new PreferenciaXCuentoPK(cuentoNombre, preferenciaPwaCedula);
    }

    public PreferenciaXCuentoPK getPreferenciaXCuentoPK() {
        return preferenciaXCuentoPK;
    }

    public void setPreferenciaXCuentoPK(PreferenciaXCuentoPK preferenciaXCuentoPK) {
        this.preferenciaXCuentoPK = preferenciaXCuentoPK;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public Cuento getCuento() {
        return cuento;
    }

    public void setCuento(Cuento cuento) {
        this.cuento = cuento;
    }

    public PerfilPreferencia getPerfilPreferencia() {
        return perfilPreferencia;
    }

    public void setPerfilPreferencia(PerfilPreferencia perfilPreferencia) {
        this.perfilPreferencia = perfilPreferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferenciaXCuentoPK != null ? preferenciaXCuentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXCuento)) {
            return false;
        }
        PreferenciaXCuento other = (PreferenciaXCuento) object;
        if ((this.preferenciaXCuentoPK == null && other.preferenciaXCuentoPK != null) || (this.preferenciaXCuentoPK != null && !this.preferenciaXCuentoPK.equals(other.preferenciaXCuentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXCuento[ preferenciaXCuentoPK=" + preferenciaXCuentoPK + " ]";
    }
    
}
