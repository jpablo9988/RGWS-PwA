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
import ResPwAEntities.CategoriaEntrenamiento;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.DiaXProgramaEjercicio;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.ProgramaEjercicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class ProgramaEjercicioJpaController implements Serializable {

    public ProgramaEjercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProgramaEjercicio programaEjercicio) throws PreexistingEntityException, Exception {
        if (programaEjercicio.getCategoriaEntrenamientoList() == null) {
            programaEjercicio.setCategoriaEntrenamientoList(new ArrayList<CategoriaEntrenamiento>());
        }
        if (programaEjercicio.getDiaXProgramaEjercicioList() == null) {
            programaEjercicio.setDiaXProgramaEjercicioList(new ArrayList<DiaXProgramaEjercicio>());
        }
        if (programaEjercicio.getPerfilEjercicioList() == null) {
            programaEjercicio.setPerfilEjercicioList(new ArrayList<PerfilEjercicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CategoriaEntrenamiento> attachedCategoriaEntrenamientoList = new ArrayList<CategoriaEntrenamiento>();
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamientoToAttach : programaEjercicio.getCategoriaEntrenamientoList()) {
                categoriaEntrenamientoListCategoriaEntrenamientoToAttach = em.getReference(categoriaEntrenamientoListCategoriaEntrenamientoToAttach.getClass(), categoriaEntrenamientoListCategoriaEntrenamientoToAttach.getTipo());
                attachedCategoriaEntrenamientoList.add(categoriaEntrenamientoListCategoriaEntrenamientoToAttach);
            }
            programaEjercicio.setCategoriaEntrenamientoList(attachedCategoriaEntrenamientoList);
            List<DiaXProgramaEjercicio> attachedDiaXProgramaEjercicioList = new ArrayList<DiaXProgramaEjercicio>();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach : programaEjercicio.getDiaXProgramaEjercicioList()) {
                diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach = em.getReference(diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach.getClass(), diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach.getDiaXProgramaEjercicioPK());
                attachedDiaXProgramaEjercicioList.add(diaXProgramaEjercicioListDiaXProgramaEjercicioToAttach);
            }
            programaEjercicio.setDiaXProgramaEjercicioList(attachedDiaXProgramaEjercicioList);
            List<PerfilEjercicio> attachedPerfilEjercicioList = new ArrayList<PerfilEjercicio>();
            for (PerfilEjercicio perfilEjercicioListPerfilEjercicioToAttach : programaEjercicio.getPerfilEjercicioList()) {
                perfilEjercicioListPerfilEjercicioToAttach = em.getReference(perfilEjercicioListPerfilEjercicioToAttach.getClass(), perfilEjercicioListPerfilEjercicioToAttach.getPerfilPwaCedula());
                attachedPerfilEjercicioList.add(perfilEjercicioListPerfilEjercicioToAttach);
            }
            programaEjercicio.setPerfilEjercicioList(attachedPerfilEjercicioList);
            em.persist(programaEjercicio);
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamiento : programaEjercicio.getCategoriaEntrenamientoList()) {
                categoriaEntrenamientoListCategoriaEntrenamiento.getProgramaEjercicioList().add(programaEjercicio);
                categoriaEntrenamientoListCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListCategoriaEntrenamiento);
            }
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListDiaXProgramaEjercicio : programaEjercicio.getDiaXProgramaEjercicioList()) {
                ProgramaEjercicio oldProgramaEjercicioOfDiaXProgramaEjercicioListDiaXProgramaEjercicio = diaXProgramaEjercicioListDiaXProgramaEjercicio.getProgramaEjercicio();
                diaXProgramaEjercicioListDiaXProgramaEjercicio.setProgramaEjercicio(programaEjercicio);
                diaXProgramaEjercicioListDiaXProgramaEjercicio = em.merge(diaXProgramaEjercicioListDiaXProgramaEjercicio);
                if (oldProgramaEjercicioOfDiaXProgramaEjercicioListDiaXProgramaEjercicio != null) {
                    oldProgramaEjercicioOfDiaXProgramaEjercicioListDiaXProgramaEjercicio.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicioListDiaXProgramaEjercicio);
                    oldProgramaEjercicioOfDiaXProgramaEjercicioListDiaXProgramaEjercicio = em.merge(oldProgramaEjercicioOfDiaXProgramaEjercicioListDiaXProgramaEjercicio);
                }
            }
            for (PerfilEjercicio perfilEjercicioListPerfilEjercicio : programaEjercicio.getPerfilEjercicioList()) {
                ProgramaEjercicio oldNombreProgramaOfPerfilEjercicioListPerfilEjercicio = perfilEjercicioListPerfilEjercicio.getNombrePrograma();
                perfilEjercicioListPerfilEjercicio.setNombrePrograma(programaEjercicio);
                perfilEjercicioListPerfilEjercicio = em.merge(perfilEjercicioListPerfilEjercicio);
                if (oldNombreProgramaOfPerfilEjercicioListPerfilEjercicio != null) {
                    oldNombreProgramaOfPerfilEjercicioListPerfilEjercicio.getPerfilEjercicioList().remove(perfilEjercicioListPerfilEjercicio);
                    oldNombreProgramaOfPerfilEjercicioListPerfilEjercicio = em.merge(oldNombreProgramaOfPerfilEjercicioListPerfilEjercicio);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProgramaEjercicio(programaEjercicio.getNombre()) != null) {
                throw new PreexistingEntityException("ProgramaEjercicio " + programaEjercicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProgramaEjercicio programaEjercicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProgramaEjercicio persistentProgramaEjercicio = em.find(ProgramaEjercicio.class, programaEjercicio.getNombre());
            List<CategoriaEntrenamiento> categoriaEntrenamientoListOld = persistentProgramaEjercicio.getCategoriaEntrenamientoList();
            List<CategoriaEntrenamiento> categoriaEntrenamientoListNew = programaEjercicio.getCategoriaEntrenamientoList();
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListOld = persistentProgramaEjercicio.getDiaXProgramaEjercicioList();
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListNew = programaEjercicio.getDiaXProgramaEjercicioList();
            List<PerfilEjercicio> perfilEjercicioListOld = persistentProgramaEjercicio.getPerfilEjercicioList();
            List<PerfilEjercicio> perfilEjercicioListNew = programaEjercicio.getPerfilEjercicioList();
            List<String> illegalOrphanMessages = null;
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListOldDiaXProgramaEjercicio : diaXProgramaEjercicioListOld) {
                if (!diaXProgramaEjercicioListNew.contains(diaXProgramaEjercicioListOldDiaXProgramaEjercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiaXProgramaEjercicio " + diaXProgramaEjercicioListOldDiaXProgramaEjercicio + " since its programaEjercicio field is not nullable.");
                }
            }
            for (PerfilEjercicio perfilEjercicioListOldPerfilEjercicio : perfilEjercicioListOld) {
                if (!perfilEjercicioListNew.contains(perfilEjercicioListOldPerfilEjercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PerfilEjercicio " + perfilEjercicioListOldPerfilEjercicio + " since its nombrePrograma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CategoriaEntrenamiento> attachedCategoriaEntrenamientoListNew = new ArrayList<CategoriaEntrenamiento>();
            for (CategoriaEntrenamiento categoriaEntrenamientoListNewCategoriaEntrenamientoToAttach : categoriaEntrenamientoListNew) {
                categoriaEntrenamientoListNewCategoriaEntrenamientoToAttach = em.getReference(categoriaEntrenamientoListNewCategoriaEntrenamientoToAttach.getClass(), categoriaEntrenamientoListNewCategoriaEntrenamientoToAttach.getTipo());
                attachedCategoriaEntrenamientoListNew.add(categoriaEntrenamientoListNewCategoriaEntrenamientoToAttach);
            }
            categoriaEntrenamientoListNew = attachedCategoriaEntrenamientoListNew;
            programaEjercicio.setCategoriaEntrenamientoList(categoriaEntrenamientoListNew);
            List<DiaXProgramaEjercicio> attachedDiaXProgramaEjercicioListNew = new ArrayList<DiaXProgramaEjercicio>();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach : diaXProgramaEjercicioListNew) {
                diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach = em.getReference(diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach.getClass(), diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach.getDiaXProgramaEjercicioPK());
                attachedDiaXProgramaEjercicioListNew.add(diaXProgramaEjercicioListNewDiaXProgramaEjercicioToAttach);
            }
            diaXProgramaEjercicioListNew = attachedDiaXProgramaEjercicioListNew;
            programaEjercicio.setDiaXProgramaEjercicioList(diaXProgramaEjercicioListNew);
            List<PerfilEjercicio> attachedPerfilEjercicioListNew = new ArrayList<PerfilEjercicio>();
            for (PerfilEjercicio perfilEjercicioListNewPerfilEjercicioToAttach : perfilEjercicioListNew) {
                perfilEjercicioListNewPerfilEjercicioToAttach = em.getReference(perfilEjercicioListNewPerfilEjercicioToAttach.getClass(), perfilEjercicioListNewPerfilEjercicioToAttach.getPerfilPwaCedula());
                attachedPerfilEjercicioListNew.add(perfilEjercicioListNewPerfilEjercicioToAttach);
            }
            perfilEjercicioListNew = attachedPerfilEjercicioListNew;
            programaEjercicio.setPerfilEjercicioList(perfilEjercicioListNew);
            programaEjercicio = em.merge(programaEjercicio);
            for (CategoriaEntrenamiento categoriaEntrenamientoListOldCategoriaEntrenamiento : categoriaEntrenamientoListOld) {
                if (!categoriaEntrenamientoListNew.contains(categoriaEntrenamientoListOldCategoriaEntrenamiento)) {
                    categoriaEntrenamientoListOldCategoriaEntrenamiento.getProgramaEjercicioList().remove(programaEjercicio);
                    categoriaEntrenamientoListOldCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListOldCategoriaEntrenamiento);
                }
            }
            for (CategoriaEntrenamiento categoriaEntrenamientoListNewCategoriaEntrenamiento : categoriaEntrenamientoListNew) {
                if (!categoriaEntrenamientoListOld.contains(categoriaEntrenamientoListNewCategoriaEntrenamiento)) {
                    categoriaEntrenamientoListNewCategoriaEntrenamiento.getProgramaEjercicioList().add(programaEjercicio);
                    categoriaEntrenamientoListNewCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListNewCategoriaEntrenamiento);
                }
            }
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListNewDiaXProgramaEjercicio : diaXProgramaEjercicioListNew) {
                if (!diaXProgramaEjercicioListOld.contains(diaXProgramaEjercicioListNewDiaXProgramaEjercicio)) {
                    ProgramaEjercicio oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio = diaXProgramaEjercicioListNewDiaXProgramaEjercicio.getProgramaEjercicio();
                    diaXProgramaEjercicioListNewDiaXProgramaEjercicio.setProgramaEjercicio(programaEjercicio);
                    diaXProgramaEjercicioListNewDiaXProgramaEjercicio = em.merge(diaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                    if (oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio != null && !oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio.equals(programaEjercicio)) {
                        oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio.getDiaXProgramaEjercicioList().remove(diaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                        oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio = em.merge(oldProgramaEjercicioOfDiaXProgramaEjercicioListNewDiaXProgramaEjercicio);
                    }
                }
            }
            for (PerfilEjercicio perfilEjercicioListNewPerfilEjercicio : perfilEjercicioListNew) {
                if (!perfilEjercicioListOld.contains(perfilEjercicioListNewPerfilEjercicio)) {
                    ProgramaEjercicio oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio = perfilEjercicioListNewPerfilEjercicio.getNombrePrograma();
                    perfilEjercicioListNewPerfilEjercicio.setNombrePrograma(programaEjercicio);
                    perfilEjercicioListNewPerfilEjercicio = em.merge(perfilEjercicioListNewPerfilEjercicio);
                    if (oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio != null && !oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio.equals(programaEjercicio)) {
                        oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio.getPerfilEjercicioList().remove(perfilEjercicioListNewPerfilEjercicio);
                        oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio = em.merge(oldNombreProgramaOfPerfilEjercicioListNewPerfilEjercicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = programaEjercicio.getNombre();
                if (findProgramaEjercicio(id) == null) {
                    throw new NonexistentEntityException("The programaEjercicio with id " + id + " no longer exists.");
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
            ProgramaEjercicio programaEjercicio;
            try {
                programaEjercicio = em.getReference(ProgramaEjercicio.class, id);
                programaEjercicio.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The programaEjercicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DiaXProgramaEjercicio> diaXProgramaEjercicioListOrphanCheck = programaEjercicio.getDiaXProgramaEjercicioList();
            for (DiaXProgramaEjercicio diaXProgramaEjercicioListOrphanCheckDiaXProgramaEjercicio : diaXProgramaEjercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaEjercicio (" + programaEjercicio + ") cannot be destroyed since the DiaXProgramaEjercicio " + diaXProgramaEjercicioListOrphanCheckDiaXProgramaEjercicio + " in its diaXProgramaEjercicioList field has a non-nullable programaEjercicio field.");
            }
            List<PerfilEjercicio> perfilEjercicioListOrphanCheck = programaEjercicio.getPerfilEjercicioList();
            for (PerfilEjercicio perfilEjercicioListOrphanCheckPerfilEjercicio : perfilEjercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProgramaEjercicio (" + programaEjercicio + ") cannot be destroyed since the PerfilEjercicio " + perfilEjercicioListOrphanCheckPerfilEjercicio + " in its perfilEjercicioList field has a non-nullable nombrePrograma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CategoriaEntrenamiento> categoriaEntrenamientoList = programaEjercicio.getCategoriaEntrenamientoList();
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamiento : categoriaEntrenamientoList) {
                categoriaEntrenamientoListCategoriaEntrenamiento.getProgramaEjercicioList().remove(programaEjercicio);
                categoriaEntrenamientoListCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListCategoriaEntrenamiento);
            }
            em.remove(programaEjercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProgramaEjercicio> findProgramaEjercicioEntities() {
        return findProgramaEjercicioEntities(true, -1, -1);
    }

    public List<ProgramaEjercicio> findProgramaEjercicioEntities(int maxResults, int firstResult) {
        return findProgramaEjercicioEntities(false, maxResults, firstResult);
    }

    private List<ProgramaEjercicio> findProgramaEjercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProgramaEjercicio.class));
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

    public ProgramaEjercicio findProgramaEjercicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProgramaEjercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getProgramaEjercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProgramaEjercicio> rt = cq.from(ProgramaEjercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
