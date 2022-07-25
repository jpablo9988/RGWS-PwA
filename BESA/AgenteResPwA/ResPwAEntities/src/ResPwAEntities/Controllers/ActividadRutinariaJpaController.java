/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.ActividadRutinaria;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.PerfilMedico;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class ActividadRutinariaJpaController implements Serializable {

    public ActividadRutinariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadRutinaria actividadRutinaria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilMedico medicoPwaCedula = actividadRutinaria.getMedicoPwaCedula();
            if (medicoPwaCedula != null) {
                medicoPwaCedula = em.getReference(medicoPwaCedula.getClass(), medicoPwaCedula.getPerfilPwaCedula());
                actividadRutinaria.setMedicoPwaCedula(medicoPwaCedula);
            }
            em.persist(actividadRutinaria);
            if (medicoPwaCedula != null) {
                medicoPwaCedula.getActividadRutinariaList().add(actividadRutinaria);
                medicoPwaCedula = em.merge(medicoPwaCedula);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadRutinaria(actividadRutinaria.getId()) != null) {
                throw new PreexistingEntityException("ActividadRutinaria " + actividadRutinaria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadRutinaria actividadRutinaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadRutinaria persistentActividadRutinaria = em.find(ActividadRutinaria.class, actividadRutinaria.getId());
            PerfilMedico medicoPwaCedulaOld = persistentActividadRutinaria.getMedicoPwaCedula();
            PerfilMedico medicoPwaCedulaNew = actividadRutinaria.getMedicoPwaCedula();
            if (medicoPwaCedulaNew != null) {
                medicoPwaCedulaNew = em.getReference(medicoPwaCedulaNew.getClass(), medicoPwaCedulaNew.getPerfilPwaCedula());
                actividadRutinaria.setMedicoPwaCedula(medicoPwaCedulaNew);
            }
            actividadRutinaria = em.merge(actividadRutinaria);
            if (medicoPwaCedulaOld != null && !medicoPwaCedulaOld.equals(medicoPwaCedulaNew)) {
                medicoPwaCedulaOld.getActividadRutinariaList().remove(actividadRutinaria);
                medicoPwaCedulaOld = em.merge(medicoPwaCedulaOld);
            }
            if (medicoPwaCedulaNew != null && !medicoPwaCedulaNew.equals(medicoPwaCedulaOld)) {
                medicoPwaCedulaNew.getActividadRutinariaList().add(actividadRutinaria);
                medicoPwaCedulaNew = em.merge(medicoPwaCedulaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = actividadRutinaria.getId();
                if (findActividadRutinaria(id) == null) {
                    throw new NonexistentEntityException("The actividadRutinaria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadRutinaria actividadRutinaria;
            try {
                actividadRutinaria = em.getReference(ActividadRutinaria.class, id);
                actividadRutinaria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadRutinaria with id " + id + " no longer exists.", enfe);
            }
            PerfilMedico medicoPwaCedula = actividadRutinaria.getMedicoPwaCedula();
            if (medicoPwaCedula != null) {
                medicoPwaCedula.getActividadRutinariaList().remove(actividadRutinaria);
                medicoPwaCedula = em.merge(medicoPwaCedula);
            }
            em.remove(actividadRutinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadRutinaria> findActividadRutinariaEntities() {
        return findActividadRutinariaEntities(true, -1, -1);
    }

    public List<ActividadRutinaria> findActividadRutinariaEntities(int maxResults, int firstResult) {
        return findActividadRutinariaEntities(false, maxResults, firstResult);
    }

    private List<ActividadRutinaria> findActividadRutinariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadRutinaria.class));
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

    public ActividadRutinaria findActividadRutinaria(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadRutinaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadRutinariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadRutinaria> rt = cq.from(ActividadRutinaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
