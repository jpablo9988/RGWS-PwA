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
    @NamedQuery(name = "Eventinfluence.findAll", query = "SELECT e FROM Eventinfluence e")
    , @NamedQuery(name = "Eventinfluence.findById", query = "SELECT e FROM Eventinfluence e WHERE e.id = :id")
    , @NamedQuery(name = "Eventinfluence.findByEventna", query = "SELECT e FROM Eventinfluence e WHERE e.eventna = :eventna")
    , @NamedQuery(name = "Eventinfluence.findByEventinfluence", query = "SELECT e FROM Eventinfluence e WHERE e.eventinfluence = :eventinfluence")})
public class Eventinfluence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 2147483647)
    private String eventna;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double eventinfluence;
    @JoinColumn(name = "emotionaxisconf_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Emotionaxisconf emotionaxisconfId;

    public Eventinfluence() {
    }

    public Eventinfluence(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventna() {
        return eventna;
    }

    public void setEventna(String eventna) {
        this.eventna = eventna;
    }

    public Double getEventinfluence() {
        return eventinfluence;
    }

    public void setEventinfluence(Double eventinfluence) {
        this.eventinfluence = eventinfluence;
    }

    public Emotionaxisconf getEmotionaxisconfId() {
        return emotionaxisconfId;
    }

    public void setEmotionaxisconfId(Emotionaxisconf emotionaxisconfId) {
        this.emotionaxisconfId = emotionaxisconfId;
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
        if (!(object instanceof Eventinfluence)) {
            return false;
        }
        Eventinfluence other = (Eventinfluence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Eventinfluence[ id=" + id + " ]";
    }
    
}
