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
@Table(name = "niveleducativo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Niveleducativo.findAll", query = "SELECT n FROM Niveleducativo n"),
    @NamedQuery(name = "Niveleducativo.findByTiponiveleducativo", query = "SELECT n FROM Niveleducativo n WHERE n.tiponiveleducativo = :tiponiveleducativo"),
    @NamedQuery(name = "Niveleducativo.findByIdnivel", query = "SELECT n FROM Niveleducativo n WHERE n.idnivel = :idnivel")})
public class Niveleducativo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "tiponiveleducativo")
    private String tiponiveleducativo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "idnivel")
    private BigDecimal idnivel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "niveleducativoIdnivel")
    private List<Perfilpwa> perfilpwaList;

    public Niveleducativo() {
    }

    public Niveleducativo(BigDecimal idnivel) {
        this.idnivel = idnivel;
    }

    public String getTiponiveleducativo() {
        return tiponiveleducativo;
    }

    public void setTiponiveleducativo(String tiponiveleducativo) {
        this.tiponiveleducativo = tiponiveleducativo;
    }

    public BigDecimal getIdnivel() {
        return idnivel;
    }

    public void setIdnivel(BigDecimal idnivel) {
        this.idnivel = idnivel;
    }

   @XmlTransient
    public List<Perfilpwa> getPerfilpwaList() {
        return perfilpwaList;
    }

    public void setPerfilpwaList(List<Perfilpwa> perfilpwaList) {
        this.perfilpwaList = perfilpwaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnivel != null ? idnivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Niveleducativo)) {
            return false;
        }
        Niveleducativo other = (Niveleducativo) object;
        if ((this.idnivel == null && other.idnivel != null) || (this.idnivel != null && !this.idnivel.equals(other.idnivel))) {
            return false;
        }
        return true;
    }
}