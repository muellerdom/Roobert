package View

import GalgenmaennchenLevel1.Galgenmaennchen
import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener

class GUILevel1(secretWord: String, maxGuesses: Int) {

  // Attribute
  private val galgenmaennchen = new Galgenmaennchen(secretWord, maxGuesses) // Instanziiere Galgenmaennchen
  private val panel = new JPanel(new BorderLayout()) // Hauptpanel
  private val inputField = new JTextField(5) // Eingabefeld für Buchstaben
  private val checkButton = new JButton("Raten") // Button zum Überprüfen
  private val resultLabel = new JLabel("Bitte raten Sie den Buchstaben.") // Label für Ergebnisse

  // Konstruktorlogik
  init()

  private def init(): Unit = {
    // Hauptpanel konfigurieren
    panel.setPreferredSize(new Dimension(400, 300))

    // Panel für die Eingabe
    val inputPanel = new JPanel()
    inputPanel.add(new JLabel("Geben Sie einen Buchstaben ein:"))
    inputPanel.add(inputField)
    inputPanel.add(checkButton)

    // Panel für das Ergebnis
    val resultPanel = new JPanel()
    resultPanel.add(resultLabel)

    // ActionListener für den Button
    checkButton.addActionListener(_ => {
      val inputBuchstabe = inputField.getText

      if (inputBuchstabe.length == 1) {
        val buchstabe = inputBuchstabe.charAt(0)

        // Buchstaben prüfen
        val ergebnis = galgenmaennchen.pruefeBuchstabeOeffentlich(buchstabe)

        // Ergebnis anzeigen
        if (ergebnis) {
          resultLabel.setText(s"Der Buchstabe '$buchstabe' ist korrekt!")
        } else {
          resultLabel.setText(s"Der Buchstabe '$buchstabe' ist falsch. Versuchen Sie es erneut!")
        }
      } else {
        resultLabel.setText("Bitte geben Sie genau einen Buchstaben ein!")
      }

      // Eingabefeld leeren
      inputField.setText("")
    })

    // Panels zum Hauptpanel hinzufügen
    panel.add(inputPanel, BorderLayout.NORTH)
    panel.add(resultPanel, BorderLayout.CENTER)
  }

  /**
   * Gibt das Hauptpanel zurück.
   *
   * @return JPanel zur Einbindung in andere GUI-Komponenten.
   */
  def getPanel: JPanel = panel
}
