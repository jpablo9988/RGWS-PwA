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
import ResPwAEntities.CategoriaEntrenamiento;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Intensidad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class IntensidadJpaController implements Serializable {

    public IntensidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intensidad intensidad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaEntrenamiento categoriaEntrenamientoTipo = intensidad.getCategoriaEntrenamientoTipo();
            if (categoriaEntrenamientoTipo != null) {
                categoriaEntrenamientoTipo = em.getReference(categoriaEntrenamientoTipo.getClass(), categoriaEntrenamientoTipo.getTipo());
                intensidad.setCategoriaEntrenamientoTipo(categoriaEntrenamientoTipo);
            }
            em.persist(intensidad);
            if (categoriaEntrenamientoTipo != null) {
                categoriaEntrenamientoTipo.getIntensidadList().add(intensidad);
                categoriaEntrenamientoTipo = em.merge(categoriaEntrenamientoTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIntensidad(intensidad.getId()) != null) {
                throw new PreexistingEntityException("Intensidad " + intensidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intensidad intensidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intensidad persistentIntensidad = em.find(Intensidad.class, intensidad.getId());
            CategoriaEntrenamiento categoriaEntrenamientoTipoOld = persistentIntensidad.getCategoriaEntrenamientoTipo();
            CategoriaEntrenamiento categoriaEntrenamientoTipoNew = intensidad.getCategoriaEntrenamientoTipo();
            if (categoriaEntrenamientoTipoNew != null) {
                categoriaEntrenamientoTipoNew = em.getReference(categoriaEntrenamientoTipoNew.getClass(), categoriaEntrenamientoTipoNew.getTipo());
                intensidad.setCategoriaEntrenamientoTipo(categoriaEntrenamientoTipoNew);
            }
            intensidad = em.merge(intensidad);
            if (categoriaEntrenamientoTipoOld != null && !categoriaEntrenamientoTipoOld.equals(categoriaEntrenamientoTipoNew)) {
                categoriaEntrenamientoTipoOld.getIntensidadList().remove(intensidad);
                categoriaEntrenamientoTipoOld = em.merge(categoriaEntrenamientoTipoOld);
            }
            if (categoriaEntrenamientoTipoNew != null && !categoriaEntrenamientoTipoNew.equals(categoriaEntrenamientoTipoOld)) {
                categoriaEntrenamientoTipoNew.getIntensidadList().add(intensidad);
                categoriaEntrenamientoTipoNew = em.merge(categoriaEntrenamientoTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = intensidad.getId();
                if (findIntensidad(id) == null) {
                    throw new NonexistentEntityException("The intensidad with id " + id + " no longer exists.");
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
            Intensidad intensidad;
            try {
                intensidad = em.getReference(Intensidad.class, id);
                intensidad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intensidad with id " + id + " no longer exists.", enfe);
            }
            CategoriaEntrenamiento categoriaEntrenamientoTipo = intensidad.getCategoriaEntrenamientoTipo();
            if (categoriaEntrenamientoTipo != null) {
                categoriaEntrenamientoTipo.getIntensidadList().remove(intensidad);
                categoriaEntrenamientoTipo = em.merge(categoriaEntrenamientoTipo);
            }
            em.remove(intensidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intensidad> findIntensidadEntities() {
        return findIntensidadEntities(true, -1, -1);
    }

    public List<Intensidad> findIntensidadEntities(int maxResults, int firstResult) {
        return findIntensidadEntities(false, maxResults, firstResult);
    }

    private List<Intensidad> findIntensidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Intensidad.class));
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

    public Intensidad findIntensidad(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intensidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getIntensidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Intensidad> rt = cq.from(Intensidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
