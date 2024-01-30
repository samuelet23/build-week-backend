package entities.sottoclassi;
import entities.PuntiDiEmissione;
import entities.Tessera;
import entities.Tickets;
import entities.type.Periodicita;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamenti extends Tickets {



    @ManyToOne
    @JoinColumn(name = "tessera_fk")
    private Tessera tessera;
    private Periodicita periodicita;
    private LocalDate scadenza;


    public Abbonamenti(){}

    public Abbonamenti(Tessera tessera, Periodicita periodicita, LocalDate scadenza) {
        this.tessera = tessera;
        this.periodicita = periodicita;
        this.scadenza = scadenza;
    }

    public Abbonamenti(int id, PuntiDiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo, Tessera tessera, Periodicita periodicita, LocalDate scadenza) {
        super(id, puntiDiEmissione, dataEmissione, valido, prezzo);
        this.tessera = tessera;
        this.periodicita = periodicita;
        this.scadenza = scadenza;
    }

    @Override
    public String toString() {
        return "Abbonamenti{" +
                "tessera=" + tessera +
                ", periodicita=" + periodicita +
                ", scadenza=" + scadenza +
                ", puntiDiEmissione=" + puntiDiEmissione +
                ", dataEmissione=" + dataEmissione +
                ", valido=" + valido +
                ", prezzo=" + prezzo +
                '}';
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }
}
