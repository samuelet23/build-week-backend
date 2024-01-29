package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "manutenzioni")

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

}
