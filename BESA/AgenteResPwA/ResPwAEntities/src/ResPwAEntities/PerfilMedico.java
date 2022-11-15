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
import javax.persistence.ManyToOne;
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
@Table(name = "perfil_medico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilMedico.findAll", query = "SELECT p FROM PerfilMedico p"),
    @NamedQuery(name = "PerfilMedico.findByPerfilPwaCedula", query = "SELECT p FROM PerfilMedico p WHERE p.perfilPwaCedula = :perfilPwaCedula"),
    @NamedQuery(name = "PerfilMedico.findByTomaMedicamentos", query = "SELECT p FROM PerfilMedico p WHERE p.tomaMedicamentos = :tomaMedicamentos"),
    @NamedQuery(name = "PerfilMedico.findByDiscapAuditiva", query = "SELECT p FROM PerfilMedico p WHERE p.discapAuditiva = :discapAuditiva"),
    @NamedQuery(name = "PerfilMedico.findByDiscapVisual", query = "SELECT p FROM PerfilMedico p WHERE p.discapVisual = :discapVisual"),
    @NamedQuery(name = "PerfilMedico.findByDiscapMotora", query = "SELECT p FROM PerfilMedico p WHERE p.discapMotora = :discapMotora"),
    @NamedQuery(name = "PerfilMedico.findByEstadoEnfermedad", query = "SELECT p FROM PerfilMedico p WHERE p.estadoEnfermedad = :estadoEnfermedad"),
    @NamedQuery(name = "PerfilMedico.findByPeriodoVigila", query = "SELECT p FROM PerfilMedico p WHERE p.periodoVigila = :periodoVigila"),
    @NamedQuery(name = "PerfilMedico.findByFast", query = "SELECT p FROM PerfilMedico p WHERE p.fast = :fast"),
    @NamedQuery(name = "PerfilMedico.findByRiesgoCaida", query = "SELECT p FROM PerfilMedico p WHERE p.riesgoCaida = :riesgoCaida"),
    @NamedQuery(name = "PerfilMedico.findBySppb", query = "SELECT p FROM PerfilMedico p WHERE p.sppb = :sppb")})
public class PerfilMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "perfil_pwa_cedula")
    private String perfilPwaCedula;
    @Basic(optional = false)
    @Column(name = "toma_medicamentos")
    private int tomaMedicamentos;
    @Basic(optional = false)
    @Column(name = "discap_auditiva")
    private int discapAuditiva;
    @Basic(optional = false)
    @Column(name = "discap_visual")
    private int discapVisual;
    @Basic(optional = false)
    @Column(name = "discap_motora")
    private int discapMotora;
    @Basic(optional = false)
    @Column(name = "estado_enfermedad")
    private int estadoEnfermedad;
    @Basic(optional = false)
    @Column(name = "periodo_vigila")
    private int periodoVigila;
    @Basic(optional = false)
    @Column(name = "fast")
    private int fast;
    @Column(name = "riesgo_caida")
    private Integer riesgoCaida;
    @Column(name = "sppb")
    private Integer sppb;
    @JoinColumn(name = "causa_demencia_condicion", referencedColumnName = "condicion")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CausaDemencia causaDemenciaCondicion;
    @JoinColumn(name = "perfil_pwa_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilPwa perfilPwa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicoPwaCedula", fetch = FetchType.EAGER)
    private List<ActividadRutinaria> actividadRutinariaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilMedico", fetch = FetchType.EAGER)
    private Cdr cdr;

    public PerfilMedico() {
    }

    public PerfilMedico(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public PerfilMedico(String perfilPwaCedula, int tomaMedicamentos, int discapAuditiva, int discapVisual, int discapMotora, int estadoEnfermedad, int periodoVigila, int fast) {
        this.perfilPwaCedula = perfilPwaCedula;
        this.tomaMedicamentos = tomaMedicamentos;
        this.discapAuditiva = discapAuditiva;
        this.discapVisual = discapVisual;
        this.discapMotora = discapMotora;
        this.estadoEnfermedad = estadoEnfermedad;
        this.periodoVigila = periodoVigila;
        this.fast = fast;
    }

    public String getPerfilPwaCedula() {
        return perfilPwaCedula;
    }

    public void setPerfilPwaCedula(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public int getTomaMedicamentos() {
        return tomaMedicamentos;
    }

    public void setTomaMedicamentos(int tomaMedicamentos) {
        this.tomaMedicamentos = tomaMedicamentos;
    }

    public int getDiscapAuditiva() {
        return discapAuditiva;
    }

    public void setDiscapAuditiva(int discapAuditiva) {
        this.discapAuditiva = discapAuditiva;
    }

    public int getDiscapVisual() {
        return discapVisual;
    }

    public void setDiscapVisual(int discapVisual) {
        this.discapVisual = discapVisual;
    }

    public int getDiscapMotora() {
        return discapMotora;
    }

    public void setDiscapMotora(int discapMotora) {
        this.discapMotora = discapMotora;
    }

    public int getEstadoEnfermedad() {
        return estadoEnfermedad;
    }

    public void setEstadoEnfermedad(int estadoEnfermedad) {
        this.estadoEnfermedad = estadoEnfermedad;
    }

    public int getPeriodoVigila() {
        return periodoVigila;
    }

    public void setPeriodoVigila(int periodoVigila) {
        this.periodoVigila = periodoVigila;
    }

    public int getFast() {
        return fast;
    }

    public void setFast(int fast) {
        this.fast = fast;
    }

    public Integer getRiesgoCaida() {
        return riesgoCaida;
    }

    public void setRiesgoCaida(Integer riesgoCaida) {
        this.riesgoCaida = riesgoCaida;
    }

    public Integer getSppb() {
        return sppb;
    }

    public void setSppb(Integer sppb) {
        this.sppb = sppb;
    }

    public CausaDemencia getCausaDemenciaCondicion() {
        return causaDemenciaCondicion;
    }

    public void setCausaDemenciaCondicion(CausaDemencia causaDemenciaCondicion) {
        this.causaDemenciaCondicion = causaDemenciaCondicion;
    }

    public PerfilPwa getPerfilPwa() {
        return perfilPwa;
    }

    public void setPerfilPwa(PerfilPwa perfilPwa) {
        this.perfilPwa = perfilPwa;
    }

    @XmlTransient
    public List<ActividadRutinaria> getActividadRutinariaList() {
        return actividadRutinariaList;
    }

    public void setActividadRutinariaList(List<ActividadRutinaria> actividadRutinariaList) {
        this.actividadRutinariaList = actividadRutinariaList;
    }

    public Cdr getCdr() {
        return cdr;
    }

    public void setCdr(Cdr cdr) {
        this.cdr = cdr;
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
        if (!(object instanceof PerfilMedico)) {
            return false;
        }
        PerfilMedico other = (PerfilMedico) object;
        if ((this.perfilPwaCedula == null && other.perfilPwaCedula != null) || (this.perfilPwaCedula != null && !this.perfilPwaCedula.equals(other.perfilPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilMedico[ perfilPwaCedula=" + perfilPwaCedula + " ]";
    }
    
}
