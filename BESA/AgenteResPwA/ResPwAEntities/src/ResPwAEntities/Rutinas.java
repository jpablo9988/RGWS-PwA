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
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Rutinas.findAll", query = "SELECT r FROM Rutinas r")
    , @NamedQuery(name = "Rutinas.findById1", query = "SELECT r FROM Rutinas r WHERE r.id1 = :id1")
    , @NamedQuery(name = "Rutinas.findByTiporutina", query = "SELECT r FROM Rutinas r WHERE r.tiporutina = :tiporutina")})
public class Rutinas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_1", nullable = false)
    private Integer id1;
    @Column(length = 2147483647)
    private String tiporutina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutinas")
    private List<Ejercicios> ejerciciosList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "rutinas")
    private Rutinaxsemana rutinaxsemana;
    @JoinColumn(name = "preferenciasxrutina_preferenciasxrutina_id", referencedColumnName = "preferenciasxrutina_id", nullable = false)
    @ManyToOne(optional = false)
    private Preferenciasxrutina preferenciasxrutinaPreferenciasxrutinaId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rutinasId1")
    private List<Frases> frasesList;

    public Rutinas() {
    }

    public Rutinas(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public String getTiporutina() {
        return tiporutina;
    }

    public void setTiporutina(String tiporutina) {
        this.tiporutina = tiporutina;
    }

    @XmlTransient
    public List<Ejercicios> getEjerciciosList() {
        return ejerciciosList;
    }

    public void setEjerciciosList(List<Ejercicios> ejerciciosList) {
        this.ejerciciosList = ejerciciosList;
    }

    public Rutinaxsemana getRutinaxsemana() {
        return rutinaxsemana;
    }

    public void setRutinaxsemana(Rutinaxsemana rutinaxsemana) {
        this.rutinaxsemana = rutinaxsemana;
    }

    public Preferenciasxrutina getPreferenciasxrutinaPreferenciasxrutinaId() {
        return preferenciasxrutinaPreferenciasxrutinaId;
    }

    public void setPreferenciasxrutinaPreferenciasxrutinaId(Preferenciasxrutina preferenciasxrutinaPreferenciasxrutinaId) {
        this.preferenciasxrutinaPreferenciasxrutinaId = preferenciasxrutinaPreferenciasxrutinaId;
    }

    @XmlTransient
    public List<Frases> getFrasesList() {
        return frasesList;
    }

    public void setFrasesList(List<Frases> frasesList) {
        this.frasesList = frasesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id1 != null ? id1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rutinas)) {
            return false;
        }
        Rutinas other = (Rutinas) object;
        if ((this.id1 == null && other.id1 != null) || (this.id1 != null && !this.id1.equals(other.id1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Rutinas[ id1=" + id1 + " ]";
    }
    
}
