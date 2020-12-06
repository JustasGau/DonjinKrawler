package donjinkrawler.chat;

import com.esotericsoftware.kryonet.Client;
import krawlercommon.packets.ChatMessagePacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Chat extends JFrame {

    private static final int CHAT_WIDTH = 470;
    private static final int CHAT_HEIGHT = 300;

    private final JTextArea chatBox;

    public Chat(Client client, String username, int playerId) {
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

        this.setTitle("Chat");
        this.setResizable(false);
        this.setSize(CHAT_WIDTH, CHAT_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        Chat chat = this;

        sendMessage.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (messageBox.getText().length() < 1) {
                    return; // do nothing
                }
                chat.addMessage(username, messageBox.getText());
                client.sendTCP(new ChatMessagePacket(messageBox.getText(), playerId));
                messageBox.setText("");
            }
        });
    }

    public void open() {
        this.setVisible(true);
    }

    public void addMessage(String sender, String message) {
        this.chatBox.append("<" + sender + ">: " + message + '\n');
    }
}
