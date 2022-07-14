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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 57305
 */
@Entity
@Table(catalog = "Res-pwaDB", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"perfilpwa_cedula"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t")
    , @NamedQuery(name = "Test.findById", query = "SELECT t FROM Test t WHERE t.id = :id")
    , @NamedQuery(name = "Test.findByTEquilibriosppb", query = "SELECT t FROM Test t WHERE t.tEquilibriosppb = :tEquilibriosppb")
    , @NamedQuery(name = "Test.findByTVelocidadsppb", query = "SELECT t FROM Test t WHERE t.tVelocidadsppb = :tVelocidadsppb")
    , @NamedQuery(name = "Test.findByPuntajesppb", query = "SELECT t FROM Test t WHERE t.puntajesppb = :puntajesppb")
    , @NamedQuery(name = "Test.findByCategoria", query = "SELECT t FROM Test t WHERE t.categoria = :categoria")})
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(name = "t_equilibriosppb")
    private Integer tEquilibriosppb;
    @Column(name = "t_velocidadsppb")
    private Integer tVelocidadsppb;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 17, scale = 17)
    private Double puntajesppb;
    @Column(length = 2147483647)
    private String categoria;
    @JoinColumn(name = "perfilpwa_cedula", referencedColumnName = "cedula", nullable = false)
    @OneToOne(optional = false)
    private Perfilpwa perfilpwaCedula;

    public Test() {
    }

    public Test(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTEquilibriosppb() {
        return tEquilibriosppb;
    }

    public void setTEquilibriosppb(Integer tEquilibriosppb) {
        this.tEquilibriosppb = tEquilibriosppb;
    }

    public Integer getTVelocidadsppb() {
        return tVelocidadsppb;
    }

    public void setTVelocidadsppb(Integer tVelocidadsppb) {
        this.tVelocidadsppb = tVelocidadsppb;
    }

    public Double getPuntajesppb() {
        return puntajesppb;
    }

    public void setPuntajesppb(Double puntajesppb) {
        this.puntajesppb = puntajesppb;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Test[ id=" + id + " ]";
    }
    
}
