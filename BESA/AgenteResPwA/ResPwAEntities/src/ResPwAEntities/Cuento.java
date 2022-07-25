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
 * @author USER
 */
@Entity
@Table(name = "cuento", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuento.findAll", query = "SELECT c FROM Cuento c"),
    @NamedQuery(name = "Cuento.findByNombre", query = "SELECT c FROM Cuento c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cuento.findByAutor", query = "SELECT c FROM Cuento c WHERE c.autor = :autor")})
public class Cuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "autor")
    private String autor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuento")
    private List<PreferenciaXCuento> preferenciaXCuentoList;
    @JoinColumn(name = "genero", referencedColumnName = "genero")
    @ManyToOne(optional = false)
    private Genero genero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuento")
    private List<Frase> fraseList;

    public Cuento() {
    }

    public Cuento(String nombre) {
        this.nombre = nombre;
    }

    public Cuento(String nombre, String autor) {
        this.nombre = nombre;
        this.autor = autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @XmlTransient
    public List<PreferenciaXCuento> getPreferenciaXCuentoList() {
        return preferenciaXCuentoList;
    }

    public void setPreferenciaXCuentoList(List<PreferenciaXCuento> preferenciaXCuentoList) {
        this.preferenciaXCuentoList = preferenciaXCuentoList;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    @XmlTransient
    public List<Frase> getFraseList() {
        return fraseList;
    }

    public void setFraseList(List<Frase> fraseList) {
        this.fraseList = fraseList;
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
        if (!(object instanceof Cuento)) {
            return false;
        }
        Cuento other = (Cuento) object;
        if ((this.nombre == null && other.nombre != null) || (this.nombre != null && !this.nombre.equals(other.nombre))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Cuento[ nombre=" + nombre + " ]";
    }
    
}
