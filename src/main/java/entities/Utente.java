package entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Utente {
    @Id
    @GeneratedValue(strategy =GenerationType.SEQUENCE, generator = "id_tessera")
    @SequenceGenerator(name = "id_tessera", initialValue = 1, allocationSize = 1)
    private int id;

    private String nome;
    private String cognome;
    @Column(name = "data_nascita")
    private Date dataNascita;

    @JoinColumn(name = "numero_tessera")
    @OneToOne(mappedBy = "utente")
    private Tessera numeroTessera;

    public Utente(){}

    public Utente(int id, String nome, String cognome, Date dataNascita, Tessera numeroTessera) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.numeroTessera = numeroTessera;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", numeroTessera=" + numeroTessera +
                '}';
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Tessera getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(Tessera numeroTessera) {
        this.numeroTessera = numeroTessera;
    }
}