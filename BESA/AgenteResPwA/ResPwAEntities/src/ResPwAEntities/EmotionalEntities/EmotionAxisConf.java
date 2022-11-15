/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.EmotionalEntities;

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
@Table(name = "emotion_axis_conf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmotionAxisConf.findAll", query = "SELECT e FROM EmotionAxisConf e"),
    @NamedQuery(name = "EmotionAxisConf.findById", query = "SELECT e FROM EmotionAxisConf e WHERE e.id = :id"),
    @NamedQuery(name = "EmotionAxisConf.findByPositiveName", query = "SELECT e FROM EmotionAxisConf e WHERE e.positiveName = :positiveName"),
    @NamedQuery(name = "EmotionAxisConf.findByNegativeName", query = "SELECT e FROM EmotionAxisConf e WHERE e.negativeName = :negativeName"),
    @NamedQuery(name = "EmotionAxisConf.findByBaseValue", query = "SELECT e FROM EmotionAxisConf e WHERE e.baseValue = :baseValue"),
    @NamedQuery(name = "EmotionAxisConf.findByForgetFactor", query = "SELECT e FROM EmotionAxisConf e WHERE e.forgetFactor = :forgetFactor")})
public class EmotionAxisConf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "positive_name")
    private String positiveName;
    @Basic(optional = false)
    @Column(name = "negative_name")
    private String negativeName;
    @Basic(optional = false)
    @Column(name = "base_value")
    private double baseValue;
    @Basic(optional = false)
    @Column(name = "forget_factor")
    private double forgetFactor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emotionalAxisConfigId", fetch = FetchType.EAGER)
    private List<EventInfluence> eventInfluenceList;

    public EmotionAxisConf() {
    }

    public EmotionAxisConf(Integer id) {
        this.id = id;
    }

    public EmotionAxisConf(Integer id, String positiveName, String negativeName, double baseValue, double forgetFactor) {
        this.id = id;
        this.positiveName = positiveName;
        this.negativeName = negativeName;
        this.baseValue = baseValue;
        this.forgetFactor = forgetFactor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositiveName() {
        return positiveName;
    }

    public void setPositiveName(String positiveName) {
        this.positiveName = positiveName;
    }

    public String getNegativeName() {
        return negativeName;
    }

    public void setNegativeName(String negativeName) {
        this.negativeName = negativeName;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public double getForgetFactor() {
        return forgetFactor;
    }

    public void setForgetFactor(double forgetFactor) {
        this.forgetFactor = forgetFactor;
    }

    @XmlTransient
    public List<EventInfluence> getEventInfluenceList() {
        return eventInfluenceList;
    }

    public void setEventInfluenceList(List<EventInfluence> eventInfluenceList) {
        this.eventInfluenceList = eventInfluenceList;
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
        if (!(object instanceof EmotionAxisConf)) {
            return false;
        }
        EmotionAxisConf other = (EmotionAxisConf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.EmotionalEntities.EmotionAxisConf[ id=" + id + " ]";
    }
    
}
