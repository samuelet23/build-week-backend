package entities;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Table(name = "tratte")
@NamedQuery(name="trattaPerMezzo", query = "SELECT COUNT(t) FROM Tratte t WHERE t.mezzo.id = :id")
public class Tratte {
    @Id
    @GeneratedValue ( strategy = GenerationType.SEQUENCE, generator = "id_tratte")
    @SequenceGenerator(name = "id_tratte", initialValue = 1, allocationSize = 1)
    private int id;

    private String nome;
    private LocalDate data;
    private String zona_partenza;

    private String capolinea;
    private Time tempo_medio;
    private Time tempo_effettivo;

    @ManyToOne
    @JoinColumn(name = "mezzo_fk")
    private Mezzi mezzo;

    public Tratte() {
    }

    public Tratte(String nome, LocalDate data, String zona_partenza, String capolinea, Time tempo_medio, Time tempo_effettivo, Mezzi mezzo) {
        this.nome = nome;
        this.data = data;
        this.zona_partenza = zona_partenza;
        this.capolinea = capolinea;
        this.tempo_medio = tempo_medio;
        this.tempo_effettivo = tempo_effettivo;
        this.mezzo = mezzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getZona_partenza() {
        return zona_partenza;
    }

    public void setZona_partenza(String zona_partenza) {
        this.zona_partenza = zona_partenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public Time getTempo_medio() {
        return tempo_medio;
    }

    public void setTempo_medio(Time tempo_medio) {
        this.tempo_medio = tempo_medio;
    }

    public Time getTempo_effettivo() {
        return tempo_effettivo;
    }

    public void setTempo_effettivo(Time tempo_effettivo) {
        this.tempo_effettivo = tempo_effettivo;
    }

    public Mezzi getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzi mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Tratte{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", data=" + data +
                ", zona_partenza='" + zona_partenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempo_medio=" + tempo_medio +
                ", tempo_effettivo=" + tempo_effettivo +
                ", mezzo=" + mezzo +
                '}';
    }
}
