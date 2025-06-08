package utilities;

import elementiDiGioco.Giocatore;
import userInterface.RappresentazioneTestuale;
import userInterface.TavoloDaGioco;

import java.io.*;

public class Partita implements Serializable {

    @Serial
    private static final long serialVersionUID = 1086084851687319236L;
    private TavoloDaGioco tavoloDaGioco;
    private LogicaDiGioco logicaDiGioco;
    private final RappresentazioneTestuale rappresentazioneTestuale;
    private boolean finePartita = false;

    public Partita() {
        this.rappresentazioneTestuale = new RappresentazioneTestuale();
    }

    public TavoloDaGioco getTavoloDaGioco() {
        return tavoloDaGioco;
    }

    public boolean isFinePartita() {
        return finePartita;
    }

    public void setFinePartita(boolean finePartita) {
        this.finePartita = finePartita;
    }


    public LogicaDiGioco getLogicaDiGioco() {
        return logicaDiGioco;
    }

    public void iniziaPartita() {
        Giocatore[] giocatori = rappresentazioneTestuale.chiediGiocatori();
        tavoloDaGioco = new TavoloDaGioco(giocatori);
        logicaDiGioco = new LogicaDiGioco(this);

        while (!finePartita) {
            rappresentazioneTestuale.mostraMenu(this);
        }

        rappresentazioneTestuale.chiudiScanner();
        logicaDiGioco.closeScanner();
    }

    public void avviaPartitaSalvata() {
        while (!finePartita) {
            rappresentazioneTestuale.mostraMenu(this);
        }
    }
}
