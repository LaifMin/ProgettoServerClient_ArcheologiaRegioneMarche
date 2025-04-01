import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager  {

    private ArrayList<Data> dataStructure = new ArrayList<>(); 
    private static DataManager instance = null; // Singleton instance
    private static final String FILE_NAME = "../Archeologia---Storia-e-Tradizioni---Regione-Marche.csv";
    private static final String FILE_PATH = new File(FILE_NAME).getAbsolutePath(); 
                                                                                                          
                                                                                                         
    private boolean isDataLoaded = false;

    private DataManager() {
        try {
            loadDataFromCSV();
        } catch (IOException | LineExeption e) {
            e.printStackTrace();
        }
        isDataLoaded = true;

    }

    public boolean isDataLoaded() { // Check if data is loaded
        return isDataLoaded;
    }

    public static synchronized DataManager getInstance() { // Using syncronized to ensure thread safety
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int returnSize() { // Return the size of the data structure
        return dataStructure.size();
    }

    private void loadDataFromCSV() throws IOException, LineExeption {
        dataStructure.clear();
        int lineNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            reader.readLine(); // field titles
            while ((linea = reader.readLine()) != null) {
                lineNumber++;
                String[] dati = splitCSVLine(linea); 
    
                if (dati.length == 13 || dati.length == 12) {
                    String urlImmagine = dati[0];
                    String didascalia = dati[1];
                    String tipologiaPDI = dati[2];
                    String denominazione = dati[3];
                    String comune = dati[4];
                    String indirizzo = dati[5];
                    String civico = dati[6].isEmpty() ? "" : dati[6]; // if civico is empty then set it to ""
                    String telefono = dati[7];
                    String email = dati[8];
                    String urlSitoWeb = dati[9];
                    String latitudine = dati[10];
                    String longitudine = dati[11];
                    String orariDiApertura = (dati.length == 13) ? dati[12] : "";  // if opening hours is empty then set it to ""
    
                    Data data = new Data(
                        urlImmagine, didascalia, tipologiaPDI, denominazione, comune,
                        indirizzo, civico, telefono, email, urlSitoWeb, latitudine,
                        longitudine, orariDiApertura
                    );
                    dataStructure.add(data);
                } else {
                    throw new LineExeption(lineNumber); 
                }
            }
        }
    }

    private String[] splitCSVLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
           
                inQuotes = !inQuotes;
            } else if (c == ';' && !inQuotes) {
               
                fields.add(currentField.toString().trim());
                currentField.setLength(0); 
            } else {
                currentField.append(c);
            }
        }
       
        fields.add(currentField.toString().trim());

        return fields.toArray(new String[0]);
    }

    /**
     * @param int index
     * @return Data
     */
    public Data getRow(int index) {
        if (index < 0 || index >= dataStructure.size())
            return null;
        return dataStructure.get(index);
    }

}
