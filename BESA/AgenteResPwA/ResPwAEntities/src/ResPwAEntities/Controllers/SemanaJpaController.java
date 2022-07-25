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
import ResPwAEntities.Rutina;
import ResPwAEntities.Semana;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class SemanaJpaController implements Serializable {

    public SemanaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Semana semana) throws PreexistingEntityException, Exception {
        if (semana.getRutinaList() == null) {
            semana.setRutinaList(new ArrayList<Rutina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Rutina> attachedRutinaList = new ArrayList<Rutina>();
            for (Rutina rutinaListRutinaToAttach : semana.getRutinaList()) {
                rutinaListRutinaToAttach = em.getReference(rutinaListRutinaToAttach.getClass(), rutinaListRutinaToAttach.getId());
                attachedRutinaList.add(rutinaListRutinaToAttach);
            }
            semana.setRutinaList(attachedRutinaList);
            em.persist(semana);
            for (Rutina rutinaListRutina : semana.getRutinaList()) {
                rutinaListRutina.getSemanaList().add(semana);
                rutinaListRutina = em.merge(rutinaListRutina);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSemana(semana.getId()) != null) {
                throw new PreexistingEntityException("Semana " + semana + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Semana semana) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Semana persistentSemana = em.find(Semana.class, semana.getId());
            List<Rutina> rutinaListOld = persistentSemana.getRutinaList();
            List<Rutina> rutinaListNew = semana.getRutinaList();
            List<Rutina> attachedRutinaListNew = new ArrayList<Rutina>();
            for (Rutina rutinaListNewRutinaToAttach : rutinaListNew) {
                rutinaListNewRutinaToAttach = em.getReference(rutinaListNewRutinaToAttach.getClass(), rutinaListNewRutinaToAttach.getId());
                attachedRutinaListNew.add(rutinaListNewRutinaToAttach);
            }
            rutinaListNew = attachedRutinaListNew;
            semana.setRutinaList(rutinaListNew);
            semana = em.merge(semana);
            for (Rutina rutinaListOldRutina : rutinaListOld) {
                if (!rutinaListNew.contains(rutinaListOldRutina)) {
                    rutinaListOldRutina.getSemanaList().remove(semana);
                    rutinaListOldRutina = em.merge(rutinaListOldRutina);
                }
            }
            for (Rutina rutinaListNewRutina : rutinaListNew) {
                if (!rutinaListOld.contains(rutinaListNewRutina)) {
                    rutinaListNewRutina.getSemanaList().add(semana);
                    rutinaListNewRutina = em.merge(rutinaListNewRutina);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = semana.getId();
                if (findSemana(id) == null) {
                    throw new NonexistentEntityException("The semana with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Semana semana;
            try {
                semana = em.getReference(Semana.class, id);
                semana.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The semana with id " + id + " no longer exists.", enfe);
            }
            List<Rutina> rutinaList = semana.getRutinaList();
            for (Rutina rutinaListRutina : rutinaList) {
                rutinaListRutina.getSemanaList().remove(semana);
                rutinaListRutina = em.merge(rutinaListRutina);
            }
            em.remove(semana);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Semana> findSemanaEntities() {
        return findSemanaEntities(true, -1, -1);
    }

    public List<Semana> findSemanaEntities(int maxResults, int firstResult) {
        return findSemanaEntities(false, maxResults, firstResult);
    }

    private List<Semana> findSemanaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Semana.class));
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

    public Semana findSemana(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Semana.class, id);
        } finally {
            em.close();
        }
    }

    public int getSemanaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Semana> rt = cq.from(Semana.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
