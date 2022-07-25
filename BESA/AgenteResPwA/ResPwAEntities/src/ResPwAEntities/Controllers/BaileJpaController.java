/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Baile;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Genero;
import ResPwAEntities.PreferenciaXBaile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class BaileJpaController implements Serializable {

    public BaileJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Baile baile) throws PreexistingEntityException, Exception {
        if (baile.getPreferenciaXBaileList() == null) {
            baile.setPreferenciaXBaileList(new ArrayList<PreferenciaXBaile>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = baile.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getGenero());
                baile.setGenero(genero);
            }
            List<PreferenciaXBaile> attachedPreferenciaXBaileList = new ArrayList<PreferenciaXBaile>();
            for (PreferenciaXBaile preferenciaXBaileListPreferenciaXBaileToAttach : baile.getPreferenciaXBaileList()) {
                preferenciaXBaileListPreferenciaXBaileToAttach = em.getReference(preferenciaXBaileListPreferenciaXBaileToAttach.getClass(), preferenciaXBaileListPreferenciaXBaileToAttach.getPreferenciaXBailePK());
                attachedPreferenciaXBaileList.add(preferenciaXBaileListPreferenciaXBaileToAttach);
            }
            baile.setPreferenciaXBaileList(attachedPreferenciaXBaileList);
            em.persist(baile);
            if (genero != null) {
                genero.getBaileList().add(baile);
                genero = em.merge(genero);
            }
            for (PreferenciaXBaile preferenciaXBaileListPreferenciaXBaile : baile.getPreferenciaXBaileList()) {
                Baile oldBaileOfPreferenciaXBaileListPreferenciaXBaile = preferenciaXBaileListPreferenciaXBaile.getBaile();
                preferenciaXBaileListPreferenciaXBaile.setBaile(baile);
                preferenciaXBaileListPreferenciaXBaile = em.merge(preferenciaXBaileListPreferenciaXBaile);
                if (oldBaileOfPreferenciaXBaileListPreferenciaXBaile != null) {
                    oldBaileOfPreferenciaXBaileListPreferenciaXBaile.getPreferenciaXBaileList().remove(preferenciaXBaileListPreferenciaXBaile);
                    oldBaileOfPreferenciaXBaileListPreferenciaXBaile = em.merge(oldBaileOfPreferenciaXBaileListPreferenciaXBaile);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBaile(baile.getId()) != null) {
                throw new PreexistingEntityException("Baile " + baile + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Baile baile) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Baile persistentBaile = em.find(Baile.class, baile.getId());
            Genero generoOld = persistentBaile.getGenero();
            Genero generoNew = baile.getGenero();
            List<PreferenciaXBaile> preferenciaXBaileListOld = persistentBaile.getPreferenciaXBaileList();
            List<PreferenciaXBaile> preferenciaXBaileListNew = baile.getPreferenciaXBaileList();
            List<String> illegalOrphanMessages = null;
            for (PreferenciaXBaile preferenciaXBaileListOldPreferenciaXBaile : preferenciaXBaileListOld) {
                if (!preferenciaXBaileListNew.contains(preferenciaXBaileListOldPreferenciaXBaile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXBaile " + preferenciaXBaileListOldPreferenciaXBaile + " since its baile field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getGenero());
                baile.setGenero(generoNew);
            }
            List<PreferenciaXBaile> attachedPreferenciaXBaileListNew = new ArrayList<PreferenciaXBaile>();
            for (PreferenciaXBaile preferenciaXBaileListNewPreferenciaXBaileToAttach : preferenciaXBaileListNew) {
                preferenciaXBaileListNewPreferenciaXBaileToAttach = em.getReference(preferenciaXBaileListNewPreferenciaXBaileToAttach.getClass(), preferenciaXBaileListNewPreferenciaXBaileToAttach.getPreferenciaXBailePK());
                attachedPreferenciaXBaileListNew.add(preferenciaXBaileListNewPreferenciaXBaileToAttach);
            }
            preferenciaXBaileListNew = attachedPreferenciaXBaileListNew;
            baile.setPreferenciaXBaileList(preferenciaXBaileListNew);
            baile = em.merge(baile);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getBaileList().remove(baile);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getBaileList().add(baile);
                generoNew = em.merge(generoNew);
            }
            for (PreferenciaXBaile preferenciaXBaileListNewPreferenciaXBaile : preferenciaXBaileListNew) {
                if (!preferenciaXBaileListOld.contains(preferenciaXBaileListNewPreferenciaXBaile)) {
                    Baile oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile = preferenciaXBaileListNewPreferenciaXBaile.getBaile();
                    preferenciaXBaileListNewPreferenciaXBaile.setBaile(baile);
                    preferenciaXBaileListNewPreferenciaXBaile = em.merge(preferenciaXBaileListNewPreferenciaXBaile);
                    if (oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile != null && !oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile.equals(baile)) {
                        oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile.getPreferenciaXBaileList().remove(preferenciaXBaileListNewPreferenciaXBaile);
                        oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile = em.merge(oldBaileOfPreferenciaXBaileListNewPreferenciaXBaile);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = baile.getId();
                if (findBaile(id) == null) {
                    throw new NonexistentEntityException("The baile with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Baile baile;
            try {
                baile = em.getReference(Baile.class, id);
                baile.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The baile with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreferenciaXBaile> preferenciaXBaileListOrphanCheck = baile.getPreferenciaXBaileList();
            for (PreferenciaXBaile preferenciaXBaileListOrphanCheckPreferenciaXBaile : preferenciaXBaileListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Baile (" + baile + ") cannot be destroyed since the PreferenciaXBaile " + preferenciaXBaileListOrphanCheckPreferenciaXBaile + " in its preferenciaXBaileList field has a non-nullable baile field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genero genero = baile.getGenero();
            if (genero != null) {
                genero.getBaileList().remove(baile);
                genero = em.merge(genero);
            }
            em.remove(baile);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Baile> findBaileEntities() {
        return findBaileEntities(true, -1, -1);
    }

    public List<Baile> findBaileEntities(int maxResults, int firstResult) {
        return findBaileEntities(false, maxResults, firstResult);
    }

    private List<Baile> findBaileEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Baile.class));
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

    public Baile findBaile(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Baile.class, id);
        } finally {
            em.close();
        }
    }

    public int getBaileCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Baile> rt = cq.from(Baile.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
