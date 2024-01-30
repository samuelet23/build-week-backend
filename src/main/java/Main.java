import dao.TicketsDao;
import entities.Tickets;
import entities.sottoclassi.Abbonamenti;
import entities.sottoclassi.Biglietti;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
        Biglietti b = new Biglietti();
        Abbonamenti a = new Abbonamenti();

        ticketsDao.aggiungi(b);
        ticketsDao.aggiungi(a);

    }
}
