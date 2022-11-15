/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResPwAEntities.Controllers;

import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ResPwAEntities.PerfilPwa;
import ResPwAEntities.ProgramaEjercicio;
import ResPwAEntities.Ejercicio;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.Horario;
import ResPwAEntities.Historial;
import ResPwAEntities.PerfilEjercicio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class PerfilEjercicioJpaController implements Serializable {

    public PerfilEjercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilEjercicio perfilEjercicio) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (perfilEjercicio.getEjercicioList() == null) {
            perfilEjercicio.setEjercicioList(new ArrayList<Ejercicio>());
        }
        if (perfilEjercicio.getHorarioList() == null) {
            perfilEjercicio.setHorarioList(new ArrayList<Horario>());
        }
        if (perfilEjercicio.getHistorialList() == null) {
            perfilEjercicio.setHistorialList(new ArrayList<Historial>());
        }
        List<String> illegalOrphanMessages = null;
        PerfilPwa perfilPwaOrphanCheck = perfilEjercicio.getPerfilPwa();
        if (perfilPwaOrphanCheck != null) {
            PerfilEjercicio oldPerfilEjercicioOfPerfilPwa = perfilPwaOrphanCheck.getPerfilEjercicio();
            if (oldPerfilEjercicioOfPerfilPwa != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PerfilPwa " + perfilPwaOrphanCheck + " already has an item of type PerfilEjercicio whose perfilPwa column cannot be null. Please make another selection for the perfilPwa field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPwa perfilPwa = perfilEjercicio.getPerfilPwa();
            if (perfilPwa != null) {
                perfilPwa = em.getReference(perfilPwa.getClass(), perfilPwa.getCedula());
                perfilEjercicio.setPerfilPwa(perfilPwa);
            }
            ProgramaEjercicio nombrePrograma = perfilEjercicio.getNombrePrograma();
            if (nombrePrograma != null) {
                nombrePrograma = em.getReference(nombrePrograma.getClass(), nombrePrograma.getNombre());
                perfilEjercicio.setNombrePrograma(nombrePrograma);
            }
            List<Ejercicio> attachedEjercicioList = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListEjercicioToAttach : perfilEjercicio.getEjercicioList()) {
                ejercicioListEjercicioToAttach = em.getReference(ejercicioListEjercicioToAttach.getClass(), ejercicioListEjercicioToAttach.getNombre());
                attachedEjercicioList.add(ejercicioListEjercicioToAttach);
            }
            perfilEjercicio.setEjercicioList(attachedEjercicioList);
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : perfilEjercicio.getHorarioList()) {
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getIndice());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            perfilEjercicio.setHorarioList(attachedHorarioList);
            List<Historial> attachedHistorialList = new ArrayList<Historial>();
            for (Historial historialListHistorialToAttach : perfilEjercicio.getHistorialList()) {
                historialListHistorialToAttach = em.getReference(historialListHistorialToAttach.getClass(), historialListHistorialToAttach.getLogId());
                attachedHistorialList.add(historialListHistorialToAttach);
            }
            perfilEjercicio.setHistorialList(attachedHistorialList);
            em.persist(perfilEjercicio);
            if (perfilPwa != null) {
                perfilPwa.setPerfilEjercicio(perfilEjercicio);
                perfilPwa = em.merge(perfilPwa);
            }
            if (nombrePrograma != null) {
                nombrePrograma.getPerfilEjercicioList().add(perfilEjercicio);
                nombrePrograma = em.merge(nombrePrograma);
            }
            for (Ejercicio ejercicioListEjercicio : perfilEjercicio.getEjercicioList()) {
                ejercicioListEjercicio.getPerfilEjercicioList().add(perfilEjercicio);
                ejercicioListEjercicio = em.merge(ejercicioListEjercicio);
            }
            for (Horario horarioListHorario : perfilEjercicio.getHorarioList()) {
                PerfilEjercicio oldCedulaOfHorarioListHorario = horarioListHorario.getCedula();
                horarioListHorario.setCedula(perfilEjercicio);
                horarioListHorario = em.merge(horarioListHorario);
                if (oldCedulaOfHorarioListHorario != null) {
                    oldCedulaOfHorarioListHorario.getHorarioList().remove(horarioListHorario);
                    oldCedulaOfHorarioListHorario = em.merge(oldCedulaOfHorarioListHorario);
                }
            }
            for (Historial historialListHistorial : perfilEjercicio.getHistorialList()) {
                PerfilEjercicio oldPwaCedulaOfHistorialListHistorial = historialListHistorial.getPwaCedula();
                historialListHistorial.setPwaCedula(perfilEjercicio);
                historialListHistorial = em.merge(historialListHistorial);
                if (oldPwaCedulaOfHistorialListHistorial != null) {
                    oldPwaCedulaOfHistorialListHistorial.getHistorialList().remove(historialListHistorial);
                    oldPwaCedulaOfHistorialListHistorial = em.merge(oldPwaCedulaOfHistorialListHistorial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfilEjercicio(perfilEjercicio.getPerfilPwaCedula()) != null) {
                throw new PreexistingEntityException("PerfilEjercicio " + perfilEjercicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilEjercicio perfilEjercicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilEjercicio persistentPerfilEjercicio = em.find(PerfilEjercicio.class, perfilEjercicio.getPerfilPwaCedula());
            PerfilPwa perfilPwaOld = persistentPerfilEjercicio.getPerfilPwa();
            PerfilPwa perfilPwaNew = perfilEjercicio.getPerfilPwa();
            ProgramaEjercicio nombreProgramaOld = persistentPerfilEjercicio.getNombrePrograma();
            ProgramaEjercicio nombreProgramaNew = perfilEjercicio.getNombrePrograma();
            List<Ejercicio> ejercicioListOld = persistentPerfilEjercicio.getEjercicioList();
            List<Ejercicio> ejercicioListNew = perfilEjercicio.getEjercicioList();
            List<Horario> horarioListOld = persistentPerfilEjercicio.getHorarioList();
            List<Horario> horarioListNew = perfilEjercicio.getHorarioList();
            List<Historial> historialListOld = persistentPerfilEjercicio.getHistorialList();
            List<Historial> historialListNew = perfilEjercicio.getHistorialList();
            List<String> illegalOrphanMessages = null;
            if (perfilPwaNew != null && !perfilPwaNew.equals(perfilPwaOld)) {
                PerfilEjercicio oldPerfilEjercicioOfPerfilPwa = perfilPwaNew.getPerfilEjercicio();
                if (oldPerfilEjercicioOfPerfilPwa != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PerfilPwa " + perfilPwaNew + " already has an item of type PerfilEjercicio whose perfilPwa column cannot be null. Please make another selection for the perfilPwa field.");
                }
            }
            for (Horario horarioListOldHorario : horarioListOld) {
                if (!horarioListNew.contains(horarioListOldHorario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Horario " + horarioListOldHorario + " since its cedula field is not nullable.");
                }
            }
            for (Historial historialListOldHistorial : historialListOld) {
                if (!historialListNew.contains(historialListOldHistorial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Historial " + historialListOldHistorial + " since its pwaCedula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (perfilPwaNew != null) {
                perfilPwaNew = em.getReference(perfilPwaNew.getClass(), perfilPwaNew.getCedula());
                perfilEjercicio.setPerfilPwa(perfilPwaNew);
            }
            if (nombreProgramaNew != null) {
                nombreProgramaNew = em.getReference(nombreProgramaNew.getClass(), nombreProgramaNew.getNombre());
                perfilEjercicio.setNombrePrograma(nombreProgramaNew);
            }
            List<Ejercicio> attachedEjercicioListNew = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListNewEjercicioToAttach : ejercicioListNew) {
                ejercicioListNewEjercicioToAttach = em.getReference(ejercicioListNewEjercicioToAttach.getClass(), ejercicioListNewEjercicioToAttach.getNombre());
                attachedEjercicioListNew.add(ejercicioListNewEjercicioToAttach);
            }
            ejercicioListNew = attachedEjercicioListNew;
            perfilEjercicio.setEjercicioList(ejercicioListNew);
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew) {
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getIndice());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            perfilEjercicio.setHorarioList(horarioListNew);
            List<Historial> attachedHistorialListNew = new ArrayList<Historial>();
            for (Historial historialListNewHistorialToAttach : historialListNew) {
                historialListNewHistorialToAttach = em.getReference(historialListNewHistorialToAttach.getClass(), historialListNewHistorialToAttach.getLogId());
                attachedHistorialListNew.add(historialListNewHistorialToAttach);
            }
            historialListNew = attachedHistorialListNew;
            perfilEjercicio.setHistorialList(historialListNew);
            perfilEjercicio = em.merge(perfilEjercicio);
            if (perfilPwaOld != null && !perfilPwaOld.equals(perfilPwaNew)) {
                perfilPwaOld.setPerfilEjercicio(null);
                perfilPwaOld = em.merge(perfilPwaOld);
            }
            if (perfilPwaNew != null && !perfilPwaNew.equals(perfilPwaOld)) {
                perfilPwaNew.setPerfilEjercicio(perfilEjercicio);
                perfilPwaNew = em.merge(perfilPwaNew);
            }
            if (nombreProgramaOld != null && !nombreProgramaOld.equals(nombreProgramaNew)) {
                nombreProgramaOld.getPerfilEjercicioList().remove(perfilEjercicio);
                nombreProgramaOld = em.merge(nombreProgramaOld);
            }
            if (nombreProgramaNew != null && !nombreProgramaNew.equals(nombreProgramaOld)) {
                nombreProgramaNew.getPerfilEjercicioList().add(perfilEjercicio);
                nombreProgramaNew = em.merge(nombreProgramaNew);
            }
            for (Ejercicio ejercicioListOldEjercicio : ejercicioListOld) {
                if (!ejercicioListNew.contains(ejercicioListOldEjercicio)) {
                    ejercicioListOldEjercicio.getPerfilEjercicioList().remove(perfilEjercicio);
                    ejercicioListOldEjercicio = em.merge(ejercicioListOldEjercicio);
                }
            }
            for (Ejercicio ejercicioListNewEjercicio : ejercicioListNew) {
                if (!ejercicioListOld.contains(ejercicioListNewEjercicio)) {
                    ejercicioListNewEjercicio.getPerfilEjercicioList().add(perfilEjercicio);
                    ejercicioListNewEjercicio = em.merge(ejercicioListNewEjercicio);
                }
            }
            for (Horario horarioListNewHorario : horarioListNew) {
                if (!horarioListOld.contains(horarioListNewHorario)) {
                    PerfilEjercicio oldCedulaOfHorarioListNewHorario = horarioListNewHorario.getCedula();
                    horarioListNewHorario.setCedula(perfilEjercicio);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                    if (oldCedulaOfHorarioListNewHorario != null && !oldCedulaOfHorarioListNewHorario.equals(perfilEjercicio)) {
                        oldCedulaOfHorarioListNewHorario.getHorarioList().remove(horarioListNewHorario);
                        oldCedulaOfHorarioListNewHorario = em.merge(oldCedulaOfHorarioListNewHorario);
                    }
                }
            }
            for (Historial historialListNewHistorial : historialListNew) {
                if (!historialListOld.contains(historialListNewHistorial)) {
                    PerfilEjercicio oldPwaCedulaOfHistorialListNewHistorial = historialListNewHistorial.getPwaCedula();
                    historialListNewHistorial.setPwaCedula(perfilEjercicio);
                    historialListNewHistorial = em.merge(historialListNewHistorial);
                    if (oldPwaCedulaOfHistorialListNewHistorial != null && !oldPwaCedulaOfHistorialListNewHistorial.equals(perfilEjercicio)) {
                        oldPwaCedulaOfHistorialListNewHistorial.getHistorialList().remove(historialListNewHistorial);
                        oldPwaCedulaOfHistorialListNewHistorial = em.merge(oldPwaCedulaOfHistorialListNewHistorial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfilEjercicio.getPerfilPwaCedula();
                if (findPerfilEjercicio(id) == null) {
                    throw new NonexistentEntityException("The perfilEjercicio with id " + id + " no longer exists.");
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
            PerfilEjercicio perfilEjercicio;
            try {
                perfilEjercicio = em.getReference(PerfilEjercicio.class, id);
                perfilEjercicio.getPerfilPwaCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilEjercicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Horario> horarioListOrphanCheck = perfilEjercicio.getHorarioList();
            for (Horario horarioListOrphanCheckHorario : horarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilEjercicio (" + perfilEjercicio + ") cannot be destroyed since the Horario " + horarioListOrphanCheckHorario + " in its horarioList field has a non-nullable cedula field.");
            }
            List<Historial> historialListOrphanCheck = perfilEjercicio.getHistorialList();
            for (Historial historialListOrphanCheckHistorial : historialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilEjercicio (" + perfilEjercicio + ") cannot be destroyed since the Historial " + historialListOrphanCheckHistorial + " in its historialList field has a non-nullable pwaCedula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PerfilPwa perfilPwa = perfilEjercicio.getPerfilPwa();
            if (perfilPwa != null) {
                perfilPwa.setPerfilEjercicio(null);
                perfilPwa = em.merge(perfilPwa);
            }
            ProgramaEjercicio nombrePrograma = perfilEjercicio.getNombrePrograma();
            if (nombrePrograma != null) {
                nombrePrograma.getPerfilEjercicioList().remove(perfilEjercicio);
                nombrePrograma = em.merge(nombrePrograma);
            }
            List<Ejercicio> ejercicioList = perfilEjercicio.getEjercicioList();
            for (Ejercicio ejercicioListEjercicio : ejercicioList) {
                ejercicioListEjercicio.getPerfilEjercicioList().remove(perfilEjercicio);
                ejercicioListEjercicio = em.merge(ejercicioListEjercicio);
            }
            em.remove(perfilEjercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilEjercicio> findPerfilEjercicioEntities() {
        return findPerfilEjercicioEntities(true, -1, -1);
    }

    public List<PerfilEjercicio> findPerfilEjercicioEntities(int maxResults, int firstResult) {
        return findPerfilEjercicioEntities(false, maxResults, firstResult);
    }

    private List<PerfilEjercicio> findPerfilEjercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilEjercicio.class));
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

    public PerfilEjercicio findPerfilEjercicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilEjercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilEjercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilEjercicio> rt = cq.from(PerfilEjercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
