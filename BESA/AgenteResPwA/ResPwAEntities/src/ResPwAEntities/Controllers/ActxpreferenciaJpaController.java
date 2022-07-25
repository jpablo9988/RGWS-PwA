/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.ActXPreferencia;
import ResPwAEntities.ActXPreferenciaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.ActividadPwa;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilPreferencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class ActXPreferenciaJpaController implements Serializable {

    public ActXPreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActXPreferencia actXPreferencia) throws PreexistingEntityException, Exception {
        if (actXPreferencia.getActXPreferenciaPK() == null) {
            actXPreferencia.setActXPreferenciaPK(new ActXPreferenciaPK());
        }
        actXPreferencia.getActXPreferenciaPK().setPreferenciaPwaCedula(actXPreferencia.getPerfilPreferencia().getPerfilPwaCedula());
        actXPreferencia.getActXPreferenciaPK().setActividadPwaId(actXPreferencia.getActividadPwa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPwa actividadPwa = actXPreferencia.getActividadPwa();
            if (actividadPwa != null) {
                actividadPwa = em.getReference(actividadPwa.getClass(), actividadPwa.getId());
                actXPreferencia.setActividadPwa(actividadPwa);
            }
            PerfilPreferencia perfilPreferencia = actXPreferencia.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                actXPreferencia.setPerfilPreferencia(perfilPreferencia);
            }
            em.persist(actXPreferencia);
            if (actividadPwa != null) {
                actividadPwa.getActXPreferenciaList().add(actXPreferencia);
                actividadPwa = em.merge(actividadPwa);
            }
            if (perfilPreferencia != null) {
                perfilPreferencia.getActXPreferenciaList().add(actXPreferencia);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActXPreferencia(actXPreferencia.getActXPreferenciaPK()) != null) {
                throw new PreexistingEntityException("ActXPreferencia " + actXPreferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActXPreferencia actXPreferencia) throws NonexistentEntityException, Exception {
        actXPreferencia.getActXPreferenciaPK().setPreferenciaPwaCedula(actXPreferencia.getPerfilPreferencia().getPerfilPwaCedula());
        actXPreferencia.getActXPreferenciaPK().setActividadPwaId(actXPreferencia.getActividadPwa().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActXPreferencia persistentActXPreferencia = em.find(ActXPreferencia.class, actXPreferencia.getActXPreferenciaPK());
            ActividadPwa actividadPwaOld = persistentActXPreferencia.getActividadPwa();
            ActividadPwa actividadPwaNew = actXPreferencia.getActividadPwa();
            PerfilPreferencia perfilPreferenciaOld = persistentActXPreferencia.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = actXPreferencia.getPerfilPreferencia();
            if (actividadPwaNew != null) {
                actividadPwaNew = em.getReference(actividadPwaNew.getClass(), actividadPwaNew.getId());
                actXPreferencia.setActividadPwa(actividadPwaNew);
            }
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                actXPreferencia.setPerfilPreferencia(perfilPreferenciaNew);
            }
            actXPreferencia = em.merge(actXPreferencia);
            if (actividadPwaOld != null && !actividadPwaOld.equals(actividadPwaNew)) {
                actividadPwaOld.getActXPreferenciaList().remove(actXPreferencia);
                actividadPwaOld = em.merge(actividadPwaOld);
            }
            if (actividadPwaNew != null && !actividadPwaNew.equals(actividadPwaOld)) {
                actividadPwaNew.getActXPreferenciaList().add(actXPreferencia);
                actividadPwaNew = em.merge(actividadPwaNew);
            }
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                perfilPreferenciaOld.getActXPreferenciaList().remove(actXPreferencia);
                perfilPreferenciaOld = em.merge(perfilPreferenciaOld);
            }
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                perfilPreferenciaNew.getActXPreferenciaList().add(actXPreferencia);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ActXPreferenciaPK id = actXPreferencia.getActXPreferenciaPK();
                if (findActXPreferencia(id) == null) {
                    throw new NonexistentEntityException("The actXPreferencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ActXPreferenciaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActXPreferencia actXPreferencia;
            try {
                actXPreferencia = em.getReference(ActXPreferencia.class, id);
                actXPreferencia.getActXPreferenciaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actXPreferencia with id " + id + " no longer exists.", enfe);
            }
            ActividadPwa actividadPwa = actXPreferencia.getActividadPwa();
            if (actividadPwa != null) {
                actividadPwa.getActXPreferenciaList().remove(actXPreferencia);
                actividadPwa = em.merge(actividadPwa);
            }
            PerfilPreferencia perfilPreferencia = actXPreferencia.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia.getActXPreferenciaList().remove(actXPreferencia);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.remove(actXPreferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActXPreferencia> findActXPreferenciaEntities() {
        return findActXPreferenciaEntities(true, -1, -1);
    }

    public List<ActXPreferencia> findActXPreferenciaEntities(int maxResults, int firstResult) {
        return findActXPreferenciaEntities(false, maxResults, firstResult);
    }

    private List<ActXPreferencia> findActXPreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActXPreferencia.class));
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

    public ActXPreferencia findActXPreferencia(ActXPreferenciaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActXPreferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getActXPreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActXPreferencia> rt = cq.from(ActXPreferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
