package dao;

import entities.Tickets;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class UtenteDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void save(Utente utente){
        try {
            et.begin();
            em.persist(utente);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
    }

    public Utente getById(int id ){
        Utente utente = null;
        try{
            et.begin();
            utente = em.find(Utente.class, id);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
        return utente;
    }
    public void delete(int id){
        try{
            et.begin();
            Utente u =getById(id);
            em.remove(u);
        }catch (Exception e){
            e.getMessage();
        }finally {
            em.close();
        }
    }

}
