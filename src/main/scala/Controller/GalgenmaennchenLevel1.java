package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import View.GUILevel1;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class GalgenmaennchenLevel1 {
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    /**
     * Konstruktor der Klasse GalgenmaennchenLevel1.
     * Initialisiert das `mainPanel` mit einem `CardLayout` zur Verwaltung verschiedener Level.
     * Lädt die Konfiguration aus der angegebenen JSON-Datei.
     */
    public GalgenmaennchenLevel1() {
        // Setzt das CardLayout für den Wechsel zwischen verschiedenen Panels auf.
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Konfiguration aus der JSON-Datei laden.
        loadConfiguration("Config.JSON");
    }

    public static void showLevel1() {
    }

    /**
     * Gibt das Hauptpanel (`mainPanel`) zurück.
     * Das `mainPanel` enthält alle Panels für die verschiedenen Level des Spiels.
     *
     * @return Das Hauptpanel mit den Level-Panels.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Lädt die Konfigurationsdaten für das Spiel aus einer JSON-Datei.
     *
     * Die Methode überprüft, ob die JSON-Datei existiert, und liest die Konfiguration für das
     * aktuelle Level aus. Abhängig von der Konfiguration wird ein neues Panel für das angegebene Level erstellt
     * und dem `mainPanel` hinzugefügt.
     *
     * @param configFilePath Der Pfad zur Konfigurationsdatei im JSON-Format.
     */
    private void loadConfiguration(String configFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Überprüfen, ob die Konfigurationsdatei existiert.
            File configFile = new File(configFilePath);
            if (!configFile.exists()) {
                System.out.println("Config-Datei nicht gefunden: " + configFile.getAbsolutePath());
                return;
            }

            // JSON-Datei parsen.
            JsonNode config = mapper.readTree(configFile);
            String currentLevel = config.get("currentLevel").asText();

            // Falls das aktuelle Level "Level1" ist, wird dieses geladen.
            if ("Level1".equals(currentLevel)) {
                JsonNode level1Config = config.get("levels").get("Level1");
                String secretWord = level1Config.get("secretWord").asText();
                int maxGuesses = level1Config.get("maxGuesses").asInt();

                // Das Panel für Level 1 wird erstellt und dem `mainPanel` hinzugefügt.
                GUILevel1 level1Panel = new GUILevel1(secretWord, maxGuesses);
                mainPanel.add(level1Panel.getPanel(), "Level1");
                System.out.println("Level1-Panel hinzugefügt");
            }

            // Weitere Level können hier hinzugefügt werden, falls nötig.

        } catch (IOException e) {
            // Fehler beim Lesen der Konfigurationsdatei.
            e.printStackTrace();
        }
    }

    /**
     * Wechselt das aktuelle Panel im `CardLayout` zu Level 1.
     *
     * Diese Methode zeigt das Panel für Level 1 an, indem sie das `CardLayout` auf das entsprechende
     * Panel umschaltet. Danach wird das JFrame aktualisiert, um die Änderungen anzuzeigen.
     *
     * @param frame Das Hauptfenster der Anwendung, das aktualisiert werden muss.
     */
    public static void startLevel1(JFrame frame) {
        System.out.println("Wechsel zu Level 1");
        // Schaltet das CardLayout auf das Panel für Level 1.
        cardLayout.show(mainPanel, "Level1");
        // Aktualisiert das Fenster.
        frame.revalidate();
        frame.repaint();
    }
}
