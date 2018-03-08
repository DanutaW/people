package people;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class NewDialog extends JDialog {

    private JTextField nameField;
    private JTextField surnameField;
    private JSpinner ageSpinner;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JCheckBox sportCheckBox, mathCheckBox, musicCheckBox, historyCheckBox;
    private JButton okButton;
    private JButton cancelButton;
    private NewDialogListener listener;

    public NewDialog(NewDialogListener listener) {
        setModal(true);

        this.listener = listener;

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(new JLabel("Imię: "));
        panel.add(nameField = new JTextField(10));
        panel.add(new JLabel("Nazwisko: "));
        panel.add(surnameField = new JTextField(10));
        panel.add(new JLabel("Wiek: "));
        SpinnerModel value = new SpinnerNumberModel(1, 1, 100, 1);
        panel.add(ageSpinner = new JSpinner(value));

        maleRadio = new JRadioButton("Mężczyzna");
        femaleRadio = new JRadioButton("Kobieta");
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleRadio);
        bg.add(femaleRadio);

        panel.add(maleRadio);
        panel.add(femaleRadio);

        panel.add( new JLabel("Zainteresowania: "));
        panel.add(sportCheckBox = new JCheckBox("Sport"));
        panel.add(mathCheckBox = new JCheckBox("Matematyka"));
        panel.add(musicCheckBox = new JCheckBox("Muzyka"));
        panel.add(historyCheckBox = new JCheckBox("Historia"));

        add(panel, BorderLayout.CENTER);

        okButton = new JButton("Ok");
        okButton.addActionListener((ActionEvent event) -> {
            String name1 = nameField.getText();
            String surname = surnameField.getText();
            int age = (Integer) ageSpinner.getValue();
            String sex = null;
            if (maleRadio.isSelected()) {
                sex = "Mężczyzna";
            }
            if (femaleRadio.isSelected()) {
                sex = "Kobieta";
            }
            ArrayList<String> listCheckBox = new ArrayList<>();
            if (sportCheckBox.isSelected()) {
                listCheckBox.add(sportCheckBox.getText());
            }
            if (mathCheckBox.isSelected()) {
                listCheckBox.add(mathCheckBox.getText());
            }
            if (musicCheckBox.isSelected()) {
                listCheckBox.add(musicCheckBox.getText());
            }
            if (historyCheckBox.isSelected()) {
                listCheckBox.add(historyCheckBox.getText());
            }
            String hobbies = String.join(",", listCheckBox);
            ArrayList<String> errors = new ArrayList<>();
            if (nameField.getText().isEmpty()) {
                errors.add("Imię musi zostać podane");
            }
            if (surnameField.getText().isEmpty()) {
                errors.add("Nazwisko musi zostać podane");
            }
            if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
                errors.add("Płeć musi zostać wybrana");
            }
            if (!errors.isEmpty()) {
                String str = String.join("\n", errors);
                JOptionPane.showMessageDialog(null, str, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                listener.okClicked(name1, surname, age, sex, hobbies);
                setVisible(false);
            }
        });

        cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener((ActionEvent event) -> {
            setVisible(false);
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }
}
