/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Cuento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Genero;
import ResPwAEntities.PreferenciaXCuento;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.Frase;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class CuentoJpaController implements Serializable {

    public CuentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuento cuento) throws PreexistingEntityException, Exception {
        if (cuento.getPreferenciaXCuentoList() == null) {
            cuento.setPreferenciaXCuentoList(new ArrayList<PreferenciaXCuento>());
        }
        if (cuento.getFraseList() == null) {
            cuento.setFraseList(new ArrayList<Frase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = cuento.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getGenero());
                cuento.setGenero(genero);
            }
            List<PreferenciaXCuento> attachedPreferenciaXCuentoList = new ArrayList<PreferenciaXCuento>();
            for (PreferenciaXCuento preferenciaXCuentoListPreferenciaXCuentoToAttach : cuento.getPreferenciaXCuentoList()) {
                preferenciaXCuentoListPreferenciaXCuentoToAttach = em.getReference(preferenciaXCuentoListPreferenciaXCuentoToAttach.getClass(), preferenciaXCuentoListPreferenciaXCuentoToAttach.getPreferenciaXCuentoPK());
                attachedPreferenciaXCuentoList.add(preferenciaXCuentoListPreferenciaXCuentoToAttach);
            }
            cuento.setPreferenciaXCuentoList(attachedPreferenciaXCuentoList);
            List<Frase> attachedFraseList = new ArrayList<Frase>();
            for (Frase fraseListFraseToAttach : cuento.getFraseList()) {
                fraseListFraseToAttach = em.getReference(fraseListFraseToAttach.getClass(), fraseListFraseToAttach.getFrasePK());
                attachedFraseList.add(fraseListFraseToAttach);
            }
            cuento.setFraseList(attachedFraseList);
            em.persist(cuento);
            if (genero != null) {
                genero.getCuentoList().add(cuento);
                genero = em.merge(genero);
            }
            for (PreferenciaXCuento preferenciaXCuentoListPreferenciaXCuento : cuento.getPreferenciaXCuentoList()) {
                Cuento oldCuentoOfPreferenciaXCuentoListPreferenciaXCuento = preferenciaXCuentoListPreferenciaXCuento.getCuento();
                preferenciaXCuentoListPreferenciaXCuento.setCuento(cuento);
                preferenciaXCuentoListPreferenciaXCuento = em.merge(preferenciaXCuentoListPreferenciaXCuento);
                if (oldCuentoOfPreferenciaXCuentoListPreferenciaXCuento != null) {
                    oldCuentoOfPreferenciaXCuentoListPreferenciaXCuento.getPreferenciaXCuentoList().remove(preferenciaXCuentoListPreferenciaXCuento);
                    oldCuentoOfPreferenciaXCuentoListPreferenciaXCuento = em.merge(oldCuentoOfPreferenciaXCuentoListPreferenciaXCuento);
                }
            }
            for (Frase fraseListFrase : cuento.getFraseList()) {
                Cuento oldCuentoOfFraseListFrase = fraseListFrase.getCuento();
                fraseListFrase.setCuento(cuento);
                fraseListFrase = em.merge(fraseListFrase);
                if (oldCuentoOfFraseListFrase != null) {
                    oldCuentoOfFraseListFrase.getFraseList().remove(fraseListFrase);
                    oldCuentoOfFraseListFrase = em.merge(oldCuentoOfFraseListFrase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuento(cuento.getNombre()) != null) {
                throw new PreexistingEntityException("Cuento " + cuento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuento cuento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuento persistentCuento = em.find(Cuento.class, cuento.getNombre());
            Genero generoOld = persistentCuento.getGenero();
            Genero generoNew = cuento.getGenero();
            List<PreferenciaXCuento> preferenciaXCuentoListOld = persistentCuento.getPreferenciaXCuentoList();
            List<PreferenciaXCuento> preferenciaXCuentoListNew = cuento.getPreferenciaXCuentoList();
            List<Frase> fraseListOld = persistentCuento.getFraseList();
            List<Frase> fraseListNew = cuento.getFraseList();
            List<String> illegalOrphanMessages = null;
            for (PreferenciaXCuento preferenciaXCuentoListOldPreferenciaXCuento : preferenciaXCuentoListOld) {
                if (!preferenciaXCuentoListNew.contains(preferenciaXCuentoListOldPreferenciaXCuento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXCuento " + preferenciaXCuentoListOldPreferenciaXCuento + " since its cuento field is not nullable.");
                }
            }
            for (Frase fraseListOldFrase : fraseListOld) {
                if (!fraseListNew.contains(fraseListOldFrase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Frase " + fraseListOldFrase + " since its cuento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getGenero());
                cuento.setGenero(generoNew);
            }
            List<PreferenciaXCuento> attachedPreferenciaXCuentoListNew = new ArrayList<PreferenciaXCuento>();
            for (PreferenciaXCuento preferenciaXCuentoListNewPreferenciaXCuentoToAttach : preferenciaXCuentoListNew) {
                preferenciaXCuentoListNewPreferenciaXCuentoToAttach = em.getReference(preferenciaXCuentoListNewPreferenciaXCuentoToAttach.getClass(), preferenciaXCuentoListNewPreferenciaXCuentoToAttach.getPreferenciaXCuentoPK());
                attachedPreferenciaXCuentoListNew.add(preferenciaXCuentoListNewPreferenciaXCuentoToAttach);
            }
            preferenciaXCuentoListNew = attachedPreferenciaXCuentoListNew;
            cuento.setPreferenciaXCuentoList(preferenciaXCuentoListNew);
            List<Frase> attachedFraseListNew = new ArrayList<Frase>();
            for (Frase fraseListNewFraseToAttach : fraseListNew) {
                fraseListNewFraseToAttach = em.getReference(fraseListNewFraseToAttach.getClass(), fraseListNewFraseToAttach.getFrasePK());
                attachedFraseListNew.add(fraseListNewFraseToAttach);
            }
            fraseListNew = attachedFraseListNew;
            cuento.setFraseList(fraseListNew);
            cuento = em.merge(cuento);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getCuentoList().remove(cuento);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getCuentoList().add(cuento);
                generoNew = em.merge(generoNew);
            }
            for (PreferenciaXCuento preferenciaXCuentoListNewPreferenciaXCuento : preferenciaXCuentoListNew) {
                if (!preferenciaXCuentoListOld.contains(preferenciaXCuentoListNewPreferenciaXCuento)) {
                    Cuento oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento = preferenciaXCuentoListNewPreferenciaXCuento.getCuento();
                    preferenciaXCuentoListNewPreferenciaXCuento.setCuento(cuento);
                    preferenciaXCuentoListNewPreferenciaXCuento = em.merge(preferenciaXCuentoListNewPreferenciaXCuento);
                    if (oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento != null && !oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento.equals(cuento)) {
                        oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento.getPreferenciaXCuentoList().remove(preferenciaXCuentoListNewPreferenciaXCuento);
                        oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento = em.merge(oldCuentoOfPreferenciaXCuentoListNewPreferenciaXCuento);
                    }
                }
            }
            for (Frase fraseListNewFrase : fraseListNew) {
                if (!fraseListOld.contains(fraseListNewFrase)) {
                    Cuento oldCuentoOfFraseListNewFrase = fraseListNewFrase.getCuento();
                    fraseListNewFrase.setCuento(cuento);
                    fraseListNewFrase = em.merge(fraseListNewFrase);
                    if (oldCuentoOfFraseListNewFrase != null && !oldCuentoOfFraseListNewFrase.equals(cuento)) {
                        oldCuentoOfFraseListNewFrase.getFraseList().remove(fraseListNewFrase);
                        oldCuentoOfFraseListNewFrase = em.merge(oldCuentoOfFraseListNewFrase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuento.getNombre();
                if (findCuento(id) == null) {
                    throw new NonexistentEntityException("The cuento with id " + id + " no longer exists.");
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
            Cuento cuento;
            try {
                cuento = em.getReference(Cuento.class, id);
                cuento.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreferenciaXCuento> preferenciaXCuentoListOrphanCheck = cuento.getPreferenciaXCuentoList();
            for (PreferenciaXCuento preferenciaXCuentoListOrphanCheckPreferenciaXCuento : preferenciaXCuentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuento (" + cuento + ") cannot be destroyed since the PreferenciaXCuento " + preferenciaXCuentoListOrphanCheckPreferenciaXCuento + " in its preferenciaXCuentoList field has a non-nullable cuento field.");
            }
            List<Frase> fraseListOrphanCheck = cuento.getFraseList();
            for (Frase fraseListOrphanCheckFrase : fraseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuento (" + cuento + ") cannot be destroyed since the Frase " + fraseListOrphanCheckFrase + " in its fraseList field has a non-nullable cuento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Genero genero = cuento.getGenero();
            if (genero != null) {
                genero.getCuentoList().remove(cuento);
                genero = em.merge(genero);
            }
            em.remove(cuento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuento> findCuentoEntities() {
        return findCuentoEntities(true, -1, -1);
    }

    public List<Cuento> findCuentoEntities(int maxResults, int firstResult) {
        return findCuentoEntities(false, maxResults, firstResult);
    }

    private List<Cuento> findCuentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuento.class));
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

    public Cuento findCuento(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuento> rt = cq.from(Cuento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
