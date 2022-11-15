/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Dia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.DiaXProgramaEjercicio;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.DiaXCategoriaEntrenamiento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class DiaJpaController implements Serializable {

    public DiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dia dia) throws PreexistingEntityException, Exception {
        if (dia.getDiaXProgramaEjercicioList() == null) {
            dia.setDiaXProgramaEjercicioList(new ArrayList<DiaXProgramaEjercicio>());
        }
        if (dia.getDiaXCategoriaEntrenamientoList() == null) {
            dia.setDiaXCategoriaEntrenamientoList(new ArrayList<DiaXCategoriaEntrenamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DiaXProgramaEjercicio> attachedDiaXProgramaEjercicioList = new ArrayList<DiaXProgramaEjercicio>();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach : dia.getDiaXProgramaEjercicioList()) {
                diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach = em.getReference(diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach.getClass(), diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach.getDiaXProgramaEjercicioPK());
                attachedDiaXProgramaEjercicioList.add(diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach);
            }
            dia.setDiaXProgramaEjercicioList(attachedDiaXProgramaEjercicioList);
            List<DiaXCategoriaEntrenamiento> attachedDiaXCategoriaEntrenamientoList = new ArrayList<DiaXCategoriaEntrenamiento>();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach : dia.getDiaXCategoriaEntrenamientoList()) {
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach = em.getReference(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach.getClass(), diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach.getDiaXCategoriaEntrenamientoPK());
                attachedDiaXCategoriaEntrenamientoList.add(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach);
            }
            dia.setDiaXCategoriaEntrenamientoList(attachedDiaXCategoriaEntrenamientoList);
            em.persist(dia);
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListDiaXProgramaEjercicio : dia.getDiaXProgramaEjercicioList()) {
                Dia oldDiaOfDiaXProgramaEjercicioListDiaXProgramaEjercicio = diaXProgramaEjercicioListDiaXProgramaEjercicio.getDia();
                diaXProgramaEjercicioListDiaXProgramaEjercicio.setDia(dia);
                diaXProgramaEjercicioListDiaXProgramaEjercicio = em.merge(diaXProgramaEjercicioListDiaXProgramaEjercicio);
                if (oldDiaOfDiaXProgramaEjercicioListDiaXProgramaEjercicio != null) {
                    oldDiaOfDiaXProgramaEjercicioListDiaXProgramaEjercicio.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicioListDiaXProgramaEjercicio);
                    oldDiaOfDiaXProgramaEjercicioListDiaXProgramaEjercicio = em.merge(oldDiaOfDiaXProgramaEjercicioListDiaXProgramaEjercicio);
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento : dia.getDiaXCategoriaEntrenamientoList()) {
                Dia oldDiaOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.getDia();
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.setDia(dia);
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = em.merge(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                if (oldDiaOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento != null) {
                    oldDiaOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                    oldDiaOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = em.merge(oldDiaOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDia(dia.getNombre()) != null) {
                throw new PreexistingEntityException("Dia " + dia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dia dia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia persistentDia = em.find(Dia.class, dia.getNombre());
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListOld = persistentDia.getDiaXProgramaEjercicioList();
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListNew = dia.getDiaXProgramaEjercicioList();
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListOld = persistentDia.getDiaXCategoriaEntrenamientoList();
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListNew = dia.getDiaXCategoriaEntrenamientoList();
            List<String> illegalOrphanMessages = null;
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListOldDiaXProgramaEjercicio : diaXProgramaEjercicioListOld) {
                if (!diaXProgramaEjercicioListNew.contains(diaXProgramaEjercicioListOldDiaXProgramaEjercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiaXProgramaEjercicio " + diaXProgramaEjercicioListOldDiaXProgramaEjercicio + " since its dia field is not nullable.");
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListOld) {
                if (!diaXCategoriaEntrenamientoListNew.contains(diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiaXCategoriaEntrenamiento " + diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento + " since its dia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DiaXProgramaEjercicio> attachedDiaXProgramaEjercicioListNew = new ArrayList<DiaXProgramaEjercicio>();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach : diaXProgramaEjercicioListNew) {
                diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach = em.getReference(diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach.getClass(), diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach.getDiaXProgramaEjercicioPK());
                attachedDiaXProgramaEjercicioListNew.add(diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach);
            }
            diaXProgramaEjercicioListNew = attachedDiaXProgramaEjercicioListNew;
            dia.setDiaXProgramaEjercicioList(diaXProgramaEjercicioListNew);
            List<DiaXCategoriaEntrenamiento> attachedDiaXCategoriaEntrenamientoListNew = new ArrayList<DiaXCategoriaEntrenamiento>();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach : diaXCategoriaEntrenamientoListNew) {
                diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach = em.getReference(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach.getClass(), diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach.getDiaXCategoriaEntrenamientoPK());
                attachedDiaXCategoriaEntrenamientoListNew.add(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach);
            }
            diaXCategoriaEntrenamientoListNew = attachedDiaXCategoriaEntrenamientoListNew;
            dia.setDiaXCategoriaEntrenamientoList(diaXCategoriaEntrenamientoListNew);
            dia = em.merge(dia);
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListNewDiaXProgramaEjercicio : diaXProgramaEjercicioListNew) {
                if (!diaXProgramaEjercicioListOld.contains(diaXProgramaEjercicioListNewDiaXProgramaEjercicio)) {
                    Dia oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio = diaXProgramaEjercicioListNewDiaXProgramaEjercicio.getDia();
                    diaXProgramaEjercicioListNewDiaXProgramaEjercicio.setDia(dia);
                    diaXProgramaEjercicioListNewDiaXProgramaEjercicio = em.merge(diaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                    if (oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio != null && !oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio.equals(dia)) {
                        oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                        oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio = em.merge(oldDiaOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                    }
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListNew) {
                if (!diaXCategoriaEntrenamientoListOld.contains(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento)) {
                    Dia oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.getDia();
                    diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.setDia(dia);
                    diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = em.merge(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                    if (oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento != null && !oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.equals(dia)) {
                        oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                        oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = em.merge(oldDiaOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = dia.getNombre();
                if (findDia(id) == null) {
                    throw new NonexistentEntityException("The dia with id " + id + " no longer exists.");
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
            Dia dia;
            try {
                dia = em.getReference(Dia.class, id);
                dia.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListOrphanCheck = dia.getDiaXProgramaEjercicioList();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListOrphanCheckDiaXProgramaEjercicio : diaXProgramaEjercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dia (" + dia + ") cannot be destroyed since the DiaXProgramaEjercicio " + diaXProgramaEjercicioListOrphanCheckDiaXProgramaEjercicio + " in its diaXProgramaEjercicioList field has a non-nullable dia field.");
            }
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListOrphanCheck = dia.getDiaXCategoriaEntrenamientoList();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListOrphanCheckDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dia (" + dia + ") cannot be destroyed since the DiaXCategoriaEntrenamiento " + diaXCategoriaEntrenamientoListOrphanCheckDiaXCategoriaEntrenamiento + " in its diaXCategoriaEntrenamientoList field has a non-nullable dia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dia> findDiaEntities() {
        return findDiaEntities(true, -1, -1);
    }

    public List<Dia> findDiaEntities(int maxResults, int firstResult) {
        return findDiaEntities(false, maxResults, firstResult);
    }

    private List<Dia> findDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dia.class));
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

    public Dia findDia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dia.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dia> rt = cq.from(Dia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
