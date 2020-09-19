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

    public static void main(String[] args) throws Exception {

        int serverPort = 59001;
        int threadPool = 4;
        int id = 0;

        System.out.println("Dundžion kroler server is running");
        var pool = Executors.newFixedThreadPool(threadPool);
        try (var listener = new ServerSocket(serverPort)) {
            while (true) {
                pool.execute(new Server.Handler(listener.accept(),id++));
            }
        }
    }


    private static class Handler implements Runnable {
        private String name = "Player";
        private Socket socket;
        private Scanner in;
        private PrintWriter out;
        private int id;

        public Handler(Socket socket, int id) {
            this.socket = socket;
            this.id = id;
        }

        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                // Now that a successful name has been chosen, add the socket's print writer
                // to the set of all writers so this client can receive broadcast messages.
                // But BEFORE THAT, let everyone else know that the new person has joined!
                out.println("Prisijunge " + name + " " + id);
                System.out.println("Prisijunge " + name + " " + id);
                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + name + " " + id + " has joined");
                }
                writers.add(out);

                while (true) {
                    //erroras iseinant nes neberanda kito line, nes nutruksta rysys
                    String input = in.nextLine();
                    System.out.println(input);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (out != null) {
                    writers.remove(out);
                }
                if (name != null) {
                    System.out.println(name + " is leaving");
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + " has left");
                    }
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
