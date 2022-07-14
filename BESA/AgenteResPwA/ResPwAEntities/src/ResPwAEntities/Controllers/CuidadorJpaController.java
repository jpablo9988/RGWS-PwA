/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.Exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.Exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.Exceptions.PreexistingEntityException;
import ResPwAEntities.Cuidador;
import ResPwAEntities.Perfilpwa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author 57305
 */
public class CuidadorJpaController {
   public CuidadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuidador cuidador) throws PreexistingEntityException, Exception {
        if (cuidador.getPerfilpwaList() == null) {
           //cuidador.setPerfilpwa();
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Perfilpwa> attachedPerfilpwaList = new ArrayList<Perfilpwa>();
            
            
            em.persist(cuidador);
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuidador(cuidador.getNombreusuario()) != null) {
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
            Cuidador persistentCuidador = em.find(Cuidador.class, cuidador.getNombreusuario());
            //List<Perfilpwa> perfilpwaListOld = persistentCuidador.getPerfilpwaList();
            //List<Perfilpwa> perfilpwaListNew = cuidador.getPerfilpwaList();
            List<String> illegalOrphanMessages = null;
            Iterable<Perfilpwa> perfilpwaListOld = null;
            for (Perfilpwa perfilpwaListOldPerfilpwa : perfilpwaListOld) {
                if (!perfilpwaListNew.contains(perfilpwaListOldPerfilpwa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Perfilpwa " + perfilpwaListOldPerfilpwa + " since its cuidadorNombreusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            //List<Perfilpwa> attachedPerfilpwaListNew = new ArrayList<Perfilpwa>();
            /*for (attachedPerfilpwaListNew : Perfilpwa perfilpwaListNew) {
                perfilpwaListNewPerfilpwaToAttach = em.getReference(perfilpwaListNewPerfilpwaToAttach.getClass(), perfilpwaListNewPerfilpwaToAttach.getCedula());
                attachedPerfilpwaListNew.add(perfilpwaListNewPerfilpwaToAttach);
            }*/
           
            
            cuidador = em.merge(cuidador);
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuidador.getNombreusuario();
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
                cuidador.getNombreusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuidador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            
           
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
        
    return 0;
}
}
