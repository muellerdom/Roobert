package Controller

import Model.GameModel
import View.GameView

import scala.io.StdIn
import Util.Observable

// Input in die View packen !

class GameController(model: GameModel, view: GameView) extends Observable {
  def startGame(): Unit = {
    model.initializeGame()
    updateView()


    while (!model.isOnTarget) {

      view.displayMessage("Bitte gib 'right', 'down', 'up' oder 'left' ein, um die Figur zu bewegen:")
      val input = StdIn.readLine()

      input match {
        case "right" | "down" | "up" | "left" =>
          if (model.move(input)) {
            if (model.isOnTarget) view.displayMessage("Hurrah!")
            updateView()
          } else {
            view.displayMessage("Ungültige Bewegung. Versuche es erneut.")
          }
        case _ => view.displayMessage("Ungültiger Befehl. Bitte versuche es erneut.")
      }
    }
  }

  private def updateView(): Unit = {
    view.displayBoard(model.getBoard)
  }
}
