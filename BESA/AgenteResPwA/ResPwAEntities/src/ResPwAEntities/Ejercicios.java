/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "Ejercicios.findAll", query = "SELECT e FROM Ejercicios e")
    , @NamedQuery(name = "Ejercicios.findByIdEjercicio", query = "SELECT e FROM Ejercicios e WHERE e.ejerciciosPK.idEjercicio = :idEjercicio")
    , @NamedQuery(name = "Ejercicios.findByDescripcion", query = "SELECT e FROM Ejercicios e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Ejercicios.findByTipoejercicio", query = "SELECT e FROM Ejercicios e WHERE e.tipoejercicio = :tipoejercicio")
    , @NamedQuery(name = "Ejercicios.findByIntensidad", query = "SELECT e FROM Ejercicios e WHERE e.intensidad = :intensidad")
    , @NamedQuery(name = "Ejercicios.findByRutinasId1", query = "SELECT e FROM Ejercicios e WHERE e.ejerciciosPK.rutinasId1 = :rutinasId1")
    , @NamedQuery(name = "Ejercicios.findByUrlvideo", query = "SELECT e FROM Ejercicios e WHERE e.urlvideo = :urlvideo")})
public class Ejercicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EjerciciosPK ejerciciosPK;
    @Column(length = 2147483647)
    private String descripcion;
    @Column(length = 2147483647)
    private String tipoejercicio;
    private Integer intensidad;
    @Column(length = 2147483647)
    private String urlvideo;
    @JoinColumn(name = "rutinas_id_1", referencedColumnName = "id_1", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rutinas rutinas;

    public Ejercicios() {
    }

    public Ejercicios(EjerciciosPK ejerciciosPK) {
        this.ejerciciosPK = ejerciciosPK;
    }

    public Ejercicios(BigInteger idEjercicio, int rutinasId1) {
        this.ejerciciosPK = new EjerciciosPK(idEjercicio, rutinasId1);
    }

    public EjerciciosPK getEjerciciosPK() {
        return ejerciciosPK;
    }

    public void setEjerciciosPK(EjerciciosPK ejerciciosPK) {
        this.ejerciciosPK = ejerciciosPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoejercicio() {
        return tipoejercicio;
    }

    public void setTipoejercicio(String tipoejercicio) {
        this.tipoejercicio = tipoejercicio;
    }

    public Integer getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(Integer intensidad) {
        this.intensidad = intensidad;
    }

    public String getUrlvideo() {
        return urlvideo;
    }

    public void setUrlvideo(String urlvideo) {
        this.urlvideo = urlvideo;
    }

    public Rutinas getRutinas() {
        return rutinas;
    }

    public void setRutinas(Rutinas rutinas) {
        this.rutinas = rutinas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ejerciciosPK != null ? ejerciciosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ejercicios)) {
            return false;
        }
        Ejercicios other = (Ejercicios) object;
        if ((this.ejerciciosPK == null && other.ejerciciosPK != null) || (this.ejerciciosPK != null && !this.ejerciciosPK.equals(other.ejerciciosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResPwAEntities.Ejercicios[ ejerciciosPK=" + ejerciciosPK + " ]";
    }
    
}
