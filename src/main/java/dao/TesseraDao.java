package dao;

import entities.Tessera;
import entities.Utente;
import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Date;


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
        check.setParameter("numeroTessera", utente.getNumeroTessera().getId());
        et.commit();
        Tessera tessera = (Tessera) check.getSingleResult();
        if (tessera.getDataScadenza().isBefore(oggi)){
            return false;
        } else return true;
    }
}
