/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.ActividadPwa;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.RegistroActividad;
import ResPwAEntities.RegistroActividadPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class RegistroActividadJpaController implements Serializable {

    public RegistroActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroActividad registroActividad) throws PreexistingEntityException, Exception {
        if (registroActividad.getRegistroActividadPK() == null) {
            registroActividad.setRegistroActividadPK(new RegistroActividadPK());
        }
        registroActividad.getRegistroActividadPK().setPerfilPwaCedula(registroActividad.getPerfilPwa().getCedula());
        registroActividad.getRegistroActividadPK().setActividadPwaId(registroActividad.getActividadPwa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPwa actividadPwa = registroActividad.getActividadPwa();
            if (actividadPwa != null) {
                actividadPwa = em.getReference(actividadPwa.getClass(), actividadPwa.getId());
                registroActividad.setActividadPwa(actividadPwa);
            }
            em.persist(registroActividad);
            if (actividadPwa != null) {
                actividadPwa.getRegistroActividadList().add(registroActividad);
                actividadPwa = em.merge(actividadPwa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroActividad(registroActividad.getRegistroActividadPK()) != null) {
                throw new PreexistingEntityException("RegistroActividad " + registroActividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroActividad registroActividad) throws NonexistentEntityException, Exception {
        registroActividad.getRegistroActividadPK().setPerfilPwaCedula(registroActividad.getPerfilPwa().getCedula());
        registroActividad.getRegistroActividadPK().setActividadPwaId(registroActividad.getActividadPwa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroActividad persistentRegistroActividad = em.find(RegistroActividad.class, registroActividad.getRegistroActividadPK());
            ActividadPwa actividadPwaOld = persistentRegistroActividad.getActividadPwa();
            ActividadPwa actividadPwaNew = registroActividad.getActividadPwa();
            if (actividadPwaNew != null) {
                actividadPwaNew = em.getReference(actividadPwaNew.getClass(), actividadPwaNew.getId());
                registroActividad.setActividadPwa(actividadPwaNew);
            }
            registroActividad = em.merge(registroActividad);
            if (actividadPwaOld != null && !actividadPwaOld.equals(actividadPwaNew)) {
                actividadPwaOld.getRegistroActividadList().remove(registroActividad);
                actividadPwaOld = em.merge(actividadPwaOld);
            }
            if (actividadPwaNew != null && !actividadPwaNew.equals(actividadPwaOld)) {
                actividadPwaNew.getRegistroActividadList().add(registroActividad);
                actividadPwaNew = em.merge(actividadPwaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RegistroActividadPK id = registroActividad.getRegistroActividadPK();
                if (findRegistroActividad(id) == null) {
                    throw new NonexistentEntityException("The registroActividad with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(RegistroActividadPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroActividad registroActividad;
            try {
                registroActividad = em.getReference(RegistroActividad.class, id);
                registroActividad.getRegistroActividadPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroActividad with id " + id + " no longer exists.", enfe);
            }
            ActividadPwa actividadPwa = registroActividad.getActividadPwa();
            if (actividadPwa != null) {
                actividadPwa.getRegistroActividadList().remove(registroActividad);
                actividadPwa = em.merge(actividadPwa);
            }
            em.remove(registroActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroActividad> findRegistroActividadEntities() {
        return findRegistroActividadEntities(true, -1, -1);
    }

    public List<RegistroActividad> findRegistroActividadEntities(int maxResults, int firstResult) {
        return findRegistroActividadEntities(false, maxResults, firstResult);
    }

    private List<RegistroActividad> findRegistroActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroActividad.class));
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

    public RegistroActividad findRegistroActividad(RegistroActividadPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroActividad> rt = cq.from(RegistroActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
