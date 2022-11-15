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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tesispepper
 */
@Entity
@Table(name = "cdr")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cdr.findAll", query = "SELECT c FROM Cdr c"),
    @NamedQuery(name = "Cdr.findByMemoria", query = "SELECT c FROM Cdr c WHERE c.memoria = :memoria"),
    @NamedQuery(name = "Cdr.findByOrientacion", query = "SELECT c FROM Cdr c WHERE c.orientacion = :orientacion"),
    @NamedQuery(name = "Cdr.findByJuicio", query = "SELECT c FROM Cdr c WHERE c.juicio = :juicio"),
    @NamedQuery(name = "Cdr.findByVidaSocial", query = "SELECT c FROM Cdr c WHERE c.vidaSocial = :vidaSocial"),
    @NamedQuery(name = "Cdr.findByHogar", query = "SELECT c FROM Cdr c WHERE c.hogar = :hogar"),
    @NamedQuery(name = "Cdr.findByCuidadopersonal", query = "SELECT c FROM Cdr c WHERE c.cuidadopersonal = :cuidadopersonal"),
    @NamedQuery(name = "Cdr.findByMedicoPwaCedula", query = "SELECT c FROM Cdr c WHERE c.medicoPwaCedula = :medicoPwaCedula")})
public class Cdr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "memoria")
    private int memoria;
    @Basic(optional = false)
    @Column(name = "orientacion")
    private int orientacion;
    @Basic(optional = false)
    @Column(name = "juicio")
    private int juicio;
    @Basic(optional = false)
    @Column(name = "vida_social")
    private int vidaSocial;
    @Basic(optional = false)
    @Column(name = "hogar")
    private int hogar;
    @Basic(optional = false)
    @Column(name = "cuidadopersonal")
    private int cuidadopersonal;
    @Id
    @Basic(optional = false)
    @Column(name = "medico_pwa_cedula")
    private String medicoPwaCedula;
    @JoinColumn(name = "medico_pwa_cedula", referencedColumnName = "perfil_pwa_cedula", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private PerfilMedico perfilMedico;

    public Cdr() {
    }

    public Cdr(String medicoPwaCedula) {
        this.medicoPwaCedula = medicoPwaCedula;
    }

    public Cdr(String medicoPwaCedula, int memoria, int orientacion, int juicio, int vidaSocial, int hogar, int cuidadopersonal) {
        this.medicoPwaCedula = medicoPwaCedula;
        this.memoria = memoria;
        this.orientacion = orientacion;
        this.juicio = juicio;
        this.vidaSocial = vidaSocial;
        this.hogar = hogar;
        this.cuidadopersonal = cuidadopersonal;
    }

    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }

    public int getJuicio() {
        return juicio;
    }

    public void setJuicio(int juicio) {
        this.juicio = juicio;
    }

    public int getVidaSocial() {
        return vidaSocial;
    }

    public void setVidaSocial(int vidaSocial) {
        this.vidaSocial = vidaSocial;
    }

    public int getHogar() {
        return hogar;
    }

    public void setHogar(int hogar) {
        this.hogar = hogar;
    }

    public int getCuidadopersonal() {
        return cuidadopersonal;
    }

    public void setCuidadopersonal(int cuidadopersonal) {
        this.cuidadopersonal = cuidadopersonal;
    }

    public String getMedicoPwaCedula() {
        return medicoPwaCedula;
    }

    public void setMedicoPwaCedula(String medicoPwaCedula) {
        this.medicoPwaCedula = medicoPwaCedula;
    }

    public PerfilMedico getPerfilMedico() {
        return perfilMedico;
    }

    public void setPerfilMedico(PerfilMedico perfilMedico) {
        this.perfilMedico = perfilMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicoPwaCedula != null ? medicoPwaCedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cdr)) {
            return false;
        }
        Cdr other = (Cdr) object;
        if ((this.medicoPwaCedula == null && other.medicoPwaCedula != null) || (this.medicoPwaCedula != null && !this.medicoPwaCedula.equals(other.medicoPwaCedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Cdr[ medicoPwaCedula=" + medicoPwaCedula + " ]";
    }
    
}
