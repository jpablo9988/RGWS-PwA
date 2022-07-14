/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
    @NamedQuery(name = "Registroactividad.findAll", query = "SELECT r FROM Registroactividad r")
    , @NamedQuery(name = "Registroactividad.findByFecha", query = "SELECT r FROM Registroactividad r WHERE r.registroactividadPK.fecha = :fecha")
    , @NamedQuery(name = "Registroactividad.findByEstadoinicial", query = "SELECT r FROM Registroactividad r WHERE r.estadoinicial = :estadoinicial")
    , @NamedQuery(name = "Registroactividad.findByEstadofinal", query = "SELECT r FROM Registroactividad r WHERE r.estadofinal = :estadofinal")
    , @NamedQuery(name = "Registroactividad.findByPerfilpwacedula", query = "SELECT r FROM Registroactividad r WHERE r.registroactividadPK.perfilpwacedula = :perfilpwacedula")
    , @NamedQuery(name = "Registroactividad.findByTipo", query = "SELECT r FROM Registroactividad r WHERE r.registroactividadPK.tipo = :tipo")
    , @NamedQuery(name = "Registroactividad.findByActividadpwaId", query = "SELECT r FROM Registroactividad r WHERE r.registroactividadPK.actividadpwaId = :actividadpwaId")})
public class Registroactividad implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroactividadPK registroactividadPK;
    @Column(length = 2147483647)
    private String estadoinicial;
    @Column(length = 2147483647)
    private String estadofinal;
    @JoinColumn(name = "perfilpwa_cedula", referencedColumnName = "cedula", nullable = false)
    @ManyToOne(optional = false)
    private Perfilpwa perfilpwaCedula;

    public Registroactividad() {
    }

    public Registroactividad(RegistroactividadPK registroactividadPK) {
        this.registroactividadPK = registroactividadPK;
    }

    public Registroactividad(Date fecha, String perfilpwacedula, String tipo, int actividadpwaId) {
        this.registroactividadPK = new RegistroactividadPK(fecha, perfilpwacedula, tipo, actividadpwaId);
    }

    public RegistroactividadPK getRegistroactividadPK() {
        return registroactividadPK;
    }
   
    public Actividadpwa getActividadpwa() {
        return null;
    }
    public void setRegistroactividadPK(RegistroactividadPK registroactividadPK) {
        this.registroactividadPK = registroactividadPK;
    }

    public String getEstadoinicial() {
        return estadoinicial;
    }

    public void setEstadoinicial(String estadoinicial) {
        this.estadoinicial = estadoinicial;
    }

    public String getEstadofinal() {
        return estadofinal;
    }

    public void setEstadofinal(String estadofinal) {
        this.estadofinal = estadofinal;
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
        hash += (registroactividadPK != null ? registroactividadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registroactividad)) {
            return false;
        }
        Registroactividad other = (Registroactividad) object;
        if ((this.registroactividadPK == null && other.registroactividadPK != null) || (this.registroactividadPK != null && !this.registroactividadPK.equals(other.registroactividadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Registroactividad[ registroactividadPK=" + registroactividadPK + " ]";
    }

    public Object getPerfilpwa() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setActividadpwa(Actividadpwa actividadpwa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setPerfilpwa(Perfilpwa perfilpwa) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
