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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
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
    @NamedQuery(name = "Antecedente.findAll", query = "SELECT a FROM Antecedente a")
    , @NamedQuery(name = "Antecedente.findById", query = "SELECT a FROM Antecedente a WHERE a.id = :id")
    , @NamedQuery(name = "Antecedente.findByPositivename", query = "SELECT a FROM Antecedente a WHERE a.positivename = :positivename")
    , @NamedQuery(name = "Antecedente.findByNegativename", query = "SELECT a FROM Antecedente a WHERE a.negativename = :negativename")
    , @NamedQuery(name = "Antecedente.findByBasevalue", query = "SELECT a FROM Antecedente a WHERE a.basevalue = :basevalue")
    , @NamedQuery(name = "Antecedente.findByForgetfactor", query = "SELECT a FROM Antecedente a WHERE a.forgetfactor = :forgetfactor")})
public class Antecedente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(nullable = false, precision = 131089)
    private BigDecimal id;
    @Column(name = "ETIQUETA")
    private String etiqueta;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private double valor;
    @JoinTable(name = "relation_56", joinColumns = {
        @JoinColumn(name = "antecedente_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "regla_id", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Regla> reglaList;

    public Antecedente() {
    }

    public Antecedente(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    public double getValor()
    {
        return valor;
    }
    public void setValor(double valor)
    {
        this.valor = valor;
    }


    @XmlTransient
    public List<Regla> getReglaList() {
        return reglaList;
    }

    public void setReglaList(List<Regla> reglaxantecedenteList) {
        this.reglaList = reglaList;
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
        if (!(object instanceof Antecedente)) {
            return false;
        }
        Antecedente other = (Antecedente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Antecedente[ id=" + id + " ]";
    }

    
}
