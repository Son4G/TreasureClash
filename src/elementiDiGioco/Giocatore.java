package elementiDiGioco;

import userInterface.TavoloDaGioco;
import utilities.LogicaDiGioco;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Giocatore extends ElementoDiGioco implements Serializable, Colpibile {
    @Serial
    private static final long serialVersionUID = -6964815780191245505L;
    private String nome;
    private int xIniziale;
    private int yIniziale;
    private int invMonete = 0;
    private List<Moneta> monete = new ArrayList<>();
    private int invGemme = 0;
    private int invPozioni = 0;
    private int contatore = 0;
    private int mosseRimanenti = 0;
    private boolean eliminato = false;
    private boolean saltaTurno = false;

    private boolean giocatoreInBotola;

    @Override
    public char getSimbolo(){
        if (!giocatoreInBotola){
            return super.getSimbolo();
        }
        return ' ';
    }

    public String getNome() {
        return nome;
    }

    public void setMosseRimanenti(int mosseRimanenti) {
        this.mosseRimanenti = mosseRimanenti;
    }

    public int getMosseRimanenti() {
        return mosseRimanenti;
    }

    public void riduciMosseRimanenti() {
        mosseRimanenti -= 1;
    }


    @Override
    public boolean onInteract(Giocatore giocatore, LogicaDiGioco logicaDiGioco) {
        return logicaDiGioco.inizioSfida();
    }

    @Override
    public boolean onHit() {
        if (this.getInvMonete() == 0) {
            this.setEliminato();
            System.out.println("Il giocatore: " + this.getNome() + " è stato eliminato");
            return true;
        }
        LogicaDiGioco.maxMonete -= this.monete.getLast().getValore();
        this.rimuoviMoneta();
        System.out.println("Il giocatore: " + this.getNome() + " è stato colpito");
        return true;
    }



    public void setNome(String nome) {
        this.nome = nome == null ? "Giocatore " + contatore : nome;
        contatore++;
    }

    public boolean isSaltaTurno() {
        boolean tmp = saltaTurno;
        this.saltaTurno = false;
        return tmp;
    }

    public void setSaltaTurno(boolean saltaTurno) {
        this.saltaTurno = saltaTurno;
    }

    public int getInvMonete() {
        return invMonete;
    }

    public void aggiungiGemma() {
        this.invGemme++;
    }

    public void aggiungiPozione() {
        this.invPozioni++;
    }

    public void rimuoviGemma() {
        this.invGemme--;
    }

    public void rimuoviPozione() {
        this.invPozioni--;
    }

    public int getxIniziale() {
        return xIniziale;
    }

    public int getyIniziale() {
        return yIniziale;
    }

    public int getGemmeRaccolte() {
        return invGemme;
    }

    public int getPozioniRaccolte() {
        return invPozioni;
    }



    public void usaGemma() {
        if (invGemme > 0)
            rimuoviGemma();
    }

    public void usaPozione() {
        if (invPozioni > 0)
            rimuoviPozione();
    }


    public String getPatrimonio() {
        if (!isEliminato())
            return "monete (" + invMonete + "), gemme (" + invGemme + "), pozioni magiche (" + invPozioni + ")";
        return "monete (" + invMonete + "), gemme (" + invGemme + "), pozioni magiche (" + invPozioni + ")" + " (Giocatore eliminato)";
    }

    public void setxIniziale(int xIniziale) {
        this.xIniziale = xIniziale;
    }

    public void setyIniziale(int yIniziale) {
        this.yIniziale = yIniziale;
    }

    public void setEliminato() {
        this.eliminato = true;
    }

    public boolean isEliminato() {
        return eliminato;
    }

    public boolean isGiocatoreInBotola(){
        return giocatoreInBotola;
    }

    public void setGiocatoreInBotola(boolean giocatoreInBotola) {
        this.giocatoreInBotola = giocatoreInBotola;
    }

    public void aggiungiMoneta(Moneta moneta) {
        invMonete += moneta.getValore();
        monete.add(moneta);
    }

    public void rimuoviMoneta() {
        invMonete -= monete.getFirst().getValore();
        this.monete.removeFirst();
    }

    public abstract boolean usaAbilita(Albero albero, LogicaDiGioco logicaDiGioco);
    public abstract boolean usaAbilita(Roccia roccia, LogicaDiGioco logicaDiGioco);
    public abstract boolean usaAbilita(Giocatore giocatore);

    public abstract boolean usaAbilita(TavoloDaGioco tavolo, LogicaDiGioco logicaDiGioco, String direzione);

    public abstract boolean usaAbilita(PozioneMagica pozione);
    public abstract boolean zaWarudo();

    public List<Moneta> getMonete() {
        return monete;
    }


}


