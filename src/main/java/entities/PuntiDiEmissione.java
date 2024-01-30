package entities;

import jakarta.persistence.*;
import java.util.List;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PuntiDiEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nome;

    private String citta;
    @OneToMany(mappedBy = "puntiDiEmissione")
    private Set<Tickets> tickets_emessi;

    public PuntiDiEmissione() {
    }

    public PuntiDiEmissione(int id, String nome, String citta, Set<Tickets> tickets_emessi) {
        this.id = id;
        this.nome = nome;
        this.citta = citta;
        this.tickets_emessi = tickets_emessi;
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

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public Set<Tickets> getTickets_emessi() {
        return tickets_emessi;
    }

    public void setTickets_emessi(Set<Tickets> tickets_emessi) {
        this.tickets_emessi = tickets_emessi;
    }

    @Override
    public String toString() {
        return "PuntiDiEmissione{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                ", tickets_emessi=" + tickets_emessi +
                '}';
    }
}


