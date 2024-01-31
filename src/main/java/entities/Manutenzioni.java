package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "manutenzioni")
@NamedQuery(name = "tracciaPeriodoManutenzione", query = "SELECT mt FROM Manutenzioni mt WHERE mt.mezzo = :mezzo AND (mt.data_inizio BETWEEN :dataInizio AND :dataFine OR mt.data_fine BETWEEN :dataInizio AND :dataFine)")
public class Manutenzioni {
@Id
@GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "id_manutenzioni")
@SequenceGenerator(name = "id_manutenzioni", initialValue = 1, allocationSize = 1)
    private int id;

private LocalDate data_inizio;
private LocalDate data_fine;
@ManyToOne
@JoinColumn(name = "mezzo_fk")
private Mezzi mezzo;

    @Override
    public String toString() {
        return "Manutenzioni{" +
                "id=" + id +
                ", data_inizio=" + data_inizio +
                ", data_fine=" + data_fine +
                ", mezzo=" + mezzo +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(LocalDate data_inizio) {
        this.data_inizio = data_inizio;
    }

    public LocalDate getData_fine() {
        return data_fine;
    }

    public void setData_fine(LocalDate data_fine) {
        this.data_fine = data_fine;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    public Manutenzioni(LocalDate data_inizio, LocalDate data_fine, Mezzi mezzo) {
        this.data_inizio = data_inizio;
        this.data_fine = data_fine;
        this.mezzo = mezzo;
    }

    public Manutenzioni() {
    }
}
