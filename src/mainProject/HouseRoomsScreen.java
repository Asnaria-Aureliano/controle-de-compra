package mainProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HouseRoomsScreen extends JFrame {
    private List<String> rooms;
    private JComboBox<String> roomsComboBox;

    public HouseRoomsScreen() {
        rooms = new ArrayList<>();
        rooms.add("Sala");
        rooms.add("Cozinha");
        rooms.add("Quarto");
        rooms.add("Banheiro");

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        JLabel roomLabel = new JLabel("Selecione um cômodo:");
        roomsComboBox = new JComboBox<>(rooms.toArray(new String[0]));

        JButton addButton = new JButton("Adicionar Item");

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(roomLabel);
        panel.add(roomsComboBox);
        panel.add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedRoom = (String) roomsComboBox.getSelectedItem();
                ListScreen listScreen = new ListScreen(selectedRoom);
                listScreen.setVisible(true);
            }
        });

        add(panel);
    }

    private void setupLayout() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cômodos da Casa");
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HouseRoomsScreen houseRoomsScreen = new HouseRoomsScreen();
            }
        });
    }
}

class ListScreen extends JFrame {
    private String room;
    private DefaultListModel<String> shoppingListModel;
    private JList<String> list;
    private JTextField itemTextField;
    private JTextField quantityTextField;
    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton clearButton;

    public ListScreen(String room) {
        this.room = room;
        this.shoppingListModel = new DefaultListModel<>();

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        JLabel itemLabel = new JLabel("Item:");
        JLabel quantityLabel = new JLabel("Quantidade:");
        itemTextField = new JTextField(20);
        quantityTextField = new JTextField(5);

        addButton = new JButton("Adicionar");
        deleteButton = new JButton("Apagar");
        updateButton = new JButton("Alterar");
        clearButton = new JButton("Limpar");

        list = new JList<>(shoppingListModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScrollPane = new JScrollPane(list);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(itemLabel);
        inputPanel.add(itemTextField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(listScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String item = itemTextField.getText();
                String quantityText = quantityTextField.getText();

                if (item.isEmpty()) {
                    JOptionPane.showMessageDialog(ListScreen.this, "Por favor, adicione um item.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (quantityText.isEmpty()) {
                    JOptionPane.showMessageDialog(ListScreen.this, "Por favor, adicione uma quantidade.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        int quantity = Integer.parseInt(quantityText);
                        String listItem = item + " - " + quantity;
                        shoppingListModel.addElement(listItem);
                        clearInputFields();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(ListScreen.this, "Por favor, adicione uma quantidade valida.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();

                if (selectedIndex != -1) {
                    shoppingListModel.remove(selectedIndex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = list.getSelectedIndex();

                if (selectedIndex != -1) {
                    String item = itemTextField.getText();
                    String quantityText = quantityTextField.getText();

                    if (item.isEmpty()) {
                        JOptionPane.showMessageDialog(ListScreen.this, "Por favor, insira um item.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (quantityText.isEmpty()) {
                        JOptionPane.showMessageDialog(ListScreen.this, "Por favor, insira uma quantidade.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        try {
                            int quantity = Integer.parseInt(quantityText);
                            String listItem = item + " - " + quantity;
                            shoppingListModel.setElementAt(listItem, selectedIndex);
                            clearInputFields();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(ListScreen.this, "Por favor, adicione uma quantidade valida.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shoppingListModel.clear();
                clearInputFields();
            }
        });

        list.addListSelectionListener(e -> {
            int selectedIndex = list.getSelectedIndex();

            if (selectedIndex != -1) {
                String selectedItem = shoppingListModel.getElementAt(selectedIndex);
                String[] parts = selectedItem.split(" - ");
                if (parts.length == 2) {
                    itemTextField.setText(parts[0]);
                    quantityTextField.setText(parts[1]);
                }
            }
        });
    }

    private void setupLayout() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Lista de compras - " + room);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void clearInputFields() {
        itemTextField.setText("");
        quantityTextField.setText("");
        itemTextField.requestFocus();
    }
}
