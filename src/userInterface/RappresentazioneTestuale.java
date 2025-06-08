package userInterface;

import elementiDiGioco.*;
import utilities.Partita;
import java.io.*;
import java.util.Scanner;

public class RappresentazioneTestuale implements Serializable{
    @Serial
    private static final long serialVersionUID = -7954923494680712710L;
    private File file = new File("Data.dat");
    private static final Scanner input = new Scanner(System.in);



    private String chiediNomeGiocatore(Giocatore[] giocatori) {
        String nomeGiocatore;
        boolean nomeDuplicato;

        do {
            nomeGiocatore = input.nextLine().trim();
            nomeDuplicato = false;

            for (Giocatore giocatore : giocatori) {
                if (giocatore != null && giocatore.getNome() != null && giocatore.getNome().equalsIgnoreCase(nomeGiocatore)) {
                    nomeDuplicato = true;
                    System.out.println("Nome non valido. Riprova. ");
                    break;
                }
            }
            if (nomeGiocatore.isBlank()) {
                System.out.println("Il nome non può essere vuoto. Riprova.");
            }
        } while (nomeGiocatore.isBlank() || nomeDuplicato);
        return nomeGiocatore;
    }

    private char chiediSimboloGiocatore(Giocatore[] giocatori) {
        char simboloGiocatore = ' ';
        boolean simboloDuplicato = false;
        boolean simboloNonValido = false;
        String inputUtente;

        do {
            inputUtente = input.nextLine();
            if (inputUtente.isBlank()) {
                System.out.println("Il simbolo non può essere vuoto. Riprova. ");
                continue;
            }
            simboloGiocatore = inputUtente.charAt(0);

            simboloNonValido = false;
            if (simboloGiocatore == '$' || simboloGiocatore == '#' || simboloGiocatore == '@' || simboloGiocatore == '%' || simboloGiocatore == '&' || simboloGiocatore == '€') {
                simboloNonValido = true;
                System.out.println("Simbolo non valido. Riprova. ");
                continue;
            }

            if (inputUtente.length() > 1) {
                if (inputUtente.charAt(0) == ' ') {
                    simboloNonValido = true;
                    System.out.println("Simbolo non valido. Riprova. ");
                    continue;
                }
                System.out.println("Hai inserito una stringa. Verrà considerato solo il primo carattere: " + simboloGiocatore);
            }

            simboloDuplicato = false;
            for (Giocatore giocatore : giocatori) {
                if (giocatore != null && giocatore.getSimbolo() == simboloGiocatore) {
                    simboloDuplicato = true;
                    System.out.println("Simbolo non valido. Riprova. ");
                    break;
                }
            }

        } while (inputUtente.isBlank() || simboloDuplicato || simboloNonValido);
        return simboloGiocatore;
    }


    public Giocatore[] chiediGiocatori() {
        Giocatore[] giocatori = new Giocatore[4];

        for (int i = 0; i < giocatori.length; i++) {
            System.out.println("Inserire il nome del giocatore " + (i + 1) + ": ");
            String nome = chiediNomeGiocatore(giocatori);
            System.out.println("Inserire il simbolo per " + nome + ": ");
            char simboloGiocatore = chiediSimboloGiocatore(giocatori);
            int scelta;
            do {
                System.out.println("Scegli la classe per " + nome + ":");
                System.out.println("1. Hakari");
                System.out.println("2. Hol Horse");
                System.out.println("3. Treasure Hunter");
                System.out.println("4. Dio");

                while (!input.hasNextInt()) {
                    System.out.println("Errore: Inserisci un numero!");
                    input.next();
                }
                scelta = input.nextInt();
                input.nextLine();
            } while (scelta < 0);

            switch (scelta) {
                case 1:
                    giocatori[i] = new Hakari();
                    System.out.println(nome + " ha scelto la classe Hakari!");
                    break;
                case 2:
                    giocatori[i] = new HolHorse();
                    System.out.println(nome + " ha scelto la classe HolHorse!");
                    break;
                case 3:
                    giocatori[i] = new TreasureHunter();
                    System.out.println(nome + " ha scelto la classe Treasure Hunter!");
                    break;
                case 4:
                    giocatori[i] = new Dio();
                    System.out.println(nome + " ha scelto la classe Dio!");
                    break;
                default:
                    System.out.println("Scelta non valida, verrà assegnata la classe di default (Hakari)");
                    giocatori[i] = new Hakari();
                    break;
            }
            giocatori[i].setNome(nome);
            giocatori[i].setSimbolo(simboloGiocatore);

        }
        return giocatori;
    }

    public void mostraMenu(Partita partita) {
        Scanner input = new Scanner(System.in);
        int scelta;

        do {
            if (partita.isFinePartita()) {
                break;
            }
            System.out.println();
            System.out.println("0. Esci (Salva partita)");
            System.out.println("1. Mostra griglia");
            System.out.println("2. Muovi");
            System.out.println("3. Mostra patrimonio");
            System.out.println("Scelta: ");

            while (!input.hasNextInt()) {
                System.out.println("Inserisci un numero valido: ");
                input.nextLine();
            }
            scelta = input.nextInt();
            input.nextLine();

            switch (scelta) {
                case 0:
                    try (FileOutputStream fos = new FileOutputStream("Data.dat");
                         ObjectOutputStream out = new ObjectOutputStream(fos)) {
                        out.writeObject(partita);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    partita.setFinePartita(true);
                    break;
                case 1:
                    System.out.println(partita.getTavoloDaGioco());
                    break;
                case 2:
                    partita.getLogicaDiGioco().playerMoving();
                    break;
                case 3:
                    mostraPatrimonio(partita.getTavoloDaGioco().getGiocatori());
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova. ");
            }
        } while (scelta != 0 && partita.isFinePartita());
    }

    public void mostraPatrimonio(Giocatore[] giocatori) {
        for (Giocatore giocatore : giocatori) {
            if (giocatore != null)
                System.out.println("Giocatore " + giocatore.getNome() + "(" + giocatore.getSimbolo() + ") -- Patrimonio: " + giocatore.getPatrimonio());
        }
    }

    public String chiediUsoAbilita() {
            System.out.println("Vuoi usare la tua abilità?");
            String risposta = input.nextLine();
            while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                System.out.println("Inserisci una risposta valida. ");
                risposta = input.nextLine();
            }
            return risposta;
    }

    public String chiediUsoGemma(Giocatore sfidato) {
        System.out.println(sfidato.getNome() + " desideri usare una gemma? (SI/NO)");
        String scelta;
        do {
            scelta = input.nextLine().trim();
            if (!scelta.equalsIgnoreCase("SI") && !scelta.equalsIgnoreCase("NO")) {
                System.out.println("Inserisci una risposta valida. ");
            }
        } while (!scelta.equalsIgnoreCase("SI") && !scelta.equalsIgnoreCase("NO"));
        return scelta;
    }

    public String chiediUsoPozione(Giocatore sfidante) {
        System.out.println(sfidante.getNome() + " desideri usare una pozione? (SI/NO)");
        String scelta;
        do {
            scelta = input.nextLine().trim();
            if (!scelta.equalsIgnoreCase("SI") && !scelta.equalsIgnoreCase("NO")) {
                System.out.println("Inserisci una risposta valida.");
            }
        } while (!scelta.equalsIgnoreCase("SI") && !scelta.equalsIgnoreCase("NO"));
        return scelta;
    }

    public void chiudiScanner() {
        input.close();
    }

}

