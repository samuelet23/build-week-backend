package entities.sottoclassi;

import entities.Mezzi;
import entities.PuntiDiEmissione;
import entities.Tickets;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "biglietti")
public class Biglietti extends Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_biglietti")
    @SequenceGenerator(name = "id_biglietti", initialValue = 1, allocationSize = 1)
    private int id;
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "mezzo_fk")
    private Mezzi mezzo;

    public Biglietti(){}

    public Biglietti(int id, LocalDate dataVidimazione, Mezzi mezzo) {
        this.id = id;
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }

    public Biglietti(PuntiDiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo, int id, LocalDate dataVidimazione, Mezzi mezzo) {
        super(puntiDiEmissione, dataEmissione, valido, prezzo);
        this.id = id;
        this.dataVidimazione = dataVidimazione;
        this.mezzo = mezzo;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
    }


    @Override
    public String toString() {
        return "Biglietti{" +
                "id=" + id +
                ", dataVidimazione=" + dataVidimazione +
                ", mezzo=" + mezzo +
                ", puntiDiEmissione=" + puntiDiEmissione +
                ", dataEmissione=" + dataEmissione +
                ", valido=" + valido +
                ", prezzo=" + prezzo +
                '}';
    }
}
