package entities;

import java.util.Date;

public class Tessera {
    private int id;
    private String utente;

    private Date dataAcquisto;
    private Date dataScadenza;

    private String numeroTessera;

    private int abbonamento;

    public Tessera(int id, String utente, Date dataAcquisto, Date dataScadenza, String numeroTessera, int abbonamento) {
        this.id = id;
        this.utente = utente;
        this.dataAcquisto = dataAcquisto;
        this.dataScadenza = dataScadenza;
        this.numeroTessera = numeroTessera;
        this.abbonamento = abbonamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(String numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public int getAbbonamento() {
        return abbonamento;
    }

    public void setAbbonamento(int abbonamento) {
        this.abbonamento = abbonamento;
    }


    @Override
    public String toString() {
        return "Tessera{" + "id=" + id + ", utente=" + utente + ", dataAcquisto=" + dataAcquisto + ", dataScadenza=" + dataScadenza + ", numeroTessera=" + numeroTessera + ", abbonamento=" + abbonamento + '}';
    }

    public Date calcolaScadenza(Date dataAcquisto, int abbonamento) {
        Date dataScadenza = new Date();
        dataScadenza.setTime(dataAcquisto.getTime() + ((long) abbonamento * 30 * 24 * 60 * 60 * 1000));
        return dataScadenza;

    }

    public void rinnovaTessera(Date dataAcquisto, int abbonamento) {
        this.dataAcquisto = new Date();
        this.dataScadenza = calcolaScadenza(dataAcquisto, abbonamento);
    }

    public boolean verificaScadenza(Date dataScadenza) {
        Date dataOggi = new Date();
        return dataOggi.after(dataScadenza);
    }

        public void stampaTessera() {
            System.out.println("Tessera n°: " + this.numeroTessera);
            System.out.println("Utente: " + this.utente);
            System.out.println("Data acquisto: " + this.dataAcquisto);
            System.out.println("Data scadenza: " + this.dataScadenza);
            System.out.println("Abbonamento: " + this.abbonamento);

    }
}




























