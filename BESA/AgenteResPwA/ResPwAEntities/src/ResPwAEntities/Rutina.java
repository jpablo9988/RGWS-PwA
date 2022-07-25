/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "rutina", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rutina.findAll", query = "SELECT r FROM Rutina r"),
    @NamedQuery(name = "Rutina.findById", query = "SELECT r FROM Rutina r WHERE r.id = :id"),
    @NamedQuery(name = "Rutina.findByTipoRutina", query = "SELECT r FROM Rutina r WHERE r.tipoRutina = :tipoRutina"),
    @NamedQuery(name = "Rutina.findByDificultad", query = "SELECT r FROM Rutina r WHERE r.dificultad = :dificultad")})
public class Rutina implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "tipo_rutina")
    private String tipoRutina;
    @Basic(optional = false)
    @Column(name = "dificultad")
    private Character dificultad;
    @JoinTable(name = "rutina_x_semana", joinColumns = {
        @JoinColumn(name = "rutina_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "semana_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Semana> semanaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutina")
    private List<PreferenciaXRutina> preferenciaXRutinaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutina")
    private List<Ejercicio> ejercicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutinaId")
    private List<Frase> fraseList;

    public Rutina() {
    }

    public Rutina(BigDecimal id) {
        this.id = id;
    }

    public Rutina(BigDecimal id, String tipoRutina, Character dificultad) {
        this.id = id;
        this.tipoRutina = tipoRutina;
        this.dificultad = dificultad;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getTipoRutina() {
        return tipoRutina;
    }

    public void setTipoRutina(String tipoRutina) {
        this.tipoRutina = tipoRutina;
    }

    public Character getDificultad() {
        return dificultad;
    }

    public void setDificultad(Character dificultad) {
        this.dificultad = dificultad;
    }

    @XmlTransient
    public List<Semana> getSemanaList() {
        return semanaList;
    }

    public void setSemanaList(List<Semana> semanaList) {
        this.semanaList = semanaList;
    }

    @XmlTransient
    public List<PreferenciaXRutina> getPreferenciaXRutinaList() {
        return preferenciaXRutinaList;
    }

    public void setPreferenciaXRutinaList(List<PreferenciaXRutina> preferenciaXRutinaList) {
        this.preferenciaXRutinaList = preferenciaXRutinaList;
    }

    @XmlTransient
    public List<Ejercicio> getEjercicioList() {
        return ejercicioList;
    }

    public void setEjercicioList(List<Ejercicio> ejercicioList) {
        this.ejercicioList = ejercicioList;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rutina)) {
            return false;
        }
        Rutina other = (Rutina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Rutina[ id=" + id + " ]";
    }
    
}
