/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.EmotionalEntities.EmotionAxisConf;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.EmotionalEntities.EventInfluence;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class EmotionAxisConfJpaController implements Serializable {

    public EmotionAxisConfJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EmotionAxisConf emotionAxisConf) throws PreexistingEntityException, Exception {
        if (emotionAxisConf.getEventInfluenceList() == null) {
            emotionAxisConf.setEventInfluenceList(new ArrayList<EventInfluence>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<EventInfluence> attachedEventInfluenceList = new ArrayList<EventInfluence>();
            for (EventInfluence eventInfluenceListEventInfluenceToAttach : emotionAxisConf.getEventInfluenceList()) {
                eventInfluenceListEventInfluenceToAttach = em.getReference(eventInfluenceListEventInfluenceToAttach.getClass(), eventInfluenceListEventInfluenceToAttach.getId());
                attachedEventInfluenceList.add(eventInfluenceListEventInfluenceToAttach);
            }
            emotionAxisConf.setEventInfluenceList(attachedEventInfluenceList);
            em.persist(emotionAxisConf);
            for (EventInfluence eventInfluenceListEventInfluence : emotionAxisConf.getEventInfluenceList()) {
                EmotionAxisConf oldEmotionalAxisConfigIdOfEventInfluenceListEventInfluence = eventInfluenceListEventInfluence.getEmotionalAxisConfigId();
                eventInfluenceListEventInfluence.setEmotionalAxisConfigId(emotionAxisConf);
                eventInfluenceListEventInfluence = em.merge(eventInfluenceListEventInfluence);
                if (oldEmotionalAxisConfigIdOfEventInfluenceListEventInfluence != null) {
                    oldEmotionalAxisConfigIdOfEventInfluenceListEventInfluence.getEventInfluenceList().remove(eventInfluenceListEventInfluence);
                    oldEmotionalAxisConfigIdOfEventInfluenceListEventInfluence = em.merge(oldEmotionalAxisConfigIdOfEventInfluenceListEventInfluence);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmotionAxisConf(emotionAxisConf.getId()) != null) {
                throw new PreexistingEntityException("EmotionAxisConf " + emotionAxisConf + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EmotionAxisConf emotionAxisConf) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmotionAxisConf persistentEmotionAxisConf = em.find(EmotionAxisConf.class, emotionAxisConf.getId());
            List<EventInfluence> eventInfluenceListOld = persistentEmotionAxisConf.getEventInfluenceList();
            List<EventInfluence> eventInfluenceListNew = emotionAxisConf.getEventInfluenceList();
            List<String> illegalOrphanMessages = null;
            for (EventInfluence eventInfluenceListOldEventInfluence : eventInfluenceListOld) {
                if (!eventInfluenceListNew.contains(eventInfluenceListOldEventInfluence)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventInfluence " + eventInfluenceListOldEventInfluence + " since its emotionalAxisConfigId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<EventInfluence> attachedEventInfluenceListNew = new ArrayList<EventInfluence>();
            for (EventInfluence eventInfluenceListNewEventInfluenceToAttach : eventInfluenceListNew) {
                eventInfluenceListNewEventInfluenceToAttach = em.getReference(eventInfluenceListNewEventInfluenceToAttach.getClass(), eventInfluenceListNewEventInfluenceToAttach.getId());
                attachedEventInfluenceListNew.add(eventInfluenceListNewEventInfluenceToAttach);
            }
            eventInfluenceListNew = attachedEventInfluenceListNew;
            emotionAxisConf.setEventInfluenceList(eventInfluenceListNew);
            emotionAxisConf = em.merge(emotionAxisConf);
            for (EventInfluence eventInfluenceListNewEventInfluence : eventInfluenceListNew) {
                if (!eventInfluenceListOld.contains(eventInfluenceListNewEventInfluence)) {
                    EmotionAxisConf oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence = eventInfluenceListNewEventInfluence.getEmotionalAxisConfigId();
                    eventInfluenceListNewEventInfluence.setEmotionalAxisConfigId(emotionAxisConf);
                    eventInfluenceListNewEventInfluence = em.merge(eventInfluenceListNewEventInfluence);
                    if (oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence != null && !oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence.equals(emotionAxisConf)) {
                        oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence.getEventInfluenceList().remove(eventInfluenceListNewEventInfluence);
                        oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence = em.merge(oldEmotionalAxisConfigIdOfEventInfluenceListNewEventInfluence);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = emotionAxisConf.getId();
                if (findEmotionAxisConf(id) == null) {
                    throw new NonexistentEntityException("The emotionAxisConf with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EmotionAxisConf emotionAxisConf;
            try {
                emotionAxisConf = em.getReference(EmotionAxisConf.class, id);
                emotionAxisConf.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The emotionAxisConf with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EventInfluence> eventInfluenceListOrphanCheck = emotionAxisConf.getEventInfluenceList();
            for (EventInfluence eventInfluenceListOrphanCheckEventInfluence : eventInfluenceListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EmotionAxisConf (" + emotionAxisConf + ") cannot be destroyed since the EventInfluence " + eventInfluenceListOrphanCheckEventInfluence + " in its eventInfluenceList field has a non-nullable emotionalAxisConfigId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(emotionAxisConf);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EmotionAxisConf> findEmotionAxisConfEntities() {
        return findEmotionAxisConfEntities(true, -1, -1);
    }

    public List<EmotionAxisConf> findEmotionAxisConfEntities(int maxResults, int firstResult) {
        return findEmotionAxisConfEntities(false, maxResults, firstResult);
    }

    private List<EmotionAxisConf> findEmotionAxisConfEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EmotionAxisConf.class));
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

    public EmotionAxisConf findEmotionAxisConf(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EmotionAxisConf.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmotionAxisConfCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EmotionAxisConf> rt = cq.from(EmotionAxisConf.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
