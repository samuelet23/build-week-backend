package dao;

import entities.Tessera;
import entities.Tickets;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class TesseraDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void save(Tessera tessera){
        try {
            et.begin();
            em.persist(tessera);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
    }

    public Tessera getById(int id ){
        Tessera tessera = null;
        try{
            et.begin();
            tessera = em.find(Tessera.class, id);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
        return tessera;
    }
    public void delete(int id){
        try{
            et.begin();
            Tessera t =getById(id);
            em.remove(t);
        }catch (Exception e){
            e.getMessage();
        }finally {
            em.close();
        }
    }

}
