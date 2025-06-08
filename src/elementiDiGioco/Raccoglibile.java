package elementiDiGioco;

import java.io.Serial;

public abstract class Raccoglibile extends ElementoDiGioco {

    @Serial
    private static final long serialVersionUID = 7650985911581876873L;

    public Raccoglibile(char simbolo) {
        super(simbolo);
    }
}
