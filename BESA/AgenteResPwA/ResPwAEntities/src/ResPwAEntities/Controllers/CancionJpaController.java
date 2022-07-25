/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Cancion;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Genero;
import ResPwAEntities.Tag;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.Enriq;
import ResPwAEntities.PreferenciaXCancion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class CancionJpaController implements Serializable {

    public CancionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancion cancion) throws PreexistingEntityException, Exception {
        if (cancion.getTagList() == null) {
            cancion.setTagList(new ArrayList<Tag>());
        }
        if (cancion.getEnriqList() == null) {
            cancion.setEnriqList(new ArrayList<Enriq>());
        }
        if (cancion.getPreferenciaXCancionList() == null) {
            cancion.setPreferenciaXCancionList(new ArrayList<PreferenciaXCancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = cancion.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getGenero());
                cancion.setGenero(genero);
            }
            List<Tag> attachedTagList = new ArrayList<Tag>();
            for (Tag tagListTagToAttach : cancion.getTagList()) {
                tagListTagToAttach = em.getReference(tagListTagToAttach.getClass(), tagListTagToAttach.getId());
                attachedTagList.add(tagListTagToAttach);
            }
            cancion.setTagList(attachedTagList);
            List<Enriq> attachedEnriqList = new ArrayList<Enriq>();
            for (Enriq enriqListEnriqToAttach : cancion.getEnriqList()) {
                enriqListEnriqToAttach = em.getReference(enriqListEnriqToAttach.getClass(), enriqListEnriqToAttach.getParams());
                attachedEnriqList.add(enriqListEnriqToAttach);
            }
            cancion.setEnriqList(attachedEnriqList);
            List<PreferenciaXCancion> attachedPreferenciaXCancionList = new ArrayList<PreferenciaXCancion>();
            for (PreferenciaXCancion preferenciaXCancionListPreferenciaXCancionToAttach : cancion.getPreferenciaXCancionList()) {
                preferenciaXCancionListPreferenciaXCancionToAttach = em.getReference(preferenciaXCancionListPreferenciaXCancionToAttach.getClass(), preferenciaXCancionListPreferenciaXCancionToAttach.getPreferenciaXCancionPK());
                attachedPreferenciaXCancionList.add(preferenciaXCancionListPreferenciaXCancionToAttach);
            }
            cancion.setPreferenciaXCancionList(attachedPreferenciaXCancionList);
            em.persist(cancion);
            if (genero != null) {
                genero.getCancionList().add(cancion);
                genero = em.merge(genero);
            }
            for (Tag tagListTag : cancion.getTagList()) {
                tagListTag.getCancionList().add(cancion);
                tagListTag = em.merge(tagListTag);
            }
            for (Enriq enriqListEnriq : cancion.getEnriqList()) {
                Cancion oldCancionNombreOfEnriqListEnriq = enriqListEnriq.getCancionNombre();
                enriqListEnriq.setCancionNombre(cancion);
                enriqListEnriq = em.merge(enriqListEnriq);
                if (oldCancionNombreOfEnriqListEnriq != null) {
                    oldCancionNombreOfEnriqListEnriq.getEnriqList().remove(enriqListEnriq);
                    oldCancionNombreOfEnriqListEnriq = em.merge(oldCancionNombreOfEnriqListEnriq);
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListPreferenciaXCancion : cancion.getPreferenciaXCancionList()) {
                Cancion oldCancionOfPreferenciaXCancionListPreferenciaXCancion = preferenciaXCancionListPreferenciaXCancion.getCancion();
                preferenciaXCancionListPreferenciaXCancion.setCancion(cancion);
                preferenciaXCancionListPreferenciaXCancion = em.merge(preferenciaXCancionListPreferenciaXCancion);
                if (oldCancionOfPreferenciaXCancionListPreferenciaXCancion != null) {
                    oldCancionOfPreferenciaXCancionListPreferenciaXCancion.getPreferenciaXCancionList().remove(preferenciaXCancionListPreferenciaXCancion);
                    oldCancionOfPreferenciaXCancionListPreferenciaXCancion = em.merge(oldCancionOfPreferenciaXCancionListPreferenciaXCancion);
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
            Genero generoOld = persistentCancion.getGenero();
            Genero generoNew = cancion.getGenero();
            List<Tag> tagListOld = persistentCancion.getTagList();
            List<Tag> tagListNew = cancion.getTagList();
            List<Enriq> enriqListOld = persistentCancion.getEnriqList();
            List<Enriq> enriqListNew = cancion.getEnriqList();
            List<PreferenciaXCancion> preferenciaXCancionListOld = persistentCancion.getPreferenciaXCancionList();
            List<PreferenciaXCancion> preferenciaXCancionListNew = cancion.getPreferenciaXCancionList();
            List<String> illegalOrphanMessages = null;
            for (Enriq enriqListOldEnriq : enriqListOld) {
                if (!enriqListNew.contains(enriqListOldEnriq)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Enriq " + enriqListOldEnriq + " since its cancionNombre field is not nullable.");
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListOldPreferenciaXCancion : preferenciaXCancionListOld) {
                if (!preferenciaXCancionListNew.contains(preferenciaXCancionListOldPreferenciaXCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXCancion " + preferenciaXCancionListOldPreferenciaXCancion + " since its cancion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getGenero());
                cancion.setGenero(generoNew);
            }
            List<Tag> attachedTagListNew = new ArrayList<Tag>();
            for (Tag tagListNewTagToAttach : tagListNew) {
                tagListNewTagToAttach = em.getReference(tagListNewTagToAttach.getClass(), tagListNewTagToAttach.getId());
                attachedTagListNew.add(tagListNewTagToAttach);
            }
            tagListNew = attachedTagListNew;
            cancion.setTagList(tagListNew);
            List<Enriq> attachedEnriqListNew = new ArrayList<Enriq>();
            for (Enriq enriqListNewEnriqToAttach : enriqListNew) {
                enriqListNewEnriqToAttach = em.getReference(enriqListNewEnriqToAttach.getClass(), enriqListNewEnriqToAttach.getParams());
                attachedEnriqListNew.add(enriqListNewEnriqToAttach);
            }
            enriqListNew = attachedEnriqListNew;
            cancion.setEnriqList(enriqListNew);
            List<PreferenciaXCancion> attachedPreferenciaXCancionListNew = new ArrayList<PreferenciaXCancion>();
            for (PreferenciaXCancion preferenciaXCancionListNewPreferenciaXCancionToAttach : preferenciaXCancionListNew) {
                preferenciaXCancionListNewPreferenciaXCancionToAttach = em.getReference(preferenciaXCancionListNewPreferenciaXCancionToAttach.getClass(), preferenciaXCancionListNewPreferenciaXCancionToAttach.getPreferenciaXCancionPK());
                attachedPreferenciaXCancionListNew.add(preferenciaXCancionListNewPreferenciaXCancionToAttach);
            }
            preferenciaXCancionListNew = attachedPreferenciaXCancionListNew;
            cancion.setPreferenciaXCancionList(preferenciaXCancionListNew);
            cancion = em.merge(cancion);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getCancionList().remove(cancion);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getCancionList().add(cancion);
                generoNew = em.merge(generoNew);
            }
            for (Tag tagListOldTag : tagListOld) {
                if (!tagListNew.contains(tagListOldTag)) {
                    tagListOldTag.getCancionList().remove(cancion);
                    tagListOldTag = em.merge(tagListOldTag);
                }
            }
            for (Tag tagListNewTag : tagListNew) {
                if (!tagListOld.contains(tagListNewTag)) {
                    tagListNewTag.getCancionList().add(cancion);
                    tagListNewTag = em.merge(tagListNewTag);
                }
            }
            for (Enriq enriqListNewEnriq : enriqListNew) {
                if (!enriqListOld.contains(enriqListNewEnriq)) {
                    Cancion oldCancionNombreOfEnriqListNewEnriq = enriqListNewEnriq.getCancionNombre();
                    enriqListNewEnriq.setCancionNombre(cancion);
                    enriqListNewEnriq = em.merge(enriqListNewEnriq);
                    if (oldCancionNombreOfEnriqListNewEnriq != null && !oldCancionNombreOfEnriqListNewEnriq.equals(cancion)) {
                        oldCancionNombreOfEnriqListNewEnriq.getEnriqList().remove(enriqListNewEnriq);
                        oldCancionNombreOfEnriqListNewEnriq = em.merge(oldCancionNombreOfEnriqListNewEnriq);
                    }
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListNewPreferenciaXCancion : preferenciaXCancionListNew) {
                if (!preferenciaXCancionListOld.contains(preferenciaXCancionListNewPreferenciaXCancion)) {
                    Cancion oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion = preferenciaXCancionListNewPreferenciaXCancion.getCancion();
                    preferenciaXCancionListNewPreferenciaXCancion.setCancion(cancion);
                    preferenciaXCancionListNewPreferenciaXCancion = em.merge(preferenciaXCancionListNewPreferenciaXCancion);
                    if (oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion != null && !oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion.equals(cancion)) {
                        oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion.getPreferenciaXCancionList().remove(preferenciaXCancionListNewPreferenciaXCancion);
                        oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion = em.merge(oldCancionOfPreferenciaXCancionListNewPreferenciaXCancion);
                    }
                }
            }
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
            List<PreferenciaXCancion> preferenciaXCancionListOrphanCheck = cancion.getPreferenciaXCancionList();
            for (PreferenciaXCancion preferenciaXCancionListOrphanCheckPreferenciaXCancion : preferenciaXCancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the PreferenciaXCancion " + preferenciaXCancionListOrphanCheckPreferenciaXCancion + " in its preferenciaXCancionList field has a non-nullable cancion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genero genero = cancion.getGenero();
            if (genero != null) {
                genero.getCancionList().remove(cancion);
                genero = em.merge(genero);
            }
            List<Tag> tagList = cancion.getTagList();
            for (Tag tagListTag : tagList) {
                tagListTag.getCancionList().remove(cancion);
                tagListTag = em.merge(tagListTag);
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
