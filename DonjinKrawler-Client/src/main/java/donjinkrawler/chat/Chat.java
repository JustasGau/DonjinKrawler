package donjinkrawler.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JFrame {

    private static final int CHAT_WIDTH = 470;
    private static final int CHAT_HEIGHT = 300;
    private JTextArea chatBox;
    private String username;

    public Chat(String username) {
        this.username = username;
        this.setup();
    }

    public void open() {
        this.setVisible(true);
    }

    public void addMessage(String sender, String message) {
        this.chatBox.append("<" + sender + ">: " + message + '\n');
    }

    private void setup() {

        JPanel southPanel = new JPanel();
        this.add(BorderLayout.SOUTH, southPanel);
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        JTextField messageBox = new JTextField(30);
        JButton sendMessage = new JButton("Send Message");
        this.chatBox = new JTextArea();
        this.chatBox.setEditable(false);
        this.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        chatBox.setLineWrap(true);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        GridBagConstraints right = new GridBagConstraints();
        right.anchor = GridBagConstraints.EAST;
        right.weightx = 2.0;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));

        String username = this.username;
        
        sendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (messageBox.getText().length() < 1) {
                    // do nothing
                } else {
                    // TODO: send these values to server: username  messageBox.getText()
                    messageBox.setText("");
                }
            }
        });

        this.setTitle("Chat");
        this.setResizable(false);
        this.setSize(CHAT_WIDTH, CHAT_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
