/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.EstadoCivil;
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
 * @author USER
 */
public class EstadoCivilJpaController implements Serializable {

    public EstadoCivilJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EstadoCivil estadoCivil) throws PreexistingEntityException, Exception {
        if (estadoCivil.getPerfilPwaList() == null) {
            estadoCivil.setPerfilPwaList(new ArrayList<PerfilPwa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilPwa> attachedPerfilPwaList = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListPerfilPwaToAttach : estadoCivil.getPerfilPwaList()) {
                perfilPwaListPerfilPwaToAttach = em.getReference(perfilPwaListPerfilPwaToAttach.getClass(), perfilPwaListPerfilPwaToAttach.getCedula());
                attachedPerfilPwaList.add(perfilPwaListPerfilPwaToAttach);
            }
            estadoCivil.setPerfilPwaList(attachedPerfilPwaList);
            em.persist(estadoCivil);
            for (PerfilPwa perfilPwaListPerfilPwa : estadoCivil.getPerfilPwaList()) {
                EstadoCivil oldEstadoCivilTipoEcOfPerfilPwaListPerfilPwa = perfilPwaListPerfilPwa.getEstadoCivilTipoEc();
                perfilPwaListPerfilPwa.setEstadoCivilTipoEc(estadoCivil);
                perfilPwaListPerfilPwa = em.merge(perfilPwaListPerfilPwa);
                if (oldEstadoCivilTipoEcOfPerfilPwaListPerfilPwa != null) {
                    oldEstadoCivilTipoEcOfPerfilPwaListPerfilPwa.getPerfilPwaList().remove(perfilPwaListPerfilPwa);
                    oldEstadoCivilTipoEcOfPerfilPwaListPerfilPwa = em.merge(oldEstadoCivilTipoEcOfPerfilPwaListPerfilPwa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstadoCivil(estadoCivil.getTipoEc()) != null) {
                throw new PreexistingEntityException("EstadoCivil " + estadoCivil + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EstadoCivil estadoCivil) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EstadoCivil persistentEstadoCivil = em.find(EstadoCivil.class, estadoCivil.getTipoEc());
            List<PerfilPwa> perfilPwaListOld = persistentEstadoCivil.getPerfilPwaList();
            List<PerfilPwa> perfilPwaListNew = estadoCivil.getPerfilPwaList();
            List<String> illegalOrphanMessages = null;
            for (PerfilPwa perfilPwaListOldPerfilPwa : perfilPwaListOld) {
                if (!perfilPwaListNew.contains(perfilPwaListOldPerfilPwa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilPwa " + perfilPwaListOldPerfilPwa + " since its estadoCivilTipoEc field is not nullable.");
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
            estadoCivil.setPerfilPwaList(perfilPwaListNew);
            estadoCivil = em.merge(estadoCivil);
            for (PerfilPwa perfilPwaListNewPerfilPwa : perfilPwaListNew) {
                if (!perfilPwaListOld.contains(perfilPwaListNewPerfilPwa)) {
                    EstadoCivil oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa = perfilPwaListNewPerfilPwa.getEstadoCivilTipoEc();
                    perfilPwaListNewPerfilPwa.setEstadoCivilTipoEc(estadoCivil);
                    perfilPwaListNewPerfilPwa = em.merge(perfilPwaListNewPerfilPwa);
                    if (oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa != null && !oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa.equals(estadoCivil)) {
                        oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa.getPerfilPwaList().remove(perfilPwaListNewPerfilPwa);
                        oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa = em.merge(oldEstadoCivilTipoEcOfPerfilPwaListNewPerfilPwa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estadoCivil.getTipoEc();
                if (findEstadoCivil(id) == null) {
                    throw new NonexistentEntityException("The estadoCivil with id " + id + " no longer exists.");
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
            EstadoCivil estadoCivil;
            try {
                estadoCivil = em.getReference(EstadoCivil.class, id);
                estadoCivil.getTipoEc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadoCivil with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PerfilPwa> perfilPwaListOrphanCheck = estadoCivil.getPerfilPwaList();
            for (PerfilPwa perfilPwaListOrphanCheckPerfilPwa : perfilPwaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EstadoCivil (" + estadoCivil + ") cannot be destroyed since the PerfilPwa " + perfilPwaListOrphanCheckPerfilPwa + " in its perfilPwaList field has a non-nullable estadoCivilTipoEc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(estadoCivil);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EstadoCivil> findEstadoCivilEntities() {
        return findEstadoCivilEntities(true, -1, -1);
    }

    public List<EstadoCivil> findEstadoCivilEntities(int maxResults, int firstResult) {
        return findEstadoCivilEntities(false, maxResults, firstResult);
    }

    private List<EstadoCivil> findEstadoCivilEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EstadoCivil.class));
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

    public EstadoCivil findEstadoCivil(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EstadoCivil.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCivilCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EstadoCivil> rt = cq.from(EstadoCivil.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
