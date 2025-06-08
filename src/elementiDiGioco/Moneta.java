package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;

public class Moneta extends Raccoglibile {

    @Serial
    private static final long serialVersionUID = -158591684136934813L;
    private final int valore;

    public Moneta() {
        super('$');
        this.valore = 1;
    }

    public Moneta(int valore){
        super('€');
        this.valore = valore;
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        giocatore.aggiungiMoneta(this);
        return true;
    }


    public int getValore() {
        return valore;
    }
}
