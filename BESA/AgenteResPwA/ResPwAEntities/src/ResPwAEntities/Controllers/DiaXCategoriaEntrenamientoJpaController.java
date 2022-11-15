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
import ResPwAEntities.Dia;
import ResPwAEntities.DiaXCategoriaEntrenamiento;
import ResPwAEntities.DiaXCategoriaEntrenamientoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class DiaXCategoriaEntrenamientoJpaController implements Serializable {

    public DiaXCategoriaEntrenamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DiaXCategoriaEntrenamiento diaXCategoriaEntrenamiento) throws PreexistingEntityException, Exception {
        if (diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK() == null) {
            diaXCategoriaEntrenamiento.setDiaXCategoriaEntrenamientoPK(new DiaXCategoriaEntrenamientoPK());
        }
        diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK().setDiaNombre(diaXCategoriaEntrenamiento.getDia().getNombre());
        diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK().setCategoriaTipo(diaXCategoriaEntrenamiento.getCategoriaEntrenamiento().getTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaEntrenamiento categoriaEntrenamiento = diaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
            if (categoriaEntrenamiento != null) {
                categoriaEntrenamiento = em.getReference(categoriaEntrenamiento.getClass(), categoriaEntrenamiento.getTipo());
                diaXCategoriaEntrenamiento.setCategoriaEntrenamiento(categoriaEntrenamiento);
            }
            Dia dia = diaXCategoriaEntrenamiento.getDia();
            if (dia != null) {
                dia = em.getReference(dia.getClass(), dia.getNombre());
                diaXCategoriaEntrenamiento.setDia(dia);
            }
            em.persist(diaXCategoriaEntrenamiento);
            if (categoriaEntrenamiento != null) {
                categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().add(diaXCategoriaEntrenamiento);
                categoriaEntrenamiento = em.merge(categoriaEntrenamiento);
            }
            if (dia != null) {
                dia.getDiaXCategoriaEntrenamientoList().add(diaXCategoriaEntrenamiento);
                dia = em.merge(dia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiaXCategoriaEntrenamiento(diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK()) != null) {
                throw new PreexistingEntityException("DiaXCategoriaEntrenamiento " + diaXCategoriaEntrenamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DiaXCategoriaEntrenamiento diaXCategoriaEntrenamiento) throws NonexistentEntityException, Exception {
        diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK().setDiaNombre(diaXCategoriaEntrenamiento.getDia().getNombre());
        diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK().setCategoriaTipo(diaXCategoriaEntrenamiento.getCategoriaEntrenamiento().getTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DiaXCategoriaEntrenamiento persistentDiaXCategoriaEntrenamiento = em.find(DiaXCategoriaEntrenamiento.class, diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK());
            CategoriaEntrenamiento categoriaEntrenamientoOld = persistentDiaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
            CategoriaEntrenamiento categoriaEntrenamientoNew = diaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
            Dia diaOld = persistentDiaXCategoriaEntrenamiento.getDia();
            Dia diaNew = diaXCategoriaEntrenamiento.getDia();
            if (categoriaEntrenamientoNew != null) {
                categoriaEntrenamientoNew = em.getReference(categoriaEntrenamientoNew.getClass(), categoriaEntrenamientoNew.getTipo());
                diaXCategoriaEntrenamiento.setCategoriaEntrenamiento(categoriaEntrenamientoNew);
            }
            if (diaNew != null) {
                diaNew = em.getReference(diaNew.getClass(), diaNew.getNombre());
                diaXCategoriaEntrenamiento.setDia(diaNew);
            }
            diaXCategoriaEntrenamiento = em.merge(diaXCategoriaEntrenamiento);
            if (categoriaEntrenamientoOld != null && !categoriaEntrenamientoOld.equals(categoriaEntrenamientoNew)) {
                categoriaEntrenamientoOld.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamiento);
                categoriaEntrenamientoOld = em.merge(categoriaEntrenamientoOld);
            }
            if (categoriaEntrenamientoNew != null && !categoriaEntrenamientoNew.equals(categoriaEntrenamientoOld)) {
                categoriaEntrenamientoNew.getDiaXCategoriaEntrenamientoList().add(diaXCategoriaEntrenamiento);
                categoriaEntrenamientoNew = em.merge(categoriaEntrenamientoNew);
            }
            if (diaOld != null && !diaOld.equals(diaNew)) {
                diaOld.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamiento);
                diaOld = em.merge(diaOld);
            }
            if (diaNew != null && !diaNew.equals(diaOld)) {
                diaNew.getDiaXCategoriaEntrenamientoList().add(diaXCategoriaEntrenamiento);
                diaNew = em.merge(diaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DiaXCategoriaEntrenamientoPK id = diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK();
                if (findDiaXCategoriaEntrenamiento(id) == null) {
                    throw new NonexistentEntityException("The diaXCategoriaEntrenamiento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DiaXCategoriaEntrenamientoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DiaXCategoriaEntrenamiento diaXCategoriaEntrenamiento;
            try {
                diaXCategoriaEntrenamiento = em.getReference(DiaXCategoriaEntrenamiento.class, id);
                diaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The diaXCategoriaEntrenamiento with id " + id + " no longer exists.", enfe);
            }
            CategoriaEntrenamiento categoriaEntrenamiento = diaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
            if (categoriaEntrenamiento != null) {
                categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamiento);
                categoriaEntrenamiento = em.merge(categoriaEntrenamiento);
            }
            Dia dia = diaXCategoriaEntrenamiento.getDia();
            if (dia != null) {
                dia.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamiento);
                dia = em.merge(dia);
            }
            em.remove(diaXCategoriaEntrenamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DiaXCategoriaEntrenamiento> findDiaXCategoriaEntrenamientoEntities() {
        return findDiaXCategoriaEntrenamientoEntities(true, -1, -1);
    }

    public List<DiaXCategoriaEntrenamiento> findDiaXCategoriaEntrenamientoEntities(int maxResults, int firstResult) {
        return findDiaXCategoriaEntrenamientoEntities(false, maxResults, firstResult);
    }

    private List<DiaXCategoriaEntrenamiento> findDiaXCategoriaEntrenamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DiaXCategoriaEntrenamiento.class));
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

    public DiaXCategoriaEntrenamiento findDiaXCategoriaEntrenamiento(DiaXCategoriaEntrenamientoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DiaXCategoriaEntrenamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaXCategoriaEntrenamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DiaXCategoriaEntrenamiento> rt = cq.from(DiaXCategoriaEntrenamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
