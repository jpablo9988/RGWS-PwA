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
import ResPwAEntities.Cuento;
import java.util.ArrayList;
import java.util.List;
import ResPwAEntities.Baile;
import ResPwAEntities.Cancion;
import ResPwAEntities.Controllers.exceptions.IllegalOrphanException;
import ResPwAEntities.Controllers.exceptions.NonexistentEntityException;
import ResPwAEntities.Controllers.exceptions.PreexistingEntityException;
import ResPwAEntities.Genero;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws PreexistingEntityException, Exception {
        if (genero.getCuentoList() == null) {
            genero.setCuentoList(new ArrayList<Cuento>());
        }
        if (genero.getBaileList() == null) {
            genero.setBaileList(new ArrayList<Baile>());
        }
        if (genero.getCancionList() == null) {
            genero.setCancionList(new ArrayList<Cancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cuento> attachedCuentoList = new ArrayList<Cuento>();
            for (Cuento cuentoListCuentoToAttach : genero.getCuentoList()) {
                cuentoListCuentoToAttach = em.getReference(cuentoListCuentoToAttach.getClass(), cuentoListCuentoToAttach.getNombre());
                attachedCuentoList.add(cuentoListCuentoToAttach);
            }
            genero.setCuentoList(attachedCuentoList);
            List<Baile> attachedBaileList = new ArrayList<Baile>();
            for (Baile baileListBaileToAttach : genero.getBaileList()) {
                baileListBaileToAttach = em.getReference(baileListBaileToAttach.getClass(), baileListBaileToAttach.getId());
                attachedBaileList.add(baileListBaileToAttach);
            }
            genero.setBaileList(attachedBaileList);
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : genero.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getNombre());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            genero.setCancionList(attachedCancionList);
            em.persist(genero);
            for (Cuento cuentoListCuento : genero.getCuentoList()) {
                Genero oldGeneroOfCuentoListCuento = cuentoListCuento.getGenero();
                cuentoListCuento.setGenero(genero);
                cuentoListCuento = em.merge(cuentoListCuento);
                if (oldGeneroOfCuentoListCuento != null) {
                    oldGeneroOfCuentoListCuento.getCuentoList().remove(cuentoListCuento);
                    oldGeneroOfCuentoListCuento = em.merge(oldGeneroOfCuentoListCuento);
                }
            }
            for (Baile baileListBaile : genero.getBaileList()) {
                Genero oldGeneroOfBaileListBaile = baileListBaile.getGenero();
                baileListBaile.setGenero(genero);
                baileListBaile = em.merge(baileListBaile);
                if (oldGeneroOfBaileListBaile != null) {
                    oldGeneroOfBaileListBaile.getBaileList().remove(baileListBaile);
                    oldGeneroOfBaileListBaile = em.merge(oldGeneroOfBaileListBaile);
                }
            }
            for (Cancion cancionListCancion : genero.getCancionList()) {
                Genero oldGeneroOfCancionListCancion = cancionListCancion.getGenero();
                cancionListCancion.setGenero(genero);
                cancionListCancion = em.merge(cancionListCancion);
                if (oldGeneroOfCancionListCancion != null) {
                    oldGeneroOfCancionListCancion.getCancionList().remove(cancionListCancion);
                    oldGeneroOfCancionListCancion = em.merge(oldGeneroOfCancionListCancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGenero(genero.getGenero()) != null) {
                throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getGenero());
            List<Cuento> cuentoListOld = persistentGenero.getCuentoList();
            List<Cuento> cuentoListNew = genero.getCuentoList();
            List<Baile> baileListOld = persistentGenero.getBaileList();
            List<Baile> baileListNew = genero.getBaileList();
            List<Cancion> cancionListOld = persistentGenero.getCancionList();
            List<Cancion> cancionListNew = genero.getCancionList();
            List<String> illegalOrphanMessages = null;
            for (Cuento cuentoListOldCuento : cuentoListOld) {
                if (!cuentoListNew.contains(cuentoListOldCuento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuento " + cuentoListOldCuento + " since its genero field is not nullable.");
                }
            }
            for (Baile baileListOldBaile : baileListOld) {
                if (!baileListNew.contains(baileListOldBaile)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Baile " + baileListOldBaile + " since its genero field is not nullable.");
                }
            }
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionListOldCancion + " since its genero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cuento> attachedCuentoListNew = new ArrayList<Cuento>();
            for (Cuento cuentoListNewCuentoToAttach : cuentoListNew) {
                cuentoListNewCuentoToAttach = em.getReference(cuentoListNewCuentoToAttach.getClass(), cuentoListNewCuentoToAttach.getNombre());
                attachedCuentoListNew.add(cuentoListNewCuentoToAttach);
            }
            cuentoListNew = attachedCuentoListNew;
            genero.setCuentoList(cuentoListNew);
            List<Baile> attachedBaileListNew = new ArrayList<Baile>();
            for (Baile baileListNewBaileToAttach : baileListNew) {
                baileListNewBaileToAttach = em.getReference(baileListNewBaileToAttach.getClass(), baileListNewBaileToAttach.getId());
                attachedBaileListNew.add(baileListNewBaileToAttach);
            }
            baileListNew = attachedBaileListNew;
            genero.setBaileList(baileListNew);
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getNombre());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            genero.setCancionList(cancionListNew);
            genero = em.merge(genero);
            for (Cuento cuentoListNewCuento : cuentoListNew) {
                if (!cuentoListOld.contains(cuentoListNewCuento)) {
                    Genero oldGeneroOfCuentoListNewCuento = cuentoListNewCuento.getGenero();
                    cuentoListNewCuento.setGenero(genero);
                    cuentoListNewCuento = em.merge(cuentoListNewCuento);
                    if (oldGeneroOfCuentoListNewCuento != null && !oldGeneroOfCuentoListNewCuento.equals(genero)) {
                        oldGeneroOfCuentoListNewCuento.getCuentoList().remove(cuentoListNewCuento);
                        oldGeneroOfCuentoListNewCuento = em.merge(oldGeneroOfCuentoListNewCuento);
                    }
                }
            }
            for (Baile baileListNewBaile : baileListNew) {
                if (!baileListOld.contains(baileListNewBaile)) {
                    Genero oldGeneroOfBaileListNewBaile = baileListNewBaile.getGenero();
                    baileListNewBaile.setGenero(genero);
                    baileListNewBaile = em.merge(baileListNewBaile);
                    if (oldGeneroOfBaileListNewBaile != null && !oldGeneroOfBaileListNewBaile.equals(genero)) {
                        oldGeneroOfBaileListNewBaile.getBaileList().remove(baileListNewBaile);
                        oldGeneroOfBaileListNewBaile = em.merge(oldGeneroOfBaileListNewBaile);
                    }
                }
            }
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    Genero oldGeneroOfCancionListNewCancion = cancionListNewCancion.getGenero();
                    cancionListNewCancion.setGenero(genero);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                    if (oldGeneroOfCancionListNewCancion != null && !oldGeneroOfCancionListNewCancion.equals(genero)) {
                        oldGeneroOfCancionListNewCancion.getCancionList().remove(cancionListNewCancion);
                        oldGeneroOfCancionListNewCancion = em.merge(oldGeneroOfCancionListNewCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = genero.getGenero();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cuento> cuentoListOrphanCheck = genero.getCuentoList();
            for (Cuento cuentoListOrphanCheckCuento : cuentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Cuento " + cuentoListOrphanCheckCuento + " in its cuentoList field has a non-nullable genero field.");
            }
            List<Baile> baileListOrphanCheck = genero.getBaileList();
            for (Baile baileListOrphanCheckBaile : baileListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Baile " + baileListOrphanCheckBaile + " in its baileList field has a non-nullable genero field.");
            }
            List<Cancion> cancionListOrphanCheck = genero.getCancionList();
            for (Cancion cancionListOrphanCheckCancion : cancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Cancion " + cancionListOrphanCheckCancion + " in its cancionList field has a non-nullable genero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
