package BusinessLogic;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DataManager {


    private ArrayList<Data> dataStructure = new ArrayList<>();
    private static DataManager instance = null; // Singleton instance
    private static final String FILE_NAME = "./Archeologia---Storia-e-Tradizioni---Regione-Marche.csv";
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
                    String orariDiApertura = (dati.length == 13) ? dati[12] : ""; // if opening hours is empty then set
                                                                                  // it to ""


                    Data data = new Data(
                            urlImmagine, didascalia, tipologiaPDI, denominazione, comune,
                            indirizzo, civico, telefono, email, urlSitoWeb, latitudine,
                            longitudine, orariDiApertura);
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


    public Data getRow(int index) { // Get a row of data using GET_ROW <index>
        if (index < 0 || index >= dataStructure.size())
            return null;
        return dataStructure.get(index);
    }


    public ArrayList<Data> searchWDenominazione(String denominazione) { // Search by name using SEARCH -n <name>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getDenominazione().equalsIgnoreCase(denominazione)) {
                tmp.add(data);
            }
        }


        return tmp;
    }


    public ArrayList<Data> searchWComune(String comune) { // Search by city using SEARCH -c <city>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getComune().equalsIgnoreCase(comune)) {
                tmp.add(data);
            }
        }
        return tmp;
    }


    public ArrayList<Data> searchWTipologia(String tipologia) { // Search by type using SEARCH -t <type>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getTipologiaPDI().equalsIgnoreCase(tipologia)) {
                return tmp;
            }
        }
        return tmp;
    }


    public Data searchWIndirizzo(String indirizzo) { // Search by address using SEARCH -a <address>
        for (Data data : dataStructure) {
            if (data.getIndirizzo().equalsIgnoreCase(indirizzo)) {
                return data;
            }
        }
        return null;
    }


    public ArrayList<Data> searchWDenominazioneParziale(String denominazione) { // Search by partial name using SEARCH
                                                                                // --n <name>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getDenominazione().toLowerCase().contains(denominazione.toLowerCase())) {
                tmp.add(data);
            }
        }
        return tmp;
    }


    public ArrayList<Data> searchWComuneParziale(String comune) { // Search by partial city using SEARCH --c <city>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getComune().toLowerCase().contains(comune.toLowerCase())) {
                tmp.add(data);
            }
        }
        return tmp; // Return null if not found
    }


    public ArrayList<Data> searchWTipologiaParziale(String tipologia) { // Search by partial type using SEARCH --t
                                                                        // <type>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getTipologiaPDI().toLowerCase().contains(tipologia.toLowerCase())) {
                tmp.add(data);
            }
        }
        return tmp; // Return null if not found
    }


    public ArrayList<Data> searchWIndirizzoParziale(String indirizzo) { // Search by partial address using SEARCH --a
                                                                        // <address>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getIndirizzo().toLowerCase().contains(indirizzo.toLowerCase())) {
                tmp.add(data);
            }
        }
        return tmp; // Return null if not found
    }


    public ArrayList<Data> sortDataStructure() { // Sort the data structure by name using SORT -n


        ArrayList<Data> sortedData = new ArrayList<>(dataStructure);
        sortedData.sort((data1, data2) -> {
            String denominazione1 = data1.getDenominazione().toLowerCase();
            String denominazione2 = data2.getDenominazione().toLowerCase();
            return denominazione1.compareTo(denominazione2);
        });
        return sortedData;
    }


    public ArrayList<Data> sortDataStructureByComune() { // Sort the data structure by city using SORT -c


        ArrayList<Data> sortedData = new ArrayList<>(dataStructure);
        sortedData.sort((data1, data2) -> {
            String comune1 = data1.getComune().toLowerCase();
            String comune2 = data2.getComune().toLowerCase();
            return comune1.compareTo(comune2);
        });
        return sortedData;
    }


    public ArrayList<Data> sortDataStructureByTipologia() { // Sort the data structure by type using SORT -t


        ArrayList<Data> sortedData = new ArrayList<>(dataStructure);
        sortedData.sort((data1, data2) -> {
            String tipologia1 = data1.getTipologiaPDI().toLowerCase();
            String tipologia2 = data2.getTipologiaPDI().toLowerCase();
            return tipologia1.compareTo(tipologia2);
        });
        return sortedData;
    }


    public ArrayList<Data> sortDataStructureByIndirizzo() { // Sort the data structure by address using SORT -a


        ArrayList<Data> sortedData = new ArrayList<>(dataStructure);
        sortedData.sort((data1, data2) -> {
            String indirizzo1 = data1.getIndirizzo().toLowerCase();
            String indirizzo2 = data2.getIndirizzo().toLowerCase();
            return indirizzo1.compareTo(indirizzo2);
        });
        return sortedData;
    }


   
    public ArrayList<Data> searchByOpenDay(String day) { // Search by open day using SEARCH --o <day>
        ArrayList<Data> tmp = new ArrayList<>();
        for (Data data : dataStructure) {
            if (data.getOrariDiApertura().toLowerCase().contains(day.toLowerCase())) {
                tmp.add(data);
            }
        }
        return tmp;
    }




    public int getNumberByTipologia(String tipologia){ // Get the number of types using COUNT -t <type>
        int count = 0;
        for(Data data : dataStructure){
            if(data.getTipologiaPDI().toLowerCase().equals(tipologia.toLowerCase())){
                count++;
            }
        }
        return count;
       
    }


   
    public int getNumberByComune(String comune){ // Get the number of cities using COUNT -c <city>
        int count = 0;
        for(Data data : dataStructure){
            if(data.getComune().toLowerCase().equals(comune.toLowerCase())){
                count++;
            }
        }
        return count;
    }


    public int getNumberByTipologiaParziale(String tipologia){ // Get the number of partial types using COUNT --t <type>
        int count = 0;
        for(Data data : dataStructure){
            if(data.getTipologiaPDI().toLowerCase().contains(tipologia.toLowerCase())){
                count++;
            }
        }
        return count;
    }


    public int getNumberByComuneParziale(String comune){ // Get the number of partial cities using COUNT --c <city>
        int count = 0;
        for(Data data : dataStructure){
            if(data.getComune().toLowerCase().contains(comune.toLowerCase())){
                count++;
            }
        }
        return count;
    }




}
