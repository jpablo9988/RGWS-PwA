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
import ResPwAEntities.Ejercicio;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.PerfilEjercicio;
import ResPwAEntities.FraseInspiracional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tesispepper
 */
public class EjercicioJpaController implements Serializable {

    public EjercicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ejercicio ejercicio) throws PreexistingEntityException, Exception {
        if (ejercicio.getCategoriaEntrenamientoList() == null) {
            ejercicio.setCategoriaEntrenamientoList(new ArrayList<CategoriaEntrenamiento>());
        }
        if (ejercicio.getPerfilEjercicioList() == null) {
            ejercicio.setPerfilEjercicioList(new ArrayList<PerfilEjercicio>());
        }
        if (ejercicio.getFraseInspiracionalList() == null) {
            ejercicio.setFraseInspiracionalList(new ArrayList<FraseInspiracional>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CategoriaEntrenamiento> attachedCategoriaEntrenamientoList = new ArrayList<CategoriaEntrenamiento>();
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamientoToAttach : ejercicio.getCategoriaEntrenamientoList()) {
                categoriaEntrenamientoListCategoriaEntrenamientoToAttach = em.getReference(categoriaEntrenamientoListCategoriaEntrenamientoToAttach.getClass(), categoriaEntrenamientoListCategoriaEntrenamientoToAttach.getTipo());
                attachedCategoriaEntrenamientoList.add(categoriaEntrenamientoListCategoriaEntrenamientoToAttach);
            }
            ejercicio.setCategoriaEntrenamientoList(attachedCategoriaEntrenamientoList);
            List<PerfilEjercicio> attachedPerfilEjercicioList = new ArrayList<PerfilEjercicio>();
            for (PerfilEjercicio perfilEjercicioListPerfilEjercicioToAttach : ejercicio.getPerfilEjercicioList()) {
                perfilEjercicioListPerfilEjercicioToAttach = em.getReference(perfilEjercicioListPerfilEjercicioToAttach.getClass(), perfilEjercicioListPerfilEjercicioToAttach.getPerfilPwaCedula());
                attachedPerfilEjercicioList.add(perfilEjercicioListPerfilEjercicioToAttach);
            }
            ejercicio.setPerfilEjercicioList(attachedPerfilEjercicioList);
            List<FraseInspiracional> attachedFraseInspiracionalList = new ArrayList<FraseInspiracional>();
            for (FraseInspiracional fraseInspiracionalListFraseInspiracionalToAttach : ejercicio.getFraseInspiracionalList()) {
                fraseInspiracionalListFraseInspiracionalToAttach = em.getReference(fraseInspiracionalListFraseInspiracionalToAttach.getClass(), fraseInspiracionalListFraseInspiracionalToAttach.getFraseInspiracionalPK());
                attachedFraseInspiracionalList.add(fraseInspiracionalListFraseInspiracionalToAttach);
            }
            ejercicio.setFraseInspiracionalList(attachedFraseInspiracionalList);
            em.persist(ejercicio);
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamiento : ejercicio.getCategoriaEntrenamientoList()) {
                categoriaEntrenamientoListCategoriaEntrenamiento.getEjercicioList().add(ejercicio);
                categoriaEntrenamientoListCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListCategoriaEntrenamiento);
            }
            for (PerfilEjercicio perfilEjercicioListPerfilEjercicio : ejercicio.getPerfilEjercicioList()) {
                perfilEjercicioListPerfilEjercicio.getEjercicioList().add(ejercicio);
                perfilEjercicioListPerfilEjercicio = em.merge(perfilEjercicioListPerfilEjercicio);
            }
            for (FraseInspiracional fraseInspiracionalListFraseInspiracional : ejercicio.getFraseInspiracionalList()) {
                Ejercicio oldEjercicio1OfFraseInspiracionalListFraseInspiracional = fraseInspiracionalListFraseInspiracional.getEjercicio1();
                fraseInspiracionalListFraseInspiracional.setEjercicio1(ejercicio);
                fraseInspiracionalListFraseInspiracional = em.merge(fraseInspiracionalListFraseInspiracional);
                if (oldEjercicio1OfFraseInspiracionalListFraseInspiracional != null) {
                    oldEjercicio1OfFraseInspiracionalListFraseInspiracional.getFraseInspiracionalList().remove(fraseInspiracionalListFraseInspiracional);
                    oldEjercicio1OfFraseInspiracionalListFraseInspiracional = em.merge(oldEjercicio1OfFraseInspiracionalListFraseInspiracional);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEjercicio(ejercicio.getNombre()) != null) {
                throw new PreexistingEntityException("Ejercicio " + ejercicio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ejercicio ejercicio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ejercicio persistentEjercicio = em.find(Ejercicio.class, ejercicio.getNombre());
            List<CategoriaEntrenamiento> categoriaEntrenamientoListOld = persistentEjercicio.getCategoriaEntrenamientoList();
            List<CategoriaEntrenamiento> categoriaEntrenamientoListNew = ejercicio.getCategoriaEntrenamientoList();
            List<PerfilEjercicio> perfilEjercicioListOld = persistentEjercicio.getPerfilEjercicioList();
            List<PerfilEjercicio> perfilEjercicioListNew = ejercicio.getPerfilEjercicioList();
            List<FraseInspiracional> fraseInspiracionalListOld = persistentEjercicio.getFraseInspiracionalList();
            List<FraseInspiracional> fraseInspiracionalListNew = ejercicio.getFraseInspiracionalList();
            List<String> illegalOrphanMessages = null;
            for (FraseInspiracional fraseInspiracionalListOldFraseInspiracional : fraseInspiracionalListOld) {
                if (!fraseInspiracionalListNew.contains(fraseInspiracionalListOldFraseInspiracional)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FraseInspiracional " + fraseInspiracionalListOldFraseInspiracional + " since its ejercicio1 field is not nullable.");
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
            ejercicio.setCategoriaEntrenamientoList(categoriaEntrenamientoListNew);
            List<PerfilEjercicio> attachedPerfilEjercicioListNew = new ArrayList<PerfilEjercicio>();
            for (PerfilEjercicio perfilEjercicioListNewPerfilEjercicioToAttach : perfilEjercicioListNew) {
                perfilEjercicioListNewPerfilEjercicioToAttach = em.getReference(perfilEjercicioListNewPerfilEjercicioToAttach.getClass(), perfilEjercicioListNewPerfilEjercicioToAttach.getPerfilPwaCedula());
                attachedPerfilEjercicioListNew.add(perfilEjercicioListNewPerfilEjercicioToAttach);
            }
            perfilEjercicioListNew = attachedPerfilEjercicioListNew;
            ejercicio.setPerfilEjercicioList(perfilEjercicioListNew);
            List<FraseInspiracional> attachedFraseInspiracionalListNew = new ArrayList<FraseInspiracional>();
            for (FraseInspiracional fraseInspiracionalListNewFraseInspiracionalToAttach : fraseInspiracionalListNew) {
                fraseInspiracionalListNewFraseInspiracionalToAttach = em.getReference(fraseInspiracionalListNewFraseInspiracionalToAttach.getClass(), fraseInspiracionalListNewFraseInspiracionalToAttach.getFraseInspiracionalPK());
                attachedFraseInspiracionalListNew.add(fraseInspiracionalListNewFraseInspiracionalToAttach);
            }
            fraseInspiracionalListNew = attachedFraseInspiracionalListNew;
            ejercicio.setFraseInspiracionalList(fraseInspiracionalListNew);
            ejercicio = em.merge(ejercicio);
            for (CategoriaEntrenamiento categoriaEntrenamientoListOldCategoriaEntrenamiento : categoriaEntrenamientoListOld) {
                if (!categoriaEntrenamientoListNew.contains(categoriaEntrenamientoListOldCategoriaEntrenamiento)) {
                    categoriaEntrenamientoListOldCategoriaEntrenamiento.getEjercicioList().remove(ejercicio);
                    categoriaEntrenamientoListOldCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListOldCategoriaEntrenamiento);
                }
            }
            for (CategoriaEntrenamiento categoriaEntrenamientoListNewCategoriaEntrenamiento : categoriaEntrenamientoListNew) {
                if (!categoriaEntrenamientoListOld.contains(categoriaEntrenamientoListNewCategoriaEntrenamiento)) {
                    categoriaEntrenamientoListNewCategoriaEntrenamiento.getEjercicioList().add(ejercicio);
                    categoriaEntrenamientoListNewCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListNewCategoriaEntrenamiento);
                }
            }
            for (PerfilEjercicio perfilEjercicioListOldPerfilEjercicio : perfilEjercicioListOld) {
                if (!perfilEjercicioListNew.contains(perfilEjercicioListOldPerfilEjercicio)) {
                    perfilEjercicioListOldPerfilEjercicio.getEjercicioList().remove(ejercicio);
                    perfilEjercicioListOldPerfilEjercicio = em.merge(perfilEjercicioListOldPerfilEjercicio);
                }
            }
            for (PerfilEjercicio perfilEjercicioListNewPerfilEjercicio : perfilEjercicioListNew) {
                if (!perfilEjercicioListOld.contains(perfilEjercicioListNewPerfilEjercicio)) {
                    perfilEjercicioListNewPerfilEjercicio.getEjercicioList().add(ejercicio);
                    perfilEjercicioListNewPerfilEjercicio = em.merge(perfilEjercicioListNewPerfilEjercicio);
                }
            }
            for (FraseInspiracional fraseInspiracionalListNewFraseInspiracional : fraseInspiracionalListNew) {
                if (!fraseInspiracionalListOld.contains(fraseInspiracionalListNewFraseInspiracional)) {
                    Ejercicio oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional = fraseInspiracionalListNewFraseInspiracional.getEjercicio1();
                    fraseInspiracionalListNewFraseInspiracional.setEjercicio1(ejercicio);
                    fraseInspiracionalListNewFraseInspiracional = em.merge(fraseInspiracionalListNewFraseInspiracional);
                    if (oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional != null && !oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional.equals(ejercicio)) {
                        oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional.getFraseInspiracionalList().remove(fraseInspiracionalListNewFraseInspiracional);
                        oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional = em.merge(oldEjercicio1OfFraseInspiracionalListNewFraseInspiracional);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ejercicio.getNombre();
                if (findEjercicio(id) == null) {
                    throw new NonexistentEntityException("The ejercicio with id " + id + " no longer exists.");
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
            Ejercicio ejercicio;
            try {
                ejercicio = em.getReference(Ejercicio.class, id);
                ejercicio.getNombre();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ejercicio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FraseInspiracional> fraseInspiracionalListOrphanCheck = ejercicio.getFraseInspiracionalList();
            for (FraseInspiracional fraseInspiracionalListOrphanCheckFraseInspiracional : fraseInspiracionalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ejercicio (" + ejercicio + ") cannot be destroyed since the FraseInspiracional " + fraseInspiracionalListOrphanCheckFraseInspiracional + " in its fraseInspiracionalList field has a non-nullable ejercicio1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CategoriaEntrenamiento> categoriaEntrenamientoList = ejercicio.getCategoriaEntrenamientoList();
            for (CategoriaEntrenamiento categoriaEntrenamientoListCategoriaEntrenamiento : categoriaEntrenamientoList) {
                categoriaEntrenamientoListCategoriaEntrenamiento.getEjercicioList().remove(ejercicio);
                categoriaEntrenamientoListCategoriaEntrenamiento = em.merge(categoriaEntrenamientoListCategoriaEntrenamiento);
            }
            List<PerfilEjercicio> perfilEjercicioList = ejercicio.getPerfilEjercicioList();
            for (PerfilEjercicio perfilEjercicioListPerfilEjercicio : perfilEjercicioList) {
                perfilEjercicioListPerfilEjercicio.getEjercicioList().remove(ejercicio);
                perfilEjercicioListPerfilEjercicio = em.merge(perfilEjercicioListPerfilEjercicio);
            }
            em.remove(ejercicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ejercicio> findEjercicioEntities() {
        return findEjercicioEntities(true, -1, -1);
    }

    public List<Ejercicio> findEjercicioEntities(int maxResults, int firstResult) {
        return findEjercicioEntities(false, maxResults, firstResult);
    }

    private List<Ejercicio> findEjercicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ejercicio.class));
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

    public Ejercicio findEjercicio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ejercicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEjercicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ejercicio> rt = cq.from(Ejercicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
