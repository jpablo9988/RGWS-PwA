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
@Table(name = "dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dia.findAll", query = "SELECT d FROM Dia d"),
    @NamedQuery(name = "Dia.findByNombre", query = "SELECT d FROM Dia d WHERE d.nombre = :nombre")})
public class Dia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dia", fetch = FetchType.EAGER)
    private List<DiaXProgramaEjercicio> diaXProgramaEjercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dia", fetch = FetchType.EAGER)
    private List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoList;

    public Dia() {
    }

    public Dia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<DiaXProgramaEjercicio> getDiaXProgramaEjercicioList() {
        return diaXProgramaEjercicioList;
    }

    public void setDiaXProgramaEjercicioList(List<DiaXProgramaEjercicio> diaXProgramaEjercicioList) {
        this.diaXProgramaEjercicioList = diaXProgramaEjercicioList;
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
        hash += (nombre != null ? nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dia)) {
            return false;
        }
        Dia other = (Dia) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Dia[ nombre=" + nombre + " ]";
    }
    
}
