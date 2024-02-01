package dao;

import entities.DistributoriAutomatici;
import entities.PuntiDiEmissione;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PuntiDiEmissione> getAll(){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createNamedQuery("selectAllPunti");
        List<PuntiDiEmissione> lista = query.getResultList();
        et.commit();
        return lista;
    }
    public List<PuntiDiEmissione> getDistributoriInServizio(){
        EntityTransaction et = em.getTransaction();
        et.begin();
        Query query = em.createNamedQuery("getDistributoriAttivi");
        List<PuntiDiEmissione> onservice = query.getResultList();
        et.commit();
        List<PuntiDiEmissione> all = getAll();
        return all.stream()
                .filter(p -> onservice.contains(p))
                .collect(Collectors.toList());
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
