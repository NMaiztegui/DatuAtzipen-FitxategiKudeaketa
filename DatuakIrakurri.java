import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.FileReader; // <-- Datuak irakurtzeko
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

public class DatuakIrakurri {
    public static Scanner aukera = new Scanner(System.in);

    public static void main(String[] args) {
        String sarrera;
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print("Zer egin nahi duzu? \n");
            System.out.println("1- .txt bat kudeatu");
            System.out.println("2- .XML bat kudeatu");
            System.out.println("3- .json bat kudeatu");
            System.out.println("4- Aplikazioa Itxi");
            sarrera = aukera.nextLine();
            if (isNumeric(sarrera) == true) {
                switch (sarrera) {
                    case "1":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        // TXT IRAKURTZEKO METODOA
                        break;
                    case "2":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        // XML IRAKURTZEKO METODOA
                    case "3":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        // JSON IRAKURTZEKO METODOA
                        jsonTxertatu("Definir_variables.json");
                        break;
                    case "4":
                        System.out.print("Aplikazioatik urtetan.");
                        // Aplikazioa itxi
                    default:
                        break;
                }
            } else {
                System.out.println("Zenbaki bat sartu behar duzu X/Y formatuan!");
            }
        } while (!sarrera.equals("4"));
    }

    public static boolean isNumeric(String cadenaString) {
        try {
            Integer.parseInt(cadenaString);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static void jsonTxertatu(String path) {
        String rutaArchivo = "Definir_variables.json";

        try (FileReader reader = new FileReader(rutaArchivo)) {
            // Parsear el archivo JSON
            JsonElement jsonElement = JsonParser.parseReader(reader);

            // Imprimir JSON en formato bonito
            System.out.println(jsonElement.toString());
        } catch (IOException e) {
            System.err.println("Error fitxategia irakurtzen: " + e.getMessage());
        }
    }
}