/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Actxpreferencia.findAll", query = "SELECT a FROM Actxpreferencia a")
    , @NamedQuery(name = "Actxpreferencia.findByActividadpwaId", query = "SELECT a FROM Actxpreferencia a WHERE a.actxpreferenciaPK.actividadpwaId = :actividadpwaId")
    , @NamedQuery(name = "Actxpreferencia.findByPerfilPreferenciaCedula", query = "SELECT a FROM Actxpreferencia a WHERE a.actxpreferenciaPK.perfilPreferenciaCedula = :perfilPreferenciaCedula")
    , @NamedQuery(name = "Actxpreferencia.findByActiva", query = "SELECT a FROM Actxpreferencia a WHERE a.activa = :activa")
    , @NamedQuery(name = "Actxpreferencia.findByGusto", query = "SELECT a FROM Actxpreferencia a WHERE a.gusto = :gusto")
    , @NamedQuery(name = "Actxpreferencia.findByEnriq", query = "SELECT a FROM Actxpreferencia a WHERE a.enriq = :enriq")})
public class Actxpreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActxpreferenciaPK actxpreferenciaPK;
    @Column(length = 2147483647)
    private String activa;
    @Column(length = 2147483647)
    private double gusto;
    @Column(length = 2147483647)
    private String enriq;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actxpreferencia")
    private List<PerfilPreferencia> perfilPreferenciaList;
    @JoinColumn(name = "actividadpwa_id2", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Actividadpwa actividadpwaId2;

    public Actxpreferencia() {
    }

    public Actxpreferencia(ActxpreferenciaPK actxpreferenciaPK) {
        this.actxpreferenciaPK = actxpreferenciaPK;
    }

    public Actxpreferencia(int actividadpwaId, String perfilPreferenciaCedula) {
        this.actxpreferenciaPK = new ActxpreferenciaPK(actividadpwaId, perfilPreferenciaCedula);
    }

    public ActxpreferenciaPK getActxpreferenciaPK() {
        return actxpreferenciaPK;
    }

    public void setActxpreferenciaPK(ActxpreferenciaPK actxpreferenciaPK) {
        this.actxpreferenciaPK = actxpreferenciaPK;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
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

    @XmlTransient
    public List<PerfilPreferencia> getPerfilPreferenciaList() {
        return perfilPreferenciaList;
    }

    public void setPerfilPreferenciaList(List<PerfilPreferencia> perfilPreferenciaList) {
        this.perfilPreferenciaList = perfilPreferenciaList;
    }

    public Actividadpwa getActividadpwa() {
        return actividadpwaId2;
    }

    public void setActividadpwa(Actividadpwa actividadpwaId2) {
        this.actividadpwaId2 = actividadpwaId2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actxpreferenciaPK != null ? actxpreferenciaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actxpreferencia)) {
            return false;
        }
        Actxpreferencia other = (Actxpreferencia) object;
        if ((this.actxpreferenciaPK == null && other.actxpreferenciaPK != null) || (this.actxpreferenciaPK != null && !this.actxpreferenciaPK.equals(other.actxpreferenciaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Actxpreferencia[ actxpreferenciaPK=" + actxpreferenciaPK + " ]";
    }
    
}
