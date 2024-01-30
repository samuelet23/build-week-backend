package entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
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

