package Controller

import Model.GameModel
import View.GameView
import scala.io.StdIn
import Util.Observable

/**
 * Controller für das Spiel im MVC-Pattern
 * Verwaltet die Interaktionen zwischen Model und View.
 */
class GameController private(model: GameModel, view: GameView) extends Observable {

  // Startet das Spiel
  def startGame(): Unit = {
    model.initializeGame() // Initialisiere das Spiel
    updateView()           // Zeige das anfängliche Spielfeld an

    // Schleife, die solange läuft, bis das Ziel erreicht ist
    while (!model.isOnTarget) {
      view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
      val input = StdIn.readLine() // Liest die Eingabe des Benutzers

      input match {
        case "right" | "down" | "up" | "left" => // Überprüfe, ob die Eingabe gültig ist
          if (model.move(input)) { // Versuche, die Figur zu bewegen
            if (model.isOnTarget) {
              view.displayMessage("Hurrah!") // Wenn das Ziel erreicht ist, feiere
            }
            updateView()         // Update die Ansicht
            notifyObservers()    // Benachrichtige alle Observer (z. B. View), dass sich das Spiel geändert hat
          } else {
            view.displayMessage("Ungültige Bewegung. Versuche es erneut.") // Gib eine Fehlermeldung bei ungültiger Bewegung aus
          }

        case _ => view.displayMessage("Ungültiger Befehl. Bitte versuche es erneut.") // Bei ungültiger Eingabe
      }
    }
  }

  // Methode, um die Ansicht (View) zu aktualisieren
  private def updateView(): Unit = {
    view.displayBoard(model.getBoard) // Zeige das aktualisierte Spielfeld
  }
}

/**
 * Factory zur Erstellung von GameController-Instanzen.
 */
object GameControllerFactory {

  /**
   * Factory-Methode zur Erstellung eines GameController.
   * @param model Das Model, das die Spielzustände verwaltet.
   * @param view Die View, die die GUI oder CLI für das Spiel bereitstellt.
   * @return Eine neue Instanz von GameController.
   */
  def createController(model: GameModel, view: GameView): GameController = {
    new GameController(model, view)
  }
}

/**
 * Singleton-Objekt zur Verwaltung der GameController-Instanz.
 */
object GameController {
  // Singleton-Instanz
  private var instance: Option[GameController] = None

  /**
   * Liefert die Singleton-Instanz des GameControllers.
   * @param model Das Model, das die Spielzustände verwaltet.
   * @param view Die View, die die GUI oder CLI für das Spiel bereitstellt.
   * @return Die Singleton-Instanz von GameController.
   */
  def getInstance(model: GameModel, view: GameView): GameController = {
    if (instance.isEmpty) {
      instance = Some(GameControllerFactory.createController(model, view))
    }
    instance.get
  }
}
