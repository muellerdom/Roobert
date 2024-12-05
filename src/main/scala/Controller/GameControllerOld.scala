package Controller

import Model.GameModel
import View.GameView
import scala.io.StdIn
import Util.Observable

/**
 * Abstrakte Klasse für den GameController, die das Template Method Pattern implementiert.
 * Definiert den allgemeinen Ablauf des Spiels.
 */
abstract class AbstractGameController(model: GameModel, view: GameView) extends Observable {

  // Template-Methode: Definiert den allgemeinen Ablauf des Spiels
  def startGame(): Unit = {
    model.initializeGame() // Initialisiere das Spiel
    updateView()           // Zeige das anfängliche Spielfeld an

    // Schleife, die solange läuft, bis das Ziel erreicht ist
    while (!isLevelCompleted) {
      view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
      val input = StdIn.readLine() // Liest die Eingabe des Benutzers

      if (isValidMove(input)) { // Überprüfe, ob die Eingabe gültig ist
        moveCharacter(input) // Bewege die Figur
        if (isLevelCompleted) {
          view.displayMessage("Hurrah! Das Ziel wurde erreicht!") // Wenn das Ziel erreicht ist, feiere
        }
        updateView()         // Update die Ansicht
        notifyObservers()    // Benachrichtige alle Observer (z. B. View), dass sich das Spiel geändert hat
      } else {
        view.displayMessage("Ungültiger Befehl. Bitte versuche es erneut.") // Bei ungültiger Eingabe
      }
    }
  }

  // Abstrakte Methode, um die Eingabe zu validieren
  protected def isValidMove(input: String): Boolean

  // Abstrakte Methode, um die Spielfigur zu bewegen
  protected def moveCharacter(input: String): Unit

  // Abstrakte Methode, um die Ansicht zu aktualisieren
  protected def updateView(): Unit

  // Abstrakte Methode, um zu prüfen, ob das Level abgeschlossen ist
  protected def isLevelCompleted: Boolean
}

/**
 * Konkrete Implementierung des GameControllers für das Spiel.
 */
class GameController(model: GameModel, view: GameView) extends AbstractGameController(model, view) {

  // Validiert, ob die Eingabe eine gültige Richtung ist
  override protected def isValidMove(input: String): Boolean = {
    input match {
      case "right" | "down" | "up" | "left" => true
      case _ => false
    }
  }

  // Bewegt die Spielfigur basierend auf der Eingabe
  override protected def moveCharacter(input: String): Unit = {
    model.move(input)
  }

  // Aktualisiert die Ansicht, um das Spielfeld anzuzeigen
  override protected def updateView(): Unit = {
    view.displayBoard(model.getBoard)
  }

  // Überprüft, ob das Level abgeschlossen ist (wenn die Spielfigur das Ziel erreicht hat)
  override protected def isLevelCompleted: Boolean = model.isOnTarget
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
      instance = Some(new GameController(model, view)) // Direkte Instanziierung ohne Factory
    }
    instance.get
  }
}
