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
import ResPwAEntities.Dia;
import ResPwAEntities.DiaXProgramaEjercicio;
import ResPwAEntities.DiaXProgramaEjercicioPK;
import ResPwAEntities.ProgramaEjercicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class DiaXProgramaEjercicioJpaController implements Serializable {

    public DiaXProgramaEjercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DiaXProgramaEjercicio diaXProgramaEjercicio) throws PreexistingEntityException, Exception {
        if (diaXProgramaEjercicio.getDiaXProgramaEjercicioPK() == null) {
            diaXProgramaEjercicio.setDiaXProgramaEjercicioPK(new DiaXProgramaEjercicioPK());
        }
        diaXProgramaEjercicio.getDiaXProgramaEjercicioPK().setProgramaEjercicioNombre(diaXProgramaEjercicio.getProgramaEjercicio().getNombre());
        diaXProgramaEjercicio.getDiaXProgramaEjercicioPK().setDiaNombre(diaXProgramaEjercicio.getDia().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia dia = diaXProgramaEjercicio.getDia();
            if (dia != null) {
                dia = em.getReference(dia.getClass(), dia.getNombre());
                diaXProgramaEjercicio.setDia(dia);
            }
            ProgramaEjercicio programaEjercicio = diaXProgramaEjercicio.getProgramaEjercicio();
            if (programaEjercicio != null) {
                programaEjercicio = em.getReference(programaEjercicio.getClass(), programaEjercicio.getNombre());
                diaXProgramaEjercicio.setProgramaEjercicio(programaEjercicio);
            }
            em.persist(diaXProgramaEjercicio);
            if (dia != null) {
                dia.getDiaXProgramaEjercicioList().add(diaXProgramaEjercicio);
                dia = em.merge(dia);
            }
            if (programaEjercicio != null) {
                programaEjercicio.getDiaXProgramaEjercicioList().add(diaXProgramaEjercicio);
                programaEjercicio = em.merge(programaEjercicio);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDiaXProgramaEjercicio(diaXProgramaEjercicio.getDiaXProgramaEjercicioPK()) != null) {
                throw new PreexistingEntityException("DiaXProgramaEjercicio " + diaXProgramaEjercicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DiaXProgramaEjercicio diaXProgramaEjercicio) throws NonexistentEntityException, Exception {
        diaXProgramaEjercicio.getDiaXProgramaEjercicioPK().setProgramaEjercicioNombre(diaXProgramaEjercicio.getProgramaEjercicio().getNombre());
        diaXProgramaEjercicio.getDiaXProgramaEjercicioPK().setDiaNombre(diaXProgramaEjercicio.getDia().getNombre());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DiaXProgramaEjercicio persistentDiaXProgramaEjercicio = em.find(DiaXProgramaEjercicio.class, diaXProgramaEjercicio.getDiaXProgramaEjercicioPK());
            Dia diaOld = persistentDiaXProgramaEjercicio.getDia();
            Dia diaNew = diaXProgramaEjercicio.getDia();
            ProgramaEjercicio programaEjercicioOld = persistentDiaXProgramaEjercicio.getProgramaEjercicio();
            ProgramaEjercicio programaEjercicioNew = diaXProgramaEjercicio.getProgramaEjercicio();
            if (diaNew != null) {
                diaNew = em.getReference(diaNew.getClass(), diaNew.getNombre());
                diaXProgramaEjercicio.setDia(diaNew);
            }
            if (programaEjercicioNew != null) {
                programaEjercicioNew = em.getReference(programaEjercicioNew.getClass(), programaEjercicioNew.getNombre());
                diaXProgramaEjercicio.setProgramaEjercicio(programaEjercicioNew);
            }
            diaXProgramaEjercicio = em.merge(diaXProgramaEjercicio);
            if (diaOld != null && !diaOld.equals(diaNew)) {
                diaOld.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicio);
                diaOld = em.merge(diaOld);
            }
            if (diaNew != null && !diaNew.equals(diaOld)) {
                diaNew.getDiaXProgramaEjercicioList().add(diaXProgramaEjercicio);
                diaNew = em.merge(diaNew);
            }
            if (programaEjercicioOld != null && !programaEjercicioOld.equals(programaEjercicioNew)) {
                programaEjercicioOld.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicio);
                programaEjercicioOld = em.merge(programaEjercicioOld);
            }
            if (programaEjercicioNew != null && !programaEjercicioNew.equals(programaEjercicioOld)) {
                programaEjercicioNew.getDiaXProgramaEjercicioList().add(diaXProgramaEjercicio);
                programaEjercicioNew = em.merge(programaEjercicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DiaXProgramaEjercicioPK id = diaXProgramaEjercicio.getDiaXProgramaEjercicioPK();
                if (findDiaXProgramaEjercicio(id) == null) {
                    throw new NonexistentEntityException("The diaXProgramaEjercicio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DiaXProgramaEjercicioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DiaXProgramaEjercicio diaXProgramaEjercicio;
            try {
                diaXProgramaEjercicio = em.getReference(DiaXProgramaEjercicio.class, id);
                diaXProgramaEjercicio.getDiaXProgramaEjercicioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The diaXProgramaEjercicio with id " + id + " no longer exists.", enfe);
            }
            Dia dia = diaXProgramaEjercicio.getDia();
            if (dia != null) {
                dia.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicio);
                dia = em.merge(dia);
            }
            ProgramaEjercicio programaEjercicio = diaXProgramaEjercicio.getProgramaEjercicio();
            if (programaEjercicio != null) {
                programaEjercicio.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicio);
                programaEjercicio = em.merge(programaEjercicio);
            }
            em.remove(diaXProgramaEjercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DiaXProgramaEjercicio> findDiaXProgramaEjercicioEntities() {
        return findDiaXProgramaEjercicioEntities(true, -1, -1);
    }

    public List<DiaXProgramaEjercicio> findDiaXProgramaEjercicioEntities(int maxResults, int firstResult) {
        return findDiaXProgramaEjercicioEntities(false, maxResults, firstResult);
    }

    private List<DiaXProgramaEjercicio> findDiaXProgramaEjercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DiaXProgramaEjercicio.class));
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

    public DiaXProgramaEjercicio findDiaXProgramaEjercicio(DiaXProgramaEjercicioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DiaXProgramaEjercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaXProgramaEjercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DiaXProgramaEjercicio> rt = cq.from(DiaXProgramaEjercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
