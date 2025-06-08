package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;


public class Botola extends ElementoDiGioco {
    @Serial
    private static final long serialVersionUID = -901428760533398233L;

    public Botola() {
        super('B');
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        System.out.println(giocatore.getNome() + " è caduto in una botola!");
        giocatore.setGiocatoreInBotola(true);
        giocatore.setMosseRimanenti(0);
        giocatore.setSaltaTurno(true);
        logicaDiGioco.getTavoloDaGioco().teletrasportaGiocatore(giocatore);
        return false;
    }

}
