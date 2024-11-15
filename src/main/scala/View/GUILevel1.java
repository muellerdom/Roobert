package View;

import GalgenmaennchenLevel1.Galgenmaennchen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUILevel1 {

    private JPanel panel;
    private JTextField inputField;
    private JButton checkButton;
    private JLabel resultLabel;
    private Galgenmaennchen galgenmaennchen;

    public GUILevel1(String secretWord, int maxGuesses) {
        // Instantiate Galgenmaennchen with the provided configuration
        galgenmaennchen = new Galgenmaennchen(secretWord, maxGuesses);

        // Initialize main panel
        panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 300));

        // Input field and button for letter input
        JPanel inputPanel = new JPanel();
        inputField = new JTextField(5);
        checkButton = new JButton("Raten");

        inputPanel.add(new JLabel("Geben Sie einen Buchstaben ein:"));
        inputPanel.add(inputField);
        inputPanel.add(checkButton);

        // Label for showing result to the user
        resultLabel = new JLabel("Bitte raten Sie den Buchstaben.");
        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);

        // ActionListener for the button
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputBuchstabe = inputField.getText();

                // Ensure user entered exactly one letter
                if (inputBuchstabe.length() == 1) {
                    char buchstabe = inputBuchstabe.charAt(0);

                    // Use galgenmaennchen instance to check if the letter is correct
                    boolean ergebnis = galgenmaennchen.pruefeBuchstabeOeffentlich(buchstabe);

                    // Show result
                    if (ergebnis) {
                        resultLabel.setText("Der Buchstabe '" + buchstabe + "' ist korrekt!");
                    } else {
                        resultLabel.setText("Der Buchstabe '" + buchstabe + "' ist falsch. Versuchen Sie es erneut!");
                    }
                } else {
                    resultLabel.setText("Bitte geben Sie genau einen Buchstaben ein!");
                }

                // Clear the input field after each guess
                inputField.setText("");
            }
        });

        // Add panels to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
    }

    // Return the main panel for use in other containers
    public JPanel getPanel() {
        return panel;
    }
}
