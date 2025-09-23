import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.FileReader; // <-- Datuak irakurtzeko
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

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

                        jsonFitxategiakKudeatu();
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

    public static void jsonFitxategiakKudeatu() {
        String path = ".\\Definir_variables.json";
        String pathCSV = "./salida.csv";
        String sarrera, aurrera;
        do {
            kontsolaGarbitu();
            System.out.print("Zer egin nahi duzu? \n");
            System.out.println("1- Fitxategia Irakurri");
            System.out.println("2- Fitxategia CSV bihurtu");
            System.out.println("3- Menu nagusira itxuli");
            System.out.print("Zure aukera: ");
            sarrera = aukera.nextLine();
            if (isNumeric(sarrera) == true) {
                switch (sarrera) {
                    case "1":
                        kontsolaGarbitu();
                        // TXT IRAKURTZEKO METODOA
                        jsonTxertatu("Definir_variables.json");
                        System.out.print("Sartu edozer aurrera jarraitzeko: ");
                        aurrera = aukera.nextLine();
                        break;
                    case "2":
                        kontsolaGarbitu(); 
                        System.out.print("Sartu JSON fitxategiaren bidea (adib. Definir_variables.json): "); 
                        String jsonPath = aukera.nextLine().trim(); if (jsonPath.isEmpty()) 
                        jsonPath = "Definir_variables.json"; 
                        System.out.print("Sartu irteerako CSV fitxategiaren bidea (adib. irteera.csv): "); String csvPath = aukera.nextLine().trim(); 
                        if (csvPath.isEmpty()) csvPath = "irteera.csv";
                        try { 
                            jsonToCsv(jsonPath, csvPath); 
                            System.out.println("\n Eginda! CSV sortua hemen: " + new File(csvPath).getAbsolutePath()); 
                        } catch (IOException e) { 
                            System.err.println(" Errorea: " + e.getMessage()); 
                        }
                        pausa(); 
                        break;
                    case "3":
                        kontsolaGarbitu();
                        System.out.print("Menu nagusira itzultzen...");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        kontsolaGarbitu();
                        System.out.print("Sartutako aukera ez da existitzen.\n1etik 4rako zenbaki bat sartu behar duzu.");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } else {
                kontsolaGarbitu();
                System.out.print("Zenbaki bat sartu behar duzu!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!sarrera.equals("3"));
    }

    public static void kontsolaGarbitu() {
        // Kontsola garbitzeko sententziak
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

        public static void jsonToCsv(String jsonPath, String csvPath) throws IOException {
        JsonElement root;
        try (FileReader reader = new FileReader(jsonPath)) {
            root = JsonParser.parseReader(reader);
        } catch (IOException e) {
            throw new IOException("Ezin izan da JSON irakurri: " + jsonPath + " (" + e.getMessage() + ")");
        }

        // Normalizamos a array para tratar ambos casos (objeto único o array)
        JsonArray array;
        if (root.isJsonArray()) {
            array = root.getAsJsonArray();
        } else if (root.isJsonObject()) {
            array = new JsonArray();
            array.add(root.getAsJsonObject());
        } else {
            throw new IOException("JSON formatu ezezaguna. Itxaroten dena: objektu bat edo objektuen array bat.");
        }

        // Unimos todas las claves para la cabecera (orden de inserción estable)
        Set<String> headers = new LinkedHashSet<>();
        for (JsonElement elem : array) {
            if (elem != null && elem.isJsonObject()) {
                headers.addAll(elem.getAsJsonObject().keySet());
            }
        }

        if (headers.isEmpty()) {
            // No había objetos con claves; generamos CSV vacío con aviso
            try (FileWriter fw = new FileWriter(csvPath)) {
                fw.write(""); // CSV vacío
            }
            return;
        }

        // Escribimos CSV
        try (FileWriter fw = new FileWriter(csvPath)) {
            // Cabecera
            fw.write(joinCsv(new ArrayList<>(headers)));
            fw.write("\n");

            // Filas
            for (JsonElement elem : array) {
                List<String> row = new ArrayList<>();
                JsonObject obj = elem != null && elem.isJsonObject() ? elem.getAsJsonObject() : new JsonObject();
                for (String key : headers) {
                    JsonElement val = obj.get(key);
                    row.add(toCsvCell(val));
                }
                fw.write(joinCsv(row));
                fw.write("\n");
            }
            fw.flush();
        }
    }

    // Convierte un JsonElement a texto CSV-safe
    private static String toCsvCell(JsonElement value) {
        if (value == null || value instanceof JsonNull) {
            return ""; // celda vacía para null
        }
        if (value.isJsonPrimitive()) {
            JsonPrimitive p = value.getAsJsonPrimitive();
            if (p.isString()) return csvEscape(p.getAsString());
            if (p.isBoolean()) return csvEscape(Boolean.toString(p.getAsBoolean()));
            if (p.isNumber()) return csvEscape(p.getAsNumber().toString());
        }
        // Si es objeto o array, lo metemos como JSON minificado en la celda
        return csvEscape(value.toString());
    }

    // Une una lista de celdas ya escapadas en una línea CSV
    private static String joinCsv(List<String> cells) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(cells.get(i));
        }
        return sb.toString();
    }

    // Escapa según reglas simples de CSV (RFC 4180):
    // - Encierra entre comillas si contiene coma, comillas dobles o salto de línea
    // - Duplica las comillas internas
    private static String csvEscape(String raw) {
        if (raw == null) return "";
        boolean mustQuote = raw.contains(",") || raw.contains("\"") || raw.contains("\n") || raw.contains("\r");
        String out = raw.replace("\"", "\"\"");
        return mustQuote ? "\"" + out + "\"" : out;
    }


    private static void pausa() {
        System.out.print("\nSakatu Enter jarraitzeko...");
        aukera.nextLine();
    }
}