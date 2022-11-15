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
import ResPwAEntities.CausaDemencia;
import ResPwAEntities.PerfilPwa;
import ResPwAEntities.Cdr;
import ResPwAEntities.ActividadRutinaria;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilMedico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class PerfilMedicoJpaController implements Serializable {

    public PerfilMedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilMedico perfilMedico) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (perfilMedico.getActividadRutinariaList() == null) {
            perfilMedico.setActividadRutinariaList(new ArrayList<ActividadRutinaria>());
        }
        List<String> illegalOrphanMessages = null;
        PerfilPwa perfilPwaOrphanCheck = perfilMedico.getPerfilPwa();
        if (perfilPwaOrphanCheck != null) {
            PerfilMedico oldPerfilMedicoOfPerfilPwa = perfilPwaOrphanCheck.getPerfilMedico();
            if (oldPerfilMedicoOfPerfilPwa != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PerfilPwa " + perfilPwaOrphanCheck + " already has an item of type PerfilMedico whose perfilPwa column cannot be null. Please make another selection for the perfilPwa field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CausaDemencia causaDemenciaCondicion = perfilMedico.getCausaDemenciaCondicion();
            if (causaDemenciaCondicion != null) {
                causaDemenciaCondicion = em.getReference(causaDemenciaCondicion.getClass(), causaDemenciaCondicion.getCondicion());
                perfilMedico.setCausaDemenciaCondicion(causaDemenciaCondicion);
            }
            PerfilPwa perfilPwa = perfilMedico.getPerfilPwa();
            if (perfilPwa != null) {
                perfilPwa = em.getReference(perfilPwa.getClass(), perfilPwa.getCedula());
                perfilMedico.setPerfilPwa(perfilPwa);
            }
            Cdr cdr = perfilMedico.getCdr();
            if (cdr != null) {
                cdr = em.getReference(cdr.getClass(), cdr.getMedicoPwaCedula());
                perfilMedico.setCdr(cdr);
            }
            List<ActividadRutinaria> attachedActividadRutinariaList = new ArrayList<ActividadRutinaria>();
            for (ActividadRutinaria actividadRutinariaListActividadRutinariaToAttach : perfilMedico.getActividadRutinariaList()) {
                actividadRutinariaListActividadRutinariaToAttach = em.getReference(actividadRutinariaListActividadRutinariaToAttach.getClass(), actividadRutinariaListActividadRutinariaToAttach.getId());
                attachedActividadRutinariaList.add(actividadRutinariaListActividadRutinariaToAttach);
            }
            perfilMedico.setActividadRutinariaList(attachedActividadRutinariaList);
            em.persist(perfilMedico);
            if (causaDemenciaCondicion != null) {
                causaDemenciaCondicion.getPerfilMedicoList().add(perfilMedico);
                causaDemenciaCondicion = em.merge(causaDemenciaCondicion);
            }
            if (perfilPwa != null) {
                perfilPwa.setPerfilMedico(perfilMedico);
                perfilPwa = em.merge(perfilPwa);
            }
            if (cdr != null) {
                PerfilMedico oldPerfilMedicoOfCdr = cdr.getPerfilMedico();
                if (oldPerfilMedicoOfCdr != null) {
                    oldPerfilMedicoOfCdr.setCdr(null);
                    oldPerfilMedicoOfCdr = em.merge(oldPerfilMedicoOfCdr);
                }
                cdr.setPerfilMedico(perfilMedico);
                cdr = em.merge(cdr);
            }
            for (ActividadRutinaria actividadRutinariaListActividadRutinaria : perfilMedico.getActividadRutinariaList()) {
                PerfilMedico oldMedicoPwaCedulaOfActividadRutinariaListActividadRutinaria = actividadRutinariaListActividadRutinaria.getMedicoPwaCedula();
                actividadRutinariaListActividadRutinaria.setMedicoPwaCedula(perfilMedico);
                actividadRutinariaListActividadRutinaria = em.merge(actividadRutinariaListActividadRutinaria);
                if (oldMedicoPwaCedulaOfActividadRutinariaListActividadRutinaria != null) {
                    oldMedicoPwaCedulaOfActividadRutinariaListActividadRutinaria.getActividadRutinariaList().remove(actividadRutinariaListActividadRutinaria);
                    oldMedicoPwaCedulaOfActividadRutinariaListActividadRutinaria = em.merge(oldMedicoPwaCedulaOfActividadRutinariaListActividadRutinaria);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfilMedico(perfilMedico.getPerfilPwaCedula()) != null) {
                throw new PreexistingEntityException("PerfilMedico " + perfilMedico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilMedico perfilMedico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilMedico persistentPerfilMedico = em.find(PerfilMedico.class, perfilMedico.getPerfilPwaCedula());
            CausaDemencia causaDemenciaCondicionOld = persistentPerfilMedico.getCausaDemenciaCondicion();
            CausaDemencia causaDemenciaCondicionNew = perfilMedico.getCausaDemenciaCondicion();
            PerfilPwa perfilPwaOld = persistentPerfilMedico.getPerfilPwa();
            PerfilPwa perfilPwaNew = perfilMedico.getPerfilPwa();
            Cdr cdrOld = persistentPerfilMedico.getCdr();
            Cdr cdrNew = perfilMedico.getCdr();
            List<ActividadRutinaria> actividadRutinariaListOld = persistentPerfilMedico.getActividadRutinariaList();
            List<ActividadRutinaria> actividadRutinariaListNew = perfilMedico.getActividadRutinariaList();
            List<String> illegalOrphanMessages = null;
            if (perfilPwaNew != null && !perfilPwaNew.equals(perfilPwaOld)) {
                PerfilMedico oldPerfilMedicoOfPerfilPwa = perfilPwaNew.getPerfilMedico();
                if (oldPerfilMedicoOfPerfilPwa != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PerfilPwa " + perfilPwaNew + " already has an item of type PerfilMedico whose perfilPwa column cannot be null. Please make another selection for the perfilPwa field.");
                }
            }
            if (cdrOld != null && !cdrOld.equals(cdrNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cdr " + cdrOld + " since its perfilMedico field is not nullable.");
            }
            for (ActividadRutinaria actividadRutinariaListOldActividadRutinaria : actividadRutinariaListOld) {
                if (!actividadRutinariaListNew.contains(actividadRutinariaListOldActividadRutinaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActividadRutinaria " + actividadRutinariaListOldActividadRutinaria + " since its medicoPwaCedula field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (causaDemenciaCondicionNew != null) {
                causaDemenciaCondicionNew = em.getReference(causaDemenciaCondicionNew.getClass(), causaDemenciaCondicionNew.getCondicion());
                perfilMedico.setCausaDemenciaCondicion(causaDemenciaCondicionNew);
            }
            if (perfilPwaNew != null) {
                perfilPwaNew = em.getReference(perfilPwaNew.getClass(), perfilPwaNew.getCedula());
                perfilMedico.setPerfilPwa(perfilPwaNew);
            }
            if (cdrNew != null) {
                cdrNew = em.getReference(cdrNew.getClass(), cdrNew.getMedicoPwaCedula());
                perfilMedico.setCdr(cdrNew);
            }
            List<ActividadRutinaria> attachedActividadRutinariaListNew = new ArrayList<ActividadRutinaria>();
            for (ActividadRutinaria actividadRutinariaListNewActividadRutinariaToAttach : actividadRutinariaListNew) {
                actividadRutinariaListNewActividadRutinariaToAttach = em.getReference(actividadRutinariaListNewActividadRutinariaToAttach.getClass(), actividadRutinariaListNewActividadRutinariaToAttach.getId());
                attachedActividadRutinariaListNew.add(actividadRutinariaListNewActividadRutinariaToAttach);
            }
            actividadRutinariaListNew = attachedActividadRutinariaListNew;
            perfilMedico.setActividadRutinariaList(actividadRutinariaListNew);
            perfilMedico = em.merge(perfilMedico);
            if (causaDemenciaCondicionOld != null && !causaDemenciaCondicionOld.equals(causaDemenciaCondicionNew)) {
                causaDemenciaCondicionOld.getPerfilMedicoList().remove(perfilMedico);
                causaDemenciaCondicionOld = em.merge(causaDemenciaCondicionOld);
            }
            if (causaDemenciaCondicionNew != null && !causaDemenciaCondicionNew.equals(causaDemenciaCondicionOld)) {
                causaDemenciaCondicionNew.getPerfilMedicoList().add(perfilMedico);
                causaDemenciaCondicionNew = em.merge(causaDemenciaCondicionNew);
            }
            if (perfilPwaOld != null && !perfilPwaOld.equals(perfilPwaNew)) {
                perfilPwaOld.setPerfilMedico(null);
                perfilPwaOld = em.merge(perfilPwaOld);
            }
            if (perfilPwaNew != null && !perfilPwaNew.equals(perfilPwaOld)) {
                perfilPwaNew.setPerfilMedico(perfilMedico);
                perfilPwaNew = em.merge(perfilPwaNew);
            }
            if (cdrNew != null && !cdrNew.equals(cdrOld)) {
                PerfilMedico oldPerfilMedicoOfCdr = cdrNew.getPerfilMedico();
                if (oldPerfilMedicoOfCdr != null) {
                    oldPerfilMedicoOfCdr.setCdr(null);
                    oldPerfilMedicoOfCdr = em.merge(oldPerfilMedicoOfCdr);
                }
                cdrNew.setPerfilMedico(perfilMedico);
                cdrNew = em.merge(cdrNew);
            }
            for (ActividadRutinaria actividadRutinariaListNewActividadRutinaria : actividadRutinariaListNew) {
                if (!actividadRutinariaListOld.contains(actividadRutinariaListNewActividadRutinaria)) {
                    PerfilMedico oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria = actividadRutinariaListNewActividadRutinaria.getMedicoPwaCedula();
                    actividadRutinariaListNewActividadRutinaria.setMedicoPwaCedula(perfilMedico);
                    actividadRutinariaListNewActividadRutinaria = em.merge(actividadRutinariaListNewActividadRutinaria);
                    if (oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria != null && !oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria.equals(perfilMedico)) {
                        oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria.getActividadRutinariaList().remove(actividadRutinariaListNewActividadRutinaria);
                        oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria = em.merge(oldMedicoPwaCedulaOfActividadRutinariaListNewActividadRutinaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfilMedico.getPerfilPwaCedula();
                if (findPerfilMedico(id) == null) {
                    throw new NonexistentEntityException("The perfilMedico with id " + id + " no longer exists.");
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
            PerfilMedico perfilMedico;
            try {
                perfilMedico = em.getReference(PerfilMedico.class, id);
                perfilMedico.getPerfilPwaCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilMedico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cdr cdrOrphanCheck = perfilMedico.getCdr();
            if (cdrOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilMedico (" + perfilMedico + ") cannot be destroyed since the Cdr " + cdrOrphanCheck + " in its cdr field has a non-nullable perfilMedico field.");
            }
            List<ActividadRutinaria> actividadRutinariaListOrphanCheck = perfilMedico.getActividadRutinariaList();
            for (ActividadRutinaria actividadRutinariaListOrphanCheckActividadRutinaria : actividadRutinariaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilMedico (" + perfilMedico + ") cannot be destroyed since the ActividadRutinaria " + actividadRutinariaListOrphanCheckActividadRutinaria + " in its actividadRutinariaList field has a non-nullable medicoPwaCedula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CausaDemencia causaDemenciaCondicion = perfilMedico.getCausaDemenciaCondicion();
            if (causaDemenciaCondicion != null) {
                causaDemenciaCondicion.getPerfilMedicoList().remove(perfilMedico);
                causaDemenciaCondicion = em.merge(causaDemenciaCondicion);
            }
            PerfilPwa perfilPwa = perfilMedico.getPerfilPwa();
            if (perfilPwa != null) {
                perfilPwa.setPerfilMedico(null);
                perfilPwa = em.merge(perfilPwa);
            }
            em.remove(perfilMedico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilMedico> findPerfilMedicoEntities() {
        return findPerfilMedicoEntities(true, -1, -1);
    }

    public List<PerfilMedico> findPerfilMedicoEntities(int maxResults, int firstResult) {
        return findPerfilMedicoEntities(false, maxResults, firstResult);
    }

    private List<PerfilMedico> findPerfilMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilMedico.class));
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

    public PerfilMedico findPerfilMedico(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilMedico.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilMedico> rt = cq.from(PerfilMedico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
