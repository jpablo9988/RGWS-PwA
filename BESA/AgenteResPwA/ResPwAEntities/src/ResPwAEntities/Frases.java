/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @NamedQuery(name = "Frases.findAll", query = "SELECT f FROM Frases f")
    , @NamedQuery(name = "Frases.findByOrden", query = "SELECT f FROM Frases f WHERE f.orden = :orden")
    , @NamedQuery(name = "Frases.findByContenido", query = "SELECT f FROM Frases f WHERE f.contenido = :contenido")
    , @NamedQuery(name = "Frases.findByEmotionalevent", query = "SELECT f FROM Frases f WHERE f.emotionalevent = :emotionalevent")
    , @NamedQuery(name = "Frases.findByAccion", query = "SELECT f FROM Frases f WHERE f.accion = :accion")
    , @NamedQuery(name = "Frases.findByUrlimagen", query = "SELECT f FROM Frases f WHERE f.urlimagen = :urlimagen")})
public class Frases implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer orden;
    @Column(length = 2147483647)
    private String contenido;
    @Column(length = 2147483647)
    private String emotionalevent;
    @Column(length = 2147483647)
    private String accion;
    @Column(length = 2147483647)
    private String urlimagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "frases")
    private List<Enriq> enriqList;
    @JoinColumn(name = "cuento_nombre", referencedColumnName = "nombre", nullable = false)
    @ManyToOne(optional = false)
    private Cuento cuento;
    @JoinColumn(name = "rutinas_id_1", referencedColumnName = "id_1", nullable = false)
    @ManyToOne(optional = false)
    private Rutinas rutinasId1;

    public Frases() {
    }

    public Frases(Integer orden) {
        this.orden = orden;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getEmotionalevent() {
        return emotionalevent;
    }

    public void setEmotionalevent(String emotionalevent) {
        this.emotionalevent = emotionalevent;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
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

    public Rutinas getRutinasId1() {
        return rutinasId1;
    }

    public void setRutinasId1(Rutinas rutinasId1) {
        this.rutinasId1 = rutinasId1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orden != null ? orden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Frases)) {
            return false;
        }
        Frases other = (Frases) object;
        if ((this.orden == null && other.orden != null) || (this.orden != null && !this.orden.equals(other.orden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Frases[ orden=" + orden + " ]";
    }
    
}
