package people;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame implements NewDialogListener, EditDialogListener {

    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton openButton;
    private JButton saveButton;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private int rowIndex = -1; 
    private NewDialog dialog = null;
    private EditDialog dial = null;

    public MainFrame() {
        super("Tabela danych");

        model = new DefaultTableModel();
        model.addColumn("Imię");
        model.addColumn("Nazwisko");
        model.addColumn("Wiek");
        model.addColumn("Płeć");
        model.addColumn("Zainteresowania");

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Dodaj");
        editButton = new JButton("Edytuj");
        deleteButton = new JButton("Usuń");
        openButton = new JButton("Otwórz");
        saveButton = new JButton("Zapisz");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        editButton.setEnabled(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        ListSelectionModel listSelectionModel = table.getSelectionModel();

        listSelectionModel.addListSelectionListener((ListSelectionEvent e) -> {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            editButton.setEnabled(!lsm.isSelectionEmpty());
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            int[] rows = table.getSelectedRows();
            
            for (int i = rows.length - 1; i >= 0; i--) {
                int j = rows[i];
                model.removeRow(j);
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            dialog = new NewDialog(MainFrame.this);
            dialog.setVisible(true);
        });

        editButton.addActionListener((ActionEvent e) -> {
            if (table.getSelectedRow() != -1) {
                
                rowIndex = table.getSelectedRow();
                
                dial = new EditDialog(MainFrame.this);
                dial.setVisible(true);
            }
        });

        openButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == openButton) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(table);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    String fileName = f.getPath();
                    
                    try {
                        FileReader fr = new FileReader(fileName);
                        Scanner sc = new Scanner(fr);
                        
                        String text1 = "";
                        while (sc.hasNextLine()) {
                            text1 = sc.nextLine();
                            String str[] = text1.split(";");
                            Object[] row = new Object[5];
                            row[0] = str[0];
                            row[1] = str[1];
                            row[2] = Integer.parseInt(str[2]);
                            row[3] = str[3];
                            row[4] = str[4];
                            model.addRow(row);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Błąd przy otwarciu pliku", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        saveButton.addActionListener((ActionEvent e) -> {
            if (e.getSource() == saveButton) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showSaveDialog(table);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    String fileName = f.getPath();
                    
                    try {
                        FileWriter fw = new FileWriter(fileName);
                        BufferedWriter bw = new BufferedWriter(fw);
                        
                        ArrayList<String> numdata = new ArrayList<>();
                        
                        for (int j = 0; j < model.getRowCount(); j++) {
                            for (int k = 0; k < model.getColumnCount(); k++) {
                                String value = model.getValueAt(j, k).toString();
                                numdata.add(value);
                            }
                        }
                        int counter = 1;
                        for (int l = 0; l < numdata.size(); l++) {
                            String data = numdata.get(l);
                            
                            if (counter % 5 == 0) {
                                bw.write(data + "\n");
                            } else
                                bw.write(data + ";");
                            
                            bw.flush();
                            counter++;
                        }
                        bw.close();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Nie zapisano danych z tabeli do pliku", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    @Override
    public void okClicked(String name, String surname, int age, String sex, String hobbies) {
        Object[] wpisane = {name, surname, age, sex, hobbies};
        model.addRow(wpisane);
    }

    @Override
    public void editClicked(String name, String surname, int age, String sex, String hobbies) {
        model.setValueAt(name, rowIndex, 0);
        model.setValueAt(surname, rowIndex, 1);
        model.setValueAt(age, rowIndex, 2);
        model.setValueAt(sex, rowIndex, 3);
        model.setValueAt(hobbies, rowIndex, 4);
    }

    public void passData(JTextField nameField, JTextField surnameField, JSpinner ageSpinner, 
            JRadioButton maleRadio, JRadioButton femaleRadio, JCheckBox sportCheckBox, 
            JCheckBox mathCheckBox, JCheckBox musicCheckBox, JCheckBox historyCheckBox) {

        nameField.setText((String) model.getValueAt(rowIndex, 0));
        surnameField.setText((String) model.getValueAt(rowIndex, 1));
        ageSpinner.setValue((Integer) model.getValueAt(rowIndex, 2));

        String p = (String) model.getValueAt(rowIndex, 3);

        if (p.equals("Kobieta")) {
            femaleRadio.setSelected(true);
        }
        if (p.equals("Mężczyzna")) {
            maleRadio.setSelected(true);
        }

        String lista = (String) model.getValueAt(rowIndex, 4);

        if (lista.contains("Sport")) {
            sportCheckBox.setSelected(true);
        }
        if (lista.contains("Matematyka")) {
            mathCheckBox.setSelected(true);
        }
        if (lista.contains("Muzyka")) {
            musicCheckBox.setSelected(true);
        }
        if (lista.contains("Historia")) {
            historyCheckBox.setSelected(true);
        }
    }
}
