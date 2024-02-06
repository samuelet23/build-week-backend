package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQuery(name = "bigliettiEmessiData", query = "SELECT COUNT(t) FROM Tickets t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine")
@NamedQuery(name = "bigliettiEmessiPuntoEmissione", query = "SELECT COUNT(t) FROM Tickets t WHERE t.puntiDiEmissione = :puntoEmissione")
@NamedQuery(name = "bigliettiEmessiPuntoEmissioneData", query = "SELECT COUNT(t) FROM Tickets t WHERE t.dataEmissione BETWEEN :dataInizio AND :dataFine AND t.puntiDiEmissione = :puntoEmissione")
public abstract class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_abbonamenti")
    @SequenceGenerator(name = "id_abbonamenti", initialValue = 1, allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "punto_di_emissione_fk")
    protected PuntiDiEmissione puntiDiEmissione;
    @Column(name = "data_emissione")
    protected LocalDate dataEmissione;

    protected boolean valido;
    protected int prezzo;

    public Tickets(){}

    public Tickets(int id, PuntiDiEmissione puntiDiEmissione, LocalDate dataEmissione, boolean valido, int prezzo) {
        this.id = id;
        this.puntiDiEmissione = puntiDiEmissione;
        this.dataEmissione = dataEmissione;
        this.valido = valido;
        this.prezzo = prezzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PuntiDiEmissione getPuntiDiEmissione() {
        return puntiDiEmissione;
    }

    public void setPuntiDiEmissione(PuntiDiEmissione puntiDiEmissione) {
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
