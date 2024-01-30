package dao;

import entities.DistributoriAutomatici;
import entities.PuntiDiEmissione;

import jakarta.persistence.*;

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
        em.refresh(c);
    }
    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        PuntiDiEmissione c = getById(id);
        em.remove(c);
        et.commit();
    }
    public PuntiDiEmissione getById(int id){

        return em.find(PuntiDiEmissione.class, id);
    }

    public void setFuoriServizio (DistributoriAutomatici fuoriServizio){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query setFuoriServizio = em.createNamedQuery("setFuoriServizio");
        setFuoriServizio.setParameter("fuoriServizio",fuoriServizio );
        et.commit();
    }
    public void setAttivo (DistributoriAutomatici attivo){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query setAttivo = em.createNamedQuery("setAttivo");
        setAttivo.setParameter("attivo",attivo );
        et.commit();
    }
}
