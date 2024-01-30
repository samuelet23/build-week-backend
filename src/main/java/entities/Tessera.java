package entities;

import entities.sottoclassi.Abbonamenti;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tessera")
@NamedQuery(name ="validationTessera", query = "SELECT COUNT(t)  FROM Tessera t WHERE t.dataScadenza > :oggi AND t.numeroTessera = :numeroTessera")

public class Tessera {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "id_tessera")
    @SequenceGenerator(name = "id_tessera", initialValue = 1, allocationSize = 1)
    private int id;
    @OneToOne
    @JoinColumn(name = "utente_fk")
    private Utente utente;
    @Column(name = "data_acquisto")
    private LocalDate dataAcquisto;
    @Column(name = "data_scadenza")
    private LocalDate dataScadenza;

    @Column(name = "numero_tessera")
    private String numeroTessera;

    private boolean valida;

    @OneToMany(mappedBy = "tessera")
    private Set<Abbonamenti> abbonamenti = new HashSet<>();


    public Tessera(){}

    public Tessera(int id, Utente utente, LocalDate dataAcquisto, LocalDate dataScadenza, String numeroTessera, Set<Abbonamenti> abbonamenti) {
        this.id = id;
        this.utente = utente;
        this.dataAcquisto = dataAcquisto;
        this.dataScadenza = dataScadenza;
        this.numeroTessera = numeroTessera;
        this.abbonamenti = abbonamenti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public LocalDate getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(LocalDate dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
        this.dataScadenza = dataAcquisto.plusYears(1);
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza() {
        this.dataScadenza = this.dataScadenza.plusYears(1);
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public boolean isValida() {
        return valida;
    }


    public String getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(String numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public Set<Abbonamenti> getAbbonamenti() {
        return abbonamenti;
    }

    public void setAbbonamenti(Set<Abbonamenti> abbonamenti) {
        this.abbonamenti = abbonamenti;
    }

    @Override
    public String toString() {
        return "Tessera{" +
                "id=" + id +
                ", dataAcquisto=" + dataAcquisto +
                ", dataScadenza=" + dataScadenza +
                ", numeroTessera='" + numeroTessera + '\'' +
                ", abbonamenti=" + abbonamenti +
                '}';
    }



}
