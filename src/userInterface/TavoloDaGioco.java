package userInterface;

import elementiDiGioco.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

public class TavoloDaGioco implements Serializable {
    public static final int LUNGHEZZA = 17;
    public static final int ALTEZZA = 13;
    @Serial
    private static final long serialVersionUID = 3681943670462077069L;
    private final Giocatore[] giocatori;
    private final ElementoDiGioco[][] griglia;
    private final Random random = new Random();



    public TavoloDaGioco(Giocatore[] giocatori) {
        this.giocatori = giocatori;
        this.griglia = new ElementoDiGioco[ALTEZZA][LUNGHEZZA];
        posizionaGiocatori();
        inizializzaGriglia();
    }

    public ElementoDiGioco[][] getGriglia() {
        return griglia;
    }

    public int getLunghezza() {
        return LUNGHEZZA;
    }

    public int getAltezza() {
        return ALTEZZA;
    }

    private void posizionaGiocatori() {
        posizionaGiocatore(giocatori[0], ALTEZZA-1, 0);
        posizionaGiocatore(giocatori[1], 0, LUNGHEZZA-1);
        posizionaGiocatore(giocatori[2], 0, 0);
        posizionaGiocatore(giocatori[3], ALTEZZA-1, LUNGHEZZA-1);
    }

    private void posizionaGiocatore(Giocatore giocatore, int x, int y) {
        giocatore.setX(x);
        giocatore.setY(y);
        giocatore.setxIniziale(x);
        giocatore.setyIniziale(y);
    }

    public void tornaAllaPosizioneIniziale(Giocatore giocatore) {
        griglia[giocatore.getX()][giocatore.getY()] = null;

        giocatore.setX(giocatore.getxIniziale());
        giocatore.setY(giocatore.getyIniziale());

        griglia[giocatore.getX()][giocatore.getY()] = giocatore;
    }

    public void tornaAllaPosizioneOpposta(Giocatore giocatore, Giocatore opposto) {

        griglia[giocatore.getX()][giocatore.getY()] = opposto;
        opposto.setX(giocatore.getX());
        opposto.setY(giocatore.getY());
        griglia[opposto.getxIniziale()][opposto.getyIniziale()] = giocatore;
        giocatore.setX(opposto.getxIniziale());
        giocatore.setY(opposto.getyIniziale());
    }



    private void inizializzaGriglia() {

        for (Giocatore giocatore : giocatori) {
            griglia[giocatore.getX()][giocatore.getY()] = giocatore;
        }

        posizionaMoneteDa2(5);
        posizionaMoneteDa1(5);
        posizionaGemme(5);
        posizionaPozioniMagiche(3);
        posizionaRocce(5);
        posizionaAlberi(7);
        posizionaBotole(3);
    }


    public void teletrasportaGiocatore(Giocatore giocatore) {
        griglia[giocatore.getX()][giocatore.getY()] = null;
        int x;
        int y;
        do {
            x = random.nextInt(ALTEZZA);
            y = random.nextInt(LUNGHEZZA);
        } while (griglia[x][y] != null);
        griglia[x][y] = giocatore;
        giocatore.setX(x);
        giocatore.setY(y);
    }


    private void posizionaMoneteDa1(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Moneta();
                i++;
            }
        }
    }

    private void posizionaMoneteDa2(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Moneta(2);
                i++;
            }
        }
    }

    private void posizionaGemme(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Gemma();
                i++;
            }
        }
    }

    private void posizionaPozioniMagiche(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new PozioneMagica();
                i++;
            }
        }
    }

    private void posizionaRocce(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Roccia();
                i++;
            }
        }
    }

    private void posizionaAlberi(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Albero();
                i++;
            }
        }
    }

    private void posizionaBotole(int quantita) {
        for (int i = 0; i < quantita;) {
            int x = random.nextInt(ALTEZZA);
            int y = random.nextInt(LUNGHEZZA);
            if (griglia[x][y] == null) {
                griglia[x][y] = new Botola();
                i++;
            }
        }
    }


    public void aggiornaPosGiocatore (Giocatore giocatore, int vecchiaX, int vecchiaY) {
        griglia[vecchiaX][vecchiaY] = null;
        griglia[giocatore.getX()][giocatore.getY()] = giocatore;
    }


    public ElementoDiGioco getElementoDiGioco(int x, int y) {
        return griglia[x][y];
    }

    public void rimuoviGiocatore(Giocatore giocatore) {
        griglia[giocatore.getX()][giocatore.getY()] = null;
    }

    public Giocatore[] getGiocatori() {
        return giocatori;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < LUNGHEZZA * 4 + 1; i++) {
            sb.append("-");
        }
        sb.append(System.lineSeparator());


        for (int i = 0; i < ALTEZZA; i++) {
            sb.append("| ");


            for (int j = 0; j < LUNGHEZZA; j++) {
                char contenutoCasella = ' ';

                if (griglia[i][j] != null) {
                    contenutoCasella = griglia[i][j].getSimbolo();
                }

                sb.append(contenutoCasella).append(" | ");
            }

            sb.append(System.lineSeparator());

            for (int j = 0; j < LUNGHEZZA * 4 + 1; j++) {
                sb.append("-");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }


}
