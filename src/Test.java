import utilities.Partita;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Vuoi ricaricare la partita precedente? (si/no)");
        String risposta = input.nextLine();
        while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
            System.out.println("inserisci una risposta valida");
            risposta = input.nextLine();
        }

        if (risposta.equalsIgnoreCase("si")) {
            Partita partitaSalvata = null;
            try (FileInputStream fis = new FileInputStream("Data.dat");
                 ObjectInputStream in = new ObjectInputStream(fis)) {
                partitaSalvata = (Partita) in.readObject();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            partitaSalvata.avviaPartitaSalvata();
        }

        Partita partita = new Partita();
        partita.iniziaPartita();
    }
}
