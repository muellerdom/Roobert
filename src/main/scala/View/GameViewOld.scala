package View


class GameView {
  def displayBoard(board: Array[Array[Char]]): Unit = {
    println("Aktuelles Spielfeld:")
    for (row <- board) {
      println(row.mkString(" "))
    }
  }

  def displayMessage(message: String): Unit = println(message)
}
