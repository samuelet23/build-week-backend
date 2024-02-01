import dao.*;
import entities.type.*;
import entities.sottoclassi.*;
import entities.*;
import org.slf4j.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


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
        menu();
    }

    public static void menu(){
        System.out.println("Benvenuto in Trasporto Pubblico");
        do {
            System.out.println(" 1 - Utente , 2 - Rivenditore, 3 Gestione Mezzi, 4 Gestione tratte");
            System.out.println(" 0 - per uscire dal programma");
            int choice = scanner.nextInt();
            if (choice == 0){
                break;
            }
            choiceCheckerIntro(choice);
        } while (true);
    }

    //ZONA CHOICE CHECKER

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
    public static void choiceCheckerUtente(int choice){
        switch (choice){
            case 1: acquistaBiglietto();
                break;
            case 2 : acquistaAbbonamento();
                break;
            case 3 : acquistaTessera();
                break;
            default :
                System.out.println("Scelta sbagliata, riprova.");
        }
    }

    //ZONA MENÙ
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
                return;
            } else if (choice == 0){
                System.exit(0);
            }
            choiceCheckerUtente(choice);
        } while (true);
    }




    public static void menuRivenditore(){
        do {
            System.out.println("---- Menù Rivenditore ----");
            System.out.println("1 - Emetti Tessera ");
            System.out.println("2 - Acquista Abbonamento");
            System.out.println("3 - Acquista Tessera");
            System.out.println("9 - Tornare al menù precedente");
            System.out.println("0 - Uscire dal programma");
            int choice = scanner.nextInt();
            if (choice == 9 ){
                return;
            } else if (choice == 0){
                System.exit(0);
            }
            choiceCheckerUtente(choice);
        } while (true);
    }
    //Acquista abbonamento collegato al menu utente
    public static void acquistaAbbonamento(){
        PuntiDiEmissione puntoSelezionato = scegliPuntoEmissione();
        try {
            //funziona per chiedere all'utente l'id
            Utente utente = askIdUtente();
            //funziona per chiedere all'utente la periodicità
            Periodicita periodicita = askPeriodicita();
           do {
               periodicita = askPeriodicita();
           }while (periodicita == null);

            System.out.println("Sei sicuro di voler acquistare l'abbonamento per 50 euro da : " + puntoSelezionato.getNome() + "?  Y/N");
            String answer = scanner.next();
            if (answer.toLowerCase().equals("y") ) {
                Abbonamenti abbonamento = emissioneAbbonamento(utente,puntoSelezionato, periodicita);
                infoLogger.info( " emesso" + abbonamento );
                menuUtente();
            } else {
                System.out.println("Abbonamento Non Emesso - Stai per tornare indietro al menu utente");
                menuUtente();
            }
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }
    }
    //funziona per chiedere all'utente l'id
    public static Utente askIdUtente(){
        Utente utente = new Utente();
        System.out.println("Inserisci id utente");
        int choice = scanner.nextInt();
        try{
            utente = utenteDao.getById(choice);
            if (utente != null){
                infoLogger.info("Utente trovato");
            } else{
                System.out.println("Utente non trovato");
                askIdUtente();
            }
        }catch (Exception e){
            errorLogger.error(e.getMessage());
            askIdUtente();
        }
        return utente;
    }

    //funziona per chiedere all'utente la periodicità
    public static Periodicita askPeriodicita() {

        System.out.println("Insersci la durata dell'abbonamento:");
        System.out.println("1 - SETTIMANALE, 2 - MENSILE");
        int choice = scanner.nextInt();
        if (choice == 1) {
            return Periodicita.SETTIMANALE;
        } else if (choice == 2) {
            return Periodicita.MENSILE;
        } else {
            errorLogger.error("ERRORE: Il periodo scelto non esiste");
            return null;
        }

    }

    //Acquista tessera collegato al menu utente
    public static void acquistaTessera(){

    }


    //Acquista biglietto collegato al menu utente
    public static PuntiDiEmissione scegliPuntoEmissione(){
        List<PuntiDiEmissione> puntiAttivi = new ArrayList<>(puntiDiEmissioneDAO.getDistributoriInServizio());
        PuntiDiEmissione puntoSelezionato = null;
        do  {
            System.out.println("Punti Di Emissione Attivi Ora:");
            puntiAttivi.stream().forEach(e -> System.out.println(e));
            if (puntoSelezionato == null){
                System.out.println("Nessun punto selezionato");
            } else {
                System.out.println("Punto Selezionato Al momento:");
                System.out.println(puntoSelezionato);
            }
            System.out.println("Inserisci l'id del punto di emissione, inserisci 0 per confermare");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Hai selezionato : "+puntoSelezionato);
                System.out.println("Sei sicuro di voler continuare? Y/N");
                String answer = scanner.next();
                if (answer.toLowerCase().equals("y")) {
                    return puntoSelezionato;
                }else {
                    continue;
                }
            }
            try {
                puntoSelezionato = puntiAttivi.stream()
                                .filter(p -> p.getId() == choice)
                                        .findAny().get();
                infoLogger.info("Punto selezionato con successo");
            } catch (Exception e){
                errorLogger.error("Punto non in servizio");
            }
        } while (true);
    }


    public static void acquistaBiglietto(){
        PuntiDiEmissione puntoSelezionato = scegliPuntoEmissione();
        try {
            System.out.println("Sei sicuro di voler acquistare il biglietto per 3 euro da : " + puntoSelezionato.getNome() + "?  Y/N");
            String answer = scanner.next();
            if (answer.toLowerCase().equals("y") ) {
                Biglietti biglietto = emissioneBiglietto(puntoSelezionato);
                infoLogger.info( " emesso" + biglietto );
                menuUtente();
            } else {
                System.out.println("Biglietto Non Emesso - Stai per tornare indietro al menu utente");
                menuUtente();
            }
        } catch (Exception e){
            errorLogger.error(e.getMessage());
        }
    }




    //metodo per l'emissione di un biglietto
    public static Biglietti emissioneBiglietto(PuntiDiEmissione puntiDiEmissione){
        Biglietti biglietto = new Biglietti();
        biglietto.setValido(true);
        biglietto.setPuntiDiEmissione(puntiDiEmissione);
        biglietto.setDataEmissione(LocalDate.now());
        biglietto.setPrezzo(3);
        saveTickets(biglietto);
        return biglietto;
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
    public static Abbonamenti emissioneAbbonamento(Utente utente, PuntiDiEmissione puntiDiEmissione, Periodicita periodo) {
        Abbonamenti abbonamento = new Abbonamenti();
        System.out.println(tesseraDao.checkValidationTessera(utente));
        if ( tesseraDao.checkValidationTessera(utente)) {
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
        return abbonamento;
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


