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
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emotionaxisconf.findAll", query = "SELECT e FROM Emotionaxisconf e")
    , @NamedQuery(name = "Emotionaxisconf.findById", query = "SELECT e FROM Emotionaxisconf e WHERE e.id = :id")
    , @NamedQuery(name = "Emotionaxisconf.findByPositivename", query = "SELECT e FROM Emotionaxisconf e WHERE e.positivename = :positivename")
    , @NamedQuery(name = "Emotionaxisconf.findByNegativename", query = "SELECT e FROM Emotionaxisconf e WHERE e.negativename = :negativename")
    , @NamedQuery(name = "Emotionaxisconf.findByBasevalue", query = "SELECT e FROM Emotionaxisconf e WHERE e.basevalue = :basevalue")
    , @NamedQuery(name = "Emotionaxisconf.findByForgetfactor", query = "SELECT e FROM Emotionaxisconf e WHERE e.forgetfactor = :forgetfactor")})
public class Emotionaxisconf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 2147483647)
    private String positivename;
    @Column(length = 2147483647)
    private String negativename;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double basevalue;
    @Column(precision = 17, scale = 17)
    private Double forgetfactor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "emotionaxisconfId")
    private List<Eventinfluence> eventinfluenceList;

    public Emotionaxisconf() {
    }

    public Emotionaxisconf(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositivename() {
        return positivename;
    }

    public void setPositivename(String positivename) {
        this.positivename = positivename;
    }

    public String getNegativename() {
        return negativename;
    }

    public void setNegativename(String negativename) {
        this.negativename = negativename;
    }

    public Double getBasevalue() {
        return basevalue;
    }

    public void setBasevalue(Double basevalue) {
        this.basevalue = basevalue;
    }

    public Double getForgetfactor() {
        return forgetfactor;
    }

    public void setForgetfactor(Double forgetfactor) {
        this.forgetfactor = forgetfactor;
    }

    @XmlTransient
    public List<Eventinfluence> getEventinfluenceList() {
        return eventinfluenceList;
    }

    public void setEventinfluenceList(List<Eventinfluence> eventinfluenceList) {
        this.eventinfluenceList = eventinfluenceList;
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
        if (!(object instanceof Emotionaxisconf)) {
            return false;
        }
        Emotionaxisconf other = (Emotionaxisconf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Emotionaxisconf[ id=" + id + " ]";
    }
    
}
