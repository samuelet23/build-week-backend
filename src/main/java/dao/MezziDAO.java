package dao;

import entities.Mezzi;
import entities.Tessera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class MezziDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public MezziDAO() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Mezzi m){
            EntityTransaction et = em.getTransaction();
            et.begin();
            em.persist(m);
            et.commit();
            em.refresh(m);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Mezzi m = getById(id);
        em.remove(m);
        et.commit();
    }

    public Mezzi getById(int id){
        return em.find(Mezzi.class, id);
    }
}
