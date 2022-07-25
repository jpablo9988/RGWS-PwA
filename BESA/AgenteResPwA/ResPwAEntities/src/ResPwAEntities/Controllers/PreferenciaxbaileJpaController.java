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
import ResPwAEntities.Baile;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PreferenciaXBaile;
import ResPwAEntities.PreferenciaXBailePK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PreferenciaXBaileJpaController implements Serializable {

    public PreferenciaXBaileJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreferenciaXBaile preferenciaXBaile) throws PreexistingEntityException, Exception {
        if (preferenciaXBaile.getPreferenciaXBailePK() == null) {
            preferenciaXBaile.setPreferenciaXBailePK(new PreferenciaXBailePK());
        }
        preferenciaXBaile.getPreferenciaXBailePK().setPreferenciaPwaCedula(preferenciaXBaile.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXBaile.getPreferenciaXBailePK().setBaileId(preferenciaXBaile.getBaile().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Baile baile = preferenciaXBaile.getBaile();
            if (baile != null) {
                baile = em.getReference(baile.getClass(), baile.getId());
                preferenciaXBaile.setBaile(baile);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXBaile.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                preferenciaXBaile.setPerfilPreferencia(perfilPreferencia);
            }
            em.persist(preferenciaXBaile);
            if (baile != null) {
                baile.getPreferenciaXBaileList().add(preferenciaXBaile);
                baile = em.merge(baile);
            }
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXBaileList().add(preferenciaXBaile);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferenciaXBaile(preferenciaXBaile.getPreferenciaXBailePK()) != null) {
                throw new PreexistingEntityException("PreferenciaXBaile " + preferenciaXBaile + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreferenciaXBaile preferenciaXBaile) throws NonexistentEntityException, Exception {
        preferenciaXBaile.getPreferenciaXBailePK().setPreferenciaPwaCedula(preferenciaXBaile.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXBaile.getPreferenciaXBailePK().setBaileId(preferenciaXBaile.getBaile().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXBaile persistentPreferenciaXBaile = em.find(PreferenciaXBaile.class, preferenciaXBaile.getPreferenciaXBailePK());
            Baile baileOld = persistentPreferenciaXBaile.getBaile();
            Baile baileNew = preferenciaXBaile.getBaile();
            PerfilPreferencia perfilPreferenciaOld = persistentPreferenciaXBaile.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = preferenciaXBaile.getPerfilPreferencia();
            if (baileNew != null) {
                baileNew = em.getReference(baileNew.getClass(), baileNew.getId());
                preferenciaXBaile.setBaile(baileNew);
            }
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                preferenciaXBaile.setPerfilPreferencia(perfilPreferenciaNew);
            }
            preferenciaXBaile = em.merge(preferenciaXBaile);
            if (baileOld != null && !baileOld.equals(baileNew)) {
                baileOld.getPreferenciaXBaileList().remove(preferenciaXBaile);
                baileOld = em.merge(baileOld);
            }
            if (baileNew != null && !baileNew.equals(baileOld)) {
                baileNew.getPreferenciaXBaileList().add(preferenciaXBaile);
                baileNew = em.merge(baileNew);
            }
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                perfilPreferenciaOld.getPreferenciaXBaileList().remove(preferenciaXBaile);
                perfilPreferenciaOld = em.merge(perfilPreferenciaOld);
            }
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                perfilPreferenciaNew.getPreferenciaXBaileList().add(preferenciaXBaile);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreferenciaXBailePK id = preferenciaXBaile.getPreferenciaXBailePK();
                if (findPreferenciaXBaile(id) == null) {
                    throw new NonexistentEntityException("The preferenciaXBaile with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreferenciaXBailePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXBaile preferenciaXBaile;
            try {
                preferenciaXBaile = em.getReference(PreferenciaXBaile.class, id);
                preferenciaXBaile.getPreferenciaXBailePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferenciaXBaile with id " + id + " no longer exists.", enfe);
            }
            Baile baile = preferenciaXBaile.getBaile();
            if (baile != null) {
                baile.getPreferenciaXBaileList().remove(preferenciaXBaile);
                baile = em.merge(baile);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXBaile.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXBaileList().remove(preferenciaXBaile);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.remove(preferenciaXBaile);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreferenciaXBaile> findPreferenciaXBaileEntities() {
        return findPreferenciaXBaileEntities(true, -1, -1);
    }

    public List<PreferenciaXBaile> findPreferenciaXBaileEntities(int maxResults, int firstResult) {
        return findPreferenciaXBaileEntities(false, maxResults, firstResult);
    }

    private List<PreferenciaXBaile> findPreferenciaXBaileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreferenciaXBaile.class));
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

    public PreferenciaXBaile findPreferenciaXBaile(PreferenciaXBailePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreferenciaXBaile.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciaXBaileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreferenciaXBaile> rt = cq.from(PreferenciaXBaile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
