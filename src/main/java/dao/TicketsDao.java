package dao;

import entities.Manutenzioni;
import entities.Tickets;
import jakarta.persistence.*;

public class TicketsDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public TicketsDao() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Tickets t){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Tickets t = getById(id);
        em.remove(t);
        et.commit();
    }

    public Tickets getById(int id){
        return em.find(Tickets.class, id);
    }
}

}
