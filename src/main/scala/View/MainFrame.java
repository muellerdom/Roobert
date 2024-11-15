package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Controller.GalgenmaennchenLevel1;  // Controller importieren

public class MainFrame {
    private JFrame frame;
    private JPanel sideMenu;
    private JPanel mainPanel;
    private JButton level1Button;
    private GalgenmaennchenLevel1 gameController;  // Referenz zum Controller

    /**
     * Konstruktor für das Hauptfenster (MainFrame).
     * Initialisiert den GameController, das Hauptfenster und alle GUI-Komponenten.
     */
    public MainFrame() {
        // Erstelle den GameController
        gameController = new GalgenmaennchenLevel1();

        // Erstelle das Hauptfenster (JFrame)
        frame = new JFrame("Galgenmännchen - Hauptmenü");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setBackground(new Color(0x6CB2F3));

        // Erstelle das Sidemenü
        sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(108, 178, 243));
        sideMenu.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.2), frame.getHeight()));

        // Füge einige Menüeinträge hinzu
        level1Button = new JButton("Level 1");
        JButton level2Button = new JButton("Level 2");  // Noch kein Funktionalität

        sideMenu.add(level1Button);
        sideMenu.add(level2Button);

        sideMenu.setLayout(new GridLayout(0, 1));
        sideMenu.setVisible(false);

        // Erstelle das Hauptpanel und füge den Controller-Panel hinzu
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF));

        // Hole das Hauptpanel vom Controller und füge es zu mainPanel hinzu
        JPanel gamePanel = gameController.getMainPanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);

        // Button zum Ein-/Ausblenden des Sidemenüs
        JButton toggleButton = new JButton("Auf zum nächsten Level!");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Sichtbarkeit des Sidemenüs umschalten
                sideMenu.setVisible(!sideMenu.isVisible());
                frame.revalidate();
                frame.repaint();
            }
        });
        mainPanel.add(toggleButton, BorderLayout.SOUTH);

        // ActionListener für den Level 1 Button
        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zeigt das Panel für Level 1 im Controller an
                GalgenmaennchenLevel1.showLevel1();
                frame.revalidate();
                frame.repaint();
            }
        });

        // Füge die Panels zum Hauptfenster hinzu
        frame.getContentPane().add(sideMenu, BorderLayout.WEST); // Sidemenü links
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER); // Hauptpanel in der Mitte

        // Fenster sichtbar machen
        frame.setVisible(true);
    }

    /**
     * Main-Methode zum Starten der Anwendung.
     * Erstellt eine Instanz des Hauptfensters.
     */
    public static void main(String[] args) {
        new MainFrame();  // Erstelle eine Instanz des Hauptfensters
    }
}
