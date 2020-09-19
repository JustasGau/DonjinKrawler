package main.java.DonjinKrawler;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame{

    public Client() {

        initUI();
        initConnection();
    }

    private void initConnection() {
        System.out.println("Connection");
    }

    private void initUI() {

        add(new Map());

        setTitle("Donjin Krawler");
        setSize(400, 300);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            Client window = new Client();
            window.setVisible(true);
        });
    }
}