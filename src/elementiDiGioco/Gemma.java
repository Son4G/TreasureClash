package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;

public class Gemma extends Raccoglibile {

    @Serial
    private static final long serialVersionUID = -4098586729540460184L;

    public Gemma() {
        super('%');
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        giocatore.aggiungiGemma();
        return true;
    }




}
