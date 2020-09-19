package main.java.DonjinKrawler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client{

    JFrame frame = new JFrame();
    int screenWidth = 500;
    int screenHeight = 500;

    int serverPort = 59001;
    String serverAddress;
    Scanner in;
    PrintWriter out;


    public Client(String serverAddress) throws IOException {
        this.serverAddress = serverAddress;
        if(serverAddress != "0.0.0.0")
            initConnection();

        initUI();
    }

    private void initConnection() throws IOException {
        var socket = new Socket(serverAddress, serverPort);
        in = new Scanner(socket.getInputStream());
        out = new PrintWriter(socket.getOutputStream(), true);
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

        frame.add(new Map(in, out, messageLabel));

    }

    public static void main(String[] args) throws IOException {
        String address = "0.0.0.0";
        if(args.length > 0)
            address = args[0];
        Client window = new Client(address);
        window.frame.setVisible(true);
    }
}