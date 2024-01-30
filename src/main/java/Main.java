import dao.TicketsDao;
import dao.UtenteDao;
import entities.*;
import entities.sottoclassi.Abbonamenti;
import entities.sottoclassi.Biglietti;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;


public class Main {
    private static final Logger errorLogger = LoggerFactory.getLogger("main_error");
    private static final Logger infoLogger = LoggerFactory.getLogger("main_info");
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
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
