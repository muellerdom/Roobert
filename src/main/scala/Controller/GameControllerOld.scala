package Controller

import Model.GameModel
import View.GameView
import scala.io.StdIn
import Util.Observable

class GameController(model: GameModel, view: GameView) extends Observable {

  // Startet das Spiel
  def startGame(): Unit = {
    model.initializeGame()  // Initialisiere das Spiel
    updateView()  // Zeige das anfängliche Spielfeld an

    // Schleife, die solange läuft, bis das Ziel erreicht ist
    while (!model.isOnTarget) {
      view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
      val input = StdIn.readLine()  // Liest die Eingabe des Benutzers

      input match {
        case "right" | "down" | "up" | "left" =>  // Überprüfe, ob die Eingabe gültig ist
          if (model.move(input)) {  // Versuche, die Figur zu bewegen
            if (model.isOnTarget) {
              view.displayMessage("Hurrah!")  // Wenn das Ziel erreicht ist, feiere
            }
            updateView()  // Update die Ansicht
            notifyObservers()  // Benachrichtige alle Observer (z. B. View), dass sich das Spiel geändert hat
          } else {
            view.displayMessage("Ungültige Bewegung. Versuche es erneut.")  // Gib eine Fehlermeldung bei ungültiger Bewegung aus
          }

        case _ => view.displayMessage("Ungültiger Befehl. Bitte versuche es erneut.")  // Bei ungültiger Eingabe
      }
    }
  }

  // Methode, um die Ansicht (View) zu aktualisieren
  private def updateView(): Unit = {
    view.displayBoard(model.getBoard)  // Zeige das aktualisierte Spielfeld
  }
}
