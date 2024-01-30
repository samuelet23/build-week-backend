<<<<<<< HEAD
import dao.MezziDAO;
import entities.Mezzi;
import entities.type.TipoMezzo;
=======
import dao.TicketsDao;
import entities.Tickets;
import entities.sottoclassi.Abbonamenti;
import entities.sottoclassi.Biglietti;

import java.time.LocalDate;
>>>>>>> Samuele

public class Main {
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
        Biglietti b = new Biglietti();
        Abbonamenti a = new Abbonamenti();

        ticketsDao.aggiungi(b);
        ticketsDao.aggiungi(a);

        MezziDAO mezziDAO = new MezziDAO();

        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.AUTOBUS);
        m1.setIn_manutenzione(false);
        try {
            mezziDAO.aggiungi(m1);
            System.out.println("Mezzo" + m1 + " salvato con successo");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
