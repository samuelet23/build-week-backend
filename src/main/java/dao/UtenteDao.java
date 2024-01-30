package dao;


import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class UtenteDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public UtenteDao() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Utente u){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(u);
        et.commit();
        em.refresh(u);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Utente u = getById(id);
        em.remove(u);
        et.commit();
    }

    public Utente getById(int id){
        return em.find(Utente.class, id);
    }
}

