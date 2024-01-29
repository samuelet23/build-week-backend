package entities;

import java.util.Date;

public class Utente {

    private String nome;
    private String cognome;

    private Date dataNascita;

    private int Id;

    private String tessera;

    public Utente(String nome, String cognome, Date dataNascita, int Id, String tessera) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.Id = Id;
        this.tessera = tessera;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public int getId() {
        return Id;
    }

    public String getTessera() {
        return tessera;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setTessera(String tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Utente{" + "nome=" + nome + ", cognome=" + cognome + ", dataNascita=" + dataNascita + ", Id=" + Id + ", tessera=" + tessera + '}';
    }


}