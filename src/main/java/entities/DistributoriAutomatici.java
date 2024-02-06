package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name =" setFuoriServizio", query = "UPDATE DistributoriAutomatici d SET d.in_servizio = :fuoriServizio")
@NamedQuery(name =" setAttivo", query = "UPDATE DistributoriAutomatici d SET d.in_servizio = :inServizio")
public class DistributoriAutomatici extends PuntiDiEmissione{

    private boolean in_servizio;

    public boolean isIn_servizio() {
        return in_servizio;
    }

    public void setIn_servizio(boolean in_servizio) {
        this.in_servizio = in_servizio;
    }

    @Override
    public String toString() {
        return "DistributoriAutomatici{" +
                "in_servizio=" + in_servizio +
                '}';
    }
}

