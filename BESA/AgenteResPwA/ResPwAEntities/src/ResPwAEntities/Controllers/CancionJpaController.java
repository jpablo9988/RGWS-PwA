/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Cancion;
import ResPwAEntities.Controllers.Exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.Exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.Exceptions.PreexistingEntityException;
import ResPwAEntities.Enriq;
import ResPwAEntities.Genero;
import ResPwAEntities.Preferenciaxcancion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author 57305
 */
public class CancionJpaController {
     public CancionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancion cancion) throws PreexistingEntityException, Exception {
        
       
        if (cancion.getPreferenciaxcancionList() == null) {
            cancion.setPreferenciaxcancionList(new ArrayList<Preferenciaxcancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero generoGenero = cancion.getGeneroGenero();
            if (generoGenero != null) {
                generoGenero = em.getReference(generoGenero.getClass(), generoGenero.getGenero());
                cancion.setGeneroGenero(generoGenero);
            }
           
          
            List<Enriq> attachedEnriqList = new ArrayList<Enriq>();
            
            
            List<Preferenciaxcancion> attachedPreferenciaxcancionList = new ArrayList<Preferenciaxcancion>();
            for (Preferenciaxcancion preferenciaxcancionListPreferenciaxcancionToAttach : cancion.getPreferenciaxcancionList()) {
                preferenciaxcancionListPreferenciaxcancionToAttach = em.getReference(preferenciaxcancionListPreferenciaxcancionToAttach.getClass(), preferenciaxcancionListPreferenciaxcancionToAttach.getPreferenciaxcancionPK());
                attachedPreferenciaxcancionList.add(preferenciaxcancionListPreferenciaxcancionToAttach);
            }
            cancion.setPreferenciaxcancionList(attachedPreferenciaxcancionList);
            em.persist(cancion);
            if (generoGenero != null) {
                generoGenero.getCancionList().add(cancion);
                generoGenero = em.merge(generoGenero);
            }
            
          
            for (Preferenciaxcancion preferenciaxcancionListPreferenciaxcancion : cancion.getPreferenciaxcancionList()) {
                Cancion oldCancionOfPreferenciaxcancionListPreferenciaxcancion = preferenciaxcancionListPreferenciaxcancion.getCancion();
                preferenciaxcancionListPreferenciaxcancion.setCancion(cancion);
                preferenciaxcancionListPreferenciaxcancion = em.merge(preferenciaxcancionListPreferenciaxcancion);
                if (oldCancionOfPreferenciaxcancionListPreferenciaxcancion != null) {
                    oldCancionOfPreferenciaxcancionListPreferenciaxcancion.getPreferenciaxcancionList().remove(preferenciaxcancionListPreferenciaxcancion);
                    oldCancionOfPreferenciaxcancionListPreferenciaxcancion = em.merge(oldCancionOfPreferenciaxcancionListPreferenciaxcancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancion(cancion.getNombre()) != null) {
                throw new PreexistingEntityException("Cancion " + cancion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancion cancion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion persistentCancion = em.find(Cancion.class, cancion.getNombre());
            Genero generoGeneroOld = persistentCancion.getGeneroGenero();
            Genero generoGeneroNew = cancion.getGeneroGenero();
            
            
            
            
            
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cancion.getNombre();
                if (findCancion(id) == null) {
                    throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.");
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
            Cancion cancion;
            try {
                cancion = em.getReference(Cancion.class, id);
                cancion.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Enriq> enriqListOrphanCheck = cancion.getEnriqList();
            for (Enriq enriqListOrphanCheckEnriq : enriqListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Enriq " + enriqListOrphanCheckEnriq + " in its enriqList field has a non-nullable cancionNombre field.");
            }
            List<Preferenciaxcancion> preferenciaxcancionListOrphanCheck = cancion.getPreferenciaxcancionList();
            for (Preferenciaxcancion preferenciaxcancionListOrphanCheckPreferenciaxcancion : preferenciaxcancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Preferenciaxcancion " + preferenciaxcancionListOrphanCheckPreferenciaxcancion + " in its preferenciaxcancionList field has a non-nullable cancion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genero generoGenero = cancion.getGeneroGenero();
            if (generoGenero != null) {
                generoGenero.getCancionList().remove(cancion);
                generoGenero = em.merge(generoGenero);
            }
          
            em.remove(cancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancion> findCancionEntities() {
        return findCancionEntities(true, -1, -1);
    }

    public List<Cancion> findCancionEntities(int maxResults, int firstResult) {
        return findCancionEntities(false, maxResults, firstResult);
    }

    private List<Cancion> findCancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancion.class));
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

    public Cancion findCancion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancion> rt = cq.from(Cancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
