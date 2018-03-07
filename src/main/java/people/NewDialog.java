package people;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class NewDialog extends JDialog {

    private JTextField name;
    private JTextField surname;
    private JSpinner age;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JButton okButton;
    private JButton cancelButton;
    private JCheckBox cb1, cb2, cb3, cb4;
    private NewDialogListener listener;

    public NewDialog(NewDialogListener listener) {

        setModal(true);

        this.listener = listener;

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.add(new JLabel("Imię: "));
        panel.add(name = new JTextField(10));
        panel.add(new JLabel("Nazwisko: "));
        panel.add(surname = new JTextField(10));
        panel.add(new JLabel("Wiek: "));
        SpinnerModel value = new SpinnerNumberModel(1, 1, 100, 1);
        panel.add(age = new JSpinner(value));

        rb1 = new JRadioButton("Mężczyzna");
        rb2 = new JRadioButton("Kobieta");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);

        panel.add(rb1);
        panel.add(rb2);

        add(panel, BorderLayout.WEST);

        JPanel panel2 = new JPanel();
        JLabel lab;
        panel2.add(lab = new JLabel("Zainteresowania: "));
        panel2.add(cb1 = new JCheckBox("Sport"));
        panel2.add(cb2 = new JCheckBox("Matematyka"));
        panel2.add(cb3 = new JCheckBox("Muzyka"));
        panel2.add(cb4 = new JCheckBox("Historia"));

        add(panel2, BorderLayout.EAST);

        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                String n = name.getText();
                String s = surname.getText();
                int a = (Integer) age.getValue();

                String p = null;
                if (rb1.isSelected()) {
                    p = "Mężczyzna";
                }
                if (rb2.isSelected()) {
                    p = "Kobieta";
                }

                ArrayList<String> listCheckBox = new ArrayList<>();
                if (cb1.isSelected()) {
                    listCheckBox.add(cb1.getText());
                }
                if (cb2.isSelected()) {
                    listCheckBox.add(cb2.getText());
                }
                if (cb3.isSelected()) {
                    listCheckBox.add(cb3.getText());
                }
                if (cb4.isSelected()) {
                    listCheckBox.add(cb4.getText());
                }

                String str = String.join(",", listCheckBox);

                ArrayList<String> errors = new ArrayList<>();

                if (name.getText().isEmpty()) {
                    errors.add("Imię musi zostać podane");
                }
                if (surname.getText().isEmpty()) {
                    errors.add("Nazwisko musi zostać podane");
                }
                if (!rb1.isSelected() && !rb2.isSelected()) {
                    errors.add("Płeć musi zostać wybrana");
                }

                if (!errors.isEmpty()) {
                    String st = String.join("\n", errors);
                    JOptionPane.showMessageDialog(null, st, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    listener.okClicked(n, s, a, p, str);
                    setVisible(false);
                }
            }
        });

        cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();

    }
}
