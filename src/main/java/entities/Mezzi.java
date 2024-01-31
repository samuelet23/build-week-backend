package entities;

import entities.type.TipoMezzo;
import jakarta.persistence.*;
import entities.sottoclassi.Biglietti;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "mezzi")
@NamedQuery( name = "setInManutenzione", query = "UPDATE Mezzi m SET m.in_manutenzione = true WHERE m.id = :id")
@NamedQuery( name = "setOffManutenzione", query = "UPDATE Mezzi m SET m.in_manutenzione = false WHERE m.id = :id")
@NamedQuery( name = "selectAllMezzi", query = "SELECT m FROM Mezzi m")
public class Mezzi {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "id_mezzi")
    @SequenceGenerator(name = "id_mezzi", initialValue = 1, allocationSize = 1)
    private int id;

    @Enumerated(EnumType.STRING)
    private TipoMezzo tipo;

    private int capienza;
    private boolean in_manutenzione;
    @OneToMany (mappedBy = "mezzo")
    private List<Biglietti> biglietti = new ArrayList<>();

    @OneToMany (mappedBy = "mezzo")
    private List<Tratte> lista_tratte = new ArrayList<>();

    @OneToMany (mappedBy = "mezzo")
    private List<Manutenzioni> lista_manutenzioni = new ArrayList<>();

    public Mezzi() {
    }

    public Mezzi(TipoMezzo tipo, int capienza, boolean in_manutenzione, List<Biglietti> biglietti, List<Tratte> lista_tratte, List<Manutenzioni> lista_manutenzioni) {
        this.tipo = tipo;
        if (tipo == TipoMezzo.AUTOBUS){
            this.capienza = 40;
        } else {
            this.capienza = 60;
        }
        this.in_manutenzione = in_manutenzione;
        this.biglietti = biglietti;
        this.lista_tratte = lista_tratte;
        this.lista_manutenzioni = lista_manutenzioni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    public void setTipo(TipoMezzo tipo) {
        this.tipo = tipo;
        if (tipo == TipoMezzo.AUTOBUS){
            this.capienza = 40;
        } else {
            this.capienza = 60;
        }
    }

    public int getCapienza() {
        return capienza;
    }

    public boolean isIn_manutenzione() {
        return in_manutenzione;
    }

    public void setIn_manutenzione(boolean in_manutenzione) {
        this.in_manutenzione = in_manutenzione;
    }

    public List<Biglietti> getBiglietti() {
        return biglietti;
    }

    public void setBiglietti(List<Biglietti> biglietti) {
        this.biglietti = biglietti;
    }

    public List<Tratte> getLista_tratte() {
        return lista_tratte;
    }

    public void setLista_tratte(List<Tratte> lista_tratte) {
        this.lista_tratte = lista_tratte;
    }

    public List<Manutenzioni> getLista_manutenzioni() {
        return lista_manutenzioni;
    }

    public void setLista_manutenzioni(List<Manutenzioni> lista_manutenzioni) {
        this.lista_manutenzioni = lista_manutenzioni;
    }

    @Override
    public String toString() {
        return "Mezzi{" +
                "id=" + id +
                ", tipo=" + tipo +
                ", capienza=" + capienza +
                ", in_manutenzione=" + in_manutenzione +
//                ", biglietti=" + biglietti +
                '}';
    }
}
