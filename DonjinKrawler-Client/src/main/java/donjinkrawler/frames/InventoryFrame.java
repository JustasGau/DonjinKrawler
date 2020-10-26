package donjinkrawler.frames;

import donjinkrawler.items.Armor;
import donjinkrawler.items.Weapon;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class InventoryFrame extends JFrame {

    private static final int INVENTORY_WIDTH = 420;
    private static final int INVENTORY_HEIGHT = 420;

    private final JList<String> inventoryList;
    private Weapon weapon;
    private Armor armor;
    JTextArea infoField;

    public InventoryFrame() {
        this.setup();

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Armor");
        listModel.addElement("Weapon");

        inventoryList = new JList<>(listModel);
        inventoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.infoField = new JTextArea();

        infoField.setPreferredSize(new Dimension(200, 200));

        JPanel infoFieldPanel = new JPanel();
        infoFieldPanel.add(infoField);

        JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, new JScrollPane(inventoryList), infoFieldPanel);
        splitPane.setEnabled( false );
        splitPane.setResizeWeight(.5d);

        inventoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    final String selectedValue = inventoryList.getSelectedValue();
                    infoField.setText(getItemStats(selectedValue));
                }
            }
        });

        add(splitPane);
    }

    private String getItemStats(String selectedValue) {
        if(selectedValue.equals("Armor")) {
            return this.getArmorStats();
        } else if(selectedValue.equals("Weapon")) {
            return this.getWeaponStats();
        }

        return null;
    }

    private String getWeaponStats() {
        if(this.weapon == null) {
            return "No weapon acquired.";
        }
        return this.weapon.getDescription();
    }

    private String getArmorStats() {
        if(this.armor == null) {
            return "No armor acquired.";
        }
        return this.armor.getDescription();
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
        this.infoField.setText(getItemStats("Weapon"));
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
        this.infoField.setText(getItemStats("Armor"));
    }

    private void setup() {
        this.setTitle("Inventory");
        this.setResizable(false);
        this.setSize(INVENTORY_WIDTH, INVENTORY_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}