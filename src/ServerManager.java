package BusinessLogic;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerManager implements Runnable {
    private final Socket clientSocket;
    private final DataManager dataManager = DataManager.getInstance();

    public ServerManager(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true)
        ) {
            out.println("Welcome to Cultural Heritage Server. Available commands:\n"
                      + "GET_ROW <index> | SEARCH [options] <term> | COUNT [options] <term>\n"
                      + "SORT <field> | HELP | END");

            while (true) {
                String input = in.readLine();
                if (input == null || input.equalsIgnoreCase("END")) {
                    out.println("Connection closed.");
                    break;
                }

                String response = processCommand(input.trim());
                out.println(response);
                System.out.println("Server response: " + response);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (IOException e) { /* Ignore */ }
        }
    }

    private String processCommand(String input) {
        String result = "";
        String[] parts = input.split(" ");
        if (parts.length < 1) return "Invalid command";

        String command = parts[0].toUpperCase();
        System.out.println(command);
        try {
            switch (command) {
                case "GET_ROW":
                    result = handleGetRow(parts);
                    System.out.println("GET_ROW!");
                    break;
                case "SEARCH":
                    result = handleSearch(parts);
                    break;
                case "COUNT":
                    result = handleCount(parts);
                    break;
                case "SORT":
                    result =  handleSort(parts);
                    System.out.println("SORT!");
                    break;
                case "HELP":
                    result = getHelpMessage();
                    break;
                default:
                    result =  "User#>";
                    System.out.println("result invalid!");
                    break;
            }
        } catch (NumberFormatException e) {
            return "Error: Invalid number format";
        } catch (Exception e) {
            return "Error processing command: " + e.getMessage();
        }

        return result;
    }

    private String handleGetRow(String[] parts) {
        if (parts.length != 2) return "Usage: GET_ROW <index>";
        int index = Integer.parseInt(parts[1]);
        Data data = dataManager.getRow(index);
        return data != null ? data.toString() : "Index out of bounds";
    }

    private String handleSearch(String[] parts) {
        if (parts.length < 3) return "Usage: SEARCH [-n|-c|-t|-a|-o] <term>";
        
        String mode = parts[1];
        String term = String.join(" ", java.util.Arrays.copyOfRange(parts, 2, parts.length));
        ArrayList<Data> results = new ArrayList<>();

        switch (mode.toUpperCase()) {
            case "-N": results = dataManager.searchWDenominazioneParziale(term); break;
            case "-C": results = dataManager.searchWComuneParziale(term); break;
            case "-T": results = dataManager.searchWTipologiaParziale(term); break;
            case "-A": results = dataManager.searchWIndirizzoParziale(term); break;
            case "-O": results = dataManager.searchByOpenDay(term); break;
            default: return "Invalid search mode. Use -n(name), -c(city), -t(type), -a(address), -o(open day)";
        }

        return formatResults(results, "No results found");
    }

    private String handleCount(String[] parts) {
        if (parts.length < 3) return "Usage: COUNT [-e|-p] <term>";
        
        String mode = parts[1];
        String term = String.join(" ", java.util.Arrays.copyOfRange(parts, 2, parts.length));
        int count = 0;

        switch (mode.toUpperCase()) {
            case "-E": count = dataManager.getNumberByTipologia(term); break;
            case "-P": count = dataManager.getNumberByTipologiaParziale(term); break;
            default: return "Invalid count mode. Use -e(exact) or -p(partial)";
        }

        return "Count: " + count;
    }

    private String handleSort(String[] parts) {
        if (parts.length != 2) return "Usage: SORT [n|c|t|a]";
        
        ArrayList<Data> sorted;
        switch (parts[1].toUpperCase()) {
            case "N": sorted = dataManager.sortDataStructure(); break;
            case "C": sorted = dataManager.sortDataStructureByComune(); break;
            case "T": sorted = dataManager.sortDataStructureByTipologia(); break;
            case "A": sorted = dataManager.sortDataStructureByIndirizzo(); break;
            default: return "Invalid sort field. Use n(name), c(city), t(type), a(address)";
        }

        return formatResults(sorted, "Sorting failed");
    }

    private String formatResults(ArrayList<Data> results, String emptyMessage) {
        if (results.isEmpty()) return emptyMessage;
        StringBuilder sb = new StringBuilder();
        results.forEach(data -> sb.append(data.toString()).append("\t"));
        return sb.toString();
    }

    private String getHelpMessage() {

        return "Available commands:\n"
             + "1. GET_ROW <index> - Get entry by index\n"
             + "2. SEARCH [options] <term> - Search entries\n"
             + "   Options: -n(name), -c(city), -t(type), -a(address), -o(open day)\n"
             + "3. COUNT [options] <term> - Count entries\n"
             + "   Options: -e(exact type), -p(partial type)\n"
             + "4. SORT [n|c|t|a] - Sort entries by name/city/type/address\n"
             + "5. HELP - Show this help\n"
             + "6. END - Close connection";
    }
}
