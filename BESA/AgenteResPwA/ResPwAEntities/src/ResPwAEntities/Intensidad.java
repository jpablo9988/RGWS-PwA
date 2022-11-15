/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "intensidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intensidad.findAll", query = "SELECT i FROM Intensidad i"),
    @NamedQuery(name = "Intensidad.findById", query = "SELECT i FROM Intensidad i WHERE i.id = :id"),
    @NamedQuery(name = "Intensidad.findBySeries", query = "SELECT i FROM Intensidad i WHERE i.series = :series"),
    @NamedQuery(name = "Intensidad.findByRepeticiones", query = "SELECT i FROM Intensidad i WHERE i.repeticiones = :repeticiones"),
    @NamedQuery(name = "Intensidad.findByDuracionEjercicio", query = "SELECT i FROM Intensidad i WHERE i.duracionEjercicio = :duracionEjercicio"),
    @NamedQuery(name = "Intensidad.findByPeso", query = "SELECT i FROM Intensidad i WHERE i.peso = :peso")})
public class Intensidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "series")
    private int series;
    @Basic(optional = false)
    @Column(name = "repeticiones")
    private int repeticiones;
    @Column(name = "duracion_ejercicio")
    private Integer duracionEjercicio;
    @Column(name = "peso")
    private Integer peso;
    @JoinColumn(name = "categoria_entrenamiento_tipo", referencedColumnName = "tipo")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CategoriaEntrenamiento categoriaEntrenamientoTipo;

    public Intensidad() {
    }

    public Intensidad(String id) {
        this.id = id;
    }

    public Intensidad(String id, int series, int repeticiones) {
        this.id = id;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Integer getDuracionEjercicio() {
        return duracionEjercicio;
    }

    public void setDuracionEjercicio(Integer duracionEjercicio) {
        this.duracionEjercicio = duracionEjercicio;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public CategoriaEntrenamiento getCategoriaEntrenamientoTipo() {
        return categoriaEntrenamientoTipo;
    }

    public void setCategoriaEntrenamientoTipo(CategoriaEntrenamiento categoriaEntrenamientoTipo) {
        this.categoriaEntrenamientoTipo = categoriaEntrenamientoTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intensidad)) {
            return false;
        }
        Intensidad other = (Intensidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Intensidad[ id=" + id + " ]";
    }
    
}
