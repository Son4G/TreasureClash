package elementiDiGioco;

import userInterface.TavoloDaGioco;
import utilities.LogicaDiGioco;

import java.io.Serial;

//Abilità: Possibilità di sopravvivere del 50% ad un albero abbattutto che gli cade sopra, del 50% di sopravvivere ad una roccia esplosiva e del 50% di ottenere +2 al tiro nella sfida.
public class Hakari extends Giocatore {
    @Serial
    private static final long serialVersionUID = -1817035359628592991L;

    @Override
    public boolean zaWarudo() {
        return false;
    }

    @Override
    public boolean usaAbilita(Albero albero, LogicaDiGioco logicaDiGioco) {
        if (Math.random() > 0.5) {
            System.out.println("JACKPOT! L'albero ti stava per schiacciare ma sei sopravvissut*!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean usaAbilita(Roccia roccia, LogicaDiGioco logicaDiGioco) {
        if (Math.random() > 0.5) {
            System.out.println("JACKPOT! La roccia era esplosiva ma sei sopravvissut*");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean usaAbilita(PozioneMagica pozione) {
        return false;
    }

    @Override
    public boolean usaAbilita(Giocatore giocatore) {
        return true;
    }

    @Override
    public boolean usaAbilita(TavoloDaGioco tavolo, LogicaDiGioco logicaDiGioco, String direzione) {
        return false;
    }
}
