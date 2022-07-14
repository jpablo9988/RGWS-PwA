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
    @NamedQuery(name = "Cuento.findAll", query = "SELECT c FROM Cuento c")
    , @NamedQuery(name = "Cuento.findByNombre", query = "SELECT c FROM Cuento c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cuento.findByAutor", query = "SELECT c FROM Cuento c WHERE c.autor = :autor")})
public class Cuento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Column(length = 2147483647)
    private String autor;
    @JoinColumn(name = "genero_genero", referencedColumnName = "genero", nullable = false)
    @ManyToOne(optional = false)
    private Genero generoGenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentoNombre")
    private List<Preferenciaxcuento> preferenciaxcuentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuentoNombre")
    private List<Frases> frasesList;

    public Cuento() {
    }

    public Cuento(String nombre) {
        this.nombre = nombre;
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

    public Genero getGeneroGenero() {
        return generoGenero;
    }

    public void setGeneroGenero(Genero generoGenero) {
        this.generoGenero = generoGenero;
    }

    @XmlTransient
    public List<Preferenciaxcuento> getPreferenciaxcuentoList() {
        return preferenciaxcuentoList;
    }

    public void setPreferenciaxcuentoList(List<Preferenciaxcuento> preferenciaxcuentoList) {
        this.preferenciaxcuentoList = preferenciaxcuentoList;
    }

    @XmlTransient
    public List<Frases> getFrasesList() {
        return frasesList;
    }

    public void setFrasesList(List<Frases> frasesList) {
        this.frasesList = frasesList;
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
