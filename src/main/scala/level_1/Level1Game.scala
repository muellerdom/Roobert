package level_1

import scala.io.StdIn

// Klasse für das Spielfeld
class Level1Game(size: Int) {

  private val board: Array[Array[Char]] = Array.fill(size, size)('-')

  // Anfangspositionen
  private var figureX: Int = 0
  private var figureY: Int = 0

  // Zielposition
  private val targetX: Int = 2
  private val targetY: Int = 0

  // Methode zur Anzeige des Spielfelds
  def display(): Unit = {
    println("Aktuelles Spielfeld:")
    for (row <- board) {
      println(row.mkString(" "))
    }
  }

  // Initiale Platzierung der Figuren
  def initializeGame(): Unit = {
    placeFigure(figureX, figureY, 'F') // Figur platzieren
    placeFigure(targetX, targetY, 'Z') // Ziel platzieren
  }

  // Platzierung einer Figur
  def placeFigure(x: Int, y: Int, symbol: Char): Boolean = {
    if (isValidPosition(x, y)) {
      board(x)(y) = symbol
      true
    } else {
      println("Ungültige Position. Bitte erneut versuchen.")
      false
    }
  }

  // rechts
  def moveRight(): Boolean = {
    val newX = figureX
    val newY = figureY + 1

    if (isValidPosition(newX, newY) && board(newX)(newY) == '-') {

      board(figureX)(figureY) = '-'

      figureY += 1
      board(figureX)(figureY) = 'F'
      true
    } else {
      println("Die Bewegung nach rechts ist nicht möglich.")
      false
    }
  }

  //  unten
  def moveDown(): Boolean = {
    val newX = figureX + 1
    val newY = figureY

    if (isValidPosition(newX, newY) && (board(newX)(newY) == '-' || board(newX)(newY) == 'Z') ){

      board(figureX)(figureY) = '-'

      figureX += 1
      board(figureX)(figureY) = 'F'
      true
    } else {
      println("Die Bewegung nach unten ist nicht möglich.")
      false
    }
  }

  // oben
  def moveUp(): Boolean = {
    val newX = figureX - 1
    val newY = figureY

    if (isValidPosition(newX, newY) && board(newX)(newY) == '-') {

      board(figureX)(figureY) = '-'

      figureX -= 1
      board(figureX)(figureY) = 'F'
      true
    } else {
      println("Die Bewegung nach oben ist nicht möglich.")
      false
    }
  }

  // Methode zur Bewegung der Figur nach links
  def moveLeft(): Boolean = {
    val newX = figureX
    val newY = figureY - 1

    if (isValidPosition(newX, newY) && board(newX)(newY) == '-') {

      board(figureX)(figureY) = '-'

      figureY -= 1
      board(figureX)(figureY) = 'F'
      true
    } else {
      println("Die Bewegung nach links ist nicht möglich.")
      false
    }
  }

  // Überprüfung der Position
  private def isValidPosition(x: Int, y: Int): Boolean = {
    x >= 0 && x < size && y >= 0 && y < size
  }

  //  Überprüfung, ob die Figur das Ziel erreicht hat oder auf der gleichen Position wie das Ziel steht
  def checkTarget(): Unit = {
    if (figureX == targetX && figureY == targetY) {
      println("Hurrah!") // Hurrah ausgeben, wenn die Figur auf dem Ziel steht
    }
  }
  // Überprüfung, ob die Figur auf der Zielposition steht
  def isOnTarget(): Boolean = {
    figureX == targetX && figureY == targetY
  }

}
object Game {
  def main(args: Array[String]): Unit = {
    val boardSize = 5
    val gameBoard = new Level1Game(boardSize)

    // Initialisiere das Spiel
    gameBoard.initializeGame()
    gameBoard.display()

    // Benutzerinteraktion für die Bewegung der Figur
    while (true) {
      println("Bitte gib 'moveRight()', 'moveDown()', 'moveUp()' oder 'moveLeft()' ein, um die Figur zu bewegen:")
      val input = StdIn.readLine()

      input match {
        case "moveRight()" =>
          if (gameBoard.moveRight()) {
            gameBoard.checkTarget()
            if (gameBoard.isOnTarget()) {
              println("Hurrah!")
            }
            gameBoard.display()
          }
        case "moveDown()" =>
          if (gameBoard.moveDown()) {
            gameBoard.checkTarget()
            if (gameBoard.isOnTarget()) {
              println("Hurrah!")
            }
            gameBoard.display()
          }
        case "moveUp()" =>
          if (gameBoard.moveUp()) {
            gameBoard.checkTarget()
            if (gameBoard.isOnTarget()) {
              println("Hurrah!")
            }
            gameBoard.display()
          }
        case "moveLeft()" =>
          if (gameBoard.moveLeft()) {
            gameBoard.checkTarget()
            if (gameBoard.isOnTarget()) {
              println("Hurrah!")
            }
            gameBoard.display()
          }
        case _ =>
          println("Ungültiger Befehl. Bitte versuche es erneut.")
      }
    }
  }
}
