package dao;

import entities.PuntiDiEmissione;

import entities.Tickets;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class PuntiDiEmissioneDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
    EntityManager em = emf.createEntityManager();
    EntityTransaction et = em.getTransaction();

    public void save(PuntiDiEmissione emissione){
        try {
            et.begin();
            em.persist(emissione);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
    }

    public PuntiDiEmissione getById(int id ){
        PuntiDiEmissione emissione = null;
        try{
            et.begin();
            emissione = em.find(PuntiDiEmissione.class, id);
            et.commit();
        }catch (Exception e){
            e.getMessage();
        }finally{
            em.close();
        }
        return emissione;
    }
    public void delete(int id){
        try{
            et.begin();
            PuntiDiEmissione p =getById(id);
            em.remove(p);
        }catch (Exception e){
            e.getMessage();
        }finally {
            em.close();
        }
    }

}
