package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;

public class PozioneMagica extends Raccoglibile {

    @Serial
    private static final long serialVersionUID = -1957603884358445865L;

    public PozioneMagica() {
        super('&');
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        giocatore.aggiungiPozione();
        giocatore.usaAbilita(this);
        return true;
    }


}
