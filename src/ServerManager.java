import java.io.*;
import java.net.*;


 
public class ServerManager implements Runnable {
    private final Socket clientSocket;
    private boolean upper = false;
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

         
            out.println("Hello (END to close connection): \n");
            out.flush();

           
            while (true) {
                String str = in.readLine();
                String msg = analyzeReturnMsg(str);
            
                if(msg != null){
                    out.println(msg);
                    out.flush();
                }else{
                    out.println("#");
                    out.flush();
                    continue;

                }


            }
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connection closed: " + clientSocket);
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }


    private String analyzeReturnMsg(String msString) { // This method analyzes the message string and returns a response based on the command
        String[] parts = msString.split(" ");
        if (parts.length == 0) {
            return "ERROR: Invalid command format.";
        }
        String command = parts[0].toUpperCase();
        switch (command) {
            case "END":
                return null; 
            case "GET_ROW":
                if (parts.length != 2) {
                    return "ERROR: Invalid command format. Use GET_ROW <index>";
                }
                try {
                    int rowIndex = Integer.parseInt(parts[1]);
                    if(rowIndex >= 0 && rowIndex < dataManager.returnSize()){
                        Data data = dataManager.getRow(rowIndex);
                        return data.toString();
                    } else {
                        return "Invalid row index: " + rowIndex;
                    }
                } catch (NumberFormatException e) {
                    return "ERROR: Invalid row index format. Must be an integer.";
                    
                }

            case "GET_SIZE":
                return String.valueOf(dataManager.returnSize());

            case "GET_ALL":
                return dataManager.getDataAsString().toString();
            
           

            default:
                return "Unknown command: " + command;
        }
    }
}
