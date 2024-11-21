import Model.GameModel
import View.GameView
import Controller.GameController

object Game {
  def main(args: Array[String]): Unit = {
    val boardSize = 5
    val model = new GameModel(boardSize)
    val view = new GameView
    val controller = new GameController(model, view)

    controller.startGame()
  }
}
