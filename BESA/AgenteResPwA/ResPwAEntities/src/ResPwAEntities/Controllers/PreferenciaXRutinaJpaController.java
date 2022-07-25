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
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PreferenciaXRutina;
import ResPwAEntities.PreferenciaXRutinaPK;
import ResPwAEntities.Rutina;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PreferenciaXRutinaJpaController implements Serializable {

    public PreferenciaXRutinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreferenciaXRutina preferenciaXRutina) throws PreexistingEntityException, Exception {
        if (preferenciaXRutina.getPreferenciaXRutinaPK() == null) {
            preferenciaXRutina.setPreferenciaXRutinaPK(new PreferenciaXRutinaPK());
        }
        preferenciaXRutina.getPreferenciaXRutinaPK().setPreferenciaPwaCedula(preferenciaXRutina.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXRutina.getPreferenciaXRutinaPK().setRutinaId(preferenciaXRutina.getRutina().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPreferencia perfilPreferencia = preferenciaXRutina.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                preferenciaXRutina.setPerfilPreferencia(perfilPreferencia);
            }
            Rutina rutina = preferenciaXRutina.getRutina();
            if (rutina != null) {
                rutina = em.getReference(rutina.getClass(), rutina.getId());
                preferenciaXRutina.setRutina(rutina);
            }
            em.persist(preferenciaXRutina);
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXRutinaList().add(preferenciaXRutina);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            if (rutina != null) {
                rutina.getPreferenciaXRutinaList().add(preferenciaXRutina);
                rutina = em.merge(rutina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreferenciaXRutina(preferenciaXRutina.getPreferenciaXRutinaPK()) != null) {
                throw new PreexistingEntityException("PreferenciaXRutina " + preferenciaXRutina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreferenciaXRutina preferenciaXRutina) throws NonexistentEntityException, Exception {
        preferenciaXRutina.getPreferenciaXRutinaPK().setPreferenciaPwaCedula(preferenciaXRutina.getPerfilPreferencia().getPerfilPwaCedula());
        preferenciaXRutina.getPreferenciaXRutinaPK().setRutinaId(preferenciaXRutina.getRutina().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXRutina persistentPreferenciaXRutina = em.find(PreferenciaXRutina.class, preferenciaXRutina.getPreferenciaXRutinaPK());
            PerfilPreferencia perfilPreferenciaOld = persistentPreferenciaXRutina.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = preferenciaXRutina.getPerfilPreferencia();
            Rutina rutinaOld = persistentPreferenciaXRutina.getRutina();
            Rutina rutinaNew = preferenciaXRutina.getRutina();
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                preferenciaXRutina.setPerfilPreferencia(perfilPreferenciaNew);
            }
            if (rutinaNew != null) {
                rutinaNew = em.getReference(rutinaNew.getClass(), rutinaNew.getId());
                preferenciaXRutina.setRutina(rutinaNew);
            }
            preferenciaXRutina = em.merge(preferenciaXRutina);
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                perfilPreferenciaOld.getPreferenciaXRutinaList().remove(preferenciaXRutina);
                perfilPreferenciaOld = em.merge(perfilPreferenciaOld);
            }
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                perfilPreferenciaNew.getPreferenciaXRutinaList().add(preferenciaXRutina);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            if (rutinaOld != null && !rutinaOld.equals(rutinaNew)) {
                rutinaOld.getPreferenciaXRutinaList().remove(preferenciaXRutina);
                rutinaOld = em.merge(rutinaOld);
            }
            if (rutinaNew != null && !rutinaNew.equals(rutinaOld)) {
                rutinaNew.getPreferenciaXRutinaList().add(preferenciaXRutina);
                rutinaNew = em.merge(rutinaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreferenciaXRutinaPK id = preferenciaXRutina.getPreferenciaXRutinaPK();
                if (findPreferenciaXRutina(id) == null) {
                    throw new NonexistentEntityException("The preferenciaXRutina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreferenciaXRutinaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreferenciaXRutina preferenciaXRutina;
            try {
                preferenciaXRutina = em.getReference(PreferenciaXRutina.class, id);
                preferenciaXRutina.getPreferenciaXRutinaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preferenciaXRutina with id " + id + " no longer exists.", enfe);
            }
            PerfilPreferencia perfilPreferencia = preferenciaXRutina.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia.getPreferenciaXRutinaList().remove(preferenciaXRutina);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            Rutina rutina = preferenciaXRutina.getRutina();
            if (rutina != null) {
                rutina.getPreferenciaXRutinaList().remove(preferenciaXRutina);
                rutina = em.merge(rutina);
            }
            em.remove(preferenciaXRutina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreferenciaXRutina> findPreferenciaXRutinaEntities() {
        return findPreferenciaXRutinaEntities(true, -1, -1);
    }

    public List<PreferenciaXRutina> findPreferenciaXRutinaEntities(int maxResults, int firstResult) {
        return findPreferenciaXRutinaEntities(false, maxResults, firstResult);
    }

    private List<PreferenciaXRutina> findPreferenciaXRutinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreferenciaXRutina.class));
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

    public PreferenciaXRutina findPreferenciaXRutina(PreferenciaXRutinaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreferenciaXRutina.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreferenciaXRutinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreferenciaXRutina> rt = cq.from(PreferenciaXRutina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
