package entities.sottoclassi;

import entities.Mezzi;
import entities.PuntiDiEmissione;
import entities.Tickets;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietti extends Tickets {

    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "mezzo_fk")
    private Mezzi mezzo;

    public Biglietti(){}

    public Biglietti(LocalDate dataVidimazione, Mezzi mezzo) {
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }

    public Biglietti(int id, PuntiDiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo, LocalDate dataVidimazione, Mezzi mezzo) {
        super(id, puntiDiEmissione, dataEmissione, valido, prezzo);
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Biglietti{" +
                "dataVidimazione=" + dataVidimazione +
                ", mezzo=" + mezzo +
                ", puntiDiEmissione=" + puntiDiEmissione +
                ", dataEmissione=" + dataEmissione +
                ", valido=" + valido +
                ", prezzo=" + prezzo +
                '}';
    }
}
