import dao.PuntiDiEmissioneDAO;
import dao.TesseraDao;
import dao.TicketsDao;
import dao.UtenteDao;
import entities.*;
import entities.sottoclassi.Abbonamenti;
import entities.sottoclassi.Biglietti;
import entities.type.Periodicita;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
        PuntiDiEmissioneDAO puntiDiEmissioneDAO = new PuntiDiEmissioneDAO();
        TesseraDao tesseraDao = new TesseraDao();
        UtenteDao utenteDao = new UtenteDao();

        DistributoriAutomatici d = new DistributoriAutomatici();
        d.setIn_servizio(true);
        d.setCitta("Milano");
        d.setNome("BuyHere");

        puntiDiEmissioneDAO.aggiungi(d);

        Utente utente = new Utente();
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataNascita(LocalDate.of(2000,Month.APRIL, 15 ));
        utenteDao.aggiungi(utente);

        Tessera tessera = new Tessera();
        tessera.setNumeroTessera("202040");
        tessera.setDataAcquisto(LocalDate.of(2024,Month.FEBRUARY, 12));
        tessera.setDataScadenza(LocalDate.of(2025,Month.FEBRUARY, 12));
        tessera.setUtente(utente);

        Biglietti b = new Biglietti();
        Abbonamenti a = new Abbonamenti();

        b.setPrezzo(20);
        b.setValido(true);
        b.setDataEmissione(LocalDate.of(2024, Month.FEBRUARY, 2));
        b.setPuntiDiEmissione(d);

        ticketsDao.aggiungi(b);

        a.setPrezzo(100);
        a.setPeriodicita(Periodicita.MENSILE);
        a.setValido(true);
        LocalDate scadenzaMensile = LocalDate.now().plusMonths(1);
        a.setScadenza(scadenzaMensile);
        a.setPuntiDiEmissione(d);
        tessera.setAbbonamenti(Set.of(a));

        tesseraDao.aggiungi(tessera);
        a.setTessera(tessera);


        ticketsDao.aggiungi(a);


    }
}
