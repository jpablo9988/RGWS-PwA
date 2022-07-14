/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
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
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Preferenciaxcuento.findAll", query = "SELECT p FROM Preferenciaxcuento p")
    , @NamedQuery(name = "Preferenciaxcuento.findByGusto", query = "SELECT p FROM Preferenciaxcuento p WHERE p.preferenciaxcuentoPK.gusto = :gusto")
    , @NamedQuery(name = "Preferenciaxcuento.findByPerfilPreferenciaNombrepreferido", query = "SELECT p FROM Preferenciaxcuento p WHERE p.preferenciaxcuentoPK.perfilPreferenciaNombrepreferido = :perfilPreferenciaNombrepreferido")})
public class Preferenciaxcuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaxcuentoPK preferenciaxcuentoPK;
    private double gusto; //Cambio de BigInteger a Double
    @JoinColumn(name = "cuento_nombre", referencedColumnName = "nombre", nullable = false)
    @ManyToOne(optional = false)
    private Cuento cuento;
    @JoinColumn(name = "perfil_preferencia_nombrepreferido", referencedColumnName = "nombrepreferido", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferencia;

    public Preferenciaxcuento() {
    }

    public Preferenciaxcuento(PreferenciaxcuentoPK preferenciaxcuentoPK) {
        this.preferenciaxcuentoPK = preferenciaxcuentoPK;
    }

    public Preferenciaxcuento(String perfilPreferenciaPerfilpwaCedula, String cuentoNombre) {
        this.preferenciaxcuentoPK = new PreferenciaxcuentoPK(perfilPreferenciaPerfilpwaCedula, cuentoNombre);
    }

    public PreferenciaxcuentoPK getPreferenciaxcuentoPK() {
        return preferenciaxcuentoPK;
    }

    public void setPreferenciaxcuentoPK(PreferenciaxcuentoPK preferenciaxcuentoPK) {
        this.preferenciaxcuentoPK = preferenciaxcuentoPK;
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
        hash += (preferenciaxcuentoPK != null ? preferenciaxcuentoPK.hashCode() : 0);
        return hash;
    }
    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferenciaxcuento)) {
            return false;
        }
        Preferenciaxcuento other = (Preferenciaxcuento) object;
        if ((this.preferenciaxcuentoPK == null && other.preferenciaxcuentoPK != null) || (this.preferenciaxcuentoPK != null && !this.preferenciaxcuentoPK.equals(other.preferenciaxcuentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Preferenciaxcuento[ preferenciaxcuentoPK=" + preferenciaxcuentoPK + " ]";
    }
    
}
