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
import ResPwAEntities.Cancion;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PreferenciaXCancion;
import ResPwAEntities.PreferenciaXCancionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class PreferenciaXCancionJpaController implements Serializable {

    public PreferenciaXCancionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreferenciaXCancion preferenciaXCancion) throws PreexistingEntityException, Exception {
        if (preferenciaXCancion.getPreferenciaXCancionPK() == null) {
            preferenciaXCancion.setPreferenciaXCancionPK(new PreferenciaXCancionPK());
        }
        preferenciaXCancion.getPreferenciaXCancionPK().setPreferenciaPwaCedula(preferenciaXCancion.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXCancion.getPreferenciaXCancionPK().setCancionNombre(preferenciaXCancion.getCancion().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion = preferenciaXCancion.getCancion();
            if (cancion != null) {
                cancion = em.getReference(cancion.getClass(), cancion.getNombre());
                preferenciaXCancion.setCancion(cancion);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXCancion.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                preferenciaXCancion.setPerfilPreferencia(perfilPreferencia);
            }
            em.persist(preferenciaXCancion);
            if (cancion != null) {
                cancion.getPreferenciaXCancionList().add(preferenciaXCancion);
                cancion = em.merge(cancion);
            }
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXCancionList().add(preferenciaXCancion);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferenciaXCancion(preferenciaXCancion.getPreferenciaXCancionPK()) != null) {
                throw new PreexistingEntityException("PreferenciaXCancion " + preferenciaXCancion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreferenciaXCancion preferenciaXCancion) throws NonexistentEntityException, Exception {
        preferenciaXCancion.getPreferenciaXCancionPK().setPreferenciaPwaCedula(preferenciaXCancion.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXCancion.getPreferenciaXCancionPK().setCancionNombre(preferenciaXCancion.getCancion().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXCancion persistentPreferenciaXCancion = em.find(PreferenciaXCancion.class, preferenciaXCancion.getPreferenciaXCancionPK());
            Cancion cancionOld = persistentPreferenciaXCancion.getCancion();
            Cancion cancionNew = preferenciaXCancion.getCancion();
            PerfilPreferencia perfilPreferenciaOld = persistentPreferenciaXCancion.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = preferenciaXCancion.getPerfilPreferencia();
            if (cancionNew != null) {
                cancionNew = em.getReference(cancionNew.getClass(), cancionNew.getNombre());
                preferenciaXCancion.setCancion(cancionNew);
            }
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                preferenciaXCancion.setPerfilPreferencia(perfilPreferenciaNew);
            }
            preferenciaXCancion = em.merge(preferenciaXCancion);
            if (cancionOld != null && !cancionOld.equals(cancionNew)) {
                cancionOld.getPreferenciaXCancionList().remove(preferenciaXCancion);
                cancionOld = em.merge(cancionOld);
            }
            if (cancionNew != null && !cancionNew.equals(cancionOld)) {
                cancionNew.getPreferenciaXCancionList().add(preferenciaXCancion);
                cancionNew = em.merge(cancionNew);
            }
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                perfilPreferenciaOld.getPreferenciaXCancionList().remove(preferenciaXCancion);
                perfilPreferenciaOld = em.merge(perfilPreferenciaOld);
            }
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                perfilPreferenciaNew.getPreferenciaXCancionList().add(preferenciaXCancion);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreferenciaXCancionPK id = preferenciaXCancion.getPreferenciaXCancionPK();
                if (findPreferenciaXCancion(id) == null) {
                    throw new NonexistentEntityException("The preferenciaXCancion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreferenciaXCancionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXCancion preferenciaXCancion;
            try {
                preferenciaXCancion = em.getReference(PreferenciaXCancion.class, id);
                preferenciaXCancion.getPreferenciaXCancionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferenciaXCancion with id " + id + " no longer exists.", enfe);
            }
            Cancion cancion = preferenciaXCancion.getCancion();
            if (cancion != null) {
                cancion.getPreferenciaXCancionList().remove(preferenciaXCancion);
                cancion = em.merge(cancion);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXCancion.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXCancionList().remove(preferenciaXCancion);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.remove(preferenciaXCancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreferenciaXCancion> findPreferenciaXCancionEntities() {
        return findPreferenciaXCancionEntities(true, -1, -1);
    }

    public List<PreferenciaXCancion> findPreferenciaXCancionEntities(int maxResults, int firstResult) {
        return findPreferenciaXCancionEntities(false, maxResults, firstResult);
    }

    private List<PreferenciaXCancion> findPreferenciaXCancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreferenciaXCancion.class));
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

    public PreferenciaXCancion findPreferenciaXCancion(PreferenciaXCancionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreferenciaXCancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciaXCancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreferenciaXCancion> rt = cq.from(PreferenciaXCancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
