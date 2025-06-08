package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;


public class Albero extends Ostacolo {
    private final static double PROBABILITA_SCHIACCIAMENTO = 0.5;
    @Serial
    private static final long serialVersionUID = -7012694355497468764L;
    private final boolean schiacciante;

    public Albero() {
        super('#');
        schiacciante = Math.random() < PROBABILITA_SCHIACCIAMENTO;
    }

    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        boolean abilita = giocatore.usaAbilita(this, logicaDiGioco);
        if (abilita) {
            return true;
        }
        if (giocatore.getMosseRimanenti() >= 2) {
            if (schiacciante) {
                logicaDiGioco.getTavoloDaGioco().getGriglia()[logicaDiGioco.getNextX()][logicaDiGioco.getNextY()] = null;
                logicaDiGioco.getTavoloDaGioco().rimuoviGiocatore(giocatore);
                giocatore.setMosseRimanenti(0);
                giocatore.setEliminato();
                System.out.println("Il giocatore: " + giocatore.getNome() + " è stato schiacciato da un albero!");
                return false;
            }
            System.out.println("Il giocatore: " + giocatore.getNome() + " ha abbattuto l'albero");
            giocatore.riduciMosseRimanenti();
            return true;
        }
        System.out.println("Servono almeno 2 mosse per abbattere l'albero.");
        return false;
    }
}
