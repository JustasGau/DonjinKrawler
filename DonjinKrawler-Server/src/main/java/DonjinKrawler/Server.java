package main.java.DonjinKrawler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;


public class Server {
    // The set of all the print writers for all the clients, used for broadcast.
    private static Set<PrintWriter> writers = new HashSet<>();
    private static Set<Integer> ids = new HashSet<>();

    public static void main(String[] args) throws Exception {

        int serverPort = 59001;
        int threadPool = 4;
        int id = 0;

        System.out.println("Dundžion kroler server is running");
        var pool = Executors.newFixedThreadPool(threadPool);
        try (var listener = new ServerSocket(serverPort)) {
            while (true) {
                id++;
                ids.add(id);
                pool.execute(new Server.Handler(listener.accept(),id));
            }
        }
    }


    private static class Handler implements Runnable {
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private int id;

        public Handler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        public void sendToAll(String message){
            for (PrintWriter writer : writers) {
                if (writer != out)
                    writer.println(message);
            }
        }
        public void sendCurrentPlayers(){
            for (int playerId : ids) {
                if( playerId != id)
                    out.println("CRT " + playerId);
            }
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("MSG You joined the server");
                System.out.println("Player has joined the server");
                sendToAll("MSG A player has joined the server");
                sendToAll("CRT "+id);
                sendCurrentPlayers();
                writers.add(out);

                while (true) {
                    //erroras iseinant nes neberanda kito line, nes nutruksta rysys
                    String input = in.nextLine();
                    sendToAll(input +" "+ id);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                    sendToAll("MSG A player has left");
                    sendToAll("DLT " + id);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
