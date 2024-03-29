import dao.*;
import entities.type.*;
import entities.sottoclassi.*;
import entities.*;
import org.slf4j.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Scanner;


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
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {


        //creazione mezzo
        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.TRAM);
        m1.setIn_manutenzione(false);
        saveMezzo(m1);



        //creazione punto di emissione
        PuntiDiEmissione d = new DistributoriAutomatici();
        ((DistributoriAutomatici)d).setIn_servizio(true);
        d.setCitta("Milano");
        d.setNome("BuyHere");
        savePunto(d);

        //creazione utente
        Utente utente = new Utente();
        utente.setNome("Mario");
        utente.setCognome("Rossi");
        utente.setDataNascita(LocalDate.of(2000,Month.APRIL, 15 ));

        //salvataggio utente
        saveUtente(utente);

        //emissione biglietto
        emissioneBiglietto(d);


        //creazione Tessera
        Tessera tessera = new Tessera();
        tessera.setDataAcquisto(LocalDate.of(2022,Month.DECEMBER, 12));
        tessera.setUtente(utente);

        //salvataggio tessera
        saveTessera(tessera);

        //creazione biglietto
        Biglietti b = new Biglietti();
        b.setPrezzo(20);
        b.setValido(true);
        b.setDataEmissione(LocalDate.of(2024, Month.FEBRUARY, 2));
        b.setPuntiDiEmissione(d);

        //salvataggio tickets
        saveTickets(b);

        //vidimazione biglietto
        vidimaBiglietto(m1,b);

        //creazione tratta
        Tratte tratta1 = new Tratte();
        tratta1.setData(LocalDate.now());
        tratta1.setNome("Pescara-Vasto");
        tratta1.setZona_partenza("Pescara");
        tratta1.setCapolinea("Vasto");
        tratta1.setTempo_medio(Time.valueOf("03:03:05"));
        tratta1.setTempo_effettivo(Time.valueOf("02:02:05"));
        saveTratta(tratta1,m1);

//          commentato in quanto avevamo bisogno di mezzi non in manutezione

//        Manutenzioni man1 = new Manutenzioni();
//        man1.setData_inizio(LocalDate.now());
//        man1.setData_fine(man1.getData_inizio().plusWeeks(2));
//	    saveManutenzioni(man1, m1);
        toggleStatusDistributore((DistributoriAutomatici) d);

        //test metodo per emissione abbonamento
        try {
            emissioneAbbonamento(utente, d, Periodicita.MENSILE);
            infoLogger.info("Lancio emissioneAbbonamento effettuato");
        } catch (Exception e){
            errorLogger.error("Errore nel lancio emissioneAbbonamento");
            e.printStackTrace();
        }

        //test metodo rinnovo tessera
        try {
            rinnovaTessera(tessera);
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }

        //test metodo conta tratte fatte da un mezzo(id)
        try {
            System.out.println(nTrattaPerMezzo(16));
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }

        //test metodo per controllare se un mezzo era in manutenzione in un range di data
        try{
            manutenzioniDAO.tracciaMezzoInManutenzione(m1, LocalDate.now(), LocalDate.now().plusWeeks(1)).stream()
                    .forEach(m -> System.out.println(m));
            infoLogger.info("Il mezzo è stato in manutenzione in questa data");
        }catch (Exception e){
            errorLogger.error("Il mezzo non è stato in manutenzione in questa data");
        }


        //test per avere lista dei mezzi in manutenzione
        try {
            List<Mezzi> mezziInManutenzione = manutenzioniDAO.getMezziInManutenzione();
            if (mezziInManutenzione.isEmpty()){
                throw new Exception();
            }
            mezziInManutenzione.stream().forEach(m -> System.out.println(m));
            infoLogger.info("Lista mezzi in manutenzione acquisita");
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
        }

        //test per avere lista dei mezzi in servizio
        try {
            List<Mezzi> mezziInServizio = manutenzioniDAO.getMezziInManutenzione();
            if (mezziInServizio.isEmpty()){
                throw new Exception();
            }
            mezziInServizio.stream().forEach(m -> System.out.println(m));
            infoLogger.info("Lista mezzi in servizio acquisita");
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
        }

        //test per avere lista dei biglietti vidimati per mezzo
        try {
            List<Biglietti> bigliettiPerMezzo = ticketsDao.bigliettiVidimatiPerMezzo(m1);
            if (bigliettiPerMezzo.isEmpty()){
                throw new Exception();
            }
            bigliettiPerMezzo.stream().forEach(m -> System.out.println(m));
            infoLogger.info("Lista biglietti vidimati per mezzo  acquisita");
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
        }

        System.out.println("-----------------------------------");

        //test per avere lista dei biglietti vidimati per data
        try {
            List<Biglietti> bigliettiPerData = ticketsDao.bigliettiVidimatiPerTempo(LocalDate.now(), LocalDate.now().plusDays(1));
            if (bigliettiPerData.isEmpty()){
                throw new Exception();
            }
            bigliettiPerData.stream().forEach(m -> System.out.println(m));
            infoLogger.info("Lista biglietti vidimati per data  acquisita");
        } catch (Exception e) {
            errorLogger.error(e.getMessage());
        }

        //testo metodo tempo effettivo per tratta
        System.out.println(tratteDAO.tempoEffettivoTratta(tratta1));


//        menu();





    }

    public static void menu(){
        System.out.println("Benvenuto in Trasporto Pubblico");
        do {
            System.out.println("Sei un utente/rivenditore? 1 - Utente , 2 - Rivenditore");
            int choice = scanner.nextInt();
            if (choice == 0){
                break;
            }
            choiceCheckerIntro(choice);
        } while (true);
    }


    public static void choiceCheckerIntro(int choice){

        switch (choice){
            case 1: menuUtente();
            break;
            case 2 : menuRivenditore();
            break;
            default :
                System.out.println("Scelta sbagliata, riprova.");
        }
    }

    public static void menuUtente(){
        do {
            System.out.println("---- Menù Utente ----");
            System.out.println("1 - Acquista Biglietto");
            System.out.println("2 - Acquista Abbonamento");
            System.out.println("3 - Acquista Tessera");
            System.out.println("9 - Tornare al menù precedente");
            System.out.println("0 - Uscire dal programma");
            int choice = scanner.nextInt();
            if (choice == 9 ){
                menu();
            } else if (choice == 0){
                return;
            }
        } while (true);
    }

    public static void menuRivenditore(){
        System.out.println("menu rivenditore");
    }

    //metodo per l'emissione di un biglietto
    public static void emissioneBiglietto(PuntiDiEmissione puntiDiEmissione){
        Biglietti biglietto = new Biglietti();
        biglietto.setValido(true);
        biglietto.setPuntiDiEmissione(puntiDiEmissione);
        biglietto.setDataEmissione(LocalDate.now());
        biglietto.setPrezzo(3);
    }

    //metodo per vidimare il biglietto
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

    //metodo che emette un abbonamento controllando che l'utente abbia una tessera valida
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
    //metodo per aggiungere o aggiornare una manutenzione
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

    //metodo per aggiungere o aggiornare una tratta e per di conseguenza settare un mezzo
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
    //metodo per aggiungere o aggiornare un punto di emissione
    public static void savePunto (PuntiDiEmissione punto){
        try {
            puntiDiEmissioneDAO.aggiungi(punto);
            infoLogger.info("Punto di emissione" + punto + "aggiunto");
        } catch (Exception e){
            errorLogger.error("Punto di emissione" + punto + " non aggiunto : ERRORE");
        }
    }
    //metodo per aggiungere o aggiornare un mezzo
    public static void saveMezzo (Mezzi m){
        try {
            mezziDAO.aggiungi(m);
            infoLogger.info("Aggiunta del mezzo riuscita con successo!");
        } catch (Exception e){
            e.getMessage();
            errorLogger.error("Aggiunta di mezzo non riuscita");
        }
    }

    //metodo per aggiungere o aggiornare una tessera
    public static void saveTessera (Tessera tessera){
        try {
        tesseraDao.aggiungi(tessera);
        infoLogger.info("Tessera aggiunta");
        } catch (Exception e){
        errorLogger.error("Tessera non aggiunta: ERRORE");
        }
    }
    //metodo per aggiungere o aggiornare un ticket
    public static void saveTickets (Tickets tickets){
        try {
            ticketsDao.aggiungi(tickets);
            infoLogger.info("Tickets aggiunto");
        } catch (Exception e){
            errorLogger.error("Tickets non aggiunto: ERRORE");
        }
    }

    //metodo per aggiungere o aggiornare un utente
    public static void saveUtente (Utente utente) {
        try{
            utenteDao.aggiungi(utente);
            infoLogger.info("Utente aggiunto correttamente");
        }catch (Exception e){
            errorLogger.error("ERRORE: Utente non aggiunto");
        }
    }


    //metodo per cambiare lo status del distributore nel suo contrario
    public static void toggleStatusDistributore (DistributoriAutomatici distributore){
        try {
            distributore.setIn_servizio(!distributore.isIn_servizio());
            puntiDiEmissioneDAO.aggiungi(distributore);
            infoLogger.info("Distributore aggiornato!");
        } catch (Exception e){
            errorLogger.error("Distributore non aggiornato: ERRORE");
        }
    }

    //metodo per rinnovare la tessera con il controllo della scadenza
    public static void rinnovaTessera (Tessera tessera){
            if (tessera.getDataScadenza().isBefore(LocalDate.now())){
                tessera.setDataScadenza();
                saveTessera(tessera);
                infoLogger.info("Tessera rinnovata!");
            } else {
                errorLogger.error("Tessera non ancora scaduta");
            }
    }

    //quante volte un mezzo fa una Tratta
    public static long nTrattaPerMezzo (int id){
        if (mezziDAO.getById(id).equals(null)){
            errorLogger.error("Mezzo non trovato");
        }
        return tratteDAO.trattaPerMezzo(id);
    }

}


