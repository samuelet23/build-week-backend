package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tickets {
    @Column(name = "punti_di_emissione")
    protected PuntiEmissione puntiDiEmissione;
    @Column(name = "data_emissione")
    protected LocalDate dataEmissione;

    protected boolean valido;
    protected int prezzo;

    public Tickets(){}
    public Tickets(PuntiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo) {
        this.puntiDiEmissione = puntiDiEmissione;
        this.dataEmissione = dataEmissione;
        this.valido = valido;
        this.prezzo = prezzo;
    }

    public PuntiEmissione getPuntiDiEmissione() {
        return puntiDiEmissione;
    }

    public void setPuntiDiEmissione(PuntiEmissione puntiDiEmissione) {
        this.puntiDiEmissione = puntiDiEmissione;
    }

    public LocalDate getDataEmissione() {
        return dataEmissione;
    }

    public void setDataEmissione(LocalDate dataEmissione) {
        this.dataEmissione = dataEmissione;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }
}
