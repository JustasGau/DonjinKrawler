package donjinkrawler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

public class Inventory {

    private final JFrame frame;

    public Inventory() {
        this.frame = new InventoryFrame();
    }

    public void open() {
        frame.setVisible(true);
    }
}

class InventoryFrame extends JFrame {

    private static final int INVENTORY_WIDTH = 420;
    private static final int INVENTORY_HEIGHT = 420;

    //    private final JList<String> countryList;

    public InventoryFrame() {
        this.setSize(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        this.setResizable(false);
        this.setTitle("Inventory");
        this.setLocationRelativeTo(null);
        this.addFocusListener(this.closeOnOutsideClick());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private FocusListener closeOnOutsideClick() {
        Frame frame = this;

        return new FocusListener() {
            private boolean gained = false;
            @Override
            public void focusGained( FocusEvent e ) {
                gained = true;
            }

            @Override
            public void focusLost( FocusEvent e ) {
                if ( gained ){
                    frame.dispose();
                }
            }
        };
    }

}