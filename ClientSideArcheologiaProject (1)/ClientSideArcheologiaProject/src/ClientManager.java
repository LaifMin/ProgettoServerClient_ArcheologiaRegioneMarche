import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientManager {
    final static String nomeServer = "localhost";
    final static int portaServer = 1050;
    private static PrintWriter outToServer;
    public static boolean exit = false;
    static ClientWindow clientWindow = new ClientWindow();
    public static void main(String[] args) {
        System.out.println("Connessione al server in corso...");
        try (Socket sck = new Socket(nomeServer, portaServer)) {
            String rem = sck.getRemoteSocketAddress().toString();
            String loc = sck.getLocalSocketAddress().toString();
            System.out.format("Server (remoto): %s%n", rem);
            System.out.format("Client (locale): %s%n", loc);
            clientWindow.start();
            comunica(sck);
        } catch (UnknownHostException e) {
            System.err.format("Nome di server non valido: %s%n", e.getMessage());
        } catch (IOException e) {
            System.err.format("Errore durante la comunicazione con il server: %s%n", e.getMessage());
        }
    }

    private static void comunica(Socket sck) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(sck.getInputStream(), "UTF-8"));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(sck.getOutputStream(), "UTF-8"), true);
        Scanner scanner = new Scanner(System.in, "UTF-8");

        System.out.println("Connessione stabilita. Digita 'HELP' per vedere i comandi disponibili.");
        String risposta = in.readLine();
        if(!risposta.equalsIgnoreCase("Hello (END to close connection): ")){
            System.out.println("Connection dead");
        }
        System.out.println("Server: " + risposta );
        boolean exitCondition = false;
        do {
            risposta = in.readLine();
            System.out.println(risposta+"\n");

         
            String comando = scanner.nextLine();
            out.println(comando);
            out.flush();
            

        } while (!exitCondition);
        scanner.close();
    }


    public static void setOutToServer(String msg) {
        if (outToServer != null) {
            outToServer.println(msg);
            outToServer.flush();
        } else {
            System.err.println("Error: outToServer not initialized.");
        }
    }


    public static void initOutToServer(Socket sck) throws IOException {
        outToServer = new PrintWriter(new OutputStreamWriter(sck.getOutputStream()), true);
    }
}
