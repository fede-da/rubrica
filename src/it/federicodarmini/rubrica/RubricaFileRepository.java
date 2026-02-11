package it.federicodarmini.rubrica;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Vector;

public class RubricaFileRepository {

    private final File file;

    public RubricaFileRepository() {
        // Working directory
        this.file = new File("informazioni.txt");
    }

    public Vector<Persona> load() {
        Vector<Persona> persone = new Vector<>();

        if (!file.exists()) {
            // Requisito: se non esiste, nessun errore, lista vuota
            return persone;
        }

        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;

                // Eventuali campi vuoti
                String[] parts = line.split(";", -1);
                if (parts.length != 5) {
                    // Riga malformata
                    continue;
                }

                String nome = parts[0];
                String cognome = parts[1];
                String indirizzo = parts[2];
                String telefono = parts[3];

                int eta;
                try {
                    eta = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException ex) {
                    // Eta non valida: ignora riga
                    continue;
                }

                persone.add(new Persona(nome, cognome, indirizzo, telefono, eta));
            }
        } catch (IOException e) {
            return new Vector<>();
        }

        return persone;
    }

    public void saveAll(Vector<Persona> persone) {
        try (PrintStream ps = new PrintStream(file, StandardCharsets.UTF_8)) {
            for (Persona p : persone) {
                // Formato richiesto: nome;cognome;indirizzo;telefono;eta
                ps.printf("%s;%s;%s;%s;%d%n",
                        safe(p.getNome()),
                        safe(p.getCognome()),
                        safe(p.getIndirizzo()),
                        safe(p.getTelefono()),
                        p.getEta()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
