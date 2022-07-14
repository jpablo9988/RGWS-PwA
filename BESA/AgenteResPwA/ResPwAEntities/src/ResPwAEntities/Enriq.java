/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
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
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enriq.findAll", query = "SELECT e FROM Enriq e")
    , @NamedQuery(name = "Enriq.findByParams", query = "SELECT e FROM Enriq e WHERE e.enriqPK.params = :params")
    , @NamedQuery(name = "Enriq.findByValor", query = "SELECT e FROM Enriq e WHERE e.valor = :valor")
    , @NamedQuery(name = "Enriq.findByFrasesOrden", query = "SELECT e FROM Enriq e WHERE e.enriqPK.frasesOrden = :frasesOrden")})
public class Enriq implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EnriqPK enriqPK;
    @Column(length = 2147483647)
    private String valor;
    @JoinColumn(name = "frases_orden", referencedColumnName = "orden", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Frases frases;

    public Enriq() {
    }

    public Enriq(EnriqPK enriqPK) {
        this.enriqPK = enriqPK;
    }

    public Enriq(String params, int frasesOrden) {
        this.enriqPK = new EnriqPK(params, frasesOrden);
    }

    public EnriqPK getEnriqPK() {
        return enriqPK;
    }

    public void setEnriqPK(EnriqPK enriqPK) {
        this.enriqPK = enriqPK;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Frases getFrases() {
        return frases;
    }

    public void setFrases(Frases frases) {
        this.frases = frases;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enriqPK != null ? enriqPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enriq)) {
            return false;
        }
        Enriq other = (Enriq) object;
        if ((this.enriqPK == null && other.enriqPK != null) || (this.enriqPK != null && !this.enriqPK.equals(other.enriqPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Enriq[ enriqPK=" + enriqPK + " ]";
    }
    
}
