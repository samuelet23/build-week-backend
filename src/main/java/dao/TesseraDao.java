package dao;

import entities.Tessera;
import entities.Utente;
import jakarta.persistence.*;

import java.time.LocalDate;

public class TesseraDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public TesseraDao() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Tessera t){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Tessera t=  getById(id);
        em.remove(t);
        et.commit();
    }

    public Tessera getById(int id){
        return em.find(Tessera.class, id);
    }

    public boolean checkValidationTessera(Utente utente){
        EntityTransaction et = em.getTransaction();
        et.begin();
        LocalDate oggi = LocalDate.now();
        Query check = em.createNamedQuery("validationTessera");
        check.setParameter("oggi", oggi);
        check.setParameter("numeroTessera", utente.getNumeroTessera());

        et.commit();
        if (check.getSingleResult().equals(0)){
            return false;
        } else {
            return true;
        }
    }
}
