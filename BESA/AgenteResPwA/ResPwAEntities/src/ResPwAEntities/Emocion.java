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
 * @author tesispepper
 */
@Entity
@Table(name = "emocion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emocion.findAll", query = "SELECT e FROM Emocion e"),
    @NamedQuery(name = "Emocion.findById", query = "SELECT e FROM Emocion e WHERE e.id = :id"),
    @NamedQuery(name = "Emocion.findByImagen", query = "SELECT e FROM Emocion e WHERE e.imagen = :imagen"),
    @NamedQuery(name = "Emocion.findByEmotionalTag", query = "SELECT e FROM Emocion e WHERE e.emotionalTag = :emotionalTag")})
public class Emocion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "emotional_tag")
    private String emotionalTag;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emocionId", fetch = FetchType.EAGER)
    private List<Accion> accionList;
    @JoinColumn(name = "robot_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Robot robotId;

    public Emocion() {
    }

    public Emocion(String id) {
        this.id = id;
    }

    public Emocion(String id, String imagen) {
        this.id = id;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEmotionalTag() {
        return emotionalTag;
    }

    public void setEmotionalTag(String emotionalTag) {
        this.emotionalTag = emotionalTag;
    }

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

    public Robot getRobotId() {
        return robotId;
    }

    public void setRobotId(Robot robotId) {
        this.robotId = robotId;
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
        if (!(object instanceof Emocion)) {
            return false;
        }
        Emocion other = (Emocion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Emocion[ id=" + id + " ]";
    }
    
}
