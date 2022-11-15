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
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PerfilMedico;
import ResPwAEntities.Cuidador;
import ResPwAEntities.EstadoCivil;
import ResPwAEntities.NivelEducativo;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.Familiar;
import ResPwAEntities.PerfilPwa;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.RegistroActividad;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class PerfilPwaJpaController implements Serializable {

    public PerfilPwaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilPwa perfilPwa) throws PreexistingEntityException, Exception {
        if (perfilPwa.getFamiliarList() == null) {
            perfilPwa.setFamiliarList(new ArrayList<Familiar>());
        }
        if (perfilPwa.getRegistroActividadList() == null) {
            perfilPwa.setRegistroActividadList(new ArrayList<RegistroActividad>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPreferencia perfilPreferencia = perfilPwa.getPerfilPreferencia();
            if (perfilPreferencia != null) {
                perfilPreferencia = em.getReference(perfilPreferencia.getClass(), perfilPreferencia.getPerfilPwaCedula());
                perfilPwa.setPerfilPreferencia(perfilPreferencia);
            }
            PerfilMedico perfilMedico = perfilPwa.getPerfilMedico();
            if (perfilMedico != null) {
                perfilMedico = em.getReference(perfilMedico.getClass(), perfilMedico.getPerfilPwaCedula());
                perfilPwa.setPerfilMedico(perfilMedico);
            }
            Cuidador cuidadorNombreUsuario = perfilPwa.getCuidadorNombreUsuario();
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario = em.getReference(cuidadorNombreUsuario.getClass(), cuidadorNombreUsuario.getNombreUsuario());
                perfilPwa.setCuidadorNombreUsuario(cuidadorNombreUsuario);
            }
            EstadoCivil estadoCivilTipoEc = perfilPwa.getEstadoCivilTipoEc();
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc = em.getReference(estadoCivilTipoEc.getClass(), estadoCivilTipoEc.getTipoEc());
                perfilPwa.setEstadoCivilTipoEc(estadoCivilTipoEc);
            }
            NivelEducativo nivelEducativoTipoNe = perfilPwa.getNivelEducativoTipoNe();
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe = em.getReference(nivelEducativoTipoNe.getClass(), nivelEducativoTipoNe.getTipoNe());
                perfilPwa.setNivelEducativoTipoNe(nivelEducativoTipoNe);
            }
            PerfilEjercicio perfilEjercicio = perfilPwa.getPerfilEjercicio();
            if (perfilEjercicio != null) {
                perfilEjercicio = em.getReference(perfilEjercicio.getClass(), perfilEjercicio.getPerfilPwaCedula());
                perfilPwa.setPerfilEjercicio(perfilEjercicio);
            }
            List<Familiar> attachedFamiliarList = new ArrayList<Familiar>();
            for (Familiar familiarListFamiliarToAttach : perfilPwa.getFamiliarList()) {
                familiarListFamiliarToAttach = em.getReference(familiarListFamiliarToAttach.getClass(), familiarListFamiliarToAttach.getId());
                attachedFamiliarList.add(familiarListFamiliarToAttach);
            }
            perfilPwa.setFamiliarList(attachedFamiliarList);
            List<RegistroActividad> attachedRegistroActividadList = new ArrayList<RegistroActividad>();
            for (RegistroActividad registroActividadListRegistroActividadToAttach : perfilPwa.getRegistroActividadList()) {
                registroActividadListRegistroActividadToAttach = em.getReference(registroActividadListRegistroActividadToAttach.getClass(), registroActividadListRegistroActividadToAttach.getRegistroActividadPK());
                attachedRegistroActividadList.add(registroActividadListRegistroActividadToAttach);
            }
            perfilPwa.setRegistroActividadList(attachedRegistroActividadList);
            em.persist(perfilPwa);
            if (perfilPreferencia != null) {
                PerfilPwa oldPerfilPwaOfPerfilPreferencia = perfilPreferencia.getPerfilPwa();
                if (oldPerfilPwaOfPerfilPreferencia != null) {
                    oldPerfilPwaOfPerfilPreferencia.setPerfilPreferencia(null);
                    oldPerfilPwaOfPerfilPreferencia = em.merge(oldPerfilPwaOfPerfilPreferencia);
                }
                perfilPreferencia.setPerfilPwa(perfilPwa);
                perfilPreferencia = em.merge(perfilPreferencia);
            }
            if (perfilMedico != null) {
                PerfilPwa oldPerfilPwaOfPerfilMedico = perfilMedico.getPerfilPwa();
                if (oldPerfilPwaOfPerfilMedico != null) {
                    oldPerfilPwaOfPerfilMedico.setPerfilMedico(null);
                    oldPerfilPwaOfPerfilMedico = em.merge(oldPerfilPwaOfPerfilMedico);
                }
                perfilMedico.setPerfilPwa(perfilPwa);
                perfilMedico = em.merge(perfilMedico);
            }
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario.getPerfilPwaList().add(perfilPwa);
                cuidadorNombreUsuario = em.merge(cuidadorNombreUsuario);
            }
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc.getPerfilPwaList().add(perfilPwa);
                estadoCivilTipoEc = em.merge(estadoCivilTipoEc);
            }
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe.getPerfilPwaList().add(perfilPwa);
                nivelEducativoTipoNe = em.merge(nivelEducativoTipoNe);
            }
            if (perfilEjercicio != null) {
                PerfilPwa oldPerfilPwaOfPerfilEjercicio = perfilEjercicio.getPerfilPwa();
                if (oldPerfilPwaOfPerfilEjercicio != null) {
                    oldPerfilPwaOfPerfilEjercicio.setPerfilEjercicio(null);
                    oldPerfilPwaOfPerfilEjercicio = em.merge(oldPerfilPwaOfPerfilEjercicio);
                }
                perfilEjercicio.setPerfilPwa(perfilPwa);
                perfilEjercicio = em.merge(perfilEjercicio);
            }
            for (Familiar familiarListFamiliar : perfilPwa.getFamiliarList()) {
                familiarListFamiliar.getPerfilPwaList().add(perfilPwa);
                familiarListFamiliar = em.merge(familiarListFamiliar);
            }
            for (RegistroActividad registroActividadListRegistroActividad : perfilPwa.getRegistroActividadList()) {
                PerfilPwa oldPerfilPwaOfRegistroActividadListRegistroActividad = registroActividadListRegistroActividad.getPerfilPwa();
                registroActividadListRegistroActividad.setPerfilPwa(perfilPwa);
                registroActividadListRegistroActividad = em.merge(registroActividadListRegistroActividad);
                if (oldPerfilPwaOfRegistroActividadListRegistroActividad != null) {
                    oldPerfilPwaOfRegistroActividadListRegistroActividad.getRegistroActividadList().remove(registroActividadListRegistroActividad);
                    oldPerfilPwaOfRegistroActividadListRegistroActividad = em.merge(oldPerfilPwaOfRegistroActividadListRegistroActividad);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfilPwa(perfilPwa.getCedula()) != null) {
                throw new PreexistingEntityException("PerfilPwa " + perfilPwa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilPwa perfilPwa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPwa persistentPerfilPwa = em.find(PerfilPwa.class, perfilPwa.getCedula());
            PerfilPreferencia perfilPreferenciaOld = persistentPerfilPwa.getPerfilPreferencia();
            PerfilPreferencia perfilPreferenciaNew = perfilPwa.getPerfilPreferencia();
            PerfilMedico perfilMedicoOld = persistentPerfilPwa.getPerfilMedico();
            PerfilMedico perfilMedicoNew = perfilPwa.getPerfilMedico();
            Cuidador cuidadorNombreUsuarioOld = persistentPerfilPwa.getCuidadorNombreUsuario();
            Cuidador cuidadorNombreUsuarioNew = perfilPwa.getCuidadorNombreUsuario();
            EstadoCivil estadoCivilTipoEcOld = persistentPerfilPwa.getEstadoCivilTipoEc();
            EstadoCivil estadoCivilTipoEcNew = perfilPwa.getEstadoCivilTipoEc();
            NivelEducativo nivelEducativoTipoNeOld = persistentPerfilPwa.getNivelEducativoTipoNe();
            NivelEducativo nivelEducativoTipoNeNew = perfilPwa.getNivelEducativoTipoNe();
            PerfilEjercicio perfilEjercicioOld = persistentPerfilPwa.getPerfilEjercicio();
            PerfilEjercicio perfilEjercicioNew = perfilPwa.getPerfilEjercicio();
            List<Familiar> familiarListOld = persistentPerfilPwa.getFamiliarList();
            List<Familiar> familiarListNew = perfilPwa.getFamiliarList();
            List<RegistroActividad> registroActividadListOld = persistentPerfilPwa.getRegistroActividadList();
            List<RegistroActividad> registroActividadListNew = perfilPwa.getRegistroActividadList();
            List<String> illegalOrphanMessages = null;
            if (perfilPreferenciaOld != null && !perfilPreferenciaOld.equals(perfilPreferenciaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PerfilPreferencia " + perfilPreferenciaOld + " since its perfilPwa field is not nullable.");
            }
            if (perfilMedicoOld != null && !perfilMedicoOld.equals(perfilMedicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PerfilMedico " + perfilMedicoOld + " since its perfilPwa field is not nullable.");
            }
            if (perfilEjercicioOld != null && !perfilEjercicioOld.equals(perfilEjercicioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PerfilEjercicio " + perfilEjercicioOld + " since its perfilPwa field is not nullable.");
            }
            for (RegistroActividad registroActividadListOldRegistroActividad : registroActividadListOld) {
                if (!registroActividadListNew.contains(registroActividadListOldRegistroActividad)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RegistroActividad " + registroActividadListOldRegistroActividad + " since its perfilPwa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (perfilPreferenciaNew != null) {
                perfilPreferenciaNew = em.getReference(perfilPreferenciaNew.getClass(), perfilPreferenciaNew.getPerfilPwaCedula());
                perfilPwa.setPerfilPreferencia(perfilPreferenciaNew);
            }
            if (perfilMedicoNew != null) {
                perfilMedicoNew = em.getReference(perfilMedicoNew.getClass(), perfilMedicoNew.getPerfilPwaCedula());
                perfilPwa.setPerfilMedico(perfilMedicoNew);
            }
            if (cuidadorNombreUsuarioNew != null) {
                cuidadorNombreUsuarioNew = em.getReference(cuidadorNombreUsuarioNew.getClass(), cuidadorNombreUsuarioNew.getNombreUsuario());
                perfilPwa.setCuidadorNombreUsuario(cuidadorNombreUsuarioNew);
            }
            if (estadoCivilTipoEcNew != null) {
                estadoCivilTipoEcNew = em.getReference(estadoCivilTipoEcNew.getClass(), estadoCivilTipoEcNew.getTipoEc());
                perfilPwa.setEstadoCivilTipoEc(estadoCivilTipoEcNew);
            }
            if (nivelEducativoTipoNeNew != null) {
                nivelEducativoTipoNeNew = em.getReference(nivelEducativoTipoNeNew.getClass(), nivelEducativoTipoNeNew.getTipoNe());
                perfilPwa.setNivelEducativoTipoNe(nivelEducativoTipoNeNew);
            }
            if (perfilEjercicioNew != null) {
                perfilEjercicioNew = em.getReference(perfilEjercicioNew.getClass(), perfilEjercicioNew.getPerfilPwaCedula());
                perfilPwa.setPerfilEjercicio(perfilEjercicioNew);
            }
            List<Familiar> attachedFamiliarListNew = new ArrayList<Familiar>();
            for (Familiar familiarListNewFamiliarToAttach : familiarListNew) {
                familiarListNewFamiliarToAttach = em.getReference(familiarListNewFamiliarToAttach.getClass(), familiarListNewFamiliarToAttach.getId());
                attachedFamiliarListNew.add(familiarListNewFamiliarToAttach);
            }
            familiarListNew = attachedFamiliarListNew;
            perfilPwa.setFamiliarList(familiarListNew);
            List<RegistroActividad> attachedRegistroActividadListNew = new ArrayList<RegistroActividad>();
            for (RegistroActividad registroActividadListNewRegistroActividadToAttach : registroActividadListNew) {
                registroActividadListNewRegistroActividadToAttach = em.getReference(registroActividadListNewRegistroActividadToAttach.getClass(), registroActividadListNewRegistroActividadToAttach.getRegistroActividadPK());
                attachedRegistroActividadListNew.add(registroActividadListNewRegistroActividadToAttach);
            }
            registroActividadListNew = attachedRegistroActividadListNew;
            perfilPwa.setRegistroActividadList(registroActividadListNew);
            perfilPwa = em.merge(perfilPwa);
            if (perfilPreferenciaNew != null && !perfilPreferenciaNew.equals(perfilPreferenciaOld)) {
                PerfilPwa oldPerfilPwaOfPerfilPreferencia = perfilPreferenciaNew.getPerfilPwa();
                if (oldPerfilPwaOfPerfilPreferencia != null) {
                    oldPerfilPwaOfPerfilPreferencia.setPerfilPreferencia(null);
                    oldPerfilPwaOfPerfilPreferencia = em.merge(oldPerfilPwaOfPerfilPreferencia);
                }
                perfilPreferenciaNew.setPerfilPwa(perfilPwa);
                perfilPreferenciaNew = em.merge(perfilPreferenciaNew);
            }
            if (perfilMedicoNew != null && !perfilMedicoNew.equals(perfilMedicoOld)) {
                PerfilPwa oldPerfilPwaOfPerfilMedico = perfilMedicoNew.getPerfilPwa();
                if (oldPerfilPwaOfPerfilMedico != null) {
                    oldPerfilPwaOfPerfilMedico.setPerfilMedico(null);
                    oldPerfilPwaOfPerfilMedico = em.merge(oldPerfilPwaOfPerfilMedico);
                }
                perfilMedicoNew.setPerfilPwa(perfilPwa);
                perfilMedicoNew = em.merge(perfilMedicoNew);
            }
            if (cuidadorNombreUsuarioOld != null && !cuidadorNombreUsuarioOld.equals(cuidadorNombreUsuarioNew)) {
                cuidadorNombreUsuarioOld.getPerfilPwaList().remove(perfilPwa);
                cuidadorNombreUsuarioOld = em.merge(cuidadorNombreUsuarioOld);
            }
            if (cuidadorNombreUsuarioNew != null && !cuidadorNombreUsuarioNew.equals(cuidadorNombreUsuarioOld)) {
                cuidadorNombreUsuarioNew.getPerfilPwaList().add(perfilPwa);
                cuidadorNombreUsuarioNew = em.merge(cuidadorNombreUsuarioNew);
            }
            if (estadoCivilTipoEcOld != null && !estadoCivilTipoEcOld.equals(estadoCivilTipoEcNew)) {
                estadoCivilTipoEcOld.getPerfilPwaList().remove(perfilPwa);
                estadoCivilTipoEcOld = em.merge(estadoCivilTipoEcOld);
            }
            if (estadoCivilTipoEcNew != null && !estadoCivilTipoEcNew.equals(estadoCivilTipoEcOld)) {
                estadoCivilTipoEcNew.getPerfilPwaList().add(perfilPwa);
                estadoCivilTipoEcNew = em.merge(estadoCivilTipoEcNew);
            }
            if (nivelEducativoTipoNeOld != null && !nivelEducativoTipoNeOld.equals(nivelEducativoTipoNeNew)) {
                nivelEducativoTipoNeOld.getPerfilPwaList().remove(perfilPwa);
                nivelEducativoTipoNeOld = em.merge(nivelEducativoTipoNeOld);
            }
            if (nivelEducativoTipoNeNew != null && !nivelEducativoTipoNeNew.equals(nivelEducativoTipoNeOld)) {
                nivelEducativoTipoNeNew.getPerfilPwaList().add(perfilPwa);
                nivelEducativoTipoNeNew = em.merge(nivelEducativoTipoNeNew);
            }
            if (perfilEjercicioNew != null && !perfilEjercicioNew.equals(perfilEjercicioOld)) {
                PerfilPwa oldPerfilPwaOfPerfilEjercicio = perfilEjercicioNew.getPerfilPwa();
                if (oldPerfilPwaOfPerfilEjercicio != null) {
                    oldPerfilPwaOfPerfilEjercicio.setPerfilEjercicio(null);
                    oldPerfilPwaOfPerfilEjercicio = em.merge(oldPerfilPwaOfPerfilEjercicio);
                }
                perfilEjercicioNew.setPerfilPwa(perfilPwa);
                perfilEjercicioNew = em.merge(perfilEjercicioNew);
            }
            for (Familiar familiarListOldFamiliar : familiarListOld) {
                if (!familiarListNew.contains(familiarListOldFamiliar)) {
                    familiarListOldFamiliar.getPerfilPwaList().remove(perfilPwa);
                    familiarListOldFamiliar = em.merge(familiarListOldFamiliar);
                }
            }
            for (Familiar familiarListNewFamiliar : familiarListNew) {
                if (!familiarListOld.contains(familiarListNewFamiliar)) {
                    familiarListNewFamiliar.getPerfilPwaList().add(perfilPwa);
                    familiarListNewFamiliar = em.merge(familiarListNewFamiliar);
                }
            }
            for (RegistroActividad registroActividadListNewRegistroActividad : registroActividadListNew) {
                if (!registroActividadListOld.contains(registroActividadListNewRegistroActividad)) {
                    PerfilPwa oldPerfilPwaOfRegistroActividadListNewRegistroActividad = registroActividadListNewRegistroActividad.getPerfilPwa();
                    registroActividadListNewRegistroActividad.setPerfilPwa(perfilPwa);
                    registroActividadListNewRegistroActividad = em.merge(registroActividadListNewRegistroActividad);
                    if (oldPerfilPwaOfRegistroActividadListNewRegistroActividad != null && !oldPerfilPwaOfRegistroActividadListNewRegistroActividad.equals(perfilPwa)) {
                        oldPerfilPwaOfRegistroActividadListNewRegistroActividad.getRegistroActividadList().remove(registroActividadListNewRegistroActividad);
                        oldPerfilPwaOfRegistroActividadListNewRegistroActividad = em.merge(oldPerfilPwaOfRegistroActividadListNewRegistroActividad);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfilPwa.getCedula();
                if (findPerfilPwa(id) == null) {
                    throw new NonexistentEntityException("The perfilPwa with id " + id + " no longer exists.");
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
            PerfilPwa perfilPwa;
            try {
                perfilPwa = em.getReference(PerfilPwa.class, id);
                perfilPwa.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilPwa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            PerfilPreferencia perfilPreferenciaOrphanCheck = perfilPwa.getPerfilPreferencia();
            if (perfilPreferenciaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPwa (" + perfilPwa + ") cannot be destroyed since the PerfilPreferencia " + perfilPreferenciaOrphanCheck + " in its perfilPreferencia field has a non-nullable perfilPwa field.");
            }
            PerfilMedico perfilMedicoOrphanCheck = perfilPwa.getPerfilMedico();
            if (perfilMedicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPwa (" + perfilPwa + ") cannot be destroyed since the PerfilMedico " + perfilMedicoOrphanCheck + " in its perfilMedico field has a non-nullable perfilPwa field.");
            }
            PerfilEjercicio perfilEjercicioOrphanCheck = perfilPwa.getPerfilEjercicio();
            if (perfilEjercicioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPwa (" + perfilPwa + ") cannot be destroyed since the PerfilEjercicio " + perfilEjercicioOrphanCheck + " in its perfilEjercicio field has a non-nullable perfilPwa field.");
            }
            List<RegistroActividad> registroActividadListOrphanCheck = perfilPwa.getRegistroActividadList();
            for (RegistroActividad registroActividadListOrphanCheckRegistroActividad : registroActividadListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPwa (" + perfilPwa + ") cannot be destroyed since the RegistroActividad " + registroActividadListOrphanCheckRegistroActividad + " in its registroActividadList field has a non-nullable perfilPwa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuidador cuidadorNombreUsuario = perfilPwa.getCuidadorNombreUsuario();
            if (cuidadorNombreUsuario != null) {
                cuidadorNombreUsuario.getPerfilPwaList().remove(perfilPwa);
                cuidadorNombreUsuario = em.merge(cuidadorNombreUsuario);
            }
            EstadoCivil estadoCivilTipoEc = perfilPwa.getEstadoCivilTipoEc();
            if (estadoCivilTipoEc != null) {
                estadoCivilTipoEc.getPerfilPwaList().remove(perfilPwa);
                estadoCivilTipoEc = em.merge(estadoCivilTipoEc);
            }
            NivelEducativo nivelEducativoTipoNe = perfilPwa.getNivelEducativoTipoNe();
            if (nivelEducativoTipoNe != null) {
                nivelEducativoTipoNe.getPerfilPwaList().remove(perfilPwa);
                nivelEducativoTipoNe = em.merge(nivelEducativoTipoNe);
            }
            List<Familiar> familiarList = perfilPwa.getFamiliarList();
            for (Familiar familiarListFamiliar : familiarList) {
                familiarListFamiliar.getPerfilPwaList().remove(perfilPwa);
                familiarListFamiliar = em.merge(familiarListFamiliar);
            }
            em.remove(perfilPwa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilPwa> findPerfilPwaEntities() {
        return findPerfilPwaEntities(true, -1, -1);
    }

    public List<PerfilPwa> findPerfilPwaEntities(int maxResults, int firstResult) {
        return findPerfilPwaEntities(false, maxResults, firstResult);
    }

    private List<PerfilPwa> findPerfilPwaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilPwa.class));
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

    public PerfilPwa findPerfilPwa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilPwa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilPwaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilPwa> rt = cq.from(PerfilPwa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
