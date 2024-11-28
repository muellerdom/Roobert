package View

import GalgenmaennchenLevel1.Galgenmaennchen
import javax.swing._
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import Util.Observer

object GUILevel1 extends Observer {

  private var galgenmaennchen: Galgenmaennchen = _
  private val panel = new JPanel(new BorderLayout())
  private val inputField = new JTextField(5)
  private val checkButton = new JButton("Raten")
  private val resultLabel = new JLabel("Bitte raten Sie den Buchstaben.")

  def initialize(secretWord: String, maxGuesses: Int): Unit = {
    galgenmaennchen = new Galgenmaennchen(secretWord, maxGuesses)
    init()
  }

  // Diese Methode wird aufgerufen, wenn das Modell oder der Controller aktualisiert wird
  override def update(): Unit = {
    // Beispielweise den Ergebnis-Label aktualisieren
    resultLabel.setText("Spielstatus wurde aktualisiert.")
  }

  private def init(): Unit = {
    panel.setPreferredSize(new Dimension(400, 300))

    val inputPanel = new JPanel()
    inputPanel.add(new JLabel("Geben Sie einen Buchstaben ein:"))
    inputPanel.add(inputField)
    inputPanel.add(checkButton)

    val resultPanel = new JPanel()
    resultPanel.add(resultLabel)

    checkButton.addActionListener(_ => {
      val inputBuchstabe = inputField.getText

      if (inputBuchstabe.length == 1) {
        val buchstabe = inputBuchstabe.charAt(0)
        val ergebnis = galgenmaennchen.pruefeBuchstabeOeffentlich(buchstabe)

        if (ergebnis) {
          resultLabel.setText(s"Der Buchstabe '$buchstabe' ist korrekt!")
        } else {
          resultLabel.setText(s"Der Buchstabe '$buchstabe' ist falsch. Versuchen Sie es erneut!")
        }
      } else {
        resultLabel.setText("Bitte geben Sie genau einen Buchstaben ein!")
      }

      inputField.setText("")
    })

    panel.add(inputPanel, BorderLayout.NORTH)
    panel.add(resultPanel, BorderLayout.CENTER)
  }

  def getPanel: JPanel = panel
}
