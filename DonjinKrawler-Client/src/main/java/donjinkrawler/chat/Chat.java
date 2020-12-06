package donjinkrawler.chat;

import javax.swing.*;
import java.awt.*;

public class Chat extends JFrame {

    private static final int CHAT_WIDTH = 470;
    private static final int CHAT_HEIGHT = 300;
    private final JTextField messageBox;
    private final JButton sendMessage;
    private final JTextArea chatBox;

    public Chat() {
        this.setup();

        JPanel southPanel = new JPanel();
        this.add(BorderLayout.SOUTH, southPanel);
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        this.messageBox = new JTextField(30);
        this.sendMessage = new JButton("Send Message");
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
//        sendMessage.addActionListener(new sendMessageButtonListener());
    }

    public void open() {
        this.setVisible(true);
    }

    private void setup() {
        this.setTitle("Chat");
        this.setResizable(false);
        this.setSize(CHAT_WIDTH, CHAT_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
