/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.NivelEducativo;
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
public class NivelEducativoJpaController implements Serializable {

    public NivelEducativoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NivelEducativo nivelEducativo) throws PreexistingEntityException, Exception {
        if (nivelEducativo.getPerfilPwaList() == null) {
            nivelEducativo.setPerfilPwaList(new ArrayList<PerfilPwa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilPwa> attachedPerfilPwaList = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListPerfilPwaToAttach : nivelEducativo.getPerfilPwaList()) {
                perfilPwaListPerfilPwaToAttach = em.getReference(perfilPwaListPerfilPwaToAttach.getClass(), perfilPwaListPerfilPwaToAttach.getCedula());
                attachedPerfilPwaList.add(perfilPwaListPerfilPwaToAttach);
            }
            nivelEducativo.setPerfilPwaList(attachedPerfilPwaList);
            em.persist(nivelEducativo);
            for (PerfilPwa perfilPwaListPerfilPwa : nivelEducativo.getPerfilPwaList()) {
                NivelEducativo oldNivelEducativoTipoNeOfPerfilPwaListPerfilPwa = perfilPwaListPerfilPwa.getNivelEducativoTipoNe();
                perfilPwaListPerfilPwa.setNivelEducativoTipoNe(nivelEducativo);
                perfilPwaListPerfilPwa = em.merge(perfilPwaListPerfilPwa);
                if (oldNivelEducativoTipoNeOfPerfilPwaListPerfilPwa != null) {
                    oldNivelEducativoTipoNeOfPerfilPwaListPerfilPwa.getPerfilPwaList().remove(perfilPwaListPerfilPwa);
                    oldNivelEducativoTipoNeOfPerfilPwaListPerfilPwa = em.merge(oldNivelEducativoTipoNeOfPerfilPwaListPerfilPwa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNivelEducativo(nivelEducativo.getTipoNe()) != null) {
                throw new PreexistingEntityException("NivelEducativo " + nivelEducativo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NivelEducativo nivelEducativo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelEducativo persistentNivelEducativo = em.find(NivelEducativo.class, nivelEducativo.getTipoNe());
            List<PerfilPwa> perfilPwaListOld = persistentNivelEducativo.getPerfilPwaList();
            List<PerfilPwa> perfilPwaListNew = nivelEducativo.getPerfilPwaList();
            List<String> illegalOrphanMessages = null;
            for (PerfilPwa perfilPwaListOldPerfilPwa : perfilPwaListOld) {
                if (!perfilPwaListNew.contains(perfilPwaListOldPerfilPwa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilPwa " + perfilPwaListOldPerfilPwa + " since its nivelEducativoTipoNe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PerfilPwa> attachedPerfilPwaListNew = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListNewPerfilPwaToAttach : perfilPwaListNew) {
                perfilPwaListNewPerfilPwaToAttach = em.getReference(perfilPwaListNewPerfilPwaToAttach.getClass(), perfilPwaListNewPerfilPwaToAttach.getCedula());
                attachedPerfilPwaListNew.add(perfilPwaListNewPerfilPwaToAttach);
            }
            perfilPwaListNew = attachedPerfilPwaListNew;
            nivelEducativo.setPerfilPwaList(perfilPwaListNew);
            nivelEducativo = em.merge(nivelEducativo);
            for (PerfilPwa perfilPwaListNewPerfilPwa : perfilPwaListNew) {
                if (!perfilPwaListOld.contains(perfilPwaListNewPerfilPwa)) {
                    NivelEducativo oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa = perfilPwaListNewPerfilPwa.getNivelEducativoTipoNe();
                    perfilPwaListNewPerfilPwa.setNivelEducativoTipoNe(nivelEducativo);
                    perfilPwaListNewPerfilPwa = em.merge(perfilPwaListNewPerfilPwa);
                    if (oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa != null && !oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa.equals(nivelEducativo)) {
                        oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa.getPerfilPwaList().remove(perfilPwaListNewPerfilPwa);
                        oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa = em.merge(oldNivelEducativoTipoNeOfPerfilPwaListNewPerfilPwa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = nivelEducativo.getTipoNe();
                if (findNivelEducativo(id) == null) {
                    throw new NonexistentEntityException("The nivelEducativo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            NivelEducativo nivelEducativo;
            try {
                nivelEducativo = em.getReference(NivelEducativo.class, id);
                nivelEducativo.getTipoNe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nivelEducativo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PerfilPwa> perfilPwaListOrphanCheck = nivelEducativo.getPerfilPwaList();
            for (PerfilPwa perfilPwaListOrphanCheckPerfilPwa : perfilPwaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NivelEducativo (" + nivelEducativo + ") cannot be destroyed since the PerfilPwa " + perfilPwaListOrphanCheckPerfilPwa + " in its perfilPwaList field has a non-nullable nivelEducativoTipoNe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nivelEducativo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<NivelEducativo> findNivelEducativoEntities() {
        return findNivelEducativoEntities(true, -1, -1);
    }

    public List<NivelEducativo> findNivelEducativoEntities(int maxResults, int firstResult) {
        return findNivelEducativoEntities(false, maxResults, firstResult);
    }

    private List<NivelEducativo> findNivelEducativoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NivelEducativo.class));
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

    public NivelEducativo findNivelEducativo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NivelEducativo.class, id);
        } finally {
            em.close();
        }
    }

    public int getNivelEducativoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NivelEducativo> rt = cq.from(NivelEducativo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
