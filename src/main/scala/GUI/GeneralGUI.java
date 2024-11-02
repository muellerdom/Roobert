package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GeneralGUI {

    public static void main(String args[]) { // Die Hauptmethode, der Einstiegspunkt des Programms.

        // Erstellen des Hauptfensters (JFrame)
        JFrame frame = new JFrame("Chat Frame"); // Erstellt ein neues Fenster (Frame) mit dem Titel "Chat Frame".
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Legt fest, dass das Programm beendet wird, wenn das Fenster geschlossen wird.
        frame.setSize(400, 400); // Setzt die Größe des Fensters auf 400x400 Pixel.
        frame.setBackground(new Color(0x6CB2F3));

        // Erstelle das Panel für das Sidemenü
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS)); // Vertikale Anordnung für das Sidemenü
        sideMenu.setBackground(new Color(108, 178, 243)); // Sanftes Blau
        sideMenu.setPreferredSize(new Dimension((int) (frame.getWidth() * 0.2), frame.getHeight())); // Breite des Sidemenüs

        // Füge einige Menüeinträge hinzu
        sideMenu.add(new JButton("Level 1"));
        sideMenu.add(new JButton("Level 2"));
        sideMenu.add(new JButton("Level 3"));
        sideMenu.add(new JButton("Level 4"));
        sideMenu.add(new JButton("Level 5"));
        sideMenu.add(new JButton("Level 6"));
        sideMenu.add(new JButton("Level 7"));
        sideMenu.add(new JButton("Level 8"));
        sideMenu.add(new JButton("Level 9"));

        sideMenu.setLayout(new GridLayout(0, 1));
        // Das Sidemenü ist anfangs nicht sichtbar
        sideMenu.setVisible(false);



        // Erstelle das Hauptpanel und die Komponenten
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF));

        // Füge einen Textbereich hinzu
        JTextArea ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        mainPanel.add(ta, BorderLayout.CENTER);

        // Füge einen Button hinzu, um das Sidemenü ein- und auszublenden
        JButton toggleButton = new JButton("Auf zum nächsten Level !");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sideMenu.setVisible(!sideMenu.isVisible()); // Sichtbarkeit umkehren
                frame.revalidate(); // Layout neu validieren
                frame.repaint(); // Fenster neu zeichnen
            }
        });
        mainPanel.add(toggleButton, BorderLayout.SOUTH);

        //Button einen Button für Code ausführen Button
        //Button für einen Los gehts-Button
        

        // Füge die Panels zum Hauptfenster hinzu
        frame.getContentPane().add(sideMenu, BorderLayout.WEST); // Sidemenü an die linke Seite
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER); // Hauptpanel in der Mitte

        frame.setVisible(true); // Macht das Fenster sichtbar.
    }
}