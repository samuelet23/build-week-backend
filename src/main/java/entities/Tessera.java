package entities;

import entities.sottoclassi.Abbonamenti;
import jakarta.persistence.*;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tessera")
public class Tessera {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "id_tessera")
    @SequenceGenerator(name = "id_tessera", initialValue = 1, allocationSize = 1)
    private int id;
    @OneToOne
    @JoinColumn(name = "utente_fk")
    private Utente utente;
    @Column(name = "data_acquisto")
    private Date dataAcquisto;
    @Column(name = "data_scadenza")
    private Date dataScadenza;

    @Column(name = "numero_tessera")
    private String numeroTessera;

    @OneToMany(mappedBy = "tessera")
    private Set<Abbonamenti> abbonamenti;


    public Tessera(){}

    public Tessera(int id, Utente utente, Date dataAcquisto, Date dataScadenza, String numeroTessera, Set<Abbonamenti> abbonamenti) {
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

    public Date getDataAcquisto() {
        return dataAcquisto;
    }

    public void setDataAcquisto(Date dataAcquisto) {
        this.dataAcquisto = dataAcquisto;
    }

    public Date getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {
        this.dataScadenza = dataScadenza;
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

    public Date calcolaScadenza(Date dataAcquisto, int abbonamento) {
        Date dataScadenza = new Date();
        dataScadenza.setTime(dataAcquisto.getTime() + ((long) abbonamento * 30 * 24 * 60 * 60 * 1000));
        return dataScadenza;

    }

    public void rinnovaTessera(Date dataAcquisto, int abbonamento) {
        this.dataAcquisto = new Date();
        this.dataScadenza = calcolaScadenza(dataAcquisto, abbonamento);
    }

    public boolean verificaScadenza(Date dataScadenza) {
        Date dataOggi = new Date();
        return dataOggi.after(dataScadenza);
    }


}
