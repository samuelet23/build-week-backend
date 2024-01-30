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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_abbonamenti")
    @SequenceGenerator(name = "id_abbonamenti", initialValue = 1, allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tessera_fk")
    private Tessera tessera;
    private Periodicita periodicita;
    private LocalDate scadenza;

    public Abbonamenti(int id, Tessera tessera, Periodicita periodicita, LocalDate scadenza) {
        this.id = id;
        this.tessera = tessera;
        this.periodicita = periodicita;
        this.scadenza = scadenza;
    }

    public Abbonamenti(PuntiDiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo, int id, Tessera tessera, Periodicita periodicita, LocalDate scadenza) {
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

    public Abbonamenti(){}
}
