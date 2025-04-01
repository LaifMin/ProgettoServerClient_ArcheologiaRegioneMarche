import java.io.*;
import java.net.*;


/**
 * 
 * @author Alex
 */
public class Main {

    public static final int PORT = 1050; // porta al di fuori del range 1-1024 !

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("EchoServer: started ");
            System.out.println("Server Socket: " + serverSocket);

            while (true) {
             
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted: " + clientSocket);

              
                new Thread(new ServerManager(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
}


