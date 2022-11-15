/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Familiar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.PerfilPwa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class FamiliarJpaController implements Serializable {

    public FamiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Familiar familiar) throws PreexistingEntityException, Exception {
        if (familiar.getPerfilPwaList() == null) {
            familiar.setPerfilPwaList(new ArrayList<PerfilPwa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilPwa> attachedPerfilPwaList = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListPerfilPwaToAttach : familiar.getPerfilPwaList()) {
                perfilPwaListPerfilPwaToAttach = em.getReference(perfilPwaListPerfilPwaToAttach.getClass(), perfilPwaListPerfilPwaToAttach.getCedula());
                attachedPerfilPwaList.add(perfilPwaListPerfilPwaToAttach);
            }
            familiar.setPerfilPwaList(attachedPerfilPwaList);
            em.persist(familiar);
            for (PerfilPwa perfilPwaListPerfilPwa : familiar.getPerfilPwaList()) {
                perfilPwaListPerfilPwa.getFamiliarList().add(familiar);
                perfilPwaListPerfilPwa = em.merge(perfilPwaListPerfilPwa);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFamiliar(familiar.getId()) != null) {
                throw new PreexistingEntityException("Familiar " + familiar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Familiar familiar) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar persistentFamiliar = em.find(Familiar.class, familiar.getId());
            List<PerfilPwa> perfilPwaListOld = persistentFamiliar.getPerfilPwaList();
            List<PerfilPwa> perfilPwaListNew = familiar.getPerfilPwaList();
            List<PerfilPwa> attachedPerfilPwaListNew = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListNewPerfilPwaToAttach : perfilPwaListNew) {
                perfilPwaListNewPerfilPwaToAttach = em.getReference(perfilPwaListNewPerfilPwaToAttach.getClass(), perfilPwaListNewPerfilPwaToAttach.getCedula());
                attachedPerfilPwaListNew.add(perfilPwaListNewPerfilPwaToAttach);
            }
            perfilPwaListNew = attachedPerfilPwaListNew;
            familiar.setPerfilPwaList(perfilPwaListNew);
            familiar = em.merge(familiar);
            for (PerfilPwa perfilPwaListOldPerfilPwa : perfilPwaListOld) {
                if (!perfilPwaListNew.contains(perfilPwaListOldPerfilPwa)) {
                    perfilPwaListOldPerfilPwa.getFamiliarList().remove(familiar);
                    perfilPwaListOldPerfilPwa = em.merge(perfilPwaListOldPerfilPwa);
                }
            }
            for (PerfilPwa perfilPwaListNewPerfilPwa : perfilPwaListNew) {
                if (!perfilPwaListOld.contains(perfilPwaListNewPerfilPwa)) {
                    perfilPwaListNewPerfilPwa.getFamiliarList().add(familiar);
                    perfilPwaListNewPerfilPwa = em.merge(perfilPwaListNewPerfilPwa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = familiar.getId();
                if (findFamiliar(id) == null) {
                    throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar familiar;
            try {
                familiar = em.getReference(Familiar.class, id);
                familiar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.", enfe);
            }
            List<PerfilPwa> perfilPwaList = familiar.getPerfilPwaList();
            for (PerfilPwa perfilPwaListPerfilPwa : perfilPwaList) {
                perfilPwaListPerfilPwa.getFamiliarList().remove(familiar);
                perfilPwaListPerfilPwa = em.merge(perfilPwaListPerfilPwa);
            }
            em.remove(familiar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Familiar> findFamiliarEntities() {
        return findFamiliarEntities(true, -1, -1);
    }

    public List<Familiar> findFamiliarEntities(int maxResults, int firstResult) {
        return findFamiliarEntities(false, maxResults, firstResult);
    }

    private List<Familiar> findFamiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Familiar.class));
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

    public Familiar findFamiliar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Familiar.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Familiar> rt = cq.from(Familiar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
