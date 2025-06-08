package elementiDiGioco;

import userInterface.TavoloDaGioco;
import utilities.LogicaDiGioco;
import utilities.Move;
import java.io.Serial;

//Abilità: Può scavalcare le rocce (usaAbilita su Roccia) e ha una probabilità del 50% di ottenere una pozione extra quando interagisce con una PozioneMagica.


public class TreasureHunter extends Giocatore{
    @Serial
    private static final long serialVersionUID = -3611326457465924806L;

    @Override
    public boolean usaAbilita(Albero albero, LogicaDiGioco logicaDiGioco) {
        return false;
    }

    @Override
    public boolean zaWarudo() {
        return false;
    }

    @Override
    public boolean usaAbilita(Roccia roccia, LogicaDiGioco logicaDiGioco) {
        System.out.println("Hai scavalcato la roccia!");
        logicaDiGioco.setNextXNextY(Move.valueOf(logicaDiGioco.getMovimento()));
        logicaDiGioco.controllaPosizioneSucessiva();
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
        if (Math.random() > 0.5) {
            this.aggiungiPozione();
            System.out.println("La fortuna ti sorride, hai trovato una pozione extra!");
            return true;
        } else {
            return false;
        }
    }
}
