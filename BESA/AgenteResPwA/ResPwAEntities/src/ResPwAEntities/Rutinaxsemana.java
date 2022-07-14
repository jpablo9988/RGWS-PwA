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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Rutinaxsemana.findAll", query = "SELECT r FROM Rutinaxsemana r")
    , @NamedQuery(name = "Rutinaxsemana.findByRutinasId1", query = "SELECT r FROM Rutinaxsemana r WHERE r.rutinasId1 = :rutinasId1")})
public class Rutinaxsemana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "rutinas_id_1", nullable = false)
    private Integer rutinasId1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutinaxsemana")
    private List<Semanas> semanasList;
    @JoinColumn(name = "rutinas_id_1", referencedColumnName = "id_1", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Rutinas rutinas;

    public Rutinaxsemana() {
    }

    public Rutinaxsemana(Integer rutinasId1) {
        this.rutinasId1 = rutinasId1;
    }

    public Integer getRutinasId1() {
        return rutinasId1;
    }

    public void setRutinasId1(Integer rutinasId1) {
        this.rutinasId1 = rutinasId1;
    }

    @XmlTransient
    public List<Semanas> getSemanasList() {
        return semanasList;
    }

    public void setSemanasList(List<Semanas> semanasList) {
        this.semanasList = semanasList;
    }

    public Rutinas getRutinas() {
        return rutinas;
    }

    public void setRutinas(Rutinas rutinas) {
        this.rutinas = rutinas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rutinasId1 != null ? rutinasId1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rutinaxsemana)) {
            return false;
        }
        Rutinaxsemana other = (Rutinaxsemana) object;
        if ((this.rutinasId1 == null && other.rutinasId1 != null) || (this.rutinasId1 != null && !this.rutinasId1.equals(other.rutinasId1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Rutinaxsemana[ rutinasId1=" + rutinasId1 + " ]";
    }
    
}
