package dao;

import entities.PuntiDiEmissione;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PuntiDiEmissioneDAO {
    private EntityManagerFactory emf;
    private EntityManager em;

    public PuntiDiEmissioneDAO() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em  = emf.createEntityManager();
    }
    public void aggiungi(PuntiDiEmissione c){
        EntityTransaction et = em.getTransaction();
        et.begin();

        em.persist(c);

        et.commit();

        emf.close();
        em.close();
    }
    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();

        PuntiDiEmissione c = getById(id);
        em.remove(c);

        et.commit();

        emf.close();
        em.close();
    }
    public PuntiDiEmissione getById(int id){

        return em.find(PuntiDiEmissione.class, id);
    }
}
