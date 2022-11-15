/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Cuento;
import ResPwAEntities.Enriq;
import ResPwAEntities.Frase;
import ResPwAEntities.FrasePK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class FraseJpaController implements Serializable {

    public FraseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Frase frase) throws PreexistingEntityException, Exception {
        if (frase.getFrasePK() == null) {
            frase.setFrasePK(new FrasePK());
        }
        if (frase.getEnriqList() == null) {
            frase.setEnriqList(new ArrayList<Enriq>());
        }
        frase.getFrasePK().setCuentoNombre(frase.getCuento().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuento cuento = frase.getCuento();
            if (cuento != null) {
                cuento = em.getReference(cuento.getClass(), cuento.getNombre());
                frase.setCuento(cuento);
            }
            List<Enriq> attachedEnriqList = new ArrayList<Enriq>();
            for (Enriq enriqListEnriqToAttach : frase.getEnriqList()) {
                enriqListEnriqToAttach = em.getReference(enriqListEnriqToAttach.getClass(), enriqListEnriqToAttach.getParams());
                attachedEnriqList.add(enriqListEnriqToAttach);
            }
            frase.setEnriqList(attachedEnriqList);
            em.persist(frase);
            if (cuento != null) {
                cuento.getFraseList().add(frase);
                cuento = em.merge(cuento);
            }
            for (Enriq enriqListEnriq : frase.getEnriqList()) {
                Frase oldFraseOfEnriqListEnriq = enriqListEnriq.getFrase();
                enriqListEnriq.setFrase(frase);
                enriqListEnriq = em.merge(enriqListEnriq);
                if (oldFraseOfEnriqListEnriq != null) {
                    oldFraseOfEnriqListEnriq.getEnriqList().remove(enriqListEnriq);
                    oldFraseOfEnriqListEnriq = em.merge(oldFraseOfEnriqListEnriq);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFrase(frase.getFrasePK()) != null) {
                throw new PreexistingEntityException("Frase " + frase + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Frase frase) throws IllegalOrphanException, NonexistentEntityException, Exception {
        frase.getFrasePK().setCuentoNombre(frase.getCuento().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Frase persistentFrase = em.find(Frase.class, frase.getFrasePK());
            Cuento cuentoOld = persistentFrase.getCuento();
            Cuento cuentoNew = frase.getCuento();
            List<Enriq> enriqListOld = persistentFrase.getEnriqList();
            List<Enriq> enriqListNew = frase.getEnriqList();
            List<String> illegalOrphanMessages = null;
            for (Enriq enriqListOldEnriq : enriqListOld) {
                if (!enriqListNew.contains(enriqListOldEnriq)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Enriq " + enriqListOldEnriq + " since its frase field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentoNew != null) {
                cuentoNew = em.getReference(cuentoNew.getClass(), cuentoNew.getNombre());
                frase.setCuento(cuentoNew);
            }
            List<Enriq> attachedEnriqListNew = new ArrayList<Enriq>();
            for (Enriq enriqListNewEnriqToAttach : enriqListNew) {
                enriqListNewEnriqToAttach = em.getReference(enriqListNewEnriqToAttach.getClass(), enriqListNewEnriqToAttach.getParams());
                attachedEnriqListNew.add(enriqListNewEnriqToAttach);
            }
            enriqListNew = attachedEnriqListNew;
            frase.setEnriqList(enriqListNew);
            frase = em.merge(frase);
            if (cuentoOld != null && !cuentoOld.equals(cuentoNew)) {
                cuentoOld.getFraseList().remove(frase);
                cuentoOld = em.merge(cuentoOld);
            }
            if (cuentoNew != null && !cuentoNew.equals(cuentoOld)) {
                cuentoNew.getFraseList().add(frase);
                cuentoNew = em.merge(cuentoNew);
            }
            for (Enriq enriqListNewEnriq : enriqListNew) {
                if (!enriqListOld.contains(enriqListNewEnriq)) {
                    Frase oldFraseOfEnriqListNewEnriq = enriqListNewEnriq.getFrase();
                    enriqListNewEnriq.setFrase(frase);
                    enriqListNewEnriq = em.merge(enriqListNewEnriq);
                    if (oldFraseOfEnriqListNewEnriq != null && !oldFraseOfEnriqListNewEnriq.equals(frase)) {
                        oldFraseOfEnriqListNewEnriq.getEnriqList().remove(enriqListNewEnriq);
                        oldFraseOfEnriqListNewEnriq = em.merge(oldFraseOfEnriqListNewEnriq);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FrasePK id = frase.getFrasePK();
                if (findFrase(id) == null) {
                    throw new NonexistentEntityException("The frase with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FrasePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Frase frase;
            try {
                frase = em.getReference(Frase.class, id);
                frase.getFrasePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The frase with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Enriq> enriqListOrphanCheck = frase.getEnriqList();
            for (Enriq enriqListOrphanCheckEnriq : enriqListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Frase (" + frase + ") cannot be destroyed since the Enriq " + enriqListOrphanCheckEnriq + " in its enriqList field has a non-nullable frase field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuento cuento = frase.getCuento();
            if (cuento != null) {
                cuento.getFraseList().remove(frase);
                cuento = em.merge(cuento);
            }
            em.remove(frase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Frase> findFraseEntities() {
        return findFraseEntities(true, -1, -1);
    }

    public List<Frase> findFraseEntities(int maxResults, int firstResult) {
        return findFraseEntities(false, maxResults, firstResult);
    }

    private List<Frase> findFraseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Frase.class));
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

    public Frase findFrase(FrasePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Frase.class, id);
        } finally {
            em.close();
        }
    }

    public int getFraseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Frase> rt = cq.from(Frase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
