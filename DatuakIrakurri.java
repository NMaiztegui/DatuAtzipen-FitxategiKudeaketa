import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.FileReader; // <-- Datuak irakurtzeko
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

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
                        break;
                    case "3":
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        // JSON IRAKURTZEKO METODOA
                        jsonFitxategiakKudeatu();
                        break;
                    case "4":
                        System.out.print("Aplikaziotik irteten.");
                        break;
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
        if (path != null && !path.isBlank()) {
            rutaArchivo = path;
        }
        try (FileReader reader = new FileReader(rutaArchivo)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            // Impresión “bonita”
            System.out.println(jsonElement.toString());
        } catch (IOException e) {
            System.err.println("Error fitxategia irakurtzen: " + e.getMessage());
        }
    }

    public static void jsonFitxategiakKudeatu() {
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
                        System.out.print("Sartu JSON fitxategiaren bidea (adib. Definir_variables.json): ");
                        String jsonToShow = aukera.nextLine().trim();
                        if (jsonToShow.isEmpty()) jsonToShow = "Definir_variables.json";
                        jsonTxertatu(jsonToShow);
                        System.out.print("Sartu edozer aurrera jarraitzeko: ");
                        aurrera = aukera.nextLine();
                        break;
                    case "2":
                        kontsolaGarbitu();
                        System.out.print("Sartu JSON fitxategiaren bidea (adib. Definir_variables.json): ");
                        String jsonPath = aukera.nextLine().trim();
                        if (jsonPath.isEmpty()) jsonPath = "Definir_variables.json";
                        System.out.print("Sartu irteerako CSV fitxategiaren bidea (adib. irteera.csv): ");
                        String csvPath = aukera.nextLine().trim();
                        if (csvPath.isEmpty()) csvPath = "irteera.csv";
                        try {
                            jsonToCsv(jsonPath, csvPath);
                            System.out.println("\nEginda! CSV sortua hemen: " + new File(csvPath).getAbsolutePath());
                        } catch (IOException e) {
                            System.err.println("Errorea: " + e.getMessage());
                        }
                        pausa();
                        break;
                    case "3":
                        kontsolaGarbitu();
                        System.out.print("Menu nagusira itzultzen...");
                        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                        break;
                    default:
                        kontsolaGarbitu();
                        System.out.print("Sartutako aukera ez da existitzen.\n1etik 4rako zenbaki bat sartu behar duzu.");
                        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
                        break;
                }
            } else {
                kontsolaGarbitu();
                System.out.print("Zenbaki bat sartu behar duzu!");
                try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        } while (!sarrera.equals("3"));
    }

    public static void kontsolaGarbitu() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Convierte JSON (objeto o array de objetos) a CSV:
     *  - Aplana objetos anidados con notación de puntos.
     *  - Arrays de primitivos se unen con " | ".
     *  - Arrays de objetos/mixtos se aplanan con índices [i].
     */
    public static void jsonToCsv(String jsonPath, String csvPath) throws IOException {
        JsonElement root;
        try (FileReader reader = new FileReader(jsonPath)) {
            root = JsonParser.parseReader(reader);
        } catch (IOException e) {
            throw new IOException("Ezin izan da JSON irakurri: " + jsonPath + " (" + e.getMessage() + ")");
        }

        // Normalizamos dataset de filas (lista de mapas “aplanados”)
        List<Map<String, String>> rows = new ArrayList<>();

        if (root == null || root instanceof JsonNull) {
            writeEmptyCsv(csvPath);
            return;
        }

        if (root.isJsonArray()) {
            JsonArray arr = root.getAsJsonArray();
            for (JsonElement el : arr) {
                Map<String, String> row = new LinkedHashMap<>();
                flattenElement(el, "", row);
                rows.add(row);
            }
        } else if (root.isJsonObject()) {
            Map<String, String> row = new LinkedHashMap<>();
            flattenElement(root, "", row);
            rows.add(row);
        } else {
            // Raíz primitiva: crear una sola fila con la clave "value"
            Map<String, String> row = new LinkedHashMap<>();
            row.put("value", primitiveToString(root));
            rows.add(row);
        }

        // Unimos todas las claves para cabeceras, manteniendo orden de aparición
        Set<String> headers = new LinkedHashSet<>();
        for (Map<String, String> r : rows) {
            headers.addAll(r.keySet());
        }

        if (headers.isEmpty()) {
            writeEmptyCsv(csvPath);
            return;
        }

        // Escribir CSV
        try (FileWriter fw = new FileWriter(csvPath)) {
            // Cabecera
            fw.write(joinCsv(new ArrayList<>(headers)));
            fw.write("\n");
            // Filas
            for (Map<String, String> r : rows) {
                List<String> cells = new ArrayList<>();
                for (String h : headers) {
                    String v = r.getOrDefault(h, "");
                    cells.add(csvEscape(v));
                }
                fw.write(joinCsv(cells));
                fw.write("\n");
            }
            fw.flush();
        }
    }

    // ========= Helpers de aplanado =========

    private static final String ARRAY_DELIM = " | "; // separador para arrays de primitivos

    private static void flattenElement(JsonElement el, String prefix, Map<String, String> out) {
        if (el == null || el instanceof JsonNull) {
            out.put(prefixOrValue(prefix), "");
            return;
        }

        if (el.isJsonPrimitive()) {
            out.put(prefixOrValue(prefix), primitiveToString(el));
            return;
        }

        if (el.isJsonObject()) {
            JsonObject obj = el.getAsJsonObject();
            // Si un objeto está vacío y hay un prefijo, creamos la clave con vacío
            if (obj.entrySet().isEmpty()) {
                out.put(prefixOrValue(prefix), "");
                return;
            }
            for (Map.Entry<String, JsonElement> e : obj.entrySet()) {
                String key = e.getKey();
                String newPrefix = prefix.isEmpty() ? key : prefix + "." + key;
                flattenElement(e.getValue(), newPrefix, out);
            }
            return;
        }

        if (el.isJsonArray()) {
            JsonArray arr = el.getAsJsonArray();
            if (arr.size() == 0) {
                out.put(prefixOrValue(prefix), "");
                return;
            }

            // ¿Todo primitivo? -> unimos en una sola celda
            boolean allPrimitives = true;
            for (JsonElement it : arr) {
                if (it == null || it instanceof JsonNull || it.isJsonPrimitive()) continue;
                allPrimitives = false;
                break;
            }
            if (allPrimitives) {
                List<String> vals = new ArrayList<>();
                for (JsonElement it : arr) {
                    vals.add(primitiveToString(it));
                }
                out.put(prefixOrValue(prefix), String.join(ARRAY_DELIM, vals));
                return;
            }

            // Si hay objetos/mixtos -> indexamos
            for (int i = 0; i < arr.size(); i++) {
                String newPrefix = prefixOrValue(prefix) + "[" + i + "]";
                flattenElement(arr.get(i), newPrefix, out);
            }
        }
    }

    private static String prefixOrValue(String prefix) {
        return (prefix == null || prefix.isEmpty()) ? "value" : prefix;
    }

    private static String primitiveToString(JsonElement el) {
        if (el == null || el instanceof JsonNull) return "";
        if (!el.isJsonPrimitive()) return el.toString();
        JsonPrimitive p = el.getAsJsonPrimitive();
        if (p.isString()) return p.getAsString();
        if (p.isBoolean()) return Boolean.toString(p.getAsBoolean());
        if (p.isNumber()) return p.getAsNumber().toString();
        return p.getAsString();
    }

    private static void writeEmptyCsv(String csvPath) throws IOException {
        try (FileWriter fw = new FileWriter(csvPath)) {
            fw.write("");
        }
    }

    // ========= Helpers CSV =========

    // Une una lista de celdas ya escapadas en una línea CSV
    private static String joinCsv(List<String> cells) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(cells.get(i));
        }
        return sb.toString();
    }

    // Escapa según reglas simples de CSV (RFC 4180)
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
