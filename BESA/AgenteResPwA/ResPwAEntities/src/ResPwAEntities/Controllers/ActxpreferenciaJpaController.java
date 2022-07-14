/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Actividadpwa;
import ResPwAEntities.Actxpreferencia;
import ResPwAEntities.ActxpreferenciaPK;
import ResPwAEntities.Controllers.Exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.Exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilPreferencia;
//import static ResPwAEntities.Preferenciaxcuento_.perfilPreferencia;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 57305
 */
public class ActxpreferenciaJpaController {
     public ActxpreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Actxpreferencia actxpreferencia) throws PreexistingEntityException, Exception {
        if (actxpreferencia.getActxpreferenciaPK() == null) {
            actxpreferencia.setActxpreferenciaPK(new ActxpreferenciaPK());
        }
        actxpreferencia.getActxpreferenciaPK().setActividadpwaId(actxpreferencia.getActividadpwa().getId());
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividadpwa actividadpwa = actxpreferencia.getActividadpwa();
            if (actividadpwa != null) {
                actividadpwa = em.getReference(actividadpwa.getClass(), actividadpwa.getId());
                actxpreferencia.setActividadpwa(actividadpwa);
            }
           
            em.persist(actxpreferencia);
            if (actividadpwa != null) {
                actividadpwa.getActxpreferenciaList().add(actxpreferencia);
                actividadpwa = em.merge(actividadpwa);
            }
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActxpreferencia(actxpreferencia.getActxpreferenciaPK()) != null) {
                throw new PreexistingEntityException("Actxpreferencia " + actxpreferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actxpreferencia actxpreferencia) throws NonexistentEntityException, Exception {
        actxpreferencia.getActxpreferenciaPK().setActividadpwaId(actxpreferencia.getActividadpwa().getId());
        actxpreferencia.getActxpreferenciaPK().setPerfilPreferenciaCedula(actxpreferencia.getActxpreferenciaPK().getPerfilPreferenciaCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actxpreferencia persistentActxpreferencia = em.find(Actxpreferencia.class, actxpreferencia.getActxpreferenciaPK());
            Actividadpwa actividadpwaOld = persistentActxpreferencia.getActividadpwa();
            Actividadpwa actividadpwaNew = actxpreferencia.getActividadpwa();
            Object perfilPreferenciaNew;
            
            
            actxpreferencia = em.merge(actxpreferencia);
            if (actividadpwaOld != null && !actividadpwaOld.equals(actividadpwaNew)) {
                actividadpwaOld.getActxpreferenciaList().remove(actxpreferencia);
                actividadpwaOld = em.merge(actividadpwaOld);
            }
            if (actividadpwaNew != null && !actividadpwaNew.equals(actividadpwaOld)) {
                actividadpwaNew.getActxpreferenciaList().add(actxpreferencia);
                actividadpwaNew = em.merge(actividadpwaNew);
            }}
            catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ActxpreferenciaPK id = actxpreferencia.getActxpreferenciaPK();
                if (findActxpreferencia(id) == null) {
                    throw new NonexistentEntityException("The actxpreferencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ActxpreferenciaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actxpreferencia actxpreferencia;
            try {
                actxpreferencia = em.getReference(Actxpreferencia.class, id);
                actxpreferencia.getActxpreferenciaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actxpreferencia with id " + id + " no longer exists.", enfe);
            }
            Actividadpwa actividadpwa = actxpreferencia.getActividadpwa();
            if (actividadpwa != null) {
                actividadpwa.getActxpreferenciaList().remove(actxpreferencia);
                actividadpwa = em.merge(actividadpwa);
            }
            
            em.remove(actxpreferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actxpreferencia> findActxpreferenciaEntities() {
        return findActxpreferenciaEntities(true, -1, -1);
    }

    public List<Actxpreferencia> findActxpreferenciaEntities(int maxResults, int firstResult) {
        return findActxpreferenciaEntities(false, maxResults, firstResult);
    }

    private List<Actxpreferencia> findActxpreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actxpreferencia.class));
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

    public Actxpreferencia findActxpreferencia(ActxpreferenciaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actxpreferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getActxpreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actxpreferencia> rt = cq.from(Actxpreferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
