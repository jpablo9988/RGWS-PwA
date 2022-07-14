/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "Preferenciaxcancion.findAll", query = "SELECT p FROM Preferenciaxcancion p")
    , @NamedQuery(name = "Preferenciaxcancion.findByCancionNombre", query = "SELECT p FROM Preferenciaxcancion p WHERE p.preferenciaxcancionPK.cancionNombre = :cancionNombre")
    , @NamedQuery(name = "Preferenciaxcancion.findByPerfilPreferenciaNombrepreferido", query = "SELECT p FROM Preferenciaxcancion p WHERE p.preferenciaxcancionPK.perfilPreferenciaNombrepreferido = :perfilPreferenciaNombrepreferido")
    , @NamedQuery(name = "Preferenciaxcancion.findByGustos", query = "SELECT p FROM Preferenciaxcancion p WHERE p.gustos = :gustos")
    , @NamedQuery(name = "Preferenciaxcancion.findByReminiscencia", query = "SELECT p FROM Preferenciaxcancion p WHERE p.reminiscencia = :reminiscencia")})
public class Preferenciaxcancion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferenciaxcancionPK preferenciaxcancionPK;
    private double gusto; //Cambiar de BigInteger a double.
    private BigInteger reminiscencia;
    @JoinColumn(name = "cancion_nombre", referencedColumnName = "nombre", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "perfil_preferencia_nombrepreferido", referencedColumnName = "nombrepreferido", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferencia;

    public Preferenciaxcancion() {
    }

    public Preferenciaxcancion(PreferenciaxcancionPK preferenciaxcancionPK) {
        this.preferenciaxcancionPK = preferenciaxcancionPK;
    }

    public Preferenciaxcancion(String cancionNombre, String perfilPreferenciaNombrepreferido) {
        this.preferenciaxcancionPK = new PreferenciaxcancionPK(cancionNombre, perfilPreferenciaNombrepreferido);
    }

    public PreferenciaxcancionPK getPreferenciaxcancionPK() {
        return preferenciaxcancionPK;
    }

    public void setPreferenciaxcancionPK(PreferenciaxcancionPK preferenciaxcancionPK) {
        this.preferenciaxcancionPK = preferenciaxcancionPK;
    }

    public double getGusto() {
        return gusto;
    }

    public void setGusto(double gusto) {
        this.gusto = gusto;
    }

    public BigInteger getReminiscencia() {
        return reminiscencia;
    }

    public void setReminiscencia(BigInteger reminiscencia) {
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
        hash += (preferenciaxcancionPK != null ? preferenciaxcancionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferenciaxcancion)) {
            return false;
        }
        Preferenciaxcancion other = (Preferenciaxcancion) object;
        if ((this.preferenciaxcancionPK == null && other.preferenciaxcancionPK != null) || (this.preferenciaxcancionPK != null && !this.preferenciaxcancionPK.equals(other.preferenciaxcancionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Preferenciaxcancion[ preferenciaxcancionPK=" + preferenciaxcancionPK + " ]";
    }
    
}
