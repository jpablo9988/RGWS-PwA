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
import ResPwAEntities.Semana;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.PreferenciaXRutina;
import ResPwAEntities.Ejercicio;
import ResPwAEntities.Frase;
import ResPwAEntities.Rutina;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class RutinaJpaController implements Serializable {

    public RutinaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rutina rutina) throws PreexistingEntityException, Exception {
        if (rutina.getSemanaList() == null) {
            rutina.setSemanaList(new ArrayList<Semana>());
        }
        if (rutina.getPreferenciaXRutinaList() == null) {
            rutina.setPreferenciaXRutinaList(new ArrayList<PreferenciaXRutina>());
        }
        if (rutina.getEjercicioList() == null) {
            rutina.setEjercicioList(new ArrayList<Ejercicio>());
        }
        if (rutina.getFraseList() == null) {
            rutina.setFraseList(new ArrayList<Frase>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Semana> attachedSemanaList = new ArrayList<Semana>();
            for (Semana semanaListSemanaToAttach : rutina.getSemanaList()) {
                semanaListSemanaToAttach = em.getReference(semanaListSemanaToAttach.getClass(), semanaListSemanaToAttach.getId());
                attachedSemanaList.add(semanaListSemanaToAttach);
            }
            rutina.setSemanaList(attachedSemanaList);
            List<PreferenciaXRutina> attachedPreferenciaXRutinaList = new ArrayList<PreferenciaXRutina>();
            for (PreferenciaXRutina preferenciaXRutinaListPreferenciaXRutinaToAttach : rutina.getPreferenciaXRutinaList()) {
                preferenciaXRutinaListPreferenciaXRutinaToAttach = em.getReference(preferenciaXRutinaListPreferenciaXRutinaToAttach.getClass(), preferenciaXRutinaListPreferenciaXRutinaToAttach.getPreferenciaXRutinaPK());
                attachedPreferenciaXRutinaList.add(preferenciaXRutinaListPreferenciaXRutinaToAttach);
            }
            rutina.setPreferenciaXRutinaList(attachedPreferenciaXRutinaList);
            List<Ejercicio> attachedEjercicioList = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListEjercicioToAttach : rutina.getEjercicioList()) {
                ejercicioListEjercicioToAttach = em.getReference(ejercicioListEjercicioToAttach.getClass(), ejercicioListEjercicioToAttach.getEjercicioPK());
                attachedEjercicioList.add(ejercicioListEjercicioToAttach);
            }
            rutina.setEjercicioList(attachedEjercicioList);
            List<Frase> attachedFraseList = new ArrayList<Frase>();
            for (Frase fraseListFraseToAttach : rutina.getFraseList()) {
                fraseListFraseToAttach = em.getReference(fraseListFraseToAttach.getClass(), fraseListFraseToAttach.getFrasePK());
                attachedFraseList.add(fraseListFraseToAttach);
            }
            rutina.setFraseList(attachedFraseList);
            em.persist(rutina);
            for (Semana semanaListSemana : rutina.getSemanaList()) {
                semanaListSemana.getRutinaList().add(rutina);
                semanaListSemana = em.merge(semanaListSemana);
            }
            for (PreferenciaXRutina preferenciaXRutinaListPreferenciaXRutina : rutina.getPreferenciaXRutinaList()) {
                Rutina oldRutinaOfPreferenciaXRutinaListPreferenciaXRutina = preferenciaXRutinaListPreferenciaXRutina.getRutina();
                preferenciaXRutinaListPreferenciaXRutina.setRutina(rutina);
                preferenciaXRutinaListPreferenciaXRutina = em.merge(preferenciaXRutinaListPreferenciaXRutina);
                if (oldRutinaOfPreferenciaXRutinaListPreferenciaXRutina != null) {
                    oldRutinaOfPreferenciaXRutinaListPreferenciaXRutina.getPreferenciaXRutinaList().remove(preferenciaXRutinaListPreferenciaXRutina);
                    oldRutinaOfPreferenciaXRutinaListPreferenciaXRutina = em.merge(oldRutinaOfPreferenciaXRutinaListPreferenciaXRutina);
                }
            }
            for (Ejercicio ejercicioListEjercicio : rutina.getEjercicioList()) {
                Rutina oldRutinaOfEjercicioListEjercicio = ejercicioListEjercicio.getRutina();
                ejercicioListEjercicio.setRutina(rutina);
                ejercicioListEjercicio = em.merge(ejercicioListEjercicio);
                if (oldRutinaOfEjercicioListEjercicio != null) {
                    oldRutinaOfEjercicioListEjercicio.getEjercicioList().remove(ejercicioListEjercicio);
                    oldRutinaOfEjercicioListEjercicio = em.merge(oldRutinaOfEjercicioListEjercicio);
                }
            }
            for (Frase fraseListFrase : rutina.getFraseList()) {
                Rutina oldRutinaIdOfFraseListFrase = fraseListFrase.getRutinaId();
                fraseListFrase.setRutinaId(rutina);
                fraseListFrase = em.merge(fraseListFrase);
                if (oldRutinaIdOfFraseListFrase != null) {
                    oldRutinaIdOfFraseListFrase.getFraseList().remove(fraseListFrase);
                    oldRutinaIdOfFraseListFrase = em.merge(oldRutinaIdOfFraseListFrase);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRutina(rutina.getId()) != null) {
                throw new PreexistingEntityException("Rutina " + rutina + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rutina rutina) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rutina persistentRutina = em.find(Rutina.class, rutina.getId());
            List<Semana> semanaListOld = persistentRutina.getSemanaList();
            List<Semana> semanaListNew = rutina.getSemanaList();
            List<PreferenciaXRutina> preferenciaXRutinaListOld = persistentRutina.getPreferenciaXRutinaList();
            List<PreferenciaXRutina> preferenciaXRutinaListNew = rutina.getPreferenciaXRutinaList();
            List<Ejercicio> ejercicioListOld = persistentRutina.getEjercicioList();
            List<Ejercicio> ejercicioListNew = rutina.getEjercicioList();
            List<Frase> fraseListOld = persistentRutina.getFraseList();
            List<Frase> fraseListNew = rutina.getFraseList();
            List<String> illegalOrphanMessages = null;
            for (PreferenciaXRutina preferenciaXRutinaListOldPreferenciaXRutina : preferenciaXRutinaListOld) {
                if (!preferenciaXRutinaListNew.contains(preferenciaXRutinaListOldPreferenciaXRutina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXRutina " + preferenciaXRutinaListOldPreferenciaXRutina + " since its rutina field is not nullable.");
                }
            }
            for (Ejercicio ejercicioListOldEjercicio : ejercicioListOld) {
                if (!ejercicioListNew.contains(ejercicioListOldEjercicio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ejercicio " + ejercicioListOldEjercicio + " since its rutina field is not nullable.");
                }
            }
            for (Frase fraseListOldFrase : fraseListOld) {
                if (!fraseListNew.contains(fraseListOldFrase)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Frase " + fraseListOldFrase + " since its rutinaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Semana> attachedSemanaListNew = new ArrayList<Semana>();
            for (Semana semanaListNewSemanaToAttach : semanaListNew) {
                semanaListNewSemanaToAttach = em.getReference(semanaListNewSemanaToAttach.getClass(), semanaListNewSemanaToAttach.getId());
                attachedSemanaListNew.add(semanaListNewSemanaToAttach);
            }
            semanaListNew = attachedSemanaListNew;
            rutina.setSemanaList(semanaListNew);
            List<PreferenciaXRutina> attachedPreferenciaXRutinaListNew = new ArrayList<PreferenciaXRutina>();
            for (PreferenciaXRutina preferenciaXRutinaListNewPreferenciaXRutinaToAttach : preferenciaXRutinaListNew) {
                preferenciaXRutinaListNewPreferenciaXRutinaToAttach = em.getReference(preferenciaXRutinaListNewPreferenciaXRutinaToAttach.getClass(), preferenciaXRutinaListNewPreferenciaXRutinaToAttach.getPreferenciaXRutinaPK());
                attachedPreferenciaXRutinaListNew.add(preferenciaXRutinaListNewPreferenciaXRutinaToAttach);
            }
            preferenciaXRutinaListNew = attachedPreferenciaXRutinaListNew;
            rutina.setPreferenciaXRutinaList(preferenciaXRutinaListNew);
            List<Ejercicio> attachedEjercicioListNew = new ArrayList<Ejercicio>();
            for (Ejercicio ejercicioListNewEjercicioToAttach : ejercicioListNew) {
                ejercicioListNewEjercicioToAttach = em.getReference(ejercicioListNewEjercicioToAttach.getClass(), ejercicioListNewEjercicioToAttach.getEjercicioPK());
                attachedEjercicioListNew.add(ejercicioListNewEjercicioToAttach);
            }
            ejercicioListNew = attachedEjercicioListNew;
            rutina.setEjercicioList(ejercicioListNew);
            List<Frase> attachedFraseListNew = new ArrayList<Frase>();
            for (Frase fraseListNewFraseToAttach : fraseListNew) {
                fraseListNewFraseToAttach = em.getReference(fraseListNewFraseToAttach.getClass(), fraseListNewFraseToAttach.getFrasePK());
                attachedFraseListNew.add(fraseListNewFraseToAttach);
            }
            fraseListNew = attachedFraseListNew;
            rutina.setFraseList(fraseListNew);
            rutina = em.merge(rutina);
            for (Semana semanaListOldSemana : semanaListOld) {
                if (!semanaListNew.contains(semanaListOldSemana)) {
                    semanaListOldSemana.getRutinaList().remove(rutina);
                    semanaListOldSemana = em.merge(semanaListOldSemana);
                }
            }
            for (Semana semanaListNewSemana : semanaListNew) {
                if (!semanaListOld.contains(semanaListNewSemana)) {
                    semanaListNewSemana.getRutinaList().add(rutina);
                    semanaListNewSemana = em.merge(semanaListNewSemana);
                }
            }
            for (PreferenciaXRutina preferenciaXRutinaListNewPreferenciaXRutina : preferenciaXRutinaListNew) {
                if (!preferenciaXRutinaListOld.contains(preferenciaXRutinaListNewPreferenciaXRutina)) {
                    Rutina oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina = preferenciaXRutinaListNewPreferenciaXRutina.getRutina();
                    preferenciaXRutinaListNewPreferenciaXRutina.setRutina(rutina);
                    preferenciaXRutinaListNewPreferenciaXRutina = em.merge(preferenciaXRutinaListNewPreferenciaXRutina);
                    if (oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina != null && !oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina.equals(rutina)) {
                        oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina.getPreferenciaXRutinaList().remove(preferenciaXRutinaListNewPreferenciaXRutina);
                        oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina = em.merge(oldRutinaOfPreferenciaXRutinaListNewPreferenciaXRutina);
                    }
                }
            }
            for (Ejercicio ejercicioListNewEjercicio : ejercicioListNew) {
                if (!ejercicioListOld.contains(ejercicioListNewEjercicio)) {
                    Rutina oldRutinaOfEjercicioListNewEjercicio = ejercicioListNewEjercicio.getRutina();
                    ejercicioListNewEjercicio.setRutina(rutina);
                    ejercicioListNewEjercicio = em.merge(ejercicioListNewEjercicio);
                    if (oldRutinaOfEjercicioListNewEjercicio != null && !oldRutinaOfEjercicioListNewEjercicio.equals(rutina)) {
                        oldRutinaOfEjercicioListNewEjercicio.getEjercicioList().remove(ejercicioListNewEjercicio);
                        oldRutinaOfEjercicioListNewEjercicio = em.merge(oldRutinaOfEjercicioListNewEjercicio);
                    }
                }
            }
            for (Frase fraseListNewFrase : fraseListNew) {
                if (!fraseListOld.contains(fraseListNewFrase)) {
                    Rutina oldRutinaIdOfFraseListNewFrase = fraseListNewFrase.getRutinaId();
                    fraseListNewFrase.setRutinaId(rutina);
                    fraseListNewFrase = em.merge(fraseListNewFrase);
                    if (oldRutinaIdOfFraseListNewFrase != null && !oldRutinaIdOfFraseListNewFrase.equals(rutina)) {
                        oldRutinaIdOfFraseListNewFrase.getFraseList().remove(fraseListNewFrase);
                        oldRutinaIdOfFraseListNewFrase = em.merge(oldRutinaIdOfFraseListNewFrase);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = rutina.getId();
                if (findRutina(id) == null) {
                    throw new NonexistentEntityException("The rutina with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rutina rutina;
            try {
                rutina = em.getReference(Rutina.class, id);
                rutina.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rutina with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreferenciaXRutina> preferenciaXRutinaListOrphanCheck = rutina.getPreferenciaXRutinaList();
            for (PreferenciaXRutina preferenciaXRutinaListOrphanCheckPreferenciaXRutina : preferenciaXRutinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rutina (" + rutina + ") cannot be destroyed since the PreferenciaXRutina " + preferenciaXRutinaListOrphanCheckPreferenciaXRutina + " in its preferenciaXRutinaList field has a non-nullable rutina field.");
            }
            List<Ejercicio> ejercicioListOrphanCheck = rutina.getEjercicioList();
            for (Ejercicio ejercicioListOrphanCheckEjercicio : ejercicioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rutina (" + rutina + ") cannot be destroyed since the Ejercicio " + ejercicioListOrphanCheckEjercicio + " in its ejercicioList field has a non-nullable rutina field.");
            }
            List<Frase> fraseListOrphanCheck = rutina.getFraseList();
            for (Frase fraseListOrphanCheckFrase : fraseListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rutina (" + rutina + ") cannot be destroyed since the Frase " + fraseListOrphanCheckFrase + " in its fraseList field has a non-nullable rutinaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Semana> semanaList = rutina.getSemanaList();
            for (Semana semanaListSemana : semanaList) {
                semanaListSemana.getRutinaList().remove(rutina);
                semanaListSemana = em.merge(semanaListSemana);
            }
            em.remove(rutina);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rutina> findRutinaEntities() {
        return findRutinaEntities(true, -1, -1);
    }

    public List<Rutina> findRutinaEntities(int maxResults, int firstResult) {
        return findRutinaEntities(false, maxResults, firstResult);
    }

    private List<Rutina> findRutinaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rutina.class));
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

    public Rutina findRutina(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rutina.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutinaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rutina> rt = cq.from(Rutina.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
