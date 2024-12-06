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

  // Enumeration der verschiedenen Spielphasen
  sealed trait GameStage
  case object InitializationStage extends GameStage
  case object UserInputStage extends GameStage
  case object ProcessMoveStage extends GameStage
  case object GameEndStage extends GameStage

  // Aktuelle Spielphase
  private var currentStage: GameStage = InitializationStage

  // Template-Methode: Definiert den allgemeinen Ablauf des Spiels
  def startGame(): Unit = {
    // Initialisierung des Spiels
    model.initializeGame()
    updateView() // Zeige das anfängliche Spielfeld an
    currentStage = UserInputStage // Setze die Phase auf Benutzereingabe

    // Schleife, die solange läuft, bis das Ziel erreicht ist
    while (currentStage != GameEndStage) {
      currentStage match {
        case InitializationStage => initializeGame()
        case UserInputStage => promptForMove()
        case ProcessMoveStage => processMove()
        case GameEndStage => endGame()
      }
    }
  }

  // Abstrakte Methoden, die von der konkreten Implementierung überschrieben werden müssen
  protected def isValidMove(input: String): Boolean
  protected def moveCharacter(input: String): Unit
  protected def updateView(): Unit
  protected def isLevelCompleted: Boolean

  // Methoden zur Verwaltung der verschiedenen Phasen
  private def initializeGame(): Unit = {
    // Hier können z.B. Level und Spielfigur initialisiert werden
    println("Spiel wird initialisiert...")
    currentStage = UserInputStage // Wechsel zu Benutzereingabe
  }

  private def promptForMove(): Unit = {
    // Zeige Aufforderung für die Benutzereingabe
    view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
    currentStage = ProcessMoveStage // Wechsel zu Verarbeitungsphase
  }

  private def processMove(): Unit = {
    // Liest die Eingabe des Benutzers
    val input = StdIn.readLine()

    // Überprüfe, ob die Eingabe gültig ist
    if (isValidMove(input)) {
      moveCharacter(input) // Bewege die Spielfigur
      updateView() // Aktualisiere die Ansicht
      if (isLevelCompleted) {
        view.displayMessage("Hurrah! Das Ziel wurde erreicht!") // Wenn das Ziel erreicht ist
        currentStage = GameEndStage // Wechsel zu Spielende
      } else {
        currentStage = UserInputStage // Zurück zur Benutzereingabe
      }
    } else {
      view.displayMessage("Ungültiger Befehl. Bitte versuche es erneut.") // Bei ungültiger Eingabe
      currentStage = UserInputStage // Wieder zur Benutzereingabe
    }
  }

  private def endGame(): Unit = {
    // Das Spiel ist zu Ende
    view.displayMessage("Das Spiel ist zu Ende!")
  }
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
