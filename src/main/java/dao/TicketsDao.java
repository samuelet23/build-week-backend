package dao;

import entities.Tickets;
import jakarta.persistence.*;

public class TicketsDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void save(Tickets tickets){
        try {
            et.begin();
            em.persist(tickets);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
    }

    public Tickets getById(int id ){
        Tickets tickets = null;
        try{
            et.begin();
            tickets = em.find(Tickets.class, id);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
        return tickets;
    }
    public void delete(int id){
        try{
            et.begin();
            Tickets t =getById(id);
            em.remove(t);
        }catch (Exception e){
            e.getMessage();
        }finally {
            em.close();
        }
    }

}
