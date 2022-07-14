/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiares.findAll", query = "SELECT f FROM Familiares f")
    , @NamedQuery(name = "Familiares.findByPerfilpwacedula", query = "SELECT f FROM Familiares f WHERE f.perfilpwacedula = :perfilpwacedula")
    , @NamedQuery(name = "Familiares.findByFamiliarId", query = "SELECT f FROM Familiares f WHERE f.familiarId = :familiarId")})
public class Familiares implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(nullable = false)
    private int perfilpwacedula;
    @Id
    @Basic(optional = false)
    @Column(name = "familiar_id", nullable = false)
    private Integer familiarId;
    @JoinColumn(name = "familiar_id2", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Familiar familiarId2;
    @JoinColumn(name = "perfilpwa_cedula", referencedColumnName = "cedula", nullable = false)
    @ManyToOne(optional = false)
    private Perfilpwa perfilpwaCedula;

    public Familiares() {
    }

    public Familiares(Integer familiarId) {
        this.familiarId = familiarId;
    }

    public Familiares(Integer familiarId, int perfilpwacedula) {
        this.familiarId = familiarId;
        this.perfilpwacedula = perfilpwacedula;
    }

    public int getPerfilpwacedula() {
        return perfilpwacedula;
    }

    public void setPerfilpwacedula(int perfilpwacedula) {
        this.perfilpwacedula = perfilpwacedula;
    }

    public Integer getFamiliarId() {
        return familiarId;
    }

    public void setFamiliarId(Integer familiarId) {
        this.familiarId = familiarId;
    }

    public Familiar getFamiliarId2() {
        return familiarId2;
    }

    public void setFamiliarId2(Familiar familiarId2) {
        this.familiarId2 = familiarId2;
    }

    public Perfilpwa getPerfilpwaCedula() {
        return perfilpwaCedula;
    }

    public void setPerfilpwaCedula(Perfilpwa perfilpwaCedula) {
        this.perfilpwaCedula = perfilpwaCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (familiarId != null ? familiarId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Familiares)) {
            return false;
        }
        Familiares other = (Familiares) object;
        if ((this.familiarId == null && other.familiarId != null) || (this.familiarId != null && !this.familiarId.equals(other.familiarId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Familiares[ familiarId=" + familiarId + " ]";
    }
    
}
