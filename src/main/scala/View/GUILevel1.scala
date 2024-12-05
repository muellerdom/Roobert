package View

import javax.swing._
import java.awt._
import Util.{Observer, Observable}

// Abstrakte GUI-Komponente, die durch das Observer-Pattern benachrichtigt wird
abstract class GameGUI extends Observer {

  private val panel = new JPanel(new BorderLayout())

  // Template-Methode: Definiert den Ablauf der Initialisierung und des Updates
  final def initializeGUI(): Unit = {
    setupPanel(panel)         // Panel mit spezifischen Komponenten konfigurieren
    setupListeners()          // Listener hinzufügen
    setupInitialState()       // Anfangszustand der GUI setzen
  }

  // Abstrakte Methoden für die spezifische Konfiguration
  protected def setupPanel(panel: JPanel): Unit
  protected def setupListeners(): Unit
  protected def setupInitialState(): Unit

  // Observer-Methode zur Aktualisierung der GUI
  override def update(): Unit = {
    val newState = GameModel.getState
    updateView(newState) // Aktualisierung der GUI mit dem neuen Zustand
  }

  // Abstrakte Methode für die spezifische Aktualisierung der View
  protected def updateView(state: String): Unit

  // Getter für das Panel
  def getPanel: JPanel = panel
}

// Subjekt (Observable), das Änderungen an alle Observer (GUIs) weitergibt
object GameModel extends Observable {
  private var state: String = ""

  def updateState(newState: String): Unit = {
    state = newState
    notifyObservers() // Alle Observer benachrichtigen
  }

  def getState: String = state
}

// Konkrete Implementation der GUI für Level 1
class GUILevel1(secretWord: String, maxGuesses: Int) extends GameGUI {

  private var galgenmaennchen: GalgenmaennchenLevel1 = _
  private val inputField = new JTextField(5)
  private val checkButton = new JButton("Raten")
  private val resultLabel = new JLabel("Bitte raten Sie den Buchstaben.")

  // Panel-Setup für Level 1
  override protected def setupPanel(panel: JPanel): Unit = {
    galgenmaennchen = new GalgenmaennchenLevel1(secretWord, maxGuesses)
    panel.setPreferredSize(new Dimension(400, 300))

    val inputPanel = new JPanel()
    inputPanel.add(new JLabel("Geben Sie einen Buchstaben ein:"))
    inputPanel.add(inputField)
    inputPanel.add(checkButton)

    val resultPanel = new JPanel()
    resultPanel.add(resultLabel)

    panel.add(inputPanel, BorderLayout.NORTH)
    panel.add(resultPanel, BorderLayout.CENTER)
  }

  // Listener-Setup für Level 1
  override protected def setupListeners(): Unit = {
    checkButton.addActionListener(_ => {
      val inputBuchstabe = inputField.getText

      if (inputBuchstabe.length == 1) {
        val buchstabe = inputBuchstabe.charAt(0)
        val ergebnis = galgenmaennchen.pruefeBuchstabe(buchstabe)

        if (ergebnis) {
          GameModel.updateState(s"Der Buchstabe '$buchstabe' ist korrekt!")
        } else {
          GameModel.updateState(s"Der Buchstabe '$buchstabe' ist falsch. Versuchen Sie es erneut!")
        }
      } else {
        GameModel.updateState("Bitte geben Sie genau einen Buchstaben ein!")
      }

      inputField.setText("")
    })
  }

  // Initialer Zustand für Level 1
  override protected def setupInitialState(): Unit = {
    resultLabel.setText("Bitte raten Sie den Buchstaben.")
  }

  // Aktualisierung der View mit neuem Zustand
  override protected def updateView(state: String): Unit = {
    resultLabel.setText(state)
  }
}

// Singleton-Objekt für die GUI-Instanz
object GameGUISingleton {

  private var currentGUI: Option[GameGUI] = None

  // Thread-sichere Methode, um die passende GUI zu setzen
  def setGUI(levelType: String, secretWord: String, maxGuesses: Int): Unit = synchronized {
    levelType match {
      case "level1" =>
        val newGUI = new GUILevel1(secretWord, maxGuesses)
        newGUI.initializeGUI() // Template-Methode wird aufgerufen
        GameModel.addObserver(newGUI) // GUI als Observer registrieren
        currentGUI = Some(newGUI)
      // Weitere Level-GUIs können hier hinzugefügt werden
      case _ => throw new IllegalArgumentException(s"Unbekannter Level-Typ: $levelType")
    }
  }

  // Methode, um die aktuelle GUI zu erhalten
  def getCurrentGUI: Option[GameGUI] = currentGUI

  // Methode zum Zurücksetzen der GUI (optional, z. B. für Tests)
  def resetGUI(): Unit = synchronized {
    currentGUI.foreach(gui => GameModel.removeObserver(gui))
    currentGUI = None
  }
}
