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
import ResPwAEntities.Ejercicio;
import ResPwAEntities.FraseInspiracional;
import ResPwAEntities.FraseInspiracionalPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class FraseInspiracionalJpaController implements Serializable {

    public FraseInspiracionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FraseInspiracional fraseInspiracional) throws PreexistingEntityException, Exception {
        if (fraseInspiracional.getFraseInspiracionalPK() == null) {
            fraseInspiracional.setFraseInspiracionalPK(new FraseInspiracionalPK());
        }
        fraseInspiracional.getFraseInspiracionalPK().setEjercicio(fraseInspiracional.getEjercicio1().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ejercicio ejercicio1 = fraseInspiracional.getEjercicio1();
            if (ejercicio1 != null) {
                ejercicio1 = em.getReference(ejercicio1.getClass(), ejercicio1.getNombre());
                fraseInspiracional.setEjercicio1(ejercicio1);
            }
            em.persist(fraseInspiracional);
            if (ejercicio1 != null) {
                ejercicio1.getFraseInspiracionalList().add(fraseInspiracional);
                ejercicio1 = em.merge(ejercicio1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFraseInspiracional(fraseInspiracional.getFraseInspiracionalPK()) != null) {
                throw new PreexistingEntityException("FraseInspiracional " + fraseInspiracional + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FraseInspiracional fraseInspiracional) throws NonexistentEntityException, Exception {
        fraseInspiracional.getFraseInspiracionalPK().setEjercicio(fraseInspiracional.getEjercicio1().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FraseInspiracional persistentFraseInspiracional = em.find(FraseInspiracional.class, fraseInspiracional.getFraseInspiracionalPK());
            Ejercicio ejercicio1Old = persistentFraseInspiracional.getEjercicio1();
            Ejercicio ejercicio1New = fraseInspiracional.getEjercicio1();
            if (ejercicio1New != null) {
                ejercicio1New = em.getReference(ejercicio1New.getClass(), ejercicio1New.getNombre());
                fraseInspiracional.setEjercicio1(ejercicio1New);
            }
            fraseInspiracional = em.merge(fraseInspiracional);
            if (ejercicio1Old != null && !ejercicio1Old.equals(ejercicio1New)) {
                ejercicio1Old.getFraseInspiracionalList().remove(fraseInspiracional);
                ejercicio1Old = em.merge(ejercicio1Old);
            }
            if (ejercicio1New != null && !ejercicio1New.equals(ejercicio1Old)) {
                ejercicio1New.getFraseInspiracionalList().add(fraseInspiracional);
                ejercicio1New = em.merge(ejercicio1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FraseInspiracionalPK id = fraseInspiracional.getFraseInspiracionalPK();
                if (findFraseInspiracional(id) == null) {
                    throw new NonexistentEntityException("The fraseInspiracional with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FraseInspiracionalPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FraseInspiracional fraseInspiracional;
            try {
                fraseInspiracional = em.getReference(FraseInspiracional.class, id);
                fraseInspiracional.getFraseInspiracionalPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fraseInspiracional with id " + id + " no longer exists.", enfe);
            }
            Ejercicio ejercicio1 = fraseInspiracional.getEjercicio1();
            if (ejercicio1 != null) {
                ejercicio1.getFraseInspiracionalList().remove(fraseInspiracional);
                ejercicio1 = em.merge(ejercicio1);
            }
            em.remove(fraseInspiracional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FraseInspiracional> findFraseInspiracionalEntities() {
        return findFraseInspiracionalEntities(true, -1, -1);
    }

    public List<FraseInspiracional> findFraseInspiracionalEntities(int maxResults, int firstResult) {
        return findFraseInspiracionalEntities(false, maxResults, firstResult);
    }

    private List<FraseInspiracional> findFraseInspiracionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FraseInspiracional.class));
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

    public FraseInspiracional findFraseInspiracional(FraseInspiracionalPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FraseInspiracional.class, id);
        } finally {
            em.close();
        }
    }

    public int getFraseInspiracionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FraseInspiracional> rt = cq.from(FraseInspiracional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
