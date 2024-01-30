import dao.MezziDAO;
import entities.Mezzi;
import entities.type.TipoMezzo;

public class Main {
    public static void main(String[] args) {

        MezziDAO mezziDAO = new MezziDAO();

        Mezzi m1 = new Mezzi();
        m1.setTipo(TipoMezzo.AUTOBUS);
        m1.setIn_manutenzione(false);
        try {
            mezziDAO.aggiungi(m1);
            System.out.println("Mezzo" + m1 + " salvato con successo");
        } catch (Exception e){
            e.getMessage();
        }
    }
}
