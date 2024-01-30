import dao.MezziDAO;
import dao.TratteDAO;
import entities.Mezzi;
import entities.Tratte;
import entities.type.TipoMezzo;
import dao.TicketsDao;
import entities.Tickets;
import entities.sottoclassi.Abbonamenti;
import entities.sottoclassi.Biglietti;

import java.sql.Time;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
        TratteDAO tratteDAO = new TratteDAO();
        MezziDAO mezziDAO = new MezziDAO();
        Biglietti b = new Biglietti();
        Abbonamenti a = new Abbonamenti();

        ticketsDao.aggiungi(b);
        ticketsDao.aggiungi(a);

        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.TRAM);
        m1.setIn_manutenzione(false);
        try {
            mezziDAO.aggiungi(m1);
        } catch (Exception e){
            e.getMessage();
        }


        Tratte tratta1 = new Tratte();
        tratta1.setData(LocalDate.now());
        tratta1.setNome("Pescara-Vasto");
        tratta1.setZona_partenza("Pescara");
        tratta1.setCapolinea("Vasto");
        tratta1.setTempo_medio(Time.valueOf("03:03:05"));
        tratta1.setMezzo(m1);

        try {
            tratteDAO.aggiungi(tratta1);
        } catch (Exception e){
            e.getMessage();
        }



    }
}
