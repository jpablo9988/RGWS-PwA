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
    @UniqueConstraint(columnNames = {"perfil_medico_perfil_medico_id"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cdr.findAll", query = "SELECT c FROM Cdr c")
    , @NamedQuery(name = "Cdr.findByMemoria", query = "SELECT c FROM Cdr c WHERE c.memoria = :memoria")
    , @NamedQuery(name = "Cdr.findByOrientacion", query = "SELECT c FROM Cdr c WHERE c.orientacion = :orientacion")
    , @NamedQuery(name = "Cdr.findByJuicio", query = "SELECT c FROM Cdr c WHERE c.juicio = :juicio")
    , @NamedQuery(name = "Cdr.findByVidaSocial", query = "SELECT c FROM Cdr c WHERE c.vidaSocial = :vidaSocial")
    , @NamedQuery(name = "Cdr.findByHogar", query = "SELECT c FROM Cdr c WHERE c.hogar = :hogar")
    , @NamedQuery(name = "Cdr.findByCuidadopersonal", query = "SELECT c FROM Cdr c WHERE c.cuidadopersonal = :cuidadopersonal")})
public class Cdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer memoria;
    private Integer orientacion;
    private Integer juicio;
    @Column(name = "vida_social")
    private Integer vidaSocial;
    private Integer hogar;
    private Integer cuidadopersonal;
    @JoinColumn(name = "perfil_medico_perfil_medico_id", referencedColumnName = "perfil_medico_id", nullable = false)
    @OneToOne(optional = false)
    private PerfilMedico perfilMedicoPerfilMedicoId;

    public Cdr() {
    }

    public Cdr(Integer memoria) {
        this.memoria = memoria;
    }

    public Integer getMemoria() {
        return memoria;
    }

    public void setMemoria(Integer memoria) {
        this.memoria = memoria;
    }

    public Integer getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Integer orientacion) {
        this.orientacion = orientacion;
    }

    public Integer getJuicio() {
        return juicio;
    }

    public void setJuicio(Integer juicio) {
        this.juicio = juicio;
    }

    public Integer getVidaSocial() {
        return vidaSocial;
    }

    public void setVidaSocial(Integer vidaSocial) {
        this.vidaSocial = vidaSocial;
    }

    public Integer getHogar() {
        return hogar;
    }

    public void setHogar(Integer hogar) {
        this.hogar = hogar;
    }

    public Integer getCuidadopersonal() {
        return cuidadopersonal;
    }

    public void setCuidadopersonal(Integer cuidadopersonal) {
        this.cuidadopersonal = cuidadopersonal;
    }

    public PerfilMedico getPerfilMedicoPerfilMedicoId() {
        return perfilMedicoPerfilMedicoId;
    }

    public void setPerfilMedicoPerfilMedicoId(PerfilMedico perfilMedicoPerfilMedicoId) {
        this.perfilMedicoPerfilMedicoId = perfilMedicoPerfilMedicoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoria != null ? memoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cdr)) {
            return false;
        }
        Cdr other = (Cdr) object;
        if ((this.memoria == null && other.memoria != null) || (this.memoria != null && !this.memoria.equals(other.memoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Cdr[ memoria=" + memoria + " ]";
    }
    
}
