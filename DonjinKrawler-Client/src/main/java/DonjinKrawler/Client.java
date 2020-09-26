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
    private int[][] mapGrid;


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
            var response = in.nextLine();
            if (response.startsWith("SBN")) {
                name = getName();
                out.println(name);
            } else if (response.startsWith("MAP")){
                String data = response.substring(4);
                String[] arrOfStr = data.split(" ", 0);
                int gridSize = Integer.parseInt(arrOfStr[0]);
                String mapString = arrOfStr[1];
                mapGrid = parseMapString(gridSize, mapString);
                printMap(mapGrid, gridSize);
                break;
            }
        }
    }

    private int[][] parseMapString(int gridSize, String mapString) {
        int[][] tempMapGrid = new int[gridSize][gridSize];
        int stringLength = gridSize * gridSize;
        int currentChar = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++ ) {
                tempMapGrid[i][j] = Character.getNumericValue(mapString.charAt(currentChar));
                currentChar++;
            }
        }

        return tempMapGrid;
    }

    private void printMap(int[][] arr, int size) {
        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    private void initUI() {

        frame.setTitle("Donjin Krawler. Player - " + name);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel messageLabel = new JLabel("...");
        messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, BorderLayout.SOUTH);

        frame.add(new Game(in, out, messageLabel, new Player(name)));

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