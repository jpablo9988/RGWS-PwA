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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "perfil_preferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilPreferencia.findAll", query = "SELECT p FROM PerfilPreferencia p"),
    @NamedQuery(name = "PerfilPreferencia.findByPerfilPwaCedula", query = "SELECT p FROM PerfilPreferencia p WHERE p.perfilPwaCedula = :perfilPwaCedula"),
    @NamedQuery(name = "PerfilPreferencia.findByNombrePreferido", query = "SELECT p FROM PerfilPreferencia p WHERE p.nombrePreferido = :nombrePreferido"),
    @NamedQuery(name = "PerfilPreferencia.findByGustoKaraoke", query = "SELECT p FROM PerfilPreferencia p WHERE p.gustoKaraoke = :gustoKaraoke"),
    @NamedQuery(name = "PerfilPreferencia.findByGustoMusica", query = "SELECT p FROM PerfilPreferencia p WHERE p.gustoMusica = :gustoMusica"),
    @NamedQuery(name = "PerfilPreferencia.findByGustoBaile", query = "SELECT p FROM PerfilPreferencia p WHERE p.gustoBaile = :gustoBaile"),
    @NamedQuery(name = "PerfilPreferencia.findByBrilloPreferido", query = "SELECT p FROM PerfilPreferencia p WHERE p.brilloPreferido = :brilloPreferido"),
    @NamedQuery(name = "PerfilPreferencia.findByVolPreferido", query = "SELECT p FROM PerfilPreferencia p WHERE p.volPreferido = :volPreferido")})
public class PerfilPreferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "perfil_pwa_cedula")
    private String perfilPwaCedula;
    @Basic(optional = false)
    @Column(name = "nombre_preferido")
    private String nombrePreferido;
    @Basic(optional = false)
    @Column(name = "gusto_karaoke")
    private double gustoKaraoke;
    @Basic(optional = false)
    @Column(name = "gusto_musica")
    private double gustoMusica;
    @Basic(optional = false)
    @Column(name = "gusto_baile")
    private double gustoBaile;
    @Basic(optional = false)
    @Column(name = "brillo_preferido")
    private int brilloPreferido;
    @Basic(optional = false)
    @Column(name = "vol_preferido")
    private int volPreferido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia", fetch = FetchType.EAGER)
    private List<PreferenciaXCuento> preferenciaXCuentoList;
    @JoinColumn(name = "perfil_pwa_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilPwa perfilPwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia", fetch = FetchType.EAGER)
    private List<ActXPreferencia> actXPreferenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia", fetch = FetchType.EAGER)
    private List<PreferenciaXBaile> preferenciaXBaileList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfilPreferencia", fetch = FetchType.EAGER)
    private List<PreferenciaXCancion> preferenciaXCancionList;

    public PerfilPreferencia() {
    }

    public PerfilPreferencia(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public PerfilPreferencia(String perfilPwaCedula, String nombrePreferido, double gustoKaraoke, double gustoMusica, double gustoBaile, int brilloPreferido, int volPreferido) {
        this.perfilPwaCedula = perfilPwaCedula;
        this.nombrePreferido = nombrePreferido;
        this.gustoKaraoke = gustoKaraoke;
        this.gustoMusica = gustoMusica;
        this.gustoBaile = gustoBaile;
        this.brilloPreferido = brilloPreferido;
        this.volPreferido = volPreferido;
    }

    public String getPerfilPwaCedula() {
        return perfilPwaCedula;
    }

    public void setPerfilPwaCedula(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public String getNombrePreferido() {
        return nombrePreferido;
    }

    public void setNombrePreferido(String nombrePreferido) {
        this.nombrePreferido = nombrePreferido;
    }

    public double getGustoKaraoke() {
        return gustoKaraoke;
    }

    public void setGustoKaraoke(double gustoKaraoke) {
        this.gustoKaraoke = gustoKaraoke;
    }

    public double getGustoMusica() {
        return gustoMusica;
    }

    public void setGustoMusica(double gustoMusica) {
        this.gustoMusica = gustoMusica;
    }

    public double getGustoBaile() {
        return gustoBaile;
    }

    public void setGustoBaile(double gustoBaile) {
        this.gustoBaile = gustoBaile;
    }

    public int getBrilloPreferido() {
        return brilloPreferido;
    }

    public void setBrilloPreferido(int brilloPreferido) {
        this.brilloPreferido = brilloPreferido;
    }

    public int getVolPreferido() {
        return volPreferido;
    }

    public void setVolPreferido(int volPreferido) {
        this.volPreferido = volPreferido;
    }

    @XmlTransient
    public List<PreferenciaXCuento> getPreferenciaXCuentoList() {
        return preferenciaXCuentoList;
    }

    public void setPreferenciaXCuentoList(List<PreferenciaXCuento> preferenciaXCuentoList) {
        this.preferenciaXCuentoList = preferenciaXCuentoList;
    }

    public PerfilPwa getPerfilPwa() {
        return perfilPwa;
    }

    public void setPerfilPwa(PerfilPwa perfilPwa) {
        this.perfilPwa = perfilPwa;
    }

    @XmlTransient
    public List<ActXPreferencia> getActXPreferenciaList() {
        return actXPreferenciaList;
    }

    public void setActXPreferenciaList(List<ActXPreferencia> actXPreferenciaList) {
        this.actXPreferenciaList = actXPreferenciaList;
    }

    @XmlTransient
    public List<PreferenciaXBaile> getPreferenciaXBaileList() {
        return preferenciaXBaileList;
    }

    public void setPreferenciaXBaileList(List<PreferenciaXBaile> preferenciaXBaileList) {
        this.preferenciaXBaileList = preferenciaXBaileList;
    }

    @XmlTransient
    public List<PreferenciaXCancion> getPreferenciaXCancionList() {
        return preferenciaXCancionList;
    }

    public void setPreferenciaXCancionList(List<PreferenciaXCancion> preferenciaXCancionList) {
        this.preferenciaXCancionList = preferenciaXCancionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilPwaCedula != null ? perfilPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilPreferencia)) {
            return false;
        }
        PerfilPreferencia other = (PerfilPreferencia) object;
        if ((this.perfilPwaCedula == null && other.perfilPwaCedula != null) || (this.perfilPwaCedula != null && !this.perfilPwaCedula.equals(other.perfilPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilPreferencia[ perfilPwaCedula=" + perfilPwaCedula + " ]";
    }
    
}
