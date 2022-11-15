/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Cuidador;
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
public class CuidadorJpaController implements Serializable {

    public CuidadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuidador cuidador) throws PreexistingEntityException, Exception {
        if (cuidador.getPerfilPwaList() == null) {
            cuidador.setPerfilPwaList(new ArrayList<PerfilPwa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PerfilPwa> attachedPerfilPwaList = new ArrayList<PerfilPwa>();
            for (PerfilPwa perfilPwaListPerfilPwaToAttach : cuidador.getPerfilPwaList()) {
                perfilPwaListPerfilPwaToAttach = em.getReference(perfilPwaListPerfilPwaToAttach.getClass(), perfilPwaListPerfilPwaToAttach.getCedula());
                attachedPerfilPwaList.add(perfilPwaListPerfilPwaToAttach);
            }
            cuidador.setPerfilPwaList(attachedPerfilPwaList);
            em.persist(cuidador);
            for (PerfilPwa perfilPwaListPerfilPwa : cuidador.getPerfilPwaList()) {
                Cuidador oldCuidadorNombreUsuarioOfPerfilPwaListPerfilPwa = perfilPwaListPerfilPwa.getCuidadorNombreUsuario();
                perfilPwaListPerfilPwa.setCuidadorNombreUsuario(cuidador);
                perfilPwaListPerfilPwa = em.merge(perfilPwaListPerfilPwa);
                if (oldCuidadorNombreUsuarioOfPerfilPwaListPerfilPwa != null) {
                    oldCuidadorNombreUsuarioOfPerfilPwaListPerfilPwa.getPerfilPwaList().remove(perfilPwaListPerfilPwa);
                    oldCuidadorNombreUsuarioOfPerfilPwaListPerfilPwa = em.merge(oldCuidadorNombreUsuarioOfPerfilPwaListPerfilPwa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuidador(cuidador.getNombreUsuario()) != null) {
                throw new PreexistingEntityException("Cuidador " + cuidador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuidador cuidador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuidador persistentCuidador = em.find(Cuidador.class, cuidador.getNombreUsuario());
            List<PerfilPwa> perfilPwaListOld = persistentCuidador.getPerfilPwaList();
            List<PerfilPwa> perfilPwaListNew = cuidador.getPerfilPwaList();
            List<String> illegalOrphanMessages = null;
            for (PerfilPwa perfilPwaListOldPerfilPwa : perfilPwaListOld) {
                if (!perfilPwaListNew.contains(perfilPwaListOldPerfilPwa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilPwa " + perfilPwaListOldPerfilPwa + " since its cuidadorNombreUsuario field is not nullable.");
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
            cuidador.setPerfilPwaList(perfilPwaListNew);
            cuidador = em.merge(cuidador);
            for (PerfilPwa perfilPwaListNewPerfilPwa : perfilPwaListNew) {
                if (!perfilPwaListOld.contains(perfilPwaListNewPerfilPwa)) {
                    Cuidador oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa = perfilPwaListNewPerfilPwa.getCuidadorNombreUsuario();
                    perfilPwaListNewPerfilPwa.setCuidadorNombreUsuario(cuidador);
                    perfilPwaListNewPerfilPwa = em.merge(perfilPwaListNewPerfilPwa);
                    if (oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa != null && !oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa.equals(cuidador)) {
                        oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa.getPerfilPwaList().remove(perfilPwaListNewPerfilPwa);
                        oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa = em.merge(oldCuidadorNombreUsuarioOfPerfilPwaListNewPerfilPwa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuidador.getNombreUsuario();
                if (findCuidador(id) == null) {
                    throw new NonexistentEntityException("The cuidador with id " + id + " no longer exists.");
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
            Cuidador cuidador;
            try {
                cuidador = em.getReference(Cuidador.class, id);
                cuidador.getNombreUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuidador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PerfilPwa> perfilPwaListOrphanCheck = cuidador.getPerfilPwaList();
            for (PerfilPwa perfilPwaListOrphanCheckPerfilPwa : perfilPwaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuidador (" + cuidador + ") cannot be destroyed since the PerfilPwa " + perfilPwaListOrphanCheckPerfilPwa + " in its perfilPwaList field has a non-nullable cuidadorNombreUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cuidador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuidador> findCuidadorEntities() {
        return findCuidadorEntities(true, -1, -1);
    }

    public List<Cuidador> findCuidadorEntities(int maxResults, int firstResult) {
        return findCuidadorEntities(false, maxResults, firstResult);
    }

    private List<Cuidador> findCuidadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuidador.class));
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

    public Cuidador findCuidador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuidador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuidadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuidador> rt = cq.from(Cuidador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
