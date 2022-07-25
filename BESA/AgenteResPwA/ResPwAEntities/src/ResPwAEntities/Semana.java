/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "semana", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Semana.findAll", query = "SELECT s FROM Semana s"),
    @NamedQuery(name = "Semana.findById", query = "SELECT s FROM Semana s WHERE s.id = :id"),
    @NamedQuery(name = "Semana.findByFechaInicio", query = "SELECT s FROM Semana s WHERE s.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Semana.findByNoSemana", query = "SELECT s FROM Semana s WHERE s.noSemana = :noSemana")})
public class Semana implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private BigDecimal id;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "no_semana")
    private int noSemana;
    @ManyToMany(mappedBy = "semanaList")
    private List<Rutina> rutinaList;

    public Semana() {
    }

    public Semana(BigDecimal id) {
        this.id = id;
    }

    public Semana(BigDecimal id, Date fechaInicio, int noSemana) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.noSemana = noSemana;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getNoSemana() {
        return noSemana;
    }

    public void setNoSemana(int noSemana) {
        this.noSemana = noSemana;
    }

    @XmlTransient
    public List<Rutina> getRutinaList() {
        return rutinaList;
    }

    public void setRutinaList(List<Rutina> rutinaList) {
        this.rutinaList = rutinaList;
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
        if (!(object instanceof Semana)) {
            return false;
        }
        Semana other = (Semana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Semana[ id=" + id + " ]";
    }
    
}
