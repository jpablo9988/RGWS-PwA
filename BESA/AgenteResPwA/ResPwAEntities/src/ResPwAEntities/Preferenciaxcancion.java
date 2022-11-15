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
@Table(name = "preferencia_x_cancion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreferenciaXCancion.findAll", query = "SELECT p FROM PreferenciaXCancion p"),
    @NamedQuery(name = "PreferenciaXCancion.findByCancionNombre", query = "SELECT p FROM PreferenciaXCancion p WHERE p.preferenciaXCancionPK.cancionNombre = :cancionNombre"),
    @NamedQuery(name = "PreferenciaXCancion.findByPreferenciaPwaCedula", query = "SELECT p FROM PreferenciaXCancion p WHERE p.preferenciaXCancionPK.preferenciaPwaCedula = :preferenciaPwaCedula"),
    @NamedQuery(name = "PreferenciaXCancion.findByGusto", query = "SELECT p FROM PreferenciaXCancion p WHERE p.gusto = :gusto"),
    @NamedQuery(name = "PreferenciaXCancion.findByReminiscencia", query = "SELECT p FROM PreferenciaXCancion p WHERE p.reminiscencia = :reminiscencia")})
public class PreferenciaXCancion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaXCancionPK preferenciaXCancionPK;
    @Basic(optional = false)
    @Column(name = "gusto")
    private double gusto;
    @Basic(optional = false)
    @Column(name = "reminiscencia")
    private int reminiscencia;
    @JoinColumn(name = "cancion_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cancion cancion;
    @JoinColumn(name = "preferencia_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilPreferencia perfilPreferencia;

    public PreferenciaXCancion() {
    }

    public PreferenciaXCancion(PreferenciaXCancionPK preferenciaXCancionPK) {
        this.preferenciaXCancionPK = preferenciaXCancionPK;
    }

    public PreferenciaXCancion(PreferenciaXCancionPK preferenciaXCancionPK, double gusto, int reminiscencia) {
        this.preferenciaXCancionPK = preferenciaXCancionPK;
        this.gusto = gusto;
        this.reminiscencia = reminiscencia;
    }

    public PreferenciaXCancion(String cancionNombre, String preferenciaPwaCedula) {
        this.preferenciaXCancionPK = new PreferenciaXCancionPK(cancionNombre, preferenciaPwaCedula);
    }

    public PreferenciaXCancionPK getPreferenciaXCancionPK() {
        return preferenciaXCancionPK;
    }

    public void setPreferenciaXCancionPK(PreferenciaXCancionPK preferenciaXCancionPK) {
        this.preferenciaXCancionPK = preferenciaXCancionPK;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public int getReminiscencia() {
        return reminiscencia;
    }

    public void setReminiscencia(int reminiscencia) {
        this.reminiscencia = reminiscencia;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
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
        hash += (preferenciaXCancionPK != null ? preferenciaXCancionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferenciaXCancion)) {
            return false;
        }
        PreferenciaXCancion other = (PreferenciaXCancion) object;
        if ((this.preferenciaXCancionPK == null && other.preferenciaXCancionPK != null) || (this.preferenciaXCancionPK != null && !this.preferenciaXCancionPK.equals(other.preferenciaXCancionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PreferenciaXCancion[ preferenciaXCancionPK=" + preferenciaXCancionPK + " ]";
    }
    
}
