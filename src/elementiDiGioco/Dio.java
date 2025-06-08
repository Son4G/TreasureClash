package elementiDiGioco;

import userInterface.TavoloDaGioco;
import utilities.LogicaDiGioco;
import utilities.Move;
import java.io.Serial;

//Abilità: Usa "Za Warudo" per avere un turno aggiuntivo; può anche superare alberi utilizzando l'abilità su Albero ma perdendo 2 mosse.

public class Dio extends Giocatore {
    @Serial
    private static final long serialVersionUID = 6715355160724771116L;
    private int utilizziAbilita = 2;

    @Override
    public boolean usaAbilita(Albero albero, LogicaDiGioco logicaDiGioco) {
        System.out.println("Ohhh...");
        logicaDiGioco.setNextXNextY(Move.valueOf(logicaDiGioco.getMovimento()));
        logicaDiGioco.controllaPosizioneSucessiva();
        return true;
    }

    @Override
    public boolean usaAbilita(Roccia roccia, LogicaDiGioco logicaDiGioco) {
        return false;
    }

    @Override
    public boolean zaWarudo() {
        if (utilizziAbilita == 0) {
            System.out.println("Non puoi più usare l'abilità");
            return false;
        }
        utilizziAbilita--;
        return true;
    }


    @Override
    public boolean usaAbilita(Giocatore giocatore) {

        return false;
    }

    @Override
    public boolean usaAbilita(TavoloDaGioco tavolo, LogicaDiGioco logicaDiGioco, String direzione) {
        return false;
    }

    @Override
    public boolean usaAbilita(PozioneMagica pozione) {
        return false;
    }
}
