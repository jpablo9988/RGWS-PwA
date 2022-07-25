/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Cuento;
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PreferenciaXCuento;
import ResPwAEntities.PreferenciaXCuentoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PreferenciaXCuentoJpaController implements Serializable {

    public PreferenciaXCuentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreferenciaXCuento preferenciaXCuento) throws PreexistingEntityException, Exception {
        if (preferenciaXCuento.getPreferenciaXCuentoPK() == null) {
            preferenciaXCuento.setPreferenciaXCuentoPK(new PreferenciaXCuentoPK());
        }
        preferenciaXCuento.getPreferenciaXCuentoPK().setCuentoNombre(preferenciaXCuento.getCuento().getNombre());
        preferenciaXCuento.getPreferenciaXCuentoPK().setPreferenciaPwaCedula(preferenciaXCuento.getPerfilPreferencia().getPerfilPwaCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuento cuento = preferenciaXCuento.getCuento();
            if (cuento != null) {
                cuento = em.getReference(cuento.getClass(), cuento.getNombre());
                preferenciaXCuento.setCuento(cuento);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXCuento.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                preferenciaXCuento.setPerfilPreferencia(perfilPreferencia);
            }
            em.persist(preferenciaXCuento);
            if (cuento != null) {
                cuento.getPreferenciaXCuentoList().add(preferenciaXCuento);
                cuento = em.merge(cuento);
            }
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXCuentoList().add(preferenciaXCuento);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferenciaXCuento(preferenciaXCuento.getPreferenciaXCuentoPK()) != null) {
                throw new PreexistingEntityException("PreferenciaXCuento " + preferenciaXCuento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreferenciaXCuento preferenciaXCuento) throws NonexistentEntityException, Exception {
        preferenciaXCuento.getPreferenciaXCuentoPK().setCuentoNombre(preferenciaXCuento.getCuento().getNombre());
        preferenciaXCuento.getPreferenciaXCuentoPK().setPreferenciaPwaCedula(preferenciaXCuento.getPerfilPreferencia().getPerfilPwaCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXCuento persistentPreferenciaXCuento = em.find(PreferenciaXCuento.class, preferenciaXCuento.getPreferenciaXCuentoPK());
            Cuento cuentoOld = persistentPreferenciaXCuento.getCuento();
            Cuento cuentoNew = preferenciaXCuento.getCuento();
            PerfilPreferencia perfilPreferenciaOld = persistentPreferenciaXCuento.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = preferenciaXCuento.getPerfilPreferencia();
            if (cuentoNew != null) {
                cuentoNew = em.getReference(cuentoNew.getClass(), cuentoNew.getNombre());
                preferenciaXCuento.setCuento(cuentoNew);
            }
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                preferenciaXCuento.setPerfilPreferencia(perfilPreferenciaNew);
            }
            preferenciaXCuento = em.merge(preferenciaXCuento);
            if (cuentoOld != null && !cuentoOld.equals(cuentoNew)) {
                cuentoOld.getPreferenciaXCuentoList().remove(preferenciaXCuento);
                cuentoOld = em.merge(cuentoOld);
            }
            if (cuentoNew != null && !cuentoNew.equals(cuentoOld)) {
                cuentoNew.getPreferenciaXCuentoList().add(preferenciaXCuento);
                cuentoNew = em.merge(cuentoNew);
            }
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                perfilPreferenciaOld.getPreferenciaXCuentoList().remove(preferenciaXCuento);
                perfilPreferenciaOld = em.merge(perfilPreferenciaOld);
            }
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                perfilPreferenciaNew.getPreferenciaXCuentoList().add(preferenciaXCuento);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreferenciaXCuentoPK id = preferenciaXCuento.getPreferenciaXCuentoPK();
                if (findPreferenciaXCuento(id) == null) {
                    throw new NonexistentEntityException("The preferenciaXCuento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreferenciaXCuentoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXCuento preferenciaXCuento;
            try {
                preferenciaXCuento = em.getReference(PreferenciaXCuento.class, id);
                preferenciaXCuento.getPreferenciaXCuentoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferenciaXCuento with id " + id + " no longer exists.", enfe);
            }
            Cuento cuento = preferenciaXCuento.getCuento();
            if (cuento != null) {
                cuento.getPreferenciaXCuentoList().remove(preferenciaXCuento);
                cuento = em.merge(cuento);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXCuento.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXCuentoList().remove(preferenciaXCuento);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            em.remove(preferenciaXCuento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreferenciaXCuento> findPreferenciaXCuentoEntities() {
        return findPreferenciaXCuentoEntities(true, -1, -1);
    }

    public List<PreferenciaXCuento> findPreferenciaXCuentoEntities(int maxResults, int firstResult) {
        return findPreferenciaXCuentoEntities(false, maxResults, firstResult);
    }

    private List<PreferenciaXCuento> findPreferenciaXCuentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreferenciaXCuento.class));
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

    public PreferenciaXCuento findPreferenciaXCuento(PreferenciaXCuentoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreferenciaXCuento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciaXCuentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreferenciaXCuento> rt = cq.from(PreferenciaXCuento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
