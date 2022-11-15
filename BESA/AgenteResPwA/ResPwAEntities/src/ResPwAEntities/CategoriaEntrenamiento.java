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
@Table(name = "categoria_entrenamiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaEntrenamiento.findAll", query = "SELECT c FROM CategoriaEntrenamiento c"),
    @NamedQuery(name = "CategoriaEntrenamiento.findByTipo", query = "SELECT c FROM CategoriaEntrenamiento c WHERE c.tipo = :tipo")})
public class CategoriaEntrenamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @JoinTable(name = "categoria_entrenamiento_x_programa", joinColumns = {
        @JoinColumn(name = "tipo_categoria", referencedColumnName = "tipo")}, inverseJoinColumns = {
        @JoinColumn(name = "programa_ejercicio_nombre", referencedColumnName = "nombre")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<ProgramaEjercicio> programaEjercicioList;
    @JoinTable(name = "ejercicio_x_categoria", joinColumns = {
        @JoinColumn(name = "categoria_tipo", referencedColumnName = "tipo")}, inverseJoinColumns = {
        @JoinColumn(name = "ejercicio_nombre", referencedColumnName = "nombre")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Ejercicio> ejercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaEntrenamientoTipo", fetch = FetchType.EAGER)
    private List<Intensidad> intensidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoriaEntrenamiento", fetch = FetchType.EAGER)
    private List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoList;

    public CategoriaEntrenamiento() {
    }

    public CategoriaEntrenamiento(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<ProgramaEjercicio> getProgramaEjercicioList() {
        return programaEjercicioList;
    }

    public void setProgramaEjercicioList(List<ProgramaEjercicio> programaEjercicioList) {
        this.programaEjercicioList = programaEjercicioList;
    }

    @XmlTransient
    public List<Ejercicio> getEjercicioList() {
        return ejercicioList;
    }

    public void setEjercicioList(List<Ejercicio> ejercicioList) {
        this.ejercicioList = ejercicioList;
    }

    @XmlTransient
    public List<Intensidad> getIntensidadList() {
        return intensidadList;
    }

    public void setIntensidadList(List<Intensidad> intensidadList) {
        this.intensidadList = intensidadList;
    }

    @XmlTransient
    public List<DiaXCategoriaEntrenamiento> getDiaXCategoriaEntrenamientoList() {
        return diaXCategoriaEntrenamientoList;
    }

    public void setDiaXCategoriaEntrenamientoList(List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoList) {
        this.diaXCategoriaEntrenamientoList = diaXCategoriaEntrenamientoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipo != null ? tipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaEntrenamiento)) {
            return false;
        }
        CategoriaEntrenamiento other = (CategoriaEntrenamiento) object;
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.CategoriaEntrenamiento[ tipo=" + tipo + " ]";
    }
    
}
