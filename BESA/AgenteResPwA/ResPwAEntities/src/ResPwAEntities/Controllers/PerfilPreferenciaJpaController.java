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
import ResPwAEntities.PreferenciaXCuento;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.PreferenciaXRutina;
import ResPwAEntities.ActXPreferencia;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.PerfilPreferencia;
import ResPwAEntities.PreferenciaXBaile;
import ResPwAEntities.PreferenciaXCancion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PerfilPreferenciaJpaController implements Serializable {

    public PerfilPreferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PerfilPreferencia perfilPreferencia) throws PreexistingEntityException, Exception {
        if (perfilPreferencia.getPreferenciaXCuentoList() == null) {
            perfilPreferencia.setPreferenciaXCuentoList(new ArrayList<PreferenciaXCuento>());
        }
        if (perfilPreferencia.getPreferenciaXRutinaList() == null) {
            perfilPreferencia.setPreferenciaXRutinaList(new ArrayList<PreferenciaXRutina>());
        }
        if (perfilPreferencia.getActXPreferenciaList() == null) {
            perfilPreferencia.setActXPreferenciaList(new ArrayList<ActXPreferencia>());
        }
        if (perfilPreferencia.getPreferenciaXBaileList() == null) {
            perfilPreferencia.setPreferenciaXBaileList(new ArrayList<PreferenciaXBaile>());
        }
        if (perfilPreferencia.getPreferenciaXCancionList() == null) {
            perfilPreferencia.setPreferenciaXCancionList(new ArrayList<PreferenciaXCancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreferenciaXCuento> attachedPreferenciaXCuentoList = new ArrayList<PreferenciaXCuento>();
            for (PreferenciaXCuento preferenciaXCuentoListPreferenciaXCuentoToAttach : perfilPreferencia.getPreferenciaXCuentoList()) {
                preferenciaXCuentoListPreferenciaXCuentoToAttach = em.getReference(preferenciaXCuentoListPreferenciaXCuentoToAttach.getClass(), preferenciaXCuentoListPreferenciaXCuentoToAttach.getPreferenciaXCuentoPK());
                attachedPreferenciaXCuentoList.add(preferenciaXCuentoListPreferenciaXCuentoToAttach);
            }
            perfilPreferencia.setPreferenciaXCuentoList(attachedPreferenciaXCuentoList);
            List<PreferenciaXRutina> attachedPreferenciaXRutinaList = new ArrayList<PreferenciaXRutina>();
            for (PreferenciaXRutina preferenciaXRutinaListPreferenciaXRutinaToAttach : perfilPreferencia.getPreferenciaXRutinaList()) {
                preferenciaXRutinaListPreferenciaXRutinaToAttach = em.getReference(preferenciaXRutinaListPreferenciaXRutinaToAttach.getClass(), preferenciaXRutinaListPreferenciaXRutinaToAttach.getPreferenciaXRutinaPK());
                attachedPreferenciaXRutinaList.add(preferenciaXRutinaListPreferenciaXRutinaToAttach);
            }
            perfilPreferencia.setPreferenciaXRutinaList(attachedPreferenciaXRutinaList);
            List<ActXPreferencia> attachedActXPreferenciaList = new ArrayList<ActXPreferencia>();
            for (ActXPreferencia actXPreferenciaListActXPreferenciaToAttach : perfilPreferencia.getActXPreferenciaList()) {
                actXPreferenciaListActXPreferenciaToAttach = em.getReference(actXPreferenciaListActXPreferenciaToAttach.getClass(), actXPreferenciaListActXPreferenciaToAttach.getActXPreferenciaPK());
                attachedActXPreferenciaList.add(actXPreferenciaListActXPreferenciaToAttach);
            }
            perfilPreferencia.setActXPreferenciaList(attachedActXPreferenciaList);
            List<PreferenciaXBaile> attachedPreferenciaXBaileList = new ArrayList<PreferenciaXBaile>();
            for (PreferenciaXBaile preferenciaXBaileListPreferenciaXBaileToAttach : perfilPreferencia.getPreferenciaXBaileList()) {
                preferenciaXBaileListPreferenciaXBaileToAttach = em.getReference(preferenciaXBaileListPreferenciaXBaileToAttach.getClass(), preferenciaXBaileListPreferenciaXBaileToAttach.getPreferenciaXBailePK());
                attachedPreferenciaXBaileList.add(preferenciaXBaileListPreferenciaXBaileToAttach);
            }
            perfilPreferencia.setPreferenciaXBaileList(attachedPreferenciaXBaileList);
            List<PreferenciaXCancion> attachedPreferenciaXCancionList = new ArrayList<PreferenciaXCancion>();
            for (PreferenciaXCancion preferenciaXCancionListPreferenciaXCancionToAttach : perfilPreferencia.getPreferenciaXCancionList()) {
                preferenciaXCancionListPreferenciaXCancionToAttach = em.getReference(preferenciaXCancionListPreferenciaXCancionToAttach.getClass(), preferenciaXCancionListPreferenciaXCancionToAttach.getPreferenciaXCancionPK());
                attachedPreferenciaXCancionList.add(preferenciaXCancionListPreferenciaXCancionToAttach);
            }
            perfilPreferencia.setPreferenciaXCancionList(attachedPreferenciaXCancionList);
            em.persist(perfilPreferencia);
            for (PreferenciaXCuento preferenciaXCuentoListPreferenciaXCuento : perfilPreferencia.getPreferenciaXCuentoList()) {
                PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXCuentoListPreferenciaXCuento = preferenciaXCuentoListPreferenciaXCuento.getPerfilPreferencia();
                preferenciaXCuentoListPreferenciaXCuento.setPerfilPreferencia(perfilPreferencia);
                preferenciaXCuentoListPreferenciaXCuento = em.merge(preferenciaXCuentoListPreferenciaXCuento);
                if (oldPerfilPreferenciaOfPreferenciaXCuentoListPreferenciaXCuento != null) {
                    oldPerfilPreferenciaOfPreferenciaXCuentoListPreferenciaXCuento.getPreferenciaXCuentoList().remove(preferenciaXCuentoListPreferenciaXCuento);
                    oldPerfilPreferenciaOfPreferenciaXCuentoListPreferenciaXCuento = em.merge(oldPerfilPreferenciaOfPreferenciaXCuentoListPreferenciaXCuento);
                }
            }
            for (PreferenciaXRutina preferenciaXRutinaListPreferenciaXRutina : perfilPreferencia.getPreferenciaXRutinaList()) {
                PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXRutinaListPreferenciaXRutina = preferenciaXRutinaListPreferenciaXRutina.getPerfilPreferencia();
                preferenciaXRutinaListPreferenciaXRutina.setPerfilPreferencia(perfilPreferencia);
                preferenciaXRutinaListPreferenciaXRutina = em.merge(preferenciaXRutinaListPreferenciaXRutina);
                if (oldPerfilPreferenciaOfPreferenciaXRutinaListPreferenciaXRutina != null) {
                    oldPerfilPreferenciaOfPreferenciaXRutinaListPreferenciaXRutina.getPreferenciaXRutinaList().remove(preferenciaXRutinaListPreferenciaXRutina);
                    oldPerfilPreferenciaOfPreferenciaXRutinaListPreferenciaXRutina = em.merge(oldPerfilPreferenciaOfPreferenciaXRutinaListPreferenciaXRutina);
                }
            }
            for (ActXPreferencia actXPreferenciaListActXPreferencia : perfilPreferencia.getActXPreferenciaList()) {
                PerfilPreferencia oldPerfilPreferenciaOfActXPreferenciaListActXPreferencia = actXPreferenciaListActXPreferencia.getPerfilPreferencia();
                actXPreferenciaListActXPreferencia.setPerfilPreferencia(perfilPreferencia);
                actXPreferenciaListActXPreferencia = em.merge(actXPreferenciaListActXPreferencia);
                if (oldPerfilPreferenciaOfActXPreferenciaListActXPreferencia != null) {
                    oldPerfilPreferenciaOfActXPreferenciaListActXPreferencia.getActXPreferenciaList().remove(actXPreferenciaListActXPreferencia);
                    oldPerfilPreferenciaOfActXPreferenciaListActXPreferencia = em.merge(oldPerfilPreferenciaOfActXPreferenciaListActXPreferencia);
                }
            }
            for (PreferenciaXBaile preferenciaXBaileListPreferenciaXBaile : perfilPreferencia.getPreferenciaXBaileList()) {
                PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXBaileListPreferenciaXBaile = preferenciaXBaileListPreferenciaXBaile.getPerfilPreferencia();
                preferenciaXBaileListPreferenciaXBaile.setPerfilPreferencia(perfilPreferencia);
                preferenciaXBaileListPreferenciaXBaile = em.merge(preferenciaXBaileListPreferenciaXBaile);
                if (oldPerfilPreferenciaOfPreferenciaXBaileListPreferenciaXBaile != null) {
                    oldPerfilPreferenciaOfPreferenciaXBaileListPreferenciaXBaile.getPreferenciaXBaileList().remove(preferenciaXBaileListPreferenciaXBaile);
                    oldPerfilPreferenciaOfPreferenciaXBaileListPreferenciaXBaile = em.merge(oldPerfilPreferenciaOfPreferenciaXBaileListPreferenciaXBaile);
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListPreferenciaXCancion : perfilPreferencia.getPreferenciaXCancionList()) {
                PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXCancionListPreferenciaXCancion = preferenciaXCancionListPreferenciaXCancion.getPerfilPreferencia();
                preferenciaXCancionListPreferenciaXCancion.setPerfilPreferencia(perfilPreferencia);
                preferenciaXCancionListPreferenciaXCancion = em.merge(preferenciaXCancionListPreferenciaXCancion);
                if (oldPerfilPreferenciaOfPreferenciaXCancionListPreferenciaXCancion != null) {
                    oldPerfilPreferenciaOfPreferenciaXCancionListPreferenciaXCancion.getPreferenciaXCancionList().remove(preferenciaXCancionListPreferenciaXCancion);
                    oldPerfilPreferenciaOfPreferenciaXCancionListPreferenciaXCancion = em.merge(oldPerfilPreferenciaOfPreferenciaXCancionListPreferenciaXCancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPerfilPreferencia(perfilPreferencia.getPerfilPwaCedula()) != null) {
                throw new PreexistingEntityException("PerfilPreferencia " + perfilPreferencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PerfilPreferencia perfilPreferencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PerfilPreferencia persistentPerfilPreferencia = em.find(PerfilPreferencia.class, perfilPreferencia.getPerfilPwaCedula());
            List<PreferenciaXCuento> preferenciaXCuentoListOld = persistentPerfilPreferencia.getPreferenciaXCuentoList();
            List<PreferenciaXCuento> preferenciaXCuentoListNew = perfilPreferencia.getPreferenciaXCuentoList();
            List<PreferenciaXRutina> preferenciaXRutinaListOld = persistentPerfilPreferencia.getPreferenciaXRutinaList();
            List<PreferenciaXRutina> preferenciaXRutinaListNew = perfilPreferencia.getPreferenciaXRutinaList();
            List<ActXPreferencia> actXPreferenciaListOld = persistentPerfilPreferencia.getActXPreferenciaList();
            List<ActXPreferencia> actXPreferenciaListNew = perfilPreferencia.getActXPreferenciaList();
            List<PreferenciaXBaile> preferenciaXBaileListOld = persistentPerfilPreferencia.getPreferenciaXBaileList();
            List<PreferenciaXBaile> preferenciaXBaileListNew = perfilPreferencia.getPreferenciaXBaileList();
            List<PreferenciaXCancion> preferenciaXCancionListOld = persistentPerfilPreferencia.getPreferenciaXCancionList();
            List<PreferenciaXCancion> preferenciaXCancionListNew = perfilPreferencia.getPreferenciaXCancionList();
            List<String> illegalOrphanMessages = null;
            for (PreferenciaXCuento preferenciaXCuentoListOldPreferenciaXCuento : preferenciaXCuentoListOld) {
                if (!preferenciaXCuentoListNew.contains(preferenciaXCuentoListOldPreferenciaXCuento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXCuento " + preferenciaXCuentoListOldPreferenciaXCuento + " since its perfilPreferencia field is not nullable.");
                }
            }
            for (PreferenciaXRutina preferenciaXRutinaListOldPreferenciaXRutina : preferenciaXRutinaListOld) {
                if (!preferenciaXRutinaListNew.contains(preferenciaXRutinaListOldPreferenciaXRutina)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXRutina " + preferenciaXRutinaListOldPreferenciaXRutina + " since its perfilPreferencia field is not nullable.");
                }
            }
            for (ActXPreferencia actXPreferenciaListOldActXPreferencia : actXPreferenciaListOld) {
                if (!actXPreferenciaListNew.contains(actXPreferenciaListOldActXPreferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ActXPreferencia " + actXPreferenciaListOldActXPreferencia + " since its perfilPreferencia field is not nullable.");
                }
            }
            for (PreferenciaXBaile preferenciaXBaileListOldPreferenciaXBaile : preferenciaXBaileListOld) {
                if (!preferenciaXBaileListNew.contains(preferenciaXBaileListOldPreferenciaXBaile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXBaile " + preferenciaXBaileListOldPreferenciaXBaile + " since its perfilPreferencia field is not nullable.");
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListOldPreferenciaXCancion : preferenciaXCancionListOld) {
                if (!preferenciaXCancionListNew.contains(preferenciaXCancionListOldPreferenciaXCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreferenciaXCancion " + preferenciaXCancionListOldPreferenciaXCancion + " since its perfilPreferencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PreferenciaXCuento> attachedPreferenciaXCuentoListNew = new ArrayList<PreferenciaXCuento>();
            for (PreferenciaXCuento preferenciaXCuentoListNewPreferenciaXCuentoToAttach : preferenciaXCuentoListNew) {
                preferenciaXCuentoListNewPreferenciaXCuentoToAttach = em.getReference(preferenciaXCuentoListNewPreferenciaXCuentoToAttach.getClass(), preferenciaXCuentoListNewPreferenciaXCuentoToAttach.getPreferenciaXCuentoPK());
                attachedPreferenciaXCuentoListNew.add(preferenciaXCuentoListNewPreferenciaXCuentoToAttach);
            }
            preferenciaXCuentoListNew = attachedPreferenciaXCuentoListNew;
            perfilPreferencia.setPreferenciaXCuentoList(preferenciaXCuentoListNew);
            List<PreferenciaXRutina> attachedPreferenciaXRutinaListNew = new ArrayList<PreferenciaXRutina>();
            for (PreferenciaXRutina preferenciaXRutinaListNewPreferenciaXRutinaToAttach : preferenciaXRutinaListNew) {
                preferenciaXRutinaListNewPreferenciaXRutinaToAttach = em.getReference(preferenciaXRutinaListNewPreferenciaXRutinaToAttach.getClass(), preferenciaXRutinaListNewPreferenciaXRutinaToAttach.getPreferenciaXRutinaPK());
                attachedPreferenciaXRutinaListNew.add(preferenciaXRutinaListNewPreferenciaXRutinaToAttach);
            }
            preferenciaXRutinaListNew = attachedPreferenciaXRutinaListNew;
            perfilPreferencia.setPreferenciaXRutinaList(preferenciaXRutinaListNew);
            List<ActXPreferencia> attachedActXPreferenciaListNew = new ArrayList<ActXPreferencia>();
            for (ActXPreferencia actXPreferenciaListNewActXPreferenciaToAttach : actXPreferenciaListNew) {
                actXPreferenciaListNewActXPreferenciaToAttach = em.getReference(actXPreferenciaListNewActXPreferenciaToAttach.getClass(), actXPreferenciaListNewActXPreferenciaToAttach.getActXPreferenciaPK());
                attachedActXPreferenciaListNew.add(actXPreferenciaListNewActXPreferenciaToAttach);
            }
            actXPreferenciaListNew = attachedActXPreferenciaListNew;
            perfilPreferencia.setActXPreferenciaList(actXPreferenciaListNew);
            List<PreferenciaXBaile> attachedPreferenciaXBaileListNew = new ArrayList<PreferenciaXBaile>();
            for (PreferenciaXBaile preferenciaXBaileListNewPreferenciaXBaileToAttach : preferenciaXBaileListNew) {
                preferenciaXBaileListNewPreferenciaXBaileToAttach = em.getReference(preferenciaXBaileListNewPreferenciaXBaileToAttach.getClass(), preferenciaXBaileListNewPreferenciaXBaileToAttach.getPreferenciaXBailePK());
                attachedPreferenciaXBaileListNew.add(preferenciaXBaileListNewPreferenciaXBaileToAttach);
            }
            preferenciaXBaileListNew = attachedPreferenciaXBaileListNew;
            perfilPreferencia.setPreferenciaXBaileList(preferenciaXBaileListNew);
            List<PreferenciaXCancion> attachedPreferenciaXCancionListNew = new ArrayList<PreferenciaXCancion>();
            for (PreferenciaXCancion preferenciaXCancionListNewPreferenciaXCancionToAttach : preferenciaXCancionListNew) {
                preferenciaXCancionListNewPreferenciaXCancionToAttach = em.getReference(preferenciaXCancionListNewPreferenciaXCancionToAttach.getClass(), preferenciaXCancionListNewPreferenciaXCancionToAttach.getPreferenciaXCancionPK());
                attachedPreferenciaXCancionListNew.add(preferenciaXCancionListNewPreferenciaXCancionToAttach);
            }
            preferenciaXCancionListNew = attachedPreferenciaXCancionListNew;
            perfilPreferencia.setPreferenciaXCancionList(preferenciaXCancionListNew);
            perfilPreferencia = em.merge(perfilPreferencia);
            for (PreferenciaXCuento preferenciaXCuentoListNewPreferenciaXCuento : preferenciaXCuentoListNew) {
                if (!preferenciaXCuentoListOld.contains(preferenciaXCuentoListNewPreferenciaXCuento)) {
                    PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento = preferenciaXCuentoListNewPreferenciaXCuento.getPerfilPreferencia();
                    preferenciaXCuentoListNewPreferenciaXCuento.setPerfilPreferencia(perfilPreferencia);
                    preferenciaXCuentoListNewPreferenciaXCuento = em.merge(preferenciaXCuentoListNewPreferenciaXCuento);
                    if (oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento != null && !oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento.equals(perfilPreferencia)) {
                        oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento.getPreferenciaXCuentoList().remove(preferenciaXCuentoListNewPreferenciaXCuento);
                        oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento = em.merge(oldPerfilPreferenciaOfPreferenciaXCuentoListNewPreferenciaXCuento);
                    }
                }
            }
            for (PreferenciaXRutina preferenciaXRutinaListNewPreferenciaXRutina : preferenciaXRutinaListNew) {
                if (!preferenciaXRutinaListOld.contains(preferenciaXRutinaListNewPreferenciaXRutina)) {
                    PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina = preferenciaXRutinaListNewPreferenciaXRutina.getPerfilPreferencia();
                    preferenciaXRutinaListNewPreferenciaXRutina.setPerfilPreferencia(perfilPreferencia);
                    preferenciaXRutinaListNewPreferenciaXRutina = em.merge(preferenciaXRutinaListNewPreferenciaXRutina);
                    if (oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina != null && !oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina.equals(perfilPreferencia)) {
                        oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina.getPreferenciaXRutinaList().remove(preferenciaXRutinaListNewPreferenciaXRutina);
                        oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina = em.merge(oldPerfilPreferenciaOfPreferenciaXRutinaListNewPreferenciaXRutina);
                    }
                }
            }
            for (ActXPreferencia actXPreferenciaListNewActXPreferencia : actXPreferenciaListNew) {
                if (!actXPreferenciaListOld.contains(actXPreferenciaListNewActXPreferencia)) {
                    PerfilPreferencia oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia = actXPreferenciaListNewActXPreferencia.getPerfilPreferencia();
                    actXPreferenciaListNewActXPreferencia.setPerfilPreferencia(perfilPreferencia);
                    actXPreferenciaListNewActXPreferencia = em.merge(actXPreferenciaListNewActXPreferencia);
                    if (oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia != null && !oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia.equals(perfilPreferencia)) {
                        oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia.getActXPreferenciaList().remove(actXPreferenciaListNewActXPreferencia);
                        oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia = em.merge(oldPerfilPreferenciaOfActXPreferenciaListNewActXPreferencia);
                    }
                }
            }
            for (PreferenciaXBaile preferenciaXBaileListNewPreferenciaXBaile : preferenciaXBaileListNew) {
                if (!preferenciaXBaileListOld.contains(preferenciaXBaileListNewPreferenciaXBaile)) {
                    PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile = preferenciaXBaileListNewPreferenciaXBaile.getPerfilPreferencia();
                    preferenciaXBaileListNewPreferenciaXBaile.setPerfilPreferencia(perfilPreferencia);
                    preferenciaXBaileListNewPreferenciaXBaile = em.merge(preferenciaXBaileListNewPreferenciaXBaile);
                    if (oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile != null && !oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile.equals(perfilPreferencia)) {
                        oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile.getPreferenciaXBaileList().remove(preferenciaXBaileListNewPreferenciaXBaile);
                        oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile = em.merge(oldPerfilPreferenciaOfPreferenciaXBaileListNewPreferenciaXBaile);
                    }
                }
            }
            for (PreferenciaXCancion preferenciaXCancionListNewPreferenciaXCancion : preferenciaXCancionListNew) {
                if (!preferenciaXCancionListOld.contains(preferenciaXCancionListNewPreferenciaXCancion)) {
                    PerfilPreferencia oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion = preferenciaXCancionListNewPreferenciaXCancion.getPerfilPreferencia();
                    preferenciaXCancionListNewPreferenciaXCancion.setPerfilPreferencia(perfilPreferencia);
                    preferenciaXCancionListNewPreferenciaXCancion = em.merge(preferenciaXCancionListNewPreferenciaXCancion);
                    if (oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion != null && !oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion.equals(perfilPreferencia)) {
                        oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion.getPreferenciaXCancionList().remove(preferenciaXCancionListNewPreferenciaXCancion);
                        oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion = em.merge(oldPerfilPreferenciaOfPreferenciaXCancionListNewPreferenciaXCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = perfilPreferencia.getPerfilPwaCedula();
                if (findPerfilPreferencia(id) == null) {
                    throw new NonexistentEntityException("The perfilPreferencia with id " + id + " no longer exists.");
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
            PerfilPreferencia perfilPreferencia;
            try {
                perfilPreferencia = em.getReference(PerfilPreferencia.class, id);
                perfilPreferencia.getPerfilPwaCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The perfilPreferencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreferenciaXCuento> preferenciaXCuentoListOrphanCheck = perfilPreferencia.getPreferenciaXCuentoList();
            for (PreferenciaXCuento preferenciaXCuentoListOrphanCheckPreferenciaXCuento : preferenciaXCuentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPreferencia (" + perfilPreferencia + ") cannot be destroyed since the PreferenciaXCuento " + preferenciaXCuentoListOrphanCheckPreferenciaXCuento + " in its preferenciaXCuentoList field has a non-nullable perfilPreferencia field.");
            }
            List<PreferenciaXRutina> preferenciaXRutinaListOrphanCheck = perfilPreferencia.getPreferenciaXRutinaList();
            for (PreferenciaXRutina preferenciaXRutinaListOrphanCheckPreferenciaXRutina : preferenciaXRutinaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPreferencia (" + perfilPreferencia + ") cannot be destroyed since the PreferenciaXRutina " + preferenciaXRutinaListOrphanCheckPreferenciaXRutina + " in its preferenciaXRutinaList field has a non-nullable perfilPreferencia field.");
            }
            List<ActXPreferencia> actXPreferenciaListOrphanCheck = perfilPreferencia.getActXPreferenciaList();
            for (ActXPreferencia actXPreferenciaListOrphanCheckActXPreferencia : actXPreferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPreferencia (" + perfilPreferencia + ") cannot be destroyed since the ActXPreferencia " + actXPreferenciaListOrphanCheckActXPreferencia + " in its actXPreferenciaList field has a non-nullable perfilPreferencia field.");
            }
            List<PreferenciaXBaile> preferenciaXBaileListOrphanCheck = perfilPreferencia.getPreferenciaXBaileList();
            for (PreferenciaXBaile preferenciaXBaileListOrphanCheckPreferenciaXBaile : preferenciaXBaileListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPreferencia (" + perfilPreferencia + ") cannot be destroyed since the PreferenciaXBaile " + preferenciaXBaileListOrphanCheckPreferenciaXBaile + " in its preferenciaXBaileList field has a non-nullable perfilPreferencia field.");
            }
            List<PreferenciaXCancion> preferenciaXCancionListOrphanCheck = perfilPreferencia.getPreferenciaXCancionList();
            for (PreferenciaXCancion preferenciaXCancionListOrphanCheckPreferenciaXCancion : preferenciaXCancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PerfilPreferencia (" + perfilPreferencia + ") cannot be destroyed since the PreferenciaXCancion " + preferenciaXCancionListOrphanCheckPreferenciaXCancion + " in its preferenciaXCancionList field has a non-nullable perfilPreferencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(perfilPreferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PerfilPreferencia> findPerfilPreferenciaEntities() {
        return findPerfilPreferenciaEntities(true, -1, -1);
    }

    public List<PerfilPreferencia> findPerfilPreferenciaEntities(int maxResults, int firstResult) {
        return findPerfilPreferenciaEntities(false, maxResults, firstResult);
    }

    private List<PerfilPreferencia> findPerfilPreferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PerfilPreferencia.class));
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

    public PerfilPreferencia findPerfilPreferencia(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PerfilPreferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPerfilPreferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PerfilPreferencia> rt = cq.from(PerfilPreferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
