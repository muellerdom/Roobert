package Controller

import Model.GameModel
import View.GameView

// Definieren von verschiedenen Phasen des Spiels
sealed trait GameStage
case object InitializationStage extends GameStage
case object UserInputStage extends GameStage
case object ProcessMoveStage extends GameStage
case object GameEndStage extends GameStage

// Abstrakte Controller-Klasse mit Schablonenmethode
abstract class GameController(model: GameModel, view: GameView) {

  private var currentStage: GameStage = InitializationStage

  // Schablonenmethode
  def startGame(): Unit = {
    while (currentStage != GameEndStage) {
      currentStage match {
        case InitializationStage => initializeGame()
        case UserInputStage => promptForMove()
        case ProcessMoveStage => processMove(getUserInput())
        case GameEndStage => endGame()
      }
    }
  }

  // Schritt 1: Initialisierung des Spiels (kann variabel sein, aber Grundlogik bleibt gleich)
  protected def initializeGame(): Unit = {
    model.initializeGame()
    updateView()
    currentStage = UserInputStage  // Wechsel zur nächsten Phase
  }

  // Schritt 2: Überprüfung, ob das Level abgeschlossen ist
  protected def isLevelCompleted: Boolean = model.isOnTarget

  // Schritt 3: Benutzeraufforderung
  protected def promptForMove(): Unit = {
    view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
    currentStage = ProcessMoveStage  // Wechsel zur nächsten Phase
  }

  // Schritt 4: Benutzereingabe verarbeiten
  protected def getUserInput(): String = scala.io.StdIn.readLine()

  // Schritt 5: Überprüfen der Eingabe
  protected def isValidMove(input: String): Boolean = {
    input == "right" || input == "down" || input == "up" || input == "left"
  }

  // Schritt 6: Verarbeite die Bewegung und aktualisiere das Spielfeld
  protected def processMove(input: String): Unit = {
    if (isValidMove(input)) {
      model.move(input)
      if (model.isOnTarget) {
        view.displayMessage("Hurrah! Das Ziel erreicht!")
      }
      updateView()
      if (isLevelCompleted) {
        currentStage = GameEndStage  // Wechsel zur Spielende-Phase, falls Ziel erreicht
      } else {
        currentStage = UserInputStage  // Weiter zu nächsten Eingabeaufforderung
      }
    } else {
      view.displayMessage("Ungültiger Move. Versuche es erneut.")
      currentStage = UserInputStage  // Zurück zur Eingabeaufforderung
    }
  }

  // Schritt 7: Ansicht aktualisieren
  protected def updateView(): Unit = {
    view.displayBoard(model.getBoard)
  }

  // Schritt 8: Das Spiel beenden
  protected def endGame(): Unit = {
    view.displayMessage("Das Spiel ist zu Ende!")
  }

  def getAvailableLevels: List[String] = {
    // Hier sollten die verfügbaren Level zurückgegeben werden
    List("Level1", "Level2")
  }
}
