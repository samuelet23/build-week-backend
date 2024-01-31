package dao;

import entities.Manutenzioni;
import entities.Mezzi;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public List<Manutenzioni> tracciaMezzoInManutenzione(Mezzi mezzo, LocalDate dataInizio, LocalDate dataFine){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query tracciaInManutenzione = em.createNamedQuery("tracciaPeriodoManutenzione");
        tracciaInManutenzione.setParameter("mezzo", mezzo)
                .setParameter("dataInizio", dataInizio)
                .setParameter("dataFine", dataFine);

        return (List<Manutenzioni>) tracciaInManutenzione.getResultList();
    }
    public Manutenzioni getById(int id){
        return em.find(Manutenzioni.class, id);
    }

    public List<Mezzi> selectAllMezzi(){
        EntityTransaction et = em.getTransaction();
        et.begin();
         List<Mezzi> list = em.createNamedQuery("selectAllMezzi").getResultList();
         et.commit();
         return  list;
    }

    public List<Mezzi> getMezziInManutenzione(){
        EntityTransaction et = em.getTransaction();
        LocalDate oggi = LocalDate.now();
        et.begin();
        Query query = em.createQuery("tracciaMezziInManutenzione");
        query.setParameter("oggi", oggi);
       List<Mezzi> lista = (List<Mezzi>) query.getResultList();
       et.commit();
       return lista;
    }
    public List<Mezzi> getMezziInServizio(){
        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Mezzi> allMezzi = selectAllMezzi();
        List<Mezzi> mezziInManutenzione = getMezziInManutenzione();
        et.commit();
        return allMezzi
                .stream()
                .filter(m -> !mezziInManutenzione.contains(m))
                .collect(Collectors.toList());
    }

}
