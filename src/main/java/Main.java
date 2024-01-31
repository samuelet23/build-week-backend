import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import ch.qos.logback.core.encoder.EchoEncoder;
import dao.*;
import entities.type.*;
import entities.sottoclassi.*;
import entities.*;
import org.slf4j.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;


public class Main {
    private static final Logger errorLogger = LoggerFactory.getLogger("main_error");
    private static final Logger infoLogger = LoggerFactory.getLogger("main_info");
    private static final ManutenzioniDAO manutenzioniDAO = new ManutenzioniDAO();
    private static final TratteDAO tratteDAO = new TratteDAO();
    private static final PuntiDiEmissioneDAO puntiDiEmissioneDAO = new PuntiDiEmissioneDAO();
    private static final MezziDAO mezziDAO = new MezziDAO();
    private static final TesseraDao tesseraDao = new TesseraDao();
    private static final TicketsDao ticketsDao = new TicketsDao();
    private static final UtenteDao utenteDao = new UtenteDao();
    public static void main(String[] args) {


        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.TRAM);
        m1.setIn_manutenzione(false);
        saveMezzo(m1);



        PuntiDiEmissione d = new DistributoriAutomatici();
        ((DistributoriAutomatici)d).setIn_servizio(true);
        d.setCitta("Milano");
        d.setNome("BuyHere");
        savePunto(d);

        Utente utente = new Utente();
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataNascita(LocalDate.of(2000,Month.APRIL, 15 ));

        saveUtente(utente);

        emissioneBiglietto(d);


        Tessera tessera = new Tessera();
        tessera.setDataAcquisto(LocalDate.of(2022,Month.DECEMBER, 12));
        tessera.setUtente(utente);

        saveTessera(tessera);

        Biglietti b = new Biglietti();

        b.setPrezzo(20);
        b.setValido(true);
        b.setDataEmissione(LocalDate.of(2024, Month.FEBRUARY, 2));
        b.setPuntiDiEmissione(d);


        saveTickets(b);

        vidimaBiglietto(m1,b);

        Tratte tratta1 = new Tratte();
        tratta1.setData(LocalDate.now());
        tratta1.setNome("Pescara-Vasto");
        tratta1.setZona_partenza("Pescara");
        tratta1.setCapolinea("Vasto");
        tratta1.setTempo_medio(Time.valueOf("03:03:05"));
        saveTratta(tratta1,m1);

        Manutenzioni man1 = new Manutenzioni();
        man1.setData_inizio(LocalDate.now());
        man1.setData_fine(man1.getData_inizio().plusWeeks(2));
	    saveManutenzioni(man1, m1);
        toggleStatusDistributore((DistributoriAutomatici) d);

        try {
            emissioneAbbonamento(utente, d, Periodicita.MENSILE);
            infoLogger.info("Lancio emissioneAbbonamento effettuato");

        } catch (Exception e){
            errorLogger.error("Errore nel lancio emissioneAbbonamento");
            e.printStackTrace();
        }

        try {
            rinnovaTessera(tessera);
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }

        try {
            System.out.println(nTrattaPerMezzo(16));
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }

        try{
            manutenzioniDAO.tracciaMezzoInManutenzione(m1, LocalDate.now(), LocalDate.now().plusWeeks(1)).stream()
                    .forEach(m -> System.out.println(m));
            infoLogger.info("Il mezzo è stato in manutenzione in questa data");
        }catch (Exception e){
            errorLogger.error("Il mezzo non è stato in manutenzione in questa data");
        }

//        manutenzioniDAO.selectAllMezzi().stream().forEach(mezzi -> System.out.println(mezzi));
        manutenzioniDAO.getMezziInManutenzione().stream().forEach(mezzi -> System.out.println(mezzi));
//        manutenzioniDAO.getMezziInServizio().stream().forEach(mezzi -> System.out.println(mezzi));


    }

    public static void emissioneBiglietto(PuntiDiEmissione puntiDiEmissione){
        Biglietti biglietto = new Biglietti();
        biglietto.setValido(true);
        biglietto.setPuntiDiEmissione(puntiDiEmissione);
        biglietto.setDataEmissione(LocalDate.now());
        biglietto.setPrezzo(3);
    }
    
    public static void emissioneAbbonamento(Utente utente, PuntiDiEmissione puntiDiEmissione, Periodicita periodo) {
        utenteDao.refresh(utente);
        System.out.println(tesseraDao.checkValidationTessera(utente));
        if ( tesseraDao.checkValidationTessera(utente)) {
            Abbonamenti abbonamento = new Abbonamenti();
            abbonamento.setTessera(utente.getNumeroTessera());
            abbonamento.setValido(true);
            abbonamento.setPrezzo(50);
            abbonamento.setPeriodicita(periodo);
            abbonamento.setDataEmissione(LocalDate.now());
            if (abbonamento.getPeriodicita() == Periodicita.MENSILE) {
                abbonamento.setScadenza(abbonamento.getDataEmissione().plusMonths(1));
            } else if (abbonamento.getPeriodicita() == Periodicita.SETTIMANALE){
                abbonamento.setScadenza(abbonamento.getDataEmissione().plusWeeks(1));
            } else {
                errorLogger.error("l'abbonamento incredibilmente non ha la periodicità richiesta, ci scusiamo per il disagio!");
            }
            abbonamento.setPuntiDiEmissione(puntiDiEmissione);
            infoLogger.info("Abbonamento emesso correttamente");
            saveTickets(abbonamento);
        } else {
            errorLogger.error("Abbonamento non emesso : Errore");
        }
    }
    public static void saveManutenzioni(Manutenzioni man, Mezzi m){
            man.setMezzo(m);
            manutenzioniDAO.setInManutenzione(m);
            try {
                manutenzioniDAO.aggiungi(man);
                infoLogger.info("Aggiunta manutenzione riuscita con successo!");
            } catch (Exception e){
                e.getMessage();
                errorLogger.error("Aggiunta della manutenzione non riuscita");
            }
    }

    public static void saveTratta(Tratte tratta, Mezzi m){
       tratta.setMezzo(m);
       try {
           tratteDAO.aggiungi(tratta);
           infoLogger.info("Aggiunta tratta riuscita con successo!");
       } catch (Exception e){
        e.getMessage();
        errorLogger.error("Aggiunta della tratta non riuscita");
       }
    }
    public static void savePunto (PuntiDiEmissione punto){
        try {
            puntiDiEmissioneDAO.aggiungi(punto);
            infoLogger.info("Punto di emissione" + punto + "aggiunto");
        } catch (Exception e){
            errorLogger.error("Punto di emissione" + punto + " non aggiunto : ERRORE");
        }
    }
    public static void saveMezzo (Mezzi m){
        try {
            mezziDAO.aggiungi(m);
            infoLogger.info("Aggiunta del mezzo riuscita con successo!");
        } catch (Exception e){
            e.getMessage();
            errorLogger.error("Aggiunta di mezzo non riuscita");
        }
    }

    public static void saveTessera (Tessera tessera){
        try {
        tesseraDao.aggiungi(tessera);
        infoLogger.info("Tessera aggiunta");
        } catch (Exception e){
        errorLogger.error("Tessera non aggiunta: ERRORE");
        }
    }
    public static void saveTickets (Tickets tickets){
        try {
            ticketsDao.aggiungi(tickets);
            infoLogger.info("Tickets aggiunto");
        } catch (Exception e){
            errorLogger.error("Tickets non aggiunto: ERRORE");
        }
    }

    public static void saveUtente (Utente utente) {
        try{
            utenteDao.aggiungi(utente);
            infoLogger.info("Utente aggiunto correttamente");
        }catch (Exception e){
            errorLogger.error("ERRORE: Utente non aggiunto");
        }
    }

    public static void vidimaBiglietto (Mezzi mezzo, Biglietti biglietto){
        biglietto.setValido(false);
        biglietto.setDataVidimazione(LocalDate.now());
        biglietto.setMezzo(mezzo);
        List <Biglietti> lista = mezzo.getBiglietti();
        lista.add(biglietto);
        mezzo.setBiglietti(lista);
        saveTickets(biglietto);
        saveMezzo(mezzo);
    }

    public static void toggleStatusDistributore (DistributoriAutomatici distributore){
        try {
            distributore.setIn_servizio(!distributore.isIn_servizio());
            puntiDiEmissioneDAO.aggiungi(distributore);
            infoLogger.info("Distributore aggiornato!");
        } catch (Exception e){
            errorLogger.error("Distributore non aggiornato: ERRORE");
        }
    }

    public static void rinnovaTessera (Tessera tessera){
            if (tessera.getDataScadenza().isBefore(LocalDate.now())){
                tessera.setDataScadenza();
                saveTessera(tessera);
                infoLogger.info("Tessera rinnovata!");
            } else {
                errorLogger.error("Tessera non ancora scaduta");
            }
    }

    public static long nTrattaPerMezzo (int id){
        if (mezziDAO.getById(id).equals(null)){
            errorLogger.error("Mezzo non trovato");
        }
        return tratteDAO.trattaPerMezzo(id);
    }

}


