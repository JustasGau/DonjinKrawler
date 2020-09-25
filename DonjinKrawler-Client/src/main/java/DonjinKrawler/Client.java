package main.java.DonjinKrawler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client{

    private JFrame frame = new JFrame();
    private int screenWidth = 500;
    private int screenHeight = 500;

    private int serverPort = 59001;
    private String serverAddress;
    private Scanner in;
    private PrintWriter out;
    private String name;


    public Client(String serverAddress) throws IOException {
        this.serverAddress = serverAddress;
        initConnection();
        logIn();
        initUI();
    }

    private void initConnection() throws IOException {
        var socket = new Socket(serverAddress, serverPort);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private void logIn() {
        while (in.hasNextLine()) {
            var line = in.nextLine();
            if (line.startsWith("SBN")) {
                name = getName();
                out.println(name);
            } else if (line.startsWith("ACC")){
                break;
            }
        }
    }

    private void initUI() {

        frame.setTitle("Donjin Krawler");
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel messageLabel = new JLabel("...");
        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        frame.add(new Map(in, out, messageLabel, new Player(name)));

    }

    private String getName() {
        return JOptionPane.showInputDialog(frame, "Pasirink vardą:", "Vartotojo vardas",
                JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) throws IOException {
        String address = "127.0.0.1";
        if(args.length > 0)
            address = args[0];
        Client window = new Client(address);
        window.frame.setVisible(true);
    }
}