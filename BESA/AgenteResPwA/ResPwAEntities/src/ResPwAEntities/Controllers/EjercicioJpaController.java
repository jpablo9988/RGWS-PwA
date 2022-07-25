/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Ejercicio;
import ResPwAEntities.EjercicioPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Rutina;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class EjercicioJpaController implements Serializable {

    public EjercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ejercicio ejercicio) throws PreexistingEntityException, Exception {
        if (ejercicio.getEjercicioPK() == null) {
            ejercicio.setEjercicioPK(new EjercicioPK());
        }
        ejercicio.getEjercicioPK().setRutinaId(ejercicio.getRutina().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rutina rutina = ejercicio.getRutina();
            if (rutina != null) {
                rutina = em.getReference(rutina.getClass(), rutina.getId());
                ejercicio.setRutina(rutina);
            }
            em.persist(ejercicio);
            if (rutina != null) {
                rutina.getEjercicioList().add(ejercicio);
                rutina = em.merge(rutina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEjercicio(ejercicio.getEjercicioPK()) != null) {
                throw new PreexistingEntityException("Ejercicio " + ejercicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ejercicio ejercicio) throws NonexistentEntityException, Exception {
        ejercicio.getEjercicioPK().setRutinaId(ejercicio.getRutina().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ejercicio persistentEjercicio = em.find(Ejercicio.class, ejercicio.getEjercicioPK());
            Rutina rutinaOld = persistentEjercicio.getRutina();
            Rutina rutinaNew = ejercicio.getRutina();
            if (rutinaNew != null) {
                rutinaNew = em.getReference(rutinaNew.getClass(), rutinaNew.getId());
                ejercicio.setRutina(rutinaNew);
            }
            ejercicio = em.merge(ejercicio);
            if (rutinaOld != null && !rutinaOld.equals(rutinaNew)) {
                rutinaOld.getEjercicioList().remove(ejercicio);
                rutinaOld = em.merge(rutinaOld);
            }
            if (rutinaNew != null && !rutinaNew.equals(rutinaOld)) {
                rutinaNew.getEjercicioList().add(ejercicio);
                rutinaNew = em.merge(rutinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EjercicioPK id = ejercicio.getEjercicioPK();
                if (findEjercicio(id) == null) {
                    throw new NonexistentEntityException("The ejercicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EjercicioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ejercicio ejercicio;
            try {
                ejercicio = em.getReference(Ejercicio.class, id);
                ejercicio.getEjercicioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ejercicio with id " + id + " no longer exists.", enfe);
            }
            Rutina rutina = ejercicio.getRutina();
            if (rutina != null) {
                rutina.getEjercicioList().remove(ejercicio);
                rutina = em.merge(rutina);
            }
            em.remove(ejercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ejercicio> findEjercicioEntities() {
        return findEjercicioEntities(true, -1, -1);
    }

    public List<Ejercicio> findEjercicioEntities(int maxResults, int firstResult) {
        return findEjercicioEntities(false, maxResults, firstResult);
    }

    private List<Ejercicio> findEjercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ejercicio.class));
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

    public Ejercicio findEjercicio(EjercicioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ejercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEjercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ejercicio> rt = cq.from(Ejercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
