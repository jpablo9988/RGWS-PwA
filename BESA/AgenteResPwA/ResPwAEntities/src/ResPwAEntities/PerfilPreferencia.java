/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.List;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "perfil_preferencia", catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilPreferencia.findAll", query = "SELECT p FROM PerfilPreferencia p")
    , @NamedQuery(name = "PerfilPreferencia.findByNombrepreferido", query = "SELECT p FROM PerfilPreferencia p WHERE p.nombrepreferido = :nombrepreferido")
    , @NamedQuery(name = "PerfilPreferencia.findByGustosejercicio", query = "SELECT p FROM PerfilPreferencia p WHERE p.gustosejercicio = :gustosejercicio")
    , @NamedQuery(name = "PerfilPreferencia.findByHobbies", query = "SELECT p FROM PerfilPreferencia p WHERE p.hobbies = :hobbies")})
public class PerfilPreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nombrepreferido;
    @Column(length = 2147483647)
    private String gustosejercicio;
    @Column(length = 2147483647)
    private String hobbies;
    @Basic(optional = false)
    @Column(name = "BRILLOPREFERIDO")
    private BigDecimal brillopreferido;
    @Basic(optional = false)
    @Column(name = "VOLPREFERIDO")
    private BigDecimal volpreferido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia")
    private List<Preferenciaxcuento> preferenciaxcuentoList;
    @JoinColumns({
        @JoinColumn(name = "actxpreferencia_perfil_preferencia_cedula", referencedColumnName = "perfil_preferencia_cedula", nullable = false)
        , @JoinColumn(name = "actxpreferencia_actividadpwa_id", referencedColumnName = "actividadpwa_id", nullable = false)})
    
    @JoinColumn(name = "perfilpwa_cedula", referencedColumnName = "cedula", nullable = false)
    @ManyToOne(optional = false)
    private Perfilpwa perfilpwaCedula;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferenciaNombrepreferido")
    private List<Preferenciaxbaile> preferenciaxbaileList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia")
    private List<Preferenciaxcancion> preferenciaxcancionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferenciaNombrepreferido")
    private List<Preferenciasxrutina> preferenciasxrutinaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia")
    private List<Actxpreferencia> actxpreferenciaList;

    public PerfilPreferencia() {
    }

    public PerfilPreferencia(String nombrepreferido) {
        this.nombrepreferido = nombrepreferido;
    }

    public String getNombrepreferido() {
        return nombrepreferido;
    }

    public void setNombrepreferido(String nombrepreferido) {
        this.nombrepreferido = nombrepreferido;
    }

    public String getGustosejercicio() {
        return gustosejercicio;
    }

    public void setGustosejercicio(String gustosejercicio) {
        this.gustosejercicio = gustosejercicio;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
    public BigDecimal getBrillopreferido()
    {
        return brillopreferido;
    }
    public void setBrillopreferido()
    {
        this.brillopreferido = brillopreferido;
    }
    public BigDecimal getVolpreferido() {
        return volpreferido;
    }

    public void setVolpreferido(BigDecimal volpreferido) {
        this.volpreferido = volpreferido;
    }

    @XmlTransient
    public List<Preferenciaxcuento> getPreferenciaxcuentoList() {
        return preferenciaxcuentoList;
    }

    public void setPreferenciaxcuentoList(List<Preferenciaxcuento> preferenciaxcuentoList) {
        this.preferenciaxcuentoList = preferenciaxcuentoList;
    }

    public List<Actxpreferencia> getActxpreferenciaList() {
        return actxpreferenciaList;
    }

    public void setActxpreferencia(List<Actxpreferencia> actxpreferenciaList) {
        this.actxpreferenciaList = actxpreferenciaList;
    }

    public Perfilpwa getPerfilpwaCedula() {
        return perfilpwaCedula;
    }

    public void setPerfilpwaCedula(Perfilpwa perfilpwaCedula) {
        this.perfilpwaCedula = perfilpwaCedula;
    }

    @XmlTransient
    public List<Preferenciaxbaile> getPreferenciaxbaileList() {
        return preferenciaxbaileList;
    }

    public void setPreferenciaxbaileList(List<Preferenciaxbaile> preferenciaxbaileList) {
        this.preferenciaxbaileList = preferenciaxbaileList;
    }

    @XmlTransient
    public List<Preferenciaxcancion> getPreferenciaxcancionList() {
        return preferenciaxcancionList;
    }

    public void setPreferenciaxcancionList(List<Preferenciaxcancion> preferenciaxcancionList) {
        this.preferenciaxcancionList = preferenciaxcancionList;
    }

    @XmlTransient
    public List<Preferenciasxrutina> getPreferenciasxrutinaList() {
        return preferenciasxrutinaList;
    }

    public void setPreferenciasxrutinaList(List<Preferenciasxrutina> preferenciasxrutinaList) {
        this.preferenciasxrutinaList = preferenciasxrutinaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombrepreferido != null ? nombrepreferido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilPreferencia)) {
            return false;
        }
        PerfilPreferencia other = (PerfilPreferencia) object;
        if ((this.nombrepreferido == null && other.nombrepreferido != null) || (this.nombrepreferido != null && !this.nombrepreferido.equals(other.nombrepreferido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilPreferencia[ nombrepreferido=" + nombrepreferido + " ]";
    }
    
}
