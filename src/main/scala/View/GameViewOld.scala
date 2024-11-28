package View

import Model.GameModel
import Util.Observer

class GameView extends Observer {

  // Diese Methode wird aufgerufen, wenn das Modell aktualisiert wird
  override def update(): Unit = {
    val board = GameModel.getBoard // Hier wird das aktuelle Spielfeld aus dem Modell geholt
    displayBoard(board)
  }

  // Diese Methode zeigt das Spielfeld an
  def displayBoard(board: Array[Array[Char]]): Unit = {
    println("Aktuelles Spielfeld:")
    for (row <- board) {
      println(row.mkString(" "))
    }
  }

  // Diese Methode zeigt Nachrichten im Spiel an
  def displayMessage(message: String): Unit = {
    println(message)
  }
}
