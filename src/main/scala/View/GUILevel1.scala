import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{BorderPane, VBox}
import scalafx.Includes._

import Util.{Observer, Observable}

// Abstrakte GUI-Komponente, die durch das Observer-Pattern benachrichtigt wird
abstract class GameGUI extends Observer {

  private var stageManager: Option[ViewStageManager] = None

  // Template-Methode: Definiert den Ablauf der Initialisierung und des Updates
  final def initializeGUI(): Unit = {
    val scene = createScene()  // Erstelle die Scene mit den spezifischen Komponenten
    setupListeners()           // Listener hinzufügen
    setupInitialState()        // Anfangszustand der GUI setzen
    stageManager = Some(createStageManager()) // StageManager erstellen
  }

  // Abstrakte Methoden für die spezifische Konfiguration
  protected def createScene(): Scene
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
  def getPanel: Scene
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

// ViewStage als Basis für die verschiedenen Phasen der Anzeige
abstract class ViewStage {
  def execute(): Unit
}

class DisplayInputStage(inputField: TextField, checkButton: Button) extends ViewStage {
  override def execute(): Unit = {
    // Setzt das Eingabefeld zurück und aktiviert den Button
    inputField.clear()
    checkButton.setDisable(false)
  }
}

class CheckGuessStage(inputBuchstabe: String, galgenmaennchen: GalgenmaennchenLevel1) extends ViewStage {
  override def execute(): Unit = {
    if (inputBuchstabe.length == 1) {
      val buchstabe = inputBuchstabe.charAt(0)
      galgenmaennchen.pruefeBuchstabe(buchstabe)
    }
  }
}

class DisplayResultStage(resultLabel: Label, state: String) extends ViewStage {
  override def execute(): Unit = {
    resultLabel.text = state
  }
}

class ViewStageManager(stages: List[ViewStage]) {
  private var currentStages = stages

  def executeAll(): Unit = {
    currentStages.foreach(_.execute()) // Führt alle Stages der Reihe nach aus
  }
}

// GUI für Level 1 mit ScalaFX
class GUILevel1(secretWord: String, maxGuesses: Int) extends GameGUI {

  private var galgenmaennchen: GalgenmaennchenLevel1 = _
  private val inputField = new TextField()
  private val checkButton = new Button("Raten")
  private val resultLabel = new Label("Bitte raten Sie den Buchstaben.")

  // Scene für Level 1 erstellen
  override protected def createScene(): Scene = {
    galgenmaennchen = new GalgenmaennchenLevel1(secretWord, maxGuesses)

    val inputPanel = new VBox {
      children = Seq(
        new Label("Geben Sie einen Buchstaben ein:"),
        inputField,
        checkButton
      )
    }

    val resultPanel = new VBox {
      children = Seq(resultLabel)
    }

    val root = new BorderPane {
      top = inputPanel
      center = resultPanel
    }

    new Scene {
      root = root
    }
  }

  // Listener für die Schaltfläche
  override protected def setupListeners(): Unit = {
    checkButton.onAction = _ => {
      val inputBuchstabe = inputField.text()

      // Stages für die aktuellen Aktionen definieren
      val displayInputStage = new DisplayInputStage(inputField, checkButton)
      val checkGuessStage = new CheckGuessStage(inputBuchstabe, galgenmaennchen)
      val displayResultStage = new DisplayResultStage(resultLabel, s"Versuch: $inputBuchstabe")

      // StageManager erstellen und alle Stages ausführen
      val stageManager = new ViewStageManager(List(displayInputStage, checkGuessStage, displayResultStage))
      stageManager.executeAll() // Alle Stages ausführen
    }
  }

  // Initialer Zustand für Level 1
  override protected def setupInitialState(): Unit = {
    resultLabel.text = "Bitte raten Sie den Buchstaben."
  }

  // Aktualisierung der View mit neuem Zustand
  override protected def updateView(state: String): Unit = {
    resultLabel.text = state
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

// Main-Application, um die GUI zu starten
object GameApp extends JFXApp {
  GameGUISingleton.setGUI("level1", "Beispiel", 5)
  stage = new JFXApp.PrimaryStage {
    title = "Galgenmännchen"
    scene = GameGUISingleton.getCurrentGUI.get.getPanel
  }
}
