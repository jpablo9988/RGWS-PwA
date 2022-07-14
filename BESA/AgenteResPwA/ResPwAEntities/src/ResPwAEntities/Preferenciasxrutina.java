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
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Preferenciasxrutina.findAll", query = "SELECT p FROM Preferenciasxrutina p")
    , @NamedQuery(name = "Preferenciasxrutina.findByPreferenciasxrutinaId", query = "SELECT p FROM Preferenciasxrutina p WHERE p.preferenciasxrutinaId = :preferenciasxrutinaId")})
public class Preferenciasxrutina implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "preferenciasxrutina_id", nullable = false, precision = 131089)
    private BigDecimal preferenciasxrutinaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "preferenciasxrutinaPreferenciasxrutinaId")
    private List<Rutinas> rutinasList;
    @JoinColumn(name = "perfil_preferencia_nombrepreferido", referencedColumnName = "nombrepreferido", nullable = false)
    @ManyToOne(optional = false)
    private PerfilPreferencia perfilPreferenciaNombrepreferido;

    public Preferenciasxrutina() {
    }

    public Preferenciasxrutina(BigDecimal preferenciasxrutinaId) {
        this.preferenciasxrutinaId = preferenciasxrutinaId;
    }

    public BigDecimal getPreferenciasxrutinaId() {
        return preferenciasxrutinaId;
    }

    public void setPreferenciasxrutinaId(BigDecimal preferenciasxrutinaId) {
        this.preferenciasxrutinaId = preferenciasxrutinaId;
    }

    @XmlTransient
    public List<Rutinas> getRutinasList() {
        return rutinasList;
    }

    public void setRutinasList(List<Rutinas> rutinasList) {
        this.rutinasList = rutinasList;
    }

    public PerfilPreferencia getPerfilPreferenciaNombrepreferido() {
        return perfilPreferenciaNombrepreferido;
    }

    public void setPerfilPreferenciaNombrepreferido(PerfilPreferencia perfilPreferenciaNombrepreferido) {
        this.perfilPreferenciaNombrepreferido = perfilPreferenciaNombrepreferido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferenciasxrutinaId != null ? preferenciasxrutinaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferenciasxrutina)) {
            return false;
        }
        Preferenciasxrutina other = (Preferenciasxrutina) object;
        if ((this.preferenciasxrutinaId == null && other.preferenciasxrutinaId != null) || (this.preferenciasxrutinaId != null && !this.preferenciasxrutinaId.equals(other.preferenciasxrutinaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Preferenciasxrutina[ preferenciasxrutinaId=" + preferenciasxrutinaId + " ]";
    }
    
}
