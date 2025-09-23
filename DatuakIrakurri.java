import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;

public class DatuakIrakurri {
    public static Scanner aukera = new Scanner(System.in);

    public static void main(String[] args) {
        String sarrera, aurrera;
        do {
            kontsolaGarbitu();
            System.out.print(Koloreak.Cyan + "Zer egin nahi duzu? \n" + Koloreak.Berdea);
            System.out.println("1- .txt bat kudeatu");
            System.out.println("2- .XML bat kudeatu");
            System.out.println("3- .json bat kudeatu");
            System.out.println("4- Aplikazioa Itxi");
            System.out.print(Koloreak.Cyan + "Zure aukera: " + Koloreak.RESET);
            sarrera = aukera.nextLine();
            if (isNumeric(sarrera) == true) {
                switch (sarrera) {
                    case "1":
                        kontsolaGarbitu();
                        // * TXT IRAKURTZEKO METODOA
                        txtFitxategiakKudeatu();
                        break;
                    case "2":
                        kontsolaGarbitu();
                        // * XML IRAKURTZEKO METODOA
                        xmlGorde();
                        Scanner sc = new Scanner(System.in);
                        System.out.println("Csv-ra pasatu nahi duzu? (Bai/Ez)");
                        String erantzuna = sc.nextLine();
                        if (erantzuna.equalsIgnoreCase("Bai")) {
                            System.out.println("CSV-ra pasatzen...");
                            xmlIdatziCSV("Helbidea.xml", "Helbidea.csv");
                        } else {
                            System.out.println("Ez da CSV-ra pasatuko.");
                        }
                        break;
                    case "3":
                        kontsolaGarbitu();
                        // * JSON IRAKURTZEKO METODOA
                        jsonFitxategiakKudeatu();
                        break;

                    case "4":
                        kontsolaGarbitu();
                        System.out.print(Koloreak.Gorria + "Aplikazioatik urtetan." + Koloreak.RESET);
                        System.exit(0); // * Honek programa bereala itxiko du

                    default:
                        kontsolaGarbitu();
                        System.out
                                .print("Sartutako aukera ez da existitzen.\n1etik 4rako zenbaki bat sartu behar duzu.");
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } else {
                kontsolaGarbitu();
                System.out.print(Koloreak.Gorria + "Zenbaki bat sartu behar duzu!" + Koloreak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!sarrera.equals("4"));
    }

    public static void txtFitxategiakKudeatu() {
        String path = ".\\TXT-Datuak.txt";
        String pathCSV = "./TXT-CSV-Datuak.csv";
        String sarrera, aurrera;
        do {
            kontsolaGarbitu();
            System.out.print(Koloreak.Cyan + "Zer egin nahi duzu? \n" + Koloreak.Berdea);
            System.out.println("1- Fitxategia Irakurri");
            System.out.println("2- Fitxategia CSV bihurtu");
            System.out.println("3- Menu nagusira itxuli");
            System.out.print(Koloreak.Cyan + "Zure aukera: " + Koloreak.RESET);
            sarrera = aukera.nextLine();
            if (isNumeric(sarrera) == true) {
                switch (sarrera) {
                    case "1":
                        kontsolaGarbitu();
                        // TXT IRAKURTZEKO METODOA
                        txtIrakurri(path);
                        System.out.print(Koloreak.Gorria + "Sartu edozer aurrera jarraitzeko: " + Koloreak.RESET);
                        aurrera = aukera.nextLine();
                        break;
                    case "2":
                        kontsolaGarbitu();
                        // CSV BIHURTZEKO METODOA
                        txtIdatziCSV(path, pathCSV);
                        System.out.print(
                                Koloreak.Gorria + "Sartu edozer hasierako menura itzultzeko: " + Koloreak.RESET);
                        aurrera = aukera.nextLine();
                        break;
                    case "3":
                        System.out.print(Koloreak.Gorria + "Menu nagusira itxultzen." + Koloreak.RESET);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        main(new String[0]);
                        break;
                    default:
                        kontsolaGarbitu();
                        System.out.print(Koloreak.Gorria
                                + "Sartutako aukera ez da existitzen.\n1etik 4rako zenbaki bat sartu behar duzu."
                                + Koloreak.RESET);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            } else {
                kontsolaGarbitu();
                System.out.print(Koloreak.Gorria + "Zenbaki bat sartu behar duzu!" + Koloreak.RESET);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (!sarrera.equals("3"));
    }

    public static void txtIrakurri(String path) {
        try {
            File txtFiles = new File(path);
            // scanner bitartez lerroz lerro fitxategia irakurriko da
            Scanner lector = new Scanner(txtFiles);
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                System.out.println(linea);
            }
            lector.close();

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static void txtIdatziCSV(String txtPath, String csvPath) {
        String delimiter = ";"; // Separador en el TXT (tab, espacio, etc. --> ; en nuestro caso)
        try (Scanner sc = new Scanner(new File(txtPath));
                FileWriter fw = new FileWriter(csvPath)) {
            // CSV-rako lehen lerroa idatzi, goiburu gisa
            fw.write("NAN,ADINA\n");

            // TXTko lerro bakoitza irakurri eta CSVan idatzi
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] campos = line.split(delimiter);

                // Datuak koma bidez bereizi eta idatzi
                for (int i = 0; i < campos.length; i++) {
                    fw.write(campos[i]);
                    if (i < campos.length - 1) {
                        fw.write(",");
                    }
                }
                fw.write("\n"); // Lerro berria
            }
            System.out.println("Fitxategia CSVra bihurtu da goiburuarekin ondo.");

        } catch (IOException e) {
            e.printStackTrace();
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
                        String jsonPath = aukera.nextLine().trim();
                        if (jsonPath.isEmpty())
                            jsonPath = "Definir_variables.json";
                        System.out.print("Sartu irteerako CSV fitxategiaren bidea (adib. irteera.csv): ");
                        String csvPath = aukera.nextLine().trim();
                        if (csvPath.isEmpty())
                            csvPath = "irteera.csv";
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
                        System.out
                                .print("Sartutako aukera ez da existitzen.\n1etik 4rako zenbaki bat sartu behar duzu.");
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
            if (p.isString())
                return csvEscape(p.getAsString());
            if (p.isBoolean())
                return csvEscape(Boolean.toString(p.getAsBoolean()));
            if (p.isNumber())
                return csvEscape(p.getAsNumber().toString());
        }
        // Si es objeto o array, lo metemos como JSON minificado en la celda
        return csvEscape(value.toString());
    }

    // Une una lista de celdas ya escapadas en una línea CSV
    private static String joinCsv(List<String> cells) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cells.size(); i++) {
            if (i > 0)
                sb.append(',');
            sb.append(cells.get(i));
        }
        return sb.toString();
    }

    // Escapa según reglas simples de CSV (RFC 4180):
    // - Encierra entre comillas si contiene coma, comillas dobles o salto de línea
    // - Duplica las comillas internas
    private static String csvEscape(String raw) {
        if (raw == null)
            return "";
        boolean mustQuote = raw.contains(",") || raw.contains("\"") || raw.contains("\n") || raw.contains("\r");
        String out = raw.replace("\"", "\"\"");
        return mustQuote ? "\"" + out + "\"" : out;
    }

    private static void pausa() {
        System.out.print("\nSakatu Enter jarraitzeko...");
        aukera.nextLine();
    }

    public static void xmlGorde() {
        // XML gordetzeko metodoa
        try {
            // Fitxategia kargatu
            File fitxategia = new File("Helbidea.xml");

            // XML parser sortu
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fitxategia);

            // Normalizatu dokumentua
            doc.getDocumentElement().normalize();

            System.out.println("Datu-Basea: " + doc.getDocumentElement().getNodeName());

            // Pertsonen zerrenda hartu
            NodeList nList = doc.getElementsByTagName("pertsona");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nodo = nList.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;

                    String nan = element.getElementsByTagName("NAN").item(0).getTextContent();
                    String helbidea = element.getElementsByTagName("helbidea").item(0).getTextContent();

                    System.out.println("------ Pertsona " + (i + 1) + " ------");
                    System.out.println("NAN: " + nan);
                    System.out.println("Helbidea: " + helbidea);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void xmlIdatziCSV(String xmlPath, String csvPath) {
        try {
            // Fitxategia kargatu
            File fitxategia = new File(xmlPath);

            // XML parser sortu
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fitxategia);

            // Normalizatu dokumentua
            doc.getDocumentElement().normalize();

            // Pertsonen zerrenda hartu
            NodeList nList = doc.getElementsByTagName("pertsona");

            // CSV fitxategia sortu
            try (FileWriter fw = new FileWriter(csvPath)) {
                // Lehenengo lerroa (goiburuak)
                fw.write("NAN;Helbidea\n");

                // Pertsona bakoitza irakurri eta CSVan idatzi
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nodo = nList.item(i);

                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) nodo;

                        String nan = element.getElementsByTagName("NAN").item(0).getTextContent();
                        String helbidea = element.getElementsByTagName("helbidea").item(0).getTextContent();

                        // CSV fitxategira idatzi
                        fw.write(nan + ";" + helbidea + "\n");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String cadenaString) {
        try {
            Integer.parseInt(cadenaString);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public static void kontsolaGarbitu() {
        // Kontsola garbitzeko sententziak
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}