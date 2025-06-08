package utilities;

import elementiDiGioco.*;
import userInterface.RappresentazioneTestuale;
import userInterface.TavoloDaGioco;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import java.util.Scanner;

public class LogicaDiGioco implements Serializable {
    @Serial
    private static final long serialVersionUID = 744528835051794438L;
    private final Partita partita;
    private final Giocatore[] giocatori;
    private final TavoloDaGioco tavoloDaGioco;
    private final RappresentazioneTestuale rappresentazioneTestuale = new RappresentazioneTestuale();
    private int turno;
    private static final Scanner input = new Scanner(System.in);
    private final Dado dado;
    public static int maxMonete = 15;
    private int nextX;
    private int nextY;
    private String movimento;
    File file = new File("ZA WARUDO - Dio's The World Sound Effect.wav");

    LogicaDiGioco(Partita partita) {
        this.partita = partita;
        this.giocatori = partita.getTavoloDaGioco().getGiocatori();
        this.tavoloDaGioco = partita.getTavoloDaGioco();
        this.dado = new Dado();
    }

    public int getNextX() {
        return nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public void playerMoving() {

        int lancio = dado.lancioDado();

        giocatori[turno].setGiocatoreInBotola(false);
        System.out.println(tavoloDaGioco);

        System.out.println("Turno di: " + giocatori[turno].getNome());
        System.out.println("Numero del dado: " + lancio);

        giocatori[turno].setMosseRimanenti(lancio);

        while (giocatori[turno].getMosseRimanenti() > 0 && !partita.isFinePartita() && !giocatori[turno].isEliminato()) {

            System.out.println("In che direzione vuoi spostarti? SOPRA(W) - SOTTO(S) - DESTRA(D) - SINISTRA(A) ");
            movimento = input.nextLine().toUpperCase();
            while (!movimento.equalsIgnoreCase("A") && !movimento.equalsIgnoreCase("D") && !movimento.equalsIgnoreCase("W") && !movimento.equalsIgnoreCase("S")) {
                System.out.println("Inserisci una direzione valida: ");
                movimento = input.nextLine().toUpperCase();
            }

            nextX = giocatori[turno].getX();
            nextY = giocatori[turno].getY();

            setNextXNextY(Move.valueOf(movimento));

            controllaPosizioneSucessiva();


            if (controllaFinePartita()) {
                return;
            }

            if (giocatori[turno].isEliminato()) {
                giocatori[turno].setMosseRimanenti(0);
            }

            if (!partita.isFinePartita() && !(giocatori[turno].isEliminato())) {
                System.out.println(tavoloDaGioco);
                if (giocatori[turno].getMosseRimanenti() < 0) {
                    giocatori[turno].setMosseRimanenti(0);
                }
                System.out.println("Giocatore: " + giocatori[turno].getNome() + " - Mosse rimanenti: " + giocatori[turno].getMosseRimanenti());

            }
        }

        if (!giocatori[turno].isEliminato() && !giocatori[turno].isGiocatoreInBotola() && giocatori[turno] instanceof HolHorse) {
            String risposta = rappresentazioneTestuale.chiediUsoAbilita();
            if (risposta.equalsIgnoreCase("si")) {
                System.out.println("Inserisci la direzione in cui vuoi sparare il proiettile");
                String direzione = input.nextLine().toUpperCase();
                while (!direzione.equalsIgnoreCase("A") && !direzione.equalsIgnoreCase("D") && !direzione.equalsIgnoreCase("W") && !direzione.equalsIgnoreCase("S")) {
                    System.out.println("Inserisci una direzione valida: ");
                    direzione = input.nextLine().toUpperCase();
                }
                if (!giocatori[turno].usaAbilita(tavoloDaGioco, this, direzione)) {
                    System.out.println("Nessun giocatore colpito");
                }
            }
        }
        if (!giocatori[turno].isEliminato() && !giocatori[turno].isGiocatoreInBotola() && giocatori[turno] instanceof Dio) {
            String risposta = rappresentazioneTestuale.chiediUsoAbilita();

            if (risposta.equalsIgnoreCase("si")) {
                if (giocatori[turno].zaWarudo()) {
                    AudioInputStream audioStream;
                    {
                        try {
                            audioStream = AudioSystem.getAudioInputStream(file);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioStream);
                            clip.start();
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    turno = turnoGiocatore(turno);
                }
            } else if (risposta.equalsIgnoreCase("no")) {
                turno = turnoGiocatore(turno);
            }
        } else {
            turno = turnoGiocatore(turno);
        }
        for (Giocatore g : giocatori) {
            if (g.isEliminato()) {
                tavoloDaGioco.rimuoviGiocatore(g);
                g.setMosseRimanenti(0);
            }
        }
    }

    private boolean controllaFinePartita() {

        int numGiocatoriAttivi = 0;
        for (Giocatore giocatore : giocatori) {
            if (!giocatore.isEliminato())
                numGiocatoriAttivi++;
        }
        int indiceGiocatoreAttivo = -1;
        if (numGiocatoriAttivi == 1) {
            for (int i = 0; i < giocatori.length; i++) {
                if (!giocatori[i].isEliminato()) {
                    indiceGiocatoreAttivo = i;
                    System.out.println(giocatori[indiceGiocatoreAttivo].getNome() + " è l'unico giocatore rimasto e vince!");
                    partita.setFinePartita(true);
                    return true;
                }
            }
        }

        if (giocatori[turno].getInvMonete() >= maxMonete) {
            System.out.println(giocatori[turno].getNome() + " ha ottenuto tutte le monete e vince!");
            partita.setFinePartita(true);
            return true;
        }
        return false;
    }

    public int turnoGiocatore(int turno) {
        int prossimo = turno;
        do {
            prossimo = (prossimo + 1) % 4;
        } while (giocatori[prossimo].isSaltaTurno() || giocatori[prossimo].isEliminato());
        return prossimo;
    }


    private boolean gestisciUsoGemma(Giocatore sfidato) {
        if (sfidato.getGemmeRaccolte() > 0) {
            String scelta = rappresentazioneTestuale.chiediUsoGemma(sfidato);
            if (scelta.equalsIgnoreCase("SI")) {
                sfidato.usaGemma();
                tavoloDaGioco.teletrasportaGiocatore(sfidato);
                return false;
            }
        }
        return true;
    }

    private boolean gestisciUsoPozione(Giocatore sfidante, Giocatore sfidato) {
        if (sfidante.getPozioniRaccolte() > 0) {
            String scelta = rappresentazioneTestuale.chiediUsoPozione(sfidante);

            if (scelta.equalsIgnoreCase("SI")) {
                sfidante.usaPozione();
                if (!sfidato.getMonete().isEmpty()) {
                    sfidante.aggiungiMoneta(sfidato.getMonete().getFirst());
                    sfidato.rimuoviMoneta();
                }
                if (sfidato.getMonete().isEmpty()) {
                    sfidato.setEliminato();
                    maxMonete -= sfidato.getInvMonete();
                    tavoloDaGioco.rimuoviGiocatore(sfidato);
                    System.out.println(sfidato.getNome() + " è stato eliminato!");
                }
                if (sfidato.getxIniziale() == nextX && sfidato.getyIniziale() == nextY && !sfidato.isEliminato()) {
                    tavoloDaGioco.tornaAllaPosizioneOpposta(sfidato, sfidante);

                } else {
                    if (tavoloDaGioco.getGriglia()[sfidato.getxIniziale()][sfidato.getyIniziale()] == null) {
                        tavoloDaGioco.tornaAllaPosizioneIniziale(sfidato);
                    } else {
                        tavoloDaGioco.teletrasportaGiocatore(sfidato);
                    }
                }
                return false;
            }
        }
        return true;
    }

    private boolean eseguiSfidaDadi(Giocatore sfidante, Giocatore sfidato) {
        int lancioSfidante;
        int lancioSfidato;
        do {
            lancioSfidante = dado.lancioDado();
            lancioSfidato = dado.lancioDado();
            if (sfidante.usaAbilita(sfidato)) {
                if (sfidante instanceof Hakari) {
                    lancioSfidante += 2;
                    System.out.println("JACKPOT (+2)");
                }
                /*if (sfidante instanceof Dio) {
                    for (int i = 0; i < lancioSfidante; i++) {
                        lancioSfidante += 1;
                        System.out.println("+1");
                    }
                }*/
            }
            System.out.println("Sfidante: (" + sfidante.getNome() + "): " + lancioSfidante);
            System.out.println("Sfidato: (" + sfidato.getNome() + "): " + lancioSfidato);

            if (lancioSfidante > lancioSfidato) {
                gestisciVittoriaSfida(sfidante, sfidato);
                return true;
            } else if (lancioSfidante < lancioSfidato) {
                gestisciVittoriaSfida(sfidato, sfidante);
                giocatori[turno].riduciMosseRimanenti();
                return false;
            }

        } while (lancioSfidante == lancioSfidato);
        return false;
    }

    private void gestisciVittoriaSfida(Giocatore vincitore, Giocatore perdente) {

        if (perdente.getInvMonete() > 0) {
            vincitore.aggiungiMoneta(perdente.getMonete().getFirst());
            perdente.rimuoviMoneta();
            if (perdente.getxIniziale() == nextX && perdente.getyIniziale() == nextY) {
                tavoloDaGioco.tornaAllaPosizioneOpposta(perdente, vincitore);
            } else {
                if (tavoloDaGioco.getGriglia()[perdente.getxIniziale()][perdente.getyIniziale()] == null) {
                    tavoloDaGioco.tornaAllaPosizioneIniziale(perdente);
                } else {
                    tavoloDaGioco.teletrasportaGiocatore(perdente);
                }
            }
        } else {
            perdente.setEliminato();
            tavoloDaGioco.getGriglia()[vincitore.getX()][vincitore.getY()] = null;
            tavoloDaGioco.getGriglia()[perdente.getX()][perdente.getY()] = vincitore;
            vincitore.setX(perdente.getX());
            vincitore.setY(perdente.getY());
            maxMonete -= perdente.getInvMonete();
            System.out.println(perdente.getNome() + " è stato eliminato!");
        }

    }


    public boolean inizioSfida() {

        Giocatore sfidante = giocatori[turno];
        Giocatore sfidato = (Giocatore) tavoloDaGioco.getGriglia()[nextX][nextY];

        if (sfidato.isGiocatoreInBotola())
            return false;
        if (!gestisciUsoGemma(sfidato))
            return true;
        if (!gestisciUsoPozione(sfidante, sfidato))
            return true;

        return eseguiSfidaDadi(sfidante, sfidato);
    }


    public TavoloDaGioco getTavoloDaGioco() {
        return tavoloDaGioco;
    }

    public void setNextXNextY(Move movimento) {
        switch (movimento) {
            case W -> nextX = (nextX == 0) ? tavoloDaGioco.getAltezza() - 1 : nextX - 1;
            case S -> nextX = (nextX == tavoloDaGioco.getAltezza() - 1) ? 0 : nextX + 1;
            case A -> nextY = (nextY == 0) ? tavoloDaGioco.getLunghezza() - 1 : nextY - 1;
            case D -> nextY = (nextY == tavoloDaGioco.getLunghezza() - 1) ? 0 : nextY + 1;
        }
    }

    public String getMovimento() {
        return movimento;
    }


    public void controllaPosizioneSucessiva() {
        ElementoDiGioco elemento = tavoloDaGioco.getElementoDiGioco(nextX, nextY);
        int vecchiaX = giocatori[turno].getX();
        int vecchiaY = giocatori[turno].getY();

        if (elemento == null) {
            giocatori[turno].setX(nextX);
            giocatori[turno].setY(nextY);
            tavoloDaGioco.aggiornaPosGiocatore(giocatori[turno], vecchiaX, vecchiaY);
            giocatori[turno].riduciMosseRimanenti();
        } else {
            boolean interact = elemento.onInteract(giocatori[turno], this);
            if (interact) {
                tavoloDaGioco.getGriglia()[giocatori[turno].getX()][giocatori[turno].getY()] = null;
                giocatori[turno].setX(nextX);
                giocatori[turno].setY(nextY);
                tavoloDaGioco.getGriglia()[nextX][nextY] = giocatori[turno];
                if (!(elemento instanceof Giocatore))
                    tavoloDaGioco.aggiornaPosGiocatore(giocatori[turno], vecchiaX, vecchiaY);
                giocatori[turno].riduciMosseRimanenti();
            }
        }
    }

    public void closeScanner() {
        this.input.close();
    }
}

