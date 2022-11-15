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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "ejercicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ejercicio.findAll", query = "SELECT e FROM Ejercicio e"),
    @NamedQuery(name = "Ejercicio.findByNombre", query = "SELECT e FROM Ejercicio e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Ejercicio.findByDuracion", query = "SELECT e FROM Ejercicio e WHERE e.duracion = :duracion"),
    @NamedQuery(name = "Ejercicio.findByNecesitaPeso", query = "SELECT e FROM Ejercicio e WHERE e.necesitaPeso = :necesitaPeso"),
    @NamedQuery(name = "Ejercicio.findByUrlVideo", query = "SELECT e FROM Ejercicio e WHERE e.urlVideo = :urlVideo")})
public class Ejercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "duracion")
    private double duracion;
    @Basic(optional = false)
    @Column(name = "necesita_peso")
    private boolean necesitaPeso;
    @Column(name = "url_video")
    private String urlVideo;
    @ManyToMany(mappedBy = "ejercicioList", fetch = FetchType.EAGER)
    private List<CategoriaEntrenamiento> categoriaEntrenamientoList;
    @JoinTable(name = "perfil_ejercicio_x_ejercicio", joinColumns = {
        @JoinColumn(name = "nombre_ejercicio", referencedColumnName = "nombre")}, inverseJoinColumns = {
        @JoinColumn(name = "perfil_pwa_cedula", referencedColumnName = "perfil_pwa_cedula")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<PerfilEjercicio> perfilEjercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ejercicio1", fetch = FetchType.EAGER)
    private List<FraseInspiracional> fraseInspiracionalList;

    public Ejercicio() {
    }

    public Ejercicio(String nombre) {
        this.nombre = nombre;
    }

    public Ejercicio(String nombre, double duracion, boolean necesitaPeso) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.necesitaPeso = necesitaPeso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public boolean getNecesitaPeso() {
        return necesitaPeso;
    }

    public void setNecesitaPeso(boolean necesitaPeso) {
        this.necesitaPeso = necesitaPeso;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    @XmlTransient
    public List<CategoriaEntrenamiento> getCategoriaEntrenamientoList() {
        return categoriaEntrenamientoList;
    }

    public void setCategoriaEntrenamientoList(List<CategoriaEntrenamiento> categoriaEntrenamientoList) {
        this.categoriaEntrenamientoList = categoriaEntrenamientoList;
    }

    @XmlTransient
    public List<PerfilEjercicio> getPerfilEjercicioList() {
        return perfilEjercicioList;
    }

    public void setPerfilEjercicioList(List<PerfilEjercicio> perfilEjercicioList) {
        this.perfilEjercicioList = perfilEjercicioList;
    }

    @XmlTransient
    public List<FraseInspiracional> getFraseInspiracionalList() {
        return fraseInspiracionalList;
    }

    public void setFraseInspiracionalList(List<FraseInspiracional> fraseInspiracionalList) {
        this.fraseInspiracionalList = fraseInspiracionalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ejercicio)) {
            return false;
        }
        Ejercicio other = (Ejercicio) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Ejercicio[ nombre=" + nombre + " ]";
    }
    
}
