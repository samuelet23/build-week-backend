package dao;

import entities.Manutenzioni;
import entities.Mezzi;
import entities.PuntiDiEmissione;
import entities.Tickets;
import entities.sottoclassi.Biglietti;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class TicketsDao {
    private EntityManagerFactory emf;
    private EntityManager em;

    public TicketsDao() {
        emf = Persistence.createEntityManagerFactory("trasporto_pubblico");
        em = emf.createEntityManager();
    }

// aggiunge un Tickets(Abbonamento o biglietto)
    public void aggiungi (Tickets t){
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(t);
        et.commit();
        em.refresh(t);
    }
// Elimina un Tickets(Abbonamento o biglietto) in base all'id passato
    public void elimina(int id){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Tickets t = getById(id);
        em.remove(t);
        et.commit();
    }

//    recupera un Tickets(Abbonamento o Biglietti) in base all'id
    public Tickets getById(int id){
        return em.find(Tickets.class, id);
    }






//    Numero totale di biglietti emessi in una determinata data
    public int bigliettiEmessiData(LocalDate dataInizio, LocalDate dataFine){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query bigliettiEmessi = em.createNamedQuery("bigliettiEmessiData");
        bigliettiEmessi.setParameter("dataInizio", dataInizio);
        bigliettiEmessi.setParameter("dataFine", dataFine);
        et.commit();
        return (int)bigliettiEmessi.getSingleResult();
    }
//    Numero totale di biglietti emessi da un determinato punto di emissione
    public int bigliettiEmessiPuntoEmissione(PuntiDiEmissione puntiDiEmissione){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query bigliettiEmessi = em.createNamedQuery("bigliettiEmessiPuntoEmissione");
        bigliettiEmessi.setParameter("puntoEmissione", puntiDiEmissione);
        et.commit();
        return (int)bigliettiEmessi.getSingleResult();
    }
//    Numero totale di biglietti emessi da un determinato punto di emissione in un determinato periodo
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

//    Lista di Biglietti vidimati in base al mezzo passato
    public List<Biglietti> bigliettiVidimatiPerMezzo(Mezzi mezzo) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createNamedQuery("bigliettiVidimatiPerMezzo");
        query.setParameter("mezzo", mezzo);

        List<Biglietti> result = query.getResultList();

        et.commit();
        return result;
    }

//    Lista di Biglietti vidimati in base alla data di inizio e alla data di fine che si vuole controllare
    public List<Biglietti> bigliettiVidimatiPerTempo(LocalDate dataInizio, LocalDate dataFine) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createNamedQuery("bigliettiVidimatiPerTempo");
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);

        List<Biglietti> result = query.getResultList();

        et.commit();
        return result;
    }

//    Lista di Biglietti vidimati in base alla data di inizio e alla data di fine che si vuole controllare ed in base al mezzo
    public List<Biglietti> bigliettiVidimatiTotale(Mezzi mezzo, LocalDate dataInizio, LocalDate dataFine) {
        EntityTransaction et = em.getTransaction();
        et.begin();

        Query query = em.createNamedQuery("bigliettiVidimatiTotale");
        query.setParameter("mezzo", mezzo);
        query.setParameter("dataInizio", dataInizio);
        query.setParameter("dataFine", dataFine);

        List<Biglietti> result = query.getResultList();

        et.commit();
        return result;
    }


}

