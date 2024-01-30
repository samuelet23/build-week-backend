package dao;

import entities.Manutenzioni;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ManutenzioniDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public ManutenzioniDAO() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Manutenzioni m){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(m);
        et.commit();
        em.refresh(m);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Manutenzioni m = getById(id);
        em.remove(m);
        et.commit();
    }

    public Manutenzioni getById(int id){
        return em.find(Manutenzioni.class, id);
    }
}