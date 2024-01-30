package dao;


import entities.PuntiDiEmissione;
import entities.Tickets;
import jakarta.persistence.*;

import java.time.LocalDate;

public class TicketsDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public TicketsDao() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

    public void aggiungi (Tickets t){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }

    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Tickets t = getById(id);
        em.remove(t);
        et.commit();
    }

    public Tickets getById(int id){
        return em.find(Tickets.class, id);
    }






    public int bigliettiEmessiData(LocalDate dataInizio, LocalDate dataFine){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query bigliettiEmessi = em.createNamedQuery("bigliettiEmessiData");
        bigliettiEmessi.setParameter("dataInizio", dataInizio);
        bigliettiEmessi.setParameter("dataFine", dataFine);
        et.commit();
        return (int)bigliettiEmessi.getSingleResult();
    }
    public int bigliettiEmessiPuntoEmissione(PuntiDiEmissione puntiDiEmissione){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query bigliettiEmessi = em.createNamedQuery("bigliettiEmessiPuntoEmissione");
        bigliettiEmessi.setParameter("puntoEmissione", puntiDiEmissione);
        et.commit();
        return (int)bigliettiEmessi.getSingleResult();
    }
    public int bigliettiEmessiTotale(LocalDate dataInizio, LocalDate dataFine, PuntiDiEmissione puntiDiEmissione){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query bigliettiEmessi = em.createNamedQuery("bigliettiEmessiPuntoEmissioneData");
        bigliettiEmessi.setParameter("dataInizio", dataInizio);
        bigliettiEmessi.setParameter("dataFine", dataFine);
        bigliettiEmessi.setParameter("puntoEmissione", puntiDiEmissione);
        et.commit();
        return (int)bigliettiEmessi.getSingleResult();
    }



}

