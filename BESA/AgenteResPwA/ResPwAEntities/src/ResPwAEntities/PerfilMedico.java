/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "perfil_medico", catalog = "Res-pwaDB", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"perfilpwa_cedula2"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PerfilMedico.findAll", query = "SELECT p FROM PerfilMedico p")
    , @NamedQuery(name = "PerfilMedico.findByPerfilpwaCedula", query = "SELECT p FROM PerfilMedico p WHERE p.perfilpwaCedula = :perfilpwaCedula")
    , @NamedQuery(name = "PerfilMedico.findByTomamedicamentos", query = "SELECT p FROM PerfilMedico p WHERE p.tomamedicamentos = :tomamedicamentos")
    , @NamedQuery(name = "PerfilMedico.findByDiscapauditiva", query = "SELECT p FROM PerfilMedico p WHERE p.discapauditiva = :discapauditiva")
    , @NamedQuery(name = "PerfilMedico.findByDiscapvisual", query = "SELECT p FROM PerfilMedico p WHERE p.discapvisual = :discapvisual")
    , @NamedQuery(name = "PerfilMedico.findByDiscmotora", query = "SELECT p FROM PerfilMedico p WHERE p.discmotora = :discmotora")
    , @NamedQuery(name = "PerfilMedico.findByRiesgocaida", query = "SELECT p FROM PerfilMedico p WHERE p.riesgocaida = :riesgocaida")
    , @NamedQuery(name = "PerfilMedico.findByPerfilMedicoId", query = "SELECT p FROM PerfilMedico p WHERE p.perfilMedicoId = :perfilMedicoId")})
public class PerfilMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "perfilpwa_cedula")
    private Integer perfilpwaCedula;
    private Integer tomamedicamentos;
    private Character discapauditiva;
    private Character discapvisual;
    private Character discmotora;
    private Character riesgocaida;
    @Column(name = "FAST")
    private int fast;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "perfil_medico_id", nullable = false, precision = 131089)
    private BigDecimal perfilMedicoId;
    @JoinColumn(name = "causademencia_idcausa", referencedColumnName = "idcausa", nullable = false)
    @ManyToOne(optional = false)
    private Causademencia causademenciaIdcausa;
    @JoinColumn(name = "perfilpwa_cedula2", referencedColumnName = "cedula", nullable = false)
    @OneToOne(optional = false)
    private Perfilpwa perfilpwaCedula2;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "perfilMedicoPerfilMedicoId")
    private Cdr cdr;

    public PerfilMedico() {
    }

    public PerfilMedico(BigDecimal perfilMedicoId) {
        this.perfilMedicoId = perfilMedicoId;
    }

    public Integer getPerfilpwaCedula() {
        return perfilpwaCedula;
    }

    public void setPerfilpwaCedula(Integer perfilpwaCedula) {
        this.perfilpwaCedula = perfilpwaCedula;
    }

    public Integer getTomamedicamentos() {
        return tomamedicamentos;
    }

    public void setTomamedicamentos(Integer tomamedicamentos) {
        this.tomamedicamentos = tomamedicamentos;
    }

    public Character getDiscapauditiva() {
        return discapauditiva;
    }

    public void setDiscapauditiva(Character discapauditiva) {
        this.discapauditiva = discapauditiva;
    }

    public Character getDiscapvisual() {
        return discapvisual;
    }

    public void setDiscapvisual(Character discapvisual) {
        this.discapvisual = discapvisual;
    }

    public Character getDiscmotora() {
        return discmotora;
    }

    public void setDiscmotora(Character discmotora) {
        this.discmotora = discmotora;
    }

    public Character getRiesgocaida() {
        return riesgocaida;
    }

    public void setRiesgocaida(Character riesgocaida) {
        this.riesgocaida = riesgocaida;
    }
    public int getFast()
    {   
        return fast;
    }
    public void setFast(int fast)
    {
        this.fast = fast;
    }

    public BigDecimal getPerfilMedicoId() {
        return perfilMedicoId;
    }

    public void setPerfilMedicoId(BigDecimal perfilMedicoId) {
        this.perfilMedicoId = perfilMedicoId;
    }

    public Causademencia getCausademenciaIdcausa() {
        return causademenciaIdcausa;
    }

    public void setCausademenciaIdcausa(Causademencia causademenciaIdcausa) {
        this.causademenciaIdcausa = causademenciaIdcausa;
    }

    public Perfilpwa getPerfilpwaCedula2() {
        return perfilpwaCedula2;
    }

    public void setPerfilpwaCedula2(Perfilpwa perfilpwaCedula2) {
        this.perfilpwaCedula2 = perfilpwaCedula2;
    }

    public Cdr getCdr() {
        return cdr;
    }

    public void setCdr(Cdr cdr) {
        this.cdr = cdr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perfilMedicoId != null ? perfilMedicoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerfilMedico)) {
            return false;
        }
        PerfilMedico other = (PerfilMedico) object;
        if ((this.perfilMedicoId == null && other.perfilMedicoId != null) || (this.perfilMedicoId != null && !this.perfilMedicoId.equals(other.perfilMedicoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.PerfilMedico[ perfilMedicoId=" + perfilMedicoId + " ]";
    }
    
}
