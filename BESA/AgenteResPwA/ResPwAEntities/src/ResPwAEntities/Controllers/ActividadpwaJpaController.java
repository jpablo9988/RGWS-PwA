/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.RegistroActividad;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.ActXPreferencia;
import ResPwAEntities.ActividadPwa;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class ActividadPwaJpaController implements Serializable {

    public ActividadPwaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActividadPwa actividadPwa) throws PreexistingEntityException, Exception {
        if (actividadPwa.getRegistroActividadList() == null) {
            actividadPwa.setRegistroActividadList(new ArrayList<RegistroActividad>());
        }
        if (actividadPwa.getActXPreferenciaList() == null) {
            actividadPwa.setActXPreferenciaList(new ArrayList<ActXPreferencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RegistroActividad> attachedRegistroActividadList = new ArrayList<RegistroActividad>();
            for (RegistroActividad registroActividadListRegistroActividadToAttach : actividadPwa.getRegistroActividadList()) {
                registroActividadListRegistroActividadToAttach = em.getReference(registroActividadListRegistroActividadToAttach.getClass(), registroActividadListRegistroActividadToAttach.getRegistroActividadPK());
                attachedRegistroActividadList.add(registroActividadListRegistroActividadToAttach);
            }
            actividadPwa.setRegistroActividadList(attachedRegistroActividadList);
            List<ActXPreferencia> attachedActXPreferenciaList = new ArrayList<ActXPreferencia>();
            for (ActXPreferencia actXPreferenciaListActXPreferenciaToAttach : actividadPwa.getActXPreferenciaList()) {
                actXPreferenciaListActXPreferenciaToAttach = em.getReference(actXPreferenciaListActXPreferenciaToAttach.getClass(), actXPreferenciaListActXPreferenciaToAttach.getActXPreferenciaPK());
                attachedActXPreferenciaList.add(actXPreferenciaListActXPreferenciaToAttach);
            }
            actividadPwa.setActXPreferenciaList(attachedActXPreferenciaList);
            em.persist(actividadPwa);
            for (RegistroActividad registroActividadListRegistroActividad : actividadPwa.getRegistroActividadList()) {
                ActividadPwa oldActividadPwaOfRegistroActividadListRegistroActividad = registroActividadListRegistroActividad.getActividadPwa();
                registroActividadListRegistroActividad.setActividadPwa(actividadPwa);
                registroActividadListRegistroActividad = em.merge(registroActividadListRegistroActividad);
                if (oldActividadPwaOfRegistroActividadListRegistroActividad != null) {
                    oldActividadPwaOfRegistroActividadListRegistroActividad.getRegistroActividadList().remove(registroActividadListRegistroActividad);
                    oldActividadPwaOfRegistroActividadListRegistroActividad = em.merge(oldActividadPwaOfRegistroActividadListRegistroActividad);
                }
            }
            for (ActXPreferencia actXPreferenciaListActXPreferencia : actividadPwa.getActXPreferenciaList()) {
                ActividadPwa oldActividadPwaOfActXPreferenciaListActXPreferencia = actXPreferenciaListActXPreferencia.getActividadPwa();
                actXPreferenciaListActXPreferencia.setActividadPwa(actividadPwa);
                actXPreferenciaListActXPreferencia = em.merge(actXPreferenciaListActXPreferencia);
                if (oldActividadPwaOfActXPreferenciaListActXPreferencia != null) {
                    oldActividadPwaOfActXPreferenciaListActXPreferencia.getActXPreferenciaList().remove(actXPreferenciaListActXPreferencia);
                    oldActividadPwaOfActXPreferenciaListActXPreferencia = em.merge(oldActividadPwaOfActXPreferenciaListActXPreferencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActividadPwa(actividadPwa.getId()) != null) {
                throw new PreexistingEntityException("ActividadPwa " + actividadPwa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActividadPwa actividadPwa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPwa persistentActividadPwa = em.find(ActividadPwa.class, actividadPwa.getId());
            List<RegistroActividad> registroActividadListOld = persistentActividadPwa.getRegistroActividadList();
            List<RegistroActividad> registroActividadListNew = actividadPwa.getRegistroActividadList();
            List<ActXPreferencia> actXPreferenciaListOld = persistentActividadPwa.getActXPreferenciaList();
            List<ActXPreferencia> actXPreferenciaListNew = actividadPwa.getActXPreferenciaList();
            List<String> illegalOrphanMessages = null;
            for (RegistroActividad registroActividadListOldRegistroActividad : registroActividadListOld) {
                if (!registroActividadListNew.contains(registroActividadListOldRegistroActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroActividad " + registroActividadListOldRegistroActividad + " since its actividadPwa field is not nullable.");
                }
            }
            for (ActXPreferencia actXPreferenciaListOldActXPreferencia : actXPreferenciaListOld) {
                if (!actXPreferenciaListNew.contains(actXPreferenciaListOldActXPreferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActXPreferencia " + actXPreferenciaListOldActXPreferencia + " since its actividadPwa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RegistroActividad> attachedRegistroActividadListNew = new ArrayList<RegistroActividad>();
            for (RegistroActividad registroActividadListNewRegistroActividadToAttach : registroActividadListNew) {
                registroActividadListNewRegistroActividadToAttach = em.getReference(registroActividadListNewRegistroActividadToAttach.getClass(), registroActividadListNewRegistroActividadToAttach.getRegistroActividadPK());
                attachedRegistroActividadListNew.add(registroActividadListNewRegistroActividadToAttach);
            }
            registroActividadListNew = attachedRegistroActividadListNew;
            actividadPwa.setRegistroActividadList(registroActividadListNew);
            List<ActXPreferencia> attachedActXPreferenciaListNew = new ArrayList<ActXPreferencia>();
            for (ActXPreferencia actXPreferenciaListNewActXPreferenciaToAttach : actXPreferenciaListNew) {
                actXPreferenciaListNewActXPreferenciaToAttach = em.getReference(actXPreferenciaListNewActXPreferenciaToAttach.getClass(), actXPreferenciaListNewActXPreferenciaToAttach.getActXPreferenciaPK());
                attachedActXPreferenciaListNew.add(actXPreferenciaListNewActXPreferenciaToAttach);
            }
            actXPreferenciaListNew = attachedActXPreferenciaListNew;
            actividadPwa.setActXPreferenciaList(actXPreferenciaListNew);
            actividadPwa = em.merge(actividadPwa);
            for (RegistroActividad registroActividadListNewRegistroActividad : registroActividadListNew) {
                if (!registroActividadListOld.contains(registroActividadListNewRegistroActividad)) {
                    ActividadPwa oldActividadPwaOfRegistroActividadListNewRegistroActividad = registroActividadListNewRegistroActividad.getActividadPwa();
                    registroActividadListNewRegistroActividad.setActividadPwa(actividadPwa);
                    registroActividadListNewRegistroActividad = em.merge(registroActividadListNewRegistroActividad);
                    if (oldActividadPwaOfRegistroActividadListNewRegistroActividad != null && !oldActividadPwaOfRegistroActividadListNewRegistroActividad.equals(actividadPwa)) {
                        oldActividadPwaOfRegistroActividadListNewRegistroActividad.getRegistroActividadList().remove(registroActividadListNewRegistroActividad);
                        oldActividadPwaOfRegistroActividadListNewRegistroActividad = em.merge(oldActividadPwaOfRegistroActividadListNewRegistroActividad);
                    }
                }
            }
            for (ActXPreferencia actXPreferenciaListNewActXPreferencia : actXPreferenciaListNew) {
                if (!actXPreferenciaListOld.contains(actXPreferenciaListNewActXPreferencia)) {
                    ActividadPwa oldActividadPwaOfActXPreferenciaListNewActXPreferencia = actXPreferenciaListNewActXPreferencia.getActividadPwa();
                    actXPreferenciaListNewActXPreferencia.setActividadPwa(actividadPwa);
                    actXPreferenciaListNewActXPreferencia = em.merge(actXPreferenciaListNewActXPreferencia);
                    if (oldActividadPwaOfActXPreferenciaListNewActXPreferencia != null && !oldActividadPwaOfActXPreferenciaListNewActXPreferencia.equals(actividadPwa)) {
                        oldActividadPwaOfActXPreferenciaListNewActXPreferencia.getActXPreferenciaList().remove(actXPreferenciaListNewActXPreferencia);
                        oldActividadPwaOfActXPreferenciaListNewActXPreferencia = em.merge(oldActividadPwaOfActXPreferenciaListNewActXPreferencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividadPwa.getId();
                if (findActividadPwa(id) == null) {
                    throw new NonexistentEntityException("The actividadPwa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActividadPwa actividadPwa;
            try {
                actividadPwa = em.getReference(ActividadPwa.class, id);
                actividadPwa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividadPwa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RegistroActividad> registroActividadListOrphanCheck = actividadPwa.getRegistroActividadList();
            for (RegistroActividad registroActividadListOrphanCheckRegistroActividad : registroActividadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ActividadPwa (" + actividadPwa + ") cannot be destroyed since the RegistroActividad " + registroActividadListOrphanCheckRegistroActividad + " in its registroActividadList field has a non-nullable actividadPwa field.");
            }
            List<ActXPreferencia> actXPreferenciaListOrphanCheck = actividadPwa.getActXPreferenciaList();
            for (ActXPreferencia actXPreferenciaListOrphanCheckActXPreferencia : actXPreferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ActividadPwa (" + actividadPwa + ") cannot be destroyed since the ActXPreferencia " + actXPreferenciaListOrphanCheckActXPreferencia + " in its actXPreferenciaList field has a non-nullable actividadPwa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(actividadPwa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActividadPwa> findActividadPwaEntities() {
        return findActividadPwaEntities(true, -1, -1);
    }

    public List<ActividadPwa> findActividadPwaEntities(int maxResults, int firstResult) {
        return findActividadPwaEntities(false, maxResults, firstResult);
    }

    private List<ActividadPwa> findActividadPwaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActividadPwa.class));
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

    public ActividadPwa findActividadPwa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActividadPwa.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadPwaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActividadPwa> rt = cq.from(ActividadPwa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
