/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "perfil_pwa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilPwa.findAll", query = "SELECT p FROM PerfilPwa p"),
    @NamedQuery(name = "PerfilPwa.findByCedula", query = "SELECT p FROM PerfilPwa p WHERE p.cedula = :cedula"),
    @NamedQuery(name = "PerfilPwa.findByNombre", query = "SELECT p FROM PerfilPwa p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PerfilPwa.findByApellido", query = "SELECT p FROM PerfilPwa p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "PerfilPwa.findByFechaNacimiento", query = "SELECT p FROM PerfilPwa p WHERE p.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "PerfilPwa.findByPaisNacimiento", query = "SELECT p FROM PerfilPwa p WHERE p.paisNacimiento = :paisNacimiento"),
    @NamedQuery(name = "PerfilPwa.findByProfesion", query = "SELECT p FROM PerfilPwa p WHERE p.profesion = :profesion"),
    @NamedQuery(name = "PerfilPwa.findByEdad", query = "SELECT p FROM PerfilPwa p WHERE p.edad = :edad"),
    @NamedQuery(name = "PerfilPwa.findByTieneProgramaEjercicio", query = "SELECT p FROM PerfilPwa p WHERE p.tieneProgramaEjercicio = :tieneProgramaEjercicio")})
public class PerfilPwa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Column(name = "pais_nacimiento")
    private String paisNacimiento;
    @Basic(optional = false)
    @Column(name = "profesion")
    private String profesion;
    @Basic(optional = false)
    @Column(name = "edad")
    private BigInteger edad;
    @Basic(optional = false)
    @Column(name = "tiene_programa_ejercicio")
    private boolean tieneProgramaEjercicio;
    @ManyToMany(mappedBy = "perfilPwaList", fetch = FetchType.EAGER)
    private List<Familiar> familiarList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilPwa", fetch = FetchType.EAGER)
    private PerfilPreferencia perfilPreferencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPwa", fetch = FetchType.EAGER)
    private List<RegistroActividad> registroActividadList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilPwa", fetch = FetchType.EAGER)
    private PerfilMedico perfilMedico;
    @JoinColumn(name = "cuidador_nombre_usuario", referencedColumnName = "nombre_usuario")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Cuidador cuidadorNombreUsuario;
    @JoinColumn(name = "estado_civil_tipo_ec", referencedColumnName = "tipo_ec")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private EstadoCivil estadoCivilTipoEc;
    @JoinColumn(name = "nivel_educativo_tipo_ne", referencedColumnName = "tipo_ne")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private NivelEducativo nivelEducativoTipoNe;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilPwa", fetch = FetchType.EAGER)
    private PerfilEjercicio perfilEjercicio;

    public PerfilPwa() {
    }

    public PerfilPwa(String cedula) {
        this.cedula = cedula;
    }

    public PerfilPwa(String cedula, String nombre, String apellido, Date fechaNacimiento, String paisNacimiento, String profesion, BigInteger edad, boolean tieneProgramaEjercicio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.paisNacimiento = paisNacimiento;
        this.profesion = profesion;
        this.edad = edad;
        this.tieneProgramaEjercicio = tieneProgramaEjercicio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public BigInteger getEdad() {
        return edad;
    }

    public void setEdad(BigInteger edad) {
        this.edad = edad;
    }

    public boolean getTieneProgramaEjercicio() {
        return tieneProgramaEjercicio;
    }

    public void setTieneProgramaEjercicio(boolean tieneProgramaEjercicio) {
        this.tieneProgramaEjercicio = tieneProgramaEjercicio;
    }

    @XmlTransient
    public List<Familiar> getFamiliarList() {
        return familiarList;
    }

    public void setFamiliarList(List<Familiar> familiarList) {
        this.familiarList = familiarList;
    }

    public PerfilPreferencia getPerfilPreferencia() {
        return perfilPreferencia;
    }

    public void setPerfilPreferencia(PerfilPreferencia perfilPreferencia) {
        this.perfilPreferencia = perfilPreferencia;
    }

    @XmlTransient
    public List<RegistroActividad> getRegistroActividadList() {
        return registroActividadList;
    }

    public void setRegistroActividadList(List<RegistroActividad> registroActividadList) {
        this.registroActividadList = registroActividadList;
    }

    public PerfilMedico getPerfilMedico() {
        return perfilMedico;
    }

    public void setPerfilMedico(PerfilMedico perfilMedico) {
        this.perfilMedico = perfilMedico;
    }

    public Cuidador getCuidadorNombreUsuario() {
        return cuidadorNombreUsuario;
    }

    public void setCuidadorNombreUsuario(Cuidador cuidadorNombreUsuario) {
        this.cuidadorNombreUsuario = cuidadorNombreUsuario;
    }

    public EstadoCivil getEstadoCivilTipoEc() {
        return estadoCivilTipoEc;
    }

    public void setEstadoCivilTipoEc(EstadoCivil estadoCivilTipoEc) {
        this.estadoCivilTipoEc = estadoCivilTipoEc;
    }

    public NivelEducativo getNivelEducativoTipoNe() {
        return nivelEducativoTipoNe;
    }

    public void setNivelEducativoTipoNe(NivelEducativo nivelEducativoTipoNe) {
        this.nivelEducativoTipoNe = nivelEducativoTipoNe;
    }

    public PerfilEjercicio getPerfilEjercicio() {
        return perfilEjercicio;
    }

    public void setPerfilEjercicio(PerfilEjercicio perfilEjercicio) {
        this.perfilEjercicio = perfilEjercicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilPwa)) {
            return false;
        }
        PerfilPwa other = (PerfilPwa) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilPwa[ cedula=" + cedula + " ]";
    }
    
}
