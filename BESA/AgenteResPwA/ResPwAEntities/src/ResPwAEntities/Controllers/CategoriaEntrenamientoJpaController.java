/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.CategoriaEntrenamiento;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.Ejercicio;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.ProgramaEjercicio;
import ResPwAEntities.Intensidad;
import ResPwAEntities.DiaXCategoriaEntrenamiento;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class CategoriaEntrenamientoJpaController implements Serializable {

    public CategoriaEntrenamientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaEntrenamiento categoriaEntrenamiento) throws PreexistingEntityException, Exception {
        if (categoriaEntrenamiento.getEjercicioList() == null) {
            categoriaEntrenamiento.setEjercicioList(new ArrayList<Ejercicio>());
        }
        if (categoriaEntrenamiento.getProgramaEjercicioList() == null) {
            categoriaEntrenamiento.setProgramaEjercicioList(new ArrayList<ProgramaEjercicio>());
        }
        if (categoriaEntrenamiento.getIntensidadList() == null) {
            categoriaEntrenamiento.setIntensidadList(new ArrayList<Intensidad>());
        }
        if (categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList() == null) {
            categoriaEntrenamiento.setDiaXCategoriaEntrenamientoList(new ArrayList<DiaXCategoriaEntrenamiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ejercicio> attachedEjercicioList = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListEjercicioToAttach : categoriaEntrenamiento.getEjercicioList()) {
                ejercicioListEjercicioToAttach = em.getReference(ejercicioListEjercicioToAttach.getClass(), ejercicioListEjercicioToAttach.getNombre());
                attachedEjercicioList.add(ejercicioListEjercicioToAttach);
            }
            categoriaEntrenamiento.setEjercicioList(attachedEjercicioList);
            List<ProgramaEjercicio> attachedProgramaEjercicioList = new ArrayList<ProgramaEjercicio>();
            for (ProgramaEjercicio programaEjercicioListProgramaEjercicioToAttach : categoriaEntrenamiento.getProgramaEjercicioList()) {
                programaEjercicioListProgramaEjercicioToAttach = em.getReference(programaEjercicioListProgramaEjercicioToAttach.getClass(), programaEjercicioListProgramaEjercicioToAttach.getNombre());
                attachedProgramaEjercicioList.add(programaEjercicioListProgramaEjercicioToAttach);
            }
            categoriaEntrenamiento.setProgramaEjercicioList(attachedProgramaEjercicioList);
            List<Intensidad> attachedIntensidadList = new ArrayList<Intensidad>();
            for (Intensidad intensidadListIntensidadToAttach : categoriaEntrenamiento.getIntensidadList()) {
                intensidadListIntensidadToAttach = em.getReference(intensidadListIntensidadToAttach.getClass(), intensidadListIntensidadToAttach.getId());
                attachedIntensidadList.add(intensidadListIntensidadToAttach);
            }
            categoriaEntrenamiento.setIntensidadList(attachedIntensidadList);
            List<DiaXCategoriaEntrenamiento> attachedDiaXCategoriaEntrenamientoList = new ArrayList<DiaXCategoriaEntrenamiento>();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach : categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList()) {
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach = em.getReference(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach.getClass(), diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach.getDiaXCategoriaEntrenamientoPK());
                attachedDiaXCategoriaEntrenamientoList.add(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamientoToAttach);
            }
            categoriaEntrenamiento.setDiaXCategoriaEntrenamientoList(attachedDiaXCategoriaEntrenamientoList);
            em.persist(categoriaEntrenamiento);
            for (Ejercicio ejercicioListEjercicio : categoriaEntrenamiento.getEjercicioList()) {
                ejercicioListEjercicio.getCategoriaEntrenamientoList().add(categoriaEntrenamiento);
                ejercicioListEjercicio = em.merge(ejercicioListEjercicio);
            }
            for (ProgramaEjercicio programaEjercicioListProgramaEjercicio : categoriaEntrenamiento.getProgramaEjercicioList()) {
                programaEjercicioListProgramaEjercicio.getCategoriaEntrenamientoList().add(categoriaEntrenamiento);
                programaEjercicioListProgramaEjercicio = em.merge(programaEjercicioListProgramaEjercicio);
            }
            for (Intensidad intensidadListIntensidad : categoriaEntrenamiento.getIntensidadList()) {
                CategoriaEntrenamiento oldCategoriaEntrenamientoTipoOfIntensidadListIntensidad = intensidadListIntensidad.getCategoriaEntrenamientoTipo();
                intensidadListIntensidad.setCategoriaEntrenamientoTipo(categoriaEntrenamiento);
                intensidadListIntensidad = em.merge(intensidadListIntensidad);
                if (oldCategoriaEntrenamientoTipoOfIntensidadListIntensidad != null) {
                    oldCategoriaEntrenamientoTipoOfIntensidadListIntensidad.getIntensidadList().remove(intensidadListIntensidad);
                    oldCategoriaEntrenamientoTipoOfIntensidadListIntensidad = em.merge(oldCategoriaEntrenamientoTipoOfIntensidadListIntensidad);
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento : categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList()) {
                CategoriaEntrenamiento oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.setCategoriaEntrenamiento(categoriaEntrenamiento);
                diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = em.merge(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                if (oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento != null) {
                    oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                    oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento = em.merge(oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListDiaXCategoriaEntrenamiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCategoriaEntrenamiento(categoriaEntrenamiento.getTipo()) != null) {
                throw new PreexistingEntityException("CategoriaEntrenamiento " + categoriaEntrenamiento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaEntrenamiento categoriaEntrenamiento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaEntrenamiento persistentCategoriaEntrenamiento = em.find(CategoriaEntrenamiento.class, categoriaEntrenamiento.getTipo());
            List<Ejercicio> ejercicioListOld = persistentCategoriaEntrenamiento.getEjercicioList();
            List<Ejercicio> ejercicioListNew = categoriaEntrenamiento.getEjercicioList();
            List<ProgramaEjercicio> programaEjercicioListOld = persistentCategoriaEntrenamiento.getProgramaEjercicioList();
            List<ProgramaEjercicio> programaEjercicioListNew = categoriaEntrenamiento.getProgramaEjercicioList();
            List<Intensidad> intensidadListOld = persistentCategoriaEntrenamiento.getIntensidadList();
            List<Intensidad> intensidadListNew = categoriaEntrenamiento.getIntensidadList();
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListOld = persistentCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoList();
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListNew = categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList();
            List<String> illegalOrphanMessages = null;
            for (Intensidad intensidadListOldIntensidad : intensidadListOld) {
                if (!intensidadListNew.contains(intensidadListOldIntensidad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Intensidad " + intensidadListOldIntensidad + " since its categoriaEntrenamientoTipo field is not nullable.");
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListOld) {
                if (!diaXCategoriaEntrenamientoListNew.contains(diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DiaXCategoriaEntrenamiento " + diaXCategoriaEntrenamientoListOldDiaXCategoriaEntrenamiento + " since its categoriaEntrenamiento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ejercicio> attachedEjercicioListNew = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListNewEjercicioToAttach : ejercicioListNew) {
                ejercicioListNewEjercicioToAttach = em.getReference(ejercicioListNewEjercicioToAttach.getClass(), ejercicioListNewEjercicioToAttach.getNombre());
                attachedEjercicioListNew.add(ejercicioListNewEjercicioToAttach);
            }
            ejercicioListNew = attachedEjercicioListNew;
            categoriaEntrenamiento.setEjercicioList(ejercicioListNew);
            List<ProgramaEjercicio> attachedProgramaEjercicioListNew = new ArrayList<ProgramaEjercicio>();
            for (ProgramaEjercicio programaEjercicioListNewProgramaEjercicioToAttach : programaEjercicioListNew) {
                programaEjercicioListNewProgramaEjercicioToAttach = em.getReference(programaEjercicioListNewProgramaEjercicioToAttach.getClass(), programaEjercicioListNewProgramaEjercicioToAttach.getNombre());
                attachedProgramaEjercicioListNew.add(programaEjercicioListNewProgramaEjercicioToAttach);
            }
            programaEjercicioListNew = attachedProgramaEjercicioListNew;
            categoriaEntrenamiento.setProgramaEjercicioList(programaEjercicioListNew);
            List<Intensidad> attachedIntensidadListNew = new ArrayList<Intensidad>();
            for (Intensidad intensidadListNewIntensidadToAttach : intensidadListNew) {
                intensidadListNewIntensidadToAttach = em.getReference(intensidadListNewIntensidadToAttach.getClass(), intensidadListNewIntensidadToAttach.getId());
                attachedIntensidadListNew.add(intensidadListNewIntensidadToAttach);
            }
            intensidadListNew = attachedIntensidadListNew;
            categoriaEntrenamiento.setIntensidadList(intensidadListNew);
            List<DiaXCategoriaEntrenamiento> attachedDiaXCategoriaEntrenamientoListNew = new ArrayList<DiaXCategoriaEntrenamiento>();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach : diaXCategoriaEntrenamientoListNew) {
                diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach = em.getReference(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach.getClass(), diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach.getDiaXCategoriaEntrenamientoPK());
                attachedDiaXCategoriaEntrenamientoListNew.add(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamientoToAttach);
            }
            diaXCategoriaEntrenamientoListNew = attachedDiaXCategoriaEntrenamientoListNew;
            categoriaEntrenamiento.setDiaXCategoriaEntrenamientoList(diaXCategoriaEntrenamientoListNew);
            categoriaEntrenamiento = em.merge(categoriaEntrenamiento);
            for (Ejercicio ejercicioListOldEjercicio : ejercicioListOld) {
                if (!ejercicioListNew.contains(ejercicioListOldEjercicio)) {
                    ejercicioListOldEjercicio.getCategoriaEntrenamientoList().remove(categoriaEntrenamiento);
                    ejercicioListOldEjercicio = em.merge(ejercicioListOldEjercicio);
                }
            }
            for (Ejercicio ejercicioListNewEjercicio : ejercicioListNew) {
                if (!ejercicioListOld.contains(ejercicioListNewEjercicio)) {
                    ejercicioListNewEjercicio.getCategoriaEntrenamientoList().add(categoriaEntrenamiento);
                    ejercicioListNewEjercicio = em.merge(ejercicioListNewEjercicio);
                }
            }
            for (ProgramaEjercicio programaEjercicioListOldProgramaEjercicio : programaEjercicioListOld) {
                if (!programaEjercicioListNew.contains(programaEjercicioListOldProgramaEjercicio)) {
                    programaEjercicioListOldProgramaEjercicio.getCategoriaEntrenamientoList().remove(categoriaEntrenamiento);
                    programaEjercicioListOldProgramaEjercicio = em.merge(programaEjercicioListOldProgramaEjercicio);
                }
            }
            for (ProgramaEjercicio programaEjercicioListNewProgramaEjercicio : programaEjercicioListNew) {
                if (!programaEjercicioListOld.contains(programaEjercicioListNewProgramaEjercicio)) {
                    programaEjercicioListNewProgramaEjercicio.getCategoriaEntrenamientoList().add(categoriaEntrenamiento);
                    programaEjercicioListNewProgramaEjercicio = em.merge(programaEjercicioListNewProgramaEjercicio);
                }
            }
            for (Intensidad intensidadListNewIntensidad : intensidadListNew) {
                if (!intensidadListOld.contains(intensidadListNewIntensidad)) {
                    CategoriaEntrenamiento oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad = intensidadListNewIntensidad.getCategoriaEntrenamientoTipo();
                    intensidadListNewIntensidad.setCategoriaEntrenamientoTipo(categoriaEntrenamiento);
                    intensidadListNewIntensidad = em.merge(intensidadListNewIntensidad);
                    if (oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad != null && !oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad.equals(categoriaEntrenamiento)) {
                        oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad.getIntensidadList().remove(intensidadListNewIntensidad);
                        oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad = em.merge(oldCategoriaEntrenamientoTipoOfIntensidadListNewIntensidad);
                    }
                }
            }
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListNew) {
                if (!diaXCategoriaEntrenamientoListOld.contains(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento)) {
                    CategoriaEntrenamiento oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.getCategoriaEntrenamiento();
                    diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.setCategoriaEntrenamiento(categoriaEntrenamiento);
                    diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = em.merge(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                    if (oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento != null && !oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.equals(categoriaEntrenamiento)) {
                        oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento.getDiaXCategoriaEntrenamientoList().remove(diaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                        oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento = em.merge(oldCategoriaEntrenamientoOfDiaXCategoriaEntrenamientoListNewDiaXCategoriaEntrenamiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = categoriaEntrenamiento.getTipo();
                if (findCategoriaEntrenamiento(id) == null) {
                    throw new NonexistentEntityException("The categoriaEntrenamiento with id " + id + " no longer exists.");
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
            CategoriaEntrenamiento categoriaEntrenamiento;
            try {
                categoriaEntrenamiento = em.getReference(CategoriaEntrenamiento.class, id);
                categoriaEntrenamiento.getTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaEntrenamiento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Intensidad> intensidadListOrphanCheck = categoriaEntrenamiento.getIntensidadList();
            for (Intensidad intensidadListOrphanCheckIntensidad : intensidadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaEntrenamiento (" + categoriaEntrenamiento + ") cannot be destroyed since the Intensidad " + intensidadListOrphanCheckIntensidad + " in its intensidadList field has a non-nullable categoriaEntrenamientoTipo field.");
            }
            List<DiaXCategoriaEntrenamiento> diaXCategoriaEntrenamientoListOrphanCheck = categoriaEntrenamiento.getDiaXCategoriaEntrenamientoList();
            for (DiaXCategoriaEntrenamiento diaXCategoriaEntrenamientoListOrphanCheckDiaXCategoriaEntrenamiento : diaXCategoriaEntrenamientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CategoriaEntrenamiento (" + categoriaEntrenamiento + ") cannot be destroyed since the DiaXCategoriaEntrenamiento " + diaXCategoriaEntrenamientoListOrphanCheckDiaXCategoriaEntrenamiento + " in its diaXCategoriaEntrenamientoList field has a non-nullable categoriaEntrenamiento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ejercicio> ejercicioList = categoriaEntrenamiento.getEjercicioList();
            for (Ejercicio ejercicioListEjercicio : ejercicioList) {
                ejercicioListEjercicio.getCategoriaEntrenamientoList().remove(categoriaEntrenamiento);
                ejercicioListEjercicio = em.merge(ejercicioListEjercicio);
            }
            List<ProgramaEjercicio> programaEjercicioList = categoriaEntrenamiento.getProgramaEjercicioList();
            for (ProgramaEjercicio programaEjercicioListProgramaEjercicio : programaEjercicioList) {
                programaEjercicioListProgramaEjercicio.getCategoriaEntrenamientoList().remove(categoriaEntrenamiento);
                programaEjercicioListProgramaEjercicio = em.merge(programaEjercicioListProgramaEjercicio);
            }
            em.remove(categoriaEntrenamiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaEntrenamiento> findCategoriaEntrenamientoEntities() {
        return findCategoriaEntrenamientoEntities(true, -1, -1);
    }

    public List<CategoriaEntrenamiento> findCategoriaEntrenamientoEntities(int maxResults, int firstResult) {
        return findCategoriaEntrenamientoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaEntrenamiento> findCategoriaEntrenamientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaEntrenamiento.class));
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

    public CategoriaEntrenamiento findCategoriaEntrenamiento(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaEntrenamiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaEntrenamientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaEntrenamiento> rt = cq.from(CategoriaEntrenamiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
