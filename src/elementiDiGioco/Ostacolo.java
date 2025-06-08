package elementiDiGioco;

import utilities.LogicaDiGioco;

import java.io.Serial;

public abstract class Ostacolo extends ElementoDiGioco implements Colpibile {

    @Serial
    private static final long serialVersionUID = 6811684431284188379L;

    public Ostacolo(char simbolo) {
        super(simbolo);

    }

    @Override
    public boolean onHit() {
        System.out.println("Il proiettile si è schiantato contro un ostacolo");
        return true;
    }
}
