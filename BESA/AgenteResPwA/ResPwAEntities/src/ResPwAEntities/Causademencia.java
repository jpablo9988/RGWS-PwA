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
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Causademencia.findAll", query = "SELECT c FROM Causademencia c")
    , @NamedQuery(name = "Causademencia.findByCondicion", query = "SELECT c FROM Causademencia c WHERE c.condicion = :condicion")
    , @NamedQuery(name = "Causademencia.findByIdcausa", query = "SELECT c FROM Causademencia c WHERE c.idcausa = :idcausa")})
public class Causademencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String condicion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(nullable = false, precision = 131089)
    private BigDecimal idcausa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "causademenciaIdcausa")
    private List<PerfilMedico> perfilMedicoList;

    public Causademencia() {
    }

    public Causademencia(BigDecimal idcausa) {
        this.idcausa = idcausa;
    }

    public Causademencia(BigDecimal idcausa, String condicion) {
        this.idcausa = idcausa;
        this.condicion = condicion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public BigDecimal getIdcausa() {
        return idcausa;
    }

    public void setIdcausa(BigDecimal idcausa) {
        this.idcausa = idcausa;
    }

    @XmlTransient
    public List<PerfilMedico> getPerfilMedicoList() {
        return perfilMedicoList;
    }

    public void setPerfilMedicoList(List<PerfilMedico> perfilMedicoList) {
        this.perfilMedicoList = perfilMedicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcausa != null ? idcausa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Causademencia)) {
            return false;
        }
        Causademencia other = (Causademencia) object;
        if ((this.idcausa == null && other.idcausa != null) || (this.idcausa != null && !this.idcausa.equals(other.idcausa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Causademencia[ idcausa=" + idcausa + " ]";
    }
    
}
