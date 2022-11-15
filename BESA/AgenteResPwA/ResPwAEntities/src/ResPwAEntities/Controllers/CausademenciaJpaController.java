/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.CausaDemencia;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.PerfilMedico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class CausaDemenciaJpaController implements Serializable {

    public CausaDemenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CausaDemencia causaDemencia) throws PreexistingEntityException, Exception {
        if (causaDemencia.getPerfilMedicoList() == null) {
            causaDemencia.setPerfilMedicoList(new ArrayList<PerfilMedico>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilMedico> attachedPerfilMedicoList = new ArrayList<PerfilMedico>();
            for (PerfilMedico perfilMedicoListPerfilMedicoToAttach : causaDemencia.getPerfilMedicoList()) {
                perfilMedicoListPerfilMedicoToAttach = em.getReference(perfilMedicoListPerfilMedicoToAttach.getClass(), perfilMedicoListPerfilMedicoToAttach.getPerfilPwaCedula());
                attachedPerfilMedicoList.add(perfilMedicoListPerfilMedicoToAttach);
            }
            causaDemencia.setPerfilMedicoList(attachedPerfilMedicoList);
            em.persist(causaDemencia);
            for (PerfilMedico perfilMedicoListPerfilMedico : causaDemencia.getPerfilMedicoList()) {
                CausaDemencia oldCausaDemenciaCondicionOfPerfilMedicoListPerfilMedico = perfilMedicoListPerfilMedico.getCausaDemenciaCondicion();
                perfilMedicoListPerfilMedico.setCausaDemenciaCondicion(causaDemencia);
                perfilMedicoListPerfilMedico = em.merge(perfilMedicoListPerfilMedico);
                if (oldCausaDemenciaCondicionOfPerfilMedicoListPerfilMedico != null) {
                    oldCausaDemenciaCondicionOfPerfilMedicoListPerfilMedico.getPerfilMedicoList().remove(perfilMedicoListPerfilMedico);
                    oldCausaDemenciaCondicionOfPerfilMedicoListPerfilMedico = em.merge(oldCausaDemenciaCondicionOfPerfilMedicoListPerfilMedico);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCausaDemencia(causaDemencia.getCondicion()) != null) {
                throw new PreexistingEntityException("CausaDemencia " + causaDemencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CausaDemencia causaDemencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CausaDemencia persistentCausaDemencia = em.find(CausaDemencia.class, causaDemencia.getCondicion());
            List<PerfilMedico> perfilMedicoListOld = persistentCausaDemencia.getPerfilMedicoList();
            List<PerfilMedico> perfilMedicoListNew = causaDemencia.getPerfilMedicoList();
            List<String> illegalOrphanMessages = null;
            for (PerfilMedico perfilMedicoListOldPerfilMedico : perfilMedicoListOld) {
                if (!perfilMedicoListNew.contains(perfilMedicoListOldPerfilMedico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilMedico " + perfilMedicoListOldPerfilMedico + " since its causaDemenciaCondicion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PerfilMedico> attachedPerfilMedicoListNew = new ArrayList<PerfilMedico>();
            for (PerfilMedico perfilMedicoListNewPerfilMedicoToAttach : perfilMedicoListNew) {
                perfilMedicoListNewPerfilMedicoToAttach = em.getReference(perfilMedicoListNewPerfilMedicoToAttach.getClass(), perfilMedicoListNewPerfilMedicoToAttach.getPerfilPwaCedula());
                attachedPerfilMedicoListNew.add(perfilMedicoListNewPerfilMedicoToAttach);
            }
            perfilMedicoListNew = attachedPerfilMedicoListNew;
            causaDemencia.setPerfilMedicoList(perfilMedicoListNew);
            causaDemencia = em.merge(causaDemencia);
            for (PerfilMedico perfilMedicoListNewPerfilMedico : perfilMedicoListNew) {
                if (!perfilMedicoListOld.contains(perfilMedicoListNewPerfilMedico)) {
                    CausaDemencia oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico = perfilMedicoListNewPerfilMedico.getCausaDemenciaCondicion();
                    perfilMedicoListNewPerfilMedico.setCausaDemenciaCondicion(causaDemencia);
                    perfilMedicoListNewPerfilMedico = em.merge(perfilMedicoListNewPerfilMedico);
                    if (oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico != null && !oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico.equals(causaDemencia)) {
                        oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico.getPerfilMedicoList().remove(perfilMedicoListNewPerfilMedico);
                        oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico = em.merge(oldCausaDemenciaCondicionOfPerfilMedicoListNewPerfilMedico);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = causaDemencia.getCondicion();
                if (findCausaDemencia(id) == null) {
                    throw new NonexistentEntityException("The causaDemencia with id " + id + " no longer exists.");
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
            CausaDemencia causaDemencia;
            try {
                causaDemencia = em.getReference(CausaDemencia.class, id);
                causaDemencia.getCondicion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The causaDemencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PerfilMedico> perfilMedicoListOrphanCheck = causaDemencia.getPerfilMedicoList();
            for (PerfilMedico perfilMedicoListOrphanCheckPerfilMedico : perfilMedicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CausaDemencia (" + causaDemencia + ") cannot be destroyed since the PerfilMedico " + perfilMedicoListOrphanCheckPerfilMedico + " in its perfilMedicoList field has a non-nullable causaDemenciaCondicion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(causaDemencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CausaDemencia> findCausaDemenciaEntities() {
        return findCausaDemenciaEntities(true, -1, -1);
    }

    public List<CausaDemencia> findCausaDemenciaEntities(int maxResults, int firstResult) {
        return findCausaDemenciaEntities(false, maxResults, firstResult);
    }

    private List<CausaDemencia> findCausaDemenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CausaDemencia.class));
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

    public CausaDemencia findCausaDemencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CausaDemencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCausaDemenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CausaDemencia> rt = cq.from(CausaDemencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
