package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;

public class Roccia extends Ostacolo {
    private final static double PROBABILITA_ESPLOSIONE = 0.5;
    @Serial
    private static final long serialVersionUID = 8720107954233497636L;
    private final boolean esplosiva;

    public Roccia() {

        super('@');
        esplosiva = Math.random() < PROBABILITA_ESPLOSIONE;
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        boolean abilita = giocatore.usaAbilita(this, logicaDiGioco);
        if (esplosiva && !abilita) {
            giocatore.setEliminato();
            logicaDiGioco.getTavoloDaGioco().getGriglia()[logicaDiGioco.getNextX()][logicaDiGioco.getNextY()] = null;
            logicaDiGioco.getTavoloDaGioco().rimuoviGiocatore(giocatore);
            giocatore.setMosseRimanenti(0);
            giocatore.setEliminato();
            System.out.println("Il giocatore: " + giocatore.getNome() + " è esploso su una roccia!");
            return false;
        }
        if (abilita) {
            return true;
        }
        System.out.println("Impossibile spostarsi su questa roccia! Scegli un'altra direzione.");
        return false;
    }

}
