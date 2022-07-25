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
@Table(name = "preferencia_x_rutina", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreferenciaXRutina.findAll", query = "SELECT p FROM PreferenciaXRutina p"),
    @NamedQuery(name = "PreferenciaXRutina.findByRutinaId", query = "SELECT p FROM PreferenciaXRutina p WHERE p.preferenciaXRutinaPK.rutinaId = :rutinaId"),
    @NamedQuery(name = "PreferenciaXRutina.findByPreferenciaPwaCedula", query = "SELECT p FROM PreferenciaXRutina p WHERE p.preferenciaXRutinaPK.preferenciaPwaCedula = :preferenciaPwaCedula"),
    @NamedQuery(name = "PreferenciaXRutina.findByGusto", query = "SELECT p FROM PreferenciaXRutina p WHERE p.gusto = :gusto")})
public class PreferenciaXRutina implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaXRutinaPK preferenciaXRutinaPK;
    @Basic(optional = false)
    @Column(name = "gusto")
    private double gusto;
    @JoinColumn(name = "preferencia_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferencia;
    @JoinColumn(name = "rutina_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rutina rutina;

    public PreferenciaXRutina() {
    }

    public PreferenciaXRutina(PreferenciaXRutinaPK preferenciaXRutinaPK) {
        this.preferenciaXRutinaPK = preferenciaXRutinaPK;
    }

    public PreferenciaXRutina(PreferenciaXRutinaPK preferenciaXRutinaPK, double gusto) {
        this.preferenciaXRutinaPK = preferenciaXRutinaPK;
        this.gusto = gusto;
    }

    public PreferenciaXRutina(BigDecimal rutinaId, String preferenciaPwaCedula) {
        this.preferenciaXRutinaPK = new PreferenciaXRutinaPK(rutinaId, preferenciaPwaCedula);
    }

    public PreferenciaXRutinaPK getPreferenciaXRutinaPK() {
        return preferenciaXRutinaPK;
    }

    public void setPreferenciaXRutinaPK(PreferenciaXRutinaPK preferenciaXRutinaPK) {
        this.preferenciaXRutinaPK = preferenciaXRutinaPK;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public PerfilPreferencia getPerfilPreferencia() {
        return perfilPreferencia;
    }

    public void setPerfilPreferencia(PerfilPreferencia perfilPreferencia) {
        this.perfilPreferencia = perfilPreferencia;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferenciaXRutinaPK != null ? preferenciaXRutinaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXRutina)) {
            return false;
        }
        PreferenciaXRutina other = (PreferenciaXRutina) object;
        if ((this.preferenciaXRutinaPK == null && other.preferenciaXRutinaPK != null) || (this.preferenciaXRutinaPK != null && !this.preferenciaXRutinaPK.equals(other.preferenciaXRutinaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXRutina[ preferenciaXRutinaPK=" + preferenciaXRutinaPK + " ]";
    }
    
}
