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
@Table(name = "frase_inspiracional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FraseInspiracional.findAll", query = "SELECT f FROM FraseInspiracional f"),
    @NamedQuery(name = "FraseInspiracional.findById", query = "SELECT f FROM FraseInspiracional f WHERE f.fraseInspiracionalPK.id = :id"),
    @NamedQuery(name = "FraseInspiracional.findByEjercicio", query = "SELECT f FROM FraseInspiracional f WHERE f.fraseInspiracionalPK.ejercicio = :ejercicio"),
    @NamedQuery(name = "FraseInspiracional.findByContenidos", query = "SELECT f FROM FraseInspiracional f WHERE f.contenidos = :contenidos"),
    @NamedQuery(name = "FraseInspiracional.findByTiempoEjecucion", query = "SELECT f FROM FraseInspiracional f WHERE f.tiempoEjecucion = :tiempoEjecucion"),
    @NamedQuery(name = "FraseInspiracional.findByPwaEmocion", query = "SELECT f FROM FraseInspiracional f WHERE f.pwaEmocion = :pwaEmocion")})
public class FraseInspiracional implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FraseInspiracionalPK fraseInspiracionalPK;
    @Basic(optional = false)
    @Column(name = "contenidos")
    private String contenidos;
    @Basic(optional = false)
    @Column(name = "tiempo_ejecucion")
    private double tiempoEjecucion;
    @Basic(optional = false)
    @Column(name = "pwa_emocion")
    private String pwaEmocion;
    @JoinColumn(name = "ejercicio", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Ejercicio ejercicio1;

    public FraseInspiracional() {
    }

    public FraseInspiracional(FraseInspiracionalPK fraseInspiracionalPK) {
        this.fraseInspiracionalPK = fraseInspiracionalPK;
    }

    public FraseInspiracional(FraseInspiracionalPK fraseInspiracionalPK, String contenidos, double tiempoEjecucion, String pwaEmocion) {
        this.fraseInspiracionalPK = fraseInspiracionalPK;
        this.contenidos = contenidos;
        this.tiempoEjecucion = tiempoEjecucion;
        this.pwaEmocion = pwaEmocion;
    }

    public FraseInspiracional(int id, String ejercicio) {
        this.fraseInspiracionalPK = new FraseInspiracionalPK(id, ejercicio);
    }

    public FraseInspiracionalPK getFraseInspiracionalPK() {
        return fraseInspiracionalPK;
    }

    public void setFraseInspiracionalPK(FraseInspiracionalPK fraseInspiracionalPK) {
        this.fraseInspiracionalPK = fraseInspiracionalPK;
    }

    public String getContenidos() {
        return contenidos;
    }

    public void setContenidos(String contenidos) {
        this.contenidos = contenidos;
    }

    public double getTiempoEjecucion() {
        return tiempoEjecucion;
    }

    public void setTiempoEjecucion(double tiempoEjecucion) {
        this.tiempoEjecucion = tiempoEjecucion;
    }

    public String getPwaEmocion() {
        return pwaEmocion;
    }

    public void setPwaEmocion(String pwaEmocion) {
        this.pwaEmocion = pwaEmocion;
    }

    public Ejercicio getEjercicio1() {
        return ejercicio1;
    }

    public void setEjercicio1(Ejercicio ejercicio1) {
        this.ejercicio1 = ejercicio1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fraseInspiracionalPK != null ? fraseInspiracionalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FraseInspiracional)) {
            return false;
        }
        FraseInspiracional other = (FraseInspiracional) object;
        if ((this.fraseInspiracionalPK == null && other.fraseInspiracionalPK != null) || (this.fraseInspiracionalPK != null && !this.fraseInspiracionalPK.equals(other.fraseInspiracionalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.FraseInspiracional[ fraseInspiracionalPK=" + fraseInspiracionalPK + " ]";
    }
    
}
