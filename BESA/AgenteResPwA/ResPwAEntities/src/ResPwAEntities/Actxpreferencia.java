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
@Table(name = "act_x_preferencia", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActXPreferencia.findAll", query = "SELECT a FROM ActXPreferencia a"),
    @NamedQuery(name = "ActXPreferencia.findByActividadPwaId", query = "SELECT a FROM ActXPreferencia a WHERE a.actXPreferenciaPK.actividadPwaId = :actividadPwaId"),
    @NamedQuery(name = "ActXPreferencia.findByPreferenciaPwaCedula", query = "SELECT a FROM ActXPreferencia a WHERE a.actXPreferenciaPK.preferenciaPwaCedula = :preferenciaPwaCedula"),
    @NamedQuery(name = "ActXPreferencia.findByActiva", query = "SELECT a FROM ActXPreferencia a WHERE a.activa = :activa"),
    @NamedQuery(name = "ActXPreferencia.findByGusto", query = "SELECT a FROM ActXPreferencia a WHERE a.gusto = :gusto"),
    @NamedQuery(name = "ActXPreferencia.findByEnriq", query = "SELECT a FROM ActXPreferencia a WHERE a.enriq = :enriq")})
public class ActXPreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActXPreferenciaPK actXPreferenciaPK;
    @Basic(optional = false)
    @Column(name = "activa")
    private BigDecimal activa;
    @Basic(optional = false)
    @Column(name = "gusto")
    private double gusto;
    @Column(name = "enriq")
    private String enriq;
    @JoinColumn(name = "actividad_pwa_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ActividadPwa actividadPwa;
    @JoinColumn(name = "preferencia_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferencia;

    public ActXPreferencia() {
    }

    public ActXPreferencia(ActXPreferenciaPK actXPreferenciaPK) {
        this.actXPreferenciaPK = actXPreferenciaPK;
    }

    public ActXPreferencia(ActXPreferenciaPK actXPreferenciaPK, BigDecimal activa, double gusto) {
        this.actXPreferenciaPK = actXPreferenciaPK;
        this.activa = activa;
        this.gusto = gusto;
    }

    public ActXPreferencia(BigDecimal actividadPwaId, String preferenciaPwaCedula) {
        this.actXPreferenciaPK = new ActXPreferenciaPK(actividadPwaId, preferenciaPwaCedula);
    }

    public ActXPreferenciaPK getActXPreferenciaPK() {
        return actXPreferenciaPK;
    }

    public void setActXPreferenciaPK(ActXPreferenciaPK actXPreferenciaPK) {
        this.actXPreferenciaPK = actXPreferenciaPK;
    }

    public BigDecimal getActiva() {
        return activa;
    }

    public void setActiva(BigDecimal activa) {
        this.activa = activa;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public String getEnriq() {
        return enriq;
    }

    public void setEnriq(String enriq) {
        this.enriq = enriq;
    }

    public ActividadPwa getActividadPwa() {
        return actividadPwa;
    }

    public void setActividadPwa(ActividadPwa actividadPwa) {
        this.actividadPwa = actividadPwa;
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
        hash += (actXPreferenciaPK != null ? actXPreferenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActXPreferencia)) {
            return false;
        }
        ActXPreferencia other = (ActXPreferencia) object;
        if ((this.actXPreferenciaPK == null && other.actXPreferenciaPK != null) || (this.actXPreferenciaPK != null && !this.actXPreferenciaPK.equals(other.actXPreferenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.ActXPreferencia[ actXPreferenciaPK=" + actXPreferenciaPK + " ]";
    }
    
}
