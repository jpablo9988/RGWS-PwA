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
@Table(name = "programa_ejercicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramaEjercicio.findAll", query = "SELECT p FROM ProgramaEjercicio p"),
    @NamedQuery(name = "ProgramaEjercicio.findByNombre", query = "SELECT p FROM ProgramaEjercicio p WHERE p.nombre = :nombre")})
public class ProgramaEjercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @ManyToMany(mappedBy = "programaEjercicioList", fetch = FetchType.EAGER)
    private List<CategoriaEntrenamiento> categoriaEntrenamientoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaEjercicio", fetch = FetchType.EAGER)
    private List<DiaXProgramaEjercicio> diaXProgramaEjercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombrePrograma", fetch = FetchType.EAGER)
    private List<PerfilEjercicio> perfilEjercicioList;

    public ProgramaEjercicio() {
    }

    public ProgramaEjercicio(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<CategoriaEntrenamiento> getCategoriaEntrenamientoList() {
        return categoriaEntrenamientoList;
    }

    public void setCategoriaEntrenamientoList(List<CategoriaEntrenamiento> categoriaEntrenamientoList) {
        this.categoriaEntrenamientoList = categoriaEntrenamientoList;
    }

    @XmlTransient
    public List<DiaXProgramaEjercicio> getDiaXProgramaEjercicioList() {
        return diaXProgramaEjercicioList;
    }

    public void setDiaXProgramaEjercicioList(List<DiaXProgramaEjercicio> diaXProgramaEjercicioList) {
        this.diaXProgramaEjercicioList = diaXProgramaEjercicioList;
    }

    @XmlTransient
    public List<PerfilEjercicio> getPerfilEjercicioList() {
        return perfilEjercicioList;
    }

    public void setPerfilEjercicioList(List<PerfilEjercicio> perfilEjercicioList) {
        this.perfilEjercicioList = perfilEjercicioList;
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
        if (!(object instanceof ProgramaEjercicio)) {
            return false;
        }
        ProgramaEjercicio other = (ProgramaEjercicio) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.ProgramaEjercicio[ nombre=" + nombre + " ]";
    }
    
}
