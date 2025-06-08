package elementiDiGioco;

import userInterface.TavoloDaGioco;
import utilities.LogicaDiGioco;
import java.io.Serial;

//Abilità: Abilità a distanza che colpisce in linea retta, consumando un utilizzo.

public class HolHorse extends Giocatore {


    private int utilizzi = 3;
    @Serial
    private static final long serialVersionUID = 1494254985090418281L;

    @Override
    public boolean usaAbilita(Albero albero, LogicaDiGioco logicaDiGioco) {
        return false;
    }

    @Override
    public boolean usaAbilita(Roccia roccia, LogicaDiGioco logicaDiGioco) {
        return false;
    }

    @Override
    public boolean usaAbilita(Giocatore giocatore) {
        return false;
    }

    @Override
    public boolean zaWarudo() {
        return false;
    }

    public  boolean controlloOnHit(int x, int i, TavoloDaGioco tavolo) {
        utilizzi--;
        if (tavolo.getElementoDiGioco(x, i) instanceof Colpibile c) {
            return c.onHit();
        }
        return false;
    }

    @Override
    public boolean usaAbilita(TavoloDaGioco tavolo, LogicaDiGioco logicaDiGioco, String direzione) {
        if (utilizzi > 0) {
            int x = this.getX();
            int y = this.getY();
            switch (direzione.charAt(0)) {
                case 'D':
                    for (int i = y + 1; i < tavolo.getLunghezza(); i++) {
                        boolean check = controlloOnHit(x, i, tavolo);
                        if (check) {
                            return check;
                        }
                    }
                    break;
                case 'A':
                    for (int i = y - 1; i >= 0; i--) {
                        boolean check = controlloOnHit(x, i, tavolo);
                        if (check) {
                            return check;
                        }
                    }
                    break;
                case 'S':
                    for (int i = x + 1; i < tavolo.getAltezza(); i++) {
                        boolean check = controlloOnHit(i, y, tavolo);
                        if (check) {
                            return check;
                        }
                    }
                    break;
                case 'W':
                    for (int i = x - 1; i >= 0; i--) {
                        boolean check = controlloOnHit(i, y, tavolo);
                        if (check) {
                            return check;
                        }
                    }
                    break;
                default:
                    return false;
            }
            utilizzi--;
        }
        else{

            System.out.println("Hai finito gli utilizzi");
            return true;
        }
        return false;
    }

    @Override
    public boolean usaAbilita(PozioneMagica pozione) {
        return false;
    }
}
