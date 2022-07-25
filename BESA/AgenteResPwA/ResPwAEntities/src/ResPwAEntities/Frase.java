/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
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
 * @author USER
 */
@Entity
@Table(name = "frase", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Frase.findAll", query = "SELECT f FROM Frase f"),
    @NamedQuery(name = "Frase.findByOrden", query = "SELECT f FROM Frase f WHERE f.frasePK.orden = :orden"),
    @NamedQuery(name = "Frase.findByCuentoNombre", query = "SELECT f FROM Frase f WHERE f.frasePK.cuentoNombre = :cuentoNombre"),
    @NamedQuery(name = "Frase.findByContenido", query = "SELECT f FROM Frase f WHERE f.contenido = :contenido"),
    @NamedQuery(name = "Frase.findByEmotionalEvent", query = "SELECT f FROM Frase f WHERE f.emotionalEvent = :emotionalEvent"),
    @NamedQuery(name = "Frase.findByAccion", query = "SELECT f FROM Frase f WHERE f.accion = :accion"),
    @NamedQuery(name = "Frase.findByUrlImagen", query = "SELECT f FROM Frase f WHERE f.urlImagen = :urlImagen")})
public class Frase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FrasePK frasePK;
    @Basic(optional = false)
    @Column(name = "contenido")
    private String contenido;
    @Column(name = "emotional_event")
    private String emotionalEvent;
    @Column(name = "accion")
    private String accion;
    @Column(name = "url_imagen")
    private String urlImagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "frase")
    private List<Enriq> enriqList;
    @JoinColumn(name = "cuento_nombre", referencedColumnName = "nombre", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cuento cuento;
    @JoinColumn(name = "rutina_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rutina rutinaId;

    public Frase() {
    }

    public Frase(FrasePK frasePK) {
        this.frasePK = frasePK;
    }

    public Frase(FrasePK frasePK, String contenido) {
        this.frasePK = frasePK;
        this.contenido = contenido;
    }

    public Frase(BigDecimal orden, String cuentoNombre) {
        this.frasePK = new FrasePK(orden, cuentoNombre);
    }

    public FrasePK getFrasePK() {
        return frasePK;
    }

    public void setFrasePK(FrasePK frasePK) {
        this.frasePK = frasePK;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEmotionalEvent() {
        return emotionalEvent;
    }

    public void setEmotionalEvent(String emotionalEvent) {
        this.emotionalEvent = emotionalEvent;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @XmlTransient
    public List<Enriq> getEnriqList() {
        return enriqList;
    }

    public void setEnriqList(List<Enriq> enriqList) {
        this.enriqList = enriqList;
    }

    public Cuento getCuento() {
        return cuento;
    }

    public void setCuento(Cuento cuento) {
        this.cuento = cuento;
    }

    public Rutina getRutinaId() {
        return rutinaId;
    }

    public void setRutinaId(Rutina rutinaId) {
        this.rutinaId = rutinaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (frasePK != null ? frasePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Frase)) {
            return false;
        }
        Frase other = (Frase) object;
        if ((this.frasePK == null && other.frasePK != null) || (this.frasePK != null && !this.frasePK.equals(other.frasePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Frase[ frasePK=" + frasePK + " ]";
    }
    
}
