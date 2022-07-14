/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToMany;
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
    @NamedQuery(name = "Regla.findAll", query = "SELECT r FROM Regla r")
    , @NamedQuery(name = "Regla.findById", query = "SELECT r FROM Regla r WHERE r.id = :id")
    , @NamedQuery(name = "Regla.findByFeedback", query = "SELECT r FROM Regla r WHERE r.feedback = :feedback")
    , @NamedQuery(name = "Regla.findByEtiqueta", query = "SELECT r FROM Regla r WHERE r.etiqueta = :etiqueta")})
public class Regla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(nullable = false, precision = 131089)
    private BigDecimal id;
    @Column(precision = 17, scale = 17)
    private Double feedback;
    @Column(length = 2147483647)
    private String etiqueta;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "id")
    private List<Antecedente> antecedenteList;

    public Regla() {
    }

    public Regla(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Double getFeedback() {
        return feedback;
    }

    public void setFeedback(Double feedback) {
        this.feedback = feedback;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @XmlTransient
    public List<Antecedente> getAntecedenteList() {
        return antecedenteList;
    }

    public void setAntecedenteList(List<Antecedente> listaAntecedente) {
        this.antecedenteList = listaAntecedente;
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
        if (!(object instanceof Regla)) {
            return false;
        }
        Regla other = (Regla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Regla[ id=" + id + " ]";
    }
    
}
