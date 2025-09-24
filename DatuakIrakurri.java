import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.FileReader; // <-- Datuak irakurtzeko
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class DatuakIrakurri{
    public static Scanner aukera = new Scanner(System.in);

    public static void main(String[] args) {
        String sarrera;
        do{
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.print("Zer egin nahi duzu? \n");
            System.out.println("1- .txt bat kudeatu");
            System.out.println("2- .XML bat kudeatu");
            System.out.println("3- .json bat kudeatu");
            System.out.println("4- Aplikazioa Itxi");
            sarrera = aukera.nextLine();
            if(isNumeric(sarrera) == true){
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
                        xmlGorde();                     Scanner sc = new Scanner(System.in);
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
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        // JSON IRAKURTZEKO METODOA
                        break;
                    case "4":
                        System.out.print("Aplikazioatik urtetan.");
                        // Aplikazioa itxi
                        break;
                    default:
                        break;
                }
            }else{
                System.out.println("Zenbaki bat sartu behar duzu X/Y formatuan!" );
            }
        }while( !sarrera.equals("4"));
    }

    public static boolean isNumeric(String cadenaString) {
        //String[] cadena = cadenaString.split("/");
        try {
            Integer.parseInt(cadenaString);
            //Integer.parseInt(cadena[1]);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
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
                String izena = element.getElementsByTagName("Izena").item(0).getTextContent();
                String abizena = element.getElementsByTagName("Abizena").item(0).getTextContent();
                String adina = element.getElementsByTagName("Adina").item(0).getTextContent();
                String helbidea = element.getElementsByTagName("Helbidea").item(0).getTextContent();

                System.out.println("------ Pertsona " + (i + 1) + " ------");
                System.out.println("NAN: " + nan);
                System.out.println("Izena: " + izena);
                System.out.println("Abizena: " + abizena);
                System.out.println("Adina: " + adina);
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
            fw.write("NAN;Izena;Abizena;Adina;Helbidea\n");

            // Pertsona bakoitza irakurri eta CSVan idatzi
            for (int i = 0; i < nList.getLength(); i++) {
                Node nodo = nList.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;

                    String nan = element.getElementsByTagName("NAN").item(0).getTextContent();
                    String izena = element.getElementsByTagName("Izena").item(0).getTextContent();
                    String abizena = element.getElementsByTagName("Abizena").item(0).getTextContent();
                    String adina = element.getElementsByTagName("Adina").item(0).getTextContent();
                    String helbidea = element.getElementsByTagName("Helbidea").item(0).getTextContent();

                    // CSV fitxategira idatzi
                    fw.write(nan + ";" + izena + ";" + abizena + ";" + adina + ";" + helbidea + "\n");
                }
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}