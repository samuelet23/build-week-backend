package entities.sottoclassi;

import entities.PuntiEmissione;
import entities.Tessera;
import entities.Tickets;
import entities.type.Periodicità;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "abbonamenti")
public class Abbonamenti extends Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_abbonamenti")
    @SequenceGenerator(name = "id_abbonamenti", initialValue = 1, allocationSize = 1)
    private int id;

    private Tessera tessera;
    private Periodicità periodicita;
    private LocalDate scadenza;

    public Abbonamenti(int id, Tessera tessera, Periodicità periodicita, LocalDate scadenza) {
        this.id = id;
        this.tessera = tessera;
        this.periodicita = periodicita;
        this.scadenza = scadenza;
    }

    public Abbonamenti(PuntiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo, int id, Tessera tessera, Periodicità periodicita, LocalDate scadenza) {
        super(puntiDiEmissione, dataEmissione, valido, prezzo);
        this.id = id;
        this.tessera = tessera;
        this.periodicita = periodicita;
        this.scadenza = scadenza;
    }

    @Override
    public String toString() {
        return "Abbonamenti{" +
                "id=" + id +
                ", tessera=" + tessera +
                ", periodicita=" + periodicita +
                ", scadenza=" + scadenza +
                ", puntiDiEmissione=" + puntiDiEmissione +
                ", dataEmissione=" + dataEmissione +
                ", valido=" + valido +
                ", prezzo=" + prezzo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    public Periodicità getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicità periodicita) {
        this.periodicita = periodicita;
    }

    public LocalDate getScadenza() {
        return scadenza;
    }

    public void setScadenza(LocalDate scadenza) {
        this.scadenza = scadenza;
    }

    public Abbonamenti(){}
}
