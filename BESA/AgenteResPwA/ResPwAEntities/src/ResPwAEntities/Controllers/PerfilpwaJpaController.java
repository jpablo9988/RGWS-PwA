/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Cuidador;
import ResPwAEntities.EstadoCivil;
import ResPwAEntities.NivelEducativo;
import ResPwAEntities.PerfilPwa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PerfilPwaJpaController implements Serializable {

    public PerfilPwaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilPwa perfilPwa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuidador cuidadorNombreUsuario = perfilPwa.getCuidadorNombreUsuario();
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario = em.getReference(cuidadorNombreUsuario.getClass(), cuidadorNombreUsuario.getNombreUsuario());
                perfilPwa.setCuidadorNombreUsuario(cuidadorNombreUsuario);
            }
            EstadoCivil estadoCivilTipoEc = perfilPwa.getEstadoCivilTipoEc();
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc = em.getReference(estadoCivilTipoEc.getClass(), estadoCivilTipoEc.getTipoEc());
                perfilPwa.setEstadoCivilTipoEc(estadoCivilTipoEc);
            }
            NivelEducativo nivelEducativoTipoNe = perfilPwa.getNivelEducativoTipoNe();
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe = em.getReference(nivelEducativoTipoNe.getClass(), nivelEducativoTipoNe.getTipoNe());
                perfilPwa.setNivelEducativoTipoNe(nivelEducativoTipoNe);
            }
            em.persist(perfilPwa);
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario.getPerfilPwaList().add(perfilPwa);
                cuidadorNombreUsuario = em.merge(cuidadorNombreUsuario);
            }
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc.getPerfilPwaList().add(perfilPwa);
                estadoCivilTipoEc = em.merge(estadoCivilTipoEc);
            }
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe.getPerfilPwaList().add(perfilPwa);
                nivelEducativoTipoNe = em.merge(nivelEducativoTipoNe);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfilPwa(perfilPwa.getCedula()) != null) {
                throw new PreexistingEntityException("PerfilPwa " + perfilPwa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilPwa perfilPwa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPwa persistentPerfilPwa = em.find(PerfilPwa.class, perfilPwa.getCedula());
            Cuidador cuidadorNombreUsuarioOld = persistentPerfilPwa.getCuidadorNombreUsuario();
            Cuidador cuidadorNombreUsuarioNew = perfilPwa.getCuidadorNombreUsuario();
            EstadoCivil estadoCivilTipoEcOld = persistentPerfilPwa.getEstadoCivilTipoEc();
            EstadoCivil estadoCivilTipoEcNew = perfilPwa.getEstadoCivilTipoEc();
            NivelEducativo nivelEducativoTipoNeOld = persistentPerfilPwa.getNivelEducativoTipoNe();
            NivelEducativo nivelEducativoTipoNeNew = perfilPwa.getNivelEducativoTipoNe();
            if (cuidadorNombreUsuarioNew != null) {
                cuidadorNombreUsuarioNew = em.getReference(cuidadorNombreUsuarioNew.getClass(), cuidadorNombreUsuarioNew.getNombreUsuario());
                perfilPwa.setCuidadorNombreUsuario(cuidadorNombreUsuarioNew);
            }
            if (estadoCivilTipoEcNew != null) {
                estadoCivilTipoEcNew = em.getReference(estadoCivilTipoEcNew.getClass(), estadoCivilTipoEcNew.getTipoEc());
                perfilPwa.setEstadoCivilTipoEc(estadoCivilTipoEcNew);
            }
            if (nivelEducativoTipoNeNew != null) {
                nivelEducativoTipoNeNew = em.getReference(nivelEducativoTipoNeNew.getClass(), nivelEducativoTipoNeNew.getTipoNe());
                perfilPwa.setNivelEducativoTipoNe(nivelEducativoTipoNeNew);
            }
            perfilPwa = em.merge(perfilPwa);
            if (cuidadorNombreUsuarioOld != null && !cuidadorNombreUsuarioOld.equals(cuidadorNombreUsuarioNew)) {
                cuidadorNombreUsuarioOld.getPerfilPwaList().remove(perfilPwa);
                cuidadorNombreUsuarioOld = em.merge(cuidadorNombreUsuarioOld);
            }
            if (cuidadorNombreUsuarioNew != null && !cuidadorNombreUsuarioNew.equals(cuidadorNombreUsuarioOld)) {
                cuidadorNombreUsuarioNew.getPerfilPwaList().add(perfilPwa);
                cuidadorNombreUsuarioNew = em.merge(cuidadorNombreUsuarioNew);
            }
            if (estadoCivilTipoEcOld != null && !estadoCivilTipoEcOld.equals(estadoCivilTipoEcNew)) {
                estadoCivilTipoEcOld.getPerfilPwaList().remove(perfilPwa);
                estadoCivilTipoEcOld = em.merge(estadoCivilTipoEcOld);
            }
            if (estadoCivilTipoEcNew != null && !estadoCivilTipoEcNew.equals(estadoCivilTipoEcOld)) {
                estadoCivilTipoEcNew.getPerfilPwaList().add(perfilPwa);
                estadoCivilTipoEcNew = em.merge(estadoCivilTipoEcNew);
            }
            if (nivelEducativoTipoNeOld != null && !nivelEducativoTipoNeOld.equals(nivelEducativoTipoNeNew)) {
                nivelEducativoTipoNeOld.getPerfilPwaList().remove(perfilPwa);
                nivelEducativoTipoNeOld = em.merge(nivelEducativoTipoNeOld);
            }
            if (nivelEducativoTipoNeNew != null && !nivelEducativoTipoNeNew.equals(nivelEducativoTipoNeOld)) {
                nivelEducativoTipoNeNew.getPerfilPwaList().add(perfilPwa);
                nivelEducativoTipoNeNew = em.merge(nivelEducativoTipoNeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfilPwa.getCedula();
                if (findPerfilPwa(id) == null) {
                    throw new NonexistentEntityException("The perfilPwa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPwa perfilPwa;
            try {
                perfilPwa = em.getReference(PerfilPwa.class, id);
                perfilPwa.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilPwa with id " + id + " no longer exists.", enfe);
            }
            Cuidador cuidadorNombreUsuario = perfilPwa.getCuidadorNombreUsuario();
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario.getPerfilPwaList().remove(perfilPwa);
                cuidadorNombreUsuario = em.merge(cuidadorNombreUsuario);
            }
            EstadoCivil estadoCivilTipoEc = perfilPwa.getEstadoCivilTipoEc();
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc.getPerfilPwaList().remove(perfilPwa);
                estadoCivilTipoEc = em.merge(estadoCivilTipoEc);
            }
            NivelEducativo nivelEducativoTipoNe = perfilPwa.getNivelEducativoTipoNe();
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe.getPerfilPwaList().remove(perfilPwa);
                nivelEducativoTipoNe = em.merge(nivelEducativoTipoNe);
            }
            em.remove(perfilPwa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilPwa> findPerfilPwaEntities() {
        return findPerfilPwaEntities(true, -1, -1);
    }

    public List<PerfilPwa> findPerfilPwaEntities(int maxResults, int firstResult) {
        return findPerfilPwaEntities(false, maxResults, firstResult);
    }

    private List<PerfilPwa> findPerfilPwaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilPwa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PerfilPwa findPerfilPwa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilPwa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilPwaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilPwa> rt = cq.from(PerfilPwa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
