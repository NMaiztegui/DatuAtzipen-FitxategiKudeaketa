import java.io.FileWriter; // <-- Datuak gordetzeko
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
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
                        // TXT IRAKURTZEKO METODOA
                        txtFitxategiakKudeatu();
                        break;
                    case "2":
                        kontsolaGarbitu();
                        // XML IRAKURTZEKO METODOA
                    case "3":
                        kontsolaGarbitu();
                        // JSON IRAKURTZEKO METODOA
                    case "4":
                        kontsolaGarbitu();
                        System.out.print(Koloreak.Gorria + "Aplikazioatik urtetan." + Koloreak.RESET);
                        System.exit(0); // Esto cierra el programa inmediatamente

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
                        main(new String[0]); // Esto cierra el programa inmediatamente
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
            // scanner bitartez fitxategia irakurriko da lerroz lerro
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
        String delimiter = ";"; // Separador en el TXT (tab, espacio, etc.)
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

    public static boolean isNumeric(String cadenaString) {
        // String[] cadena = cadenaString.split("/");
        try {
            Integer.parseInt(cadenaString);
            // Integer.parseInt(cadena[1]);
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