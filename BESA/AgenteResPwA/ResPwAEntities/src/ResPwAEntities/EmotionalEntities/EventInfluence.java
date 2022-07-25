/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.EmotionalEntities;

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
 * @author juan.amorocho y andres.cacique
 */
@Entity
@Table(name = "event_influence", catalog = "Res_PwADB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventInfluence.findAll", query = "SELECT e FROM EventInfluence e"),
    @NamedQuery(name = "EventInfluence.findById", query = "SELECT e FROM EventInfluence e WHERE e.id = :id"),
    @NamedQuery(name = "EventInfluence.findByEventName", query = "SELECT e FROM EventInfluence e WHERE e.eventName = :eventName"),
    @NamedQuery(name = "EventInfluence.findByEventInfluence", query = "SELECT e FROM EventInfluence e WHERE e.eventInfluence = :eventInfluence")})
public class EventInfluence implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "event_name")
    private String eventName;
    @Basic(optional = false)
    @Column(name = "event_influence")
    private double eventInfluence;
    @JoinColumn(name = "emotional_axis_config_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmotionAxisConf emotionalAxisConfigId;

    public EventInfluence() {
    }

    public EventInfluence(Long id) {
        this.id = id;
    }

    public EventInfluence(Long id, String eventName, double eventInfluence) {
        this.id = id;
        this.eventName = eventName;
        this.eventInfluence = eventInfluence;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public double getEventInfluence() {
        return eventInfluence;
    }

    public void setEventInfluence(double eventInfluence) {
        this.eventInfluence = eventInfluence;
    }

    public EmotionAxisConf getEmotionalAxisConfigId() {
        return emotionalAxisConfigId;
    }

    public void setEmotionalAxisConfigId(EmotionAxisConf emotionalAxisConfigId) {
        this.emotionalAxisConfigId = emotionalAxisConfigId;
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
        if (!(object instanceof EventInfluence)) {
            return false;
        }
        EventInfluence other = (EventInfluence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EmotionalEntities.EventInfluence[ id=" + id + " ]";
    }
    
}
