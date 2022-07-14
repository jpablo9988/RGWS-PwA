/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cuidador_nombreusuario"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Perfilpwa.findAll", query = "SELECT p FROM Perfilpwa p")
    , @NamedQuery(name = "Perfilpwa.findByNombre", query = "SELECT p FROM Perfilpwa p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Perfilpwa.findByApellido", query = "SELECT p FROM Perfilpwa p WHERE p.apellido = :apellido")
    , @NamedQuery(name = "Perfilpwa.findByFechanacimiento", query = "SELECT p FROM Perfilpwa p WHERE p.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "Perfilpwa.findByPaisnacimiento", query = "SELECT p FROM Perfilpwa p WHERE p.paisnacimiento = :paisnacimiento")
    , @NamedQuery(name = "Perfilpwa.findByCedula", query = "SELECT p FROM Perfilpwa p WHERE p.cedula = :cedula")
    , @NamedQuery(name = "Perfilpwa.findByProfesion", query = "SELECT p FROM Perfilpwa p WHERE p.profesion = :profesion")})
public class Perfilpwa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nombre;
    @Column(length = 2147483647)
    private String apellido;
    @Temporal(TemporalType.DATE)
    private Date fechanacimiento;
    @Column(length = 2147483647)
    private String paisnacimiento;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private String cedula;
    @Column(length = 2147483647)
    private String profesion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilpwaCedula")
    private List<Registroactividad> registroactividadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilpwaCedula")
    private List<PerfilPreferencia> perfilPreferenciaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilpwaCedula2")
    private PerfilMedico perfilMedico;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilpwaCedula")
    private Test test;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilpwaCedula")
    private List<Familiares> familiaresList;
    @JoinColumn(name = "cuidador_nombreusuario", referencedColumnName = "nombreusuario", nullable = false)
    @OneToOne(optional = false)
    private Cuidador cuidadorNombreusuario;
    @JoinColumn(name = "estadocivil_tipoec", referencedColumnName = "tipoec", nullable = false)
    @ManyToOne(optional = false)
    private Estadocivil estadocivilTipoec;
    @JoinColumn(name = "nivel_educativo_idnivel", referencedColumnName = "idnivel")
    @ManyToOne(optional = false)
    private Niveleducativo niveleducativoIdnivel;

    public Perfilpwa() {
    }

    public Perfilpwa(String cedula) {
        this.cedula = cedula;
    }
     @XmlTransient
    public List<Familiares> getFamiliarList() {
        return familiaresList;
    }

    public Perfilpwa(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
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

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getPaisnacimiento() {
        return paisnacimiento;
    }

    public void setPaisnacimiento(String paisnacimiento) {
        this.paisnacimiento = paisnacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @XmlTransient
    public List<Registroactividad> getRegistroactividadList() {
        return registroactividadList;
    }

    public void setRegistroactividadList(List<Registroactividad> registroactividadList) {
        this.registroactividadList = registroactividadList;
    }

    @XmlTransient
    public List<PerfilPreferencia> getPerfilPreferenciaList() {
        return perfilPreferenciaList;
    }

    public void setPerfilPreferenciaList(List<PerfilPreferencia> perfilPreferenciaList) {
        this.perfilPreferenciaList = perfilPreferenciaList;
    }

    public PerfilMedico getPerfilMedico() {
        return perfilMedico;
    }

    public void setPerfilMedico(PerfilMedico perfilMedico) {
        this.perfilMedico = perfilMedico;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    @XmlTransient
    public List<Familiares> getFamiliaresList() {
        return familiaresList;
    }

    public void setFamiliaresList(List<Familiares> familiaresList) {
        this.familiaresList = familiaresList;
    }

    public Cuidador getCuidadorNombreusuario() {
        return cuidadorNombreusuario;
    }

    public void setCuidadorNombreusuario(Cuidador cuidadorNombreusuario) {
        this.cuidadorNombreusuario = cuidadorNombreusuario;
    }

    public Estadocivil getEstadocivilTipoec() {
        return estadocivilTipoec;
    }

    public void setEstadocivilTipoec(Estadocivil estadocivilTipoec) {
        this.estadocivilTipoec = estadocivilTipoec;
    }

    public Niveleducativo getNiveleducativoIdnivel() {
        return niveleducativoIdnivel;
    }

    public void setNiveleducativoIdnivel(Niveleducativo niveleducativoIdnivel) {
        this.niveleducativoIdnivel = niveleducativoIdnivel;
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
        if (!(object instanceof Perfilpwa)) {
            return false;
        }
        Perfilpwa other = (Perfilpwa) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Perfilpwa[ cedula=" + cedula + " ]";
    }

    public void setFamiliarList(ArrayList<Familiar> arrayList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setPerfilPreferencia(PerfilPreferencia perfilPreferencia) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PerfilPreferencia getPerfilPreferencia() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
