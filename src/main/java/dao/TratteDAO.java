package dao;

import entities.Mezzi;
import entities.Tratte;
import jakarta.persistence.*;

public class TratteDAO {

    private EntityManagerFactory emf;
    private EntityManager em;

    public TratteDAO() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Tratte t){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Tratte t = getById(id);
        em.remove(t);
        et.commit();
    }

    public Tratte getById(int id){
        return em.find(Tratte.class, id);
    }
}
