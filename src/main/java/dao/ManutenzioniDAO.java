package dao;

import entities.Manutenzioni;
import entities.Mezzi;
import jakarta.persistence.*;

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

    public void setInManutenzione (Mezzi m){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query setInManutenzione = em.createNamedQuery("setInManutenzione");
        setInManutenzione.setParameter("id", m.getId());
        setInManutenzione.executeUpdate();
        et.commit();
    }

    public void setOffManutenzione (Mezzi m){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query setOffManutenzione = em.createNamedQuery("setOffManutenzione");
        setOffManutenzione.setParameter("id", m.getId());
        setOffManutenzione.executeUpdate();
        et.commit();
    }

    public Manutenzioni getById(int id){
        return em.find(Manutenzioni.class, id);
    }
    public Manutenzioni getAll (){
        return 
    }
}
