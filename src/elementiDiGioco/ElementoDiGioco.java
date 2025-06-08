package elementiDiGioco;

import utilities.LogicaDiGioco;
import java.io.Serial;
import java.io.Serializable;

public abstract class ElementoDiGioco implements Serializable {
    @Serial
    private static final long serialVersionUID = 4118309373042769962L;
    private int x;
    private int y;
    private char simbolo;

    public abstract boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco);


    public ElementoDiGioco() {
    }

    public ElementoDiGioco(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return simbolo;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }



    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }





}


