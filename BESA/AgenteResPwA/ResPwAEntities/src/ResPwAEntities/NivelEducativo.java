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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "nivel_educativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NivelEducativo.findAll", query = "SELECT n FROM NivelEducativo n"),
    @NamedQuery(name = "NivelEducativo.findByTipoNe", query = "SELECT n FROM NivelEducativo n WHERE n.tipoNe = :tipoNe")})
public class NivelEducativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tipo_ne")
    private String tipoNe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelEducativoTipoNe", fetch = FetchType.EAGER)
    private List<PerfilPwa> perfilPwaList;

    public NivelEducativo() {
    }

    public NivelEducativo(String tipoNe) {
        this.tipoNe = tipoNe;
    }

    public String getTipoNe() {
        return tipoNe;
    }

    public void setTipoNe(String tipoNe) {
        this.tipoNe = tipoNe;
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
        hash += (tipoNe != null ? tipoNe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NivelEducativo)) {
            return false;
        }
        NivelEducativo other = (NivelEducativo) object;
        if ((this.tipoNe == null && other.tipoNe != null) || (this.tipoNe != null && !this.tipoNe.equals(other.tipoNe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.NivelEducativo[ tipoNe=" + tipoNe + " ]";
    }
    
}
