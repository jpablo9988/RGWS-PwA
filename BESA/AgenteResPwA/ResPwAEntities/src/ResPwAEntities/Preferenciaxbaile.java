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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "preferencia_x_baile", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreferenciaXBaile.findAll", query = "SELECT p FROM PreferenciaXBaile p"),
    @NamedQuery(name = "PreferenciaXBaile.findByBaileId", query = "SELECT p FROM PreferenciaXBaile p WHERE p.preferenciaXBailePK.baileId = :baileId"),
    @NamedQuery(name = "PreferenciaXBaile.findByPreferenciaPwaCedula", query = "SELECT p FROM PreferenciaXBaile p WHERE p.preferenciaXBailePK.preferenciaPwaCedula = :preferenciaPwaCedula"),
    @NamedQuery(name = "PreferenciaXBaile.findByGusto", query = "SELECT p FROM PreferenciaXBaile p WHERE p.gusto = :gusto")})
public class PreferenciaXBaile implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaXBailePK preferenciaXBailePK;
    @Basic(optional = false)
    @Column(name = "gusto")
    private double gusto;
    @JoinColumn(name = "baile_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Baile baile;
    @JoinColumn(name = "preferencia_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferencia;

    public PreferenciaXBaile() {
    }

    public PreferenciaXBaile(PreferenciaXBailePK preferenciaXBailePK) {
        this.preferenciaXBailePK = preferenciaXBailePK;
    }

    public PreferenciaXBaile(PreferenciaXBailePK preferenciaXBailePK, double gusto) {
        this.preferenciaXBailePK = preferenciaXBailePK;
        this.gusto = gusto;
    }

    public PreferenciaXBaile(BigDecimal baileId, String preferenciaPwaCedula) {
        this.preferenciaXBailePK = new PreferenciaXBailePK(baileId, preferenciaPwaCedula);
    }

    public PreferenciaXBailePK getPreferenciaXBailePK() {
        return preferenciaXBailePK;
    }

    public void setPreferenciaXBailePK(PreferenciaXBailePK preferenciaXBailePK) {
        this.preferenciaXBailePK = preferenciaXBailePK;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public Baile getBaile() {
        return baile;
    }

    public void setBaile(Baile baile) {
        this.baile = baile;
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
        hash += (preferenciaXBailePK != null ? preferenciaXBailePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXBaile)) {
            return false;
        }
        PreferenciaXBaile other = (PreferenciaXBaile) object;
        if ((this.preferenciaXBailePK == null && other.preferenciaXBailePK != null) || (this.preferenciaXBailePK != null && !this.preferenciaXBailePK.equals(other.preferenciaXBailePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXBaile[ preferenciaXBailePK=" + preferenciaXBailePK + " ]";
    }
    
}
