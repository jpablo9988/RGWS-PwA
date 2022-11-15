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
@Table(name = "cuidador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuidador.findAll", query = "SELECT c FROM Cuidador c"),
    @NamedQuery(name = "Cuidador.findByNombreUsuario", query = "SELECT c FROM Cuidador c WHERE c.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Cuidador.findByContrasena", query = "SELECT c FROM Cuidador c WHERE c.contrasena = :contrasena"),
    @NamedQuery(name = "Cuidador.findByNombre", query = "SELECT c FROM Cuidador c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Cuidador.findByCorreo", query = "SELECT c FROM Cuidador c WHERE c.correo = :correo"),
    @NamedQuery(name = "Cuidador.findByCelular", query = "SELECT c FROM Cuidador c WHERE c.celular = :celular")})
public class Cuidador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic(optional = false)
    @Column(name = "contrasena")
    private String contrasena;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "celular")
    private String celular;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuidadorNombreUsuario", fetch = FetchType.EAGER)
    private List<PerfilPwa> perfilPwaList;

    public Cuidador() {
    }

    public Cuidador(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Cuidador(String nombreUsuario, String contrasena, String nombre, String correo, String celular) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @XmlTransient
    public List<PerfilPwa> getPerfilPwaList() {
        return perfilPwaList;
    }

    public void setPerfilPwaList(List<PerfilPwa> perfilPwaList) {
        this.perfilPwaList = perfilPwaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombreUsuario != null ? nombreUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuidador)) {
            return false;
        }
        Cuidador other = (Cuidador) object;
        if ((this.nombreUsuario == null && other.nombreUsuario != null) || (this.nombreUsuario != null && !this.nombreUsuario.equals(other.nombreUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Cuidador[ nombreUsuario=" + nombreUsuario + " ]";
    }
    
}
