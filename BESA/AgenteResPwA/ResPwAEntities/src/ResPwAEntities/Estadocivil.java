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
import javax.persistence.Id;
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
@Table(name = "estado_civil", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoCivil.findAll", query = "SELECT e FROM EstadoCivil e"),
    @NamedQuery(name = "EstadoCivil.findByTipoEc", query = "SELECT e FROM EstadoCivil e WHERE e.tipoEc = :tipoEc")})
public class EstadoCivil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tipo_ec")
    private String tipoEc;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estadoCivilTipoEc")
    private List<PerfilPwa> perfilPwaList;

    public EstadoCivil() {
    }

    public EstadoCivil(String tipoEc) {
        this.tipoEc = tipoEc;
    }

    public String getTipoEc() {
        return tipoEc;
    }

    public void setTipoEc(String tipoEc) {
        this.tipoEc = tipoEc;
    }

    @XmlTransient
    public List<PerfilPwa> getPerfilPwaList() {
        return perfilPwaList;
    }

    public void setPerfilPwaList(List<PerfilPwa> perfilPwaList) {
        this.perfilPwaList = perfilPwaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoEc != null ? tipoEc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoCivil)) {
            return false;
        }
        EstadoCivil other = (EstadoCivil) object;
        if ((this.tipoEc == null && other.tipoEc != null) || (this.tipoEc != null && !this.tipoEc.equals(other.tipoEc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EstadoCivil[ tipoEc=" + tipoEc + " ]";
    }
    
}
