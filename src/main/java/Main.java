import jakarta.persistence.Query;
import ch.qos.logback.core.encoder.EchoEncoder;
import dao.*;
import entities.type.*;
import entities.*;
import entities.sottoclassi.*;
import org.slf4j.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.Set;


public class Main {
    private static final Logger errorLogger = LoggerFactory.getLogger("main_error");
    private static final Logger infoLogger = LoggerFactory.getLogger("main_info");
    public static void main(String[] args) {
        TicketsDao ticketsDao = new TicketsDao();
        TratteDAO tratteDAO = new TratteDAO();
        MezziDAO mezziDAO = new MezziDAO();
        ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO();
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
        try{
        utenteDao.aggiungi(utente);
        infoLogger.info("Utente aggiunto correttamente");
        }catch (Exception e){
            errorLogger.error("ERRORE: Utente non aggiunto");
        }

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

        try{
        ticketsDao.aggiungi(b);
            infoLogger.info("Il biglietto è stato aggiunto correttamente");
        }catch (Exception e){
            errorLogger.error("ERRORE: Il biglietto non è stato aggiunto");
        }

        a.setPrezzo(100);
        a.setPeriodicita(Periodicita.MENSILE);
        a.setValido(true);
        LocalDate scadenzaMensile = LocalDate.now().plusMonths(1);
        a.setScadenza(scadenzaMensile);
        a.setPuntiDiEmissione(d);
        tessera.setAbbonamenti(Set.of(a));

        tesseraDao.aggiungi(tessera);
        a.setTessera(tessera);

        try{
        ticketsDao.aggiungi(a);
            infoLogger.info("Il ticket è stato aggiunto correttamente");
        }catch (Exception e ){
            errorLogger.error("ERRORE: il ticket non è stato aggiunto");
        }

        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.TRAM);
        m1.setIn_manutenzione(false);
        try {
            mezziDAO.aggiungi(m1);
            infoLogger.info("Aggiunta del mezzo riuscita con successo!");
        } catch (Exception e){
            e.getMessage();
            errorLogger.error("Aggiunta di mezzo non riuscita");
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
            infoLogger.info("Aggiunta tratta riuscita con successo!");
        } catch (Exception e){
            e.getMessage();
            errorLogger.error("Aggiunta della tratta non riuscita");
        }


        Manutenzioni man1 = new Manutenzioni();
        man1.setData_inizio(LocalDate.now());
        man1.setData_fine(man1.getData_inizio().plusWeeks(2));
        man1.setMezzo(m1);
        manutenzioniDAO.setInManutenzione(m1);

        try {
            manutenzioniDAO.aggiungi(man1);
            infoLogger.info("Aggiunta manutenzione riuscita con successo!");
        } catch (Exception e){
            e.getMessage();
            errorLogger.error("Aggiunta della manutenzione non riuscita");
        }


    }
}
