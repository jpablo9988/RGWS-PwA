/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
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
@Table(name = "perfil_ejercicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilEjercicio.findAll", query = "SELECT p FROM PerfilEjercicio p"),
    @NamedQuery(name = "PerfilEjercicio.findByPerfilPwaCedula", query = "SELECT p FROM PerfilEjercicio p WHERE p.perfilPwaCedula = :perfilPwaCedula"),
    @NamedQuery(name = "PerfilEjercicio.findByIndexIntensidadActual", query = "SELECT p FROM PerfilEjercicio p WHERE p.indexIntensidadActual = :indexIntensidadActual"),
    @NamedQuery(name = "PerfilEjercicio.findByFechaProx", query = "SELECT p FROM PerfilEjercicio p WHERE p.fechaProx = :fechaProx"),
    @NamedQuery(name = "PerfilEjercicio.findByHoraProx", query = "SELECT p FROM PerfilEjercicio p WHERE p.horaProx = :horaProx"),
    @NamedQuery(name = "PerfilEjercicio.findByDiasHechos", query = "SELECT p FROM PerfilEjercicio p WHERE p.diasHechos = :diasHechos")})
public class PerfilEjercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "perfil_pwa_cedula")
    private String perfilPwaCedula;
    @Basic(optional = false)
    @Column(name = "index_intensidad_actual")
    private int indexIntensidadActual;
    @Basic(optional = false)
    @Column(name = "fecha_prox")
    @Temporal(TemporalType.DATE)
    private Date fechaProx;
    @Basic(optional = false)
    @Column(name = "hora_prox")
    private int horaProx;
    @Basic(optional = false)
    @Column(name = "dias_hechos")
    private int diasHechos;
    @ManyToMany(mappedBy = "perfilEjercicioList", fetch = FetchType.EAGER)
    private List<Ejercicio> ejercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedula", fetch = FetchType.EAGER)
    private List<Horario> horarioList;
    @JoinColumn(name = "perfil_pwa_cedula", referencedColumnName = "cedula", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilPwa perfilPwa;
    @JoinColumn(name = "nombre_programa", referencedColumnName = "nombre")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private ProgramaEjercicio nombrePrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pwaCedula", fetch = FetchType.EAGER)
    private List<Historial> historialList;

    public PerfilEjercicio() {
    }

    public PerfilEjercicio(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public PerfilEjercicio(String perfilPwaCedula, int indexIntensidadActual, Date fechaProx, int horaProx, int diasHechos) {
        this.perfilPwaCedula = perfilPwaCedula;
        this.indexIntensidadActual = indexIntensidadActual;
        this.fechaProx = fechaProx;
        this.horaProx = horaProx;
        this.diasHechos = diasHechos;
    }

    public String getPerfilPwaCedula() {
        return perfilPwaCedula;
    }

    public void setPerfilPwaCedula(String perfilPwaCedula) {
        this.perfilPwaCedula = perfilPwaCedula;
    }

    public int getIndexIntensidadActual() {
        return indexIntensidadActual;
    }

    public void setIndexIntensidadActual(int indexIntensidadActual) {
        this.indexIntensidadActual = indexIntensidadActual;
    }

    public Date getFechaProx() {
        return fechaProx;
    }

    public void setFechaProx(Date fechaProx) {
        this.fechaProx = fechaProx;
    }

    public int getHoraProx() {
        return horaProx;
    }

    public void setHoraProx(int horaProx) {
        this.horaProx = horaProx;
    }

    public int getDiasHechos() {
        return diasHechos;
    }

    public void setDiasHechos(int diasHechos) {
        this.diasHechos = diasHechos;
    }

    @XmlTransient
    public List<Ejercicio> getEjercicioList() {
        return ejercicioList;
    }

    public void setEjercicioList(List<Ejercicio> ejercicioList) {
        this.ejercicioList = ejercicioList;
    }

    @XmlTransient
    public List<Horario> getHorarioList() {
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList) {
        this.horarioList = horarioList;
    }

    public PerfilPwa getPerfilPwa() {
        return perfilPwa;
    }

    public void setPerfilPwa(PerfilPwa perfilPwa) {
        this.perfilPwa = perfilPwa;
    }

    public ProgramaEjercicio getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(ProgramaEjercicio nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @XmlTransient
    public List<Historial> getHistorialList() {
        return historialList;
    }

    public void setHistorialList(List<Historial> historialList) {
        this.historialList = historialList;
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
        if (!(object instanceof PerfilEjercicio)) {
            return false;
        }
        PerfilEjercicio other = (PerfilEjercicio) object;
        if ((this.perfilPwaCedula == null && other.perfilPwaCedula != null) || (this.perfilPwaCedula != null && !this.perfilPwaCedula.equals(other.perfilPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilEjercicio[ perfilPwaCedula=" + perfilPwaCedula + " ]";
    }
    
}
