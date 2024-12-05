package Controller

import Model.GameModel
import View.GameView

// Abstrakte Controller-Klasse mit Schablonenmethode
abstract class GameController(model: GameModel, view: GameView) {

  // Schablonenmethode
  def startGame(): Unit = {
    initializeGame()
    while (!isLevelCompleted) {
      promptForMove()
      val input = getUserInput()
      if (isValidMove(input)) {
        processMove(input)
        updateView()
      }
    }
    endGame()
  }

  // Schritt 1: Initialisierung des Spiels (kann variabel sein, aber Grundlogik bleibt gleich)
  protected def initializeGame(): Unit = {
    model.initializeGame()
    updateView()
  }

  // Schritt 2: Überprüfung, ob das Level abgeschlossen ist
  protected def isLevelCompleted: Boolean = model.isOnTarget

  // Schritt 3: Benutzeraufforderung
  protected def promptForMove(): Unit = {
    view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
  }

  // Schritt 4: Benutzereingabe verarbeiten
  protected def getUserInput(): String = scala.io.StdIn.readLine()

  // Schritt 5: Überprüfen der Eingabe
  protected def isValidMove(input: String): Boolean = {
    input == "right" || input == "down" || input == "up" || input == "left"
  }

  // Schritt 6: Verarbeite die Bewegung und aktualisiere das Spielfeld
  protected def processMove(input: String): Unit = {
    model.move(input)
    if (model.isOnTarget) {
      view.displayMessage("Hurrah! Das Ziel erreicht!")
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
}
