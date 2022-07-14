/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Preferenciaxbaile.findAll", query = "SELECT p FROM Preferenciaxbaile p")
    , @NamedQuery(name = "Preferenciaxbaile.findByBaileId", query = "SELECT p FROM Preferenciaxbaile p WHERE p.baileId = :baileId")
    , @NamedQuery(name = "Preferenciaxbaile.findByGusto", query = "SELECT p FROM Preferenciaxbaile p WHERE p.gusto = :gusto")})
public class Preferenciaxbaile implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "baile_id", nullable = false, precision = 131089)
    private BigDecimal baileId;
    private double gusto; //Cambio de BigInteger a Double
    @JoinColumn(name = "baile_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Baile baile;
    @JoinColumn(name = "perfil_preferencia_nombrepreferido", referencedColumnName = "nombrepreferido", nullable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferenciaNombrepreferido;

    public Preferenciaxbaile() {
    }

    public Preferenciaxbaile(BigDecimal baileId) {
        this.baileId = baileId;
    }

    public BigDecimal getBaileId() {
        return baileId;
    }

    public void setBaileId(BigDecimal baileId) {
        this.baileId = baileId;
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

    public PerfilPreferencia getPerfilPreferenciaNombrepreferido() {
        return perfilPreferenciaNombrepreferido;
    }

    public void setPerfilPreferenciaNombrepreferido(PerfilPreferencia perfilPreferenciaNombrepreferido) {
        this.perfilPreferenciaNombrepreferido = perfilPreferenciaNombrepreferido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (baileId != null ? baileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferenciaxbaile)) {
            return false;
        }
        Preferenciaxbaile other = (Preferenciaxbaile) object;
        if ((this.baileId == null && other.baileId != null) || (this.baileId != null && !this.baileId.equals(other.baileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Preferenciaxbaile[ baileId=" + baileId + " ]";
    }
    
}
