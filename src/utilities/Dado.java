package utilities;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class Dado implements Serializable {
    @Serial
    private static final long serialVersionUID = 724995374358669308L;
    private final Random random = new Random();

    public int lancioDado() {
        return random.nextInt(6) + 1;
    }
}
